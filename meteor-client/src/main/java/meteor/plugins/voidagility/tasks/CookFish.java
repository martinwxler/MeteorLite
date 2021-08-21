package meteor.plugins.voidagility.tasks;

import meteor.plugins.voidagility.VoidTemporossPlugin;
import meteor.plugins.voidutils.OSRSUtils;
import meteor.plugins.voidutils.tasks.Task;
import net.runelite.api.GameObject;

import javax.inject.Inject;

public class CookFish extends Task {

    @Inject
    VoidTemporossPlugin plugin;

    @Inject
    OSRSUtils osrs;

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
        cookFish();
    }

    private void cookFish() {
        GameObject oven = getOven();
        if (oven != null)
            oven.interact(0);
    }

    public int getCaughtFishCount() {
        if (osrs.items(25564) != null)
            return osrs.items(25564).size();
        return 0;
    }

    public GameObject getOven() {
        return plugin.getSidesObjectNS(41236);
    }
}
