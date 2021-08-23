package meteor.plugins.chat;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;
import meteor.config.Keybind;

import java.awt.*;
import java.awt.event.KeyEvent;

@ConfigGroup("sChat")
public interface sChatConfig extends Config
{
    @ConfigItem(
            keyName = "hotkey",
            name = "Chat Toggle",
            description = "When you press this key it will toggle typing to socket",
            position = 0
    )
    default Keybind hotkey()
    {
        return new Keybind(KeyEvent.VK_BACK_QUOTE, 0);
    }

    @ConfigItem(
            keyName = "messageColor",
            name = "Message Color",
            description = "Color of socket messages",
            position = 1
    )
    default Color messageColor() { return Color.decode("0x113774");}

    @ConfigItem(
            keyName = "overrideTradeButton",
            name = "Override Trade Button",
            description = "Uses trade button for socket chat",
            position = 2
    )
    default boolean overrideTradeButton() { return false;}

    @ConfigItem(
            keyName = "overrideSlash",
            name = "/ Bypasses socket chat",
            description = "Typing / before message will send it to cc even if socket chat is toggled.",
            position = 3
    )
    default boolean overrideSlash() { return false;}
}
