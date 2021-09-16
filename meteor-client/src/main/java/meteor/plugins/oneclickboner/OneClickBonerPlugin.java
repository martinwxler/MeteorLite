package meteor.plugins.oneclickboner;

import com.google.inject.Inject;
import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.entities.NPCs;
import meteor.plugins.api.entities.Players;
import meteor.plugins.api.entities.TileObjects;
import meteor.plugins.api.items.Inventory;
import meteor.plugins.api.widgets.Dialog;
import net.runelite.api.*;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;

import java.util.List;

@PluginDescriptor(
				name = "One Click Boner",
				description = "uses bones on a gilded altar for reclined prayer xp",
				enabledByDefault = false
)
public class OneClickBonerPlugin extends Plugin {
	@Inject
	private Client client;

	@Inject
	private OneClickBonerConfig config;

	@Provides
	public OneClickBonerConfig getConfig(ConfigManager configManager) {
		return configManager.getConfig(OneClickBonerConfig.class);
	}

	@Subscribe
	public void onClientTick(ClientTick event) {
		client.insertMenuItem("One Click Boner", "", 0, 0, 0, 0, false);
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked event) {
		if (!event.getMenuOption().equals("One Click Boner")) {
			return;
		}

		if (Players.getLocal().isMoving()) {
			event.consume();
			return;
		}

		Item bones = Inventory.getFirst(config.ID());
		Item noted = Inventory.getFirst(config.notedID());

		if (client.getWidget(WidgetInfo.DIALOG_OPTION_OPTIONS) != null) {
			Widget dialog = client.getWidget(WidgetInfo.DIALOG_OPTION_OPTIONS);
			List<Widget> children = Dialog.getOptions();
			int x = 0;
			for (int i = 0; i < children.size(); i++) {
				if (children.get(i).getText().toLowerCase().contains("all")) {
					x = i;
				}
			}

			event.setMenuEntry(new MenuEntry("continue", "", 0, MenuAction.WIDGET_TYPE_6.getId(), x + 1, dialog.getId(), false));
			return;
		}

		NPC phials = NPCs.getNearest("Phials");
		if (phials != null) {
			if (noted == null) {
				toggle();
				return;
			}

			if (bones == null) {
				client.setSelectedItemSlot(noted.getSlot());
				client.setSelectedItemWidget(noted.getWidgetId());
				client.setSelectedItemID(noted.getId());
				event.setMenuEntry(new MenuEntry("", "", phials.getIndex(), MenuAction.ITEM_USE_ON_NPC.getId(), 0, 0, false));
				return;
			}

			if (config.useLast()) {
				TileObject advertisement = TileObjects.getNearest(29091);
				if (advertisement != null) {
					event.setMenuEntry(new MenuEntry("", "", 29091, MenuAction.GAME_OBJECT_THIRD_OPTION.getId(), advertisement.getLocalLocation().getSceneX(), advertisement.getLocalLocation().getSceneY(), false));
				}

				return;
			}

			TileObject x = TileObjects.getNearest(15478);
			if (x != null) {
				event.setId(x.getId());
				event.setMenuAction(MenuAction.GAME_OBJECT_SECOND_OPTION);
				event.setParam0(x.menuPoint().getX());
				event.setParam1(x.menuPoint().getY());
			}

			return;
		}

		if (TileObjects.getNearest("Altar") == null) {
			return;
		}

		if (bones != null) {
			TileObject x = TileObjects.getNearest("Altar");
			if (x != null) {
				client.setSelectedItemSlot(bones.getSlot());
				client.setSelectedItemWidget(bones.getWidgetId());
				client.setSelectedItemID(bones.getId());

				event.setId(x.getId());
				event.setMenuAction(MenuAction.ITEM_USE_ON_GAME_OBJECT);
				event.setParam0(x.menuPoint().getX());
				event.setParam1(x.menuPoint().getY());
			}

			return;
		}

		TileObject x = TileObjects.getNearest(4525);
		if (x != null) {
			event.setId(x.getId());
			event.setMenuAction(MenuAction.GAME_OBJECT_FIRST_OPTION);
			event.setParam0(x.menuPoint().getX());
			event.setParam1(x.menuPoint().getY());
		}
	}
}
