package osrs;

import java.math.BigInteger;
import java.util.Date;
import net.runelite.mapping.Export;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("cv")
public class class80 {
   @ObfuscatedName("p")
   static final BigInteger field1006 = new BigInteger("80782894952180643741752986186714059433953886149239752893425047584684715842049");
   @ObfuscatedName("j")
   static final BigInteger field1007 = new BigInteger("7237300117305667488707183861728052766358166655052137727439795191253340127955075499635575104901523446809299097934591732635674173519120047404024393881551683");
   @ObfuscatedName("er")
   @Export("worldHost")
   static String worldHost;

   @ObfuscatedName("j")
   @ObfuscatedSignature(
      descriptor = "(IZS)Ljava/lang/String;",
      garbageValue = "7104"
   )
   @Export("intToString")
   public static String intToString(int var0, boolean var1) {
      return var1 && var0 >= 0 ? Script.method1998(var0, 10, var1) : Integer.toString(var0);
   }

   @ObfuscatedName("l")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "1650201726"
   )
   public static final void method1906() {
      ViewportMouse.ViewportMouse_isInViewport = false;
      ViewportMouse.ViewportMouse_entityCount = 0;
   }

   @ObfuscatedName("ad")
   @ObfuscatedSignature(
      descriptor = "(ILcf;ZI)I",
      garbageValue = "1607858695"
   )
   static int method1902(int var0, Script var1, boolean var2) {
      String var3;
      int var4;
      if (var0 == 4100) {
         var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
         var4 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
         Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var3 + var4;
         return 1;
      } else {
         String var5;
         if (var0 == 4101) {
            Interpreter.Interpreter_stringStackSize -= 2;
            var3 = Interpreter.Interpreter_stringStack[Interpreter.Interpreter_stringStackSize];
            var5 = Interpreter.Interpreter_stringStack[Interpreter.Interpreter_stringStackSize + 1];
            Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var3 + var5;
            return 1;
         } else if (var0 == 4102) {
            var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
            var4 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
            Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var3 + intToString(var4, true);
            return 1;
         } else if (var0 == 4103) {
            var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
            Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var3.toLowerCase();
            return 1;
         } else {
            int var6;
            int var7;
            if (var0 == 4104) {
               var7 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
               long var14 = 86400000L * ((long)var7 + 11745L);
               Interpreter.Interpreter_calendar.setTime(new Date(var14));
               var6 = Interpreter.Interpreter_calendar.get(5);
               int var15 = Interpreter.Interpreter_calendar.get(2);
               int var16 = Interpreter.Interpreter_calendar.get(1);
               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var6 + "-" + Interpreter.Interpreter_MONTHS[var15] + "-" + var16;
               return 1;
            } else if (var0 != 4105) {
               if (var0 == 4106) {
                  var7 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = Integer.toString(var7);
                  return 1;
               } else if (var0 == 4107) {
                  Interpreter.Interpreter_stringStackSize -= 2;
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = UserComparator10.method2474(Calendar.compareStrings(Interpreter.Interpreter_stringStack[Interpreter.Interpreter_stringStackSize], Interpreter.Interpreter_stringStack[Interpreter.Interpreter_stringStackSize + 1], class378.clientLanguage));
                  return 1;
               } else {
                  int var8;
                  byte[] var9;
                  Font var10;
                  if (var0 == 4108) {
                     var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
                     class44.Interpreter_intStackSize -= 2;
                     var4 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
                     var8 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1];
                     var9 = Decimator.archive13.takeFile(var8, 0);
                     var10 = new Font(var9);
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var10.lineCount(var3, var4);
                     return 1;
                  } else if (var0 == 4109) {
                     var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
                     class44.Interpreter_intStackSize -= 2;
                     var4 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
                     var8 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1];
                     var9 = Decimator.archive13.takeFile(var8, 0);
                     var10 = new Font(var9);
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var10.lineWidth(var3, var4);
                     return 1;
                  } else if (var0 == 4110) {
                     Interpreter.Interpreter_stringStackSize -= 2;
                     var3 = Interpreter.Interpreter_stringStack[Interpreter.Interpreter_stringStackSize];
                     var5 = Interpreter.Interpreter_stringStack[Interpreter.Interpreter_stringStackSize + 1];
                     if (Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1) {
                        Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var3;
                     } else {
                        Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var5;
                     }

                     return 1;
                  } else if (var0 == 4111) {
                     var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
                     Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = AbstractFont.escapeBrackets(var3);
                     return 1;
                  } else if (var0 == 4112) {
                     var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
                     var4 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                     Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var3 + (char)var4;
                     return 1;
                  } else if (var0 == 4113) {
                     var7 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = class245.isCharPrintable((char)var7) ? 1 : 0;
                     return 1;
                  } else if (var0 == 4114) {
                     var7 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = Login.isAlphaNumeric((char)var7) ? 1 : 0;
                     return 1;
                  } else if (var0 == 4115) {
                     var7 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = WorldMapElement.isCharAlphabetic((char)var7) ? 1 : 0;
                     return 1;
                  } else if (var0 == 4116) {
                     var7 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = UserComparator10.isDigit((char)var7) ? 1 : 0;
                     return 1;
                  } else if (var0 == 4117) {
                     var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
                     if (var3 != null) {
                        Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var3.length();
                     } else {
                        Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                     }

                     return 1;
                  } else if (var0 == 4118) {
                     var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
                     class44.Interpreter_intStackSize -= 2;
                     var4 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
                     var8 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1];
                     Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var3.substring(var4, var8);
                     return 1;
                  } else if (var0 == 4119) {
                     var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
                     StringBuilder var11 = new StringBuilder(var3.length());
                     boolean var12 = false;

                     for(var6 = 0; var6 < var3.length(); ++var6) {
                        char var13 = var3.charAt(var6);
                        if (var13 == '<') {
                           var12 = true;
                        } else if (var13 == '>') {
                           var12 = false;
                        } else if (!var12) {
                           var11.append(var13);
                        }
                     }

                     Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var11.toString();
                     return 1;
                  } else if (var0 == 4120) {
                     var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
                     var4 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var3.indexOf(var4);
                     return 1;
                  } else if (var0 == 4121) {
                     Interpreter.Interpreter_stringStackSize -= 2;
                     var3 = Interpreter.Interpreter_stringStack[Interpreter.Interpreter_stringStackSize];
                     var5 = Interpreter.Interpreter_stringStack[Interpreter.Interpreter_stringStackSize + 1];
                     var8 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var3.indexOf(var5, var8);
                     return 1;
                  } else if (var0 == 4122) {
                     var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
                     Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var3.toUpperCase();
                     return 1;
                  } else {
                     return 2;
                  }
               }
            } else {
               Interpreter.Interpreter_stringStackSize -= 2;
               var3 = Interpreter.Interpreter_stringStack[Interpreter.Interpreter_stringStackSize];
               var5 = Interpreter.Interpreter_stringStack[Interpreter.Interpreter_stringStackSize + 1];
               if (class93.localPlayer.appearance != null && class93.localPlayer.appearance.isFemale) {
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var5;
               } else {
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var3;
               }

               return 1;
            }
         }
      }
   }
}
