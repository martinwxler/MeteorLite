package net.runelite.api;

public interface Interactable {
    void interact(String action);

    void interact(int index);

    void interact(final int identifier, final int opcode, final int param0, final int param1);
}
