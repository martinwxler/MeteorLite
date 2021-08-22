package meteor.plugins.voidtempoross.tasks.low;

import meteor.plugins.voidtempoross.VoidTemporossPlugin;
import meteor.plugins.voidutils.OSRSUtils;
import meteor.plugins.voidutils.tasks.Task;
import net.runelite.api.GameObject;

import javax.inject.Inject;

public class RepairMast extends Task {

    @Inject
    OSRSUtils osrs;

    @Inject
    VoidTemporossPlugin plugin;

    public RepairMast(VoidTemporossPlugin plugin) {
        super();
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "Repair Mast";
    }

    @Override
    public boolean shouldExecute() {
        if (plugin.location.equals("SHIP"))
            if (!plugin.shouldTether)
                if (getBrokenMast() != null)
                    return true;
        return false;
    }

    @Override
    public void execute() {
        fixBrokenTotem();
    }

    public GameObject getBrokenMast() {
        return plugin.getSidesObjectNS(10571);
    }

    public void fixBrokenTotem() {
        GameObject brokenTotem = getBrokenMast();
        if (brokenTotem != null)
            brokenTotem.interact(0);
    }
}
