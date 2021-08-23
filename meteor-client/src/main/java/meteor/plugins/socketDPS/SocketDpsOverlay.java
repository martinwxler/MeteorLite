package meteor.plugins.socketDPS;

import meteor.plugins.socketDPS.table.SocketTableAlignment;
import meteor.plugins.socketDPS.table.SocketTableComponent;
import meteor.ui.overlay.OverlayMenuEntry;
import meteor.ui.overlay.OverlayPanel;
import meteor.ui.overlay.components.ComponentConstants;
import meteor.ui.overlay.components.LayoutableRenderableEntity;
import meteor.util.ColorUtil;
import meteor.util.QuantityFormatter;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.Player;

import javax.inject.Inject;
import java.awt.*;
import java.text.DecimalFormat;
import java.util.Map;

class SocketDpsOverlay extends OverlayPanel {
    private static final DecimalFormat DPS_FORMAT = new DecimalFormat("#0.0");

    private static final int PANEL_WIDTH_OFFSET = 10;

    @Inject
    SocketDpsOverlay(SocketDpsCounterPlugin socketDpsCounterPlugin, SocketDpsConfig socketDpsConfig, Client client) {
        super(socketDpsCounterPlugin);
        this.socketDpsCounterPlugin = socketDpsCounterPlugin;
        this.socketDpsConfig = socketDpsConfig;
        this.client = client;
        getMenuEntries().add(RESET_ENTRY);
    }

    public Dimension render(Graphics2D graphics) {
        if (this.socketDpsConfig.displayOverlay()) {
                Map<String, Integer> dpsMembers = this.socketDpsCounterPlugin.getMembers();
                this.panelComponent.getChildren().clear();
                int tot = 0;
                if (dpsMembers.isEmpty())
                    return null;
                Player player = this.client.getLocalPlayer();
                if (dpsMembers.containsKey("total")) {
                    tot = (Integer) dpsMembers.get("total");
                    dpsMembers.remove("total");
                }
                SocketTableComponent tableComponent = new SocketTableComponent();
                tableComponent.setColumnAlignments(SocketTableAlignment.LEFT, SocketTableAlignment.RIGHT);
                int maxWidth = 129;
                dpsMembers.forEach((k, v) -> {
                    String right = QuantityFormatter.formatNumber(v);
                    if (k.equalsIgnoreCase(this.client.getLocalPlayer().getName()) && this.socketDpsConfig.highlightSelf()) {
                        tableComponent.addRow(ColorUtil.prependColorTag(k, Color.green), ColorUtil.prependColorTag(right, Color.green));
                    } else if (this.socketDpsConfig.highlightOtherPlayer() && this.socketDpsCounterPlugin.getHighlights().contains(k.toLowerCase())) {
                        tableComponent.addRow(ColorUtil.prependColorTag(k, this.socketDpsConfig.getHighlightColor()), ColorUtil.prependColorTag(right, this.socketDpsConfig.getHighlightColor()));
                    } else {
                        tableComponent.addRow(ColorUtil.prependColorTag(k, Color.white), ColorUtil.prependColorTag(right, Color.white));
                    }
                });
                this.panelComponent.setPreferredSize(new Dimension(maxWidth + 10, 0));
                dpsMembers.put("total", tot);
                if (player.getName() != null && dpsMembers.containsKey(player.getName()) && tot > (Integer) dpsMembers.get(player.getName()) && this.socketDpsConfig.showTotal())
                    tableComponent.addRow(ColorUtil.prependColorTag("total", Color.red), ColorUtil.prependColorTag(((Integer) dpsMembers.get("total")).toString(), Color.red));
                if (!tableComponent.isEmpty())
                    this.panelComponent.getChildren().add((LayoutableRenderableEntity) tableComponent);

            if (this.socketDpsConfig.backgroundStyle() == SocketDpsConfig.backgroundMode.HIDE)
            {
                panelComponent.setBackgroundColor(null);
                return super.render(graphics);
            }
            else if (this.socketDpsConfig.backgroundStyle() == SocketDpsConfig.backgroundMode.STANDARD)
            {
                panelComponent.setBackgroundColor(ComponentConstants.STANDARD_BACKGROUND_COLOR);
                return this.panelComponent.render(graphics);
            }
            else if (this.socketDpsConfig.backgroundStyle() == SocketDpsConfig.backgroundMode.CUSTOM)
            {
                panelComponent.setBackgroundColor(new Color(socketDpsConfig.backgroundColor().getRed(), socketDpsConfig.backgroundColor().getGreen(), socketDpsConfig.backgroundColor().getBlue(), socketDpsConfig.backgroundColor().getAlpha()));
                return this.panelComponent.render(graphics);
            }
        }
        return null;
    }

    static final OverlayMenuEntry RESET_ENTRY = new OverlayMenuEntry(MenuAction.RUNELITE_OVERLAY, "Reset", "DPS counter");

    private final SocketDpsCounterPlugin socketDpsCounterPlugin;

    private final SocketDpsConfig socketDpsConfig;

    private final Client client;
}
