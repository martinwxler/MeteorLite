package net.runelite.mixins.sponge;

import net.runelite.api.mixins.*;
import net.runelite.rs.Reflection;
import net.runelite.rs.api.RSClient;

@Mixin(RSClient.class)
public abstract class CheatManager implements RSClient{

    @Shadow("client")
    public static RSClient client;

    @Inject
    @Copy("doCheat")
    public static void rs$doCheat(String s)
    {

    }

    @Replace("doCheat")
    public static void doCheat$api(String s)
    {
        boolean foundCustomCheat = false;
        if (s.equals("reflection"))
        {
            Reflection.printDebugMessages = !Reflection.printDebugMessages;
            client.getLogger().debug("Toggled Reflection debug messages");
            foundCustomCheat = true;
        }

        if (!foundCustomCheat)
            rs$doCheat(s);
    }
}
