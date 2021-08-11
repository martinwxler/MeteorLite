package meteor.plugins.voidHunter;

import com.google.inject.Provides;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.inject.Inject;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.voidutils.OSRSUtils;
import meteor.ui.overlay.OverlayManager;
import net.runelite.api.GameObject;
import net.runelite.api.ItemID;
import net.runelite.api.ObjectID;
import net.runelite.api.TileItem;
import net.runelite.api.events.ConfigButtonClicked;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.WidgetItem;

@PluginDescriptor(
    name = "Void Hunter"
)
public class VoidHunterPlugin extends Plugin {

  @Inject
  OSRSUtils osrs;

  @Inject
  VoidHunterOverlay overlay;

  @Inject
  OverlayManager overlayManager;

  public static List<GameObject> gameObjects = new ArrayList<>();

  public static boolean enabled = false;

  private long lastDelayedAction;
  private List<GameObject> emptyTraps = new ArrayList<>();

  @Provides
  public VoidHunterConfig getConfig(ConfigManager configManager) {
    return configManager.getConfig(VoidHunterConfig.class);
  }

  @Subscribe
  public void onGameTick(GameTick event) {

    if (!enabled)
      return;

    if (client.getLocalPlayer() == null)
      return;

    if (!client.getLocalPlayer().isIdle())
      return;

    TileItem nearestItemToPickup = nearestItemToPickup();
    if (nearestItemToPickup != null) {
      nearestItemToPickup.pickup();
      return;
    }

    int BLACK_SALAMANDER = ItemID.BLACK_SALAMANDER;
    List<WidgetItem> items = osrs.items(BLACK_SALAMANDER);
    if (items.size() > 0) {
      WidgetItem salamanderToDrop = items.get(0);
      salamanderToDrop.interact("Release");
      return;
    }

    if ((System.currentTimeMillis() - lastDelayedAction) < 600)
      return;

    GameObject nearestCaughtTrap = nearestCaughtTrap();

    if (countActiveTraps() >= 3)
      if (nearestCaughtTrap != null)
      {
        nearestCaughtTrap.interact("Check");
        lastDelayedAction = System.currentTimeMillis();
        return;
      }

    GameObject nearestEmptyTrap = nearestEmptyTrap();
    if (nearestEmptyTrap() != null) {
      nearestEmptyTrap.interact("Set-trap");
      lastDelayedAction = System.currentTimeMillis();
      return;
    }

    if (nearestCaughtTrap != null) {
      nearestCaughtTrap.interact("Check");
      lastDelayedAction = System.currentTimeMillis();
    }
  }

  public int countActiveTraps() {
    int TRAP_SET = ObjectID.NET_TRAP_9002;
    int TRAP_CAUGHT = ObjectID.NET_TRAP_8996;
    return osrs.objects(TRAP_SET, TRAP_CAUGHT).size();
  }

  public List<GameObject> activeTraps() {
    int TRAP_SET = ObjectID.NET_TRAP_9002;
    int TRAP_CAUGHT = ObjectID.NET_TRAP_8996;
    return osrs.objects(TRAP_SET, TRAP_CAUGHT);
  }

  public List<GameObject> emptyTraps() {
    int TRAP_EMPTY = ObjectID.YOUNG_TREE_9000;
    return osrs.objects(TRAP_EMPTY);
  }

  public GameObject nearestCaughtTrap() {
    int TRAP_CAUGHT = ObjectID.NET_TRAP_8996;
    return osrs.objects(TRAP_CAUGHT).get(0);
  }

  public GameObject nearestEmptyTrap() {
    int TRAP_EMPTY = ObjectID.YOUNG_TREE_9000;
    return osrs.objects(TRAP_EMPTY).get(0);
  }

  public TileItem nearestItemToPickup() {
    int ROPE = ItemID.ROPE;
    int NET = ItemID.SMALL_FISHING_NET;
    return osrs.loot(ROPE, NET).get(0);
  }

  @Subscribe
  public void onConfigButtonClicked(ConfigButtonClicked event) {
    if (event.getGroup().equals("voidHunter"))
      if (event.getKey().equals("startStop"))
        enabled = !enabled;
  }

  public void startup() {
    overlayManager.add(overlay);
  }

  public void shutdown() {
    overlayManager.remove(overlay);
  }

  public void updateConfig() {

  }
}
