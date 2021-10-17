package meteor.ui.client;

import com.jfoenix.controls.JFXTooltip;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Paint;
import lombok.Getter;
import meteor.MeteorLiteClientModule;
import meteor.config.Config;
import meteor.config.ConfigManager;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.ui.components.MeteorText;

public class PluginListCell extends AnchorPane {

	@Getter
	private PluginToggleButton toggleButton;

	@Getter
	private String pluginName;

	public PluginListCell(Plugin plugin, ConfigManager configManager) {
		setBackground(new Background(new BackgroundFill(Paint.valueOf("252525"), null, null)));
		pluginName = plugin.getName();
		ContextMenu contextMenu = new ContextMenu();
		contextMenu.setStyle("-fx-background-color: #121212;");
		if (!plugin.getClass().getAnnotation(PluginDescriptor.class).cantDisable()) {
			toggleButton = new PluginToggleButton(plugin);
			AnchorPane.setTopAnchor(toggleButton, 6.0);
			AnchorPane.setRightAnchor(toggleButton, 8.0);

			toggleButton.selectedProperty().addListener((options, oldValue, newValue) -> {
				if (newValue == plugin.isEnabled()) {
					return;
				}
				plugin.toggle();
			});

			toggleButton.setSelected(Boolean.parseBoolean(configManager.getConfiguration(plugin.getClass().getSimpleName(), "pluginEnabled")));
		}

		if (toggleButton != null) {
			toggleButton.selectedProperty().addListener((options, oldValue, newValue) -> {
				configManager.setConfiguration(plugin.getClass().getSimpleName(), "pluginEnabled", newValue);
			});
		}

		PluginConfigButton configButton = new PluginConfigButton(plugin);
		Config pluginConfig = plugin.getConfig(configManager);
		if (pluginConfig != null && pluginConfig.getClass().getDeclaredMethods().length > 4) {
			AnchorPane.setRightAnchor(configButton, 48.0);
			AnchorPane.setTopAnchor(configButton, 0.0);

			FontAwesomeIconView cog = new FontAwesomeIconView(FontAwesomeIcon.COG);
			cog.setFill(MeteorLiteClientModule.METEOR_FONT_COLOR);
			cog.setSize(String.valueOf(MeteorLiteClientModule.METEOR_FONT_SIZE));
			configButton.setGraphic(cog);
			configButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
				plugin.showConfig();
			});
		}

		MeteorText pluginName = new MeteorText(plugin);
		pluginName.setFill(Paint.valueOf("WHITE"));

		AnchorPane.setLeftAnchor(pluginName, 2.0);
		AnchorPane.setTopAnchor(pluginName, 0.0);

		if (plugin.getClass().getAnnotation(PluginDescriptor.class).description().length() > 0) {
			JFXTooltip tooltip = new JFXTooltip();
			tooltip.setText(plugin.getClass().getAnnotation(PluginDescriptor.class).description());

			pluginName.addEventHandler(MouseEvent.MOUSE_ENTERED, (e) -> {
				tooltip.showOnAnchors(pluginName, 0, -50);
			});

			pluginName.addEventHandler(MouseEvent.MOUSE_EXITED, (e) -> {
				tooltip.hide();
			});
		}

//		HBox.setHgrow(pluginName, Priority.ALWAYS);
		getChildren().addAll(pluginName, configButton);
		if (toggleButton != null) {
			getChildren().add(toggleButton);
		}

		pluginName.setOnContextMenuRequested(e -> {
			contextMenu.show(pluginName, e.getScreenX(), e.getScreenY());
		});


	}
}
