package sponge.ui.controllers;
 
import com.jfoenix.controls.JFXButton;
import javafx.embed.swing.SwingNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import osrs.Launcher;

import java.io.IOException;

public class ToolbarFXMLController {
    @FXML private JFXButton settingsButton;
    @FXML private Text title;
    
    @FXML protected void handleSettingsPressed(ActionEvent event) {
        Launcher.togglePluginsPanel();
    }

    @FXML
    public void initialize() {
    }

}