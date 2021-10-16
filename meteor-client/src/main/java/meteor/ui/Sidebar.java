package meteor.ui;

import com.jfoenix.controls.JFXButton;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import lombok.extern.slf4j.Slf4j;

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
		setBackground(new Background(new BackgroundFill(Paint.valueOf("212121"), null, null)));

		// Plugins button
		FontAwesomeIconView plugIcon = new FontAwesomeIconView();
		plugIcon.setFill(Color.AQUA);
		plugIcon.setGlyphName("PLUG");
		plugIcon.setSize("12");
		plugIcon.setWrappingWidth(WRAPPING_WIDTH);

		JFXButton pluginsButton = new JFXButton();
		pluginsButton.setMaxSize(32, 32);
		pluginsButton.setMinSize(32, 32);
		pluginsButton.setGraphic(plugIcon);
		pluginsButton.setAlignment(Pos.CENTER);
		pluginsButton.setTextAlignment(TextAlignment.CENTER);
		pluginsButton.setTextFill(Color.CYAN);
		pluginsButton.setButtonType(JFXButton.ButtonType.RAISED);
		pluginsButton.setBackground(new Background(new BackgroundFill(Paint.valueOf("252525"), new CornerRadii(3), null)));

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

		JFXButton panelButton = new JFXButton();
		panelButton.setMaxSize(32, 32);
		panelButton.setMinSize(32, 32);
		panelButton.setGraphic(panelButtonIcon);
		panelButton.setAlignment(Pos.CENTER);
		panelButton.setTextAlignment(TextAlignment.CENTER);
		panelButton.setTextFill(Color.CYAN);
		panelButton.setButtonType(JFXButton.ButtonType.RAISED);
		panelButton.setBackground(new Background(new BackgroundFill(Paint.valueOf("252525"), new CornerRadii(3), null)));

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

		JFXButton panelButton = new JFXButton();
		panelButton.setMaxSize(32, 32);
		panelButton.setMinSize(32, 32);
		panelButton.setGraphic(imageView);
		panelButton.setAlignment(Pos.CENTER);
		panelButton.setTextAlignment(TextAlignment.CENTER);
		panelButton.setTextFill(Color.CYAN);
		panelButton.setButtonType(JFXButton.ButtonType.RAISED);
		panelButton.setBackground(new Background(new BackgroundFill(Paint.valueOf("252525"), new CornerRadii(3), null)));

		panelButton.setOnMouseClicked(event -> {
			meteorUI.updateRightPanel(parent);
		});

		getChildren().add(panelButton);
		buttons.put(parent, panelButton);
	}

	public void removeNavigationButton(Parent parent) {
		JFXButton button = buttons.getOrDefault(parent, null);
		if (button != null) {
			getChildren().remove(button);
		}
	}

}
