package meteor.plugins.api.entities;

import meteor.plugins.api.game.Game;
import meteor.plugins.api.game.GameThread;
import meteor.plugins.api.scene.Tiles;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;

import javax.inject.Inject;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TileObjects {
    public static List<TileObject> getAll(Predicate<TileObject> filter) {
        return getTileObjects(Tiles.getTiles()).stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    public static List<TileObject> getAll(int... ids) {
        return getAll(x -> {
            for (int id : ids) {
                if (id == x.getId()) {
                    return true;
                }
            }

            return false;
        });
    }

    public static List<TileObject> getAll(String... names) {
        return getAll(x -> {
            if (x.getName() == null) {
                return false;
            }

            for (String name : names) {
                if (name.equals(x.getName())) {
                    return true;
                }
            }

            return false;
        });
    }

    public static TileObject getNearest(Predicate<TileObject> filter) {
        Player local = Game.getClient().getLocalPlayer();
        if (local == null) {
            return null;
        }

        return getAll(filter).stream()
                .min(Comparator.comparingInt(t -> t.getWorldLocation().distanceTo(local.getWorldLocation())))
                .orElse(null);
    }

    public static TileObject getNearest(int... ids) {
        return getNearest(x -> {
            for (int id : ids) {
                if (id == x.getId()) {
                    return true;
                }
            }

            return false;
        });
    }

    public static TileObject getNearest(String... names) {
        return getNearest(x -> {
            if (x.getName() == null) {
                return false;
            }

            for (String name : names) {
                if (name.equals(x.getName())) {
                    return true;
                }
            }

            return false;
        });
    }

    public static List<TileObject> getAt(LocalPoint localPoint, Predicate<TileObject> filter) {
        Tile tile = Game.getClient().getScene().getTiles()[Game.getClient().getPlane()][localPoint.getSceneX()][localPoint.getSceneY()];
        if (tile == null) {
            return Collections.emptyList();
        }

        return parseTile(tile, filter);
    }

    public static List<TileObject> getAt(WorldPoint worldPoint, Predicate<TileObject> filter) {
        LocalPoint localPoint = LocalPoint.fromWorld(Game.getClient(), worldPoint);
        if (localPoint == null) {
            return Collections.emptyList();
        }

        Tile tile = Game.getClient().getScene().getTiles()[Game.getClient().getPlane()][localPoint.getSceneX()][localPoint.getSceneY()];
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
            if (!x.isDefinitionCached()) {
                return GameThread.invokeLater(() -> {
                    x.getCachedDefinition(); // cache it
                    return pred.test(x);
                });
            }

            return pred.test(x);
        };

        return getTileObjects(tile).stream()
                .filter(filter)
                .collect(Collectors.toList());
    }

    private static List<TileObject> getTileObjects(List<Tile> tiles) {
        List<TileObject> out = new ArrayList<>();
        for (Tile tile : tiles) {
            out.addAll(getTileObjects(tile));
        }

        List<TileObject> notCached = out.stream()
                .filter(x -> !x.isDefinitionCached())
                .collect(Collectors.toList());
        if (!notCached.isEmpty()) {
            GameThread.invokeLater(() -> {
                for (TileObject tileObject : notCached) {
                    tileObject.getCachedDefinition();
                }

                return true;
            });
        }

        return out;
    }

    private static List<TileObject> getTileObjects(Tile tile) {
        List<TileObject> out = new ArrayList<>();
        DecorativeObject dec = tile.getDecorativeObject();
        if (dec != null && dec.getId() != -1) {
            out.add(dec);
        }

        WallObject wall = tile.getWallObject();
        if (wall != null && wall.getId() != -1) {
            out.add(wall);
        }

        GroundObject grnd = tile.getGroundObject();
        if (grnd != null && grnd.getId() != -1) {
            out.add(grnd);
        }

        GameObject[] gameObjects = tile.getGameObjects();
        if (gameObjects != null) {
            for (GameObject gameObject : gameObjects) {
                if (gameObject == null
                        || !Game.getClient().isTileObjectValid(tile, gameObject)
                        || gameObject.getId() == -1) {
                    continue;
                }

                out.add(gameObject);
            }
        }

        return out;
    }
}
