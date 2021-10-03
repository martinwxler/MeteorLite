package org.sponge.util;

import java.util.Arrays;

public class Logger {

  public static final String ANSI_RESET = "\u001B[0m";
  public static final String ANSI_BLACK = "\u001B[30m";
  public static final String ANSI_RED = "\u001B[31m";
  public static final String ANSI_GREEN = "\u001B[32m";
  public static final String ANSI_YELLOW = "\u001B[33m";
  public static final String ANSI_BLUE = "\u001B[34m";
  public static final String ANSI_PURPLE = "\u001B[35m";
  public static final String ANSI_CYAN = "\u001B[36m";
  public static final String ANSI_WHITE = "\u001B[37m";

  public static String DEFAULT_CONTROLLER_COLOR = ANSI_CYAN;
  public String name;
  public String plugin;
  String format = "%-35s%s%n";
  private static boolean debugOutput = false;

  public Logger(String name) {
    this.name = name;
  }

  public static Logger getLogger(Class<?> loggedClass) {
    return new Logger(loggedClass.getSimpleName());
  }

  public void info(Object message, Object... replacers) {
    printColorMessageReplacers(ANSI_WHITE, message, replacers);
  }

  public void warn(Object message, Object... replacers) {
    printColorMessageReplacers(ANSI_YELLOW, message, replacers);
  }

  public void warn(String message, Exception e) {
    printColorMessage(ANSI_RED, message);
    e.printStackTrace();
  }

  public void debug(Object message, Object... replacers) {
    if (!isDebugEnabled()) {
      return;
    }

    printColorMessageReplacers(ANSI_GREEN, message, replacers);
  }

  public void error(Object message, Object... replacers) {
    printColorMessageReplacers(ANSI_RED, message, replacers);
  }

  private void printColorMessage(String ansiColor, Object message)
  {
    String tempName;
    if (plugin != null) {
      tempName = plugin;
    } else {
      tempName = name;
    }
    String header = Message.newMessage()
            .add(DEFAULT_CONTROLLER_COLOR, "[" + tempName + "] ")
            .build();
    System.out.format(format, header, ansiColor + message);
    System.out.print(ANSI_RESET);
  }

  private void printColorMessageReplacers(String ansiColor, Object message, Object... replacers)
  {
    String sRef;
    try {
      sRef = (String)message;
    }
    catch (Exception e)
    {
      printColorMessage(ansiColor, message);
      return;
    }

    if (!sRef.contains("{}"))
    {
      printColorMessage(ansiColor, sRef);
      return;
    }

    StringBuilder finalMessage = new StringBuilder();
    Object[] replacersArray = Arrays.stream(replacers).toArray();
    int i = 0;

    for (String s : sRef.split("\\{}"))
    {
      if (i != replacersArray.length)
        finalMessage.append(s).append(replacersArray[i]);
      else
        finalMessage.append(s);
      i++;
    }

    printColorMessage(ansiColor, finalMessage);
  }

  public static void setDebugEnabled(boolean enabled) {
    debugOutput = enabled;
  }

  public static boolean isDebugEnabled() {
    return debugOutput;
  }

  public static String generateError(String s) {
    if (s.length() < 5)
      return "";
    String[] lines = s.split(" at ");
    StringBuilder output = new StringBuilder();
    if (lines.length > 0) {
      for (String line : lines) {
        if (line.length() < 10)
          continue;
        output.append(line.replace("\n", "")).append("\n");
      }
    }
     return ANSI_RED + output + ANSI_RESET;
  }
}
