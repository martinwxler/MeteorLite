package dev.hoot.api.packets;

import dev.hoot.api.game.Game;
import net.runelite.api.NPC;
import osrs.Client;
import osrs.ClientPacket;

import java.util.List;

public class NPCPackets {
    public static void queueNPCActionPacket(int NPCIndex, int ctrlDown) {
        osrs.PacketBufferNode var9 = (osrs.PacketBufferNode) Game.getClient().preparePacket(ClientPacket.OPNPC1, Client.packetWriter.isaacCipher);
        var9.packetBuffer.write2(ctrlDown);
        var9.packetBuffer.writeByteB0(NPCIndex);
        Client.packetWriter.addNode(var9);
    }

    public static void queueNPCAction2Packet(int NPCIndex, int ctrlDown) {
        osrs.PacketBufferNode var9 = (osrs.PacketBufferNode) Game.getClient().preparePacket(ClientPacket.OPNPC2, Client.packetWriter.isaacCipher);
        var9.packetBuffer.writeByteB0(NPCIndex);
        var9.packetBuffer.writeByte(ctrlDown);
        Client.packetWriter.addNode(var9);
    }

    public static void queueNPCAction3Packet(int NPCIndex, int ctrlDown) {
        osrs.PacketBufferNode var9 = (osrs.PacketBufferNode) Game.getClient().preparePacket(ClientPacket.OPNPC3, Client.packetWriter.isaacCipher);
        var9.packetBuffer.write2(ctrlDown);
        var9.packetBuffer.writeByteA(NPCIndex);
        Client.packetWriter.addNode(var9);
    }

    public static void queueNPCAction4Packet(int NPCIndex, int ctrlDown) {
        osrs.PacketBufferNode var9 = (osrs.PacketBufferNode) Game.getClient().preparePacket(ClientPacket.OPNPC4, Client.packetWriter.isaacCipher);
        var9.packetBuffer.writeByteB0(NPCIndex);
        var9.packetBuffer.writeByteB(ctrlDown);
        Client.packetWriter.addNode(var9);
    }

    public static void queueNPCAction5Packet(int NPCIndex, int ctrlDown) {
        osrs.PacketBufferNode var9 = (osrs.PacketBufferNode) Game.getClient().preparePacket(ClientPacket.OPNPC5, Client.packetWriter.isaacCipher);
        var9.packetBuffer.write1(ctrlDown);
        var9.packetBuffer.writeShort(NPCIndex);
        Client.packetWriter.addNode(var9);
    }
    public static void npcFirstOption(NPC npc, int ctrlDown) {
        queueNPCActionPacket(npc.getIndex(),ctrlDown);
    }
    public static void npcSecondOption(NPC npc, int ctrlDown) {
        queueNPCAction2Packet(npc.getIndex(),ctrlDown);
    }
    public static void npcThirdOption(NPC npc, int ctrlDown) {
        queueNPCAction3Packet(npc.getIndex(),ctrlDown);
    }
    public static void npcFourthOption(NPC npc, int ctrlDown) {
        queueNPCAction4Packet(npc.getIndex(),ctrlDown);
    }
    public static void npcFifthOption(NPC npc, int ctrlDown) {
        queueNPCAction5Packet(npc.getIndex(),ctrlDown);
    }
    public static void npcAction(NPC npc, String action, int ctrlDown) {
        List<String> actions = npc.getActions();
        int index = actions.indexOf(action);
        switch (index) {
            case 0 -> npcFirstOption(npc,ctrlDown);
            case 1 -> npcSecondOption(npc,ctrlDown);
            case 2 -> npcThirdOption(npc,ctrlDown);
            case 3 -> npcFourthOption(npc,ctrlDown);
            case 4 -> npcFifthOption(npc,ctrlDown);
        }
    }

}
