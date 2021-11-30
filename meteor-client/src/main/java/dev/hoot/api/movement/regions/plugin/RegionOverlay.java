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
	private final RegionConfig regionConfig;

	@Inject
	public RegionOverlay(RegionPlugin regionPlugin, RegionConfig regionConfig) {
		this.regionPlugin = regionPlugin;
		this.regionConfig = regionConfig;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
		setPriority(OverlayPriority.LOW);
	}

	@Override
	public Dimension render(Graphics2D graphics) {
		if (!regionConfig.overlay()) {
			return null;
		}

		Movement.drawCollisions(graphics, regionPlugin.collisionMap);
		return null;
	}
}
