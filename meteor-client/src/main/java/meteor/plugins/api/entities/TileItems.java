package meteor.plugins.api.entities;

import meteor.plugins.api.game.Game;
import meteor.plugins.api.game.GameThread;
import meteor.plugins.api.scene.Tiles;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import org.sponge.util.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TileItems {
    private static final Logger logger = new Logger("TileItems");

    public static List<TileItem> getAll(Predicate<TileItem> filter) {
        return Tiles.getTiles().stream()
                .flatMap(tile -> parseTile(tile, filter).stream())
                .collect(Collectors.toList());
    }

    public static List<TileItem> getAll(int... ids) {
        return getAll(x -> {
            for (int id : ids) {
                if (id == x.getId()) {
                    return true;
                }
            }

            return false;
        });
    }

    public static List<TileItem> getAll(String... names) {
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

    public static TileItem getNearest(Predicate<TileItem> filter) {
        Player local = Players.getLocal();
        return getAll(filter).stream()
                .min(Comparator.comparingInt(t -> t.getTile().getWorldLocation().distanceTo(local.getWorldLocation())))
                .orElse(null);
    }

    public static TileItem getNearest(int... ids) {
        return getNearest(x -> {
            for (int id : ids) {
                if (id == x.getId()) {
                    return true;
                }
            }

            return false;
        });
    }

    public static TileItem getNearest(String... names) {
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

    public static List<TileItem> getAt(LocalPoint localPoint, Predicate<TileItem> filter) {
        Tile tile = Game.getClient().getScene().getTiles()[Game.getClient().getPlane()][localPoint.getSceneX()][localPoint.getSceneY()];
        if (tile == null) {
            return Collections.emptyList();
        }

        return parseTile(tile, filter);
    }

    public static List<TileItem> getAt(WorldPoint worldPoint, Predicate<TileItem> filter) {
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

    public static List<TileItem> getAt(Tile tile, Predicate<TileItem> filter) {
        return parseTile(tile, filter);
    }

    private static List<TileItem> parseTile(Tile tile, Predicate<TileItem> pred) {
        List<TileItem> out = new ArrayList<>();
        if (tile.getGroundItems() != null) {
            for (TileItem item : tile.getGroundItems()) {
                if (item == null || item.getId() == -1) {
                    continue;
                }

                if (!Game.getClient().isItemDefinitionCached(item.getId())) {
                    GameThread.invokeLater(() -> Game.getClient().getItemComposition(item.getId()));
                }

                if (!pred.test(item)) {
                    continue;
                }

                out.add(item);
            }
        }

        return out;
    }
}
