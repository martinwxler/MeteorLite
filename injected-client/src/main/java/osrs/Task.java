package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("eu")
@Implements("Task")
public class Task {
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "Leu;"
   )
   @Export("next")
   Task next;
   @ObfuscatedName("p")
   @Export("status")
   public volatile int status = 0;
   @ObfuscatedName("j")
   @ObfuscatedGetter(
      intValue = -308758867
   )
   @Export("type")
   int type;
   @ObfuscatedName("r")
   @Export("intArgument")
   public int intArgument;
   @ObfuscatedName("b")
   @Export("objectArgument")
   Object objectArgument;
   @ObfuscatedName("d")
   @Export("result")
   public volatile Object result;
}
