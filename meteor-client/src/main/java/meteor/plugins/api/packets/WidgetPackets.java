package meteor.plugins.api.packets;

import meteor.plugins.api.game.Game;
import net.runelite.api.packets.PacketBufferNode;
import net.runelite.api.packets.PacketWriter;

public class WidgetPackets {

    public static void queueWidgetActionPacket(int widgetID, int itemID, int itemSlot){
        PacketWriter writer = Game.getClient().getPacketWriter();
        PacketBufferNode packet = Game.getClient().preparePacket(Game.getClient().getWidgetActionPacket(), writer.getIsaacCipher());
        packet.getPacketBuffer().writeInt$api(widgetID);
        packet.getPacketBuffer().writeShort$api(itemSlot);
        packet.getPacketBuffer().writeShort$api(itemID);
        writer.queuePacket(packet);
    }
    public static void queueWidgetAction2Packet(int widgetID, int itemID, int itemSlot){
        PacketWriter writer = Game.getClient().getPacketWriter();
        PacketBufferNode packet = Game.getClient().preparePacket(Game.getClient().getWidgetAction2Packet(), writer.getIsaacCipher());
        packet.getPacketBuffer().writeInt$api(widgetID);
        packet.getPacketBuffer().writeShort$api(itemSlot);
        packet.getPacketBuffer().writeShort$api(itemID);
        writer.queuePacket(packet);
    }
}
