package meteor.plugins.api.scene;

import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Singleton
public class Tiles {
    @Inject
    private static Client client;

    public static List<Tile> getTiles(Predicate<Tile> filter) {
        List<Tile> out = new ArrayList<>();
        Player local = client.getLocalPlayer();
        if (local == null) {
            return out;
        }

        for (int x = 0; x < Constants.SCENE_SIZE; x++) {
            for (int y = 0; y < Constants.SCENE_SIZE; y++) {
                Tile tile = client.getScene().getTiles()[client.getPlane()][x][y];
                if (tile != null
                        && tile.getWorldLocation().distanceTo(local.getWorldLocation()) <= 50
                        && filter.test(tile)) {
                    out.add(tile);
                }
            }
        }

        return out;
    }

    public static List<Tile> getTiles() {
        return getTiles(x -> true);
    }

    public static Tile getHoveredTile() {
        return getTiles(x -> {
            LocalPoint localPoint = LocalPoint.fromWorld(client, x.getWorldLocation());
            if (localPoint == null) {
                return false;
            }

            Polygon poly = Perspective.getCanvasTilePoly(client, localPoint);
            if (poly == null) {
                return false;
            }

            return poly.contains(client.getMouseCanvasPosition().getX(), client.getMouseCanvasPosition().getY());
        }).stream().findFirst().orElse(null);
    }
}
