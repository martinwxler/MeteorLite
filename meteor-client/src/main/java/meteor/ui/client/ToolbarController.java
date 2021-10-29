//package meteor.ui.client;
//
//import com.google.inject.Singleton;
//import javafx.embed.swing.JFXPanel;
//import javafx.scene.Scene;
//import javafx.scene.layout.VBox;
//
//import javax.tools.Tool;
//import java.awt.*;
//import java.util.List;
//
//@Singleton
//public class ToolbarController extends JFXPanel {
//
//    private final List<JFXPanel> toolbars;
//    private final Toolbar eastToolbar;
//
//    public ToolbarController() {
//        toolbars = List.of(
//                new Toolbar(BorderLayout.NORTH),
//                new Toolbar(BorderLayout.SOUTH),
//                new Toolbar(BorderLayout.WEST)
//        );
//        this.eastToolbar = new Toolbar();
//    }
//
//    private JFXPanel initToolbar(String position) {
//        JFXPanel panel = new JFXPanel();
//        Toolbar toolbar = new Toolbar(position);
//        panel.setScene(new Scene(toolbar, toolbar.getWidth(), toolbar.getHeight()));
//
//
//
//        return panel;
//    }
//
//    public JFXPanel getNorthToolbar() {
//
//    }
//
//    public JFXPanel getNorthToolbar() {
//
//    }
//
//    public JFXPanel getNorthToolbar() {
//
//    }
//
//    public Toolbar getEastToolbar() {
//        return eastToolbar;
//    }
//
//}
