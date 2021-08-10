package meteor.plugins.illutils.walking;

import meteor.plugins.illutils.osrs.OSRSUtils;

public interface Requirement {

    OSRSUtils game();

    boolean satisfies();
}
