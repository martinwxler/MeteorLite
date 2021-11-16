package meteor.plugins.banksetups;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.hoot.api.commons.Time;
import dev.hoot.api.game.Game;
import dev.hoot.api.game.GameThread;
import dev.hoot.api.items.Bank;
import dev.hoot.api.items.Equipment;
import dev.hoot.api.items.Inventory;
import dev.hoot.api.packets.DialogPackets;
import dev.hoot.api.packets.ItemPackets;
import dev.hoot.api.packets.MousePackets;
import dev.hoot.api.packets.WidgetPackets;
import dev.hoot.api.widgets.Dialog;
import meteor.eventbus.Subscribe;
import meteor.game.ItemManager;
import meteor.game.chatbox.ChatboxPanelManager;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.ui.overlay.OverlayManager;
import net.runelite.api.Item;
import net.runelite.api.KeyCode;
import net.runelite.api.Point;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.queries.BankItemQuery;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;

import javax.inject.Inject;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@PluginDescriptor(name = "Bank Setups",description = "Allows saving and loading bank setups. Hold shift to remove setups. Not yet working with noted items :(")
public class BankSetupsRefactored extends Plugin {
    @Inject
    private OverlayManager overlayManager;
    @Inject
    private BankSetupsOverlay overlay;
    @Inject
    ItemManager itemManager;
    GsonBuilder builder;
    boolean shouldClose;
    Gson gson;
    Reader reader=null;
    ArrayList<BankSetupObject> bankSetups;

    @Inject
    private ChatboxPanelManager chatboxPanelManager;
    private ExecutorService executor;

    @Override
    public void startup(){
        executor = Executors.newSingleThreadExecutor();
        overlayManager.add(overlay);
        builder = new GsonBuilder();
        gson = builder.create();
        try {
            if (!Files.exists(Paths.get("bankSetups.json"))) {
                Files.createFile(Paths.get("bankSetups.json"));
            }
            reader = Files.newBufferedReader(Paths.get("bankSetups.json"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        bankSetups = gson.fromJson(reader, new TypeToken<ArrayList<BankSetupObject>>() {}.getType());
        if(bankSetups ==null){
            logger.debug("banksetups was null");
            bankSetups = new ArrayList<BankSetupObject>();
        }
    }
    @Override
    public void shutdown(){
        executor.shutdown();
        overlayManager.remove(overlay);
        try {
            reader.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void closeWithdrawX(int timer){
        shouldClose=true;
        Time.sleep(timer);
        shouldClose=false;
    }
    @Subscribe
    public void onMenuOptionClicked(MenuOptionClicked e){
        if(e.getMenuOption().equals("save-current")){
            e.consume();
            List<int[]> invent = getInvent();
            nameInput(invent, null);
        }
        if(e.getMenuOption().equals("save-current+equipment")){
            e.consume();
            List<int[]> invent = getInvent();
            List<int[]> equipment = getEquipment();
            nameInput(invent, equipment);
        }
        if(e.getMenuOption().contains("Gear: ")){
            e.consume();
            BankSetupObject setup = bankSetups.get(e.getId());
            if(setup.equipment!=null){
                int firstFree = getFirstEmptySlot();
                for (int[] equipment : setup.equipment) {
                    Widget item = getBankItemWidget(equipment[0]);
                    if(item==null){
                        int y = itemManager.canonicalize(equipment[0]);
                        item = getBankItemWidget(y);
                        if(item==null) {
                            continue;
                        }
                    }
                    MousePackets.queueClickPacket(0,0);
                    if(equipment[1]!=1){
                        WidgetPackets.widgetAction(item,"Withdraw-X");
                        DialogPackets.sendNumberInput(equipment[1]);
                        MousePackets.queueClickPacket(0,0);
                        ItemPackets.queueBankItemActionPacket(WidgetInfo.BANK_INVENTORY_ITEMS_CONTAINER.getPackedId(), item.getItemId(), firstFree);
                        continue;
                    }
                    WidgetPackets.widgetAction(item,"Withdraw-1");
                    MousePackets.queueClickPacket(0,0);
                    ItemPackets.queueBankItemActionPacket(WidgetInfo.BANK_INVENTORY_ITEMS_CONTAINER.getPackedId(), item.getItemId(), firstFree);
                }
            }
            for (int[] ints : setup.inventory) {
                Widget item = getBankItemWidget(ints[0]);
                if(item==null){
                    continue;
                }
                MousePackets.queueClickPacket(0,0);
                if(ints[1]!=1){
                    WidgetPackets.widgetAction(item,"Withdraw-X");
                    DialogPackets.sendNumberInput(ints[1]);
                    continue;
                }
                WidgetPackets.widgetAction(item,"Withdraw-1");
            }
            int timeout = 0;
            if(setup.equipment!=null){
                timeout+=setup.equipment.size()/5;
            }
            timeout+=setup.inventory.size()/5;
            timeout*=650;
            int finalTimeout = timeout;
            executor.submit(()-> closeWithdrawX(finalTimeout));
        }
        if(e.getMenuOption().contains("Remove: ")){
            e.consume();
            bankSetups.remove(e.getId());
            try {
                FileWriter writer = new FileWriter("bankSetups.json",false);
                writer.write(gson.toJson(bankSetups));
                writer.flush();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void nameInput(List<int[]> invent, List<int[]> equipment) {
        chatboxPanelManager.openTextInput("Setup Name")
                .value("")
                .onDone((input) ->
                {
                    input=input.strip();
                    bankSetups.add(new BankSetupObject(input,invent,equipment));
                    try {
                        FileWriter writer = new FileWriter("bankSetups.json",false);
                        writer.write(gson.toJson(bankSetups));
                        writer.flush();
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                })
                .build();
    }

    @Subscribe
    public void onClientTick(ClientTick e){
        if(Dialog.isEnterInputOpen()&&shouldClose){
            GameThread.invoke(() -> Game.getClient().runScript(138));
        }
        Widget incinerator = client.getWidget(WidgetInfo.BANK_INCINERATOR);
        if (incinerator != null) {
            if (!incinerator.isHidden()) {
                Point p = incinerator.getCanvasLocation();
                int x = p.getX();
                int y = p.getY();
                overlay.setPreferredLocation(new java.awt.Point(x,y-(incinerator.getHeight()+10)));
            }
        }
        Point mousePoint = client.getMouseCanvasPosition();
        if (overlay.getBounds().contains(mousePoint.getX(), mousePoint.getY())) {
            client.insertMenuItem("save-current", "", 10000000, 10000, 0, 0, false);
            client.insertMenuItem("save-current+equipment", "", 10000000, 10000, 0, 0, false);
            for (int i = 0; i < bankSetups.size(); i++) {
                if (client.isKeyPressed(KeyCode.KC_SHIFT)) {
                    client.insertMenuItem("Remove: " + bankSetups.get(i).name, "", 10000000, i, 0, 0, false);
                    continue;
                }
                client.insertMenuItem("Gear: " + bankSetups.get(i).name, "", 10000000, i, 0, 0, false);
            }
        }
    }
    public List<int[]> getInvent(){
        List<Item> items= Inventory.getAll();
        return getItems(items);
    }
    public List<int[]> getEquipment(){
        List<Item> items= Equipment.getAll();
        return getItems(items);
    }
    public List<int[]> getItems(List<Item> items){
        List<int[]> retItems= new ArrayList<int[]>();
        int currItemID=-1;
        int currQuantity=-1;
        for (Item item : items) {
            if(item.getId()!=currItemID){
                if(currItemID!=-1) {
                    retItems.add(new int[]{currItemID,currQuantity});
                }
                currItemID=item.getId();
                currQuantity=item.getQuantity();
                if(items.size()-1==items.indexOf(item)){
                    retItems.add(new int[]{currItemID,currQuantity});
                }
                continue;
            }
            currQuantity++;
            if(items.size()-1==items.indexOf(item)){
                retItems.add(new int[]{currItemID,currQuantity});
            }
        }
        return retItems;
    }
    public Widget getBankItemWidget(int id) {
        WidgetItem bankItem = new BankItemQuery().idEquals(id).result(client).first();
        if (bankItem != null) {
            return bankItem.getWidget();
        } else {
            return null;
        }
    }
    private int getFirstEmptySlot() {
        List<Item> items = Bank.getInventory(x->true);
        int lastIndex=0;
        if(items.size()==0){
            return lastIndex;
        }
        for (Item item : items) {
            if((item.getSlot()-1)>lastIndex){
                logger.debug("returned: {}",lastIndex);
                return lastIndex+1;
            }
            logger.debug("last index: {} updated to: {}",lastIndex,item.getSlot());
            lastIndex=item.getSlot();
        }
        if(lastIndex!=27){
            return lastIndex+1;
        }
        return -1;
    }
}
