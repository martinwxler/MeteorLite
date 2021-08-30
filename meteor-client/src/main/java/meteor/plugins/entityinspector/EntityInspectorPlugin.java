package meteor.plugins.entityinspector;

import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.ui.overlay.OverlayManager;

import javax.inject.Inject;

@PluginDescriptor(
				name = "Entity Inspector",
				description = "Shows entity information on mouse hover"
)
public class EntityInspectorPlugin extends Plugin {
	@Inject
	private EntityInspectorOverlay overlay;

	@Inject
	private OverlayManager overlayManager;

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
}
