package meteor.plugins.illutils.api;

import meteor.plugins.illutils.osrs.OSRSUtils;
import meteor.plugins.illutils.ui.Bank;
import meteor.plugins.illutils.walking.BankLocations;
import meteor.plugins.illutils.walking.TeleportSpell;
import meteor.plugins.illutils.walking.TeleportTab;

import java.util.ArrayList;
import java.util.List;

public class TeleportMethod {

    private OSRSUtils game;
    private TeleportLocation teleportLocation;
    private int quantity;

    public TeleportMethod(OSRSUtils game, TeleportLocation teleportLocation, int quantity) {
        this.teleportLocation = teleportLocation;
        this.quantity = quantity;
        this.game = game;
    }

    public List<ItemQuantity> getItems() {
        List<ItemQuantity> items = new ArrayList<>();
        TeleportTab tabLocation = this.teleportLocation.getTeleportTab();
        TeleportSpell locationSpell = this.teleportLocation.getTeleportSpell();
        int[][] itemIds = this.teleportLocation.getItemIds();

        if (game.membersWorld()) {
            if (itemIds != null && itemIds.length > 0) {

                ItemQuantity jewelleryTeleport = getItemTeleportInventory();
                if (jewelleryTeleport != null) {
                    List<ItemQuantity> l = new ArrayList<>();
                    l.add(jewelleryTeleport);
                    return l;
                }

                List<ItemQuantity> l = new ArrayList<>();
                jewelleryTeleport = getItemTeleportBank();
                if (jewelleryTeleport == null)
                    l.add(new ItemQuantity(itemIds[0][0], 1));
                else
                    l.add(jewelleryTeleport);

                return l;
            }

            if (tabLocation != null && tabLocation.hasRequirements(game)) {
                List<ItemQuantity> l = new ArrayList<>();
                l.add(new ItemQuantity(tabLocation.getTabletId(), this.quantity));
                return l;
            }
        }

        if (locationSpell != null && locationSpell.hasRequirements(game)) {
            for (int i = 0; i < this.quantity; i++) {
                items.addAll(locationSpell.recipe(game));
            }
            return items;
        }

        return items;
    }

    public boolean getTeleport(boolean checkBank) {
        if (hasTeleportInventory()) {
            return true;
        }

        if (checkBank) {
            TeleportTab tabLocation = this.teleportLocation.getTeleportTab();
            TeleportSpell locationSpell = this.teleportLocation.getTeleportSpell();

            if (tabLocation != null) {
                if (bank().withdraw(tabLocation.getTabletId(), 1, false) != 0) {
                    return true;
                }
            }

            if (locationSpell != null) {
                List<ItemQuantity> recipe = locationSpell.recipe(game);
                if (bank().contains(recipe)) {
                    for (ItemQuantity item : recipe) {
                        bank().withdraw(item.id, item.quantity, false);
                    }
                    return true;
                }
            }

            int[][] itemIds = this.teleportLocation.getItemIds();

            if (itemIds != null && itemIds.length > 0) {
                for (int[] itemId : itemIds) {
                    for (int id : itemId) {
                        if (bank().contains(new ItemQuantity(id, 1))) {
                            return bank().withdraw(id, 1, false) != 0;
                        }
                    }
                }
            }
        }

        return false;
    }

    public ItemQuantity getItemTeleportBank() {
        int[][] itemIds = this.teleportLocation.getItemIds();

        if (itemIds != null && itemIds.length > 0) {
            for (int[] itemId : itemIds) {
                for (int id : itemId) {
                    if (bank().contains(new ItemQuantity(id, 1))) {
                        return new ItemQuantity(id, 1);
                    }
                }
            }
        }
        return null;
    }

    public boolean hasTeleportBank() {
        TeleportTab tabLocation = this.teleportLocation.getTeleportTab();
        TeleportSpell locationSpell = this.teleportLocation.getTeleportSpell();
        int[][] itemIds = this.teleportLocation.getItemIds();

        if (tabLocation != null && bank().contains(new ItemQuantity(tabLocation.getTabletId(), this.quantity))) {
            return true;
        }

        if (locationSpell != null && bank().contains(locationSpell.recipe(game))) {
            return true;
        }

        if (itemIds != null && itemIds.length > 0) {
            for (int[] itemId : itemIds) {
                for (int id : itemId) {
                    if (bank().contains(new ItemQuantity(id, 1))) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public boolean hasTeleportInventory() {
        TeleportTab tabLocation = this.teleportLocation.getTeleportTab();
        TeleportSpell locationSpell = this.teleportLocation.getTeleportSpell();

        if (tabLocation != null && tabLocation.canUse(game) && game.membersWorld()) {
            return true;
        }

        if (locationSpell != null && locationSpell.canUse(game)) {
            return true;
        }

        return getItemTeleportInventory() != null;
    }

    public ItemQuantity getItemTeleportInventory() {
        int[][] itemIds = this.teleportLocation.getItemIds();

        if (itemIds != null && itemIds.length > 0) {
            for (int[] itemId : itemIds) {
                if (/*game.equipment().withId(itemId).exists() || */game.inventory().withId(itemId).exists()) {
                    return new ItemQuantity(game.inventory().withId(itemId).first().id(), 1);
                }
            }
        }
        return null;
    }

    protected Bank bank() {
        Bank bank = new Bank(game);

        if (!bank.isOpen()) {
            BankLocations.walkToBank(game);
            if (game.npcs().withName("Banker").withAction("Bank").exists()) {
                game.npcs().withName("Banker").withAction("Bank").nearest().interact("Bank");
            } else if (game.objects().withName("Bank booth").withAction("Bank").exists()) {
                game.objects().withName("Bank booth").withAction("Bank").nearest().interact("Bank");
            } else {
                game.objects().withName("Bank chest").nearest().interact("Use");
            }
            game.waitUntil(bank::isOpen, 10);
            game.tick();
        }

        return bank;
    }
}
