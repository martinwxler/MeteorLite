package agent.mixins.sponge;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("unused")
@Mixin(targets = "Client", remap = false)
public class MixinMixinMixin {

    @Inject(method = "onGameStateChanged", at = @At("RETURN"))
    private static void onOnGameStateChanged(int gamestate, CallbackInfo callbackInfo) {
        System.out.println("Mixed into the RuneLite mixin");
    }
}
