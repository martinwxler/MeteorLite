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
import meteor.plugins.voidutils.OSRSUtils;
import meteor.plugins.voidutils.tasks.PriorityTask;
import meteor.plugins.voidutils.tasks.Task;
import meteor.plugins.voidutils.tasks.TaskSet;
import meteor.task.Scheduler;
import meteor.ui.components.PluginToggleButton;
import meteor.ui.controllers.PluginListUI;
import meteor.ui.overlay.OverlayManager;
import net.runelite.api.Client;
import org.sponge.util.Logger;

public class Plugin implements Module {

  public Logger logger = new Logger("");

  @Inject
  public Client client;

  @Inject
  public OSRSUtils osrs;

  @Inject
  public EventBus eventBus;

  @Getter @Setter
  public Injector injector;

  @Inject
  public Scheduler scheduler;

  @Getter @Setter
  public boolean enabled = false;

  @Getter @Setter
  private boolean running = false;

  @Setter
  private Config config;

  @Inject
  public OverlayManager overlayManager;

  public TaskSet tasks = new TaskSet(this);

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
      scheduler.unregister(this);
      eventBus.unregister(this);
      updateConfig();
    }
    else {
      startup();
      setEnabled(true);
      scheduler.register(this);
      eventBus.register(this);
      updateConfig();
    }

    PluginListUI.overrideToggleListener = true;
    for (PluginToggleButton ptb : PluginListUI.configGroupPluginMap.values()) {
      if (ptb.plugin == this) {
        ptb.setSelected(enabled);
      }
    }
    PluginListUI.overrideToggleListener = false;
  }


  // These should NOT be used as they are not called
  // This will create errors in plugins that try to use the protected variant
  public void startUp() {

  }

  public void shutDown() {

  }

  public <T extends Task> T getTask(Class<? extends Task> type) {
    for (Task t : tasks.tasks) {
      if (type.isInstance(t))
      {
        return (T) t;
      }
    }
    return null;
  }

  public <T extends PriorityTask> T getPriorityTask(Class<? extends Task> type) {
    for (Task t : tasks.tasks) {
      if (type.isInstance(t))
      {
        return (T) t;
      }
    }
    return null;
  }

}