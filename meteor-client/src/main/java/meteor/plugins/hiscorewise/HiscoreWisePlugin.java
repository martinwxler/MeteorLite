/*
 * Copyright (c) 2017, Adam <Adam@sigterm.info>
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
package meteor.plugins.hiscorewise;

import static meteor.plugins.hiscore.HiscoreController.INSTANCE;
import static meteor.plugins.hiscore.HiscoreController.searchBox;
import static meteor.ui.MeteorUI.lastButtonPressed;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ObjectArrays;
import com.google.inject.Provides;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import java.io.IOException;
import java.util.EnumSet;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.eventbus.events.ConfigChanged;
import meteor.eventbus.events.ToolbarButtonClicked;
import meteor.menus.MenuManager;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.hiscore.HiscoreConfig;
import meteor.plugins.hiscore.HiscoreController;
import meteor.ui.MeteorUI;
import meteor.ui.components.ToolbarButton;
import meteor.ui.controllers.ToolbarController;
import meteor.util.Text;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.IconID;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.Player;
import net.runelite.api.WorldType;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.events.VarbitChanged;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.http.api.hiscore.HiscoreEndpoint;
import org.apache.commons.lang3.ArrayUtils;

@PluginDescriptor(
	name = "HiScore",
	description = "Enable the HiScore panel and an optional Lookup option on players",
	tags = {"panel", "players"},
	loadWhenOutdated = true
)
public class HiscoreWisePlugin extends Plugin
{
	private static final String LOOKUP = "Lookup";
	private static final String KICK_OPTION = "Kick";
	private static final ImmutableList<String> AFTER_OPTIONS = ImmutableList.of("Message", "Add ignore", "Remove friend", "Delete", KICK_OPTION);
	private static final Pattern BOUNTY_PATTERN = Pattern.compile("<col=ff0000>You've been assigned a target: (.*)</col>");
	private static ToolbarButton navButton;

	static {
		navButton = new ToolbarButton(FontAwesomeIcon.LINE_CHART, "WiseOldMan");
		ToolbarController.addButton(navButton);
	}

	@Inject
	@Nullable
	private Client client;

	@Inject
	private Provider<MenuManager> menuManager;

	@Inject
	private MeteorUI ui;

	private Scene wiseOldManPanel;
	public static Scene skillOverviewPanel;
	{
		try {
			wiseOldManPanel = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader()
					.getResource("meteor/plugins/hiscorewise/hiscorewise.fxml"))), 350, 800);
			skillOverviewPanel = new Scene(FXMLLoader.load(Objects.requireNonNull(getClass().getClassLoader()
					.getResource("meteor/plugins/hiscorewise/skilloverview.fxml"))), 350, 800);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Provides
	public HiscoreConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(HiscoreConfig.class);
	}

	@Subscribe
	public void onToolbarButtonClicked(ToolbarButtonClicked event) {
		if (event.getName().equals("WiseOldMan")) {
			if (lastButtonPressed.equals(event.getName()))
				ui.toggleRightPanel();
			else {
				ui.updateRightPanel(wiseOldManPanel);
				lastButtonPressed = event.getName();
			}
		}
	}

	@Override
	public void startup()
	{

	}

	@Override
	public void shutdown()
	{
		ToolbarController.removeButton(navButton);

		if (client != null)
		{
			menuManager.get().removePlayerMenuItem(LOOKUP);
		}
	}
}
