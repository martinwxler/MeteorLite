package meteor.plugins.api.interaction;

import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.input.MouseManager;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.commons.Rand;
import meteor.plugins.api.input.Mouse;
import meteor.plugins.api.movement.Movement;
import meteor.plugins.api.widgets.Widgets;
import meteor.ui.overlay.OverlayManager;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.InvokeMenuActionEvent;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;

import javax.inject.Inject;
import java.awt.*;

@PluginDescriptor(
				name = "Interaction Manager",
				cantDisable = true
)
public class InteractionPlugin extends Plugin {
	@Inject
	private OverlayManager overlayManager;

	@Inject
	private MouseManager mouseManager;

	@Inject
	private InteractionConfig config;

	@Inject
	private InteractionOverlay overlay;

	@Inject
	private Client client;
	private MenuEntry action;
	private int mouseClickX;
	private int mouseClickY;

	@Subscribe
	public void onInvokeMenuAction(InvokeMenuActionEvent e) {
		if (config.mouseEvents()) {
			Point randomPoint = getClickPoint();
			mouseClickX = randomPoint.x;
			mouseClickY = randomPoint.y;
			logger.debug("Sending click to {} {}", mouseClickX, mouseClickY);

			action = new MenuEntry(e.getOption(), e.getTarget(), e.getId(),
							e.getOpcode(), e.getParam0(), e.getParam1(), false);

			Mouse.click(mouseClickX, mouseClickY, true);
		} else {
			processAction(e);
		}
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked e) {
		if (config.mouseEvents() && e.getCanvasX() == mouseClickX && e.getCanvasY() == mouseClickY) {
			e.consume();

			if (action == null) {
				logger.error("Couldn't send menu action after click");
				return;
			}

			processAction(action);
		}
	}

	private void processAction(MenuEntry action) {
		if (action.getMenuAction() == MenuAction.WALK) {
			Movement.setDestination(action.getParam0(), action.getParam1());
		} else {
			client.invokeMenuAction(action.getOption(), action.getTarget(), action.getId(),
							action.getMenuAction().getId(), action.getParam0(), action.getParam1());
		}
	}

	@Provides
	public InteractionConfig getConfig(ConfigManager configManager) {
		return configManager.getConfig(InteractionConfig.class);
	}

	@Override
	public void startup() {
		overlayManager.add(overlay);
		mouseManager.registerMouseListener(overlay);
	}

	@Override
	public void shutdown() {
		overlayManager.remove(overlay);
		mouseManager.unregisterMouseListener(overlay);
	}

	private Point getClickPoint() {
		Rectangle bounds = client.getCanvas().getBounds();
		Point randomPoint = new Point(Rand.nextInt(2, bounds.width), Rand.nextInt(2, bounds.height));
		Rectangle minimap = getMinimap();
		if (minimap != null && minimap.contains(randomPoint)) {
			return getClickPoint();
		}

		return randomPoint;
	}

	private Rectangle getMinimap() {
		Widget fixedMinimap = Widgets.get(WidgetInfo.FIXED_VIEWPORT_MINIMAP_DRAW_AREA);
		if (fixedMinimap != null) {
			return fixedMinimap.getBounds();
		}

		Widget resizableMinimap = Widgets.get(WidgetInfo.RESIZABLE_MINIMAP_DRAW_AREA);
		if (resizableMinimap != null) {
			return resizableMinimap.getBounds();
		}

		Widget resizable2Minimap = Widgets.get(WidgetInfo.RESIZABLE_MINIMAP_STONES_DRAW_AREA);
		if (resizable2Minimap != null) {
			return resizable2Minimap.getBounds();
		}

		return null;
	}
}
