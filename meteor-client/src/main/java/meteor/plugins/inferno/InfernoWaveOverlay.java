package meteor.plugins.inferno;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import lombok.AccessLevel;
import lombok.Setter;
import meteor.plugins.inferno.displaymodes.InfernoWaveDisplayMode;
import meteor.ui.overlay.Overlay;
import meteor.ui.overlay.OverlayPosition;
import meteor.ui.overlay.OverlayPriority;
import meteor.ui.overlay.components.PanelComponent;

import java.awt.*;

import static meteor.plugins.inferno.InfernoWaveMappings.addWaveComponent;

@Singleton
public class InfernoWaveOverlay extends Overlay
{
	private final InfernoPlugin plugin;
	private final InfernoConfig config;
	private final PanelComponent panelComponent;

	@Setter(AccessLevel.PACKAGE)
	private Color waveHeaderColor;

	@Setter(AccessLevel.PACKAGE)
	private Color waveTextColor;

	@Setter(AccessLevel.PACKAGE)
	private InfernoWaveDisplayMode displayMode;

	@Inject
	InfernoWaveOverlay(final InfernoPlugin plugin, final InfernoConfig config)
	{
		this.plugin = plugin;
		this.config = config;
		this.panelComponent = new PanelComponent();
		setPosition(OverlayPosition.TOP_RIGHT);
		setPriority(OverlayPriority.HIGH);
		panelComponent.setPreferredSize(new Dimension(160, 0));
	}

	public Dimension render(final Graphics2D graphics)
	{
		panelComponent.getChildren().clear();

		if (displayMode == InfernoWaveDisplayMode.CURRENT ||
			displayMode == InfernoWaveDisplayMode.BOTH)
		{
			addWaveComponent(
				config,
				panelComponent,
				"Current Wave (Wave " + plugin.getCurrentWaveNumber() + ")",
				plugin.getCurrentWaveNumber(),
				waveHeaderColor,
				waveTextColor
			);
		}

		if (displayMode == InfernoWaveDisplayMode.NEXT ||
			displayMode == InfernoWaveDisplayMode.BOTH)
		{
			addWaveComponent(
				config,
				panelComponent,
				"Next Wave (Wave " + plugin.getNextWaveNumber() + ")",
				plugin.getNextWaveNumber(),
				waveHeaderColor,
				waveTextColor
			);
		}

		return panelComponent.render(graphics);
	}
}
