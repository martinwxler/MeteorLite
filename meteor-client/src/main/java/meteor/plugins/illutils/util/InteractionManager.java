package meteor.plugins.illutils.util;

import meteor.plugins.illutils.osrs.OSRSUtils;
import org.sponge.util.Logger;

public class InteractionManager {

    Logger log = new Logger("InteractionManager");
    private final OSRSUtils game;

    public InteractionManager(OSRSUtils game) {
        this.game = game;
    }

    public void submit(Runnable runnable) {
        log.info("interacting");
        game.sleepDelay();
        runnable.run();
    }

    public void interact(int identifier, int opcode, int param0, int param1) {
        log.info("interacting");
        game.sleepDelay();
        game.clientThread.invoke(() -> game.client().invokeMenuAction("", "", identifier, opcode, param0, param1));
    }

}
