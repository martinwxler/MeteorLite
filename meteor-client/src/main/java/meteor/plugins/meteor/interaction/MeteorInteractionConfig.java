package meteor.plugins.meteor.interaction;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;

@ConfigGroup("interaction")
public interface MeteorInteractionConfig extends Config {
	@ConfigItem(
					keyName = "mouseEvents",
					name = "Mouse events",
					description = "Sends mouse events before interaction"
	)
	default boolean mouseEvents() {
		return false;
	}

	@ConfigItem(
					keyName = "drawMouse",
					name = "Draw mouse events",
					description = "Draws the sent mouse events on screen"
	)
	default boolean drawMouse() {
		return false;
	}

	@ConfigItem(
					keyName = "debug",
					name = "Debug actions",
					description = "Prints out sent actions to console"
	)
	default boolean debug() {
		return false;
	}

	@ConfigItem(
					keyName = "debugDialog",
					name = "Debug dialogs",
					description = "Prints out dialog actions to console"
	)
	default boolean debugDialogs() {
		return false;
	}
}
