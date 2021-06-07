package agent.mixins;

import agent.Mappings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import osrs.Client;
import sponge.SpongeOSRS;

@Mixin(targets = Mappings.clientInstanceClass)
public class ClientInstanceMixin {
    @Shadow(remap = false)
    public static Client client;

    @Inject(method = "<init>", at = @At(value = "RETURN"), remap = false)
    public void onInit(CallbackInfo callbackInfo)
    {
        SpongeOSRS.clientInstance = client;
    }
}
