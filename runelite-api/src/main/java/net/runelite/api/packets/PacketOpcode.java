package net.runelite.api.packets;

public enum PacketOpcode {
    ENTERINPUT_NAME(46),
    ENTERINPUT_TEXT(100),
    ENTERINPUT_NUMBER(57),
    REPORT_PLAYER(38),

    ;

    private final int opcode;

    PacketOpcode(int opcode) {
        this.opcode = opcode;
    }

    public int getOpcode() {
        return opcode;
    }
}
