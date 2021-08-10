package meteor.plugins.illutils.script;

import meteor.plugins.illutils.util.Util;
import org.sponge.util.Logger;

public class IScriptHandler implements Runnable {
    private final iScript script;
    private final Logger log = new Logger("iScriptHandler");

    public IScriptHandler(iScript script) {
        this.script = script;
    }

    public void run() {
        script.onStart();
        while (!Thread.currentThread().isInterrupted()) {
            try {
                script.loop();
            } catch (IllegalStateException | AssertionError | NullPointerException e) {
                log.info("Caught error, restarting in 3 seconds");
                e.printStackTrace();
                log.info(e.getMessage());
                Util.sleep(3000);
            }
        }
    }
}
