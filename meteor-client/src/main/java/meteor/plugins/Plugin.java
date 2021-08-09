package meteor.plugins;

import com.google.inject.Binder;
import com.google.inject.Injector;
import com.google.inject.Module;
import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javax.inject.Inject;
import lombok.Getter;
import lombok.Setter;
import meteor.MeteorLiteClientModule;
import meteor.config.Config;
import meteor.config.ConfigManager;
import meteor.eventbus.EventBus;
import meteor.task.Scheduler;
import net.runelite.api.Client;
import org.sponge.util.Logger;

public class Plugin implements Module {

  public Logger logger = new Logger("");

  @Inject
  public Client client;

  @Inject
  public EventBus eventBus;

  @Getter @Setter
  private Injector injector;

  @Inject
  public Scheduler scheduler;

  @Getter @Setter
  private boolean enabled = false;

  @Setter
  private Config config;

  public Plugin() {
    logger.name = this.getClass().getAnnotation(PluginDescriptor.class).name();
  }

  public void startup() { }
  public void shutdown() { }
  
  public void updateConfig() { }
  public void resetConfiguration(){ }

  @Override
  public void configure(Binder binder) { }

  public String getName() {
    return getClass().getAnnotation(PluginDescriptor.class).name();
  }

  public Config getConfig(ConfigManager configManager)
  {
    return config;
  }

  public void showConfig() {
    Parent configRoot = null;
    try {
      configRoot = FXMLLoader.load(Objects.requireNonNull(ClassLoader.getSystemClassLoader()
              .getResource("plugin-config.fxml")));
    } catch (IOException e) {
      e.printStackTrace();
    }
    MeteorLiteClientModule.updateRightPanel(configRoot, 370);
  }

  public void toggle() {
    if (enabled)
    {
      shutdown();
      setEnabled(false);
      unschedule(this);
      eventBus.unregister(this);
    }
    else {
      startup();
      setEnabled(true);
      schedule(this);
      eventBus.register(this);
    }
  }

  private void schedule(Plugin plugin)
  {
    // note to devs: this method will almost certainly merge conflict in the future, just apply the changes in the scheduler instead
    scheduler.registerObject(plugin);
  }

  private void unschedule(Plugin plugin)
  {
    // note to devs: this method will almost certainly merge conflict in the future, just apply the changes in the scheduler instead
    scheduler.unregisterObject(plugin);
  }
}