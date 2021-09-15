package meteor.plugins.api.packets;

import meteor.plugins.api.game.Game;
import net.runelite.api.packets.PacketBufferNode;
import net.runelite.api.packets.PacketWriter;

public class MovementPackets {
	public static void sendMovement(int sceneX, int sceneY, boolean run) {
		PacketWriter writer = Game.getClient().getPacketWriter();
		PacketBufferNode packet = Game.getClient().preparePacket(Game.getClient().getWalkPacket(), writer.getIsaacCipher());
		packet.getPacketBuffer().writeByte$api(5);
		packet.getPacketBuffer().writeByte01$api(run ? 2 : 0);
		packet.getPacketBuffer().writeShort01$api(sceneX + Game.getClient().getBaseX());
		packet.getPacketBuffer().writeShort01A$api(sceneY + Game.getClient().getBaseY());
		writer.queuePacket(packet);
	}

	public static void sendMovement(int sceneX, int sceneY) {
		sendMovement(sceneX, sceneY, false);
	}
}
