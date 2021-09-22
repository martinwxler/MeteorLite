package meteor.plugins.meteorlite.interaction;

import meteor.eventbus.Subscribe;
import meteor.plugins.api.commons.Rand;
import meteor.plugins.api.game.GameThread;
import meteor.plugins.api.input.Mouse;
import meteor.plugins.api.movement.Movement;
import meteor.plugins.api.packets.MousePackets;
import meteor.plugins.api.widgets.DialogOption;
import meteor.config.MeteorLiteConfig;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.MouseHandler;
import net.runelite.api.events.DialogProcessed;
import net.runelite.api.events.InvokeMenuActionEvent;
import net.runelite.api.events.MenuOptionClicked;
import org.sponge.util.Logger;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;

@Singleton
public class InteractionManager {
	private static final Logger logger = new Logger("Interaction Manager");
	private static final int MINIMAP_WIDTH = 250;
	private static final int MINIMAP_HEIGHT = 180;

	@Inject
	private MeteorLiteConfig config;

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
						+ " | P1=" + e.getParam1();

		if (config.debugInteractions()) {
			logger.info("[Bot Action] {}", debug);
		}

		if (config.mouseEvents()) {
			if (!interactReady()) {
				logger.error("Interact was not ready {} {}", mouseClickX, mouseClickY);
				return;
			}

			Point randomPoint = getClickPoint();
			mouseClickX = randomPoint.x;
			mouseClickY = randomPoint.y;
			if (config.debugInteractions()) {
				logger.info("Sending click to {} {}", mouseClickX, mouseClickY);
			}

			action = new MenuEntry(e.getOption(), e.getTarget(), e.getId(),
							e.getOpcode(), e.getParam0(), e.getParam1(), false);

			Mouse.click(mouseClickX, mouseClickY, true);
		} else {
			// Spoof mouse
			MouseHandler mouseHandler = client.getMouseHandler();
			Point randomPoint = getClickPoint();
			mouseClickX = randomPoint.x;
			mouseClickY = randomPoint.y;
			mouseHandler.sendMovement(mouseClickX, mouseClickY);
			mouseHandler.sendClick(mouseClickX, mouseClickY);
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
			reset();
			return;
		}

		if (config.debugInteractions()) {
			String action = "O=" + e.getMenuOption()
							+ " | T=" + e.getMenuTarget()
							+ " | ID=" + e.getId()
							+ " | OP=" + e.getMenuAction().getId()
							+ " | P0=" + e.getParam0()
							+ " | P1=" + e.getParam1();
			logger.info("[Manual Action] {}", action);
		}

		reset();
	}

	@Subscribe
	public void onDialogProcessed(DialogProcessed e) {
		if (!config.debugDialogs()) {
			return;
		}

		DialogOption dialogOption = DialogOption.of(e.getWidgetUid(), e.getIndex());
		if (dialogOption != null) {
			logger.info("Dialog processed {}", dialogOption);
		} else {
			logger.info("Unknown or unmapped dialog {}", e);
		}
	}

	private void processAction(MenuEntry entry) {
		if (entry.getMenuAction() == MenuAction.WALK) {
			Movement.setDestination(entry.getParam0(), entry.getParam1());
		} else {
			GameThread.invoke(() -> client.invokeMenuAction(entry.getOption(), entry.getTarget(), entry.getId(),
							entry.getMenuAction().getId(), entry.getParam0(), entry.getParam1()));
		}
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

	private void reset() {
		action = null;
		mouseClickX = -1;
		mouseClickY = -1;
	}

	private boolean interactReady() {
		return mouseClickX == -1 && mouseClickY == -1;
	}
}
