package osrs;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import net.runelite.mapping.Export;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("as")
public class class35 {
   @ObfuscatedName("ra")
   @ObfuscatedGetter(
      intValue = -1989236693
   )
   static int field264;
   @ObfuscatedName("w")
   @Export("operatingSystemName")
   public static String operatingSystemName;
   @ObfuscatedName("ga")
   @ObfuscatedSignature(
      descriptor = "[Loh;"
   )
   @Export("headIconPkSprites")
   static SpritePixels[] headIconPkSprites;
   @ObfuscatedName("v")
   ExecutorService field262 = Executors.newSingleThreadExecutor();
   @ObfuscatedName("n")
   Future field260;
   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "Lnd;"
   )
   final Buffer field261;
   @ObfuscatedName("y")
   @ObfuscatedSignature(
      descriptor = "Lai;"
   )
   final class31 field268;

   @ObfuscatedSignature(
      descriptor = "(Lnd;Lai;)V"
   )
   public class35(Buffer var1, class31 var2) {
      this.field261 = var1;
      this.field268 = var2;
      this.method335();
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(B)Z",
      garbageValue = "70"
   )
   public boolean method342() {
      return this.field260.isDone();
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "(B)V",
      garbageValue = "-47"
   )
   public void method332() {
      this.field262.shutdown();
      this.field262 = null;
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "(I)Lnd;",
      garbageValue = "-1675442246"
   )
   public Buffer method331() {
      try {
         return (Buffer)this.field260.get();
      } catch (Exception var2) {
         return null;
      }
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      descriptor = "(B)V",
      garbageValue = "4"
   )
   void method335() {
      this.field260 = this.field262.submit(new class29(this, this.field261, this.field268));
   }

   @ObfuscatedName("ip")
   @ObfuscatedSignature(
      descriptor = "(Lef;IIII)V",
      garbageValue = "-336055981"
   )
   @Export("addNpcToMenu")
   static final void addNpcToMenu(NPCComposition var0, int var1, int var2, int var3) {
      if (Client.menuOptionsCount < 400) {
         if (var0.transforms != null) {
            var0 = var0.transform();
         }

         if (var0 != null && var0.isInteractable && (!var0.isFollower || Client.followerIndex == var1)) {
            String var4 = var0.name;
            int var5;
            int var6;
            int var7;
            if (var0.combatLevel != 0) {
               var5 = var0.combatLevel;
               var6 = class93.localPlayer.combatLevel;
               var7 = var6 - var5;
               String var8;
               if (var7 < -9) {
                  var8 = class44.colorStartTag(16711680);
               } else if (var7 < -6) {
                  var8 = class44.colorStartTag(16723968);
               } else if (var7 < -3) {
                  var8 = class44.colorStartTag(16740352);
               } else if (var7 < 0) {
                  var8 = class44.colorStartTag(16756736);
               } else if (var7 > 9) {
                  var8 = class44.colorStartTag(65280);
               } else if (var7 > 6) {
                  var8 = class44.colorStartTag(4259584);
               } else if (var7 > 3) {
                  var8 = class44.colorStartTag(8453888);
               } else if (var7 > 0) {
                  var8 = class44.colorStartTag(12648192);
               } else {
                  var8 = class44.colorStartTag(16776960);
               }

               var4 = var4 + var8 + "  (level-" + var0.combatLevel + ")";
            }

            if (var0.isFollower && Client.followerOpsLowPriority) {
               Occluder.insertMenuItemNoShift("Examine", class44.colorStartTag(16776960) + var4, 1003, var1, var2, var3);
            }

            if (Client.isItemSelected == 1) {
               Occluder.insertMenuItemNoShift("Use", Client.selectedItemName + " -> " + class44.colorStartTag(16776960) + var4, 7, var1, var2, var3);
            } else if (Client.isSpellSelected) {
               if ((class4.selectedSpellFlags & 2) == 2) {
                  Occluder.insertMenuItemNoShift(Client.selectedSpellActionName, Client.selectedSpellName + " -> " + class44.colorStartTag(16776960) + var4, 8, var1, var2, var3);
               }
            } else {
               var7 = var0.isFollower && Client.followerOpsLowPriority ? 2000 : 0;
               String[] var10 = var0.actions;
               if (var10 != null) {
                  for(var5 = 4; var5 >= 0; --var5) {
                     if (var10[var5] != null && !var10[var5].equalsIgnoreCase("Attack")) {
                        var6 = 0;
                        if (var5 == 0) {
                           var6 = var7 + 9;
                        }

                        if (var5 == 1) {
                           var6 = var7 + 10;
                        }

                        if (var5 == 2) {
                           var6 = var7 + 11;
                        }

                        if (var5 == 3) {
                           var6 = var7 + 12;
                        }

                        if (var5 == 4) {
                           var6 = var7 + 13;
                        }

                        Occluder.insertMenuItemNoShift(var10[var5], class44.colorStartTag(16776960) + var4, var6, var1, var2, var3);
                     }
                  }
               }

               if (var10 != null) {
                  for(var5 = 4; var5 >= 0; --var5) {
                     if (var10[var5] != null && var10[var5].equalsIgnoreCase("Attack")) {
                        short var9 = 0;
                        if (Client.npcAttackOption != AttackOption.AttackOption_hidden) {
                           if (Client.npcAttackOption == AttackOption.AttackOption_alwaysRightClick || Client.npcAttackOption == AttackOption.AttackOption_dependsOnCombatLevels && var0.combatLevel > class93.localPlayer.combatLevel) {
                              var9 = 2000;
                           }

                           var6 = 0;
                           if (var5 == 0) {
                              var6 = var9 + 9;
                           }

                           if (var5 == 1) {
                              var6 = var9 + 10;
                           }

                           if (var5 == 2) {
                              var6 = var9 + 11;
                           }

                           if (var5 == 3) {
                              var6 = var9 + 12;
                           }

                           if (var5 == 4) {
                              var6 = var9 + 13;
                           }

                           Occluder.insertMenuItemNoShift(var10[var5], class44.colorStartTag(16776960) + var4, var6, var1, var2, var3);
                        }
                     }
                  }
               }

               if (!var0.isFollower || !Client.followerOpsLowPriority) {
                  Occluder.insertMenuItemNoShift("Examine", class44.colorStartTag(16776960) + var4, 1003, var1, var2, var3);
               }
            }
         }
      }

   }
}
