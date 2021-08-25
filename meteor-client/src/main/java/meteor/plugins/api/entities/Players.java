package meteor.plugins.api.entities;

import meteor.plugins.api.game.Game;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Player;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Players {

    public static List<Player> getAll(Predicate<Player> filter) {
        return Game.getClient().getPlayers()
                .stream()
                .filter(filter)
                .collect(Collectors.toList());
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
        Player local = Game.getClient().getLocalPlayer();
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
        return Game.getClient().getHintArrowPlayer();
    }

    public static Player getLocal() {
        Player local = Game.getClient().getLocalPlayer();
        if (local == null) {
            throw new IllegalStateException("Local player was null");
        }

        return local;
    }
}
