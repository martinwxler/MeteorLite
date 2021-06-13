package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ev")
@Implements("HitSplatDefinition")
public class HitSplatDefinition extends DualNode {
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "Ljv;"
   )
   @Export("HitSplatDefinition_archive")
   public static AbstractArchive HitSplatDefinition_archive;
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "Ljv;"
   )
   public static AbstractArchive field1734;
   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "Ljv;"
   )
   @Export("HitSplatDefinition_fontsArchive")
   public static AbstractArchive HitSplatDefinition_fontsArchive;
   @ObfuscatedName("y")
   @ObfuscatedSignature(
      descriptor = "Lhz;"
   )
   @Export("HitSplatDefinition_cached")
   static EvictingDualNodeHashTable HitSplatDefinition_cached = new EvictingDualNodeHashTable(64);
   @ObfuscatedName("p")
   @ObfuscatedSignature(
      descriptor = "Lhz;"
   )
   @Export("HitSplatDefinition_cachedSprites")
   static EvictingDualNodeHashTable HitSplatDefinition_cachedSprites = new EvictingDualNodeHashTable(64);
   @ObfuscatedName("j")
   @ObfuscatedSignature(
      descriptor = "Lhz;"
   )
   @Export("HitSplatDefinition_cachedFonts")
   static EvictingDualNodeHashTable HitSplatDefinition_cachedFonts = new EvictingDualNodeHashTable(20);
   @ObfuscatedName("bs")
   @ObfuscatedSignature(
      descriptor = "[Lop;"
   )
   @Export("worldSelectArrows")
   static IndexedSprite[] worldSelectArrows;
   @ObfuscatedName("l")
   @ObfuscatedGetter(
      intValue = -418995629
   )
   @Export("fontId")
   int fontId = -1;
   @ObfuscatedName("o")
   @ObfuscatedGetter(
      intValue = -131436485
   )
   @Export("textColor")
   public int textColor = 16777215;
   @ObfuscatedName("c")
   @ObfuscatedGetter(
      intValue = -1784798067
   )
   public int field1731 = 70;
   @ObfuscatedName("e")
   @ObfuscatedGetter(
      intValue = 444689543
   )
   int field1735 = -1;
   @ObfuscatedName("g")
   @ObfuscatedGetter(
      intValue = 311873853
   )
   int field1736 = -1;
   @ObfuscatedName("a")
   @ObfuscatedGetter(
      intValue = 592944735
   )
   int field1737 = -1;
   @ObfuscatedName("k")
   @ObfuscatedGetter(
      intValue = -1479453361
   )
   int field1738 = -1;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = -264775369
   )
   public int field1739 = 0;
   @ObfuscatedName("x")
   @ObfuscatedGetter(
      intValue = 1703061619
   )
   public int field1740 = 0;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      intValue = 154309501
   )
   public int field1749 = -1;
   @ObfuscatedName("w")
   String field1742 = "";
   @ObfuscatedName("t")
   @ObfuscatedGetter(
      intValue = -1751658075
   )
   public int field1743 = -1;
   @ObfuscatedName("h")
   @ObfuscatedGetter(
      intValue = -942409287
   )
   public int field1744 = 0;
   @ObfuscatedName("q")
   @Export("transforms")
   public int[] transforms;
   @ObfuscatedName("i")
   @ObfuscatedGetter(
      intValue = 1819526357
   )
   @Export("transformVarbit")
   int transformVarbit = -1;
   @ObfuscatedName("ae")
   @ObfuscatedGetter(
      intValue = 1097710595
   )
   @Export("transformVarp")
   int transformVarp = -1;

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "(Lnd;I)V",
      garbageValue = "1040710174"
   )
   @Export("decode")
   void decode(Buffer var1) {
      while(true) {
         int var2 = var1.readUnsignedByte();
         if (var2 == 0) {
            return;
         }

         this.decodeNext(var1, var2);
      }
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "(Lnd;II)V",
      garbageValue = "1647125333"
   )
   @Export("decodeNext")
   void decodeNext(Buffer var1, int var2) {
      if (var2 == 1) {
         this.fontId = var1.method6583();
      } else if (var2 == 2) {
         this.textColor = var1.readMedium();
      } else if (var2 == 3) {
         this.field1735 = var1.method6583();
      } else if (var2 == 4) {
         this.field1737 = var1.method6583();
      } else if (var2 == 5) {
         this.field1736 = var1.method6583();
      } else if (var2 == 6) {
         this.field1738 = var1.method6583();
      } else if (var2 == 7) {
         this.field1739 = var1.readShort();
      } else if (var2 == 8) {
         this.field1742 = var1.readStringCp1252NullCircumfixed();
      } else if (var2 == 9) {
         this.field1731 = var1.readUnsignedShort();
      } else if (var2 == 10) {
         this.field1740 = var1.readShort();
      } else if (var2 == 11) {
         this.field1749 = 0;
      } else if (var2 == 12) {
         this.field1743 = var1.readUnsignedByte();
      } else if (var2 == 13) {
         this.field1744 = var1.readShort();
      } else if (var2 == 14) {
         this.field1749 = var1.readUnsignedShort();
      } else if (var2 == 17 || var2 == 18) {
         this.transformVarbit = var1.readUnsignedShort();
         if (this.transformVarbit == 65535) {
            this.transformVarbit = -1;
         }

         this.transformVarp = var1.readUnsignedShort();
         if (this.transformVarp == 65535) {
            this.transformVarp = -1;
         }

         int var3 = -1;
         if (var2 == 18) {
            var3 = var1.readUnsignedShort();
            if (var3 == 65535) {
               var3 = -1;
            }
         }

         int var4 = var1.readUnsignedByte();
         this.transforms = new int[var4 + 2];

         for(int var5 = 0; var5 <= var4; ++var5) {
            this.transforms[var5] = var1.readUnsignedShort();
            if (this.transforms[var5] == 65535) {
               this.transforms[var5] = -1;
            }
         }

         this.transforms[var4 + 1] = var3;
      }

   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      descriptor = "(I)Lev;",
      garbageValue = "-1025207361"
   )
   @Export("transform")
   public final HitSplatDefinition transform() {
      int var1 = -1;
      if (this.transformVarbit != -1) {
         var1 = Skeleton.getVarbit(this.transformVarbit);
      } else if (this.transformVarp != -1) {
         var1 = Varps.Varps_main[this.transformVarp];
      }

      int var2;
      if (var1 >= 0 && var1 < this.transforms.length - 1) {
         var2 = this.transforms[var1];
      } else {
         var2 = this.transforms[this.transforms.length - 1];
      }

      return var2 != -1 ? Projectile.method1966(var2) : null;
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      descriptor = "(II)Ljava/lang/String;",
      garbageValue = "-1622557292"
   )
   @Export("getString")
   public String getString(int var1) {
      String var2 = this.field1742;

      while(true) {
         int var3 = var2.indexOf("%1");
         if (var3 < 0) {
            return var2;
         }

         var2 = var2.substring(0, var3) + class80.intToString(var1, false) + var2.substring(var3 + 2);
      }
   }

   @ObfuscatedName("j")
   @ObfuscatedSignature(
      descriptor = "(I)Loh;",
      garbageValue = "-1594808435"
   )
   public SpritePixels method2916() {
      if (this.field1735 < 0) {
         return null;
      } else {
         SpritePixels var1 = (SpritePixels)HitSplatDefinition_cachedSprites.get((long)this.field1735);
         if (var1 != null) {
            return var1;
         } else {
            var1 = ClientPacket.SpriteBuffer_getSprite(field1734, this.field1735, 0);
            if (var1 != null) {
               HitSplatDefinition_cachedSprites.put(var1, (long)this.field1735);
            }

            return var1;
         }
      }
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      descriptor = "(I)Loh;",
      garbageValue = "1804785923"
   )
   public SpritePixels method2931() {
      if (this.field1736 < 0) {
         return null;
      } else {
         SpritePixels var1 = (SpritePixels)HitSplatDefinition_cachedSprites.get((long)this.field1736);
         if (var1 != null) {
            return var1;
         } else {
            var1 = ClientPacket.SpriteBuffer_getSprite(field1734, this.field1736, 0);
            if (var1 != null) {
               HitSplatDefinition_cachedSprites.put(var1, (long)this.field1736);
            }

            return var1;
         }
      }
   }

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      descriptor = "(I)Loh;",
      garbageValue = "-2095016456"
   )
   public SpritePixels method2951() {
      if (this.field1737 < 0) {
         return null;
      } else {
         SpritePixels var1 = (SpritePixels)HitSplatDefinition_cachedSprites.get((long)this.field1737);
         if (var1 != null) {
            return var1;
         } else {
            var1 = ClientPacket.SpriteBuffer_getSprite(field1734, this.field1737, 0);
            if (var1 != null) {
               HitSplatDefinition_cachedSprites.put(var1, (long)this.field1737);
            }

            return var1;
         }
      }
   }

   @ObfuscatedName("d")
   @ObfuscatedSignature(
      descriptor = "(B)Loh;",
      garbageValue = "-1"
   )
   public SpritePixels method2945() {
      if (this.field1738 < 0) {
         return null;
      } else {
         SpritePixels var1 = (SpritePixels)HitSplatDefinition_cachedSprites.get((long)this.field1738);
         if (var1 != null) {
            return var1;
         } else {
            var1 = ClientPacket.SpriteBuffer_getSprite(field1734, this.field1738, 0);
            if (var1 != null) {
               HitSplatDefinition_cachedSprites.put(var1, (long)this.field1738);
            }

            return var1;
         }
      }
   }

   @ObfuscatedName("s")
   @ObfuscatedSignature(
      descriptor = "(I)Lkt;",
      garbageValue = "2065112934"
   )
   @Export("getFont")
   public Font getFont() {
      if (this.fontId == -1) {
         return null;
      } else {
         Font var1 = (Font)HitSplatDefinition_cachedFonts.get((long)this.fontId);
         if (var1 != null) {
            return var1;
         } else {
            AbstractArchive var2 = field1734;
            AbstractArchive var3 = HitSplatDefinition_fontsArchive;
            int var4 = this.fontId;
            byte[] var5 = var2.takeFile(var4, 0);
            boolean var6;
            if (var5 == null) {
               var6 = false;
            } else {
               ItemLayer.SpriteBuffer_decode(var5);
               var6 = true;
            }

            Font var7;
            if (!var6) {
               var7 = null;
            } else {
               byte[] var8 = var3.takeFile(var4, 0);
               Font var9;
               if (var8 == null) {
                  var9 = null;
               } else {
                  Font var10 = new Font(var8, WorldMapDecoration.SpriteBuffer_xOffsets, Calendar.SpriteBuffer_yOffsets, class396.SpriteBuffer_spriteWidths, class302.SpriteBuffer_spriteHeights, MilliClock.SpriteBuffer_spritePalette, class396.SpriteBuffer_pixels);
                  MilliClock.method2587();
                  var9 = var10;
               }

               var7 = var9;
            }

            if (var7 != null) {
               HitSplatDefinition_cachedFonts.put(var7, (long)this.fontId);
            }

            return var7;
         }
      }
   }

   @ObfuscatedName("if")
   @ObfuscatedSignature(
      descriptor = "(Lce;IIII)V",
      garbageValue = "65535"
   )
   @Export("addPlayerToMenu")
   static final void addPlayerToMenu(Player var0, int var1, int var2, int var3) {
      if (class93.localPlayer != var0 && Client.menuOptionsCount < 400) {
         String var4;
         int var5;
         if (var0.skillLevel == 0) {
            String var6 = var0.actions[0] + var0.username + var0.actions[1];
            var5 = var0.combatLevel;
            int var7 = class93.localPlayer.combatLevel;
            int var8 = var7 - var5;
            String var9;
            if (var8 < -9) {
               var9 = class44.colorStartTag(16711680);
            } else if (var8 < -6) {
               var9 = class44.colorStartTag(16723968);
            } else if (var8 < -3) {
               var9 = class44.colorStartTag(16740352);
            } else if (var8 < 0) {
               var9 = class44.colorStartTag(16756736);
            } else if (var8 > 9) {
               var9 = class44.colorStartTag(65280);
            } else if (var8 > 6) {
               var9 = class44.colorStartTag(4259584);
            } else if (var8 > 3) {
               var9 = class44.colorStartTag(8453888);
            } else if (var8 > 0) {
               var9 = class44.colorStartTag(12648192);
            } else {
               var9 = class44.colorStartTag(16776960);
            }

            var4 = var6 + var9 + "  (level-" + var0.combatLevel + ")" + var0.actions[2];
         } else {
            var4 = var0.actions[0] + var0.username + var0.actions[1] + "  (skill-" + var0.skillLevel + ")" + var0.actions[2];
         }

         int var10;
         if (Client.isItemSelected == 1) {
            Occluder.insertMenuItemNoShift("Use", Client.selectedItemName + " -> " + class44.colorStartTag(16777215) + var4, 14, var1, var2, var3);
         } else if (Client.isSpellSelected) {
            if ((class4.selectedSpellFlags & 8) == 8) {
               Occluder.insertMenuItemNoShift(Client.selectedSpellActionName, Client.selectedSpellName + " -> " + class44.colorStartTag(16777215) + var4, 15, var1, var2, var3);
            }
         } else {
            for(var10 = 7; var10 >= 0; --var10) {
               if (Client.playerMenuActions[var10] != null) {
                  short var11 = 0;
                  if (Client.playerMenuActions[var10].equalsIgnoreCase("Attack")) {
                     if (AttackOption.AttackOption_hidden == Client.playerAttackOption) {
                        continue;
                     }

                     if (Client.playerAttackOption == AttackOption.AttackOption_alwaysRightClick || Client.playerAttackOption == AttackOption.AttackOption_dependsOnCombatLevels && var0.combatLevel > class93.localPlayer.combatLevel) {
                        var11 = 2000;
                     }

                     if (class93.localPlayer.team != 0 && var0.team != 0) {
                        if (var0.team == class93.localPlayer.team) {
                           var11 = 2000;
                        } else {
                           var11 = 0;
                        }
                     } else if (var0.isClanMember()) {
                        var11 = 2000;
                     }
                  } else if (Client.playerOptionsPriorities[var10]) {
                     var11 = 2000;
                  }

                  boolean var12 = false;
                  var5 = Client.playerMenuOpcodes[var10] + var11;
                  Occluder.insertMenuItemNoShift(Client.playerMenuActions[var10], class44.colorStartTag(16777215) + var4, var5, var1, var2, var3);
               }
            }
         }

         for(var10 = 0; var10 < Client.menuOptionsCount; ++var10) {
            if (Client.menuOpcodes[var10] == 23) {
               Client.menuTargets[var10] = class44.colorStartTag(16777215) + var4;
               break;
            }
         }
      }

   }

   @ObfuscatedName("lk")
   @ObfuscatedSignature(
      descriptor = "(Lio;I)Z",
      garbageValue = "2130552221"
   )
   @Export("isComponentHidden")
   static boolean isComponentHidden(Widget var0) {
      return var0.isHidden;
   }
}
