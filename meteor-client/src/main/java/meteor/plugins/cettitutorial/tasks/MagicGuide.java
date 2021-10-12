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
import net.runelite.api.widgets.Widget;

import static osrs.Client.logger;

public class MagicGuide implements PluginTask {

	@Override
	public boolean validate() {
		return Game.getClient().getVarpValue(281) < 670;
	}

	private void talkToGuide() {
		if (Dialog.canContinue()) {
			Dialog.continueSpace();
			return;
		}

		NPC guide = NPCs.getNearest("Magic Instructor");
		if (guide == null) {
			Movement.walk(new WorldPoint(3141, 3088, 0));
			return;
		}

		if (!Players.getLocal().isIdle()) {
			return;
		}

		guide.interact(0);
	}

	private void openSpellBook() {
		if (Dialog.canContinue()) {
			Dialog.continueSpace();
			return;
		}
		Widget magic = Game.getClient().getWidget(164, 65);

		if (magic == null) {
			return;
		}

		magic.interact("Magic");
	}

	private WorldArea magicHouse = new WorldArea(new WorldPoint(3143, 3089, 0), new WorldPoint(3140, 3087, 0));

	private void killChicken() {
		if (!Players.getLocal().isIdle()) {
			return;
		}

		if (Players.getLocal().getWorldLocation().equals(new WorldPoint(3141, 3090, 0))) {
			Magic.cast(Regular.WIND_STRIKE, NPCs.getNearest("Chicken"));
		} else {
			Movement.walk(new WorldPoint(3141, 3090, 0));
		}
	}

	@Override
	public int execute() {
		logger.info("section: " + Game.getClient().getVarpValue(281));
		switch (Game.getClient().getVarpValue(281)) {
			case 620, 640, 670 -> talkToGuide();
			case 630 -> openSpellBook();
			case 650 -> killChicken();
		}
		return 1000;
	}
}
