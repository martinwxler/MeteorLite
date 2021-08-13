package meteor.plugins.devtools;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;

@ConfigGroup("devtools")
public interface DevToolsConfig extends Config {

  @ConfigItem(
      keyName = "playersActive",
      name = "Players",
      description = ""
  )
  default boolean playersActive()
  {
    return false;
  }

  @ConfigItem(
      keyName = "npcsActive",
      name = "NPCs",
      description = ""
  )
  default boolean npcsActive()
  {
    return false;
  }

  @ConfigItem(
      keyName = "groundItemsActive",
      name = "Ground Items",
      description = ""
  )
  default boolean groundItemsActive()
  {
    return false;
  }

  @ConfigItem(
      keyName = "groundObjectsActive",
      name = "Ground Objects",
      description = ""
  )
  default boolean groundObjectsActive()
  {
    return false;
  }

  @ConfigItem(
      keyName = "wallsActive",
      name = "Walls",
      description = ""
  )
  default boolean wallsActive()
  {
    return false;
  }

  @ConfigItem(
      keyName = "gameObjectsActive",
      name = "gameObjects",
      description = ""
  )
  default boolean gameObjectsActive()
  {
    return false;
  }

  @ConfigItem(
      keyName = "decorationsActive",
      name = "Ground Decorations",
      description = ""
  )
  default boolean decorationsActive()
  {
    return false;
  }

  @ConfigItem(
      keyName = "tileLocationsActive",
      name = "Tile Location",
      description = ""
  )
  default boolean tileLocationsActive()
  {
    return false;
  }

  @ConfigItem(
      keyName = "movementFlagsActive",
      name = "Movement Flags",
      description = ""
  )
  default boolean movementFlagsActive()
  {
    return false;
  }

  @ConfigItem(
      keyName = "inventoryActive",
      name = "Inventory",
      description = ""
  )
  default boolean inventoryActive()
  {
    return false;
  }

  @ConfigItem(
      keyName = "projectilesActive",
      name = "Projectiles",
      description = ""
  )
  default boolean projectilesActive()
  {
    return false;
  }

  @ConfigItem(
      keyName = "graphcisObjectsActive",
      name = "Graphics Objects",
      description = ""
  )
  default boolean graphcisObjectsActive()
  {
    return false;
  }

  @ConfigItem(
      keyName = "roofsActive",
      name = "Roofs",
      description = ""
  )
  default boolean roofsActive()
  {
    return false;
  }

}
