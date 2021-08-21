package meteor.plugins.oneclickdropper;

import com.google.inject.Provides;
import meteor.eventbus.events.ConfigChanged;
import meteor.util.Text;
import net.runelite.api.*;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.ItemContainerChanged;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.queries.InventoryWidgetItemQuery;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import meteor.callback.ClientThread;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;

import javax.inject.Inject;
import java.util.*;

@PluginDescriptor(
      name = "One Click Dropper",
      description = "Drop items without having to click on them",
        enabledByDefault = false
)

public class OneClickDropperPlugin extends Plugin
{

   @Inject
   private Client client;

   @Inject
   private ClientThread clientThread;

   @Inject
   private OneClickDropperConfig config;

   @Provides
   public OneClickDropperConfig getConfig(ConfigManager configManager)
   {
      return configManager.getConfig(OneClickDropperConfig.class);
   }

   boolean dropping = false;
   int previousSize = 0;
   List<WidgetItem> dropList;
   ListIterator<WidgetItem> dropListIterator;

   Set<Integer> dropIDs = Set.of(19669,11328,11332,11330);
   HashMap<Integer,Integer> dropOrder;

   @Override
   public void startup()
   {
      config();
   }

   @Override
   public void shutdown()
   {
      dropListIterator = null;
      dropList = null;
      dropOrder = null;
      dropping = false;
   }

   @Subscribe
   private void onConfigChanged(ConfigChanged event)
   {
      if(event.getGroup().equals("oneclickdropper"))
      {
         config();
         dropping = false;
         clientThread.execute(this::createDropList);
      }
   }

   @Subscribe
   private void onMenuOptionClicked(MenuOptionClicked event)
   {
      if(dropping && event.getMenuOption().equals("One Click Drop"))
      {
         if(dropListIterator.hasNext())
         {
            event.setMenuEntry(createDropMenuEntry(dropListIterator.next()));
         }
         if(!dropListIterator.hasNext())
         {
            dropping = false;
         }
      }
   }

   @Subscribe
   private void onClientTick(ClientTick event)
   {
      if(client.getLocalPlayer() == null
              || client.getGameState() != GameState.LOGGED_IN
              || client.getItemContainer(InventoryID.BANK) != null
              || client.getWidget(WidgetInfo.DEPOSIT_BOX_INVENTORY_ITEMS_CONTAINER) != null
      )
         return;

      if(dropping)
      {
         client.insertMenuItem(
                 "One Click Drop",
                 "",
                 MenuAction.UNKNOWN.getId(),
                 0,
                 0,
                 0,
                 true);
      }
   }

   @Subscribe
   private void onItemContainerChanged(ItemContainerChanged event)
   {
      if (event.getContainerId() != InventoryID.INVENTORY.getId())
      {
         return;
      }
      createDropList();
   }

   //has to be called on client thread
   private void createDropList()
   {
      int size = client.getWidget(WidgetInfo.INVENTORY).getWidgetItems().size();
      if (size == 28)
      {
         updateDropList();
      }
      else if (size >= previousSize && !config.requireFullInventory())
      {
         updateDropList();
      }
      previousSize = size;
   }

   private void updateDropList()
   {
      dropList = getItems(dropIDs);
      if( dropList == null || dropList.size() == 0)
      {
         dropping = false;
         return;
      }
      dropListIterator = dropList.listIterator();
      dropping = true;
   }

   public List<WidgetItem> getItems(Collection<Integer> ids)
   {
      Widget inventoryWidget = client.getWidget(WidgetInfo.INVENTORY);
      List<WidgetItem> matchedItems = new ArrayList<>();

      if (inventoryWidget != null)
      {
         for(int i = 0; i <= 27; i++)
         {
            int index = config.customDrop() ? dropOrder.get(i) : i;
            WidgetItem item = new  InventoryWidgetItemQuery()
                    .idEquals(ids)
                    .indexEquals(index)
                    .result(client)
                    .first();
            if (item != null)
            {
               matchedItems.add(item);
            }
         }
         return matchedItems;
      }
      return null;
   }

   private MenuEntry createDropMenuEntry(WidgetItem item)
   {
      return new MenuEntry("Drop", "Item", item.getId(), MenuAction.ITEM_FIFTH_OPTION.getId(), item.getSlot(), 9764864, false);
   }

   private void config()
   {
      dropIDs = new HashSet<>();
      dropOrder = new HashMap<>();
      for (String s : Text.COMMA_SPLITTER.split(config.itemIDs()))
      {
         try
         {
            dropIDs.add(Integer.parseInt(s));
         }
         catch (NumberFormatException ignored)
         {
         }
      }

      int order = 0;
      Set<Integer> uniquieIndexes = new HashSet<>();
      for (String s : Text.COMMA_SPLITTER.split(config.dropOrder()))
      {
         try
         {
            int inventoryIndex = Integer.parseInt(s)-1;

            //check if inx is out of bounds or already used
            if(inventoryIndex > 27 || inventoryIndex < 0 || uniquieIndexes.contains(inventoryIndex))
            {
               continue;
            }
            uniquieIndexes.add(inventoryIndex);
            dropOrder.put(order,inventoryIndex);
            order++;
         }
         catch (Exception ignored)
         {
         }
      }
   }
}
