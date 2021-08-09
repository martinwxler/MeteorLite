package meteor.plugins.barrowsminimap;

import java.util.*;
import javax.inject.Inject;
import java.util.HashSet;
import com.google.common.collect.Sets;
import lombok.AccessLevel;
import lombok.Getter;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.ui.overlay.OverlayManager;
import net.runelite.api.*;
import net.runelite.api.events.*;

@PluginDescriptor(
        name = "Barrows Minimap",
        enabledByDefault = false,
        description = "Adds back the minimap to Barrows",
        tags = {"barrows", "minimap", "combat", "pvm"}
)
public class BarrowsMinimap extends Plugin
{
    @Inject
    private Client client;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private BarrowsMinimapOverlay barrowsMinimapOverlay;

    @Getter(AccessLevel.PACKAGE)
    private static final Set<Integer> BARROWS_WALLS = Sets.newHashSet
            (
                    ObjectID.DOOR_20678, NullObjectID.NULL_20681, NullObjectID.NULL_20682, NullObjectID.NULL_20683, NullObjectID.NULL_20684, NullObjectID.NULL_20685, NullObjectID.NULL_20686, NullObjectID.NULL_20687,
                    NullObjectID.NULL_20688, NullObjectID.NULL_20689, NullObjectID.NULL_20690, NullObjectID.NULL_20691, NullObjectID.NULL_20692, NullObjectID.NULL_20693, NullObjectID.NULL_20694, NullObjectID.NULL_20695,
                    NullObjectID.NULL_20696, ObjectID.DOOR_20697, NullObjectID.NULL_20700, NullObjectID.NULL_20701, NullObjectID.NULL_20702, NullObjectID.NULL_20703, NullObjectID.NULL_20704, NullObjectID.NULL_20705,
                    NullObjectID.NULL_20706, NullObjectID.NULL_20707, NullObjectID.NULL_20708, NullObjectID.NULL_20709, NullObjectID.NULL_20710, NullObjectID.NULL_20711, NullObjectID.NULL_20712, NullObjectID.NULL_20713,
                    NullObjectID.NULL_20714, NullObjectID.NULL_20715, NullObjectID.NULL_20728, NullObjectID.NULL_20730
            );

    private static final Set<Integer> BARROWS_LADDERS = Sets.newHashSet(NullObjectID.NULL_20675, NullObjectID.NULL_20676, NullObjectID.NULL_20677);

    @Getter(AccessLevel.PACKAGE)
    private final Set<WallObject> walls = new HashSet<>();

    @Getter(AccessLevel.PACKAGE)
    private final Set<GameObject> ladders = new HashSet<>();



    @Override
    public void startup()
    {
        overlayManager.add(barrowsMinimapOverlay);
    }

    @Override
    public void shutdown()
    {
        overlayManager.remove(barrowsMinimapOverlay);
        walls.clear();
        ladders.clear();
    }
    @Subscribe
    private void onWallObjectSpawned(WallObjectSpawned event)
    {
        WallObject wallObject = event.getWallObject();
        if (BARROWS_WALLS.contains(wallObject.getId()))
        {
            walls.add(wallObject);
        }
    }

    @Subscribe
    private void onWallObjectChanged(WallObjectChanged event)
    {
        WallObject previous = event.getPrevious();
        WallObject wallObject = event.getWallObject();

        walls.remove(previous);
        if (BARROWS_WALLS.contains(wallObject.getId()))
        {
            walls.add(wallObject);
        }
    }

    @Subscribe
    private void onWallObjectDespawned(WallObjectDespawned event)
    {
        WallObject wallObject = event.getWallObject();
        walls.remove(wallObject);
    }

    @Subscribe
    private void onGameObjectSpawned(GameObjectSpawned event)
    {
        GameObject gameObject = event.getGameObject();
        if (BARROWS_LADDERS.contains(gameObject.getId()))
        {
            ladders.add(gameObject);
        }
    }

    @Subscribe
    private void onGameObjectChanged(GameObjectChanged event)
    {
        GameObject previous = event.getPrevious();
        GameObject gameObject = event.getGameObject();

        ladders.remove(previous);
        if (BARROWS_LADDERS.contains(gameObject.getId()))
        {
            ladders.add(gameObject);
        }
    }

    @Subscribe
    private void onGameObjectDespawned(GameObjectDespawned event)
    {
        GameObject gameObject = event.getGameObject();
        ladders.remove(gameObject);
    }

    @Subscribe
    private void onGameStateChanged(GameStateChanged event) {
        if (event.getGameState() == GameState.LOADING && (!walls.isEmpty() || !ladders.isEmpty())) {
            walls.clear();
            ladders.clear();
        }
    }
}
