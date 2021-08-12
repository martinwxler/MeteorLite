package meteor.plugins.voidutils;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import meteor.eventbus.EventBus;
import meteor.eventbus.Subscribe;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.GameState;
import net.runelite.api.NPC;
import net.runelite.api.Tile;
import net.runelite.api.TileItem;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameObjectChanged;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.ItemDespawned;
import net.runelite.api.events.ItemSpawned;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;

@Singleton
public class OSRSUtils {

  @Inject
  Client client;

  @Inject
  OSRSUtils(EventBus eventBus) {
    eventBus.register(this);
  }

  // These represent various entities. Stored via ID / WorldPoint for easy access
  public static HashMap<Integer, HashMap<WorldPoint, GameObject>> gameObjects = new HashMap<>();
  public static HashMap<Integer, List<TileItem>> loot = new HashMap<>();
  public static HashMap<Integer, List<NPC>> npcs = new HashMap<>();

  // Loads GameObjects from the map via Item IDs - Sorted by distance from local player ascending
  public List<GameObject> objects(int... ids) {
    List<GameObject> mergedGameObjects = new ArrayList<>();
    for (int id : ids) {
      HashMap<WorldPoint, GameObject> gameObjectsMap = gameObjects.get(id);
      if (gameObjectsMap != null) {
        mergedGameObjects.addAll(gameObjectsMap.values());
      }
    }
    if (mergedGameObjects.size() == 0)
      return null;
    mergedGameObjects.sort(Comparator.comparing(GameObject::getDistanceFromLocalPlayer));
    return mergedGameObjects;
  }

  public GameObject nearestObject(int... ids) {
    List<GameObject> objects = objects(ids);
    if (objects == null)
      return null;
    return objects.get(0);
  }

  // Loads NPCs from the map via IDs - Sorted by distance from local player ascending
  public List<NPC> npcs(int... ids) {
    List<NPC> mergedNPCs = new ArrayList<>();
    for (int id : ids) {
      List<NPC> npcs = OSRSUtils.npcs.get(id);
      if (npcs != null)
        mergedNPCs.addAll(npcs);
    }
    if (mergedNPCs.size() == 0)
      return null;
    mergedNPCs.sort(Comparator.comparing(NPC::getDistanceFromLocalPlayer));
    return mergedNPCs;
  }

  public NPC nearestNPC(int... ids) {
    List<NPC> npcs = npcs(ids);
    if (npcs == null)
      return null;
    return npcs.get(0);
  }

  // Loads GroundItems from the map via IDs - Sorted by distance from local player ascending
  public List<TileItem> loots(int... ids) {
    List<TileItem> mergedLoot = new ArrayList<>();
    for (int id : ids) {
      List<TileItem> loot = OSRSUtils.loot.get(id);
      if (loot != null)
        mergedLoot.addAll(loot);
    }
    if (mergedLoot.size() == 0)
      return null;
    mergedLoot.sort(Comparator.comparing(TileItem::getDistanceFromLocalPlayer));
    return mergedLoot;
  }

  public TileItem nearestLoot(int... ids) {
    List<TileItem> loots = loots(ids);
    if (loots == null)
      return null;
    return loots.get(0);
  }

  // Loads Inventory items from the map via IDs
  public List<WidgetItem> items(int... ids) {
    Widget inventoryWidget = client.getWidget(WidgetInfo.INVENTORY);
    if (inventoryWidget == null || inventoryWidget.isHidden())
    {
      return null;
    }
    ArrayList<WidgetItem> items = new ArrayList<>();
    for (WidgetItem item : inventoryWidget.getWidgetItems())
    {
      for (int i : ids) {
        if (item.getId() == i)
          items.add(item);
      }
    }
    if (items.size() == 0)
      return null;
    return items;
  }

  public WidgetItem firstItem(int... ids) {
    List<WidgetItem> items = items(ids);
    if (items == null)
      return null;
    return items.get(0);
  }

  //ALWAYS clear maps on GameState unless LOGGED_IN
  @Subscribe
  private void onGameStateChanged(GameStateChanged event) {
    if (event.getGameState() != GameState.LOGGED_IN) {
      resetGameObjects();
      loot.clear();
      npcs.clear();
    }
  }

  @Subscribe
  private void onGameObjectSpawned(GameObjectSpawned event) {
    resetGameObjects();
  }

  @Subscribe
  private void onGameObjectChanged(GameObjectChanged event) {
    resetGameObjects();
  }

  @Subscribe
  private void onGameObjectDespawned(GameObjectDespawned event) {
    resetGameObjects();
  }

  @Subscribe
  private void onNPCSpawned(NpcSpawned event) {
    List<NPC> knownSpawns = npcs.get(event.getNpc().getId());
    if (knownSpawns == null)
      knownSpawns = new ArrayList<>();

    knownSpawns.remove(event.getNpc());
    npcs.put(event.getNpc().getId(), knownSpawns);
  }

  @Subscribe
  private void onNPCDespawned(NpcDespawned event) {
    List<NPC> knownSpawns = npcs.get(event.getNpc().getId());
    if (knownSpawns == null)
      knownSpawns = new ArrayList<>();

    knownSpawns.remove(event.getNpc());
    npcs.put(event.getNpc().getId(), knownSpawns);
  }

  @Subscribe
  private void onItemSpawned(ItemSpawned event) {
    List<TileItem> knownSpawns = loot.get(event.getItem().getId());
    if (knownSpawns == null)
      knownSpawns = new ArrayList<>();

    knownSpawns.add(event.getItem());
    loot.put(event.getItem().getId(), knownSpawns);
  }

  @Subscribe
  private void onItemDespawned(ItemDespawned event) {
    List<TileItem> knownSpawns = loot.get(event.getItem().getId());
    if (knownSpawns == null)
      knownSpawns = new ArrayList<>();

    knownSpawns.remove(event.getItem());
    loot.put(event.getItem().getId(), knownSpawns);
  }

  // This is done to ensure a solid GameObject map
  private void resetGameObjects() {
    gameObjects.clear();
    for (Tile[][] ttt : client.getScene().getTiles())
      for (Tile[] tt : ttt)
        for (Tile t : tt)
          if (t != null)
            if (t.getGameObjects() != null)
              for (GameObject gameObject : t.getGameObjects())
                if (gameObject != null) {
                  HashMap<WorldPoint, GameObject> knownSpawnsMap = gameObjects.get(gameObject.getId());
                  if (knownSpawnsMap == null)
                    knownSpawnsMap = new HashMap<>();

                  knownSpawnsMap.put(t.getWorldLocation(), gameObject);
                  gameObjects.put(gameObject.getId(), knownSpawnsMap);
                }
  }
}
