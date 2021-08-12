package meteor.util;

public class Timer {

  public long start;

  public Timer(long currentTimeMillis) {
    start = currentTimeMillis;
  }

  public Timer() {
  }

  public int getSecondsFromStart() {
    return (int) ((System.currentTimeMillis() - start) / 1000);
  }

  public int getSeconds() {
    return getSecondsFromStart() % 60;
  }

  public int getMinutesFromStart() {
    return getSecondsFromStart() / 60;
  }

  public int getMinutes() {
    return getMinutesFromStart() % 60;
  }

  public int getHoursFromStart() {
    return getMinutesFromStart() / 60;
  }

  public int getHours() {
    return getHoursFromStart() % 60;
  }

  public int getDaysFromStart() {
    return getHoursFromStart() / 24;
  }

  public int getDays() {
    return getDaysFromStart() % 365;
  }

  public void reset() {
    start = System.currentTimeMillis();
  }
}
