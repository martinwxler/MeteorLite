package org.sponge.util;

import java.util.concurrent.Callable;

public class Logger {

    public String name;
    public String plugin;
    public String cachedMessage;
    String format = "%-35s%s%n";


    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public Logger(String name)
    {
        this.name = name;
    }

    public void info(String message)
    {
        String tempName;
            if (plugin != null)
                tempName = plugin;
            else
                tempName = name;
            String header = Message.buildMessage()
                    .changeColor(ANSI_YELLOW)
                    .addText("[" + tempName + "] ")
                    .build();
        System.out.format(format, header, message);
        System.out.print(ANSI_RESET);
    }

    public void warn(String message)
    {
        String tempName;
        if (plugin != null)
            tempName = plugin;
        else
            tempName = name;
        String header = Message.buildMessage()
                .changeColor(ANSI_YELLOW)
                .addText("[" + tempName + "] ")
                .build();
        System.out.format(format, header, ANSI_BLUE + message);
        System.out.print(ANSI_RESET);
    }

    public void debug(String message)
    {
        String tempName;
        if (plugin != null)
            tempName = plugin;
        else
            tempName = name;
        String header = Message.buildMessage()
                .changeColor(ANSI_YELLOW)
                .addText("[" + tempName + "] ")
                .build();
        System.out.format(format, header, ANSI_GREEN + message);
        System.out.print(ANSI_RESET);
    }

    public void error(String message)
    {
        String tempName;
        if (plugin != null)
            tempName = plugin;
        else
            tempName = name;
        String header = Message.buildMessage()
                .changeColor(ANSI_YELLOW)
                .addText("[" + tempName + "] ")
                .build();        System.out.format(format, header, ANSI_RED + message);
        System.out.print(ANSI_RESET);
    }

    public void warn(String message, Exception ex) {
        warn(message);
    }

    public <V> void warn(String s, Callable<V> callable, Throwable ex) {
        warn(s + ":" + callable.toString());
    }

    public <V> void warn(String s, Runnable runnable, Throwable ex) {
        warn(s + ":" + runnable.toString());
    }

    public void event(String event, String message)
    {
        String tempName;
        if (plugin != null)
            tempName = plugin;
        else
            tempName = name;
        String header = Message.buildMessage()
                .changeColor(ANSI_YELLOW)
                .addText("[" + tempName + "] ")
                .build();
        System.out.format(format, header, ANSI_CYAN + "[" + event + "] " + ANSI_RESET + message);
        System.out.print(ANSI_RESET);
    }
}
