package meteor.plugins.voidHunter;

import meteor.config.*;

import java.awt.event.InputEvent;

import static java.awt.event.KeyEvent.VK_H;

@ConfigGroup("voidHunter")
public interface VoidHunterConfig extends Config {

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

  @ConfigItem(
          keyName = "keybind",
          name = "Toggle Key",
          description = "Toggles the plugin",
          position = 1
  )
  default Keybind keybind()
  {
    return new Keybind(VK_H, InputEvent.CTRL_DOWN_MASK, false);
  }

  @ConfigItem(
          keyName = "hide", //disables the widget
          name = "Privacy",
          description = "Toggles the overlay",
          position = 2
  )
  default boolean hide()
  {
    return false;
  }
}
