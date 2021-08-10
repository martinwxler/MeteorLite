package meteor.plugins.illutils.api;

import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Obtains latest item prices from the <a href="https://oldschool.runescape.wiki/w/RuneScape:Real-time_Prices">OSRS Wiki API</a>.
 * @Author Runemoro
 */
public class GrandExchangePrices {
    private static final ReentrantLock UPDATE_LOCK = new ReentrantLock();
    private static long lastUpdateTime = 0;
    private static AllPricesData data;

    public static ItemPrice get(int id) {
        UPDATE_LOCK.lock();

        try {
            if (System.currentTimeMillis() - lastUpdateTime > 5 * 60 * 1000) {
                update();
            }
        } finally {
            UPDATE_LOCK.unlock();
        }

        return data.data.get(id);
    }

    private static void update() {
        throw new UnsupportedOperationException("iutils grand exchange lookup noop");
    }

    private static class AllPricesData {
        private final Map<Integer, ItemPrice> data;

        private AllPricesData(Map<Integer, ItemPrice> data) {
            this.data = data;
        }
    }

    public static class ItemPrice {
        public final int high;
        public final int low;
        public final long highTime;
        public final int lowTime;

        private ItemPrice(int high, int low, long highTime, int lowTime) {
            this.high = high;
            this.low = low;
            this.highTime = highTime;
            this.lowTime = lowTime;
        }

        public String toString() {
            return low + "-" + high;
        }
    }
}
