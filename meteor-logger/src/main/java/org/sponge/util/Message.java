package org.sponge.util;

import static org.sponge.util.Logger.ANSI_GREEN;
import static org.sponge.util.Logger.ANSI_RESET;

public class Message {

  public String message;

  Message(String message) {
    this.message = message;
  }

  public static Message newMessage() {
    return new Message("");
  }

  public Message add(String ansiColor, String text) {
    if (ansiColor == null || ansiColor.equals(""))
      return addDefault(text);
    return new Message(message + ansiColor + text);
  }

  public Message addDefault(String text) {
    return new Message(message + ANSI_GREEN + text);
  }

  public String build() {
    return message + ANSI_RESET;
  }
}
