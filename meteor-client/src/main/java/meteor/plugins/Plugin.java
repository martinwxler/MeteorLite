package meteor.plugins;

import com.google.inject.Binder;
import com.google.inject.Injector;
import com.google.inject.Module;
import java.awt.Graphics2D;
import javax.inject.Inject;
import meteor.eventbus.EventBus;
import net.runelite.api.Client;
import org.sponge.util.Logger;

public class Plugin implements Module {

  public Logger logger = new Logger("");

  @Inject
  public Client client;

  @Inject
  public EventBus eventBus;

  private Injector injector;

  public void startup() {

  }

  public void shutdown() {

  }

  public Injector getInjector() {
    return injector;
  }

  public void setInjector(Injector injector) {
    this.injector = injector;
  }

  @Override
  public void configure(Binder binder) {

  }

  public String getName()
  {
    return getClass().getAnnotation(PluginDescriptor.class).name();
  }
}
