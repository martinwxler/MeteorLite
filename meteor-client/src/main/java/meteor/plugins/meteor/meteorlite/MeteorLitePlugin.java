package meteor.plugins.meteor.meteorlite;

import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;

import javax.inject.Inject;


@PluginDescriptor(
        name = "MeteorLite",
        cantDisable = true,
        enabledByDefault = true
)
public class MeteorLitePlugin extends Plugin {

    @Inject
    private MeteorLiteConfig config;

    @Provides
    public MeteorLiteConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(MeteorLiteConfig.class);
    }
}
