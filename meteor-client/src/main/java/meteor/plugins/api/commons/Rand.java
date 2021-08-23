package meteor.plugins.api.commons;

import java.util.Random;

public class Rand {
    private static final Random random;

    static {
        random = new Random(System.nanoTime());
    }

    public static synchronized int nextInt(int min, int max) {
        return min + random.nextInt(max);
    }

    public static synchronized int nextInt() {
        return random.nextInt();
    }

    public static synchronized boolean nextBool() {
        return random.nextBoolean();
    }
}
