package meteor.plugins.illutils.api;

import meteor.plugins.illutils.osrs.OSRSUtils;

import java.util.Arrays;

public class SpellBook {

    private static final int SPELLBOOK_VARBIT = 4070;

    public enum Type {
        STANDARD(0),
        ANCIENT(1),
        LUNAR(2),
        ARCEUUS(3);

        private int varbit;

        Type(int varbit) {
            this.varbit = varbit;
        }

        public boolean isInUse(OSRSUtils game) {
            return game.varb(SPELLBOOK_VARBIT) == varbit;
        }
    }

    public static Type getCurrentSpellBook(OSRSUtils game) {
        return Arrays.stream(Type.values()).filter(t -> t.isInUse(game)).findAny().orElse(null);
    }

}