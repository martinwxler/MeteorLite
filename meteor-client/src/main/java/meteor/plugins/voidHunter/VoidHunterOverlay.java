package meteor.plugins.voidHunter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import meteor.ui.overlay.Overlay;
import meteor.ui.overlay.OverlayLayer;
import meteor.ui.overlay.OverlayPosition;
import net.runelite.api.GameObject;

@Singleton
public class VoidHunterOverlay extends Overlay {

  private final VoidHunterPlugin plugin;
  public List<GameObject> knownObjects = new ArrayList<>();

  @Inject
  VoidHunterOverlay(VoidHunterPlugin plugin) {
    setPosition(OverlayPosition.DYNAMIC);
    setLayer(OverlayLayer.ABOVE_SCENE);
    this.plugin = plugin;
  }

  @Override
  public Dimension render(Graphics2D graphics) {
    graphics.setColor(Color.cyan);
    for (GameObject gameObject : plugin.activeTraps())
    {
      if (gameObject != null)
        graphics.draw(gameObject.getConvexHull());
    }
    for (GameObject gameObject : plugin.emptyTraps())
    {
      if (gameObject != null)
        graphics.draw(gameObject.getConvexHull());
    }
    return null;
  }
}
