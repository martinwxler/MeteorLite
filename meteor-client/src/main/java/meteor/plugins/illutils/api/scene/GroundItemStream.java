package meteor.plugins.illutils.api.scene;

import meteor.plugins.illutils.osrs.wrappers.IllGroundItem;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class GroundItemStream extends LocatableStream<IllGroundItem, GroundItemStream> {
    public GroundItemStream(Stream<IllGroundItem> stream) {
        super(stream);
    }

    @Override
    protected GroundItemStream wrap(Stream<IllGroundItem> stream) {
        return new GroundItemStream(stream);
    }

    /**
     * Returns a stream consisting of the elements of this stream with
     * any of the given {@link IllGroundItem#id()}s
     */
    public GroundItemStream withId(int... ids) {
        return filter(o -> Arrays.stream(ids).anyMatch(id -> o.id() == id));
    }

    /**
     * Returns a stream consisting of the elements of this stream with
     * a minimum {@link IllGroundItem#quantity()}
     */
    public GroundItemStream withMinimumQuantity(int quantity) {
        return filter(o -> o.quantity() >= quantity);
    }

    /**
     * Returns a stream consisting of the elements of this stream with
     * any of the given {@link IllGroundItem#name()}s
     */
    public GroundItemStream withName(String... names) {
        return filter(o -> Arrays.stream(names).anyMatch(name -> Objects.equals(o.name(), name)));
    }

    /**
     * Returns a stream consisting of the elements of this stream with
     * any of the given {@link IllGroundItem#actions()}s
     */
    public GroundItemStream withAction(String... actions) {
        return filter(o -> Arrays.stream(actions).anyMatch(action -> o.actions().contains(action)));
    }

    /**
     * Returns a stream consisting of the elements of this stream with
     * any of the given {@link IllGroundItem#position()}s
     */
    public GroundItemStream withPosition(Position... positions) {
        return filter(o -> Arrays.stream(positions).anyMatch(position -> o.position().equals(position)));
    }
}
