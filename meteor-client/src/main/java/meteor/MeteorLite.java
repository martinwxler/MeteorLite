package meteor;

import com.google.inject.*;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.events.ToggleToolbarEvent;
import meteor.plugins.BankPin;
import meteor.plugins.EventTestPlugin;
import meteor.plugins.agility.AgilityPlugin;
import meteor.plugins.aoewarnings.AoeWarningPlugin;
import meteor.plugins.combatlevel.CombatLevelPlugin;
import meteor.plugins.gpu.GpuPlugin;
import meteor.plugins.groundmarkers.GroundMarkerPlugin;
import meteor.plugins.mousetooltips.MouseTooltipPlugin;
import meteor.plugins.neverlog.NeverLogoutPlugin;
import meteor.plugins.stretchedmode.StretchedModePlugin;
import meteor.ui.overlay.OverlayManager;
import meteor.ui.overlay.tooltip.TooltipOverlay;
import net.runelite.api.Client;
import org.sponge.util.Logger;
import meteor.eventbus.EventBus;

import javax.annotation.Nullable;
import javax.swing.*;
import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.applet.AudioClip;
import java.awt.*;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.*;
import java.util.List;

import static org.sponge.util.Logger.ANSI_RESET;
import static org.sponge.util.Logger.ANSI_YELLOW;

public class MeteorLite extends Application implements AppletStub, AppletContext {
    public static List<Plugin> plugins = new ArrayList<>();
    public static Client clientInstance; // This is reserved for the Sponge Mixins Agent

    public static Injector injector;

    static MeteorLiteModule module = new MeteorLiteModule();

    public static boolean pluginsPanelVisible = false;
    public static JFXPanel toolbarJFXPanel = new JFXPanel();
    public static JFXPanel hudbarJFXPanel = new JFXPanel();
    public static JFXPanel rightPanel = new JFXPanel();
    private static boolean toolbarVisible = true;

    public static final File METEOR_DIR = new File(System.getProperty("user.home"), ".meteorlite");
    public static final File CACHE_DIR = new File(METEOR_DIR, "cache");
    public static final File PLUGINS_DIR = new File(METEOR_DIR, "plugin-hub");
    public static final File PROFILES_DIR = new File(METEOR_DIR, "profiles");
    public static final File SCREENSHOT_DIR = new File(METEOR_DIR, "screenshots");
    public static final File LOGS_DIR = new File(METEOR_DIR, "logs");
    public static final File DEFAULT_SESSION_FILE = new File(METEOR_DIR, "session");
    public static final File DEFAULT_CONFIG_FILE = new File(METEOR_DIR, "settings.properties");

    @Inject
    public static Logger logger;

    @Inject
    public EventBus eventBus;

    @Inject
    public ConfigManager configManager;

    @com.google.inject.Inject
    @Nullable
    public Client client;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private javax.inject.Provider<TooltipOverlay> tooltipOverlay;

    Applet applet;
    public static JPanel panel;
    public static JPanel gamePanel = new JPanel();
    public static BorderLayout layout = new BorderLayout();
    public static JFrame frame;

    static Parent pluginsRoot;
    static Scene pluginsRootScene;
    static {
        try {
            pluginsRoot = FXMLLoader.load(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("plugins.fxml")));
            pluginsRootScene = new Scene(pluginsRoot, 275, 800);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void start() throws IOException {

        injector.injectMembers(client);
        injector.injectMembers(this);
        eventBus.register(this);

        startPlugins();
        configManager.load();

        overlayManager.add(tooltipOverlay.get());
        logger.info(ANSI_YELLOW + "Guice injection completed" + ANSI_RESET);

        applet = (Applet) client;

        applet.setSize(1280, 720);
        setAppletConfiguration(applet);

        setupFrame(applet);

        logger.info(ANSI_YELLOW + "MeteorLite started" + ANSI_RESET);


    }

    private void startPlugins() {
        MeteorLite.plugins.add(new EventTestPlugin());
        //MeteorLite.plugins.add(new DebugPlugin());
        MeteorLite.plugins.add(new StretchedModePlugin());
        MeteorLite.plugins.add(new GpuPlugin());
        MeteorLite.plugins.add(new AoeWarningPlugin());
        MeteorLite.plugins.add(new NeverLogoutPlugin());
        MeteorLite.plugins.add(new BankPin());
        MeteorLite.plugins.add(new AgilityPlugin());
        MeteorLite.plugins.add(new MouseTooltipPlugin());
        MeteorLite.plugins.add(new CombatLevelPlugin());
        MeteorLite.plugins.add(new GroundMarkerPlugin());
        for (Plugin plugin : MeteorLite.plugins)
        {
            Injector injector = plugin.getInjector();
            if (injector == null)
            {
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
            }
            plugin.init();
            plugin.startup();
        }
    }

    private static Map<String, String> properties;

    private static Map<String, String> parameters;

    public MeteorLite() {
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        System.setProperty("sun.awt.noerasebackground", "true"); // fixes resize flickering

        loadJagexConfiguration();

        injector = Guice.createInjector(module);

        injector.getInstance(MeteorLite.class).start();
    }

    public static void togglePluginsPanel() {
        if (pluginsPanelVisible)
            rightPanel.setVisible(false);
        else
        {
            rightPanel.setScene(pluginsRootScene);
            rightPanel.setVisible(true);
        }


        pluginsPanelVisible = !pluginsPanelVisible;
    }

    public static void updateRightPanel(Parent root)
    {
        rightPanel.setScene(new Scene(root, 275, 800));
    }

    public void setupFrame(Applet applet) throws IOException {
        frame = new JFrame("MeteorLite");
        Image image = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemClassLoader().getResource("MeteorLite_icon.png"));
        frame.setIconImage(image);
        frame.setSize(1280, 720);
        panel = new JPanel();
        panel.setLayout(layout);
        panel.setSize(1280, 720);

        toolbarJFXPanel.setSize(1280, 100);
        rightPanel.setSize(475, 800);
        Parent toolbarRoot = FXMLLoader.load(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("toolbar.fxml")));
        Parent hudbarRoot = FXMLLoader.load(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("hudbar.fxml")));

        toolbarJFXPanel.setScene(new Scene(toolbarRoot, 300, 50));
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
        applet.init();
        applet.start();
        frame.requestFocus();
    }

    @Subscribe
    public void onToggleToolbar(ToggleToolbarEvent event)
    {
        if (toolbarVisible)
        {
            panel.remove(toolbarJFXPanel);
            toolbarVisible = false;
            panel.validate();
        }
        else
        {
            panel.add(toolbarJFXPanel, BorderLayout.NORTH);
            toolbarVisible = true;
            panel.validate();
        }
    }

    public static void loadJagexConfiguration() throws IOException {
        Map<String, String> properties = new HashMap<>();
        Map<String, String> parameters = new HashMap<>();
        URL url = new URL("http://oldschool.runescape.com/jav_config.ws");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.ISO_8859_1))) {
            String line;
            while ((line = reader.readLine()) != null) {
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

    private URL gamepackUrl() throws MalformedURLException {
        return new URL(properties.get("codebase") + properties.get("initial_jar"));
    }

    private String initialClass() {
        return "Client";
    }

    @Override public URL getCodeBase() {
        try {
            return new URL(properties.get("codebase"));
        } catch (MalformedURLException e) {
            throw new InvalidParameterException();
        }
    }

    @Override public URL getDocumentBase() {
        return getCodeBase();
    }

    @Override public boolean isActive() {
        return true;
    }

    @Override public String getParameter(String name) {
        return parameters.get(name);
    }

    @Override public void showDocument(URL url) {
        try {
            Desktop.getDesktop().browse(url.toURI());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override public void showDocument(URL url, String target) {
        showDocument(url);
    }

    @Override public AppletContext getAppletContext() {
        return this;
    }

    @Override public void appletResize(int width, int height) {}

    @Override public AudioClip getAudioClip(URL url) {
        throw new UnsupportedOperationException();
    }

    @Override public Image getImage(URL url) {
        throw new UnsupportedOperationException();
    }

    @Override public Applet getApplet(String name) {
        throw new UnsupportedOperationException();
    }

    @Override public Enumeration<Applet> getApplets() {
        throw new UnsupportedOperationException();
    }

    @Override public void showStatus(String status) {
        throw new UnsupportedOperationException();
    }

    @Override public void setStream(String key, InputStream stream) {
        throw new UnsupportedOperationException();
    }

    @Override public InputStream getStream(String key) {
        throw new UnsupportedOperationException();
    }

    @Override public Iterator<String> getStreamKeys() {
        throw new UnsupportedOperationException();
    }
}
