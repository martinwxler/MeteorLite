package net.runelite.mixins;

import net.runelite.api.hooks.DrawCallbacks;
import net.runelite.api.mixins.Inject;
import net.runelite.api.mixins.MethodHook;
import net.runelite.api.mixins.Mixin;
import net.runelite.api.mixins.Shadow;
import net.runelite.rs.api.RSClient;
import net.runelite.rs.api.RSGameEngine;

@Mixin(RSGameEngine.class)
public abstract class GameEngine implements RSGameEngine{

    @Shadow("client")
    private static RSClient client;

    @Shadow("viewportColor")
    private static int viewportColor;

    @Inject
    @MethodHook("post")
    public void onPost(Object canvas)
    {
        DrawCallbacks drawCallbacks = client.getDrawCallbacks();
        if (drawCallbacks != null)
        {
            drawCallbacks.draw(viewportColor);
            viewportColor = 0;
        }
    }
}
