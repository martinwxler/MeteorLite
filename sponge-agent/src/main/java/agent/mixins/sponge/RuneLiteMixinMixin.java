package agent.mixins.sponge;

import net.runelite.api.Client;
import net.runelite.rs.api.RSClient;
import org.sponge.util.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sponge.SpongeOSRS;

@SuppressWarnings("unused")
@Mixin(value = osrs.Client.class, remap = false)
public abstract class RuneLiteMixinMixin implements RSClient {

    @Shadow
    private static RSClient clientInstance;

    private static Client client;

    private static Logger spongeLogger = new Logger("Agent");

    //This is a test hook and also serves to init SpongeOSRS.client for other SpongeMixins (cant shadow across classes)
    @Inject(method = "onGameStateChanged", at = @At("RETURN"))
    private static void onOnGameStateChanged(int gamestate, CallbackInfo callbackInfo) {
        if (SpongeOSRS.client == null)
        {
            SpongeOSRS.client = clientInstance;
            client = SpongeOSRS.client;
        }

        spongeLogger.debug("GameState ValueOf: " + client.api$getRSGameState());
    }
}
