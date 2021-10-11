package meteor.plugins.cettitutorial.tasks;

import meteor.PluginTask;
import meteor.plugins.api.commons.Time;
import meteor.plugins.api.entities.NPCs;
import meteor.plugins.api.entities.Players;
import meteor.plugins.api.entities.TileObjects;
import meteor.plugins.api.game.Combat;
import meteor.plugins.api.game.Game;
import meteor.plugins.api.items.Equipment;
import meteor.plugins.api.items.Inventory;
import meteor.plugins.api.movement.Movement;
import meteor.plugins.api.movement.Reachable;
import meteor.plugins.api.widgets.Dialog;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.InteractingChanged;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetItem;

import static osrs.Client.logger;

public class CombatGuide implements PluginTask {

	@Override
	public boolean validate() {
		return Game.getClient().getVarpValue(281) < 510;
	}

	private void talkToGuide() {
		if (Dialog.canContinue()) {
			Dialog.continueSpace();
		} else {
			if (!Players.getLocal().isMoving()) {
				NPCs.getNearest("Combat Instructor").interact("Talk-to");
			}
		}
	}

	private void enterArea() {
		if (!Players.getLocal().isMoving()) {
			TileObjects.getNearest(9719).interact(0);
		}
	}

	private void leaveArea() {
		if (!Players.getLocal().isMoving()) {
			TileObjects.getNearest(9727).interact(0);
		}
	}

	private void openEquipment() {
		if (Dialog.canContinue()) {
			Dialog.continueSpace();
		}
		Game.getClient().getWidget(164, 63).interact("Worn Equipment");
	}

	private void viewEquipment() {
		if (Dialog.canContinue()) {
			Dialog.continueSpace();
		}
		Game.getClient().getWidget(387, 1).interact("View equipment stats");
	}

	private void openCombat() {
		if (Dialog.canContinue()) {
			Dialog.continueSpace();
		}
		Game.getClient().getWidget(164, 59).interact("Combat Options");
	}

	private void equipEquipItems(int... items) {
		Widget[] eq_items = Game.getClient().getWidget(85, 0).getChildren();

		// ADD TO API FOR EQUIPING MUTLIPLE ITEMS
		if (eq_items != null) {
			for (Widget eq_item : eq_items) {
				for (int item : items) {
					if (eq_item.getItemId() == item) {
						eq_item.interact("Equip");
					}
				}
			}
		}
	}

	private void equipItems(int... items) {
		for (Item invItem : Inventory.getAll()) {
			for (int item : items)
				if (invItem.getId() == item) {
					invItem.interact("Wield");
				}
		}
	}

	private void killRat(boolean ranged) {
		if (ranged) {
			WorldPoint rangedSpot = new WorldPoint(3111, 9518, 0);
			if (Players.getLocal().isIdle() && !Players.getLocal().getWorldLocation().equals(rangedSpot)) {
				Movement.walk(rangedSpot);
				Time.sleepUntil(() -> Players.getLocal().getWorldLocation().equals(rangedSpot), 2000);
			}
		}
		NPC rat = NPCs.getNearest("Giant rat");
		if (Players.getLocal().isIdle()) {
			logger.info("Attacking rat");
			rat.interact("Attack");
		} else {
			logger.info("We are interacting with " + Players.getLocal().getInteracting());
		}
	}

	@Override
	public int execute() {
		logger.info("prog: " + Game.getClient().getVarpValue(281));
		switch (Game.getClient().getVarpValue(281)) {
			case 370, 410 -> talkToGuide();
			case 390 -> openEquipment();
			case 400 -> viewEquipment();
			case 405 -> equipEquipItems(1205);
			case 420 -> equipItems(ItemID.BRONZE_SWORD, ItemID.WOODEN_SHIELD);
			case 430 -> openCombat();
			case 440 -> enterArea();
			case 450, 460 -> killRat(false);
			case 470 -> {
				if (Reachable.isWalkable(NPCs.getNearest("Combat Instructor").getWorldLocation())) {
					talkToGuide();
				} else {
					enterArea();
				}
			}
			case 480 -> {
				if (!Equipment.contains(ItemID.SHORTBOW) || !Equipment.contains(ItemID.BRONZE_ARROW)) {
					equipItems(ItemID.SHORTBOW, ItemID.BRONZE_ARROW);
				} else {
					killRat(true);
				}
			}
			case 500 -> leaveArea();
		}
		return 1000;
	}
}
