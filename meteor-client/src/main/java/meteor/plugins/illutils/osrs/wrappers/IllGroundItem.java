package meteor.plugins.illutils.osrs.wrappers;

import meteor.plugins.illutils.osrs.OSRSUtils;
import net.runelite.api.Client;
import net.runelite.api.ItemComposition;
import net.runelite.api.MenuAction;
import net.runelite.api.TileItem;
import meteor.plugins.illutils.api.Interactable;
import meteor.plugins.illutils.api.scene.Locatable;
import meteor.plugins.illutils.api.scene.Position;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class IllGroundItem implements Locatable, Interactable {
    private final OSRSUtils game;
    private final TileItem tileItem;
    private final ItemComposition definition;

    public IllGroundItem(OSRSUtils game, TileItem tileItem, ItemComposition definition) {
        this.game = game;
        this.tileItem = tileItem;
        this.definition = definition;
    }

    public OSRSUtils game() {
        return game;
    }

    public Client client() {
        return game.client;
    }

    @Override
    public Position position() {
        return new Position(tileItem.getTile().getWorldLocation());
    }

    public int id() {
        return tileItem.getId();
    }

    public int quantity() {
        return tileItem.getQuantity();
    }

    public String name() {
        return definition.getName();
    }

    @Override
    public List<String> actions() {
        return Arrays.stream(definition.getGroundActions())
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public void interact(String action) {
        for (int i = 0; i < actions().size(); i++) {
            if (action.equalsIgnoreCase(actions().get(i))) {
                interact(i);
                return;
            }
        }
        throw new IllegalArgumentException("no action \"" + action + "\" on ground item " + id());
    }

    private int getActionId(int action) {
        switch (action) {
            case 0:
                return MenuAction.GROUND_ITEM_FIRST_OPTION.getId();
            case 1:
                return MenuAction.GROUND_ITEM_SECOND_OPTION.getId();
            case 2:
                return MenuAction.GROUND_ITEM_THIRD_OPTION.getId();
            case 3:
                return MenuAction.GROUND_ITEM_FOURTH_OPTION.getId();
            case 4:
                return MenuAction.GROUND_ITEM_FIFTH_OPTION.getId();
            default:
                throw new IllegalArgumentException("action = " + action);
        }
    }

    public void interact(int action) {
        game.interactionManager().interact(
                id(),
                action,
                tileItem.getTile().getSceneLocation().getX(),
                tileItem.getTile().getSceneLocation().getY()
        );
    }

    public void pickup() {
        game.interactionManager().interact(
            id(),
            MenuAction.GROUND_ITEM_THIRD_OPTION.getId(),
            tileItem.getTile().getSceneLocation().getX(),
            tileItem.getTile().getSceneLocation().getY()
        );
    }

    public String toString() {
        return name() + " (" + id() + ")" + (quantity() == 1 ? "" : " x" + quantity());
    }
}
