package meteor.plugins.api.magic;

import net.runelite.api.ItemID;

public enum Rune {
    AIR(ItemID.AIR_RUNE, "Air", "Smoke", "Mist", "Dust"),
    EARTH(ItemID.EARTH_RUNE, "Earth", "Lava", "Mud", "Dust"),
    FIRE(ItemID.FIRE_RUNE, "Fire", "Lava", "Smoke", "Steam"),
    WATER(ItemID.WATER_RUNE, "Water", "Mud", "Steam", "Mist"),
    MIND(ItemID.MIND_RUNE, "Mind"),
    BODY(ItemID.BODY_RUNE, "Body"),
    COSMIC(ItemID.COSMIC_RUNE, "Cosmic"),
    CHAOS(ItemID.CHAOS_RUNE, "Chaos"),
    NATURE(ItemID.NATURE_RUNE, "Nature"),
    LAW(ItemID.LAW_RUNE, "Law"),
    DEATH(ItemID.DEATH_RUNE, "Death"),
    ASTRAL(ItemID.ASTRAL_RUNE, "Astral"),
    BLOOD(ItemID.BLOOD_RUNE, "Blood"),
    SOUL(ItemID.SOUL_RUNE, "Soul"),
    WRATH(ItemID.WRATH_RUNE, "Wrath")
    ;

    private final int runeId;
    private final String[] runeNames;

    Rune(int runeId, String... runeNames) {
        this.runeId = runeId;
        this.runeNames = runeNames;
    }

    public String[] getRuneNames() {
        return runeNames;
    }

    public int getRuneId() {
        return runeId;
    }
}
