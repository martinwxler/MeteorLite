package meteor.ui.components;

import javafx.scene.text.Font;
import javafx.scene.text.Text;
import meteor.MeteorLiteClientModule;
import meteor.plugins.Plugin;

public class MeteorText extends Text {
	public MeteorText() {
		super();
		setFont(Font.font(MeteorLiteClientModule.METEOR_FONT_SIZE));
		setFill(MeteorLiteClientModule.METEOR_FONT_COLOR);
	}

	public MeteorText(Plugin plugin) {
		this(plugin.getName());
	}

	public MeteorText(String text) {
		this();
		setText(text);
	}
}
