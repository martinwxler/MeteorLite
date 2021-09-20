package meteor.plugins.menuentryswappercustom;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Provides;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import javax.inject.Inject;

import meteor.eventbus.events.ConfigChanged;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.FocusChanged;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.input.KeyListener;
import meteor.input.KeyManager;
import meteor.menus.MenuManager;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.util.Text;
import meteor.util.WildcardMatcher;


@PluginDescriptor(
    name = "MES Custom",
    description = "Set your own custom swaps and/or remove options.",
    tags = {"mes", "menu", "entry", "swapper", "custom", "swap"},
    enabledByDefault = false
)
public class MenuEntrySwapperCustomPlugin extends Plugin implements KeyListener {

  @Inject
  private Client client;
  @Inject
  private MenuManager menuManager;
  @Inject
  private KeyManager keyManager;
  @Inject
  private MenuEntrySwapperCustomConfig config;

  private static final Set<MenuAction> EXAMINE_TYPES;
  private static final Splitter SPLITTER;
  private final ArrayList<MenuEntrySwapperCustomPlugin.EntryFromConfig> customSwaps = new ArrayList();
  private final ArrayList<MenuEntrySwapperCustomPlugin.EntryFromConfig> shiftCustomSwaps = new ArrayList();
  private final ArrayList<MenuEntrySwapperCustomPlugin.EntryFromConfig> removeOptions = new ArrayList();
  private final ArrayList<MenuEntrySwapperCustomPlugin.EntryFromConfig> bankCustomSwaps = new ArrayList();
  private final ArrayList<MenuEntrySwapperCustomPlugin.EntryFromConfig> shiftBankCustomSwaps = new ArrayList();
  private boolean holdingShift = false;

  @Provides
  public MenuEntrySwapperCustomConfig getConfig(ConfigManager configManager) {
    return configManager.getConfig(MenuEntrySwapperCustomConfig.class);
  }

  public void startup() {
    this.holdingShift = false;
    this.keyManager.registerKeyListener(this, this.getClass());
    this.customSwaps.clear();
    this.parseConfigToList(this.config.customSwapsString(), this.customSwaps);
    this.shiftCustomSwaps.clear();
    this.parseConfigToList(this.config.shiftCustomSwapsString(), this.shiftCustomSwaps);
    this.removeOptions.clear();
    this.parseConfigToList(this.config.removeOptionsString(), this.removeOptions);
    this.bankCustomSwaps.clear();
    this.parseConfigToList(this.config.bankCustomSwapsString(), this.bankCustomSwaps);
    this.shiftBankCustomSwaps.clear();
    this.parseConfigToList(this.config.bankShiftCustomSwapsString(), this.shiftBankCustomSwaps);
  }

  public void shutdown() {
    this.holdingShift = false;
    this.keyManager.unregisterKeyListener(this);
  }

  @Subscribe
  public void onConfigChanged(ConfigChanged event) {
    if (event.getGroup().equals("zmenuentryswapper")) {
      String var2 = event.getKey();
      byte var3 = -1;
      switch (var2.hashCode()) {
        case -1885351337:
          if (var2.equals("removeOptionsStr")) {
            var3 = 2;
          }
          break;
        case -637339480:
          if (var2.equals("bankShiftCustomSwapsStr")) {
            var3 = 4;
          }
          break;
        case -179783548:
          if (var2.equals("shiftCustomSwapsStr")) {
            var3 = 1;
          }
          break;
        case 1157466978:
          if (var2.equals("customSwapsStr")) {
            var3 = 0;
          }
          break;
        case 1423754430:
          if (var2.equals("bankCustomSwapsStr")) {
            var3 = 3;
          }
      }

      switch (var3) {
        case 0:
          this.customSwaps.clear();
          this.parseConfigToList(this.config.customSwapsString(), this.customSwaps);
          break;
        case 1:
          this.shiftCustomSwaps.clear();
          this.parseConfigToList(this.config.shiftCustomSwapsString(), this.shiftCustomSwaps);
          break;
        case 2:
          this.removeOptions.clear();
          this.parseConfigToList(this.config.removeOptionsString(), this.removeOptions);
          break;
        case 3:
          this.bankCustomSwaps.clear();
          this.parseConfigToList(this.config.bankCustomSwapsString(), this.bankCustomSwaps);
          break;
        case 4:
          this.shiftBankCustomSwaps.clear();
          this.parseConfigToList(this.config.bankShiftCustomSwapsString(),
              this.shiftBankCustomSwaps);
      }
    }

  }

  private void parseConfigToList(String value,
      List<MenuEntrySwapperCustomPlugin.EntryFromConfig> set) {
    List<String> strList = SPLITTER.splitToList(value);
    Iterator var4 = strList.iterator();

    while (var4.hasNext()) {
      String str = (String) var4.next();
      String[] stringList = str.split(",");
      if (stringList.length > 1) {
        String option = stringList[0].toLowerCase().trim();
        String target = stringList[1].toLowerCase().trim();
        String topOption = null;
        String topTarget = null;
        if (stringList.length == 4) {
          topOption = stringList[2].toLowerCase().trim();
          topTarget = stringList[3].toLowerCase().trim();
        }

        MenuEntrySwapperCustomPlugin.EntryFromConfig entryFromConfig = new MenuEntrySwapperCustomPlugin.EntryFromConfig(
            option, target, topOption, topTarget);
        set.add(entryFromConfig);
      }
    }

  }

  private static int topEntryIndex(MenuEntry[] entries) {
    for (int i = entries.length - 1; i >= 0; --i) {
      int type = entries[i].getType();
      if (!EXAMINE_TYPES.contains(MenuAction.of(type))) {
        return i;
      }
    }

    return entries.length - 1;
  }

  private static int indexOfEntry(List<MenuEntrySwapperCustomPlugin.EntryFromConfig> configEntries,
      MenuEntrySwapperCustomPlugin.EntryFromConfig entryFromConfig, MenuEntry[] entries) {
    int topEntryIndex = topEntryIndex(entries);
    MenuEntry topEntry = entries[topEntryIndex];
    String target = Text.removeTags(topEntry.getTarget()).toLowerCase();
    String option = Text.removeTags(topEntry.getOption()).toLowerCase();

    for (int i = 0; i < configEntries.size(); ++i) {
      MenuEntrySwapperCustomPlugin.EntryFromConfig _configEntry = configEntries
          .get(i);
      if ((_configEntry.option.equals(entryFromConfig.option) || WildcardMatcher
          .matches(_configEntry.option, entryFromConfig.option)) && (
          _configEntry.target.equals(entryFromConfig.target) || WildcardMatcher
              .matches(_configEntry.target, entryFromConfig.target))) {
        boolean a = _configEntry.topOption == null;
        boolean b = _configEntry.topTarget == null;
        Supplier<Boolean> c = () -> {
          return _configEntry.topOption.equals(option) || WildcardMatcher
              .matches(_configEntry.topOption, option);
        };
        Supplier<Boolean> d = () -> {
          return _configEntry.topTarget.equals(target) || WildcardMatcher
              .matches(_configEntry.topTarget, target);
        };
        if (a || b || c.get() && d.get()) {
          return i;
        }
      }
    }

    return -1;
  }

  private MenuEntry[] filterEntries(MenuEntry[] menuEntries) {
    ArrayList<MenuEntry> filtered = new ArrayList();
    MenuEntry[] var3 = menuEntries;
    int var4 = menuEntries.length;

    for (int var5 = 0; var5 < var4; ++var5) {
      MenuEntry entry = var3[var5];
      String target = Text.standardize(Text.removeTags(entry.getTarget()));
      String option = Text.standardize(Text.removeTags(entry.getOption()));
      MenuEntrySwapperCustomPlugin.EntryFromConfig entryFromConfig = new MenuEntrySwapperCustomPlugin.EntryFromConfig(
          option, target);
      if (indexOfEntry(this.removeOptions, entryFromConfig, menuEntries) == -1) {
        filtered.add(entry);
      }
    }

    return filtered.toArray(new MenuEntry[0]);
  }

  @Subscribe
  public void onClientTick(ClientTick event) {
    if (this.client.getGameState() == GameState.LOGGED_IN && !this.client.isMenuOpen() && this
        .isBankInterfaceClosed()) {
      MenuEntry[] menuEntries = this.client.getMenuEntries();
      if (this.config.removeOptionsToggle()) {
        menuEntries = this.filterEntries(menuEntries);
        this.client.setMenuEntries(menuEntries);
      }

      int entryIndex = -1;
      int priority = -1;

      int index;
      for (int i = 0; i < menuEntries.length; ++i) {
        MenuEntry entry = menuEntries[i];
        String target = Text.standardize(Text.removeTags(entry.getTarget()));
        String option = Text.standardize(Text.removeTags(entry.getOption()));
        MenuEntrySwapperCustomPlugin.EntryFromConfig entryFromConfig = new MenuEntrySwapperCustomPlugin.EntryFromConfig(
            option, target);
        if (this.holdingShift && this.config.shiftCustomSwapsToggle()) {
          index = indexOfEntry(this.shiftCustomSwaps, entryFromConfig, menuEntries);
          if (index > priority) {
            entryIndex = i;
            priority = index;
          }
        } else if (this.config.customSwapsToggle()) {
          index = indexOfEntry(this.customSwaps, entryFromConfig, menuEntries);
          if (index > priority) {
            entryIndex = i;
            priority = index;
          }
        }
      }

      if (entryIndex >= 0) {
        MenuEntry target = menuEntries[entryIndex];
        int targetId = target.getIdentifier();
        int targetType = target.getType();
        MenuEntry[] var15 = menuEntries;
        int var17 = menuEntries.length;

        for (index = 0; index < var17; ++index) {
          MenuEntry menuEntry = var15[index];
          if (menuEntry.getType() < target.getType()) {
            menuEntry.setType(menuEntry.getType() + 2000);
          }
        }

        if (targetId >= 6 && targetId <= 9 && targetType == MenuAction.CC_OP_LOW_PRIORITY.getId()) {
          target.setType(MenuAction.CC_OP.getId());
        }

        MenuEntry first = menuEntries[menuEntries.length - 1];
        menuEntries[menuEntries.length - 1] = menuEntries[entryIndex];
        menuEntries[entryIndex] = first;
        this.client.setMenuEntries(menuEntries);
      }
    }
  }

  @Subscribe
  public void onMenuEntryAdded(MenuEntryAdded event) {
    if (!this.isBankInterfaceClosed() && event.getIdentifier() <= 2) {
      MenuEntry[] menuEntries = this.client.getMenuEntries();
      int entryIndex = -1;
      int priority = -1;

      for (int i = 0; i < menuEntries.length; ++i) {
        MenuEntry entry = menuEntries[i];
        String target = Text.removeTags(entry.getTarget()).toLowerCase();
        String option = Text.removeTags(entry.getOption()).toLowerCase();
        MenuEntrySwapperCustomPlugin.EntryFromConfig entryFromConfig = new MenuEntrySwapperCustomPlugin.EntryFromConfig(
            option, target);
        int index;
        if (this.holdingShift && this.config.shiftCustomSwapsToggle()) {
          index = indexOfEntry(this.shiftBankCustomSwaps, entryFromConfig, menuEntries);
          if (index > priority) {
            entryIndex = i;
            priority = index;
          }
        } else if (this.config.customSwapsToggle()) {
          index = indexOfEntry(this.bankCustomSwaps, entryFromConfig, menuEntries);
          if (index > priority) {
            entryIndex = i;
            priority = index;
          }
        }
      }

      if (entryIndex >= 0) {
        MenuEntry target = menuEntries[entryIndex];
        int opId = target.getIdentifier();
        int actionId = opId >= 6 ? MenuAction.CC_OP_LOW_PRIORITY.getId() : MenuAction.CC_OP.getId();
        if (event.getType() == MenuAction.CC_OP.getId() && (event.getIdentifier() == 1
            || event.getIdentifier() == 2)) {
          this.specialSwap(actionId, opId);
        }

      }
    }
  }

  private void specialSwap(int actionId, int opId) {
    MenuEntry[] menuEntries = this.client.getMenuEntries();

    for (int i = menuEntries.length - 1; i >= 0; --i) {
      MenuEntry entry = menuEntries[i];
      if (entry.getType() == actionId && entry.getIdentifier() == opId) {
        entry.setType(MenuAction.CC_OP.getId());
        menuEntries[i] = menuEntries[menuEntries.length - 1];
        menuEntries[menuEntries.length - 1] = entry;
        this.client.setMenuEntries(menuEntries);
        break;
      }
    }

  }

  public void keyTyped(KeyEvent event) {
  }

  public void keyPressed(KeyEvent event) {
    if (event.getKeyCode() == 16) {
      this.holdingShift = true;
    }

  }

  public void keyReleased(KeyEvent event) {
    if (event.getKeyCode() == 16) {
      this.holdingShift = false;
    }

  }

  @Subscribe
  public void onFocusChanged(FocusChanged event) {
    if (!event.isFocused()) {
      this.holdingShift = false;
    }

  }

  private boolean isBankInterfaceClosed() {
    Widget widgetBankTitleBar = this.client.getWidget(WidgetInfo.BANK_TITLE_BAR);
    Widget widgetDepositBox = this.client.getWidget(12582912);
    Widget coxPublicChest = this.client.getWidget(550, 1);
    Widget coxPrivateChest = this.client.getWidget(271, 1);
    return (widgetBankTitleBar == null || widgetBankTitleBar.isHidden()) && (
        widgetDepositBox == null || widgetDepositBox.isHidden()) && (coxPublicChest == null
        || coxPublicChest.isHidden()) && (coxPrivateChest == null || coxPrivateChest.isHidden());
  }

  static {
    EXAMINE_TYPES = ImmutableSet
        .of(MenuAction.EXAMINE_ITEM, MenuAction.EXAMINE_ITEM_GROUND, MenuAction.EXAMINE_NPC,
            MenuAction.EXAMINE_OBJECT);
    SPLITTER = Splitter.on("\n").omitEmptyStrings().trimResults();
  }

  static class EntryFromConfig {

    private String option;
    private String target;
    private String topOption;
    private String topTarget;

    EntryFromConfig(String option, String target) {
      this(option, target, null, null);
    }

    public boolean equals(Object other) {
      if (!(other instanceof MenuEntrySwapperCustomPlugin.EntryFromConfig)) {
        return false;
      } else {
        return this.option.equals(((MenuEntrySwapperCustomPlugin.EntryFromConfig) other).option)
            && this.target.equals(((MenuEntrySwapperCustomPlugin.EntryFromConfig) other).target)
            && this.topOption
            .equals(((MenuEntrySwapperCustomPlugin.EntryFromConfig) other).topOption)
            && this.topTarget
            .equals(((MenuEntrySwapperCustomPlugin.EntryFromConfig) other).topTarget);
      }
    }

    public int hashCode() {
      return Objects.hash(this.option, this.target, this.topOption, this.topTarget);
    }

    public String getOption() {
      return this.option;
    }

    public String getTarget() {
      return this.target;
    }

    public String getTopOption() {
      return this.topOption;
    }

    public String getTopTarget() {
      return this.topTarget;
    }

    public void setOption(String option) {
      this.option = option;
    }

    public void setTarget(String target) {
      this.target = target;
    }

    public void setTopOption(String topOption) {
      this.topOption = topOption;
    }

    public void setTopTarget(String topTarget) {
      this.topTarget = topTarget;
    }

    public String toString() {
      String var10000 = this.getOption();
      return "MenuEntrySwapperCustomPlugin.EntryFromConfig(option=" + var10000 + ", target=" + this
          .getTarget() + ", topOption=" + this.getTopOption() + ", topTarget=" + this.getTopTarget()
          + ")";
    }

    public EntryFromConfig(String option, String target, String topOption, String topTarget) {
      this.option = option;
      this.target = target;
      this.topOption = topOption;
      this.topTarget = topTarget;
    }
  }
}
 