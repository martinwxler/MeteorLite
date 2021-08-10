package meteor.plugins.illutils.util.actor;

import meteor.plugins.illutils.osrs.wrappers.IllPlayer;

import java.util.Arrays;
import java.util.stream.Stream;

public class PlayerStream extends ActorStream<IllPlayer, PlayerStream> {
    public PlayerStream(Stream<IllPlayer> stream) {
        super(stream);
    }

    @Override
    protected PlayerStream wrap(Stream<IllPlayer> stream) {
        return new PlayerStream(stream);
    }

    /**
     * Returns a stream consisting of the elements of this stream with
     * any of the given {@link IllPlayer#index()}s
     */
    public PlayerStream withIndex(int... indices) {
        return filter(n -> Arrays.stream(indices).anyMatch(index -> n.index() == index));
    }

    /**
     * Returns a stream consisting of the elements of this stream with
     * any of the given {@link IllPlayer#index()}s
     */
    public PlayerStream withoutIndex(int... indices) {
        return filter(n -> Arrays.stream(indices).anyMatch(index -> n.index() != index));
    }
}
