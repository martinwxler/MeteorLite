package meteor.plugins.worldmapwalker;

import meteor.eventbus.Subscribe;
import meteor.menus.WidgetMenuOption;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.entities.Players;
import meteor.plugins.api.movement.Movement;
import meteor.plugins.api.widgets.Widgets;
import meteor.ui.overlay.worldmap.WorldMapOverlay;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import static net.runelite.api.widgets.WidgetInfo.MINIMAP_WORLDMAP_OPTIONS;

@PluginDescriptor(
        name = "World Map Walker",
        description = "Right click anywhere within the World Map to walk there",
        enabledByDefault = false
)
@Singleton
public class WorldMapWalkerPlugin extends Plugin {

    @Inject
    private WorldMapOverlay worldMapOverlay;

    private Point lastMenuOpenedPoint;
    private WorldPoint mapPoint;


    private static final String DESTINATION_MENU_TARGET = "<col=00ff00>Destination";

    @Override
    public void startup() {
    }

    @Override
    public void shutdown() {
    }

    @Subscribe
    public void onGameTick(GameTick event) {
        if (Movement.isWalking()) {
            return;
        }

        if (mapPoint == null || mapPoint.equals(Players.getLocal().getWorldLocation())) {
            mapPoint = null;
            return;
        }

        logger.debug("Destination is {} {}", mapPoint.getX(), mapPoint.getY());
        Movement.walkTo(mapPoint);
    }

    @Subscribe
    public void onMenuOpened(MenuOpened event) { //TODO: Event doesn't work
        lastMenuOpenedPoint = client.getMouseCanvasPosition();
    }

    //Check if menu options have already been added
    private boolean menuContainsEntries(){
        MenuEntry[] entries = client.getMenuEntries();
        if(entries != null){
            for (MenuEntry entry : entries) {
                if(entry == null){
                    continue;
                }
                if(entry.getTarget().equals(DESTINATION_MENU_TARGET)){
                    return true;
                }
            }
        }
        return false;
    }

    @Subscribe
    public void onMenuEntryAdded(MenuEntryAdded event) {

        final Widget map = client.getWidget(WidgetInfo.WORLD_MAP_VIEW);

        // If user clicks on map
        if (map != null && map.getBounds().contains(client.getMouseCanvasPosition().getX(), client.getMouseCanvasPosition().getY())) {
            if (!menuContainsEntries()) {
                addMenuEntry(event, "Walk to",0);
                addMenuEntry(event, "Clear",0);
                logger.info("World map param1: " + event.getActionParam1());
            }
            return;
        }
        // If user clicks on globe icon on minimap
        if (event.getActionParam1() == MINIMAP_WORLDMAP_OPTIONS.getId()) {
            if (!menuContainsEntries()) {
                addMenuEntry(event, "Clear",1);
            }
            return;
        }
        // If user right clicks on ground
        if (mapPoint != null && event.getOption().equals("Walk here")) {
            if (!menuContainsEntries()) {
                addMenuEntry(event, "Clear",1);
            }
        }
    }

    @Subscribe
    public void onMenuOptionClicked(MenuOptionClicked e) {
        if(e.getMenuTarget().equals(DESTINATION_MENU_TARGET)) {
            if (e.getMenuOption().equals("Walk to")) {
                mapPoint = calculateMapPoint(client.isMenuOpen() ? lastMenuOpenedPoint : client.getMouseCanvasPosition());
                logger.debug("Walking to: {}", mapPoint.toString());
                var mapWidget = Widgets.get(595, 38);

                if (mapWidget != null) {
                    Widgets.get(595, 38).interact("Close");
                }
            }

            if (e.getMenuOption().equals("Clear")) {
                logger.info("Stopping walking");
                mapPoint = null;
            }
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
        final WorldPoint mapPoint = new WorldPoint(renderOverview.getWorldMapPosition().getX(), renderOverview.getWorldMapPosition().getY(), 0);
        final Point middle = worldMapOverlay.mapWorldPointToGraphicsPoint(mapPoint);

        final int dx = (int) ((point.getX() - middle.getX()) / zoom);
        final int dy = (int) ((-(point.getY() - middle.getY())) / zoom);

        return mapPoint.dx(dx).dy(dy);
    }

    private void addMenuEntry(MenuEntryAdded event, String option, int index) {
        List<MenuEntry> entries = new LinkedList<>(Arrays.asList(client.getMenuEntries()));

        MenuEntry entry = new MenuEntry();
        entry.setOption(option);
        entry.setTarget(DESTINATION_MENU_TARGET);
        entry.setOpcode(MenuAction.RUNELITE.getId());
        entries.add(index,entry);

        client.setMenuEntries(entries.toArray(new MenuEntry[0]));
    }
}
