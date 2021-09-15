package meteor.ui.components;

import javafx.scene.Node;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class Category {
	private final SectionPane sectionPane;
	public String name;
	public ArrayList<String> plugins = new ArrayList<>();

	public Category(String name) {
		this.sectionPane = new SectionPane(name);
		this.name = name;
	}

	public SectionPane getSectionPane() {
		return sectionPane;
	}

	public TitledPane getTitlePane() {
		return sectionPane.getRootPane();
	}

	public VBox getContainerPane() {
		return sectionPane.getContainer();
	}

	public void addNode(Node node) {
		sectionPane.getContainer().getChildren().add(node);
	}

	public void clear() {
		getSectionPane().getContainer().getChildren().clear();
	}
}
