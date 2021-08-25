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

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.eventbus.events.ConfigChanged;
import meteor.input.KeyManager;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.util.HotkeyListener;
import meteor.util.Text;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.MenuEntryAdded;

import javax.inject.Inject;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@PluginDescriptor(
        name = "Menu Entry Modifier",
        description = "Fully customizable menu entries",
        tags = {"menu", "entry", "mes", "jz"},
        enabledByDefault = false
)

public class MenuEntryModifierPlugin extends Plugin
{
    @Inject
    private Client client;

    @Inject
    private KeyManager keyManager;

    @Inject
    private ConfigManager configManager;

    @Inject
    private MenuEntryModifierConfig config;

    private boolean active;
    private MenuEntry cancel;
    private final Multimap<String, String> filterEntries = ArrayListMultimap.create();
    private final Multimap<String, String> hotkeyEntries = ArrayListMultimap.create();
    private final ArrayList<String> removed = new ArrayList<>();

    public enum filterOption {
        NONE,
        OPTION,
        TARGET,
        BOTH
    }

    @Provides
    public MenuEntryModifierConfig getConfig(ConfigManager configManager)
    {
        return configManager.getConfig(MenuEntryModifierConfig.class);
    }

    private final HotkeyListener hotkeyListener = new HotkeyListener(() -> config.hotkeyButton())
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            if (config.hotkeyButton().matches(e))
                active = true;
        }

        @Override
        public void keyReleased(KeyEvent e)
        {
            if (config.hotkeyButton().matches(e))
                active = false;
        }
    };

    @Override
    public void startup()
    {
        active = false;
        loadPriorityEntries();
        loadRemovedEntries();
        loadHotkeyEntries();

        keyManager.registerKeyListener(hotkeyListener, this.getClass());
    }

    @Override
    public void shutdown()
    {
        active = false;
        removed.clear();
        filterEntries.clear();
        hotkeyEntries.clear();

        keyManager.unregisterKeyListener(hotkeyListener);
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged event)
    {
        if (event.getGroup().equals("menuentrymodifier"))
        {
            if (event.getKey().equals("hotkeyButton"))
                active = false;

            loadPriorityEntries();
            loadRemovedEntries();
            loadHotkeyEntries();
        }
    }

    @Subscribe
    public void onGameStateChanged(GameStateChanged event)
    {
        if (client.getGameState() != GameState.LOGGED_IN)
            active = false;
    }

    @Subscribe
    public void onMenuEntryAdded(MenuEntryAdded event)
    {
        if (config.menuFilter() == filterOption.NONE &&
                config.hotkeyFilter() == filterOption.NONE &&
                !config.removeEnabled())
            return;

        ArrayList<MenuEntry> entries = new ArrayList<>();
        Collections.addAll(entries, client.getMenuEntries());
        Collections.reverse(entries);

        if (config.removeEnabled())
            entries.removeIf(e -> removed.stream().anyMatch(sanitizeEntry(e.getOption())::contains));

        MenuEntry newCancel = entries
                .stream()
                .filter(e -> e.getOption().equals("Cancel"))
                .findFirst()
                .orElse(null);

        if (newCancel != null && newCancel != cancel)
            cancel = newCancel;

        if ((active ? config.hotkeyFilter() : config.menuFilter()) == filterOption.BOTH)
        {
            MenuEntry entry = entries
                    .stream()
                    .filter(e -> (active ? hotkeyEntries : filterEntries)
                            .entries()
                            .stream()
                            .anyMatch(p ->
                                    sanitizeEntry(e.getTarget()).contains(p.getKey()) &&
                                            sanitizeEntry(e.getOption()).contains(p.getValue())))
                    .findFirst()
                    .orElse(null);

            if (entry != null)
            {
                setPriorityEntry(entry);
                return;
            }
        }
        else if ((active ? config.hotkeyFilter() : config.menuFilter()) == filterOption.OPTION)
        {
            MenuEntry entry = entries
                    .stream()
                    .filter(e -> (active ? hotkeyEntries : filterEntries)
                            .values()
                            .stream()
                            .anyMatch(sanitizeEntry(e.getOption())::contains))
                    .findFirst()
                    .orElse(null);

            if (entry != null)
            {
                setPriorityEntry(entry);
                return;
            }
        }
        else if ((active ? config.hotkeyFilter() : config.menuFilter()) == filterOption.TARGET)
        {
            MenuEntry entry = entries
                    .stream()
                    .filter(e -> (active ? hotkeyEntries : filterEntries)
                            .keySet()
                            .stream()
                            .anyMatch(sanitizeEntry(e.getTarget())::contains))
                    .findFirst()
                    .orElse(null);

            if (entry != null)
            {
                setPriorityEntry(entry);
                return;
            }
        }

        if (config.removeEnabled())
        {
            Collections.reverse(entries);
            reconstructMenuEntries(entries);
        }
    }

    private String sanitizeEntry(String text)
    {
        return Text.removeTags(Text.standardize(text.toLowerCase()));
    }

    private void reconstructMenuEntries(List<MenuEntry> reconstruct)
    {
        MenuEntry[] entries = new MenuEntry[reconstruct.size()];

        int index = 0;
        for (MenuEntry entry : reconstruct)
        {
            entries[index] = entry;
            index++;
        }

        client.setMenuEntries(entries);
    }

    private void setPriorityEntry(MenuEntry priority)
    {
        MenuEntry[] entries = new MenuEntry[2];
        priority.setForceLeftClick(true);

        entries[1] = priority;
        entries[0] = cancel;
        client.setMenuEntries(entries);
    }

    private void loadPriorityEntries()
    {
        String[] lines = config.priorityList().split("\\r?\\n");
        filterEntries.clear();

        for (String line : lines)
        {
            String[] priority = line.split(",");
            filterEntries.put(priority[0].toLowerCase(), priority[1].toLowerCase());
        }
    }

    private void loadHotkeyEntries()
    {
        String[] lines = config.hotkeyList().split("\\r?\\n");
        hotkeyEntries.clear();

        for (String line : lines)
        {
            String[] priority = line.split(",");
            hotkeyEntries.put(priority[0].toLowerCase(), priority[1].toLowerCase());
        }
    }

    private void loadRemovedEntries()
    {
        String[] lines = config.removeList().split("\\r?\\n");
        removed.clear();

        if (lines.length == 1 && lines[0].isBlank())
            return;

        for (String line : lines)
            removed.add(line.toLowerCase());
    }
}