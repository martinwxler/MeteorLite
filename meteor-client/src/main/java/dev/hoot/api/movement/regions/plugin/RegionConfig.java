package dev.hoot.api.movement.regions.plugin;

import meteor.config.Button;
import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;
import meteor.config.Icon;

@ConfigGroup("region-debug")
public interface RegionConfig extends Config {
    @Icon
    @ConfigItem(
            keyName = "download",
            name = "Download new data",
            description = "Download new data"
    )
    default Button download() {
        return new Button();
    }
}
