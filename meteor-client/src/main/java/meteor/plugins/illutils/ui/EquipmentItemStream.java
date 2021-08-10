package meteor.plugins.illutils.ui;

import meteor.plugins.illutils.api.EquipmentSlot;
import meteor.plugins.illutils.osrs.wrappers.IllEquipmentItem;
import meteor.plugins.illutils.util.RandomizedStreamAdapter;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public class EquipmentItemStream extends RandomizedStreamAdapter<IllEquipmentItem, EquipmentItemStream> {
    public EquipmentItemStream(Stream<IllEquipmentItem> stream) {
        super(stream);
    }

    @Override
    protected EquipmentItemStream wrap(Stream<IllEquipmentItem> stream) {
        return new EquipmentItemStream(stream);
    }

    /**
     * Returns a stream consisting of the elements of this stream with
     * any of the given {@link IllEquipmentItem#id()}s
     */
    public EquipmentItemStream withId(int... ids) {
        return filter(o -> Arrays.stream(ids).anyMatch(id -> o.id() == id));
    }

    /**
     * Returns a stream consisting of the elements of this stream with
     * a minimum {@link IllEquipmentItem#quantity()}
     */
    public EquipmentItemStream withMinimumQuantity(int quantity) {
        return filter(o -> o.quantity() >= quantity);
    }

    /**
     * Returns a stream consisting of the elements of this stream with
     * any of the given {@link IllEquipmentItem#name()}s
     */
    public EquipmentItemStream withName(String... names) {
        return filter(o -> Arrays.stream(names).anyMatch(name -> Objects.equals(o.name(), name)));
    }

    /**
     * Returns a stream consisting of the elements of this stream whose
     * {@link IllEquipmentItem#name()}s contain any of the given name parts
     */
    public EquipmentItemStream withNamePart(String... names) {
        return filter(o -> Arrays.stream(names).anyMatch(name -> o.name().toLowerCase().contains(name.toLowerCase())));
    }

    /**
     * Returns a stream consisting of the elements of this stream with
     * any of the given {@link IllEquipmentItem#actions()}s
     */
    public EquipmentItemStream withAction(String... actions) {
        return filter(o -> Arrays.stream(actions).anyMatch(action -> o.actions().contains(action)));
    }

    /**
     * Returns a stream consisting of the elements of this stream with
     * any of the given {@link EquipmentSlot}s
     */
    public EquipmentItemStream withSlot(EquipmentSlot... slots) {
        return filter(o -> Arrays.stream(slots).anyMatch(slot -> o.slot() == slot.index));
    }

    /**
     * Returns a stream consisting of the elements of this stream with
     * any of the given {@link IllEquipmentItem#slot()}s
     */
    public EquipmentItemStream withSlot(int... slots) {
        return filter(o -> Arrays.stream(slots).anyMatch(slot -> o.slot() == slot));
    }

    public void unequip() {
        forEach(item -> item.interact("Remove"));
    }
}
