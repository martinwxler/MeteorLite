package meteor.plugins.api.plugin.interaction;

import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.entities.Players;
import meteor.plugins.api.entities.TileObjects;
import meteor.plugins.api.items.Inventory;
import net.runelite.api.GameState;
import net.runelite.api.Item;
import net.runelite.api.TileObject;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.ScheduledExecutorService;

@PluginDescriptor(
        name = "Interact debug",
        description = "Weed",
        enabledByDefault = false
)
@Singleton
public class InteractPlugin extends Plugin {
    @Inject
    private InteractConfig config;

    @Inject
    private ScheduledExecutorService executorService;

    @Provides
    public InteractConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(InteractConfig.class);
    }

    @Subscribe
    public void onGameTick(GameTick e) {
        if (config.enabled()) {
            executorService.execute(() -> {
                Item logs = Inventory.getFirst("Logs");
                if (logs != null) {
                    logs.interact("Drop");
                }
            });
        }
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged e) {
        if (e.getGameState() == GameState.LOGIN_SCREEN) {
            client.setUsername("yarrakhasan69@protonmail.com");
            client.setPassword("112123");
        }
    }
}
