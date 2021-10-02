/*
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
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
package meteor.plugins.menuentryswapper;

import java.awt.event.KeyEvent;
import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;
import meteor.config.ConfigSection;
import meteor.config.ModifierlessKeybind;
import meteor.plugins.menuentryswapper.util.BurningAmuletMode;
import meteor.plugins.menuentryswapper.util.BuyMode;
import meteor.plugins.menuentryswapper.util.CombatBraceletMode;
import meteor.plugins.menuentryswapper.util.ConstructionCapeMode;
import meteor.plugins.menuentryswapper.util.ConstructionMode;
import meteor.plugins.menuentryswapper.util.CraftingCapeMode;
import meteor.plugins.menuentryswapper.util.DigsitePendantMode;
import meteor.plugins.menuentryswapper.util.DrakansMedallionMode;
import meteor.plugins.menuentryswapper.util.DuelingRingMode;
import meteor.plugins.menuentryswapper.util.FairyRingMode;
import meteor.plugins.menuentryswapper.util.GEItemCollectMode;
import meteor.plugins.menuentryswapper.util.GamesNecklaceMode;
import meteor.plugins.menuentryswapper.util.GloryMode;
import meteor.plugins.menuentryswapper.util.HouseAdvertisementMode;
import meteor.plugins.menuentryswapper.util.HouseMode;
import meteor.plugins.menuentryswapper.util.MagicCapeMode;
import meteor.plugins.menuentryswapper.util.MaxCapeEquippedMode;
import meteor.plugins.menuentryswapper.util.NecklaceOfPassageMode;
import meteor.plugins.menuentryswapper.util.RingOfWealthMode;
import meteor.plugins.menuentryswapper.util.SellMode;
import meteor.plugins.menuentryswapper.util.ShiftDepositMode;
import meteor.plugins.menuentryswapper.util.ShiftWithdrawMode;
import meteor.plugins.menuentryswapper.util.SkillsNecklaceMode;
import meteor.plugins.menuentryswapper.util.XericsTalismanMode;

@ConfigGroup(MenuEntrySwapperConfig.GROUP)
public interface MenuEntrySwapperConfig extends Config {

  String GROUP = "menuentryswapper";

  @ConfigSection(
      name = "Custom Swaps",
      description = "Configuration for custom Swaps",
      position = 0,
      closedByDefault = true
  )
  String customSwapsSection = "Custom Swaps";

  @ConfigSection(
      name = "Shift Swaps",
      description = "Configuration for shift custom Swaps",
      position = 1,
      closedByDefault = true
  )
  String shiftCustomSwapsSection = "Shift Swaps";

  @ConfigSection(
      name = "Hotkey Swaps",
      description = "Configuration for Hotkey custom Swaps",
      position = 2,
      closedByDefault = true
  )
  String keyCustomSwapsSection = "Hotkey Swaps";

  @ConfigSection(
      name = "Remove Options",
      description = "Configuration for removing swaps",
      position = 3,
      closedByDefault = true
  )
  String removeSwapsSection = "Remove Options";

  @ConfigSection(
      name = "Item Swaps",
      description = "All options that swap item menu entries",
      position = 4,
      closedByDefault = true
  )
  String itemSection = "Item Swaps";

  @ConfigSection(
      name = "NPC Swaps",
      description = "All options that swap NPC menu entries",
      position = 5,
      closedByDefault = true
  )
  String npcSection = "NPC Swaps";

  @ConfigSection(
      name = "Object Swaps",
      description = "All options that swap object menu entries",
      position = 6,
      closedByDefault = true
  )
  String objectSection = "Object Swaps";

  @ConfigSection(
      name = "UI Swaps",
      description = "All options that swap entries in the UI (except Items)",
      position = 7,
      closedByDefault = true
  )
  String uiSection = "UI Swaps";

  @ConfigSection(
      name = "Skilling",
      description = "",
      position = 8,
      keyName = "skillingSection",
      closedByDefault = true
  )
  String skillingSection = "Skilling";

  @ConfigSection(
      name = "Teleportation",
      description = "",
      position = 8,
      keyName = "teleportationSection",
      closedByDefault = true
  )
  String teleportationSection = "Teleportation";

  @ConfigSection(
      name = "Right Click Options",
      description = "",
      position = 9,
      keyName = "rightClickOptionsSection",
      closedByDefault = true
  )
  String rightClickOptionsSection = "Right Click Options";


  @ConfigSection(
      name = "PvM",
      description = "",
      position = 10,
      keyName = "pvmSection",
      closedByDefault = true
  )
  String pvmSection = "PvM";

  @ConfigItem(
      position = -2,
      keyName = "shiftClickCustomization",
      name = "Customizable shift-click",
      description = "Allows customization of shift-clicks on items",
      section = itemSection
  )
  default boolean shiftClickCustomization() {
    return true;
  }

  @ConfigItem(
      keyName = "swapAdmire",
      name = "Admire",
      description = "Swap Admire with Teleport, Spellbook and Perks (max cape) for mounted skill capes.",
      section = objectSection
  )
  default boolean swapAdmire() {
    return true;
  }

  @ConfigItem(
      keyName = "swapAssignment",
      name = "Assignment",
      description = "Swap Talk-to with Assignment for Slayer Masters. This will take priority over swapping Trade.",
      section = npcSection
  )
  default boolean swapAssignment() {
    return true;
  }

  @ConfigItem(
      keyName = "swapBanker",
      name = "Bank",
      description = "Swap Talk-to with Bank on Bank NPC Example: Banker",
      section = npcSection
  )
  default boolean swapBank() {
    return true;
  }

  @ConfigItem(
      keyName = "swapBirdhouseEmpty",
      name = "Birdhouse",
      description = "Swap Interact with Empty for birdhouses on Fossil Island",
      section = objectSection
  )
  default boolean swapBirdhouseEmpty() {
    return true;
  }

  @ConfigItem(
      keyName = "swapBones",
      name = "Bury",
      description = "Swap Bury with Use on Bones",
      section = itemSection
  )
  default boolean swapBones() {
    return false;
  }

  @ConfigItem(
      keyName = "swapHerbs",
      name = "Clean",
      description = "Swap Clean with Use on Herbs",
      section = itemSection
  )
  default boolean swapHerbs() {
    return false;
  }

  @ConfigItem(
      keyName = "swapBattlestaves",
      name = "Battlestaff",
      description = "Swap Wield with Use on Battlestaves without orbs",
      section = itemSection
  )
  default boolean swapBattlestaves() {
    return false;
  }

  @ConfigItem(
      keyName = "swapPrayerBook",
      name = "Recite-Prayer",
      description = "Swap Read with Recite-prayer on the Prayer Book from The Great Brain Robbery quest",
      section = itemSection
  )
  default boolean swapPrayerBook() {
    return false;
  }

  @ConfigItem(
      keyName = "swapContract",
      name = "Contract",
      description = "Swap Talk-to with Contract on Guildmaster Jane",
      section = npcSection
  )
  default boolean swapContract() {
    return true;
  }

  @ConfigItem(
      keyName = "swapChase",
      name = "Chase",
      description = "Allows to left click your cat to chase",
      section = npcSection
  )
  default boolean swapChase() {
    return true;
  }

  @ConfigItem(
      keyName = "claimSlime",
      name = "Claim Slime",
      description = "Swap Talk-to with Claim Slime from Morytania diaries",
      section = npcSection
  )
  default boolean claimSlime() {
    return true;
  }

  @ConfigItem(
      keyName = "swapDarkMage",
      name = "Repairs",
      description = "Swap Talk-to with Repairs for Dark Mage",
      section = npcSection
  )
  default boolean swapDarkMage() {
    return true;
  }

  @ConfigItem(
      keyName = "swapCaptainKhaled",
      name = "Task",
      description = "Swap Talk-to with Task for Captain Khaled in Port Piscarilius",
      section = npcSection
  )
  default boolean swapCaptainKhaled() {
    return false;
  }

  @ConfigItem(
      keyName = "swapDecant",
      name = "Decant",
      description = "Swap Talk-to with Decant for Bob Barter and Murky Matt at the Grand Exchange.",
      section = npcSection
  )
  default boolean swapDecant() {
    return false;
  }

  @ConfigItem(
      keyName = "swapExchange",
      name = "Exchange",
      description = "Swap Talk-to with Exchange on NPC Example: Grand Exchange Clerk, Tool Leprechaun, Void Knight",
      section = npcSection
  )
  default boolean swapExchange() {
    return true;
  }

  @ConfigItem(
      keyName = "swapFairyRing",
      name = "Fairy ring",
      description = "Swap Zanaris with Last-destination or Configure on Fairy rings",
      section = objectSection
  )
  default FairyRingMode swapFairyRing() {
    return FairyRingMode.LAST_DESTINATION;
  }

  @ConfigItem(
      keyName = "swapHardWoodGrove",
      name = "Hardwood Grove Quick-Pay",
      description = "Swap Quick-Pay(100) at the Hardwood Grove",
      section = objectSection
  )
  default boolean swapHardWoodGrove() {
    return true;
  }

  @ConfigItem(
      keyName = "swapHardWoodGroveParcel",
      name = "Hardwood Grove Send-Parcel",
      description = "Swap Send-Parcel at the Hardwood Grove",
      section = npcSection
  )
  default boolean swapHardWoodGroveParcel() {
    return true;
  }

  @ConfigItem(
      keyName = "swapHarpoon",
      name = "Harpoon",
      description = "Swap Cage, Big Net with Harpoon on Fishing spot",
      section = objectSection
  )
  default boolean swapHarpoon() {
    return false;
  }

  @ConfigItem(
      keyName = "swapBait",
      name = "Bait",
      description = "Swap Lure, Small Net with Bait on Fishing spot",
      section = objectSection
  )
  default boolean swapBait() {
    return false;
  }

  @ConfigItem(
      keyName = "swapHelp",
      name = "Help",
      description = "Swap Talk-to with Help on Arceuus library customers",
      section = npcSection
  )
  default boolean swapHelp() {
    return true;
  }

  @ConfigItem(
      keyName = "swapHomePortal",
      name = "Home",
      description = "Swap Enter with Home or Build or Friend's house on Portal",
      section = objectSection
  )
  default HouseMode swapHomePortal() {
    return HouseMode.HOME;
  }

  @ConfigItem(
      keyName = "swapHouseAdvertisement",
      name = "House Advertisement",
      description = "Swap View with Add-House or Visit-Last on House Advertisement board",
      section = objectSection
  )
  default HouseAdvertisementMode swapHouseAdvertisement() {
    return HouseAdvertisementMode.VIEW;
  }

  @ConfigItem(
      keyName = "swapPay",
      name = "Pay",
      description = "Swap Talk-to with Pay on NPC Example: Elstan, Heskel, Fayeth",
      section = npcSection
  )
  default boolean swapPay() {
    return true;
  }

  @ConfigItem(
      keyName = "swapJewelleryBox",
      name = "Jewellery Box",
      description = "Swap Teleport Menu with previous destination on Jewellery Box",
      section = objectSection
  )
  default boolean swapJewelleryBox() {
    return false;
  }

  @ConfigItem(
      keyName = "swapPortalNexus",
      name = "Portal Nexus",
      description = "Swap Teleport options with Teleport Menu on the Portal Nexus",
      section = objectSection
  )
  default boolean swapPortalNexus() {
    return false;
  }

  @ConfigItem(
      keyName = "swapPrivate",
      name = "Private",
      description = "Swap Shared with Private on the Chambers of Xeric storage units.",
      section = objectSection
  )
  default boolean swapPrivate() {
    return false;
  }

  @ConfigItem(
      keyName = "swapPick",
      name = "Pick",
      description = "Swap Pick with Pick-lots of the Gourd tree in the Chambers of Xeric",
      section = objectSection
  )
  default boolean swapPick() {
    return false;
  }

  @ConfigItem(
      keyName = "swapQuick",
      name = "Quick Pass/Open/Start/Travel",
      description = "Swap Pass with Quick-Pass, Open with Quick-Open, Ring with Quick-Start and Talk-to with Quick-Travel",
      section = objectSection
  )
  default boolean swapQuick() {
    return true;
  }

  @ConfigItem(
      keyName = "swapBoxTrap",
      name = "Reset",
      description = "Swap Check with Reset on box trap",
      section = objectSection
  )
  default boolean swapBoxTrap() {
    return true;
  }

  @ConfigItem(
      keyName = "swapTeleportItem",
      name = "Teleport item",
      description = "Swap Wear, Wield with Rub, Teleport on teleport item Example: Amulet of glory, Explorer's ring, Chronicle",
      section = itemSection
  )
  default boolean swapTeleportItem() {
    return false;
  }

  @ConfigItem(
      keyName = "swapTeleToPoh",
      name = "Tele to POH",
      description = "Swap Wear with Tele to POH on the construction cape",
      section = itemSection
  )
  default boolean swapTeleToPoh() {
    return false;
  }

  @ConfigItem(
      keyName = "swapKaramjaGloves",
      name = "Karamja Gloves",
      description = "Swap Wear with the Gem Mine or Duradel teleport on the Karamja Gloves 3 and 4",
      section = itemSection
  )
  default KaramjaGlovesMode swapKaramjaGlovesMode() {
    return KaramjaGlovesMode.WEAR;
  }

  @ConfigItem(
      keyName = "swapArdougneCloak",
      name = "Ardougne Cloak",
      description = "Swap Wear with Monastery Teleport or Farm Teleport on the Ardougne cloak.",
      section = itemSection
  )
  default ArdougneCloakMode swapArdougneCloakMode() {
    return ArdougneCloakMode.WEAR;
  }

  @ConfigItem(
      keyName = "swapRadasBlessing",
      name = "Rada's Blessing",
      description = "Swap Equip with the Woodland or Mount Karuulm teleport on Rada's Blessing.",
      section = itemSection
  )
  default RadasBlessingMode swapRadasBlessingMode() {
    return RadasBlessingMode.EQUIP;
  }

  @ConfigItem(
      keyName = "swapMorytaniaLegs",
      name = "Morytania Legs",
      description = "Swap Wear with the Ectofunctus or Burgh de Rott teleport on the Morytania Legs.",
      section = itemSection
  )
  default MorytaniaLegsMode swapMorytaniaLegsMode() {
    return MorytaniaLegsMode.WEAR;
  }

  @ConfigItem(
      keyName = "swapDesertAmulet",
      name = "Desert Amulet",
      description = "Swap Wear with the Nardah or Kalphite Cave teleport on Desert Amulet 4.",
      section = itemSection
  )
  default DesertAmuletMode swapDesertAmuletMode() {
    return DesertAmuletMode.WEAR;
  }

  @ConfigItem(
      keyName = "swapAbyssTeleport",
      name = "Teleport to Abyss",
      description = "Swap Talk-to with Teleport for the Mage of Zamorak",
      section = npcSection
  )
  default boolean swapAbyssTeleport() {
    return true;
  }

  @ConfigItem(
      keyName = "swapTrade",
      name = "Trade",
      description = "Swap Talk-to with Trade on NPC Example: Shop keeper, Shop assistant",
      section = npcSection
  )
  default boolean swapTrade() {
    return true;
  }

  @ConfigItem(
      keyName = "swapTravel",
      name = "Travel",
      description = "Swap Talk-to with Travel, Take-boat, Pay-fare, Charter on NPC Example: Squire, Monk of Entrana, Customs officer, Trader Crewmember",
      section = npcSection
  )
  default boolean swapTravel() {
    return true;
  }

  @ConfigItem(
      keyName = "swapEnchant",
      name = "Enchant",
      description = "Swap Talk-to with Enchant for Eluned",
      section = npcSection
  )
  default boolean swapEnchant() {
    return true;
  }

  @ConfigItem(
      keyName = "swapTeleportSpell",
      name = "Shift-click teleport spells",
      description = "Swap teleport spells that have a second destination on shift",
      section = uiSection
  )
  default boolean swapTeleportSpell() {
    return false;
  }

  @ConfigItem(
      keyName = "swapStartMinigame",
      name = "Pyramid Plunder Start-minigame",
      description = "Swap Talk-to with Start-minigame at the Guardian Mummy",
      section = npcSection
  )
  default boolean swapStartMinigame() {
    return true;
  }

  @ConfigItem(
      keyName = "swapQuickleave",
      name = "Quick-Leave",
      description = "Swap Leave Tomb with Quick-Leave at Pyramid Plunder",
      section = objectSection
  )
  default boolean swapQuickLeave() {
    return false;
  }

  @ConfigItem(
      keyName = "swapGEItemCollect",
      name = "GE Item Collect",
      description = "Swap Collect-notes, Collect-items, or Bank options from GE offer",
      section = uiSection
  )
  default GEItemCollectMode swapGEItemCollect() {
    return GEItemCollectMode.DEFAULT;
  }

  @ConfigItem(
      keyName = "swapGEAbort",
      name = "GE Abort",
      description = "Swap abort offer on Grand Exchange offers when shift-clicking"
      ,
      section = uiSection
  )
  default boolean swapGEAbort() {
    return false;
  }

  @ConfigItem(
      keyName = "swapNpcContact",
      name = "NPC Contact",
      description = "Swap NPC Contact with last contacted NPC when shift-clicking",
      section = uiSection
  )
  default boolean swapNpcContact() {
    return false;
  }

  @ConfigItem(
      keyName = "bankWithdrawShiftClick",
      name = "Bank Withdraw Shift-Click",
      description = "Swaps the behavior of shift-click when withdrawing from bank.",
      section = itemSection
  )
  default ShiftWithdrawMode bankWithdrawShiftClick() {
    return ShiftWithdrawMode.OFF;
  }

  @ConfigItem(
      keyName = "bankDepositShiftClick",
      name = "Bank Deposit Shift-Click",
      description = "Swaps the behavior of shift-click when depositing to bank.",
      section = itemSection
  )
  default ShiftDepositMode bankDepositShiftClick() {
    return ShiftDepositMode.OFF;
  }

  @ConfigItem(
      keyName = "shopBuy",
      name = "Shop Buy Shift-Click",
      description = "Swaps the Buy options with Value on items in shops when shift is held.",
      section = uiSection
  )
  default BuyMode shopBuy() {
    return BuyMode.OFF;
  }

  @ConfigItem(
      keyName = "shopSell",
      name = "Shop Sell Shift-Click",
      description = "Swaps the Sell options with Value on items in your inventory when selling to shops when shift is held.",
      section = uiSection
  )
  default SellMode shopSell() {
    return SellMode.OFF;
  }

  @ConfigItem(
      keyName = "swapEssenceMineTeleport",
      name = "Essence Mine Teleport",
      description = "Swaps Talk-To with Teleport for NPCs which teleport you to the essence mine",
      section = npcSection
  )
  default boolean swapEssenceMineTeleport() {
    return false;
  }

  @ConfigItem(
      keyName = "swapNets",
      name = "Nets",
      description = "Swap Talk-to with Nets on Annette",
      section = npcSection
  )
  default boolean swapNets() {
    return true;
  }

  @ConfigItem(
      keyName = "swapGauntlet",
      name = "Corrupted Gauntlet",
      description = "Swap Enter with Enter-corrupted when entering The Gauntlet",
      section = objectSection
  )
  default boolean swapGauntlet() {
    return false;
  }

  @ConfigItem(
      keyName = "swapTan",
      name = "Tan",
      description = "Swap Tan 1 with Tan All",
      section = uiSection
  )
  default boolean swapTan() {
    return false;
  }

  @ConfigItem(
      keyName = "swapCollectMiscellania",
      name = "Miscellania",
      description = "Swap Talk-to with Collect for Advisor Ghrim",
      section = npcSection
  )
  default boolean swapCollectMiscellania() {
    return false;
  }

  @ConfigItem(
      keyName = "swapDepositItems",
      name = "Deposit Items",
      description = "Swap Talk-to with Deposit-items",
      section = npcSection
  )
  default boolean swapDepositItems() {
    return false;
  }

  @ConfigItem(
      keyName = "swapRockCake",
      name = "Dwarven rock cake",
      description = "Swap Eat with Guzzle on the Dwarven rock cake",
      section = itemSection
  )
  default boolean swapRockCake() {
    return false;
  }

  @ConfigItem(
      keyName = "swapRowboatDive",
      name = "Fossil Island Rowboat Dive",
      description = "Swap Travel with Dive on the rowboat found on the small island north-east of Fossil Island",
      section = objectSection
  )
  default boolean swapRowboatDive() {
    return false;
  }

  @ConfigItem(
      keyName = "swapTemporossLeave",
      name = "Tempoross Leave",
      description = "Swap Talk-to with Leave after subduing Tempoross",
      section = npcSection
  )
  default boolean swapTemporossLeave() {
    return false;
  }

  @ConfigItem(
      keyName = "swapReleaseLizards",
      name = "Release Lizards",
      description = "Swap Release with Wield for lizards",
      section = npcSection
  )
  default boolean swapLizardRelease() {
    return true;
  }

  //------------------------------------------------------------//
  // Skilling
  //------------------------------------------------------------//

  @ConfigItem(
      keyName = "getEasyConstruction",
      name = "Easy Construction",
      description = "Makes 'Remove'/'Build' the default option for listed items.",
      position = 0,
      section = skillingSection
  )
  default boolean getEasyConstruction() {
    return true;
  }

  @ConfigItem(
      keyName = "getConstructionMode",
      name = "EZ Construction Type",
      description = "",
      position = 1,
      section = skillingSection,
      hidden = true,
      unhide = "getEasyConstruction"
  )
  default ConstructionMode getConstructionMode() {
    return ConstructionMode.LARDER;
  }

  @ConfigItem(
      keyName = "swapPickpocket",
      name = "Pickpocket",
      description = "Swap Talk-to with Pickpocket on NPC Example: Man, Woman",
      position = 2,
      section = skillingSection
  )
  default boolean swapPickpocket() {
    return false;
  }

  @ConfigItem(
      keyName = "hideLootImpJars",
      name = "Hide 'Loot' Impling Jars",
      description = "Hides the 'Loot' option from impling jars if you have the type of clue.",
      position = 3,
      section = skillingSection
  )
  default boolean hideLootImpJars()
  {
    return false;
  }

  //------------------------------------------------------------//
  // Teleportation
  //------------------------------------------------------------//

  @ConfigItem(
      keyName = "gloryMode",
      name = "Amulet of Glory",
      description = "",
      position = 0,
      section = teleportationSection
  )
  default GloryMode getGloryMode() {
    return GloryMode.OFF;
  }

  @ConfigItem(
      keyName = "burningamuletmode",
      name = "Burning Amulet",
      description = "",
      position = 1,
      section = teleportationSection
  )
  default BurningAmuletMode getBurningAmuletMode() {
    return BurningAmuletMode.OFF;
  }

  @ConfigItem(
      keyName = "combatbraceletmode",
      name = "Combat Bracelet",
      description = "",
      position = 2,
      section = teleportationSection
  )
  default CombatBraceletMode getCombatBraceletMode() {
    return CombatBraceletMode.OFF;
  }

  @ConfigItem(
      keyName = "digsitependantmode",
      name = "Digsite Pendant",
      description = "",
      position = 3,
      section = teleportationSection
  )
  default DigsitePendantMode getDigsitePendantMode() {
    return DigsitePendantMode.OFF;
  }

  @ConfigItem(
      keyName = "drakansmedallion",
      name = "Drakans Medallion",
      description = "",
      position = 4,
      section = teleportationSection
  )
  default DrakansMedallionMode getDrakansMedallionMode()
  {
    return DrakansMedallionMode.OFF;
  }

  @ConfigItem(
      keyName = "gamesNecklaceMode",
      name = "Games Necklace",
      description = "",
      position = 5,
      section = teleportationSection
  )
  default GamesNecklaceMode getGamesNecklaceMode() {
    return GamesNecklaceMode.OFF;
  }

  @ConfigItem(
      keyName = "necklaceofpassagemode",
      name = "Necklace of Passage",
      description = "",
      position = 6,
      section = teleportationSection
  )
  default NecklaceOfPassageMode getNecklaceofPassageMode() {
    return NecklaceOfPassageMode.OFF;
  }

  @ConfigItem(
      keyName = "duelingRingMode",
      name = "Ring of Dueling",
      description = "",
      position = 7,
      section = teleportationSection
  )
  default DuelingRingMode getDuelingRingMode() {
    return DuelingRingMode.OFF;
  }

  @ConfigItem(
      keyName = "ringofwealthmode",
      name = "Ring of Wealth",
      description = "",
      position = 8,
      section = teleportationSection
  )
  default RingOfWealthMode getRingofWealthMode() {
    return RingOfWealthMode.OFF;
  }

  @ConfigItem(
      keyName = "skillsnecklacemode",
      name = "Skills Necklace",
      description = "",
      position = 9,
      section = teleportationSection
  )
  default SkillsNecklaceMode getSkillsNecklaceMode() {
    return SkillsNecklaceMode.OFF;
  }

  @ConfigItem(
      keyName = "xericstalismanmode",
      name = "Xerics Talisman",
      description = "",
      position = 10,
      section = teleportationSection
  )
  default XericsTalismanMode getXericsTalismanMode() {
    return XericsTalismanMode.OFF;
  }

  @ConfigItem(
      keyName = "swapConstructionCape",
      name = "Construction Cape",
      description = "Swap the left click option with 'Tele to POH' on a Construction Cape.",
      position = 11,
      section = teleportationSection
  )
  default ConstructionCapeMode getConstructionCapeMode() {
    return ConstructionCapeMode.OFF;
  }

  @ConfigItem(
      keyName = "swapCraftingCape",
      name = "Crafting Cape",
      description = "Swap the left click option with 'teleport' on a Crafting Cape.",
      position = 12,
      section = teleportationSection
  )
  default CraftingCapeMode getCraftingCapeMode() {
    return CraftingCapeMode.OFF;
  }

  @ConfigItem(
      keyName = "magicCapeMode",
      name = "Magic Cape",
      description = "Swap the left click option with 'spellbook' on a Magic Cape.",
      position = 13,
      section = teleportationSection
  )
  default MagicCapeMode getMagicCapeMode() {
    return MagicCapeMode.OFF;
  }

  @ConfigItem(
      keyName = "swapMaxCapeEquipped",
      name = "Max Cape",
      description = "Swap the left click 'remove' option with another on a worn Max Cape.",
      position = 14,
      section = teleportationSection
  )
  default MaxCapeEquippedMode getMaxCapeEquippedMode() {
    return MaxCapeEquippedMode.OFF;
  }

  //------------------------------------------------------------//
  // Right Click Options
  //------------------------------------------------------------//

  @ConfigItem(
      keyName = "hideTradeWith",
      name = "Hide 'Trade With'",
      description = "Hides the 'Trade with' option from the right click menu.",
      position = 0,
      section = rightClickOptionsSection
  )
  default boolean hideTradeWith() {
    return false;
  }

  @ConfigItem(
      keyName = "hideEmpty",
      name = "Hide 'Empty'",
      description = "Hides the 'Empty' option from the right click menu for potions.",
      position = 1,
      section = rightClickOptionsSection
  )
  default boolean hideEmpty() {
    return false;
  }

  @ConfigItem(
      keyName = "hideExamine",
      name = "Hide 'Examine'",
      description = "Hides the 'Examine' option from the right click menu.",
      position = 2,
      section = rightClickOptionsSection
  )
  default boolean hideExamine()
  {
    return false;
  }

  @ConfigItem(
      keyName = "hideDestroy",
      name = "Hide 'Destroy' Rune Pouch",
      description = "Hides the 'Destroy' option from rune pouch.",
      position = 3,
      section = rightClickOptionsSection
  )
  default boolean hideDestroy()
  {
    return false;
  }

  //------------------------------------------------------------//
  // PVM
  //------------------------------------------------------------//

  @ConfigItem(
      keyName = "hideAttack",
      name = "Hide Attack On Dead NPCs",
      description = "Hides the 'Attack' option on dead npcs.",
      position = 0,
      section = pvmSection
  )

  default boolean hideAttack() {
    return false;
  }

  @ConfigItem(
      keyName = "hideAttackIgnoredNPCs",
      name = "Ignored NPCs",
      description = "NPCs that should not be hidden from being attacked, separated by a comma",
      position = 1,
      section = pvmSection,
      hidden = true,
      unhide = "hideAttack"
  )
  default String hideAttackIgnoredNPCs() {
    return "";
  }

  @ConfigItem(
      keyName = "hideCastRaids",
      name = "Hide Cast On Players In Raids",
      description = "Hides the cast option for players while in raids.",
      position = 2,
      section = pvmSection
  )

  default boolean hideCastRaids() {
    return true;
  }

  @ConfigItem(
      keyName = "hideCastIgnoredSpells",
      name = "Ignored Spells",
      description = "Spells that should not be hidden from being cast, separated by a comma",
      position = 3,
      section = pvmSection,
      hidden = true,
      unhide = "hideCastRaids"
  )
  default String hideCastIgnoredSpells() {
    return "cure other, energy transfer, heal other, vengeance other";
  }

  @ConfigItem(
      keyName = "hideCastThralls",
      name = "Hide Cast On Thralls",
      description = "Hides the cast option on thralls.",
      position = 4,
      section = pvmSection
  )
  default boolean hideCastThralls()
  {
    return true;
  }


  //------------------------------------------------------------//
  // Custom swaps
  //------------------------------------------------------------//

  @ConfigItem(
      name = "Custom Swaps Toggle",
      keyName = "customSwapsToggle",
      description = "Toggles the use of the Custom Swaps",
      section = customSwapsSection,
      position = 3
  )
  default boolean customSwapsToggle() {
    return false;
  }

  @ConfigItem(
      name = "Custom Swaps",
      keyName = "customSwapsStr",
      description = "",
      section = customSwapsSection,
      position = 4
  )
  default String customSwapsString() {
    return "";
  }

  @ConfigItem(
      name = "Bank Swaps",
      keyName = "bankCustomSwapsStr",
      description = "",
      section = customSwapsSection,
      position = 5
  )
  default String bankCustomSwapsString() {
    return "";
  }

  @ConfigItem(
      name = "Shift Swaps Toggle",
      keyName = "shiftCustomSwapsToggle",
      description = "Toggles the use of the Shift Custom Swaps",
      section = shiftCustomSwapsSection,
      position = 6
  )
  default boolean shiftCustomSwapsToggle() {
    return false;
  }

  @ConfigItem(
      name = "Shift Swaps",
      keyName = "shiftCustomSwapsStr",
      description = "",
      section = shiftCustomSwapsSection,
      position = 7
  )
  default String shiftCustomSwapsString() {
    return "";
  }

  @ConfigItem(
      name = "Shift Bank Swaps",
      keyName = "bankShiftCustomSwapsStr",
      description = "",
      section = shiftCustomSwapsSection,
      position = 8
  )
  default String bankShiftCustomSwapsString() {
    return "";
  }

  @ConfigItem(
      name = "Hotkey Swaps Toggle",
      keyName = "keyCustomSwapsToggle",
      description = "Toggles the use of the Hotkey Custom Swaps",
      section = keyCustomSwapsSection,
      position = 1
  )
  default boolean keyCustomSwapsToggle() {
    return false;
  }

  @ConfigItem(
      keyName = "hotkey",
      name = "Set Hotkey",
      description = "Binds the key to hold to enable this section",
      section = keyCustomSwapsSection,
      position = 2
  )
  default ModifierlessKeybind hotkey() {
    return new ModifierlessKeybind(KeyEvent.VK_UNDEFINED, 0);
  }

  @ConfigItem(
      name = "Hotkey Swaps",
      keyName = "keyCustomSwapsStr",
      description = "",
      section = keyCustomSwapsSection,
      position = 3
  )
  default String keyCustomSwapsString() {
    return "";
  }

  @ConfigItem(
      name = "Hotkey Bank Swaps",
      keyName = "bankKeyCustomSwapsStr",
      description = "",
      section = keyCustomSwapsSection,
      position = 4
  )
  default String bankKeyCustomSwapsString() {
    return "";
  }

  @ConfigItem(
      name = "Remove Options Toggle",
      keyName = "removeOptionsToggle",
      description = "Toggles the use of the removing options",
      section = removeSwapsSection,
      position = 9
  )
  default boolean removeOptionsToggle() {
    return false;
  }

  @ConfigItem(
      name = "Remove Options",
      keyName = "removeOptionsStr",
      description = "",
      section = removeSwapsSection,
      position = 10
  )
  default String removeOptionsString() {
    return "";
  }

  enum ArdougneCloakMode {
    WEAR,
    MONASTERY,
    FARM,
  }

  enum KaramjaGlovesMode {
    WEAR,
    GEM_MINE,
    DURADEL,
  }

  enum MorytaniaLegsMode {
    WEAR,
    ECTOFUNTUS,
    BURGH_DE_ROTT;

    @Override
    public String toString() {
      switch (this) {
        case BURGH_DE_ROTT:
          return "Burgh de Rott";
        default:
          return name();
      }
    }
  }

  enum RadasBlessingMode {
    EQUIP,
    KOUREND_WOODLAND,
    MOUNT_KARUULM,
  }

  enum DesertAmuletMode {
    WEAR,
    NARDAH,
    KALPHITE_CAVE,
  }
}
