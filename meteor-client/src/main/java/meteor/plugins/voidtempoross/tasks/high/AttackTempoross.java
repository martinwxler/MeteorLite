package meteor.plugins.voidtempoross.tasks.high;

import meteor.eventbus.Subscribe;
import meteor.plugins.voidtempoross.VoidTemporossPlugin;
import meteor.plugins.voidutils.OSRSUtils;
import meteor.plugins.voidutils.tasks.PriorityTask;
import meteor.plugins.voidutils.tasks.Task;
import net.runelite.api.ChatMessageType;
import net.runelite.api.GameObject;
import net.runelite.api.NPC;
import net.runelite.api.events.ChatMessage;

import javax.inject.Inject;

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
        return getSpiritPool() != null;
    }

    @Override
    public void execute() {
        getSpiritPool().interact(0);
    }

    public NPC getSpiritPool() {
        return osrs.nearestNPC(10571);
    }
}
