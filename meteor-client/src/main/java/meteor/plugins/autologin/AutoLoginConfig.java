package meteor.plugins.autologin;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;

@ConfigGroup("autologin")
public interface AutoLoginConfig extends Config {
	@ConfigItem(
					keyName = "username",
					name = "Username",
					description = "Username",
					textField = true,
					position = 0
	)
	default String username() {
		return "Username";
	}

	@ConfigItem(
					keyName = "password",
					name = "Password",
					description = "Password",
					secret = true,
					textField = true,
					position = 1
	)
	default String password() {
		return "Password";
	}
}
