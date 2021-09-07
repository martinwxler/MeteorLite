package meteor.plugins.hootherblore;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;
import meteor.config.ConfigSection;

@ConfigGroup("hootherblore")
public interface HootHerbloreConfig extends Config {
	@ConfigSection(
					name = "Cleaning",
					description = "Cleaning grimy herbs"
	)
	String cleaning = "Cleaning";

	@ConfigItem(
					keyName = "herbIds",
					name = "Grimy herb ids",
					description = "Herb IDs separated by comma",
					section = cleaning,
					position = 0
	)
	default String herbIds() {
		return "";
	}
}
