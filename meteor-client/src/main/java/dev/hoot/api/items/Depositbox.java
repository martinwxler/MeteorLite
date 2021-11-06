package dev.hoot.api.items;

import dev.hoot.api.commons.Time;
import dev.hoot.api.game.Game;
import dev.hoot.api.widgets.Widgets;
import net.runelite.api.widgets.Widget;

public class Depositbox {
    public final Game game;

    public Depositbox(Game game) {
        this.game = game;
    }

    public void depositInventory() {
        checkDepositboxOpen();
        if (!Inventory.isEmpty()) {
            Widget depositInventory = Widgets.get(192, 4);
            if (depositInventory == null)
                return;

            depositInventory.interact(0);
            Time.sleepUntil(Inventory::isEmpty, 5);
        }
    }

    public void depositEquipment() {
        checkDepositboxOpen();
        if (Equipment.getAll().size() != 0) {
            Widget depositEquipment = Widgets.get(192, 6);
            if (depositEquipment == null)
                return;
            depositEquipment.interact(0);
            Time.sleepUntil(() -> Equipment.getAll().size() == 0, 5);
        }
    }

    public void depositLootingbag() {
        checkDepositboxOpen();
        Widget depositLootingbag = Widgets.get(192, 8);
        if (depositLootingbag == null)
            return;
        depositLootingbag.interact(0);

    }

    private void checkDepositboxOpen() {
        if (!isOpen()) {
            throw new IllegalStateException("depositbox isn't open");
        }
    }

    public boolean isOpen() {
        Widget open = Widgets.get(192, 1);
        return open != null;
    }

    public void close() {
        if (isOpen()) {
            Widget exitDepositBox = Widgets.get(192, 1, 11);
            exitDepositBox.interact(0);
            Time.sleepUntil(() -> !isOpen(), 5);
        }
    }
}