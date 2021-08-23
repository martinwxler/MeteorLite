package net.runelite.api.packets;

public interface ClientPacket extends Packet {
    int getId();
    int getLength();
}
