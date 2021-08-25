package meteor.plugins.api.widgets;

import meteor.plugins.api.game.Game;
import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;

import javax.inject.Inject;

public class Widgets {

    public static Widget get(WidgetInfo widgetInfo) {
        return Game.getClient().getWidget(widgetInfo);
    }

    public static Widget get(int group, int id) {
        return Game.getClient().getWidget(group, id);
    }
}
