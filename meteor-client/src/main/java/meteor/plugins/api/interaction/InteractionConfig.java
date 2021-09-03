package meteor.plugins.api.interaction;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;

@ConfigGroup("interaction")
public interface InteractionConfig extends Config {
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
}
