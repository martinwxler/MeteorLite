package meteor.plugins.specbar;

import com.google.inject.Provides;
import net.runelite.api.*;
import net.runelite.api.events.ClientTick;
import net.runelite.api.widgets.Widget;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.config.ConfigManager;
import net.runelite.api.widgets.WidgetID;
import javax.inject.Inject;

@PluginDescriptor(
        name = "Spec Bar",
        description = "Adds a permanent spec bar",
        tags = {"spec", "bar", "permanent"},
        enabledByDefault = false)

public class SpecBarPlugin extends Plugin
{

    @Inject
    private Client client;

    @Inject
    private SpecBarConfig config;

    @Inject
    private ConfigManager configManager;

    @Provides
    public SpecBarConfig getConfig(ConfigManager configManager)
    {
        return configManager.getConfig(SpecBarConfig.class);
    }

    @Subscribe
    private void onClientTick(ClientTick event)
    {
        Widget specbarWidget = client.getWidget(WidgetID.COMBAT_GROUP_ID, 35);
        if (specbarWidget != null)
        {
            specbarWidget.setHidden(false);
        }
    }
}
