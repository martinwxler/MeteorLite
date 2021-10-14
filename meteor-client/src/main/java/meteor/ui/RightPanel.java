package meteor.ui;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class RightPanel extends JFXPanel {

    private HBox root;
    private Parent lastPanel = null;

    @Inject
    private Sidebar sidebar;

    @Inject
    private MeteorUI meteorUI;

    public RightPanel() {
        Platform.runLater(this::createScene);
    }

    private void createScene() {
        root = new HBox();
        root.getChildren().add(sidebar);
        Scene scene = new Scene(root, 24, 1080);
        setScene(scene);
    }

    private void createScene(int width) {
        root = new HBox();
        if (lastPanel != null && width == 374) {
            root.getChildren().add(lastPanel);
        }
        root.getChildren().add(sidebar);
        Scene scene = new Scene(root, width, 1080);
        setScene(scene);
    }

    public boolean isOpen() {
        return getScene().getWidth() == 374;
    }

    public void changePanel(Pane pane) {
        if (root.getChildren().size() > 1) {
            root.getChildren().clear();
        }
        pane.setMinWidth(350);
        pane.setMaxWidth(350);
        root.getChildren().addAll(pane, sidebar);
    }

    public void addPanel(Parent parent) {
        if (parent instanceof ScrollPane) {
            ((ScrollPane) parent).setMinWidth(350);
        }
        if (parent instanceof Pane) {
            ((Pane) parent).setMinWidth(350);
        }
        if (root.getChildren().size() > 1) {
            root.getChildren().remove(0);
        }
        root.getChildren().add(0, parent);
        lastPanel = parent;
    }

    private void open(Parent parent) {
        Platform.runLater(() -> createScene(374));
        addPanel(parent);
    }

    private void close() {
        Platform.runLater(() -> createScene(24));
    }

    public void refresh() {
        Platform.runLater(() -> createScene(getWidth()));
    }

    public void update(Parent parent) {
        if (isOpen()) {
            if (lastPanel != null && parent.idProperty().equals(lastPanel.idProperty())) {
                close();
                meteorUI.updateClientSize();
            } else {
                addPanel(parent);
            }
        } else {
            open(parent);
            meteorUI.updateClientSize();
        }
    }
}
