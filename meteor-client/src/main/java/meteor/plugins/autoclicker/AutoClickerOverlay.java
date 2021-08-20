package meteor.plugins.autoclicker;

import meteor.ui.overlay.OverlayMenuEntry;
import meteor.ui.overlay.OverlayPanel;
import meteor.ui.overlay.OverlayPosition;
import meteor.ui.overlay.components.TableAlignment;
import meteor.ui.overlay.components.TableComponent;
import meteor.ui.overlay.components.TitleComponent;
import meteor.util.ColorUtil;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;

import static net.runelite.api.MenuAction.RUNELITE_OVERLAY_CONFIG;
import static meteor.ui.overlay.OverlayManager.OPTION_CONFIGURE;

@Singleton
class AutoClickerOverlay extends OverlayPanel {
    private final AutoClickerPlugin plugin;
    private final AutoClickerConfig config;

    @Inject
    private AutoClickerOverlay(final AutoClickerPlugin plugin, final AutoClickerConfig config) {
        super(plugin);
        setPosition(OverlayPosition.BOTTOM_LEFT);
        this.plugin = plugin;
        this.config = config;
        getMenuEntries().add(new OverlayMenuEntry(RUNELITE_OVERLAY_CONFIG, OPTION_CONFIGURE, "Soxs' AutoClicker"));
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if (!plugin.run || config.disableUI()) {
            return null;
        }

        TableComponent tableComponent = new TableComponent();
        tableComponent.setColumnAlignments(TableAlignment.LEFT, TableAlignment.RIGHT);
        tableComponent.addRow("Running...");
        if (!tableComponent.isEmpty()) {
            panelComponent.setBackgroundColor(ColorUtil.fromHex("#121212")); //Material Dark default
            panelComponent.setPreferredSize(new Dimension(270, 200));
            panelComponent.setBorder(new Rectangle(5, 5, 5, 5));
            panelComponent.getChildren().add(TitleComponent.builder()
                    .text("Soxs' AutoClicker")
                    .color(ColorUtil.fromHex("#40C4FF"))
                    .build());
            panelComponent.getChildren().add(tableComponent);
        }
        return super.render(graphics);
    }
}
