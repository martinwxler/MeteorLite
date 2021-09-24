/*
 * Copyright (c) 2018, Lotto <https://github.com/devLotto>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package meteor.plugins.entityhider;

import com.google.inject.Provides;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.eventbus.events.ConfigChanged;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.util.Text;
import net.runelite.api.Client;

import javax.inject.Inject;
import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;

@PluginDescriptor(
	name = "Entity Hider",
	description = "Hide players, NPCs, and/or projectiles",
	tags = {"npcs", "players", "projectiles"},
	enabledByDefault = false
)
public class EntityHiderPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private EntityHiderConfig config;

	@Provides
	public EntityHiderConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(EntityHiderConfig.class);
	}

	@Override
	public void startUp()
	{
		updateConfig();
		Text.fromCSV(config.hideNPCsNames()).forEach(client::addHiddenNpcName);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged event) {
		if (event.getGroup().equals(EntityHiderConfig.GROUP)) {
			updateConfig();

			if (event.getOldValue() == null || event.getNewValue() == null) {
				return;
			}

			if (event.getKey().equals("hideNPCsNames")) {
				List<String> oldList = Text.fromCSV(event.getOldValue());
				List<String> newList = Text.fromCSV(event.getNewValue());

				List<String> removed = oldList.stream().filter(s -> !newList.contains(s)).collect(
						Collectors.toCollection(ArrayList::new));
				List<String> added = newList.stream().filter(s -> !oldList.contains(s)).collect(
						Collectors.toCollection(ArrayList::new));

				removed.forEach(client::removeHiddenNpcName);
				added.forEach(client::addHiddenNpcName);
			}
		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOGGED_IN)
		{
			client.setIsHidingEntities(true);
		}
	}

	public void updateConfig()
	{
		client.setIsHidingEntities(true);

		client.setOthersHidden(config.hideOthers());
		client.setOthersHidden2D(config.hideOthers2D());

		client.setFriendsHidden(config.hideFriends());
		client.setFriendsChatMembersHidden(config.hideFriendsChatMembers());
		client.setIgnoresHidden(config.hideIgnores());

		client.setLocalPlayerHidden(config.hideLocalPlayer());
		client.setLocalPlayerHidden2D(config.hideLocalPlayer2D());

		client.setNPCsHidden(config.hideNPCs());
		client.setNPCsHidden2D(config.hideNPCs2D());

		client.setPetsHidden(config.hidePets());

		client.setAttackersHidden(config.hideAttackers());

		client.setProjectilesHidden(config.hideProjectiles());
	}

	@Override
	public void shutDown()
	{
		client.setIsHidingEntities(false);

		client.setOthersHidden(false);
		client.setOthersHidden2D(false);

		client.setFriendsHidden(false);
		client.setFriendsChatMembersHidden(false);
		client.setIgnoresHidden(false);

		client.setLocalPlayerHidden(false);
		client.setLocalPlayerHidden2D(false);

		client.setNPCsHidden(false);
		client.setNPCsHidden2D(false);

		client.setPetsHidden(false);

		client.setAttackersHidden(false);

		client.setProjectilesHidden(false);
	}
}
