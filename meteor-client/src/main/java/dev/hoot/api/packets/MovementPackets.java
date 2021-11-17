package dev.hoot.api.packets;

import dev.hoot.api.game.Game;
import net.runelite.api.coords.WorldPoint;
import osrs.ClientPacket;

import static osrs.Client.packetWriter;

public class MovementPackets {
	public static void sendMovement(int worldX, int worldY, boolean run) {
		osrs.PacketBufferNode var18 = (osrs.PacketBufferNode) Game.getClient().preparePacket(ClientPacket.MOVE_GAMECLICK, packetWriter.isaacCipher);
		var18.packetBuffer.writeByte(5);
		var18.packetBuffer.writeByteA(worldX);
		var18.packetBuffer.writeByteB(run ? 2 : 0);
		var18.packetBuffer.writeByteC(worldY);
		packetWriter.addNode(var18);
	}

	public static void sendMovement(int worldX, int worldY) {
		sendMovement(worldX, worldY, false);
	}

	public static void sendMovement(WorldPoint worldPoint, boolean run) {
		sendMovement(worldPoint.getX(), worldPoint.getY(), run);
	}

	public static void sendMovement(WorldPoint worldPoint) {
		sendMovement(worldPoint, false);
	}
}
