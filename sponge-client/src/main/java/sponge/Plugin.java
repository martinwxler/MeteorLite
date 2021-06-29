package sponge;

import net.runelite.api.Client;
import org.sponge.util.Logger;
import sponge.eventbus.EventBus;
import sponge.input.MouseManager;

import javax.inject.Inject;
import java.awt.*;

public class Plugin {

    public Logger logger = new Logger("");

    @Inject
    public Client client;

    @Inject
    public EventBus eventBus;

    @Inject
    public MouseManager mouseManager;

    public void init()
    {
        eventBus.register(this);
    }

    public void onStartup()
    {

    }

    public void shutdown()
    {

    }

    public void paintAboveScene(Graphics2D graphics2D)
    {

    }
}
