package meteor.plugins.api.packets;

import meteor.plugins.api.game.Game;
import meteor.plugins.api.game.GameThread;

public class DialogPackets {
	public static void sendNumberInput(int number) {
		Packets.queuePacket(Game.getClient().getNumberInputPacket(), number);
		GameThread.invoke(() -> Game.getClient().runScript(138)); // closes the input dialog
	}

	public static void sendTextInput(String text) {
		Packets.queuePacket(Game.getClient().getTextInputPacket(), text);
		GameThread.invoke(() -> Game.getClient().runScript(138));
	}

	public static void sendNameInput(String name) {
		Packets.queuePacket(Game.getClient().getNameInputPacket(), name);
		GameThread.invoke(() -> Game.getClient().runScript(138));
	}
}
