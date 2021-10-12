package meteor.plugins.cettitutorial.tasks;

import meteor.PluginTask;
import meteor.plugins.api.entities.NPCs;
import meteor.plugins.api.entities.Players;
import meteor.plugins.api.entities.TileObjects;
import meteor.plugins.api.game.Game;
import meteor.plugins.api.game.GameThread;
import meteor.plugins.api.movement.Movement;
import meteor.plugins.api.widgets.Dialog;
import net.runelite.api.NPC;
import net.runelite.api.TileObject;
import net.runelite.api.coords.WorldPoint;

public class LeavingIsland implements PluginTask {

	@Override
	public boolean validate() {
		return Game.getClient().getVarpValue(281) < 1000;
	}

	private void talkToGuide() {
		if (Dialog.canContinue()) {
			Dialog.continueSpace();
			return;
		}

		if (Dialog.isViewingOptions()) {
			Dialog.chooseOption("Yes.", "No,");
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

	private void toTheNode() {
		if (Dialog.canContinue()) {
			Dialog.continueSpace();
			return;
		}

		if (Dialog.isViewingOptions()) {
			Dialog.chooseOption("Yes");
		}

		if (!Players.getLocal().isIdle()) {
			return;
		}

		TileObject rowBoat = TileObjects.getNearest(42826);

		if (rowBoat == null) {
			return;
		}

		rowBoat.interact(0);
	}


	@Override
	public int execute() {
		var ironStatus = GameThread.invokeLater(() -> Game.getClient().getVarbitValue(1777));
		if (ironStatus < 4) {
			talkToGuide();
		} else {
			toTheNode();
		}
		return 1000;
	}
}
