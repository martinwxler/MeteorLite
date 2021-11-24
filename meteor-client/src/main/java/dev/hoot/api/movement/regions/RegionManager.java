package dev.hoot.api.movement.regions;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.hoot.api.game.Game;
import dev.hoot.api.movement.Reachable;
import dev.hoot.api.movement.pathfinder.Transport;
import dev.hoot.api.movement.pathfinder.Walker;
import dev.hoot.api.scene.Tiles;
import net.runelite.api.CollisionData;
import net.runelite.api.CollisionDataFlag;
import net.runelite.api.Tile;
import net.runelite.api.coords.Direction;
import net.runelite.api.coords.WorldPoint;
import okhttp3.*;
import org.sponge.util.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Singleton
public class RegionManager {
    private static final Logger logger = new Logger("RegionManager");
    private static final int VERSION = 1;
    private static final MediaType JSON_MEDIATYPE = MediaType.parse("application/json");
    private static final String API_URL = "http://174.138.15.181:8080/regions/" + VERSION;

    private final Gson gson = new GsonBuilder().create();

    @Inject
    private OkHttpClient okHttpClient;

    public void sendRegion() {
        CollisionData[] col = Game.getClient().getCollisionMaps();
        if (col == null) {
            return;
        }

        List<TileFlag> tileFlags = new ArrayList<>();
        Map<WorldPoint, List<Transport>> transportLinks = Walker.buildTransportLinks();
        int plane = Game.getClient().getPlane();
        CollisionData data = col[plane];
        if (data == null) {
            return;
        }

        int[][] flags = data.getFlags();
        for (int x = 0; x < flags.length; x++) {
            for (int y = 0; y < flags.length; y++) {
                int tileX = x + Game.getClient().getBaseX();
                int tileY = y + Game.getClient().getBaseY();
                int flag = flags[x][y];

                if (flag == 0xFFFFFF) {
                    continue;
                }

                Tile tile = Tiles.getAt(tileX, tileY, plane);
                if (tile == null) {
                    continue;
                }

                WorldPoint tileCoords = tile.getWorldLocation();
                TileFlag tileFlag = new TileFlag(tileX, tileY, plane, flag, tileCoords.getRegionID(), x, y);
                if (!Reachable.isObstacle(tileCoords)) {
                    WorldPoint northernTile = tileCoords.dy(1);
                    if (Reachable.isObstacle(northernTile)
                            && !Reachable.isWalled(Direction.NORTH, tileFlag.getFlag())) {
                        tileFlag.setFlag(tileFlag.getFlag() + CollisionDataFlag.BLOCK_MOVEMENT_NORTH);
                    }

                    WorldPoint easternTile = tileCoords.dx(1);
                    if (Reachable.isObstacle(easternTile)
                            && !Reachable.isWalled(Direction.EAST, tileFlag.getFlag())) {
                        tileFlag.setFlag(tileFlag.getFlag() + CollisionDataFlag.BLOCK_MOVEMENT_EAST);
                    }

                    List<Transport> transports = transportLinks.get(tileCoords);
                    if (plane == Game.getClient().getPlane()) {
                        for (Direction direction : Direction.values()) {
                            switch (direction) {
                                case NORTH:
                                    if ((Reachable.hasDoor(tile, direction) || Reachable.hasDoor(northernTile, Direction.SOUTH))
                                            && !isTransport(transports, tileCoords, northernTile)) {
                                        tileFlag.setFlag(tileFlag.getFlag() - CollisionDataFlag.BLOCK_MOVEMENT_NORTH);
                                    }

                                    break;
                                case EAST:
                                    if ((Reachable.hasDoor(tile, direction) || Reachable.hasDoor(easternTile, Direction.WEST))
                                            && !isTransport(transports, tileCoords, easternTile)) {
                                        tileFlag.setFlag(tileFlag.getFlag() - CollisionDataFlag.BLOCK_MOVEMENT_EAST);
                                    }

                                    break;
                                case WEST:
                                    if ((Reachable.hasDoor(tile, direction) || Reachable.hasDoor(tileCoords.dx(-1), Direction.EAST))
                                            && !isTransport(transports, tileCoords, tileCoords.dx(-1))) {
                                        tileFlag.setFlag(tileFlag.getFlag() - CollisionDataFlag.BLOCK_MOVEMENT_WEST);
                                    }

                                    break;
                                case SOUTH:
                                    if ((Reachable.hasDoor(tile, direction) || Reachable.hasDoor(tileCoords.dy(-1), Direction.NORTH))
                                            && !isTransport(transports, tileCoords, tileCoords.dy(-1))) {
                                        tileFlag.setFlag(tileFlag.getFlag() - CollisionDataFlag.BLOCK_MOVEMENT_SOUTH);
                                    }

                                    break;
                            }
                        }
                    }
                }

                tileFlags.add(tileFlag);
            }
        }

        try {
            String json = gson.toJson(tileFlags);
            RequestBody body = RequestBody.create(JSON_MEDIATYPE, json);
            Request request = new Request.Builder()
                    .post(body)
                    .url(API_URL)
                    .build();
            Response response = okHttpClient.newCall(request)
                    .execute();
            int code = response.code();
            if (code != 200) {
                logger.error("Request was unsuccessful: {}", code);
                return;
            }

            logger.debug("Region saved successfully");
        } catch (Exception e) {
            logger.error("Failed to POST: {}", e.getMessage());
            e.printStackTrace();
        }
    }

    public boolean isTransport(List<Transport> transports, WorldPoint from, WorldPoint to) {
        if (transports == null) {
            return false;
        }

        return transports.stream().anyMatch(t -> t.getSource().equals(from) && t.getDestination().equals(to));
    }
}
