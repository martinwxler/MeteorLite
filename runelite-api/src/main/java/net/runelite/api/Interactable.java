package net.runelite.api;

import java.util.List;

public interface Interactable {
    String[] getActions();

    int getActionId(int action);

    List<String> actions();

    void interact(String action);

    void interact(int index);

    void interact(final int identifier, final int opcode, final int param0, final int param1);
}
