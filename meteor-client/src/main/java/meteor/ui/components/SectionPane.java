package meteor.ui.components;

import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class SectionPane {
	private final VBox container;
	private final TitledPane titledPane;
	private final String name;
	private final Text title;

	public SectionPane(String name) {
		this.name = name;

		container = new VBox();
		titledPane = new TitledPane();
		title = new Text(name);
		title.setFill(Paint.valueOf("AQUA"));
		title.setFont(Font.font(18));
		titledPane.setGraphic(title);

		titledPane.setContent(container);
		titledPane.getStylesheets().add("css/plugins/jfx-titledpane.css");
	}

	public Text getTitle() {
		return title;
	}

	public VBox getContainer() {
		return container;
	}

	public TitledPane getRootPane() {
		return titledPane;
	}

	public String getName() {
		return name;
	}
}
