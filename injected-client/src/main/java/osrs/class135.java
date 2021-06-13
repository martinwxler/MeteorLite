package osrs;

import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ed")
public class class135 {
   @ObfuscatedName("f")
   public short[] field1537;
   @ObfuscatedName("y")
   public short[] field1538;

   public class135(int var1) {
      ItemComposition var2 = class260.ItemDefinition_get(var1);
      if (var2.method3024()) {
         this.field1537 = new short[var2.recolorTo.length];
         System.arraycopy(var2.recolorTo, 0, this.field1537, 0, this.field1537.length);
      }

      if (var2.method3025()) {
         this.field1538 = new short[var2.retextureTo.length];
         System.arraycopy(var2.retextureTo, 0, this.field1538, 0, this.field1538.length);
      }

   }

   @ObfuscatedName("d")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "660073374"
   )
   public static void method2601() {
      KitDefinition.KitDefinition_cached.clear();
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      descriptor = "(IIB)V",
      garbageValue = "-54"
   )
   public static final void method2599(int var0, int var1) {
      ViewportMouse.ViewportMouse_x = var0;
      ViewportMouse.ViewportMouse_y = var1;
      ViewportMouse.ViewportMouse_isInViewport = true;
      ViewportMouse.ViewportMouse_entityCount = 0;
      ViewportMouse.ViewportMouse_false0 = false;
   }

   @ObfuscatedName("l")
   @ObfuscatedSignature(
      descriptor = "(ILcf;ZI)I",
      garbageValue = "1611533259"
   )
   static int method2600(int var0, Script var1, boolean var2) {
      Widget var3;
      if (var0 >= 2000) {
         var0 -= 1000;
         var3 = Frames.getWidget(Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize]);
      } else {
         var3 = var2 ? class13.scriptDotWidget : Interpreter.scriptActiveWidget;
      }

      String var4 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
      int[] var5 = null;
      if (var4.length() > 0 && var4.charAt(var4.length() - 1) == 'Y') {
         int var6 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
         if (var6 > 0) {
            for(var5 = new int[var6]; var6-- > 0; var5[var6] = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize]) {
               ;
            }
         }

         var4 = var4.substring(0, var4.length() - 1);
      }

      Object[] var8 = new Object[var4.length() + 1];

      int var7;
      for(var7 = var8.length - 1; var7 >= 1; --var7) {
         if (var4.charAt(var7 - 1) == 's') {
            var8[var7] = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
         } else {
            var8[var7] = new Integer(Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize]);
         }
      }

      var7 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
      if (var7 != -1) {
         var8[0] = new Integer(var7);
      } else {
         var8 = null;
      }

      if (var0 == 1400) {
         var3.onClick = var8;
      } else if (var0 == 1401) {
         var3.onHold = var8;
      } else if (var0 == 1402) {
         var3.onRelease = var8;
      } else if (var0 == 1403) {
         var3.onMouseOver = var8;
      } else if (var0 == 1404) {
         var3.onMouseLeave = var8;
      } else if (var0 == 1405) {
         var3.onDrag = var8;
      } else if (var0 == 1406) {
         var3.onTargetLeave = var8;
      } else if (var0 == 1407) {
         var3.onVarTransmit = var8;
         var3.varTransmitTriggers = var5;
      } else if (var0 == 1408) {
         var3.onTimer = var8;
      } else if (var0 == 1409) {
         var3.onOp = var8;
      } else if (var0 == 1410) {
         var3.onDragComplete = var8;
      } else if (var0 == 1411) {
         var3.onClickRepeat = var8;
      } else if (var0 == 1412) {
         var3.onMouseRepeat = var8;
      } else if (var0 == 1414) {
         var3.onInvTransmit = var8;
         var3.invTransmitTriggers = var5;
      } else if (var0 == 1415) {
         var3.onStatTransmit = var8;
         var3.statTransmitTriggers = var5;
      } else if (var0 == 1416) {
         var3.onTargetEnter = var8;
      } else if (var0 == 1417) {
         var3.onScroll = var8;
      } else if (var0 == 1418) {
         var3.onChatTransmit = var8;
      } else if (var0 == 1419) {
         var3.onKey = var8;
      } else if (var0 == 1420) {
         var3.onFriendTransmit = var8;
      } else if (var0 == 1421) {
         var3.onClanTransmit = var8;
      } else if (var0 == 1422) {
         var3.onMiscTransmit = var8;
      } else if (var0 == 1423) {
         var3.onDialogAbort = var8;
      } else if (var0 == 1424) {
         var3.onSubChange = var8;
      } else if (var0 == 1425) {
         var3.onStockTransmit = var8;
      } else if (var0 == 1426) {
         var3.field3077 = var8;
      } else if (var0 == 1427) {
         var3.onResize = var8;
      } else if (var0 == 1428) {
         var3.field3043 = var8;
      } else {
         if (var0 != 1429) {
            return 2;
         }

         var3.field3071 = var8;
      }

      var3.hasListener = true;
      return 1;
   }
}
