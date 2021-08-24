package meteor.plugins.api.entities;

import meteor.plugins.api.game.GameThread;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Player;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class NPCs {
    @Inject
    private static Client client;

    public static List<NPC> getAll(Predicate<NPC> filter) {
        List<NPC> out = new ArrayList<>();
        for (NPC npc : client.getNpcs()) {
            if (npc.isTransformRequired() && !npc.isDefinitionCached()) {
                GameThread.invokeLater(npc::getName); // Transform and cache it by calling getName
            }

            if (filter.test(npc)) {
                out.add(npc);
            }
        }
        if (out.size() == 0)
            return null;
        return out;
    }

    public static NPC getNearest(Predicate<NPC> filter) {
        Player local = client.getLocalPlayer();
        if (local == null) {
            return null;
        }

        return getAll(filter).stream()
                .min(Comparator.comparingInt(t -> t.getWorldLocation().distanceTo(local.getWorldLocation())))
                .orElse(null);
    }

    public static NPC getNearest(int id) {
        return getNearest(x -> x.getId() == id);
    }

    public static NPC getNearest(String name) {
        return getNearest(x -> x.getName() != null && x.getName().equals(name));
    }
}
