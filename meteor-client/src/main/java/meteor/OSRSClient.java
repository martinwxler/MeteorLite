package meteor;

import static org.sponge.util.Logger.ANSI_RESET;
import static org.sponge.util.Logger.ANSI_YELLOW;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provides;
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javax.inject.Named;
import javax.inject.Provider;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import meteor.config.ConfigManager;
import meteor.eventbus.EventBus;
import meteor.eventbus.events.ClientShutdown;
import meteor.ui.overlay.OverlayManager;
import meteor.ui.overlay.WidgetOverlay;
import meteor.ui.overlay.tooltip.TooltipOverlay;
import meteor.ui.overlay.worldmap.WorldMapOverlay;
import net.runelite.api.Client;
import org.sponge.util.Logger;

public class OSRSClient  implements Module, AppletStub, AppletContext {

  public static List<OSRSClient> clientInstances = new ArrayList<>();

  public static JFrame mainInstanceFrame = new JFrame();

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
  private Logger logger;

  @Inject
  private Client client;

  @Inject
  private Applet applet;

  private static Map<String, String> properties;
  private static Map<String, String> parameters;

  static MeteorLiteModule meteorLiteModule = new MeteorLiteModule();
  public Injector instanceInjector = Guice.createInjector(meteorLiteModule);
  private static Injector staticInjectorInstance;
  public static BorderLayout borderLayout = new BorderLayout();
  private static boolean toolbarVisible = true;
  private static JFXPanel rightPanel = new JFXPanel();
  private static Scene pluginsRootScene;
  public static boolean pluginsPanelVisible = false;

  @Provides
  @Named("rightPanelScene")
  public Scene getRightPanel() {
    return pluginsRootScene;
  }

  OSRSClient() {
    try {
      clientInstances.add(this);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public void start() throws IOException {
    staticInjectorInstance = instanceInjector;
    long startTime = System.currentTimeMillis();
    loadJagexConfiguration();

    Module osrsClientModule = (Binder binder) ->
    {
      // Since the plugin itself is a module, it won't bind itself, so we'll bind it here
      binder.bind((Class<OSRSClient>) this.getClass()).toInstance(this);
      binder.install(this);
    };
    Injector osrsClientModuleInjector = instanceInjector.createChildInjector(osrsClientModule);
    instanceInjector = osrsClientModuleInjector;
    instanceInjector.injectMembers(client);
    instanceInjector.injectMembers(pluginManager);
    instanceInjector.injectMembers(this);
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

    setupInstanceFrame(applet);

    logger.info(
        ANSI_YELLOW + "OSRS instance started in " + (System.currentTimeMillis() - startTime) + " ms"
            + ANSI_RESET);
  }

  public static void togglePluginsPanel(Scene pluginsScene) {
    if (pluginsPanelVisible) {
      rightPanel.setVisible(false);
    } else {
      rightPanel.setScene(pluginsScene);
      rightPanel.setVisible(true);
    }

    pluginsPanelVisible = !pluginsPanelVisible;
  }

  public static void updateRightPanel(Parent root, int width) {
    rightPanel.setScene(new Scene(root, width, 800));
  }

  public static void setupInstanceFrame(Applet applet) throws IOException {

    JFrame instanceFrame = new JFrame();
    if (mainInstanceFrame == null)
      mainInstanceFrame = instanceFrame;
    instanceFrame.setSize(1280, 720);
    instanceFrame.setMinimumSize(new Dimension(1280, 720));

    JPanel rootPanel = new JPanel();
    rootPanel.setLayout(new BorderLayout());

    JFXPanel toolbarPanel = new JFXPanel();
    toolbarPanel.setSize(1280, 100);
    rightPanel.setSize(550, 800);
    Parent pluginsRoot = FXMLLoader.load(
        Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("plugins.fxml")));
    pluginsRootScene = new Scene(pluginsRoot, 350, 800);
    Parent toolbarRoot = FXMLLoader.load(
        Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("toolbar.fxml")));
    Parent hudbarRoot = FXMLLoader.load(
        Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("hudbar.fxml")));

    JPanel gamePanel = new JPanel();
    JFXPanel hudbarPanel = new JFXPanel();

    toolbarPanel.setScene(new Scene(toolbarRoot, 300, 33));
    toolbarPanel.setVisible(true);
    hudbarPanel.setScene(new Scene(hudbarRoot, 300, 75));
    hudbarPanel.setVisible(true);
    rightPanel.setVisible(false);
    gamePanel.setLayout(new BorderLayout());
    gamePanel.add(applet, BorderLayout.CENTER);
    rootPanel.add(gamePanel, BorderLayout.CENTER);
    rootPanel.add(toolbarPanel, BorderLayout.NORTH);
    rootPanel.add(hudbarPanel, BorderLayout.SOUTH);
    rootPanel.add(rightPanel, BorderLayout.EAST);
    instanceFrame.add(rootPanel);
    rootPanel.setVisible(true);
    instanceFrame.setVisible(true);
    instanceFrame.setExtendedState(instanceFrame.getExtendedState() | JFrame.MAXIMIZED_BOTH);
    instanceFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    instanceFrame.addWindowListener(new WindowAdapter() {
      @Override
      public void windowClosing(WindowEvent event) {
        ClientShutdown shutdown = new ClientShutdown();
        staticInjectorInstance.getInstance(EventBus.class).post(shutdown);
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
    OSRSClient.properties = properties;
    OSRSClient.parameters = parameters;
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
  public void configure(Binder binder) {

  }
}
