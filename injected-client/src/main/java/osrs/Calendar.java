package osrs;

import java.util.TimeZone;
import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ip")
@Implements("Calendar")
public class Calendar {
   @ObfuscatedName("v")
   @Export("MONTH_NAMES_ENGLISH_GERMAN")
   public static final String[][] MONTH_NAMES_ENGLISH_GERMAN = new String[][]{{"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"}, {"Jan", "Feb", "Mär", "Apr", "Mai", "Jun", "Jul", "Aug", "Sep", "Okt", "Nov", "Dez"}, {"jan", "fév", "mars", "avr", "mai", "juin", "juil", "août", "sept", "oct", "nov", "déc"}, {"jan", "fev", "mar", "abr", "mai", "jun", "jul", "ago", "set", "out", "nov", "dez"}, {"jan", "feb", "mrt", "apr", "mei", "jun", "jul", "aug", "sep", "okt", "nov", "dec"}, {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"}, {"ene", "feb", "mar", "abr", "may", "jun", "jul", "ago", "sep", "oct", "nov", "dic"}};
   @ObfuscatedName("n")
   @Export("DAYS_OF_THE_WEEK")
   public static final String[] DAYS_OF_THE_WEEK = new String[]{"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
   @ObfuscatedName("f")
   @Export("Calendar_calendar")
   public static java.util.Calendar Calendar_calendar;
   @ObfuscatedName("p")
   @Export("SpriteBuffer_yOffsets")
   public static int[] SpriteBuffer_yOffsets;

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(Ljava/lang/CharSequence;Ljava/lang/CharSequence;Lkk;I)I",
      garbageValue = "61619413"
   )
   @Export("compareStrings")
   public static int compareStrings(CharSequence var0, CharSequence var1, Language var2) {
      int var3 = var0.length();
      int var4 = var1.length();
      int var5 = 0;
      int var6 = 0;
      char var7 = 0;
      char var8 = 0;

      char var14;
      while(var5 - var7 < var3 || var6 - var8 < var4) {
         if (var5 - var7 >= var3) {
            return -1;
         }

         if (var6 - var8 >= var4) {
            return 1;
         }

         char var9;
         if (var7 != 0) {
            var9 = var7;
            boolean var10 = false;
         } else {
            var9 = var0.charAt(var5++);
         }

         if (var8 != 0) {
            var14 = var8;
            boolean var11 = false;
         } else {
            var14 = var1.charAt(var6++);
         }

         var7 = MusicPatchPcmStream.method4620(var9);
         var8 = MusicPatchPcmStream.method4620(var14);
         var9 = standardizeChar(var9, var2);
         var14 = standardizeChar(var14, var2);
         if (var9 != var14 && Character.toUpperCase(var9) != Character.toUpperCase(var14)) {
            var9 = Character.toLowerCase(var9);
            var14 = Character.toLowerCase(var14);
            if (var9 != var14) {
               return WorldMapLabelSize.lowercaseChar(var9, var2) - WorldMapLabelSize.lowercaseChar(var14, var2);
            }
         }
      }

      int var15 = Math.min(var3, var4);

      int var16;
      for(var16 = 0; var16 < var15; ++var16) {
         if (var2 == Language.Language_FR) {
            var5 = var3 - 1 - var16;
            var6 = var4 - 1 - var16;
         } else {
            var6 = var16;
            var5 = var16;
         }

         char var12 = var0.charAt(var5);
         var14 = var1.charAt(var6);
         if (var12 != var14 && Character.toUpperCase(var12) != Character.toUpperCase(var14)) {
            var12 = Character.toLowerCase(var12);
            var14 = Character.toLowerCase(var14);
            if (var14 != var12) {
               return WorldMapLabelSize.lowercaseChar(var12, var2) - WorldMapLabelSize.lowercaseChar(var14, var2);
            }
         }
      }

      var16 = var3 - var4;
      if (var16 != 0) {
         return var16;
      } else {
         for(int var17 = 0; var17 < var15; ++var17) {
            var14 = var0.charAt(var17);
            char var13 = var1.charAt(var17);
            if (var13 != var14) {
               return WorldMapLabelSize.lowercaseChar(var14, var2) - WorldMapLabelSize.lowercaseChar(var13, var2);
            }
         }

         return 0;
      }
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "(CLkk;I)C",
      garbageValue = "-1670785567"
   )
   @Export("standardizeChar")
   static char standardizeChar(char var0, Language var1) {
      if (var0 >= 192 && var0 <= 255) {
         if (var0 >= 192 && var0 <= 198) {
            return 'A';
         }

         if (var0 == 199) {
            return 'C';
         }

         if (var0 >= 200 && var0 <= 203) {
            return 'E';
         }

         if (var0 >= 204 && var0 <= 207) {
            return 'I';
         }

         if (var0 == 209 && var1 != Language.Language_ES) {
            return 'N';
         }

         if (var0 >= 210 && var0 <= 214) {
            return 'O';
         }

         if (var0 >= 217 && var0 <= 220) {
            return 'U';
         }

         if (var0 == 221) {
            return 'Y';
         }

         if (var0 == 223) {
            return 's';
         }

         if (var0 >= 224 && var0 <= 230) {
            return 'a';
         }

         if (var0 == 231) {
            return 'c';
         }

         if (var0 >= 232 && var0 <= 235) {
            return 'e';
         }

         if (var0 >= 236 && var0 <= 239) {
            return 'i';
         }

         if (var0 == 241 && var1 != Language.Language_ES) {
            return 'n';
         }

         if (var0 >= 242 && var0 <= 246) {
            return 'o';
         }

         if (var0 >= 249 && var0 <= 252) {
            return 'u';
         }

         if (var0 == 253 || var0 == 255) {
            return 'y';
         }
      }

      if (var0 == 338) {
         return 'O';
      } else if (var0 == 339) {
         return 'o';
      } else {
         return var0 == 376 ? 'Y' : var0;
      }
   }

   static {
      java.util.Calendar.getInstance();
      Calendar_calendar = java.util.Calendar.getInstance(TimeZone.getTimeZone("GMT"));
   }
}
