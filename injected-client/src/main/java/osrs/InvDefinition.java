package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ep")
@Implements("InvDefinition")
public class InvDefinition extends DualNode {
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "Ljv;"
   )
   @Export("InvDefinition_archive")
   static AbstractArchive InvDefinition_archive;
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "Lhz;"
   )
   @Export("InvDefinition_cached")
   static EvictingDualNodeHashTable InvDefinition_cached = new EvictingDualNodeHashTable(64);
   @ObfuscatedName("f")
   @ObfuscatedGetter(
      intValue = -1820336081
   )
   @Export("size")
   public int size = 0;

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "(Lnd;I)V",
      garbageValue = "1433861399"
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
      garbageValue = "-1191086190"
   )
   @Export("decodeNext")
   void decodeNext(Buffer var1, int var2) {
      if (var2 == 2) {
         this.size = var1.readUnsignedShort();
      }

   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(Ljv;II)Lin;",
      garbageValue = "-1662391635"
   )
   static MusicPatch method2609(AbstractArchive var0, int var1) {
      byte[] var2 = var0.takeFileFlat(var1);
      return var2 == null ? null : new MusicPatch(var2);
   }

   @ObfuscatedName("j")
   @ObfuscatedSignature(
      descriptor = "(ILcf;ZI)I",
      garbageValue = "1464061704"
   )
   static int method2619(int var0, Script var1, boolean var2) {
      int var3 = -1;
      Widget var4;
      if (var0 >= 2000) {
         var0 -= 1000;
         var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
         var4 = Frames.getWidget(var3);
      } else {
         var4 = var2 ? class13.scriptDotWidget : Interpreter.scriptActiveWidget;
      }

      if (var0 == 1100) {
         class44.Interpreter_intStackSize -= 2;
         var4.scrollX = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
         if (var4.scrollX > var4.scrollWidth - var4.width) {
            var4.scrollX = var4.scrollWidth - var4.width;
         }

         if (var4.scrollX < 0) {
            var4.scrollX = 0;
         }

         var4.scrollY = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1];
         if (var4.scrollY > var4.scrollHeight - var4.height) {
            var4.scrollY = var4.scrollHeight - var4.height;
         }

         if (var4.scrollY < 0) {
            var4.scrollY = 0;
         }

         WorldMapCacheName.invalidateWidget(var4);
         return 1;
      } else if (var0 == 1101) {
         var4.color = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
         WorldMapCacheName.invalidateWidget(var4);
         return 1;
      } else if (var0 == 1102) {
         var4.fill = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
         WorldMapCacheName.invalidateWidget(var4);
         return 1;
      } else if (var0 == 1103) {
         var4.transparencyTop = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
         WorldMapCacheName.invalidateWidget(var4);
         return 1;
      } else if (var0 == 1104) {
         var4.lineWid = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
         WorldMapCacheName.invalidateWidget(var4);
         return 1;
      } else if (var0 == 1105) {
         var4.spriteId2 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
         WorldMapCacheName.invalidateWidget(var4);
         return 1;
      } else if (var0 == 1106) {
         var4.spriteAngle = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
         WorldMapCacheName.invalidateWidget(var4);
         return 1;
      } else if (var0 == 1107) {
         var4.spriteTiling = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
         WorldMapCacheName.invalidateWidget(var4);
         return 1;
      } else if (var0 == 1108) {
         var4.modelType = 1;
         var4.modelId = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
         WorldMapCacheName.invalidateWidget(var4);
         return 1;
      } else if (var0 == 1109) {
         class44.Interpreter_intStackSize -= 6;
         var4.modelOffsetX = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
         var4.modelOffsetY = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1];
         var4.modelAngleX = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 2];
         var4.modelAngleY = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 3];
         var4.modelAngleZ = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 4];
         var4.modelZoom = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 5];
         WorldMapCacheName.invalidateWidget(var4);
         return 1;
      } else {
         int var5;
         if (var0 == 1110) {
            var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
            if (var5 != var4.sequenceId) {
               var4.sequenceId = var5;
               var4.modelFrame = 0;
               var4.modelFrameCycle = 0;
               WorldMapCacheName.invalidateWidget(var4);
            }

            return 1;
         } else if (var0 == 1111) {
            var4.modelOrthog = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
            WorldMapCacheName.invalidateWidget(var4);
            return 1;
         } else if (var0 == 1112) {
            String var8 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
            if (!var8.equals(var4.text)) {
               var4.text = var8;
               WorldMapCacheName.invalidateWidget(var4);
            }

            return 1;
         } else if (var0 == 1113) {
            var4.fontId = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
            WorldMapCacheName.invalidateWidget(var4);
            return 1;
         } else if (var0 == 1114) {
            class44.Interpreter_intStackSize -= 3;
            var4.textXAlignment = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
            var4.textYAlignment = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1];
            var4.textLineHeight = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 2];
            WorldMapCacheName.invalidateWidget(var4);
            return 1;
         } else if (var0 == 1115) {
            var4.textShadowed = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
            WorldMapCacheName.invalidateWidget(var4);
            return 1;
         } else if (var0 == 1116) {
            var4.outline = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
            WorldMapCacheName.invalidateWidget(var4);
            return 1;
         } else if (var0 == 1117) {
            var4.spriteShadow = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
            WorldMapCacheName.invalidateWidget(var4);
            return 1;
         } else if (var0 == 1118) {
            var4.spriteFlipV = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
            WorldMapCacheName.invalidateWidget(var4);
            return 1;
         } else if (var0 == 1119) {
            var4.spriteFlipH = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
            WorldMapCacheName.invalidateWidget(var4);
            return 1;
         } else if (var0 == 1120) {
            class44.Interpreter_intStackSize -= 2;
            var4.scrollWidth = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
            var4.scrollHeight = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1];
            WorldMapCacheName.invalidateWidget(var4);
            if (var3 != -1 && var4.type == 0) {
               class313.revalidateWidgetScroll(Widget.Widget_interfaceComponents[var3 >> 16], var4, false);
            }

            return 1;
         } else if (var0 == 1121) {
            class22.resumePauseWidget(var4.id, var4.childIndex);
            Client.meslayerContinueWidget = var4;
            WorldMapCacheName.invalidateWidget(var4);
            return 1;
         } else if (var0 == 1122) {
            var4.spriteId = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
            WorldMapCacheName.invalidateWidget(var4);
            return 1;
         } else if (var0 == 1123) {
            var4.color2 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
            WorldMapCacheName.invalidateWidget(var4);
            return 1;
         } else if (var0 == 1124) {
            var4.transparencyBot = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
            WorldMapCacheName.invalidateWidget(var4);
            return 1;
         } else if (var0 == 1125) {
            var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
            FillMode var7 = (FillMode)Messages.findEnumerated(VertexNormal.FillMode_values(), var5);
            if (var7 != null) {
               var4.fillMode = var7;
               WorldMapCacheName.invalidateWidget(var4);
            }

            return 1;
         } else {
            boolean var6;
            if (var0 == 1126) {
               var6 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
               var4.field2992 = var6;
               return 1;
            } else if (var0 == 1127) {
               var6 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
               var4.modelTransparency = var6;
               return 1;
            } else if (var0 == 1128) {
               class44.Interpreter_intStackSize -= 2;
               return 1;
            } else {
               return 2;
            }
         }
      }
   }

   @ObfuscatedName("jo")
   @ObfuscatedSignature(
      descriptor = "(Lio;I)Z",
      garbageValue = "-1051636424"
   )
   @Export("runCs1")
   static final boolean runCs1(Widget var0) {
      if (var0.cs1Comparisons == null) {
         return false;
      } else {
         for(int var1 = 0; var1 < var0.cs1Comparisons.length; ++var1) {
            int var2 = ModeWhere.method5160(var0, var1);
            int var3 = var0.cs1ComparisonValues[var1];
            if (var0.cs1Comparisons[var1] == 2) {
               if (var2 >= var3) {
                  return false;
               }
            } else if (var0.cs1Comparisons[var1] == 3) {
               if (var2 <= var3) {
                  return false;
               }
            } else if (var0.cs1Comparisons[var1] == 4) {
               if (var3 == var2) {
                  return false;
               }
            } else if (var2 != var3) {
               return false;
            }
         }

         return true;
      }
   }
}
