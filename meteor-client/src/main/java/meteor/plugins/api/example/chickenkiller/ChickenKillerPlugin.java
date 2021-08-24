package meteor.plugins.api.example.chickenkiller;

import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.commons.Time;
import meteor.plugins.api.entities.NPCs;
import meteor.plugins.api.entities.Players;
import meteor.plugins.api.entities.TileItems;
import meteor.plugins.api.entities.TileObjects;
import meteor.plugins.api.game.Combat;
import meteor.plugins.api.items.Equipment;
import meteor.plugins.api.items.Inventory;
import meteor.plugins.api.movement.Movement;
import meteor.plugins.api.movement.Reachable;
import net.runelite.api.Item;
import net.runelite.api.NPC;
import net.runelite.api.TileItem;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@PluginDescriptor(
        name = "Chicken Killer",
        description = "Weed"
)
@Singleton
public class ChickenKillerPlugin extends Plugin {
    @Inject
    private ScheduledExecutorService executor;

    @Inject
    private ChickenKillerConfig chickenKillerConfig;

    @Override
    public void startup() {
        executor.scheduleAtFixedRate(() -> {
            if (!chickenKillerConfig.enabled()) {
                return;
            }

            logger.debug("Looping");

            try {
                if (Movement.isWalking()) {
                    logger.debug("We are pathing");
                    return;
                }

                Item bones = Inventory.getFirst("Bones");
                if (bones != null) {
                    bones.interact("Bury");
                    return;
                }

                long start = System.currentTimeMillis();
                TileItem loot = TileItems.getNearest(x -> x.getName() != null &&
                        (x.getName().equals("Bones") || x.getName().equals("Feather")));
                logger.debug("TileItem took {}", System.currentTimeMillis() - start);
                if (loot != null) {
                    if (!Reachable.isInteractable(loot.getTile())) {
                        start = System.currentTimeMillis();
                        Movement.walkTo(loot.getTile().getWorldLocation());
                        logger.debug("TileItem walkTo took {}", System.currentTimeMillis() - start);
                        return;
                    }

                    loot.pickup();
                    return;
                }

                if (Players.getLocal().getInteracting() != null) {
                    return;
                }

                start = System.currentTimeMillis();
                NPC chicken = Combat.getAttackableNPC(x -> x.getName() != null
                        && x.getName().equals("Chicken") && !x.isDead()
                );
                logger.debug("Chicken took {}", System.currentTimeMillis() - start);
                if (chicken == null) {
                    start = System.currentTimeMillis();
                    Movement.walkTo(new WorldPoint(3233, 3293, 0));
                    logger.debug("Area walkTo took {}", System.currentTimeMillis() - start);
                    return;
                }

                if (!Reachable.isInteractable(chicken)) {
                    start = System.currentTimeMillis();
                    Movement.walkTo(chicken.getWorldLocation());
                    logger.debug("Chicken walkTo took {}", System.currentTimeMillis() - start);
                    return;
                }

                chicken.interact("Attack");
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }, 0, 1000, TimeUnit.MILLISECONDS);
    }

    @Provides
    public ChickenKillerConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(ChickenKillerConfig.class);
    }
}
