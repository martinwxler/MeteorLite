package net.runelite.mixins;

import net.runelite.api.Perspective;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.mixins.Inject;
import net.runelite.api.mixins.Mixin;
import net.runelite.api.mixins.Shadow;
import net.runelite.rs.api.RSActor;
import net.runelite.rs.api.RSClient;

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
}
