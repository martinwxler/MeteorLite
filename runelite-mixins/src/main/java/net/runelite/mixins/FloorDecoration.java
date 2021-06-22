package net.runelite.mixins;

import net.runelite.api.mixins.Inject;
import net.runelite.api.mixins.Mixin;
import net.runelite.api.mixins.Shadow;
import net.runelite.rs.api.RSClient;
import net.runelite.rs.api.RSFloorDecoration;
import net.runelite.rs.api.RSWallDecoration;

@Mixin(RSFloorDecoration.class)
public abstract class FloorDecoration implements RSFloorDecoration{

    @Shadow("client")
    private static RSClient client;

    @Inject
    private int groundObjectPlane;

    @Inject
    @Override
    public int getPlane()
    {
        return groundObjectPlane;
    }

    @Inject
    @Override
    public void setPlane(int plane)
    {
        this.groundObjectPlane = plane;
    }
}
