package meteor.plugins.voidagility.tasks;

import meteor.plugins.voidagility.VoidTemporossPlugin;
import meteor.plugins.voidutils.OSRSUtils;
import meteor.plugins.voidutils.tasks.Task;
import net.runelite.api.GameObject;

import javax.inject.Inject;

public class CollectHammer extends Task {

    @Inject
    VoidTemporossPlugin plugin;

    @Inject
    OSRSUtils osrs;

    @Override
    public String name() {
        return "Hammer";
    }

    @Override
    public boolean shouldExecute() {
        if (getHammerCount() < 1)
            return true;
        return false;
    }

    @Override
    public void execute() {
        collectHammer();
    }

    public int getHammerCount() {
        if (osrs.items(2347) != null)
            return osrs.items(2347).size();
        return 0;
    }

    public GameObject getHammerCrate() {
        return plugin.getSidesObjectEW(40964);
    }

    private void collectHammer() {
        GameObject harpoonsCrate = getHammerCrate();
        if (harpoonsCrate != null) {
            harpoonsCrate.interact(0);
        }
    }
}
