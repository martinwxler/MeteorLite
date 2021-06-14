package sponge;

import com.google.inject.Inject;
import net.runelite.api.Client;
import sponge.eventbus.EventBus;

public class Plugin {

    @Inject
    Client client;

    @Inject
    EventBus eventBus;

    public void init()
    {
        eventBus.register(this);
    }
}
