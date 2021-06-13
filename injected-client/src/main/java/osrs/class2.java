package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("f")
public class class2 {
   @ObfuscatedName("l")
   @ObfuscatedSignature(
      descriptor = "Lie;"
   )
   @Export("musicTrack")
   public static MusicTrack musicTrack;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      longValue = 4932681583424374285L
   )
   long field21;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = -2049607965
   )
   int field13 = -1;
   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "Lki;"
   )
   IterableNodeDeque field14 = new IterableNodeDeque();

   @ObfuscatedSignature(
      descriptor = "(Lnd;)V"
   )
   public class2(Buffer var1) {
      this.method19(var1);
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(Lnd;I)V",
      garbageValue = "1835710687"
   )
   void method19(Buffer var1) {
      this.field21 = var1.readLong();
      this.field13 = var1.readInt();

      for(int var2 = var1.readUnsignedByte(); var2 != 0; var2 = var1.readUnsignedByte()) {
         Object var3;
         if (var2 == 3) {
            var3 = new class13(this);
         } else if (var2 == 1) {
            var3 = new class8(this);
         } else if (var2 == 13) {
            var3 = new class26(this);
         } else if (var2 == 4) {
            var3 = new class17(this);
         } else if (var2 == 6) {
            var3 = new class10(this);
         } else if (var2 == 5) {
            var3 = new class22(this);
         } else if (var2 == 2) {
            var3 = new class23(this);
         } else if (var2 == 7) {
            var3 = new class21(this);
         } else if (var2 == 14) {
            var3 = new class18(this);
         } else if (var2 == 8) {
            var3 = new class6(this);
         } else if (var2 == 9) {
            var3 = new class0(this);
         } else if (var2 == 10) {
            var3 = new class4(this);
         } else if (var2 == 11) {
            var3 = new class1(this);
         } else if (var2 == 12) {
            var3 = new class15(this);
         } else {
            if (var2 != 15) {
               throw new RuntimeException("");
            }

            var3 = new class27(this);
         }

         ((class14)var3).vmethod276(var1);
         this.field14.addFirst((Node)var3);
      }

   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "(Ll;I)V",
      garbageValue = "-184881258"
   )
   public void method16(ClanSettings var1) {
      if (var1.field109 == this.field21 && this.field13 == var1.field94) {
         for(class14 var2 = (class14)this.field14.last(); var2 != null; var2 = (class14)this.field14.previous()) {
            var2.vmethod281(var1);
         }

         ++var1.field94;
      } else {
         throw new RuntimeException("");
      }
   }

   @ObfuscatedName("gg")
   @ObfuscatedSignature(
      descriptor = "(Lcy;B)V",
      garbageValue = "0"
   )
   static final void method21(Actor var0) {
      if (var0.field1283 == Client.cycle || var0.sequence == -1 || var0.sequenceDelay != 0 || var0.sequenceFrameCycle + 1 > LoginScreenAnimation.SequenceDefinition_get(var0.sequence).frameLengths[var0.sequenceFrame]) {
         int var1 = var0.field1283 - var0.field1265;
         int var2 = Client.cycle - var0.field1265;
         int var3 = var0.field1239 * 64 + var0.field1278 * 128;
         int var4 = var0.field1239 * 64 + var0.field1280 * 128;
         int var5 = var0.field1239 * 64 + var0.field1269 * 128;
         int var6 = var0.field1239 * 64 + var0.field1238 * 128;
         var0.x = (var5 * var2 + var3 * (var1 - var2)) / var1;
         var0.y = (var2 * var6 + var4 * (var1 - var2)) / var1;
      }

      var0.field1294 = 0;
      var0.orientation = var0.field1284;
      var0.rotation = var0.orientation;
   }

   @ObfuscatedName("im")
   @ObfuscatedSignature(
      descriptor = "(IIIII)V",
      garbageValue = "-1987725219"
   )
   @Export("addSceneMenuOptions")
   static final void addSceneMenuOptions(int var0, int var1, int var2, int var3) {
      if (Client.isItemSelected == 0 && !Client.isSpellSelected) {
         Occluder.insertMenuItemNoShift("Walk here", "", 23, 0, var0 - var2, var1 - var3);
      }

      long var4 = -1L;
      long var6 = -1L;

      int var8;
      for(var8 = 0; var8 < ItemComposition.method3084(); ++var8) {
         long var9 = HealthBarDefinition.method2720(var8);
         if (var9 != var6) {
            var6 = var9;
            int var11 = class5.method58(var8);
            int var12 = GrandExchangeOfferOwnWorldComparator.method1166(var8);
            int var13 = class261.method4842(var8);
            int var14 = class93.Entity_unpackID(ViewportMouse.ViewportMouse_entityTags[var8]);
            int var15 = var14;
            if (var13 == 2 && AbstractSocket.scene.getObjectFlags(class22.Client_plane, var11, var12, var9) >= 0) {
               ObjectComposition var16 = class23.getObjectDefinition(var14);
               if (var16.transforms != null) {
                  var16 = var16.transform();
               }

               if (var16 == null) {
                  continue;
               }

               if (Client.isItemSelected == 1) {
                  Occluder.insertMenuItemNoShift("Use", Client.selectedItemName + " -> " + class44.colorStartTag(65535) + var16.name, 1, var14, var11, var12);
               } else if (Client.isSpellSelected) {
                  if ((class4.selectedSpellFlags & 4) == 4) {
                     Occluder.insertMenuItemNoShift(Client.selectedSpellActionName, Client.selectedSpellName + " -> " + class44.colorStartTag(65535) + var16.name, 2, var14, var11, var12);
                  }
               } else {
                  String[] var17 = var16.actions;
                  if (var17 != null) {
                     for(int var18 = 4; var18 >= 0; --var18) {
                        if (var17[var18] != null) {
                           short var19 = 0;
                           if (var18 == 0) {
                              var19 = 3;
                           }

                           if (var18 == 1) {
                              var19 = 4;
                           }

                           if (var18 == 2) {
                              var19 = 5;
                           }

                           if (var18 == 3) {
                              var19 = 6;
                           }

                           if (var18 == 4) {
                              var19 = 1001;
                           }

                           Occluder.insertMenuItemNoShift(var17[var18], class44.colorStartTag(65535) + var16.name, var19, var15, var11, var12);
                        }
                     }
                  }

                  Occluder.insertMenuItemNoShift("Examine", class44.colorStartTag(65535) + var16.name, 1002, var16.id, var11, var12);
               }
            }

            int[] var20;
            Player var28;
            int var29;
            NPC var30;
            int var32;
            if (var13 == 1) {
               NPC var21 = Client.npcs[var15];
               if (var21 == null) {
                  continue;
               }

               if (var21.definition.size == 1 && (var21.x & 127) == 64 && (var21.y & 127) == 64) {
                  for(var29 = 0; var29 < Client.npcCount; ++var29) {
                     var30 = Client.npcs[Client.npcIndices[var29]];
                     if (var30 != null && var21 != var30 && var30.definition.size == 1 && var30.x == var21.x && var30.y == var21.y) {
                        class35.addNpcToMenu(var30.definition, Client.npcIndices[var29], var11, var12);
                     }
                  }

                  var29 = Players.Players_count;
                  var20 = Players.Players_indices;

                  for(var32 = 0; var32 < var29; ++var32) {
                     var28 = Client.players[var20[var32]];
                     if (var28 != null && var28.x == var21.x && var21.y == var28.y) {
                        HitSplatDefinition.addPlayerToMenu(var28, var20[var32], var11, var12);
                     }
                  }
               }

               class35.addNpcToMenu(var21.definition, var15, var11, var12);
            }

            if (var13 == 0) {
               Player var31 = Client.players[var15];
               if (var31 == null) {
                  continue;
               }

               if ((var31.x & 127) == 64 && (var31.y & 127) == 64) {
                  for(var29 = 0; var29 < Client.npcCount; ++var29) {
                     var30 = Client.npcs[Client.npcIndices[var29]];
                     if (var30 != null && var30.definition.size == 1 && var31.x == var30.x && var30.y == var31.y) {
                        class35.addNpcToMenu(var30.definition, Client.npcIndices[var29], var11, var12);
                     }
                  }

                  var29 = Players.Players_count;
                  var20 = Players.Players_indices;

                  for(var32 = 0; var32 < var29; ++var32) {
                     var28 = Client.players[var20[var32]];
                     if (var28 != null && var28 != var31 && var31.x == var28.x && var31.y == var28.y) {
                        HitSplatDefinition.addPlayerToMenu(var28, var20[var32], var11, var12);
                     }
                  }
               }

               if (var15 != Client.combatTargetPlayerIndex) {
                  HitSplatDefinition.addPlayerToMenu(var31, var15, var11, var12);
               } else {
                  var4 = var9;
               }
            }

            if (var13 == 3) {
               NodeDeque var33 = Client.groundItems[class22.Client_plane][var11][var12];
               if (var33 != null) {
                  for(TileItem var22 = (TileItem)var33.first(); var22 != null; var22 = (TileItem)var33.next()) {
                     ItemComposition var23 = class260.ItemDefinition_get(var22.id);
                     if (Client.isItemSelected == 1) {
                        Occluder.insertMenuItemNoShift("Use", Client.selectedItemName + " -> " + class44.colorStartTag(16748608) + var23.name, 16, var22.id, var11, var12);
                     } else if (Client.isSpellSelected) {
                        if ((class4.selectedSpellFlags & 1) == 1) {
                           Occluder.insertMenuItemNoShift(Client.selectedSpellActionName, Client.selectedSpellName + " -> " + class44.colorStartTag(16748608) + var23.name, 17, var22.id, var11, var12);
                        }
                     } else {
                        String[] var24 = var23.groundActions;

                        for(int var25 = 4; var25 >= 0; --var25) {
                           if (var24 != null && var24[var25] != null) {
                              byte var26 = 0;
                              if (var25 == 0) {
                                 var26 = 18;
                              }

                              if (var25 == 1) {
                                 var26 = 19;
                              }

                              if (var25 == 2) {
                                 var26 = 20;
                              }

                              if (var25 == 3) {
                                 var26 = 21;
                              }

                              if (var25 == 4) {
                                 var26 = 22;
                              }

                              Occluder.insertMenuItemNoShift(var24[var25], class44.colorStartTag(16748608) + var23.name, var26, var22.id, var11, var12);
                           } else if (var25 == 2) {
                              Occluder.insertMenuItemNoShift("Take", class44.colorStartTag(16748608) + var23.name, 20, var22.id, var11, var12);
                           }
                        }

                        Occluder.insertMenuItemNoShift("Examine", class44.colorStartTag(16748608) + var23.name, 1004, var22.id, var11, var12);
                     }
                  }
               }
            }
         }
      }

      if (var4 != -1L) {
         var8 = VarpDefinition.method2630(var4);
         int var27 = class229.method4374(var4);
         Player var10 = Client.players[Client.combatTargetPlayerIndex];
         HitSplatDefinition.addPlayerToMenu(var10, Client.combatTargetPlayerIndex, var8, var27);
      }

   }
}
