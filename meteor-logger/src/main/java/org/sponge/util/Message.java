package org.sponge.util;

import static org.sponge.util.Logger.ANSI_RESET;

public class Message {

    public String message;

    Message(String message)
    {
        this.message = message;
    }

    public static Message buildMessage()
    {
        return new Message("");
    }

    public Message changeColor(String ansiColor)
    {
        return new Message(message + ansiColor);
    }

    public Message addText(String text)
    {
        return new Message(message + text);
    }

    public String build()
    {
        return message + ANSI_RESET;
    }
}
