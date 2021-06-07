package sponge;

import osrs.Client;
import sponge.eventbus.EventBus;
import sponge.logger.Logger;

import java.util.ArrayList;
import java.util.List;

public class SpongeOSRS {
    public static EventBus eventBus = new EventBus();
    public static Logger logger = new Logger();

    public static List<Plugin> plugins = new ArrayList<>();
    public static Client clientInstance;
}
