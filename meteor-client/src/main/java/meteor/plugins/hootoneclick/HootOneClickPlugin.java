package meteor.plugins.hootoneclick;

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
import meteor.plugins.api.game.Game;
import meteor.plugins.api.items.Inventory;
import meteor.plugins.api.widgets.Widgets;
import meteor.util.Text;
import net.runelite.api.*;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.Widget;

import java.util.*;

@PluginDescriptor(
				name = "Hoot One Click"
)
public class HootOneClickPlugin extends Plugin {
	@Inject
	private HootOneClickConfig config;

	private static final List<Integer> GAME_OBJECT_OPCODES = List.of(1, 2, 3, 4, 5, 6, 1001);
	private static final List<Integer> NPC_OPCODES = List.of(7, 8, 9, 10, 11, 12, 13);
	private static final List<Integer> GROUND_ITEM_OPCODES = List.of(18, 19, 20, 21, 22);
	private static final List<Integer> WIDGET_OPCODES = List.of(24, 25, 26, 28, 29, 30, 39, 40, 41, 42, 43);
	private static final List<Integer> ITEM_OPCODES = List.of(33, 34, 35, 36, 37, 38);
	private static final List<Integer> PLAYER_OPCODES = List.of(44, 45, 46, 47, 48, 49, 50, 51);

	private final Map<String, String> gameObjectConfigs = new HashMap<>();
	private final Map<String, String> npcConfigs = new HashMap<>();
	private final Map<String, String> groundItemConfigs = new HashMap<>();
	private final Map<String, String> widgetConfigs = new HashMap<>();
	private final Map<String, String> itemConfigs = new HashMap<>();
	private final Map<String, String> playerConfigs = new HashMap<>();

	@Provides
	public HootOneClickConfig getConfig(ConfigManager configManager) {
		return configManager.getConfig(HootOneClickConfig.class);
	}

	@Subscribe
	private void onClientTick(ClientTick e) {
		MenuEntry[] entries = client.getMenuEntries();
		if (entries.length <= 1) {
			return;
		}

		List<? extends SceneEntity> hovered = getHoveredEntities();
		if (hovered.isEmpty()) {
			return;
		}

		SceneEntity top = hovered.stream().filter(x -> isConfigured(x.getName(), gameObjectConfigs) ||
										isConfigured(x.getName(), npcConfigs) || isConfigured(x.getName(), groundItemConfigs) ||
										isConfigured(x.getName(), playerConfigs))
						.findFirst()
						.orElse(null);
		if (top == null) {
			return;
		}

		if (top.getActions().stream().anyMatch(Objects::nonNull)) {
			return;
		}

		if (top instanceof GameObject gameObject) {
			client.insertMenuItem(
							"Hoot One Click",
							"",
							MenuAction.GAME_OBJECT_FIRST_OPTION.getId(),
							gameObject.getId(),
							gameObject.menuPoint().getX(),
							gameObject.menuPoint().getY(),
							true
			);
			return;
		}

		if (top instanceof TileItem tileItem) {
			client.insertMenuItem(
							"Hoot One Click",
							"",
							MenuAction.GROUND_ITEM_FIRST_OPTION.getId(),
							tileItem.getId(),
							tileItem.getTile().getSceneLocation().getX(),
							tileItem.getTile().getSceneLocation().getY(),
							true
			);
			return;
		}

		if (top instanceof NPC npc) {
			client.insertMenuItem("Hoot One Click", "", MenuAction.NPC_FIRST_OPTION.getId(), npc.getIndex(), 0, 0, true);
			return;
		}

		if (top instanceof Player player) {
			client.insertMenuItem("Hoot One Click", "", MenuAction.PLAYER_FIRST_OPTION.getId(), player.getIndex(), 0, 0, true);
			return;
		}

		logger.info("wtf {}", top.getClass());
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged e) {
		if (!e.getGroup().equals("hootoneclick")) {
			return;
		}

		clearConfigs();

		parseConfigs(config.gameObjectConfig(), gameObjectConfigs);
		parseConfigs(config.npcConfig(), npcConfigs);
		parseConfigs(config.groundItemConfig(), groundItemConfigs);
		parseConfigs(config.widgetConfig(), widgetConfigs);
		parseConfigs(config.itemConfig(), itemConfigs);
		parseConfigs(config.playerConfig(), playerConfigs);
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked e) {
		int opcode = e.getMenuAction().getId();

		if (!gameObjectConfigs.isEmpty() && GAME_OBJECT_OPCODES.contains(opcode)) {
			Tile tile = Game.getClient().getScene().getTiles()[Game.getClient().getPlane()][e.getParam0()][e.getParam1()];
			TileObject obj = TileObjects.getFirstAt(tile, e.getId());
			if (replace(gameObjectConfigs, obj, e)) {
				return;
			}
		}

		if (!npcConfigs.isEmpty() && NPC_OPCODES.contains(opcode)) {
			NPC npc = NPCs.getNearest(x -> x.getIndex() == e.getId());
			if (replace(npcConfigs, npc, e)) {
				return;
			}
		}

		if (!groundItemConfigs.isEmpty() && GROUND_ITEM_OPCODES.contains(opcode)) {
			Tile tile = Game.getClient().getScene().getTiles()[Game.getClient().getPlane()][e.getParam0()][e.getParam1()];
			TileItem item = TileItems.getFirstAt(tile, e.getId());
			if (replace(groundItemConfigs, item, e)) {
				return;
			}
		}

		if (!itemConfigs.isEmpty() && ITEM_OPCODES.contains(opcode)) {
			Item item = Inventory.getFirst(e.getId());
			if (replace(itemConfigs, item, e)) {
				return;
			}
		}

		if (!playerConfigs.isEmpty() && PLAYER_OPCODES.contains(opcode)) {
			Player player = Players.getNearest(x -> x.getIndex() == e.getId());
			if (replace(playerConfigs, player, e)) {
				return;
			}
		}

		if (!widgetConfigs.isEmpty() && WIDGET_OPCODES.contains(opcode)) {
			String action = Text.removeTags(e.getMenuOption()) + " " + Text.removeTags(e.getMenuTarget());
			Widget widget = Widgets.fromId(e.getParam1());
			if (widget != null && widgetConfigs.containsKey(action)) {
				String replaced = widgetConfigs.get(action);
				e.consume();

				if (isUseOn(replaced)) {
					Item usedItem = getUsedItem(replaced);
					if (usedItem != null) {
						usedItem.useOn(widget);
					}

					return;
				}

				widget.interact(replaced);
			}
		}
	}

	private <T extends Interactable> boolean replace(Map<String, String> replacements, T t, MenuOptionClicked event) {
		if (!(t instanceof EntityNameable target)) {
			return false;
		}

		if ((!config.exactEntityNames() || !replacements.containsKey(target.getName()))
						&& replacements.keySet().stream().noneMatch(x -> target.getName().toLowerCase().contains(x.toLowerCase()))) {
			return false;
		}

		event.consume();

		String replacement;
		if (config.exactEntityNames()) {
			replacement = replacements.get(target.getName());
		} else {
			String key = replacements.keySet().stream()
							.filter(x -> target.getName().toLowerCase().contains(x.toLowerCase()))
							.findFirst()
							.orElse(null);
			replacement = replacements.get(key);
		}

		if (replacement == null) {
			return false;
		}

		if (isUseOn(replacement)) {
			Item usedItem = getUsedItem(replacement);
			if (usedItem != null) {
				usedItem.useOn(t);
			}

			return true;
		}

		t.interact(replacement);
		return true;
	}

	private Item getUsedItem(String action) {
		return Inventory.getFirst(x -> {
			if (config.exactItemNames()) {
				return x.getName().equals(action.substring(4));
			}

			return x.getName().toLowerCase().contains(action.substring(4).toLowerCase());
		});
	}

	private boolean isUseOn(String action) {
		return action.contains("Use ") && action.split(" ").length >= 2;
	}

	private void parseConfigs(String text, Map<String, String> configs) {
		if (text.isBlank()) {
			return;
		}

		String[] items = text.split(",");

		for (String i : items) {
			String[] pairs = i.split(":");
			if (pairs.length < 2) {
				continue;
			}

			configs.put(pairs[0], pairs[1]);
		}
	}

	private boolean isConfigured(String entityName, Map<String, String> configs) {
		if (config.exactEntityNames()) {
			return configs.containsKey(entityName);
		}

		return configs.keySet().stream().anyMatch(x -> entityName.toLowerCase().contains(x.toLowerCase()));
	}

	private void clearConfigs() {
		gameObjectConfigs.clear();
		npcConfigs.clear();
		groundItemConfigs.clear();
		widgetConfigs.clear();
		itemConfigs.clear();
		playerConfigs.clear();
	}

	public static List<? extends SceneEntity> getHoveredEntities() {
		MenuEntry[] menuEntries = Game.getClient().getMenuEntries();
		if (menuEntries.length == 0) {
			return Collections.emptyList();
		}

		List<SceneEntity> out = new ArrayList<>();

		for (MenuEntry menuEntry : menuEntries) {
			MenuAction menuAction = MenuAction.of(menuEntry.getType());

			switch (menuAction) {
				case EXAMINE_OBJECT:
				case ITEM_USE_ON_GAME_OBJECT:
				case SPELL_CAST_ON_GAME_OBJECT:
				case GAME_OBJECT_FIRST_OPTION:
				case GAME_OBJECT_SECOND_OPTION:
				case GAME_OBJECT_THIRD_OPTION:
				case GAME_OBJECT_FOURTH_OPTION:
				case GAME_OBJECT_FIFTH_OPTION: {
					int x = menuEntry.getParam0();
					int y = menuEntry.getParam1();
					int id = menuEntry.getIdentifier();
					Tile tile = Game.getClient().getScene().getTiles()[Game.getClient().getPlane()][x][y];
					out.addAll(TileObjects.getAt(tile, id));
					break;
				}

				case EXAMINE_NPC:
				case ITEM_USE_ON_NPC:
				case SPELL_CAST_ON_NPC:
				case NPC_FIRST_OPTION:
				case NPC_SECOND_OPTION:
				case NPC_THIRD_OPTION:
				case NPC_FOURTH_OPTION:
				case NPC_FIFTH_OPTION: {
					int id = menuEntry.getIdentifier();
					out.add(Game.getClient().getCachedNPCs()[id]);
					break;
				}

				case EXAMINE_ITEM_GROUND:
				case ITEM_USE_ON_GROUND_ITEM:
				case SPELL_CAST_ON_GROUND_ITEM:
				case GROUND_ITEM_FIRST_OPTION:
				case GROUND_ITEM_SECOND_OPTION:
				case GROUND_ITEM_THIRD_OPTION:
				case GROUND_ITEM_FOURTH_OPTION:
				case GROUND_ITEM_FIFTH_OPTION: {
					int x = menuEntry.getParam0();
					int y = menuEntry.getParam1();
					int id = menuEntry.getIdentifier();
					Tile tile = Game.getClient().getScene().getTiles()[Game.getClient().getPlane()][x][y];
					out.addAll(TileItems.getAt(tile, id));
					break;
				}

				case ITEM_USE_ON_PLAYER:
				case SPELL_CAST_ON_PLAYER:
				case PLAYER_FIRST_OPTION:
				case PLAYER_SECOND_OPTION:
				case PLAYER_THIRD_OPTION:
				case PLAYER_FOURTH_OPTION:
				case PLAYER_FIFTH_OPTION:
				case PLAYER_SIXTH_OPTION:
				case PLAYER_SEVENTH_OPTION:
				case PLAYER_EIGTH_OPTION: {
					out.add(Game.getClient().getCachedPlayers()[menuEntry.getIdentifier()]);
					break;
				}

				default:
					break;
			}
		}

		return out;
	}
}
