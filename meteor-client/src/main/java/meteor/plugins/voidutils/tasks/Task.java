package meteor.plugins.voidutils.tasks;

import meteor.eventbus.EventBus;

import javax.inject.Inject;

public class Task {

    @Inject
    EventBus eventBus;

    public Task() {
        eventBus.register(this);
    }

    public String name() {
        return "";
    };

    public boolean shouldExecute() {
        return false;
    }

    public void execute() {

    }
}
