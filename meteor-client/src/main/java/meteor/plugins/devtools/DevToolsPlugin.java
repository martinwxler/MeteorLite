package meteor.plugins.devtools;

import com.google.inject.Provides;
import javax.inject.Inject;
import meteor.config.ConfigManager;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.ui.overlay.OverlayManager;

@PluginDescriptor(
    name = "Dev Tools",
    enabledByDefault = false,
    description = ""
)
public class DevToolsPlugin extends Plugin {

  @Inject
  DevToolsConfig config;

  @Inject
  DevToolsOverlay overlay;

  @Inject
  OverlayManager overlayManager;

  @Provides
  public DevToolsConfig getConfig(ConfigManager configManager) {
    return configManager.getConfig(DevToolsConfig.class);
  }

  public void startup() {
    overlayManager.add(overlay);
  }

  public void shutdown() {
    overlayManager.remove(overlay);
  }
}
