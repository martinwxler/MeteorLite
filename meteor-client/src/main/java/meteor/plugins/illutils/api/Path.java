package meteor.plugins.illutils.api;

import net.runelite.api.Player;
import net.runelite.api.coords.WorldPoint;

public class Path {
    private WorldPoint start;
    private WorldPoint end;
    private Player player;

    public Path(WorldPoint start, WorldPoint end, Player player) {
        this.start = start;
        this.end = end;
        this.player = player;
    }
}
