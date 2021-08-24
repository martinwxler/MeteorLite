package meteor.plugins.api.example.walking;

import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.movement.Movement;
import meteor.plugins.voidutils.events.LocalPlayerIdleEvent;
import meteor.ui.overlay.OverlayLayer;
import meteor.ui.overlay.OverlayManager;
import meteor.ui.overlay.OverlayPosition;
import meteor.ui.overlay.OverlayPriority;
import net.runelite.api.GameState;
import net.runelite.api.MenuAction;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.MenuOptionClicked;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

@PluginDescriptor(
        name = "Walker debug",
        description = "Weed",
        enabledByDefault = false
)
@Singleton
public class WalkerPlugin extends Plugin {
    @Inject
    private ScheduledExecutorService executorService;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private WalkerOverlay overlay;

    @Inject
    private WorldMapOverlay worldMapOverlay;

    @Inject
    private WalkerConfig config;

    @Provides
    public WalkerConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(WalkerConfig.class);
    }

    private Point lastMenuOpenedPoint;
    private boolean mapWalking;
    private WorldPoint mapPoint;

    @Override
    public void startup() {
        overlay.setPosition(OverlayPosition.DYNAMIC);
        overlay.setLayer(OverlayLayer.ABOVE_WIDGETS);
        overlay.setPriority(OverlayPriority.LOW);

        overlayManager.add(overlay);
    }

    @Override
    public void shutdown() {
        overlayManager.remove(overlay);
    }

    @Subscribe
    public void onLocalPlayerIdle(LocalPlayerIdleEvent e) {
        if (Movement.isWalking()) {
            return;
        }

        if (config.walk() || mapWalking) {
            WorldPoint walkPoint = mapWalking ? mapPoint : new WorldPoint(config.x(), config.y(), client.getPlane());
            logger.debug("Destination is {} {}", walkPoint.getX(), walkPoint.getY());
            overlay.setTile(walkPoint);
            Movement.walkTo(walkPoint);
        }
    }

    @Subscribe
    public void onMenuOpened(MenuOpened event) {
        logger.info("Last menu opened point: {}", client.getMouseCanvasPosition());
        lastMenuOpenedPoint = client.getMouseCanvasPosition();
    }

    @Subscribe
    public void onMenuEntryAdded(MenuEntryAdded event) {
        final Widget map = client.getWidget(WidgetInfo.WORLD_MAP_VIEW);

        if (map == null) {
            return;
        }

        if (map.getBounds().contains(client.getMouseCanvasPosition().getX(), client.getMouseCanvasPosition().getY())) {
            addMenuEntry(event, "Map walk here");
            addMenuEntry(event, "Clear destination");
        }
    }

    @Subscribe
    public void onMenuOptionClicked(MenuOptionClicked e) {
        if (e.getMenuAction() == MenuAction.WALK && config.walk()) {
            e.consume();
        }

        if (e.getMenuOption().equals("Map walk here")) {
            mapPoint = calculateMapPoint(/*client.isMenuOpen() ? lastMenuOpenedPoint :*/ client.getMouseCanvasPosition());
            logger.debug("Walking to: {}", mapPoint.toString());
            var mapWidget = Widgets.get(595, 38);

            if (mapWidget != null) {
                Widgets.get(595, 38).interact("Close");
            }
            overlay.setTile(mapPoint);
//            executorService.execute(() -> Movement.walkTo(mapPoint));
            mapWalking = true;
        }

        if (e.getMenuOption().equals("Clear Destination") && Movement.isWalking()) {
            logger.debug("Stopping walking");
            mapWalking = false;
        }
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged e) {
        if (e.getGameState() == GameState.LOGIN_SCREEN) {
            client.setUsername("");
            client.setPassword("");
        }
    }

    private WorldPoint calculateMapPoint(Point point) {
        float zoom = client.getRenderOverview().getWorldMapZoom();
        RenderOverview renderOverview = client.getRenderOverview();
        final WorldPoint mapPoint = new WorldPoint(renderOverview.getWorldMapPosition().getX(), renderOverview.getWorldMapPosition().getY() + 40, 0);
        final Point middle = worldMapOverlay.mapWorldPointToGraphicsPoint(mapPoint);

        final int dx = (int) ((point.getX() - middle.getX()) / zoom);
        final int dy = (int) ((-(point.getY() - middle.getY())) / zoom);

        return mapPoint.dx(dx).dy(dy);
    }

    private void addMenuEntry(MenuEntryAdded event, String option) {
        List<MenuEntry> entries = new LinkedList<>(Arrays.asList(client.getMenuEntries()));

        MenuEntry entry = new MenuEntry();
        entry.setOption(option);
        entry.setTarget(event.getTarget());
        entry.setOpcode(MenuAction.RUNELITE.getId());
        entries.add(0, entry);

        client.setMenuEntries(entries.toArray(new MenuEntry[0]));
    }
}
