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
import meteor.plugins.voidtempoross.tasks.high.AttackTempoross;
import meteor.plugins.voidtempoross.tasks.high.Tether;
import meteor.plugins.voidtempoross.tasks.low.*;
import meteor.plugins.voidutils.events.LocalPlayerIdleEvent;
import meteor.plugins.voidutils.tasks.PriorityTask;
import meteor.plugins.voidutils.tasks.Task;
import meteor.plugins.voidutils.tasks.TaskSet;
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

  TaskSet taskSet = new TaskSet(this);

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
  private int lastSpotX;
  private int lastSpotY;

  public boolean canInterrupt;
  private NPC lastInteracting;
  public String state;

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

    if (!canInterrupt) {
      return;
    }

    if (client.getLocalPlayer() == null)
      return;

    if (!client.getLocalPlayer().isIdle())
      return;

    for (Task task: taskSet.tasks) {
      if (task instanceof PriorityTask)
      if (task.shouldExecute()) {
        logger.debug(task.name());
        state = task.name();
        task.execute();
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

    for (Task task: taskSet.tasks) {
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
    taskSet.add(new AttackTempoross(this));
    taskSet.add(new CollectBucket(this));
    taskSet.add(new CollectFish(this));
    taskSet.add(new CollectHammer(this));
    taskSet.add(new CollectHarpoon(this));
    taskSet.add(new CollectIslandWater(this));
    taskSet.add(new CollectRope(this));
    taskSet.add(new CollectShipWater(this));
    taskSet.add(new CookFish(this));
    taskSet.add(new DouseFireIsland(this));
    taskSet.add(new DouseFireShip(this));
    taskSet.add(new RepairMast(this));
    taskSet.add(new RepairTotem(this));
    taskSet.add(new Tether(this));
    taskSet.add(new WalkToShip(this));
    taskSet.add(new WalkToShore(this));
  }

  public void shutdown() {
    taskSet.tasks.clear();
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
