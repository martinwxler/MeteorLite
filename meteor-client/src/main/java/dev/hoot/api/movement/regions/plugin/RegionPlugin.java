package dev.hoot.api.movement.regions.plugin;

import com.google.inject.Provides;
import dev.hoot.api.movement.pathfinder.GlobalCollisionMap;
import dev.hoot.api.movement.regions.RegionManager;
import lombok.Getter;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.ui.overlay.OverlayManager;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.ConfigButtonClicked;
import okhttp3.OkHttpClient;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.zip.GZIPInputStream;

@PluginDescriptor(name = "Region debug")
public class RegionPlugin extends Plugin {
    @Inject
    private OverlayManager overlayManager;

    @Inject
    private RegionOverlay overlay;

    @Inject
    private OkHttpClient okHttpClient;

    @Inject
    private RegionConfig regionConfig;

    public GlobalCollisionMap collisionMap = new GlobalCollisionMap();

    public static boolean selectingSourceTile = false;
    public static boolean selectingDestinationTile = false;
    public static boolean selectingObject = false;

    private AddTransportDialog transportDialog;

    @Override
    public void startup() {
        if (transportDialog == null) {
            transportDialog = new AddTransportDialog(okHttpClient, regionConfig);
        }

        eventBus.register(transportDialog);

        updateCollisionMap();

        overlayManager.add(overlay);
    }

    @Override
    public void shutdown() {
        overlayManager.remove(overlay);
        eventBus.unregister(transportDialog);
    }

    @Subscribe
    public void onClientTick(ClientTick e) {
        if (selectingSourceTile) {
            client.setLeftClickMenuEntry(new MenuEntry("Set", "<col=00ff00>Source tile", TileSelection.SOURCE.id, -1, -1,
                    -1, false));
            return;
        }

        if (selectingDestinationTile) {
            client.setLeftClickMenuEntry(new MenuEntry("Set", "<col=00ff00>Destination tile",
                    TileSelection.DESTINATION.id, -1, -1, -1, false));
            return;
        }

        if (selectingObject) {
            client.setLeftClickMenuEntry(new MenuEntry("Select", "<col=00ff00>Transport object",
                    TileSelection.OBJECT.id, -1, -1, -1, false));
        }
    }

    @Subscribe
    public void onConfigButtonClicked(ConfigButtonClicked e) {
        if (!e.getGroup().equals("region-debug")) {
            return;
        }

        switch (e.getKey()) {
            case "download":
                updateCollisionMap();
                break;
            case "transport":
                if (transportDialog == null) {
                    logger.error("Add transport UI was not loaded somehow");
                    return;
                }

                transportDialog.display();
                break;
        }
    }

    private void updateCollisionMap() {
        try (InputStream is = new URL(RegionManager.API_URL + "/regions").openStream()) {
            collisionMap = new GlobalCollisionMap(readGzip(is.readAllBytes()));
        } catch (IOException e) {
            logger.error("Error downloading collision data: {}", e.getMessage());
        }
    }

    private byte[] readGzip(byte[] input) throws IOException {
        return new GZIPInputStream(new ByteArrayInputStream(input)).readAllBytes();
    }

    @Override
    @Provides
    public RegionConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(RegionConfig.class);
    }

    enum TileSelection {
        SOURCE(-420),
        DESTINATION(-421),
        OBJECT(-422);

        @Getter
        private final int id;

        TileSelection(int id) {
            this.id = id;
        }
    }
}
