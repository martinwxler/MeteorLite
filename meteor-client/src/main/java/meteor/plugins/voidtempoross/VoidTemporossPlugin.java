package meteor.plugins.voidtempoross;

import static net.runelite.api.ObjectID.*;

import com.google.inject.Provides;

import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import javax.inject.Singleton;

import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.input.KeyManager;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.voidtempoross.tasks.high.*;
import meteor.plugins.voidtempoross.tasks.low.*;
import meteor.plugins.voidutils.events.LocalPlayerIdleEvent;
import meteor.plugins.voidutils.tasks.PriorityTask;
import meteor.plugins.voidutils.tasks.Task;
import meteor.util.HotkeyListener;
import meteor.util.Timer;
import net.runelite.api.*;
import net.runelite.api.events.*;

@Singleton
@PluginDescriptor(
    name = "Void Tempoross",
        enabledByDefault = false
)
public class VoidTemporossPlugin extends Plugin {

  public boolean shouldTether;

  @Inject
  VoidTemporossOverlay overlay;

  @Inject
  VoidTemporossInfoOverlay infoOverlay;

  @Inject
  VoidTemporossConfig config;

  @Inject
  private KeyManager keyManager;

  private long lastDelayedAction;
  private String lastStateExecuted;
  private int prevSalamanderCount = 0;
  private int caught = 0;
  private int startXP = 0;
  private int gainedXP = 0;
  private long nextDelay = 0;
  private long nextLongDelay = 0;
  private Random random = new Random();
  private boolean determinedSide;
  public String side;
  public String location;
  public int lastSpotX;
  public int lastSpotY;

  public boolean canInterrupt;
  private NPC lastInteracting;
  public String state;

  public boolean temporossVulnerable = false;

  //Handle high priority interactions here
  @Subscribe
  public void onGameTick(GameTick event) {
    if (side == null || side.equals("") || getInstanceAnchor() == null)
      location = "GAME_WAITING";
    else if (side.equals("WEST")) {
      if (client.getLocalPlayer().getLocalLocation().getY() >= getInstanceAnchor().getLocalLocation().getY())
        location = "ISLAND";
      else
        location = "SHIP";
    } else {
      if (client.getLocalPlayer().getLocalLocation().getY() <= getInstanceAnchor().getLocalLocation().getY())
        location = "ISLAND";
      else
        location = "SHIP";
    }

    if (shouldTether) {
      Tether tether = getTask(Tether.class);
      if (tether.clickedTether)
        if (client.getLocalPlayer().isMoving()) {
          tether.movedToTether = true;
          canInterrupt = false;
          tether.clickedTether = false;
        }

      if (!tether.movedToTether)
        tether.execute();
    }

    CollectFish collectFish = getTask(CollectFish.class);
    if (collectFish.shouldExecute()) {
      state = "Collect Fish";
      collectFish.execute();
    }

    DouseFireIsland douseFireIsland = getTask(DouseFireIsland.class);
    if (douseFireIsland.shouldExecute()) {
      state = "Douse (ISLAND)";
      douseFireIsland.execute();
    }

    DouseFireShip douseFireShip = getTask(DouseFireShip.class);
    if (douseFireShip.shouldExecute()) {
      state = "Douse (SHIP)";
      douseFireShip.execute();
    }

    AttackTempoross attackTempoross = getTask(AttackTempoross.class);
    if (attackTempoross.shouldExecute()) {
      state = "Attack (Tempoross)";
      attackTempoross.execute();
    }

    if (!canInterrupt) {
      return;
    }

    if (client.getLocalPlayer() == null)
      return;

    for (Task task: tasks.tasks) {
      if (task instanceof PriorityTask)
        if (task instanceof Tether) {
        }
        else if (task instanceof DouseFireIsland) {
        }
        else if (task instanceof DouseFireShip) {
        }
        else if (task instanceof AttackTempoross) {
        } else if (task.shouldExecute()) {
        logger.debug(task.name());
        state = task.name();
        task.execute();
        canInterrupt = false;
      }

    }
  }

  //Handle low priority interactions here
  @Subscribe
  private void onLocalPlayerIdle(LocalPlayerIdleEvent event) {
    if (!enabled) {
      return;
    }

    if (client.getLocalPlayer() == null) {
      return;
    }

    if (!client.isInInstancedRegion())
      return;

    if (!determinedSide) {
      determineSide();
    }

    if (!client.getLocalPlayer().isIdle()) {
      return;
    }

    for (Task task: tasks.tasks) {
      if (task instanceof Tether) {
        if (((Tether) task).movedToTether)
          continue;
      }
      if (task instanceof PriorityTask)
        continue;
      if (task.shouldExecute()) {
        logger.debug(task.name());
        state = task.name();
        task.execute();
      }
    }
  }

  @Provides
  public VoidTemporossConfig getConfig(ConfigManager configManager) {
    return configManager.getConfig(VoidTemporossConfig.class);
  }

  private HotkeyListener toggleListener = new HotkeyListener(() -> config.keybind()) {
    @Override
    public void hotkeyPressed() {
      enabled = !enabled;
      reset();
    }
  };


  @Subscribe
  public void onGameStateChanged(GameStateChanged event) {
    if (event.getGameState() != GameState.LOGGED_IN) {
      determinedSide = false;
    }
  }



  public GameObject getInstanceAnchor() {
    return getSidesObjectNS(41004);
  }

  @Subscribe
  private void onStatChanged(StatChanged event) {
    if (enabled) {
      if (event.getSkill() == Skill.FISHING) {
        gainedXP = event.getXp() - startXP;
      }
    }
  }



  private void determineSide() {
    GameObject nearestRightCannon = osrs.nearestObject(HARPOONFISH_CANNON_41240);
    GameObject nearestLeftCannon = osrs.nearestObject(HARPOONFISH_CANNON);
    if (nearestRightCannon != null && nearestLeftCannon == null) {
      determinedSide = true;
      side = "EAST";
    } else if (nearestLeftCannon != null && nearestRightCannon == null){
      determinedSide = true;
      side = "WEST";
    } else if (nearestLeftCannon != null){
      if (nearestLeftCannon.getDistanceFromLocalPlayer() > nearestRightCannon.getDistanceFromLocalPlayer()) {
        determinedSide = true;
        side = "EAST";
      }
      else  {
        determinedSide = true;
        side = "WEST";
      }
    }
  }

  public GameObject getSidesObjectEW(int... ids) {
    List<GameObject> objects = osrs.objects(ids);
    GameObject object = null;
    if (side == null)
      return null;
    if (objects != null)
    for (GameObject o : objects) {
      if (object != null) {
        if (side.equals("EAST")) {
          if (o.getLocalLocation().getX() > object.getLocalLocation().getX())
            object = o;
        } else {
          if (o.getLocalLocation().getX() < object.getLocalLocation().getX())
            object = o;
        }
      }
      else {
        object = o;
      }
    }
    return object;
  }

  public GameObject getSidesObjectNS(int... ids) {
    List<GameObject> objects = osrs.objects(ids);
    GameObject object = null;
    if (objects != null)
    for (GameObject o : objects) {
      if (object != null) {
        if (side.equals("WEST")) {
          if (o.getLocalLocation().getY() > object.getLocalLocation().getY())
            object = o;
        } else {
          if (o.getLocalLocation().getY() <= object.getLocalLocation().getY())
            object = o;
        }
      }
      else {
        object = o;
      }
    }
    return object;
  }

  public NPC getSidesNPCNS(int... ids) {
    List<NPC> npcs = osrs.npcs(ids);
    NPC npc = null;
    if (npcs != null)
      for (NPC n : npcs) {
        if (npc != null) {
          if (side.equals("WEST")) {
            if (n.getLocalLocation().getY() > npc.getLocalLocation().getY())
              npc = n;
          } else {
            if (n.getLocalLocation().getY() <= npc.getLocalLocation().getY())
              npc = n;
          }
        }
        else {
          npc = n;
        }
      }
    return npc;
  }

  @Subscribe
  public void onConfigButtonClicked(ConfigButtonClicked event) {
    if (event.getGroup().equals("voidTempoross")) {
      if (event.getKey().equals("startStop")) {
        enabled = !enabled;
        reset();
      }
    }
  }

  private void reset() {
    infoOverlay.instanceTimer = new Timer();
    startXP = client.getSkillExperience(Skill.FISHING);
  }

  public void startup() {
    overlayManager.add(overlay, infoOverlay);
    keyManager.registerKeyListener(toggleListener, getClass());
    tasks.add(new AttackTempoross(this));
    tasks.add(new CollectBucket(this));
    tasks.add(new CollectShipWater(this));
    tasks.add(new CollectHammer(this));
    tasks.add(new CollectHarpoon(this));
    tasks.add(new RepairMast(this));
    tasks.add(new LoadFish(this));
    tasks.add(new RepairTotem(this));
    tasks.add(new CollectFish(this));
    tasks.add(new CollectIslandWater(this));
    tasks.add(new CollectRope(this));
    tasks.add(new CookFish(this));
    tasks.add(new DouseFireIsland(this));
    tasks.add(new DouseFireShip(this));
    tasks.add(new Tether(this));
    tasks.add(new WalkToShip(this));
    tasks.add(new WalkToShore(this));
  }

  public void shutdown() {
    tasks.tasks.clear();
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
