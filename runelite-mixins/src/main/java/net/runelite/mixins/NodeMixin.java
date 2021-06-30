package net.runelite.mixins;

import net.runelite.api.mixins.Inject;
import net.runelite.api.mixins.MethodHook;
import net.runelite.api.mixins.Mixin;
import net.runelite.api.mixins.Shadow;
import net.runelite.rs.api.RSClient;
import net.runelite.rs.api.RSNode;
import net.runelite.rs.api.RSWallDecoration;

@Mixin(RSNode.class)
public abstract class NodeMixin implements RSNode{

    @Inject
    public void onUnlink()
    {
    }

    @Inject
    @MethodHook("remove")
    public void rl$unlink()
    {
        onUnlink();
    }
}
