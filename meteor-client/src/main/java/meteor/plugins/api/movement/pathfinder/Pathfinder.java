package meteor.plugins.api.movement.pathfinder;

import lombok.RequiredArgsConstructor;
import meteor.plugins.api.movement.Reachable;
import net.runelite.api.Client;
import net.runelite.api.coords.WorldPoint;

import java.util.*;

@RequiredArgsConstructor
public class Pathfinder {
    private final CollisionMap collisionMap;
    private final Map<WorldPoint, List<Transport>> transportCoords;
    private final List<WorldPoint> startCoords;
    private final WorldPoint destination;
    private final Deque<WorldPoint> boundary = new ArrayDeque<>();
    private final CoordMap predecessors = new CoordMap();
    private final Client client;

    private WorldPoint nearest = null;
    
    public List<WorldPoint> find() {
        boundary.addAll(startCoords);
        int bestDistance = Integer.MAX_VALUE;
        
        for (WorldPoint start : startCoords) {
            predecessors.put(start, null);
        }

        while (!boundary.isEmpty()) {
            WorldPoint node = boundary.removeFirst();

            if (node.equals(destination)) {
                LinkedList<WorldPoint> result = new LinkedList<>();
                while (node != null) {
                    result.add(0, node);
                    node = predecessors.get(node);
                }

                return result;
            }

            int distance = node.distanceTo(destination);
            if (nearest == null || distance < bestDistance) {
                nearest = node;
                bestDistance = distance;
            }

            if (destination.isInScene(client) && ((Reachable.isObstacle(destination) && boundary.size() > 200)) || boundary.size() > 1000) {
                break;
            }

            addNeighbours(node);
        }

        if (nearest != null) {
            LinkedList<WorldPoint> result = new LinkedList<>();
            WorldPoint node = nearest;
            while (node != null) {
                result.add(0, node);
                node = predecessors.get(node);
            }

            return result;
        }

        return Collections.emptyList();
    }

    private void addNeighbours(WorldPoint position) {
        for (Transport transport : transportCoords.getOrDefault(position, new ArrayList<>())) {
            if (predecessors.containsKey(transport.getDestination())) {
                continue;
            }

            predecessors.put(transport.getDestination(), position);
            boundary.addLast(transport.getDestination());
        }

        if (collisionMap.w(position.getX(), position.getY(), position.getPlane())) {
            WorldPoint neighbor = position.dx(-1);
            if (!predecessors.containsKey(neighbor)) {
                predecessors.putE(neighbor);
                boundary.addLast(neighbor);
            }
        }

        if (collisionMap.e(position.getX(), position.getY(), position.getPlane())) {
            WorldPoint neighbor = position.dx(1);
            if (!predecessors.containsKey(neighbor)) {
                predecessors.putW(neighbor);
                boundary.addLast(neighbor);
            }
        }

        if (collisionMap.s(position.getX(), position.getY(), position.getPlane())) {
            WorldPoint neighbor = position.dy(-1);
            if (!predecessors.containsKey(neighbor)) {
                predecessors.putN(neighbor);
                boundary.addLast(neighbor);
            }
        }

        if (collisionMap.n(position.getX(), position.getY(), position.getPlane())) {
            WorldPoint neighbor = position.dy(1);
            if (!predecessors.containsKey(neighbor)) {
                predecessors.putS(neighbor);
                boundary.addLast(neighbor);
            }
        }

        if (collisionMap.sw(position.getX(), position.getY(), position.getPlane())) {
            WorldPoint neighbor = position.dx(-1).dy(-1);
            if (!predecessors.containsKey(neighbor)) {
                predecessors.putNE(neighbor);
                boundary.addLast(neighbor);
            }
        }

        if (collisionMap.se(position.getX(), position.getY(), position.getPlane())) {
            WorldPoint neighbor = position.dx(1).dy(-1);
            if (!predecessors.containsKey(neighbor)) {
                predecessors.putNW(neighbor);
                boundary.addLast(neighbor);
            }
        }

        if (collisionMap.nw(position.getX(), position.getY(), position.getPlane())) {
            WorldPoint neighbor = position.dx(-1).dy(1);
            if (!predecessors.containsKey(neighbor)) {
                predecessors.putSE(neighbor);
                boundary.addLast(neighbor);
            }
        }

        if (collisionMap.ne(position.getX(), position.getY(), position.getPlane())) {
            WorldPoint neighbor = position.dx(1).dy(1);
            if (!predecessors.containsKey(neighbor)) {
                predecessors.putSW(neighbor);
                boundary.addLast(neighbor);
            }
        }
    }
}
