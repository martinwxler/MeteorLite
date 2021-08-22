package meteor.plugins.api.coords;

import meteor.plugins.api.commons.Rand;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.mixins.Inject;

public class RectangularArea implements Area {
    private final int x1;
    private final int x2;
    private final int y1;
    private final int y2;
    private final int plane;

    private int minX;
    private int maxX;
    private int minY;
    private int maxY;

    @Inject
    private Rand rand;

    public RectangularArea(int x1, int x2, int y1, int y2, int plane) {
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
        this.plane = plane;

        if (x1 <= x2) {
            minX = x1;
            maxX = x2;
        } else {
            minX = x2;
            maxX = x1;
        }

        if (y1 <= y2) {
            minY = y1;
            maxY = y2;
        } else {
            minY = y2;
            maxY = y1;
        }
    }

    public RectangularArea(int x1, int x2, int y1, int y2) {
        this(x1, x2, y1, y2, 0);
    }

    public WorldPoint getRandomTile() {
        return new WorldPoint(rand.nextInt(minX, maxX), rand.nextInt(minY, maxY), plane);
    }

    public WorldPoint getCenter() {
        return new WorldPoint((minX + maxX) / 2, (minY + maxY) / 2, plane);
    }

    @Override
    public boolean contains(WorldPoint worldPoint) {
        if (worldPoint.getPlane() == -1 || worldPoint.getPlane() != plane) {
            return false;
        }

        int x = worldPoint.getX();
        int y = worldPoint.getY();
        return x >= minX && y >= minY && x <= maxX && y <= maxY;
    }
}
