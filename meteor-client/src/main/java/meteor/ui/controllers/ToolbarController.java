package meteor.ui.controllers;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import meteor.MeteorLiteClientLauncher;
import meteor.ui.MeteorUI;

import javax.inject.Inject;
import meteor.ui.components.ToolbarButton;

public class ToolbarController {

  @FXML
  AnchorPane toolbar;

  @FXML
  JFXButton idleButton;

  @FXML
  JFXButton pluginsButton;

  @Inject
  private MeteorUI meteorUI;

  public static JFXButton idleButtonInstance;
  public static ArrayList<ToolbarButton> buttons = new ArrayList<>();
  public static JFXButton staticPB;
  public static AnchorPane staticAP;
  static int DEFAULT_SPACING = 5;
  static int PLUGINS_BUTTON_WIDTH = 82;

  @FXML
  protected void initialize() {
    MeteorLiteClientLauncher.injector.injectMembers(this);
    idleButtonInstance = idleButton;
    staticPB = pluginsButton;
    staticAP = toolbar;
    idleButton.setVisible(false);
  }

  public static void addButton(ToolbarButton button) {
    buttons.add(button);
    AnchorPane.setRightAnchor(button, getNextButtonPos());
    staticAP.getChildren().add(button);
  }

  public static void removeButton(ToolbarButton button) {
    staticAP.getChildren().remove(button);
  }

  public static double getNextButtonPos() {
    double newButtonLayoutX = AnchorPane.getRightAnchor(staticPB) + PLUGINS_BUTTON_WIDTH + DEFAULT_SPACING;
    JFXButton lastButton = staticPB;
    for (ToolbarButton button : buttons) {
      if (lastButton != staticPB) {
        newButtonLayoutX = newButtonLayoutX + ((ToolbarButton)lastButton).getRealWidth() + DEFAULT_SPACING;
      }
      lastButton = button;
    }
    return newButtonLayoutX;
  }

  @FXML
  protected void handlePluginsPressed(ActionEvent event) throws IOException {
    meteorUI.rightPanel.setScene(meteorUI.pluginsRootScene);
    if (!meteorUI.isRightPanelVisible() || meteorUI.getRightPanel() == meteorUI.pluginsRootScene)
      meteorUI.toggleRightPanel();
  }
}
