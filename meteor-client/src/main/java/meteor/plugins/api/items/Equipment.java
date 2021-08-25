package meteor.plugins.api.items;

import meteor.plugins.api.game.GameThread;
import meteor.plugins.api.widgets.Widgets;
import net.runelite.api.*;
import net.runelite.api.widgets.WidgetInfo;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Equipment {
    @Inject
    private static Client client;

    public static List<Item> getAll(Predicate<Item> filter) {
        List<Item> items = new ArrayList<>();
        ItemContainer container = client.getItemContainer(InventoryID.EQUIPMENT);
        if (container == null) {
            return items;
        }

        for (Item item : container.getItems()) {
            if (!client.isItemDefinitionCached(item.getId())) {
                GameThread.invokeLater(() -> client.getItemComposition(item.getId()));
            }

            if (item.getId() != -1 && item.getName() != null && !item.getName().equals("null")) {
                WidgetInfo widgetInfo = getEquipmentWidgetInfo(item.getIndex());
                if (widgetInfo != null) {
                    item.setIdentifier(0);
                    item.setActionParam(-1);
                    item.setWidgetId(widgetInfo.getPackedId());
                    item.setActions(Widgets.get(widgetInfo).getActions());
                    item.setWidgetInfo(widgetInfo);

                    if (filter.test(item)) {
                        items.add(item);
                    }
                }
            }
        }

        return items;
    }

    public static List<Item> getAll() {
        return getAll(x -> true);
    }

    public static Item getFirst(Predicate<Item> filter) {
        return getAll(filter).stream().findFirst().orElse(null);
    }

    public static Item getFirst(int... ids) {
        return getFirst(x -> {
            for (int id : ids) {
                if (id == x.getId()) {
                    return true;
                }
            }

            return false;
        });
    }

    public static Item getFirst(String... names) {
        return getFirst(x -> {
            if (x.getName() == null) {
                return false;
            }

            for (String name : names) {
                if (name.equals(x.getName())) {
                    return true;
                }
            }

            return false;
        });
    }

    private static WidgetInfo getEquipmentWidgetInfo(int itemIndex) {
        for (EquipmentInventorySlot equipmentInventorySlot : EquipmentInventorySlot.values()) {
            if (equipmentInventorySlot.getSlotIdx() == itemIndex) {
                return equipmentInventorySlot.getWidgetInfo();
            }
        }

        return null;
    }

    public static boolean contains(Predicate<Item> filter) {
        return getFirst(filter) != null;
    }

    public static boolean contains(int... ids) {
        return contains(x -> {
            for (int id : ids) {
                if (id == x.getId()) {
                    return true;
                }
            }

            return false;
        });
    }

    public static boolean contains(String... names) {
        return contains(x -> {
            if (x.getName() == null) {
                return false;
            }

            for (String name : names) {
                if (name.equals(x.getName())) {
                    return true;
                }
            }

            return false;
        });
    }
}
