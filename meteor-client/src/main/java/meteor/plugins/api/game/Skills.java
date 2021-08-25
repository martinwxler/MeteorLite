package meteor.plugins.api.game;

import net.runelite.api.Client;
import net.runelite.api.Skill;

import javax.inject.Inject;

public class Skills {
    @Inject
    private static Client client;

    public static int getBoostedLevel(Skill skill) {
        return client.getBoostedSkillLevel(skill);
    }

    public static int getLevel(Skill skill) {
        return client.getRealSkillLevel(skill);
    }

    public static int getExperience(Skill skill) {
        return client.getSkillExperience(skill);
    }
}
