package meteor.plugins.cettitutorial.tasks;

import meteor.PluginTask;
import meteor.plugins.api.entities.NPCs;
import meteor.plugins.api.entities.Players;
import meteor.plugins.api.entities.TileObjects;
import meteor.plugins.api.game.Game;
import meteor.plugins.api.movement.Movement;
import meteor.plugins.api.movement.Reachable;
import meteor.plugins.api.widgets.Dialog;
import net.runelite.api.NPC;
import net.runelite.api.coords.WorldPoint;

import static osrs.Client.logger;

public class PrayerGuide implements PluginTask {

	@Override
	public boolean validate() {
		return Game.getClient().getVarpValue(281) < 620;
	}

	private void talkToGuide() {
		NPC guide = NPCs.getNearest("Brother Brace");
		if (Dialog.canContinue()) {
			Dialog.continueSpace();
		} else {
			if (guide == null) {
				Movement.walk(new WorldPoint(3125, 3107, 0));
			} else {
				if (!Players.getLocal().isMoving()) {
					guide.interact(0);
				}
			}
		}
	}

	private void leaveArea() {
		if (!Players.getLocal().isMoving()) {
			TileObjects.getNearest(9723).interact(0);
		}
	}

	private void openPrayer() {
		if (Dialog.canContinue()) {
			Dialog.continueSpace();
		}
		Game.getClient().getWidget(164, 64).interact("Prayer");
	}

	private void openFriendsList() {
		if (Dialog.canContinue()) {
			Dialog.continueSpace();
		}
		Game.getClient().getWidget(164, 46).interact("Friends List");
	}

	@Override
	public int execute() {
		logger.info("prog: " + Game.getClient().getVarpValue(281));
		switch (Game.getClient().getVarpValue(281)) {
			case 550, 570, 600 -> talkToGuide();
			case 560 -> openPrayer();
			case 580 -> openFriendsList();
			case 610 -> leaveArea();
		}
		return 1000;
	}
}
