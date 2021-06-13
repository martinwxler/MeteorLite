package osrs;

import java.util.LinkedList;
import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("fm")
@Implements("AbstractWorldMapData")
public abstract class AbstractWorldMapData {
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = -1347091631
   )
   @Export("regionXLow")
   int regionXLow;
   @ObfuscatedName("j")
   @ObfuscatedGetter(
      intValue = -868813013
   )
   @Export("regionYLow")
   int regionYLow;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 1253594293
   )
   @Export("regionX")
   int regionX;
   @ObfuscatedName("b")
   @ObfuscatedGetter(
      intValue = 174092143
   )
   @Export("regionY")
   int regionY;
   @ObfuscatedName("d")
   @ObfuscatedGetter(
      intValue = -1260242687
   )
   @Export("minPlane")
   int minPlane;
   @ObfuscatedName("s")
   @ObfuscatedGetter(
      intValue = -1482547443
   )
   @Export("planes")
   int planes;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = -630921225
   )
   @Export("groupId")
   int groupId = -1;
   @ObfuscatedName("l")
   @ObfuscatedGetter(
      intValue = -373998791
   )
   @Export("fileId")
   int fileId = -1;
   @ObfuscatedName("o")
   @Export("floorUnderlayIds")
   short[][][] floorUnderlayIds;
   @ObfuscatedName("c")
   @Export("floorOverlayIds")
   short[][][] floorOverlayIds;
   @ObfuscatedName("e")
   byte[][][] field2067;
   @ObfuscatedName("g")
   byte[][][] field2065;
   @ObfuscatedName("a")
   @ObfuscatedSignature(
      descriptor = "[[[[Lfh;"
   )
   @Export("decorations")
   WorldMapDecoration[][][][] decorations;
   @ObfuscatedName("k")
   boolean field2068;
   @ObfuscatedName("m")
   boolean field2069;

   AbstractWorldMapData() {
      new LinkedList();
      this.field2068 = false;
      this.field2069 = false;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "(Lnd;I)V",
      garbageValue = "1223315238"
   )
   @Export("readGeography")
   abstract void readGeography(Buffer var1);

   @ObfuscatedName("ae")
   @ObfuscatedSignature(
      descriptor = "(B)Z",
      garbageValue = "25"
   )
   @Export("isFullyLoaded")
   boolean isFullyLoaded() {
      return this.field2068 && this.field2069;
   }

   @ObfuscatedName("ap")
   @ObfuscatedSignature(
      descriptor = "(Ljv;I)V",
      garbageValue = "-1881595992"
   )
   @Export("loadGeography")
   void loadGeography(AbstractArchive var1) {
      if (!this.isFullyLoaded()) {
         byte[] var2 = var1.takeFile(this.groupId, this.fileId);
         if (var2 != null) {
            this.readGeography(new Buffer(var2));
            this.field2068 = true;
            this.field2069 = true;
         }
      }

   }

   @ObfuscatedName("ab")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "244458309"
   )
   @Export("reset")
   void reset() {
      this.floorUnderlayIds = (short[][][])null;
      this.floorOverlayIds = (short[][][])null;
      this.field2067 = (byte[][][])null;
      this.field2065 = (byte[][][])null;
      this.decorations = (WorldMapDecoration[][][][])null;
      this.field2068 = false;
      this.field2069 = false;
   }

   @ObfuscatedName("al")
   @ObfuscatedSignature(
      descriptor = "(IILnd;I)V",
      garbageValue = "-570193983"
   )
   @Export("readTile")
   void readTile(int var1, int var2, Buffer var3) {
      int var4 = var3.readUnsignedByte();
      if (var4 != 0) {
         if ((var4 & 1) != 0) {
            this.method3602(var1, var2, var3, var4);
         } else {
            this.method3622(var1, var2, var3, var4);
         }
      }

   }

   @ObfuscatedName("ad")
   @ObfuscatedSignature(
      descriptor = "(IILnd;II)V",
      garbageValue = "-300212411"
   )
   void method3602(int var1, int var2, Buffer var3, int var4) {
      boolean var5 = (var4 & 2) != 0;
      if (var5) {
         this.floorOverlayIds[0][var1][var2] = (short)var3.readUnsignedByte();
      }

      this.floorUnderlayIds[0][var1][var2] = (short)var3.readUnsignedByte();
   }

   @ObfuscatedName("ai")
   @ObfuscatedSignature(
      descriptor = "(IILnd;II)V",
      garbageValue = "-2123814102"
   )
   void method3622(int var1, int var2, Buffer var3, int var4) {
      int var5 = ((var4 & 24) >> 3) + 1;
      boolean var6 = (var4 & 2) != 0;
      boolean var7 = (var4 & 4) != 0;
      this.floorUnderlayIds[0][var1][var2] = (short)var3.readUnsignedByte();
      int var8;
      int var9;
      int var10;
      if (var6) {
         var8 = var3.readUnsignedByte();

         for(var9 = 0; var9 < var8; ++var9) {
            int var11 = var3.readUnsignedByte();
            if (var11 != 0) {
               this.floorOverlayIds[var9][var1][var2] = (short)var11;
               var10 = var3.readUnsignedByte();
               this.field2067[var9][var1][var2] = (byte)(var10 >> 2);
               this.field2065[var9][var1][var2] = (byte)(var10 & 3);
            }
         }
      }

      if (var7) {
         for(var8 = 0; var8 < var5; ++var8) {
            var9 = var3.readUnsignedByte();
            if (var9 != 0) {
               WorldMapDecoration[] var14 = this.decorations[var8][var1][var2] = new WorldMapDecoration[var9];

               for(var10 = 0; var10 < var9; ++var10) {
                  int var12 = var3.method6583();
                  int var13 = var3.readUnsignedByte();
                  var14[var10] = new WorldMapDecoration(var12, var13 >> 2, var13 & 3);
               }
            }
         }
      }

   }

   @ObfuscatedName("ar")
   @ObfuscatedSignature(
      descriptor = "(I)I",
      garbageValue = "-802989109"
   )
   @Export("getRegionX")
   int getRegionX() {
      return this.regionX;
   }

   @ObfuscatedName("ag")
   @ObfuscatedSignature(
      descriptor = "(B)I",
      garbageValue = "-49"
   )
   @Export("getRegionY")
   int getRegionY() {
      return this.regionY;
   }
}
