/*
 * Copyright (c) 2021 JumpIfZero <https://github.com/JumpIfZero>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package meteor.plugins.menuentrymodifier;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;
import meteor.config.ConfigSection;
import meteor.config.Keybind;

@ConfigGroup("menuentrymodifier")
public interface MenuEntryModifierConfig extends Config
{
    //region sections
    @ConfigSection(
            name = "Filters",
            description = "",
            position = 10,
            keyName = "filtersSection"
    )
    String filtersSection = "Filters";

    @ConfigSection(
        name = "General",
        description = "",
        position = 9,
        keyName = "general"
    )
    String general = "General";

    //endregion sections

    //region general
    @ConfigItem(
            keyName = "hotkeyButton",
            name = "Hotkey",
            description = "Hotkey for filtering",
            section = general,
            position = 0
    )
    default Keybind hotkeyButton() { return Keybind.NOT_SET; }

    @ConfigItem(
            keyName = "removeEnabled",
            name = "Removing enabled",
            description = "Remove option(s) enabled",
            section = general,
            position = 1
    )
    default boolean removeEnabled() { return false; }
    //endregion general

    //region filters
    @ConfigItem(
            keyName = "menuFilter",
            name = "Priority filter",
            description = "Choose filter type for menu entry priority filtering",
            position = 0,
            section = filtersSection
    )
    default MenuEntryModifierPlugin.filterOption menuFilter() { return MenuEntryModifierPlugin.filterOption.NONE; }

    @ConfigItem(
            keyName = "priorityList",
            name = "Priority list filter",
            description = "List of priority configure options. Syntax target,option such as man,pickpocket. Newline for each option, case is insensitive.",
            position = 1,
            section = filtersSection
    )
    default String priorityList() { return "banker,bank\nhammer,buy 50"; }

    @ConfigItem(
            keyName = "hotkeyFilter",
            name = "Hotkey filter",
            description = "Choose filter type for menu entry hotkey filtering",
            position = 2,
            section = filtersSection
    )
    default MenuEntryModifierPlugin.filterOption hotkeyFilter() { return MenuEntryModifierPlugin.filterOption.OPTION; }

    @ConfigItem(
            keyName = "hotkeyList",
            name = "Hotkey list filter",
            description = "List of hotkey configure options. Syntax target,option such as man,pickpocket. Newline for each option, case is insensitive.",
            position = 3,
            section = filtersSection
    )
    default String hotkeyList() { return "*,trade"; }

    @ConfigItem(
            keyName = "removeList",
            name = "Remove list filter",
            description = "List of removed menu entries. Such as examine. Newline for each option, case is insensitive.",
            position = 4,
            section = filtersSection
    )
    default String removeList() { return "examine\nempty"; }
    //endregion filters
}