package meteor.plugins.entityinspector;

import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.entityinspector.scriptinspector.ScriptInspector;
import meteor.plugins.entityinspector.varinspector.VarInspector;
import meteor.plugins.entityinspector.widgetinspector.WidgetInspector;
import meteor.ui.overlay.OverlayManager;
import net.runelite.api.events.ConfigButtonClicked;
import net.runelite.api.events.PacketSent;

import javax.inject.Inject;

@PluginDescriptor(
				name = "Entity Inspector",
				description = "Shows entity information"
)
public class EntityInspectorPlugin extends Plugin {
	@Inject
	private EntityInspectorConfig config;

	@Inject
	private EntityInspectorOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private WidgetInspector widgetInspector;

	@Inject
	private VarInspector varInspector;

	@Inject
	private ScriptInspector scriptInspector;

	@Override
	public void startup() {
		overlayManager.add(overlay);
	}

	@Override
	public void shutdown() {
		overlayManager.remove(overlay);
	}

	@Provides
	public EntityInspectorConfig getConfig(ConfigManager configManager) {
		return configManager.getConfig(EntityInspectorConfig.class);
	}

	@Subscribe
	public void onConfigButtonClicked(ConfigButtonClicked event) {
		if (!event.getGroup().equals("entityinspector")) {
			return;
		}

		if (event.getKey().equals("widgetInspector")) {
			widgetInspector.open();
			return;
		}

		if (event.getKey().equals("varInspector")) {
			varInspector.open();
			return;
		}

		if (event.getKey().equals("scriptInspector")) {
			scriptInspector.open();
		}
	}

	@Subscribe
	public void onPacketSent(PacketSent e) {
		if (!config.packets()) {
			return;
		}

		System.out.println(e.hexDump());
	}
}
