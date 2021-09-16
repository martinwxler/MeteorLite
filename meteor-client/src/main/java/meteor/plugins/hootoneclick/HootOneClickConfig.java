package meteor.plugins.hootoneclick;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;
import meteor.config.ConfigSection;

@ConfigGroup("hootoneclick")
public interface HootOneClickConfig extends Config {
	@ConfigSection(
					name = "Game Objects",
					description = "Replace Game Object interactions",
					closedByDefault = true,
					position = 0
	)
	String gameObjs = "Game Objects";

	@ConfigItem(
					keyName = "gameObjects",
					name = "Config",
					description = "Syntax = ObjectName:ReplacedAction,Object2:ReplacedAction",
					section = gameObjs,
					position = 0
	)
	default String gameObjectConfig() {
		return "";
	}

	@ConfigSection(
					name = "NPCs",
					description = "Replace NPC interactions",
					closedByDefault = true,
					position = 1
	)
	String npcs = "NPCs";

	@ConfigItem(
					keyName = "npcs",
					name = "Config",
					description = "Syntax = ObjectName:ReplacedAction,Object2:ReplacedAction",
					section = npcs,
					position = 1
	)
	default String npcConfig() {
		return "";
	}

	@ConfigSection(
					name = "Ground Items",
					description = "Replace Ground Item interactions",
					closedByDefault = true,
					position = 2
	)
	String groundItems = "Ground Items";

	@ConfigItem(
					keyName = "groundItems",
					name = "Config",
					description = "Syntax = ObjectName:ReplacedAction,Object2:ReplacedAction",
					section = groundItems,
					position = 2
	)
	default String groundItemConfig() {
		return "";
	}

	@ConfigSection(
					name = "Widgets",
					description = "Replace Widget interactions",
					closedByDefault = true,
					position = 3
	)
	String widgets = "Widgets";

	@ConfigItem(
					keyName = "widgets",
					name = "Config",
					description = "Syntax = ObjectName:ReplacedAction,Object2:ReplacedAction",
					section = widgets,
					position = 3
	)
	default String widgetConfig() {
		return "";
	}

	@ConfigSection(
					name = "Items",
					description = "Replace Item interactions",
					closedByDefault = true,
					position = 4
	)
	String items = "Items";

	@ConfigItem(
					keyName = "items",
					name = "Config",
					description = "Syntax = ObjectName:ReplacedAction,Object2:ReplacedAction",
					section = items,
					position = 4
	)
	default String itemConfig() {
		return "";
	}

	@ConfigSection(
					name = "Players",
					description = "Replace Player interactions",
					closedByDefault = true,
					position = 5
	)
	String players = "Players";

	@ConfigItem(
					keyName = "players",
					name = "Config",
					description = "Syntax = ObjectName:ReplacedAction,Object2:ReplacedAction",
					section = players,
					position = 5
	)
	default String playerConfig() {
		return "";
	}
}
