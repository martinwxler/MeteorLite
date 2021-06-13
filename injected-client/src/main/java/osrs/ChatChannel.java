package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("cs")
@Implements("ChatChannel")
public class ChatChannel {
   @ObfuscatedName("s")
   @Export("Tiles_hue")
   static int[] Tiles_hue;
   @ObfuscatedName("ey")
   @ObfuscatedGetter(
      intValue = -495301739
   )
   static int field1110;
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "[Lbi;"
   )
   @Export("messages")
   Message[] messages = new Message[100];
   @ObfuscatedName("f")
   @ObfuscatedGetter(
      intValue = -1182640923
   )
   @Export("count")
   int count;

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;I)Lbi;",
      garbageValue = "437343873"
   )
   @Export("addMessage")
   Message addMessage(int var1, String var2, String var3, String var4) {
      Message var5 = this.messages[99];

      for(int var6 = this.count; var6 > 0; --var6) {
         if (var6 != 100) {
            this.messages[var6] = this.messages[var6 - 1];
         }
      }

      if (var5 == null) {
         var5 = new Message(var1, var2, var4, var3);
      } else {
         var5.remove();
         var5.removeDual();
         var5.set(var1, var2, var4, var3);
      }

      this.messages[0] = var5;
      if (this.count < 100) {
         ++this.count;
      }

      return var5;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "(II)Lbi;",
      garbageValue = "-94614591"
   )
   @Export("getMessage")
   Message getMessage(int var1) {
      return var1 >= 0 && var1 < this.count ? this.messages[var1] : null;
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "(B)I",
      garbageValue = "-36"
   )
   @Export("size")
   int size() {
      return this.count;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(Ljv;Ljv;I)V",
      garbageValue = "-1209366980"
   )
   public static void method2012(AbstractArchive var0, AbstractArchive var1) {
      class404.KitDefinition_archive = var0;
      KitDefinition.KitDefinition_modelsArchive = var1;
      ModelData0.KitDefinition_fileCount = class404.KitDefinition_archive.getGroupFileCount(3);
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "([Ljava/lang/String;[II)V",
      garbageValue = "-652699785"
   )
   public static void method2013(String[] var0, int[] var1) {
      TaskHandler.method2518(var0, var1, 0, var0.length - 1);
   }

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      descriptor = "(ILcf;ZB)I",
      garbageValue = "-84"
   )
   static int method2009(int var0, Script var1, boolean var2) {
      boolean var3 = true;
      Widget var4;
      if (var0 >= 2000) {
         var0 -= 1000;
         var4 = Frames.getWidget(Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize]);
         var3 = false;
      } else {
         var4 = var2 ? class13.scriptDotWidget : Interpreter.scriptActiveWidget;
      }

      int var5;
      if (var0 == 1300) {
         var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] - 1;
         if (var5 >= 0 && var5 <= 9) {
            var4.setAction(var5, Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize]);
            return 1;
         } else {
            --Interpreter.Interpreter_stringStackSize;
            return 1;
         }
      } else {
         int var6;
         if (var0 == 1301) {
            class44.Interpreter_intStackSize -= 2;
            var5 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
            var6 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1];
            var4.parent = ModeWhere.getWidgetChild(var5, var6);
            return 1;
         } else if (var0 == 1302) {
            var4.isScrollBar = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
            return 1;
         } else if (var0 == 1303) {
            var4.dragZoneSize = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
            return 1;
         } else if (var0 == 1304) {
            var4.dragThreshold = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
            return 1;
         } else if (var0 == 1305) {
            var4.dataText = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
            return 1;
         } else if (var0 == 1306) {
            var4.spellActionName = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
            return 1;
         } else if (var0 == 1307) {
            var4.actions = null;
            return 1;
         } else if (var0 == 1308) {
            var4.prioritizeMenuEntry = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
            return 1;
         } else if (var0 == 1309) {
            --class44.Interpreter_intStackSize;
            return 1;
         } else {
            int var7;
            byte[] var8;
            if (var0 != 1350) {
               byte var11;
               if (var0 == 1351) {
                  class44.Interpreter_intStackSize -= 2;
                  var11 = 10;
                  var8 = new byte[]{(byte)Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize]};
                  byte[] var10 = new byte[]{(byte)Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1]};
                  class253.Widget_setKey(var4, var11, var8, var10);
                  return 1;
               } else if (var0 == 1352) {
                  class44.Interpreter_intStackSize -= 3;
                  var5 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize] - 1;
                  var6 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1];
                  var7 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 2];
                  if (var5 >= 0 && var5 <= 9) {
                     IsaacCipher.Widget_setKeyRate(var4, var5, var6, var7);
                     return 1;
                  } else {
                     throw new RuntimeException();
                  }
               } else if (var0 == 1353) {
                  var11 = 10;
                  var6 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                  var7 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                  IsaacCipher.Widget_setKeyRate(var4, var11, var6, var7);
                  return 1;
               } else if (var0 == 1354) {
                  --class44.Interpreter_intStackSize;
                  var5 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize] - 1;
                  if (var5 >= 0 && var5 <= 9) {
                     class23.Widget_setKeyIgnoreHeld(var4, var5);
                     return 1;
                  } else {
                     throw new RuntimeException();
                  }
               } else if (var0 == 1355) {
                  var11 = 10;
                  class23.Widget_setKeyIgnoreHeld(var4, var11);
                  return 1;
               } else {
                  return 2;
               }
            } else {
               byte[] var9 = null;
               var8 = null;
               if (var3) {
                  class44.Interpreter_intStackSize -= 10;

                  for(var7 = 0; var7 < 10 && Interpreter.Interpreter_intStack[var7 + class44.Interpreter_intStackSize] >= 0; var7 += 2) {
                     ;
                  }

                  if (var7 > 0) {
                     var9 = new byte[var7 / 2];
                     var8 = new byte[var7 / 2];

                     for(var7 -= 2; var7 >= 0; var7 -= 2) {
                        var9[var7 / 2] = (byte)Interpreter.Interpreter_intStack[var7 + class44.Interpreter_intStackSize];
                        var8[var7 / 2] = (byte)Interpreter.Interpreter_intStack[var7 + class44.Interpreter_intStackSize + 1];
                     }
                  }
               } else {
                  class44.Interpreter_intStackSize -= 2;
                  var9 = new byte[]{(byte)Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize]};
                  var8 = new byte[]{(byte)Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1]};
               }

               var7 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] - 1;
               if (var7 >= 0 && var7 <= 9) {
                  class253.Widget_setKey(var4, var7, var9, var8);
                  return 1;
               } else {
                  throw new RuntimeException();
               }
            }
         }
      }
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "1145228216"
   )
   public static void method2015() {
      PlayerComposition.PlayerAppearance_cachedModels.clear();
   }

   @ObfuscatedName("gd")
   @ObfuscatedSignature(
      descriptor = "(III)V",
      garbageValue = "694193640"
   )
   static final void method2014(int var0, int var1) {
      if (Client.hintArrowType == 2) {
         GrandExchangeEvents.worldToScreen((Client.hintArrowX - VertexNormal.baseX << 7) + Client.hintArrowSubX, (Client.hintArrowY - SoundSystem.baseY << 7) + Client.hintArrowSubY, Client.hintArrowHeight * 2);
         if (Client.viewportTempX > -1 && Client.cycle % 20 < 10) {
            PacketBufferNode.headIconHintSprites[0].drawTransBgAt(var0 + Client.viewportTempX - 12, Client.viewportTempY + var1 - 28);
         }
      }

   }
}
