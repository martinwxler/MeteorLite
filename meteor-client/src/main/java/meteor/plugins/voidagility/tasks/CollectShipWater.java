package meteor.plugins.voidagility.tasks;

import meteor.plugins.voidagility.VoidTemporossPlugin;
import meteor.plugins.voidutils.OSRSUtils;
import meteor.plugins.voidutils.tasks.Task;
import net.runelite.api.GameObject;

import javax.inject.Inject;

public class CollectShipWater extends Task {

    @Inject
    VoidTemporossPlugin plugin;

    @Inject
    OSRSUtils osrs;

    @Override
    public String name() {
        return "Water (Ship)";
    }

    @Override
    public boolean shouldExecute() {
        if (getBucketOfWaterCount() < 5)
            return true;
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
}
