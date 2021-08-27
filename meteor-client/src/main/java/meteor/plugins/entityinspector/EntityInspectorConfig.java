package meteor.plugins.entityinspector;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;

@ConfigGroup("entityinspector")
public interface EntityInspectorConfig extends Config {
	@ConfigItem(
					keyName = "gameObjects",
					name = "Game Objects",
					description = "Render Game Objects"
	)
	default boolean gameObjects() {
		return false;
	}

	@ConfigItem(
					keyName = "decorObjects",
					name = "Decorative Objects",
					description = "Render Decorative Objects"
	)
	default boolean decorObjects() {
		return false;
	}

	@ConfigItem(
					keyName = "wallObjects",
					name = "Wall Objects",
					description = "Render Wall Objects"
	)
	default boolean wallObjects() {
		return false;
	}

	@ConfigItem(
					keyName = "groundObjects",
					name = "Ground Objects",
					description = "Render Ground Objects"
	)
	default boolean groundObjects() {
		return false;
	}

	@ConfigItem(
					keyName = "tileItems",
					name = "TileItems",
					description = "Render Tile Items"
	)
	default boolean tileItems() {
		return false;
	}

	@ConfigItem(
					keyName = "npcs",
					name = "NPCs",
					description = "Render NPCs"
	)
	default boolean npcs() {
		return false;
	}

	@ConfigItem(
					keyName = "players",
					name = "Players",
					description = "Render Players"
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
