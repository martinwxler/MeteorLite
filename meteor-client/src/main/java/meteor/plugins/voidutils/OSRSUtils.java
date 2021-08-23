package meteor.plugins.voidutils;

import java.awt.*;
import java.util.*;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;

import com.owain.automation.Automation;
import meteor.callback.ClientThread;
import meteor.eventbus.EventBus;
import meteor.eventbus.Subscribe;
import meteor.events.AutomationClickEvent;
import meteor.events.AutomationMouseMoveEvent;
import meteor.plugins.voidutils.events.LocalPlayerIdleEvent;
import meteor.ui.overlay.OverlayUtil;
import net.runelite.api.*;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import org.sponge.util.Logger;

@Singleton
public class OSRSUtils {

  @Inject
  Client client;

  @Inject
  ClientThread clientThread;

  private boolean lastLocalPlayerIdleState = true;
  private long lastLocalPlayerIdleEventTime = System.currentTimeMillis();

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

  public GameObject nearestObject(List<GameObject> objects) {
    if (objects.size() == 0)
      return null;
    objects.sort(Comparator.comparing(GameObject::getDistanceFromLocalPlayer));
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

  public NPC nearestNPC(List<NPC> npcs) {
    if (npcs.size() == 0)
      return null;
    npcs.sort(Comparator.comparing(NPC::getDistanceFromLocalPlayer));
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

  public TileItem nearestLoot(List<TileItem> loots) {
    if (loots.size() == 0)
      return null;
    loots.sort(Comparator.comparing(TileItem::getDistanceFromLocalPlayer));
    return loots.get(0);
  }

  public List<NPC> findTransformedNPCs(String name) {
    List<NPC> npcs = new ArrayList<>();
    for (NPC npc: client.getNpcs()) {
      if (npc.getTransformedComposition() != null) {
        NPCComposition transformed = npc.getTransformedComposition();
        if (transformed.getName().equalsIgnoreCase(name))
          npcs.add(npc);
      }
    }
    npcs.sort(Comparator.comparing(NPC::getDistanceFromLocalPlayer));
    if (npcs.size() == 0)
      return null;
    return npcs;
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

  public int inventorySize() {
    Widget inventoryWidget = client.getWidget(WidgetInfo.INVENTORY);
    if (inventoryWidget == null || inventoryWidget.isHidden())
      return 0;
    ArrayList<WidgetItem> items = new ArrayList<>(inventoryWidget.getWidgetItems());
    if (items.size() == 0)
      return 0;
    return items.size();
  }

  public int inventoryFreeSlots() {
    Widget inventoryWidget = client.getWidget(WidgetInfo.INVENTORY);
    if (inventoryWidget == null || inventoryWidget.isHidden())
      return 0;
    ArrayList<WidgetItem> items = new ArrayList<>(inventoryWidget.getWidgetItems());
    if (items.size() == 0)
      return 0;
    return 28 - items.size();
  }

  public boolean inventoryFull() {
    Widget inventoryWidget = client.getWidget(WidgetInfo.INVENTORY);
    if (inventoryWidget == null || inventoryWidget.isHidden())
      return false;
    ArrayList<WidgetItem> items = new ArrayList<>(inventoryWidget.getWidgetItems());
    if (items.size() == 0)
      return false;
    return 28 == items.size();
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
  private void onGameTick(GameTick event) {
    if (client.getLocalPlayer().isIdle() && (System.currentTimeMillis() > lastLocalPlayerIdleEventTime + 600)) {
      client.getCallbacks().post(LocalPlayerIdleEvent.INSTANCE);
      lastLocalPlayerIdleEventTime = System.currentTimeMillis();
    }
    if (client.getLocalPlayer().isIdle())
      if (!lastLocalPlayerIdleState) {
        client.getCallbacks().post(LocalPlayerIdleEvent.INSTANCE);
        lastLocalPlayerIdleEventTime = System.currentTimeMillis();
      }
    lastLocalPlayerIdleState = client.getLocalPlayer().isIdle();
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
    NPCComposition composition = event.getNpc().getComposition();

    List<NPC> knownSpawns = npcs.get(composition.getId());
    if (knownSpawns == null)
      knownSpawns = new ArrayList<>();

    knownSpawns.add(event.getNpc());
    npcs.put(composition.getId(), knownSpawns);
  }

  @Subscribe
  private void onNPCTransformedSpawned(NpcTransformedSpawned event) {
    NPCComposition composition = event.getNpc().getTransformedComposition();

    List<NPC> knownSpawns = npcs.get(composition.getId());
    if (knownSpawns == null)
      knownSpawns = new ArrayList<>();

    knownSpawns.add(event.getNpc());
    npcs.put(composition.getId(), knownSpawns);
  }

  @Subscribe
  private void onNPCChanged(NpcChanged event) {
    NPCComposition composition = event.getNpc().getComposition();

    NPC npcToRemove = null;
    if (npcs.get(composition.getId()) != null)
      for (NPC npc : npcs.get(composition.getId())) {
        if (npc.getLocalLocation().getX() == event.getNpc().getLocalLocation().getX())
          if (npc.getLocalLocation().getY() == event.getNpc().getLocalLocation().getY());
        npcToRemove = npc;
      }
    if (npcToRemove != null) {
      npcs.get(composition.getId()).remove(npcToRemove);
    }
  }

  @Subscribe
  private void onNPCTransformedChanged(NpcTransformedChanged event) {
    NPCComposition composition = event.getNpc().getTransformedComposition();

    NPC npcToRemove = null;
    if (npcs.get(composition.getId()) != null)
      for (NPC npc : npcs.get(composition.getId())) {
        if (npc.getLocalLocation().getX() == event.getNpc().getLocalLocation().getX())
          if (npc.getLocalLocation().getY() == event.getNpc().getLocalLocation().getY());
        npcToRemove = npc;
      }
    if (npcToRemove != null) {
      npcs.get(composition.getId()).remove(npcToRemove);
    }
  }

  @Subscribe
  private void onNPCDespawned(NpcDespawned event) {
    NPCComposition composition = event.getNpc().getComposition();
    List<NPC> knownSpawns = npcs.get(composition.getId());
    if (knownSpawns == null)
      knownSpawns = new ArrayList<>();

    knownSpawns.remove(event.getNpc());
    npcs.put(composition.getId(), knownSpawns);
  }

  @Subscribe
  private void onNPCTransformedDespawned(NpcTransformedDespawned event) {
    NPCComposition composition = event.getNpc().getTransformedComposition();
    List<NPC> knownSpawns = npcs.get(composition.getId());
    if (knownSpawns == null)
      knownSpawns = new ArrayList<>();

    knownSpawns.remove(event.getNpc());
    npcs.put(composition.getId(), knownSpawns);
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

  public Tile tileAt(WorldPoint worldPoint) {
    LocalPoint localPoint = LocalPoint.fromWorld(client, worldPoint);
    if (localPoint != null)
      return client.getScene().getTiles()[client.getPlane()][localPoint.getSceneX()][localPoint
            .getSceneY()];
    return null;
  }

  public Tile tileRandom(WorldPoint minXY, WorldPoint maxXY) {
    Random random = new Random();
    int randomX = minXY.getX() + random.nextInt(maxXY.getX() - minXY.getX());
    int randomY = minXY.getY() + random.nextInt(maxXY.getY() - minXY.getY());
    WorldPoint randomPoint = new WorldPoint(randomX, randomY, minXY.getPlane());
    return findTile(randomPoint);
  }

  private Tile findTile(WorldPoint wp) {
    for (Tile[][] ttt : client.getScene().getTiles())
      for (Tile[] tt : ttt)
        for (Tile t : tt)
          if (t != null) {
            WorldPoint location = t.getWorldLocation();
            if (location.getPlane() == wp.getPlane())
              if (location.getY() == wp.getY())
                if (location.getX() == wp.getX())
                  return t;
          }
    return null;
  }

  public List<Tile> tiles(WorldPoint minXY, WorldPoint maxXY) {
    List<Tile> tiles = new ArrayList<>();
    for (Tile[][] ttt : client.getScene().getTiles())
      for (Tile[] tt : ttt)
        for (Tile t : tt)
          if (t != null) {
            WorldPoint location = t.getWorldLocation();
            if (t.getPlane() == minXY.getPlane())
              if (location.getPlane() == maxXY.getPlane() &&
                      (location.getX() >= minXY.getX()) &&
                      (location.getX() <= maxXY.getX()) &&
                      (location.getY() >= minXY.getY()) &&
                      (location.getY() <= maxXY.getY())) {
                tiles.add(t);
              }
          }
    return tiles;
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

  @Subscribe
  private void onAutomationClickEvent(AutomationClickEvent event) {
    new Logger("asd").debug("Processing click");
    Point p = Automation.getClickPoint(event.bounds);
    Automation.move(p, client);
    Automation.click(p, client);
  }

  @Subscribe
  private void onAutomationMouseMoveEvent(AutomationMouseMoveEvent event) {
    Automation.move(event.point, client);
  }
}
