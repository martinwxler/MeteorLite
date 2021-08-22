package meteor.plugins.api.debug.walking;

import lombok.Setter;
import meteor.plugins.api.movement.Movement;
import meteor.plugins.api.scene.Tiles;
import meteor.ui.overlay.Overlay;
import net.runelite.api.Tile;
import net.runelite.api.coords.WorldPoint;

import javax.inject.Inject;
import java.awt.*;

public class WalkerDebugOverlay extends Overlay {
    @Setter
    private WorldPoint tile;
    private final Movement movement;
    @Inject
    public WalkerDebugOverlay(final Movement movement) {
        this.movement = movement;
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if (tile == null) {
            return null;
        }

        graphics.setColor(Color.RED);
        movement.drawPath(graphics, tile);
        return null;
    }
}
