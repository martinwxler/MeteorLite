package meteor.plugins.stretchedmode;

import static org.sponge.util.Logger.ANSI_CYAN;
import static org.sponge.util.Logger.ANSI_YELLOW;

import java.io.IOException;
import java.util.Objects;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javax.inject.Inject;
import meteor.MeteorLite;
import meteor.Plugin;
import meteor.input.MouseManager;
import meteor.ui.TranslateMouseListener;
import meteor.ui.TranslateMouseWheelListener;

public class StretchedModePlugin extends Plugin {

  public static boolean integerScalingEnabled = false;
  public static boolean keepAspectRatio = false;
  public static boolean isStretchedFast = false;
  public static int scalingFactor = 100;
  public static boolean enabled = true;
  public String name = ANSI_CYAN + "StretchedModePlugin" + ANSI_YELLOW;

  @Inject
  private MouseManager mouseManager;

  @Inject
  private TranslateMouseListener mouseListener;

  @Inject
  private TranslateMouseWheelListener mouseWheelListener;

  {
    logger.name = name;
  }

  public static void showConfig() throws IOException {
    Parent configRoot = FXMLLoader.load(Objects.requireNonNull(ClassLoader.getSystemClassLoader()
        .getResource("meteor/plugins/stretchedmode/config.fxml")));
    MeteorLite.updateRightPanel(configRoot);
  }

  @Override
  public void startup() {
    mouseManager.registerMouseListener(0, mouseListener);
    mouseManager.registerMouseWheelListener(0, mouseWheelListener);
    client.setGameDrawingMode(2);
    if (enabled) {
      client.setStretchedEnabled(true);
      updateConfig();
    }
  }

  @Override
  public void shutdown() {
    mouseManager.unregisterMouseListener(mouseListener);
    mouseManager.unregisterMouseWheelListener(mouseWheelListener);
    client.setStretchedEnabled(false);
    updateConfig();
  }

  public void updateConfig() {
    client.setStretchedIntegerScaling(integerScalingEnabled);
    client.setStretchedKeepAspectRatio(keepAspectRatio);
    client.setStretchedFast(isStretchedFast);
    client.setScalingFactor(scalingFactor);
    client.invalidateStretching(true);
  }
}
