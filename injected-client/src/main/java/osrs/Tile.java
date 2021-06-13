package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("gx")
@Implements("Tile")
public final class Tile extends Node {
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = 952681323
   )
   @Export("plane")
   int plane;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = -509167035
   )
   @Export("x")
   int x;
   @ObfuscatedName("f")
   @ObfuscatedGetter(
      intValue = 950473561
   )
   @Export("y")
   int y;
   @ObfuscatedName("y")
   @ObfuscatedGetter(
      intValue = 1257822241
   )
   @Export("originalPlane")
   int originalPlane;
   @ObfuscatedName("p")
   @ObfuscatedSignature(
      descriptor = "Lga;"
   )
   @Export("paint")
   SceneTilePaint paint;
   @ObfuscatedName("j")
   @ObfuscatedSignature(
      descriptor = "Lgp;"
   )
   @Export("model")
   SceneTileModel model;
   @ObfuscatedName("r")
   @ObfuscatedSignature(
      descriptor = "Lhw;"
   )
   @Export("boundaryObject")
   BoundaryObject boundaryObject;
   @ObfuscatedName("b")
   @ObfuscatedSignature(
      descriptor = "Lho;"
   )
   @Export("wallDecoration")
   WallDecoration wallDecoration;
   @ObfuscatedName("d")
   @ObfuscatedSignature(
      descriptor = "Lgo;"
   )
   @Export("floorDecoration")
   FloorDecoration floorDecoration;
   @ObfuscatedName("s")
   @ObfuscatedSignature(
      descriptor = "Lgv;"
   )
   @Export("itemLayer")
   ItemLayer itemLayer;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = 1720897143
   )
   @Export("gameObjectsCount")
   int gameObjectsCount;
   @ObfuscatedName("l")
   @ObfuscatedSignature(
      descriptor = "[Lhj;"
   )
   @Export("gameObjects")
   GameObject[] gameObjects = new GameObject[5];
   @ObfuscatedName("o")
   @Export("gameObjectEdgeMasks")
   int[] gameObjectEdgeMasks = new int[5];
   @ObfuscatedName("c")
   @ObfuscatedGetter(
      intValue = 1099123981
   )
   @Export("gameObjectsEdgeMask")
   int gameObjectsEdgeMask = 0;
   @ObfuscatedName("e")
   @ObfuscatedGetter(
      intValue = 961427167
   )
   @Export("minPlane")
   int minPlane;
   @ObfuscatedName("g")
   @Export("drawPrimary")
   boolean drawPrimary;
   @ObfuscatedName("a")
   @Export("drawSecondary")
   boolean drawSecondary;
   @ObfuscatedName("k")
   @Export("drawGameObjects")
   boolean drawGameObjects;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = 1695083523
   )
   @Export("drawGameObjectEdges")
   int drawGameObjectEdges;
   @ObfuscatedName("x")
   @ObfuscatedGetter(
      intValue = 1060231869
   )
   int field2254;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = -726038297
   )
   int field2244;
   @ObfuscatedName("w")
   @ObfuscatedGetter(
      intValue = 1501732115
   )
   int field2235;
   @ObfuscatedName("t")
   @ObfuscatedSignature(
      descriptor = "Lgx;"
   )
   @Export("linkedBelowTile")
   Tile linkedBelowTile;

   Tile(int var1, int var2, int var3) {
      this.originalPlane = this.plane = var1;
      this.x = var2;
      this.y = var3;
   }

   @ObfuscatedName("hn")
   @ObfuscatedSignature(
      descriptor = "(Lha;I)V",
      garbageValue = "236848204"
   )
   static final void method3843(class225 var0) {
      PacketBuffer var1 = Client.packetWriter.packetBuffer;
      int var2;
      int var3;
      int var4;
      int var5;
      int var6;
      int var7;
      int var8;
      int var9;
      int var10;
      if (class225.field2683 == var0) {
         var2 = var1.method6595();
         var3 = (var2 >> 4 & 7) + Occluder.field2387;
         var4 = (var2 & 7) + class69.field596;
         var5 = var1.method6595();
         var6 = var1.readUnsignedByte();
         var7 = var6 >> 4 & 15;
         var8 = var6 & 7;
         var9 = var1.method6603();
         if (var3 >= 0 && var4 >= 0 && var3 < 104 && var4 < 104) {
            var10 = var7 + 1;
            if (class93.localPlayer.pathX[0] >= var3 - var10 && class93.localPlayer.pathX[0] <= var3 + var10 && class93.localPlayer.pathY[0] >= var4 - var10 && class93.localPlayer.pathY[0] <= var10 + var4 && ObjectComposition.clientPreferences.areaSoundEffectsVolume != 0 && var8 > 0 && Client.soundEffectCount < 50) {
               Client.soundEffectIds[Client.soundEffectCount] = var9;
               Client.queuedSoundEffectLoops[Client.soundEffectCount] = var8;
               Client.queuedSoundEffectDelays[Client.soundEffectCount] = var5;
               Client.soundEffects[Client.soundEffectCount] = null;
               Client.soundLocations[Client.soundEffectCount] = var7 + (var4 << 8) + (var3 << 16);
               ++Client.soundEffectCount;
            }
         }
      }

      if (class225.field2688 == var0) {
         var2 = var1.method6603();
         var3 = var1.method6549();
         var4 = var3 >> 2;
         var5 = var3 & 3;
         var6 = Client.field702[var4];
         var7 = var1.method6595();
         var8 = (var7 >> 4 & 7) + Occluder.field2387;
         var9 = (var7 & 7) + class69.field596;
         if (var8 >= 0 && var9 >= 0 && var8 < 104 && var9 < 104) {
            DevicePcmPlayerProvider.updatePendingSpawn(class22.Client_plane, var8, var9, var6, var2, var4, var5, 0, -1);
         }
      } else {
         TileItem var12;
         if (class225.field2680 == var0) {
            var2 = var1.method6603();
            var3 = var1.method6604();
            var4 = var1.method6603();
            var5 = var1.readUnsignedByte();
            var6 = (var5 >> 4 & 7) + Occluder.field2387;
            var7 = (var5 & 7) + class69.field596;
            if (var6 >= 0 && var7 >= 0 && var6 < 104 && var7 < 104) {
               NodeDeque var11 = Client.groundItems[class22.Client_plane][var6][var7];
               if (var11 != null) {
                  for(var12 = (TileItem)var11.last(); var12 != null; var12 = (TileItem)var11.previous()) {
                     if ((var4 & 32767) == var12.id && var3 == var12.quantity) {
                        var12.quantity = var2;
                        break;
                     }
                  }

                  ClanSettings.updateItemPile(var6, var7);
               }
            }
         } else {
            int var35;
            if (class225.field2684 == var0) {
               var2 = var1.method6671();
               var3 = (var2 >> 4 & 7) + Occluder.field2387;
               var4 = (var2 & 7) + class69.field596;
               var5 = var1.method6605();
               var6 = var1.method6549();
               var7 = var6 >> 2;
               var8 = var6 & 3;
               var9 = Client.field702[var7];
               if (var3 >= 0 && var4 >= 0 && var3 < 103 && var4 < 103) {
                  if (var9 == 0) {
                     BoundaryObject var36 = AbstractSocket.scene.method4089(class22.Client_plane, var3, var4);
                     if (var36 != null) {
                        var35 = class93.Entity_unpackID(var36.tag);
                        if (var7 == 2) {
                           var36.renderable1 = new DynamicObject(var35, 2, var8 + 4, class22.Client_plane, var3, var4, var5, false, var36.renderable1);
                           var36.renderable2 = new DynamicObject(var35, 2, var8 + 1 & 3, class22.Client_plane, var3, var4, var5, false, var36.renderable2);
                        } else {
                           var36.renderable1 = new DynamicObject(var35, var7, var8, class22.Client_plane, var3, var4, var5, false, var36.renderable1);
                        }
                     }
                  }

                  if (var9 == 1) {
                     WallDecoration var37 = AbstractSocket.scene.method3948(class22.Client_plane, var3, var4);
                     if (var37 != null) {
                        var35 = class93.Entity_unpackID(var37.tag);
                        if (var7 != 4 && var7 != 5) {
                           if (var7 == 6) {
                              var37.renderable1 = new DynamicObject(var35, 4, var8 + 4, class22.Client_plane, var3, var4, var5, false, var37.renderable1);
                           } else if (var7 == 7) {
                              var37.renderable1 = new DynamicObject(var35, 4, (var8 + 2 & 3) + 4, class22.Client_plane, var3, var4, var5, false, var37.renderable1);
                           } else if (var7 == 8) {
                              var37.renderable1 = new DynamicObject(var35, 4, var8 + 4, class22.Client_plane, var3, var4, var5, false, var37.renderable1);
                              var37.renderable2 = new DynamicObject(var35, 4, (var8 + 2 & 3) + 4, class22.Client_plane, var3, var4, var5, false, var37.renderable2);
                           }
                        } else {
                           var37.renderable1 = new DynamicObject(var35, 4, var8, class22.Client_plane, var3, var4, var5, false, var37.renderable1);
                        }
                     }
                  }

                  if (var9 == 2) {
                     GameObject var39 = AbstractSocket.scene.method3926(class22.Client_plane, var3, var4);
                     if (var7 == 11) {
                        var7 = 10;
                     }

                     if (var39 != null) {
                        var39.renderable = new DynamicObject(class93.Entity_unpackID(var39.tag), var7, var8, class22.Client_plane, var3, var4, var5, false, var39.renderable);
                     }
                  }

                  if (var9 == 3) {
                     FloorDecoration var40 = AbstractSocket.scene.getFloorDecoration(class22.Client_plane, var3, var4);
                     if (var40 != null) {
                        var40.renderable = new DynamicObject(class93.Entity_unpackID(var40.tag), 22, var8, class22.Client_plane, var3, var4, var5, false, var40.renderable);
                     }
                  }
               }
            } else if (class225.field2682 == var0) {
               var2 = var1.method6595();
               var3 = var2 >> 2;
               var4 = var2 & 3;
               var5 = Client.field702[var3];
               var6 = var1.method6595();
               var7 = (var6 >> 4 & 7) + Occluder.field2387;
               var8 = (var6 & 7) + class69.field596;
               if (var7 >= 0 && var8 >= 0 && var7 < 104 && var8 < 104) {
                  DevicePcmPlayerProvider.updatePendingSpawn(class22.Client_plane, var7, var8, var5, -1, var3, var4, 0, -1);
               }
            } else if (class225.field2685 == var0) {
               var2 = var1.readUnsignedShort();
               var3 = var1.method6595();
               var4 = (var3 >> 4 & 7) + Occluder.field2387;
               var5 = (var3 & 7) + class69.field596;
               if (var4 >= 0 && var5 >= 0 && var4 < 104 && var5 < 104) {
                  NodeDeque var13 = Client.groundItems[class22.Client_plane][var4][var5];
                  if (var13 != null) {
                     for(var12 = (TileItem)var13.last(); var12 != null; var12 = (TileItem)var13.previous()) {
                        if ((var2 & 32767) == var12.id) {
                           var12.remove();
                           break;
                        }
                     }

                     if (var13.last() == null) {
                        Client.groundItems[class22.Client_plane][var4][var5] = null;
                     }

                     ClanSettings.updateItemPile(var4, var5);
                  }
               }
            } else {
               int var14;
               int var15;
               byte var16;
               byte var17;
               int var38;
               if (class225.field2686 == var0) {
                  var2 = var1.method6726();
                  var3 = var1.method6595() * 4;
                  var16 = var1.method6559();
                  var5 = var1.method6671() * 4;
                  var6 = var1.method6549();
                  var7 = (var6 >> 4 & 7) + Occluder.field2387;
                  var8 = (var6 & 7) + class69.field596;
                  var9 = var1.method6549();
                  var10 = var1.method6604();
                  var17 = var1.method6559();
                  var38 = var1.method6605();
                  var14 = var1.readUnsignedShort();
                  var15 = var1.method6595();
                  var35 = var17 + var7;
                  var4 = var16 + var8;
                  if (var7 >= 0 && var8 >= 0 && var7 < 104 && var8 < 104 && var35 >= 0 && var4 >= 0 && var35 < 104 && var4 < 104 && var14 != 65535) {
                     var7 = var7 * 128 + 64;
                     var8 = var8 * 128 + 64;
                     var35 = var35 * 128 + 64;
                     var4 = var4 * 128 + 64;
                     Projectile var18 = new Projectile(var14, class22.Client_plane, var7, var8, class105.getTileHeight(var7, var8, class22.Client_plane) - var3, var38 + Client.cycle, var10 + Client.cycle, var9, var15, var2, var5);
                     var18.setDestination(var35, var4, class105.getTileHeight(var35, var4, class22.Client_plane) - var5, var38 + Client.cycle);
                     Client.projectiles.addFirst(var18);
                  }
               } else {
                  if (class225.field2681 == var0) {
                     var16 = var1.readByte();
                     var3 = var1.method6604();
                     var4 = var1.method6603();
                     var17 = var1.method6597();
                     var6 = var1.readUnsignedShort();
                     var7 = var1.method6603();
                     byte var42 = var1.method6593();
                     var9 = var1.method6549();
                     var10 = (var9 >> 4 & 7) + Occluder.field2387;
                     var35 = (var9 & 7) + class69.field596;
                     var38 = var1.method6595();
                     var14 = var38 >> 2;
                     var15 = var38 & 3;
                     int var19 = Client.field702[var14];
                     byte var20 = var1.readByte();
                     Player var21;
                     if (var6 == Client.localPlayerIndex) {
                        var21 = class93.localPlayer;
                     } else {
                        var21 = Client.players[var6];
                     }

                     if (var21 != null) {
                        ObjectComposition var22 = class23.getObjectDefinition(var3);
                        int var23;
                        int var24;
                        if (var15 != 1 && var15 != 3) {
                           var23 = var22.sizeX;
                           var24 = var22.sizeY;
                        } else {
                           var23 = var22.sizeY;
                           var24 = var22.sizeX;
                        }

                        int var25 = var10 + (var23 >> 1);
                        int var26 = var10 + (var23 + 1 >> 1);
                        int var27 = var35 + (var24 >> 1);
                        int var28 = var35 + (var24 + 1 >> 1);
                        int[][] var29 = Tiles.Tiles_heights[class22.Client_plane];
                        int var30 = var29[var25][var27] + var29[var26][var27] + var29[var25][var28] + var29[var26][var28] >> 2;
                        int var31 = (var10 << 7) + (var23 << 6);
                        int var32 = (var35 << 7) + (var24 << 6);
                        Model var33 = var22.getModel(var14, var15, var29, var31, var30, var32);
                        if (var33 != null) {
                           DevicePcmPlayerProvider.updatePendingSpawn(class22.Client_plane, var10, var35, var19, -1, 0, 0, var4 + 1, var7 + 1);
                           var21.animationCycleStart = var4 + Client.cycle;
                           var21.animationCycleEnd = var7 + Client.cycle;
                           var21.model0 = var33;
                           var21.field1202 = var23 * 64 + var10 * 128;
                           var21.field1220 = var24 * 64 + var35 * 128;
                           var21.tileHeight2 = var30;
                           byte var34;
                           if (var20 > var17) {
                              var34 = var20;
                              var20 = var17;
                              var17 = var34;
                           }

                           if (var16 > var42) {
                              var34 = var16;
                              var16 = var42;
                              var42 = var34;
                           }

                           var21.minX = var20 + var10;
                           var21.maxX = var10 + var17;
                           var21.minY = var35 + var16;
                           var21.maxY = var35 + var42;
                        }
                     }
                  }

                  if (class225.field2687 == var0) {
                     var2 = var1.method6605();
                     var3 = var1.readUnsignedShort();
                     var4 = var1.readUnsignedByte();
                     var5 = (var4 >> 4 & 7) + Occluder.field2387;
                     var6 = (var4 & 7) + class69.field596;
                     if (var5 >= 0 && var6 >= 0 && var5 < 104 && var6 < 104) {
                        var12 = new TileItem();
                        var12.id = var3;
                        var12.quantity = var2;
                        if (Client.groundItems[class22.Client_plane][var5][var6] == null) {
                           Client.groundItems[class22.Client_plane][var5][var6] = new NodeDeque();
                        }

                        Client.groundItems[class22.Client_plane][var5][var6].addFirst(var12);
                        ClanSettings.updateItemPile(var5, var6);
                     }
                  } else if (class225.field2689 == var0) {
                     var2 = var1.readUnsignedShort();
                     var3 = var1.method6595();
                     var4 = var1.method6595();
                     var5 = (var4 >> 4 & 7) + Occluder.field2387;
                     var6 = (var4 & 7) + class69.field596;
                     var7 = var1.method6605();
                     if (var5 >= 0 && var6 >= 0 && var5 < 104 && var6 < 104) {
                        var5 = var5 * 128 + 64;
                        var6 = var6 * 128 + 64;
                        GraphicsObject var41 = new GraphicsObject(var2, class22.Client_plane, var5, var6, class105.getTileHeight(var5, var6, class22.Client_plane) - var3, var7, Client.cycle);
                        Client.graphicsObjects.addFirst(var41);
                     }
                  }
               }
            }
         }
      }

   }
}
