package meteor.ui.components;

import com.jfoenix.controls.JFXButton;

public class ConfigButton extends JFXButton {
	private final boolean toggleable;
	private boolean toggled;

	public ConfigButton(boolean toggleable) {
		this.toggleable = toggleable;
	}

	public boolean isToggled() {
		return toggled;
	}

	public void setToggled(boolean toggled) {
		this.toggled = toggled;
	}

	public void toggle() {
		toggled = !toggled;
	}

	public boolean isToggleable() {
		return toggleable;
	}
}
