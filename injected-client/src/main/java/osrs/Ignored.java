package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("la")
@Implements("Ignored")
public class Ignored extends User {
   @ObfuscatedName("rl")
   @ObfuscatedGetter(
      intValue = -114974043
   )
   static int field3847;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -792451577
   )
   @Export("id")
   int id;

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(Lla;I)I",
      garbageValue = "-208555214"
   )
   @Export("compareTo_ignored")
   int compareTo_ignored(Ignored var1) {
      return this.id - var1.id;
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "(Llx;I)I",
      garbageValue = "539732409"
   )
   @Export("compareTo_user")
   public int compareTo_user(User var1) {
      return this.compareTo_ignored((Ignored)var1);
   }

   public int compareTo(Object var1) {
      return this.compareTo_ignored((Ignored)var1);
   }

   @ObfuscatedName("gy")
   @ObfuscatedSignature(
      descriptor = "(Lcy;IIIIII)V",
      garbageValue = "-82802445"
   )
   @Export("drawActor2d")
   static final void drawActor2d(Actor var0, int var1, int var2, int var3, int var4, int var5) {
      if (var0 != null && var0.isVisible()) {
         if (var0 instanceof NPC) {
            NPCComposition var6 = ((NPC)var0).definition;
            if (var6.transforms != null) {
               var6 = var6.transform();
            }

            if (var6 == null) {
               return;
            }
         }

         int var77 = Players.Players_count;
         int[] var7 = Players.Players_indices;
         byte var8 = 0;
         int var9;
         if (var1 < var77 && var0.playerCycle == Client.cycle && WorldMapIcon_1.method3232((Player)var0)) {
            Player var10 = (Player)var0;
            if (var1 < var77) {
               var9 = var0.defaultHeight + 15;
               GrandExchangeEvents.worldToScreen(var0.x, var0.y, var9);
               AbstractFont var11 = (AbstractFont)Client.fontsMap.get(FontName.FontName_plain12);
               byte var12 = 9;
               var11.drawCentered(var10.username.getName(), var2 + Client.viewportTempX, var3 + Client.viewportTempY - var12, 16777215, 0);
               var8 = 18;
            }
         }

         int var78 = -2;
         int var13;
         int var24;
         int var25;
         int var79;
         int var80;
         if (!var0.healthBars.method5236()) {
            var9 = var0.defaultHeight + 15;
            GrandExchangeEvents.worldToScreen(var0.x, var0.y, var9);

            for(HealthBar var14 = (HealthBar)var0.healthBars.last(); var14 != null; var14 = (HealthBar)var0.healthBars.previous()) {
               HealthBarUpdate var15 = var14.get(Client.cycle);
               if (var15 == null) {
                  if (var14.isEmpty()) {
                     var14.remove();
                  }
               } else {
                  HealthBarDefinition var16 = var14.definition;
                  SpritePixels var17 = var16.getBackSprite();
                  SpritePixels var18 = var16.getFrontSprite();
                  var79 = 0;
                  int var19;
                  if (var17 != null && var18 != null) {
                     if (var16.widthPadding * 2 < var18.subWidth) {
                        var79 = var16.widthPadding;
                     }

                     var19 = var18.subWidth - var79 * 2;
                  } else {
                     var19 = var16.width;
                  }

                  int var20 = 255;
                  boolean var21 = true;
                  int var22 = Client.cycle - var15.cycle;
                  int var23 = var19 * var15.health2 / var16.width;
                  if (var15.cycleOffset > var22) {
                     var24 = var16.field1608 == 0 ? 0 : var16.field1608 * (var22 / var16.field1608);
                     var80 = var19 * var15.health / var16.width;
                     var25 = var24 * (var23 - var80) / var15.cycleOffset + var80;
                  } else {
                     var25 = var23;
                     var24 = var16.int5 + var15.cycleOffset - var22;
                     if (var16.int3 >= 0) {
                        var20 = (var24 << 8) / (var16.int5 - var16.int3);
                     }
                  }

                  if (var15.health2 > 0 && var25 < 1) {
                     var25 = 1;
                  }

                  if (var17 != null && var18 != null) {
                     if (var25 == var19) {
                        var25 += var79 * 2;
                     } else {
                        var25 += var79;
                     }

                     var24 = var17.subHeight;
                     var78 += var24;
                     var80 = var2 + Client.viewportTempX - (var19 >> 1);
                     var13 = var3 + Client.viewportTempY - var78;
                     var80 -= var79;
                     if (var20 >= 0 && var20 < 255) {
                        var17.drawTransAt(var80, var13, var20);
                        Rasterizer2D.Rasterizer2D_expandClip(var80, var13, var25 + var80, var13 + var24);
                        var18.drawTransAt(var80, var13, var20);
                     } else {
                        var17.drawTransBgAt(var80, var13);
                        Rasterizer2D.Rasterizer2D_expandClip(var80, var13, var80 + var25, var13 + var24);
                        var18.drawTransBgAt(var80, var13);
                     }

                     Rasterizer2D.Rasterizer2D_setClip(var2, var3, var2 + var4, var3 + var5);
                     var78 += 2;
                  } else {
                     var78 += 5;
                     if (Client.viewportTempX > -1) {
                        var24 = var2 + Client.viewportTempX - (var19 >> 1);
                        var80 = var3 + Client.viewportTempY - var78;
                        Rasterizer2D.Rasterizer2D_fillRectangle(var24, var80, var25, 5, 65280);
                        Rasterizer2D.Rasterizer2D_fillRectangle(var24 + var25, var80, var19 - var25, 5, 16711680);
                     }

                     var78 += 2;
                  }
               }
            }
         }

         if (var78 == -2) {
            var78 += 7;
         }

         var78 += var8;
         int var81;
         if (var1 < var77) {
            Player var83 = (Player)var0;
            if (var83.isHidden) {
               return;
            }

            if (var83.headIconPk != -1 || var83.headIconPrayer != -1) {
               var81 = var0.defaultHeight + 15;
               GrandExchangeEvents.worldToScreen(var0.x, var0.y, var81);
               if (Client.viewportTempX > -1) {
                  if (var83.headIconPk != -1) {
                     var78 += 25;
                     class35.headIconPkSprites[var83.headIconPk].drawTransBgAt(var2 + Client.viewportTempX - 12, var3 + Client.viewportTempY - var78);
                  }

                  if (var83.headIconPrayer != -1) {
                     var78 += 25;
                     TextureProvider.headIconPrayerSprites[var83.headIconPrayer].drawTransBgAt(var2 + Client.viewportTempX - 12, var3 + Client.viewportTempY - var78);
                  }
               }
            }

            if (var1 >= 0 && Client.hintArrowType == 10 && var7[var1] == Client.hintArrowPlayerIndex) {
               var81 = var0.defaultHeight + 15;
               GrandExchangeEvents.worldToScreen(var0.x, var0.y, var81);
               if (Client.viewportTempX > -1) {
                  var78 += PacketBufferNode.headIconHintSprites[1].subHeight;
                  PacketBufferNode.headIconHintSprites[1].drawTransBgAt(var2 + Client.viewportTempX - 12, var3 + Client.viewportTempY - var78);
               }
            }
         } else {
            NPCComposition var82 = ((NPC)var0).definition;
            if (var82.transforms != null) {
               var82 = var82.transform();
            }

            if (var82.headIconPrayer >= 0 && var82.headIconPrayer < TextureProvider.headIconPrayerSprites.length) {
               var81 = var0.defaultHeight + 15;
               GrandExchangeEvents.worldToScreen(var0.x, var0.y, var81);
               if (Client.viewportTempX > -1) {
                  TextureProvider.headIconPrayerSprites[var82.headIconPrayer].drawTransBgAt(var2 + Client.viewportTempX - 12, var3 + Client.viewportTempY - 30);
               }
            }

            if (Client.hintArrowType == 1 && Client.npcIndices[var1 - var77] == Client.hintArrowNpcIndex && Client.cycle % 20 < 10) {
               var81 = var0.defaultHeight + 15;
               GrandExchangeEvents.worldToScreen(var0.x, var0.y, var81);
               if (Client.viewportTempX > -1) {
                  PacketBufferNode.headIconHintSprites[0].drawTransBgAt(var2 + Client.viewportTempX - 12, var3 + Client.viewportTempY - 28);
               }
            }
         }

         if (var0.overheadText != null && (var1 >= var77 || !var0.field1248 && (Client.publicChatMode == 4 || !var0.isAutoChatting && (Client.publicChatMode == 0 || Client.publicChatMode == 3 || Client.publicChatMode == 1 && ((Player)var0).isFriend())))) {
            var9 = var0.defaultHeight;
            GrandExchangeEvents.worldToScreen(var0.x, var0.y, var9);
            if (Client.viewportTempX > -1 && Client.overheadTextCount < Client.overheadTextLimit) {
               Client.overheadTextXOffsets[Client.overheadTextCount] = Widget.fontBold12.stringWidth(var0.overheadText) / 2;
               Client.overheadTextAscents[Client.overheadTextCount] = Widget.fontBold12.ascent;
               Client.overheadTextXs[Client.overheadTextCount] = Client.viewportTempX;
               Client.overheadTextYs[Client.overheadTextCount] = Client.viewportTempY;
               Client.overheadTextColors[Client.overheadTextCount] = var0.overheadTextColor;
               Client.overheadTextEffects[Client.overheadTextCount] = var0.overheadTextEffect;
               Client.overheadTextCyclesRemaining[Client.overheadTextCount] = var0.overheadTextCyclesRemaining;
               Client.overheadText[Client.overheadTextCount] = var0.overheadText;
               ++Client.overheadTextCount;
            }
         }

         for(var9 = 0; var9 < 4; ++var9) {
            var81 = var0.hitSplatCycles[var9];
            int var85 = var0.hitSplatTypes[var9];
            HitSplatDefinition var84 = null;
            int var86 = 0;
            if (var85 >= 0) {
               if (var81 <= Client.cycle) {
                  continue;
               }

               var84 = Projectile.method1966(var0.hitSplatTypes[var9]);
               var86 = var84.field1731;
               if (var84 != null && var84.transforms != null) {
                  var84 = var84.transform();
                  if (var84 == null) {
                     var0.hitSplatCycles[var9] = -1;
                     continue;
                  }
               }
            } else if (var81 < 0) {
               continue;
            }

            int var87 = var0.hitSplatTypes2[var9];
            HitSplatDefinition var88 = null;
            if (var87 >= 0) {
               var88 = Projectile.method1966(var87);
               if (var88 != null && var88.transforms != null) {
                  var88 = var88.transform();
               }
            }

            if (var81 - var86 <= Client.cycle) {
               if (var84 == null) {
                  var0.hitSplatCycles[var9] = -1;
               } else {
                  var79 = var0.defaultHeight / 2;
                  GrandExchangeEvents.worldToScreen(var0.x, var0.y, var79);
                  if (Client.viewportTempX > -1) {
                     if (var9 == 1) {
                        Client.viewportTempY -= 20;
                     }

                     if (var9 == 2) {
                        Client.viewportTempX -= 15;
                        Client.viewportTempY -= 10;
                     }

                     if (var9 == 3) {
                        Client.viewportTempX += 15;
                        Client.viewportTempY -= 10;
                     }

                     SpritePixels var89 = null;
                     SpritePixels var90 = null;
                     SpritePixels var91 = null;
                     SpritePixels var92 = null;
                     var80 = 0;
                     var13 = 0;
                     var24 = 0;
                     var25 = 0;
                     int var26 = 0;
                     int var27 = 0;
                     int var28 = 0;
                     int var29 = 0;
                     SpritePixels var30 = null;
                     SpritePixels var31 = null;
                     SpritePixels var32 = null;
                     SpritePixels var33 = null;
                     int var34 = 0;
                     int var35 = 0;
                     int var36 = 0;
                     int var37 = 0;
                     int var38 = 0;
                     int var39 = 0;
                     int var40 = 0;
                     int var41 = 0;
                     int var42 = 0;
                     var89 = var84.method2916();
                     int var43;
                     if (var89 != null) {
                        var80 = var89.subWidth;
                        var43 = var89.subHeight;
                        if (var43 > var42) {
                           var42 = var43;
                        }

                        var26 = var89.xOffset;
                     }

                     var90 = var84.method2931();
                     if (var90 != null) {
                        var13 = var90.subWidth;
                        var43 = var90.subHeight;
                        if (var43 > var42) {
                           var42 = var43;
                        }

                        var27 = var90.xOffset;
                     }

                     var91 = var84.method2951();
                     if (var91 != null) {
                        var24 = var91.subWidth;
                        var43 = var91.subHeight;
                        if (var43 > var42) {
                           var42 = var43;
                        }

                        var28 = var91.xOffset;
                     }

                     var92 = var84.method2945();
                     if (var92 != null) {
                        var25 = var92.subWidth;
                        var43 = var92.subHeight;
                        if (var43 > var42) {
                           var42 = var43;
                        }

                        var29 = var92.xOffset;
                     }

                     if (var88 != null) {
                        var30 = var88.method2916();
                        if (var30 != null) {
                           var34 = var30.subWidth;
                           var43 = var30.subHeight;
                           if (var43 > var42) {
                              var42 = var43;
                           }

                           var38 = var30.xOffset;
                        }

                        var31 = var88.method2931();
                        if (var31 != null) {
                           var35 = var31.subWidth;
                           var43 = var31.subHeight;
                           if (var43 > var42) {
                              var42 = var43;
                           }

                           var39 = var31.xOffset;
                        }

                        var32 = var88.method2951();
                        if (var32 != null) {
                           var36 = var32.subWidth;
                           var43 = var32.subHeight;
                           if (var43 > var42) {
                              var42 = var43;
                           }

                           var40 = var32.xOffset;
                        }

                        var33 = var88.method2945();
                        if (var33 != null) {
                           var37 = var33.subWidth;
                           var43 = var33.subHeight;
                           if (var43 > var42) {
                              var42 = var43;
                           }

                           var41 = var33.xOffset;
                        }
                     }

                     Font var44 = var84.getFont();
                     if (var44 == null) {
                        var44 = Actor.fontPlain11;
                     }

                     Font var45;
                     if (var88 != null) {
                        var45 = var88.getFont();
                        if (var45 == null) {
                           var45 = Actor.fontPlain11;
                        }
                     } else {
                        var45 = Actor.fontPlain11;
                     }

                     String var46 = null;
                     String var47 = null;
                     boolean var48 = false;
                     int var49 = 0;
                     var46 = var84.getString(var0.hitSplatValues[var9]);
                     int var50 = var44.stringWidth(var46);
                     if (var88 != null) {
                        var47 = var88.getString(var0.hitSplatValues2[var9]);
                        var49 = var45.stringWidth(var47);
                     }

                     int var51 = 0;
                     int var52 = 0;
                     if (var13 > 0) {
                        if (var91 == null && var92 == null) {
                           var51 = 1;
                        } else {
                           var51 = var50 / var13 + 1;
                        }
                     }

                     if (var88 != null && var35 > 0) {
                        if (var32 == null && var33 == null) {
                           var52 = 1;
                        } else {
                           var52 = var49 / var35 + 1;
                        }
                     }

                     int var53 = 0;
                     int var54 = var53;
                     if (var80 > 0) {
                        var53 += var80;
                     }

                     var53 += 2;
                     int var55 = var53;
                     if (var24 > 0) {
                        var53 += var24;
                     }

                     int var56 = var53;
                     int var57 = var53;
                     int var58;
                     if (var13 > 0) {
                        var58 = var13 * var51;
                        var53 += var58;
                        var57 += (var58 - var50) / 2;
                     } else {
                        var53 += var50;
                     }

                     var58 = var53;
                     if (var25 > 0) {
                        var53 += var25;
                     }

                     int var59 = 0;
                     int var60 = 0;
                     int var61 = 0;
                     int var62 = 0;
                     int var63 = 0;
                     int var64;
                     if (var88 != null) {
                        var53 += 2;
                        var59 = var53;
                        if (var34 > 0) {
                           var53 += var34;
                        }

                        var53 += 2;
                        var60 = var53;
                        if (var36 > 0) {
                           var53 += var36;
                        }

                        var61 = var53;
                        var63 = var53;
                        if (var35 > 0) {
                           var64 = var35 * var52;
                           var53 += var64;
                           var63 += (var64 - var49) / 2;
                        } else {
                           var53 += var49;
                        }

                        var62 = var53;
                        if (var37 > 0) {
                           var53 += var37;
                        }
                     }

                     var64 = var0.hitSplatCycles[var9] - Client.cycle;
                     int var65 = var84.field1739 - var64 * var84.field1739 / var84.field1731;
                     int var66 = var64 * var84.field1740 / var84.field1731 + -var84.field1740;
                     int var67 = var65 + (var2 + Client.viewportTempX - (var53 >> 1));
                     int var68 = var3 + Client.viewportTempY - 12 + var66;
                     int var69 = var68;
                     int var70 = var68 + var42;
                     int var71 = var68 + var84.field1744 + 15;
                     int var72 = var71 - var44.maxAscent;
                     int var73 = var71 + var44.maxDescent;
                     if (var72 < var68) {
                        var69 = var72;
                     }

                     if (var73 > var70) {
                        var70 = var73;
                     }

                     int var74 = 0;
                     int var75;
                     int var76;
                     if (var88 != null) {
                        var74 = var68 + var88.field1744 + 15;
                        var75 = var74 - var45.maxAscent;
                        var76 = var74 + var45.maxDescent;
                        if (var75 < var69) {
                           ;
                        }

                        if (var76 > var70) {
                           ;
                        }
                     }

                     var75 = 255;
                     if (var84.field1749 >= 0) {
                        var75 = (var64 << 8) / (var84.field1731 - var84.field1749);
                     }

                     if (var75 >= 0 && var75 < 255) {
                        if (var89 != null) {
                           var89.drawTransAt(var54 + var67 - var26, var68, var75);
                        }

                        if (var91 != null) {
                           var91.drawTransAt(var67 + var55 - var28, var68, var75);
                        }

                        if (var90 != null) {
                           for(var76 = 0; var76 < var51; ++var76) {
                              var90.drawTransAt(var13 * var76 + (var67 + var56 - var27), var68, var75);
                           }
                        }

                        if (var92 != null) {
                           var92.drawTransAt(var67 + var58 - var29, var68, var75);
                        }

                        var44.drawAlpha(var46, var67 + var57, var71, var84.textColor, 0, var75);
                        if (var88 != null) {
                           if (var30 != null) {
                              var30.drawTransAt(var59 + var67 - var38, var68, var75);
                           }

                           if (var32 != null) {
                              var32.drawTransAt(var67 + var60 - var40, var68, var75);
                           }

                           if (var31 != null) {
                              for(var76 = 0; var76 < var52; ++var76) {
                                 var31.drawTransAt(var35 * var76 + (var61 + var67 - var39), var68, var75);
                              }
                           }

                           if (var33 != null) {
                              var33.drawTransAt(var67 + var62 - var41, var68, var75);
                           }

                           var45.drawAlpha(var47, var63 + var67, var74, var88.textColor, 0, var75);
                        }
                     } else {
                        if (var89 != null) {
                           var89.drawTransBgAt(var67 + var54 - var26, var68);
                        }

                        if (var91 != null) {
                           var91.drawTransBgAt(var55 + var67 - var28, var68);
                        }

                        if (var90 != null) {
                           for(var76 = 0; var76 < var51; ++var76) {
                              var90.drawTransBgAt(var13 * var76 + (var56 + var67 - var27), var68);
                           }
                        }

                        if (var92 != null) {
                           var92.drawTransBgAt(var58 + var67 - var29, var68);
                        }

                        var44.draw(var46, var67 + var57, var71, var84.textColor | -16777216, 0);
                        if (var88 != null) {
                           if (var30 != null) {
                              var30.drawTransBgAt(var59 + var67 - var38, var68);
                           }

                           if (var32 != null) {
                              var32.drawTransBgAt(var60 + var67 - var40, var68);
                           }

                           if (var31 != null) {
                              for(var76 = 0; var76 < var52; ++var76) {
                                 var31.drawTransBgAt(var35 * var76 + (var61 + var67 - var39), var68);
                              }
                           }

                           if (var33 != null) {
                              var33.drawTransBgAt(var62 + var67 - var41, var68);
                           }

                           var45.draw(var47, var67 + var63, var74, var88.textColor | -16777216, 0);
                        }
                     }
                  }
               }
            }
         }
      }

   }
}
