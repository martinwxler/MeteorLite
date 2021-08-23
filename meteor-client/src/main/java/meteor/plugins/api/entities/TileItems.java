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

public class TileItems {
    @Inject
    private static Client client;

    public static List<TileItem> getAll(Predicate<TileItem> filter) {
        return Tiles.getTiles().stream()
                .flatMap(tile -> parseTile(null, filter).stream())
                .collect(Collectors.toList());
    }

    public static TileItem getNearest(Predicate<TileItem> filter) {
        Player local = Players.getLocal();
        return getAll(filter).stream()
                .min(Comparator.comparingInt(t -> t.getTile().getWorldLocation().distanceTo(local.getWorldLocation())))
                .orElse(null);
    }

    public static TileItem getNearest(int id) {
        return getNearest(x -> x.getId() == id);
    }

    public static TileItem getNearest(String name) {
        return getNearest(x -> x.getName() != null && x.getName().equals(name));
    }

    public static List<TileItem> getAt(LocalPoint localPoint, Predicate<TileItem> filter) {
        Tile tile = client.getScene().getTiles()[client.getPlane()][localPoint.getSceneX()][localPoint.getSceneY()];
        if (tile == null) {
            return Collections.emptyList();
        }

        return parseTile(tile, filter);
    }

    public static List<TileItem> getAt(WorldPoint worldPoint, Predicate<TileItem> filter) {
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

    public static List<TileItem> getAt(Tile tile, Predicate<TileItem> filter) {
        return parseTile(tile, filter);
    }

    private static List<TileItem> parseTile(Tile tile, Predicate<TileItem> pred) {
        List<TileItem> out = new ArrayList<>();
        Tiles.getTiles().forEach(t -> {
            if (t.getGroundItems() != null) {
                t.getGroundItems().forEach(item -> {
                    if (item == null || item.getId() == -1 || !pred.test(item)) {
                        return;
                    }

                    if (tile != null && !tile.getWorldLocation().equals(item.getTile().getWorldLocation())) {
                        return;
                    }

                    out.add(item);
                });
            }
        });

        return out;
    }
}
