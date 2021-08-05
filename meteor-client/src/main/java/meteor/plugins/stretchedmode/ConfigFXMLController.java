package meteor.plugins.stretchedmode;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import meteor.PluginManager;
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
    for (Plugin p : PluginManager.plugins) {
      if (p instanceof StretchedModePlugin) {
        plugin = (StretchedModePlugin) p;
      }
    }
  }

  @FXML
  public void initialize() {
  }

}