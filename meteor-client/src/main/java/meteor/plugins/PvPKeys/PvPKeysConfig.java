package meteor.plugins.PvPKeys;

import meteor.config.*;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.*;

@ConfigGroup("PvPKeys")
public interface PvPKeysConfig extends Config {
    @ConfigItem(
            keyName = "Inventory",
            name = "Open inventory",
            description = ""
    )
    default ModifierlessKeybind Inventory()
    {
        return new ModifierlessKeybind(VK_ESCAPE,KEY_PRESSED);
    }
    @ConfigItem(
            keyName = "Spectab",
            name = "Open spec tab",
            description = ""
    )
    default ModifierlessKeybind Spectab()
    {
        return new ModifierlessKeybind(VK_F1,KEY_PRESSED);
    }
    @ConfigItem(
            keyName = "Prayertab",
            name = "Open prayer tab",
            description = ""
    )
    default ModifierlessKeybind Prayertab()
    {
        return new ModifierlessKeybind(VK_F2,KEY_PRESSED);
    }
    @ConfigItem(
            keyName = "Spellstab",
            name = "Open spells tab",
            description = ""
    )
    default ModifierlessKeybind Spellstab()
    {
        return new ModifierlessKeybind(VK_F3,KEY_PRESSED);
    }
    @ConfigItem(
            keyName = "Gearstab",
            name = "Open gear tab",
            description = ""
    )
    default ModifierlessKeybind Gearstab()
    {
        return new ModifierlessKeybind(VK_F4,KEY_PRESSED);
    }
    @ConfigItem(
            keyName = "Magepray",
            name = "Toggle prot mage",
            description = ""
    )
    default ModifierlessKeybind Magepray()
    {
        return new ModifierlessKeybind(VK_1,KEY_PRESSED);
    }
    @ConfigItem(
            keyName = "Rangepray",
            name = "Toggle prot range",
            description = ""
    )
    default ModifierlessKeybind Rangepray()
    {
            return new ModifierlessKeybind(VK_2,KEY_PRESSED);
    }
    @ConfigItem(
            keyName = "Meleepray",
            name = "Toggle prot melee",
            description = ""
    )
    default ModifierlessKeybind Meleepray()
    {
        return new ModifierlessKeybind(VK_3,KEY_PRESSED);
    }
    @ConfigItem(
            keyName = "Smite",
            name = "Toggle smite",
            description = ""
    )
    default ModifierlessKeybind Smite()
    {
        return new ModifierlessKeybind(VK_4,KEY_PRESSED);
    }
    @ConfigItem(
            keyName = "Brew",
            name = "Sip Brew",
            description = ""
    )
    default ModifierlessKeybind Brew()
    {
        return new ModifierlessKeybind(VK_5,KEY_PRESSED);
    }
    @ConfigItem(
            keyName = "Restore",
            name = "Sip Restore",
            description = ""
    )
    default ModifierlessKeybind Restore()
    {
        return new ModifierlessKeybind(VK_6,KEY_PRESSED);
    }
    @ConfigItem(
            keyName = "Quickpray",
            name = "Toggle Quick Prayer",
            description = ""
    )
    default ModifierlessKeybind Quickpray()
    {
        return new ModifierlessKeybind(VK_TAB,KEY_PRESSED);
    }
    @ConfigItem(
            keyName = "Magegear",
            name = "Swap to mage gear",
            description = ""
    )
    default ModifierlessKeybind Magegear()
    {
        return new ModifierlessKeybind(VK_Q,KEY_PRESSED);
    }
    @ConfigItem(
            keyName = "MageIDs",
            name = "Mage gear id's",
            description = ""
    )
    default String MageIDs()
    {
        return "";
    }
    @ConfigItem(
            keyName = "Rangegear",
            name = "Swap to Range gear",
            description = ""
    )
    default ModifierlessKeybind Rangegear()
    {
        return new ModifierlessKeybind(VK_W,KEY_PRESSED);
    }
    @ConfigItem(
            keyName = "RangeIDs",
            name = "Range gear id's",
            description = ""
    )
    default String RangeIDs()
    {
        return "";
    }
    @ConfigItem(
            keyName = "Meleegear",
            name = "Swap to Melee gear",
            description = ""
    )
    default ModifierlessKeybind Meleegear()
    {
        return new ModifierlessKeybind(VK_E,KEY_PRESSED);
    }
    @ConfigItem(
            keyName = "MeleeIDs",
            name = "Melee gear id's",
            description = ""
    )
    default String MeleeIDs()
    {
        return "";
    }
    @ConfigItem(
            keyName = "Icebarrage",
            name = "Select Ice Barrage",
            description = ""
    )
    default ModifierlessKeybind Icebarrage()
    {
        return new ModifierlessKeybind(VK_R,KEY_PRESSED);
    }
    @ConfigItem(
            keyName = "Iceblitz",
            name = "Select Ice Blitz(t)",
            description = ""
    )
    default ModifierlessKeybind Iceblitz()
    {
        return new ModifierlessKeybind(VK_T,KEY_PRESSED);
    }
    @ConfigItem(
            keyName = "Bloodblitz",
            name = "Select Blood Blitz(y)",
            description = ""
    )
    default ModifierlessKeybind Bloodblitz()
    {
        return new ModifierlessKeybind(VK_Y,KEY_PRESSED);
    }
    @ConfigItem(
            keyName = "Augury",
            name = "Toggle augury(a)",
            description = ""
    )
    default ModifierlessKeybind Augury()
    {
        return new ModifierlessKeybind(VK_A,KEY_PRESSED);
    }
    @ConfigItem(
            keyName = "Rigour",
            name = "Toggle rigour(s)",
            description = ""
    )
    default ModifierlessKeybind Rigour()
    {
        return new ModifierlessKeybind(VK_S,KEY_PRESSED);
    }
    @ConfigItem(
            keyName = "Piety",
            name = "Toggle piety(d)",
            description = ""
    )
    default ModifierlessKeybind Piety()
    {
        return new ModifierlessKeybind(VK_D,KEY_PRESSED);
    }
    @ConfigItem(
            keyName = "Lasttarger",
            name = "Last target",
            description = ""
    )
    default ModifierlessKeybind Lasttarget()
    {
        return new ModifierlessKeybind(VK_SPACE,KEY_PRESSED);
    }
}
