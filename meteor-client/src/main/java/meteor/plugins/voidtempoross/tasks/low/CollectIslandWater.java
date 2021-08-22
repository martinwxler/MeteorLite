package meteor.plugins.voidtempoross.tasks.low;

import meteor.plugins.voidtempoross.VoidTemporossPlugin;
import meteor.plugins.voidutils.OSRSUtils;
import meteor.plugins.voidutils.tasks.Task;
import net.runelite.api.GameObject;

import javax.inject.Inject;

public class CollectIslandWater extends Task {

    @Inject
    OSRSUtils osrs;

    @Override
    public String name() {
        return "Water (Island)";
    }

    @Inject
    VoidTemporossPlugin plugin;

    public CollectIslandWater(VoidTemporossPlugin plugin) {
        super();
        this.plugin = plugin;
    }

    @Override
    public boolean shouldExecute() {
        if (getBucketOfWaterCount() < 5)
            return true;
        return false;
    }

    @Override
    public void execute() {
        collectIslandWater();
    }

    private void collectIslandWater() {
        GameObject shipWater = getIslandWater();
        if (shipWater != null) {
            shipWater.interact(0);
        }
    }

    public GameObject getIslandWater() {
        return plugin.getSidesObjectNS(41004);
    }

    public int getBucketOfWaterCount() {
        if (osrs.items(1929) != null)
            return osrs.items(1929).size();
        return 0;
    }
}
