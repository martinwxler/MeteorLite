package meteor.plugins.cettitutorial.tasks;

import meteor.PluginTask;
import meteor.plugins.api.commons.Time;
import meteor.plugins.api.entities.NPCs;
import meteor.plugins.api.entities.Players;
import meteor.plugins.api.entities.TileObjects;
import meteor.plugins.api.game.Game;
import meteor.plugins.api.items.Inventory;
import meteor.plugins.api.items.Items;
import meteor.plugins.api.widgets.Dialog;
import net.runelite.api.Item;
import net.runelite.api.ItemID;
import osrs.GameObject;

import static osrs.Client.logger;

public class SurvivalGuide implements PluginTask {

    @Override
    public boolean validate() {
        return Game.getClient().getVarpValue(281) < 130;
    }

    private void talkToGuide() {
        if (Dialog.canContinue()) {
            Dialog.continueSpace();
        } else {
            if (!Players.getLocal().isMoving()) {
                NPCs.getNearest("Survival Expert").interact("Talk-to");
            }
        }
    }

    private void openInventory() {
        if (Dialog.canContinue()) {
            Dialog.continueSpace();
        }
        Game.getClient().getWidget(164, 62).interact("Inventory");
    }

    private void openSkills() {
        if (Dialog.canContinue()) {
            Dialog.continueSpace();
        }
        Game.getClient().getWidget(164, 60).interact("Skills");
    }

    private void leaveArea() {
        if (!Players.getLocal().isMoving()) {
            TileObjects.getNearest(9708).interact(0);
//            Time.sleep(600);
        }
    }

    private void fishPond() {
        if (!Players.getLocal().isMoving() && !Players.getLocal().isAnimating()) {
            NPCs.getNearest(3317).interact("Net");
        }
    }

    private void chopTree() {
        if (Dialog.canContinue()) {
            Dialog.continueSpace();
        }
        if (!Players.getLocal().isMoving() && !Players.getLocal().isAnimating()) {
            TileObjects.getNearest(9730).interact("Chop down");
        }
    }

    private void lightFire() {
        if (!Players.getLocal().isAnimating()) {
            Inventory.getFirst(ItemID.TINDERBOX).useOn(Inventory.getFirst(2511));
        }
    }

    private void cookFood() {
        if (!Players.getLocal().isAnimating()) {
            Inventory.getFirst(2514).useOn(TileObjects.getNearest(26185));
        }
    }

    @Override
    public int execute() {
        logger.info("prog: " + Game.getClient().getVarpValue(281));
        switch (Game.getClient().getVarpValue(281)) {
            case 20, 60 -> {
                logger.info("Talk to guide");
                talkToGuide();
            }
            case 30 -> openInventory();
            case 40 -> fishPond();
            case 50 -> openSkills();
            case 70 -> chopTree();
            case 80 -> lightFire();
            case 90 -> cookFood();
            case 120 -> leaveArea();
        }
        return 1000;
    }
}
