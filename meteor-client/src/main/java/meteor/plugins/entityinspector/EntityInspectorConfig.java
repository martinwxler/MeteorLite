package meteor.plugins.entityinspector;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;
import meteor.config.ConfigSection;

@ConfigGroup("entityinspector")
public interface EntityInspectorConfig extends Config {
	@ConfigSection(
					keyName = "displayedInfo",
					position = 0,
					name = "Displayed info",
					description = ""
	)
	String displayedInfo = "Displayed info";

	@ConfigItem(
					keyName = "hover",
					name = "Hover",
					description = "Show on hover",
					section = displayedInfo
	)
	default boolean hover() {
		return true;
	}

	@ConfigItem(
					keyName = "ids",
					name = "IDs",
					description = "Show ids",
					section = displayedInfo
	)
	default boolean ids() {
		return true;
	}

	@ConfigItem(
					keyName = "names",
					name = "Names",
					description = "Show names",
					section = displayedInfo
	)
	default boolean names() {
		return true;
	}

	@ConfigItem(
					keyName = "actions",
					name = "Actions",
					description = "Show actions",
					section = displayedInfo
	)
	default boolean actions() {
		return true;
	}

	@ConfigItem(
					keyName = "locations",
					name = "World locations",
					description = "Show world locations",
					section = displayedInfo
	)
	default boolean worldLocations() {
		return true;
	}

	@ConfigItem(
					keyName = "indexes",
					name = "Indexes (Actors)",
					description = "Show indexes",
					section = displayedInfo
	)
	default boolean indexes() {
		return true;
	}

	@ConfigItem(
					keyName = "animations",
					name = "Animations",
					description = "Show animations",
					section = displayedInfo
	)
	default boolean animations() {
		return true;
	}

	@ConfigItem(
					keyName = "graphics",
					name = "Graphic (Actors)",
					description = "Show graphics",
					section = displayedInfo
	)
	default boolean graphics() {
		return true;
	}

	@ConfigItem(
					keyName = "quantities",
					name = "Quantities (TileItems)",
					description = "Show quantities",
					section = displayedInfo
	)
	default boolean quantities() {
		return true;
	}

	@ConfigSection(
					keyName = "tileObjects",
					position = 1,
					name = "Tile Objects",
					description = ""
	)
	String tileObjects = "Tile Objects";

	@ConfigItem(
					keyName = "gameObjects",
					name = "Game Objects",
					description = "Render Game Objects",
					section = tileObjects
	)
	default boolean gameObjects() {
		return false;
	}

	@ConfigItem(
					keyName = "decorObjects",
					name = "Decorative Objects",
					description = "Render Decorative Objects",
					section = tileObjects
	)
	default boolean decorObjects() {
		return false;
	}

	@ConfigItem(
					keyName = "wallObjects",
					name = "Wall Objects",
					description = "Render Wall Objects",
					section = tileObjects
	)
	default boolean wallObjects() {
		return false;
	}

	@ConfigItem(
					keyName = "groundObjects",
					name = "Ground Objects",
					description = "Render Ground Objects",
					section = tileObjects
	)
	default boolean groundObjects() {
		return false;
	}

	@ConfigItem(
					keyName = "tileItems",
					name = "TileItems",
					description = "Render Tile Items",
					section = tileObjects
	)
	default boolean tileItems() {
		return false;
	}

	@ConfigSection(
					keyName = "actorInfo",
					position = 0,
					name = "Actor info",
					description = ""
	)
	String actorInfo = "Actor info";

	@ConfigSection(
					keyName = "actors",
					position = 0,
					name = "Actors",
					description = ""
	)
	String actors = "Actors";

	@ConfigItem(
					keyName = "npcs",
					name = "NPCs",
					description = "Render NPCs",
					section = actors
	)
	default boolean npcs() {
		return false;
	}

	@ConfigItem(
					keyName = "players",
					name = "Players",
					description = "Render Players",
					section = actors
	)
	default boolean players() {
		return false;
	}

	@ConfigItem(
					keyName = "projectiles",
					name = "Projectiles",
					description = "Render Projectiles"
	)
	default boolean projectiles() {
		return false;
	}

	@ConfigItem(
					keyName = "graphicsObjects",
					name = "Graphics Objects",
					description = "Render Graphics Objects"
	)
	default boolean graphicsObjects() {
		return false;
	}

	@ConfigItem(
					keyName = "inventory",
					name = "Inventory",
					description = "Render Inventory"
	)
	default boolean inventory() {
		return false;
	}

	@ConfigItem(
					keyName = "tileLocation",
					name = "Tile Location",
					description = "Render Tile Location"
	)
	default boolean tileLocation() {
		return false;
	}

	@ConfigItem(
					keyName = "path",
					name = "Path",
					description = "Render calculated Path"
	)
	default boolean path() {
		return false;
	}

	@ConfigItem(
					keyName = "collisions",
					name = "Collision Map",
					description = "Render Collision Map"
	)
	default boolean collisions() {
		return false;
	}
}
