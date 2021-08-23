package meteor.plugins.api.packets;

import net.runelite.api.Client;
import net.runelite.api.packets.ClientPacket;
import net.runelite.api.packets.PacketBufferNode;
import net.runelite.api.packets.PacketWriter;

import javax.inject.Inject;

public class Packets {
    @Inject
    private static Client client;
    
    public static class Dialog {
        public static void sendNumberInput(int number) {
            queuePacket(client.getNumberInputPacket(), number);
        }

        public static void sendTextInput(String text) {
            queuePacket(client.getTextInputPacket(), text);
        }

        public static void sendNameInput(String name) {
            queuePacket(client.getNameInputPacket(), name);
        }
    }

    public static void queuePacket(ClientPacket clientPacket, Object... data) {
        PacketWriter writer = client.getPacketWriter();
        PacketBufferNode packet = client.preparePacket(clientPacket, writer.getIsaacCipher());

        for (Object o : data) {
            if (o instanceof Byte) {
                packet.getPacketBuffer().writeByte$api((int) o);
                continue;
            }

            if (o instanceof Short) {
                packet.getPacketBuffer().writeShort$api((int) o);
                continue;
            }

            if (o instanceof Integer) {
                packet.getPacketBuffer().writeInt$api((int) o);
                continue;
            }

            if (o instanceof Long) {
                packet.getPacketBuffer().writeLong$api((long) o);
                continue;
            }

            if (o instanceof String) {
                packet.getPacketBuffer().writeStringCp1252NullTerminated$api( (String) o);
                continue;
            }

            // invalid data
            return;
        }

        writer.queuePacket(packet);
    }
}
