package meteor.ui.controllers;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.lang.reflect.Method;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import meteor.MeteorLite;
import meteor.config.Config;
import meteor.config.ConfigDescriptor;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;
import meteor.config.ConfigItemDescriptor;
import meteor.config.ConfigManager;
import meteor.eventbus.events.ConfigChanged;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.ui.components.PluginToggleButton;
import net.runelite.api.mixins.Inject;
import org.sponge.util.Logger;

public class PluginConfig {

  Logger logger = new Logger("PluginConfigController");

  @FXML
  private AnchorPane pluginConfigPanel ;

  @FXML
  private VBox nodeList;

  private Plugin plugin;

  @Inject
  ConfigManager configManager;

  @FXML
  public void initialize() {
    MeteorLite.injector.injectMembers(this);
    plugin = PluginsFXMLController.lastPluginInteracted;

    ScrollPane scrollPane = new ScrollPane();
    scrollPane.setMinSize(350, 600);
    AnchorPane.setTopAnchor(scrollPane, 45.0);
    AnchorPane.setBottomAnchor(scrollPane, 5.0);

    FontAwesomeIconView plug = new FontAwesomeIconView(FontAwesomeIcon.PLUG);
    plug.setFill(Paint.valueOf("CYAN"));
    plug.setLayoutY(25);
    AnchorPane.setLeftAnchor(plug, 0.0);

    pluginConfigPanel.getChildren().add(plug);

    Text pluginsString = new Text();
    pluginsString.setText(plugin.getName());
    pluginsString.setFill(Paint.valueOf("WHITE"));
    pluginsString.setLayoutY(28);
    pluginsString.setWrappingWidth(300);
    pluginsString.setFont(Font.font(18));
    AnchorPane.setLeftAnchor(pluginsString, 25.0);

    pluginConfigPanel.getChildren().add(pluginsString);
    nodeList = new VBox();
    nodeList.setLayoutY(45);

    pluginConfigPanel.getChildren().add(nodeList);

    AnchorPane configPanel = new AnchorPane();
    configPanel.setStyle("-fx-border-color: #102027");
    configPanel.setPrefHeight(25);
    configPanel.setPrefWidth(280);

    configManager = MeteorLite.injector.getInstance(ConfigManager.class);
    Config config = plugin.getConfig(configManager);
    ConfigDescriptor descriptor = configManager.getConfigDescriptor(config);
    if (descriptor != null)
    {
      for (ConfigItemDescriptor configItemDescriptor : descriptor.getItems())
      {
        ConfigItem configItem = configItemDescriptor.getItem();
        logger.debug(configItem.name());
        AnchorPane nodePanel = createNode();
        if (configItemDescriptor.getType() == int.class)
        {
          createIntegerNode(nodePanel, configItemDescriptor);
        }
        if (configItemDescriptor.getType() == boolean.class)
        {
          createBooleanNode(config, nodePanel, configItemDescriptor);
        }
        if (nodePanel.getChildren().size() > 0)
        {
          nodeList.getChildren().add(nodePanel);
        }
      }
    }
  }

  private AnchorPane createNode()
  {
    AnchorPane node = new AnchorPane();
    node.setStyle("-fx-border-color: #102027");
    node.setPrefHeight(25);
    node.setPrefWidth(280);
    return node;
  }

  private void createBooleanNode(Config config, AnchorPane root, ConfigItemDescriptor descriptor) {
    Text name = new Text();
    name.setText(descriptor.name());
    name.setFill(Paint.valueOf("WHITE"));
    name.setLayoutX(20);
    name.setLayoutY(24);
    name.setWrappingWidth(300);
    name.setFont(Font.font(18));

    root.getChildren().add(name);

    PluginToggleButton toggleButton = new PluginToggleButton(plugin);
    toggleButton.setSize(4);
    toggleButton.setLayoutX(305);
    toggleButton.setLayoutY(8);

    toggleButton.addEventHandler(MouseEvent.MOUSE_PRESSED, (e) -> toggleBooleanConfig(config, descriptor));
    Object o = configManager.getConfiguration(configManager.getConfigDescriptor(config).getGroup().value(), descriptor.key(), descriptor.getType());
    boolean enabled = false;
    if (o instanceof Boolean)
    {
      enabled = (Boolean) o;
    }
    toggleButton.setSelected(enabled);
    toggleButton.setStyle("-fx-text-fill: CYAN;");

    root.getChildren().add(toggleButton);
  }

  private void createIntegerNode(AnchorPane root, ConfigItemDescriptor descriptor)
  {
    Text name = new Text();
    name.setText(descriptor.name());
    name.setFill(Paint.valueOf("WHITE"));
    name.setLayoutX(20);
    name.setLayoutY(24);
    name.setWrappingWidth(300);
    name.setFont(Font.font(18));

    root.getChildren().add(name);
  }

  private void toggleBooleanConfig(Config config, ConfigItemDescriptor descriptor)
  {
    Object o = configManager.getConfiguration(configManager.getConfigDescriptor(config).getGroup().value(), descriptor.key(), descriptor.getType());
    boolean enabled = false;
    if (o instanceof Boolean)
    {
      enabled = (Boolean) o;
      enabled = !enabled;
    }
    configManager.setConfiguration(configManager.getConfigDescriptor(config).getGroup().value(), descriptor.key(), enabled);

  }
}