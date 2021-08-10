package meteor.plugins.illutils.ui;

import net.runelite.api.widgets.WidgetInfo;
import meteor.plugins.illutils.api.Spells;
import meteor.plugins.illutils.osrs.OSRSUtils;

/**
 * @author kylestev
 */
public class StandardSpellbook {
    private final OSRSUtils game;

    public StandardSpellbook(OSRSUtils game) {
        this.game = game;
    }

    public void castSpell(Spells spell) {
        System.out.println("At castSpell 1");
        System.out.println("Spell info: " + spell.getInfo());
        castSpell(spell.getInfo());
    }

    public void testing(Spells spell) {
        System.out.println("Test method ran");
    }

    public void castSpell(WidgetInfo spellInfo) {
        System.out.println("Casting spell: " + spellInfo.toString());
        game.widget(spellInfo).interact("Cast");
    }

    public void lumbridgeHomeTeleport() {
        // TODO: check response to update timer in Profile
        if (game.localPlayer().position().regionID() == 12850) {
            return;
        }

        castSpell(Spells.LUMBRIDGE_HOME_TELEPORT);
        game.waitUntil(() -> game.localPlayer().position().regionID() == 12850, 30);
        game.tick(3);
    }
}
