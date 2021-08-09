package meteor.plugins.barrowsminimap;

import meteor.ui.overlay.Overlay;
import meteor.ui.overlay.OverlayLayer;
import meteor.ui.overlay.OverlayPosition;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;
import java.util.List;

@Singleton
class BarrowsMinimapOverlay extends Overlay
{
    private final Client client;
    private final BarrowsMinimap plugin;

    @Inject
    private BarrowsMinimapOverlay(final Client client, final BarrowsMinimap plugin)
    {
        setPosition(OverlayPosition.DYNAMIC);
        setLayer(OverlayLayer.ABOVE_WIDGETS);
        this.client = client;
        this.plugin = plugin;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        Player local = client.getLocalPlayer();
        final Color npcColor = getMinimapDotColor(1);
        final Color playerColor = getMinimapDotColor(2);

        // tunnels are only on z=0
        if (!plugin.getWalls().isEmpty() && client.getPlane() == 0)
        {
            // NPC dots
            graphics.setColor(npcColor);
            final List<NPC> npcs = client.getNpcs();
            for (NPC npc : npcs)
            {
                final NPCComposition composition = npc.getComposition();

                if (composition != null && !composition.isMinimapVisible())
                {
                    continue;
                }

                net.runelite.api.Point minimapLocation = npc.getMinimapLocation();
                if (minimapLocation != null)
                {
                    graphics.fillOval(minimapLocation.getX(), minimapLocation.getY(), 4, 4);
                }
            }

            // Player dots
            graphics.setColor(playerColor);
            final List<Player> players = client.getPlayers();
            for (Player player : players)
            {
                if (player == local)
                {
                    // Skip local player as we draw square for it later
                    continue;
                }

                net.runelite.api.Point minimapLocation = player.getMinimapLocation();
                if (minimapLocation != null)
                {
                    graphics.fillOval(minimapLocation.getX(), minimapLocation.getY(), 4, 4);
                }
            }

            // Render barrows walls/doors
            renderObjects(graphics, local);

            // Local player square
            graphics.setColor(playerColor);
            graphics.fillRect(local.getMinimapLocation().getX(), local.getMinimapLocation().getY(), 3, 3);
        }
        return null;
    }
    private void renderObjects(Graphics2D graphics, Player localPlayer)
    {
        LocalPoint localLocation = localPlayer.getLocalLocation();
        for (WallObject wall : plugin.getWalls())
        {
            LocalPoint location = wall.getLocalLocation();
            if (localLocation.distanceTo(location) <= 2350)
            {
                renderWalls(graphics, wall);
            }
        }

        for (GameObject ladder : plugin.getLadders())
        {
            LocalPoint location = ladder.getLocalLocation();
            if (localLocation.distanceTo(location) <= 2350)
            {
                renderLadders(graphics, ladder);
            }
        }
    }

    private void renderWalls(Graphics2D graphics, WallObject wall)
    {
        net.runelite.api.Point minimapLocation = wall.getMinimapLocation();

        if (minimapLocation == null)
        {
            return;
        }

        ObjectComposition objectComp = client.getObjectDefinition(wall.getId());
        ObjectComposition impostor = objectComp.getImpostorIds() != null ? objectComp.getImpostor() : null;

        if (impostor != null && impostor.getActions()[0] != null)
        {
            graphics.setColor(Color.green);
        }
        else
        {
            graphics.setColor(Color.gray);
        }

        graphics.fillRect(minimapLocation.getX(), minimapLocation.getY(), 3, 3);
    }

    /**
     * Get minimap dot color from client
     *
     * @param typeIndex index of minimap dot type (1 npcs, 2 players)
     * @return color
     */
    private Color getMinimapDotColor(int typeIndex)
    {
        final int pixel = client.getMapDots()[typeIndex].getPixels()[1];
        return new Color(pixel);
    }

    private void renderLadders(Graphics2D graphics, GameObject ladder)
    {
        net.runelite.api.Point minimapLocation = ladder.getMinimapLocation();

        if (minimapLocation == null)
        {
            return;
        }

        ObjectComposition objectComp = client.getObjectDefinition(ladder.getId());

        if (objectComp.getImpostorIds() != null && objectComp.getImpostor() != null)
        {
            graphics.setColor(Color.orange);
            graphics.fillRect(minimapLocation.getX(), minimapLocation.getY(), 6, 6);
        }
    }


}