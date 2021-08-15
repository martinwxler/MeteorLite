package meteor.plugins.void3tteaks;

import meteor.config.*;

@ConfigGroup("void3tTeaks")
public interface Void3tTeaksConfig extends Config {

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
