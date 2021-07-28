package meteor.plugins.stretchedmode;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import meteor.MeteorLite;
import meteor.plugins.Plugin;
import org.sponge.util.Logger;

public class ConfigFXMLController {

  Logger logger = new Logger("PluginsFXMLController");
  StretchedModePlugin plugin;
  @FXML
  private JFXToggleButton keepAspectRatioEnabled;
  @FXML
  private JFXToggleButton increasedPerfEnabled;
  @FXML
  private JFXToggleButton integerScalingEnabled;
  @FXML
  private JFXSlider scalingFactorSlider;

  {
    for (Plugin p : MeteorLite.plugins) {
      if (p instanceof StretchedModePlugin) {
        plugin = (StretchedModePlugin) p;
      }
    }
  }

  @FXML
  protected void onKeepAspectRatioEnabled(ActionEvent event) {
    StretchedModePlugin.keepAspectRatio = !StretchedModePlugin.keepAspectRatio;
    plugin.updateConfig();
  }

  @FXML
  protected void onIncreasedPerfEnabled(ActionEvent event) {
    StretchedModePlugin.isStretchedFast = !StretchedModePlugin.isStretchedFast;
    plugin.updateConfig();
  }

  @FXML
  protected void onIntegerScalingEnabled(ActionEvent event) {
    StretchedModePlugin.integerScalingEnabled = !StretchedModePlugin.integerScalingEnabled;
    plugin.updateConfig();
  }

  @FXML
  protected void onScalingFactorChanged(MouseEvent event) {
    StretchedModePlugin.scalingFactor = (int) scalingFactorSlider.getValue();
    plugin.updateConfig();
  }

  @FXML
  public void initialize() {
    keepAspectRatioEnabled.setSelected(StretchedModePlugin.keepAspectRatio);
    increasedPerfEnabled.setSelected(StretchedModePlugin.isStretchedFast);
    integerScalingEnabled.setSelected(StretchedModePlugin.integerScalingEnabled);
    scalingFactorSlider.setValue(StretchedModePlugin.scalingFactor);
  }

}