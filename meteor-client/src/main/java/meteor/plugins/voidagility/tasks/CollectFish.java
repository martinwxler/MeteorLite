package meteor.plugins.voidagility.tasks;

import meteor.plugins.voidagility.VoidTemporossPlugin;
import meteor.plugins.voidutils.OSRSUtils;
import meteor.plugins.voidutils.tasks.Task;
import net.runelite.api.GameObject;
import net.runelite.api.NPC;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class CollectFish extends Task {

    @Inject
    VoidTemporossPlugin plugin;

    @Inject
    OSRSUtils osrs;

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
