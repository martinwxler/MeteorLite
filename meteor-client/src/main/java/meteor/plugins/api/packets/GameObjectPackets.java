package meteor.plugins.api.packets;

import meteor.plugins.api.game.Game;
import net.runelite.api.packets.PacketBufferNode;
import net.runelite.api.packets.PacketWriter;

public class GameObjectPackets {
    public static void queueGameObjectAction2Packet(int objectID, int worldPointX, int worldPointY, int ctrlDown){
        PacketWriter writer = Game.getClient().getPacketWriter();
        PacketBufferNode packet = Game.getClient().preparePacket(Game.getClient().getGameObjectAction2Packet(), writer.getIsaacCipher());
        packet.getPacketBuffer().writeShort01A$api(worldPointY);
        packet.getPacketBuffer().writeShortA$api(worldPointX);
        packet.getPacketBuffer().writeByte$api(ctrlDown);
        packet.getPacketBuffer().writeShortA$api(objectID);
        writer.queuePacket(packet);
    }
    public static void queueGameObjectActionPacket(int objectID, int worldPointX, int worldPointY, int ctrlDown) {
        PacketWriter writer = Game.getClient().getPacketWriter();
        PacketBufferNode packet = Game.getClient().preparePacket(Game.getClient().getGameObjectActionPacket(), writer.getIsaacCipher());
        packet.getPacketBuffer().writeShort01A$api(objectID);
        packet.getPacketBuffer().writeByte01A$api(ctrlDown);
        packet.getPacketBuffer().writeShort01A$api(worldPointY);
        packet.getPacketBuffer().writeShort01A$api(worldPointX);
        writer.queuePacket(packet);
    }
}
