package meteor.plugins.autorun;


import com.google.inject.Provides;
import meteor.PluginManager;
import net.runelite.api.Client;
import net.runelite.api.MenuAction;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import meteor.callback.ClientThread;
import meteor.config.ConfigManager;
import meteor.eventbus.EventBus;
import meteor.eventbus.Subscribe;
import meteor.game.WorldService;
import meteor.plugins.*;
import meteor.ui.overlay.OverlayManager;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.Random;
import java.util.concurrent.ExecutorService;

@PluginDescriptor(
	name = "AutoRunEnabler",
	description = "Automatically enables run."
)

@SuppressWarnings("unused")
@Singleton
public class AutoRun extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private PluginManager pluginManager;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private AutoRunConfig config;

	@Inject
	private EventBus eventBus;

	@Inject
	private ClientThread clientThread;

	@Inject
	private WorldService worldService;

	@Inject
	private ExecutorService executor;

	private final Random rand = new Random();

	private int nextRunThreshhold = -1;

	@Provides
	public AutoRunConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(AutoRunConfig.class);
	}

	@Override
	public void startup()
	{
	}

	@Override
	public void shutdown()
	{

	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		if (nextRunThreshhold <= 0)
			nextRunThreshhold = randInt(config.minRun(), config.maxRun());
		else {
			if (!isRunning() && client.getEnergy() > nextRunThreshhold) {
				toggleRun();
				nextRunThreshhold = -1;
			}
		}
	}

	public boolean isRunning() {
		return client.getVarpValue(173) == 1;
	}

	public void toggleRun()
	{
		executor.submit(() ->
		{
			try {
				Thread.sleep(randInt(0, 599));
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Widget runOrb = client.getWidget(WidgetInfo.MINIMAP_TOGGLE_RUN_ORB);
			clientThread.invokeLater(() -> client.invokeMenuAction("", "", 1, MenuAction.CC_OP.getId(), -1, runOrb.getId()));
		});
	}

	public int randInt(Random r, int min, int max) {
		return r.nextInt((max - min) + 1) + min;
	}

	public int randInt(int min, int max) {
		if (max < min)
			max = min;
		return randInt(rand, min, max);
	}

}
