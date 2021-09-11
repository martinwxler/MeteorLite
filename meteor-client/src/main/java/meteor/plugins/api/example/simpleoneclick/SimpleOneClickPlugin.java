package meteor.plugins.api.example.simpleoneclick;

import com.google.inject.Inject;
import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.eventbus.events.ConfigChanged;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.scene.Tiles;
import meteor.util.Text;
import net.runelite.api.SceneEntity;
import net.runelite.api.events.MenuOptionClicked;

import java.util.HashMap;
import java.util.Map;

@PluginDescriptor(
				name = "Simple One Click"
)
public class SimpleOneClickPlugin extends Plugin {
	@Inject
	private SimpleOneClickConfig config;

	private final Map<String, String> configs = new HashMap<>();

	@Provides
	public SimpleOneClickConfig getConfig(ConfigManager configManager) {
		return configManager.getConfig(SimpleOneClickConfig.class);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged e) {
		if (!e.getGroup().equals("simpleoneclick")) {
			return;
		}

		configs.clear();

		String[] items = config.config().split(",");
		for (String i : items) {
			String[] pairs = i.split(":");
			configs.put(pairs[0], pairs[1]);
		}
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked e) {
		String target = Text.removeTags(e.getMenuTarget());
		if (!configs.containsKey(target)) {
			return;
		}

		e.consume();

		String replaced = configs.get(target);

		SceneEntity interactable = (SceneEntity) Tiles.getHoveredObject();
		if (interactable == null || !interactable.getName().equals(target)) {
			return;
		}

		interactable.interact(replaced);
	}
}
