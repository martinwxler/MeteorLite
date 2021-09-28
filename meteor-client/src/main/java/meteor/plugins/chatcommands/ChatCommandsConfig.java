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
package meteor.plugins.chatcommands;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;
import meteor.config.Keybind;

@ConfigGroup("chatcommands")
public interface ChatCommandsConfig extends Config
{
	@ConfigItem(
		position = 0,
		keyName = "priceEnabled",
		name = "Price Command",
		description = "Configures whether the Price command is enabled !price [item]"
	)
	default boolean priceEnabled()
	{
		return true;
	}

	@ConfigItem(
		position = 1,
		keyName = "lvlEnabled",
		name = "Level Command",
		description = "Configures whether the Level command is enabled !lvl [skill]"
	)
	default boolean lvlEnabled()
	{
		return true;
	}

	@ConfigItem(
		position = 2,
		keyName = "clueEnabled",
		name = "Clue Command",
		description = "Configures whether the Clue command is enabled !clues"
	)
	default boolean clueEnabled()
	{
		return true;
	}

	@ConfigItem(
		position = 3,
		keyName = "killcountEnabled",
		name = "Killcount Command",
		description = "Configures whether the Killcount command is enabled !kc [boss]"
	)
	default boolean killcountEnabled()
	{
		return true;
	}

	@ConfigItem(
		position = 4,
		keyName = "qpEnabled",
		name = "QP Command",
		description = "Configures whether the quest point command is enabled !qp"
	)
	default boolean qpEnabled()
	{
		return true;
	}

	@ConfigItem(
		position = 5,
		keyName = "pbEnabled",
		name = "PB Command",
		description = "Configures whether the personal best command is enabled !pb"
	)
	default boolean pbEnabled()
	{
		return true;
	}

	@ConfigItem(
		position = 6,
		keyName = "gcEnabled",
		name = "GC Command",
		description = "Configures whether the Barbarian Assault High gamble count command is enabled !gc"
	)
	default boolean gcEnabled()
	{
		return true;
	}

	@ConfigItem(
		position = 7,
		keyName = "duelsEnabled",
		name = "Duels Command",
		description = "Configures whether the duel arena command is enabled !duels"
	)
	default boolean duelsEnabled()
	{
		return true;
	}

	@ConfigItem(
		position = 8,
		keyName = "bhEnabled",
		name = "BH Command",
		description = "Configures whether the Bounty Hunter - Hunter command is enabled !bh"
	)
	default boolean bhEnabled()
	{
		return true;
	}

	@ConfigItem(
		position = 9,
		keyName = "bhRogueEnabled",
		name = "BH Rogue Command",
		description = "Configures whether the Bounty Hunter - Rogue command is enabled !bhrogue"
	)
	default boolean bhRogueEnabled()
	{
		return true;
	}

	@ConfigItem(
		position = 10,
		keyName = "lmsEnabled",
		name = "LMS Command",
		description = "Configures whether the Last Man Standing command is enabled !lms"
	)
	default boolean lmsEnabled()
	{
		return true;
	}

	@ConfigItem(
		position = 11,
		keyName = "lpEnabled",
		name = "LP Command",
		description = "Configures whether the League Points command is enabled !lp"
	)
	default boolean lpEnabled()
	{
		return true;
	}

	@ConfigItem(
		position = 12,
		keyName = "swEnabled",
		name = "SW Command",
		description = "Configures whether the Soul Wars Zeal command is enabled !sw"
	)
	default boolean swEnabled()
	{
		return true;
	}

	@ConfigItem(
		position = 13,
		keyName = "petsEnabled",
		name = "Pets Command",
		description = "Configures whether the player pet list command is enabled !pets" +
			" Note: Update your pet list by looking at the All Pets tab in the Collection Log"
	)
	default boolean petsEnabled()
	{
		return true;
	}

	@ConfigItem(
		position = 20,
		keyName = "clearSingleWord",
		name = "Clear Single Word",
		description = "Enable hot key to clear single word at a time"
	)
	default Keybind clearSingleWord()
	{
		return new Keybind(KeyEvent.VK_W, InputEvent.CTRL_DOWN_MASK);
	}

	@ConfigItem(
		position = 21,
		keyName = "clearEntireChatBox",
		name = "Clear Chat Box",
		description = "Enable hotkey to clear entire chat box"
	)
	default Keybind clearChatBox()
	{
		return new Keybind(KeyEvent.VK_BACK_SPACE, InputEvent.CTRL_DOWN_MASK);
	}
}
