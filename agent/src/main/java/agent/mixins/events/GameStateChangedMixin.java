package agent.mixins.events;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sponge.SpongeOSRS;
import sponge.eventbus.events.GameStateChanged;

import static agent.Mappings.updateGameStateClass;

@SuppressWarnings("unused")
@Mixin(targets = updateGameStateClass, remap = false)
public class GameStateChangedMixin {

    @Inject(method = "updateGameState", at = @At("RETURN"))
    private static void gamestateChanged(int gamestate, CallbackInfo callbackInfo) {
        GameStateChanged gameStateChanged = new GameStateChanged(gamestate);
        SpongeOSRS.eventBus.post(gameStateChanged);
    }
}
