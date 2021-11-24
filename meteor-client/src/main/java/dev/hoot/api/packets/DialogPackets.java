package dev.hoot.api.packets;

import dev.hoot.api.game.Game;
import dev.hoot.api.game.GameThread;
import osrs.Client;
import osrs.ClientPacket;

import static osrs.Client.packetWriter;

public class DialogPackets {

	public static void sendNumberInput(int number) {
		sendNumberInput(number,true);
	}

	public static void sendNumberInput(int number, boolean closeDialog) {
		osrs.PacketBufferNode var14 = (osrs.PacketBufferNode) Game.getClient().preparePacket(ClientPacket.RESUME_P_COUNTDIALOG, packetWriter.isaacCipher);
		var14.packetBuffer.writeInt(number);
		Client.packetWriter.addNode(var14);
		if(closeDialog)
			GameThread.invoke(() -> Game.getClient().runScript(138)); // closes the input dialog
	}

	public static void sendTextInput(String text) {
		osrs.PacketBufferNode var12 = (osrs.PacketBufferNode) Game.getClient().preparePacket(ClientPacket.RESUME_P_STRINGDIALOG, packetWriter.isaacCipher);
		var12.packetBuffer.writeByte(text.length()+1);
		var12.packetBuffer.writeStringCp1252NullTerminated(text);
		Client.packetWriter.addNode(var12);
		GameThread.invoke(() -> Game.getClient().runScript(138));
	}

	public static void sendNameInput(String name) {
		osrs.PacketBufferNode var12 = (osrs.PacketBufferNode) Game.getClient().preparePacket(ClientPacket.RESUME_P_NAMEDIALOG, packetWriter.isaacCipher);
		var12.packetBuffer.writeByte(name.length() + 1);
		var12.packetBuffer.writeStringCp1252NullTerminated(name);
		Client.packetWriter.addNode(var12);
		GameThread.invoke(() -> Game.getClient().runScript(138));
	}
	public static void closeInterface(){
		Packets.queuePacket(Game.getClient().getInterfaceClosePacket());
	}
}
