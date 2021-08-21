package meteor.plugins.voidutils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import net.runelite.api.GameObject;
import net.runelite.api.NPC;
import net.runelite.api.TileItem;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.ItemDespawned;
import net.runelite.api.events.ItemSpawned;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcSpawned;

@PluginDescriptor(
    name = "Void",
    description = "Void Framework"
)
public class VoidUtils extends Plugin {

    public VoidUtilsConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(VoidUtilsConfig.class);
    }
}
