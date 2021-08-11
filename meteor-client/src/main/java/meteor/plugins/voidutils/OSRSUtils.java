package meteor.plugins.voidutils;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import javax.inject.Inject;
import javax.inject.Singleton;
import meteor.eventbus.EventBus;
import meteor.eventbus.Subscribe;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.GameState;
import net.runelite.api.NPC;
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

  public static HashMap<Integer, HashMap<WorldPoint, GameObject>> gameObjects = new HashMap<>();
  public static HashMap<Integer, List<TileItem>> loot = new HashMap<>();
  public static HashMap<Integer, List<NPC>> npcs = new HashMap<>();

  public List<GameObject> objects(int... ids) {
    List<GameObject> mergedGameObjects = new ArrayList<>();
    for (int id : ids) {
      HashMap<WorldPoint, GameObject> gameObjectsMap = gameObjects.get(id);
      if (gameObjectsMap != null) {
        mergedGameObjects.addAll(gameObjectsMap.values());
      }
    }
    mergedGameObjects.sort(Comparator.comparing(GameObject::getDistanceFromLocalPlayer));
    try
    {
      mergedGameObjects.get(0);
    }
    catch (Exception e) {
      mergedGameObjects.add(null);
    }
    return mergedGameObjects;
  }

  public List<NPC> npcs(int... ids) {
    List<NPC> mergedNPCs = new ArrayList<>();
    for (int id : ids) {
      List<NPC> npcs = OSRSUtils.npcs.get(id);
      if (npcs != null)
        mergedNPCs.addAll(npcs);
    }
    mergedNPCs.sort(Comparator.comparing(NPC::getDistanceFromLocalPlayer));
    try
    {
      mergedNPCs.get(0);
    }
    catch (Exception e) {
      mergedNPCs.add(null);
    }
    return mergedNPCs;
  }

  public List<TileItem> loot(int... ids) {
    List<TileItem> mergedLoot = new ArrayList<>();
    for (int id : ids) {
      List<TileItem> loot = OSRSUtils.loot.get(id);
      if (loot != null)
        mergedLoot.addAll(loot);
    }
    mergedLoot.sort(Comparator.comparing(TileItem::getDistanceFromLocalPlayer));
    try
    {
      mergedLoot.get(0);
    }
    catch (Exception e) {
      mergedLoot.add(null);
    }
    return mergedLoot;
  }

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
    return items;
  }

  @Subscribe
  public void onGameStateChanged(GameStateChanged event) {
    if (event.getGameState() != GameState.LOGGED_IN) {
      gameObjects.clear();
      loot.clear();
      npcs.clear();
    }
  }

  @Subscribe
  public void onGameObjectSpawned(GameObjectSpawned event) {
    HashMap<WorldPoint, GameObject> knownSpawnsMap = gameObjects.get(event.getGameObject().getId());
    if (knownSpawnsMap == null)
      knownSpawnsMap = new HashMap<>();

    knownSpawnsMap.put(event.getTile().getWorldLocation(), event.getGameObject());
    gameObjects.put(event.getGameObject().getId(), knownSpawnsMap);
  }

  @Subscribe
  public void onGameObjectChanged(GameObjectChanged event) {
    HashMap<WorldPoint, GameObject> knownOldSpawnsMap = gameObjects.get(event.getOldObject().getId());
    if (knownOldSpawnsMap == null)
      knownOldSpawnsMap = new HashMap<>();

    WorldPoint toRemove = null;
    for (WorldPoint wp : knownOldSpawnsMap.keySet()) {
      if (wp.getX() == event.getTile().getWorldLocation().getX())
        if (wp.getY() == event.getTile().getWorldLocation().getY())
          if (wp.getPlane() == event.getTile().getWorldLocation().getPlane())
          {
            toRemove = wp;
            break;
          }
    }

    knownOldSpawnsMap.remove(toRemove);
    gameObjects.put(event.getOldObject().getId(), knownOldSpawnsMap);

    HashMap<WorldPoint, GameObject> knownNewSpawnsMap = gameObjects.get(event.getNewObject().getId());
    if (knownNewSpawnsMap == null)
      knownNewSpawnsMap = new HashMap<>();

    knownNewSpawnsMap.put(event.getTile().getWorldLocation(), event.getNewObject());
    gameObjects.put(event.getNewObject().getId(), knownNewSpawnsMap);
  }

  @Subscribe
  public void onGameObjectDespawned(GameObjectDespawned event) {
    HashMap<WorldPoint, GameObject> knownSpawnsMap = gameObjects.get(event.getGameObject().getId());
    if (knownSpawnsMap == null)
      knownSpawnsMap = new HashMap<>();

    WorldPoint toRemove = null;
    for (WorldPoint wp : knownSpawnsMap.keySet()) {
      if (wp.getX() == event.getTile().getWorldLocation().getX())
        if (wp.getY() == event.getTile().getWorldLocation().getY())
          if (wp.getPlane() == event.getTile().getWorldLocation().getPlane())
          {
            toRemove = wp;
            break;
          }
    }

    knownSpawnsMap.remove(toRemove);
    gameObjects.put(event.getGameObject().getId(), knownSpawnsMap);
  }

  @Subscribe
  public void onNPCSpawned(NpcSpawned event) {
    List<NPC> knownSpawns = npcs.get(event.getNpc().getId());
    if (knownSpawns == null)
      knownSpawns = new ArrayList<>();

    knownSpawns.remove(event.getNpc());
    npcs.put(event.getNpc().getId(), knownSpawns);
  }

  @Subscribe
  public void onNPCDespawned(NpcDespawned event) {
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
}
