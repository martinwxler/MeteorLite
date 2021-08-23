package meteor.plugins.voidutils.tasks;

import meteor.plugins.Plugin;

import java.util.ArrayList;

public class TaskSet {

    public ArrayList<Task> tasks = new ArrayList<>();

    private final Plugin plugin;

    public TaskSet(Plugin plugin) {
        this.plugin = plugin;
    }

    public void add(Task task) {
        tasks.add(task);
        plugin.injector.injectMembers(task);
        plugin.eventBus.register(task);
    }

    public <T extends Task> T getInstance(Class<? extends Task> type)
    {
        for (Task t : tasks) {
            if (type.isInstance(t))
            {
                return (T) t;
            }
        }
        return null;
    }
}
