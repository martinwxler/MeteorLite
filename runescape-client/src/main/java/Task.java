import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("el")
@Implements("Task")
public class Task {

  @ObfuscatedName("j")
  @Export("status")
  public volatile int status;
  @ObfuscatedName("m")
  @Export("intArgument")
  public int intArgument;
  @ObfuscatedName("h")
  @Export("result")
  public volatile Object result;
  @ObfuscatedName("f")
  @ObfuscatedSignature(
      descriptor = "Lel;"
  )
  @Export("next")
  Task next;
  @ObfuscatedName("o")
  @ObfuscatedGetter(
      intValue = 522618097
  )
  @Export("type")
  int type;
  @ObfuscatedName("r")
  @Export("objectArgument")
  Object objectArgument;

  Task() {
    this.status = 0; // L: 11
  } // L: 23
}
