package meteor.plugins.voidtempoross.tasks.low;

import meteor.plugins.voidtempoross.VoidTemporossPlugin;
import meteor.plugins.voidutils.OSRSUtils;
import meteor.plugins.voidutils.tasks.Task;
import net.runelite.api.GameObject;

import javax.inject.Inject;

public class WalkToShore extends Task {

    @Inject
    OSRSUtils osrs;

    @Inject
    VoidTemporossPlugin plugin;

    public WalkToShore(VoidTemporossPlugin plugin) {
        super();
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "Cook Fish";
    }

    @Override
    public boolean shouldExecute() {
        if (plugin.location.equals("SHIP"))
            if (!osrs.inventoryFull())
                if (getCookedFishCount() == 0)
                    return true;
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
}
