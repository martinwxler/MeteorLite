package meteor.plugins.voidHunter;

import com.google.inject.Provides;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.illutils.IllUtils;
import meteor.plugins.illutils.osrs.OSRSUtils;
import meteor.plugins.illutils.osrs.wrappers.IllInventoryItem;
import meteor.plugins.illutils.osrs.wrappers.IllGroundItem;
import meteor.plugins.illutils.osrs.wrappers.IllObject;
import net.runelite.api.GameObject;
import net.runelite.api.ItemID;
import net.runelite.api.MenuAction;
import net.runelite.api.ObjectID;
import net.runelite.api.events.ConfigButtonClicked;
import net.runelite.api.events.GameTick;

@PluginDescriptor(
    name = "Void Hunter"
)
public class VoidHunterPlugin extends Plugin {

  @Inject
  IllUtils illUtils;

  @Inject
  OSRSUtils osrs;

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

    if (!osrs.localPlayer().isIdle())
      return;

    IllGroundItem nearestItemToPickup = nearestItemToPickup();
    if (nearestItemToPickup != null) {
      nearestItemToPickup.pickup();
      return;
    }

    int BLACK_SALAMANDER = ItemID.BLACK_SALAMANDER;
    IllInventoryItem salamanderToDrop = osrs.inventory().withId(BLACK_SALAMANDER).first();
    if (salamanderToDrop != null) {
      salamanderToDrop.interact("Release");
      return;
    }

    if ((System.currentTimeMillis() - lastDelayedAction) < 600)
      return;

    IllObject nearestCaughtTrap = nearestCaughtTrap();
    if (nearestCaughtTrap != null) {
      nearestCaughtTrap.interact("Check");
      lastDelayedAction = System.currentTimeMillis();
      return;
    }

    if (countActiveTraps() >= 5)
      return;

    IllObject nearestEmptyTrap = nearestEmptyTrap();
    if (nearestEmptyTrap() != null) {
      nearestEmptyTrap.interact("Set-trap");
      lastDelayedAction = System.currentTimeMillis();
    }
  }

  public int countActiveTraps() {
    int TRAP_SET = ObjectID.YOUNG_TREE_8999;
    return (int) osrs.objects().withId(TRAP_SET).count();
  }

  public IllObject nearestCaughtTrap() {
    int TRAP_CAUGHT = ObjectID.NET_TRAP_8996;
    return osrs.objects().withId(TRAP_CAUGHT).nearest();
  }

  public IllObject nearestEmptyTrap() {
    int TRAP_EMPTY = ObjectID.YOUNG_TREE_9000;
    return osrs.objects().withId(TRAP_EMPTY).nearest();
  }

  public IllGroundItem nearestItemToPickup() {
    int ROPE = ItemID.ROPE;
    int NET = ItemID.SMALL_FISHING_NET;
    return osrs.groundItems().withId(NET, ROPE).nearest();
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
