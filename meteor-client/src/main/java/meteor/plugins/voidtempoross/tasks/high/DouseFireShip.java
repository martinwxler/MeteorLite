package meteor.plugins.voidtempoross.tasks.high;

import meteor.plugins.voidtempoross.VoidTemporossPlugin;
import meteor.plugins.voidutils.OSRSUtils;
import meteor.plugins.voidutils.tasks.PriorityTask;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.NPC;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class DouseFireShip extends PriorityTask {

    @Inject
    OSRSUtils osrs;

    @Inject
    Client client;

    VoidTemporossPlugin plugin;

    public DouseFireShip(VoidTemporossPlugin plugin) {
        super();
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "Douse (SHIP)";
    }

    @Override
    public boolean shouldExecute() {
        if (plugin.location.equals("SHIP"))
                if (getNearestShipFire() != null)
                    if (getNearestShipFire().getDistanceFromLocalPlayer() < 120)
                        return true;
        return false;
    }

    @Override
    public void execute() {
        getNearestShipFire().interact(0);
    }

    public NPC getNearestShipFire() {
        GameObject islandWater = plugin.getInstanceAnchor();
        if (islandWater == null)
            return null;

        List<NPC> fires = new ArrayList<>();
        if (osrs.npcs(8643) != null)
            for (NPC fire : osrs.npcs(8643)) {
                if (plugin.side.equals("WEST")) {
                    if ((fire.getLocalLocation().getX() < plugin.getInstanceAnchor().getLocalLocation().getX())
                            && (fire.getLocalLocation().getY() < plugin.getInstanceAnchor().getLocalLocation().getY()))
                        fires.add(fire);
                } else {
                    if ((fire.getLocalLocation().getX() > plugin.getInstanceAnchor().getLocalLocation().getX())
                            && (fire.getLocalLocation().getY() > plugin.getInstanceAnchor().getLocalLocation().getY()))
                        fires.add(fire);
                }
            }
        return osrs.nearestNPC(fires);
    }
}
