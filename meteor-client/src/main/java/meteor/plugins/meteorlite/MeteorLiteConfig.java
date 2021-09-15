package meteor.plugins.meteorlite;

import meteor.config.*;

@ConfigGroup("MeteorLite")
public interface MeteorLiteConfig extends Config {
    /*
      Client settings
     */

    @ConfigSection(
            name = "Client settings",
            description = "Client settings",
            closedByDefault = true,
            position = 0
    )
    String clientSettings = "Client settings";

    @ConfigItem(
            keyName = "pluginPanelResize",
            name = "Plugin Panel Resizes Game",
            description = "Whether toggling the plugin panel resizes the game client.",
            section = clientSettings,
            position = 0
    )
    default boolean resizeGame() {
        return false;
    }

    /*
      Debugging/Logger
     */

    @ConfigSection(
            name = "Debug/Logger",
            description = "Debugging settings",
            closedByDefault = true,
            position = 1
    )
    String debugLogger = "Debug/Logger";

    @ConfigItem(
            keyName = "debugEnabled",
            name = "Enable debug logs",
            description = "Enables printing of debug logs",
            section = debugLogger,
            position = 0
    )
    default boolean debugEnabled() {
        return false;
    }

    /*
      Externals
     */

    @ConfigSection(
            name = "External manager",
            description = "External plugins",
            closedByDefault = true,
            position = 2
    )
    String externalManager = "External manager";

    @ConfigItem(
            keyName = "externalsLoadOnStartup",
            name = "Load on startup",
            description = "Load externals on startup",
            section = externalManager,
            position = 0
    )
    default boolean externalsLoadOnStartup() {
        return false;
    }

    @Icon
    @ConfigItem(
            keyName = "reloadExternals",
            name = "(Re-)Load Externals",
            description = "Reload external plugins from externals folder",
            section = externalManager,
            position = 1
    )
    default Button reloadExternals() {
        return new Button();
    }

    @ConfigSection(
            name = "Interaction manager",
            description = "Interaction settings",
            closedByDefault = true,
            position = 3
    )
    String interactionManager = "Interaction manager";

    @ConfigItem(
            keyName = "mouseEvents",
            name = "Mouse events",
            description = "Sends mouse events before interaction",
            section = interactionManager,
            position = 0
    )
    default boolean mouseEvents() {
        return false;
    }

    @ConfigItem(
            keyName = "drawMouse",
            name = "Draw mouse events",
            description = "Draws the sent mouse events on screen",
            section = interactionManager,
            position = 1
    )
    default boolean drawMouse() {
        return false;
    }

    @ConfigItem(
            keyName = "debugInteractions",
            name = "Debug interactions",
            description = "Prints interactions to console",
            section = interactionManager,
            position = 2
    )
    default boolean debugInteractions() {
        return false;
    }

    @ConfigItem(
            keyName = "debugDialogs",
            name = "Debug dialogs",
            description = "Prints dialog actions to console",
            section = interactionManager,
            position = 3
    )
    default boolean debugDialogs() {
        return false;
    }

    @ConfigSection(
            name = "Visuals",
            description = "Client visual settings",
            closedByDefault = true,
            position = 4
    )
    String visualSettings = "Visuals";

    @ConfigItem(
            keyName = "meteorLoginScreen",
            name = "MeteorLite Login screen",
            description = "Sets the custom MeteorLite Login screen",
            section = visualSettings,
            position = 0
    )
    default boolean meteorLoginScreen() {
        return true;
    }
}
