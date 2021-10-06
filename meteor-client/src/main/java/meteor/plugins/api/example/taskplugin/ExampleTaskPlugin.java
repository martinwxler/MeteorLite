package meteor.plugins.api.example.taskplugin;

import meteor.plugins.PluginDescriptor;
import meteor.plugins.TaskPlugin;
import meteor.plugins.api.example.taskplugin.tasks.OpenGE;
import meteor.plugins.api.example.taskplugin.tasks.WalkToGE;

@PluginDescriptor(name = "Example Task Plugin", enabledByDefault = false)
public class ExampleTaskPlugin extends TaskPlugin {
	@Override
	public void startup() {
		// Submit the tasks that should be executed.
		// Tasks are executed from top to bottom, and block each other by default.
		submit(
						new WalkToGE(),
						new OpenGE()
		);
	}
}
