package meteor.plugins.voidtempoross;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import javax.inject.Singleton;
import meteor.ui.overlay.OverlayLayer;
import meteor.ui.overlay.OverlayPanel;
import meteor.ui.overlay.OverlayPosition;
import net.runelite.api.*;

@Singleton
public class VoidTemporossOverlay extends OverlayPanel {

  private final VoidTemporossPlugin plugin;

  @Inject
  VoidTemporossOverlay(VoidTemporossPlugin plugin) {
    setPosition(OverlayPosition.DYNAMIC);
    setLayer(OverlayLayer.ABOVE_SCENE);
    this.plugin = plugin;
  }

  @Override
  public Dimension render(Graphics2D graphics) {
    if (!plugin.enabled) {
      return null;
    }
    GameObject[] objects = new GameObject[] {

    };
    NPC[] npcs = new NPC[] {

    };
    graphics.setColor(Color.CYAN);
    for (GameObject o : objects) {
      if (o != null)
        if (o.getConvexHull() != null)
          graphics.draw(o.getConvexHull());
    }
    for (NPC n : npcs) {
      if (n != null)
        if (n.getConvexHull() != null)
          graphics.draw(n.getConvexHull());
    }
    return super.render(graphics);
  }
}
