package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ix")
@Implements("WorldMapDecorationType")
public enum WorldMapDecorationType implements Enumerated {
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "Lix;"
   )
   field3149(0, 0),
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "Lix;"
   )
   field3143(1, 0),
   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "Lix;"
   )
   field3167(2, 0),
   @ObfuscatedName("y")
   @ObfuscatedSignature(
      descriptor = "Lix;"
   )
   field3145(3, 0),
   @ObfuscatedName("p")
   @ObfuscatedSignature(
      descriptor = "Lix;"
   )
   field3146(9, 2),
   @ObfuscatedName("j")
   @ObfuscatedSignature(
      descriptor = "Lix;"
   )
   field3147(4, 1),
   @ObfuscatedName("r")
   @ObfuscatedSignature(
      descriptor = "Lix;"
   )
   field3148(5, 1),
   @ObfuscatedName("b")
   @ObfuscatedSignature(
      descriptor = "Lix;"
   )
   field3164(6, 1),
   @ObfuscatedName("d")
   @ObfuscatedSignature(
      descriptor = "Lix;"
   )
   field3150(7, 1),
   @ObfuscatedName("s")
   @ObfuscatedSignature(
      descriptor = "Lix;"
   )
   field3151(8, 1),
   @ObfuscatedName("u")
   @ObfuscatedSignature(
      descriptor = "Lix;"
   )
   field3142(12, 2),
   @ObfuscatedName("l")
   @ObfuscatedSignature(
      descriptor = "Lix;"
   )
   field3156(13, 2),
   @ObfuscatedName("o")
   @ObfuscatedSignature(
      descriptor = "Lix;"
   )
   field3163(14, 2),
   @ObfuscatedName("c")
   @ObfuscatedSignature(
      descriptor = "Lix;"
   )
   field3155(15, 2),
   @ObfuscatedName("e")
   @ObfuscatedSignature(
      descriptor = "Lix;"
   )
   field3166(16, 2),
   @ObfuscatedName("g")
   @ObfuscatedSignature(
      descriptor = "Lix;"
   )
   field3157(17, 2),
   @ObfuscatedName("a")
   @ObfuscatedSignature(
      descriptor = "Lix;"
   )
   field3158(18, 2),
   @ObfuscatedName("k")
   @ObfuscatedSignature(
      descriptor = "Lix;"
   )
   field3159(19, 2),
   @ObfuscatedName("m")
   @ObfuscatedSignature(
      descriptor = "Lix;"
   )
   field3152(20, 2),
   @ObfuscatedName("x")
   @ObfuscatedSignature(
      descriptor = "Lix;"
   )
   field3161(21, 2),
   @ObfuscatedName("z")
   @ObfuscatedSignature(
      descriptor = "Lix;"
   )
   field3162(10, 2),
   @ObfuscatedName("w")
   @ObfuscatedSignature(
      descriptor = "Lix;"
   )
   field3144(11, 2),
   @ObfuscatedName("t")
   @ObfuscatedSignature(
      descriptor = "Lix;"
   )
   field3154(22, 3);

   @ObfuscatedName("h")
   @ObfuscatedGetter(
      intValue = -2083476425
   )
   @Export("id")
   public final int id;

   @ObfuscatedSignature(
      descriptor = "(II)V",
      garbageValue = "0"
   )
   private WorldMapDecorationType(int var3, int var4) {
      this.id = var3;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(I)I",
      garbageValue = "-907662946"
   )
   @Export("rsOrdinal")
   public int rsOrdinal() {
      return this.id;
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      descriptor = "(ILcf;ZB)I",
      garbageValue = "29"
   )
   static int method4833(int var0, Script var1, boolean var2) {
      int var3;
      int var4;
      Widget var6;
      if (var0 == 100) {
         class44.Interpreter_intStackSize -= 3;
         var4 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
         var3 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1];
         int var9 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 2];
         if (var3 == 0) {
            throw new RuntimeException();
         } else {
            var6 = Frames.getWidget(var4);
            if (var6.children == null) {
               var6.children = new Widget[var9 + 1];
            }

            if (var6.children.length <= var9) {
               Widget[] var7 = new Widget[var9 + 1];

               for(int var8 = 0; var8 < var6.children.length; ++var8) {
                  var7[var8] = var6.children[var8];
               }

               var6.children = var7;
            }

            if (var9 > 0 && var6.children[var9 - 1] == null) {
               throw new RuntimeException("" + (var9 - 1));
            } else {
               Widget var10 = new Widget();
               var10.type = var3;
               var10.parentId = var10.id = var6.id;
               var10.childIndex = var9;
               var10.isIf3 = true;
               var6.children[var9] = var10;
               if (var2) {
                  class13.scriptDotWidget = var10;
               } else {
                  Interpreter.scriptActiveWidget = var10;
               }

               WorldMapCacheName.invalidateWidget(var6);
               return 1;
            }
         }
      } else {
         Widget var5;
         if (var0 == 101) {
            var5 = var2 ? class13.scriptDotWidget : Interpreter.scriptActiveWidget;
            var6 = Frames.getWidget(var5.id);
            var6.children[var5.childIndex] = null;
            WorldMapCacheName.invalidateWidget(var6);
            return 1;
         } else if (var0 == 102) {
            var5 = Frames.getWidget(Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize]);
            var5.children = null;
            WorldMapCacheName.invalidateWidget(var5);
            return 1;
         } else if (var0 == 103) {
            class44.Interpreter_intStackSize -= 3;
            return 1;
         } else if (var0 == 104) {
            --class44.Interpreter_intStackSize;
            return 1;
         } else if (var0 != 200) {
            if (var0 == 201) {
               var5 = Frames.getWidget(Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize]);
               if (var5 != null) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 1;
                  if (var2) {
                     class13.scriptDotWidget = var5;
                  } else {
                     Interpreter.scriptActiveWidget = var5;
                  }
               } else {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
               }

               return 1;
            } else if (var0 == 202) {
               Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1] = 0;
               return 1;
            } else if (var0 == 203) {
               Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize + 1] = 0;
               return 1;
            } else {
               return 2;
            }
         } else {
            class44.Interpreter_intStackSize -= 2;
            var4 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
            var3 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1];
            var6 = ModeWhere.getWidgetChild(var4, var3);
            if (var6 != null && var3 != -1) {
               Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 1;
               if (var2) {
                  class13.scriptDotWidget = var6;
               } else {
                  Interpreter.scriptActiveWidget = var6;
               }
            } else {
               Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
            }

            return 1;
         }
      }
   }

   @ObfuscatedName("z")
   @ObfuscatedSignature(
      descriptor = "(ZI)V",
      garbageValue = "-59563077"
   )
   public static void method4834(boolean var0) {
      if (var0 != ItemContainer.ItemDefinition_inMembersWorld) {
         SceneTilePaint.method4259();
         ItemContainer.ItemDefinition_inMembersWorld = var0;
      }

   }
}
