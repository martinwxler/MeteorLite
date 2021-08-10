package meteor.plugins.illutils.api.scene;

import meteor.plugins.illutils.osrs.wrappers.IllTile;
import net.runelite.api.Client;
import meteor.plugins.illutils.osrs.OSRSUtils;

public interface Locatable {
    /**
     * The {@link OSRSUtils} instance this object belongs to.
     */
    OSRSUtils game();

    Client client();

    /**
     * The position in the world. In an instance, this is the actual
     * world position, not the position in the chunk template.
     */
    Position position();

    /**
     * If not in an instance, this is equal to {@link Locatable#position()}. In an instance, this returns
     * the position in the chunk template.
     */
    default Position templatePosition() {
        IllTile tile = game().tile(position());

        if (tile == null) {
            return position();
        }

        return tile.templatePosition();
    }
}
