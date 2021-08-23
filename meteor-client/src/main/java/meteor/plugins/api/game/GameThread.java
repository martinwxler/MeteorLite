package meteor.plugins.api.game;

import meteor.callback.ClientThread;
import net.runelite.api.Client;

import javax.inject.Inject;
import java.util.concurrent.*;

public class GameThread {
    private static final long TIMEOUT = 500;
    @Inject
    private static ClientThread clientThread;

    @Inject
    private static Client client;

    public static <T> void invoke(Runnable runnable) {
        if (client.isClientThread()) {
            runnable.run();
        } else {
            clientThread.invokeLater(runnable);
        }
    }

    public static <T> T invokeLater(Callable<T> callable) {
        if (client.isClientThread()) {
            try {
                return callable.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            FutureTask<T> futureTask = new FutureTask<>(callable);
            clientThread.invokeLater(futureTask);
            return futureTask.get(TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (ExecutionException | InterruptedException | TimeoutException e) {
            e.printStackTrace();
            throw new RuntimeException("GameThread couldn't return result");
        }
    }
}

