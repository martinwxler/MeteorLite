package meteor.plugins.voidagility.tasks;

import meteor.eventbus.Subscribe;
import meteor.plugins.voidagility.VoidTemporossPlugin;
import meteor.plugins.voidutils.OSRSUtils;
import meteor.plugins.voidutils.tasks.Task;
import net.runelite.api.ChatMessageType;
import net.runelite.api.GameObject;
import net.runelite.api.events.ChatMessage;

import javax.inject.Inject;

public class Tether extends Task {

    @Inject
    VoidTemporossPlugin plugin;

    @Inject
    OSRSUtils osrs;

    @Override
    public String name() {
        return "Tether";
    }

    @Override
    public boolean shouldExecute() {
        return plugin.shouldTether;
    }

    @Override
    public void execute() {
        if (getNearestTether() != null)
            getNearestTether().interact(0);
    }

    public GameObject getNearestTether() {
        GameObject islandTether = getTotem();
        GameObject shipTether = getMast();
        if (islandTether != null && shipTether != null) {
            if (islandTether.getDistanceFromLocalPlayer() > shipTether.getDistanceFromLocalPlayer())
                return shipTether;
            else
                return islandTether;
        }
        if (islandTether == null)
            return shipTether;
        return islandTether;
    }

    public GameObject getTotem() {
        if (plugin.side == null)
            return null;
        if (plugin.side.equals("EAST"))
            return osrs.nearestObject(41355);
        else
            return osrs.nearestObject(41354);
    }

    public GameObject getMast() {
        if (plugin.side == null)
            return null;
        if (plugin.side.equals("EAST"))
            return osrs.nearestObject(41353);
        else
            return osrs.nearestObject(41352);
    }

    @Subscribe
    public void onChatMessage(ChatMessage event) {
        if (event.getType() != ChatMessageType.GAMEMESSAGE)
            return;

        if (event.getMessage().contains("colossal wave closes in")) {
            plugin.shouldTether = true;
        } else if (event.getMessage().contains("slams into you")) {
            plugin.shouldTether = false;
        } else if (event.getMessage().contains("the rope keeps you")) {
            plugin.shouldTether = false;
        }
    }
}
