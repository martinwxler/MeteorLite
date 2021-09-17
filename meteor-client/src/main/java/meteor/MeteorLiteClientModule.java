package meteor;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.inject.*;
import com.google.inject.name.Names;
import javafx.scene.paint.Paint;
import meteor.callback.Hooks;
import meteor.chat.ChatMessageManager;
import meteor.config.ChatColorConfig;
import meteor.config.ConfigManager;
import meteor.config.RuneLiteConfig;
import meteor.discord.DiscordService;
import meteor.eventbus.DeferredEventBus;
import meteor.eventbus.EventBus;
import meteor.game.WorldService;
import meteor.plugins.api.game.Game;
import meteor.plugins.api.game.GameThread;
import meteor.plugins.itemstats.ItemStatChangesService;
import meteor.plugins.itemstats.ItemStatChangesServiceImpl;
import meteor.plugins.meteorlite.MeteorLiteConfig;
import meteor.ui.MeteorUI;
import meteor.ui.overlay.OverlayManager;
import meteor.ui.overlay.WidgetOverlay;
import meteor.ui.overlay.tooltip.TooltipOverlay;
import meteor.ui.overlay.worldmap.WorldMapOverlay;
import meteor.util.ExecutorServiceExceptionLogger;
import meteor.util.NonScheduledExecutorServiceExceptionLogger;
import meteor.util.WorldUtil;
import net.runelite.api.Client;
import net.runelite.api.hooks.Callbacks;
import net.runelite.http.api.chat.ChatClient;
import net.runelite.http.api.worlds.World;
import net.runelite.http.api.worlds.WorldResult;
import okhttp3.OkHttpClient;
import org.sponge.util.Logger;

import javax.annotation.Nullable;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import java.applet.Applet;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.*;

import static meteor.MeteorLiteClientLauncher.DEFAULT_CONFIG_FILE;
import static org.sponge.util.Logger.ANSI_RESET;
import static org.sponge.util.Logger.ANSI_YELLOW;

public class MeteorLiteClientModule extends AbstractModule {
  private static final Logger log = new Logger("MeteorLiteClient");
  public static int METEOR_FONT_SIZE = 14;
  public static Paint METEOR_FONT_COLOR = Paint.valueOf("AQUA");

  public static String uuid = UUID.randomUUID().toString();

  @Inject
  private EventBus eventBus;

  @Inject
  private PluginManager pluginManager;

  @Inject
  private OverlayManager overlayManager;

  @Inject
  private Provider<WorldMapOverlay> worldMapOverlay;

  @Inject
  private Provider<TooltipOverlay> tooltipOverlay;

  @Inject
  private MeteorUI meteorUI;

  @Inject
  private Client client;

  @Inject
  private WorldService worldService;

  @Inject
  private DiscordService discordService;

  public static Map<String, String> properties;
  public static Map<String, String> parameters;

  public void start() throws IOException {
    long startTime = System.currentTimeMillis();

    loadJagexConfiguration();

    MeteorLiteClientLauncher.injector.injectMembers(pluginManager);
    MeteorLiteClientLauncher.injector.injectMembers(client);
    eventBus.register(this);

    discordService.init();

    Collection<WidgetOverlay> overlays = WidgetOverlay.createOverlays(client);
    overlays.forEach((overlay) -> {
      MeteorLiteClientLauncher.injector.injectMembers(overlay);
      overlayManager.add(overlay);
    });

    overlayManager.add(worldMapOverlay.get());

    overlayManager.add(tooltipOverlay.get());

    meteorUI.init();
    eventBus.register(meteorUI);

    log.info(
            ANSI_YELLOW + "OSRS instance started in " + (System.currentTimeMillis() - startTime) + " ms"
                    + ANSI_RESET);
  }

  @Override
  protected void configure() {
    bind(MeteorLiteClientModule.class).toInstance(this);
    bind(Callbacks.class).to(Hooks.class);
    bind(ChatMessageManager.class);
    bind(ScheduledExecutorService.class).toInstance(
            new ExecutorServiceExceptionLogger(Executors.newSingleThreadScheduledExecutor()));

    bind(EventBus.class)
            .toInstance(new EventBus());

    bind(EventBus.class)
            .annotatedWith(Names.named("Deferred EventBus"))
            .to(DeferredEventBus.class);

    bind(ItemStatChangesService.class).to(ItemStatChangesServiceImpl.class);
    bind(Logger.class).toInstance(log);

    requestStaticInjection(
            GameThread.class,
            Game.class
    );
  }

  private void setWorld(int cliWorld)
  {
    int correctedWorld = cliWorld < 300 ? cliWorld + 300 : cliWorld;

    if (correctedWorld <= 300 || client.getWorld() == correctedWorld)
    {
      return;
    }

    final WorldResult worldResult = worldService.getWorlds();

    if (worldResult == null)
    {
      log.warn("Failed to lookup worlds.");
      return;
    }

    final World world = worldResult.findWorld(correctedWorld);

    if (world != null)
    {
      final net.runelite.api.World rsWorld = client.createWorld();
      rsWorld.setActivity(world.getActivity());
      rsWorld.setAddress(world.getAddress());
      rsWorld.setId(world.getId());
      rsWorld.setPlayerCount(world.getPlayers());
      rsWorld.setLocation(world.getLocation());
      rsWorld.setTypes(WorldUtil.toWorldTypes(world.getTypes()));

      client.changeWorld(rsWorld);
      log.debug("Applied new world {}", correctedWorld);
    }
    else
    {
      log.warn("World {} not found.", correctedWorld);
    }
  }

  public void loadJagexConfiguration() throws IOException {
    Map<String, String> properties = new HashMap<>();
    Map<String, String> parameters = new HashMap<>();
    URL url = new URL("http://oldschool.runescape.com/jav_config.ws");
    BufferedReader br;
    try
    {
      br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.ISO_8859_1));

      String line;
      while ((line = br.readLine()) != null) {
        String[] split1 = line.split("=", 2);
        switch (split1[0]) {
          case "param":
            String[] split2 = split1[1].split("=", 2);
            parameters.put(split2[0], split2[1]);
            break;
          case "msg":
            // ignore
            break;
          default:
            properties.put(split1[0], split1[1]);
        }
      }
    } catch (Exception e)
    {
      br = new BufferedReader(
              new InputStreamReader(ClassLoader.getSystemClassLoader().getResourceAsStream("jav_config.ws"), StandardCharsets.ISO_8859_1));
      String line;
      while ((line = br.readLine()) != null) {
        String[] split1 = line.split("=", 2);
        switch (split1[0]) {
          case "param":
            String[] split2 = split1[1].split("=", 2);
            parameters.put(split2[0], split2[1]);
            break;
          case "msg":
            // ignore
            break;
          default:
            properties.put(split1[0], split1[1]);
        }
      }
    }
    MeteorLiteClientModule.properties = properties;
    MeteorLiteClientModule.parameters = parameters;
  }

  public String title() {
    return properties.get("title");
  }

  @Provides
  @Named("developerMode")
  private boolean getDeveloperMode() {
    return false;
  }

  @com.google.inject.name.Named("config")
  @Provides
  @javax.inject.Singleton
  File provideConfigFile() {
    return DEFAULT_CONFIG_FILE;
  }

  @Provides
  @Singleton
  Applet provideApplet() {
    try {
      return (Applet) ClassLoader.getSystemClassLoader().loadClass("osrs.Client").newInstance();
    } catch (Exception e) {
      e.printStackTrace();
    }
    return null;
  }

  @Provides
  @Singleton
  Client provideClient(@Nullable Applet applet) {
    return applet instanceof Client ? (Client) applet : null;
  }

  @Provides
  @Singleton
  RuneLiteConfig provideConfig(ConfigManager configManager) {
    return configManager.getConfig(RuneLiteConfig.class);
  }

  @Provides
  @Singleton
  ExecutorService provideExecutorService() {
    int poolSize = 2 * Runtime.getRuntime().availableProcessors();

    // Will start up to poolSize threads (because of allowCoreThreadTimeOut) as necessary, and times out
    // unused threads after 1 minute
    ThreadPoolExecutor executor = new ThreadPoolExecutor(poolSize, poolSize,
            60L, TimeUnit.SECONDS,
            new LinkedBlockingQueue<>(),
            new ThreadFactoryBuilder().setNameFormat("worker-%d").build());
    executor.allowCoreThreadTimeOut(true);

    return new NonScheduledExecutorServiceExceptionLogger(executor);
  }

  @Provides
  @Singleton
  ChatColorConfig provideChatColorConfig(ConfigManager configManager)
  {
    return configManager.getConfig(ChatColorConfig.class);
  }

  @Provides
  @Singleton
  ChatClient provideChatClient(OkHttpClient okHttpClient)
  {
    return new ChatClient(okHttpClient);
  }

  @Provides
  @Singleton
  MeteorLiteConfig provideMeteorConfig(ConfigManager configManager) {
    return configManager.getConfig(MeteorLiteConfig.class);
  }
}
