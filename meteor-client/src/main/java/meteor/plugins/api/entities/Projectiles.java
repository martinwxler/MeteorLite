package meteor.plugins.api.entities;

import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.NPC;
import net.runelite.api.Projectile;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

public class Projectiles {
    @Inject
    private static Client client;

    public static List<Projectile> getAll(Predicate<Projectile> filter) {
        List<Projectile> out = new ArrayList<>();
        for (Projectile projectile : client.getProjectiles()) {
            if (projectile != null && filter.test(projectile)) {
                out.add(projectile);
            }
        }

        return out;
    }

    public static List<Projectile> getAll(int... ids) {
        return getAll(x -> {
            for (int id : ids) {
                if (id == x.getId()) {
                    return true;
                }
            }

            return false;
        });
    }

    public static Projectile getNearest(Predicate<Projectile> filter) {
        return getAll(filter).stream()
                .min(Comparator.comparingInt(p ->
                        WorldPoint.fromLocal(client, (int) p.getX(), (int) p.getY(), client.getPlane())
                                .distanceTo(Players.getLocal().getWorldLocation()))
                )
                .orElse(null);
    }

    public static Projectile getNearest(int... ids) {
        return getNearest(x -> {
            for (int id : ids) {
                if (id == x.getId()) {
                    return true;
                }
            }

            return false;
        });
    }

    public static Projectile getNearest(Actor target) {
        return getNearest(x -> x.getInteracting() != null && x.getInteracting().equals(target));
    }

    public static Projectile getNearest(WorldPoint startPoint) {
        LocalPoint localPoint = LocalPoint.fromWorld(client, startPoint);
        if (localPoint == null) {
            return null;
        }

        return getNearest(x -> x.getX1() == localPoint.getX() && x.getY1() == localPoint.getY());
    }
}
