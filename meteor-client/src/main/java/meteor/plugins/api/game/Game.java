package meteor.plugins.api.game;

import net.runelite.api.Client;
import net.runelite.api.GameState;

import javax.inject.Inject;

public class Game {
    @Inject
    private static Client client;

    public static boolean isLoggedIn() {
        return getState() == GameState.LOGGED_IN || getState() == GameState.LOADING;
    }

    public static GameState getState() {
        return client.getGameState();
    }
}
