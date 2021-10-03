package meteor.plugins.autobankpin;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;
import meteor.config.ConfigSection;

@ConfigGroup("autobankpin")
public interface AutoBankPinConfig extends Config{

  @ConfigItem(
      keyName = "bankpin",
      name = "Bank Pin",
      description = "Bank pin that will be entered",
      position = 0,
      secret = true
  )
  default String bankpin()
  {
    return "";
  }
}
