package meteor;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import java.io.File;
import java.io.IOException;
import javafx.application.Application;
import javafx.stage.Stage;
import net.runelite.api.Client;

public class MeteorLiteClientLauncher extends Application implements Module {
  public static final File METEOR_DIR
      = new File(System.getProperty("user.home"), ".meteorlite");
  public static final File CACHE_DIR = new File(METEOR_DIR, "cache");
  public static final File PLUGINS_DIR = new File(METEOR_DIR, "plugin-hub");
  public static final File PROFILES_DIR = new File(METEOR_DIR, "profiles");
  public static final File SCREENSHOT_DIR = new File(METEOR_DIR, "screenshots");
  public static final File LOGS_DIR = new File(METEOR_DIR, "logs");
  public static final File DEFAULT_SESSION_FILE = new File(METEOR_DIR, "session");
  public static final File DEFAULT_CONFIG_FILE = new File(METEOR_DIR, "settings.properties");

  public static MeteorLiteClientModule mainClientInstance;
  public static Injector mainInjectorInstance;

  public static Client clientInstance; // This is reserved for the Sponge Mixins Agent

  @Override
  public void start(Stage primaryStage) throws IOException {
    mainInjectorInstance = Guice.createInjector(meteorLite());
    mainInjectorInstance.injectMembers(meteorLite());
    mainClientInstance = getNewOSRSInstance();
    mainClientInstance.start();
    //MeteorLiteClientModule secondInstance = getNewOSRSInstance();
    //secondInstance.start();
  }

  private MeteorLiteClientModule getNewOSRSInstance() {
    return new MeteorLiteClientModule();
  }

  @Override
  public void configure(Binder binder) {

  }

  public MeteorLiteClientLauncher meteorLite() {
    return this;
  }
}
