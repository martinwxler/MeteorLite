package sponge;

import org.sponge.util.Logger;
import sponge.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

public class SpongeOSRS {
    public static EventBus eventBus = new EventBus();
    public static Logger logger = new Logger("SpongeOSRS");

    public static List<Plugin> plugins = new ArrayList<>();
}
