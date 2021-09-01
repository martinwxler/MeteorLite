package meteor.plugins.hootfighter;

import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.game.ItemManager;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.entities.Players;
import meteor.plugins.api.entities.TileItems;
import meteor.plugins.api.game.Combat;
import meteor.plugins.api.game.Game;
import meteor.plugins.api.items.Inventory;
import meteor.plugins.api.movement.Movement;
import meteor.plugins.api.movement.Reachable;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@PluginDescriptor(
				name = "Hoot Fighter",
				description = "Weed",
				enabledByDefault = false
)
@Singleton
public class HootFighterPlugin extends Plugin {

	@Inject
  private	HootFighterConfig config;

	@Inject
	private ItemManager itemManager;

	private WorldPoint startPoint;

	@Override
	public void startup() {
		if (Game.isLoggedIn()) {
			startPoint = Players.getLocal().getWorldLocation();
		}
	}

	@SuppressWarnings("unused")
	@Subscribe
	private void onGameTick(GameTick e) {
		try {
			if (Movement.isWalking()) {
				return;
			}

			if (config.eat() && Combat.getHealthPercent() <= config.healthPercent()) {
				List<String> foods = List.of(config.foods().split(","));
				Item food = Inventory.getFirst(x -> !foods.isEmpty() && x.getName() != null && foods.contains(x.getName())
								|| foods.isEmpty() && x.hasAction("Eat"));
				if (food != null) {
					food.interact("Eat");
					return;
				}
			}

			if (config.buryBones()) {
				Item bones = Inventory.getFirst("Bones");
				if (bones != null) {
					bones.interact("Bury");
					return;
				}
			}

			Player local = Players.getLocal();
			List<String> itemsToLoot = List.of(config.loot().split(","));
			if (!itemsToLoot.isEmpty()) {
				TileItem loot = TileItems.getNearest(x -> x.getTile().getWorldLocation().distanceTo(local.getWorldLocation()) < 15
								&& (x.getName() != null && itemsToLoot.contains(x.getName())
								|| (config.lootValue() > -1 && itemManager.getItemPrice(x.getId()) * x.getQuantity() > config.lootValue())));
				if (loot != null && loot.getTile().getWorldLocation().distanceTo(local.getWorldLocation()) < config.attackRange()) {
					if (!Reachable.isInteractable(loot.getTile())) {
						Movement.walkTo(loot.getTile().getWorldLocation());
						return;
					}

					loot.pickup();
					return;
				}
			}

			if (local.getInteracting() != null) {
				return;
			}

			NPC mob = Combat.getAttackableNPC(x -> x.getName() != null
							&& x.getName().equals(config.monster()) && !x.isDead()
							&& x.getWorldLocation().distanceTo(local.getWorldLocation()) < config.attackRange()
			);
			if (mob == null) {
				if (startPoint == null) {
					logger.info("No attackable monsters in area");
					return;
				}

				Movement.walkTo(startPoint);
				return;
			}

			if (!Reachable.isInteractable(mob)) {
				Movement.walkTo(mob.getWorldLocation());
				return;
			}

			mob.interact("Attack");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Provides
	public HootFighterConfig getConfig(ConfigManager configManager) {
		return configManager.getConfig(HootFighterConfig.class);
	}
}
