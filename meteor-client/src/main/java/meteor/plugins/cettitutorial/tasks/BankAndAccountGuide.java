package meteor.plugins.cettitutorial.tasks;

import meteor.PluginTask;
import meteor.plugins.api.entities.NPCs;
import meteor.plugins.api.entities.Players;
import meteor.plugins.api.entities.TileObjects;
import meteor.plugins.api.game.Game;
import meteor.plugins.api.items.Equipment;
import meteor.plugins.api.items.Inventory;
import meteor.plugins.api.movement.Movement;
import meteor.plugins.api.movement.Reachable;
import meteor.plugins.api.widgets.Dialog;
import net.runelite.api.Item;
import net.runelite.api.ItemID;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.widgets.Widget;

import static osrs.Client.logger;

public class BankAndAccountGuide implements PluginTask {

	@Override
	public boolean validate() {
		return Game.getClient().getVarpValue(281) < 550;
	}

	private void talkToGuide() {
		if (Dialog.canContinue()) {
			Dialog.continueSpace();
		} else {
			if (!Players.getLocal().isMoving()) {
				NPCs.getNearest("Account Guide").interact("Talk-to");
			}
		}
	}

	private void enterArea() {
		if (!Players.getLocal().isMoving()) {
			TileObjects.getNearest(9721).interact(0);
		}
	}

	private void leaveArea() {
		if (!Players.getLocal().isMoving()) {
			TileObjects.getNearest(9722).interact(0);
		}
	}

	private void openAccountManagement() {
		if (Dialog.canContinue()) {
			Dialog.continueSpace();
		}
		Game.getClient().getWidget(164, 45).interact("Account Management");
	}

	@Override
	public int execute() {
		logger.info("prog: " + Game.getClient().getVarpValue(281));
		switch (Game.getClient().getVarpValue(281)) {
			case 510 -> {
				if (Players.getLocal().isIdle()) {
					TileObjects.getNearest(10083).interact("Use");
				}
			}
			case 520 -> {
				if (Dialog.canContinue()) {
					Dialog.continueSpace();
				} else if (Players.getLocal().isIdle()) {
					TileObjects.getNearest(26815).interact("Use");
				}
			}
			case 525 -> enterArea();
			case 530 -> talkToGuide();
			case 531 -> openAccountManagement();
			case 532 -> talkToGuide();
			case 540 -> leaveArea();
		}
		return 1000;
	}
}
