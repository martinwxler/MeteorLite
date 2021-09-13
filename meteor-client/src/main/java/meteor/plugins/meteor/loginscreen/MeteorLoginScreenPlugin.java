package meteor.plugins.meteor.loginscreen;

import com.google.inject.Provides;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import javax.inject.Inject;

import meteor.callback.ClientThread;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.eventbus.events.ConfigChanged;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.game.Game;
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
public class MeteorLoginScreenPlugin extends Plugin {
	@Inject
	private ClientThread clientThread;

	@Inject
	private ScheduledExecutorService executor;

	private static final Map<String, IndexedSprite> DEFAULT_SPRITES = new HashMap<>();
	private SpritePixels defaultLoginScreen;

	@Provides
	public MeteorLoginScreenConfig getConfig(ConfigManager configManager) {
		return configManager.getConfig(MeteorLoginScreenConfig.class);
	}

	@Override
	public void startup() {
		client.setLoginTitleColor(0x00FFFF);
		client.setLoginTitleMessage("Welcome to MeteorLite");
		executor.submit(() -> clientThread.invokeLater(this::overrideSprites));
	}

	@Override
	public void shutdown() {
		executor.submit(() -> clientThread.invokeLater(this::resetLoginScreen));
		client.setLoginTitleColor(16776960);
		client.setLoginTitleMessage("Welcome to RuneScape");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged e) {
		if (e.getGameState() == GameState.UNKNOWN || e.getGameState() == GameState.LOGGING_IN || Game.isOnLoginScreen()) {
			cacheDefaultSprites();
			client.setLogoSprite(getIndexedSprite("logo.png"));
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

	private IndexedSprite getIndexedSprite(String imageName) {
		return ImageUtil.getImageIndexedSprite(
						ImageUtil.loadImageResource(MeteorLoginScreenPlugin.class, imageName),
						client
		);
	}

	private void overrideSprites() {
		SpritePixels loginscreensprite = ImageUtil.getImageSpritePixels(ImageUtil.loadImageResource(
						MeteorLoginScreenPlugin.class, "background.png"), client);
		if (loginscreensprite != null) {
			client.setLoginScreen(loginscreensprite);
		}
	}

	private void resetLoginScreen() {
		client.setLoginScreen(defaultLoginScreen);
		client.setLogoSprite(DEFAULT_SPRITES.get("logosprite"));
		client.setLoginBoxSprite(DEFAULT_SPRITES.get("loginbox"));
		client.setLoginButtonSprite(DEFAULT_SPRITES.get("loginbutton"));
		client.setLoginWorldsButtonSprite(DEFAULT_SPRITES.get("worldsbutton"));
		client.setOptionSprite(DEFAULT_SPRITES.get("optionsprite"));
		client.setOptionSprite1(DEFAULT_SPRITES.get("optionsprite1"));
		client.setOptionSprite2(DEFAULT_SPRITES.get("optionsprite2"));
		client.setOptionSprite3(DEFAULT_SPRITES.get("optionsprite3"));
		client.getTitleMuteSprites()[0] = DEFAULT_SPRITES.get("mutesprite");
		client.getTitleMuteSprites()[1] = DEFAULT_SPRITES.get("mutesprite1");
	}

	private void cacheDefaultSprites() {
		defaultLoginScreen = ImageUtil.getImageSpritePixels(ImageUtil.loadImageResource(
				MeteorLoginScreenPlugin.class, "normal.jpg"), client);
		DEFAULT_SPRITES.putIfAbsent("logosprite", client.getLogoSprite());
		DEFAULT_SPRITES.putIfAbsent("loginbox", client.getLoginBoxSprite());
		DEFAULT_SPRITES.putIfAbsent("loginbutton", client.getLoginButtonSprite());
		DEFAULT_SPRITES.putIfAbsent("worldsbutton", client.getLoginWorldsButtonSprite());
		DEFAULT_SPRITES.putIfAbsent("optionsprite", client.getOptionSprite());
		DEFAULT_SPRITES.putIfAbsent("optionsprite1", client.getOptionSprite1());
		DEFAULT_SPRITES.putIfAbsent("optionsprite2", client.getOptionSprite2());
		DEFAULT_SPRITES.putIfAbsent("optionsprite3", client.getOptionSprite3());
		DEFAULT_SPRITES.putIfAbsent("mutesprite", client.getTitleMuteSprites()[0]);
		DEFAULT_SPRITES.putIfAbsent("mutesprite1", client.getTitleMuteSprites()[1]);
	}
}
