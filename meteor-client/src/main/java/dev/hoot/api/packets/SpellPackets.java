package dev.hoot.api.packets;

import dev.hoot.api.game.Game;
import dev.hoot.api.magic.Spell;
import dev.hoot.api.widgets.Widgets;
import net.runelite.api.Item;
import net.runelite.api.widgets.Widget;
import osrs.Client;
import osrs.ClientPacket;

public class SpellPackets {
	public static void spellOnItem(Spell spell, Item item) {
		Widget spellWidget = Widgets.get(spell.getWidget());
		if (spellWidget == null) {
			return;
		}

		queueSpellOnItemPacket(spellWidget.getId(), spellWidget.getIndex(),
						item.getWidgetId(), item.getId(), item.getSlot());
	}

	public static void queueSpellOnItemPacket(int spellWidgetID, int spellWidgetIndex, int inventoryID, int itemID, int itemIndex) {
		osrs.PacketBufferNode var8 = (osrs.PacketBufferNode) Game.getClient().preparePacket(ClientPacket.OPHELDT, Client.packetWriter.isaacCipher);
		var8.packetBuffer.writeByteC(itemIndex);
		var8.packetBuffer.writeInt(spellWidgetID);
		var8.packetBuffer.writeShort(spellWidgetIndex);
		var8.packetBuffer.writeIntME2(inventoryID);
		var8.packetBuffer.writeByteA(itemID);
		Client.packetWriter.addNode(var8);
	}
	public static void spellOnNpc(int spellWidgetID, net.runelite.api.NPC npc){
		Widget spellWidget = Game.getClient().getWidget(spellWidgetID);
		queueSpellOnNpcPacket(spellWidget.getId(), -1,0,npc.getIndex(),-1);
	}
	public static void spellOnNpcIndex(int spellWidgetID, int index){
		Widget spellWidget = Game.getClient().getWidget(spellWidgetID);
		queueSpellOnNpcPacket(spellWidget.getId(), -1,0,index,-1);
	}
	public static void queueSpellOnNpcPacket(int spellWidgetID,int spellWidgetIndex,int ctrlDown,int npcIndex,int idk){
		osrs.PacketBufferNode var9 = (osrs.PacketBufferNode) Game.getClient().preparePacket(ClientPacket.spellOnNpc, Client.packetWriter.isaacCipher);
		var9.packetBuffer.writeIntME(spellWidgetID);
		var9.packetBuffer.writeByteA(spellWidgetIndex);
		var9.packetBuffer.writeByteB(ctrlDown);
		var9.packetBuffer.writeByteA(npcIndex);
		var9.packetBuffer.writeShort(idk);
		Client.packetWriter.addNode(var9);
	}
}
