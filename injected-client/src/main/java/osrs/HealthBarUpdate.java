package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("cx")
@Implements("HealthBarUpdate")
public class HealthBarUpdate extends Node {
   @ObfuscatedName("d")
   @ObfuscatedSignature(
      descriptor = "Ljv;"
   )
   @Export("Widget_archive")
   public static AbstractArchive Widget_archive;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -859723449
   )
   @Export("cycle")
   int cycle;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = 36021957
   )
   @Export("health")
   int health;
   @ObfuscatedName("f")
   @ObfuscatedGetter(
      intValue = 1070199867
   )
   @Export("health2")
   int health2;
   @ObfuscatedName("y")
   @ObfuscatedGetter(
      intValue = 687360175
   )
   @Export("cycleOffset")
   int cycleOffset;

   HealthBarUpdate(int var1, int var2, int var3, int var4) {
      this.cycle = var1;
      this.health = var2;
      this.health2 = var3;
      this.cycleOffset = var4;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(IIIII)V",
      garbageValue = "-629620630"
   )
   @Export("set")
   void set(int var1, int var2, int var3, int var4) {
      this.cycle = var1;
      this.health = var2;
      this.health2 = var3;
      this.cycleOffset = var4;
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      descriptor = "(S)V",
      garbageValue = "-1591"
   )
   public static void method2197() {
      VarbitComposition.VarbitDefinition_cached.clear();
   }

   @ObfuscatedName("ey")
   @ObfuscatedSignature(
      descriptor = "(B)Lmc;",
      garbageValue = "7"
   )
   @Export("getWorldMap")
   static WorldMap getWorldMap() {
      return class243.worldMap;
   }

   @ObfuscatedName("fi")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "-1808857292"
   )
   @Export("playPcmPlayers")
   static final void playPcmPlayers() {
      if (MouseRecorder.pcmPlayer1 != null) {
         MouseRecorder.pcmPlayer1.run();
      }

      if (DesktopPlatformInfoProvider.pcmPlayer0 != null) {
         DesktopPlatformInfoProvider.pcmPlayer0.run();
      }

   }

   @ObfuscatedName("jy")
   @ObfuscatedSignature(
      descriptor = "(Lio;III)V",
      garbageValue = "-190288118"
   )
   @Export("Widget_addToMenu")
   static final void Widget_addToMenu(Widget var0, int var1, int var2) {
      if (var0.buttonType == 1) {
         Occluder.insertMenuItemNoShift(var0.buttonText, "", 24, 0, 0, var0.id);
      }

      String var3;
      if (var0.buttonType == 2 && !Client.isSpellSelected) {
         var3 = BoundaryObject.Widget_getSpellActionName(var0);
         if (var3 != null) {
            Occluder.insertMenuItemNoShift(var3, class44.colorStartTag(65280) + var0.spellName, 25, 0, -1, var0.id);
         }
      }

      if (var0.buttonType == 3) {
         Occluder.insertMenuItemNoShift("Close", "", 26, 0, 0, var0.id);
      }

      if (var0.buttonType == 4) {
         Occluder.insertMenuItemNoShift(var0.buttonText, "", 28, 0, 0, var0.id);
      }

      if (var0.buttonType == 5) {
         Occluder.insertMenuItemNoShift(var0.buttonText, "", 29, 0, 0, var0.id);
      }

      if (var0.buttonType == 6 && Client.meslayerContinueWidget == null) {
         Occluder.insertMenuItemNoShift(var0.buttonText, "", 30, 0, -1, var0.id);
      }

      int var4;
      int var5;
      int var6;
      if (var0.type == 2) {
         var6 = 0;

         for(var4 = 0; var4 < var0.height; ++var4) {
            for(var5 = 0; var5 < var0.width; ++var5) {
               int var7 = (var0.paddingX + 32) * var5;
               int var8 = (var0.paddingY + 32) * var4;
               if (var6 < 20) {
                  var7 += var0.inventoryXOffsets[var6];
                  var8 += var0.inventoryYOffsets[var6];
               }

               if (var1 >= var7 && var2 >= var8 && var1 < var7 + 32 && var2 < var8 + 32) {
                  Client.dragItemSlotDestination = var6;
                  class18.hoveredItemContainer = var0;
                  if (var0.itemIds[var6] > 0) {
                     ItemComposition var9 = class260.ItemDefinition_get(var0.itemIds[var6] - 1);
                     if (Client.isItemSelected == 1 && World.method1663(class21.getWidgetFlags(var0))) {
                        if (var0.id != Player.selectedItemWidget || var6 != ClanChannelMember.selectedItemSlot) {
                           Occluder.insertMenuItemNoShift("Use", Client.selectedItemName + " -> " + class44.colorStartTag(16748608) + var9.name, 31, var9.id, var6, var0.id);
                        }
                     } else if (Client.isSpellSelected && World.method1663(class21.getWidgetFlags(var0))) {
                        if ((class4.selectedSpellFlags & 16) == 16) {
                           Occluder.insertMenuItemNoShift(Client.selectedSpellActionName, Client.selectedSpellName + " -> " + class44.colorStartTag(16748608) + var9.name, 32, var9.id, var6, var0.id);
                        }
                     } else {
                        String[] var10 = var9.inventoryActions;
                        int var11 = -1;
                        if (Client.shiftClickDrop) {
                           boolean var12 = Client.tapToDrop || KeyHandler.KeyHandler_pressedKeys[81];
                           if (var12) {
                              var11 = var9.getShiftClickIndex();
                           }
                        }

                        int var16;
                        if (World.method1663(class21.getWidgetFlags(var0))) {
                           for(var16 = 4; var16 >= 3; --var16) {
                              if (var11 != var16) {
                                 class125.addWidgetItemMenuItem(var0, var9, var6, var16, false);
                              }
                           }
                        }

                        if (StructComposition.method2908(class21.getWidgetFlags(var0))) {
                           Occluder.insertMenuItemNoShift("Use", class44.colorStartTag(16748608) + var9.name, 38, var9.id, var6, var0.id);
                        }

                        if (World.method1663(class21.getWidgetFlags(var0))) {
                           for(var16 = 2; var16 >= 0; --var16) {
                              if (var16 != var11) {
                                 class125.addWidgetItemMenuItem(var0, var9, var6, var16, false);
                              }
                           }

                           if (var11 >= 0) {
                              class125.addWidgetItemMenuItem(var0, var9, var6, var11, true);
                           }
                        }

                        var10 = var0.itemActions;
                        if (var10 != null) {
                           for(var16 = 4; var16 >= 0; --var16) {
                              if (var10[var16] != null) {
                                 byte var13 = 0;
                                 if (var16 == 0) {
                                    var13 = 39;
                                 }

                                 if (var16 == 1) {
                                    var13 = 40;
                                 }

                                 if (var16 == 2) {
                                    var13 = 41;
                                 }

                                 if (var16 == 3) {
                                    var13 = 42;
                                 }

                                 if (var16 == 4) {
                                    var13 = 43;
                                 }

                                 Occluder.insertMenuItemNoShift(var10[var16], class44.colorStartTag(16748608) + var9.name, var13, var9.id, var6, var0.id);
                              }
                           }
                        }

                        Occluder.insertMenuItemNoShift("Examine", class44.colorStartTag(16748608) + var9.name, 1005, var9.id, var6, var0.id);
                     }
                  }
               }

               ++var6;
            }
         }
      }

      if (var0.isIf3) {
         boolean var14;
         if (Client.isSpellSelected) {
            var4 = class21.getWidgetFlags(var0);
            var14 = (var4 >> 21 & 1) != 0;
            if (var14 && (class4.selectedSpellFlags & 32) == 32) {
               Occluder.insertMenuItemNoShift(Client.selectedSpellActionName, Client.selectedSpellName + " -> " + var0.dataText, 58, 0, var0.childIndex, var0.id);
            }
         } else {
            String var15;
            for(var6 = 9; var6 >= 5; --var6) {
               if (!TextureProvider.method4141(class21.getWidgetFlags(var0), var6) && var0.onOp == null) {
                  var15 = null;
               } else if (var0.actions != null && var0.actions.length > var6 && var0.actions[var6] != null && var0.actions[var6].trim().length() != 0) {
                  var15 = var0.actions[var6];
               } else {
                  var15 = null;
               }

               if (var15 != null) {
                  Occluder.insertMenuItemNoShift(var15, var0.dataText, 1007, var6 + 1, var0.childIndex, var0.id);
               }
            }

            var3 = BoundaryObject.Widget_getSpellActionName(var0);
            if (var3 != null) {
               Occluder.insertMenuItemNoShift(var3, var0.dataText, 25, 0, var0.childIndex, var0.id);
            }

            for(var4 = 4; var4 >= 0; --var4) {
               if (!TextureProvider.method4141(class21.getWidgetFlags(var0), var4) && var0.onOp == null) {
                  var15 = null;
               } else if (var0.actions != null && var0.actions.length > var4 && var0.actions[var4] != null && var0.actions[var4].trim().length() != 0) {
                  var15 = var0.actions[var4];
               } else {
                  var15 = null;
               }

               if (var15 != null) {
                  BoundaryObject.insertMenuItem(var15, var0.dataText, 57, var4 + 1, var0.childIndex, var0.id, var0.prioritizeMenuEntry);
               }
            }

            var5 = class21.getWidgetFlags(var0);
            var14 = (var5 & 1) != 0;
            if (var14) {
               Occluder.insertMenuItemNoShift("Continue", "", 30, 0, var0.childIndex, var0.id);
            }
         }
      }

   }
}
