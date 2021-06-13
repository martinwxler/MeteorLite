package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("mv")
@Implements("FontName")
public class FontName {
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "Lmv;"
   )
   @Export("FontName_plain11")
   public static final FontName FontName_plain11 = new FontName("p11_full");
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "Lmv;"
   )
   @Export("FontName_plain12")
   public static final FontName FontName_plain12 = new FontName("p12_full");
   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "Lmv;"
   )
   @Export("FontName_bold12")
   public static final FontName FontName_bold12 = new FontName("b12_full");
   @ObfuscatedName("y")
   @ObfuscatedSignature(
      descriptor = "Lmv;"
   )
   @Export("FontName_verdana11")
   public static final FontName FontName_verdana11 = new FontName("verdana_11pt_regular");
   @ObfuscatedName("p")
   @ObfuscatedSignature(
      descriptor = "Lmv;"
   )
   @Export("FontName_verdana13")
   public static final FontName FontName_verdana13 = new FontName("verdana_13pt_regular");
   @ObfuscatedName("j")
   @ObfuscatedSignature(
      descriptor = "Lmv;"
   )
   @Export("FontName_verdana15")
   public static final FontName FontName_verdana15 = new FontName("verdana_15pt_regular");
   @ObfuscatedName("eu")
   @ObfuscatedSignature(
      descriptor = "Ljp;"
   )
   @Export("archive18")
   static Archive archive18;
   @ObfuscatedName("r")
   @Export("name")
   String name;

   FontName(String var1) {
      this.name = var1;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "(II)Lee;",
      garbageValue = "743368656"
   )
   public static VarbitComposition method6299(int var0) {
      VarbitComposition var1 = (VarbitComposition)VarbitComposition.VarbitDefinition_cached.get((long)var0);
      if (var1 != null) {
         return var1;
      } else {
         byte[] var2 = VarbitComposition.VarbitDefinition_archive.takeFile(14, var0);
         var1 = new VarbitComposition();
         if (var2 != null) {
            var1.decode(new Buffer(var2));
         }

         VarbitComposition.VarbitDefinition_cached.put(var1, (long)var0);
         return var1;
      }
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      descriptor = "([BIIIIIIILgt;[Lfz;I)V",
      garbageValue = "305716239"
   )
   static final void method6296(byte[] var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, Scene var8, CollisionMap[] var9) {
      Buffer var10 = new Buffer(var0);
      int var11 = -1;

      while(true) {
         int var12 = var10.method6560();
         if (var12 == 0) {
            return;
         }

         var11 += var12;
         int var13 = 0;

         while(true) {
            int var14 = var10.readUShortSmart();
            if (var14 == 0) {
               break;
            }

            var13 += var14 - 1;
            int var15 = var13 & 63;
            int var16 = var13 >> 6 & 63;
            int var17 = var13 >> 12;
            int var18 = var10.readUnsignedByte();
            int var19 = var18 >> 2;
            int var20 = var18 & 3;
            if (var17 == var4 && var16 >= var5 && var16 < var5 + 8 && var15 >= var6 && var15 < var6 + 8) {
               ObjectComposition var21 = class23.getObjectDefinition(var11);
               int var22 = var16 & 7;
               int var23 = var15 & 7;
               int var24 = var21.sizeX;
               int var25 = var21.sizeY;
               int var26;
               if ((var20 & 1) == 1) {
                  var26 = var24;
                  var24 = var25;
                  var25 = var26;
               }

               int var27 = var7 & 3;
               int var28;
               if (var27 == 0) {
                  var28 = var22;
               } else if (var27 == 1) {
                  var28 = var23;
               } else if (var27 == 2) {
                  var28 = 7 - var22 - (var24 - 1);
               } else {
                  var28 = 7 - var23 - (var25 - 1);
               }

               var26 = var28 + var2;
               int var29 = var3 + class179.method3635(var16 & 7, var15 & 7, var7, var21.sizeX, var21.sizeY, var20);
               if (var26 > 0 && var29 > 0 && var26 < 103 && var29 < 103) {
                  int var30 = var1;
                  if ((Tiles.Tiles_renderFlags[1][var26][var29] & 2) == 2) {
                     var30 = var1 - 1;
                  }

                  CollisionMap var31 = null;
                  if (var30 >= 0) {
                     var31 = var9[var30];
                  }

                  ClanChannelMember.method89(var1, var26, var29, var11, var20 + var7 & 3, var19, var8, var31);
               }
            }
         }
      }
   }

   @ObfuscatedName("m")
   @ObfuscatedSignature(
      descriptor = "(ILcf;ZI)I",
      garbageValue = "1272154519"
   )
   static int method6298(int var0, Script var1, boolean var2) {
      Widget var3 = Frames.getWidget(Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize]);
      if (var0 == 2600) {
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var3.scrollX;
         return 1;
      } else if (var0 == 2601) {
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var3.scrollY;
         return 1;
      } else if (var0 == 2602) {
         Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var3.text;
         return 1;
      } else if (var0 == 2603) {
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var3.scrollWidth;
         return 1;
      } else if (var0 == 2604) {
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var3.scrollHeight;
         return 1;
      } else if (var0 == 2605) {
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var3.modelZoom;
         return 1;
      } else if (var0 == 2606) {
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var3.modelAngleX;
         return 1;
      } else if (var0 == 2607) {
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var3.modelAngleZ;
         return 1;
      } else if (var0 == 2608) {
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var3.modelAngleY;
         return 1;
      } else if (var0 == 2609) {
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var3.transparencyTop;
         return 1;
      } else if (var0 == 2610) {
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var3.transparencyBot;
         return 1;
      } else if (var0 == 2611) {
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var3.color;
         return 1;
      } else if (var0 == 2612) {
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var3.color2;
         return 1;
      } else if (var0 == 2613) {
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var3.fillMode.rsOrdinal();
         return 1;
      } else if (var0 == 2614) {
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var3.modelTransparency ? 1 : 0;
         return 1;
      } else if (var0 != 2615 && var0 != 2616) {
         return 2;
      } else {
         ++class44.Interpreter_intStackSize;
         return 1;
      }
   }

   @ObfuscatedName("fp")
   @ObfuscatedSignature(
      descriptor = "(IZZZB)Ljp;",
      garbageValue = "28"
   )
   @Export("newArchive")
   static Archive newArchive(int var0, boolean var1, boolean var2, boolean var3) {
      ArchiveDisk var4 = null;
      if (JagexCache.JagexCache_dat2File != null) {
         var4 = new ArchiveDisk(var0, JagexCache.JagexCache_dat2File, class93.JagexCache_idxFiles[var0], 1000000);
      }

      return new Archive(var4, class43.masterDisk, var0, var1, var2, var3);
   }

   @ObfuscatedName("ln")
   @ObfuscatedSignature(
      descriptor = "(I)Z",
      garbageValue = "-81607341"
   )
   public static boolean method6297() {
      return Client.staffModLevel >= 2;
   }
}
