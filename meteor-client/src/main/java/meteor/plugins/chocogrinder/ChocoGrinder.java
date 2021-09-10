package meteor.plugins.chocogrinder;

import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.entities.TileObjects;
import meteor.plugins.api.game.Game;
import meteor.plugins.api.items.Bank;
import meteor.plugins.api.items.Inventory;
import meteor.plugins.api.packets.Packets;
import net.runelite.api.*;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.WidgetInfo;

@PluginDescriptor(name = "Chocolate grinder", enabledByDefault = false)
public class ChocoGrinder extends Plugin {
    @Provides
    public ChocoGrinderConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(ChocoGrinderConfig.class);
    }
    int gametick =0;
    Item knife;
    @Override
    public void startup(){
        if(client.getGameState()== GameState.LOGGED_IN){
            knife = Inventory.getFirst(	946);
        }
    }
    public void clickPacket(){
        client.setMouseLastPressedMillis(System.currentTimeMillis());
        int mousePressedTime = (int)((client.getMouseLastPressedMillis()) - (client.getClientMouseLastPressedMillis()));
        if (mousePressedTime < 0) {
            return;
        }
        if (mousePressedTime > 32767) {
            mousePressedTime = 32767;
        }
        client.setClientMouseLastPressedMillis(client.getMouseLastPressedMillis());
        int mouseInfo = (mousePressedTime << 1) + (1);
        Packets.queueClickPacket(mouseInfo,0,0);
    }
    public void itemOnItemPacket(int id1,int slot1,int id2, int slot2){
        Packets.queueItemOnItemPacket(id1,slot1,id2,slot2);
    }
    @Subscribe
    public void onGameTick(GameTick event) throws InterruptedException {
        if(client.getGameState()!= GameState.LOGGED_IN){
            return;
        }
        if(knife==null){
            knife = Inventory.getFirst(	946);
            return;
        }
        if(gametick==0){
            if(!Inventory.getAll(1975).isEmpty()) {
                clickPacket();
                Bank.depositInventory();
            }
            if(Bank.getFirst(1973).getQuantity()<27){
                this.toggle();
            }
            clickPacket();
            Bank.withdrawAll(1973,Bank.WithdrawMode.ITEM);
            for (int i = 0; i < 8; i++) {
                //client.setSelectedItemWidget(WidgetInfo.INVENTORY.getPackedId());
                //client.setSelectedItemSlot(knife.getSlot());
                //client.setSelectedItemID(knife.getId());
                clickPacket();
                itemOnItemPacket(1973,26,946,27);
                //client.invokeMenuAction("","",1973, MenuAction.ITEM_USE_ON_WIDGET_ITEM.getId(),26,9764864);
            }
        }
        if(gametick==1){
            for (int i = 0; i < 10; i++) {
                //client.setSelectedItemWidget(WidgetInfo.INVENTORY.getPackedId());
                //client.setSelectedItemSlot(knife.getSlot());
                //client.setSelectedItemID(knife.getId());
                clickPacket();
                itemOnItemPacket(1973,26,946,27);
                //client.invokeMenuAction("","",1973, MenuAction.ITEM_USE_ON_WIDGET_ITEM.getId(),26,9764864);
            }
        }
        if(gametick==2){
            for (int i = 0; i < 9; i++) {
                //client.setSelectedItemWidget(WidgetInfo.INVENTORY.getPackedId());
                //client.setSelectedItemSlot(knife.getSlot());
                //client.setSelectedItemID(knife.getId());
                clickPacket();
                itemOnItemPacket(1973,26,946,27);
                //client.invokeMenuAction("","",1973, MenuAction.ITEM_USE_ON_WIDGET_ITEM.getId(),26,9764864);
            }
            TileObject bank = TileObjects.getNearest(x -> x.hasAction("Bank") && x.hasAction("Collect"));
            if (bank != null) {
                clickPacket();
                bank.interact("Bank");
            }
        }
        gametick++;
        if(gametick==3){
            gametick=0;
        }
    }
}
