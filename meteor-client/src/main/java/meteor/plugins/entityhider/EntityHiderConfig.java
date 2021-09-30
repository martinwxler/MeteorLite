/*
 * Copyright (c) 2018, Lotto <https://github.com/devLotto>
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


import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;
import meteor.config.ConfigSection;

@ConfigGroup("entityhiderextended")
public interface EntityHiderConfig extends Config {

  @ConfigSection(
      position = 0,
      name = "Custom Hider",
      description = "Hide specfic NPCs base of name or death animation",
      closedByDefault = true
  )
  String customhider = "Custom Hider";

  @ConfigItem(
      position = 0,
      keyName = "hideNPCsNames",
      name = "Hide Specific NPCs",
      description = "Configures whether or not other players are hidden",
      section = customhider
  )
  default String hideNPCsNames() {
    return "";
  }

  @ConfigItem(
      position = 1,
      keyName = "hidePlayers",
      name = "Hide Others",
      description = "Configures whether or not other players are hidden"
  )
  default boolean hideOthers() {
    return true;
  }

  @ConfigItem(
      position = 2,
      keyName = "hidePlayers2D",
      name = "Hide Others 2D",
      description = "Configures whether or not other players 2D elements are hidden"
  )
  default boolean hideOthers2D() {
    return true;
  }

  @ConfigItem(
      position = 3,
      keyName = "hideFriends",
      name = "Hide Friends",
      description = "Configures whether or not friends are hidden"
  )
  default boolean hideFriends() {
    return false;
  }

  @ConfigItem(
      position = 4,
      keyName = "hideClanMates",
      name = "Hide Friends Chat members",
      description = "Configures whether or not friends chat members are hidden"
  )
  default boolean hideFriendsChatMembers() {
    return false;
  }

  @ConfigItem(
      position = 5,
      keyName = "hideIgnores",
      name = "Hide Ignores",
      description = "Configures whether or not ignored players are hidden"
  )
  default boolean hideIgnores() {
    return false;
  }

  @ConfigItem(
      position = 6,
      keyName = "hideLocalPlayer",
      name = "Hide Local Player",
      description = "Configures whether or not the local player is hidden"
  )
  default boolean hideLocalPlayer() {
    return false;
  }

  @ConfigItem(
      position = 7,
      keyName = "hideLocalPlayer2D",
      name = "Hide Local Player 2D",
      description = "Configures whether or not the local player's 2D elements are hidden"
  )
  default boolean hideLocalPlayer2D() {
    return false;
  }

  @ConfigItem(
      position = 8,
      keyName = "hideNPCs",
      name = "Hide NPCs",
      description = "Configures whether or not NPCs are hidden"
  )
  default boolean hideNPCs() {
    return false;
  }

  @ConfigItem(
      position = 9,
      keyName = "hideNPCs2D",
      name = "Hide NPCs 2D",
      description = "Configures whether or not NPCs 2D elements are hidden"
  )
  default boolean hideNPCs2D() {
    return false;
  }

  @ConfigItem(
      position = 10,
      keyName = "hidePets",
      name = "Hide Pets",
      description = "Configures whether or not other player pets are hidden"
  )
  default boolean hidePets() {
    return false;
  }

  @ConfigItem(
      position = 11,
      keyName = "hideAttackers",
      name = "Hide Attackers",
      description = "Configures whether or not NPCs/players attacking you are hidden"
  )
  default boolean hideAttackers() {
    return false;
  }

  @ConfigItem(
      position = 12,
      keyName = "hideProjectiles",
      name = "Hide Projectiles",
      description = "Configures whether or not projectiles are hidden"
  )
  default boolean hideProjectiles() {
    return false;
  }

  ////////////////////////////////////////////////////////////////////////////////////////////
  ///////////////////////////////////Entity Hider Extended///////////////////////////////////
  ///////////////////////////////////////////////////////////////////////////////////////////

  @ConfigItem(
      position = 0,
      keyName = "hideDeadNPCs",
      name = "Hide ALL Dead NPCs",
      description = "Configures whether or not NPCs that just died are hidden"
  )
  default boolean hideDeadNPCs() {
    return false;
  }

  @ConfigItem(
      position = 1,
      keyName = "hideNPCsOnDeathName",
      name = "Hide NPCs On Death (Name)",
      description = "Configures which NPCs by Name to hide when they die",
      section = customhider
  )
  default String hideNPCsOnDeathName() {
    return "";
  }

  @ConfigItem(
      position = 2,
      keyName = "hideNPCsOnDeathID",
      name = "Hide NPCs On Death (ID)",
      description = "Configures which NPCs by ID to hide when they die",
      section = customhider
  )
  default String hideNPCsOnDeathID() {
    return "";
  }

  @ConfigItem(
      position = 3,
      keyName = "hideNPCsOnAnimationID",
      name = "Hide NPCs On Animation (ID)",
      description = "Configures which NPCs by Animation ID to hide",
      section = customhider
  )
  default String hideNPCsOnAnimationID() {
    return "";
  }

  @ConfigItem(
      position = 4,
      keyName = "blacklistDeadNpcsName",
      name = "Hide ALL Dead Blacklist (Name)",
      description = "Configures which NPCs by Name NOT to hide when they die",
      section = customhider
  )
  default String blacklistDeadNpcsName() {
    return "";
  }

  @ConfigItem(
      position = 5,
      keyName = "blacklistDeadNpcsID",
      name = "Hide ALL Dead Blacklist (ID)",
      description = "Configures which NPCs by ID NOT to hide when they die",
      section = customhider
  )
  default String blacklistDeadNpcsID() {
    return "";
  }
}
