package net.runelite.mixins;

import net.runelite.api.GameState;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.hooks.Callbacks;
import net.runelite.api.mixins.*;
import net.runelite.rs.api.RSClient;
import org.sponge.util.Logger;


@Mixin(RSClient.class)
public abstract class Client implements RSClient{

    @Shadow("client")
    public static RSClient client;

    @Inject
    private static RSClient clientInstance;

    @Inject
    @javax.inject.Inject
    private Callbacks callbacks;

    @Inject
    @Override
    public Callbacks getCallbacks()
    {
        return callbacks;
    }

    @Inject
    @Override
    public GameState getGameState()
    {
        return GameState.of(getRSGameState$api());
    }


    @Inject
    public static Logger logger = new Logger("injected-clients");
    @Inject
    @FieldHook("gameState")
    public static void onGameStateChanged(int idx)
    {
        clientInstance = client;
        GameStateChanged gameStateChanged = new GameStateChanged();
        gameStateChanged.setGameState(GameState.of(client.getRSGameState$api()));
        client.getCallbacks().post(gameStateChanged);
    }

    @Inject
    @Override
    public Logger getLogger() {
        return logger;
    }
}
