package meteor.ui;

import javafx.embed.swing.JFXPanel;

import javax.swing.*;
import java.awt.*;

public class Frame {
    JFrame mainFrame = new JFrame("SpongeOSRS");
    JPanel mainPanel = new JPanel();
    JFXPanel toolbarPanel = new JFXPanel();

    BorderLayout layout = new BorderLayout();

    Frame()
    {
        mainFrame.setSize(1280, 720);
        mainPanel.setSize(1280, 720);
        mainPanel.setLayout(layout);
        mainPanel.add(toolbarPanel);
    }
}
