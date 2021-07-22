package meteor.ui;

import java.awt.BorderLayout;
import javafx.embed.swing.JFXPanel;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Frame {

  JFrame mainFrame = new JFrame("MeteorLite");
  JPanel mainPanel = new JPanel();
  JFXPanel toolbarPanel = new JFXPanel();

  BorderLayout layout = new BorderLayout();

  Frame() {
    mainFrame.setSize(1280, 720);
    mainPanel.setSize(1280, 720);
    mainPanel.setLayout(layout);
    mainPanel.add(toolbarPanel);
  }
}
