package meteor.plugins.api.example.chickenkiller;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;

@ConfigGroup("chickenkiller")
public interface ChickenKillerConfig extends Config {
    @ConfigItem(
            keyName = "enabled",
            name = "Enabled",
            description = "Enabled"
    )
    default boolean enabled() {
        return false;
    }
}
