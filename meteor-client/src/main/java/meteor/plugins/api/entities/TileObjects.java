package meteor.plugins.api.entities;

import meteor.plugins.api.game.GameThread;
import meteor.plugins.api.scene.Tiles;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TileObjects {
    @Inject
    private static Client client;

    public static List<TileObject> getAll(Predicate<TileObject> filter) {
        return Tiles.getTiles().stream()
                .flatMap(tile -> parseTile(tile, filter).stream())
                .collect(Collectors.toList());
    }

    public static TileObject getNearest(Predicate<TileObject> filter) {
        Player local = client.getLocalPlayer();
        if (local == null) {
            return null;
        }

        return getAll(filter).stream()
                .min(Comparator.comparingInt(t -> t.getWorldLocation().distanceTo(local.getWorldLocation())))
                .orElse(null);
    }

    public static TileObject getNearest(int id) {
        return getNearest(x -> x.getId() == id);
    }

    public static TileObject getNearest(String name) {
        return getNearest(x -> x.getName() != null && x.getName().equals(name));
    }

    public static List<TileObject> getAt(LocalPoint localPoint, Predicate<TileObject> filter) {
        Tile tile = client.getScene().getTiles()[client.getPlane()][localPoint.getSceneX()][localPoint.getSceneY()];
        if (tile == null) {
            return Collections.emptyList();
        }

        return parseTile(tile, filter);
    }

    public static List<TileObject> getAt(WorldPoint worldPoint, Predicate<TileObject> filter) {
        LocalPoint localPoint = LocalPoint.fromWorld(client, worldPoint);
        if (localPoint == null) {
            return Collections.emptyList();
        }

        Tile tile = client.getScene().getTiles()[client.getPlane()][localPoint.getSceneX()][localPoint.getSceneY()];
        if (tile == null) {
            return Collections.emptyList();
        }

        return parseTile(tile, filter);
    }

    public static List<TileObject> getAt(Tile tile, Predicate<TileObject> filter) {
        return parseTile(tile, filter);
    }

    private static List<TileObject> parseTile(Tile tile, Predicate<TileObject> pred) {
        Predicate<TileObject> filter = x -> {
            if (x.getId() == -1) {
                return false;
            }

            if (!client.isTileObjectValid(tile, x)) {
                return false;
            }

            if (!x.isDefinitionCached()) {
                return GameThread.invokeLater(() -> {
                    x.getCachedDefinition(); // cache it
                    return pred.test(x);
                });
            }

            return pred.test(x);
        };

        List<TileObject> out = new ArrayList<>();
        DecorativeObject dec = tile.getDecorativeObject();
        if (dec != null && filter.test(dec)) {
            out.add(dec);
        }

        WallObject wall = tile.getWallObject();
        if (wall != null && filter.test(wall)) {
            out.add(wall);
        }

        GroundObject grnd = tile.getGroundObject();
        if (grnd != null && filter.test(grnd)) {
            out.add(grnd);
        }

        GameObject[] gameObjects = tile.getGameObjects();
        if (gameObjects != null) {
            for (GameObject gameObject : gameObjects) {
                if (gameObject == null || !filter.test(gameObject)) {
                    continue;
                }

                out.add(gameObject);
            }
        }

        return out;
    }
}
