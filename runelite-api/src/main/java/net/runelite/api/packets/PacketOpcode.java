package net.runelite.api.packets;

public enum PacketOpcode {
    ENTERINPUT_NAME(32),
    ENTERINPUT_TEXT(59),
    ENTERINPUT_NUMBER(25),

    ;

    private final int opcode;

    PacketOpcode(int opcode) {
        this.opcode = opcode;
    }

    public int getOpcode() {
        return opcode;
    }
}
