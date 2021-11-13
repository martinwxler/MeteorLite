package dev.hoot.api.movement.regions.plugin;

import dev.hoot.api.movement.pathfinder.GlobalCollisionMap;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.ui.overlay.OverlayManager;

import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.GZIPInputStream;

@PluginDescriptor(name = "Region debug")
public class RegionPlugin extends Plugin {
	@Inject
	private OverlayManager overlayManager;

	@Inject
	private RegionOverlay overlay;

	public GlobalCollisionMap collisionMap = new GlobalCollisionMap();

	@Override
	public void startup() {
		try {
			collisionMap = new GlobalCollisionMap(
							new GZIPInputStream(
											new ByteArrayInputStream(
															Files.readAllBytes(Path.of(System.getProperty("user.home") + "/Downloads/regions"))
											)
							).readAllBytes()
			);
		} catch (IOException e) {
			e.printStackTrace();
		}

		overlayManager.add(overlay);
	}

	@Override
	public void shutdown() {
		overlayManager.remove(overlay);
	}
}
