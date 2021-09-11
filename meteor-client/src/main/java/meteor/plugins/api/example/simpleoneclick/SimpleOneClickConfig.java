package meteor.plugins.api.example.simpleoneclick;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;

@ConfigGroup("simpleoneclick")
public interface SimpleOneClickConfig extends Config {
	@ConfigItem(
					keyName = "config",
					name = "Config",
					description = "config"
	)
	default String config() {
		return "Grand Exchange booth:Collect,Blabla:Attack";
	}
}
