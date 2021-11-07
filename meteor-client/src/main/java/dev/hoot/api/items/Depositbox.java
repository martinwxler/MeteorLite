package dev.hoot.api.items;

import dev.hoot.api.commons.Time;
import dev.hoot.api.game.Game;
import dev.hoot.api.widgets.Widgets;
import net.runelite.api.widgets.Widget;

public class Depositbox {

    public static void depositInventory() {
        Widget depositInventory = Widgets.get(192, 4);
        if (depositInventory == null)
            return;

        depositInventory.interact(0);
    }

    public static void depositEquipment() {
        Widget depositEquipment = Widgets.get(192, 6);
        if (depositEquipment == null)
            return;
        depositEquipment.interact(0);
        Time.sleepUntil(() -> Equipment.getAll().size() == 0, 5);
    }

    public static void depositLootingbag() {
        Widget depositLootingbag = Widgets.get(192, 8);
        if (depositLootingbag == null)
            return;
        depositLootingbag.interact(0);
    }

    public static boolean isOpen() {
        Widget depositBox = Widgets.get(192, 1);
        return depositBox != null;
    }

    public static void close() {
        Widget exitDepositBox = Widgets.get(192, 1, 11);
        if (exitDepositBox == null)
            return;
        exitDepositBox.interact(0);
    }
}