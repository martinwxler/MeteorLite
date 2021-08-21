package meteor.ui.controllers;

import static meteor.MeteorLiteClientModule.pluginsPanelVisible;

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
import meteor.MeteorLiteClientLauncher;
import meteor.MeteorLiteClientModule;
import meteor.PluginManager;
import meteor.config.ConfigGroup;
import meteor.config.ConfigManager;
import meteor.eventbus.EventBus;
import meteor.eventbus.Subscribe;
import meteor.eventbus.events.ConfigChanged;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.ui.components.PluginConfigButton;
import meteor.ui.components.PluginToggleButton;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class PluginListUI {

  @FXML
  private AnchorPane pluginPanel;

  @FXML
  private VBox pluginList;

  public static Plugin lastPluginInteracted;

  private double configItemViewOffset = 0;

  boolean fakeEvent = false;

  public Map<String, PluginToggleButton> configGroupPluginMap = new HashMap<>();

  @Inject
  ConfigManager configManager;

  @Inject
  EventBus eventBus;

  @FXML
  public void initialize() {
    MeteorLiteClientModule.instanceInjectorStatic.injectMembers(this);
    eventBus.register(this);
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
      pluginPanel.setStyle("-fx-background-color: #212121; -fx-border-style: solid;  -fx-border-color: #121212; -fx-border-width: 1;");

      PluginConfigButton configButton = new PluginConfigButton(p);
      if (p.getConfig(MeteorLiteClientLauncher.mainClientInstance.instanceInjector.getInstance(ConfigManager.class)) != null)
      {
        if (p.getConfig(MeteorLiteClientLauncher.mainClientInstance.instanceInjector.getInstance(ConfigManager.class)).getClass().getDeclaredMethods().length > 4) {
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
      }

      PluginToggleButton toggleButton = null;
      if (!p.getClass().getAnnotation(PluginDescriptor.class).cantDisable())
      {
        toggleButton = new PluginToggleButton(p);
        toggleButton.setSize(6);
        toggleButton.setLayoutX(290);
        PluginToggleButton finalToggleButton = toggleButton;
        toggleButton.selectedProperty().addListener((options, oldValue, newValue) -> {
          if (newValue && finalToggleButton.plugin.isEnabled())
            return;
          if (!newValue && !finalToggleButton.plugin.isEnabled())
            return;
          p.toggle();
        });
        toggleButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> p.toggle());

        toggleButton.setStyle("-fx-text-fill: CYAN;");
        if (p.getConfig(configManager).getClass().getInterfaces()[0].getAnnotation(ConfigGroup.class) != null) {
          configGroupPluginMap.put(p.getConfig(configManager).getClass().getInterfaces()[0].getAnnotation(ConfigGroup.class).value(), toggleButton);
          toggleButton.setSelected(Boolean.parseBoolean(configManager.getConfiguration(p.getConfig(configManager)
                  .getClass().getInterfaces()[0].getAnnotation(ConfigGroup.class).value(), "pluginEnabled")));
        }
      }

      if (toggleButton != null)
      toggleButton.selectedProperty().addListener((options, oldValue, newValue) -> {
        if (p.getConfig(configManager).getClass().getInterfaces()[0].getAnnotation(ConfigGroup.class) != null) {
          configManager.setConfiguration(p.getConfig(configManager).getClass().getInterfaces()[0].getAnnotation(ConfigGroup.class).value(), "pluginEnabled", newValue);
        }
      });

      Text pluginName = new Text();
      pluginName.setText(p.getClass().getAnnotation(PluginDescriptor.class).name());
      pluginName.setFill(Paint.valueOf("WHITE"));
      AnchorPane.setLeftAnchor(pluginName, 10.0);
      AnchorPane.setTopAnchor(pluginName, 7.0);
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