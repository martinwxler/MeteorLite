package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ex")
@Implements("VarpDefinition")
public class VarpDefinition extends DualNode {
   @ObfuscatedName("rr")
   @ObfuscatedGetter(
      intValue = 634754105
   )
   static int field1544;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "Ljv;"
   )
   @Export("VarpDefinition_archive")
   static AbstractArchive VarpDefinition_archive;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = -1782450907
   )
   @Export("VarpDefinition_fileCount")
   public static int VarpDefinition_fileCount;
   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "Lhz;"
   )
   @Export("VarpDefinition_cached")
   public static EvictingDualNodeHashTable VarpDefinition_cached = new EvictingDualNodeHashTable(64);
   @ObfuscatedName("y")
   @ObfuscatedGetter(
      intValue = 812709151
   )
   @Export("type")
   public int type = 0;

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "(Lnd;I)V",
      garbageValue = "1856210017"
   )
   @Export("decode")
   void decode(Buffer var1) {
      while(true) {
         int var2 = var1.readUnsignedByte();
         if (var2 == 0) {
            return;
         }

         this.decodeNext(var1, var2);
      }
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      descriptor = "(Lnd;II)V",
      garbageValue = "1230881703"
   )
   @Export("decodeNext")
   void decodeNext(Buffer var1, int var2) {
      if (var2 == 5) {
         this.type = var1.readUnsignedShort();
      }

   }

   @ObfuscatedName("p")
   public static int method2630(long var0) {
      return (int)(var0 >>> 0 & 127L);
   }

   @ObfuscatedName("k")
   @ObfuscatedSignature(
      descriptor = "(ILcf;ZI)I",
      garbageValue = "-2015232474"
   )
   static int method2629(int var0, Script var1, boolean var2) {
      Widget var3 = Frames.getWidget(Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize]);
      if (var0 == 2500) {
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var3.x;
         return 1;
      } else if (var0 == 2501) {
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var3.y;
         return 1;
      } else if (var0 == 2502) {
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var3.width;
         return 1;
      } else if (var0 == 2503) {
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var3.height;
         return 1;
      } else if (var0 == 2504) {
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var3.isHidden ? 1 : 0;
         return 1;
      } else if (var0 == 2505) {
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var3.parentId;
         return 1;
      } else {
         return 2;
      }
   }
}
