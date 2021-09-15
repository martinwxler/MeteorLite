package meteor.plugins.meteor.meteorlite;

import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import net.runelite.api.events.GameTick;
import org.sponge.util.Logger;

import javax.inject.Inject;


@PluginDescriptor(
        name = "MeteorLite",
        cantDisable = true
)
public class MeteorLitePlugin extends Plugin {

    @Inject
    private MeteorLiteConfig config;

    @Override
    public void updateConfig() {
        Logger.setDebugEnabled(config.debugEnabled());
    }

    @Provides
    public MeteorLiteConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(MeteorLiteConfig.class);
    }
}
