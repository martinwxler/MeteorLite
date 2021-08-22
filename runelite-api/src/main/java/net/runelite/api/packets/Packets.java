package net.runelite.api.packets;

import net.runelite.api.Client;

public class Packets {
    public static class Dialog {
        public static void sendNumberInput(Client client, int number) {
            queuePacket(client, client.getNumberInputPacket(), number);
        }

        public static void sendTextInput(Client client, String text) {
            queuePacket(client, client.getTextInputPacket(), text);
        }

        public static void sendNameInput(Client client, String name) {
            queuePacket(client, client.getNameInputPacket(), name);
        }
    }

    public static void queuePacket(Client client, ClientPacket clientPacket, Object... data) {
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
