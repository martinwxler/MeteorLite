package net.runelite.http.api.wiseoldman;

public enum Period {
  FIVE_MIN("5min"),
  DAY("day"),
  WEEK("week"),
  MONTH("month"),
  YEAR("year");

  private final String name;

  Period(String s) {
    this.name = s;
  }
}
