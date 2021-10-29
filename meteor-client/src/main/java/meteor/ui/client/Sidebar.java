package meteor.ui.client;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.TextAlignment;
import lombok.extern.slf4j.Slf4j;
import meteor.ui.MeteorUI;
import meteor.util.MeteorConstants;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;


@Singleton
@Slf4j
public class Sidebar extends VBox {

	private static final double WRAPPING_WIDTH = 11.523646354675293;

	private final Map<Parent, JFXButton> buttons = new HashMap<>();

	@Inject
	private MeteorUI meteorUI;

	@Inject
	private RightPanel rightPanel;

	public Sidebar() {
		setMinWidth(32);
		setMaxWidth(32);
		setBackground(new Background(new BackgroundFill(MeteorConstants.GRAY, null, null)));

		// Plugins button
		FontAwesomeIconView plugIcon = new FontAwesomeIconView();
		plugIcon.setFill(Color.AQUA);
		plugIcon.setGlyphName("PLUG");
		plugIcon.setSize("12");
		plugIcon.setWrappingWidth(WRAPPING_WIDTH);

		JFXButton pluginsButton = createButton();
		pluginsButton.setGraphic(plugIcon);

		pluginsButton.setOnMouseClicked(event -> {
			meteorUI.showPlugins();
		});

		getChildren().add(pluginsButton);
	}

	public void addNavigationButton(FontAwesomeIcon icon, Parent parent) {
		FontAwesomeIconView panelButtonIcon = new FontAwesomeIconView(icon);
		panelButtonIcon.setFill(Color.AQUA);
		panelButtonIcon.setSize("10");
		panelButtonIcon.setWrappingWidth(WRAPPING_WIDTH);

		JFXButton panelButton = createButton();
		panelButton.setGraphic(panelButtonIcon);

		panelButton.setOnMouseClicked(event -> {
			meteorUI.updateRightPanel(parent);
		});

		getChildren().add(panelButton);
		buttons.put(parent, panelButton);
	}

	public void addNavigationButton(Image image, Parent parent) {
		ImageView imageView = new ImageView(image);
		imageView.resize(24, 24);
		imageView.setPreserveRatio(true);

		JFXButton panelButton = createButton();
		panelButton.setGraphic(imageView);

		panelButton.setOnMouseClicked(event -> {
			meteorUI.updateRightPanel(parent);
		});

		getChildren().add(panelButton);
		buttons.put(parent, panelButton);
	}

	private JFXButton createButton() {
		JFXButton button = new JFXButton();
		button.setMaxSize(32, 32);
		button.setMinSize(32, 32);
		button.setAlignment(Pos.CENTER);
		button.setTextAlignment(TextAlignment.CENTER);
		button.setTextFill(Color.CYAN);
		button.setButtonType(JFXButton.ButtonType.FLAT);
		button.setBackground(new Background(new BackgroundFill(MeteorConstants.GRAY, null, null)));
		return button;
	}

	public void removeNavigationButton(Parent parent) {
		JFXButton button = buttons.getOrDefault(parent, null);
		if (button != null) {
			getChildren().remove(button);
		}
	}

}
