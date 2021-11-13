package dev.hoot.api.movement.regions.plugin;

import dev.hoot.api.movement.Movement;
import meteor.ui.overlay.Overlay;

import javax.inject.Inject;
import java.awt.*;

public class RegionOverlay extends Overlay {
	private final RegionPlugin regionPlugin;

	@Inject
	public RegionOverlay(RegionPlugin regionPlugin) {
		this.regionPlugin = regionPlugin;
	}

	@Override
	public Dimension render(Graphics2D graphics) {
		Movement.drawCollisions(graphics, regionPlugin.collisionMap);
		return null;
	}
}
