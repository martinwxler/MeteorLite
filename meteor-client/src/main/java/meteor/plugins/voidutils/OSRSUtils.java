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
