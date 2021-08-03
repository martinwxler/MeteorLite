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
import meteor.plugins.EventTestPlugin;
import meteor.plugins.Plugin;
import meteor.plugins.actions.ActionPlugin;
import meteor.plugins.agility.AgilityPlugin;
import meteor.plugins.aoewarnings.AoeWarningPlugin;
import meteor.plugins.bank.BankPlugin;
import meteor.plugins.boosts.BoostsPlugin;
import meteor.plugins.botutils.BotUtils;
import meteor.plugins.camera.CameraPlugin;
import meteor.plugins.cluescrolls.ClueScrollPlugin;
import meteor.plugins.combatlevel.CombatLevelPlugin;
import meteor.plugins.fishing.FishingPlugin;
import meteor.plugins.gpu.GpuPlugin;
import meteor.plugins.grounditems.GroundItemsPlugin;
import meteor.plugins.groundmarkers.GroundMarkerPlugin;
import meteor.plugins.hunter.HunterPlugin;
import meteor.plugins.implings.ImplingsPlugin;
import meteor.plugins.itemcharges.ItemChargePlugin;
import meteor.plugins.itemprices.ItemPricesPlugin;
import meteor.plugins.itemstats.ItemStatPlugin;
import meteor.plugins.menuentryswapper.MenuEntrySwapperPlugin;
import meteor.plugins.mousetooltips.MouseTooltipPlugin;
import meteor.plugins.neverlog.NeverLogoutPlugin;
import meteor.plugins.npcindicators.NpcIndicatorsPlugin;
import meteor.plugins.objectindicators.ObjectIndicatorsPlugin;
import meteor.plugins.stretchedmode.StretchedModePlugin;
import meteor.plugins.worldmap.WorldMapPlugin;

public class PluginManager {

  public static List<Plugin> plugins = new ArrayList<>();
  @Inject
  private EventBus eventBus;
  @Inject
  private ConfigManager configManager;

  public void startInternalPlugins() {
    plugins.add(new EventTestPlugin());
    //MeteorLite.plugins.add(new DebugPlugin());

    plugins.add(new StretchedModePlugin());
    plugins.add(new GpuPlugin());
    plugins.add(new AoeWarningPlugin());
    plugins.add(new NeverLogoutPlugin());
    plugins.add(new BankPlugin());
    plugins.add(new AgilityPlugin());
    plugins.add(new MouseTooltipPlugin());
    plugins.add(new CombatLevelPlugin());
    plugins.add(new GroundMarkerPlugin());
    plugins.add(new ItemStatPlugin());
    plugins.add(new ItemPricesPlugin());
    plugins.add(new WorldMapPlugin());
    plugins.add(new ClueScrollPlugin());
    plugins.add(new GroundItemsPlugin());
    plugins.add(new CameraPlugin());
    plugins.add(new BoostsPlugin());
    plugins.add(new FishingPlugin());
    plugins.add(new HunterPlugin());
    plugins.add(new ItemChargePlugin());
    plugins.add(new ObjectIndicatorsPlugin());
    plugins.add(new NpcIndicatorsPlugin());
    plugins.add(new ImplingsPlugin());
    plugins.add(new MenuEntrySwapperPlugin());
    plugins.add(new ActionPlugin());

    for (Plugin plugin : plugins) {
      Injector injector = plugin.getInjector();
      if (injector == null) {
        // Create injector for the module
        Module pluginModule = (Binder binder) ->
        {
          // Since the plugin itself is a module, it won't bind itself, so we'll bind it here
          binder.bind((Class<Plugin>) plugin.getClass()).toInstance(plugin);
          binder.install(plugin);
        };
        Injector pluginInjector = MeteorLite.injector.createChildInjector(pluginModule);
        pluginInjector.injectMembers(plugin);
        plugin.setInjector(pluginInjector);
        for (Key<?> key : pluginInjector.getBindings().keySet()) {
          Class<?> type = key.getTypeLiteral().getRawType();
          if (Config.class.isAssignableFrom(type)) {
            Config config = (Config) pluginInjector.getInstance(key);
            configManager.setDefaultConfiguration(config, false);
          }
        }
      }

      eventBus.register(plugin);
      plugin.startup();
    }
  }
}
