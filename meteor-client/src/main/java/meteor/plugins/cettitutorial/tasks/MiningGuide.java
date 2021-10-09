package meteor.plugins.cettitutorial.tasks;

import meteor.PluginTask;
import meteor.plugins.api.commons.Time;
import meteor.plugins.api.entities.NPCs;
import meteor.plugins.api.entities.Players;
import meteor.plugins.api.entities.TileObjects;
import meteor.plugins.api.game.Game;
import meteor.plugins.api.items.Inventory;
import meteor.plugins.api.movement.Movement;
import meteor.plugins.api.movement.pathfinder.Walker;
import meteor.plugins.api.widgets.Dialog;
import net.runelite.api.Item;
import net.runelite.api.ItemID;
import net.runelite.api.NPC;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.WidgetInfo;

import static osrs.Client.logger;

public class MiningGuide implements PluginTask {

    @Override
    public boolean validate() {
        return Game.getClient().getVarpValue(281) < 370;
    }

    private void talkToGuide() {
        NPC guide = NPCs.getNearest("Mining Instructor");
        if (Dialog.canContinue()) {
            Dialog.continueSpace();
        } else {
            if (guide == null) {
                Movement.walk(new WorldPoint(3080, 9500, 0));
            } else {
                if (!Players.getLocal().isMoving()) {
                    guide.interact(0);
                }
            }
        }
    }

    private void leaveArea() {
        if (!Players.getLocal().isMoving()) {
            TileObjects.getNearest(9717).interact(0);
        }
    }

    private void mineOre(int ore) {
        if (!Players.getLocal().isAnimating() && !Players.getLocal().isMoving()) {
            TileObjects.getNearest(ore).interact(0);

            if (ore == 10080) {
                Time.sleepUntil(() -> Inventory.contains(ItemID.TIN_ORE), 5000);
            } else {
                Time.sleepUntil(() -> Inventory.contains(ItemID.COPPER_ORE), 5000);
            }
        }
    }

    private void smeltOre() {
        if (!Players.getLocal().isAnimating() && !Players.getLocal().isMoving()) {
            TileObjects.getNearest(10082).interact(0);
            Time.sleepUntil(() -> Inventory.contains(ItemID.BRONZE_BAR), 5000);
        }
    }

    private void useAnvil() {
        if (Game.getClient().getWidget(WidgetInfo.SMITHING_ANVIL_DAGGER) != null) {
            Game.getClient().getWidget(WidgetInfo.SMITHING_ANVIL_DAGGER).interact("Smith");
        } else {
            if (!Players.getLocal().isMoving() && !Players.getLocal().isAnimating()) {
                TileObjects.getNearest(2097).interact(0);
            }
        }
    }

    @Override
    public int execute() {
        logger.info("prog: " + Game.getClient().getVarpValue(281));
        switch (Game.getClient().getVarpValue(281)) {
            case 260, 330 -> talkToGuide();
            case 270 -> mineOre(10080);
            case 310 -> mineOre(10079);
            case 320 -> smeltOre();
            case 340, 350 -> useAnvil();
            case 360 -> leaveArea();
        }
        return 1000;
    }
}
