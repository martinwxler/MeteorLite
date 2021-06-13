package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ii")
@Implements("Coord")
public class Coord {
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -1820038389
   )
   @Export("plane")
   public int plane;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = -2092998769
   )
   @Export("x")
   public int x;
   @ObfuscatedName("f")
   @ObfuscatedGetter(
      intValue = 349527629
   )
   @Export("y")
   public int y;

   @ObfuscatedSignature(
      descriptor = "(Lii;)V"
   )
   public Coord(Coord var1) {
      this.plane = var1.plane;
      this.x = var1.x;
      this.y = var1.y;
   }

   public Coord(int var1, int var2, int var3) {
      this.plane = var1;
      this.x = var2;
      this.y = var3;
   }

   public Coord(int var1) {
      if (var1 == -1) {
         this.plane = -1;
      } else {
         this.plane = var1 >> 28 & 3;
         this.x = var1 >> 14 & 16383;
         this.y = var1 & 16383;
      }

   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(I)I",
      garbageValue = "-738811910"
   )
   @Export("packed")
   public int packed() {
      return this.plane << 28 | this.x << 14 | this.y;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "(Lii;B)Z",
      garbageValue = "-43"
   )
   @Export("equalsCoord")
   boolean equalsCoord(Coord var1) {
      if (this.plane != var1.plane) {
         return false;
      } else if (this.x != var1.x) {
         return false;
      } else {
         return this.y == var1.y;
      }
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "(Ljava/lang/String;I)Ljava/lang/String;",
      garbageValue = "36531623"
   )
   @Export("toString")
   String toString(String var1) {
      return this.plane + var1 + (this.x >> 6) + var1 + (this.y >> 6) + var1 + (this.x & 63) + var1 + (this.y & 63);
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else {
         return !(var1 instanceof Coord) ? false : this.equalsCoord((Coord)var1);
      }
   }

   public int hashCode() {
      return this.packed();
   }

   public String toString() {
      return this.toString(",");
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(Lnb;B)V",
      garbageValue = "-128"
   )
   @Export("updatePlayer")
   static final void updatePlayer(PacketBuffer var0) {
      var0.importIndex();
      int var1 = Client.localPlayerIndex;
      Player var2 = class93.localPlayer = Client.players[var1] = new Player();
      var2.index = var1;
      int var3 = var0.readBits(30);
      byte var4 = (byte)(var3 >> 28);
      int var5 = var3 >> 14 & 16383;
      int var6 = var3 & 16383;
      var2.pathX[0] = var5 - VertexNormal.baseX;
      var2.x = (var2.pathX[0] << 7) + (var2.transformedSize() << 6);
      var2.pathY[0] = var6 - SoundSystem.baseY;
      var2.y = (var2.pathY[0] << 7) + (var2.transformedSize() << 6);
      class22.Client_plane = var2.plane = var4;
      if (Players.field1374[var1] != null) {
         var2.read(Players.field1374[var1]);
      }

      Players.Players_count = 0;
      Players.Players_indices[++Players.Players_count - 1] = var1;
      Players.field1370[var1] = 0;
      Players.Players_emptyIdxCount = 0;

      for(int var7 = 1; var7 < 2048; ++var7) {
         if (var1 != var7) {
            int var8 = var0.readBits(18);
            int var9 = var8 >> 16;
            int var10 = var8 >> 8 & 597;
            int var11 = var8 & 597;
            Players.Players_regions[var7] = (var10 << 14) + var11 + (var9 << 28);
            Players.Players_orientations[var7] = 0;
            Players.Players_targetIndices[var7] = -1;
            Players.Players_emptyIndices[++Players.Players_emptyIdxCount - 1] = var7;
            Players.field1370[var7] = 0;
         }
      }

      var0.exportIndex();
   }

   @ObfuscatedName("hm")
   @ObfuscatedSignature(
      descriptor = "(IIIIII)V",
      garbageValue = "1649813812"
   )
   @Export("drawObject")
   static final void drawObject(int var0, int var1, int var2, int var3, int var4) {
      long var5 = AbstractSocket.scene.getBoundaryObjectTag(var0, var1, var2);
      int var7;
      int var8;
      int var9;
      int var10;
      int var11;
      int var12;
      int var17;
      if (var5 != 0L) {
         var7 = AbstractSocket.scene.getObjectFlags(var0, var1, var2, var5);
         var8 = var7 >> 6 & 3;
         var9 = var7 & 31;
         var10 = var3;
         boolean var13 = 0L != var5;
         if (var13) {
            boolean var14 = (int)(var5 >>> 16 & 1L) == 1;
            var13 = !var14;
         }

         if (var13) {
            var10 = var4;
         }

         int[] var20 = GameEngine.sceneMinimapSprite.pixels;
         var12 = var1 * 4 + (103 - var2) * 2048 + 24624;
         var11 = class93.Entity_unpackID(var5);
         ObjectComposition var15 = class23.getObjectDefinition(var11);
         if (var15.mapSceneId != -1) {
            IndexedSprite var16 = class10.mapSceneSprites[var15.mapSceneId];
            if (var16 != null) {
               var17 = (var15.sizeX * 4 - var16.subWidth) / 2;
               int var18 = (var15.sizeY * 4 - var16.subHeight) / 2;
               var16.drawAt(var1 * 4 + var17 + 48, var18 + (104 - var2 - var15.sizeY) * 4 + 48);
            }
         } else {
            if (var9 == 0 || var9 == 2) {
               if (var8 == 0) {
                  var20[var12] = var10;
                  var20[var12 + 512] = var10;
                  var20[var12 + 1024] = var10;
                  var20[var12 + 1536] = var10;
               } else if (var8 == 1) {
                  var20[var12] = var10;
                  var20[var12 + 1] = var10;
                  var20[var12 + 2] = var10;
                  var20[var12 + 3] = var10;
               } else if (var8 == 2) {
                  var20[var12 + 3] = var10;
                  var20[var12 + 512 + 3] = var10;
                  var20[var12 + 1024 + 3] = var10;
                  var20[var12 + 1536 + 3] = var10;
               } else if (var8 == 3) {
                  var20[var12 + 1536] = var10;
                  var20[var12 + 1536 + 1] = var10;
                  var20[var12 + 1536 + 2] = var10;
                  var20[var12 + 1536 + 3] = var10;
               }
            }

            if (var9 == 3) {
               if (var8 == 0) {
                  var20[var12] = var10;
               } else if (var8 == 1) {
                  var20[var12 + 3] = var10;
               } else if (var8 == 2) {
                  var20[var12 + 1536 + 3] = var10;
               } else if (var8 == 3) {
                  var20[var12 + 1536] = var10;
               }
            }

            if (var9 == 2) {
               if (var8 == 3) {
                  var20[var12] = var10;
                  var20[var12 + 512] = var10;
                  var20[var12 + 1024] = var10;
                  var20[var12 + 1536] = var10;
               } else if (var8 == 0) {
                  var20[var12] = var10;
                  var20[var12 + 1] = var10;
                  var20[var12 + 2] = var10;
                  var20[var12 + 3] = var10;
               } else if (var8 == 1) {
                  var20[var12 + 3] = var10;
                  var20[var12 + 512 + 3] = var10;
                  var20[var12 + 1024 + 3] = var10;
                  var20[var12 + 1536 + 3] = var10;
               } else if (var8 == 2) {
                  var20[var12 + 1536] = var10;
                  var20[var12 + 1536 + 1] = var10;
                  var20[var12 + 1536 + 2] = var10;
                  var20[var12 + 1536 + 3] = var10;
               }
            }
         }
      }

      var5 = AbstractSocket.scene.getGameObjectTag(var0, var1, var2);
      ObjectComposition var19;
      IndexedSprite var21;
      if (0L != var5) {
         var7 = AbstractSocket.scene.getObjectFlags(var0, var1, var2, var5);
         var8 = var7 >> 6 & 3;
         var9 = var7 & 31;
         var10 = class93.Entity_unpackID(var5);
         var19 = class23.getObjectDefinition(var10);
         if (var19.mapSceneId != -1) {
            var21 = class10.mapSceneSprites[var19.mapSceneId];
            if (var21 != null) {
               var12 = (var19.sizeX * 4 - var21.subWidth) / 2;
               var11 = (var19.sizeY * 4 - var21.subHeight) / 2;
               var21.drawAt(var1 * 4 + var12 + 48, var11 + (104 - var2 - var19.sizeY) * 4 + 48);
            }
         } else if (var9 == 9) {
            int var22 = 15658734;
            boolean var23 = var5 != 0L;
            if (var23) {
               boolean var24 = (int)(var5 >>> 16 & 1L) == 1;
               var23 = !var24;
            }

            if (var23) {
               var22 = 15597568;
            }

            int[] var26 = GameEngine.sceneMinimapSprite.pixels;
            var17 = var1 * 4 + (103 - var2) * 2048 + 24624;
            if (var8 != 0 && var8 != 2) {
               var26[var17] = var22;
               var26[var17 + 1 + 512] = var22;
               var26[var17 + 1024 + 2] = var22;
               var26[var17 + 1536 + 3] = var22;
            } else {
               var26[var17 + 1536] = var22;
               var26[var17 + 1 + 1024] = var22;
               var26[var17 + 512 + 2] = var22;
               var26[var17 + 3] = var22;
            }
         }
      }

      var5 = AbstractSocket.scene.getFloorDecorationTag(var0, var1, var2);
      if (var5 != 0L) {
         var7 = class93.Entity_unpackID(var5);
         var19 = class23.getObjectDefinition(var7);
         if (var19.mapSceneId != -1) {
            var21 = class10.mapSceneSprites[var19.mapSceneId];
            if (var21 != null) {
               var10 = (var19.sizeX * 4 - var21.subWidth) / 2;
               int var25 = (var19.sizeY * 4 - var21.subHeight) / 2;
               var21.drawAt(var10 + var1 * 4 + 48, (104 - var2 - var19.sizeY) * 4 + var25 + 48);
            }
         }
      }

   }
}
