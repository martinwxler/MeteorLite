package meteor.plugins.voidutils.tasks;

import meteor.plugins.Plugin;

public class Task {

    public Class<Plugin> plugin;

    public String name() {
        return "";
    };

    public boolean shouldExecute() {
        return false;
    }

    public void execute() {}
}
