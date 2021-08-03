package meteor.plugins.actions;

import javax.inject.Inject;
import meteor.MeteorLite;
import meteor.input.KeyManager;
import meteor.input.MouseManager;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;

@PluginDescriptor(
    name = "Actions",
    description = "Handles actions"
)
public class ActionPlugin extends Plugin {

  @Inject
  private ActionListener inputListener;

  @Inject
  private MouseManager mouseManager;

  @Inject
  private KeyManager keyManager;

  @Override
  public void startup() {
    MeteorLite.injector.injectMembers(inputListener);
    eventBus.register(inputListener);
    mouseManager.registerMouseListener(inputListener);
    keyManager.registerKeyListener(inputListener, this.getClass());
  }

  @Override
  public void shutdown() {
    mouseManager.unregisterMouseListener(inputListener);
    keyManager.unregisterKeyListener(inputListener);
  }
}
