package meteor.plugins.hootagility;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;

@ConfigGroup("hootagility")
public interface HootAgilityConfig extends Config {
	@ConfigItem(
					name = "Course",
					keyName = "course",
					description = "Course to complete"
	)
	default Course course() {
		return Course.NEAREST;
	}
}
