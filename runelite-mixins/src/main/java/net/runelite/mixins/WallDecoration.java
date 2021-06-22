package net.runelite.mixins;

import net.runelite.api.mixins.Inject;
import net.runelite.api.mixins.Mixin;
import net.runelite.api.mixins.Shadow;
import net.runelite.rs.api.RSClient;
import net.runelite.rs.api.RSGameObject;
import net.runelite.rs.api.RSWallDecoration;

@Mixin(RSWallDecoration.class)
public abstract class WallDecoration implements RSWallDecoration{

    @Shadow("client")
    private static RSClient client;

    @Inject
    private int decorativeObjectPlane;

    @Inject
    @Override
    public int getPlane()
    {
        return decorativeObjectPlane;
    }

    @Inject
    @Override
    public void setPlane(int plane)
    {
        this.decorativeObjectPlane = plane;
    }
}
