package meteor.plugins;

import static org.sponge.util.Logger.ANSI_CYAN;
import static org.sponge.util.Logger.ANSI_YELLOW;

import com.google.inject.Inject;
import meteor.MeteorLite;
import meteor.eventbus.Subscribe;
import meteor.eventbus.events.GameMessageReceived;
import net.runelite.api.Client;
import net.runelite.api.events.DecorativeObjectChanged;
import net.runelite.api.events.DecorativeObjectDespawned;
import net.runelite.api.events.DecorativeObjectSpawned;
import net.runelite.api.events.GameObjectChanged;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.GroundObjectChanged;
import net.runelite.api.events.GroundObjectDespawned;
import net.runelite.api.events.GroundObjectSpawned;
import net.runelite.api.events.ItemDespawned;
import net.runelite.api.events.ItemSpawned;
import net.runelite.api.events.NpcChanged;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;
import net.runelite.api.events.WallObjectChanged;
import net.runelite.api.events.WallObjectDespawned;
import net.runelite.api.events.WallObjectSpawned;
import org.sponge.util.Logger;

@PluginDescriptor(
    name = "Event Test",
    description = "prints debug text for untested events"
)
public class EventTestPlugin extends Plugin {

  public String name = ANSI_CYAN + "EventLoggerPlugin" + ANSI_YELLOW;

  Logger logger = new Logger(name);

  @Inject
  Client client;

  @Subscribe
  public void test(GameStateChanged event) {
    //logger.event("GameStateChanged", "" + event.getGameState());
  }

  @Subscribe
  public void onGameTick(GameTick event) {
    MeteorLite.frame.setTitle("MeteorLite - " + client.getLocalPlayer().getName());
  }

  @Subscribe
  public void test1(GameMessageReceived event) {
    logger.event("GameMessageReceived", "" + event.text);
  }

  @Subscribe
  public void test2(NpcSpawned event) {
    //logger.event("NpcSpawned", event.getNpc().getName());
  }

  @Subscribe
  public void test3(NpcChanged event) {
    //logger.event("NpcChanged", event.getNpc().getName());
  }

  @Subscribe
  public void testt(NpcDespawned event) {
    //logger.event("NpcDespawned", event.getNpc().getName());
  }

  @Subscribe
  public void test5(GameObjectSpawned event) {
    //logger.event("GameObjectSpawned", event.getGameObject().getName());
  }

  @Subscribe
  public void test6(GameObjectDespawned event) {
    //logger.event("GameObjectDespawned", event.getGameObject().getName());
  }

  @Subscribe
  public void test7(GameObjectChanged event) {
    //logger.event("GameObjectChanged", event.getGameObject().getName());
  }

  @Subscribe
  public void test8(WallObjectDespawned event) {
    //logger.event("WallObjectDespawned", event.getWallObject().getName());
  }

  @Subscribe
  public void test9(WallObjectSpawned event) {
    //logger.event("WallObjectSpawned", event.getWallObject().getName());
  }

  @Subscribe
  public void test10(WallObjectChanged event) {
    logger.event("WallObjectChanged", event.getWallObject().getName());
  }

  @Subscribe
  public void test11(DecorativeObjectDespawned event) {
    logger.event("DecorativeObjectDespawned", event.getDecorativeObject().getName());
  }

  @Subscribe
  public void test12(DecorativeObjectSpawned event) {
    //logger.event("DecorativeObjectSpawned", event.getDecorativeObject().getName());
  }

  @Subscribe
  public void test13(DecorativeObjectChanged event) {
    logger.event("DecorativeObjectChanged", event.getDecorativeObject().getName());
  }

  @Subscribe
  public void test14(GroundObjectDespawned event) {
    //logger.event("GroundObjectDespawned", event.getGroundObject().getName());
  }

  @Subscribe
  public void test15(GroundObjectSpawned event) {
    //logger.event("GroundObjectSpawned", event.getGroundObject().getName());
  }

  @Subscribe
  public void test16(GroundObjectChanged event) {
    logger.event("GroundObjectChanged", event.getGroundObject().getName());
  }

  @Subscribe
  public void test17(ItemDespawned event) {
    //logger.event("ItemDespawned", client.getItemComposition(event.getItem().getId()).getName());
  }

  @Subscribe
  public void test18(ItemSpawned event) {
    //logger.event("ItemSpawned", client.getItemComposition(event.getItem().getId()).getName());
  }
}
