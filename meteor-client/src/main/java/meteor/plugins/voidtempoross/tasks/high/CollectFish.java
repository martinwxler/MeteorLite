package meteor.plugins.voidtempoross.tasks.high;

import meteor.plugins.voidtempoross.VoidTemporossPlugin;
import meteor.plugins.voidutils.OSRSUtils;
import meteor.plugins.voidutils.tasks.PriorityTask;
import net.runelite.api.NPC;

import javax.inject.Inject;
import java.util.List;

public class CollectFish extends PriorityTask {

    @Inject
    OSRSUtils osrs;

    VoidTemporossPlugin plugin;

    public CollectFish(VoidTemporossPlugin plugin) {
        super();
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "Collect Fish";
    }

    @Override
    public boolean shouldExecute() {

        if (plugin.canInterrupt || plugin.client.getLocalPlayer().isIdle()
                || plugin.client.getLocalPlayer().getInteracting() == null)
            if (plugin.location.equals("ISLAND"))
                if (!osrs.inventoryFull())
                    if (!plugin.shouldTether)
                        if (!plugin.temporossVulnerable) {
                            DouseFireIsland douseFireIsland = plugin.getTask(DouseFireIsland.class);
                            if (douseFireIsland.getNearestIslandFire() == null)
                                if (getNearestDoubleFishingSpot() != null || getNearestFishingSpot() != null) {
                                    return true;
                                }
                        }
        return false;
    }

    @Override
    public void execute() {
         if (getNearestDoubleFishingSpot() != null) {
             getNearestDoubleFishingSpot().interact(0);
             plugin.lastSpotX = getNearestDoubleFishingSpot().getLocalLocation().getX();
             plugin.lastSpotY = getNearestDoubleFishingSpot().getLocalLocation().getY();
             return;
         }
        if (getNearestFishingSpot() != null) {
            getNearestFishingSpot().interact(0);
        }
        plugin.canInterrupt = false;
    }

    public List<NPC> getFishingSpots() {
        return osrs.npcs(10565, 10568);
    }

    public List<NPC> getDoubleFishingSpots() {
        return osrs.npcs(10569);
    }

    public NPC getNearestFishingSpot() {
        if (getFishingSpots() != null)
            return osrs.nearestNPC(getFishingSpots());
        return null;
    }

    public NPC getNearestDoubleFishingSpot() {
        if (getDoubleFishingSpots() != null)
            return osrs.nearestNPC(getDoubleFishingSpots());
        return null;
    }
}
