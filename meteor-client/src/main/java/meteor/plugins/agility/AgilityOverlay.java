package meteor.plugins.agility;

import com.google.inject.Inject;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Shape;
import meteor.ui.overlay.Overlay;
import meteor.ui.overlay.OverlayLayer;
import meteor.ui.overlay.OverlayPosition;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.Skill;
import net.runelite.api.Tile;
import net.runelite.api.TileObject;

public class AgilityOverlay extends Overlay {


  AgilityPlugin plugin;

  private final String MARK_OF_GRACE = "Mark of Grace";
  private final int OFFSET_Z = 20;
  private Color overlayFillColor;
  private Color activeColor;
  private Color prevColor;
  private Color prevOverlayFillColor;
  private AgilityShortcut agilityShortcut;

  @Inject
  public AgilityOverlay(AgilityPlugin plugin) {
    this.plugin = plugin;
    setPosition(OverlayPosition.DYNAMIC);
    setLayer(OverlayLayer.ABOVE_SCENE);
  }

  @Override
  public Dimension render(Graphics2D graphics) {
    if (plugin.marks.size() > 0) {
      activeColor = Color.red;
    } else {
      activeColor = Color.cyan;
    }

    overlayFillColor = new Color(activeColor.darker().getRed(), activeColor.darker().getGreen(),
        activeColor.darker().getBlue(), 125);

    graphics.setColor(activeColor);

    for (TileObject o : plugin.obstacles.keySet()) {
      prevColor = activeColor;
      prevOverlayFillColor = overlayFillColor;
      agilityShortcut = plugin.obstacles.get(o).getShortcut();
      if (agilityShortcut != null) {
        if (agilityShortcut.getLevel() > client.getBoostedSkillLevel(Skill.AGILITY)) {
          activeColor = Color.red;
        } else {
          activeColor = Color.green;
        }
        prevOverlayFillColor = overlayFillColor;
        overlayFillColor = new Color(activeColor.darker().getRed(), activeColor.darker().getGreen(),
            activeColor.darker().getBlue(), 125);
      }
      paintClickBox(graphics, o.getPlane(), o.getClickbox());
      activeColor = prevColor;
      overlayFillColor = prevOverlayFillColor;
    }
    Point p;
    for (Tile tile : plugin.marks) {
      if (tile.getPlane() != client.getPlane()) {
        continue;
      }

      graphics.setColor(activeColor);
      graphics.draw(Perspective.getCanvasTileAreaPoly(client, tile.getLocalLocation(), 1));
      graphics.setColor(Color.white);
      p = Perspective
          .getCanvasTextLocation(client, graphics, tile.getLocalLocation(), MARK_OF_GRACE,
              OFFSET_Z);
      if (p != null) {
        drawShadowText(graphics, MARK_OF_GRACE, p.getX(), p.getY());
      }
    }
    return null;
  }

  private void paintClickBox(Graphics2D graphics2D, int plane, Shape clickbox) {

    if (plane == client.getPlane()) {
      if (clickbox != null) {
        graphics2D.setColor(activeColor);
        graphics2D.draw(clickbox);
        graphics2D.setColor(overlayFillColor);
        graphics2D.fill(clickbox);
      }
    }
  }

  private void drawShadowText(Graphics2D graphics, String s, int x, int y) {
    graphics.setColor(Color.black);
    graphics.drawString(s, x, y + 1);
    graphics.drawString(s, x, y - 1);
    graphics.drawString(s, x + 1, y);
    graphics.drawString(s, x - 1, y);
    graphics.setColor(Color.white);
    graphics.drawString(s, x, y);
    graphics.setColor(activeColor);
  }

}
