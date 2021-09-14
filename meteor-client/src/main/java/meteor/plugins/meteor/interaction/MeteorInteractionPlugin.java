package meteor.plugins.meteor.interaction;

import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.input.MouseManager;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.commons.Rand;
import meteor.plugins.api.game.GameThread;
import meteor.plugins.api.input.Mouse;
import meteor.plugins.api.movement.Movement;
import meteor.plugins.api.widgets.DialogOption;
import meteor.ui.overlay.OverlayManager;
import net.runelite.api.Client;
import net.runelite.api.events.DialogProcessed;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.InvokeMenuActionEvent;
import net.runelite.api.events.MenuOptionClicked;

import javax.inject.Inject;
import java.awt.*;

@PluginDescriptor(
				name = "Interaction Manager",
				cantDisable = true
)
public class MeteorInteractionPlugin extends Plugin {
	private static final int MINIMAP_WIDTH = 250;
	private static final int MINIMAP_HEIGHT = 180;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private MouseManager mouseManager;

	@Inject
	private MeteorInteractionConfig config;

	@Inject
	private MeteorInteractionOverlay overlay;

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
			logger.info("[Bot Action] {}", debug);
		}

		if (config.mouseEvents()) {
			if (!interactReady()) {
				logger.error("Interact was not ready [{}]", debug);
				return;
			}

			Point randomPoint = getClickPoint();
			mouseClickX = randomPoint.x;
			mouseClickY = randomPoint.y;
			if (config.debug()) {
				logger.info("Sending click to {} {}", mouseClickX, mouseClickY);
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
			// Don't re-process automated actions
			if (e.isAutomated()) {
				return;
			}

			e.consume();

			if (action == null) {
				logger.error("Menu replace failed");
				return;
			}

			processAction(action);
			action = null;
			mouseClickX = -1;
			mouseClickY = -1;
			return;
		}

		if (config.debug()) {
			String action = "O=" + e.getMenuOption()
							+ " | T=" + e.getMenuTarget()
							+ " | ID=" + e.getId()
							+ " | OP=" + e.getMenuAction().getId()
							+ " | P0=" + e.getParam0()
							+ " | P1=" +  e.getParam1();
			logger.info("[Manual Action] {}", action);
		}
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

	private boolean interactReady() {
		return mouseClickX == -1 && mouseClickY == -1;
	}

	@Provides
	public MeteorInteractionConfig getConfig(ConfigManager configManager) {
		return configManager.getConfig(MeteorInteractionConfig.class);
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
