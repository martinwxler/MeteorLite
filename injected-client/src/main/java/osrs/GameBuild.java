package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("jk")
@Implements("GameBuild")
public class GameBuild {
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "Ljk;"
   )
   @Export("LIVE")
   public static final GameBuild LIVE = new GameBuild("LIVE", 0);
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "Ljk;"
   )
   @Export("BUILDLIVE")
   public static final GameBuild BUILDLIVE = new GameBuild("BUILDLIVE", 3);
   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "Ljk;"
   )
   @Export("RC")
   public static final GameBuild RC = new GameBuild("RC", 1);
   @ObfuscatedName("y")
   @ObfuscatedSignature(
      descriptor = "Ljk;"
   )
   @Export("WIP")
   public static final GameBuild WIP = new GameBuild("WIP", 2);
   @ObfuscatedName("p")
   @Export("name")
   public final String name;
   @ObfuscatedName("j")
   @ObfuscatedGetter(
      intValue = 629900867
   )
   @Export("buildId")
   public final int buildId;

   GameBuild(String var1, int var2) {
      this.name = var1;
      this.buildId = var2;
   }

   @ObfuscatedName("ac")
   @ObfuscatedSignature(
      descriptor = "(ILcf;ZB)I",
      garbageValue = "4"
   )
   static int method4843(int var0, Script var1, boolean var2) {
      int var3;
      if (var0 == 6600) {
         var3 = class22.Client_plane;
         int var13 = (class93.localPlayer.x >> 7) + VertexNormal.baseX;
         int var16 = (class93.localPlayer.y >> 7) + SoundSystem.baseY;
         HealthBarUpdate.getWorldMap().method6110(var3, var13, var16, true);
         return 1;
      } else {
         WorldMapArea var4;
         if (var0 == 6601) {
            var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
            String var14 = "";
            var4 = HealthBarUpdate.getWorldMap().getMapArea(var3);
            if (var4 != null) {
               var14 = var4.getExternalName();
            }

            Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var14;
            return 1;
         } else if (var0 == 6602) {
            var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
            HealthBarUpdate.getWorldMap().setCurrentMapAreaId(var3);
            return 1;
         } else if (var0 == 6603) {
            Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = HealthBarUpdate.getWorldMap().getZoomLevel();
            return 1;
         } else if (var0 == 6604) {
            var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
            HealthBarUpdate.getWorldMap().setZoomPercentage(var3);
            return 1;
         } else if (var0 == 6605) {
            Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = HealthBarUpdate.getWorldMap().isCacheLoaded() ? 1 : 0;
            return 1;
         } else {
            Coord var5;
            if (var0 == 6606) {
               var5 = new Coord(Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize]);
               HealthBarUpdate.getWorldMap().setWorldMapPositionTarget(var5.x, var5.y);
               return 1;
            } else if (var0 == 6607) {
               var5 = new Coord(Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize]);
               HealthBarUpdate.getWorldMap().setWorldMapPositionTargetInstant(var5.x, var5.y);
               return 1;
            } else if (var0 == 6608) {
               var5 = new Coord(Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize]);
               HealthBarUpdate.getWorldMap().jumpToSourceCoord(var5.plane, var5.x, var5.y);
               return 1;
            } else if (var0 == 6609) {
               var5 = new Coord(Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize]);
               HealthBarUpdate.getWorldMap().jumpToSourceCoordInstant(var5.plane, var5.x, var5.y);
               return 1;
            } else if (var0 == 6610) {
               Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = HealthBarUpdate.getWorldMap().getDisplayX();
               Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = HealthBarUpdate.getWorldMap().getDisplayY();
               return 1;
            } else {
               WorldMapArea var6;
               if (var0 == 6611) {
                  var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                  var6 = HealthBarUpdate.getWorldMap().getMapArea(var3);
                  if (var6 == null) {
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                  } else {
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var6.getOrigin().packed();
                  }

                  return 1;
               } else if (var0 == 6612) {
                  var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                  var6 = HealthBarUpdate.getWorldMap().getMapArea(var3);
                  if (var6 == null) {
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                  } else {
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = (var6.getRegionHighX() - var6.getRegionLowX() + 1) * 64;
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = (var6.getRegionHighY() - var6.getRegionLowY() + 1) * 64;
                  }

                  return 1;
               } else if (var0 == 6613) {
                  var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                  var6 = HealthBarUpdate.getWorldMap().getMapArea(var3);
                  if (var6 == null) {
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                  } else {
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var6.getRegionLowX() * 64;
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var6.getRegionLowY() * 64;
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var6.getRegionHighX() * 64 + 64 - 1;
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var6.getRegionHighY() * 64 + 64 - 1;
                  }

                  return 1;
               } else if (var0 == 6614) {
                  var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                  var6 = HealthBarUpdate.getWorldMap().getMapArea(var3);
                  if (var6 == null) {
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
                  } else {
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var6.getZoom();
                  }

                  return 1;
               } else if (var0 == 6615) {
                  var5 = HealthBarUpdate.getWorldMap().getDisplayCoord();
                  if (var5 == null) {
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
                  } else {
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var5.x;
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var5.y;
                  }

                  return 1;
               } else if (var0 == 6616) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = HealthBarUpdate.getWorldMap().currentMapAreaId();
                  return 1;
               } else if (var0 == 6617) {
                  var5 = new Coord(Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize]);
                  var6 = HealthBarUpdate.getWorldMap().getCurrentMapArea();
                  if (var6 == null) {
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
                     return 1;
                  } else {
                     int[] var15 = var6.position(var5.plane, var5.x, var5.y);
                     if (var15 == null) {
                        Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
                        Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
                     } else {
                        Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var15[0];
                        Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var15[1];
                     }

                     return 1;
                  }
               } else {
                  Coord var7;
                  if (var0 == 6618) {
                     var5 = new Coord(Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize]);
                     var6 = HealthBarUpdate.getWorldMap().getCurrentMapArea();
                     if (var6 == null) {
                        Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
                        Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
                        return 1;
                     } else {
                        var7 = var6.coord(var5.x, var5.y);
                        if (var7 == null) {
                           Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
                        } else {
                           Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var7.packed();
                        }

                        return 1;
                     }
                  } else {
                     Coord var8;
                     if (var0 == 6619) {
                        class44.Interpreter_intStackSize -= 2;
                        var3 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
                        var8 = new Coord(Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1]);
                        Skeleton.method3922(var3, var8, false);
                        return 1;
                     } else if (var0 == 6620) {
                        class44.Interpreter_intStackSize -= 2;
                        var3 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
                        var8 = new Coord(Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1]);
                        Skeleton.method3922(var3, var8, true);
                        return 1;
                     } else if (var0 == 6621) {
                        class44.Interpreter_intStackSize -= 2;
                        var3 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
                        var8 = new Coord(Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1]);
                        var4 = HealthBarUpdate.getWorldMap().getMapArea(var3);
                        if (var4 == null) {
                           Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                           return 1;
                        } else {
                           Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var4.containsCoord(var8.plane, var8.x, var8.y) ? 1 : 0;
                           return 1;
                        }
                     } else if (var0 == 6622) {
                        Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = HealthBarUpdate.getWorldMap().getDisplayWith();
                        Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = HealthBarUpdate.getWorldMap().getDisplayHeight();
                        return 1;
                     } else if (var0 == 6623) {
                        var5 = new Coord(Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize]);
                        var6 = HealthBarUpdate.getWorldMap().mapAreaAtCoord(var5.plane, var5.x, var5.y);
                        if (var6 == null) {
                           Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
                        } else {
                           Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var6.getId();
                        }

                        return 1;
                     } else if (var0 == 6624) {
                        HealthBarUpdate.getWorldMap().setMaxFlashCount(Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize]);
                        return 1;
                     } else if (var0 == 6625) {
                        HealthBarUpdate.getWorldMap().resetMaxFlashCount();
                        return 1;
                     } else if (var0 == 6626) {
                        HealthBarUpdate.getWorldMap().setCyclesPerFlash(Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize]);
                        return 1;
                     } else if (var0 == 6627) {
                        HealthBarUpdate.getWorldMap().resetCyclesPerFlash();
                        return 1;
                     } else {
                        boolean var9;
                        if (var0 == 6628) {
                           var9 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                           HealthBarUpdate.getWorldMap().setPerpetualFlash(var9);
                           return 1;
                        } else if (var0 == 6629) {
                           var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                           HealthBarUpdate.getWorldMap().flashElement(var3);
                           return 1;
                        } else if (var0 == 6630) {
                           var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                           HealthBarUpdate.getWorldMap().flashCategory(var3);
                           return 1;
                        } else if (var0 == 6631) {
                           HealthBarUpdate.getWorldMap().stopCurrentFlashes();
                           return 1;
                        } else if (var0 == 6632) {
                           var9 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                           HealthBarUpdate.getWorldMap().setElementsDisabled(var9);
                           return 1;
                        } else {
                           boolean var10;
                           if (var0 == 6633) {
                              class44.Interpreter_intStackSize -= 2;
                              var3 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
                              var10 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1] == 1;
                              HealthBarUpdate.getWorldMap().disableElement(var3, var10);
                              return 1;
                           } else if (var0 == 6634) {
                              class44.Interpreter_intStackSize -= 2;
                              var3 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
                              var10 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1] == 1;
                              HealthBarUpdate.getWorldMap().setCategoryDisabled(var3, var10);
                              return 1;
                           } else if (var0 == 6635) {
                              Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = HealthBarUpdate.getWorldMap().getElementsDisabled() ? 1 : 0;
                              return 1;
                           } else if (var0 == 6636) {
                              var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                              Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = HealthBarUpdate.getWorldMap().isElementDisabled(var3) ? 1 : 0;
                              return 1;
                           } else if (var0 == 6637) {
                              var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                              Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = HealthBarUpdate.getWorldMap().isCategoryDisabled(var3) ? 1 : 0;
                              return 1;
                           } else if (var0 == 6638) {
                              class44.Interpreter_intStackSize -= 2;
                              var3 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
                              var8 = new Coord(Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1]);
                              var7 = HealthBarUpdate.getWorldMap().method6153(var3, var8);
                              if (var7 == null) {
                                 Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
                              } else {
                                 Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var7.packed();
                              }

                              return 1;
                           } else {
                              AbstractWorldMapIcon var11;
                              if (var0 == 6639) {
                                 var11 = HealthBarUpdate.getWorldMap().iconStart();
                                 if (var11 == null) {
                                    Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
                                    Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
                                 } else {
                                    Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var11.getElement();
                                    Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var11.coord2.packed();
                                 }

                                 return 1;
                              } else if (var0 == 6640) {
                                 var11 = HealthBarUpdate.getWorldMap().iconNext();
                                 if (var11 == null) {
                                    Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
                                    Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
                                 } else {
                                    Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var11.getElement();
                                    Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var11.coord2.packed();
                                 }

                                 return 1;
                              } else {
                                 WorldMapElement var12;
                                 if (var0 == 6693) {
                                    var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                                    var12 = class6.WorldMapElement_get(var3);
                                    if (var12.name == null) {
                                       Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                                    } else {
                                       Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var12.name;
                                    }

                                    return 1;
                                 } else if (var0 == 6694) {
                                    var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                                    var12 = class6.WorldMapElement_get(var3);
                                    Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var12.textSize;
                                    return 1;
                                 } else if (var0 == 6695) {
                                    var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                                    var12 = class6.WorldMapElement_get(var3);
                                    if (var12 == null) {
                                       Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
                                    } else {
                                       Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var12.category;
                                    }

                                    return 1;
                                 } else if (var0 == 6696) {
                                    var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                                    var12 = class6.WorldMapElement_get(var3);
                                    if (var12 == null) {
                                       Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
                                    } else {
                                       Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var12.sprite1;
                                    }

                                    return 1;
                                 } else if (var0 == 6697) {
                                    Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = class69.worldMapEvent.mapElement;
                                    return 1;
                                 } else if (var0 == 6698) {
                                    Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = class69.worldMapEvent.coord1.packed();
                                    return 1;
                                 } else if (var0 == 6699) {
                                    Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = class69.worldMapEvent.coord2.packed();
                                    return 1;
                                 } else {
                                    return 2;
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }
}
