package dev.hoot.api.packets;

import dev.hoot.api.game.Game;
import net.runelite.api.Point;
import net.runelite.api.TileObject;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import osrs.Client;
import osrs.ClientPacket;

import java.util.List;

public class TileObjectPackets {

    public static void queueTileObjectActionPacket(int objectID, int worldPointX, int worldPointY, int ctrlDown) {
        osrs.PacketBufferNode var8 = (osrs.PacketBufferNode) Game.getClient().preparePacket(ClientPacket.OPLOC1, Client.packetWriter.isaacCipher);
        var8.packetBuffer.writeByteB0(worldPointX);
        var8.packetBuffer.writeShort(worldPointY);
        var8.packetBuffer.writeByteA(objectID);
        var8.packetBuffer.writeByte(ctrlDown);
        Client.packetWriter.addNode(var8);
    }

    public static void queueTileObjectAction2Packet(int objectID, int worldPointX, int worldPointY, int ctrlDown){
        osrs.PacketBufferNode var8 = (osrs.PacketBufferNode) Game.getClient().preparePacket(ClientPacket.OPLOC2, Client.packetWriter.isaacCipher);
        var8.packetBuffer.writeByteB0(worldPointX);
        var8.packetBuffer.writeByte(ctrlDown);
        var8.packetBuffer.writeShort(objectID);
        var8.packetBuffer.writeByteC(worldPointY);
        Client.packetWriter.addNode(var8);
    }

    public static void queueTileObjectAction3Packet(int objectID, int worldPointX, int worldPointY, int ctrlDown) {
        osrs.PacketBufferNode var8 = (osrs.PacketBufferNode) Game.getClient().preparePacket(ClientPacket.OPLOC3, Client.packetWriter.isaacCipher);
        var8.packetBuffer.writeShort(worldPointX);
        var8.packetBuffer.writeByte(ctrlDown);
        var8.packetBuffer.writeShort(worldPointY);
        var8.packetBuffer.writeByteA(objectID);
        Client.packetWriter.addNode(var8);
    }
    public static void queueTileObjectAction4Packet(int objectID, int worldPointX, int worldPointY, int ctrlDown) {
        osrs.PacketBufferNode var8 = (osrs.PacketBufferNode) Game.getClient().preparePacket(ClientPacket.OPLOC4, Client.packetWriter.isaacCipher);
        var8.packetBuffer.writeByteC(objectID);
        var8.packetBuffer.writeShort(worldPointY);
        var8.packetBuffer.writeByteC(worldPointX);
        var8.packetBuffer.write1(ctrlDown);
        Client.packetWriter.addNode(var8);
    }
    public static void queueTileObjectAction5Packet(int objectID, int worldPointX, int worldPointY, int ctrlDown) {
        osrs.PacketBufferNode var8 = (osrs.PacketBufferNode) Game.getClient().preparePacket(ClientPacket.OPLOC5, Client.packetWriter.isaacCipher);
        var8.packetBuffer.writeByteA(worldPointX);
        var8.packetBuffer.writeByteC(objectID);
        var8.packetBuffer.writeByteC(worldPointY);
        var8.packetBuffer.writeByteB(ctrlDown);
        Client.packetWriter.addNode(var8);
    }
    public static void tileObjectFirstOption(TileObject object, int ctrlDown){
        Point p = object.menuPoint();
        LocalPoint lp = new LocalPoint(p.getX(),p.getY());
        WorldPoint wp = WorldPoint.fromScene(Game.getClient(),lp.getX(),lp.getY(),object.getPlane());
        queueTileObjectActionPacket(object.getId(),wp.getX(),wp.getY(),ctrlDown);
    }
    public static void tileObjectSecondOption(TileObject object,int ctrlDown){
        Point p = object.menuPoint();
        LocalPoint lp = new LocalPoint(p.getX(),p.getY());
        WorldPoint wp = WorldPoint.fromScene(Game.getClient(),lp.getX(),lp.getY(),object.getPlane());
        queueTileObjectAction2Packet(object.getId(),wp.getX(),wp.getY(),ctrlDown);
    }
    public static void tileObjectThirdOption(TileObject object,int ctrlDown){
        Point p = object.menuPoint();
        LocalPoint lp = new LocalPoint(p.getX(),p.getY());
        WorldPoint wp = WorldPoint.fromScene(Game.getClient(),lp.getX(),lp.getY(),object.getPlane());
        queueTileObjectAction3Packet(object.getId(),wp.getX(),wp.getY(),ctrlDown);
    }
    public static void tileObjectFourthOption(TileObject object,int ctrlDown){
        Point p = object.menuPoint();
        LocalPoint lp = new LocalPoint(p.getX(),p.getY());
        WorldPoint wp = WorldPoint.fromScene(Game.getClient(),lp.getX(),lp.getY(),object.getPlane());
        queueTileObjectAction4Packet(object.getId(),wp.getX(),wp.getY(),ctrlDown);
    }
    public static void tileObjectFifthOption(TileObject object,int ctrlDown){
        Point p = object.menuPoint();
        LocalPoint lp = new LocalPoint(p.getX(),p.getY());
        WorldPoint wp = WorldPoint.fromScene(Game.getClient(),lp.getX(),lp.getY(),object.getPlane());
        queueTileObjectAction5Packet(object.getId(),wp.getX(),wp.getY(),ctrlDown);
    }
    public static void tileObjectAction(TileObject object, String action, int ctrlDown) {
        List<String> actions = object.getActions();
        int index = actions.indexOf(action);
        switch (index) {
            case 0 -> tileObjectFirstOption(object,ctrlDown);
            case 1 -> tileObjectSecondOption(object,ctrlDown);
            case 2 -> tileObjectThirdOption(object,ctrlDown);
            case 3 -> tileObjectFourthOption(object,ctrlDown);
            case 4 -> tileObjectFifthOption(object,ctrlDown);
        }
    }
}
