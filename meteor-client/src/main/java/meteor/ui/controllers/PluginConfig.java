package meteor.ui.controllers;

import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.awt.Color;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import meteor.MeteorLite;
import meteor.config.Config;
import meteor.config.ConfigDescriptor;
import meteor.config.ConfigItem;
import meteor.config.ConfigItemDescriptor;
import meteor.config.ConfigManager;
import meteor.plugins.Plugin;
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
          if (configItemDescriptor.getRange() != null)
          {
            if (configItemDescriptor.getRange().textInput()) {
              createIntegerTextNode(descriptor, nodePanel, configItemDescriptor);
            } else {
              createIntegerSliderNode(descriptor, nodePanel, configItemDescriptor);
            }
          }
          else
            createIntegerSliderNode(descriptor, nodePanel, configItemDescriptor);
        }
        if (configItemDescriptor.getType() == boolean.class)
        {
          createBooleanNode(descriptor, nodePanel, configItemDescriptor);
        }
        if (configItemDescriptor.getType() == String.class)
        {
          createStringNode(descriptor, nodePanel, configItemDescriptor);
        }
        if (configItemDescriptor.getType() == Color.class)
        {
          createColorPickerNode(descriptor, nodePanel, configItemDescriptor);
        }
        if (nodePanel.getChildren().size() > 0)
        {
          nodeList.getChildren().add(nodePanel);
        }
      }
    }
  }

  private void createStringNode(ConfigDescriptor config, AnchorPane root, ConfigItemDescriptor descriptor) {
    Text name = new Text();
    name.setText(descriptor.name());
    name.setFill(Paint.valueOf("WHITE"));
    name.setLayoutX(20);
    name.setLayoutY(25);
    name.setWrappingWidth(300);
    name.setFont(Font.font(18));

    root.getChildren().add(name);

    JFXTextArea textArea = new JFXTextArea();
    textArea.setFont(Font.font(18));

    textArea.setWrapText(true);
    textArea.setText(configManager.getConfiguration(config.getGroup().value(), descriptor.key(), String.class));
    textArea.setMinSize(350, 200);
    textArea.setMaxSize(350, 200);
    textArea.setLayoutY(25);
    textArea.getStylesheets().add("css/plugins/jfx-textarea.css");
    textArea.textProperty().addListener((observable, oldValue, newValue) -> {
      setValue(config, descriptor, newValue);
    });

    root.getChildren().add(textArea);
  }

  private AnchorPane createNode()
  {
    AnchorPane node = new AnchorPane();
    node.setStyle("-fx-border-color: #102027");
    node.setPrefHeight(25);
    node.setPrefWidth(280);
    return node;
  }

  private void createBooleanNode(ConfigDescriptor config, AnchorPane root, ConfigItemDescriptor descriptor) {
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

    Object o = configManager.getConfiguration(config.getGroup().value(), descriptor.key(), descriptor.getType());
    boolean enabled = false;
    if (o instanceof Boolean)
    {
      enabled = (Boolean) o;
    }
    toggleButton.setSelected(enabled);
    toggleButton.setStyle("-fx-text-fill: CYAN;");
    toggleButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> setValue(config, descriptor, toggleButton.isSelected()));

    root.getChildren().add(toggleButton);
  }

  private void createIntegerSliderNode(ConfigDescriptor config, AnchorPane root, ConfigItemDescriptor descriptor)
  {
    Text name = new Text();
    name.setText(descriptor.name());
    name.setFill(Paint.valueOf("WHITE"));
    name.setLayoutX(20);
    name.setLayoutY(24);
    name.setWrappingWidth(300);
    name.setFont(Font.font(18));

    root.getChildren().add(name);

    JFXSlider slider = new JFXSlider();
    AnchorPane.setLeftAnchor(slider, 0.0);
    AnchorPane.setRightAnchor(slider, 0.0);
    slider.setMinSize(350, 35);
    slider.setLayoutY(35);
    slider.autosize();
    int min = 0;
    int max = Integer.MAX_VALUE;
    if (descriptor.getRange() != null)
    {
      min = descriptor.getRange().min();
      max = descriptor.getRange().max();
    }
    slider.setValue(configManager.getConfiguration(config.getGroup().value(), descriptor.key(), Double.class));
    slider.setMax(max);
    slider.setMin(min);
    slider.setMajorTickUnit(max / 4.0);
    slider.setMinorTickCount(2);
    slider.getStylesheets().add("css/plugins/jfx-slider.css");
    slider.setShowTickMarks(true);
    slider.setShowTickLabels(true);
    slider.addEventHandler(MouseEvent.MOUSE_DRAGGED, (e) -> setValue(config, descriptor, (int)slider.getValue()));

    root.getChildren().add(slider);
  }

  private void createIntegerTextNode(ConfigDescriptor config, AnchorPane root, ConfigItemDescriptor descriptor)
  {
    Text name = new Text();
    name.setText(descriptor.name());
    name.setFill(Paint.valueOf("WHITE"));
    name.setLayoutX(20);
    name.setLayoutY(24);
    name.setWrappingWidth(300);
    name.setFont(Font.font(18));

    root.getChildren().add(name);

    JFXTextField textField = new JFXTextField();

    AnchorPane.setLeftAnchor(textField, 200.0);
    AnchorPane.setRightAnchor(textField, 10.0);
    textField.setMinSize(150, 35);
    textField.setFont(Font.font(18));
    textField.setLayoutY(20);
    textField.autosize();
    textField.setText(configManager.getConfiguration(config.getGroup().value(), descriptor.key(), String.class));
    textField.addEventHandler(KeyEvent.KEY_TYPED, (e) ->
    {
      int min = 0;
      if (descriptor.getRange() != null)
      {
        min = descriptor.getRange().min();
      }
      if (isInputValid(descriptor, textField.getText()))
        setValue(config, descriptor, Integer.parseInt(textField.getText()));
      else
      {
        textField.setText("" + min);
        setValue(config, descriptor, min);
      }
    });
    textField.getStylesheets().add("css/plugins/jfx-textfield.css");

    root.getChildren().add(textField);
  }

  private void createColorPickerNode(ConfigDescriptor config, AnchorPane root, ConfigItemDescriptor descriptor)
  {
    Text name = new Text();
    name.setText(descriptor.name());
    name.setFill(Paint.valueOf("WHITE"));
    name.setLayoutX(20);
    name.setLayoutY(24);
    name.setWrappingWidth(300);
    name.setFont(Font.font(18));

    root.getChildren().add(name);

    JFXColorPicker colorPicker = new JFXColorPicker();
    AnchorPane.setLeftAnchor(colorPicker, 200.0);
    AnchorPane.setRightAnchor(colorPicker, 10.0);
    colorPicker.setMinSize(100, 35);
    colorPicker.setLayoutY(5);
    colorPicker.autosize();
    Color c = configManager.getConfiguration(config.getGroup().value(), descriptor.key(), Color.class);
    double r = c.getRed() / 255.0;
    double g = c.getGreen() / 255.0;
    double b = c.getBlue() / 255.0;
    double a = c.getAlpha() / 255.0;
    colorPicker.setValue(javafx.scene.paint.Color.color(r, g, b, a));
    colorPicker.valueProperty().addListener((observable, oldValue, newValue) -> {
      double nr = newValue.getRed() * 255.0;
      double ng = newValue.getGreen() * 255.0;
      double nb = newValue.getBlue() * 255.0;
      double na = newValue.getOpacity() * 255.0;
      Color colorToSet = new Color((int)nr, (int)ng, (int)nb, (int)na);
      setValue(config, descriptor, colorToSet);
    });

    root.getChildren().add(colorPicker);
  }

  private boolean isInputValid(ConfigItemDescriptor descriptor, String input)
  {
    int i;
    try {
      i = Integer.parseInt(input);
    }
    catch (Exception e)
    {
      e.printStackTrace();
      return false;
    }

    if (descriptor.getRange() == null)
    return true;

    if (descriptor.getRange().max() < i)
    return false;

    if (descriptor.getRange().min() > i)
    return false;

    return true;
  }

  private void setValue(ConfigDescriptor config, ConfigItemDescriptor descriptor, Object value)
  {
    configManager.setConfiguration(config.getGroup().value(), descriptor.key(), value);
  }
}