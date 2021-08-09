package meteor.plugins.prayerpotdrinker;


import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;
import meteor.config.Range;

@ConfigGroup("praypotdrinker")
public interface PrayerPotDrinkerConfig extends Config
{
    @Range(
            min = 1,
            max = 99
    )

    @ConfigItem(
            keyName = "minPrayerLevel",
            name = "Minimum",
            description = "",
            position = 1
    )
    default int minPrayerLevel() { return 1; }

    @Range(
            min = 1,
            max = 99
    )

    @ConfigItem(
            keyName = "maxPrayerLevel",
            name = "Maximum",
            description = "",
            position = 2
    )
    default int maxPrayerLevel() { return 30; }
}