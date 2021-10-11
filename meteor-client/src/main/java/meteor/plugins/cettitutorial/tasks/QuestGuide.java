package meteor.plugins.cettitutorial.tasks;

import meteor.PluginTask;
import meteor.plugins.api.entities.NPCs;
import meteor.plugins.api.entities.Players;
import meteor.plugins.api.entities.TileObjects;
import meteor.plugins.api.game.Game;
import meteor.plugins.api.items.Inventory;
import meteor.plugins.api.widgets.Dialog;

import static osrs.Client.logger;

public class QuestGuide implements PluginTask {

	@Override
	public boolean validate() {
		return Game.getClient().getVarpValue(281) < 260;
	}

	private void talkToGuide() {
		if (Dialog.canContinue()) {
			Dialog.continueSpace();
		} else {
			if (!Players.getLocal().isMoving()) {
				NPCs.getNearest("Quest Guide").interact("Talk-to");
			}
		}
	}

	private void enterArea() {
		if (!Players.getLocal().isMoving()) {
			TileObjects.getNearest(9716).interact(0);
		}
	}

	private void leaveArea() {
		if (!Players.getLocal().isMoving()) {
			TileObjects.getNearest(9726).interact(0);
		}
	}

	private void openQuests() {
		if (Dialog.canContinue()) {
			Dialog.continueSpace();
		}
		Game.getClient().getWidget(164, 61).interact("Quest List");
	}

	@Override
	public int execute() {
		logger.info("prog: " + Game.getClient().getVarpValue(281));
		switch (Game.getClient().getVarpValue(281)) {
			case 200 -> enterArea();
			case 220, 240 -> talkToGuide();
			case 230 -> openQuests();
			case 250 -> leaveArea();
		}
		return 1000;
	}
}
