package meteor.ui.controllers;

import static meteor.MeteorLiteClientModule.mainWindow;
import static meteor.ui.controllers.PluginListUI.lastPluginInteracted;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXSlider;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTooltip;
import com.sun.javafx.collections.ObservableListWrapper;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

import java.awt.Color;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import javax.inject.Inject;

import meteor.MeteorLiteClientLauncher;
import meteor.MeteorLiteClientModule;
import meteor.config.*;
import meteor.plugins.Plugin;
import meteor.ui.components.ConfigButton;
import meteor.ui.components.PluginToggleButton;
import net.runelite.api.Client;
import net.runelite.api.events.ConfigButtonClicked;
import org.sponge.util.Logger;

public class PluginConfigUI {

	Logger logger = new Logger("PluginConfigController");

	@FXML
	private AnchorPane pluginConfigPanel;

	@FXML
	private VBox nodeList;

	private Map<String, VBox> sections = new HashMap<>();

	private Plugin plugin;

	@Inject
	Client client;

	@Inject
	ConfigManager configManager;

	private double configItemViewOffset = 0;
	AnchorPane configPanel;
	boolean fakeEvent = false;

	@FXML
	public void initialize() {
		MeteorLiteClientModule.instanceInjectorStatic.injectMembers(this);
		plugin = lastPluginInteracted;

		FontAwesomeIconView plug = new FontAwesomeIconView(FontAwesomeIcon.PLUG);
		plug.setFill(Paint.valueOf("CYAN"));
		plug.setLayoutY(25);
		AnchorPane.setLeftAnchor(plug, 10.0);

		pluginConfigPanel.getChildren().add(plug);
		pluginConfigPanel.setMinHeight(8000);

		Text pluginsString = new Text();
		pluginsString.setText(plugin.getName());
		pluginsString.setFill(Paint.valueOf("WHITE"));
		pluginsString.setLayoutY(28);
		pluginsString.setWrappingWidth(300);
		pluginsString.setFont(Font.font(18));
		AnchorPane.setLeftAnchor(pluginsString, 35.0);

		pluginConfigPanel.getChildren().add(pluginsString);
		nodeList = new VBox();
		nodeList.setLayoutY(45);

		configPanel = new AnchorPane();
		configPanel.setStyle("-fx-border-color: #102027");
		configPanel.setPrefHeight(25);
		configPanel.setPrefWidth(280);

		configManager = MeteorLiteClientLauncher.mainClientInstance.instanceInjector.getInstance(ConfigManager.class);
		Config config = plugin.getConfig(configManager);
		ConfigDescriptor descriptor = configManager.getConfigDescriptor(config);
		if (descriptor != null) {
			for (ConfigSectionDescriptor csd : descriptor.getSections().stream()
							.sorted(Comparator.comparingInt(ConfigSectionDescriptor::position))
							.collect(Collectors.toList())) {
				ConfigSection section = csd.getSection();
				VBox sectionBox = createSection(section);
				sections.put(section.name(), sectionBox);
				nodeList.getChildren().add(sectionBox);
			}

			for (ConfigItemDescriptor configItemDescriptor : descriptor.getItems().stream()
							.sorted(Comparator.comparingInt(ConfigItemDescriptor::position))
							.collect(Collectors.toList())) {
				VBox sectionBox = sections.get(configItemDescriptor.getItem().section());
				Pane configContainer = sectionBox != null ? sectionBox : createNode();

				AnchorPane.setRightAnchor(configContainer, 2.0);
				AnchorPane.setLeftAnchor(configContainer, 2.0);
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
				if (configItemDescriptor.getType().isEnum()) {
					createEnumNode(descriptor, configContainer, configItemDescriptor);
				}
				if (!configContainer.getChildren().isEmpty() && !nodeList.getChildren().contains(configContainer)) {
					nodeList.getChildren().add(configContainer);
				}
			}
		}

		pluginConfigPanel.getChildren().add(nodeList);

		ScrollBar scrollBar = new ScrollBar();
		scrollBar.setOrientation(Orientation.VERTICAL);
		scrollBar.setMinSize(5, mainWindow.getHeight());
		AnchorPane.setRightAnchor(scrollBar, 0.0);
		scrollBar.setMin(0);
		scrollBar.setMax(100);
		scrollBar.setValue(0);
		scrollBar.getStylesheets().add("css/plugins/jfx-scrollbar.css");

		scrollBar.valueProperty().addListener((observable, oldValue, newValue) -> {
			if (fakeEvent) {
				fakeEvent = false;
				return;
			}

			configItemViewOffset = newValue.doubleValue();
			configItemViewOffset = (configItemViewOffset - (configItemViewOffset *= 45));
			nodeList.setLayoutY(configItemViewOffset + 45);
			pluginsString.setLayoutY(configItemViewOffset + 28);
			plug.setLayoutY(configItemViewOffset + 25);
		});

		pluginConfigPanel.getChildren().add(scrollBar);
		pluginConfigPanel.addEventHandler(ScrollEvent.SCROLL, (e) -> {
			configItemViewOffset = configItemViewOffset + e.getDeltaY();
			if (configItemViewOffset > 0)
				configItemViewOffset = 0;

			nodeList.setLayoutY(configItemViewOffset + 45);
			pluginsString.setLayoutY(configItemViewOffset + 28);
			plug.setLayoutY(configItemViewOffset + 25);

			fakeEvent = true;
			scrollBar.setValue((configItemViewOffset * -1) / 200);
		});
	}

	private void createButtonNode(ConfigDescriptor config, Pane root, ConfigItemDescriptor configItem) {
		root.setMinSize(350, 56);

		ConfigButton button = new ConfigButton(configItem.getIcon().canToggle());
		button.setMinSize(100, 50);
		AnchorPane.setTopAnchor(button, 2.0);
		AnchorPane.setRightAnchor(button, 125.0);
		AnchorPane.setLeftAnchor(button, 125.0);
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
		buttonIcon.setSize("11");
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

	private void createEnumNode(ConfigDescriptor config, Pane root, ConfigItemDescriptor configItem) {
		Text name = createText(configItem.name(), Paint.valueOf("WHITE"), configItem.getItem().description());

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
		comboBox.setMinSize(150, 15);
		AnchorPane.setLeftAnchor(comboBox, 200.0);
		AnchorPane.setRightAnchor(comboBox, 10.0);
		AnchorPane.setBottomAnchor(comboBox, 2.0);
		comboBox.autosize();
		comboBox.getStylesheets().add("css/plugins/jfx-combobox.css");
		comboBox.getSelectionModel().selectedItemProperty().addListener((options, oldValue, newValue) -> {
			updateConfigItemValue(config, configItem, newValue.name());
		});

		addConfigItemComponents(root, name, comboBox);
	}

	private void createdDoubleTextNode(ConfigDescriptor config, Pane root, ConfigItemDescriptor configItem) {
		Text name = createText(configItem.name(), Paint.valueOf("WHITE"), configItem.getItem().description());

		JFXTextField textField = new JFXTextField();

		AnchorPane.setLeftAnchor(textField, 200.0);
		AnchorPane.setRightAnchor(textField, 10.0);
		textField.setMinSize(150, 15);
		textField.setFont(Font.font(18));
		textField.autosize();
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
		AnchorPane.setBottomAnchor(name, 150.0);

		JFXTextArea textArea = new JFXTextArea();
		AnchorPane.setLeftAnchor(textArea, 15.0);
		AnchorPane.setRightAnchor(textArea, 25.0);
		AnchorPane.setBottomAnchor(textArea, 2.0);
		textArea.setFont(Font.font(18));

		textArea.setWrapText(true);
		textArea.setText(configManager.getConfiguration(config.getGroup().value(), descriptor.key(), String.class));
		textArea.setMaxSize(305, 150);
		textArea.setLayoutY(45);
		textArea.getStylesheets().add("css/plugins/jfx-textarea.css");
		textArea.setStyle("-jfx-focus-color: CYAN;");
		textArea.textProperty().addListener((observable, oldValue, newValue) -> updateConfigItemValue(config, descriptor, newValue));

		addConfigItemComponents(root, name, textArea);
	}


	private void createBooleanNode(ConfigDescriptor config, Pane root, ConfigItemDescriptor descriptor) {
		Text name = createText(descriptor.name(), Paint.valueOf("WHITE"), 30, descriptor.getItem().description());

		PluginToggleButton toggleButton = new PluginToggleButton(plugin);
		toggleButton.setSize(6);
		toggleButton.setLayoutX(305);
		toggleButton.setLayoutY(8);

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
		AnchorPane.setTopAnchor(name, 5.0);

		JFXSlider slider = new JFXSlider();
		AnchorPane.setLeftAnchor(slider, 15.0);
		AnchorPane.setRightAnchor(slider, 25.0);
		slider.setMinSize(305, 35);
		slider.setLayoutY(35);
		slider.autosize();
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
		root.setMinSize(350, 25);
		root.setMinSize(0, 35);
		Text name = createText(descriptor.name(), Paint.valueOf("WHITE"), 30, descriptor.getItem().description());
		root.getChildren().add(name);

		JFXTextField textField = new JFXTextField();
		AnchorPane.setTopAnchor(textField, 0.0);
		AnchorPane.setBottomAnchor(textField, 0.0);
		AnchorPane.setLeftAnchor(textField, 200.0);
		AnchorPane.setRightAnchor(textField, 10.0);
		textField.setMaxSize(150, 15);
		textField.setFont(Font.font(18));
		textField.setText(configManager.getConfiguration(config.getGroup().value(), descriptor.key(), String.class));
		textField.addEventHandler(KeyEvent.KEY_TYPED, (e) ->
		{
			int min = 0;
			if (descriptor.getRange() != null) {
				min = descriptor.getRange().min();
			}
			if (isInputValidInteger(descriptor, textField.getText()))
				updateConfigItemValue(config, descriptor, Integer.parseInt(textField.getText()));
			else {
				textField.setText("" + min);
				updateConfigItemValue(config, descriptor, min);
			}
		});
		textField.setStyle("-jfx-focus-color: CYAN;");
		textField.getStylesheets().add("css/plugins/jfx-textfield.css");

		addConfigItemComponents(root, textField);
	}

	private void createColorPickerNode(ConfigDescriptor config, Pane root, ConfigItemDescriptor configItem) {
		Text name = createText(configItem.name(), Paint.valueOf("WHITE"), configItem.getItem().description());

		JFXColorPicker colorPicker = new JFXColorPicker();
		AnchorPane.setLeftAnchor(colorPicker, 200.0);
		AnchorPane.setRightAnchor(colorPicker, 15.0);
		colorPicker.setMinSize(50, 35);
		colorPicker.autosize();
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
		node.setPrefHeight(25);
		node.setPrefWidth(280);
		return node;
	}

	public VBox createSection(ConfigSection configSection) {
		VBox sectionBox = new VBox();
		Text name = createText(configSection.name(), Paint.valueOf("CYAN"), 30, configSection.description());
		sectionBox.setStyle("-fx-border-style: solid;  -fx-border-color: #8dcecc; -fx-border-width: 2;");
		sectionBox.getChildren().add(name);
		return sectionBox;
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
		return createText(text, color, 18, 18, tooltipText);
	}

	private Text createText(String text, Paint color, int layoutX, int layoutY, String tooltipText) {
		Text label = new Text();
		label.setText(text);
		label.setFill(color);
		label.setLayoutX(layoutX);
		label.setLayoutY(layoutY);
		label.setWrappingWidth(300);
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

	private Text createText(String text, Paint color, int layoutY, String tooltipText) {
		return createText(text, color, 18, layoutY, tooltipText);
	}

	private Text createText(String text, Paint color, int layoutY) {
		return createText(text, color, 18, layoutY, null);
	}

	private Text createText(String text, Paint color) {
		return createText(text, color, 18, 18, null);
	}

	private Text createText(String text, Paint color, int layoutX, int layoutY) {
		return createText(text, color, layoutX, layoutY, null);
	}
}
