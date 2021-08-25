package meteor.plugins.api.example.chickenkiller;

import lombok.Setter;
import meteor.plugins.api.game.Game;
import meteor.plugins.api.movement.Reachable;
import meteor.ui.overlay.Overlay;
import net.runelite.api.Locatable;
import net.runelite.api.coords.WorldPoint;

import java.awt.*;

public class ChickenKillerOverlay extends Overlay {

    @Setter
    private Locatable locatable;

    @Override
    public Dimension render(Graphics2D graphics) {
        if (locatable == null) {
            return null;
        }

        for (WorldPoint visitedTile : Reachable.getVisitedTiles(locatable.getWorldLocation(), locatable)) {
            visitedTile.outline(Game.getClient(), graphics, Color.RED);
        }

        return null;
    }
}
