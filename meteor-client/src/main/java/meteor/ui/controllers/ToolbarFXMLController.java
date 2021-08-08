package meteor.ui.controllers;

import com.google.inject.Injector;
import com.jfoenix.controls.JFXButton;
import javafx.embed.swing.JFXPanel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.text.Text;
import javax.inject.Inject;
import javax.inject.Named;
import meteor.MeteorLite;
import meteor.OSRSClient;
import meteor.eventbus.EventBus;
import net.runelite.api.Client;

public class ToolbarFXMLController {

  @Inject
  Client client;

  @Inject
  EventBus eventBus;

  @FXML
  private JFXButton pluginsButton;
  @FXML
  private JFXButton optionsButton;
  @FXML
  private Text title;

  @Inject
  @Named("rightPanelScene")
  public Scene rightPanelScene;

  @FXML
  protected void handlePluginsPressed(ActionEvent event) {
    OSRSClient.togglePluginsPanel(rightPanelScene);
  }

  @FXML
  protected void handleOptionsPressed(ActionEvent event) {
  }

  @FXML
  public void initialize() {
    for (OSRSClient clientInstance : OSRSClient.clientInstances)
      clientInstance.instanceInjector.injectMembers(this);
    eventBus.register(this);
  }
}