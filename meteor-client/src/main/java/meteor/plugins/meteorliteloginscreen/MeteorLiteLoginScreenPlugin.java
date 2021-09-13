package meteor.plugins.meteorliteloginscreen;

import com.google.inject.Provides;
import java.util.concurrent.ScheduledExecutorService;
import javax.inject.Inject;
import meteor.callback.ClientThread;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.game.Game;
import meteor.plugins.resourcepacks.ResourcePacksPlugin;
import meteor.util.ImageUtil;
import net.runelite.api.GameState;
import net.runelite.api.IndexedSprite;
import net.runelite.api.SpritePixels;
import net.runelite.api.events.GameStateChanged;

@PluginDescriptor(
    name = "MeteorLite Login Screen",
    description = "Change the look of the client",
    enabledByDefault = false
)

public class MeteorLiteLoginScreenPlugin extends Plugin {

  @Inject
  private ClientThread clientThread;

  @Inject
  private ScheduledExecutorService executor;

  @Provides
  public MeteorLiteLoginScreenConfig getConfig(ConfigManager configManager) {
    return configManager.getConfig(MeteorLiteLoginScreenConfig.class);
  }

  @Override
  public void startup() {
    executor.submit(() -> {
      clientThread.invokeLater(this::overrideSprites);
    });
  }

  private IndexedSprite getIndexedSprite(String imageName) {
    return ImageUtil.getImageIndexedSprite(
        ImageUtil.loadImageResource(MeteorLiteLoginScreenPlugin.class, imageName),
        client
    );
  }

  @Subscribe
  public void onGameStateChanged(GameStateChanged e) {
    if (e.getGameState() == GameState.UNKNOWN || e.getGameState() == GameState.LOGGING_IN || Game.isOnLoginScreen())
    {
        client.setLogoSprite(client.createIndexedSprite());
        client.setLoginBoxSprite(getIndexedSprite("titlebox.png"));
        client.setLoginButtonSprite(getIndexedSprite("titlebutton.png"));
        client.setLoginWorldsButtonSprite(getIndexedSprite("sl_button.png"));
        client.setOptionSprite(getIndexedSprite("options_radio_buttons4.png"));
        client.setOptionSprite1(getIndexedSprite("options_radio_buttons4.png"));
        client.setOptionSprite2(getIndexedSprite("mod_icon.png"));
        client.setOptionSprite3(getIndexedSprite("mod_icon.png"));
        client.getTitleMuteSprites()[0] = getIndexedSprite("title_mute.png");
        client.getTitleMuteSprites()[1] = getIndexedSprite("title_mute.1.png");
    }
  }

  private void overrideSprites() {
    SpritePixels loginscreensprite = ImageUtil.getImageSpritePixels(ImageUtil.loadImageResource(
        MeteorLiteLoginScreenPlugin.class, "background.png"), client);
    if (loginscreensprite != null) {
      client.setLoginScreen(loginscreensprite);
    }
  }
}
