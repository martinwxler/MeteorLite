package meteor.plugins.api.scene;

import meteor.plugins.api.entities.Players;
import meteor.plugins.api.game.Game;
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
    public static List<Tile> getTiles(Predicate<Tile> filter) {
        List<Tile> out = new ArrayList<>();
        Player local = Players.getLocal();

        for (int x = 0; x < Constants.SCENE_SIZE; x++) {
            for (int y = 0; y < Constants.SCENE_SIZE; y++) {
                Tile tile = Game.getClient().getScene().getTiles()[Game.getClient().getPlane()][x][y];
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
            LocalPoint localPoint = LocalPoint.fromWorld(Game.getClient(), x.getWorldLocation());
            if (localPoint == null) {
                return false;
            }

            Polygon poly = Perspective.getCanvasTilePoly(Game.getClient(), localPoint);
            if (poly == null) {
                return false;
            }

            return poly.contains(Game.getClient().getMouseCanvasPosition().getX(), Game.getClient().getMouseCanvasPosition().getY());
        }).stream().findFirst().orElse(null);
    }
}
