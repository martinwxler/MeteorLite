package meteor.plugins.menuentryswapper.util;

public enum DrakansMedallionMode
{
  OFF("Off"),
  DARKMEYER_WORN("Worn Darkmeyer"),
  SLEPE_WORN("Worn Slepe"),
  VER_SINHAZA_WORN("Worn Ver Sinhaza"),
  DARKMEYER_INV("Inventory Darkmeyer"),
  SLEPE_INV("Inventory Slepe"),
  VER_SINHAZA_INV("Inventory Ver Sinhaza"),
  DARKMEYER_ALWAYS("Both Darkmeyer"),
  SLEPE_ALWAYS("Both Slepe"),
  VER_SINHAZA_ALWAYS("Both Ver Sinhaza");


  private final String name;

  DrakansMedallionMode(String name)
  {
    this.name = name;
  }

  @Override
  public String toString()
  {
    return name;
  }
}