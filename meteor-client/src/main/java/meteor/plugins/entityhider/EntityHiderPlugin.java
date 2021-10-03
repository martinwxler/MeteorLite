/*
 * Copyright (c) 2018, Lotto <https://github.com/devLotto>
 * Copyright (c) 2019, ThatGamerBlue <thatgamerblue@gmail.com>
 * Copyright (c) 2021, BickusDiggus <https://github.com/BickusDiggus>
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

import java.util.stream.Collectors;
import javax.inject.Inject;
import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.eventbus.events.ConfigChanged;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.util.WildcardMatcher;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.NPC;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.NpcDespawned;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import net.runelite.api.util.Text;

@PluginDescriptor(
    name = "Entity Hider",
    enabledByDefault = false,
    description = "Hide dead NPCs animations",
    tags = {"npcs"}
)
public class EntityHiderPlugin extends Plugin {

  @Inject
  private Client client;

  @Inject
  private EntityHiderConfig config;

  @Provides
  public EntityHiderConfig getConfig(ConfigManager configManager) {
    return configManager.getConfig(EntityHiderConfig.class);
  }

  private ArrayList<Integer> hiddenIndices;
  private ArrayList<Integer> animationHiddenIndices;
  private Set<String> hideNPCsOnDeathName;
  private Set<Integer> hideNPCsOnDeathID;
  private Set<Integer> hideNPCsOnAnimationID;
  private Set<String> blacklistName;
  private Set<Integer> blacklistID;

  @Override
  public void startup() {
    client.setIsHidingEntities(true);
    //client.setDeadNPCsHidden(true);
    hiddenIndices = new ArrayList<>();
    animationHiddenIndices = new ArrayList<>();
    updateConfig();
    meteor.util.Text.fromCSV(config.hideNPCsNames()).forEach(client::addHiddenNpcName);
  }

  @Subscribe
  public void onGameStateChanged(GameStateChanged event) {
    if (event.getGameState() == GameState.LOGGED_IN) {
      client.setIsHidingEntities(true);
      clearHiddenNpcs();
    }
  }

  @Override
  public void shutdown() {
    client.setIsHidingEntities(false);
    //client.setDeadNPCsHidden(false);
    clearHiddenNpcs();
    hiddenIndices = null;
    animationHiddenIndices = null;
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


  @Subscribe
  public void onClientTick(ClientTick event) {
    for (NPC npc : client.getNpcs()) {
      if (npc == null) {
        continue;
      }

      if ((config.hideDeadNPCs() && npc.getHealthRatio() == 0 && npc.getName() != null
          && !matchWildCards(blacklistName, net.runelite.api.util.Text
          .standardize(npc.getName())) && !blacklistID.contains(npc.getId()))
          || (npc.getHealthRatio() == 0 && npc.getName() != null && matchWildCards(
          hideNPCsOnDeathName, net.runelite.api.util.Text
              .standardize(npc.getName())))
          || (npc.getHealthRatio() == 0 && hideNPCsOnDeathID.contains(npc.getId()))
          || (hideNPCsOnAnimationID.contains(npc.getAnimation()))) {
        if (!hiddenIndices.contains(npc.getIndex())) {
          setHiddenNpc(npc, true);
        }
      }

      if (animationHiddenIndices.contains(npc.getIndex()) && !hideNPCsOnAnimationID
          .contains(npc.getAnimation())) {
        if (hiddenIndices.contains(npc.getIndex())) {
          setHiddenNpc(npc, false);
        }
      }
    }
  }

  @Subscribe
  public void onNpcDespawned(NpcDespawned event) {
    if (hiddenIndices.contains(event.getNpc().getIndex())) {
      setHiddenNpc(event.getNpc(), false);
    }
  }

  @Subscribe
  public void onConfigChanged(ConfigChanged event) {
    if (!event.getGroup().equals("entityhiderextended")) {
      return;
    }
    client.setIsHidingEntities(true);
    updateConfig();

    if (event.getOldValue() == null || event.getNewValue() == null) {
      return;
    }

    if (event.getKey().equals("hideNPCsNames")) {
      List<String> oldList = meteor.util.Text.fromCSV(event.getOldValue());
      List<String> newList = meteor.util.Text.fromCSV(event.getNewValue());

      List<String> removed = oldList.stream().filter(s -> !newList.contains(s)).collect(
          Collectors.toCollection(ArrayList::new));
      List<String> added = newList.stream().filter(s -> !oldList.contains(s)).collect(
          Collectors.toCollection(ArrayList::new));

      removed.forEach(client::removeHiddenNpcName);
      added.forEach(client::addHiddenNpcName);
    }
  }

  public void updateConfig() {
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

    hideNPCsOnDeathName = new HashSet<>();
    hideNPCsOnDeathID = new HashSet<>();
    hideNPCsOnAnimationID = new HashSet<>();
    blacklistID = new HashSet<>();
    blacklistName = new HashSet<>();

    for (String s : net.runelite.api.util.Text.COMMA_SPLITTER
        .split(config.hideNPCsOnDeathName().toLowerCase())) {
      hideNPCsOnDeathName.add(s);
    }
    for (String s : net.runelite.api.util.Text.COMMA_SPLITTER.split(config.hideNPCsOnDeathID())) {
      try {
        hideNPCsOnDeathID.add(Integer.parseInt(s));
      } catch (NumberFormatException ignored) {
      }

    }
    for (String s : net.runelite.api.util.Text.COMMA_SPLITTER
        .split(config.hideNPCsOnAnimationID())) {
      try {
        hideNPCsOnAnimationID.add(Integer.parseInt(s));
      } catch (NumberFormatException ignored) {
      }

    }
    for (String s : net.runelite.api.util.Text.COMMA_SPLITTER
        .split(config.blacklistDeadNpcsName().toLowerCase())) {
      blacklistName.add(s);
    }
    for (String s : Text.COMMA_SPLITTER.split(config.blacklistDeadNpcsID())) {
      try {
        blacklistID.add(Integer.parseInt(s));
      } catch (NumberFormatException ignored) {
      }

    }
  }

  private void setHiddenNpc(NPC npc, boolean hidden) {

    List<Integer> newHiddenNpcIndicesList = client.getHiddenNpcIndices();
    if (hidden) {
      newHiddenNpcIndicesList.add(npc.getIndex());
      hiddenIndices.add(npc.getIndex());
      if (hideNPCsOnAnimationID.contains(npc.getAnimation())) {
        animationHiddenIndices.add(npc.getIndex());
      }
    } else {
      if (newHiddenNpcIndicesList.contains(npc.getIndex())) {
        newHiddenNpcIndicesList.remove((Integer) npc.getIndex());
        hiddenIndices.remove((Integer) npc.getIndex());
        animationHiddenIndices.remove((Integer) npc.getIndex());
      }
    }
    client.setHiddenNpcIndices(newHiddenNpcIndicesList);

  }

  private void clearHiddenNpcs() {
    if (!hiddenIndices.isEmpty()) {
      List<Integer> newHiddenNpcIndicesList = client.getHiddenNpcIndices();
      newHiddenNpcIndicesList.removeAll(hiddenIndices);
      client.setHiddenNpcIndices(newHiddenNpcIndicesList);
      hiddenIndices.clear();
      animationHiddenIndices.clear();
    }
  }

  private boolean matchWildCards(Set<String> items, String pattern) {
    boolean matched = false;
    for (final String item : items) {
      matched = WildcardMatcher.matches(item, pattern);
      if (matched) {
        break;
      }
    }
    return matched;
  }
}