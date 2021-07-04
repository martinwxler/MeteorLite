package meteor.plugins;

import net.runelite.api.*;
import net.runelite.api.events.*;
import meteor.Plugin;
import meteor.eventbus.Subscribe;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.sponge.util.Logger.ANSI_CYAN;
import static org.sponge.util.Logger.ANSI_YELLOW;

public class DebugPlugin extends Plugin
{
    public String name = ANSI_CYAN + "EventLoggerPlugin" + ANSI_YELLOW;
    {
        logger.name = name;
    }

    public List<GameObject> gameObjects = new ArrayList<>();
    public List<GroundObject> groundObjects = new ArrayList<>();
    public List<DecorativeObject> decorativeObjects = new ArrayList<>();
    public List<WallObject> wallObjects = new ArrayList<>();
    public List<NPC> npcs = new ArrayList<>();
    public List<Player> players = new ArrayList<>();

    public boolean shouldPaint = true;



    @Subscribe
    public void onGameStateChanged(GameStateChanged event)
    {
        if (event.getGameState() != GameState.LOGGED_IN)
        {
            gameObjects.clear();
            groundObjects.clear();
            decorativeObjects.clear();
            wallObjects.clear();
            npcs.clear();
            players.clear();
        }
    }

    @Subscribe
    public void onGameObjectSpawned(GameObjectSpawned event)
    {
        gameObjects.add(event.getGameObject());
    }

    @Subscribe
    public void onGroundObjectSpawned(GroundObjectSpawned event)
    {
        groundObjects.add(event.getGroundObject());
    }

    @Subscribe
    public void onDecorativeObjectSpawned(DecorativeObjectSpawned event)
    {
        decorativeObjects.add(event.getDecorativeObject());
    }

    @Subscribe
    public void onWallObjectSpawned(WallObjectSpawned event)
    {
        wallObjects.add(event.getWallObject());
    }

    @Subscribe
    public void onNpcSpawned(NpcSpawned event)
    {
        npcs.add(event.getNpc());
    }

    @Subscribe
    public void onPlayerSpawned(PlayerSpawned event)
    {
        players.add(event.getPlayer());
    }

    @Override
    public void paintAboveScene(Graphics2D graphics2D)
    {
        if (shouldPaint)
        {
            graphics2D.setColor(Color.cyan);
            for (GameObject go : gameObjects)
            {
                if (go.getConvexHull() != null)
                {
                    graphics2D.draw(go.getConvexHull());
                }
            }
            for (GroundObject go : groundObjects)
            {
                if (go.getConvexHull() != null)
                {
                    graphics2D.draw(go.getConvexHull());
                }
            }
            for (DecorativeObject go : decorativeObjects)
            {
                if (go.getConvexHull() != null)
                {
                    graphics2D.draw(go.getConvexHull());
                }
            }
            for (WallObject go : wallObjects)
            {
                if (go.getConvexHull() != null)
                {
                    graphics2D.draw(go.getConvexHull());
                }
            }
            for (NPC go : npcs)
            {
                if (go.getConvexHull() != null)
                {
                    graphics2D.draw(go.getConvexHull());
                }
            }
            for (Player go : players)
            {
                if (go.getConvexHull() != null)
                {
                    graphics2D.draw(go.getConvexHull());
                }
            }
        }

    }
}
