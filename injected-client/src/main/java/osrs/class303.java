package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("kp")
public final class class303 {
   @ObfuscatedName("ij")
   @ObfuscatedSignature(
      descriptor = "([Lio;IIIIIIIII)V",
      garbageValue = "-1945038256"
   )
   @Export("drawInterface")
   static final void drawInterface(Widget[] var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      Rasterizer2D.Rasterizer2D_setClip(var2, var3, var4, var5);
      Rasterizer3D.Rasterizer3D_setClipFromRasterizer2D();

      for(int var9 = 0; var9 < var0.length; ++var9) {
         Widget var10 = var0[var9];
         if (var10 != null && (var10.parentId == var1 || var1 == -1412584499 && var10 == Client.clickedWidget)) {
            int var11;
            if (var8 == -1) {
               Client.rootWidgetXs[Client.rootWidgetCount] = var10.x + var6;
               Client.rootWidgetYs[Client.rootWidgetCount] = var7 + var10.y;
               Client.rootWidgetWidths[Client.rootWidgetCount] = var10.width;
               Client.rootWidgetHeights[Client.rootWidgetCount] = var10.height;
               var11 = ++Client.rootWidgetCount - 1;
            } else {
               var11 = var8;
            }

            var10.rootIndex = var11;
            var10.cycle = Client.cycle;
            if (!var10.isIf3 || !HitSplatDefinition.isComponentHidden(var10)) {
               if (var10.contentType > 0) {
                  class258.method4836(var10);
               }

               int var12 = var10.x + var6;
               int var13 = var7 + var10.y;
               int var14 = var10.transparencyTop;
               int var15;
               int var16;
               if (var10 == Client.clickedWidget) {
                  if (var1 != -1412584499 && !var10.isScrollBar) {
                     class4.field49 = var0;
                     ClanChannel.field38 = var6;
                     class82.field1020 = var7;
                     continue;
                  }

                  if (Client.isDraggingWidget && Client.field819) {
                     var15 = MouseHandler.MouseHandler_x;
                     var16 = MouseHandler.MouseHandler_y;
                     var15 -= Client.widgetClickX;
                     var16 -= Client.widgetClickY;
                     if (var15 < Client.field740) {
                        var15 = Client.field740;
                     }

                     if (var15 + var10.width > Client.field740 + Client.clickedWidgetParent.width) {
                        var15 = Client.field740 + Client.clickedWidgetParent.width - var10.width;
                     }

                     if (var16 < Client.field821) {
                        var16 = Client.field821;
                     }

                     if (var16 + var10.height > Client.field821 + Client.clickedWidgetParent.height) {
                        var16 = Client.field821 + Client.clickedWidgetParent.height - var10.height;
                     }

                     var12 = var15;
                     var13 = var16;
                  }

                  if (!var10.isScrollBar) {
                     var14 = 128;
                  }
               }

               int var17;
               int var18;
               int var19;
               int var20;
               int var21;
               int var22;
               if (var10.type == 2) {
                  var15 = var2;
                  var16 = var3;
                  var17 = var4;
                  var18 = var5;
               } else if (var10.type == 9) {
                  var19 = var12;
                  var20 = var13;
                  var21 = var12 + var10.width;
                  var22 = var13 + var10.height;
                  if (var21 < var12) {
                     var19 = var21;
                     var21 = var12;
                  }

                  if (var22 < var13) {
                     var20 = var22;
                     var22 = var13;
                  }

                  ++var21;
                  ++var22;
                  var15 = var19 > var2 ? var19 : var2;
                  var16 = var20 > var3 ? var20 : var3;
                  var17 = var21 < var4 ? var21 : var4;
                  var18 = var22 < var5 ? var22 : var5;
               } else {
                  var19 = var12 + var10.width;
                  var20 = var13 + var10.height;
                  var15 = var12 > var2 ? var12 : var2;
                  var16 = var13 > var3 ? var13 : var3;
                  var17 = var19 < var4 ? var19 : var4;
                  var18 = var20 < var5 ? var20 : var5;
               }

               if (!var10.isIf3 || var15 < var17 && var16 < var18) {
                  if (var10.contentType != 0) {
                     if (var10.contentType == 1336) {
                        if (Client.displayFps) {
                           var13 += 15;
                           UserComparator3.fontPlain12.drawRightAligned("Fps:" + GameEngine.fps, var12 + var10.width, var13, 16776960, -1);
                           var13 += 15;
                           Runtime var34 = Runtime.getRuntime();
                           var20 = (int)((var34.totalMemory() - var34.freeMemory()) / 1024L);
                           var21 = 16776960;
                           if (var20 > 327680 && !Client.isLowDetail) {
                              var21 = 16711680;
                           }

                           UserComparator3.fontPlain12.drawRightAligned("Mem:" + var20 + "k", var12 + var10.width, var13, var21, -1);
                           var13 += 15;
                        }
                        continue;
                     }

                     if (var10.contentType == 1337) {
                        Client.viewportX = var12;
                        Client.viewportY = var13;
                        ScriptFrame.drawEntities(var12, var13, var10.width, var10.height);
                        Client.field719[var10.rootIndex] = true;
                        Rasterizer2D.Rasterizer2D_setClip(var2, var3, var4, var5);
                        continue;
                     }

                     if (var10.contentType == 1338) {
                        ObjectSound.drawMinimap(var10, var12, var13, var11);
                        Rasterizer2D.Rasterizer2D_setClip(var2, var3, var4, var5);
                        continue;
                     }

                     if (var10.contentType == 1339) {
                        class13.drawCompass(var10, var12, var13, var11);
                        Rasterizer2D.Rasterizer2D_setClip(var2, var3, var4, var5);
                        continue;
                     }

                     if (var10.contentType == 1400) {
                        class243.worldMap.draw(var12, var13, var10.width, var10.height, Client.cycle);
                     }

                     if (var10.contentType == 1401) {
                        class243.worldMap.drawOverview(var12, var13, var10.width, var10.height);
                     }

                     if (var10.contentType == 1402) {
                        class24.loginScreenRunesAnimation.draw(var12, Client.cycle);
                     }
                  }

                  if (var10.type == 0) {
                     if (!var10.isIf3 && HitSplatDefinition.isComponentHidden(var10) && var10 != World.mousedOverWidgetIf1) {
                        continue;
                     }

                     if (!var10.isIf3) {
                        if (var10.scrollY > var10.scrollHeight - var10.height) {
                           var10.scrollY = var10.scrollHeight - var10.height;
                        }

                        if (var10.scrollY < 0) {
                           var10.scrollY = 0;
                        }
                     }

                     drawInterface(var0, var10.id, var15, var16, var17, var18, var12 - var10.scrollX, var13 - var10.scrollY, var11);
                     if (var10.children != null) {
                        drawInterface(var10.children, var10.id, var15, var16, var17, var18, var12 - var10.scrollX, var13 - var10.scrollY, var11);
                     }

                     InterfaceParent var23 = (InterfaceParent)Client.interfaceParents.get((long)var10.id);
                     if (var23 != null) {
                        class225.drawWidgets(var23.group, var15, var16, var17, var18, var12, var13, var11);
                     }

                     Rasterizer2D.Rasterizer2D_setClip(var2, var3, var4, var5);
                     Rasterizer3D.Rasterizer3D_setClipFromRasterizer2D();
                  }

                  if (Client.isResizable || Client.field860[var11] || Client.gameDrawingMode > 1) {
                     if (var10.type == 0 && !var10.isIf3 && var10.scrollHeight > var10.height) {
                        class32.drawScrollBar(var12 + var10.width, var13, var10.scrollY, var10.height, var10.scrollHeight);
                     }

                     if (var10.type != 1) {
                        int var24;
                        int var25;
                        int var26;
                        int var33;
                        if (var10.type == 2) {
                           var19 = 0;

                           for(var20 = 0; var20 < var10.rawHeight; ++var20) {
                              for(var21 = 0; var21 < var10.rawWidth; ++var21) {
                                 var22 = var12 + var21 * (var10.paddingX + 32);
                                 var33 = var20 * (var10.paddingY + 32) + var13;
                                 if (var19 < 20) {
                                    var22 += var10.inventoryXOffsets[var19];
                                    var33 += var10.inventoryYOffsets[var19];
                                 }

                                 if (var10.itemIds[var19] <= 0) {
                                    if (var10.inventorySprites != null && var19 < 20) {
                                       SpritePixels var36 = var10.getInventorySprite(var19);
                                       if (var36 != null) {
                                          var36.drawTransBgAt(var22, var33);
                                       } else if (Widget.field2956) {
                                          WorldMapCacheName.invalidateWidget(var10);
                                       }
                                    }
                                 } else {
                                    boolean var35 = false;
                                    boolean var46 = false;
                                    var26 = var10.itemIds[var19] - 1;
                                    if (var22 + 32 > var2 && var22 < var4 && var33 + 32 > var3 && var33 < var5 || var10 == Script.dragInventoryWidget && var19 == Client.dragItemSlotSource) {
                                       SpritePixels var42;
                                       if (Client.isItemSelected == 1 && var19 == ClanChannelMember.selectedItemSlot && var10.id == Player.selectedItemWidget) {
                                          var42 = ModelData0.getItemSprite(var26, var10.itemQuantities[var19], 2, 0, 2, false);
                                       } else {
                                          var42 = ModelData0.getItemSprite(var26, var10.itemQuantities[var19], 1, 3153952, 2, false);
                                       }

                                       if (var42 != null) {
                                          if (var10 == Script.dragInventoryWidget && var19 == Client.dragItemSlotSource) {
                                             var24 = MouseHandler.MouseHandler_x - Client.draggedWidgetX;
                                             var25 = MouseHandler.MouseHandler_y - Client.draggedWidgetY;
                                             if (var24 < 5 && var24 > -5) {
                                                var24 = 0;
                                             }

                                             if (var25 < 5 && var25 > -5) {
                                                var25 = 0;
                                             }

                                             if (Client.itemDragDuration < 5) {
                                                var24 = 0;
                                                var25 = 0;
                                             }

                                             var42.drawTransAt(var24 + var22, var33 + var25, 128);
                                             if (var1 != -1) {
                                                Widget var47 = var0[var1 & '\uffff'];
                                                int var45;
                                                if (var33 + var25 < Rasterizer2D.Rasterizer2D_yClipStart && var47.scrollY > 0) {
                                                   var45 = (Rasterizer2D.Rasterizer2D_yClipStart - var33 - var25) * Client.field913 / 3;
                                                   if (var45 > Client.field913 * 10) {
                                                      var45 = Client.field913 * 10;
                                                   }

                                                   if (var45 > var47.scrollY) {
                                                      var45 = var47.scrollY;
                                                   }

                                                   var47.scrollY -= var45;
                                                   Client.draggedWidgetY += var45;
                                                   WorldMapCacheName.invalidateWidget(var47);
                                                }

                                                if (var25 + var33 + 32 > Rasterizer2D.Rasterizer2D_yClipEnd && var47.scrollY < var47.scrollHeight - var47.height) {
                                                   var45 = (var33 + var25 + 32 - Rasterizer2D.Rasterizer2D_yClipEnd) * Client.field913 / 3;
                                                   if (var45 > Client.field913 * 10) {
                                                      var45 = Client.field913 * 10;
                                                   }

                                                   if (var45 > var47.scrollHeight - var47.height - var47.scrollY) {
                                                      var45 = var47.scrollHeight - var47.height - var47.scrollY;
                                                   }

                                                   var47.scrollY += var45;
                                                   Client.draggedWidgetY -= var45;
                                                   WorldMapCacheName.invalidateWidget(var47);
                                                }
                                             }
                                          } else if (var10 == GrandExchangeEvents.field3626 && var19 == Client.field841) {
                                             var42.drawTransAt(var22, var33, 128);
                                          } else {
                                             var42.drawTransBgAt(var22, var33);
                                          }
                                       } else {
                                          WorldMapCacheName.invalidateWidget(var10);
                                       }
                                    }
                                 }

                                 ++var19;
                              }
                           }
                        } else if (var10.type == 3) {
                           if (InvDefinition.runCs1(var10)) {
                              var19 = var10.color2;
                              if (var10 == World.mousedOverWidgetIf1 && var10.mouseOverColor2 != 0) {
                                 var19 = var10.mouseOverColor2;
                              }
                           } else {
                              var19 = var10.color;
                              if (var10 == World.mousedOverWidgetIf1 && var10.mouseOverColor != 0) {
                                 var19 = var10.mouseOverColor;
                              }
                           }

                           if (var10.fill) {
                              switch(var10.fillMode.field4230) {
                              case 1:
                                 Rasterizer2D.Rasterizer2D_fillRectangleGradient(var12, var13, var10.width, var10.height, var10.color, var10.color2);
                                 break;
                              case 2:
                                 Rasterizer2D.Rasterizer2D_fillRectangleGradientAlpha(var12, var13, var10.width, var10.height, var10.color, var10.color2, 255 - (var10.transparencyTop & 255), 255 - (var10.transparencyBot & 255));
                                 break;
                              default:
                                 if (var14 == 0) {
                                    Rasterizer2D.Rasterizer2D_fillRectangle(var12, var13, var10.width, var10.height, var19);
                                 } else {
                                    Rasterizer2D.Rasterizer2D_fillRectangleAlpha(var12, var13, var10.width, var10.height, var19, 256 - (var14 & 255));
                                 }
                              }
                           } else if (var14 == 0) {
                              Rasterizer2D.Rasterizer2D_drawRectangle(var12, var13, var10.width, var10.height, var19);
                           } else {
                              Rasterizer2D.Rasterizer2D_drawRectangleAlpha(var12, var13, var10.width, var10.height, var19, 256 - (var14 & 255));
                           }
                        } else {
                           Font var27;
                           if (var10.type == 4) {
                              var27 = var10.getFont();
                              if (var27 == null) {
                                 if (Widget.field2956) {
                                    WorldMapCacheName.invalidateWidget(var10);
                                 }
                              } else {
                                 String var38 = var10.text;
                                 if (InvDefinition.runCs1(var10)) {
                                    var20 = var10.color2;
                                    if (var10 == World.mousedOverWidgetIf1 && var10.mouseOverColor2 != 0) {
                                       var20 = var10.mouseOverColor2;
                                    }

                                    if (var10.text2.length() > 0) {
                                       var38 = var10.text2;
                                    }
                                 } else {
                                    var20 = var10.color;
                                    if (var10 == World.mousedOverWidgetIf1 && var10.mouseOverColor != 0) {
                                       var20 = var10.mouseOverColor;
                                    }
                                 }

                                 if (var10.isIf3 && var10.itemId != -1) {
                                    ItemComposition var41 = class260.ItemDefinition_get(var10.itemId);
                                    var38 = var41.name;
                                    if (var38 == null) {
                                       var38 = "null";
                                    }

                                    if ((var41.isStackable == 1 || var10.itemQuantity != 1) && var10.itemQuantity != -1) {
                                       var38 = class44.colorStartTag(16748608) + var38 + "</col> " + 'x' + UserComparator3.formatItemStacks(var10.itemQuantity);
                                    }
                                 }

                                 if (var10 == Client.meslayerContinueWidget) {
                                    var38 = "Please wait...";
                                    var20 = var10.color;
                                 }

                                 if (!var10.isIf3) {
                                    var38 = JagexCache.method2540(var38, var10);
                                 }

                                 var27.drawLines(var38, var12, var13, var10.width, var10.height, var20, var10.textShadowed ? 0 : -1, var10.textXAlignment, var10.textYAlignment, var10.textLineHeight);
                              }
                           } else if (var10.type == 5) {
                              SpritePixels var37;
                              if (!var10.isIf3) {
                                 var37 = var10.getSprite(InvDefinition.runCs1(var10));
                                 if (var37 != null) {
                                    var37.drawTransBgAt(var12, var13);
                                 } else if (Widget.field2956) {
                                    WorldMapCacheName.invalidateWidget(var10);
                                 }
                              } else {
                                 if (var10.itemId != -1) {
                                    var37 = ModelData0.getItemSprite(var10.itemId, var10.itemQuantity, var10.outline, var10.spriteShadow, var10.itemQuantityMode, false);
                                 } else {
                                    var37 = var10.getSprite(false);
                                 }

                                 if (var37 == null) {
                                    if (Widget.field2956) {
                                       WorldMapCacheName.invalidateWidget(var10);
                                    }
                                 } else {
                                    var20 = var37.width;
                                    var21 = var37.height;
                                    if (!var10.spriteTiling) {
                                       var22 = var10.width * 4096 / var20;
                                       if (var10.spriteAngle != 0) {
                                          var37.method6991(var10.width / 2 + var12, var10.height / 2 + var13, var10.spriteAngle, var22);
                                       } else if (var14 != 0) {
                                          var37.drawTransScaledAt(var12, var13, var10.width, var10.height, 256 - (var14 & 255));
                                       } else if (var20 == var10.width && var21 == var10.height) {
                                          var37.drawTransBgAt(var12, var13);
                                       } else {
                                          var37.drawScaledAt(var12, var13, var10.width, var10.height);
                                       }
                                    } else {
                                       Rasterizer2D.Rasterizer2D_expandClip(var12, var13, var12 + var10.width, var13 + var10.height);
                                       var22 = (var20 - 1 + var10.width) / var20;
                                       var33 = (var21 - 1 + var10.height) / var21;

                                       for(var24 = 0; var24 < var22; ++var24) {
                                          for(var25 = 0; var25 < var33; ++var25) {
                                             if (var10.spriteAngle != 0) {
                                                var37.method6991(var20 / 2 + var12 + var24 * var20, var21 / 2 + var13 + var25 * var21, var10.spriteAngle, 4096);
                                             } else if (var14 != 0) {
                                                var37.drawTransAt(var12 + var24 * var20, var13 + var21 * var25, 256 - (var14 & 255));
                                             } else {
                                                var37.drawTransBgAt(var12 + var24 * var20, var13 + var21 * var25);
                                             }
                                          }
                                       }

                                       Rasterizer2D.Rasterizer2D_setClip(var2, var3, var4, var5);
                                    }
                                 }
                              }
                           } else {
                              ItemComposition var28;
                              if (var10.type == 6) {
                                 boolean var40 = InvDefinition.runCs1(var10);
                                 if (var40) {
                                    var20 = var10.sequenceId2;
                                 } else {
                                    var20 = var10.sequenceId;
                                 }

                                 Model var43 = null;
                                 var22 = 0;
                                 if (var10.itemId != -1) {
                                    var28 = class260.ItemDefinition_get(var10.itemId);
                                    if (var28 != null) {
                                       var28 = var28.getCountObj(var10.itemQuantity);
                                       var43 = var28.getModel(1);
                                       if (var43 != null) {
                                          var43.calculateBoundsCylinder();
                                          var22 = var43.height / 2;
                                       } else {
                                          WorldMapCacheName.invalidateWidget(var10);
                                       }
                                    }
                                 } else if (var10.modelType == 5) {
                                    if (var10.modelId == 0) {
                                       var43 = Client.playerAppearance.getModel((SequenceDefinition)null, -1, (SequenceDefinition)null, -1);
                                    } else {
                                       var43 = class93.localPlayer.getModel();
                                    }
                                 } else if (var20 == -1) {
                                    var43 = var10.getModel((SequenceDefinition)null, -1, var40, class93.localPlayer.appearance);
                                    if (var43 == null && Widget.field2956) {
                                       WorldMapCacheName.invalidateWidget(var10);
                                    }
                                 } else {
                                    SequenceDefinition var44 = LoginScreenAnimation.SequenceDefinition_get(var20);
                                    var43 = var10.getModel(var44, var10.modelFrame, var40, class93.localPlayer.appearance);
                                    if (var43 == null && Widget.field2956) {
                                       WorldMapCacheName.invalidateWidget(var10);
                                    }
                                 }

                                 Rasterizer3D.method3848(var10.width / 2 + var12, var10.height / 2 + var13);
                                 var33 = Rasterizer3D.Rasterizer3D_sine[var10.modelAngleX] * var10.modelZoom >> 16;
                                 var24 = Rasterizer3D.Rasterizer3D_cosine[var10.modelAngleX] * var10.modelZoom >> 16;
                                 if (var43 != null) {
                                    if (!var10.isIf3) {
                                       var43.method4217(0, var10.modelAngleY, 0, var10.modelAngleX, 0, var33, var24);
                                    } else {
                                       var43.calculateBoundsCylinder();
                                       if (var10.modelOrthog) {
                                          var43.method4209(0, var10.modelAngleY, var10.modelAngleZ, var10.modelAngleX, var10.modelOffsetX, var22 + var33 + var10.modelOffsetY, var24 + var10.modelOffsetY, var10.modelZoom);
                                       } else {
                                          var43.method4217(0, var10.modelAngleY, var10.modelAngleZ, var10.modelAngleX, var10.modelOffsetX, var33 + var22 + var10.modelOffsetY, var24 + var10.modelOffsetY);
                                       }
                                    }
                                 }

                                 Rasterizer3D.Rasterizer3D_method3();
                              } else {
                                 if (var10.type == 7) {
                                    var27 = var10.getFont();
                                    if (var27 == null) {
                                       if (Widget.field2956) {
                                          WorldMapCacheName.invalidateWidget(var10);
                                       }
                                       continue;
                                    }

                                    var20 = 0;

                                    for(var21 = 0; var21 < var10.rawHeight; ++var21) {
                                       for(var22 = 0; var22 < var10.rawWidth; ++var22) {
                                          if (var10.itemIds[var20] > 0) {
                                             var28 = class260.ItemDefinition_get(var10.itemIds[var20] - 1);
                                             String var29;
                                             if (var28.isStackable != 1 && var10.itemQuantities[var20] == 1) {
                                                var29 = class44.colorStartTag(16748608) + var28.name + "</col>";
                                             } else {
                                                var29 = class44.colorStartTag(16748608) + var28.name + "</col> " + 'x' + UserComparator3.formatItemStacks(var10.itemQuantities[var20]);
                                             }

                                             var25 = var22 * (var10.paddingX + 115) + var12;
                                             var26 = var21 * (var10.paddingY + 12) + var13;
                                             if (var10.textXAlignment == 0) {
                                                var27.draw(var29, var25, var26, var10.color, var10.textShadowed ? 0 : -1);
                                             } else if (var10.textXAlignment == 1) {
                                                var27.drawCentered(var29, var10.width / 2 + var25, var26, var10.color, var10.textShadowed ? 0 : -1);
                                             } else {
                                                var27.drawRightAligned(var29, var25 + var10.width - 1, var26, var10.color, var10.textShadowed ? 0 : -1);
                                             }
                                          }

                                          ++var20;
                                       }
                                    }
                                 }

                                 if (var10.type == 8 && var10 == ReflectionCheck.field609 && Client.field833 == Client.field792) {
                                    var19 = 0;
                                    var20 = 0;
                                    Font var39 = UserComparator3.fontPlain12;
                                    String var30 = var10.text;

                                    String var31;
                                    for(var30 = JagexCache.method2540(var30, var10); var30.length() > 0; var20 = var20 + var39.ascent + 1) {
                                       var24 = var30.indexOf("<br>");
                                       if (var24 != -1) {
                                          var31 = var30.substring(0, var24);
                                          var30 = var30.substring(var24 + 4);
                                       } else {
                                          var31 = var30;
                                          var30 = "";
                                       }

                                       var25 = var39.stringWidth(var31);
                                       if (var25 > var19) {
                                          var19 = var25;
                                       }
                                    }

                                    var19 += 6;
                                    var20 += 7;
                                    var24 = var12 + var10.width - 5 - var19;
                                    var25 = var13 + var10.height + 5;
                                    if (var24 < var12 + 5) {
                                       var24 = var12 + 5;
                                    }

                                    if (var19 + var24 > var4) {
                                       var24 = var4 - var19;
                                    }

                                    if (var25 + var20 > var5) {
                                       var25 = var5 - var20;
                                    }

                                    Rasterizer2D.Rasterizer2D_fillRectangle(var24, var25, var19, var20, 16777120);
                                    Rasterizer2D.Rasterizer2D_drawRectangle(var24, var25, var19, var20, 0);
                                    var30 = var10.text;
                                    var26 = var25 + var39.ascent + 2;

                                    for(var30 = JagexCache.method2540(var30, var10); var30.length() > 0; var26 = var26 + var39.ascent + 1) {
                                       int var32 = var30.indexOf("<br>");
                                       if (var32 != -1) {
                                          var31 = var30.substring(0, var32);
                                          var30 = var30.substring(var32 + 4);
                                       } else {
                                          var31 = var30;
                                          var30 = "";
                                       }

                                       var39.draw(var31, var24 + 3, var26, 0, -1);
                                    }
                                 }

                                 if (var10.type == 9) {
                                    if (var10.field2992) {
                                       var19 = var12;
                                       var20 = var13 + var10.height;
                                       var21 = var12 + var10.width;
                                       var22 = var13;
                                    } else {
                                       var19 = var12;
                                       var20 = var13;
                                       var21 = var12 + var10.width;
                                       var22 = var13 + var10.height;
                                    }

                                    if (var10.lineWid == 1) {
                                       Rasterizer2D.Rasterizer2D_drawLine(var19, var20, var21, var22, var10.color);
                                    } else {
                                       ClanChannel.method36(var19, var20, var21, var22, var10.color, var10.lineWid);
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
}
