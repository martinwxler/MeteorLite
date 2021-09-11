package meteor.plugins.api.example.simpleoneclick;

import com.google.inject.Inject;
import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.eventbus.events.ConfigChanged;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.entities.NPCs;
import meteor.plugins.api.entities.Players;
import meteor.plugins.api.entities.TileItems;
import meteor.plugins.api.entities.TileObjects;
import meteor.plugins.api.items.Bank;
import meteor.plugins.api.items.Inventory;
import meteor.plugins.api.widgets.Widgets;
import net.runelite.api.*;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@PluginDescriptor(
				name = "Hoot One Click"
)
public class HootOneClickPlugin extends Plugin {
	@Inject
	private HootOneClickConfig config;

	private final Map<Integer, String> configs = new HashMap<>();

	private static final List<Integer> GAME_OBJECT_OPCODES = List.of(1, 2, 3, 4, 5, 6, 1001);
	private static final List<Integer> NPC_OPCODES = List.of(7, 8, 9, 10, 11, 12, 13);
	private static final List<Integer> GROUND_ITEM_OPCODES = List.of(18, 19, 20, 21, 22);
	private static final List<Integer> WIDGET_OPCODES = List.of(24, 25, 26, 28, 29, 30, 39, 40, 41, 42, 43);
	private static final List<Integer> ITEM_OPCODES = List.of(33, 34, 35, 36, 37, 38);
	private static final List<Integer> PLAYER_OPCODES = List.of(44, 45, 46, 47, 48, 49, 50, 51);

	@Provides
	public HootOneClickConfig getConfig(ConfigManager configManager) {
		return configManager.getConfig(HootOneClickConfig.class);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged e) {
		if (!e.getGroup().equals("simpleoneclick")) {
			return;
		}

		configs.clear();

		String[] items = config.config().split(",");
		for (String i : items) {
			String[] pairs = i.split(":");
			configs.put(Integer.parseInt(pairs[0]), pairs[1]);
		}
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked e) {
		int opcode = e.getMenuAction().getId();
		if (opcode == MenuAction.CC_OP.getId()) {
			if (e.getParam1() == WidgetInfo.BANK_ITEM_CONTAINER.getPackedId()) {
				Item item = Bank.getFirst(x -> x.getSlot() == e.getParam0());
				if (weed(e, item)) return;

				return;
			}

			if (e.getParam1() == WidgetInfo.BANK_INVENTORY_ITEMS_CONTAINER.getPackedId()) {
				Item item = Bank.getInventory(x -> x.getSlot() == e.getParam0()).stream().findFirst().orElse(null);
				if (weed(e, item)) return;
			}

			return;
		}


		if (GAME_OBJECT_OPCODES.contains(opcode) && configs.containsKey(e.getId())) {
			TileObject obj = TileObjects.getNearest(e.getId());
			if (obj != null) {
				String replaced = configs.get(e.getId());
				e.consume();
				obj.interact(replaced);
			}

			return;
		}

		if (NPC_OPCODES.contains(opcode)) {
			NPC npc = NPCs.getNearest(x -> x.getIndex() == e.getId());
			if (npc != null && configs.containsKey(npc.getId())) {
				String replaced = configs.get(npc.getId());
				e.consume();
				npc.interact(replaced);
			}

			return;
		}

		if (GROUND_ITEM_OPCODES.contains(opcode) && configs.containsKey(e.getId())) {
			TileItem item = TileItems.getNearest(e.getId());
			if (item != null) {
				String replaced = configs.get(e.getId());
				e.consume();
				item.interact(replaced);
			}

			return;
		}

		if (WIDGET_OPCODES.contains(opcode) && configs.containsKey(e.getParam1())) {
			Widget widget = Widgets.fromId(e.getParam1());
			if (widget != null) {
				String replaced = configs.get(e.getParam1());
				e.consume();
				widget.interact(replaced);
			}

			return;
		}

		if (ITEM_OPCODES.contains(opcode) && configs.containsKey(e.getId())) {
			Item item = Inventory.getFirst(e.getId());
			if (item != null) {
				String replaced = configs.get(e.getId());
				e.consume();
				item.interact(replaced);
			}

			return;
		}

		if (PLAYER_OPCODES.contains(opcode) && configs.containsKey(e.getId())) {
			Player player = Players.getNearest(x -> x.getIndex() == e.getId());
			if (player != null) {
				String replaced = configs.get(e.getId());
				e.consume();
				player.interact(replaced);
			}
		}
	}

	private boolean weed(MenuOptionClicked e, Item item) {
		if (item != null && configs.containsKey(item.getId())) {
			String replaced = configs.get(item.getId());
			if (!item.hasAction(replaced)) {
				return true;
			}

			int index = item.getActions().indexOf(replaced);
			e.consume();
			item.interact(index + 1);
		}

		return false;
	}
}
