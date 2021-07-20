package meteor.plugins.agility;

import meteor.ui.overlay.Overlay;
import net.runelite.api.*;
import net.runelite.api.Point;

import java.awt.*;

public class AgilityOverlay extends Overlay {

    AgilityPlugin plugin;
    private Color overlayFillColor;
    private Color activeColor;
    private Color prevColor;
    private Color prevOverlayFillColor;
    private final String MARK_OF_GRACE = "Mark of Grace";
    private AgilityShortcut agilityShortcut;
    private final int OFFSET_Z = 20;

    public AgilityOverlay(AgilityPlugin agilityPlugin) {
        super(agilityPlugin);
        plugin = agilityPlugin;
    }

    @Override
    public Dimension render(Graphics2D graphics) {
        if (plugin.marks.size() > 0)
            activeColor = Color.red;
        else
            activeColor = Color.cyan;

        overlayFillColor = new Color(activeColor.darker().getRed(), activeColor.darker().getGreen(), activeColor.darker().getBlue(), 125);

        graphics.setColor(activeColor);

        for (TileObject o : plugin.obstacles.keySet())
        {
            prevColor = activeColor;
            prevOverlayFillColor = overlayFillColor;
            agilityShortcut = plugin.obstacles.get(o).getShortcut();
            if (agilityShortcut != null)
            {
                if (agilityShortcut.getLevel() > client.getBoostedSkillLevel(Skill.AGILITY))
                {
                    activeColor = Color.red;
                }
                else
                {
                    activeColor = Color.green;
                }
                prevOverlayFillColor = overlayFillColor;
                overlayFillColor = new Color(activeColor.darker().getRed(), activeColor.darker().getGreen(), activeColor.darker().getBlue(), 125);
            }
            paintClickBox(graphics, o.getPlane(), o.getClickbox());
            activeColor = prevColor;
            overlayFillColor = prevOverlayFillColor;
        }
        Point p;
        for (Tile tile : plugin.marks)
        {
            if (tile.getPlane() != client.getPlane())
                continue;

            graphics.setColor(activeColor);
            graphics.draw(Perspective.getCanvasTileAreaPoly(client, tile.getLocalLocation(), 1));
            graphics.setColor(Color.white);
            p = Perspective.getCanvasTextLocation(client, graphics,  tile.getLocalLocation(), MARK_OF_GRACE, OFFSET_Z);
            if (p != null)
                graphics.drawString(MARK_OF_GRACE, p.getX(), p.getY());
        }
        return null;
    }

    private void paintClickBox(Graphics2D graphics2D, int plane, Shape clickbox) {

        if (plane == client.getPlane())
        {
            if (clickbox != null)
            {
                graphics2D.setColor(activeColor);
                graphics2D.draw(clickbox);
                graphics2D.setColor(overlayFillColor);
                graphics2D.fill(clickbox);
            }
        }
    }
}
