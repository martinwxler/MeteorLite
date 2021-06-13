package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ly")
@Implements("Username")
public class Username implements Comparable {
   @ObfuscatedName("v")
   @Export("name")
   String name;
   @ObfuscatedName("n")
   @Export("cleanName")
   String cleanName;

   @ObfuscatedSignature(
      descriptor = "(Ljava/lang/String;Lnc;)V"
   )
   public Username(String var1, LoginType var2) {
      this.name = var1;
      String var3;
      if (var1 == null) {
         var3 = null;
      } else {
         label108: {
            int var4 = 0;

            int var5;
            boolean var6;
            char var7;
            for(var5 = var1.length(); var4 < var5; ++var4) {
               var7 = var1.charAt(var4);
               var6 = var7 == 160 || var7 == ' ' || var7 == '_' || var7 == '-';
               if (!var6) {
                  break;
               }
            }

            while(var5 > var4) {
               var7 = var1.charAt(var5 - 1);
               var6 = var7 == 160 || var7 == ' ' || var7 == '_' || var7 == '-';
               if (!var6) {
                  break;
               }

               --var5;
            }

            int var8 = var5 - var4;
            if (var8 >= 1) {
               byte var9;
               if (var2 == null) {
                  var9 = 12;
               } else {
                  switch(var2.field4092) {
                  case 8:
                     var9 = 20;
                     break;
                  default:
                     var9 = 12;
                  }
               }

               if (var8 <= var9) {
                  StringBuilder var10 = new StringBuilder(var8);

                  for(int var11 = var4; var11 < var5; ++var11) {
                     char var12 = var1.charAt(var11);
                     if (class14.method188(var12)) {
                        char var13;
                        switch(var12) {
                        case ' ':
                        case '-':
                        case '_':
                        case ' ':
                           var13 = '_';
                           break;
                        case '#':
                        case '[':
                        case ']':
                           var13 = var12;
                           break;
                        case 'À':
                        case 'Á':
                        case 'Â':
                        case 'Ã':
                        case 'Ä':
                        case 'à':
                        case 'á':
                        case 'â':
                        case 'ã':
                        case 'ä':
                           var13 = 'a';
                           break;
                        case 'Ç':
                        case 'ç':
                           var13 = 'c';
                           break;
                        case 'È':
                        case 'É':
                        case 'Ê':
                        case 'Ë':
                        case 'è':
                        case 'é':
                        case 'ê':
                        case 'ë':
                           var13 = 'e';
                           break;
                        case 'Í':
                        case 'Î':
                        case 'Ï':
                        case 'í':
                        case 'î':
                        case 'ï':
                           var13 = 'i';
                           break;
                        case 'Ñ':
                        case 'ñ':
                           var13 = 'n';
                           break;
                        case 'Ò':
                        case 'Ó':
                        case 'Ô':
                        case 'Õ':
                        case 'Ö':
                        case 'ò':
                        case 'ó':
                        case 'ô':
                        case 'õ':
                        case 'ö':
                           var13 = 'o';
                           break;
                        case 'Ù':
                        case 'Ú':
                        case 'Û':
                        case 'Ü':
                        case 'ù':
                        case 'ú':
                        case 'û':
                        case 'ü':
                           var13 = 'u';
                           break;
                        case 'ß':
                           var13 = 'b';
                           break;
                        case 'ÿ':
                        case 'Ÿ':
                           var13 = 'y';
                           break;
                        default:
                           var13 = Character.toLowerCase(var12);
                        }

                        if (var13 != 0) {
                           var10.append(var13);
                        }
                     }
                  }

                  if (var10.length() == 0) {
                     var3 = null;
                  } else {
                     var3 = var10.toString();
                  }
                  break label108;
               }
            }

            var3 = null;
         }
      }

      this.cleanName = var3;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(I)Ljava/lang/String;",
      garbageValue = "2072568808"
   )
   @Export("getName")
   public String getName() {
      return this.name;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "(B)Z",
      garbageValue = "0"
   )
   @Export("hasCleanName")
   public boolean hasCleanName() {
      return this.cleanName != null;
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "(Lly;I)I",
      garbageValue = "831558423"
   )
   @Export("compareToTyped")
   public int compareToTyped(Username var1) {
      if (this.cleanName == null) {
         return var1.cleanName == null ? 0 : 1;
      } else {
         return var1.cleanName == null ? -1 : this.cleanName.compareTo(var1.cleanName);
      }
   }

   public boolean equals(Object var1) {
      if (var1 instanceof Username) {
         Username var2 = (Username)var1;
         if (this.cleanName == null) {
            return var2.cleanName == null;
         } else if (var2.cleanName == null) {
            return false;
         } else {
            return this.hashCode() != var2.hashCode() ? false : this.cleanName.equals(var2.cleanName);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.cleanName == null ? 0 : this.cleanName.hashCode();
   }

   public String toString() {
      return this.getName();
   }

   public int compareTo(Object var1) {
      return this.compareToTyped((Username)var1);
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "(CI)C",
      garbageValue = "717003887"
   )
   static char method5877(char var0) {
      return var0 != 181 && var0 != 402 ? Character.toTitleCase(var0) : var0;
   }
}
