package sponge.ui.controllers;

import com.jfoenix.controls.JFXToggleButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import net.runelite.rs.Reflection;
import org.sponge.util.Logger;

public class PluginsFXMLController {
    Logger logger = new Logger("PluginsFXMLController");
    @FXML private JFXToggleButton reflectionEnabled;

    @FXML protected void onReflectionPluginToggled(ActionEvent event) {
        Reflection.printDebugMessages = !Reflection.printDebugMessages;
        logger.warn("[DEBUG] Jagex reflection checks enabled: " + Reflection.printDebugMessages);
    }

    @FXML
    public void initialize() {
        reflectionEnabled.setSelected(Reflection.printDebugMessages);
    }

}