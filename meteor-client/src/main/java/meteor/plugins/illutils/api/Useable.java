package meteor.plugins.illutils.api;

import meteor.plugins.illutils.osrs.wrappers.IllInventoryItem;
import meteor.plugins.illutils.osrs.wrappers.IllNPC;
import meteor.plugins.illutils.osrs.wrappers.IllObject;

public interface Useable {
    void useOn(IllObject object);

//    void useOn(GroundItem groundItem); TODO

    void useOn(IllNPC npc);
//
//    void useOn(iPlayer player);

    void useOn(IllInventoryItem item);
}
