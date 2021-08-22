package net.runelite.api;

import org.gradle.internal.impldep.org.bouncycastle.util.Arrays;

import java.util.List;

public interface Interactable {
    String[] getActions();

    int getActionId(int action);

    List<String> actions();

    void interact(String action);

    void interact(int index);

    void interact(final int identifier, final int opcode, final int param0, final int param1);

    default boolean hasAction(String action) {
        if (getActions() == null) {
            return false;
        }

        for (String a : getActions()) {
            if (a != null && a.equals(action)) {
                return true;
            }
        }

        return false;
    }
}
