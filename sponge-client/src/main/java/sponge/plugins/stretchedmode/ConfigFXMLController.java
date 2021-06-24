package sponge.plugins.stretchedmode;

import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import net.runelite.rs.Reflection;
import org.sponge.util.Logger;
import sponge.Plugin;
import sponge.SpongeOSRS;

public class ConfigFXMLController {
    Logger logger = new Logger("PluginsFXMLController");
    @FXML private JFXToggleButton keepAspectRatioEnabled;
    @FXML private JFXToggleButton increasedPerfEnabled;
    @FXML private JFXToggleButton integerScalingEnabled;
    @FXML private JFXSlider scalingFactorSlider;
    StretchedModePlugin plugin;

    {
        for (Plugin p : SpongeOSRS.plugins)
        {
            if (p instanceof StretchedModePlugin)
            {
                plugin = (StretchedModePlugin) p;
            }
        }
    }

    @FXML protected void onKeepAspectRatioEnabled(ActionEvent event) {
        StretchedModePlugin.keepAspectRatio = !StretchedModePlugin.keepAspectRatio;
        plugin.updateConfig();
    }

    @FXML protected void onIncreasedPerfEnabled(ActionEvent event) {
        StretchedModePlugin.isStretchedFast = !StretchedModePlugin.isStretchedFast;
        plugin.updateConfig();
    }

    @FXML protected void onIntegerScalingEnabled(ActionEvent event) {
        StretchedModePlugin.integerScalingEnabled = !StretchedModePlugin.integerScalingEnabled;
        plugin.updateConfig();
    }

    @FXML protected void onScalingFactorChanged(MouseEvent event) {
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