package meteor.plugins.itemdropper;

import com.google.inject.Provides;
import meteor.plugins.illutils.IllUtils;
import net.runelite.api.Client;
import net.runelite.api.events.GameTick;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.illutils.osrs.InventoryUtils;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@PluginDescriptor(
        name = "Item Dropper",
        description = "Automatically drops specified items when inventory is full.",
        tags = {"drop", "dropper", "auto", "skilling"}
)
public class ItemDropperPlugin extends Plugin {
    @Inject
    private ItemDropperConfig config;

    @Inject
    private Client client;

    @Inject
    private InventoryUtils inventory;

    @Inject
    IllUtils iUtils;

    public List<String> itemList;

    public List<Integer> itemIDList;

    public ItemDropperPlugin() {
    }

    @Provides
   public ItemDropperConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(ItemDropperConfig.class);
    }

    @Override
    public void startup() {

    }

    @Override
    public void shutdown() {

    }

    @Subscribe
    private void onGameTick(GameTick event) {
        if (config.itemIDs() == null)
        {
            return;
        }
        else {
            itemList = Arrays.asList(config.itemIDs().split(","));
            itemIDList = itemList.stream().map(Integer::parseInt).collect(Collectors.toList());
        }
        if (inventory.isFull() && inventory.containsItem(itemIDList))
        {
            inventory.dropItems(itemIDList, true, config.sleepMin(), config.sleepMax());
        }
    }
}
