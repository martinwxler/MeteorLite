package meteor.plugins.cettitutorial.tasks;

import meteor.PluginTask;
import meteor.plugins.api.entities.NPCs;
import meteor.plugins.api.entities.Players;
import meteor.plugins.api.entities.TileObjects;
import meteor.plugins.api.game.Game;
import meteor.plugins.api.magic.Magic;
import meteor.plugins.api.magic.Regular;
import meteor.plugins.api.movement.Movement;
import meteor.plugins.api.widgets.Dialog;
import meteor.plugins.leftclickcast.Spells;
import net.runelite.api.NPC;
import net.runelite.api.World;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;

import static osrs.Client.logger;

public class MagicGuide implements PluginTask {

	@Override
	public boolean validate() {
		return Game.getClient().getVarpValue(281) < 1000;
	}

	private void talkToGuide() {
		NPC guide = NPCs.getNearest("Magic Instructor");
		if (Dialog.canContinue()) {
			Dialog.continueSpace();
		} else if (Dialog.isViewingOptions()) {
			Dialog.chooseOption("Yes", "No");
		} else {
			if (guide == null) {
				Movement.walk(new WorldPoint(3141, 3088, 0));
			} else {
				if (!Players.getLocal().isMoving()) {
					guide.interact(0);
				}
			}
		}
	}

	private void openSpellBook() {
		if (Dialog.canContinue()) {
			Dialog.continueSpace();
		}
		Game.getClient().getWidget(164, 65).interact("Magic");
	}

	private WorldArea magicHouse = new WorldArea(new WorldPoint(3143, 3089, 0), new WorldPoint(3140, 3087, 0));

	private void killChicken() {
		if (Players.getLocal().getWorldLocation().equals(new WorldPoint(3141, 3090, 0))) {
			logger.info("Kill chicken");
			Magic.cast(Regular.WIND_STRIKE, NPCs.getNearest("Chicken"));
		} else {
			if (Players.getLocal().isIdle()) {
				Movement.walk(new WorldPoint(3141, 3090, 0));
			}
		}
	}

	@Override
	public int execute() {
		logger.info("prog: " + Game.getClient().getVarpValue(281));
		switch (Game.getClient().getVarpValue(281)) {
			case 620, 640, 670 -> talkToGuide();
			case 630 -> openSpellBook();
			case 650 -> killChicken();
		}
		return 1000;
	}
}
