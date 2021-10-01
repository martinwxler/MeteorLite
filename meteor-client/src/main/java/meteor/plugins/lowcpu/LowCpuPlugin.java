package meteor.plugins.lowcpu;

import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import net.runelite.api.Client;
import net.runelite.api.hooks.DrawCallbacks;

import javax.inject.Inject;

@PluginDescriptor(
				name = "Low Cpu",
				enabledByDefault = false
)
public class LowCpuPlugin extends Plugin {
	@Inject
	private Client client;
	private DrawCallbacks drawCallbacks;
	private final DisableRenderCallbacks disableRenderCallbacks = new DisableRenderCallbacks();

	@Override
	public void startup() {
		drawCallbacks = client.getDrawCallbacks();
		client.setIsHidingEntities(true);
		client.setLowCpu(true);
		client.setDrawCallbacks(disableRenderCallbacks);
	}

	@Override
	public void shutdown() {
		client.setIsHidingEntities(false);
		client.setLowCpu(false);
		client.setDrawCallbacks(drawCallbacks);
	}
}
