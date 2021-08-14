package com.owain.chinLogin;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;

@ConfigGroup("chinLogin")
public interface ChinLoginConfig extends Config {

  @ConfigItem(
      keyName = "email",
      name = "Email",
      description = "",
      position = 0
  )
  default String email() { return ""; }

  @ConfigItem(
      keyName = "password",
      name = "Password",
      description = "",
      position = 1,
      secret = true
  )
  default String password() { return ""; }
}
