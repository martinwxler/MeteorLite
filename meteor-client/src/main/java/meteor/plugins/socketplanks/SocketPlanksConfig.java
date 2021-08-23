package meteor.plugins.socketplanks;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;

@ConfigGroup("planks")
public interface SocketPlanksConfig extends Config {
    @ConfigItem(
            keyName = "splitTimer",
            name = "Split Timer",
            description = "Displays split timer"
    )
    default boolean splitTimer() {
        return true;
    }
}