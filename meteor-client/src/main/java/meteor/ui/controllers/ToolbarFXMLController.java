package meteor.ui.controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import meteor.MeteorLiteClientModule;

public class ToolbarFXMLController {

  @FXML
  JFXButton idleButton;

  public static JFXButton idleButtonInstance;

  @FXML
  protected void initialize() {
    idleButtonInstance = idleButton;
    idleButton.setVisible(false);
  }

  @FXML
  protected void handlePluginsPressed(ActionEvent event) throws IOException {
    MeteorLiteClientModule.togglePluginsPanel();
  }
}