package meteor.plugins.illutils.osrs.wrappers;

import meteor.plugins.illutils.osrs.OSRSUtils;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import meteor.plugins.illutils.api.scene.Locatable;
import meteor.plugins.illutils.api.scene.ObjectCategory;
import meteor.plugins.illutils.api.scene.Position;

import java.util.Arrays;
import java.util.Objects;

import static net.runelite.api.Constants.CHUNK_SIZE;

public class IllTile implements Locatable {
    final OSRSUtils game;
    final Tile tile;
    IllObject regularObject;
    IllObject wall;
    IllObject wallDecoration;
    IllObject floorDecoration;

    public IllTile(OSRSUtils game, Tile tile) {
        this.game = game;
        this.tile = tile;
    }

    //    @Override
    public OSRSUtils game() {
        return game;
    }

    @Override
    public Client client() {
        return game.client;
    }

    @Override
    public Position position() {
        return new Position(tile.getWorldLocation().getX(), tile.getWorldLocation().getY(), tile.getPlane());
    }

    public Position templatePosition() {
        if (client().isInInstancedRegion()) {
            LocalPoint localPoint = client().getLocalPlayer().getLocalLocation();
            int[][][] instanceTemplateChunks = client().getInstanceTemplateChunks();
            int z = client().getPlane();
            int chunkData = instanceTemplateChunks[z][localPoint.getSceneX() / CHUNK_SIZE][localPoint.getSceneY() / CHUNK_SIZE];

            int rotation = chunkData >> 1 & 0x3; //TODO
            int chunkX = (chunkData >> 14 & 0x3FF) * CHUNK_SIZE + (localPoint.getSceneX() % CHUNK_SIZE);
            int chunkY = (chunkData >> 3 & 0x7FF) * CHUNK_SIZE + (localPoint.getSceneY() % CHUNK_SIZE);
            int chunkZ = (chunkData >> 24 & 0x3);

            return new Position(chunkX, chunkY, chunkZ);
        }
        return game.localPlayer().position();
    }

    public void walkTo() {
        game.interactionManager().submit(() -> game.walkUtils.sceneWalk(position(), 0, 0));
    }

//    public List<GroundItem> items() {
//        return items;
//    }

//    public List<iObject> objects() {
//        ArrayList<iObject> objects = new ArrayList<>(4);
//        if (regularObject != null) objects.add(regularObject);
//        if (wall != null) objects.add(wall);
//        if (wallDecoration != null) objects.add(wallDecoration);
//        if (floorDecoration != null) objects.add(floorDecoration);
//        return objects;
//    }

    public IllObject object(ObjectCategory category) {
        switch (category) {
            case REGULAR:
                GameObject go = Arrays.stream(tile.getGameObjects()).filter(Objects::nonNull).findFirst().orElse(null);
                return (go == null) ? null : new IllObject(game, go,
                        game.getFromClientThread(() -> client().getObjectDefinition(go.getId()))
                );
            case WALL:
                WallObject wo = tile.getWallObject();
                return (wo == null) ? null : new IllObject(game, wo, game.getFromClientThread(() -> client().getObjectDefinition(wo.getId())));
            case WALL_DECORATION:
                DecorativeObject dec = tile.getDecorativeObject();
                return (dec == null) ? null : new IllObject(game, dec, game.getFromClientThread(() -> client().getObjectDefinition(dec.getId())));
            case FLOOR_DECORATION:
                GroundObject ground = tile.getGroundObject();
                return (ground == null) ? null : new IllObject(game, ground, game.getFromClientThread(() -> client().getObjectDefinition(ground.getId())));
            default:
                return null;
        }
    }

    /*public iObject object(ObjectCategory category) {
    	iObject result;
        return switch (category) {
            case REGULAR -> regularObject;
            case WALL -> wall;
            case WALL_DECORATION -> wallDecoration;
            case FLOOR_DECORATION -> floorDecoration;
        };
    }*/
}
