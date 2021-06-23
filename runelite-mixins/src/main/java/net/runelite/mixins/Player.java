package net.runelite.mixins;

import net.runelite.api.Perspective;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.NpcChanged;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.mixins.FieldHook;
import net.runelite.api.mixins.Inject;
import net.runelite.api.mixins.Mixin;
import net.runelite.api.mixins.Shadow;
import net.runelite.rs.api.*;

import java.awt.*;

@Mixin(RSPlayer.class)
public abstract class Player implements RSPlayer{

    @Shadow("client")
    private static RSClient client;

    @Inject
    @Override
    public Shape getConvexHull()
    {
        RSModel model = getModel$api();
        if (model == null)
        {
            return null;
        }

        int tileHeight = Perspective.getTileHeight(client, new LocalPoint(getX(), getY()), client.getPlane());

        return model.getConvexHull(getX(), getY(), getOrientation(), tileHeight);
    }
}
