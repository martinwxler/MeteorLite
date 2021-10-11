package meteor.plugins.cettitutorial.tasks;

import meteor.PluginTask;
import meteor.plugins.api.commons.Time;
import meteor.plugins.api.entities.NPCs;
import meteor.plugins.api.entities.Players;
import meteor.plugins.api.entities.TileObjects;
import meteor.plugins.api.game.Game;
import meteor.plugins.api.widgets.Dialog;

import static osrs.Client.logger;

public class GielinorGuide implements PluginTask {

	@Override
	public boolean validate() {
		return Game.getClient().getVarpValue(281) < 20;
	}

	private void talkToGuide() {
		if (Dialog.canContinue()) {
			logger.info("Can continue!");
			Dialog.continueSpace();
		} else if (Dialog.isViewingOptions()) {
			Dialog.chooseOption("I am an experienced player.");
		} else if (!Dialog.isViewingOptions()
						|| !Dialog.canContinue()
						|| !Dialog.canContinueTutIsland()
						|| !Dialog.canContinueTutIsland2()
						|| !Dialog.canContinueTutIsland3()) {
			logger.info("Not talking with NPC!");
			if (!Players.getLocal().isMoving()) {
				logger.info("Interacting with NPC");
				NPCs.getNearest("Gielinor Guide").interact("Talk-to");

			}
		}
	}

	private void openSettings() {
		if (Dialog.canContinue()) {
			Dialog.continueSpace();
		}
		Game.getClient().getWidget(164, 47).interact("Settings");
	}

	private void leaveArea() {
		if (!Players.getLocal().isMoving()) {
			TileObjects.getNearest(9398).interact(0);
			Time.sleep(600);
		}
	}

	@Override
	public int execute() {
		logger.info("Get through house: prog: " + Game.getClient().getVarpValue(281));
		switch (Game.getClient().getVarpValue(281)) {
			case 2, 7 -> {
				logger.info("Talk to guide");
				talkToGuide();
			}
			case 3 -> openSettings();
			case 10 -> leaveArea();
		}
		return 1000;
	}
}
