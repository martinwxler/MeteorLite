package meteor.plugins.agility;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.inject.Inject;

import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.ui.overlay.OverlayLayer;
import meteor.ui.overlay.OverlayManager;
import net.runelite.api.GameState;
import net.runelite.api.ItemID;
import net.runelite.api.Tile;
import net.runelite.api.TileObject;
import net.runelite.api.events.DecorativeObjectChanged;
import net.runelite.api.events.DecorativeObjectDespawned;
import net.runelite.api.events.DecorativeObjectSpawned;
import net.runelite.api.events.GameObjectChanged;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GroundObjectChanged;
import net.runelite.api.events.GroundObjectDespawned;
import net.runelite.api.events.GroundObjectSpawned;
import net.runelite.api.events.ItemDespawned;
import net.runelite.api.events.ItemSpawned;
import net.runelite.api.events.WallObjectChanged;
import net.runelite.api.events.WallObjectDespawned;
import net.runelite.api.events.WallObjectSpawned;

@PluginDescriptor(
    name = "Agility",
    description = "Show helpful information about agility courses and obstacles",
    tags = {"grace", "marks", "overlay", "shortcuts", "skilling", "traps", "sepulchre",}
)
public class AgilityPlugin extends Plugin {

  public final Map<TileObject, Obstacle> obstacles = new HashMap<>();
  public List<Tile> marks = new ArrayList<>();

  @Inject
  OverlayManager overlayManager;

  @Inject
  AgilityOverlay overlay;

  @Override
  public void startup() {
    overlay.setLayer(OverlayLayer.ABOVE_SCENE);
    overlayManager.add(overlay);
  }

  @Subscribe
  public void onGameStateChanged(GameStateChanged event) {
    if (event.getGameState() == GameState.LOADING) {
      marks.clear();
      obstacles.clear();
    }
  }

  @Subscribe
  public void onGameObjectSpawned(GameObjectSpawned event) {
    onTileObject(event.getTile(), null, event.getGameObject());
  }

  @Subscribe
  public void onGameObjectChanged(GameObjectChanged event) {
    onTileObject(event.getTile(), event.getOldObject(), event.getNewObject());
  }

  @Subscribe
  public void onGameObjectDespawned(GameObjectDespawned event) {
    onTileObject(event.getTile(), event.getGameObject(), null);
  }

  @Subscribe
  public void onGroundObjectSpawned(GroundObjectSpawned event) {
    onTileObject(event.getTile(), null, event.getGroundObject());
  }

  @Subscribe
  public void onGroundObjectChanged(GroundObjectChanged event) {
    onTileObject(event.getTile(), event.getPrevious(), event.getGroundObject());
  }

  @Subscribe
  public void onGroundObjectDespawned(GroundObjectDespawned event) {
    onTileObject(event.getTile(), event.getGroundObject(), null);
  }

  @Subscribe
  public void onWallObjectSpawned(WallObjectSpawned event) {
    onTileObject(event.getTile(), null, event.getWallObject());
  }

  @Subscribe
  public void onWallObjectChanged(WallObjectChanged event) {
    onTileObject(event.getTile(), event.getPrevious(), event.getWallObject());
  }

  @Subscribe
  public void onWallObjectDespawned(WallObjectDespawned event) {
    onTileObject(event.getTile(), event.getWallObject(), null);
  }

  @Subscribe
  public void onDecorativeObjectSpawned(DecorativeObjectSpawned event) {
    onTileObject(event.getTile(), null, event.getDecorativeObject());
  }

  @Subscribe
  public void onDecorativeObjectChanged(DecorativeObjectChanged event) {
    onTileObject(event.getTile(), event.getPrevious(), event.getDecorativeObject());
  }

  @Subscribe
  public void onDecorativeObjectDespawned(DecorativeObjectDespawned event) {
    onTileObject(event.getTile(), event.getDecorativeObject(), null);
  }

  @Subscribe
  public void onItemSpawned(ItemSpawned event) {
    if (event.getItem().getId() == ItemID.MARK_OF_GRACE) {
      marks.add(event.getTile());
    }
  }

  @Subscribe
  public void onItemDespawned(ItemDespawned event) {
    if (event.getItem().getId() == ItemID.MARK_OF_GRACE) {
      marks.remove(event.getTile());
    }
  }

  private void onTileObject(Tile tile, TileObject oldObject, TileObject newObject) {
    obstacles.remove(oldObject);

    if (newObject == null) {
      return;
    }

    if (Obstacles.OBSTACLE_IDS.contains(newObject.getId()) ||
        Obstacles.PORTAL_OBSTACLE_IDS.contains(newObject.getId()) ||
        (Obstacles.TRAP_OBSTACLE_IDS.contains(newObject.getId())
            && Obstacles.TRAP_OBSTACLE_REGIONS.contains(newObject.getWorldLocation().getRegionID()))
        ||
        Obstacles.SEPULCHRE_OBSTACLE_IDS.contains(newObject.getId()) ||
        Obstacles.SEPULCHRE_SKILL_OBSTACLE_IDS.contains(newObject.getId())) {
      obstacles.put(newObject, new Obstacle(tile, null));
    }

    if (Obstacles.SHORTCUT_OBSTACLE_IDS.containsKey(newObject.getId())) {
      AgilityShortcut closestShortcut = null;
      int distance = -1;

      // Find the closest shortcut to this object
      for (AgilityShortcut shortcut : Obstacles.SHORTCUT_OBSTACLE_IDS.get(newObject.getId())) {
        if (!shortcut.matches(newObject)) {
          continue;
        }

        if (shortcut.getWorldLocation() == null) {
          closestShortcut = shortcut;
          break;
        } else {
          int newDistance = shortcut.getWorldLocation().distanceTo2D(newObject.getWorldLocation());
          if (closestShortcut == null || newDistance < distance) {
            closestShortcut = shortcut;
            distance = newDistance;
          }
        }
      }

      if (closestShortcut != null) {
        obstacles.put(newObject, new Obstacle(tile, closestShortcut));
      }
    }
  }
}
