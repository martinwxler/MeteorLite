// 
// Decompiled by Procyon v0.5.36
// 

package meteor.plugins.barbassault.overlays;

import meteor.plugins.barbassault.BAConfig;
import meteor.plugins.barbassault.BAPlugin;
import meteor.plugins.barbassault.util.Role;
import meteor.ui.overlay.WidgetItemOverlay;
import net.runelite.api.Client;
import net.runelite.api.widgets.WidgetItem;

import javax.inject.Inject;
import java.awt.*;

public class BAItemOverlay extends WidgetItemOverlay
{
    private final Client client;
    private final BAPlugin plugin;
    private final BAConfig config;
    
    @Inject
    private BAItemOverlay(final Client client, final BAPlugin plugin, final BAConfig config) {
        this.client = client;
        this.plugin = plugin;
        this.config = config;
        this.showOnInventory();
        this.showOnEquipment();
    }
    
    public void renderItemOverlay(final Graphics2D graphics, final int itemId, final WidgetItem widgetItem) {
        if (this.plugin.getInGame() == 0 || this.plugin.getRole() == null) {
            return;
        }
        final Role role = this.plugin.getRole();
        final int id = role.getListenItem(this.client);
        final Color color = getColor(this.config, role);
        if (id == -1 || color == null) {
            return;
        }
        if (itemId == id) {
            this.outlineBounds(graphics, widgetItem.getCanvasBounds(), color);
        }
    }
    
    private static Color getColor(final BAConfig config, final Role role) {
        switch (role) {
            case ATTACKER: {
                return config.shouldMarkArrows() ? config.getArrowMarkerColor() : null;
            }
            case DEFENDER: {
                return config.shouldMarkBait() ? config.getBaitMarkerColor() : null;
            }
            case HEALER: {
                return config.shouldMarkPoison() ? config.getPoisonMarkerColor() : null;
            }
            default: {
                return null;
            }
        }
    }
    
    private void outlineBounds(final Graphics2D g, final Shape s, final Color c) {
        if (s == null) {
            return;
        }
        g.setColor(c);
        g.setStroke(new BasicStroke(1.0f));
        g.draw(s);
    }
}
