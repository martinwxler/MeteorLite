package agent.mixins.sponge;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import sponge.Plugin;
import sponge.SpongeOSRS;
import sponge.plugins.EventLoggerPlugin;

import static agent.Mappings.updateGameStateClass;

@SuppressWarnings("unused")
@Mixin(targets = updateGameStateClass, remap = false)
public class PluginStartupMixin {

    private static boolean hasInit = false;

    @Inject(method = "updateGameState", at = @At("RETURN"))
    private static void gamestateChanged(int gamestate, CallbackInfo callbackInfo) {
        if (!hasInit) {
            SpongeOSRS.plugins.add(new EventLoggerPlugin());

            for (Plugin p : SpongeOSRS.plugins)
                p.init();

            hasInit = true;
        }
    }
}
