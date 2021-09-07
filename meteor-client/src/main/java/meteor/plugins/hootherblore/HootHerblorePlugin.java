package meteor.plugins.hootherblore;

import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.entities.TileObjects;
import meteor.plugins.api.items.Bank;
import meteor.plugins.api.items.Inventory;
import net.runelite.api.Item;
import net.runelite.api.TileObject;
import net.runelite.api.events.GameTick;

import javax.inject.Inject;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@PluginDescriptor(name = "Hoot Herblore", enabledByDefault = false)
public class HootHerblorePlugin extends Plugin {
	@Inject
	private HootHerbloreConfig config;

	@Subscribe
	public void onGameTick(GameTick e) {
		List<Integer> ids = Arrays.stream(config.herbIds().split(",")).map(Integer::parseInt).collect(Collectors.toList());

		List<Item> grimy = Inventory.getAll(x -> ids.contains(x.getId()));
		if (grimy.isEmpty()) {
			if (Bank.isOpen()) {
				Item unneeded = Inventory.getFirst(x -> !x.hasAction("Clean"));
				if (unneeded != null) {
					Bank.depositInventory();
					return;
				}

				Bank.withdrawAll(x -> ids.contains(x.getId()), Bank.WithdrawMode.ITEM);
				return;
			}

			TileObject bank = TileObjects.getNearest(x -> x.hasAction("Bank") && x.hasAction("Collect"));
			if (bank != null) {
				bank.interact("Bank");
			}

			return;
		}

		for (Item item : grimy) {
			item.interact("Clean");
		}
	}

	@Override
	public HootHerbloreConfig getConfig(ConfigManager configManager) {
		return configManager.getConfig(HootHerbloreConfig.class);
	}
}
