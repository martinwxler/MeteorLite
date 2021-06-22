package net.runelite.mixins;

import net.runelite.api.mixins.Inject;
import net.runelite.api.mixins.Mixin;
import net.runelite.api.mixins.Shadow;
import net.runelite.rs.api.RSBoundaryObject;
import net.runelite.rs.api.RSClient;
import net.runelite.rs.api.RSFloorDecoration;

@Mixin(RSBoundaryObject.class)
public abstract class BoundaryObject implements RSBoundaryObject{

    @Shadow("client")
    private static RSClient client;

    @Inject
    private int boundaryObjectPlane;

    @Inject
    @Override
    public int getPlane()
    {
        return boundaryObjectPlane;
    }

    @Inject
    @Override
    public void setPlane(int plane)
    {
        this.boundaryObjectPlane = plane;
    }
}
