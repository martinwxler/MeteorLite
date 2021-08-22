package meteor.plugins.api.movement;

import lombok.extern.slf4j.Slf4j;
import meteor.plugins.api.commons.Rand;
import meteor.plugins.api.movement.pathfinder.CollisionMap;
import meteor.plugins.api.movement.pathfinder.Walker;
import meteor.plugins.api.scene.Tiles;
import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import org.sponge.util.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Comparator;
import java.util.zip.GZIPInputStream;

@Singleton
public class Movement {
    private Logger logger = new Logger("Movement");

    @Inject
    private Client client;

    @Inject
    private Reachable reachable;

    @Inject
    private Tiles tiles;

    @Inject
    private Rand rand;

    private final CollisionMap collisionMap;

    public Movement() {
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

    public void setDestination(int sceneX, int sceneY) {
        client.setSelectedSceneTileX(sceneX);
        client.setSelectedSceneTileY(sceneY);
        client.setViewportWalking(true);
        logger.debug("Setting destination {} {}", sceneX, sceneY);
//        client.setCheckClick(true);
    }

    public boolean isWalking() {
        Player local = client.getLocalPlayer();
        LocalPoint destination = client.getLocalDestinationLocation();
        return local != null
                && local.isMoving()
                && destination != null
                && destination.distanceTo(local.getLocalLocation()) > 4;
    }

    public void walk(Point point) {
        setDestination(point.getX(), point.getY());
    }

    public void walk(int worldX, int worldY) {
        int sceneX = worldX - client.getBaseX();
        int sceneY = worldY - client.getBaseY();

        if (sceneX > 104) {
            sceneX = 104;
        }

        if (sceneX < 0) {
            sceneX = 0;
        }

        if (sceneY > 104) {
            sceneY = 104;
        }

        if (sceneY < 0) {
            sceneY = 0;
        }

        walk(new Point(sceneX, sceneY));
    }

    public void walk(WorldPoint worldPoint) {
        Player local = client.getLocalPlayer();
        if (local == null) {
            return;
        }

        WorldPoint walkPoint = worldPoint;
        Tile destinationTile = tiles.getTiles(t -> t.getWorldLocation().equals(worldPoint))
                .stream()
                .findFirst()
                .orElse(null);
        // Check if tile is in loaded client scene
        if (destinationTile == null) {
            logger.debug("Destination {} is not in scene", worldPoint);
            Tile nearestInScene = tiles.getTiles()
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

        setDestination(localPoint.getSceneX(), localPoint.getSceneY());
    }

    public void walk(Locatable locatable) {
        walk(locatable.getWorldLocation());
    }

    public void walkTo(WorldPoint worldPoint) {
        new Walker(client, reachable, tiles, rand, this, collisionMap)
                .walkTo(worldPoint);
    }

    public boolean isRunEnabled() {
        return client.getVarpValue(173) == 1;
    }

    public void drawPath(Graphics2D graphics2D, WorldPoint destination) {
        new Walker(client, reachable, tiles, rand, this, collisionMap)
                .buildPath(destination)
                .forEach(tile -> tile.outline(client, graphics2D, Color.RED, null));
        destination.outline(client, graphics2D, Color.GREEN, "Destination");
    }
}
