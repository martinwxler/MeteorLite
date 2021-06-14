package sponge.plugins;

import com.google.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.events.GameStateChanged;
import org.sponge.util.Logger;
import sponge.Plugin;
import sponge.eventbus.Subscribe;
import sponge.eventbus.events.GameMessageReceived;

import static org.sponge.util.Logger.ANSI_CYAN;
import static org.sponge.util.Logger.ANSI_YELLOW;

public class EventLoggerPlugin extends Plugin
{
    public String name = ANSI_CYAN + "EventLoggerPlugin" + ANSI_YELLOW;
    @Inject
    Logger logger;

    @Inject
    Client client;

    @Subscribe
    public void onGameStateChanged(GameStateChanged event)
    {
        logger.plugin = name;
        logger.event("GameStateChanged", "" +  event.getGameState());
        logger.plugin = null;
    }

    @Subscribe
    public void onGameMessageReceived(GameMessageReceived event)
    {
        logger.plugin = name;
        logger.event("GameMessageReceived", "" + event.text);
        logger.plugin = null;
    }
}
