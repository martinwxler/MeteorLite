package meteor.plugins.api.game;

import meteor.plugins.api.entities.NPCs;
import meteor.plugins.api.entities.Players;
import net.runelite.api.NPC;
import net.runelite.api.Player;

import java.util.function.Predicate;

public class Combat {
    public static NPC getAttackableNPC(Predicate<NPC> filter) {
        Player local = Players.getLocal();
        NPC attackingMe = NPCs.getNearest(x -> {
           if (Players.getNearest(p -> p.getInteracting() != null && p.getInteracting().equals(x)) != null) {
               return false;
           }

           return x.getInteracting() != null && x.getInteracting().equals(local) && filter.test(x);
        });

        if (attackingMe != null) {
            return attackingMe;
        }

        return NPCs.getNearest(x -> {
            if (Players.getNearest(p -> p.getInteracting() != null && p.getInteracting().equals(x)) != null) {
                return false;
            }

            return x.getInteracting() == null && filter.test(x);
        });
    }
}
