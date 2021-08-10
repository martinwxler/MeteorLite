package meteor.plugins.voidHunter;

import com.google.inject.Provides;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.iutils.game.Game;
import meteor.plugins.iutils.game.InventoryItem;
import meteor.plugins.iutils.game.iGroundItem;
import meteor.plugins.iutils.game.iObject;
import net.runelite.api.GameObject;
import net.runelite.api.ItemID;
import net.runelite.api.ObjectID;
import net.runelite.api.events.ConfigButtonClicked;
import net.runelite.api.events.GameTick;

@PluginDescriptor(
    name = "Void Hunter"
)
public class VoidHunterPlugin extends Plugin {

  @Inject
  Game game;

  public static List<GameObject> gameObjects = new ArrayList<>();

  public static boolean enabled = false;

  private long lastDelayedAction;

  @Provides
  public VoidHunterConfig getConfig(ConfigManager configManager) {
    return configManager.getConfig(VoidHunterConfig.class);
  }

  @Subscribe
  public void onGameTick(GameTick event) {

    if (!enabled)
      return;

    if (!game.localPlayer().isIdle())
      return;

    iGroundItem nearestItemToPickup = nearestItemToPickup();
    if (nearestItemToPickup != null) {
      nearestItemToPickup.pickup();
      return;
    }

    int BLACK_SALAMANDER = ItemID.BLACK_SALAMANDER;
    InventoryItem salamanderToDrop = game.inventory().withId(BLACK_SALAMANDER).first();
    if (salamanderToDrop != null) {
      salamanderToDrop.interact("Release");
      return;
    }

    if ((System.currentTimeMillis() - lastDelayedAction) < 600)
      return;

    iObject nearestCaughtTrap = nearestCaughtTrap();
    if (nearestCaughtTrap != null) {
      nearestCaughtTrap.interact("Check");
      lastDelayedAction = System.currentTimeMillis();
      return;
    }

    if (countActiveTraps() >= 5)
      return;

    iObject nearestEmptyTrap = nearestEmptyTrap();
    if (nearestEmptyTrap() != null) {
      nearestEmptyTrap.interact("Set-trap");
      lastDelayedAction = System.currentTimeMillis();
    }
  }

  public int countActiveTraps() {
    int TRAP_SET = ObjectID.YOUNG_TREE_8999;
    return (int) game.objects().withId(TRAP_SET).count();
  }

  public iObject nearestCaughtTrap() {
    int TRAP_CAUGHT = ObjectID.NET_TRAP_8996;
    return game.objects().withId(TRAP_CAUGHT).nearest();
  }

  public iObject nearestEmptyTrap() {
    int TRAP_EMPTY = ObjectID.YOUNG_TREE_9000;
    return game.objects().withId(TRAP_EMPTY).nearest();
  }

  public iGroundItem nearestItemToPickup() {
    int ROPE = ItemID.ROPE;
    int NET = ItemID.SMALL_FISHING_NET;
    return game.groundItems().withId(NET, ROPE).nearest();
  }

  @Subscribe
  public void onConfigButtonClicked(ConfigButtonClicked event) {
    if (event.getGroup().equals("voidHunter"))
      if (event.getKey().equals("startStop"))
        enabled = !enabled;
  }

  public void startup() {

  }

  public void shutdown() {

  }

  public void updateConfig() {

  }
}
