/*
 * Copyright (c) 2019-2020, ganom <https://github.com/Ganom>
 * Copyright (c) 2019, TomC <https://github.com/tomcylke>
 * All rights reserved.
 * Licensed under GPL3, see LICENSE for the full scope.
 */
package meteor.plugins.oneclick;

import lombok.AllArgsConstructor;
import lombok.Getter;
import static net.runelite.api.ItemID.AIR_RUNE;
import static net.runelite.api.ItemID.EARTH_RUNE;
import static net.runelite.api.ItemID.WATER_RUNE;
import meteor.plugins.oneclick.comparables.ClickCompare;
import meteor.plugins.oneclick.comparables.misc.Blank;
import meteor.plugins.oneclick.comparables.misc.Compost;
import meteor.plugins.oneclick.comparables.misc.Custom;
import meteor.plugins.oneclick.comparables.misc.Healer;
import meteor.plugins.oneclick.comparables.misc.Herbtar;
import meteor.plugins.oneclick.comparables.misc.Seeds;
import meteor.plugins.oneclick.comparables.skilling.Birdhouses;
import meteor.plugins.oneclick.comparables.skilling.Bones;
import meteor.plugins.oneclick.comparables.skilling.DarkEssence;
import meteor.plugins.oneclick.comparables.skilling.Darts;
import meteor.plugins.oneclick.comparables.skilling.Firemaking;
import meteor.plugins.oneclick.comparables.skilling.Karambwans;
import meteor.plugins.oneclick.comparables.skilling.Runes;
import meteor.plugins.oneclick.comparables.skilling.Tiara;

@AllArgsConstructor
@Getter
public enum Types
{
	CUSTOM("Custom", new Custom()),
	COMPOST("Compost", new Compost()),
	DARTS("Darts", new Darts()),
	FIREMAKING("Firemaking", new Firemaking()),
	BIRDHOUSES("Birdhouses", new Birdhouses()),
	HERB_TAR("Herb Tar", new Herbtar()),
	LAVA_RUNES("Lava Runes", new Runes("Earth rune", EARTH_RUNE)),
	STEAM_RUNES("Steam Runes", new Runes("Water rune", WATER_RUNE)),
	SMOKE_RUNES("Smoke Runes", new Runes("Air rune", AIR_RUNE)),
	BONES("Bones", new Bones()),
	KARAMBWANS("Karambwans", new Karambwans()),
	DARK_ESSENCE("Dark Essence", new DarkEssence()),
	SEED_SET("Tithe Farm", new Seeds()),
	TIARA("Tiara", new Tiara()),
	SPELL("Spell Casting", new Blank()),
	BA_HEALER("BA Healer", new Healer()),
	NONE("None", new Blank());

	private final String name;
	private final ClickCompare comparable;

	@Override
	public String toString()
	{
		return getName();
	}
}
