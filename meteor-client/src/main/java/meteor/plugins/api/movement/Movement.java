package meteor.plugins.api.movement;

import meteor.plugins.api.commons.Time;
import meteor.plugins.api.entities.Players;
import meteor.plugins.api.game.Game;
import meteor.plugins.api.game.Vars;
import meteor.plugins.api.movement.pathfinder.CollisionMap;
import meteor.plugins.api.movement.pathfinder.Transport;
import meteor.plugins.api.movement.pathfinder.TransportLoader;
import meteor.plugins.api.movement.pathfinder.Walker;
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

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Singleton
public class Movement {
    private static final Logger logger = new Logger("Movement");
    private static final Color TILE_BLOCKED_COLOR = new Color(0, 128, 255, 128);
    private static final Color TRANSPORT_COLOR = new Color(0, 255, 0, 128);

    private static final int STAMINA_VARBIT = 25;
    private static final int RUN_VARP = 173;

    @Inject
    private static Client client;

    @Inject
    private static ScheduledExecutorService executor;

    private static void setWalkDestination(int sceneX, int sceneY) {
        int attempts = 0;

        do {
            client.setSelectedSceneTileX(sceneX);
            client.setSelectedSceneTileY(sceneY);
            client.setViewportWalking(true);
            Time.sleep(25);
        } while (client.getLocalDestinationLocation() == null && attempts++ < 10);
    }

    public static void setDestination(int sceneX, int sceneY) {
        if (client.isClientThread()) {
            executor.schedule(() -> setWalkDestination(sceneX, sceneY), 25, TimeUnit.MILLISECONDS);
        } else {
            setWalkDestination(sceneX, sceneY);
        }
    }

    public static boolean isWalking() {
        Player local = Players.getLocal();
        LocalPoint destination = client.getLocalDestinationLocation();
        return local.isMoving()
                && destination != null
                && destination.distanceTo(local.getLocalLocation()) > 4;
    }

    public static void walk(Point point) {
        Game.getClient().interact(0, MenuAction.WALK.getId(), point.getX(), point.getY());
    }

    public static void walk(WorldPoint worldPoint) {
        Player local = client.getLocalPlayer();
        if (local == null) {
            return;
        }

        WorldPoint walkPoint = worldPoint;
        Tile destinationTile = Tiles.getTiles(t -> t.getWorldLocation().equals(worldPoint))
                .stream()
                .findFirst()
                .orElse(null);
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

        LocalPoint localPoint = LocalPoint.fromWorld(client, walkPoint);
        if (localPoint == null) {
            logger.debug("Couldn't convert destination point to local");
            return;
        }

        Game.getClient().interact(0, MenuAction.WALK.getId(), localPoint.getSceneX(), localPoint.getSceneY());
    }

    public static void walk(Locatable locatable) {
        walk(locatable.getWorldLocation());
    }

    public static void walkTo(WorldPoint worldPoint) {
        Walker.walkTo(worldPoint);
    }

    public static boolean isRunEnabled() {
        return client.getVarpValue(RUN_VARP) == 1;
    }

    public static void drawPath(Graphics2D graphics2D, WorldPoint destination) {
        Walker.buildPath(destination)
                .forEach(tile -> tile.outline(client, graphics2D, Color.RED, null));
        destination.outline(client, graphics2D, Color.GREEN, "Destination");
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

        CollisionMap collisionMap = Walker.collisionMap;
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
}
