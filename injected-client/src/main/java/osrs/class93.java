package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("cc")
public class class93 {
   @ObfuscatedName("e")
   @ObfuscatedSignature(
      descriptor = "[Lnf;"
   )
   @Export("JagexCache_idxFiles")
   public static BufferedFile[] JagexCache_idxFiles;
   @ObfuscatedName("kr")
   @ObfuscatedSignature(
      descriptor = "Lce;"
   )
   @Export("localPlayer")
   static Player localPlayer;

   @ObfuscatedName("d")
   @Export("Entity_unpackID")
   public static int Entity_unpackID(long var0) {
      return (int)(var0 >>> 17 & 4294967295L);
   }

   @ObfuscatedName("x")
   @ObfuscatedSignature(
      descriptor = "(ILcf;ZI)I",
      garbageValue = "992004750"
   )
   static int method2080(int var0, Script var1, boolean var2) {
      Widget var3;
      if (var0 == 2700) {
         var3 = Frames.getWidget(Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize]);
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var3.itemId;
         return 1;
      } else if (var0 == 2701) {
         var3 = Frames.getWidget(Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize]);
         if (var3.itemId != -1) {
            Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var3.itemQuantity;
         } else {
            Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
         }

         return 1;
      } else if (var0 == 2702) {
         int var4 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
         InterfaceParent var5 = (InterfaceParent)Client.interfaceParents.get((long)var4);
         if (var5 != null) {
            Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 1;
         } else {
            Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
         }

         return 1;
      } else if (var0 == 2706) {
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = Client.rootInterface;
         return 1;
      } else {
         return 2;
      }
   }
}
