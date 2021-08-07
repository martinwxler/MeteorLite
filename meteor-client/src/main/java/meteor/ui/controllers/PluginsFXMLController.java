package meteor.ui.controllers;

import static meteor.MeteorLite.pluginsPanelVisible;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ScrollBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import meteor.MeteorLite;
import meteor.PluginManager;
import meteor.config.ConfigManager;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.ui.components.PluginConfigButton;
import meteor.ui.components.PluginToggleButton;
import org.sponge.util.Logger;

public class PluginsFXMLController {

  Logger logger = new Logger("PluginsFXMLController");

  @FXML
  private AnchorPane pluginPanel;

  @FXML
  private VBox pluginList;

  public static Plugin lastPluginInteracted;

  private double configItemViewOffset = 0;

  boolean fakeEvent = false;

  @FXML
  public void initialize() {
    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setMinSize(350, 600);
    AnchorPane.setTopAnchor(scrollPane, 45.0);
    AnchorPane.setBottomAnchor(scrollPane, 0.0);

    FontAwesomeIconView plug = new FontAwesomeIconView(FontAwesomeIcon.PLUG);
    plug.setFill(Paint.valueOf("CYAN"));
    plug.setLayoutY(25);
    AnchorPane.setLeftAnchor(plug, 130.0);

    pluginPanel.getChildren().add(plug);

    Text pluginsString = new Text();
    pluginsString.setText("Plugins");
    pluginsString.setFill(Paint.valueOf("WHITE"));
    pluginsString.setLayoutY(28);
    pluginsString.setWrappingWidth(300);
    pluginsString.setFont(Font.font(18));
    AnchorPane.setLeftAnchor(pluginsString, 148.0);

    pluginPanel.getChildren().add(pluginsString);
    pluginList = new VBox();
    pluginList.setLayoutY(45);

    for (Plugin p : PluginManager.plugins)
    {
      AnchorPane pluginPanel = new AnchorPane();
      pluginPanel.setStyle("-fx-border-color: transparent;");

      pluginPanel.setPrefHeight(25);
      pluginPanel.setPrefWidth(280);

      PluginConfigButton configButton = new PluginConfigButton(p);
      if (p.getConfig(MeteorLite.injector.getInstance(ConfigManager.class)) != null)
      {
        configButton.setContentDisplay(ContentDisplay.RIGHT);
        configButton.setLayoutX(260);
        configButton.setPrefSize(40, 40);

        FontAwesomeIconView cog = new FontAwesomeIconView(FontAwesomeIcon.COG);
        cog.setFill(Paint.valueOf("CYAN"));
        cog.setLayoutX(265);
        cog.setSize("18");
        configButton.setGraphic(cog);
        configButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
          lastPluginInteracted = p;
          p.showConfig();
          pluginsPanelVisible = !pluginsPanelVisible;
        });
      }

      PluginToggleButton toggleButton = null;
      if (!p.getClass().getAnnotation(PluginDescriptor.class).cantDisable())
      {
        toggleButton = new PluginToggleButton(p);
        toggleButton.setSize(6);
        toggleButton.setLayoutX(290);

        toggleButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> p.toggle());

        toggleButton.setSelected(true);
        toggleButton.setStyle("-fx-text-fill: CYAN;");
      }

      Text pluginName = new Text();
      pluginName.setText(p.getClass().getAnnotation(PluginDescriptor.class).name());
      pluginName.setFill(Paint.valueOf("WHITE"));
      pluginName.setLayoutX(20);
      pluginName.setLayoutY(24);
      pluginName.setWrappingWidth(300);
      pluginName.setFont(Font.font(18));

      pluginPanel.getChildren().add(pluginName);
      pluginPanel.getChildren().add(configButton); //Order matters here! Very Important! uwu
      if (toggleButton != null)
      pluginPanel.getChildren().add(toggleButton);

      pluginList.getChildren().add(pluginPanel);
    }

    ScrollBar scrollBar = new ScrollBar();
    scrollBar.setOrientation(Orientation.VERTICAL);
    scrollBar.setMinSize(5, 4096);
    AnchorPane.setRightAnchor(scrollBar, 0.0);
    scrollBar.setMin(0);
    scrollBar.setMax(100);
    scrollBar.setValue(0);
    scrollBar.getStylesheets().add("css/plugins/jfx-scrollbar.css");
    scrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {
      if (fakeEvent)
      {
        fakeEvent = false;
        return;
      }
      configItemViewOffset =  newValue.doubleValue();
      configItemViewOffset = (configItemViewOffset - (configItemViewOffset *= 45));
      plug.setLayoutY(configItemViewOffset + 25);
      pluginsString.setLayoutY(configItemViewOffset + 28);
      pluginList.setLayoutY(configItemViewOffset + 45);
    });
    pluginPanel.getChildren().add(pluginList);
    pluginPanel.getChildren().add(scrollBar);

    pluginPanel.addEventHandler(ScrollEvent.SCROLL, (e) -> {
      configItemViewOffset = configItemViewOffset + e.getDeltaY();
      if (configItemViewOffset > 0)
        configItemViewOffset = 0;

      pluginList.setLayoutY(configItemViewOffset + 45);
      pluginsString.setLayoutY(configItemViewOffset + 28);
      plug.setLayoutY(configItemViewOffset + 25);

      fakeEvent = true;
      scrollBar.setValue((configItemViewOffset * -1) / 200);
    });
  }
}