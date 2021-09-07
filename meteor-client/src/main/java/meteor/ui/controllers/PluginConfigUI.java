package meteor.ui.controllers;

import com.jfoenix.controls.*;
import com.sun.javafx.collections.ObservableListWrapper;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import meteor.MeteorLiteClientLauncher;
import meteor.MeteorLiteClientModule;
import meteor.config.Button;
import meteor.config.*;
import meteor.plugins.Plugin;
import meteor.ui.components.Category;
import meteor.ui.components.ConfigButton;
import meteor.ui.components.ConfigSectionPane;
import meteor.ui.components.PluginToggleButton;
import net.runelite.api.Client;
import net.runelite.api.events.ConfigButtonClicked;
import org.sponge.util.Logger;

import javax.inject.Inject;
import java.awt.*;
import java.io.IOException;
import java.util.List;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import static java.awt.event.KeyEvent.getExtendedKeyCodeForChar;
import static meteor.ui.controllers.PluginListUI.lastPluginInteracted;
import static meteor.ui.controllers.PluginListUI.pluginPanels;

public class PluginConfigUI {

	private static final Logger logger = new Logger("PluginConfigController");

	@FXML
	private AnchorPane rootPanel;

	@FXML
	private AnchorPane titlePanel;

	@FXML
	private VBox configList;

	@FXML
	private Text pluginTitle;

	private final Map<String, ConfigSectionPane> sections = new HashMap<>();

	private Plugin plugin;

	@Inject
	private Client client;

	@Inject
	private ConfigManager configManager;

	private PluginToggleButton toggleButton;

	@FXML
	public void initialize() {
		MeteorLiteClientModule.instanceInjectorStatic.injectMembers(this);
		plugin = lastPluginInteracted;
		pluginTitle.setText(plugin.getName());
		configManager = MeteorLiteClientLauncher.mainClientInstance.instanceInjector.getInstance(ConfigManager.class);

		toggleButton = PluginListUI.configGroupPluginMap
						.get(plugin.getConfig(configManager).getClass().getInterfaces()[0].getAnnotation(ConfigGroup.class).value());
		if (toggleButton != null) {
			titlePanel.getChildren().add(toggleButton);
		}

		initSections();
		initConfigs();
	}

	private void initSections() {
		Config config = plugin.getConfig(configManager);
		ConfigDescriptor descriptor = configManager.getConfigDescriptor(config);

		for (ConfigSectionDescriptor csd : descriptor.getSections().stream()
						.sorted(Comparator.comparingInt(ConfigSectionDescriptor::position))
						.collect(Collectors.toList())) {
			ConfigSection section = csd.getSection();
			ConfigSectionPane sectionBox = createSection(section);
			configList.getChildren().add(sectionBox.getTitledPane());
			sectionBox.getTitledPane().setExpanded(!section.closedByDefault());
		}
	}

	private void initConfigs() {
		Config config = plugin.getConfig(configManager);
		ConfigDescriptor descriptor = configManager.getConfigDescriptor(config);

		if (descriptor != null) {
			for (ConfigItemDescriptor configItemDescriptor : descriptor.getItems().stream()
							.sorted(Comparator.comparingInt(ConfigItemDescriptor::position))
							.collect(Collectors.toList())) {
				ConfigSectionPane sectionBox = sections.get(configItemDescriptor.getItem().section());
				Pane configContainer = sectionBox != null ? sectionBox.getContainer() : createNode();

				if (configItemDescriptor.getType() == int.class) {
					if (configItemDescriptor.getRange() != null) {
						if (configItemDescriptor.getRange().textInput()) {
							createIntegerTextNode(descriptor, configContainer, configItemDescriptor);
						} else {
							createIntegerSliderNode(descriptor, configContainer, configItemDescriptor);
						}
					} else {
						createIntegerSliderNode(descriptor, configContainer, configItemDescriptor);
					}
				}
				if (configItemDescriptor.getType() == boolean.class) {
					createBooleanNode(descriptor, configContainer, configItemDescriptor);
				}
				if (configItemDescriptor.getType() == String.class) {
					createStringNode(descriptor, configContainer, configItemDescriptor);
				}
				if (configItemDescriptor.getType() == Color.class) {
					createColorPickerNode(descriptor, configContainer, configItemDescriptor);
				}
				if (configItemDescriptor.getType() == double.class) {
					createdDoubleTextNode(descriptor, configContainer, configItemDescriptor);
				}
				if (configItemDescriptor.getType() == Button.class) {
					createButtonNode(descriptor, configContainer, configItemDescriptor);
				}
				if (configItemDescriptor.getType() == ModifierlessKeybind.class) {
					createHotKeyNode(descriptor, configContainer, configItemDescriptor);
				}
				if (configItemDescriptor.getType() == Keybind.class) {
					createDefaultKeyBindNode(descriptor, configContainer, configItemDescriptor);
				}
				if (configItemDescriptor.getType().isEnum()) {
					createEnumNode(descriptor, configContainer, configItemDescriptor);
				}
				if (!configContainer.getChildren().isEmpty() && !configList.getChildren().contains(configContainer)) {
					configList.getChildren().add(configContainer);
				}
			}
		}
	}

	private void createButtonNode(ConfigDescriptor config, Pane root, ConfigItemDescriptor configItem) {
		ConfigButton button = new ConfigButton(configItem.getIcon().canToggle());

		AnchorPane.setTopAnchor(button, 4.0);
		AnchorPane.setBottomAnchor(button, 4.0);
		AnchorPane.setRightAnchor(button, 40.0);
		AnchorPane.setLeftAnchor(button, 40.0);
		AtomicReference<FontAwesomeIcon> icon = new AtomicReference<>();

		if (button.isToggleable()) {
			if (button.isToggled()) {
				icon.set(configItem.getIcon().stop());
				button.setText("Stop");
			} else {
				icon.set(configItem.getIcon().start());
				button.setText("Start");
			}
		} else {
			icon.set(configItem.getIcon().value());
			button.setText(configItem.name());
		}

		FontAwesomeIconView buttonIcon = new FontAwesomeIconView(icon.get());
		buttonIcon.setSize("16");
		buttonIcon.setFill(javafx.scene.paint.Color.valueOf("CYAN"));
		button.setGraphic(buttonIcon);
		button.autosize();
		button.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");

		button.pressedProperty().addListener((options, oldValue, pressed) -> {
			if (!pressed) {
				return;
			}

			button.toggle();

			if (button.isToggleable()) {
				if (button.isToggled()) {
					icon.set(configItem.getIcon().start());
					button.setText("Start");
					client.getCallbacks().post(new ConfigButtonClicked(config.getGroup().value(), configItem.key()));
					return;
				}

				icon.set(configItem.getIcon().stop());
				button.setText("Stop");
				client.getCallbacks().post(new ConfigButtonClicked(config.getGroup().value(), configItem.key()));
				return;
			}

			client.getCallbacks().post(new ConfigButtonClicked(config.getGroup().value(), configItem.key()));
			icon.set(configItem.getIcon().value());
		});

		addConfigItemComponents(root, button);
	}

	private void createHotKeyNode(ConfigDescriptor config, Pane root, ConfigItemDescriptor configItem) {
		Text name = createText(configItem.name(), Paint.valueOf("WHITE"), configItem.getItem().description());
		AnchorPane.setTopAnchor(name, 8.0);
		AnchorPane.setLeftAnchor(name, 8.0);

		ConfigButton button = new ConfigButton(false);;
		AnchorPane.setTopAnchor(button, 4.0);
		AnchorPane.setBottomAnchor(button, 4.0);
		AnchorPane.setRightAnchor(button, 0.0);
		AnchorPane.setLeftAnchor(button, 190.0);
		AtomicReference<FontAwesomeIcon> icon = new AtomicReference<>();

		button.setText(configManager.getConfiguration(config.getGroup().value(), configItem.key(), ModifierlessKeybind.class).toString());
		button.autosize();
		button.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");

		button.pressedProperty().addListener((options, oldValue, pressed) -> {
			if (!pressed) {
				return;
			}

			button.setText("Press any key...");
			EventHandler<KeyEvent> keyListener = (e) -> {
				configManager.setConfiguration(config.getGroup().value(), configItem.key(), new ModifierlessKeybind(getExtendedKeyCodeForChar(e.getCharacter().charAt(0)),
						0));
				button.setText(e.getCharacter().toUpperCase());
			};
			EventHandler<KeyEvent> unregisterListener = (e) -> {
				button.removeEventHandler(KeyEvent.KEY_TYPED, keyListener);
			};
			button.addEventHandler(KeyEvent.KEY_TYPED, keyListener);
			button.addEventHandler(KeyEvent.KEY_TYPED, unregisterListener);
		});
		addConfigItemComponents(root, name, button);
	}

	private void createDefaultKeyBindNode(ConfigDescriptor config, Pane root, ConfigItemDescriptor configItem) {
		Text name = createText(configItem.name(), Paint.valueOf("WHITE"), configItem.getItem().description());
		AnchorPane.setTopAnchor(name, 8.0);
		AnchorPane.setLeftAnchor(name, 8.0);

		ConfigButton button = new ConfigButton(false);;
		AnchorPane.setTopAnchor(button, 4.0);
		AnchorPane.setBottomAnchor(button, 4.0);
		AnchorPane.setRightAnchor(button, 0.0);
		AnchorPane.setLeftAnchor(button, 190.0);
		AtomicReference<FontAwesomeIcon> icon = new AtomicReference<>();

		button.setText(configManager.getConfiguration(config.getGroup().value(), configItem.key(), Keybind.class).toString());
		button.autosize();
		button.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");

		button.pressedProperty().addListener((options, oldValue, pressed) -> {
			if (!pressed) {
				return;
			}

			button.setText("Press any key...");
			EventHandler<KeyEvent> keyListener = (e) -> {
				configManager.setConfiguration(config.getGroup().value(), configItem.key(), new ModifierlessKeybind(getExtendedKeyCodeForChar(e.getCharacter().charAt(0)),
						0));
				button.setText(e.getCharacter().toUpperCase());
			};
			EventHandler<KeyEvent> unregisterListener = (e) -> {
				button.removeEventHandler(KeyEvent.KEY_TYPED, keyListener);
			};
			button.addEventHandler(KeyEvent.KEY_TYPED, keyListener);
			button.addEventHandler(KeyEvent.KEY_TYPED, unregisterListener);
		});
		addConfigItemComponents(root, name, button);
	}

	private void createEnumNode(ConfigDescriptor config, Pane root, ConfigItemDescriptor configItem) {
		Text name = createText(configItem.name(), Paint.valueOf("WHITE"), configItem.getItem().description());
		AnchorPane.setTopAnchor(name, 8.0);
		AnchorPane.setLeftAnchor(name, 8.0);

		Class<? extends Enum> type = (Class<? extends Enum>) configItem.getType();
		Enum<?> currentConfigEnum = Enum.valueOf(type, configManager.getConfiguration(config.getGroup().value(), configItem.key()));
		List<Enum<?>> list = new ArrayList<>();
		Enum<?> currentToSet = null;
		for (Enum<?> o : type.getEnumConstants()) {
			if (o.equals(currentConfigEnum))
				currentToSet = o;
			list.add(o);
		}
		ObservableList<Enum<?>> observableList = new ObservableListWrapper<>(list);
		JFXComboBox<Enum<?>> comboBox = new JFXComboBox<>(observableList);
		comboBox.setValue(currentToSet);

		AnchorPane.setLeftAnchor(comboBox, 200.0);
		AnchorPane.setRightAnchor(comboBox, 8.0);
		AnchorPane.setTopAnchor(comboBox, 8.0);
		AnchorPane.setBottomAnchor(comboBox, 8.0);

		comboBox.getStylesheets().add("css/plugins/jfx-combobox.css");
		comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			updateConfigItemValue(config, configItem, newValue.name());
		});

		addConfigItemComponents(root, name, comboBox);
	}

	private void createdDoubleTextNode(ConfigDescriptor config, Pane root, ConfigItemDescriptor configItem) {
		Text name = createText(configItem.name(), Paint.valueOf("WHITE"), configItem.getItem().description());
		AnchorPane.setTopAnchor(name, 8.0);
		AnchorPane.setLeftAnchor(name, 8.0);

		JFXTextField textField = new JFXTextField();

		AnchorPane.setLeftAnchor(textField, 200.0);
		AnchorPane.setRightAnchor(textField, 8.0);

		textField.setFont(Font.font(18));
		textField.setText(configManager.getConfiguration(config.getGroup().value(), configItem.key(), String.class));
		textField.addEventHandler(KeyEvent.KEY_TYPED, (e) ->
		{
			if (isInputValidDouble(configItem, textField.getText()))
				updateConfigItemValue(config, configItem, Double.parseDouble(textField.getText()));
			else {
				textField.clear();
				textField.setText("0.0");
				updateConfigItemValue(config, configItem, 0.0);
			}
		});
		textField.getStylesheets().add("css/plugins/jfx-textfield.css");

		addConfigItemComponents(root, name, textField);
	}

	private void createStringNode(ConfigDescriptor config, Pane root, ConfigItemDescriptor descriptor) {
		Text name = createText(descriptor.name(), Paint.valueOf("WHITE"), descriptor.getItem().description());
		AnchorPane.setLeftAnchor(name, 8.0);
		AnchorPane.setTopAnchor(name, 8.0);

		JFXTextArea textArea = new JFXTextArea();
		AnchorPane.setLeftAnchor(textArea, 8.0);
		AnchorPane.setRightAnchor(textArea, 8.0);
		AnchorPane.setTopAnchor(textArea, 34.0);
		AnchorPane.setBottomAnchor(textArea, 8.0);
		textArea.setFont(Font.font(18));

		textArea.setWrapText(true);
		textArea.setText(configManager.getConfiguration(config.getGroup().value(), descriptor.key(), String.class));
		textArea.getStylesheets().add("css/plugins/jfx-textarea.css");
		textArea.setStyle("-jfx-focus-color: CYAN;");
		textArea.textProperty().addListener((observable, oldValue, newValue) -> updateConfigItemValue(config, descriptor, newValue));

		addConfigItemComponents(root, name, textArea);
	}


	private void createBooleanNode(ConfigDescriptor config, Pane root, ConfigItemDescriptor descriptor) {
		Text name = createText(descriptor.name(), Paint.valueOf("WHITE"), descriptor.getItem().description());
		AnchorPane.setLeftAnchor(name, 8.0);
		AnchorPane.setTopAnchor(name, 8.0);

		PluginToggleButton toggleButton = new PluginToggleButton(plugin);
		AnchorPane.setTopAnchor(toggleButton, 2.0);
		AnchorPane.setBottomAnchor(toggleButton, 2.0);
		AnchorPane.setRightAnchor(toggleButton, 8.0);
		toggleButton.setSize(6);

		Object o = configManager.getConfiguration(config.getGroup().value(), descriptor.key(), descriptor.getType());
		boolean enabled = false;
		if (o instanceof Boolean) {
			enabled = (Boolean) o;
		}
		toggleButton.setSelected(enabled);
		toggleButton.setStyle("-fx-text-fill: CYAN;");
		toggleButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> updateConfigItemValue(config, descriptor, toggleButton.isSelected()));

		addConfigItemComponents(root, name, toggleButton);
	}

	private void createIntegerSliderNode(ConfigDescriptor config, Pane root, ConfigItemDescriptor descriptor) {
		Text name = createText(descriptor.name(), Paint.valueOf("WHITE"), descriptor.getItem().description());
		AnchorPane.setTopAnchor(name, 8.0);
		AnchorPane.setLeftAnchor(name, 8.0);

		JFXSlider slider = new JFXSlider();
		AnchorPane.setLeftAnchor(slider, 8.0);
		AnchorPane.setTopAnchor(slider, 34.0);
		AnchorPane.setRightAnchor(slider, 8.0);

		int min = 0;
		int max = Integer.MAX_VALUE;
		if (descriptor.getRange() != null) {
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
		slider.addEventHandler(MouseEvent.MOUSE_DRAGGED, (e) -> updateConfigItemValue(config, descriptor, (int) slider.getValue()));

		addConfigItemComponents(root, name, slider);
	}

	private void createIntegerTextNode(ConfigDescriptor config, Pane root, ConfigItemDescriptor descriptor) {
		Text name = createText(descriptor.name(), Paint.valueOf("WHITE"), descriptor.getItem().description());
		AnchorPane.setTopAnchor(name, 8.0);
		AnchorPane.setLeftAnchor(name, 8.0);

		JFXTextField textField = new JFXTextField();
		AnchorPane.setTopAnchor(textField, 8.0);
		AnchorPane.setBottomAnchor(textField, 8.0);
		AnchorPane.setLeftAnchor(textField, 200.0);
		AnchorPane.setRightAnchor(textField, 8.0);

		textField.setFont(Font.font(18));
		textField.setText(configManager.getConfiguration(config.getGroup().value(), descriptor.key(), String.class));
		textField.addEventHandler(KeyEvent.KEY_TYPED, (e) ->
		{
			int min = 0;
			if (descriptor.getRange() != null) {
				min = descriptor.getRange().min();
			}

			if (isInputValidInteger(descriptor, textField.getText())) {
				updateConfigItemValue(config, descriptor, Integer.parseInt(textField.getText()));
			} else {
				textField.setText("" + min);
				updateConfigItemValue(config, descriptor, min);
			}
		});
		textField.setStyle("-jfx-focus-color: CYAN;");
		textField.getStylesheets().add("css/plugins/jfx-textfield.css");

		addConfigItemComponents(root, name, textField);
	}

	private void createColorPickerNode(ConfigDescriptor config, Pane root, ConfigItemDescriptor configItem) {
		Text name = createText(configItem.name(), Paint.valueOf("WHITE"), configItem.getItem().description());
		AnchorPane.setTopAnchor(name, 8.0);
		AnchorPane.setLeftAnchor(name, 8.0);

		JFXColorPicker colorPicker = new JFXColorPicker();
		AnchorPane.setTopAnchor(colorPicker, 8.0);
		AnchorPane.setBottomAnchor(colorPicker, 8.0);
		AnchorPane.setLeftAnchor(colorPicker, 200.0);
		AnchorPane.setRightAnchor(colorPicker, 8.0);

		Color c = configManager.getConfiguration(config.getGroup().value(), configItem.key(), Color.class);
		if (c == null) {
			logger.warn(config.getGroup().value() + ":" + configItem.key() + " color can't be null");
			c = Color.WHITE;
		}
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
			Color colorToSet = new Color((int) nr, (int) ng, (int) nb, (int) na);
			updateConfigItemValue(config, configItem, colorToSet);
		});

		addConfigItemComponents(root, name, colorPicker);
	}

	private boolean isInputValidInteger(ConfigItemDescriptor descriptor, String input) {
		int i;
		try {
			i = Integer.parseInt(input);
		} catch (Exception e) {
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

	private boolean isInputValidDouble(ConfigItemDescriptor descriptor, String input) {
		double d;
		try {
			d = Double.parseDouble(input);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		if (descriptor.getRange() == null)
			return true;

		if (descriptor.getRange().max() < Double.MIN_VALUE)
			return false;

		if (descriptor.getRange().min() > Double.MAX_VALUE)
			return false;

		return true;
	}

	private void updateConfigItemValue(ConfigDescriptor config, ConfigItemDescriptor configItem, Object value) {
		configManager.setConfiguration(config.getGroup().value(), configItem.key(), value);
		lastPluginInteracted.updateConfig();
	}

	private AnchorPane createNode() {
		AnchorPane node = new AnchorPane();
		node.setStyle("-fx-background-color: #212121; -fx-border-style: solid;  -fx-border-color: #121212; -fx-border-width: 1;");
		return node;
	}

	public ConfigSectionPane createSection(ConfigSection configSection) {
		ConfigSectionPane section = new ConfigSectionPane(configSection.name());
		sections.put(section.getName(), section);
		return section;
	}

	private void addConfigItemComponents(Pane root, Node... nodes) {
		if (root instanceof VBox) {
			Pane pane = createNode();
			pane.getChildren().addAll(nodes);
			root.getChildren().add(pane);
		} else {
			root.getChildren().addAll(nodes);
		}
	}

	private Text createText(String text, Paint color, String tooltipText) {
		Text label = new Text();
		label.setText(text);
		label.setFill(color);
		label.setFont(Font.font(18));

		if (tooltipText != null) {
			JFXTooltip tooltip = new JFXTooltip();
			tooltip.setText(tooltipText);

			label.addEventHandler(MouseEvent.MOUSE_ENTERED, (e) -> {
				if (!tooltip.isShowing() && tooltip.getText().length() > 0) {
					tooltip.showOnAnchors(label, 0, -50);
				}
			});

			label.addEventHandler(MouseEvent.MOUSE_EXITED, (e) -> {
				if (tooltip.isShowing()) {
					tooltip.hide();
				}
			});
		}

		return label;
	}

	@FXML
	protected void closeConfig(MouseEvent event) throws IOException {
		PluginListUI.INSTANCE.refreshPlugins();
		MeteorLiteClientModule.showPlugins();
	}
}
