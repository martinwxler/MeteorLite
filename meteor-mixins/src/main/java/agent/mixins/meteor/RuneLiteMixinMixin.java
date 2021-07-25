package agent.mixins.meteor;

import meteor.MeteorLite;
import net.runelite.api.Client;
import net.runelite.rs.api.RSClient;
import org.sponge.util.Logger;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@SuppressWarnings("unused")
@Mixin(targets = "Client", remap = false)
public abstract class RuneLiteMixinMixin implements RSClient {

  @Shadow
  private static RSClient clientInstance;

  private static Client client;

  private static Logger spongeLogger = new Logger("Agent");

  //This is a test hook and also serves to init MeteorLite.client for other SpongeMixins (cant shadow across classes)
  @Inject(method = "onGameStateChanged", at = @At("RETURN"), require = 1)
  private static void onOnGameStateChanged(int gamestate, CallbackInfo callbackInfo) {
    if (client == null) {
      client = MeteorLite.clientInstance = clientInstance;
    }
  }
}
