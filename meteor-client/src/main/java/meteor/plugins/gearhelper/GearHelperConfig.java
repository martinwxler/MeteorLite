package meteor.plugins.gearhelper;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;

@ConfigGroup("GearHelper")
public interface GearHelperConfig extends Config {
    @ConfigItem(
            keyName = "fastEquip",
            name = "fast equip id's",
            description = "gear to be one click equipped from bank"
    )
    default String fastEquip()
    {
        return "";
    }
}
