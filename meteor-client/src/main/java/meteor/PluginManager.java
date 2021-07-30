package meteor;

import com.google.inject.Binder;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import meteor.config.Config;
import meteor.config.ConfigManager;
import meteor.eventbus.EventBus;
import meteor.plugins.EventTestPlugin;
import meteor.plugins.Plugin;
import meteor.plugins.agility.AgilityPlugin;
import meteor.plugins.aoewarnings.AoeWarningPlugin;
import meteor.plugins.bank.BankPlugin;
import meteor.plugins.boosts.BoostsPlugin;
import meteor.plugins.camera.CameraPlugin;
import meteor.plugins.cluescrolls.ClueScrollPlugin;
import meteor.plugins.combatlevel.CombatLevelPlugin;
import meteor.plugins.fishing.FishingPlugin;
import meteor.plugins.gpu.GpuPlugin;
import meteor.plugins.grounditems.GroundItemsPlugin;
import meteor.plugins.groundmarkers.GroundMarkerPlugin;
import meteor.plugins.itemprices.ItemPricesPlugin;
import meteor.plugins.itemstats.ItemStatPlugin;
import meteor.plugins.mousetooltips.MouseTooltipPlugin;
import meteor.plugins.neverlog.NeverLogoutPlugin;
import meteor.plugins.stretchedmode.StretchedModePlugin;
import meteor.plugins.worldmap.WorldMapPlugin;

public class PluginManager {

  @Inject
  private EventBus eventBus;

  @Inject
  private ConfigManager configManager;

  public void startInternalPlugins()
  {
    MeteorLite.plugins.add(new EventTestPlugin());
    //MeteorLite.plugins.add(new DebugPlugin());

    MeteorLite.plugins.add(new StretchedModePlugin());
    MeteorLite.plugins.add(new GpuPlugin());
    MeteorLite.plugins.add(new AoeWarningPlugin());
    MeteorLite.plugins.add(new NeverLogoutPlugin());
    MeteorLite.plugins.add(new BankPlugin());
    MeteorLite.plugins.add(new AgilityPlugin());
    MeteorLite.plugins.add(new MouseTooltipPlugin());
    MeteorLite.plugins.add(new CombatLevelPlugin());
    MeteorLite.plugins.add(new GroundMarkerPlugin());
    MeteorLite.plugins.add(new ItemStatPlugin());
    MeteorLite.plugins.add(new ItemPricesPlugin());
    MeteorLite.plugins.add(new WorldMapPlugin());
    MeteorLite.plugins.add(new ClueScrollPlugin());
    MeteorLite.plugins.add(new GroundItemsPlugin());
    MeteorLite.plugins.add(new CameraPlugin());
    MeteorLite.plugins.add(new BoostsPlugin());
    MeteorLite.plugins.add(new FishingPlugin());

    for (Plugin plugin : MeteorLite.plugins) {
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
        for (Key<?> key : pluginInjector.getBindings().keySet())
        {
          Class<?> type = key.getTypeLiteral().getRawType();
          if (Config.class.isAssignableFrom(type))
          {
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
