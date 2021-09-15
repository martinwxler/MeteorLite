package meteor.plugins.meteorlite;

import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.events.ExternalsReloaded;
import meteor.input.MouseManager;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.game.Game;
import meteor.plugins.meteorlite.interaction.InteractionManager;
import meteor.plugins.meteorlite.interaction.InteractionOverlay;
import meteor.plugins.meteorlite.loginscreen.MeteorLiteLoginScreen;
import net.runelite.api.events.ConfigButtonClicked;
import org.sponge.util.Logger;

import javax.inject.Inject;


@PluginDescriptor(
        name = "MeteorLite",
        cantDisable = true
)
public class MeteorLitePlugin extends Plugin {

    @Inject
    private MeteorLiteConfig config;

    @Inject
    private InteractionManager interactionManager;

    @Inject
    private InteractionOverlay interactionOverlay;

    @Inject
    private MeteorLiteLoginScreen meteorLiteLoginScreen;

    @Inject
    private MouseManager mouseManager;

    @Override
    public void updateConfig() {
        Logger.setDebugEnabled(config.debugEnabled());

        if (config.meteorLoginScreen()) {
            meteorLiteLoginScreen.customize();
        } else {
            meteorLiteLoginScreen.reset();
        }
    }

    @Override
    public void startup() {
        overlayManager.add(interactionOverlay);
        mouseManager.registerMouseListener(interactionOverlay);
        eventBus.register(interactionManager);
        eventBus.register(meteorLiteLoginScreen);

        if (config.externalsLoadOnStartup()) {
            Game.getClient().getCallbacks().post(new ExternalsReloaded());
        }
    }

    @Override
    public void shutdown() {
        overlayManager.remove(interactionOverlay);
        mouseManager.unregisterMouseListener(interactionOverlay);
        eventBus.unregister(interactionManager);
        eventBus.unregister(meteorLiteLoginScreen);
    }

    @Subscribe
    public void onConfigButtonClicked(ConfigButtonClicked event) {
        if (!event.getGroup().equals("MeteorLite")) {
            return;
        }
        if (!event.getKey().equals("reloadExternals")) {
            return;
        }

        Game.getClient().getCallbacks().post(new ExternalsReloaded());
    }

    @Provides
    public MeteorLiteConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(MeteorLiteConfig.class);
    }
}
