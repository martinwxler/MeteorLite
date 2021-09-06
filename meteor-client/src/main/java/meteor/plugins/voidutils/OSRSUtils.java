package meteor.plugins.voidutils;

import javax.inject.Inject;
import javax.inject.Singleton;

import meteor.eventbus.EventBus;
import meteor.eventbus.Subscribe;
import meteor.plugins.voidutils.events.LocalPlayerIdleEvent;
import net.runelite.api.*;
import net.runelite.api.events.*;

@Singleton
public class OSRSUtils {

  @Inject
  Client client;

  private boolean lastLocalPlayerIdleState = true;
  private long lastLocalPlayerIdleEventTime = System.currentTimeMillis();

  @Inject
  OSRSUtils(EventBus eventBus) {
    eventBus.register(this);
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
}
