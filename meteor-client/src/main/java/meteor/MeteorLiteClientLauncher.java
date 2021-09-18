package meteor;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

import javafx.application.Application;
import javafx.stage.Stage;
import meteor.util.LoggerStream;
import net.runelite.api.Client;

public class MeteorLiteClientLauncher extends Application implements Module {
  public static final File METEOR_DIR
      = new File(System.getProperty("user.home"), ".meteorlite");
  public static final File CACHE_DIR = new File(METEOR_DIR, "cache");
  public static final File PLUGINS_DIR = new File(METEOR_DIR, "plugin-hub");
  public static final File PROFILES_DIR = new File(METEOR_DIR, "profiles");
  public static final File SCREENSHOT_DIR = new File(METEOR_DIR, "screenshots");
  public static final File VERBOSE_LOG = new File(new File(METEOR_DIR, "logs"), "log.txt");
  public static final File ERROR_LOG = new File(new File(METEOR_DIR, "logs"), "error.txt");
  public static final File DEFAULT_SESSION_FILE = new File(METEOR_DIR, "session");
  public static final File DEFAULT_CONFIG_FILE = new File(METEOR_DIR, "settings.properties");
  public static PrintStream consoleStream = null;
  public static LoggerStream verboseFileStream = null;
  public static LoggerStream errorFileStream = null;

  public static Client clientInstance; // This is reserved for the Sponge Mixins Agent

  public static Injector injector; //this is so bad

  @Override
  public void start(Stage primaryStage) throws IOException, InterruptedException, InvocationTargetException {
    try {
      consoleStream = System.out;
      errorFileStream = new LoggerStream(new FileOutputStream(ERROR_LOG));
      verboseFileStream = new LoggerStream(new FileOutputStream(VERBOSE_LOG));
      errorFileStream.error = true;
      System.setErr(errorFileStream);
      System.setOut(verboseFileStream);
    } catch (FileNotFoundException ex) {
      ex.printStackTrace();
    }

    MeteorLiteClientModule module = new MeteorLiteClientModule();
    injector = Guice.createInjector(module);
    module.start();
  }

  @Override
  public void configure(Binder binder) {

  }
}
