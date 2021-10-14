package meteor.ui.controllers;

import static meteor.ui.MeteorUI.lastButtonPressed;
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
    AnchorPane.setRightAnchor(button, getNextButtonPos(button));
    staticAP.getChildren().add(button);
  }

  public static void removeButton(ToolbarButton button) {
    staticAP.getChildren().remove(button);
  }

  public static double getNextButtonPos(ToolbarButton b) {
    double newButtonLayoutX = AnchorPane.getRightAnchor(staticPB) + PLUGINS_BUTTON_WIDTH + DEFAULT_SPACING;
    JFXButton lastButton = staticPB;
    int layoutX = (int) newButtonLayoutX;
    for (ToolbarButton button : buttons) {
      if (button != b)
      layoutX += button.width + 5;
    }
    for (ToolbarButton button : buttons) {
      if (lastButton != staticPB) {
        newButtonLayoutX = newButtonLayoutX + ((ToolbarButton)lastButton).width + DEFAULT_SPACING;
      }
      lastButton = button;
    }
    return layoutX;
  }

  @FXML
  protected void handlePluginsPressed(ActionEvent event) {
      meteorUI.showPlugins();
  }
}
