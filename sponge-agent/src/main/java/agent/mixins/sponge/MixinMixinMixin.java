package agent.mixins.sponge;

import net.runelite.rs.api.RSClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("unused")
@Mixin(targets = "Client", remap = false)
public abstract class MixinMixinMixin implements RSClient {

    @Shadow
    public static RSClient clientInstance;

    @Inject(method = "onGameStateChanged", at = @At("RETURN"))
    private static void onOnGameStateChanged(int gamestate, CallbackInfo callbackInfo) {
        if (clientInstance != null)
            System.out.println(clientInstance.api$getRSGameState());
        System.out.println("Mixed into the RuneLite mixin");
    }
}
