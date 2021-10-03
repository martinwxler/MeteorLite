package meteor.ui.components;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;

public class ConfigButton extends JFXButton {

	public ConfigButton(final String text) {
		this.setText(text);
	}

	public ConfigButton(final String text, final FontAwesomeIcon icon) {
		this.setText(text);
		FontAwesomeIconView iconView = new FontAwesomeIconView(icon);
		iconView.setSize("16");
		iconView.setFill(javafx.scene.paint.Color.valueOf("CYAN"));
		this.setGraphic(iconView);
	}

}
