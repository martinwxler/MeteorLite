package meteor.plugins.api.example.chickenkiller;

import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.commons.Time;
import meteor.plugins.api.entities.Players;
import meteor.plugins.api.entities.TileItems;
import meteor.plugins.api.game.Combat;
import meteor.plugins.api.movement.Movement;
import meteor.plugins.api.movement.Reachable;
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

            try {
                logger.debug("Looping");
                if (Movement.isWalking()) {
                    logger.debug("Pathing");
                    return;
                }

                TileItem feather = TileItems.getNearest("Feather");
                if (feather != null) {
                    if (!Reachable.isInteractable(feather.getTile())) {
                        logger.debug("Reaching feather");
                        Movement.walkTo(feather.getTile().getWorldLocation());
                        return;
                    }

                    logger.debug("Picking up feather");
                    feather.pickup();
                    return;
                }

                if (Players.getLocal().getInteracting() != null) {
                    logger.debug("We're currently fighting {}", Players.getLocal().getInteracting());
                    return;
                }

                NPC chicken = Combat.getAttackableNPC(x -> x.getName() != null
                        && x.getName().equals("Chicken") && !x.isDead()
                );
                if (chicken == null) {
                    logger.debug("Moving to chicken area");
                    Movement.walkTo(new WorldPoint(3233, 3293, 0));
                    return;
                }

                if (!Reachable.isInteractable(chicken)) {
                    logger.debug("Trying to reach chicken");
                    Movement.walkTo(chicken.getWorldLocation());
                    return;
                }

                logger.debug("Attacking chicken");
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
