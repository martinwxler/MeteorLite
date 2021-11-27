package dev.hoot.api.movement.regions.plugin;

import com.google.inject.Provides;
import dev.hoot.api.movement.pathfinder.GlobalCollisionMap;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.ui.overlay.OverlayManager;
import net.runelite.api.events.ConfigButtonClicked;

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

    public GlobalCollisionMap collisionMap = new GlobalCollisionMap();

    private static final String URL = "http://174.138.15.181:8080/regions";

    @Override
    public void startup() {
        updateCollisionMap();

        overlayManager.add(overlay);
    }

    @Override
    public void shutdown() {
        overlayManager.remove(overlay);
    }

    @Subscribe
    public void onConfigButtonClicked(ConfigButtonClicked e) {
        if (!e.getGroup().equals("region-debug") && !e.getKey().equals("download")) {
            return;
        }

        updateCollisionMap();
    }

    private void updateCollisionMap() {
        try (InputStream is = new URL(URL).openStream()) {
            collisionMap = new GlobalCollisionMap(
                    readGzip(is.readAllBytes())
            );
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
}
