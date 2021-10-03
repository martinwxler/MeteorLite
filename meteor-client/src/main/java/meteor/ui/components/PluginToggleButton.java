package meteor.ui.components;

import com.jfoenix.controls.JFXToggleButton;
import javafx.scene.control.ContentDisplay;
import meteor.plugins.Plugin;

public class PluginToggleButton extends JFXToggleButton {

  public Plugin plugin;

  public PluginToggleButton(Plugin plugin)
  {
    this.plugin = plugin;
    setStyle("-fx-text-fill: CYAN;");
    setSize(5);
    setMinHeight(0);
    setPrefHeight(12);
    contentDisplayProperty().set(ContentDisplay.GRAPHIC_ONLY);
  }
}
