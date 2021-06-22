package net.runelite.mixins;

import net.runelite.api.*;
import net.runelite.api.Node;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.NpcSpawned;
import net.runelite.api.hooks.Callbacks;
import net.runelite.api.hooks.DrawCallbacks;
import net.runelite.api.mixins.*;
import net.runelite.rs.api.*;
import org.sponge.util.Logger;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;


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
    private static int tickCount;

    @Inject
    @Override
    public Callbacks getCallbacks()
    {
        return callbacks;
    }

    @Inject
    @Override
    public DrawCallbacks getDrawCallbacks()
    {
        return drawCallbacks;
    }

    @Inject
    private DrawCallbacks drawCallbacks;

    @Inject
    @Override
    public GameState getGameState()
    {
        return GameState.of(getRSGameState$api());
    }

    @Inject
    public static int viewportColor;

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
    public void setDrawCallbacks(DrawCallbacks drawCallbacks)
    {
        this.drawCallbacks = drawCallbacks;
    }

    @Inject
    @Override
    public Logger getLogger() {
        return logger;
    }

    @Inject
    static int skyboxColor;

    @Inject
    @Override
    public int getSkyboxColor()
    {
        return skyboxColor;
    }

    @Inject
    @Override
    public boolean isGpu()
    {
        return gpu;
    }

    @Inject
    @MethodHook("draw")
    public void draw$api(boolean var1)
    {
        callbacks.clientMainLoop();
    }

    @Inject
    private boolean gpu;

    @Override
    @Inject
    public int getTickCount()
    {
        return tickCount;
    }

    @FieldHook("npcs")
    @Inject
    public static void cachedNPCsChanged(int idx)
    {
        RSNPC[] cachedNPCs = client.getCachedNPCs();
        if (idx < 0 || idx >= cachedNPCs.length)
        {
            return;
        }

        RSNPC npc = cachedNPCs[idx];
        if (npc != null)
        {
            npc.setIndex(idx);

            client.getCallbacks().postDeferred(new NpcSpawned(npc));
        }
    }

    @Inject
    @MethodHook("updateNpcs")
    public static void updateNpcs(boolean var0, RSPacketBuffer var1)
    {
        client.getCallbacks().updateNpcs();
    }

    @Override
    @Inject
    public void setTickCount(int tick)
    {
        tickCount = tick;
    }

    @Inject
    @Override
    public ObjectComposition getObjectDefinition(int objectId)
    {
        assert this.isClientThread() : "getObjectDefinition must be called on client thread";
        return getRSObjectComposition(objectId);
    }

    @Inject
    @Override
    @Nonnull
    public ItemComposition getItemComposition(int id)
    {
        assert this.isClientThread() : "getItemComposition must be called on client thread";
        return getRSItemDefinition(id);
    }

    @Inject
    @Override
    @Nonnull
    public ItemComposition getItemDefinition(int id)
    {
        return getItemComposition(id);
    }

    @Inject
    @Override
    public NPCComposition getNpcDefinition(int id)
    {
        assert this.isClientThread() : "getNpcDefinition must be called on client thread";
        return getRSNpcComposition(id);
    }

    @Inject
    @Override
    public RSTileItem getLastItemDespawn()
    {
        return lastItemDespawn;
    }

    @Inject
    private static RSTileItem lastItemDespawn;

    @Inject
    @Override
    public void setLastItemDespawn(RSTileItem lastItemDespawn)
    {
        Client.lastItemDespawn = lastItemDespawn;
    }

    @Inject
    @Override
    public List<Projectile> getProjectiles()
    {
        List<Projectile> projectiles = new ArrayList<>();
        RSNodeDeque projectileDeque = this.getProjectilesDeque();
        net.runelite.api.Node head = projectileDeque.getSentinel();

        for (net.runelite.api.Node node = head.getNext(); node != head; node = node.getNext())
        {
            projectiles.add((Projectile) node);
        }

        return projectiles;
    }

    @Inject
    @Override
    public List<GraphicsObject> getGraphicsObjects()
    {
        List<GraphicsObject> graphicsObjects = new ArrayList<>();
        RSNodeDeque graphicsObjectDeque = this.getGraphicsObjectDeque();
        net.runelite.api.Node head = graphicsObjectDeque.getSentinel();

        for (Node node = head.getNext(); node != head; node = node.getNext())
        {
            graphicsObjects.add((GraphicsObject) node);
        }

        return graphicsObjects;
    }
}
