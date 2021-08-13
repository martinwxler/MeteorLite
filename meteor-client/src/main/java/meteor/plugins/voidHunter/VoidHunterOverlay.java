package meteor.plugins.voidHunter;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import javax.inject.Inject;
import javax.inject.Singleton;
import meteor.ui.overlay.OverlayLayer;
import meteor.ui.overlay.OverlayPanel;
import meteor.ui.overlay.OverlayPosition;
import net.runelite.api.GameObject;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.TileItem;

@Singleton
public class VoidHunterOverlay extends OverlayPanel {

  private final VoidHunterPlugin plugin;

  @Inject
  VoidHunterOverlay(VoidHunterPlugin plugin) {
    setPosition(OverlayPosition.DYNAMIC);
    setLayer(OverlayLayer.ABOVE_SCENE);
    this.plugin = plugin;
  }

  @Override
  public Dimension render(Graphics2D graphics) {
    if (!VoidHunterPlugin.enabled) {
      return null;
    }

    GameObject nearestCaughtTrap = plugin.nearestCaughtTrap();
    GameObject nearestEmptyrap = plugin.nearestEmptyTrap();
    TileItem nearestItemToPickup = plugin.nearestItemToPickup();


    graphics.setColor(Color.CYAN);
    if (plugin.activeTraps() != null) {
      for (GameObject gameObject : plugin.activeTraps()) {
        if (gameObject != null) {
          graphics.draw(gameObject.getConvexHull());
        }
      }
    }

    graphics.setColor(Color.white);
    if (nearestItemToPickup != null) {
      Shape tilePoly = Perspective
          .getCanvasTileAreaPoly(client, nearestItemToPickup.getTile().getLocalLocation(), 1);
      if (tilePoly != null) {
        graphics.draw(tilePoly);
      }

      int distance = nearestItemToPickup.getDistanceFromLocalPlayer();
      Point canvasPoint = Perspective
          .getCanvasTextLocation(client, graphics, nearestItemToPickup.getTile().getLocalLocation(),
              "" + nearestItemToPickup.getDistanceFromLocalPlayer(), 0);
      graphics.drawString("" + distance, canvasPoint.getX(), canvasPoint.getY());
    }

    graphics.setColor(Color.CYAN);
    if (plugin.emptyTraps() != null) {
      for (GameObject gameObject : plugin.emptyTraps()) {
        if (gameObject != null) {
          graphics.draw(gameObject.getConvexHull());
          int distance = gameObject.getDistanceFromLocalPlayer();
          Point canvasPoint = Perspective
              .getCanvasTextLocation(client, graphics, gameObject.getLocalLocation(),
                  "" + gameObject.getDistanceFromLocalPlayer(), 0);
          graphics.drawString("" + distance, canvasPoint.getX(), canvasPoint.getY());
        }
      }
    }

    graphics.setColor(Color.BLUE);
    if (nearestEmptyrap != null) {
      graphics.draw(nearestEmptyrap.getConvexHull());
    }
    graphics.setColor(Color.GREEN);
    if (nearestCaughtTrap != null) {
      graphics.draw(nearestCaughtTrap.getConvexHull());
    }
    return super.render(graphics);
  }
}
