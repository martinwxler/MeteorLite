package meteor.plugins.api.plugin.interaction;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;
import meteor.config.Range;

@ConfigGroup("interactdebug")
public interface InteractConfig extends Config {
    @Range(textInput = true)
    @ConfigItem(
            keyName = "actionIndex",
            name = "Action Index",
            description = "Action Index",
            position = 0
    )
    default int actionIndex()
    {
        return 3330;
    }

    @ConfigItem(
            keyName = "actionName",
            name = "Action Name",
            description = "Action Name",
            position = 1
    )
    default String actionName()
    {
        return "";
    }

    @ConfigItem(
            keyName = "enabled",
            name = "Enabled",
            description = "Enabled",
            position = 2
    )
    default boolean enabled() {
        return false;
    }
}
