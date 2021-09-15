package meteor.plugins.meteor.meteorlite;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;

@ConfigGroup("MeteorLite")
public interface MeteorLiteConfig extends Config {
    @ConfigItem(
            keyName = "pluginPanelResize",
            name = "Plugin Panel Resizes Game",
            description = "Whether toggling the plugin panel resizes the game client."
    )
    default boolean resizeGame() {
        return false;
    }

    @ConfigItem(
            keyName = "debugEnabled",
            name = "Enable debug logs",
            description = "Enables printing of debug logs"
    )
    default boolean debugEnabled() {
        return false;
    }
}
