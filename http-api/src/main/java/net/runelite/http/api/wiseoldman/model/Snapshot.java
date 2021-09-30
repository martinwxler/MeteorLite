package net.runelite.http.api.wiseoldman.model;

import net.runelite.api.Skill;

public class Snapshot {
  public String createdAt;
  public String importedAt;
  public SkillSnapshot overall;
  public SkillSnapshot attack;
  public SkillSnapshot defence;
  public SkillSnapshot strength;
  public SkillSnapshot hitpoints;
  public SkillSnapshot ranged;
  public SkillSnapshot prayer;
  public SkillSnapshot magic;
  public SkillSnapshot cooking;
  public SkillSnapshot woodcutting;
  public SkillSnapshot fletching;
  public SkillSnapshot fishing;
  public SkillSnapshot firemaking;
  public SkillSnapshot crafting;
  public SkillSnapshot smithing;
  public SkillSnapshot mining;
  public SkillSnapshot herblore;
  public SkillSnapshot agility;
  public SkillSnapshot thieving;
  public SkillSnapshot slayer;
  public SkillSnapshot farming;
  public SkillSnapshot runecrafting;
  public SkillSnapshot hunter;
  public SkillSnapshot construction;
  public int totalLevel = 0;
  public ScoreSnapshot leaguePoints;
  public ScoreSnapshot bountyHunterHunter;
  public ScoreSnapshot bountyHunterTogue;
  public ScoreSnapshot clueScrollsAll;
  public ScoreSnapshot clueScrollsEasy;
  public ScoreSnapshot clueScrollsMed;
  public ScoreSnapshot clueScrollsHard;
  public ScoreSnapshot clueScrollsElite;
  public ScoreSnapshot clueScrollsMaster;
  public ScoreSnapshot lastManStanding;
  public ScoreSnapshot soulWarsZeal;
  public KillSnapshot abyssalSire;
  public KillSnapshot alchemicalHydra;
  public KillSnapshot barrowsChests;
  public KillSnapshot bryophyta;
  public KillSnapshot callisto;
  public KillSnapshot cerberus;
  public KillSnapshot cox;
  public KillSnapshot coxChallenge;
  public KillSnapshot chaosElemental;
  public KillSnapshot chaosFanatic;
  public KillSnapshot commanderZilyana;
  public KillSnapshot corp;
  public KillSnapshot crazyArch;
  public KillSnapshot dagannothPrime;
  public KillSnapshot dagannothRex;
  public KillSnapshot dagannothSupreme;
  public KillSnapshot generalGraardor;
  public KillSnapshot giantMole;
  public KillSnapshot grotesqueGuardians;
  public KillSnapshot hespori;
  public KillSnapshot kalphiteQueen;
  public KillSnapshot kbd;
  public KillSnapshot kraken;
  public KillSnapshot kreearra;
  public KillSnapshot krilTsutsaroth;
  public KillSnapshot mimic;
  public KillSnapshot nightmare;
  public KillSnapshot phosanisNightmare;
  public KillSnapshot obor;
  public KillSnapshot sarachnis;
  public KillSnapshot scorpia;
  public KillSnapshot skotizo;
  public KillSnapshot tempoross;
  public KillSnapshot theGauntlet;
  public KillSnapshot theCorruptedGauntlet;
  public KillSnapshot tob;
  public KillSnapshot tobHard;
  public KillSnapshot thermie;
  public KillSnapshot zuk;
  public KillSnapshot jad;
  public KillSnapshot venenatis;
  public KillSnapshot vetion;
  public KillSnapshot vorkath;
  public KillSnapshot wintertodt;
  public KillSnapshot zalcano;
  public KillSnapshot zulrah;
  public EfficiencySnapshot efficiency;

  public SkillSnapshot getSnapshotFromSkill(Skill skill) {
    if (skill == Skill.ATTACK)
      return attack;
    else if (skill == Skill.DEFENCE)
      return defence;
    else if (skill == Skill.STRENGTH)
      return strength;
    else if (skill == Skill.HITPOINTS)
      return hitpoints;
    else if (skill == Skill.RANGED)
      return ranged;
    else if (skill == Skill.PRAYER)
      return prayer;
    else if (skill == Skill.MAGIC)
      return magic;
    else if (skill == Skill.COOKING)
      return cooking;
    else if (skill == Skill.WOODCUTTING)
      return woodcutting;
    else if (skill == Skill.FLETCHING)
      return fletching;
    else if (skill == Skill.FISHING)
      return fishing;
    else if (skill == Skill.FIREMAKING)
      return firemaking;
    else if (skill == Skill.CRAFTING)
      return crafting;
    else if (skill == Skill.SMITHING)
      return smithing;
    else if (skill == Skill.MINING)
      return mining;
    else if (skill == Skill.HERBLORE)
      return herblore;
    else if (skill == Skill.AGILITY)
      return agility;
    else if (skill == Skill.THIEVING)
      return thieving;
    else if (skill == Skill.SLAYER)
      return slayer;
    else if (skill == Skill.FARMING)
      return farming;
    else if (skill == Skill.RUNECRAFT)
      return runecrafting;
    else if (skill == Skill.HUNTER)
      return hunter;
    else if (skill == Skill.CONSTRUCTION)
      return construction;
    else if (skill == Skill.OVERALL)
      return overall;
    return null;
  }
}
