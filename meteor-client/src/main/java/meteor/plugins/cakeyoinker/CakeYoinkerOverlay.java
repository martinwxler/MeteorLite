package meteor.plugins.cakeyoinker;

import meteor.ui.overlay.OverlayMenuEntry;
import meteor.ui.overlay.OverlayPanel;
import meteor.ui.overlay.OverlayPosition;
import meteor.ui.overlay.components.TableAlignment;
import meteor.ui.overlay.components.TableComponent;
import meteor.ui.overlay.components.TitleComponent;
import meteor.util.ColorUtil;
import net.runelite.api.Client;


import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;
import java.time.Duration;
import java.time.Instant;

import static net.runelite.api.MenuAction.RUNELITE_OVERLAY_CONFIG;
import static meteor.ui.overlay.OverlayManager.OPTION_CONFIGURE;
import static org.apache.commons.lang3.time.DurationFormatUtils.formatDuration;

@Singleton
class CakeYoinkerOverlay extends OverlayPanel
{
	private final CakeYoinkerPlugin plugin;
	private final CakeYoinkerConfiguration config;

	String timeFormat;
	private String infoStatus = "Starting...";

	@Inject
	public CakeYoinkerOverlay(final Client client, final CakeYoinkerPlugin plugin, final CakeYoinkerConfiguration config)
	{
		super(plugin);
		setPosition(OverlayPosition.BOTTOM_LEFT);
		this.plugin = plugin;
		this.config = config;
		getMenuEntries().add(new OverlayMenuEntry(RUNELITE_OVERLAY_CONFIG, OPTION_CONFIGURE, "rune Maker overlay"));
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (plugin.botTimer == null || !plugin.startCakeYoinker || !config.enableUI())
		{

			return null;
		}
		TableComponent tableComponent = new TableComponent();
		tableComponent.setColumnAlignments(TableAlignment.LEFT, TableAlignment.RIGHT);

		Duration duration = Duration.between(plugin.botTimer, Instant.now());
		timeFormat = (duration.toHours() < 1) ? "mm:ss" : "HH:mm:ss";
		tableComponent.addRow("Time running:", formatDuration(duration.toMillis(), timeFormat));
		if (plugin.state != null)
		{
			if (!plugin.state.name().equals("TIMEOUT"))
			{
				infoStatus = plugin.state.name();
			}
		}
		tableComponent.addRow("Status:", infoStatus);

		TableComponent tableDelayComponent = new TableComponent();
		tableDelayComponent.setColumnAlignments(TableAlignment.LEFT, TableAlignment.RIGHT);
		tableDelayComponent.addRow("Sleep delay:", plugin.sleepLength + "ms");
		tableDelayComponent.addRow("Tick delay:", String.valueOf(plugin.timeout));

		if (!tableComponent.isEmpty())
		{
			panelComponent.setBackgroundColor(ColorUtil.fromHex("#121211")); //Material Dark default
			panelComponent.setPreferredSize(new Dimension(200, 200));
			panelComponent.setBorder(new Rectangle(5, 5, 5, 5));
			panelComponent.getChildren().add(TitleComponent.builder()
				.text("Cake Yoinker")
				.color(ColorUtil.fromHex("8E4ACA"))
				.build());
			panelComponent.getChildren().add(tableComponent);
			panelComponent.getChildren().add(TitleComponent.builder()
				.text("Delays")
				.color(ColorUtil.fromHex("8E4ACA"))
				.build());
			panelComponent.getChildren().add(tableDelayComponent);
		}
		return super.render(graphics);
	}
}