package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ms")
public class class339 implements class344 {
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "Lmg;"
   )
   public final class362 field3921;

   @ObfuscatedSignature(
      descriptor = "(Lmt;)V"
   )
   class339(class363 var1) {
      this.field3921 = var1;
   }

   @ObfuscatedSignature(
      descriptor = "(Lme;)V"
   )
   public class339(class340 var1) {
      this(new class363(var1));
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(II)I",
      garbageValue = "423690194"
   )
   public int method6014(int var1) {
      return this.field3921.vmethod6362(var1);
   }

   @ObfuscatedName("l")
   @ObfuscatedSignature(
      descriptor = "(Ljv;IIB)Z",
      garbageValue = "47"
   )
   static boolean method6015(AbstractArchive var0, int var1, int var2) {
      byte[] var3 = var0.takeFile(var1, var2);
      if (var3 == null) {
         return false;
      } else {
         ItemLayer.SpriteBuffer_decode(var3);
         return true;
      }
   }

   @ObfuscatedName("ho")
   @ObfuscatedSignature(
      descriptor = "(ZLnb;I)V",
      garbageValue = "-2130717093"
   )
   @Export("loadRegions")
   static final void loadRegions(boolean var0, PacketBuffer var1) {
      Client.isInInstance = var0;
      int var2;
      int var3;
      int var4;
      int var5;
      int var6;
      int var7;
      if (!Client.isInInstance) {
         var2 = var1.method6605();
         var3 = var1.method6603();
         int var8 = var1.readUnsignedShort();
         WorldMapAreaData.xteaKeys = new int[var8][4];

         for(var4 = 0; var4 < var8; ++var4) {
            for(var5 = 0; var5 < 4; ++var5) {
               WorldMapAreaData.xteaKeys[var4][var5] = var1.readInt();
            }
         }

         PlayerComposition.regions = new int[var8];
         class16.regionMapArchiveIds = new int[var8];
         class24.regionLandArchiveIds = new int[var8];
         class18.regionLandArchives = new byte[var8][];
         class82.regionMapArchives = new byte[var8][];
         boolean var9 = false;
         if ((var3 / 8 == 48 || var3 / 8 == 49) && var2 / 8 == 48) {
            var9 = true;
         }

         if (var3 / 8 == 48 && var2 / 8 == 148) {
            var9 = true;
         }

         var8 = 0;

         for(var5 = (var3 - 6) / 8; var5 <= (var3 + 6) / 8; ++var5) {
            for(var6 = (var2 - 6) / 8; var6 <= (var2 + 6) / 8; ++var6) {
               var7 = var6 + (var5 << 8);
               if (!var9 || var6 != 49 && var6 != 149 && var6 != 147 && var5 != 50 && (var5 != 49 || var6 != 47)) {
                  PlayerComposition.regions[var8] = var7;
                  class16.regionMapArchiveIds[var8] = class247.archive5.getGroupId("m" + var5 + "_" + var6);
                  class24.regionLandArchiveIds[var8] = class247.archive5.getGroupId("l" + var5 + "_" + var6);
                  ++var8;
               }
            }
         }

         Canvas.method391(var3, var2, true);
      } else {
         var2 = var1.readUnsignedShort();
         var3 = var1.method6603();
         boolean var15 = var1.method6549() == 1;
         var4 = var1.readUnsignedShort();
         var1.importIndex();

         int var16;
         for(var5 = 0; var5 < 4; ++var5) {
            for(var6 = 0; var6 < 13; ++var6) {
               for(var7 = 0; var7 < 13; ++var7) {
                  var16 = var1.readBits(1);
                  if (var16 == 1) {
                     Client.instanceChunkTemplates[var5][var6][var7] = var1.readBits(26);
                  } else {
                     Client.instanceChunkTemplates[var5][var6][var7] = -1;
                  }
               }
            }
         }

         var1.exportIndex();
         WorldMapAreaData.xteaKeys = new int[var4][4];

         for(var5 = 0; var5 < var4; ++var5) {
            for(var6 = 0; var6 < 4; ++var6) {
               WorldMapAreaData.xteaKeys[var5][var6] = var1.readInt();
            }
         }

         PlayerComposition.regions = new int[var4];
         class16.regionMapArchiveIds = new int[var4];
         class24.regionLandArchiveIds = new int[var4];
         class18.regionLandArchives = new byte[var4][];
         class82.regionMapArchives = new byte[var4][];
         var4 = 0;

         for(var5 = 0; var5 < 4; ++var5) {
            for(var6 = 0; var6 < 13; ++var6) {
               for(var7 = 0; var7 < 13; ++var7) {
                  var16 = Client.instanceChunkTemplates[var5][var6][var7];
                  if (var16 != -1) {
                     int var10 = var16 >> 14 & 1023;
                     int var11 = var16 >> 3 & 2047;
                     int var12 = (var10 / 8 << 8) + var11 / 8;

                     int var13;
                     for(var13 = 0; var13 < var4; ++var13) {
                        if (PlayerComposition.regions[var13] == var12) {
                           var12 = -1;
                           break;
                        }
                     }

                     if (var12 != -1) {
                        PlayerComposition.regions[var4] = var12;
                        var13 = var12 >> 8 & 255;
                        int var14 = var12 & 255;
                        class16.regionMapArchiveIds[var4] = class247.archive5.getGroupId("m" + var13 + "_" + var14);
                        class24.regionLandArchiveIds[var4] = class247.archive5.getGroupId("l" + var13 + "_" + var14);
                        ++var4;
                     }
                  }
               }
            }
         }

         Canvas.method391(var3, var2, !var15);
      }

   }
}
