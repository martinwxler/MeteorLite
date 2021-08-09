package meteor.plugins.cannonreloader;


import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;
import meteor.config.Range;

@ConfigGroup("cannonreloader")
public interface CannonReloaderConfig extends Config
{

    @Range(
            min = 1,
            max = 30
    )

    @ConfigItem(
            keyName = "minReloadAmount",
            name = "Minimum count for reload",
            description = "The minimum cannonball count when we want to reload",
            position = 1
    )
    default int minReloadAmount()
    {
        return 9;
    }

    @Range(
            min = 1,
            max = 30
    )

    @ConfigItem(
            keyName = "maxReloadAmount",
            name = "Maximum count for reload",
            description = "The maximum cannonball count when we want to reload",
            position = 2
    )
    default int maxReloadAmount()
    {
        return 14;
    }
}