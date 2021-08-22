package meteor.plugins.api.commons;

import net.runelite.api.Client;
import org.sponge.util.Logger;

import javax.inject.Inject;

public class Time {
    private static final Logger logger = new Logger("Time");
    @Inject
    private static Client client;

    public static void sleep(int ms) {
        if (client.isClientThread()) {
            logger.warn("Tried to sleep on client thread!");
            return;
        }

        try {
            Thread.sleep(ms);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
