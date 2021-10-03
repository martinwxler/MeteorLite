package meteor.plugins.api.movement.pathfinder;

import meteor.plugins.api.movement.Reachable;
import net.runelite.api.coords.Direction;
import net.runelite.api.coords.WorldPoint;

public class LocalCollisionMap implements CollisionMap {

	@Override
	public boolean n(int x, int y, int z) {
		WorldPoint current = new WorldPoint(x, y, z);
		if (Reachable.isObstacle(current)) {
			return false;
		}

		return Reachable.canWalk(Direction.NORTH, Reachable.getCollisionFlag(current), Reachable.getCollisionFlag(current.dy(1)));
	}

	@Override
	public boolean e(int x, int y, int z) {
		WorldPoint current = new WorldPoint(x, y, z);
		if (Reachable.isObstacle(current)) {
			return false;
		}

		return Reachable.canWalk(Direction.EAST, Reachable.getCollisionFlag(current), Reachable.getCollisionFlag(current.dx(1)));
	}
}
