package meteor.plugins.menuentryswapper.util;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableSet;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import javax.inject.Inject;
import java.util.List;
import meteor.callback.ClientThread;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.menuentryswapper.MenuEntrySwapperConfig;
import meteor.util.Text;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.InventoryID;
import net.runelite.api.Item;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.NPC;
import net.runelite.api.events.ClientTick;

public class ImpJar {
  private final MenuEntrySwapperConfig config;

  private static final Set<String> TOB_CHEST_TARGETS = ImmutableSet.of(
      "stamina potion(4)",
      "prayer potion(4)",
      "saradomin brew(4)",
      "super restore(4)",
      "mushroom potato",
      "shark",
      "sea turtle",
      "manta ray"
  );

  @Inject
  private Client client;

  @Inject
  private ClientThread clientThread;

  @Inject
  private ConfigManager configManager;

  @Inject
  public ImpJar(MenuEntrySwapperConfig config) {
    this.config = config;
  }

  private final ArrayListMultimap<String, Integer> optionIndexes = ArrayListMultimap.create();
  private List<String> bankItemNames = new ArrayList<>();
  

  private void swapMenuEntry(int index, MenuEntry menuEntry)
  {
    final String option = Text.removeTags(menuEntry.getOption()).toLowerCase();
    final String target = Text.removeTags(menuEntry.getTarget()).toLowerCase();


    if (config.swapImps() && target.contains("impling"))
    {

      if (client.getItemContainer(InventoryID.BANK) != null)
      {
        bankItemNames = new ArrayList<>();
        for (Item i : Objects.requireNonNull(client.getItemContainer(InventoryID.BANK)).getItems())
        {
          bankItemNames.add(client.getItemDefinition((i.getId())).getName());
        }
      }
      List<String> invItemNames = new ArrayList<>();
      switch (target)
      {
        case "gourmet impling jar":
          if (client.getItemContainer(InventoryID.INVENTORY) != null)
          {
            for (Item i : Objects.requireNonNull(client.getItemContainer(InventoryID.INVENTORY)).getItems())
            {
              invItemNames.add(client.getItemDefinition((i.getId())).getName());
            }
            if ((invItemNames.contains("Clue scroll (easy)") || bankItemNames.contains("Clue scroll (easy)")))
            {
              swap("use", option, target, index);
            }
          }
          break;
        case "young impling jar":
          if (client.getItemContainer(InventoryID.INVENTORY) != null)
          {
            for (Item i : Objects.requireNonNull(client.getItemContainer(InventoryID.INVENTORY)).getItems())
            {
              invItemNames.add(client.getItemDefinition((i.getId())).getName());
            }
            if (invItemNames.contains("Clue scroll (beginner)") || bankItemNames.contains("Clue scroll (beginner)"))
            {
              swap("use", option, target, index);
            }
          }
          break;
        case "eclectic impling jar":
          if (client.getItemContainer(InventoryID.INVENTORY) != null)
          {
            for (Item i : Objects.requireNonNull(client.getItemContainer(InventoryID.INVENTORY)).getItems())
            {
              invItemNames.add(client.getItemDefinition((i.getId())).getName());
            }
            if ((invItemNames.contains("Clue scroll (medium)") || bankItemNames.contains("Clue scroll (medium)")))
            {
              swap("use", option, target, index);
            }
          }
          break;
        case "magpie impling jar":
        case "nature impling jar":
        case "ninja impling jar":
          if (client.getItemContainer(InventoryID.INVENTORY) != null)
          {
            for (Item i : Objects.requireNonNull(client.getItemContainer(InventoryID.INVENTORY)).getItems())
            {
              invItemNames.add(client.getItemDefinition((i.getId())).getName());
            }
            if ((invItemNames.contains("Clue scroll (hard)") || bankItemNames.contains("Clue scroll (hard)")))
            {
              swap("use", option, target, index);
            }
          }
          break;
        case "crystal impling jar":
        case "dragon impling jar":
          if (client.getItemContainer(InventoryID.INVENTORY) != null)
          {
            for (Item i : Objects.requireNonNull(client.getItemContainer(InventoryID.INVENTORY)).getItems())
            {
              invItemNames.add(client.getItemDefinition((i.getId())).getName());
            }
            if ((invItemNames.contains("Clue scroll (elite)") || bankItemNames.contains("Clue scroll (elite)")))
            {
              swap("use", option, target, index);
            }
          }
          break;
      }
    }
  }

  @Subscribe
  public void onClientTick(ClientTick clientTick)
  {
    // The menu is not rebuilt when it is open, so don't swap or else it will
    // repeatedly swap entries
    if (client.getGameState() != GameState.LOGGED_IN || client.isMenuOpen())
    {
      return;
    }

    MenuEntry[] menuEntries = client.getMenuEntries();

    // Build option map for quick lookup in findIndex
    int idx = 0;
    optionIndexes.clear();
    for (MenuEntry entry : menuEntries)
    {
      String option = Text.removeTags(entry.getOption()).toLowerCase();
      optionIndexes.put(option, idx++);
    }

    // Perform swaps
    idx = 0;
    for (MenuEntry entry : menuEntries)
    {
      swapMenuEntry(idx++, entry);
    }
  }

  private void swap(String optionA, String optionB, String target, int index)
  {
    swap(optionA, optionB, target, index, true);
  }

  private void swap(String optionA, String optionB, String target, int index, boolean strict)
  {
    MenuEntry[] menuEntries = client.getMenuEntries();

    int thisIndex = findIndex(menuEntries, index, optionB, target, strict);
    int optionIdx = findIndex(menuEntries, thisIndex, optionA, target, strict);

    if (thisIndex >= 0 && optionIdx >= 0)
    {
      swap(optionIndexes, menuEntries, optionIdx, thisIndex);
    }
  }

  private int findIndex(MenuEntry[] entries, int limit, String option, String target, boolean strict)
  {
    if (strict)
    {
      List<Integer> indexes = optionIndexes.get(option);

      // We want the last index which matches the target, as that is what is top-most
      // on the menu
      for (int i = indexes.size() - 1; i >= 0; --i)
      {
        int idx = indexes.get(i);
        MenuEntry entry = entries[idx];
        String entryTarget = Text.removeTags(entry.getTarget()).toLowerCase();

        // Limit to the last index which is prior to the current entry
        if (idx <= limit && entryTarget.equals(target))
        {
          return idx;
        }
      }
    }
    else
    {
      // Without strict matching we have to iterate all entries up to the current limit...
      for (int i = limit; i >= 0; i--)
      {
        MenuEntry entry = entries[i];
        String entryOption = Text.removeTags(entry.getOption()).toLowerCase();
        String entryTarget = Text.removeTags(entry.getTarget()).toLowerCase();

        if (entryOption.contains(option.toLowerCase()) && entryTarget.equals(target))
        {
          return i;
        }
      }

    }

    return -1;
  }

  private void swap(ArrayListMultimap<String, Integer> optionIndexes, MenuEntry[] entries, int index1, int index2)
  {
    MenuEntry entry = entries[index1];
    entries[index1] = entries[index2];
    entries[index2] = entry;

    client.setMenuEntries(entries);

    // Rebuild option indexes
    optionIndexes.clear();
    int idx = 0;
    for (MenuEntry menuEntry : entries)
    {
      String option = Text.removeTags(menuEntry.getOption()).toLowerCase();
      optionIndexes.put(option, idx++);
    }
  }
}