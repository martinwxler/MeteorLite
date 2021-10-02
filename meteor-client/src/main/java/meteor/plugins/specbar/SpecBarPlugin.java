package meteor.plugins.specbar;

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
    private ConfigManager configManager;


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
