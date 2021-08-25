package meteor.plugins.nightmare;

import net.runelite.api.Client;
import net.runelite.api.Prayer;
import net.runelite.api.VarClientInt;
import net.runelite.api.vars.InterfaceTab;
import net.runelite.api.widgets.Widget;
import meteor.ui.overlay.Overlay;
import meteor.ui.overlay.OverlayLayer;
import meteor.ui.overlay.OverlayPosition;
import meteor.ui.overlay.OverlayPriority;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;

import static meteor.ui.overlay.OverlayUtil.renderPolygon;

@Singleton
class NightmarePrayerOverlay extends Overlay
{
	private final Client client;
	private final NightmarePlugin plugin;
	private final NightmareConfig config;

	@Inject
	private NightmarePrayerOverlay(final Client client, final NightmarePlugin plugin, final NightmareConfig config)
	{
		this.client = client;
		this.plugin = plugin;
		this.config = config;
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
		setPriority(OverlayPriority.LOW);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (!plugin.isInFight() || plugin.getNm() == null)
		{
			return null;
		}

		NightmareAttack attack = plugin.getPendingNightmareAttack();

		if (attack == null)
		{
			return null;
		}

		if (!config.prayerHelper().showWidgetHelper())
		{
			return null;
		}

		Color color = client.isPrayerActive(attack.getPrayer()) ? Color.GREEN : Color.RED;
		renderPrayerOverlay(graphics, client, attack.getPrayer(), color);

		return null;
	}

	public static Rectangle renderPrayerOverlay(Graphics2D graphics, Client client, Prayer prayer, Color color)
	{
		Widget widget = client.getWidget(prayer.getWidgetInfo());

		if (widget == null || client.getVar(VarClientInt.INVENTORY_TAB) != InterfaceTab.PRAYER.getId())
		{
			return null;
		}

		Rectangle bounds = widget.getBounds();
		renderPolygon(graphics, rectangleToPolygon(bounds), color);
		return bounds;
	}

	private static Polygon rectangleToPolygon(Rectangle rect)
	{
		int[] xpoints = {rect.x, rect.x + rect.width, rect.x + rect.width, rect.x};
		int[] ypoints = {rect.y, rect.y, rect.y + rect.height, rect.y + rect.height};

		return new Polygon(xpoints, ypoints, 4);
	}
}