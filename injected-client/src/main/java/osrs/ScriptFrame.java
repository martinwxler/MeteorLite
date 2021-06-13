package osrs;

import java.io.File;
import java.io.RandomAccessFile;
import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("bw")
@Implements("ScriptFrame")
public class ScriptFrame {
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "Lcf;"
   )
   @Export("script")
   Script script;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = -364828753
   )
   @Export("pc")
   int pc = -1;
   @ObfuscatedName("f")
   @Export("intLocals")
   int[] intLocals;
   @ObfuscatedName("y")
   @Export("stringLocals")
   String[] stringLocals;

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(Ljava/lang/String;I)Ljava/io/File;",
      garbageValue = "-128953674"
   )
   @Export("getFile")
   public static File getFile(String var0) {
      if (!FileSystem.FileSystem_hasPermissions) {
         throw new RuntimeException("");
      } else {
         File var1 = (File)FileSystem.FileSystem_cacheFiles.get(var0);
         if (var1 != null) {
            return var1;
         } else {
            File var2 = new File(FileSystem.FileSystem_cacheDir, var0);
            RandomAccessFile var3 = null;

            try {
               File var4 = new File(var2.getParent());
               if (!var4.exists()) {
                  throw new RuntimeException("");
               } else {
                  var3 = new RandomAccessFile(var2, "rw");
                  int var5 = var3.read();
                  var3.seek(0L);
                  var3.write(var5);
                  var3.seek(0L);
                  var3.close();
                  FileSystem.FileSystem_cacheFiles.put(var0, var2);
                  return var2;
               }
            } catch (Exception var7) {
               try {
                  if (var3 != null) {
                     var3.close();
                     var3 = null;
                  }
               } catch (Exception var6) {
                  ;
               }

               throw new RuntimeException();
            }
         }
      }
   }

   @ObfuscatedName("gf")
   @ObfuscatedSignature(
      descriptor = "(IIIII)V",
      garbageValue = "1213701936"
   )
   @Export("drawEntities")
   static final void drawEntities(int var0, int var1, int var2, int var3) {
      ++Client.viewportDrawCount;
      if (class93.localPlayer.x >> 7 == Client.destinationX && class93.localPlayer.y >> 7 == Client.destinationY) {
         Client.destinationX = 0;
      }

      Decimator.method1068();
      GameObject.method4279();
      GrandExchangeOfferOwnWorldComparator.addNpcsToScene(true);
      class373.method6474();
      GrandExchangeOfferOwnWorldComparator.addNpcsToScene(false);

      int var4;
      for(Projectile var5 = (Projectile)Client.projectiles.last(); var5 != null; var5 = (Projectile)Client.projectiles.previous()) {
         if (var5.plane == class22.Client_plane && Client.cycle <= var5.cycleEnd) {
            if (Client.cycle >= var5.cycleStart) {
               if (var5.targetIndex > 0) {
                  NPC var6 = Client.npcs[var5.targetIndex - 1];
                  if (var6 != null && var6.x >= 0 && var6.x < 13312 && var6.y >= 0 && var6.y < 13312) {
                     var5.setDestination(var6.x, var6.y, class105.getTileHeight(var6.x, var6.y, var5.plane) - var5.endHeight, Client.cycle);
                  }
               }

               if (var5.targetIndex < 0) {
                  var4 = -var5.targetIndex - 1;
                  Player var20;
                  if (var4 == Client.localPlayerIndex) {
                     var20 = class93.localPlayer;
                  } else {
                     var20 = Client.players[var4];
                  }

                  if (var20 != null && var20.x >= 0 && var20.x < 13312 && var20.y >= 0 && var20.y < 13312) {
                     var5.setDestination(var20.x, var20.y, class105.getTileHeight(var20.x, var20.y, var5.plane) - var5.endHeight, Client.cycle);
                  }
               }

               var5.advance(Client.field913);
               AbstractSocket.scene.drawEntity(class22.Client_plane, (int)var5.x, (int)var5.y, (int)var5.z, 60, var5, var5.yaw, -1L, false);
            }
         } else {
            var5.remove();
         }
      }

      for(GraphicsObject var18 = (GraphicsObject)Client.graphicsObjects.last(); var18 != null; var18 = (GraphicsObject)Client.graphicsObjects.previous()) {
         if (var18.plane == class22.Client_plane && !var18.isFinished) {
            if (Client.cycle >= var18.cycleStart) {
               var18.advance(Client.field913);
               if (var18.isFinished) {
                  var18.remove();
               } else {
                  AbstractSocket.scene.drawEntity(var18.plane, var18.x, var18.y, var18.height, 60, var18, 0, -1L, false);
               }
            }
         } else {
            var18.remove();
         }
      }

      UrlRequester.setViewportShape(var0, var1, var2, var3, true);
      var0 = Client.viewportOffsetX;
      var1 = Client.viewportOffsetY;
      var2 = Client.viewportWidth;
      var3 = Client.viewportHeight;
      Rasterizer2D.Rasterizer2D_setClip(var0, var1, var0 + var2, var3 + var1);
      Rasterizer3D.Rasterizer3D_setClipFromRasterizer2D();
      int var7;
      int var8;
      int var9;
      int var10;
      int var11;
      int var12;
      int var13;
      int var14;
      int var19;
      int var21;
      if (!Client.isCameraLocked) {
         var14 = Client.camAngleX;
         if (Client.field727 / 256 > var14) {
            var14 = Client.field727 / 256;
         }

         if (Client.field693[4] && Client.field891[4] + 128 > var14) {
            var14 = Client.field891[4] + 128;
         }

         var19 = Client.camAngleY & 2047;
         var4 = RouteStrategy.oculusOrbFocalPointX;
         var21 = class17.field148;
         var7 = ModelData0.oculusOrbFocalPointY;
         var8 = SecureRandomFuture.method1980(var14);
         var8 = WorldMapDecoration.method3627(var8, var3);
         var9 = 2048 - var14 & 2047;
         var10 = 2048 - var19 & 2047;
         var11 = 0;
         var12 = 0;
         var13 = var8;
         int var15;
         int var16;
         int var17;
         if (var9 != 0) {
            var15 = Rasterizer3D.Rasterizer3D_sine[var9];
            var16 = Rasterizer3D.Rasterizer3D_cosine[var9];
            var17 = var12 * var16 - var15 * var8 >> 16;
            var13 = var15 * var12 + var8 * var16 >> 16;
            var12 = var17;
         }

         if (var10 != 0) {
            var15 = Rasterizer3D.Rasterizer3D_sine[var10];
            var16 = Rasterizer3D.Rasterizer3D_cosine[var10];
            var17 = var11 * var16 + var13 * var15 >> 16;
            var13 = var13 * var16 - var11 * var15 >> 16;
            var11 = var17;
         }

         MouseHandler.cameraX = var4 - var11;
         SecureRandomCallable.cameraY = var21 - var12;
         class105.cameraZ = var7 - var13;
         SpotAnimationDefinition.cameraPitch = var14;
         class376.cameraYaw = var19;
         if (Client.oculusOrbState == 1 && Client.staffModLevel >= 2 && Client.cycle % 50 == 0 && (RouteStrategy.oculusOrbFocalPointX >> 7 != class93.localPlayer.x >> 7 || ModelData0.oculusOrbFocalPointY >> 7 != class93.localPlayer.y >> 7)) {
            var15 = class93.localPlayer.plane;
            var16 = (RouteStrategy.oculusOrbFocalPointX >> 7) + VertexNormal.baseX;
            var17 = (ModelData0.oculusOrbFocalPointY >> 7) + SoundSystem.baseY;
            class4.method50(var16, var17, var15, true);
         }
      }

      if (!Client.isCameraLocked) {
         if (ObjectComposition.clientPreferences.roofsHidden) {
            var19 = class22.Client_plane;
         } else {
            label348: {
               var4 = 3;
               if (SpotAnimationDefinition.cameraPitch < 310) {
                  if (Client.oculusOrbState == 1) {
                     var21 = RouteStrategy.oculusOrbFocalPointX >> 7;
                     var7 = ModelData0.oculusOrbFocalPointY >> 7;
                  } else {
                     var21 = class93.localPlayer.x >> 7;
                     var7 = class93.localPlayer.y >> 7;
                  }

                  var8 = MouseHandler.cameraX >> 7;
                  var9 = class105.cameraZ >> 7;
                  if (var8 < 0 || var9 < 0 || var8 >= 104 || var9 >= 104) {
                     var19 = class22.Client_plane;
                     break label348;
                  }

                  if (var21 < 0 || var7 < 0 || var21 >= 104 || var7 >= 104) {
                     var19 = class22.Client_plane;
                     break label348;
                  }

                  if ((Tiles.Tiles_renderFlags[class22.Client_plane][var8][var9] & 4) != 0) {
                     var4 = class22.Client_plane;
                  }

                  if (var21 > var8) {
                     var10 = var21 - var8;
                  } else {
                     var10 = var8 - var21;
                  }

                  if (var7 > var9) {
                     var11 = var7 - var9;
                  } else {
                     var11 = var9 - var7;
                  }

                  if (var10 > var11) {
                     var12 = var11 * 65536 / var10;
                     var13 = 32768;

                     while(var21 != var8) {
                        if (var8 < var21) {
                           ++var8;
                        } else if (var8 > var21) {
                           --var8;
                        }

                        if ((Tiles.Tiles_renderFlags[class22.Client_plane][var8][var9] & 4) != 0) {
                           var4 = class22.Client_plane;
                        }

                        var13 += var12;
                        if (var13 >= 65536) {
                           var13 -= 65536;
                           if (var9 < var7) {
                              ++var9;
                           } else if (var9 > var7) {
                              --var9;
                           }

                           if ((Tiles.Tiles_renderFlags[class22.Client_plane][var8][var9] & 4) != 0) {
                              var4 = class22.Client_plane;
                           }
                        }
                     }
                  } else if (var11 > 0) {
                     var12 = var10 * 65536 / var11;
                     var13 = 32768;

                     while(var9 != var7) {
                        if (var9 < var7) {
                           ++var9;
                        } else if (var9 > var7) {
                           --var9;
                        }

                        if ((Tiles.Tiles_renderFlags[class22.Client_plane][var8][var9] & 4) != 0) {
                           var4 = class22.Client_plane;
                        }

                        var13 += var12;
                        if (var13 >= 65536) {
                           var13 -= 65536;
                           if (var8 < var21) {
                              ++var8;
                           } else if (var8 > var21) {
                              --var8;
                           }

                           if ((Tiles.Tiles_renderFlags[class22.Client_plane][var8][var9] & 4) != 0) {
                              var4 = class22.Client_plane;
                           }
                        }
                     }
                  }
               }

               if (class93.localPlayer.x >= 0 && class93.localPlayer.y >= 0 && class93.localPlayer.x < 13312 && class93.localPlayer.y < 13312) {
                  if ((Tiles.Tiles_renderFlags[class22.Client_plane][class93.localPlayer.x >> 7][class93.localPlayer.y >> 7] & 4) != 0) {
                     var4 = class22.Client_plane;
                  }

                  var19 = var4;
               } else {
                  var19 = class22.Client_plane;
               }
            }
         }

         var14 = var19;
      } else {
         var14 = class32.method305();
      }

      var19 = MouseHandler.cameraX;
      var4 = SecureRandomCallable.cameraY;
      var21 = class105.cameraZ;
      var7 = SpotAnimationDefinition.cameraPitch;
      var8 = class376.cameraYaw;

      for(var9 = 0; var9 < 5; ++var9) {
         if (Client.field693[var9]) {
            var10 = (int)(Math.random() * (double)(Client.field890[var9] * 2 + 1) - (double)Client.field890[var9] + Math.sin((double)Client.field893[var9] * ((double)Client.field892[var9] / 100.0D)) * (double)Client.field891[var9]);
            if (var9 == 0) {
               MouseHandler.cameraX += var10;
            }

            if (var9 == 1) {
               SecureRandomCallable.cameraY += var10;
            }

            if (var9 == 2) {
               class105.cameraZ += var10;
            }

            if (var9 == 3) {
               class376.cameraYaw = var10 + class376.cameraYaw & 2047;
            }

            if (var9 == 4) {
               SpotAnimationDefinition.cameraPitch += var10;
               if (SpotAnimationDefinition.cameraPitch < 128) {
                  SpotAnimationDefinition.cameraPitch = 128;
               }

               if (SpotAnimationDefinition.cameraPitch > 383) {
                  SpotAnimationDefinition.cameraPitch = 383;
               }
            }
         }
      }

      var9 = MouseHandler.MouseHandler_x;
      var10 = MouseHandler.MouseHandler_y;
      if (MouseHandler.MouseHandler_lastButton != 0) {
         var9 = MouseHandler.MouseHandler_lastPressedX;
         var10 = MouseHandler.MouseHandler_lastPressedY;
      }

      if (var9 >= var0 && var9 < var0 + var2 && var10 >= var1 && var10 < var3 + var1) {
         class135.method2599(var9 - var0, var10 - var1);
      } else {
         class80.method1906();
      }

      HealthBarUpdate.playPcmPlayers();
      Rasterizer2D.Rasterizer2D_fillRectangle(var0, var1, var2, var3, 0);
      HealthBarUpdate.playPcmPlayers();
      var11 = Rasterizer3D.Rasterizer3D_zoom;
      Rasterizer3D.Rasterizer3D_zoom = Client.viewportZoom;
      AbstractSocket.scene.draw(MouseHandler.cameraX, SecureRandomCallable.cameraY, class105.cameraZ, SpotAnimationDefinition.cameraPitch, class376.cameraYaw, var14);
      Rasterizer3D.Rasterizer3D_zoom = var11;
      HealthBarUpdate.playPcmPlayers();
      AbstractSocket.scene.clearTempGameObjects();
      NetCache.method5025(var0, var1, var2, var3);
      ChatChannel.method2014(var0, var1);
      ((TextureProvider)Rasterizer3D.Rasterizer3D_textureLoader).animate(Client.field913);
      class27.method283(var0, var1, var2, var3);
      MouseHandler.cameraX = var19;
      SecureRandomCallable.cameraY = var4;
      class105.cameraZ = var21;
      SpotAnimationDefinition.cameraPitch = var7;
      class376.cameraYaw = var8;
      if (Client.isLoading) {
         byte var22 = 0;
         var13 = var22 + NetCache.NetCache_pendingPriorityWritesCount + NetCache.NetCache_pendingPriorityResponsesCount;
         if (var13 == 0) {
            Client.isLoading = false;
         }
      }

      if (Client.isLoading) {
         Rasterizer2D.Rasterizer2D_fillRectangle(var0, var1, var2, var3, 0);
         DirectByteArrayCopier.drawLoadingMessage("Loading - please wait.", false);
      }

   }
}
