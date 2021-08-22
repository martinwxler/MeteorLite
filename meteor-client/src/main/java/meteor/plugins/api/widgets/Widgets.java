package meteor.plugins.api.widgets;

import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;

import javax.inject.Inject;

public class Widgets {
    @Inject
    private static Client client;

    public static Widget get(WidgetInfo widgetInfo) {
        return client.getWidget(widgetInfo);
    }

    public static Widget get(int group, int id) {
        return client.getWidget(group, id);
    }
}
