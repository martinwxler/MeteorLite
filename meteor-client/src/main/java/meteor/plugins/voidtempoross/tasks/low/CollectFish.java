package meteor.plugins.voidtempoross.tasks.low;

import meteor.plugins.voidtempoross.VoidTemporossPlugin;
import meteor.plugins.voidutils.OSRSUtils;
import meteor.plugins.voidutils.tasks.Task;
import net.runelite.api.NPC;

import javax.inject.Inject;
import java.util.List;

public class CollectFish extends Task {

    @Inject
    OSRSUtils osrs;

    @Inject
    VoidTemporossPlugin plugin;

    public CollectFish(VoidTemporossPlugin plugin) {
        super();
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "Collect Fish";
    }

    @Override
    public boolean shouldExecute() {
        return false;
    }

    @Override
    public void execute() {

    }

    public List<NPC> getFishingSpots() {
        return null;
    }

    public NPC getNearestFishingSpot() {
        return osrs.nearestNPC(getFishingSpots());
    }
}
