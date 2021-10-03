package meteor.plugins.chocogrinder;

import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.entities.NPCs;
import meteor.plugins.api.items.Bank;
import meteor.plugins.api.items.Inventory;
import meteor.plugins.api.packets.ItemPackets;
import meteor.plugins.api.packets.MousePackets;
import meteor.plugins.api.packets.NPCPackets;
import meteor.plugins.api.packets.WidgetPackets;
import net.runelite.api.GameState;
import net.runelite.api.Item;
import net.runelite.api.NPC;
import net.runelite.api.events.GameTick;

@PluginDescriptor(name = "Chocolate grinder", enabledByDefault = false)
public class ChocoGrinder extends Plugin {
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
        MousePackets.queueClickPacket(mouseInfo,0,0);
    }
    public void itemOnItemPacket(int id1,int slot1,int id2, int slot2){
        ItemPackets.queueItemOnItemPacket(id1,slot1,id2,slot2);
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
                WidgetPackets.queueWidgetAction2Packet(983043,1975,0);
            }
            if(Bank.getFirst(1973).getQuantity()<27){
                this.toggle();
            }
            clickPacket();
            WidgetPackets.queueWidgetActionPacket(786444,1973,0);
            for (int i = 0; i < 8; i++) {
                clickPacket();
                itemOnItemPacket(1973,26,946,27);
            }
        }
        if(gametick==1){
            for (int i = 0; i < 10; i++) {
                clickPacket();
                itemOnItemPacket(1973,26,946,27);
            }
        }
        if(gametick==2){
            for (int i = 0; i < 9; i++) {
                clickPacket();
                itemOnItemPacket(1973,26,946,27);
            }
            NPC bank = NPCs.getNearest("Banker");
            if (bank != null) {
                clickPacket();
                NPCPackets.queueNPCAction3Packet(bank.getIndex(),0);
            }
        }
        gametick++;
        if(gametick==3){
            gametick=0;
        }
    }
}
