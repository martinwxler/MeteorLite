package meteor.plugins.void3tFishing;

import static net.runelite.api.ItemID.BARBARIAN_ROD;
import static net.runelite.api.ItemID.GUAM_LEAF;
import static net.runelite.api.ItemID.LEAPING_SALMON;
import static net.runelite.api.ItemID.LEAPING_STURGEON;
import static net.runelite.api.ItemID.LEAPING_TROUT;
import static net.runelite.api.ItemID.PESTLE_AND_MORTAR;
import static net.runelite.api.ItemID.SWAMP_TAR;
import static net.runelite.api.NpcID.FISHING_SPOT_1542;
import static net.runelite.api.Skill.FISHING;

import com.google.inject.Provides;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
import meteor.callback.ClientThread;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import net.runelite.api.ItemID;
import net.runelite.api.NPC;
import net.runelite.api.Skill;
import net.runelite.api.events.ConfigButtonClicked;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.StatChanged;
import net.runelite.api.widgets.WidgetItem;

@PluginDescriptor(
    name = "Void 3t Fishing",
        enabledByDefault = false
)
public class Void3tFishingPlugin extends Plugin {

  @Inject
  Void3tFishingInfoOverlay infoOverlay;

  @Inject
  ClientThread clientThread;

  public static boolean enabled = false;
  private int startXP = 0;
  private int delayedTicks = -111;
  private int gainedXP;
  private int caught;
  private int lastCaught = 0;
  private Random random = new Random();
  private final int ONE_TILE = 64;

  @Provides
  public Void3tFishingConfig getConfig(ConfigManager configManager) {
    return configManager.getConfig(Void3tFishingConfig.class);
  }

  @Override
  public void startup() {
    overlayManager.add(infoOverlay);
    startXP = client.getSkillExperience(FISHING);
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

    if (osrs.nearestNPC(FISHING_SPOT_1542) == null)
      return;

    if (osrs.items(GUAM_LEAF) == null)
      return;

    if (osrs.items(BARBARIAN_ROD) == null)
      return;

    if (osrs.items(PESTLE_AND_MORTAR) == null)
      return;

    if (osrs.items(SWAMP_TAR) == null || osrs.items(SWAMP_TAR).get(0).getQuantity() < 15)
      return;

    if (osrs.nearestNPC(FISHING_SPOT_1542).getDistanceFromLocalPlayer() > ONE_TILE ||
            (osrs.nearestNPC(FISHING_SPOT_1542).getDistanceFromLocalPlayer() == ONE_TILE && client.getLocalPlayer().isIdle()))
      delayedTicks = -2;

    List<WidgetItem> catches = osrs.items(LEAPING_TROUT, LEAPING_SALMON, LEAPING_STURGEON);
    if (catches != null) {
      caught++;
      lastCaught = 0;
      dropCatch();
    }

    if (delayedTicks == -3) {
      useGuamOnSwampTar();
      delayedTicks = -2;
      return;
    }

    if (delayedTicks == -2) {
      clickFishingSpot();
      delayedTicks = -1;
      return;
    }

    if (delayedTicks == -1) {
      delayedTicks = -3;
    }
  }

  private void dropCatch() {
    WidgetItem caughtFish = osrs.firstItem(LEAPING_TROUT, LEAPING_SALMON, LEAPING_STURGEON);
    if (caughtFish != null)
      caughtFish.interact("drop");
  }

  private void clickFishingSpot() {
    if (client.getLocalPlayer().isMoving())
      return;
    NPC nearestFishingSpot = osrs.nearestNPC(FISHING_SPOT_1542);
    if (nearestFishingSpot != null)
      nearestFishingSpot.interact(0);
  }

  private void useGuamOnSwampTar() {
    if (osrs.nearestNPC(FISHING_SPOT_1542).getDistanceFromLocalPlayer() > ONE_TILE)
      return;
    WidgetItem cleanGuam = osrs.firstItem(GUAM_LEAF);
    WidgetItem swampTar = osrs.firstItem(SWAMP_TAR);
    if (cleanGuam != null && swampTar != null)
      cleanGuam.useOn(swampTar);
  }

  @Subscribe
  private void onStatChanged(StatChanged event) {
    if (enabled) {
      if (event.getSkill() == FISHING) {
        gainedXP = event.getXp() - startXP;
      }
    }
  }

  @Subscribe
  public void onConfigButtonClicked(ConfigButtonClicked event) {
    if (event.getGroup().equals("void3tFishing")) {
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
    caught = 0;
    gainedXP = 0;
    startXP = client.getSkillExperience(FISHING);
    infoOverlay.instanceTimer.reset();
  }


  public int getGainedXP() {
    return gainedXP;
  }

  public int getCaught() {
    return caught;
  }
}
