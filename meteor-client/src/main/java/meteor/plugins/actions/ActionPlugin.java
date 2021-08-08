package meteor.plugins.actions;

import javax.inject.Inject;
import meteor.MeteorLite;
import meteor.eventbus.Subscribe;
import meteor.input.KeyManager;
import meteor.input.MouseManager;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.botutils.BotUtils;
import meteor.plugins.botutils.Spells;
import meteor.plugins.iutils.iUtils;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;

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

  public static boolean enabled;

  int i = 0;

  @Subscribe
  public void onGameTick(GameTick event) {
  }

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
