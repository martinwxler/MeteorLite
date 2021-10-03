package meteor.plugins.objecthider;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;
import meteor.config.ConfigSection;

@SuppressWarnings({"unused"})
@ConfigGroup("objecthider")
public interface ObjectHiderConfig extends Config
{
  @ConfigSection(
      name = "Hide by ID",
      description = "Hide objects by ID",
      position = 0
  )
  String hideObjectsID = "Hide by ID";

  @ConfigItem(
      keyName = "objectIds",
      name = "Object IDs",
      description = "Configure hidden objects by id. Format: (id), (id)",
      position = 1,
      section = hideObjectsID
  )
  default String objectIds()
  {
    return "";
  }

  @ConfigSection(
      name = "Hide by name",
      description = "Hide objects by name",
      position = 2
  )
  String hideObjectsName = "Hide by name";

  @ConfigItem(
      keyName = "objectNames",
      name = "Objects names",
      description = "Configure hidden objects by name. Format: (Tree), (Mom)",
      position = 3,
      section = hideObjectsName
  )
  default String objectNames()
  {
    return "";
  }

  @ConfigSection(
      name = "Hide all objects",
      description = "Hide all objects",
      position = 4
  )
  String hideAllObjects = "Hide all objects";

  @ConfigItem(
      keyName = "hideAllGameObjects",
      name = "Hide all game objects",
      description = "",
      position = 5,
      section = hideAllObjects
  )
  default boolean hideAllGameObjects()
  {
    return false;
  }

  @ConfigItem(
      keyName = "hideAllDecorativeObjects",
      name = "Hide all decorative objects",
      description = "",
      position = 6,
      section = hideAllObjects
  )
  default boolean hideAllDecorativeObjects()
  {
    return false;
  }

  @ConfigItem(
      keyName = "hideAllWallObjects",
      name = "Hide all wall objects",
      description = "",
      position = 7,
      section = hideAllObjects
  )
  default boolean hideAllWallObjects()
  {
    return false;
  }

  @ConfigItem(
      keyName = "hideAllGroundObjects",
      name = "Hide all ground objects",
      description = "",
      position = 8,
      section = hideAllObjects
  )
  default boolean hideAllGroundObjects()
  {
    return false;
  }
}