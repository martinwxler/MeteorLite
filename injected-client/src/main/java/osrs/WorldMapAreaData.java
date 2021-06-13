package osrs;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("gz")
@Implements("WorldMapAreaData")
public class WorldMapAreaData extends WorldMapArea {
   @ObfuscatedName("gx")
   @Export("xteaKeys")
   static int[][] xteaKeys;
   @ObfuscatedName("o")
   @Export("worldMapData0Set")
   HashSet worldMapData0Set;
   @ObfuscatedName("c")
   @Export("worldMapData1Set")
   HashSet worldMapData1Set;
   @ObfuscatedName("e")
   @Export("iconList")
   List iconList;

   @ObfuscatedName("ba")
   @ObfuscatedSignature(
      descriptor = "(Lnd;Lnd;IZI)V",
      garbageValue = "1129445653"
   )
   @Export("init")
   void init(Buffer var1, Buffer var2, int var3, boolean var4) {
      this.read(var1, var3);
      int var5 = var2.readUnsignedShort();
      this.worldMapData0Set = new HashSet(var5);

      int var6;
      for(var6 = 0; var6 < var5; ++var6) {
         WorldMapData_0 var7 = new WorldMapData_0();

         try {
            var7.init(var2);
         } catch (IllegalStateException var11) {
            continue;
         }

         this.worldMapData0Set.add(var7);
      }

      var6 = var2.readUnsignedShort();
      this.worldMapData1Set = new HashSet(var6);

      for(int var12 = 0; var12 < var6; ++var12) {
         WorldMapData_1 var8 = new WorldMapData_1();

         try {
            var8.init(var2);
         } catch (IllegalStateException var10) {
            continue;
         }

         this.worldMapData1Set.add(var8);
      }

      this.initIconsList(var2, var4);
   }

   @ObfuscatedName("be")
   @ObfuscatedSignature(
      descriptor = "(Lnd;ZI)V",
      garbageValue = "1182006206"
   )
   @Export("initIconsList")
   void initIconsList(Buffer var1, boolean var2) {
      this.iconList = new LinkedList();
      int var3 = var1.readUnsignedShort();

      for(int var4 = 0; var4 < var3; ++var4) {
         int var5 = var1.method6583();
         Coord var6 = new Coord(var1.readInt());
         boolean var7 = var1.readUnsignedByte() == 1;
         if (var2 || !var7) {
            this.iconList.add(new WorldMapIcon_0((Coord)null, var6, var5, (WorldMapLabel)null));
         }
      }

   }

   @ObfuscatedName("j")
   @ObfuscatedSignature(
      descriptor = "(Lnd;IIIIIIB)V",
      garbageValue = "-20"
   )
   @Export("loadTerrain")
   static final void loadTerrain(Buffer var0, int var1, int var2, int var3, int var4, int var5, int var6) {
      int var7;
      if (var2 >= 0 && var2 < 104 && var3 >= 0 && var3 < 104) {
         Tiles.Tiles_renderFlags[var1][var2][var3] = 0;

         while(true) {
            var7 = var0.readUnsignedByte();
            if (var7 == 0) {
               if (var1 == 0) {
                  int[] var12 = Tiles.Tiles_heights[0][var2];
                  int var9 = var2 + var4 + 932731;
                  int var10 = var3 + var5 + 556238;
                  int var11 = Skeleton.method3920(var9 + '넵', var10 + 91923, 4) - 128 + (Skeleton.method3920(10294 + var9, '鎽' + var10, 2) - 128 >> 1) + (Skeleton.method3920(var9, var10, 1) - 128 >> 2);
                  var11 = (int)((double)var11 * 0.3D) + 35;
                  if (var11 < 10) {
                     var11 = 10;
                  } else if (var11 > 60) {
                     var11 = 60;
                  }

                  var12[var3] = -var11 * 8;
               } else {
                  Tiles.Tiles_heights[var1][var2][var3] = Tiles.Tiles_heights[var1 - 1][var2][var3] - 240;
               }
               break;
            }

            if (var7 == 1) {
               int var8 = var0.readUnsignedByte();
               if (var8 == 1) {
                  var8 = 0;
               }

               if (var1 == 0) {
                  Tiles.Tiles_heights[0][var2][var3] = -var8 * 8;
               } else {
                  Tiles.Tiles_heights[var1][var2][var3] = Tiles.Tiles_heights[var1 - 1][var2][var3] - var8 * 8;
               }
               break;
            }

            if (var7 <= 49) {
               class253.field3130[var1][var2][var3] = var0.readByte();
               class20.field182[var1][var2][var3] = (byte)((var7 - 2) / 4);
               DirectByteArrayCopier.field3128[var1][var2][var3] = (byte)(var7 - 2 + var6 & 3);
            } else if (var7 <= 81) {
               Tiles.Tiles_renderFlags[var1][var2][var3] = (byte)(var7 - 49);
            } else {
               Tiles.field1120[var1][var2][var3] = (byte)(var7 - 81);
            }
         }
      } else {
         while(true) {
            var7 = var0.readUnsignedByte();
            if (var7 == 0) {
               break;
            }

            if (var7 == 1) {
               var0.readUnsignedByte();
               break;
            }

            if (var7 <= 49) {
               var0.readUnsignedByte();
            }
         }
      }

   }

   @ObfuscatedName("hf")
   @ObfuscatedSignature(
      descriptor = "(B)V",
      garbageValue = "0"
   )
   static final void method3708() {
      TextureProvider.method4142(false);
      Client.field694 = 0;
      boolean var0 = true;

      int var1;
      for(var1 = 0; var1 < class18.regionLandArchives.length; ++var1) {
         if (class16.regionMapArchiveIds[var1] != -1 && class18.regionLandArchives[var1] == null) {
            class18.regionLandArchives[var1] = class247.archive5.takeFile(class16.regionMapArchiveIds[var1], 0);
            if (class18.regionLandArchives[var1] == null) {
               var0 = false;
               ++Client.field694;
            }
         }

         if (class24.regionLandArchiveIds[var1] != -1 && class82.regionMapArchives[var1] == null) {
            class82.regionMapArchives[var1] = class247.archive5.takeFileEncrypted(class24.regionLandArchiveIds[var1], 0, xteaKeys[var1]);
            if (class82.regionMapArchives[var1] == null) {
               var0 = false;
               ++Client.field694;
            }
         }
      }

      if (!var0) {
         Client.field698 = 1;
      } else {
         Client.field696 = 0;
         var0 = true;

         int var2;
         int var3;
         int var4;
         int var5;
         int var6;
         int var7;
         int var8;
         int var9;
         int var10;
         Buffer var11;
         int var12;
         int var13;
         for(var1 = 0; var1 < class18.regionLandArchives.length; ++var1) {
            byte[] var14 = class82.regionMapArchives[var1];
            if (var14 != null) {
               var2 = (PlayerComposition.regions[var1] >> 8) * 64 - VertexNormal.baseX;
               var3 = (PlayerComposition.regions[var1] & 255) * 64 - SoundSystem.baseY;
               if (Client.isInInstance) {
                  var2 = 10;
                  var3 = 10;
               }

               boolean var15 = true;
               var11 = new Buffer(var14);
               var4 = -1;

               label521:
               while(true) {
                  var5 = var11.method6560();
                  if (var5 == 0) {
                     var0 &= var15;
                     break;
                  }

                  var4 += var5;
                  var6 = 0;
                  boolean var16 = false;

                  while(true) {
                     ObjectComposition var17;
                     do {
                        do {
                           do {
                              do {
                                 do {
                                    while(var16) {
                                       var12 = var11.readUShortSmart();
                                       if (var12 == 0) {
                                          continue label521;
                                       }

                                       var11.readUnsignedByte();
                                    }

                                    var12 = var11.readUShortSmart();
                                    if (var12 == 0) {
                                       continue label521;
                                    }

                                    var6 += var12 - 1;
                                    var7 = var6 & 63;
                                    var8 = var6 >> 6 & 63;
                                    var9 = var11.readUnsignedByte() >> 2;
                                    var10 = var8 + var2;
                                    var13 = var3 + var7;
                                 } while(var10 <= 0);
                              } while(var13 <= 0);
                           } while(var10 >= 103);
                        } while(var13 >= 103);

                        var17 = class23.getObjectDefinition(var4);
                     } while(var9 == 22 && Client.isLowDetail && var17.int1 == 0 && var17.interactType != 1 && !var17.boolean2);

                     if (!var17.needsModelFiles()) {
                        ++Client.field696;
                        var15 = false;
                     }

                     var16 = true;
                  }
               }
            }
         }

         if (!var0) {
            Client.field698 = 2;
         } else {
            if (Client.field698 != 0) {
               DirectByteArrayCopier.drawLoadingMessage("Loading - please wait.<br> (100%)", true);
            }

            HealthBarUpdate.playPcmPlayers();
            AbstractSocket.scene.clear();

            for(var1 = 0; var1 < 4; ++var1) {
               Client.collisionMaps[var1].clear();
            }

            int var32;
            for(var1 = 0; var1 < 4; ++var1) {
               for(var32 = 0; var32 < 104; ++var32) {
                  for(var2 = 0; var2 < 104; ++var2) {
                     Tiles.Tiles_renderFlags[var1][var32][var2] = 0;
                  }
               }
            }

            HealthBarUpdate.playPcmPlayers();
            class34.method327();
            var1 = class18.regionLandArchives.length;
            WorldMapSprite.method3631();
            TextureProvider.method4142(true);
            int var18;
            int var19;
            int var33;
            int var34;
            int var35;
            if (!Client.isInInstance) {
               byte[] var20;
               for(var32 = 0; var32 < var1; ++var32) {
                  var2 = (PlayerComposition.regions[var32] >> 8) * 64 - VertexNormal.baseX;
                  var3 = (PlayerComposition.regions[var32] & 255) * 64 - SoundSystem.baseY;
                  var20 = class18.regionLandArchives[var32];
                  if (var20 != null) {
                     HealthBarUpdate.playPcmPlayers();
                     WorldMapLabel.method3632(var20, var2, var3, GrandExchangeOfferOwnWorldComparator.field632 * 8 - 48, UserComparator4.field1427 * 8 - 48, Client.collisionMaps);
                  }
               }

               for(var32 = 0; var32 < var1; ++var32) {
                  var2 = (PlayerComposition.regions[var32] >> 8) * 64 - VertexNormal.baseX;
                  var3 = (PlayerComposition.regions[var32] & 255) * 64 - SoundSystem.baseY;
                  var20 = class18.regionLandArchives[var32];
                  if (var20 == null && UserComparator4.field1427 < 800) {
                     HealthBarUpdate.playPcmPlayers();
                     ItemComposition.method3085(var2, var3, 64, 64);
                  }
               }

               TextureProvider.method4142(true);

               for(var32 = 0; var32 < var1; ++var32) {
                  byte[] var21 = class82.regionMapArchives[var32];
                  if (var21 != null) {
                     var3 = (PlayerComposition.regions[var32] >> 8) * 64 - VertexNormal.baseX;
                     var33 = (PlayerComposition.regions[var32] & 255) * 64 - SoundSystem.baseY;
                     HealthBarUpdate.playPcmPlayers();
                     Scene var22 = AbstractSocket.scene;
                     CollisionMap[] var23 = Client.collisionMaps;
                     var11 = new Buffer(var21);
                     var4 = -1;

                     while(true) {
                        var5 = var11.method6560();
                        if (var5 == 0) {
                           break;
                        }

                        var4 += var5;
                        var6 = 0;

                        while(true) {
                           var19 = var11.readUShortSmart();
                           if (var19 == 0) {
                              break;
                           }

                           var6 += var19 - 1;
                           var12 = var6 & 63;
                           var7 = var6 >> 6 & 63;
                           var8 = var6 >> 12;
                           var9 = var11.readUnsignedByte();
                           var10 = var9 >> 2;
                           var13 = var9 & 3;
                           var34 = var3 + var7;
                           var35 = var33 + var12;
                           if (var34 > 0 && var35 > 0 && var34 < 103 && var35 < 103) {
                              var18 = var8;
                              if ((Tiles.Tiles_renderFlags[1][var34][var35] & 2) == 2) {
                                 var18 = var8 - 1;
                              }

                              CollisionMap var24 = null;
                              if (var18 >= 0) {
                                 var24 = var23[var18];
                              }

                              ClanChannelMember.method89(var8, var34, var35, var4, var13, var10, var22, var24);
                           }
                        }
                     }
                  }
               }
            }

            int var36;
            int var37;
            int var38;
            if (Client.isInInstance) {
               var32 = 0;

               label412:
               while(true) {
                  if (var32 >= 4) {
                     for(var32 = 0; var32 < 13; ++var32) {
                        for(var2 = 0; var2 < 13; ++var2) {
                           var3 = Client.instanceChunkTemplates[0][var32][var2];
                           if (var3 == -1) {
                              ItemComposition.method3085(var32 * 8, var2 * 8, 8, 8);
                           }
                        }
                     }

                     TextureProvider.method4142(true);
                     var32 = 0;

                     while(true) {
                        if (var32 >= 4) {
                           break label412;
                        }

                        HealthBarUpdate.playPcmPlayers();

                        for(var2 = 0; var2 < 13; ++var2) {
                           for(var3 = 0; var3 < 13; ++var3) {
                              var33 = Client.instanceChunkTemplates[var32][var2][var3];
                              if (var33 != -1) {
                                 var36 = var33 >> 24 & 3;
                                 var38 = var33 >> 1 & 3;
                                 var37 = var33 >> 14 & 1023;
                                 var4 = var33 >> 3 & 2047;
                                 var5 = (var37 / 8 << 8) + var4 / 8;

                                 for(var6 = 0; var6 < PlayerComposition.regions.length; ++var6) {
                                    if (PlayerComposition.regions[var6] == var5 && class82.regionMapArchives[var6] != null) {
                                       FontName.method6296(class82.regionMapArchives[var6], var32, var2 * 8, var3 * 8, var36, (var37 & 7) * 8, (var4 & 7) * 8, var38, AbstractSocket.scene, Client.collisionMaps);
                                       break;
                                    }
                                 }
                              }
                           }
                        }

                        ++var32;
                     }
                  }

                  HealthBarUpdate.playPcmPlayers();

                  for(var2 = 0; var2 < 13; ++var2) {
                     for(var3 = 0; var3 < 13; ++var3) {
                        boolean var39 = false;
                        var36 = Client.instanceChunkTemplates[var32][var2][var3];
                        if (var36 != -1) {
                           var38 = var36 >> 24 & 3;
                           var37 = var36 >> 1 & 3;
                           var4 = var36 >> 14 & 1023;
                           var5 = var36 >> 3 & 2047;
                           var6 = (var4 / 8 << 8) + var5 / 8;

                           for(var19 = 0; var19 < PlayerComposition.regions.length; ++var19) {
                              if (PlayerComposition.regions[var19] == var6 && class18.regionLandArchives[var19] != null) {
                                 byte[] var42 = class18.regionLandArchives[var19];
                                 var7 = var2 * 8;
                                 var8 = var3 * 8;
                                 var9 = (var4 & 7) * 8;
                                 var10 = (var5 & 7) * 8;
                                 CollisionMap[] var25 = Client.collisionMaps;

                                 for(var34 = 0; var34 < 8; ++var34) {
                                    for(var35 = 0; var35 < 8; ++var35) {
                                       if (var7 + var34 > 0 && var7 + var34 < 103 && var8 + var35 > 0 && var35 + var8 < 103) {
                                          int[] var26 = var25[var32].flags[var34 + var7];
                                          var26[var35 + var8] &= -16777217;
                                       }
                                    }
                                 }

                                 Buffer var43 = new Buffer(var42);

                                 for(var35 = 0; var35 < 4; ++var35) {
                                    for(var18 = 0; var18 < 64; ++var18) {
                                       for(int var27 = 0; var27 < 64; ++var27) {
                                          if (var38 == var35 && var18 >= var9 && var18 < var9 + 8 && var27 >= var10 && var27 < var10 + 8) {
                                             int var28 = var18 & 7;
                                             int var29 = var27 & 7;
                                             int var30 = var37 & 3;
                                             int var31;
                                             if (var30 == 0) {
                                                var31 = var28;
                                             } else if (var30 == 1) {
                                                var31 = var29;
                                             } else if (var30 == 2) {
                                                var31 = 7 - var28;
                                             } else {
                                                var31 = 7 - var29;
                                             }

                                             loadTerrain(var43, var32, var31 + var7, var8 + ClanChannelMember.method86(var18 & 7, var27 & 7, var37), 0, 0, var37);
                                          } else {
                                             loadTerrain(var43, 0, -1, -1, 0, 0, 0);
                                          }
                                       }
                                    }
                                 }

                                 var39 = true;
                                 break;
                              }
                           }
                        }

                        if (!var39) {
                           MouseRecorder.method2099(var32, var2 * 8, var3 * 8);
                        }
                     }
                  }

                  ++var32;
               }
            }

            TextureProvider.method4142(true);
            HealthBarUpdate.playPcmPlayers();
            class17.method210(AbstractSocket.scene, Client.collisionMaps);
            TextureProvider.method4142(true);
            var32 = Tiles.Tiles_minPlane;
            if (var32 > class22.Client_plane) {
               var32 = class22.Client_plane;
            }

            if (var32 < class22.Client_plane - 1) {
               var32 = class22.Client_plane - 1;
            }

            if (Client.isLowDetail) {
               AbstractSocket.scene.init(Tiles.Tiles_minPlane);
            } else {
               AbstractSocket.scene.init(0);
            }

            for(var2 = 0; var2 < 104; ++var2) {
               for(var3 = 0; var3 < 104; ++var3) {
                  ClanSettings.updateItemPile(var2, var3);
               }
            }

            HealthBarUpdate.playPcmPlayers();

            for(PendingSpawn var40 = (PendingSpawn)Client.pendingSpawns.last(); var40 != null; var40 = (PendingSpawn)Client.pendingSpawns.previous()) {
               if (var40.hitpoints == -1) {
                  var40.delay = 0;
                  UserComparator5.method2448(var40);
               } else {
                  var40.remove();
               }
            }

            ObjectComposition.ObjectDefinition_cachedModelData.clear();
            PacketBufferNode var41;
            if (class23.client.hasFrame()) {
               var41 = class21.getPacketBufferNode(ClientPacket.field2583, Client.packetWriter.isaacCipher);
               var41.packetBuffer.writeInt(1057001181);
               Client.packetWriter.addNode(var41);
            }

            if (!Client.isInInstance) {
               var2 = (GrandExchangeOfferOwnWorldComparator.field632 - 6) / 8;
               var3 = (GrandExchangeOfferOwnWorldComparator.field632 + 6) / 8;
               var33 = (UserComparator4.field1427 - 6) / 8;
               var36 = (UserComparator4.field1427 + 6) / 8;

               for(var38 = var2 - 1; var38 <= var3 + 1; ++var38) {
                  for(var37 = var33 - 1; var37 <= var36 + 1; ++var37) {
                     if (var38 < var2 || var38 > var3 || var37 < var33 || var37 > var36) {
                        class247.archive5.loadRegionFromName("m" + var38 + "_" + var37);
                        class247.archive5.loadRegionFromName("l" + var38 + "_" + var37);
                     }
                  }
               }
            }

            class12.updateGameState(30);
            HealthBarUpdate.playPcmPlayers();
            class34.method328();
            var41 = class21.getPacketBufferNode(ClientPacket.field2669, Client.packetWriter.isaacCipher);
            Client.packetWriter.addNode(var41);
            GameEngine.clock.mark();

            for(var3 = 0; var3 < 32; ++var3) {
               GameEngine.graphicsTickTimes[var3] = 0L;
            }

            for(var3 = 0; var3 < 32; ++var3) {
               GameEngine.clientTickTimes[var3] = 0L;
            }

            class260.gameCyclesToDo = 0;
         }
      }

   }
}
