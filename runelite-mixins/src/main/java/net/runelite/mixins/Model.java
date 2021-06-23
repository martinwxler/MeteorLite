package net.runelite.mixins;

import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.Angle;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.mixins.Inject;
import net.runelite.api.mixins.Mixin;
import net.runelite.api.mixins.Shadow;
import net.runelite.api.model.Jarvis;
import net.runelite.rs.api.RSClient;
import net.runelite.rs.api.RSGameObject;
import net.runelite.rs.api.RSModel;
import net.runelite.rs.api.RSRenderable;

import java.awt.*;

@Mixin(RSModel.class)
public abstract class Model implements RSModel{

    @Shadow("client")
    private static RSClient client;

    @Override
    @Inject
    public Shape getConvexHull(int localX, int localY, int orientation, int tileHeight)
    {
        int[] x2d = new int[this.getVerticesCount()];
        int[] y2d = new int[this.getVerticesCount()];

        Perspective.modelToCanvas(client, this.getVerticesCount(), localX, localY, tileHeight, orientation, this.getVerticesX(), this.getVerticesZ(), this.getVerticesY(), x2d, y2d);

        return Jarvis.convexHull(x2d, y2d);
    }
}
