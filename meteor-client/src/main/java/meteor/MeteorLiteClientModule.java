package meteor;

import static meteor.MeteorLiteClientLauncher.DEFAULT_CONFIG_FILE;
import static org.sponge.util.Logger.ANSI_RESET;
import static org.sponge.util.Logger.ANSI_YELLOW;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provides;
import com.google.inject.name.Names;
import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.applet.AudioClip;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.google.inject.util.Providers;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javax.annotation.Nullable;
import javax.inject.Named;
import javax.inject.Provider;
import javax.inject.Singleton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import meteor.callback.Hooks;
import meteor.chat.ChatMessageManager;
import meteor.config.ChatColorConfig;
import meteor.config.ConfigManager;
import meteor.config.RuneLiteConfig;
import meteor.discord.DiscordService;
import meteor.eventbus.DeferredEventBus;
import meteor.eventbus.EventBus;
import meteor.eventbus.Subscribe;
import meteor.eventbus.events.ClientShutdown;
import meteor.events.ExternalsReloaded;
import meteor.game.WorldService;
import meteor.plugins.api.game.*;
import meteor.plugins.api.movement.Movement;
import meteor.plugins.itemstats.ItemStatChangesService;
import meteor.plugins.itemstats.ItemStatChangesServiceImpl;
import meteor.plugins.meteor.meteorlite.MeteorLiteConfig;
import meteor.ui.controllers.ToolbarFXMLController;
import meteor.ui.overlay.OverlayManager;
import meteor.ui.overlay.WidgetOverlay;
import meteor.ui.overlay.tooltip.TooltipOverlay;
import meteor.ui.overlay.worldmap.WorldMapOverlay;
import meteor.util.ExecutorServiceExceptionLogger;
import meteor.util.NonScheduledExecutorServiceExceptionLogger;
import meteor.util.WorldUtil;
import net.runelite.api.Client;
import net.runelite.api.Constants;
import net.runelite.api.events.GameTick;
import net.runelite.api.hooks.Callbacks;
import net.runelite.http.api.chat.ChatClient;
import net.runelite.http.api.worlds.World;
import net.runelite.http.api.worlds.WorldResult;
import okhttp3.OkHttpClient;
import org.sponge.util.Logger;

public class MeteorLiteClientModule extends AbstractModule implements AppletStub, AppletContext {

  public static String uuid = UUID.randomUUID().toString();
  public static JFrame mainWindow;

  public static final int TOOLBAR_HEIGHT = 33;
  public static final int RIGHT_PANEL_WIDTH = 350;

  private static final int CLIENT_WIDTH = Constants.GAME_FIXED_WIDTH + (Constants.GAME_FIXED_WIDTH - 749);
  private static final int CLIENT_HEIGHT = Constants.GAME_FIXED_HEIGHT + (Constants.GAME_FIXED_HEIGHT - 464) + TOOLBAR_HEIGHT;
  private static final Dimension CLIENT_SIZE = new Dimension(CLIENT_WIDTH, CLIENT_HEIGHT);

  @Inject
  private EventBus eventBus;

  @Inject
  private PluginManager pluginManager;

  @Inject
  private OverlayManager overlayManager;

  @Inject
  private ConfigManager configManager;

  @Inject
  private Provider<WorldMapOverlay> worldMapOverlay;

  @Inject
  private Provider<TooltipOverlay> tooltipOverlay;

  @Inject
  private MeteorLiteClientModule meteorLiteClientModule;

  private Logger log = new Logger("MeteorLiteClient");

  @Inject
  private Client client;

  @Inject
  private Applet applet;

  @Inject
  private WorldService worldService;

  @Inject
  private DiscordService discordService;

  private static MeteorLiteConfig meteorLiteConfig;

  private static Map<String, String> properties;
  private static Map<String, String> parameters;

  public Injector instanceInjector;
  public static Injector instanceInjectorStatic; //this is so bad
  public static BorderLayout borderLayout = new BorderLayout();
  private static boolean toolbarVisible = true;
  private static JFXPanel rightPanel = new JFXPanel();
  private static Scene pluginsRootScene;
  public static boolean pluginsPanelVisible = false;
  static Parent pluginsRoot;
  static Parent toolbarRoot;

  static JPanel rootPanel = new JPanel();

  @Provides
  @Named("developerMode")
  private boolean getDeveloperMode() {
    return false;
  }

  @Subscribe
  public void onExternalsReloaded(ExternalsReloaded e) {
    pluginManager.startExternals();
  }

  @Subscribe
  public void onGameTick(GameTick event) {
    //This fixes bad drawing
    if (client.getGameDrawingMode() != 2) {
      client.setGameDrawingMode(2);
    }

    if (client.getLocalPlayer().isIdle())
      ToolbarFXMLController.idleButtonInstance.setVisible(true);
    else
      ToolbarFXMLController.idleButtonInstance.setVisible(false);
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

  @Provides
  @Named("rightPanelScene")
  public Scene getRightPanel() {
    return pluginsRootScene;
  }

  public void start() throws IOException {
    long startTime = System.currentTimeMillis();

    loadJagexConfiguration();

    instanceInjector = Guice.createInjector(this);
    instanceInjectorStatic = instanceInjector;
    instanceInjector.injectMembers(this);
    instanceInjector.injectMembers(pluginManager);
    instanceInjector.injectMembers(client);
    eventBus.register(this);

    discordService.init();

    Collection<WidgetOverlay> overlays = WidgetOverlay.createOverlays(client);
    overlays.forEach((overlay) -> {
      instanceInjector.injectMembers(overlay);
      overlayManager.add(overlay);
    });

    overlayManager.add(worldMapOverlay.get());

    overlayManager.add(tooltipOverlay.get());

    applet = (Applet) client;
    applet.setMinimumSize(Constants.GAME_FIXED_SIZE);
    setAppletConfiguration(applet);

    //Early init game panel so gpu doesn't eat shit when enabling
    JPanel gamePanel = new JPanel();
    rootPanel.setLayout(new BorderLayout());
    gamePanel.setMinimumSize(Constants.GAME_FIXED_SIZE);
    toolbarRoot = FXMLLoader.load(
            Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("toolbar.fxml")));

    gamePanel.setLayout(new BorderLayout());
    gamePanel.add(applet, BorderLayout.CENTER);
    rootPanel.add(gamePanel, BorderLayout.CENTER);
    rootPanel.setMinimumSize(Constants.GAME_FIXED_SIZE);

    configManager.load();
    pluginManager.startInternalPlugins();

    // preload plugins scene, needs to be after pluginManager.startInternalPlugins() is called.
    pluginsRootScene = new Scene(FXMLLoader.load(
            Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("plugins.fxml"))), 350, 800);
    rightPanel.setScene(pluginsRootScene);

    // This is shit, but needs to be done until UI is reworked
    meteorLiteConfig = (MeteorLiteConfig) PluginManager.getInstance("MeteorLite").getConfig(configManager);

    log.info(
            ANSI_YELLOW + "OSRS instance started in " + (System.currentTimeMillis() - startTime) + " ms"
                    + ANSI_RESET);

    setupJavaFXComponents(applet);
  }

  public static void toggleRightPanel() throws IOException {
    if (pluginsPanelVisible) {
      rightPanel.setVisible(false);
    } else {
      rightPanel.setVisible(true);
    }

    pluginsPanelVisible = !pluginsPanelVisible;
    if (pluginsPanelVisible) {
      try {
        // if maximized, dont resize
        if (mainWindow.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
          return;
        }

        // if panel would extend past screen, dont resize
        Dimension currentSize = mainWindow.getSize();
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        if (currentSize.getWidth() + RIGHT_PANEL_WIDTH > screenSize.getWidth()) {
          return;
        }

        // If resizing the game would go below the minimum size, always extend panel.
        if (mainWindow.getWidth() < CLIENT_WIDTH + RIGHT_PANEL_WIDTH) {
          mainWindow.setSize(new Dimension(mainWindow.getWidth() + RIGHT_PANEL_WIDTH, mainWindow.getHeight()));
          return;
        }

        if (meteorLiteConfig.resizeGame()) {
          return;
        }

        // if current client size is less than window size, but showing the panel would go past the screen, set size equal to screen size.
        Dimension newClientSize = new Dimension(mainWindow.getWidth() + RIGHT_PANEL_WIDTH, mainWindow.getHeight());
        if (newClientSize.getWidth() > screenSize.getWidth()) {
          newClientSize = screenSize;
          mainWindow.setExtendedState(JFrame.MAXIMIZED_BOTH);
        }
        mainWindow.setSize(newClientSize);
      } finally {
        mainWindow.setMinimumSize(new Dimension(CLIENT_WIDTH + RIGHT_PANEL_WIDTH, CLIENT_HEIGHT));
        mainWindow.validate();
      }
    } else {
      setMinimumFrameSize();
    }
  }

  private static void setMinimumFrameSize() {
    // if resize game is checked, and we are at the minimum size, still resize.
    boolean resize = mainWindow.getMinimumSize().equals(mainWindow.getSize());
    mainWindow.setMinimumSize(CLIENT_SIZE);

    // if maximized, dont resize
    if (mainWindow.getExtendedState() == JFrame.MAXIMIZED_BOTH) {
      return;
    }

    // if size is the same as screen size, dont resize
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    if (mainWindow.getSize().equals(screenSize)) {
      return;
    }

    if (!meteorLiteConfig.resizeGame()) {
      mainWindow.setSize(new Dimension(mainWindow.getWidth() - RIGHT_PANEL_WIDTH, mainWindow.getHeight()));
    }
    if (resize) {
      mainWindow.setSize(mainWindow.getMinimumSize());
    }
    mainWindow.validate();
  }

  public static void showPlugins() {
    if (rightPanel.getScene() == null || !rightPanel.getScene().equals(pluginsRootScene)) {
      rightPanel.setScene(pluginsRootScene);
    }
  }

  public static void updateRightPanel(Parent root, int width) {
    rightPanel.setScene(new Scene(root, width, 800));
  }

  public static void setupJavaFXComponents(Applet applet) throws IOException {

    mainWindow = new JFrame();
    setMinimumFrameSize();
    JFXPanel toolbarPanel = new JFXPanel();
    toolbarPanel.setSize(1280, 100);
    rightPanel.setSize(550, 800);

    toolbarPanel.setScene(new Scene(toolbarRoot, 300, 33));
    toolbarPanel.setVisible(true);
    rightPanel.setVisible(false);

    rootPanel.add(toolbarPanel, BorderLayout.NORTH);
    rootPanel.add(rightPanel, BorderLayout.EAST);
    mainWindow.add(rootPanel);
    rootPanel.setVisible(true);
    mainWindow.setVisible(true);
    mainWindow.setExtendedState(mainWindow.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    mainWindow.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    mainWindow.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent event) {
        ClientShutdown shutdown = new ClientShutdown();
        MeteorLiteClientLauncher.mainInjectorInstance.getInstance(EventBus.class).post(shutdown);
      }
    });
    applet.init();
    applet.start();
  }

  public static void loadJagexConfiguration() throws IOException {
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

  public Applet setAppletConfiguration(Applet applet) {
    applet.setStub(this);
    applet.setMaximumSize(appletMaxSize());
    applet.setMinimumSize(appletMinSize());
    applet.setPreferredSize(applet.getMinimumSize());
    return applet;
  }

  public String title() {
    return properties.get("title");
  }

  private Dimension appletMinSize() {
    return new Dimension(
            Integer.parseInt(properties.get("applet_minwidth")),
            Integer.parseInt(properties.get("applet_minheight"))
    );
  }

  private Dimension appletMaxSize() {
    return new Dimension(
            Integer.parseInt(properties.get("applet_maxwidth")),
            Integer.parseInt(properties.get("applet_maxheight"))
    );
  }

  @Override
  public URL getCodeBase() {
    try {
      return new URL(properties.get("codebase"));
    } catch (MalformedURLException e) {
      throw new InvalidParameterException();
    }
  }

  @Override
  public URL getDocumentBase() {
    return getCodeBase();
  }

  @Override
  public boolean isActive() {
    return true;
  }

  @Override
  public String getParameter(String name) {
    return parameters.get(name);
  }

  @Override
  public void showDocument(URL url) {
    try {
      Desktop.getDesktop().browse(url.toURI());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  @Override
  public void showDocument(URL url, String target) {
    showDocument(url);
  }

  @Override
  public AppletContext getAppletContext() {
    return this;
  }

  @Override
  public void appletResize(int width, int height) {
  }

  @Override
  public AudioClip getAudioClip(URL url) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Image getImage(URL url) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Applet getApplet(String name) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Enumeration<Applet> getApplets() {
    throw new UnsupportedOperationException();
  }

  @Override
  public void showStatus(String status) {
    throw new UnsupportedOperationException();
  }

  @Override
  public void setStream(String key, InputStream stream) {
    throw new UnsupportedOperationException();
  }

  @Override
  public InputStream getStream(String key) {
    throw new UnsupportedOperationException();
  }

  @Override
  public Iterator<String> getStreamKeys() {
    throw new UnsupportedOperationException();
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
            Movement.class,
            Game.class
    );
  }

  @com.google.inject.name.Named("config")
  @Provides
  @javax.inject.Singleton
  File provideMeteorLiteConfig() {
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
}
