package meteor.plugins.voidtempoross.tasks.low;

import meteor.plugins.voidtempoross.VoidTemporossPlugin;
import meteor.plugins.voidutils.OSRSUtils;
import meteor.plugins.voidutils.tasks.Task;
import net.runelite.api.GameObject;

import javax.inject.Inject;

public class CollectBucket extends Task {

    @Inject
    OSRSUtils osrs;

    VoidTemporossPlugin plugin;

    public CollectBucket(VoidTemporossPlugin plugin) {
        super();
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "Bucket";
    }

    @Override
    public boolean shouldExecute() {
        if (plugin.location.equals("SHIP"))
            if (!plugin.shouldTether)
                return getActiveBucketsCount() < 5;
        return false;
    }

    @Override
    public void execute() {
        collectBucket();
    }

    private void collectBucket() {
        GameObject bucketsCrate = getBucketsCrate();
        if (bucketsCrate != null) {
            bucketsCrate.interact(0);
        }
    }

    public GameObject getBucketsCrate() {
        return plugin.getSidesObjectEW(40966);
    }

    public int getActiveBucketsCount() {
        if (osrs.items(1929, 1925) != null)
            return osrs.items(1929, 1925).size();
        return 0;
    }
}
