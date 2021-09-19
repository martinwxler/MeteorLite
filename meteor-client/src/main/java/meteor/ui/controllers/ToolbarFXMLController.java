package meteor.ui.controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import meteor.MeteorLiteClientLauncher;
import meteor.MeteorLiteClientModule;
import meteor.ui.MeteorUI;

import javax.inject.Inject;

public class ToolbarFXMLController {

  @FXML
  JFXButton idleButton;

  @Inject
  private MeteorUI meteorUI;

  public static JFXButton idleButtonInstance;

  @FXML
  protected void initialize() {
    MeteorLiteClientLauncher.injector.injectMembers(this);
    idleButtonInstance = idleButton;
    idleButton.setVisible(false);
  }

  @FXML
  protected void handlePluginsPressed(ActionEvent event) throws IOException {
    meteorUI.toggleRightPanel();
  }
}
