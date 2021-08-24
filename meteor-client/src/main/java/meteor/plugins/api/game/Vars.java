package meteor.plugins.api.game;

import net.runelite.api.Client;
import net.runelite.api.VarClientInt;
import net.runelite.api.VarClientStr;
import net.runelite.api.Varbits;

import javax.inject.Inject;

public class Vars {
    @Inject
    private static Client client;

    public static int getBit(int id) {
        return GameThread.invokeLater(() -> client.getVarbitValue(client.getVarps(), id));
    }

    public static int getBit(Varbits varbits) {
        return getBit(varbits.getId());
    }

    public static int getVarp(int id) {
        return client.getVarpValue(client.getVarps(), id);
    }

    public static int getVarcInt(VarClientInt varClientInt) {
        return client.getVar(varClientInt);
    }

    public static String getVarcStr(VarClientStr varClientStr) {
        return client.getVar(varClientStr);
    }
}
