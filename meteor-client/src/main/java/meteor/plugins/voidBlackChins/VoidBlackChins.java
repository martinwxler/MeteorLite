package meteor.plugins.voidBlackChins;

import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.input.KeyManager;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.entities.Players;
import meteor.plugins.api.entities.TileItems;
import meteor.plugins.api.entities.TileObjects;
import meteor.plugins.api.items.Inventory;
import meteor.plugins.api.movement.Movement;
import meteor.plugins.voidutils.OSRSUtils;
import meteor.plugins.voidutils.events.LocalPlayerIdleEvent;
import meteor.util.HotkeyListener;
import meteor.util.Timer;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ConfigButtonClicked;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.StatChanged;

import javax.inject.Inject;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static net.runelite.api.ItemID.*;

@PluginDescriptor(
        name = "Void Black Chins",
        enabledByDefault = false
)
public class VoidBlackChins extends Plugin {

  @Inject
  VoidHunterOverlay overlay;

  @Inject
  VoidHunterInfoOverlay infoOverlay;

  @Inject
  VoidHunterConfig config;

  @Inject
  OSRSUtils osrs;

  @Inject
  private KeyManager keyManager;

  @Inject
  ScheduledExecutorService executorService;

  public static boolean enabled = false;
  private long lastDelayedAction;
  private String lastStateExecuted;
  private int prevSalamanderCount = 0;
  private int caught = 0;
  private int startXP = 0;
  private int gainedXP = 0;
  private long nextDelay = 0;
  private long nextLongDelay = 0;
  private Random random = new Random();

  public WorldPoint[] eastSpots = new WorldPoint[] {
          new WorldPoint(3158, 3771, 0), //se
          new WorldPoint(3159, 3774, 0), //ne
          new WorldPoint(3154, 3773, 0), //nw
          new WorldPoint(3152, 3769, 0), //sw
  };

  public WorldPoint eastSE = eastSpots[0];
  public WorldPoint eastNE = eastSpots[1];
  public WorldPoint eastNW = eastSpots[2];
  public WorldPoint eastSW = eastSpots[3];
  TileObject trapSE;
  TileObject trapNE;
  TileObject trapNW;
  TileObject trapSW;

  private int TRAP_SET = 9380;
  private int TRAP_CAUGHT = 721;
  private int TRAP_FAILED = 9385;
  private int TRAP_ITEM_ID = 10008;

  @Provides
  public VoidHunterConfig getConfig(ConfigManager configManager) {
    return configManager.getConfig(VoidHunterConfig.class);
  }

  private HotkeyListener toggleListener = new HotkeyListener(() -> config.keybind()) {
    @Override
    public void hotkeyPressed() {
      enabled = !enabled;
      reset();
    }
  };


  @Subscribe
  public void onGameTick(GameTick event) {
    if (!enabled) {
      return;
    }

    if (client.getLocalPlayer() == null) {
      return;
    }

    if (!Players.getLocal().isIdle()) {
      return;
    }

    if (handleTrapSpot(eastSE))
      return;

    if (handleTrapSpot(eastNE))
      return;

    if (handleTrapSpot(eastNW))
      return;

    handleTrapSpot(eastSW);
  }

  @Subscribe
  private void onStatChanged(StatChanged event) {
    if (enabled) {
      if (event.getSkill() == Skill.HUNTER) {
        gainedXP = event.getXp() - startXP;
      }
    }
  }

  @Subscribe
  private void onLocalPlayerIdle(LocalPlayerIdleEvent event) {

  }

  private boolean handleTrapSpot(WorldPoint worldPoint) {
    if (getActiveTrap(worldPoint) == null)
      if (getFailedTrap(worldPoint) == null) {
        if (getCaughtTrap(worldPoint) == null) {
          if (getDroppedTrap(worldPoint) != null) {
            getDroppedTrap(worldPoint).interact("Lay");
            return true;
          }
        } else  {
          getCaughtTrap(worldPoint).interact("Reset");
          return true;
        }
      } else {
        getFailedTrap(worldPoint).interact("Reset");
        return true;
      }


    if (Players.getLocal().getWorldLocation().distanceTo(worldPoint) > 0) {
      Movement.walkTo(worldPoint);
      return true;
    }

    if (Inventory.contains(TRAP_ITEM_ID)) {
      Inventory.getFirst(TRAP_ITEM_ID).interact("Lay");
      return true;
    }

    return false;
  }

  private TileObject getActiveTrap(WorldPoint worldPoint) {
    List<TileObject> trapObjs = TileObjects.getAt(worldPoint, object -> (object.getId() == TRAP_SET));
    if (trapObjs.size() > 0)
      return trapObjs.get(0);
    return null;
  }

  private TileObject getCaughtTrap(WorldPoint worldPoint) {
    List<TileObject> trapObjs = TileObjects.getAt(worldPoint, object -> (object.getId() == TRAP_CAUGHT));
    if (trapObjs.size() > 0)
      return trapObjs.get(0);
    return null;
  }

  private TileObject getFailedTrap(WorldPoint worldPoint) {
    List<TileObject> trapObjs = TileObjects.getAt(worldPoint, object -> (object.getId() == TRAP_FAILED));
    if (trapObjs.size() > 0)
      return trapObjs.get(0);
    return null;
  }

  private TileItem getDroppedTrap(WorldPoint worldPoint) {
    List<TileItem> items = TileItems.getAt(worldPoint, tileItem -> {
      return tileItem.getId() == TRAP_ITEM_ID;
    });
    if (items.size() > 0)
      return items.get(0);
    return null;
  }

  @Subscribe
  private void onItemContainerChanged(ItemContainerChanged event) {
    int salamanderCount = 0;
    for (Item item : event.getItemContainer().getItems()) {
      if (item.getId() == BLACK_SALAMANDER) {
        salamanderCount++;
      }
    }

    if (salamanderCount > prevSalamanderCount) {
      caught++;
    }

    prevSalamanderCount = salamanderCount;
  }

  @Subscribe
  public void onConfigButtonClicked(ConfigButtonClicked event) {
    if (event.getGroup().equals("blackChins")) {
      if (event.getKey().equals("startStop")) {
        enabled = !enabled;
        reset();
      }
    }
  }

  private void reset() {
    infoOverlay.instanceTimer = new Timer();
    startXP = client.getSkillExperience(Skill.HUNTER);
  }

  public void startup() {
    overlayManager.add(overlay, infoOverlay);
    keyManager.registerKeyListener(toggleListener, getClass());
  }

  public void shutdown() {
    overlayManager.remove(overlay, infoOverlay);
    keyManager.unregisterKeyListener(toggleListener);
  }

  public void updateConfig() {

  }

  public String getState() {
    return lastStateExecuted;
  }

  public int getCaught() {
    return caught;
  }

  public int getGainedXP() {
    return gainedXP;
  }
}
