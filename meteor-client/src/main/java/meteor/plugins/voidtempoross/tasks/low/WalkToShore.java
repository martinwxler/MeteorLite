package meteor.plugins.voidtempoross.tasks.low;

import meteor.plugins.voidtempoross.VoidTemporossPlugin;
import meteor.plugins.voidtempoross.tasks.high.DouseFireShip;
import meteor.plugins.voidutils.OSRSUtils;
import meteor.plugins.voidutils.tasks.Task;
import net.runelite.api.GameObject;

import javax.inject.Inject;

public class WalkToShore extends Task {

    @Inject
    OSRSUtils osrs;

    VoidTemporossPlugin plugin;

    public WalkToShore(VoidTemporossPlugin plugin) {
        super();
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "Walk (SHORE)";
    }

    @Override
    public boolean shouldExecute() {
        if (plugin.location.equals("SHIP"))
            if (!osrs.inventoryFull()) {
                CollectHarpoon collectHarpoon = plugin.getTask(CollectHarpoon.class);
                CollectHammer collectHammer = plugin.getTask(CollectHammer.class);
                CollectRope collectRope = plugin.getTask(CollectRope.class);
                CollectBucket collectBucket = plugin.getTask(CollectBucket.class);
                DouseFireShip douseFireShip = plugin.getTask(DouseFireShip.class);

                if (collectBucket.getActiveBucketsCount() == 5)
                    if (collectHammer.getHammerCount() == 1)
                        if (collectRope.getRopeCount() == 1)
                            if (collectHarpoon.getHarpoonCount() == 1)
                                if (getCookedFishCount() == 0)
                                    if (!plugin.shouldTether)
                                        if (douseFireShip.getNearestShipFire() == null) {
                                            plugin.canInterrupt = true;
                                            return true;
                                        }
            }
        return false;
    }

    @Override
    public void execute() {
        walkToShore();
    }

    private void walkToShore() {
        GameObject islandWater = plugin.getInstanceAnchor();
        if (islandWater != null) {
            // We do this because it is much more reliable than actually clicking tiles on screen
            islandWater.interact(0);
            plugin.canInterrupt = true;
        }
    }

    public int getCookedFishCount() {
        if (osrs.items(25565) != null)
            return osrs.items(25565).size();
        return 0;
    }

    public int getCaughtFishCount() {
        if (osrs.items(25564) != null)
            return osrs.items(25564).size();
        return 0;
    }
}
