package meteor.plugins.illutils.api.scene;

import meteor.plugins.illutils.osrs.wrappers.IllObject;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class GameObjectStream extends LocatableStream<IllObject, GameObjectStream> {
    public GameObjectStream(Stream<IllObject> stream) {
        super(stream);
    }

    @Override
    protected GameObjectStream wrap(Stream<IllObject> stream) {
        return new GameObjectStream(stream);
    }

    /**
     * Returns a stream consisting of the elements of this stream with
     * any of the given {@link IllObject#id()}s
     */
    public GameObjectStream withId(int... ids) {
        return filter(o -> Arrays.stream(ids).anyMatch(id -> o.id() == id));
    }

//    /**
//     * Returns a stream consisting of the elements of this stream with
//     * any of the given {@link iObject#type()}s
//     */
//    public GameObjectStream withType(ObjectType... types) {
//        return filter(o -> Arrays.stream(types).anyMatch(type -> o.type() == type));
//    }
//
//    /**
//     * Returns a stream consisting of the elements of this stream with
//     * any of the given {@link iObject#orientation()}s
//     */
//    public GameObjectStream withOrientation(int... orientations) {
//        return filter(o -> Arrays.stream(orientations).anyMatch(orientation -> o.orientation() == orientation));
//    }
//
//    /**
//     * Returns a stream consisting of the elements of this stream with
//     * any of the given {@link iObject#sequence()}s
//     */
//    public GameObjectStream withSequence(int... sequences) {
//        return filter(o -> Arrays.stream(sequences).anyMatch(sequence -> o.sequence() == sequence));
//    }

    /**
     * Returns a stream consisting of the elements of this stream with
     * any of the given {@link IllObject#name()}s
     */
    public GameObjectStream withName(String... names) {
        return filter(o -> Arrays.stream(names).anyMatch(name -> Objects.equals(o.name(), name)));
    }

    /**
     * Returns a stream consisting of the elements of this stream with
     * any of the given {@link IllObject#actions()}s
     */
    public GameObjectStream withAction(String... actions) {
        return filter(o -> Arrays.stream(actions).anyMatch(action -> o.actions().contains(action)));
    }

    /**
     * Returns a stream consisting of the elements of this stream with
     * any of the given {@link IllObject#position()}s
     */
    public GameObjectStream withPosition(Position... positions) {
        return filter(o -> Arrays.stream(positions).anyMatch(position -> o.position().equals(position)));
    }
}
