package dev.hoot.api.movement.regions.plugin;

import dev.hoot.api.movement.Movement;
import meteor.ui.overlay.Overlay;
import meteor.ui.overlay.OverlayLayer;
import meteor.ui.overlay.OverlayPosition;
import meteor.ui.overlay.OverlayPriority;

import javax.inject.Inject;
import java.awt.*;

public class RegionOverlay extends Overlay {
	private final RegionPlugin regionPlugin;

	@Inject
	public RegionOverlay(RegionPlugin regionPlugin) {
		this.regionPlugin = regionPlugin;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
		setPriority(OverlayPriority.LOW);
	}

	@Override
	public Dimension render(Graphics2D graphics) {
		Movement.drawCollisions(graphics, regionPlugin.collisionMap);
		return null;
	}
}
