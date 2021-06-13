package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("fv")
@Implements("WorldMapLabel")
public class WorldMapLabel {
   @ObfuscatedName("v")
   @Export("text")
   String text;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = -1781114869
   )
   @Export("width")
   int width;
   @ObfuscatedName("f")
   @ObfuscatedGetter(
      intValue = -733130897
   )
   @Export("height")
   int height;
   @ObfuscatedName("y")
   @ObfuscatedSignature(
      descriptor = "Lfp;"
   )
   @Export("size")
   WorldMapLabelSize size;

   @ObfuscatedSignature(
      descriptor = "(Ljava/lang/String;IILfp;)V"
   )
   WorldMapLabel(String var1, int var2, int var3, WorldMapLabelSize var4) {
      this.text = var1;
      this.width = var2;
      this.height = var3;
      this.size = var4;
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      descriptor = "([BIIII[Lfz;I)V",
      garbageValue = "1162395561"
   )
   static final void method3632(byte[] var0, int var1, int var2, int var3, int var4, CollisionMap[] var5) {
      int var6;
      int var7;
      for(int var8 = 0; var8 < 4; ++var8) {
         for(var6 = 0; var6 < 64; ++var6) {
            for(var7 = 0; var7 < 64; ++var7) {
               if (var6 + var1 > 0 && var6 + var1 < 103 && var7 + var2 > 0 && var7 + var2 < 103) {
                  int[] var9 = var5[var8].flags[var6 + var1];
                  var9[var7 + var2] &= -16777217;
               }
            }
         }
      }

      Buffer var10 = new Buffer(var0);

      for(var6 = 0; var6 < 4; ++var6) {
         for(var7 = 0; var7 < 64; ++var7) {
            for(int var11 = 0; var11 < 64; ++var11) {
               WorldMapAreaData.loadTerrain(var10, var6, var7 + var1, var11 + var2, var3, var4, 0);
            }
         }
      }

   }
}
