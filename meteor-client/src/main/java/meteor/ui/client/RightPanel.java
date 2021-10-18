package meteor.ui.client;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import meteor.ui.MeteorUI;
import meteor.util.MeteorConstants;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RightPanel extends JFXPanel {

	private Parent panel = new Pane();
	private HBox root = new HBox(panel);

	@Inject
	private MeteorUI meteorUI;

	@Inject
	private Toolbar toolbar;

	public RightPanel() {
		Platform.runLater(this::createScene);
	}

	private void createScene() {
		panel.setVisible(false);
		Scene scene = new Scene(root, MeteorConstants.PANEL_WIDTH, 1080);
		setScene(scene);
	}

	public void addToolbar() {
		root = new HBox(panel, toolbar.getCurrentRoot());
		toolbar.refresh();
		Scene scene = new Scene(root, MeteorConstants.PANEL_WIDTH + MeteorConstants.TOOLBAR_SIZE, 1080);
		setScene(scene);
	}

	public void removeToolbar() {
		root = new HBox(panel);
		toolbar.refresh();
		Scene scene = new Scene(root, MeteorConstants.PANEL_WIDTH, 1080);
		setScene(scene);
	}

	public boolean isOpen() {
		return panel.isVisible();
	}

	public void addPanel(Parent parent) {
		if (parent instanceof ScrollPane) {
			((ScrollPane) parent).setMinWidth(MeteorConstants.PANEL_WIDTH);
		}
		if (parent instanceof Pane) {
			((Pane) parent).setMinWidth(MeteorConstants.PANEL_WIDTH);
		}
		panel = parent;
		root.getChildren().remove(0);
		root.getChildren().add(panel);
	}

	private void open(Parent parent) {
		addPanel(parent);
		Platform.runLater(() -> {
			panel.setVisible(true);
			meteorUI.updateClientSize();
		});
	}

	private void close() {
		Platform.runLater(() -> {
			panel.setVisible(false);
			meteorUI.updateClientSize();
		});
	}

	public void update(Parent parent) {
		if (isOpen()) {
			if (parent.idProperty().equals(getScene().getRoot().idProperty())) {
				close();
			} else {
				addPanel(parent);
			}
		} else {
			open(parent);
		}
	}
}
