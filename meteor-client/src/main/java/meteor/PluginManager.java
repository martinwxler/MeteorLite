package meteor;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import java.util.ArrayList;
import java.util.List;
import meteor.config.Config;
import meteor.config.ConfigManager;
import meteor.eventbus.EventBus;
import meteor.plugins.Plugin;
import meteor.plugins.actions.ActionPlugin;
import meteor.plugins.agility.AgilityPlugin;
import meteor.plugins.aoewarnings.AoeWarningPlugin;
import meteor.plugins.bank.BankPlugin;
import meteor.plugins.banktags.BankTagsPlugin;
import meteor.plugins.betterantidrag.BetterAntiDragPlugin;
import meteor.plugins.betterroguesden.BetterRougesDenPlugin;
import meteor.plugins.blastfurnace.BlastFurnacePlugin;
import meteor.plugins.boosts.BoostsPlugin;
import meteor.plugins.botutils.BotUtils;
import meteor.plugins.camera.CameraPlugin;
import meteor.plugins.cannon.CannonPlugin;
import meteor.plugins.chatcommands.ChatCommandsPlugin;
import meteor.plugins.cluescrolls.ClueScrollPlugin;
import meteor.plugins.combatlevel.CombatLevelPlugin;
import meteor.plugins.fishing.FishingPlugin;
import meteor.plugins.gpu.GpuPlugin;
import meteor.plugins.grounditems.GroundItemsPlugin;
import meteor.plugins.groundmarkers.GroundMarkerPlugin;
import meteor.plugins.hunter.HunterPlugin;
import meteor.plugins.implings.ImplingsPlugin;
import meteor.plugins.interacthighlight.InteractHighlightPlugin;
import meteor.plugins.itemcharges.ItemChargePlugin;
import meteor.plugins.itemprices.ItemPricesPlugin;
import meteor.plugins.itemstats.ItemStatPlugin;
import meteor.plugins.iutils.iUtils;
import meteor.plugins.leftclickcast.LeftClickCast;
import meteor.plugins.menuentryswapper.MenuEntrySwapperPlugin;
import meteor.plugins.menuentryswapperextended.MenuEntrySwapperExtendedPlugin;
import meteor.plugins.mousetooltips.MouseTooltipPlugin;
import meteor.plugins.neverlog.NeverLogoutPlugin;
import meteor.plugins.npcindicators.NpcIndicatorsPlugin;
import meteor.plugins.npcstatus.NpcStatusPlugin;
import meteor.plugins.objectindicators.ObjectIndicatorsPlugin;
import meteor.plugins.oneclick.OneClickPlugin;
import meteor.plugins.playerattacktimer.PlayerAttackTimerPlugin;
import meteor.plugins.playerindicators.PlayerIndicatorsPlugin;
import meteor.plugins.poh.PohPlugin;
import meteor.plugins.puzzlesolver.PuzzleSolverPlugin;
import meteor.plugins.questlist.QuestListPlugin;
import meteor.plugins.randomevents.RandomEventPlugin;
import meteor.plugins.regenmeter.RegenMeterPlugin;
import meteor.plugins.reportbutton.ReportButtonPlugin;
import meteor.plugins.runecraft.RunecraftPlugin;
import meteor.plugins.runenergy.RunEnergyPlugin;
import meteor.plugins.runepouch.RunepouchPlugin;
import meteor.plugins.slayer.SlayerPlugin;
import meteor.plugins.smithing.SmithingPlugin;
import meteor.plugins.stretchedmode.StretchedModePlugin;
import meteor.plugins.tearsofguthix.TearsOfGuthixPlugin;
import meteor.plugins.ticktimers.TickTimersPlugin;
import meteor.plugins.tileindicators.TileIndicatorsPlugin;
import meteor.plugins.timestamp.TimestampPlugin;
import meteor.plugins.worldmap.WorldMapPlugin;

public class PluginManager {

  @Inject
  private EventBus eventBus;

  @Inject
  private ConfigManager configManager;

  @Inject
  private MeteorLiteClientModule meteorLiteClientModule;

  PluginManager() {
  }

  public static List<Plugin> plugins = new ArrayList<>();
  private static BotUtils botUtils = new BotUtils();
  private static iUtils iUtils = new iUtils();

  private void initPlugins() {
    plugins.add(new AoeWarningPlugin());
    plugins.add(new BankPlugin());
    plugins.add(new BankTagsPlugin());
    plugins.add(new BetterAntiDragPlugin());
    plugins.add(new BetterRougesDenPlugin());
    plugins.add(new BlastFurnacePlugin());
    plugins.add(new BoostsPlugin());
    plugins.add(new TickTimersPlugin());
    plugins.add(new CameraPlugin());
    plugins.add(new CannonPlugin());
    plugins.add(new ChatCommandsPlugin());
    plugins.add(new TimestampPlugin());
    plugins.add(new ClueScrollPlugin());
    plugins.add(new CombatLevelPlugin());
    plugins.add(new FishingPlugin());
    plugins.add(new GpuPlugin());
    plugins.add(new GroundItemsPlugin());
    plugins.add(new GroundMarkerPlugin());
    plugins.add(new HunterPlugin());
    plugins.add(new ImplingsPlugin());
    plugins.add(new InteractHighlightPlugin());
    plugins.add(new ItemChargePlugin());
    plugins.add(new ItemPricesPlugin());
    plugins.add(new ItemStatPlugin());
    plugins.add(new LeftClickCast());
    plugins.add(new MenuEntrySwapperPlugin());
    plugins.add(new MenuEntrySwapperExtendedPlugin());
    plugins.add(new MouseTooltipPlugin());
    plugins.add(new NeverLogoutPlugin());
    plugins.add(new NpcIndicatorsPlugin());
    plugins.add(new NpcStatusPlugin());
    plugins.add(new ObjectIndicatorsPlugin());
    plugins.add(new OneClickPlugin());
    plugins.add(new PlayerAttackTimerPlugin());
    plugins.add(new PlayerIndicatorsPlugin());
    plugins.add(new PohPlugin());
    plugins.add(new PuzzleSolverPlugin());
    plugins.add(new QuestListPlugin());
    plugins.add(new RandomEventPlugin());
    plugins.add(new RegenMeterPlugin());
    plugins.add(new ReportButtonPlugin());
    plugins.add(new RunEnergyPlugin());
    plugins.add(new RunepouchPlugin());
    plugins.add(new RunecraftPlugin());
    plugins.add(new SlayerPlugin());
    plugins.add(new SmithingPlugin());
    plugins.add(new StretchedModePlugin());
    plugins.add(new TileIndicatorsPlugin());
    plugins.add(new TearsOfGuthixPlugin());
    plugins.add(new WorldMapPlugin());

    plugins.add(botUtils);
    plugins.add(iUtils);

    plugins.add(new ActionPlugin());
  }

  public void startInternalPlugins() {
    initPlugins();
    for (Plugin plugin : plugins) {
      Injector parent = meteorLiteClientModule.instanceInjector;

      List<Module> depModules = new ArrayList<>();
      if (!plugin.getClass().isInstance(iUtils) && !plugin.getClass().isInstance(botUtils))
      {
        Module botUtilsModule = (Binder binder) ->
        {
          try {
            Plugin botUtilsInstance = botUtils.getClass().getDeclaredConstructor().newInstance();
            binder.bind((Class<Plugin>) botUtilsInstance.getClass()).toInstance(botUtilsInstance);
            binder.install(botUtilsInstance);
          } catch (Exception e) {
            e.printStackTrace();
          }
        };
        depModules.add(botUtilsModule);
        Module iUtilsModule = (Binder binder) ->
        {
          try {
            Plugin iUtilsInstance = iUtils.getClass().getDeclaredConstructor().newInstance();
            binder.bind((Class<Plugin>) iUtilsInstance.getClass()).toInstance(iUtilsInstance);
            binder.install(iUtilsInstance);
          } catch (Exception e) {
            e.printStackTrace();
          }
        };
        depModules.add(iUtilsModule);
        parent = parent.createChildInjector(depModules);
      }

      Module pluginModule = (Binder binder) ->
      {
        // Since the plugin itself is a module, it won't bind itself, so we'll bind it here
        binder.bind((Class<Plugin>) plugin.getClass()).toInstance(plugin);
        binder.install(plugin);
      };
      Injector pluginInjector = parent.createChildInjector(pluginModule);
      pluginInjector.injectMembers(plugin);
      plugin.setInjector(pluginInjector);
      for (Key<?> key : pluginInjector.getBindings().keySet()) {
        Class<?> type = key.getTypeLiteral().getRawType();
        if (Config.class.isAssignableFrom(type)) {
          Config config = (Config) pluginInjector.getInstance(key);
          configManager.setDefaultConfiguration(config, false);
        }
      }

      plugin.toggle();
    }
  }

  public static<T extends Plugin> T getInstance(Class<? extends Plugin> type)
  {
    for (Plugin p : PluginManager.plugins)
    {
      if (type.isInstance(p))
      {
        return (T) p;
      }
    }
    return null;
  }


}
