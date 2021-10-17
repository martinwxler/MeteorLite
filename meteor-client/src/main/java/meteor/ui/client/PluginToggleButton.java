package meteor.ui.client;

import com.jfoenix.controls.JFXToggleButton;
import javafx.scene.control.ContentDisplay;
import meteor.eventbus.Subscribe;
import meteor.eventbus.events.PluginChanged;
import meteor.plugins.Plugin;

public class PluginToggleButton extends JFXToggleButton {

	private final Plugin plugin;

	public PluginToggleButton(Plugin plugin) {
		this.plugin = plugin;
		setStyle("-fx-text-fill: CYAN;");
		setSize(5);
		setMinHeight(0);
		setPrefHeight(12);
		contentDisplayProperty().set(ContentDisplay.GRAPHIC_ONLY);
	}

	@Subscribe
	public void onPluginChanged(PluginChanged e) {
		if (e.getPlugin().equals(plugin)) {
			setSelected(plugin.isEnabled());
		}
	}
}
