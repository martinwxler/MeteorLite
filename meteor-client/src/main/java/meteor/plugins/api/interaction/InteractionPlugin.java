package meteor.plugins.api.interaction;

import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.input.MouseManager;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.commons.Rand;
import meteor.plugins.api.input.Mouse;
import meteor.ui.overlay.OverlayManager;
import net.runelite.api.Client;
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

	@Subscribe
	public void onInvokeMenuAction(InvokeMenuActionEvent e) {
		if (config.mouseEvents()) {
			int clickX = Rand.nextInt(0, client.getCanvasWidth());
			int clickY = Rand.nextInt(0, client.getCanvasHeight());
			logger.debug("Sending click to {} {}", clickX, clickY);

			action = new MenuEntry(e.getOption(), e.getTarget(), e.getId(),
							e.getOpcode(), e.getParam0(), e.getParam1(), false);

			Mouse.click(clickX, clickY, true);
		} else {
			client.invokeMenuAction(e.getOption(), e.getTarget(), e.getId(),
							e.getOpcode(), e.getParam0(), e.getParam1());
		}
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked e) {
		if (config.mouseEvents()) {
			e.consume();

			if (action == null) {
				logger.error("Couldn't send menu action after click");
				return;
			}

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
