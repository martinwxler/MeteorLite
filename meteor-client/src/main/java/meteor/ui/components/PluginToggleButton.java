package meteor.ui.components;

import com.jfoenix.controls.JFXToggleButton;
import meteor.plugins.Plugin;

public class PluginToggleButton extends JFXToggleButton {

  public Plugin plugin;

  public PluginToggleButton(Plugin plugin)
  {
    this.plugin = plugin;
  }
}
