package meteor.plugins.api.movement;

import meteor.plugins.api.entities.Players;
import meteor.plugins.api.game.Game;
import meteor.plugins.api.game.Vars;
import meteor.plugins.api.movement.pathfinder.*;
import meteor.plugins.api.packets.MovementPackets;
import meteor.plugins.api.scene.Tiles;
import meteor.plugins.api.widgets.Widgets;
import meteor.ui.overlay.OverlayUtil;
import net.runelite.api.Point;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import org.sponge.util.Logger;

import java.awt.*;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;

public class Movement {
    private static final Logger logger = new Logger("Movement");
    private static final Color TILE_BLOCKED_COLOR = new Color(0, 128, 255, 128);
    private static final Color TRANSPORT_COLOR = new Color(0, 255, 0, 128);

    private static final int STAMINA_VARBIT = 25;
    private static final int RUN_VARP = 173;

    public static void setDestination(int worldX, int worldY) {
        MovementPackets.sendMovement(worldX, worldY, Movement.isRunEnabled());
        Game.getClient().setDestinationX(worldX - Game.getClient().getBaseX());
        Game.getClient().setDestinationY(worldY - Game.getClient().getBaseY());
    }

    public static boolean isWalking() {
        Player local = Players.getLocal();
        LocalPoint destination = Game.getClient().getLocalDestinationLocation();
        return local.isMoving()
                && destination != null
                && destination.distanceTo(local.getLocalLocation()) > 4;
    }

    public static void walk(Point point) {
        Game.getClient().interact(0, MenuAction.WALK.getId(), point.getX(), point.getY());
    }

    public static void walk(WorldPoint worldPoint) {
        Player local = Game.getClient().getLocalPlayer();
        if (local == null) {
            return;
        }

        WorldPoint walkPoint = worldPoint;
        Tile destinationTile = Tiles.getAt(worldPoint);
        // Check if tile is in loaded client scene
        if (destinationTile == null) {
            logger.debug("Destination {} is not in scene", worldPoint);
            Tile nearestInScene = Tiles.getTiles()
                    .stream()
                    .min(Comparator.comparingInt(x -> x.getWorldLocation().distanceTo(local.getWorldLocation())))
                    .orElse(null);
            if (nearestInScene == null) {
                logger.debug("Couldn't find nearest walkable tile");
                return;
            }

            walkPoint = nearestInScene.getWorldLocation();
        }

        Game.getClient().interact(0, MenuAction.WALK.getId(), walkPoint.getX(), walkPoint.getY());
    }

    public static void walk(Locatable locatable) {
        walk(locatable.getWorldLocation());
    }

    public static boolean walkTo(WorldPoint worldPoint) {
        return Walker.walkTo(worldPoint);
    }

    public static boolean walkTo(Locatable locatable) {
        return walkTo(locatable.getWorldLocation());
    }

    public static boolean walkTo(BankLocation bankLocation) {
        return walkTo(bankLocation.getArea().toWorldPoint());
    }

    public static boolean walkTo(int x, int y) {
        return walkTo(x, y, Game.getClient().getPlane());
    }

    public static boolean walkTo(int x, int y, int plane) {
        return walkTo(new WorldPoint(x, y, plane));
    }

    public static boolean isRunEnabled() {
        return Game.getClient().getVarpValue(RUN_VARP) == 1;
    }

    public static void drawPath(Graphics2D graphics2D, WorldPoint destination) {
        Walker.buildPath(destination)
                .forEach(tile -> tile.outline(Game.getClient(), graphics2D, Color.RED, null));
        destination.outline(Game.getClient(), graphics2D, Color.GREEN, "Destination");
    }

    public static void drawCollisions(Graphics2D graphics2D) {
        Client client = Game.getClient();
        java.util.List<Tile> tiles = Tiles.getTiles();

        if (tiles.isEmpty()) {
            return;
        }

        List<Transport> transports = TransportLoader.buildTransports();

        for (Transport transport : transports) {
            OverlayUtil.fillTile(graphics2D, client, transport.getSource(), TRANSPORT_COLOR);
            net.runelite.api.Point center = Perspective.tileCenter(client, transport.getSource());
            if (center == null) {
                continue;
            }

            Point linkCenter = Perspective.tileCenter(client, transport.getDestination());
            if (linkCenter == null) {
                continue;
            }

            graphics2D.drawLine(center.getX(), center.getY(), linkCenter.getX(), linkCenter.getY());
        }

        CollisionMap collisionMap = Walker.COLLISION_MAP;
        if (collisionMap == null) {
            return;
        }

        for (Tile tile : tiles) {
            Polygon poly = Perspective.getCanvasTilePoly(client, tile.getLocalLocation());
            if (poly == null) {
                continue;
            }

            StringBuilder sb = new StringBuilder("");
            graphics2D.setColor(Color.WHITE);
            if (!collisionMap.n(tile.getWorldLocation())) {
                sb.append("n");
            }

            if (!collisionMap.s(tile.getWorldLocation())) {
                sb.append("s");
            }

            if (!collisionMap.w(tile.getWorldLocation())) {
                sb.append("w");
            }

            if (!collisionMap.e(tile.getWorldLocation())) {
                sb.append("e");
            }

            String s = sb.toString();
            if (s.isEmpty()) {
                continue;
            }

            if (!s.equals("nswe")) {
                graphics2D.setColor(Color.WHITE);
                if (s.contains("n")) {
                    graphics2D.drawLine(poly.xpoints[3], poly.ypoints[3], poly.xpoints[2], poly.ypoints[2]);
                }

                if (s.contains("s")) {
                    graphics2D.drawLine(poly.xpoints[0], poly.ypoints[0], poly.xpoints[1], poly.ypoints[1]);
                }

                if (s.contains("w")) {
                    graphics2D.drawLine(poly.xpoints[0], poly.ypoints[0], poly.xpoints[3], poly.ypoints[3]);
                }

                if (s.contains("e")) {
                    graphics2D.drawLine(poly.xpoints[1], poly.ypoints[1], poly.xpoints[2], poly.ypoints[2]);
                }

                int stringX = (int)
                        (poly.getBounds().getCenterX() -
                                graphics2D.getFontMetrics().getStringBounds(s, graphics2D).getWidth() / 2);
                int stringY = (int) poly.getBounds().getCenterY();
                graphics2D.drawString(s, stringX, stringY);
                continue;
            }

            graphics2D.setColor(TILE_BLOCKED_COLOR);
            graphics2D.fill(poly);
        }
    }

    public static void toggleRun() {
        Widget widget = Widgets.get(WidgetInfo.MINIMAP_TOGGLE_RUN_ORB);
        if (widget != null) {
            widget.interact("Toggle Run");
        }
    }

    public static boolean isStaminaBoosted() {
        return Vars.getBit(STAMINA_VARBIT) == 1;
    }

    public static int calculateDistance(WorldPoint destination) {
        List<WorldPoint> path = Walker.buildPath(destination);

        if (path.size() < 2) {
            return 0;
        }

        Iterator<WorldPoint> it = path.iterator();
        WorldPoint prev = it.next();
        WorldPoint current;
        int distance = 0;

        // WorldPoint#distanceTo() returns max int when planes are different, but since the pathfinder can traverse
        // obstacles, we just add one to the distance to account for whatever obstacle is in between the current point
        // and the next.
        while (it.hasNext()) {
            current = it.next();
            if (prev.getPlane() != current.getPlane()) {
                distance += 1;
            } else {
                distance += Math.max(Math.abs(prev.getX() - current.getX()), Math.abs(prev.getY() - current.getY()));
            }
        }
        return distance;
    }
}
