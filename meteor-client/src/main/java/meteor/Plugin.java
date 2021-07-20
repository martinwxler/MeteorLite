package meteor;

import com.google.inject.Binder;
import com.google.inject.Injector;
import com.google.inject.Module;
import net.runelite.api.Client;
import org.sponge.util.Logger;
import meteor.eventbus.EventBus;
import meteor.input.MouseManager;

import javax.inject.Inject;
import java.awt.*;

public class Plugin implements Module {

    public Logger logger = new Logger("");

    @Inject
    public Client client;

    @Inject
    public EventBus eventBus;

    @Inject
    public MouseManager mouseManager;
    private Injector injector;

    public void init()
    {
        eventBus.register(this);
    }

    public void startup()
    {

    }

    public void shutdown()
    {

    }

    public Injector getInjector() {
        return injector;
    }

    public void setInjector(Injector injector)
    {
        this.injector = injector;
    }

    @Override
    public void configure(Binder binder) {

    }

    public void paintAboveScene(Graphics2D graphics2D)
    {

    }

    public void paintAlwaysOnTop(Graphics2D graphics2d)
    {

    }
}
