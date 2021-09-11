package meteor.plugins.api.packets;

import meteor.plugins.api.game.Game;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.packets.PacketBufferNode;
import net.runelite.api.packets.PacketWriter;
import net.runelite.api.widgets.WidgetInfo;

public class ItemPackets {
	public static void itemAction1(Item item) {
		queueItemActionPacket(item.getType().getInventoryID().getId(), item.getId(), item.getSlot());
	}

	public static void itemAction2(Item item) {
		queueItemAction2Packet(item.getType().getInventoryID().getId(), item.getId(), item.getSlot());
	}

	public static void useItemOnItem(Item item, Item item2) {
		if (item.getType().getInventoryID() != InventoryID.INVENTORY
						|| item2.getType().getInventoryID() != InventoryID.INVENTORY) {
			return;
		}

		queueItemOnItemPacket(item.getId(), item.getSlot(), item2.getId(), item2.getSlot());
	}

	public static void queueItemActionPacket(int inventoryID, int itemID, int itemSlot) {
		PacketWriter writer = Game.getClient().getPacketWriter();
		PacketBufferNode packet = Game.getClient().preparePacket(Game.getClient().getItemActionPacket(), writer.getIsaacCipher());
		packet.getPacketBuffer().writeIntME$api(inventoryID);
		packet.getPacketBuffer().writeShort$api(itemID);
		packet.getPacketBuffer().writeShort01A$api(itemSlot);
		writer.queuePacket(packet);
	}

	public static void queueItemAction2Packet(int inventoryID, int itemID, int itemSlot) {
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
}
