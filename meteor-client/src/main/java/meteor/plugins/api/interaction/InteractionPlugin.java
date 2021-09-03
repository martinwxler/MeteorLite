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
import meteor.ui.overlay.OverlayManager;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.MenuEntry;
import net.runelite.api.events.InvokeMenuActionEvent;
import net.runelite.api.events.MenuOptionClicked;

import javax.inject.Inject;

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
			mouseClickX = Rand.nextInt(0, client.getCanvasWidth());
			mouseClickY = Rand.nextInt(0, client.getCanvasHeight());
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
}
