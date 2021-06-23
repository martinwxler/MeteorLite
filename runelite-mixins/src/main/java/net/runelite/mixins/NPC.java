package net.runelite.mixins;

import net.runelite.api.Perspective;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.NpcChanged;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.mixins.FieldHook;
import net.runelite.api.mixins.Inject;
import net.runelite.api.mixins.Mixin;
import net.runelite.api.mixins.Shadow;
import net.runelite.rs.api.RSClient;
import net.runelite.rs.api.RSModel;
import net.runelite.rs.api.RSNPC;
import net.runelite.rs.api.RSNPCComposition;

import java.awt.*;

@Mixin(RSNPC.class)
public abstract class NPC implements RSNPC{

    @Shadow("client")
    private static RSClient client;

    @Inject
    private int npcIndex;

    @Inject
    @Override
    public int getIndex()
    {
        return npcIndex;
    }

    @Inject
    @Override
    public void setIndex(int id)
    {
        npcIndex = id;
    }

    @Inject
    @Override
    public String getName()
    {
        RSNPCComposition composition = getComposition();
        if (composition != null && composition.getConfigs() != null)
        {
            composition = composition.transform$api();
        }
        return composition == null ? null : composition.getName().replace('\u00A0', ' ');
    }

    @FieldHook(value = "definition", before = true)
    @Inject
    public void onDefinitionChanged(RSNPCComposition composition)
    {
        if (composition == null)
        {
            client.getCallbacks().post(new NpcDespawned(this));
        }
        else if (this.getId() != -1)
        {
            RSNPCComposition oldComposition = getComposition();
            if (oldComposition == null)
            {
                return;
            }

            if (composition.getId() == oldComposition.getId())
            {
                return;
            }

            client.getCallbacks().postDeferred(new NpcChanged(this, oldComposition));
        }
    }

    @Inject
    @Override
    public int getId()
    {
        RSNPCComposition composition = getComposition();
        if (composition != null && composition.getConfigs() != null)
        {
            composition = composition.transform$api();
        }
        return composition == null ? -1 : composition.getId();
    }

    @Inject
    @Override
    public Shape getConvexHull()
    {
        RSModel model = getModel$api();
        if (model == null)
        {
            return null;
        }

        int size = getComposition().getSize();
        LocalPoint tileHeightPoint = new LocalPoint(
                size * Perspective.LOCAL_HALF_TILE_SIZE - Perspective.LOCAL_HALF_TILE_SIZE + getX(),
                size * Perspective.LOCAL_HALF_TILE_SIZE - Perspective.LOCAL_HALF_TILE_SIZE + getY());

        int tileHeight = Perspective.getTileHeight(client, tileHeightPoint, client.getPlane());

        return model.getConvexHull(getX(), getY(), getOrientation(), tileHeight);
    }
}
