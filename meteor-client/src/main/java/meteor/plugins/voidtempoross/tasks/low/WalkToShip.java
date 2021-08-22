package meteor.plugins.voidtempoross.tasks.low;

import meteor.plugins.voidtempoross.VoidTemporossPlugin;
import meteor.plugins.voidutils.OSRSUtils;
import meteor.plugins.voidutils.tasks.Task;
import net.runelite.api.GameObject;

import javax.inject.Inject;

public class WalkToShip extends Task {

    @Inject
    OSRSUtils osrs;

    @Inject
    VoidTemporossPlugin plugin;

    public WalkToShip(VoidTemporossPlugin plugin) {
        super();
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "Cook Fish";
    }

    @Override
    public boolean shouldExecute() {
        if (plugin.location.equals("ISLAND"))
            if (osrs.inventoryFull())
                if (getCaughtFishCount() > 0)
                    return true;
        return false;
    }

    @Override
    public void execute() {
        walkToShip();
    }

    private void walkToShip() {
        GameObject shipWater = getShipWater();
        if (shipWater != null) {
            // We do this because it is much more reliable than actually clicking tiles on screen
            shipWater.interact(0);
            plugin.canInterrupt = true;
        }
    }

    public GameObject getShipWater() {
        return plugin.getSidesObjectEW(41000);
    }

    public int getCaughtFishCount() {
        if (osrs.items(25564) != null)
            return osrs.items(25564).size();
        return 0;
    }
}
