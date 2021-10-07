package meteor.plugins.highalchemy;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;
import meteor.config.Range;

@ConfigGroup("HighAlchPlugin")
public interface HighAlchConfig extends Config{
    @Range(textInput = true)
    @ConfigItem(
            keyName = "itemID",
            name = "itemID",
            description = "Item id of what you want to alch",
            position = 1
    )
    default int itemID() {
        return 2;
    }
}
