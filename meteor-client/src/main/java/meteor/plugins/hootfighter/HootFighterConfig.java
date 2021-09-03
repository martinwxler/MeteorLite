package meteor.plugins.hootfighter;

import meteor.config.*;

@ConfigGroup("hootfighter")
public interface HootFighterConfig extends Config {
	@ConfigSection(
					name = "General",
					description = "General settings",
					position = 0,
					closedByDefault = true
	)
	String general = "General";

	@ConfigSection(
					name = "Health",
					description = "General settings",
					position = 1,
					closedByDefault = true
	)
	String health = "Health";

	@ConfigSection(
					name = "Loot",
					description = "Loot settings",
					position = 2,
					closedByDefault = true
	)
	String loot = "Loot";

	@ConfigSection(
					name = "Prayers",
					description = "Prayers settings",
					position = 3,
					closedByDefault = true
	)
	String prayers = "Prayers";

	@ConfigItem(
					keyName = "monster",
					name = "Monster",
					description = "Monster to kill",
					position = 0,
					section = general
	)
	default String monster() {
		return "Chicken";
	}

	@Range(max = 100)
	@ConfigItem(
					keyName = "attackRange",
					name = "Attack range",
					description = "Monster attack range",
					position = 1,
					section = general
	)
	default int attackRange() {
		return 15;
	}

	@ConfigItem(
					keyName = "bury",
					name = "Bury bones",
					description = "Bury bones",
					position = 2,
					section = general
	)
	default boolean buryBones() {
		return true;
	}

	@ConfigItem(
					keyName = "loots",
					name = "Loot Items",
					description = "Items to loot separated by comma",
					position = 0,
					section = loot
	)
	default String loot() {
		return "Bones,Coins";
	}

	@Range(textInput = true)
	@ConfigItem(
					keyName = "lootValue",
					name = "Loot GP value",
					description = "Items to loot by value, -1 to check by name only",
					position = 1,
					section = loot
	)
	default int lootValue() {
		return -1;
	}

	@ConfigItem(
					keyName = "untradables",
					name = "Loot untradables",
					description = "Loot untradables",
					position = 2,
					section = loot
	)
	default boolean untradables() {
		return true;
	}

	@ConfigItem(
					keyName = "eat",
					name = "Eat food",
					description = "Eat food to heal",
					position = 0,
					section = health
	)
	default boolean eat() {
		return true;
	}

	@Range(max = 100)
	@ConfigItem(
					keyName = "eatHealthPercent",
					name = "Health %",
					description = "Health % to eat at",
					position = 1,
					section = health
	)
	default int healthPercent() {
		return 65;
	}

	@ConfigItem(
					keyName = "foods",
					name = "Food",
					description = "Food to eat, separated by comma",
					position = 0,
					section = health
	)
	default String foods() {
		return "Lobster,Tuna";
	}

	@ConfigItem(
					keyName = "quickPrayer",
					name = "Use Quick Prayers",
					description = "Use Quick Prayers",
					position = 0,
					section = prayers
	)
	default boolean quickPrayer() {
		return false;
	}

	@ConfigItem(
					keyName = "flick",
					name = "Flick",
					description = "One ticks quick prayers",
					position = 1,
					section = prayers
	)
	default boolean flick() {
		return false;
	}

	@ConfigItem(
					keyName = "restore",
					name = "Restore prayer",
					description = "Drinks pots to restore prayer points",
					position =  2,
					section = prayers
	)
	default boolean restore() {
		return false;
	}
}
