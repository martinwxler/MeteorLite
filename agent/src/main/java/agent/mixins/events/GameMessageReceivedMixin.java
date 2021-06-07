package agent.mixins.events;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sponge.SpongeOSRS;
import sponge.eventbus.events.GameMessageReceived;

import static agent.Mappings.addGameMessageClass;

@SuppressWarnings("unused")
@Mixin(targets = addGameMessageClass, remap = false)
public class GameMessageReceivedMixin {
    @Inject(method = "addGameMessage", at = @At("RETURN"))
    private static void onGameMessage(int var0, String var1, String var2, CallbackInfo callbackInfo) {
        GameMessageReceived gameMessageReceived = new GameMessageReceived(var2);
        SpongeOSRS.eventBus.post(gameMessageReceived);
    }
}
