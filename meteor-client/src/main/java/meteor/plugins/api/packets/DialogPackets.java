package meteor.plugins.api.packets;

import meteor.plugins.api.game.Game;

public class DialogPackets {
	public static void sendNumberInput(int number) {
		Packets.queuePacket(Game.getClient().getNumberInputPacket(), number);
	}

	public static void sendTextInput(String text) {
		Packets.queuePacket(Game.getClient().getTextInputPacket(), text);
	}

	public static void sendNameInput(String name) {
		Packets.queuePacket(Game.getClient().getNameInputPacket(), name);
	}
}
