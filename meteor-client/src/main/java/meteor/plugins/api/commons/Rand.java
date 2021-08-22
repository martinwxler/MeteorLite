package meteor.plugins.api.commons;

import javax.inject.Singleton;
import java.util.Random;

@Singleton
public class Rand {
    private final Random random;

    public Rand() {
        random = new Random(System.nanoTime());
    }

    public synchronized int nextInt(int min, int max) {
        return min + random.nextInt(max);
    }

    public synchronized int nextInt() {
        return random.nextInt();
    }

    public synchronized boolean nextBool() {
        return random.nextBoolean();
    }
}
