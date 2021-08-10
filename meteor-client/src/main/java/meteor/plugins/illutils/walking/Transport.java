package meteor.plugins.illutils.walking;

import meteor.plugins.illutils.osrs.OSRSUtils;
import meteor.plugins.illutils.api.scene.Position;

import java.util.function.Consumer;

public class Transport {
    public final Position source;
    public final Position target;
    public final Consumer<OSRSUtils> handler;
    public final int targetRadius;
    public final int sourceRadius;

    public Transport(Position source, Position target, int sourceRadius, int targetRadius, Consumer<OSRSUtils> handler) {
        this.source = source;
        this.target = target;
        this.targetRadius = targetRadius;
        this.handler = handler;
        this.sourceRadius = sourceRadius;
    }
}
