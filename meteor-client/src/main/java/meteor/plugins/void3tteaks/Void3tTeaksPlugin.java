package meteor.plugins.void3tteaks;

import com.google.inject.Provides;
import meteor.callback.ClientThread;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.entities.TileObjects;
import meteor.plugins.api.items.Inventory;
import net.runelite.api.GameObject;
import net.runelite.api.Item;
import net.runelite.api.events.ConfigButtonClicked;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.StatChanged;
import net.runelite.api.widgets.WidgetItem;

import javax.inject.Inject;
import java.util.List;
import java.util.Random;

import static net.runelite.api.ItemID.*;
import static net.runelite.api.ObjectID.*;
import static net.runelite.api.Skill.WOODCUTTING;

@PluginDescriptor(
    name = "Void 3t Teaks",
        enabledByDefault = false
)
public class Void3tTeaksPlugin extends Plugin {

  @Inject
  Void3tTeaksInfoOverlay infoOverlay;

  @Inject
  ClientThread clientThread;

  public static boolean enabled = false;
  private int startXP = 0;
  private int delayedTicks = -111;
  private int gainedXP;
  private int cut;
  private int lastCut = 0;
  private Random random = new Random();
  private final int ONE_TILE = 64;
  private int[] teakTreeIDs = new int[] {TEAK_TREE,TEAK_TREE_30438,TEAK_TREE_30439,TEAK_TREE_30440,TEAK_TREE_30441,TEAK_TREE_30442,TEAK_TREE_30443,TEAK_TREE_30444,TEAK_TREE_30445,40758, 9036, 36686};

  @Provides
  public Void3tTeaksConfig getConfig(ConfigManager configManager) {
    return configManager.getConfig(Void3tTeaksConfig.class);
  }

  @Override
  public void startup() {
    overlayManager.add(infoOverlay);
    startXP = client.getSkillExperience(WOODCUTTING);
  }

  @Override
  public void shutdown() {
    overlayManager.remove(infoOverlay);
  }

  @Subscribe
  public void onGameTick(GameTick event) {
    if (!enabled) {
      return;
    }

    if (client.getLocalPlayer() == null) {
      return;
    }

    if (TileObjects.getNearest(teakTreeIDs) == null)
      return;

    if (Inventory.getFirst(GUAM_LEAF) == null)
      return;

    if (Inventory.getFirst(PESTLE_AND_MORTAR) == null)
      return;

    if (Inventory.getFirst(SWAMP_TAR) == null || Inventory.getAll(SWAMP_TAR).get(0).getQuantity() < 15)
      return;

    if (((GameObject)TileObjects.getNearest(teakTreeIDs)).getDistanceFromLocalPlayer() > ONE_TILE)
      delayedTicks = -2;

    List<Item> cuts = Inventory.getAll(TEAK_LOGS);
    if (!cuts.isEmpty()) {
      cut++;
      lastCut = 0;
      dropCut();
    }

    if (delayedTicks == -3) {
      useGuamOnSwampTar();
      delayedTicks = -2;
      return;
    }

    if (delayedTicks == -2) {
      clickTeakTree();
      delayedTicks = -1;
      return;
    }

    if (delayedTicks == -1) {
      delayedTicks = -3;
    }
  }

  private void dropCut() {
    Item logsCut = Inventory.getFirst(TEAK_LOGS);
    if (logsCut != null)
      logsCut.drop();
  }

  private void clickTeakTree() {
    if (client.getLocalPlayer().isMoving())
      return;
    GameObject nearestTeakTree = (GameObject) TileObjects.getNearest(teakTreeIDs);
    if (nearestTeakTree != null)
      nearestTeakTree.interact("Chop down");
  }

  private void useGuamOnSwampTar() {
    if (((GameObject)TileObjects.getNearest(teakTreeIDs)).getDistanceFromLocalPlayer() > ONE_TILE)
      return;
    Item cleanGuam = Inventory.getFirst(GUAM_LEAF);
    Item swampTar = Inventory.getFirst(SWAMP_TAR);
    if (cleanGuam != null && swampTar != null)
      cleanGuam.useOn(swampTar);
  }

  @Subscribe
  private void onStatChanged(StatChanged event) {
    if (enabled) {
      if (event.getSkill() == WOODCUTTING) {
        gainedXP = event.getXp() - startXP;
      }
    }
  }

  @Subscribe
  public void onConfigButtonClicked(ConfigButtonClicked event) {
    if (event.getGroup().equals("void3tTeaks")) {
      if (event.getKey().equals("startStop")) {
        enabled = !enabled;
        if (enabled) {
          reset();
        }
      }
    }
  }

  private void reset() {
    delayedTicks = -111;
    cut = 0;
    gainedXP = 0;
    startXP = client.getSkillExperience(WOODCUTTING);
    infoOverlay.instanceTimer.reset();
  }


  public int getGainedXP() {
    return gainedXP;
  }

  public int getLogsCut() {
    return cut;
  }
}
