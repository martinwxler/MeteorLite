package sponge.plugins.stretchedmode;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import osrs.Launcher;
import sponge.Plugin;
import sponge.input.MouseManager;
import sponge.ui.TranslateMouseListener;
import sponge.ui.TranslateMouseWheelListener;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Objects;

import static org.sponge.util.Logger.ANSI_CYAN;
import static org.sponge.util.Logger.ANSI_YELLOW;

public class StretchedModePlugin extends Plugin
{
    public String name = ANSI_CYAN + "StretchedModePlugin" + ANSI_YELLOW;
    {
        logger.name = name;
    }

    @Inject
    private TranslateMouseListener mouseListener;

    @Inject
    private TranslateMouseWheelListener mouseWheelListener;

    public static boolean integerScalingEnabled = false;
    public static boolean keepAspectRatio = false;
    public static boolean isStretchedFast = false;
    public static int scalingFactor = 100;

    public static boolean enabled = false;

    @Override
    public void onStartup()
    {
        mouseManager.registerMouseListener(0, mouseListener);
        mouseManager.registerMouseWheelListener(0, mouseWheelListener);
        client.setGameDrawingMode(2);
        if (enabled)
        {
            client.setStretchedEnabled(true);
            updateConfig();
        }
    }

    @Override
    public void onShutdown()
    {
        mouseManager.unregisterMouseListener(mouseListener);
        mouseManager.unregisterMouseWheelListener(mouseWheelListener);
        client.setStretchedEnabled(false);
        updateConfig();
    }

    public static void showConfig() throws IOException {
        Parent configRoot = FXMLLoader.load(Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResource("sponge/plugins/stretchedmode/config.fxml")));
        Launcher.updateRightPanel(configRoot);
    }

    public void updateConfig()
    {
        client.setStretchedIntegerScaling(integerScalingEnabled);
        client.setStretchedKeepAspectRatio(keepAspectRatio);
        client.setStretchedFast(isStretchedFast);
        client.setScalingFactor(scalingFactor);
        client.invalidateStretching(true);
    }
}
