package meteor.plugins.menuentryswapper.util;

public enum MusicCapeMode
{
  OFF("None"),
  INVENTORY("Inventory"),
  EQUIPPED("Worn"),
  ALWAYS("Both");

  private final String name;

  MusicCapeMode(String name)
  {
    this.name = name;
  }

  @Override
  public String toString()
  {
    return name;
  }
}
