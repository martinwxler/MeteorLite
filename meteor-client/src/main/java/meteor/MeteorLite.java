package meteor;

import net.runelite.api.Client;
import org.sponge.util.Logger;
import meteor.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class MeteorLite {
    public static EventBus eventBus = new EventBus();
    public static Logger logger = new Logger("MeteorLite");

    public static List<Plugin> plugins = new ArrayList<>();
    public static Client client;
}
