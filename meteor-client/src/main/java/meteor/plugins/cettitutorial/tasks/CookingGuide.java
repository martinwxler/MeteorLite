package meteor.plugins.cettitutorial.tasks;

import meteor.PluginTask;
import meteor.plugins.api.entities.NPCs;
import meteor.plugins.api.entities.Players;
import meteor.plugins.api.entities.TileObjects;
import meteor.plugins.api.game.Game;
import meteor.plugins.api.items.Inventory;
import meteor.plugins.api.widgets.Dialog;
import net.runelite.api.ItemID;

import static osrs.Client.logger;

public class CookingGuide implements PluginTask {

    @Override
    public boolean validate() {
        return Game.getClient().getVarpValue(281) < 200;
    }

    private void talkToGuide() {
        if (Dialog.canContinue()) {
            Dialog.continueSpace();
        } else {
            if (!Players.getLocal().isMoving()) {
                NPCs.getNearest("Master Chef").interact("Talk-to");
            }
        }
    }

    private void enterArea() {
        if (!Players.getLocal().isMoving()) {
            TileObjects.getNearest(9709).interact(0);
        }
    }

    private void leaveArea() {
        if (!Players.getLocal().isMoving()) {
            TileObjects.getNearest(9710).interact(0);
        }
    }

    private void makeDough() {
        if (!Players.getLocal().isAnimating()) {
            Inventory.getFirst(2516).useOn(Inventory.getFirst(1929));
        }
    }

    private void cookFood() {
        if (!Players.getLocal().isAnimating()) {
            Inventory.getFirst(2307).useOn(TileObjects.getNearest(9736));
        }
    }

    @Override
    public int execute() {
        logger.info("prog: " + Game.getClient().getVarpValue(281));
        switch (Game.getClient().getVarpValue(281)) {
            case 130 -> enterArea();
            case 140 -> talkToGuide();
            case 150 -> makeDough();
            case 160 -> cookFood();
            case 170 -> leaveArea();
        }
        return 1000;
    }
}
