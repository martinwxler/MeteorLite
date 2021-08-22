package meteor.plugins.api.movement.pathfinder;

import meteor.plugins.api.commons.Rand;
import meteor.plugins.api.commons.Time;
import meteor.plugins.api.movement.Movement;
import meteor.plugins.api.movement.Reachable;
import meteor.plugins.api.scene.Tiles;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.Tile;
import net.runelite.api.WallObject;
import net.runelite.api.coords.WorldPoint;
import org.sponge.util.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.*;
import java.util.zip.GZIPInputStream;

@Singleton
public class Walker {
    private static final Logger logger = new Logger("Walker");
    private static final int MAX_INTERACT_DISTANCE = 20;
    private static final int MIN_TILES_WALKED_IN_STEP = 10;
    private static final int MIN_TILES_WALKED_BEFORE_RECHOOSE = 10;
    private static final int MIN_TILES_LEFT_BEFORE_RECHOOSE = 3;
    private static final int MAX_MIN_ENERGY = 50;
    private static final int MIN_ENERGY = 5;

    private static List<Transport> transports = TransportLoader.buildTransports();
    private static List<Teleport> teleports = TeleportLoader.buildTeleports();

    @Inject
    private static Client client;

    private static final CollisionMap collisionMap;

    static {
        CollisionMap loaded;
        try {
            loaded = new CollisionMap(
                    new GZIPInputStream(
                            new ByteArrayInputStream(
                                    Walker.class.getResourceAsStream("/collision-map").readAllBytes()
                            )
                    ).readAllBytes()
            );
        } catch (IOException e) {
            e.printStackTrace();
            loaded = null;
        }

        collisionMap = loaded;
    }

    public static boolean walkTo(WorldPoint destination) {
        Player local = client.getLocalPlayer();
        if (local == null) {
            return false;
        }

        Map<WorldPoint, List<Transport>> transports = buildTransportLinks();
        LinkedHashMap<WorldPoint, Teleport> teleports = buildTeleportLinks(destination);
        List<WorldPoint> startPoints = new ArrayList<>(teleports.keySet());
        startPoints.add(local.getWorldLocation());
        List<WorldPoint> path = calculatePath(startPoints, destination, transports);

        if (path.isEmpty()) {
            return false;
        }

        WorldPoint startPosition = path.get(0);
        Teleport teleport = teleports.get(startPosition);

        if (teleport != null) {
            teleport.getHandler().run();
            logger.debug("Running teleport handler");
            Time.sleep(5000);
            return false;
        }

        return walkAlong(path, transports);
    }

    public static boolean walkAlong(List<WorldPoint> path, Map<WorldPoint, List<Transport>> transports) {
        Player local = client.getLocalPlayer();
        if (local == null) {
            return false;
        }

        WorldPoint destination = path.get(path.size() - 1);
        if (local.getWorldLocation().distanceTo(destination) > 0) {
            List<WorldPoint> remainingPath = remainingPath(path);

            if (handleTransports(remainingPath, transports)) {
                logger.debug("Running transport handler");
                return false;
            }

            return stepAlong(remainingPath);
        }

        return false;
    }

    public static boolean stepAlong(List<WorldPoint> path) {
        List<WorldPoint> reachablePath = reachablePath(path);
        if (reachablePath.isEmpty()) {
            return false;
        }

        if (reachablePath.size() - 1 <= MIN_TILES_WALKED_IN_STEP) {
            return step(reachablePath.get(reachablePath.size() - 1), Integer.MAX_VALUE);
        }

        int targetDistance = MIN_TILES_WALKED_IN_STEP + Rand.nextInt(0, reachablePath.size() - MIN_TILES_WALKED_IN_STEP);
        int rechooseDistance = recalculateDistance(targetDistance);

        return step(reachablePath.get(targetDistance), rechooseDistance);
    }

    public static List<WorldPoint> reachablePath(List<WorldPoint> remainingPath) {
        Player local = client.getLocalPlayer();
        if (local == null) {
            return Collections.emptyList();
        }

        List<WorldPoint> out = new ArrayList<>();
        for (WorldPoint p : remainingPath) {
            Tile tile = Tiles.getTiles(x -> x.getWorldLocation().equals(p)).stream().findFirst().orElse(null);
            if (tile == null) {
                break;
            }

            out.add(p);
        }

        if (out.isEmpty() || out.size() == 1 && out.get(0).equals(local.getWorldLocation())) {
            return Collections.emptyList();
        }

        return out;
    }

    public static boolean step(WorldPoint destination, int tiles) {
        Player local = client.getLocalPlayer();
        if (local == null) {
            return false;
        }

        logger.debug("Stepping towards " + destination);
        Movement.walk(destination);
        int tilesWalked = 0;

        while (tilesWalked < tiles) {
            if (Movement.isRunEnabled()) {
                tilesWalked += 2;
            } else {
                tilesWalked++;
            }

            if (local.getWorldLocation().equals(destination)) {
                return false;
            }

            if (!Movement.isRunEnabled() && (client.getEnergy() >= Rand.nextInt(MIN_ENERGY, MAX_MIN_ENERGY) || (local.getHealthScale() > -1 && client.getEnergy() > 0))) {
                // TODO: toggle run and sleep
            }

            // TODO: check if stam boosted and run not enabled
        }

        return true;
    }

    public static int recalculateDistance(int targetDistance) {
        int rechoose = MIN_TILES_WALKED_BEFORE_RECHOOSE + Rand.nextInt(0, targetDistance - MIN_TILES_WALKED_BEFORE_RECHOOSE + 1);
        rechoose = Math.min(rechoose, targetDistance - MIN_TILES_LEFT_BEFORE_RECHOOSE);
        return rechoose;
    }

    public static boolean handleTransports(List<WorldPoint> path, Map<WorldPoint, List<Transport>> transports) {
        Player local = client.getLocalPlayer();
        if (local == null) {
            return false;
        }

        for (int i = 0; i < MAX_INTERACT_DISTANCE; i++) {
            if (i + 1 >= path.size()) {
                break;
            }

            WorldPoint a = path.get(i);
            WorldPoint b = path.get(i + 1);
            Tile tileA = Tiles.getTiles(x -> x.getWorldLocation().equals(a)).stream().findFirst().orElse(null);
            if (tileA == null) {
                return false;
            }

            List<Transport> transportTargets = transports.get(a);
            if (transportTargets != null) {
                Transport transport = transportTargets.stream().filter(x -> x.getDestination().equals(b)).findFirst().orElse(null);

                if (transport != null && local.getWorldLocation().distanceTo(transport.getSource()) <= transport.getSourceRadius()) {
                    transport.getHandler().run();
                    Time.sleep(2800);
                    return true;
                }
            }

            Tile tileB = Tiles.getTiles(x -> x.getWorldLocation().equals(b)).stream().findFirst().orElse(null);
            if (tileB == null) {
                return false;
            }

            if (isDoored(tileA, tileB)) {
                tileA.getWallObject().interact("Open");
                Time.sleep(2000);
                return true;
            }

            if (isDoored(tileB, tileA)) {
                tileB.getWallObject().interact("Open");
                Time.sleep(2000);
                return true;
            }
        }

        return false;
    }

    public static boolean isDoored(Tile source, Tile destination) {
        WallObject wall = source.getWallObject();
        if (wall == null) {
            return false;
        }

        return isWalled(source, destination) && wall.hasAction("Open");
    }

    public static boolean isWalled(Tile source, Tile destination) {
        WallObject wall = source.getWallObject();
        if (wall == null) {
            return false;
        }

        WorldPoint a = source.getWorldLocation();
        WorldPoint b = destination.getWorldLocation();

        return switch (wall.getOrientationA()) {
            case 1 -> a.dx(-1).equals(b) || a.dx(-1).dy(1).equals(b) || a.dx(-1).dy(-1).equals(b);
            case 2 -> a.dy(1).equals(b) || a.dx(-1).dy(1).equals(b) || a.dx(1).dy(1).equals(b);
            case 4 -> a.dx(1).equals(b) || a.dx(1).dy(1).equals(b) || a.dx(1).dy(-1).equals(b);
            case 8 -> a.dy(-1).equals(b) || a.dx(-1).dy(-1).equals(b) || a.dx(-1).dy(1).equals(b);
            default -> false;
        };
    }

    public static List<WorldPoint> remainingPath(List<WorldPoint> path) {
        Player local = client.getLocalPlayer();
        if (local == null) {
            return Collections.emptyList();
        }

        var nearest = path.stream().min(Comparator.comparingInt(x -> x.distanceTo(local.getWorldLocation())))
                .orElse(null);
        if (nearest == null) {
            return Collections.emptyList();
        }

        return path.subList(path.indexOf(nearest), path.size());
    }

    public static List<WorldPoint> calculatePath(
            List<WorldPoint> startPoints,
            WorldPoint destination,
            Map<WorldPoint, List<Transport>> transports
    ) {
        if (collisionMap == null) {
            return Collections.emptyList();
        }

        return new Pathfinder(collisionMap, transports, startPoints,
                destination, client).find();
    }

    public static Map<WorldPoint, List<Transport>> buildTransportLinks() {
        Map<WorldPoint, List<Transport>> out = new HashMap<>();
        for (Transport transport : transports) {
            out.computeIfAbsent(transport.getSource(), x -> new ArrayList<>()).add(transport);
        }

        return out;
    }

    public static List<WorldPoint> buildPath(WorldPoint destination) {
        Player local = client.getLocalPlayer();

        if (local == null) {
            return Collections.emptyList();
        }

        Map<WorldPoint, List<Transport>> transports = buildTransportLinks();
        LinkedHashMap<WorldPoint, Teleport> teleports = buildTeleportLinks(destination);
        List<WorldPoint> startPoints = new ArrayList<>(teleports.keySet());
        startPoints.add(local.getWorldLocation());

        return calculatePath(startPoints, destination, transports);
    }

    public static LinkedHashMap<WorldPoint, Teleport> buildTeleportLinks(WorldPoint destination) {
        LinkedHashMap<WorldPoint, Teleport> out = new LinkedHashMap<>();
        Player local = client.getLocalPlayer();

        if (local == null) {
            return out;
        }

        for (Teleport teleport : teleports) {
            if (teleport.getDestination().distanceTo(local.getWorldLocation()) > 50
                    && local.getWorldLocation().distanceTo(destination) > teleport.getDestination().distanceTo(destination) + 20) {
                out.putIfAbsent(teleport.getDestination(), teleport);
            }
        }

        return out;
    }
}
