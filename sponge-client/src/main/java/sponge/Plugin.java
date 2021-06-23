package sponge;

import com.google.inject.Inject;
import net.runelite.api.Client;
import sponge.eventbus.EventBus;

import java.awt.*;

public class Plugin {

    @Inject
    Client client;

    @Inject
    EventBus eventBus;

    public void init()
    {
        eventBus.register(this);
    }

    public void onStartup()
    {

    }

    public void paintAboveScene(Graphics2D graphics2D)
    {

    }
}
