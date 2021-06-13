package osrs;

import java.awt.Component;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ac")
@Implements("Canvas")
public final class Canvas extends java.awt.Canvas {
   @ObfuscatedName("tf")
   @ObfuscatedGetter(
      intValue = 1846107143
   )
   @Export("foundItemIndex")
   static int foundItemIndex;
   @ObfuscatedName("v")
   @Export("component")
   Component component;

   Canvas(Component var1) {
      this.component = var1;
   }

   public final void paint(Graphics var1) {
      this.component.paint(var1);
   }

   public final void update(Graphics var1) {
      this.component.update(var1);
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(Ljava/lang/String;Ljava/lang/String;II)Ljava/io/File;",
      garbageValue = "1145652403"
   )
   public static File method393(String var0, String var1, int var2) {
      String var3 = var2 == 0 ? "" : "" + var2;
      class390.JagexCache_locationFile = new File(class22.userHomeDirectory, "jagex_cl_" + var0 + "_" + var1 + var3 + ".dat");
      String var4 = null;
      String var5 = null;
      boolean var6 = false;
      Buffer var7;
      File var8;
      int var10;
      if (class390.JagexCache_locationFile.exists()) {
         try {
            AccessFile var9 = new AccessFile(class390.JagexCache_locationFile, "rw", 10000L);

            for(var7 = new Buffer((int)var9.length()); var7.offset < var7.array.length; var7.offset += var10) {
               var10 = var9.read(var7.array, var7.offset, var7.array.length - var7.offset);
               if (var10 == -1) {
                  throw new IOException();
               }
            }

            var7.offset = 0;
            var10 = var7.readUnsignedByte();
            if (var10 < 1 || var10 > 3) {
               throw new IOException("" + var10);
            }

            int var11 = 0;
            if (var10 > 1) {
               var11 = var7.readUnsignedByte();
            }

            if (var10 <= 2) {
               var4 = var7.readStringCp1252NullCircumfixed();
               if (var11 == 1) {
                  var5 = var7.readStringCp1252NullCircumfixed();
               }
            } else {
               var4 = var7.readCESU8();
               if (var11 == 1) {
                  var5 = var7.readCESU8();
               }
            }

            var9.close();
         } catch (IOException var18) {
            var18.printStackTrace();
         }

         if (var4 != null) {
            var8 = new File(var4);
            if (!var8.exists()) {
               var4 = null;
            }
         }

         if (var4 != null) {
            var8 = new File(var4, "test.dat");
            if (!FaceNormal.isWriteable(var8, true)) {
               var4 = null;
            }
         }
      }

      if (var4 == null && var2 == 0) {
         label118:
         for(int var19 = 0; var19 < PlayerType.cacheSubPaths.length; ++var19) {
            for(var10 = 0; var10 < InterfaceParent.cacheParentPaths.length; ++var10) {
               File var23 = new File(InterfaceParent.cacheParentPaths[var10] + PlayerType.cacheSubPaths[var19] + File.separatorChar + var0 + File.separatorChar);
               if (var23.exists() && FaceNormal.isWriteable(new File(var23, "test.dat"), true)) {
                  var4 = var23.toString();
                  var6 = true;
                  break label118;
               }
            }
         }
      }

      if (var4 == null) {
         var4 = class22.userHomeDirectory + File.separatorChar + "jagexcache" + var3 + File.separatorChar + var0 + File.separatorChar + var1 + File.separatorChar;
         var6 = true;
      }

      File var20;
      if (var5 != null) {
         var20 = new File(var5);
         var8 = new File(var4);

         try {
            File[] var21 = var20.listFiles();
            File[] var24 = var21;

            for(int var12 = 0; var12 < var24.length; ++var12) {
               File var13 = var24[var12];
               File var14 = new File(var8, var13.getName());
               boolean var15 = var13.renameTo(var14);
               if (!var15) {
                  throw new IOException();
               }
            }
         } catch (Exception var17) {
            var17.printStackTrace();
         }

         var6 = true;
      }

      if (var6) {
         var20 = new File(var4);
         var7 = null;

         try {
            AccessFile var22 = new AccessFile(class390.JagexCache_locationFile, "rw", 10000L);
            Buffer var25 = new Buffer(500);
            var25.writeByte(3);
            var25.writeByte(var7 != null ? 1 : 0);
            var25.writeCESU8(var20.getPath());
            if (var7 != null) {
               var25.writeCESU8("");
            }

            var22.write(var25.array, 0, var25.offset);
            var22.close();
         } catch (IOException var16) {
            var16.printStackTrace();
         }
      }

      return new File(var4);
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      descriptor = "(ILjv;IIIZI)V",
      garbageValue = "1003938789"
   )
   public static void method395(int var0, AbstractArchive var1, int var2, int var3, int var4, boolean var5) {
      class232.musicPlayerStatus = 1;
      ModelData0.musicTrackArchive = var1;
      class32.musicTrackGroupId = var2;
      class18.musicTrackFileId = var3;
      class232.musicTrackVolume = var4;
      class232.musicTrackBoolean = var5;
      class232.pcmSampleLength = var0;
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      descriptor = "(II)Z",
      garbageValue = "1869214638"
   )
   public static boolean method394(int var0) {
      return (var0 >> 29 & 1) != 0;
   }

   @ObfuscatedName("hj")
   @ObfuscatedSignature(
      descriptor = "(IIZI)V",
      garbageValue = "-786826616"
   )
   static final void method391(int var0, int var1, boolean var2) {
      if (!var2 || var0 != GrandExchangeOfferOwnWorldComparator.field632 || UserComparator4.field1427 != var1) {
         GrandExchangeOfferOwnWorldComparator.field632 = var0;
         UserComparator4.field1427 = var1;
         class12.updateGameState(25);
         DirectByteArrayCopier.drawLoadingMessage("Loading - please wait.", true);
         int var3 = VertexNormal.baseX;
         int var4 = SoundSystem.baseY;
         VertexNormal.baseX = (var0 - 6) * 8;
         SoundSystem.baseY = (var1 - 6) * 8;
         int var5 = VertexNormal.baseX - var3;
         int var6 = SoundSystem.baseY - var4;
         var3 = VertexNormal.baseX;
         var4 = SoundSystem.baseY;

         int var7;
         int var8;
         int[] var9;
         for(var7 = 0; var7 < 32768; ++var7) {
            NPC var10 = Client.npcs[var7];
            if (var10 != null) {
               for(var8 = 0; var8 < 10; ++var8) {
                  var9 = var10.pathX;
                  var9[var8] -= var5;
                  var9 = var10.pathY;
                  var9[var8] -= var6;
               }

               var10.x -= var5 * 128;
               var10.y -= var6 * 128;
            }
         }

         for(var7 = 0; var7 < 2048; ++var7) {
            Player var21 = Client.players[var7];
            if (var21 != null) {
               for(var8 = 0; var8 < 10; ++var8) {
                  var9 = var21.pathX;
                  var9[var8] -= var5;
                  var9 = var21.pathY;
                  var9[var8] -= var6;
               }

               var21.x -= var5 * 128;
               var21.y -= var6 * 128;
            }
         }

         byte var22 = 0;
         byte var11 = 104;
         byte var12 = 1;
         if (var5 < 0) {
            var22 = 103;
            var11 = -1;
            var12 = -1;
         }

         byte var13 = 0;
         byte var14 = 104;
         byte var15 = 1;
         if (var6 < 0) {
            var13 = 103;
            var14 = -1;
            var15 = -1;
         }

         int var16;
         for(int var17 = var22; var11 != var17; var17 += var12) {
            for(var16 = var13; var16 != var14; var16 += var15) {
               int var18 = var17 + var5;
               int var19 = var6 + var16;

               for(int var20 = 0; var20 < 4; ++var20) {
                  if (var18 >= 0 && var19 >= 0 && var18 < 104 && var19 < 104) {
                     Client.groundItems[var20][var17][var16] = Client.groundItems[var20][var18][var19];
                  } else {
                     Client.groundItems[var20][var17][var16] = null;
                  }
               }
            }
         }

         for(PendingSpawn var23 = (PendingSpawn)Client.pendingSpawns.last(); var23 != null; var23 = (PendingSpawn)Client.pendingSpawns.previous()) {
            var23.x -= var5;
            var23.y -= var6;
            if (var23.x < 0 || var23.y < 0 || var23.x >= 104 || var23.y >= 104) {
               var23.remove();
            }
         }

         if (Client.destinationX != 0) {
            Client.destinationX -= var5;
            Client.destinationY -= var6;
         }

         Client.soundEffectCount = 0;
         Client.isCameraLocked = false;
         MouseHandler.cameraX -= var5 << 7;
         class105.cameraZ -= var6 << 7;
         RouteStrategy.oculusOrbFocalPointX -= var5 << 7;
         ModelData0.oculusOrbFocalPointY -= var6 << 7;
         Client.field872 = -1;
         Client.graphicsObjects.clear();
         Client.projectiles.clear();

         for(var16 = 0; var16 < 4; ++var16) {
            Client.collisionMaps[var16].clear();
         }
      }

   }

   @ObfuscatedName("jz")
   @ObfuscatedSignature(
      descriptor = "([Lio;II)V",
      garbageValue = "1454811445"
   )
   @Export("drawModelComponents")
   static final void drawModelComponents(Widget[] var0, int var1) {
      for(int var2 = 0; var2 < var0.length; ++var2) {
         Widget var3 = var0[var2];
         if (var3 != null && var3.parentId == var1 && (!var3.isIf3 || !HitSplatDefinition.isComponentHidden(var3))) {
            int var4;
            if (var3.type == 0) {
               if (!var3.isIf3 && HitSplatDefinition.isComponentHidden(var3) && var3 != World.mousedOverWidgetIf1) {
                  continue;
               }

               drawModelComponents(var0, var3.id);
               if (var3.children != null) {
                  drawModelComponents(var3.children, var3.id);
               }

               InterfaceParent var5 = (InterfaceParent)Client.interfaceParents.get((long)var3.id);
               if (var5 != null) {
                  var4 = var5.group;
                  if (Clock.loadInterface(var4)) {
                     drawModelComponents(Widget.Widget_interfaceComponents[var4], -1);
                  }
               }
            }

            if (var3.type == 6) {
               if (var3.sequenceId != -1 || var3.sequenceId2 != -1) {
                  boolean var7 = InvDefinition.runCs1(var3);
                  if (var7) {
                     var4 = var3.sequenceId2;
                  } else {
                     var4 = var3.sequenceId;
                  }

                  if (var4 != -1) {
                     SequenceDefinition var6 = LoginScreenAnimation.SequenceDefinition_get(var4);

                     for(var3.modelFrameCycle += Client.field913; var3.modelFrameCycle > var6.frameLengths[var3.modelFrame]; WorldMapCacheName.invalidateWidget(var3)) {
                        var3.modelFrameCycle -= var6.frameLengths[var3.modelFrame];
                        ++var3.modelFrame;
                        if (var3.modelFrame >= var6.frameIds.length) {
                           var3.modelFrame -= var6.frameCount;
                           if (var3.modelFrame < 0 || var3.modelFrame >= var6.frameIds.length) {
                              var3.modelFrame = 0;
                           }
                        }
                     }
                  }
               }

               if (var3.field3014 != 0 && !var3.isIf3) {
                  int var8 = var3.field3014 >> 16;
                  var4 = var3.field3014 << 16 >> 16;
                  var8 *= Client.field913;
                  var4 *= Client.field913;
                  var3.modelAngleX = var8 + var3.modelAngleX & 2047;
                  var3.modelAngleY = var4 + var3.modelAngleY & 2047;
                  WorldMapCacheName.invalidateWidget(var3);
               }
            }
         }
      }

   }
}
