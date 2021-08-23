package meteor.plugins.voidtempoross.tasks.low;

import meteor.plugins.voidtempoross.VoidTemporossPlugin;
import meteor.plugins.voidutils.OSRSUtils;
import meteor.plugins.voidutils.tasks.Task;
import net.runelite.api.GameObject;

import javax.inject.Inject;

public class CollectHarpoon extends Task {

    @Inject
    OSRSUtils osrs;

    VoidTemporossPlugin plugin;

    public CollectHarpoon(VoidTemporossPlugin plugin) {
        super();
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "Harpoon";
    }

    @Override
    public boolean shouldExecute() {
        if (getHarpoonCount() < 1)
            if (plugin.location.equals("SHIP"))
                if (!plugin.shouldTether)
                    return true;
        return false;
    }

    @Override
    public void execute() {
        collectHarpoon();
    }

    public GameObject getHarpoonsCrate() {
        return plugin.getSidesObjectEW(40967);
    }

    private void collectHarpoon() {
        GameObject harpoonsCrate = getHarpoonsCrate();
        if (harpoonsCrate != null) {
            harpoonsCrate.interact(0);
        }
    }

    public int getHarpoonCount() {
        if (osrs.items(311) != null)
            return osrs.items(311).size();
        return 0;
    }
}
