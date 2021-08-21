package meteor.plugins.voidagility.tasks;

import meteor.plugins.voidagility.VoidTemporossPlugin;
import meteor.plugins.voidutils.OSRSUtils;
import meteor.plugins.voidutils.tasks.PriorityTask;
import meteor.plugins.voidutils.tasks.Task;
import net.runelite.api.Client;
import net.runelite.api.GameObject;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class DouseFireShip extends PriorityTask {

    @Inject
    VoidTemporossPlugin plugin;

    @Inject
    OSRSUtils osrs;

    @Inject
    Client client;

    @Override
    public String name() {
        return "Cook Fish";
    }

    @Override
    public boolean shouldExecute() {
        if (plugin.location.equals("SHIP"))
            if (client.getLocalPlayer().isIdle())
                if (getNearestShipFire() != null)
                    return true;
        return false;
    }

    @Override
    public void execute() {
        getNearestShipFire().interact(0);
    }

    public GameObject getNearestShipFire() {
        GameObject islandWater = plugin.getInstanceAnchor();
        if (islandWater == null)
            return null;

        List<GameObject> fires = new ArrayList<>();
        if (osrs.objects(37582) != null)
            for (GameObject fire : osrs.objects(37582)) {
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
        return osrs.nearestObject(fires);
    }
}
