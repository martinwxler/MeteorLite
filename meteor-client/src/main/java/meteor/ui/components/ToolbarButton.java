package meteor.ui.components;

import com.jfoenix.controls.JFXButton;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import lombok.Getter;
import lombok.Setter;

public class ToolbarButton extends JFXButton {

  /*
  This must be manually defined because the size of a node cannot be determined before it is added
  to a screen.
   */
  @Getter
  @Setter
  int realWidth;

  public ToolbarButton(String text) {
    super(text);
    setTextFill(Paint.valueOf("CYAN"));
    setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(this, 2.0);
    AnchorPane.setBottomAnchor(this, 2.0);
  }
}
