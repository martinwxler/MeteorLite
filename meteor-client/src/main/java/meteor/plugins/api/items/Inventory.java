package meteor.plugins.api.items;

import meteor.plugins.api.widgets.Widgets;
import net.runelite.api.*;
import net.runelite.api.widgets.WidgetInfo;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Inventory {
    @Inject
    private static Client client;

    public static List<Item> getAll(Predicate<Item> filter) {
        List<Item> items = new ArrayList<>();
        ItemContainer container = client.getItemContainer(InventoryID.INVENTORY);
        if (container == null) {
            return items;
        }

        for (Item item : container.getItems()) {
            if (item.getId() != -1 && !item.getName().equals("null")) {
                WidgetInfo widgetInfo = WidgetInfo.INVENTORY;
                item.setIdentifier(item.getId());
                item.setWidgetInfo(widgetInfo);
                item.setActionParam(item.getIndex());
                item.setWidgetId(widgetInfo.getId());
                item.setActions(Widgets.get(widgetInfo).getActions());

                if (filter.test(item)) {
                    items.add(item);
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

    public static Item getFirst(int id) {
        return getFirst(x -> x.getId() == id);
    }

    public static Item getFirst(String name) {
        return getFirst(x -> x.getName().equals(name));
    }

    public static boolean contains(Predicate<Item> filter) {
        return getFirst(filter) != null;
    }

    public static boolean contains(int id) {
        return contains(x -> x.getId() == id);
    }

    public static boolean contains(String name) {
        return contains(x -> x.getName().equals(name));
    }
}
