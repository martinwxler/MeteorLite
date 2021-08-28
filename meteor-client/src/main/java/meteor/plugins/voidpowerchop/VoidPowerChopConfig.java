package meteor.plugins.voidpowerchop;

import meteor.config.Button;
import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;
import meteor.config.Icon;

@ConfigGroup("voidPowerchop")
public interface VoidPowerChopConfig extends Config {

  @Icon(canToggle = true)
  @ConfigItem(
      keyName = "startStop",
      name = "Start/Stop",
      description = "",
      position = 150
  )
  default Button startButton() {
    return new Button();
  }
}
