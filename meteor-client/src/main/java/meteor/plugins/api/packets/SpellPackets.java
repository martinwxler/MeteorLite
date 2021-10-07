package meteor.plugins.api.packets;

import meteor.plugins.api.game.Game;
import meteor.plugins.api.magic.Spell;
import meteor.plugins.api.widgets.Widgets;
import net.runelite.api.Item;
import net.runelite.api.packets.PacketBufferNode;
import net.runelite.api.packets.PacketWriter;
import net.runelite.api.widgets.Widget;

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
		PacketWriter writer = Game.getClient().getPacketWriter();
		PacketBufferNode packet = Game.getClient().preparePacket(Game.getClient().getSpellOnItemPacket(), writer.getIsaacCipher());
		packet.getPacketBuffer().writeShort$api(spellWidgetIndex);
		packet.getPacketBuffer().writeShort01A$api(itemIndex);
		packet.getPacketBuffer().writeShort01A$api(itemID);
		packet.getPacketBuffer().writeInt$api(inventoryID);
		packet.getPacketBuffer().writeIntME$api(spellWidgetID);
		writer.queuePacket(packet);
	}
}
