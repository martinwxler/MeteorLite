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

        return out;
    }

    public static List<NPC> getAll(int... ids) {
        return getAll(x -> {
            for (int id : ids) {
                if (id == x.getId()) {
                    return true;
                }
            }

            return false;
        });
    }

    public static List<NPC> getAll(String... names) {
        return getAll(x -> {
            if (x.getName() == null) {
                return false;
            }

            for (String name : names) {
                if (name.equals(x.getName())) {
                    return true;
                }
            }

            return false;
        });
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

    public static NPC getNearest(int... ids) {
        return getNearest(x -> {
            for (int id : ids) {
                if (id == x.getId()) {
                    return true;
                }
            }

            return false;
        });
    }

    public static NPC getNearest(String... names) {
        return getNearest(x -> {
            if (x.getName() == null) {
                return false;
            }

            for (String name : names) {
                if (name.equals(x.getName())) {
                    return true;
                }
            }

            return false;
        });
    }
}
