package meteor.ui.client;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.BorderWidths;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import lombok.Getter;


public class PluginPanel extends GridPane {
    public static final int PANEL_WIDTH = 350;
    public static final int SCROLLBAR_WIDTH = 17;
    public static final int BORDER_OFFSET = 6;

    private final ScrollPane scrollPane;
    @Getter
    private final BorderPane wrappedPane;

    public PluginPanel() {
        super();

        setBorder(new Border(new BorderStroke(Paint.valueOf("009688"), BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        //        setBackground(Paint.valueOf(ColorScheme.DARK_GRAY_COLOR.toString()));

        final BorderPane northPanel = new BorderPane();
        northPanel.setTop(this);
        //        northPanel.setBackground(Paint.valueOf(ColorScheme.DARK_GRAY_COLOR.toString()));

        scrollPane = new ScrollPane(northPanel);
        scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

        wrappedPane = new BorderPane();

        // Adjust the preferred size to expand to width of scrollbar to
        // to preven scrollbar overlapping over contents
        wrappedPane.setMinWidth(PANEL_WIDTH);
        wrappedPane.setCenter(scrollPane);

        setMinWidth(PANEL_WIDTH);
        setMinHeight(800);
    }

    public void onActivate() {
    }

    public void onDeactivate() {
    }
}
