package meteor.ui.controllers;
 
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import osrs.Launcher;

public class ToolbarFXMLController {
    @FXML private JFXButton pluginsButton;
    @FXML private JFXButton optionsButton;
    @FXML private Text title;
    
    @FXML protected void handlePluginsPressed(ActionEvent event) {
        Launcher.togglePluginsPanel();
    }

    @FXML protected void handleOptionsPressed(ActionEvent event) {

    }

    @FXML
    public void initialize() {
    }

}