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
  private ConfigManager configManager;

  @Inject
  private ClientThread clientThread;

  @Inject
  private MeteorLiteLoginScreenConfig config;

  @Inject
  private ScheduledExecutorService executor;

  @Provides
  public MeteorLiteLoginScreenConfig getConfig(ConfigManager configManager) {
    return configManager.getConfig(MeteorLiteLoginScreenConfig.class);
  }

  @Override
  public void startup() {
    client.setLogoSprite(client.createIndexedSprite());

    executor.submit(() -> {
      clientThread.invokeLater(this::updateAllOverrides);
    });
  }

  @Override
  public void shutdown() {

  }

  @Subscribe
  public void onGameStateChanged(GameStateChanged e) {
    if (e.getGameState() == GameState.UNKNOWN || Game.isOnLoginScreen()) {
      IndexedSprite sprite = client.createIndexedSprite();
      client.setLogoSprite(sprite);

      IndexedSprite loginBoxSprite = ImageUtil.getImageIndexedSprite
      (ImageUtil.loadImageResource(MeteorLiteLoginScreenPlugin.class, "titlebox.png"), client);
      client.setLoginBoxSprite(loginBoxSprite);

      IndexedSprite loginButtonSprite = ImageUtil.getImageIndexedSprite
      (ImageUtil.loadImageResource(MeteorLiteLoginScreenPlugin.class, "titlebutton.png"), client);
      client.setLoginButtonSprite(loginButtonSprite);

      IndexedSprite worldButtonSprite = ImageUtil.getImageIndexedSprite
      (ImageUtil.loadImageResource(MeteorLiteLoginScreenPlugin.class, "sl_button.png"), client);
      client.setLoginWorldsButtonSprite(worldButtonSprite);

      IndexedSprite optionSprite = ImageUtil.getImageIndexedSprite
      (ImageUtil.loadImageResource(MeteorLiteLoginScreenPlugin.class, "options_radio_buttons4.png"), client);
      client.setOptionSprite(optionSprite);

      IndexedSprite optionSprite1 = ImageUtil.getImageIndexedSprite
      (ImageUtil.loadImageResource(MeteorLiteLoginScreenPlugin.class, "options_radio_buttons4.png"), client);
      client.setOptionSprite1(optionSprite1);

      IndexedSprite optionSprite2 = ImageUtil.getImageIndexedSprite
      (ImageUtil.loadImageResource(MeteorLiteLoginScreenPlugin.class, "mod_icon.png"), client);
      client.setOptionSprite2(optionSprite2);

      IndexedSprite optionSprite3 = ImageUtil.getImageIndexedSprite
      (ImageUtil.loadImageResource(MeteorLiteLoginScreenPlugin.class, "mod_icon.png"), client);
      client.setOptionSprite3(optionSprite3);

      client.getTitleMuteSprites()[0] = ImageUtil.getImageIndexedSprite
      (ImageUtil.loadImageResource(MeteorLiteLoginScreenPlugin.class, "title_mute.png"), client);
      client.getTitleMuteSprites()[1] = ImageUtil.getImageIndexedSprite
      (ImageUtil.loadImageResource(MeteorLiteLoginScreenPlugin.class, "title_mute.1.png"), client);

    }
    if (e.getGameState() != GameState.LOGIN_SCREEN) {
      return;
    }
  }

  void updateAllOverrides() {
    overrideSprites();
  }

  void overrideSprites() {
    SpritePixels loginscreensprite = ImageUtil.getImageSpritePixels(ImageUtil.loadImageResource(
        MeteorLiteLoginScreenPlugin.class, "background.png"), client);
    if (loginscreensprite != null) {
      client.setLoginScreen(loginscreensprite);
    }
  }
}
