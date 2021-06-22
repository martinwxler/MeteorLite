package net.runelite.mixins;

import net.runelite.api.mixins.*;
import net.runelite.rs.api.RSClient;
import net.runelite.rs.api.RSRasterProvider;

import java.awt.*;
import java.nio.IntBuffer;

@Mixin(RSRasterProvider.class)
public abstract class RasterProvider implements RSRasterProvider{

    @Shadow("client")
    private static RSClient client;

    @Inject
    private IntBuffer buffer;

    @Inject
    @Copy("drawFull0")
    final void draw$copy(Graphics graphics, int x, int y)
    {
    }
    /**
     * Replacing this method makes it so we can completely
     * control when/what is drawn on the game's canvas,
     * as the method that is replaced draws
     * the game's image on the canvas.
     */
    @Replace("drawFull0")
    final void draw(Graphics graphics, int x, int y)
    {
        draw$copy(graphics, x, y);
        client.getCallbacks().draw(this, graphics, x, y);
    }
}
