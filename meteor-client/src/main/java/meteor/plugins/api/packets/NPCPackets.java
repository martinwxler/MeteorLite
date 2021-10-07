package meteor.plugins.api.packets;

import meteor.plugins.api.game.Game;
import net.runelite.api.packets.PacketBufferNode;
import net.runelite.api.packets.PacketWriter;

public class NPCPackets {
    public static void queueNPCAction3Packet(int NPCIndex, int ctrlDown){
        PacketWriter writer = Game.getClient().getPacketWriter();
        PacketBufferNode packet = Game.getClient().preparePacket(Game.getClient().getNPCAction3Packet(), writer.getIsaacCipher());
        packet.getPacketBuffer().writeByte$api(ctrlDown);
        packet.getPacketBuffer().writeShort01$api(NPCIndex);
        writer.queuePacket(packet);
    }
    public static void queueNPCActionPacket(int NPCIndex, int ctrlDown){
        PacketWriter writer = Game.getClient().getPacketWriter();
        PacketBufferNode packet = Game.getClient().preparePacket(Game.getClient().getNPCActionPacket(), writer.getIsaacCipher());
        packet.getPacketBuffer().writeByteA$api(ctrlDown);
        packet.getPacketBuffer().writeShort$api(NPCIndex);
        writer.queuePacket(packet);
    }
}
