package meteor.plugins.api.debug.walking;

import meteor.config.*;

import java.awt.event.InputEvent;

import static java.awt.event.KeyEvent.*;

@ConfigGroup("walkerdebug")
public interface WalkerDebugConfig extends Config {
    @Range(textInput = true)
    @ConfigItem(
            keyName = "x",
            name = "X",
            description = "X Coordinate",
            position = 0
    )
    default int x()
    {
        return 3264;
    }

    @Range(textInput = true)
    @ConfigItem(
            keyName = "y",
            name = "Y",
            description = "Y Coordinate",
            position = 1
    )
    default int y()
    {
        return 3330;
    }

    @ConfigItem(
            keyName = "walk",
            name = "Execute walker",
            description = "Walks to specified coords, blocks manual walk input",
            position = 2
    )
    default boolean walk() {
        return true;
    }
}
