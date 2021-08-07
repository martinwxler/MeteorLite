package meteor;

import static org.sponge.util.Logger.ANSI_RESET;
import static org.sponge.util.Logger.ANSI_YELLOW;

import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.applet.AudioClip;
import java.awt.BorderLayout;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
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
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.annotation.Nullable;
import javax.inject.Provider;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import meteor.config.ConfigManager;
import meteor.eventbus.EventBus;
import meteor.eventbus.Subscribe;
import meteor.eventbus.events.ClientShutdown;
import meteor.events.ToggleToolbarEvent;
import meteor.ui.overlay.OverlayManager;
import meteor.ui.overlay.WidgetOverlay;
import meteor.ui.overlay.tooltip.TooltipOverlay;
import meteor.ui.overlay.worldmap.WorldMapOverlay;
import net.runelite.api.Client;
import org.sponge.util.Logger;

public class MeteorLite extends Application implements AppletStub, AppletContext {

  public static final File METEOR_DIR = new File(System.getProperty("user.home"), ".meteorlite");
  public static final File CACHE_DIR = new File(METEOR_DIR, "cache");
  public static final File PLUGINS_DIR = new File(METEOR_DIR, "plugin-hub");
  public static final File PROFILES_DIR = new File(METEOR_DIR, "profiles");
  public static final File SCREENSHOT_DIR = new File(METEOR_DIR, "screenshots");
  public static final File LOGS_DIR = new File(METEOR_DIR, "logs");
  public static final File DEFAULT_SESSION_FILE = new File(METEOR_DIR, "session");
  public static final File DEFAULT_CONFIG_FILE = new File(METEOR_DIR, "settings.properties");
  public static Client clientInstance; // This is reserved for the Sponge Mixins Agent
  static MeteorLiteModule module = new MeteorLiteModule();
  public static Injector injector = Guice.createInjector(module);
  public static boolean pluginsPanelVisible = false;
  public static JFXPanel toolbarJFXPanel = new JFXPanel();
  public static JFXPanel hudbarJFXPanel = new JFXPanel();
  public static JFXPanel rightPanel = new JFXPanel();
  public static Logger logger = new Logger("MeteorLite");
  public static JPanel panel;
  public static JPanel gamePanel = new JPanel();
  public static BorderLayout layout = new BorderLayout();
  public static JFrame frame;
  static Parent pluginsRoot;
  static Scene pluginsRootScene;
  private static boolean toolbarVisible = true;
  private static Map<String, String> properties;
  private static Map<String, String> parameters;

  static {
    try {
      pluginsRoot = FXMLLoader.load(
          Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("plugins.fxml")));
      pluginsRootScene = new Scene(pluginsRoot, 350, 800);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Inject
  public EventBus eventBus;
  @Inject
  public ConfigManager configManager;
  @com.google.inject.Inject
  @Nullable
  public Client client;
  Applet applet;
  @Inject
  private Provider<WorldMapOverlay> worldMapOverlay;
  private final PluginManager pluginManager = new PluginManager();
  private long startTime;
  private long loadTime;
  @Inject
  private OverlayManager overlayManager;
  @Inject
  private javax.inject.Provider<TooltipOverlay> tooltipOverlay;

  public MeteorLite() {
  }

  public static void togglePluginsPanel() {
    if (pluginsPanelVisible) {
      rightPanel.setVisible(false);
    } else {
      rightPanel.setScene(pluginsRootScene);
      rightPanel.setVisible(true);
    }

    pluginsPanelVisible = !pluginsPanelVisible;
  }

  public static void updateRightPanel(Parent root) {
    rightPanel.setScene(new Scene(root, 350, 800));
  }

  public static void updateRightPanel(Parent root, int width) {
    rightPanel.setScene(new Scene(root, width, 800));
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
    MeteorLite.properties = properties;
    MeteorLite.parameters = parameters;
  }

  public void start() throws IOException {
    startTime = System.currentTimeMillis();
    loadJagexConfiguration();

    injector.injectMembers(client);
    injector.injectMembers(pluginManager);
    injector.injectMembers(this);
    eventBus.register(this);

    // Add core overlays
    WidgetOverlay.createOverlays(client).forEach(overlayManager::add);
    overlayManager.add(worldMapOverlay.get());

    pluginManager.startInternalPlugins();
    configManager.load();

    overlayManager.add(tooltipOverlay.get());

    applet = (Applet) client;

    applet.setSize(1280, 720);
    setAppletConfiguration(applet);

    setupFrame(applet);

    logger.info(
        ANSI_YELLOW + "MeteorLite started in " + (System.currentTimeMillis() - startTime) + " ms"
            + ANSI_RESET);
  }

  @Override
  public void start(Stage primaryStage) throws IOException {
    System.setProperty("sun.awt.noerasebackground", "true"); // fixes resize flickering
    injector.getInstance(MeteorLite.class).start();
  }

  public void setupFrame(Applet applet) throws IOException {
    frame = new JFrame();
    frame.setSize(1280, 720);
    frame.setMinimumSize(new Dimension(1280, 720));
    panel = new JPanel();
    panel.setLayout(layout);
    panel.setSize(1280, 720);

    toolbarJFXPanel.setSize(1280, 100);
    rightPanel.setSize(550, 800);
    Parent toolbarRoot = FXMLLoader.load(
        Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("toolbar.fxml")));
    Parent hudbarRoot = FXMLLoader.load(
        Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("hudbar.fxml")));

    toolbarJFXPanel.setScene(new Scene(toolbarRoot, 300, 45));
    toolbarJFXPanel.setVisible(true);
    hudbarJFXPanel.setScene(new Scene(hudbarRoot, 300, 75));
    hudbarJFXPanel.setVisible(true);
    rightPanel.setVisible(false);
    gamePanel.setLayout(new BorderLayout());
    gamePanel.add(applet, BorderLayout.CENTER);
    panel.add(gamePanel, BorderLayout.CENTER);
    panel.add(toolbarJFXPanel, BorderLayout.NORTH);
    panel.add(hudbarJFXPanel, BorderLayout.SOUTH);
    panel.add(rightPanel, BorderLayout.EAST);
    frame.add(panel);
    panel.setVisible(true);
    frame.setVisible(true);
    frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    frame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent event) {
        ClientShutdown shutdown = new ClientShutdown();
        eventBus.post(shutdown);
      }
    });
    applet.init();
    applet.start();
  }

  @Subscribe
  public void onToggleToolbar(ToggleToolbarEvent event) {
    if (toolbarVisible) {
      panel.remove(toolbarJFXPanel);
      toolbarVisible = false;
      panel.validate();
    } else {
      panel.add(toolbarJFXPanel, BorderLayout.NORTH);
      toolbarVisible = true;
      panel.validate();
    }
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
}
