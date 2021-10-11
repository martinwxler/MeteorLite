package meteor.plugins.cettitutorial.tasks;

import meteor.PluginTask;
import meteor.plugins.api.commons.Time;
import meteor.plugins.api.entities.NPCs;
import meteor.plugins.api.entities.Players;
import meteor.plugins.api.entities.TileObjects;
import meteor.plugins.api.game.Game;
import meteor.plugins.api.game.Vars;
import meteor.plugins.api.items.Inventory;
import meteor.plugins.api.items.Items;
import meteor.plugins.api.widgets.Dialog;
import meteor.plugins.api.widgets.Tab;
import meteor.plugins.api.widgets.Tabs;
import net.runelite.api.Item;
import net.runelite.api.ItemID;
import net.runelite.api.NPC;
import net.runelite.api.TileObject;
import osrs.GameObject;

import static osrs.Client.logger;

public class SurvivalGuide implements PluginTask {

	@Override
	public boolean validate() {
		return Vars.getVarp(281) < 130;
	}

	private void talkToGuide() {
		if (Dialog.canContinue()) {
			Dialog.continueSpace();
			return;
		}

		if (Players.getLocal().isMoving()) {
			return;
		}

		NPC expert = NPCs.getNearest("Survival Expert");
		if (expert == null) {
			return;
		}

		expert.interact("Talk-to");
	}

	private void openInventory() {
		if (Dialog.canContinue()) {
			Dialog.continueSpace();
		}

		Tabs.open(Tab.INVENTORY);
	}

	private void openSkills() {
		if (Dialog.canContinue()) {
			Dialog.continueSpace();
		}

		Tabs.open(Tab.SKILLS);
	}

	private void leaveArea() {
		if (Players.getLocal().isMoving()) {
			return;
		}

		TileObject obj = TileObjects.getNearest(9708);
		if (obj == null) {
			return;
		}

		obj.interact(0);
//            Time.sleep(600);
	}

	private void fishPond() {
		if (Players.getLocal().isMoving() || Players.getLocal().isAnimating()) {
			return;
		}

		NPCs.getNearest(3317).interact("Net");
	}

	private void chopTree() {
		if (Dialog.canContinue()) {
			Dialog.continueSpace();
		}

		if (Players.getLocal().isMoving() || Players.getLocal().isAnimating()) {
			return;
		}

		TileObject tree = TileObjects.getNearest(9730);
		if (tree == null) {
			return;
		}

		tree.interact("Chop down");
	}

	private void lightFire() {
		if (Players.getLocal().isAnimating()) {
			return;
		}

		Inventory.getFirst(ItemID.TINDERBOX).useOn(Inventory.getFirst(2511));
	}

	private void cookFood() {
		if (Players.getLocal().isAnimating()) {
			return;
		}

		Item food = Inventory.getFirst(2514);
		TileObject fire = TileObjects.getNearest(26185);
		if (food == null || fire == null) {
			return;
		}

		food.useOn(fire);
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
