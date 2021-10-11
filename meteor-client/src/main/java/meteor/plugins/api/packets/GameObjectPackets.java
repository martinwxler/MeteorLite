package meteor.plugins.api.packets;

import meteor.plugins.api.game.Game;
import net.runelite.api.GameObject;
import net.runelite.api.NPC;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.packets.PacketBufferNode;
import net.runelite.api.packets.PacketWriter;
import osrs.KeyHandler;
import osrs.PlayerComposition;
import osrs.WorldMapSectionType;

import java.util.List;

public class GameObjectPackets {

    public static void queueGameObjectActionPacket(int objectID, int worldPointX, int worldPointY, int ctrlDown) {
        PacketWriter writer = Game.getClient().getPacketWriter();
        PacketBufferNode packet = Game.getClient().preparePacket(Game.getClient().getGameObjectActionPacket(), writer.getIsaacCipher());
        packet.getPacketBuffer().writeShort01A$api(objectID);
        packet.getPacketBuffer().writeByte01A$api(ctrlDown);
        packet.getPacketBuffer().writeShort01A$api(worldPointY);
        packet.getPacketBuffer().writeShort01A$api(worldPointX);
        writer.queuePacket(packet);
    }

    public static void queueGameObjectAction2Packet(int objectID, int worldPointX, int worldPointY, int ctrlDown){
        PacketWriter writer = Game.getClient().getPacketWriter();
        PacketBufferNode packet = Game.getClient().preparePacket(Game.getClient().getGameObjectAction2Packet(), writer.getIsaacCipher());
        packet.getPacketBuffer().writeShort01A$api(worldPointY);
        packet.getPacketBuffer().writeShortA$api(worldPointX);
        packet.getPacketBuffer().writeByte$api(ctrlDown);
        packet.getPacketBuffer().writeShortA$api(objectID);
        writer.queuePacket(packet);
    }

    public static void queueGameObjectAction3Packet(int objectID, int worldPointX, int worldPointY, int ctrlDown) {
        PacketWriter writer = Game.getClient().getPacketWriter();
        PacketBufferNode packet = Game.getClient().preparePacket(Game.getClient().getGameObjectAction3Packet(), writer.getIsaacCipher());
        packet.getPacketBuffer().writeShort01$api(worldPointY);
        packet.getPacketBuffer().writeByte$api(ctrlDown);
        packet.getPacketBuffer().writeShort01A$api(worldPointX);
        packet.getPacketBuffer().writeShortA$api(objectID);
        writer.queuePacket(packet);
    }
    public static void queueGameObjectAction4Packet(int objectID, int worldPointX, int worldPointY, int ctrlDown) {
        PacketWriter writer = Game.getClient().getPacketWriter();
        PacketBufferNode packet = Game.getClient().preparePacket(Game.getClient().getGameObjectAction4Packet(), writer.getIsaacCipher());
        packet.getPacketBuffer().writeShortA$api(worldPointY);
        packet.getPacketBuffer().writeShortA$api(worldPointX);
        packet.getPacketBuffer().writeByte01A$api(ctrlDown);
        packet.getPacketBuffer().writeShort01A$api(objectID);
        writer.queuePacket(packet);
    }
    public static void queueGameObjectAction5Packet(int objectID, int worldPointX, int worldPointY, int ctrlDown) {
        PacketWriter writer = Game.getClient().getPacketWriter();
        PacketBufferNode packet = Game.getClient().preparePacket(Game.getClient().getGameObjectAction5Packet(), writer.getIsaacCipher());
        packet.getPacketBuffer().writeShort01$api(objectID);
        packet.getPacketBuffer().writeShort01$api(worldPointY);
        packet.getPacketBuffer().writeShort01$api(worldPointX);
        packet.getPacketBuffer().writeByte01$api(ctrlDown);
        writer.queuePacket(packet);
    }
    public static void gameObjectFirstOption(GameObject object,int ctrlDown){
        Point p = object.menuPoint();
        LocalPoint lp = new LocalPoint(p.getX(),p.getY());
        WorldPoint wp = WorldPoint.fromScene(Game.getClient(),lp.getX(),lp.getY(),object.getPlane());
        queueGameObjectActionPacket(object.getId(),wp.getX(),wp.getY(),ctrlDown);
    }
    public static void gameObjectSecondOption(GameObject object,int ctrlDown){
        Point p = object.menuPoint();
        LocalPoint lp = new LocalPoint(p.getX(),p.getY());
        WorldPoint wp = WorldPoint.fromScene(Game.getClient(),lp.getX(),lp.getY(),object.getPlane());
        queueGameObjectAction2Packet(object.getId(),wp.getX(),wp.getY(),ctrlDown);
    }
    public static void gameObjectThirdOption(GameObject object,int ctrlDown){
        Point p = object.menuPoint();
        LocalPoint lp = new LocalPoint(p.getX(),p.getY());
        WorldPoint wp = WorldPoint.fromScene(Game.getClient(),lp.getX(),lp.getY(),object.getPlane());
        queueGameObjectAction3Packet(object.getId(),wp.getX(),wp.getY(),ctrlDown);
    }
    public static void gameObjectFourthOption(GameObject object,int ctrlDown){
        Point p = object.menuPoint();
        LocalPoint lp = new LocalPoint(p.getX(),p.getY());
        WorldPoint wp = WorldPoint.fromScene(Game.getClient(),lp.getX(),lp.getY(),object.getPlane());
        queueGameObjectAction4Packet(object.getId(),wp.getX(),wp.getY(),ctrlDown);
    }
    public static void gameObjectFifthOption(GameObject object,int ctrlDown){
        Point p = object.menuPoint();
        LocalPoint lp = new LocalPoint(p.getX(),p.getY());
        WorldPoint wp = WorldPoint.fromScene(Game.getClient(),lp.getX(),lp.getY(),object.getPlane());
        queueGameObjectAction5Packet(object.getId(),wp.getX(),wp.getY(),ctrlDown);
    }
    public static void gameObjectAction(GameObject object, String action, int ctrlDown) {
        List<String> actions = object.getActions();
        int index = actions.indexOf(action);
        switch (index) {
            case 0 -> gameObjectFirstOption(object,ctrlDown);
            case 1 -> gameObjectSecondOption(object,ctrlDown);
            case 2 -> gameObjectThirdOption(object,ctrlDown);
            case 3 -> gameObjectFourthOption(object,ctrlDown);
            case 4 -> gameObjectFifthOption(object,ctrlDown);
        }
    }
}
