package meteor.plugins.api.entities;

import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Player;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class Players {
    @Inject
    private static Client client;

    public static List<Player> getAll(Predicate<Player> filter) {
        List<Player> out = new ArrayList<>();
        for (Player Player : client.getPlayers()) {
            if (filter.test(Player)) {
                out.add(Player);
            }
        }

        return out;
    }

    public static List<Player> getAll(String... names) {
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

    public static Player getNearest(Predicate<Player> filter) {
        Player local = client.getLocalPlayer();
        if (local == null) {
            return null;
        }

        return getAll(filter).stream()
                .min(Comparator.comparingInt(t -> t.getWorldLocation().distanceTo(local.getWorldLocation())))
                .orElse(null);
    }

    public static Player getNearest(String... names) {
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

    public static Player getHintArrowed() {
        return client.getHintArrowPlayer();
    }

    public static Player getLocal() {
        Player local = client.getLocalPlayer();
        if (local == null) {
            throw new IllegalStateException("Local player was null");
        }

        return local;
    }
}
