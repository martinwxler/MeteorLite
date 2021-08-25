// 
// Decompiled by Procyon v0.5.36
// 

package meteor.plugins.barbassault.overlays;

import joptsimple.internal.Strings;
import meteor.plugins.barbassault.BAConfig;
import meteor.plugins.barbassault.BAPlugin;
import meteor.plugins.barbassault.util.Role;
import meteor.ui.overlay.Overlay;
import meteor.ui.overlay.OverlayLayer;
import meteor.ui.overlay.OverlayPosition;
import meteor.ui.overlay.OverlayUtil;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;

import javax.inject.Inject;
import java.awt.*;

public class BASceneOverlay extends Overlay
{
    private final Client client;
    private final BAPlugin plugin;
    private final BAConfig config;
    
    @Inject
    private BASceneOverlay(final Client client, final BAPlugin plugin, final BAConfig config) {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        this.setLayer(OverlayLayer.ABOVE_SCENE);
        this.setPosition(OverlayPosition.DYNAMIC);
    }
    
    public Dimension render(final Graphics2D graphics) {
        if (this.plugin.getInGame() == 0 || this.plugin.getRole() == null) {
            return null;
        }
        final Role role = this.plugin.getRole();
        if (role == Role.COLLECTOR) {
            this.markEggs(graphics, role);
        }
        return null;
    }
    
    private void markEggs(final Graphics2D graphics, final Role role) {
        if (!this.config.shouldMarkCollectorEggs()) {
            return;
        }
        final String listen = role.getListen(this.client);
        if (!this.plugin.getYellowEggs().isEmpty()) {
            this.plugin.getYellowEggs().forEach((w, c) -> this.drawTile(graphics, w, c, Color.YELLOW));
        }
        if (Strings.isNullOrEmpty(listen) || listen.equals("- - -")) {
            return;
        }
        final String s = listen;
        switch (s) {
            case "Red eggs": {
                this.plugin.getRedEggs().forEach((w, c) -> this.drawTile(graphics, w, c, Color.RED));
                break;
            }
            case "Blue eggs": {
                this.plugin.getBlueEggs().forEach((w, c) -> this.drawTile(graphics, w, c, Color.BLUE));
                break;
            }
            case "Green eggs": {
                this.plugin.getGreenEggs().forEach((w, c) -> this.drawTile(graphics, w, c, Color.GREEN));
                break;
            }
        }
    }
    
    private void drawTile(final Graphics2D g, final WorldPoint w, final int c, final Color color) {
        final LocalPoint lp = LocalPoint.fromWorld(this.client, w);
        if (lp == null) {
            return;
        }
        final Polygon poly = Perspective.getCanvasTileAreaPoly(this.client, lp, 1);
        if (poly != null) {
            OverlayUtil.renderPolygon(g, (Shape)poly, color);
        }
    }
}
