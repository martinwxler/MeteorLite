package meteor.plugins.api.interaction;

import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.eventbus.EventBus;
import meteor.eventbus.Subscribe;
import meteor.input.MouseManager;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.commons.Rand;
import meteor.plugins.api.game.GameThread;
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
	private static final int MINIMAP_WIDTH = 250;
	private static final int MINIMAP_HEIGHT = 180;

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
	private volatile MenuEntry action;
	private volatile int mouseClickX = -1;
	private volatile int mouseClickY = -1;

	@Subscribe
	public void onInvokeMenuAction(InvokeMenuActionEvent e) {
		String debug = "O=" + e.getOption()
						+ " | T=" + e.getTarget()
						+ " | ID=" + e.getId()
						+ " | OP=" + e.getOpcode()
						+ " | P0=" + e.getParam0()
						+ " | P1=" +  e.getParam1();

		if (config.debug()) {
			logger.debug("[Bot Action] {}", debug);
		}

		if (config.mouseEvents()) {
			if (!interactReady()) {
				action = null;
				mouseClickX = -1;
				mouseClickY = -1;
				logger.error("Interact was not ready [{}]", debug);
				return;
			}

			Point randomPoint = getClickPoint();
			mouseClickX = randomPoint.x;
			mouseClickY = randomPoint.y;
			if (config.debug()) {
				logger.debug("Sending click to {} {}", mouseClickX, mouseClickY);
			}


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
				logger.error("Menu replace failed");
				return;
			}

			processAction(action);
			return;
		}

		if (config.debug()) {
			String action = "O=" + e.getMenuOption()
							+ " | T=" + e.getMenuTarget()
							+ " | ID=" + e.getId()
							+ " | OP=" + e.getMenuAction().getId()
							+ " | P0=" + e.getParam0()
							+ " | P1=" +  e.getParam1();
			logger.debug("[Manual Action] {}", action);
		}
	}

	private void processAction(MenuEntry entry) {
		if (entry.getMenuAction() == MenuAction.WALK) {
			Movement.setDestination(entry.getParam0(), entry.getParam1());
		} else {
			GameThread.invoke(() -> client.invokeMenuAction(entry.getOption(), entry.getTarget(), entry.getId(),
							entry.getMenuAction().getId(), entry.getParam0(), entry.getParam1()));
		}

		action = null;
		mouseClickX = -1;
		mouseClickY = -1;
	}

	private Point getClickPoint() {
		Rectangle bounds = client.getCanvas().getBounds();
		Point randomPoint = new Point(Rand.nextInt(2, bounds.width), Rand.nextInt(2, bounds.height));
		Rectangle minimap = getMinimap();
		if (minimap.contains(randomPoint)) {
			logger.debug("Click {} was inside minimap", randomPoint);
			return getClickPoint();
		}

		return randomPoint;
	}

	private Rectangle getMinimap() {
		Rectangle bounds = client.getCanvas().getBounds();
		return new Rectangle(bounds.width - MINIMAP_WIDTH, 0, MINIMAP_WIDTH, MINIMAP_HEIGHT);
	}

	private boolean interactReady() {
		return mouseClickX == -1 && mouseClickY == -1;
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
}
