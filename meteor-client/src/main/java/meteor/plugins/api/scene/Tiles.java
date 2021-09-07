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
    private static final int MAX_RANGE = 50;
    public static List<Tile> getTiles(Predicate<Tile> filter) {
        List<Tile> out = new ArrayList<>();
        Player local = Players.getLocal();

        for (int x = 0; x < Constants.SCENE_SIZE; x++) {
            for (int y = 0; y < Constants.SCENE_SIZE; y++) {
                Tile tile = Game.getClient().getScene().getTiles()[Game.getClient().getPlane()][x][y];
                if (tile != null
                        && tile.getWorldLocation().distanceTo(local.getWorldLocation()) <= MAX_RANGE
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

    public static Locatable getHoveredObject() {
        MenuEntry[] menuEntries = Game.getClient().getMenuEntries();
        if (menuEntries.length == 0) {
            return null;
        }

        MenuEntry top = menuEntries[menuEntries.length - 1];
        MenuAction menuAction = MenuAction.of(top.getType());

        switch (menuAction) {
            case ITEM_USE_ON_GAME_OBJECT:
            case SPELL_CAST_ON_GAME_OBJECT:
            case GAME_OBJECT_FIRST_OPTION:
            case GAME_OBJECT_SECOND_OPTION:
            case GAME_OBJECT_THIRD_OPTION:
            case GAME_OBJECT_FOURTH_OPTION:
            case GAME_OBJECT_FIFTH_OPTION: {
                int x = top.getParam0();
                int y = top.getParam1();
                int id = top.getIdentifier();
                return findTileObject(x, y, id);
            }
            case ITEM_USE_ON_NPC:
            case SPELL_CAST_ON_NPC:
            case NPC_FIRST_OPTION:
            case NPC_SECOND_OPTION:
            case NPC_THIRD_OPTION:
            case NPC_FOURTH_OPTION:
            case NPC_FIFTH_OPTION: {
                int id = top.getIdentifier();
                return findNpc(id);
            }

            default:
                return null;
        }
    }

    private static TileObject findTileObject(int x, int y, int id) {
        Scene scene = Game.getClient().getScene();
        Tile[][][] tiles = scene.getTiles();
        Tile tile = tiles[Game.getClient().getPlane()][x][y];
        if (tile != null) {
            for (GameObject gameObject : tile.getGameObjects()) {
                if (gameObject != null && gameObject.getId() == id) {
                    return gameObject;
                }
            }

            WallObject wallObject = tile.getWallObject();
            if (wallObject != null && wallObject.getId() == id) {
                return wallObject;
            }

            DecorativeObject decorativeObject = tile.getDecorativeObject();
            if (decorativeObject != null && decorativeObject.getId() == id) {
                return decorativeObject;
            }

            GroundObject groundObject = tile.getGroundObject();
            if (groundObject != null && groundObject.getId() == id) {
                return groundObject;
            }
        }
        return null;
    }

    private static NPC findNpc(int id) {
        return Game.getClient().getCachedNPCs()[id];
    }
}
