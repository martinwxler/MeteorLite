package meteor.ui.controllers;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import meteor.MeteorLiteClientModule;

public class ToolbarFXMLController {
  @FXML
  protected void handlePluginsPressed(ActionEvent event) throws IOException {
    MeteorLiteClientModule.togglePluginsPanel();
  }
}