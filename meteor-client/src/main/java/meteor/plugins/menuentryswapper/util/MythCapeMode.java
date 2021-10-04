package meteor.plugins.menuentryswapper.util;

public enum MythCapeMode
{
  OFF("None"),
  INVENTORY("Inventory"),
  EQUIPPED("Worn"),
  ALWAYS("Both");

  private final String name;

  MythCapeMode(String name)
  {
    this.name = name;
  }

  @Override
  public String toString()
  {
    return name;
  }
}