package meteor.ui.components;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import meteor.plugins.Plugin;

public class PluginConfigButton extends JFXButton {

  private Plugin plugin;

  public PluginConfigButton(Plugin plugin)
  {
    this.plugin = plugin;
  }
}
