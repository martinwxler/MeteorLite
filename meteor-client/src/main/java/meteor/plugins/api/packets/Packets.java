package meteor.plugins.api.packets;

import meteor.plugins.api.game.Game;
import net.runelite.api.packets.ClientPacket;
import net.runelite.api.packets.PacketBufferNode;
import net.runelite.api.packets.PacketWriter;
import net.runelite.api.widgets.WidgetInfo;

public class Packets {
    
    public static class Dialog {
        public static void sendNumberInput(int number) {
            queuePacket(Game.getClient().getNumberInputPacket(), number);
        }

        public static void sendTextInput(String text) {
            queuePacket(Game.getClient().getTextInputPacket(), text);
        }

        public static void sendNameInput(String name) {
            queuePacket(Game.getClient().getNameInputPacket(), name);
        }
    }
    public static void queueClickPacket(int mouseinfo , int x, int y){
        PacketWriter writer = Game.getClient().getPacketWriter();
        PacketBufferNode packet = Game.getClient().preparePacket(Game.getClient().getClickPacket(), writer.getIsaacCipher());
        packet.getPacketBuffer().writeShort$api(mouseinfo);
        packet.getPacketBuffer().writeShort$api(x);
        packet.getPacketBuffer().writeShort$api(y);
        writer.queuePacket(packet);
    }
    public static void queueItemActionPacket(int inventoryID, int itemID, int itemSlot){
        PacketWriter writer = Game.getClient().getPacketWriter();
        PacketBufferNode packet = Game.getClient().preparePacket(Game.getClient().getItemActionPacket(), writer.getIsaacCipher());
        packet.getPacketBuffer().writeIntME$api(inventoryID);
        packet.getPacketBuffer().writeShort$api(itemID);
        packet.getPacketBuffer().writeShort01A$api(itemSlot);
        writer.queuePacket(packet);
    }
    public static void queueSpellOnItemPacket(int spellWidgetID, int spellWidgetIndex, int inventoryID, int itemID, int itemIndex){
        PacketWriter writer = Game.getClient().getPacketWriter();
        PacketBufferNode packet = Game.getClient().preparePacket(Game.getClient().getSpellOnItemPacket(), writer.getIsaacCipher());
        packet.getPacketBuffer().writeIntME$api(spellWidgetID);
        packet.getPacketBuffer().writeShort$api(spellWidgetIndex);
        packet.getPacketBuffer().writeInt$api(inventoryID);
        packet.getPacketBuffer().writeShort01A$api(itemID);
        packet.getPacketBuffer().writeShort01$api(itemIndex);
        writer.queuePacket(packet);
    }
    public static void queueItemAction2Packet(int itemID, int inventoryID, int itemSlot){
        PacketWriter writer = Game.getClient().getPacketWriter();
        PacketBufferNode packet = Game.getClient().preparePacket(Game.getClient().getItemAction2Packet(), writer.getIsaacCipher());
        packet.getPacketBuffer().writeShort$api(itemID);
        packet.getPacketBuffer().writeInt2$api(inventoryID);
        packet.getPacketBuffer().writeShort01A$api(itemSlot);
        writer.queuePacket(packet);
    }
    public static void queueItemOnItemPacket(int itemId1, int slot1, int itemId2, int slot2) {
        PacketWriter writer = Game.getClient().getPacketWriter();
        PacketBufferNode packet = Game.getClient().preparePacket(Game.getClient().getItemOnItemPacket(), writer.getIsaacCipher());
        packet.getPacketBuffer().writeShort01$api(slot2);
        packet.getPacketBuffer().writeInt0123$api(WidgetInfo.INVENTORY.getPackedId());
        packet.getPacketBuffer().writeShort01$api(itemId1);
        packet.getPacketBuffer().writeInt$api(WidgetInfo.INVENTORY.getPackedId());
        packet.getPacketBuffer().writeShortA$api(slot1);
        packet.getPacketBuffer().writeShort01A$api(itemId2);
        writer.queuePacket(packet);
    }
    public static void queuePacket(ClientPacket clientPacket, Object... data) {
        PacketWriter writer = Game.getClient().getPacketWriter();
        PacketBufferNode packet = Game.getClient().preparePacket(clientPacket, writer.getIsaacCipher());
        for (Object o : data) {
            if (o instanceof Byte) {
                packet.getPacketBuffer().writeByte$api((int) o);
                continue;
            }

            if (o instanceof Short) {
                packet.getPacketBuffer().writeShort$api((int) o);
                continue;
            }

            if (o instanceof Integer) {
                packet.getPacketBuffer().writeInt$api((int) o);
                continue;
            }
            if (o instanceof Long) {
                packet.getPacketBuffer().writeLong$api((long) o);
                continue;
            }

            if (o instanceof String) {
                packet.getPacketBuffer().writeStringCp1252NullTerminated$api( (String) o);
                continue;
            }

            // invalid data
            return;
        }
        writer.queuePacket(packet);
    }
}
