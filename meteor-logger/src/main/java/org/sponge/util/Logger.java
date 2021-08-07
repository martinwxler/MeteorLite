package org.sponge.util;

import java.util.concurrent.Callable;

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

  public Logger(String name) {
    this.name = name;
  }

  public void info(Object message) {
    printColorMessage(ANSI_WHITE, message);
  }

  public void warn(Object message) {
    printColorMessage(ANSI_YELLOW, message);
  }

  public void debug(Object message) {
    printColorMessage(ANSI_GREEN, message);
  }

  public void error(Object message) {
    printColorMessage(ANSI_RED, message);
  }

  private void printColorMessage(String ansiColor, Object message)
  {
    String tempName;
    if (plugin != null) {
      tempName = plugin;
    } else {
      tempName = name;
    }
    String header = Message.buildMessage()
        .changeColor(DEFAULT_CONTROLLER_COLOR)
        .addText("[" + tempName + "] ")
        .build();
    System.out.format(format, header, ansiColor + message);
    System.out.print(ANSI_RESET);
  }
}
