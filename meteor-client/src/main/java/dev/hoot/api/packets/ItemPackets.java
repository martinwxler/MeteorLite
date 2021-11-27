package dev.hoot.api.packets;

import dev.hoot.api.game.Game;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.Point;
import net.runelite.api.TileObject;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.WidgetInfo;
import osrs.Client;
import osrs.ClientPacket;

import java.util.List;

public class ItemPackets {
	public static void itemAction(Item item, String action) {
		List<String> actions = item.getActions();
		int index = actions.indexOf(action);
		switch (index) {
			case 0 -> itemFirstOption(item);
			case 1 -> itemSecondOption(item);
			case 2 -> itemThirdOption(item);
			case 3 -> itemFourthOption(item);
			case 4 -> itemFifthOption(item);
			default ->{
				WidgetPackets.widgetAction(Game.getClient().getWidget(item.getWidgetId()),action);
			}
		}
	}

	public static void itemFirstOption(Item item) {
		queueItemActionPacket(item.getWidgetId(), item.getId(), item.getSlot());
	}

	public static void itemSecondOption(Item item) {
		queueItemAction2Packet(item.getWidgetId(), item.getId(), item.getSlot());
	}
	public static void itemThirdOption(Item item) {
		queueItemAction3Packet(item.getWidgetId(), item.getId(), item.getSlot());
	}
	public static void itemFourthOption(Item item) {
		queueItemAction4Packet(item.getWidgetId(), item.getId(), item.getSlot());
	}

	public static void itemFifthOption(Item item) {
		queueItemAction5Packet(item.getWidgetId(), item.getId(), item.getSlot());
	}

	public static void useItemOnItem(Item item, Item item2) {
		if (item.getType().getInventoryID() != InventoryID.INVENTORY
						|| item2.getType().getInventoryID() != InventoryID.INVENTORY) {
			return;
		}

		queueItemOnItemPacket(item.getId(), item.getSlot(), item2.getId(), item2.getSlot());
	}

	public static void useItemOnTileObject(Item item, TileObject object) {
		Point p = object.menuPoint();
		LocalPoint lp = new LocalPoint(p.getX(),p.getY());
		WorldPoint wp = WorldPoint.fromScene(Game.getClient(),lp.getX(),lp.getY(),object.getPlane());
		queueItemUseOnTileObjectPacket(object.getId(), wp.getX(), wp.getY(), item.getSlot(), item.getId(), item.getWidgetId(), 0);
	}
	public static void queueBankItemActionPacket(int inventoryID, int itemID, int itemSlot){
		osrs.PacketBufferNode var10 = (osrs.PacketBufferNode) Game.getClient().preparePacket(ClientPacket.IF_BUTTON9, Client.packetWriter.isaacCipher);
		var10.packetBuffer.writeInt(inventoryID);
		var10.packetBuffer.writeShort(itemSlot);
		var10.packetBuffer.writeShort(itemID);
		Client.packetWriter.addNode(var10);
	}
	public static void queueItemOnNpcPacket(int npcIndex,int itemWidgetID,int itemID,int itemSlot,int ctrlDown){
		osrs.PacketBufferNode var9 = (osrs.PacketBufferNode) Game.getClient().preparePacket(ClientPacket.OPNPCU, Client.packetWriter.isaacCipher);
		var9.packetBuffer.write2(ctrlDown);
		var9.packetBuffer.writeByteA(itemID);
		var9.packetBuffer.writeByteB0(itemSlot);
		var9.packetBuffer.writeIntME3(itemWidgetID);
		var9.packetBuffer.writeByteC(npcIndex);
		Client.packetWriter.addNode(var9);
	}
	public static void queueItemUseOnTileObjectPacket(int objectID, int objectX, int objectY, int itemSlot, int itemID, int itemWidgetID, int ctrlDown){
		osrs.PacketBufferNode var8 = (osrs.PacketBufferNode) Game.getClient().preparePacket(ClientPacket.OPLOCU, Client.packetWriter.isaacCipher);
		var8.packetBuffer.writeByteB0(itemSlot);
		var8.packetBuffer.writeByteB0(objectY);
		var8.packetBuffer.writeIntME2(itemWidgetID);
		var8.packetBuffer.writeByteA(objectX);
		var8.packetBuffer.writeByteB0(objectID);
		var8.packetBuffer.writeByteC(itemID);
		var8.packetBuffer.writeByteB(ctrlDown);
		Client.packetWriter.addNode(var8);
	}
	public static void queueItemActionPacket(int inventoryID, int itemID, int itemSlot) {
		osrs.PacketBufferNode var8 = (osrs.PacketBufferNode) Game.getClient().preparePacket(ClientPacket.OPHELD1, Client.packetWriter.isaacCipher);
		var8.packetBuffer.writeByteC(itemID);
		var8.packetBuffer.writeByteB0(itemSlot);
		var8.packetBuffer.writeIntME(inventoryID);
		Client.packetWriter.addNode(var8);
	}
	public static void queueItemAction2Packet(int inventoryID, int itemID, int itemSlot) {
		osrs.PacketBufferNode var8 = (osrs.PacketBufferNode) Game.getClient().preparePacket(Game.getClient().getItemAction2Packet(), Client.packetWriter.isaacCipher);
		var8.packetBuffer.writeByteA(itemID);
		var8.packetBuffer.writeByteA(itemSlot);
		var8.packetBuffer.writeIntME(inventoryID);
		Client.packetWriter.addNode(var8);
	}
	
	public static void queueItemAction3Packet(int inventoryID, int itemID, int itemSlot){
		osrs.PacketBufferNode var8 = (osrs.PacketBufferNode) Game.getClient().preparePacket(Game.getClient().getItemAction3Packet(), Client.packetWriter.isaacCipher);
		var8.packetBuffer.writeByteC(itemID);
		var8.packetBuffer.writeByteA(itemSlot);
		var8.packetBuffer.writeIntME(inventoryID);
		Client.packetWriter.addNode(var8);
	}
	public static void queueItemAction4Packet(int inventoryID, int itemID, int itemSlot){
		osrs.PacketBufferNode var8 = (osrs.PacketBufferNode) Game.getClient().preparePacket(ClientPacket.OPHELD4, Client.packetWriter.isaacCipher);
		var8.packetBuffer.writeByteC(itemID);
		var8.packetBuffer.writeByteB0(itemSlot);
		var8.packetBuffer.writeIntME3(inventoryID);
		Client.packetWriter.addNode(var8);
	}
	
	public static void queueItemAction5Packet(int inventoryID, int itemID, int itemSlot) {
		osrs.PacketBufferNode var8 = (osrs.PacketBufferNode) Game.getClient().preparePacket(ClientPacket.OPHELD5, Client.packetWriter.isaacCipher);
		var8.packetBuffer.writeByteA(itemSlot);
		var8.packetBuffer.writeShort(itemID);
		var8.packetBuffer.writeIntME2(inventoryID);
		Client.packetWriter.addNode(var8);
	}

	public static void queueItemOnItemPacket(int itemId1, int slot1, int itemId2, int slot2) {
		osrs.PacketBufferNode var8 = (osrs.PacketBufferNode) Game.getClient().preparePacket(ClientPacket.OPHELDU, Client.packetWriter.isaacCipher);
		var8.packetBuffer.writeByteA(itemId2);
		var8.packetBuffer.writeIntME2(WidgetInfo.INVENTORY.getPackedId());
		var8.packetBuffer.writeShort(slot2);
		var8.packetBuffer.writeIntME2(WidgetInfo.INVENTORY.getPackedId());
		var8.packetBuffer.writeByteC(itemId1);
		var8.packetBuffer.writeByteA(slot1);
		Client.packetWriter.addNode(var8);
	}
}
