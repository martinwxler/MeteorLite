package net.runelite.mixins;

import net.runelite.api.Perspective;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ActorDeath;
import net.runelite.api.events.HealthBarUpdated;
import net.runelite.api.mixins.Inject;
import net.runelite.api.mixins.MethodHook;
import net.runelite.api.mixins.Mixin;
import net.runelite.api.mixins.Shadow;
import net.runelite.rs.api.*;

@Mixin(RSActor.class)
public abstract class ActorMixin implements RSActor{

    @Shadow("client")
    private static RSClient client;

    @Inject
    @Override
    public WorldPoint getWorldLocation()
    {
        return WorldPoint.fromLocal(client,
                this.getPathX()[0] * Perspective.LOCAL_TILE_SIZE + Perspective.LOCAL_TILE_SIZE / 2,
                this.getPathY()[0] * Perspective.LOCAL_TILE_SIZE + Perspective.LOCAL_TILE_SIZE / 2,
                client.getPlane());
    }

    @Inject
    @Override
    public int getHealthRatio()
    {
        RSIterableNodeDeque healthBars = getHealthBars();
        if (healthBars != null)
        {
            RSNode current = healthBars.getCurrent();
            RSNode next = current.getNext();
            if (next instanceof RSHealthBar)
            {
                RSHealthBar wrapper = (RSHealthBar) next;
                RSIterableNodeDeque updates = wrapper.getUpdates();

                RSNode currentUpdate = updates.getCurrent();
                RSNode nextUpdate = currentUpdate.getNext();
                if (nextUpdate instanceof RSHealthBarUpdate)
                {
                    RSHealthBarUpdate update = (RSHealthBarUpdate) nextUpdate;
                    return update.getHealthRatio();
                }
            }
        }
        return -1;
    }

    @Inject
    @Override
    public int getHealthScale()
    {
        RSIterableNodeDeque healthBars = getHealthBars();
        if (healthBars != null)
        {
            RSNode current = healthBars.getCurrent();
            RSNode next = current.getNext();
            if (next instanceof RSHealthBar)
            {
                RSHealthBar wrapper = (RSHealthBar) next;
                RSHealthBarDefinition definition = wrapper.getDefinition();
                return definition.getHealthScale();
            }
        }
        return -1;
    }
}
