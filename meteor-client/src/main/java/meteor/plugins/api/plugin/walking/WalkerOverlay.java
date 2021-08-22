package meteor.plugins.api.plugin.walking;

import lombok.Setter;
import meteor.plugins.api.movement.Movement;
import meteor.ui.overlay.Overlay;
import net.runelite.api.coords.WorldPoint;

import java.awt.*;

public class WalkerOverlay extends Overlay {
    @Setter
    private WorldPoint tile;

    @Override
    public Dimension render(Graphics2D graphics) {
        if (tile == null) {
            return null;
        }

        graphics.setColor(Color.RED);
        Movement.drawPath(graphics, tile);
        return null;
    }
}
