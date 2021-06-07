package agent.mixins;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sponge.SpongeOSRS;

@SuppressWarnings("unused")
@Mixin(targets = "osrs/GameEngine", remap = false)
public class GameEngineMixin {
    private Thread thread;

    @Inject(method = "run", at = @At("HEAD"))
    public void onRun(CallbackInfo callbackInfo) {
        SpongeOSRS.logger.debug("Client thread obtained");
        thread = Thread.currentThread();
        thread.setName("Client");
    }
}
