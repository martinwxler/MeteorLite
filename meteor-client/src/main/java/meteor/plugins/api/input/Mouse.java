package meteor.plugins.api.input;

import meteor.plugins.api.commons.Rand;
import meteor.plugins.api.commons.Time;
import net.runelite.api.Client;

import javax.inject.Inject;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.function.Supplier;

public class Mouse {
    private static final Supplier<Point> CLICK_POINT_SUPPLIER = () -> new Point(Rand.nextInt(520, 568), Rand.nextInt(55, 70));

    @Inject
    private static Client client;

    private static boolean exited = true;

    public static void click(int x, int y, boolean left) {
        Canvas canvas = client.getCanvas();

        if (exited) {
            entered(x, y, canvas, System.currentTimeMillis());
        }

        pressed(x, y, canvas, System.currentTimeMillis(),left ? MouseEvent.BUTTON1 : MouseEvent.BUTTON3);
        Time.sleep(1, 66);
        long currTime = System.currentTimeMillis();
        released(x, y, canvas, currTime, left ? MouseEvent.BUTTON1 : MouseEvent.BUTTON3);
        clicked(x, y, canvas, currTime, left ? MouseEvent.BUTTON1 : MouseEvent.BUTTON3);

        if (Rand.nextBool() && !exited) {
            exited(x, y, canvas, System.currentTimeMillis());
        }
    }

    public static void click(Point point, boolean left) {
        click((int) point.getX(), (int) point.getY(), left);
    }

    public static void clickRandom(boolean left) {
        click(CLICK_POINT_SUPPLIER.get(), left);
    }

    public static synchronized void pressed(int x, int y, Canvas canvas, long time, int button) {
        MouseEvent event = new MouseEvent(canvas, MouseEvent.MOUSE_PRESSED, time, 0, x, y, 1, false, button);
        event.setSource("meteor");
        canvas.dispatchEvent(event);
    }

    public static synchronized void released(int x, int y, Canvas canvas, long time, int button) {
        MouseEvent event = new MouseEvent(canvas, MouseEvent.MOUSE_RELEASED, time, 0, x, y, 1, false, button);
        event.setSource("meteor");
        canvas.dispatchEvent(event);
    }

    public static synchronized void clicked(int x, int y, Canvas canvas, long time, int button) {
        MouseEvent event = new MouseEvent(canvas, MouseEvent.MOUSE_CLICKED, time, 0, x, y, 1, false, button);
        event.setSource("meteor");
        canvas.dispatchEvent(event);
    }

    public static synchronized void released(int x, int y, Canvas canvas, long time) {
        MouseEvent event = new MouseEvent(canvas, MouseEvent.MOUSE_RELEASED, time, 0, x, y, 1, false);
        event.setSource("meteor");
        canvas.dispatchEvent(event);
    }

    public static synchronized void clicked(int x, int y, Canvas canvas, long time) {
        MouseEvent event = new MouseEvent(canvas, MouseEvent.MOUSE_CLICKED, time, 0, x, y, 1, false);
        event.setSource("meteor");
        canvas.dispatchEvent(event);
    }

    public static synchronized void exited(int x, int y, Canvas canvas, long time) {
        MouseEvent event = new MouseEvent(canvas, MouseEvent.MOUSE_EXITED, time, 0, x, y, 0, false);
        event.setSource("meteor");
        canvas.dispatchEvent(event);
        exited = true;
    }

    public static synchronized void entered(int x, int y, Canvas canvas, long time) {
        MouseEvent event = new MouseEvent(canvas, MouseEvent.MOUSE_ENTERED, time, 0, x, y, 0, false);
        event.setSource("meteor");
        canvas.dispatchEvent(event);
        exited = false;
    }
}
