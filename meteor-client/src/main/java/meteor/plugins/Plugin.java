package meteor.plugins;

import com.google.inject.Binder;
import com.google.inject.Injector;
import com.google.inject.Module;
import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javax.inject.Inject;
import meteor.MeteorLite;
import meteor.config.Config;
import meteor.config.ConfigManager;
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
  private boolean enabled = true;
  private Config config;

  public Plugin() {
    logger.name = this.getClass().getAnnotation(PluginDescriptor.class).name();
  }

  public void startup() {
  }
  public void shutdown() {
  }
  public void toggle() {
    if (enabled)
    {
      shutdown();
      setEnabled(false);
    }
    else {
      startup();
      setEnabled(true);
    }
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

  public String getName() {
    return getClass().getAnnotation(PluginDescriptor.class).name();
  }

  public void showConfig() {
    Parent configRoot = null;
    try {
      configRoot = FXMLLoader.load(Objects.requireNonNull(ClassLoader.getSystemClassLoader()
          .getResource("plugin-config.fxml")));
    } catch (IOException e) {
      e.printStackTrace();
    }
    MeteorLite.updateRightPanel(configRoot);
  }

  public boolean isEnabled()
  {
    return enabled;
  }

  public void setEnabled(boolean enabled)
  {
    this.enabled = enabled;
  }

  public Config getConfig(ConfigManager configManager)
  {
    return config;
  }

  public void setConfig(Config config)
  {
    this.config = config;
  }
}
