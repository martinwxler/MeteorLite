package net.runelite.mixins;

import net.runelite.api.Perspective;
import net.runelite.api.hooks.DrawCallbacks;
import net.runelite.api.mixins.*;
import net.runelite.rs.api.RSClient;
import net.runelite.rs.api.RSGameEngine;
import net.runelite.rs.api.RSScene;
import net.runelite.rs.api.RSTile;

@Mixin(RSScene.class)
public abstract class Scene implements RSScene{

    private static final int INVALID_HSL_COLOR = 12345678;
    private static final int DEFAULT_DISTANCE = 25;
    private static final int PITCH_LOWER_LIMIT = 128;
    private static final int PITCH_UPPER_LIMIT = 383;

    @Shadow("client")
    private static RSClient client;

    @Shadow("viewportColor")
    private static int viewportColor;

    @Shadow("skyboxColor")
    static int skyboxColor;

    @Inject
    private static int rl$drawDistance;

    @Replace("draw")
    void drawScene(int cameraX, int cameraY, int cameraZ, int cameraPitch, int cameraYaw, int plane)
    {
        final DrawCallbacks drawCallbacks = client.getDrawCallbacks();
        if (drawCallbacks != null)
        {
            drawCallbacks.drawScene(cameraX, cameraY, cameraZ, cameraPitch, cameraYaw, plane);
        }

        final boolean isGpu = client.isGpu();
        final boolean checkClick = client.isCheckClick();
        if (!client.isMenuOpen())
        {
            // Force check click to update the selected tile
            client.setCheckClick(true);
            final int mouseX = client.getMouseX();
            final int mouseY = client.getMouseY();
            client.setMouseCanvasHoverPositionX(mouseX - client.getViewportXOffset());
            client.setMouseCanvasHoverPositionY(mouseY - client.getViewportYOffset());
        }

        if (!isGpu)
        {
            if (skyboxColor != 0)
            {
                client.rasterizerFillRectangle(
                        client.getViewportXOffset(),
                        client.getViewportYOffset(),
                        client.getViewportWidth(),
                        client.getViewportHeight(),
                        skyboxColor
                );
            }
        }

        final int maxX = getMaxX();
        final int maxY = getMaxY();
        final int maxZ = getMaxZ();

        final int minLevel = getMinLevel();

        final RSTile[][][] tiles = getTiles();
        final int distance = isGpu ? rl$drawDistance : DEFAULT_DISTANCE;

        if (cameraX < 0)
        {
            cameraX = 0;
        }
        else if (cameraX >= maxX * Perspective.LOCAL_TILE_SIZE)
        {
            cameraX = maxX * Perspective.LOCAL_TILE_SIZE - 1;
        }

        if (cameraZ < 0)
        {
            cameraZ = 0;
        }
        else if (cameraZ >= maxZ * Perspective.LOCAL_TILE_SIZE)
        {
            cameraZ = maxZ * Perspective.LOCAL_TILE_SIZE - 1;
        }

        int realPitch = cameraPitch;

        client.setCycle(client.getCycle() + 1);

        client.setPitchSin(Perspective.SINE[realPitch]);
        client.setPitchCos(Perspective.COSINE[realPitch]);
        client.setYawSin(Perspective.SINE[cameraYaw]);
        client.setYawCos(Perspective.COSINE[cameraYaw]);

        final int[][][] tileHeights = client.getTileHeights();
        boolean[][] renderArea = client.getVisibilityMaps()[(cameraPitch - 128) / 32][cameraYaw / 64];
        client.setRenderArea(renderArea);

        client.setCameraX2(cameraX);
        client.setCameraY2(cameraY);
        client.setCameraZ2(cameraZ);

        int screenCenterX = cameraX / Perspective.LOCAL_TILE_SIZE;
        int screenCenterZ = cameraZ / Perspective.LOCAL_TILE_SIZE;

        client.setScreenCenterX(screenCenterX);
        client.setScreenCenterZ(screenCenterZ);
        client.setScenePlane(plane);

        int minTileX = screenCenterX - distance;
        if (minTileX < 0)
        {
            minTileX = 0;
        }

        int minTileZ = screenCenterZ - distance;
        if (minTileZ < 0)
        {
            minTileZ = 0;
        }

        int maxTileX = screenCenterX + distance;
        if (maxTileX > maxX)
        {
            maxTileX = maxX;
        }

        int maxTileZ = screenCenterZ + distance;
        if (maxTileZ > maxZ)
        {
            maxTileZ = maxZ;
        }

        client.setMinTileX(minTileX);
        client.setMinTileZ(minTileZ);
        client.setMaxTileX(maxTileX);
        client.setMaxTileZ(maxTileZ);

        updateOccluders();

        client.setTileUpdateCount(0);

        for (int z = minLevel; z < maxY; ++z)
        {
            RSTile[][] planeTiles = tiles[z];

            for (int x = minTileX; x < maxTileX; ++x)
            {
                for (int y = minTileZ; y < maxTileZ; ++y)
                {
                    RSTile tile = planeTiles[x][y];
                    if (tile != null)
                    {
                        if (tile.getPhysicalLevel() <= plane
                                && (isGpu
                                || renderArea[x - screenCenterX + DEFAULT_DISTANCE][y - screenCenterZ + DEFAULT_DISTANCE]
                                || tileHeights[z][x][y] - cameraY >= 2000))
                        {
                            tile.setDraw(true);
                            tile.setVisible(true);
                            tile.setDrawEntities(true);
                            client.setTileUpdateCount(client.getTileUpdateCount() + 1);
                        }
                        else
                        {
                            tile.setDraw(false);
                            tile.setVisible(false);
                            tile.setWallCullDirection(0);
                        }
                    }
                }
            }
        }

        for (int z = minLevel; z < maxY; ++z)
        {
            RSTile[][] planeTiles = tiles[z];

            for (int x = -distance; x <= 0; ++x)
            {
                int var10 = x + screenCenterX;
                int var16 = screenCenterX - x;
                if (var10 >= minTileX || var16 < maxTileX)
                {
                    for (int y = -distance; y <= 0; ++y)
                    {
                        int var13 = y + screenCenterZ;
                        int var14 = screenCenterZ - y;
                        if (var10 >= minTileX)
                        {
                            if (var13 >= minTileZ)
                            {
                                RSTile tile = planeTiles[var10][var13];
                                if (tile != null && tile.isDraw())
                                {
                                    draw(tile, true);
                                }
                            }

                            if (var14 < maxTileZ)
                            {
                                RSTile tile = planeTiles[var10][var14];
                                if (tile != null && tile.isDraw())
                                {
                                    draw(tile, true);
                                }
                            }
                        }

                        if (var16 < maxTileX)
                        {
                            if (var13 >= minTileZ)
                            {
                                RSTile tile = planeTiles[var16][var13];
                                if (tile != null && tile.isDraw())
                                {
                                    draw(tile, true);
                                }
                            }

                            if (var14 < maxTileZ)
                            {
                                RSTile tile = planeTiles[var16][var14];
                                if (tile != null && tile.isDraw())
                                {
                                    draw(tile, true);
                                }
                            }
                        }

                        if (client.getTileUpdateCount() == 0)
                        {
                            if (!isGpu && (client.getOculusOrbState() != 0 && !client.getComplianceValue("orbInteraction")))
                            {
                                client.setEntitiesAtMouseCount(0);
                            }
                            client.setCheckClick(false);
                            if (!checkClick)
                            {
                                client.setViewportWalking(false);
                            }
                            client.getCallbacks().drawScene();

                            if (client.getDrawCallbacks() != null)
                            {
                                client.getDrawCallbacks().postDrawScene();
                            }

                            return;
                        }
                    }
                }
            }
        }
        outer:
        for (int z = minLevel; z < maxY; ++z)
        {
            RSTile[][] planeTiles = tiles[z];

            for (int x = -distance; x <= 0; ++x)
            {
                int var10 = x + screenCenterX;
                int var16 = screenCenterX - x;
                if (var10 >= minTileX || var16 < maxTileX)
                {
                    for (int y = -distance; y <= 0; ++y)
                    {
                        int var13 = y + screenCenterZ;
                        int var14 = screenCenterZ - y;
                        if (var10 >= minTileX)
                        {
                            if (var13 >= minTileZ)
                            {
                                RSTile tile = planeTiles[var10][var13];
                                if (tile != null && tile.isDraw())
                                {
                                    draw(tile, false);
                                }
                            }

                            if (var14 < maxTileZ)
                            {
                                RSTile tile = planeTiles[var10][var14];
                                if (tile != null && tile.isDraw())
                                {
                                    draw(tile, false);
                                }
                            }
                        }

                        if (var16 < maxTileX)
                        {
                            if (var13 >= minTileZ)
                            {
                                RSTile tile = planeTiles[var16][var13];
                                if (tile != null && tile.isDraw())
                                {
                                    draw(tile, false);
                                }
                            }

                            if (var14 < maxTileZ)
                            {
                                RSTile tile = planeTiles[var16][var14];
                                if (tile != null && tile.isDraw())
                                {
                                    draw(tile, false);
                                }
                            }
                        }

                        if (client.getTileUpdateCount() == 0)
                        {
                            // exit the loop early and go straight to "if (!isGpu && (client..."
                            break outer;
                        }
                    }
                }
            }
        }

        if (!isGpu && (client.getOculusOrbState() != 0 && !client.getComplianceValue("orbInteraction")))
        {
            client.setEntitiesAtMouseCount(0);
        }
        client.setCheckClick(false);
        if (!checkClick)
        {
            // If checkClick was false, then the selected tile wouldn't have existed next tick,
            // so clear viewport walking in order to prevent it triggering a walk
            client.setViewportWalking(false);
        }
        client.getCallbacks().drawScene();
        if (client.getDrawCallbacks() != null)
        {
            client.getDrawCallbacks().postDrawScene();
        }
    }
}
