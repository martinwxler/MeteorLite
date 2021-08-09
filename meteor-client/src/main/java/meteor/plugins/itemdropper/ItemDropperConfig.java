package meteor.plugins.itemdropper;

import meteor.config.*;

@ConfigGroup("ItemDropperConfig")

public interface ItemDropperConfig extends Config
{
    @ConfigTitle(
            keyName = "delayConfig",
            name = "Sleep Delay Configuration",
            description = "Configure how the bot handles sleep delays",
            position = 1
    )
    String delayConfig = "delayConfig";


    @Range(
            min = 0,
            max = 550
    )
    @ConfigItem(
            keyName = "sleepMin",
            name = "Sleep Min",
            description = "",
            position = 2,
            section = "delayConfig"
    )
    default int sleepMin()
    {
        return 60;
    }

    @Range(
            min = 0,
            max = 550
    )

    @ConfigItem(
            keyName = "sleepMax",
            name = "Sleep Max",
            description = "",
            position = 3,
            section = "delayConfig"
    )
    default int sleepMax()
    {
        return 350;
    }

    @Range(
            min = 0,
            max = 550
    )

    @ConfigItem(
            keyName = "itemIDs",
            name = "Item IDs to drop",
            description = "List of item IDs. Seperated by a comma.",
            position = 4
    )
    default String itemIDs()
    {
        return null;
    }
}