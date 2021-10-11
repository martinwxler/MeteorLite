package meteor.plugins.cettitutorial;

import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.TaskPlugin;
import meteor.plugins.cettitutorial.tasks.*;

import javax.inject.Inject;

@PluginDescriptor(
		name = "Cetti's Tutorial Island",
		enabledByDefault = false
)
public class CettiTutorialPlugin extends TaskPlugin {

	@Inject
	private CettiTutorialConfig config;
	private CettiTutorialPlugin plugin;

	public static boolean ironTypeSet = false;
	@Provides
	public CettiTutorialConfig getConfig(final ConfigManager configManager) {
		return configManager.getConfig(CettiTutorialConfig.class);
	}

	@Override
	public void startup() {
		ironTypeSet = false;
		submit(
//				new Task(),
				new CreateCharacter(config),
				new GielinorGuide(),
				new SurvivalGuide(),
				new CookingGuide(),
				new QuestGuide(),
				new MiningGuide(),
				new CombatGuide(),
				new BankAndAccountGuide(),
				new PrayerGuide(),
				new IronmanGuide(config, plugin),
				new MagicGuide()
		);
	}

	@Override
	public void shutdown() {
	}
}
