package meteor.plugins.voidHunter;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import meteor.config.Button;
import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;
import meteor.config.Icon;

@ConfigGroup("voidHunter")
public interface VoidHunterConfig extends Config {

  @Icon(icon = FontAwesomeIcon.PLAY)
  @ConfigItem(
      keyName = "startStop",
      name = "Start/Stop",
      description = "",
      position = 150
  )
  default Button startButton()
  {
    return new Button();
  }
}
