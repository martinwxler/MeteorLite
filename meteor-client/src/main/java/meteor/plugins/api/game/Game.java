package meteor.plugins.api.game;

import meteor.plugins.api.widgets.Widgets;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;

import javax.inject.Inject;

public class Game {
    @Inject
    private static Client client;

    public static Client getClient() {
        return client;
    }

    public static boolean isLoggedIn() {
        return getState() == GameState.LOGGED_IN || getState() == GameState.LOADING;
    }

    public static boolean isOnLoginScreen() {
        return getState() == GameState.LOGIN_SCREEN || getState() == GameState.LOGIN_SCREEN_AUTHENTICATOR;
    }

    public static GameState getState() {
        return client.getGameState();
    }

    public static int getWildyLevel() {
        Widget wildyLevelWidget = Widgets.get(WidgetInfo.PVP_WILDERNESS_LEVEL);
        if (wildyLevelWidget == null || GameThread.invokeLater(wildyLevelWidget::isHidden)) {
            return 0;
        }

        return Integer.parseInt(wildyLevelWidget.getText().replace("Level: ", ""));
    }
}
