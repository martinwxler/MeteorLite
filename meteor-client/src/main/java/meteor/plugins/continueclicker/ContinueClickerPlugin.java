package meteor.plugins.continueclicker;

import com.google.inject.Provides;
import meteor.callback.ClientThread;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.widgets.Dialog;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
@PluginDescriptor(
        name = "Continue Clicker",
        description = "Presses continue on dialogue when available",
        tags = {"continue", "chat", "dialogue", "clicker"},
        enabledByDefault = false
)
public class ContinueClickerPlugin extends Plugin {
    @Inject
    public Client client;

    @Inject
    public ClientThread clientThread;

    @Inject
    public ContinueClickerConfig config;

    @Provides
    public ContinueClickerConfig getConfig(ConfigManager configManager)
    {
        return configManager.getConfig(ContinueClickerConfig.class);
    }

    @Override
    public void startup() {
    }

    @Override
    public void shutdown() {
    }

    @Subscribe
    public void onGameTick(GameTick event) {
        if (config.continueChat()) {
            if (Dialog.canContinue()){
                Dialog.continueSpace();
            }
        }
        if (config.questHelper()) {
            if (Dialog.isViewingOptions()){
                    Dialog.chooseOption("[");
            }
        }
    }
}