package meteor.plugins.oneclickcustom;

import com.google.inject.Provides;
import meteor.eventbus.events.ConfigChanged;
import net.runelite.api.*;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.queries.GameObjectQuery;
import net.runelite.api.queries.NPCQuery;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.config.ConfigManager;
import meteor.util.GameEventManager;

import javax.annotation.Nullable;
import javax.inject.Inject;
import java.util.*;

@PluginDescriptor(
        name = "One Click Custom",
        description = "Custom one click for game objects. Fishing spots count as NPC's so input NPC ID.",
        tags = {"one click","custom"},
        enabledByDefault = false
)

public class OneClickCustomPlugin extends Plugin
{

    @Inject
    private Client client;

    @Inject
    GameEventManager gameEventManager;

    @Inject
    private OneClickCustomConfig config;

    @Inject
    private ConfigManager configManager;

    @Provides
    public OneClickCustomConfig getConfig(ConfigManager configManager)
    {
        return configManager.getConfig(OneClickCustomConfig.class);
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged event)
    {
        if(event.getGroup().equals("oneclickcustom"))
        {
            gameEventManager.simulateGameEvents(this);
        }
    }


    @Subscribe
    public void onMenuOptionClicked(MenuOptionClicked event)
    {
        if(event.getMenuOption().equals("<col=00ff00>One Click Custom"))
        {
            handleClick(event);
        }
    }

    @Subscribe
    private void onClientTick(ClientTick event)
    {

        if (config.fishingSpot() && checkForNPCObject()==null)
        {
            return;
        }

        if (config.gameObjectID()!=0 &! config.fishingSpot() && checkforGameObject()==null )
        {
            return;
        }

        if (getInventQuantity(client)==28 && config.InventoryFull())
        {
            return;
        }

        if (config.gameObjectID()==0)
        {
            return;
        }

        if(client.getLocalPlayer() == null || client.getGameState() != GameState.LOGGED_IN) return;
        String text;
        {
            text =  "<col=00ff00>One Click Custom";
        }

        client.insertMenuItem(
                text,
                "",
                MenuAction.UNKNOWN.getId(),
                0,
                0,
                0,
                true);
    }

    private void handleClick(MenuOptionClicked event)
    {
        if (getInventQuantity(client)==28 && config.InventoryFull())
        {
            return;
        }
        event.setMenuEntry(setCustomMenuEntry());
    }

    private MenuEntry setCustomMenuEntry()
    {
        if (config.gameObjectID()!=0 && config.fishingSpot())
        {
            NPC customNPCObject = checkForNPCObject();
            return new MenuEntry(
                    "option",
                    "target",
                    customNPCObject.getIndex(),
                    MenuAction.NPC_FIRST_OPTION.getId(),
                    getNPCLocation(customNPCObject).getX(),
                    getNPCLocation(customNPCObject).getY(),
                    true);
        }

        if (config.gameObjectID()!=0)
        {
            GameObject customGameObject = checkforGameObject();
            return new MenuEntry(
                    "option",
                    "target",
                    config.gameObjectID(),
                    MenuAction.GAME_OBJECT_FIRST_OPTION.getId(),
                    getLocation(customGameObject).getX(),
                    getLocation(customGameObject).getY(),
                    true);
        }
        return null;
    }

    private Point getLocation(TileObject tileObject)
    {
        if (tileObject instanceof GameObject)
        {

            return ((GameObject) tileObject).getSceneMinLocation();
        }
        else
        {
            return new Point(tileObject.getLocalLocation().getSceneX(), tileObject.getLocalLocation().getSceneY());
        }
    }

    private Point getNPCLocation(NPC npc)
    {
        return new Point(npc.getLocalLocation().getSceneX(),npc.getLocalLocation().getSceneY());
    }

    private NPC checkForNPCObject()
    {
        return new NPCQuery()
                .idEquals(config.gameObjectID())
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }

    private GameObject checkforGameObject()
    {
        return new GameObjectQuery()
                .idEquals(config.gameObjectID())
                .result(client)
                .nearestTo(client.getLocalPlayer());
    }


    private void Print(String string)
    {
        client.addChatMessage(ChatMessageType.GAMEMESSAGE,"",string,"");
    }

    @Nullable
    public static Collection<WidgetItem> getInventoryItems(Client client) {
        Widget inventory = client.getWidget(WidgetInfo.INVENTORY);

        if (inventory == null) {
            return null;
        }

        return new ArrayList<>(inventory.getWidgetItems());
    }


    public static int getInventQuantity(Client client) {
        Collection<WidgetItem> inventoryItems = getInventoryItems(client);

        if (inventoryItems == null) {
            return 0;
        }

        int count = 0;

        for (WidgetItem inventoryItem : inventoryItems) {
            if (!(String.valueOf(inventoryItem).contains("id=-1"))){
                count += 1;
            }
        }
        return count;
    }

}