package meteor.plugins.illutils.util.actor;


import meteor.plugins.illutils.osrs.wrappers.IllNPC;
import meteor.plugins.illutils.osrs.wrappers.IllObject;

import java.util.Arrays;
import java.util.stream.Stream;

public class NpcStream extends ActorStream<IllNPC, NpcStream> {
    public NpcStream(Stream<IllNPC> stream) {
        super(stream);
    }

    @Override
    protected NpcStream wrap(Stream<IllNPC> stream) {
        return new NpcStream(stream);
    }

    /**
     * Returns a stream consisting of the elements of this stream with
     * any of the given {@link IllNPC#index()}s
     */
    public NpcStream withIndex(int... indices) {
        return filter(n -> Arrays.stream(indices).anyMatch(index -> n.index() == index));
    }

    /**
     * Returns a stream consisting of the elements of this stream with
     * any of the given {@link IllNPC#id()}s
     */
    public NpcStream withId(int... ids) {
        return filter(n -> Arrays.stream(ids).anyMatch(id -> n.id() == id));
    }

    /**
     * Returns a stream consisting of the elements of this stream with
     * any of the given {@link IllObject#actions()}s
     */
    public NpcStream withAction(String... actions) {
        return filter(n -> Arrays.stream(actions).anyMatch(action -> n.actions().contains(action)));
    }
}
