package meteor.ui.components;

import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

public class ConfigSectionPane {
	private final VBox container;
	private final TitledPane titledPane;
	private final String name;

	public ConfigSectionPane(String name) {
		this.name = name;

		container = new VBox();
		titledPane = new TitledPane(name, container);
		titledPane.getStylesheets().add("css/plugins/jfx-titledpane.css");
	}

	public VBox getContainer() {
		return container;
	}

	public TitledPane getTitledPane() {
		return titledPane;
	}

	public String getName() {
		return name;
	}
}
