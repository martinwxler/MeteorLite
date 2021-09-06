package meteor.plugins.api.entities;

import meteor.plugins.api.game.Game;
import meteor.plugins.api.game.GameThread;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Player;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NPCs {

    public static List<NPC> getAll(Predicate<NPC> filter) {
        List<NPC> out = new ArrayList<>();
        List<NPC> npcs = Game.getClient().getNpcs();
        List<NPC> uncached = npcs.stream()
                .filter(x -> x.isTransformRequired() && !x.isDefinitionCached())
                .collect(Collectors.toList());
        if (!uncached.isEmpty()) {
            GameThread.invokeLater(() -> {
                for (NPC npc : uncached) {
                    npc.getName(); // Transform and cache it by calling getName
                }

                return true;
            });
        }

        for (NPC npc : npcs) {
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
        return getAll(filter).stream()
                .min(Comparator.comparingInt(t -> t.getWorldLocation().distanceTo(Players.getLocal())))
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
