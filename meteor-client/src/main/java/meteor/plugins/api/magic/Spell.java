package meteor.plugins.api.magic;

import net.runelite.api.widgets.WidgetInfo;

public interface Spell {
    int getLevel();
    WidgetInfo getWidget();
}
