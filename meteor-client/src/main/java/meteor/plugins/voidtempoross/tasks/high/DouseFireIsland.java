package meteor.plugins.voidtempoross.tasks.high;

import meteor.plugins.voidtempoross.VoidTemporossPlugin;
import meteor.plugins.voidutils.OSRSUtils;
import meteor.plugins.voidutils.tasks.PriorityTask;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.NPC;
import org.sponge.util.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class DouseFireIsland extends PriorityTask {

    @Inject
    OSRSUtils osrs;

    @Inject
    Client client;

    VoidTemporossPlugin plugin;

    public DouseFireIsland(VoidTemporossPlugin plugin) {
        super();
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "Douse (ISLAND)";
    }

    @Override
    public boolean shouldExecute() {
        if (plugin.location.equals("ISLAND"))
                if (getNearestIslandFire() != null)
                    if (getNearestIslandFire().getDistanceFromLocalPlayer() < 200)
                        return true;
                    else
                        new Logger("").debug(getNearestIslandFire().getDistanceFromLocalPlayer());
        return false;
    }

    @Override
    public void execute() {
        getNearestIslandFire().interact(0);
    }

    public NPC getNearestIslandFire() {
        GameObject islandWater = plugin.getInstanceAnchor();
        if (islandWater == null)
            return null;

        List<NPC> fires = new ArrayList<>();
        if (osrs.objects(8643) != null)
            for (NPC fire : osrs.npcs(8643)) {
                if (plugin.side.equals("WEST")) {
                    if (fire.getLocalLocation().getY() > plugin.getInstanceAnchor().getLocalLocation().getY())
                        fires.add(fire);
                } else {
                    if (fire.getLocalLocation().getY() < plugin.getInstanceAnchor().getLocalLocation().getY())
                        fires.add(fire);
                }
            }
        return osrs.nearestNPC(fires);
    }
}
