package meteor.plugins.api.externals;

import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.events.ExternalsReloaded;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.game.Game;
import net.runelite.api.events.ConfigButtonClicked;

@PluginDescriptor(name = "External Manager")
public class ExternalManagerPlugin extends Plugin {
	@Provides
	public ExternalManagerConfig getConfig(ConfigManager configManager) {
		return configManager.getConfig(ExternalManagerConfig.class);
	}

	@Subscribe
	public void onConfigButtonClicked(ConfigButtonClicked event) {
		if (event.getGroup().equals("externalmanager")) {
			if (event.getKey().equals("reload")) {
				Game.getClient().getCallbacks().post(new ExternalsReloaded());
			}
		}
	}
}
