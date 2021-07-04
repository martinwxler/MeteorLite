package osrs;/*
 * ISC License
 *
 * Copyright (c) 2017-2019, Hunter WB <hunterwb.com>
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR
 * ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF
 * OR IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

import com.google.inject.*;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import meteor.plugins.aoewarnings.AoeWarningPlugin;
import meteor.plugins.neverlog.NeverLogoutPlugin;
import net.runelite.api.Client;
import org.sponge.util.Logger;
import meteor.Plugin;
import meteor.MeteorLite;
import meteor.MeteorLiteModule;
import meteor.plugins.EventTestPlugin;
import meteor.plugins.gpu.GpuPlugin;
import meteor.plugins.stretchedmode.StretchedModePlugin;

import javax.annotation.Nullable;
import javax.swing.*;
import java.applet.Applet;
import java.applet.AppletContext;
import java.applet.AppletStub;
import java.applet.AudioClip;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.InvalidParameterException;
import java.util.*;

import static org.sponge.util.Logger.ANSI_RESET;
import static org.sponge.util.Logger.ANSI_YELLOW;

@SuppressWarnings("deprecation")
public final class Launcher extends Application implements AppletStub, AppletContext {

    public static Injector injector;

    static MeteorLiteModule module = new MeteorLiteModule();

    public static boolean pluginsPanelVisible = false;
    public static JFXPanel toolbarJFXPanel = new JFXPanel();
    public static JFXPanel rightPanel = new JFXPanel();

    @Inject
    public Logger logger;

    @com.google.inject.Inject
    @Nullable
    private Client client;

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

        startPlugins();

        logger.info(ANSI_YELLOW + "Guice injection completed" + ANSI_RESET);

        applet = (Applet) client;

        applet.setSize(1280, 720);
        setAppletConfiguration(applet);

        setupFrame(applet);

        logger.info(ANSI_YELLOW + "SpongeOSRS started" + ANSI_RESET);


    }

    private void startPlugins() {
        MeteorLite.plugins.add(new EventTestPlugin());
        //SpongeOSRS.plugins.add(new DebugPlugin());
        MeteorLite.plugins.add(new StretchedModePlugin());
        MeteorLite.plugins.add(new GpuPlugin());
        MeteorLite.plugins.add(new AoeWarningPlugin());
        MeteorLite.plugins.add(new NeverLogoutPlugin());
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
                Injector pluginInjector = Launcher.injector.createChildInjector(pluginModule);
                pluginInjector.injectMembers(plugin);
                plugin.setInjector(pluginInjector);
            }
            plugin.init();
            plugin.startup();
        }
    }

    private static Map<String, String> properties;

    private static Map<String, String> parameters;

    public Launcher() {
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        System.setProperty("sun.awt.noerasebackground", "true"); // fixes resize flickering

        loadJagexConfiguration();

        injector = Guice.createInjector(module);

        injector.getInstance(Launcher.class).start();
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
        frame = new JFrame("SpongeOSRS");
        Image image = Toolkit.getDefaultToolkit().getImage(ClassLoader.getSystemClassLoader().getResource("MeteorLite_icon.png"));
        frame.setIconImage(image);
        frame.setSize(1280, 720);
        panel = new JPanel();
        panel.setLayout(layout);
        panel.setSize(1280, 720);

        toolbarJFXPanel.setSize(1280, 100);
        rightPanel.setSize(475, 800);
        Parent toolbarRoot = FXMLLoader.load(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("toolbar.fxml")));

        toolbarJFXPanel.setScene(new Scene(toolbarRoot, 300, 40));
        toolbarJFXPanel.setVisible(true);
        rightPanel.setVisible(false);
        gamePanel.setLayout(new BorderLayout());
        gamePanel.add(applet, BorderLayout.CENTER);
        panel.add(gamePanel, BorderLayout.CENTER);
        panel.add(toolbarJFXPanel, BorderLayout.NORTH);
        panel.add(rightPanel, BorderLayout.EAST);
        frame.add(panel);
        panel.setVisible(true);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        applet.init();
        applet.start();
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
        Launcher.properties = properties;
        Launcher.parameters = parameters;
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