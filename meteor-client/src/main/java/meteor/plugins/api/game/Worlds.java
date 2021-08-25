package meteor.plugins.api.game;

import meteor.plugins.api.commons.Rand;
import meteor.plugins.api.widgets.Tab;
import meteor.plugins.api.widgets.Tabs;
import net.runelite.api.Client;
import net.runelite.api.World;
import net.runelite.api.WorldType;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Worlds {
    @Inject
    private static Client client;

    public static List<World> getAll(Predicate<World> filter) {
        List<World> out = new ArrayList<>();

        World[] worlds = client.getWorldList();
        if (worlds == null) {
            loadWorlds();
            return out;
        }

        for (World world : worlds) {
            if (filter.test(world)) {
                out.add(world);
            }
        }

        return out;
    }

    public static World getFirst(Predicate<World> filter) {
        return getAll(filter)
                .stream()
                .findFirst()
                .orElse(null);
    }

    public static World getFirst(int id) {
        return getFirst(x -> x.getId() == id);
    }

    public static World getRandom(Predicate<World> filter) {
        List<World> all = getAll(filter);
        return all.get(Rand.nextInt(0, all.size()));
    }

    public static World getCurrentWorld() {
        return getFirst(client.getWorld());
    }

    public static boolean inMembersWorld() {
        return getCurrentWorld() != null && getCurrentWorld().getTypes().contains(WorldType.MEMBERS);
    }

    public static void loadWorlds() {
        // TODO: load worlds in login screen

        if (Game.isLoggedIn()) {
            openHopper();
        }
    }

    public static void openHopper() {
        if (!Tabs.isOpen(Tab.LOG_OUT)) {
            Tabs.open(Tab.LOG_OUT);
        }

        client.openWorldHopper();
    }
}
