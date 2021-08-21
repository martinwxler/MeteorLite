package meteor.plugins.voidagility;

import static net.runelite.api.ObjectID.*;

import com.google.inject.Provides;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import javax.inject.Singleton;

import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.input.KeyManager;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.voidutils.events.LocalPlayerIdleEvent;
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
  private boolean determinedSide;
  public String side;
  public String location;
  private int lastSpotX;
  private int lastSpotY;

  public boolean canInterrupt;
  private NPC lastInteracting;

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

    if (lastInteracting != null)
    if (lastInteracting.getLocalLocation().getX() != lastSpotX
    || lastInteracting.getLocalLocation().getY() != lastSpotY)
      canInterrupt = true;

    if (lastInteracting != null)
      if (lastInteracting.getId() != 10569)
        if (getNearestDoubleFishingSpot() != null)
          canInterrupt = true;

    if (!canInterrupt) {
      return;
    }

    if (client.getLocalPlayer() == null)
      return;

    if (!client.getLocalPlayer().isIdle())
      return;
  }

  public GameObject getInstanceAnchor() {
    return getSidesObjectNS(41004);
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

    if (getSpiritPool() != null) {
      getSpiritPool().interact(0);
      return;
    }

    if (!client.getLocalPlayer().isIdle()) {
      return;
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
    } else {
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

  public NPC getFishCrate() {
    if (side == null)
      return null;
    if (side.equals("EAST"))
      return osrs.nearestNPC(10579);
    else
      return osrs.nearestNPC(10576);
  }

  public NPC getSpiritPool() {
    return osrs.nearestNPC(10571);
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

  public NPC getNearestFishingSpot() {
    return osrs.nearestNPC(getFishingSpots());
  }

  public NPC getNearestDoubleFishingSpot() {
    return osrs.nearestNPC(getDoubleFishingSpots());
  }

  public List<NPC> getDoubleFishingSpots() {
    List<NPC> finalNPCs = new ArrayList<>();
    List<NPC> npcs = osrs.npcs(10569);
    GameObject islandWater = getInstanceAnchor();
    if (npcs != null)
      for (NPC n : npcs) {
        if (n != null) {
          if (side.equals("WEST")) {
            if (n.getLocalLocation().getY() > islandWater.getLocalLocation().getY())
              finalNPCs.add(n);
          } else {
            if (n.getLocalLocation().getY() < islandWater.getLocalLocation().getY())
              finalNPCs.add(n);
          }
        }
      }
    return finalNPCs;
  }

  public List<NPC> getFishingSpots() {
    List<NPC> finalNPCs = new ArrayList<>();
    List<NPC> npcs = osrs.npcs(10568, 10565);
    GameObject islandWater = getInstanceAnchor();
    if (npcs != null)
      for (NPC n : npcs) {
        if (n != null) {
          if (side.equals("WEST")) {
            if (n.getLocalLocation().getY() > islandWater.getLocalLocation().getY())
              finalNPCs.add(n);
          } else {
            if (n.getLocalLocation().getY() < islandWater.getLocalLocation().getY())
              finalNPCs.add(n);
          }
        }
      }
    return finalNPCs;
  }

  public int getRopeCount() {
    if (osrs.items(954) != null)
      return osrs.items(954).size();
    return 0;
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
