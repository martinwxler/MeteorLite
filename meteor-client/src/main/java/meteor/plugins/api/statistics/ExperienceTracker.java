package meteor.plugins.api.statistics;

import lombok.Value;
import meteor.plugins.api.game.Skills;
import net.runelite.api.Skill;

@Value
public class ExperienceTracker {
	Skill skill;
	int startExp;
	int startLevel;

	public int getExperienceGained() {
		return Skills.getExperience(skill) - startExp;
	}

	public int getLevelsGained() {
		return Skills.getLevel(skill) - startLevel;
	}
}
