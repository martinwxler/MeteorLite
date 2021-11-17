package dev.hoot.api.packets;

import dev.hoot.api.game.Game;
import osrs.ClientPacket;

import static osrs.Client.packetWriter;

public class MousePackets {
	public static void queueClickPacket(int x, int y) {
		Game.getClient().setMouseLastPressedMillis(System.currentTimeMillis());
		int mousePressedTime = (int)((Game.getClient().getMouseLastPressedMillis()) - (Game.getClient().getClientMouseLastPressedMillis()));
		if (mousePressedTime < 0) {
			return;
		}

		if (mousePressedTime > 32767) {
			mousePressedTime = 32767;
		}

		Game.getClient().setClientMouseLastPressedMillis(Game.getClient().getMouseLastPressedMillis());
		int mouseInfo = (mousePressedTime << 1) + (1);
		queueClickPacket(mouseInfo, x, y);
	}

	public static void queueClickPacket(int mouseinfo, int x, int y){
		osrs.PacketBufferNode var18 = (osrs.PacketBufferNode) Game.getClient().preparePacket(ClientPacket.EVENT_MOUSE_CLICK, packetWriter.isaacCipher);
		var18.packetBuffer.writeShort(mouseinfo);
		var18.packetBuffer.writeShort(x);
		var18.packetBuffer.writeShort(y);
		packetWriter.addNode(var18);
	}
}
