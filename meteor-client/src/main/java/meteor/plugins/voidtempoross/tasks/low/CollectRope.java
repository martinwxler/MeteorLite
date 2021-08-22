package meteor.plugins.voidtempoross.tasks.low;

import meteor.plugins.voidtempoross.VoidTemporossPlugin;
import meteor.plugins.voidutils.OSRSUtils;
import meteor.plugins.voidutils.tasks.Task;
import net.runelite.api.GameObject;

import javax.inject.Inject;

public class CollectRope extends Task {

    @Inject
    OSRSUtils osrs;

    @Inject
    VoidTemporossPlugin plugin;

    public CollectRope(VoidTemporossPlugin plugin) {
        super();
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "Rope";
    }

    @Override
    public boolean shouldExecute() {
        if (getRopeCount() < 1)
            return true;
        return false;
    }

    @Override
    public void execute() {
        collectRope();
    }

    public int getRopeCount() {
        if (osrs.items(954) != null)
            return osrs.items(954).size();
        return 0;
    }

    private void collectRope() {
        GameObject ropesCrate = getRopesCrate();
        if (ropesCrate != null) {
            ropesCrate.interact(0);
        }
    }

    public GameObject getRopesCrate() {
        return plugin.getSidesObjectEW(40965);
    }
}
