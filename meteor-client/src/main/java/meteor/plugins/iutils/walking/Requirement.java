package meteor.plugins.iutils.walking;

import meteor.plugins.iutils.game.Game;

public interface Requirement {

    Game game();

    boolean satisfies();
}
