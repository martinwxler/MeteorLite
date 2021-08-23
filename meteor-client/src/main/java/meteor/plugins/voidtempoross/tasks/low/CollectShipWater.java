package meteor.plugins.voidtempoross.tasks.low;

import meteor.plugins.voidtempoross.VoidTemporossPlugin;
import meteor.plugins.voidutils.OSRSUtils;
import meteor.plugins.voidutils.tasks.Task;
import net.runelite.api.GameObject;

import javax.inject.Inject;

public class CollectShipWater extends Task {

    @Inject
    OSRSUtils osrs;

    VoidTemporossPlugin plugin;

    public CollectShipWater(VoidTemporossPlugin plugin) {
        super();
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "Water (Ship)";
    }

    @Override
    public boolean shouldExecute() {
        if (plugin.location.equals("SHIP"))
            if (getBucketOfWaterCount() < 5)
                if (getEmptyBuckets() > 0)
                    if (!plugin.shouldTether)
                        return getActiveBucketsCount() == 5;
        return false;
    }

    @Override
    public void execute() {
        collectShipWater();
    }

    private void collectShipWater() {
        GameObject shipWater = getShipWater();
        if (shipWater != null) {
            shipWater.interact(0);
        }
    }

    public GameObject getShipWater() {
        return plugin.getSidesObjectEW(41000);
    }

    public int getBucketOfWaterCount() {
        if (osrs.items(1929) != null)
            return osrs.items(1929).size();
        return 0;
    }

    public int getEmptyBuckets() {
        if (osrs.items(1925) != null)
            return osrs.items(1925).size();
        return 0;
    }

    public int getActiveBucketsCount() {
        if (osrs.items(1929, 1925) != null)
            return osrs.items(1929, 1925).size();
        return 0;
    }
}
