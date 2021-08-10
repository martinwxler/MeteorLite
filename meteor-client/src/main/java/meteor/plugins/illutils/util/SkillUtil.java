package meteor.plugins.illutils.util;

public class SkillUtil {
    private static final int[] LEVEL_TO_EXPERIENCE = new int[99];

    static {
        int experience = 0;

        for (int level = 0; level < 99; ++level) {
            int nextLevel = level + 1;
            experience += (int) (nextLevel + 300 * Math.pow(2, nextLevel / 7.));
            LEVEL_TO_EXPERIENCE[level] = experience / 4;
        }
    }

    public static int level(int experience) {
        int level = 1;

        for (int i = 0; i < 98; ++i) {
            if (experience >= LEVEL_TO_EXPERIENCE[i]) {
                level = i + 2;
            }
        }

        return level;
    }

    public static int experience(int level) {
        if (level <= 1) {
            return 0;
        }

        return LEVEL_TO_EXPERIENCE[level - 2];
    }
}
