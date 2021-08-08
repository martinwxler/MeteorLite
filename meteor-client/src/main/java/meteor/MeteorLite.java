package meteor;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.name.Named;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.util.Objects;
import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import meteor.eventbus.EventBus;
import meteor.eventbus.Subscribe;
import meteor.eventbus.events.ClientShutdown;
import meteor.events.ToggleToolbarEvent;
import net.runelite.api.Client;
import org.sponge.util.Logger;

public class MeteorLite extends Application implements Module {
  public static final File METEOR_DIR = new File(System.getProperty("user.home"), ".meteorlite");
  public static final File CACHE_DIR = new File(METEOR_DIR, "cache");
  public static final File PLUGINS_DIR = new File(METEOR_DIR, "plugin-hub");
  public static final File PROFILES_DIR = new File(METEOR_DIR, "profiles");
  public static final File SCREENSHOT_DIR = new File(METEOR_DIR, "screenshots");
  public static final File LOGS_DIR = new File(METEOR_DIR, "logs");
  public static final File DEFAULT_SESSION_FILE = new File(METEOR_DIR, "session");
  public static final File DEFAULT_CONFIG_FILE = new File(METEOR_DIR, "settings.properties");

  public static OSRSClient mainInstance = new OSRSClient();

  @Provides
  @Named("mainInstance")
  private OSRSClient getMainInstance() {
    return mainInstance;
  }

  public static Client clientInstance; // This is reserved for the Sponge Mixins Agent

  @Override
  public void start(Stage primaryStage) throws IOException {
    System.setProperty("sun.awt.noerasebackground", "true"); // fixes resize flickering
    Guice.createInjector(this).injectMembers(this);
    getMainInstance().start();
  }

  @Override
  public void configure(Binder binder) {

  }
}
