package meteor.plugins.void3tFishing;

import static net.runelite.api.ItemID.LEAPING_SALMON;
import static net.runelite.api.ItemID.LEAPING_STURGEON;
import static net.runelite.api.ItemID.LEAPING_TROUT;
import static net.runelite.api.NpcID.FISHING_SPOT_1542;

import com.google.inject.Provides;
import java.util.List;
import java.util.Random;
import javax.inject.Inject;
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
    name = "Void 3t Fishing"
)
public class Void3tFishingPlugin extends Plugin {

  @Inject
  Void3tFishingInfoOverlay infoOverlay;

  public static boolean enabled = false;
  private int startXP = 0;
  private int delayedTicks = -111;
  private int gainedXP;
  private int caught;
  private int lastCaught = 0;
  private Random random = new Random();

  @Provides
  public Void3tFishingConfig getConfig(ConfigManager configManager) {
    return configManager.getConfig(Void3tFishingConfig.class);
  }

  @Override
  public void startup() {
    overlayManager.add(infoOverlay);
    startXP = client.getSkillExperience(Skill.FISHING);
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

    List<WidgetItem> catches = osrs.items(LEAPING_TROUT, LEAPING_SALMON, LEAPING_STURGEON);
    if (catches != null) {
      caught++;
      lastCaught = 0;
      dropCatch();
    }

    if (delayedTicks == -111 || delayedTicks == -3) {
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
    List<WidgetItem> catches = osrs.items(LEAPING_TROUT, LEAPING_SALMON, LEAPING_STURGEON);
    for (WidgetItem c : catches)
      if (c != null) {
        c.interact("drop");
        try {
          Thread.sleep(70 + random.nextInt(120));
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
  }

  private void clickFishingSpot() {
    NPC nearestFishingSpot = osrs.nearestNPC(FISHING_SPOT_1542);
    if (nearestFishingSpot != null)
      nearestFishingSpot.interact(0);
  }

  private void useGuamOnSwampTar() {
    WidgetItem cleanGuam = osrs.firstItem(ItemID.GUAM_LEAF);
    WidgetItem swampTar = osrs.firstItem(ItemID.SWAMP_TAR);
    if (cleanGuam != null && swampTar != null)
      cleanGuam.useOn(swampTar);
  }

  @Subscribe
  private void onStatChanged(StatChanged event) {
    if (enabled) {
      if (event.getSkill() == Skill.FISHING) {
        gainedXP = event.getXp() - startXP;
      }
    }
  }

  @Subscribe
  public void onConfigButtonClicked(ConfigButtonClicked event) {
    if (event.getGroup().equals("void3tFishing")) {
      if (event.getKey().equals("startStop")) {
        enabled = !enabled;
        if (enabled)
          delayedTicks = -111;
      }
    }
  }

  public int getGainedXP() {
    return gainedXP;
  }

  public int getCaught() {
    return caught;
  }
}
