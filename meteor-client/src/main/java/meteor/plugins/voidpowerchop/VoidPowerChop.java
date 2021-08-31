package meteor.plugins.voidpowerchop;

import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.entities.Players;
import meteor.plugins.api.entities.TileObjects;
import meteor.plugins.api.game.Game;
import meteor.plugins.api.items.Inventory;
import net.runelite.api.GameObject;
import net.runelite.api.Player;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.ConfigButtonClicked;
import net.runelite.api.events.GameTick;

@PluginDescriptor(
    name = "Void Powerchop",
        enabledByDefault = false
)
public class VoidPowerChop extends Plugin {

  public static boolean enabled = false;
  private boolean isDropping;
  private long lastDropMS = 0;

  int[] willowTrees = new int[] {
      10829, 10819
  };

  @Provides
  public VoidPowerChopConfig getConfig(ConfigManager configManager) {
    return configManager.getConfig(VoidPowerChopConfig.class);
  }

  @Subscribe
  public void onClientTick(ClientTick event) {
    if (!enabled) {
      return;
    }

    if (client.getLocalPlayer() == null) {
      return;
    }

    if (!Players.getLocal().isIdle())
      return;

    if (Inventory.getFirst(1519) != null) {
      isDropping = true;
      if (System.currentTimeMillis() > lastDropMS + 250) {
        Inventory.getFirst(1519).drop();
        lastDropMS = System.currentTimeMillis();
      }
    }
    else
      isDropping = false;

  }

  @Subscribe
  public void onGameTick(GameTick event) {
    if (!enabled) {
      return;
    }

    if (client.getLocalPlayer() == null) {
      return;
    }

    if (isDropping)
      return;

    if (!Players.getLocal().isIdle())
      return;

    GameObject nearestRock = (GameObject) TileObjects.getNearest(willowTrees);
    if (nearestRock != null) {
      if (nearestRock.getDistanceFromLocalPlayer() < 300)
        nearestRock.interact("Chop down");
    }
  }

  @Subscribe
  public void onConfigButtonClicked(ConfigButtonClicked event) {
    if (event.getGroup().equals("voidPowerchop")) {
      if (event.getKey().equals("startStop")) {
        enabled = !enabled;
      }
    }
  }
}
