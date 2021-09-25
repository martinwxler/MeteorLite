package meteor.plugins.objecthider;

import com.google.inject.Inject;
import com.google.inject.Provides;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import meteor.eventbus.events.ConfigChanged;
import net.runelite.api.Client;
import net.runelite.api.Constants;
import net.runelite.api.DecorativeObject;
import net.runelite.api.GameObject;
import net.runelite.api.GameState;
import net.runelite.api.GroundObject;
import net.runelite.api.Scene;
import net.runelite.api.Tile;
import net.runelite.api.WallObject;
import net.runelite.api.events.GameStateChanged;
import meteor.callback.ClientThread;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;

@PluginDescriptor(
    name = "Object Hider",
    description = "Hides various objects",
    enabledByDefault = false
)

public class ObjectHiderPlugin extends Plugin
{
  static final String CONFIG_GROUP = "objecthider";

  @Inject
  private Client client;

  @Inject
  private ClientThread clientThread;

  @Inject
  private ObjectHiderConfig config;

  private List<Integer> objectIds = List.of();
  private List<String> objectNames = List.of();

  @Provides
  public ObjectHiderConfig getConfig(final ConfigManager configManager)
  {
    return configManager.getConfig(ObjectHiderConfig.class);
  }

  public void startUp()
  {
    if (!config.objectIds().equals(""))
    {
      try
      {
        objectIds = Arrays.stream(config.objectIds().split(","))
            .map(s -> Integer.parseInt(s.trim()))
            .collect(Collectors.toList());
      }
      catch (Exception ex)
      {
        // Don't care
      }
    }

    try
    {
      objectNames = Arrays.stream(config.objectNames().split(","))
          .map(String::trim)
          .map(String::toLowerCase)
          .collect(Collectors.toList());
    }
    catch (Exception ex)
    {
      // Don't care
    }

    if (client.getGameState() == GameState.LOGGED_IN)
    {
      clientThread.invoke(this::hide);
    }
  }

  public void shutDown()
  {
    objectIds = List.of();
    objectNames = List.of();

    clientThread.invoke(() ->
    {
      if (client.getGameState() == GameState.LOGGED_IN)
      {
        client.setGameState(GameState.LOADING);
      }
    });
  }

  @Subscribe
  public void onGameStateChanged(GameStateChanged gameStateChanged)
  {
    if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
    {
      hide();
    }
  }

  @Subscribe
  public void onConfigChanged(ConfigChanged configChanged)
  {
    if (!configChanged.getGroup().equals(CONFIG_GROUP))
    {
      return;
    }

    if (configChanged.getKey().equals("objectIds"))
    {
      if (!config.objectIds().equals(""))
      {
        try
        {
          objectIds = Arrays.stream(config.objectIds().split(","))
              .map(s -> Integer.parseInt(s.trim()))
              .collect(Collectors.toList());
        }
        catch (Exception ex)
        {
        }
      }
    }
    else if (configChanged.getKey().equals("objectNames"))
    {
      try
      {
        objectNames = Arrays.stream(config.objectNames().split(","))
            .map(String::trim)
            .map(String::toLowerCase)
            .collect(Collectors.toList());
      }
      catch (Exception ex)
      {
      }
    }

    clientThread.invoke(() ->
    {
      if (client.getGameState() == GameState.LOGGED_IN)
      {
        client.setGameState(GameState.LOADING);
      }
    });
  }

  private void hide()
  {
    Scene scene = client.getScene();
    Tile[][][] tiles = scene.getTiles();

    for (int z = 0; z < 3; ++z){
      for (int x = 0; x < Constants.SCENE_SIZE; ++x)
      {
        for (int y = 0; y < Constants.SCENE_SIZE; ++y)
        {
          Tile tile = tiles[z][x][y];
          if (tile == null)
          {
            continue;
          }

          GameObject[] gameObjects = tile.getGameObjects();
          DecorativeObject decorativeObject = tile.getDecorativeObject();
          WallObject wallObject = tile.getWallObject();
          GroundObject groundObject = tile.getGroundObject();

          for (GameObject gameObject : gameObjects)
          {
            if (gameObject != null)
            {
              if (config.hideAllGameObjects() || objectIds.contains(gameObject.getId()) ||
                  (!objectNames.isEmpty() && objectNames.contains(client.getObjectComposition(gameObject.getId()).getName().toLowerCase()))
              )
              {
                scene.removeGameObject(z, x, y);
              }
            }
          }

          if (decorativeObject != null)
          {
            if (config.hideAllDecorativeObjects() || objectIds.contains(decorativeObject.getId()) ||
                (!objectNames.isEmpty() && objectNames.contains(client.getObjectComposition(decorativeObject.getId()).getName().toLowerCase()))
            )
            {
              scene.removeDecorativeObject(z, x, y);
            }
          }

          if (wallObject != null)
          {
            if (config.hideAllWallObjects() || objectIds.contains(wallObject.getId()) ||
                (!objectNames.isEmpty() && objectNames.contains(client.getObjectComposition(wallObject.getId()).getName().toLowerCase()))
            )
            {
              scene.removeWallObject(z, x, y);
            }
          }

          if (groundObject != null && config.hideAllGroundObjects())
          {
            if (config.hideAllGroundObjects() || objectIds.contains(groundObject.getId()) ||
                (!objectNames.isEmpty() && objectNames.contains(client.getObjectComposition(groundObject.getId()).getName().toLowerCase()))
            )
            {
              scene.removeGroundObject(z, x, y);
            }
          }
        }
      }
    }
  }
}