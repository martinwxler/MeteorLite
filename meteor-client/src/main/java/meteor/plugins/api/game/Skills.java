package meteor.plugins.api.game;

import net.runelite.api.Client;
import net.runelite.api.Skill;

import javax.inject.Inject;

public class Skills {

    public static int getBoostedLevel(Skill skill) {
        return Game.getClient().getBoostedSkillLevel(skill);
    }

    public static int getLevel(Skill skill) {
        return Game.getClient().getRealSkillLevel(skill);
    }

    public static int getExperience(Skill skill) {
        return Game.getClient().getSkillExperience(skill);
    }
}
