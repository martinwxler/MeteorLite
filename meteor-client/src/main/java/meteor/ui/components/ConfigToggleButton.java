package meteor.ui.components;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import lombok.Getter;

public class ConfigToggleButton extends JFXButton {

    @Getter
    private boolean toggled;

    private final FontAwesomeIconView on;
    private final FontAwesomeIconView off;

    public ConfigToggleButton() {
        on = new FontAwesomeIconView(FontAwesomeIcon.PLAY);
        on.setSize("16");
        on.setFill(javafx.scene.paint.Color.valueOf("CYAN"));
        off = new FontAwesomeIconView(FontAwesomeIcon.STOP);
        off.setSize("16");
        off.setFill(javafx.scene.paint.Color.valueOf("CYAN"));

        this.toggled = false;
        this.setGraphic(on);
        this.setText("Start");
    }

    public void toggle() {
        toggled = !toggled;
        this.setGraphic(toggled ? off : on);
        this.setText(toggled ? "Stop" : "Start");
    }

}
