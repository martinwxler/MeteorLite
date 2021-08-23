package meteor.plugins.voidtempoross.tasks.high;

import meteor.eventbus.Subscribe;
import meteor.plugins.voidtempoross.VoidTemporossPlugin;
import meteor.plugins.voidtempoross.tasks.low.WalkToShore;
import meteor.plugins.voidutils.OSRSUtils;
import meteor.plugins.voidutils.tasks.PriorityTask;
import net.runelite.api.NPC;
import net.runelite.api.events.ChatMessage;

import javax.inject.Inject;
import java.util.List;

public class AttackTempoross extends PriorityTask {

    @Inject
    OSRSUtils osrs;

    @Inject
    VoidTemporossPlugin plugin;

    public AttackTempoross(VoidTemporossPlugin plugin) {
        super();
        this.plugin = plugin;
    }

    @Override
    public String name() {
        return "Attack Tempoross";
    }

    @Override
    public boolean shouldExecute() {
        if (plugin.temporossVulnerable)
            return true;
        return false;
    }

    @Override
    public void execute() {
        if (getSpiritPool() != null)
            getSpiritPool().interact(0);
        else
            plugin.getTask(WalkToShore.class).execute();
    }

    public NPC getSpiritPool() {
        List<NPC> npcs = osrs.npcs(10571);
        if (npcs != null) {
            return npcs.get(0);
        }
        return null;
    }

    public NPC getInactiveSpiritPool() {
        List<NPC> npcs = osrs.npcs(10570);
        if (npcs != null) {
            return npcs.get(0);
        }
        return null;
    }

    public NPC getTempoross() {
        List<NPC> npcs = osrs.npcs(10572);
        if (npcs != null) {
            return npcs.get(0);
        }
        return null;
    }

    @Subscribe
    public void onChatMessage(ChatMessage event) {
        if (event.getMessage().contains("empoross is vulnerable")) {
            plugin.temporossVulnerable = true;
        }
    }
}
