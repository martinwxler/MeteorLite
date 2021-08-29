package meteor.plugins.oneclickcustom;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;
import meteor.config.Range;

@ConfigGroup("oneclickcustom")
public interface OneClickCustomConfig extends Config {
    @Range(textInput = true)
    @ConfigItem(
            position = 0,
            keyName = "gameObjectID",
            name = "gameObjectID",
            description = "input game object ID"
    )
    default int  gameObjectID() { return 0;}

    @ConfigItem(
            position = 1,
            keyName = "fishingSpot",
            name = "Fishing?",
            description = "Check if Fishing Spots(NPC). Input NPC ID to the ID field."
    )
    default boolean fishingSpot() { return true;}

    @ConfigItem(
            position = 2,
            keyName = "InventoryFull",
            name = "Disable on full inventory",
            description = "Disable on full invent"
    )
    default boolean InventoryFull() {
        return true;
    }
}