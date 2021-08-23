package meteor.plugins.voidtempoross.tasks.high;

import meteor.plugins.voidtempoross.VoidTemporossPlugin;
import meteor.plugins.voidtempoross.tasks.low.CookFish;
import meteor.plugins.voidutils.OSRSUtils;
import meteor.plugins.voidutils.tasks.PriorityTask;
import meteor.plugins.voidutils.tasks.Task;
import net.runelite.api.GameObject;
import net.runelite.api.NPC;

import javax.inject.Inject;

public class LoadFish extends PriorityTask {

    @Inject
    OSRSUtils osrs;

    VoidTemporossPlugin plugin;

    public LoadFish(VoidTemporossPlugin plugin) {
        super();
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "Load Fish";
    }

    @Override
    public boolean shouldExecute() {
            if (plugin.location.equals("SHIP"))
                if (!plugin.shouldTether)
                    if (plugin.tasks.getInstance(CookFish.class) != null) {
                        CookFish cookFish = plugin.getTask(CookFish.class);
                            if (cookFish.getCaughtFishCount() == 0)
                                if (getCookedFishCount() != 0)
                                    return true;
                    }
        return false;
    }

    @Override
    public void execute() {
        if (getFishCrate() != null)
            getFishCrate().interact(0);
    }

    public NPC getFishCrate() {
        if (plugin.side == null)
            return null;
        if (plugin.side.equals("EAST"))
            return osrs.nearestNPC(10579);
        else
            return osrs.nearestNPC(10576);
    }

    public int getCookedFishCount() {
        if (osrs.items(25565) != null)
            return osrs.items(25565).size();
        return 0;
    }
}
