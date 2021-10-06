package meteor.plugins.api.packets;

import meteor.plugins.api.game.Game;
import net.runelite.api.packets.PacketBufferNode;
import net.runelite.api.packets.PacketWriter;

public class GameObjectPackets {
    public static void queueGameObjectAction2Packet(int objectID, int worldPointX, int worldPointY, int ctrlDown){
        PacketWriter writer = Game.getClient().getPacketWriter();
        PacketBufferNode packet = Game.getClient().preparePacket(Game.getClient().getGameObjectAction2Packet(), writer.getIsaacCipher());
        packet.getPacketBuffer().writeShort01$api(objectID);
        packet.getPacketBuffer().writeShortA$api(worldPointX);
        packet.getPacketBuffer().writeShort01$api(worldPointY);
        packet.getPacketBuffer().writeByte01$api(ctrlDown);
        writer.queuePacket(packet);
    }
}
