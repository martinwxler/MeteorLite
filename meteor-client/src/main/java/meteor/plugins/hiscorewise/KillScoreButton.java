package meteor.plugins.hiscorewise;

import com.jfoenix.controls.JFXButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class KillScoreButton extends JFXButton{

  public String imageString = "";

  public KillScoreButton(String image, int row, int column) {
    imageString = image;
    setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(this, getTopAnchor(row));
    AnchorPane.setLeftAnchor(this, getLeftAnchor(column));
    AnchorPane.setRightAnchor(this, getRightAnchor(column));
    Image buttonImg = new Image(image);
    ImageView imageView = new ImageView(buttonImg);
    setGraphic(imageView);
    setVisible(false);
  }

  private double getTopAnchor(int row) {
    return 210.0 + ((row - 1) * 35);
  }

  private double getLeftAnchor(int column) {
    return switch (column) {
      case 3 -> 240;
      case 2 -> 135;
      case 1 -> 14;
      default -> -1;
    };
  }

  private double getRightAnchor(int column) {
    return switch (column) {
      case 3 -> 18;
      case 2 -> 120;
      case 1 -> 225;
      default -> -1;
    };
  }
}
