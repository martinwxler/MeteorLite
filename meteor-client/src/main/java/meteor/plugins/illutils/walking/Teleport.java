package meteor.plugins.illutils.walking;

import meteor.plugins.illutils.api.scene.Position;

public class Teleport {
    public final Position target;
    public final int radius;
    public final Runnable handler;

    public Teleport(Position target, int radius, Runnable handler) {
        this.target = target;
        this.radius = radius;
        this.handler = handler;
    }
}
