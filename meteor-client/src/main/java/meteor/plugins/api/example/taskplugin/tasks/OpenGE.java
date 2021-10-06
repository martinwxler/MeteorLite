package meteor.plugins.api.example.taskplugin.tasks;

import meteor.PluginTask;
import meteor.plugins.api.entities.NPCs;
import meteor.plugins.api.items.GrandExchange;
import meteor.plugins.api.movement.Movement;

public class OpenGE implements PluginTask {
	@Override
	public boolean validate() {
		return NPCs.getNearest("Grand Exchange Clerk") != null && !GrandExchange.isOpen();
	}

	@Override
	public int execute() {
		if (Movement.isWalking()) {
			return 1000;
		}

		NPCs.getNearest("Grand Exchange Clerk").interact("Exchange");
		return 1000;
	}
}
