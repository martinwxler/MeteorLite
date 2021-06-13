package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("s")
@Implements("ClanChannelMember")
public class ClanChannelMember {
   @ObfuscatedName("mq")
   @ObfuscatedGetter(
      intValue = 81340013
   )
   @Export("selectedItemSlot")
   static int selectedItemSlot;
   @ObfuscatedName("v")
   @Export("rank")
   public byte rank;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = 186545257
   )
   @Export("world")
   public int world;
   @ObfuscatedName("f")
   @Export("name")
   public String name;

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(Lid;I)V",
      garbageValue = "-2073984636"
   )
   public static void method88(Huffman var0) {
      class249.huffman = var0;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(IIII)I",
      garbageValue = "-1338731866"
   )
   public static int method86(int var0, int var1, int var2) {
      var2 &= 3;
      if (var2 == 0) {
         return var1;
      } else if (var2 == 1) {
         return 7 - var0;
      } else {
         return var2 == 2 ? 7 - var1 : var0;
      }
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "(IIB)Lbi;",
      garbageValue = "-22"
   )
   @Export("Messages_getByChannelAndID")
   static Message Messages_getByChannelAndID(int var0, int var1) {
      ChatChannel var2 = (ChatChannel)Messages.Messages_channels.get(var0);
      return var2.getMessage(var1);
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      descriptor = "(I)Z",
      garbageValue = "-1385459044"
   )
   @Export("isKeyDown")
   public static final boolean isKeyDown() {
      KeyHandler var0 = KeyHandler.KeyHandler_instance;
      synchronized(KeyHandler.KeyHandler_instance) {
         if (KeyHandler.field293 == KeyHandler.field295) {
            return false;
         } else {
            ItemComposition.field1859 = KeyHandler.field289[KeyHandler.field293];
            class249.field3116 = KeyHandler.field286[KeyHandler.field293];
            KeyHandler.field293 = KeyHandler.field293 + 1 & 127;
            return true;
         }
      }
   }

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      descriptor = "(ZB)V",
      garbageValue = "74"
   )
   @Export("Login_promptCredentials")
   static void Login_promptCredentials(boolean var0) {
      Login.Login_response1 = "";
      Login.Login_response2 = "Enter your username/email & password.";
      Login.Login_response3 = "";
      Login.loginIndex = 2;
      if (var0) {
         Login.Login_password = "";
      }

      if (Login.Login_username == null || Login.Login_username.length() <= 0) {
         if (ObjectComposition.clientPreferences.rememberedUsername != null) {
            Login.Login_username = ObjectComposition.clientPreferences.rememberedUsername;
            Client.Login_isUsernameRemembered = true;
         } else {
            Client.Login_isUsernameRemembered = false;
         }
      }

      if (Client.Login_isUsernameRemembered && Login.Login_username != null && Login.Login_username.length() > 0) {
         Login.currentLoginField = 1;
      } else {
         Login.currentLoginField = 0;
      }

   }

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      descriptor = "(IIIIIILgt;Lfz;B)V",
      garbageValue = "11"
   )
   static final void method89(int var0, int var1, int var2, int var3, int var4, int var5, Scene var6, CollisionMap var7) {
      if (!Client.isLowDetail || (Tiles.Tiles_renderFlags[0][var1][var2] & 2) != 0 || (Tiles.Tiles_renderFlags[var0][var1][var2] & 16) == 0) {
         if (var0 < Tiles.Tiles_minPlane) {
            Tiles.Tiles_minPlane = var0;
         }

         ObjectComposition var8 = class23.getObjectDefinition(var3);
         int var9;
         int var10;
         if (var4 != 1 && var4 != 3) {
            var9 = var8.sizeX;
            var10 = var8.sizeY;
         } else {
            var9 = var8.sizeY;
            var10 = var8.sizeX;
         }

         int var11;
         int var12;
         if (var9 + var1 <= 104) {
            var11 = (var9 >> 1) + var1;
            var12 = (var9 + 1 >> 1) + var1;
         } else {
            var11 = var1;
            var12 = var1 + 1;
         }

         int var13;
         int var14;
         if (var10 + var2 <= 104) {
            var13 = (var10 >> 1) + var2;
            var14 = var2 + (var10 + 1 >> 1);
         } else {
            var13 = var2;
            var14 = var2 + 1;
         }

         int[][] var15 = Tiles.Tiles_heights[var0];
         int var16 = var15[var12][var14] + var15[var11][var14] + var15[var12][var13] + var15[var11][var13] >> 2;
         int var17 = (var1 << 7) + (var9 << 6);
         int var18 = (var2 << 7) + (var10 << 6);
         long var19 = DevicePcmPlayerProvider.calculateTag(var1, var2, 2, var8.int1 == 0, var3);
         int var21 = var5 + (var4 << 6);
         if (var8.int3 == 1) {
            var21 += 256;
         }

         if (var8.hasSound()) {
            class268.method4851(var0, var1, var2, var8, var4);
         }

         Object var22;
         if (var5 == 22) {
            if (!Client.isLowDetail || var8.int1 != 0 || var8.interactType == 1 || var8.boolean2) {
               if (var8.animationId == -1 && var8.transforms == null) {
                  var22 = var8.getEntity(22, var4, var15, var17, var16, var18);
               } else {
                  var22 = new DynamicObject(var3, 22, var4, var0, var1, var2, var8.animationId, var8.field1801, (Renderable)null);
               }

               var6.newFloorDecoration(var0, var1, var2, var16, (Renderable)var22, var19, var21);
               if (var8.interactType == 1 && var7 != null) {
                  var7.setBlockedByFloorDec(var1, var2);
               }
            }
         } else {
            int var23;
            int var25;
            if (var5 != 10 && var5 != 11) {
               int[] var31;
               if (var5 >= 12) {
                  if (var8.animationId == -1 && var8.transforms == null) {
                     var22 = var8.getEntity(var5, var4, var15, var17, var16, var18);
                  } else {
                     var22 = new DynamicObject(var3, var5, var4, var0, var1, var2, var8.animationId, var8.field1801, (Renderable)null);
                  }

                  var6.method4007(var0, var1, var2, var16, 1, 1, (Renderable)var22, 0, var19, var21);
                  if (var5 >= 12 && var5 <= 17 && var5 != 13 && var0 > 0) {
                     var31 = UrlRequester.field1419[var0][var1];
                     var31[var2] |= 2340;
                  }

                  if (var8.interactType != 0 && var7 != null) {
                     var7.addGameObject(var1, var2, var9, var10, var8.boolean1);
                  }
               } else if (var5 == 0) {
                  if (var8.animationId == -1 && var8.transforms == null) {
                     var22 = var8.getEntity(0, var4, var15, var17, var16, var18);
                  } else {
                     var22 = new DynamicObject(var3, 0, var4, var0, var1, var2, var8.animationId, var8.field1801, (Renderable)null);
                  }

                  var6.newBoundaryObject(var0, var1, var2, var16, (Renderable)var22, (Renderable)null, Tiles.field1117[var4], 0, var19, var21);
                  if (var4 == 0) {
                     if (var8.clipped) {
                        class372.field4123[var0][var1][var2] = 50;
                        class372.field4123[var0][var1][var2 + 1] = 50;
                     }

                     if (var8.modelClipped) {
                        var31 = UrlRequester.field1419[var0][var1];
                        var31[var2] |= 585;
                     }
                  } else if (var4 == 1) {
                     if (var8.clipped) {
                        class372.field4123[var0][var1][var2 + 1] = 50;
                        class372.field4123[var0][var1 + 1][var2 + 1] = 50;
                     }

                     if (var8.modelClipped) {
                        var31 = UrlRequester.field1419[var0][var1];
                        var31[1 + var2] |= 1170;
                     }
                  } else if (var4 == 2) {
                     if (var8.clipped) {
                        class372.field4123[var0][var1 + 1][var2] = 50;
                        class372.field4123[var0][var1 + 1][var2 + 1] = 50;
                     }

                     if (var8.modelClipped) {
                        var31 = UrlRequester.field1419[var0][var1 + 1];
                        var31[var2] |= 585;
                     }
                  } else if (var4 == 3) {
                     if (var8.clipped) {
                        class372.field4123[var0][var1][var2] = 50;
                        class372.field4123[var0][var1 + 1][var2] = 50;
                     }

                     if (var8.modelClipped) {
                        var31 = UrlRequester.field1419[var0][var1];
                        var31[var2] |= 1170;
                     }
                  }

                  if (var8.interactType != 0 && var7 != null) {
                     var7.method3164(var1, var2, var5, var4, var8.boolean1);
                  }

                  if (var8.int2 != 16) {
                     var6.method3967(var0, var1, var2, var8.int2);
                  }
               } else if (var5 == 1) {
                  if (var8.animationId == -1 && var8.transforms == null) {
                     var22 = var8.getEntity(1, var4, var15, var17, var16, var18);
                  } else {
                     var22 = new DynamicObject(var3, 1, var4, var0, var1, var2, var8.animationId, var8.field1801, (Renderable)null);
                  }

                  var6.newBoundaryObject(var0, var1, var2, var16, (Renderable)var22, (Renderable)null, Tiles.field1125[var4], 0, var19, var21);
                  if (var8.clipped) {
                     if (var4 == 0) {
                        class372.field4123[var0][var1][var2 + 1] = 50;
                     } else if (var4 == 1) {
                        class372.field4123[var0][var1 + 1][var2 + 1] = 50;
                     } else if (var4 == 2) {
                        class372.field4123[var0][var1 + 1][var2] = 50;
                     } else if (var4 == 3) {
                        class372.field4123[var0][var1][var2] = 50;
                     }
                  }

                  if (var8.interactType != 0 && var7 != null) {
                     var7.method3164(var1, var2, var5, var4, var8.boolean1);
                  }
               } else if (var5 == 2) {
                  var25 = var4 + 1 & 3;
                  Object var26;
                  Object var27;
                  if (var8.animationId == -1 && var8.transforms == null) {
                     var27 = var8.getEntity(2, var4 + 4, var15, var17, var16, var18);
                     var26 = var8.getEntity(2, var25, var15, var17, var16, var18);
                  } else {
                     var27 = new DynamicObject(var3, 2, var4 + 4, var0, var1, var2, var8.animationId, var8.field1801, (Renderable)null);
                     var26 = new DynamicObject(var3, 2, var25, var0, var1, var2, var8.animationId, var8.field1801, (Renderable)null);
                  }

                  var6.newBoundaryObject(var0, var1, var2, var16, (Renderable)var27, (Renderable)var26, Tiles.field1117[var4], Tiles.field1117[var25], var19, var21);
                  if (var8.modelClipped) {
                     if (var4 == 0) {
                        var31 = UrlRequester.field1419[var0][var1];
                        var31[var2] |= 585;
                        var31 = UrlRequester.field1419[var0][var1];
                        var31[1 + var2] |= 1170;
                     } else if (var4 == 1) {
                        var31 = UrlRequester.field1419[var0][var1];
                        var31[var2 + 1] |= 1170;
                        var31 = UrlRequester.field1419[var0][var1 + 1];
                        var31[var2] |= 585;
                     } else if (var4 == 2) {
                        var31 = UrlRequester.field1419[var0][var1 + 1];
                        var31[var2] |= 585;
                        var31 = UrlRequester.field1419[var0][var1];
                        var31[var2] |= 1170;
                     } else if (var4 == 3) {
                        var31 = UrlRequester.field1419[var0][var1];
                        var31[var2] |= 1170;
                        var31 = UrlRequester.field1419[var0][var1];
                        var31[var2] |= 585;
                     }
                  }

                  if (var8.interactType != 0 && var7 != null) {
                     var7.method3164(var1, var2, var5, var4, var8.boolean1);
                  }

                  if (var8.int2 != 16) {
                     var6.method3967(var0, var1, var2, var8.int2);
                  }
               } else if (var5 == 3) {
                  if (var8.animationId == -1 && var8.transforms == null) {
                     var22 = var8.getEntity(3, var4, var15, var17, var16, var18);
                  } else {
                     var22 = new DynamicObject(var3, 3, var4, var0, var1, var2, var8.animationId, var8.field1801, (Renderable)null);
                  }

                  var6.newBoundaryObject(var0, var1, var2, var16, (Renderable)var22, (Renderable)null, Tiles.field1125[var4], 0, var19, var21);
                  if (var8.clipped) {
                     if (var4 == 0) {
                        class372.field4123[var0][var1][var2 + 1] = 50;
                     } else if (var4 == 1) {
                        class372.field4123[var0][var1 + 1][var2 + 1] = 50;
                     } else if (var4 == 2) {
                        class372.field4123[var0][var1 + 1][var2] = 50;
                     } else if (var4 == 3) {
                        class372.field4123[var0][var1][var2] = 50;
                     }
                  }

                  if (var8.interactType != 0 && var7 != null) {
                     var7.method3164(var1, var2, var5, var4, var8.boolean1);
                  }
               } else if (var5 == 9) {
                  if (var8.animationId == -1 && var8.transforms == null) {
                     var22 = var8.getEntity(var5, var4, var15, var17, var16, var18);
                  } else {
                     var22 = new DynamicObject(var3, var5, var4, var0, var1, var2, var8.animationId, var8.field1801, (Renderable)null);
                  }

                  var6.method4007(var0, var1, var2, var16, 1, 1, (Renderable)var22, 0, var19, var21);
                  if (var8.interactType != 0 && var7 != null) {
                     var7.addGameObject(var1, var2, var9, var10, var8.boolean1);
                  }

                  if (var8.int2 != 16) {
                     var6.method3967(var0, var1, var2, var8.int2);
                  }
               } else if (var5 == 4) {
                  if (var8.animationId == -1 && var8.transforms == null) {
                     var22 = var8.getEntity(4, var4, var15, var17, var16, var18);
                  } else {
                     var22 = new DynamicObject(var3, 4, var4, var0, var1, var2, var8.animationId, var8.field1801, (Renderable)null);
                  }

                  var6.newWallDecoration(var0, var1, var2, var16, (Renderable)var22, (Renderable)null, Tiles.field1117[var4], 0, 0, 0, var19, var21);
               } else {
                  Object var28;
                  long var32;
                  if (var5 == 5) {
                     var25 = 16;
                     var32 = var6.getBoundaryObjectTag(var0, var1, var2);
                     if (0L != var32) {
                        var25 = class23.getObjectDefinition(class93.Entity_unpackID(var32)).int2;
                     }

                     if (var8.animationId == -1 && var8.transforms == null) {
                        var28 = var8.getEntity(4, var4, var15, var17, var16, var18);
                     } else {
                        var28 = new DynamicObject(var3, 4, var4, var0, var1, var2, var8.animationId, var8.field1801, (Renderable)null);
                     }

                     var6.newWallDecoration(var0, var1, var2, var16, (Renderable)var28, (Renderable)null, Tiles.field1117[var4], 0, var25 * Tiles.field1126[var4], var25 * Tiles.field1123[var4], var19, var21);
                  } else if (var5 == 6) {
                     var25 = 8;
                     var32 = var6.getBoundaryObjectTag(var0, var1, var2);
                     if (var32 != 0L) {
                        var25 = class23.getObjectDefinition(class93.Entity_unpackID(var32)).int2 / 2;
                     }

                     if (var8.animationId == -1 && var8.transforms == null) {
                        var28 = var8.getEntity(4, var4 + 4, var15, var17, var16, var18);
                     } else {
                        var28 = new DynamicObject(var3, 4, var4 + 4, var0, var1, var2, var8.animationId, var8.field1801, (Renderable)null);
                     }

                     var6.newWallDecoration(var0, var1, var2, var16, (Renderable)var28, (Renderable)null, 256, var4, var25 * Tiles.field1128[var4], var25 * Tiles.field1129[var4], var19, var21);
                  } else if (var5 == 7) {
                     var23 = var4 + 2 & 3;
                     if (var8.animationId == -1 && var8.transforms == null) {
                        var22 = var8.getEntity(4, var23 + 4, var15, var17, var16, var18);
                     } else {
                        var22 = new DynamicObject(var3, 4, var23 + 4, var0, var1, var2, var8.animationId, var8.field1801, (Renderable)null);
                     }

                     var6.newWallDecoration(var0, var1, var2, var16, (Renderable)var22, (Renderable)null, 256, var23, 0, 0, var19, var21);
                  } else if (var5 == 8) {
                     var25 = 8;
                     var32 = var6.getBoundaryObjectTag(var0, var1, var2);
                     if (0L != var32) {
                        var25 = class23.getObjectDefinition(class93.Entity_unpackID(var32)).int2 / 2;
                     }

                     int var29 = var4 + 2 & 3;
                     Object var30;
                     if (var8.animationId == -1 && var8.transforms == null) {
                        var28 = var8.getEntity(4, var4 + 4, var15, var17, var16, var18);
                        var30 = var8.getEntity(4, var29 + 4, var15, var17, var16, var18);
                     } else {
                        var28 = new DynamicObject(var3, 4, var4 + 4, var0, var1, var2, var8.animationId, var8.field1801, (Renderable)null);
                        var30 = new DynamicObject(var3, 4, var29 + 4, var0, var1, var2, var8.animationId, var8.field1801, (Renderable)null);
                     }

                     var6.newWallDecoration(var0, var1, var2, var16, (Renderable)var28, (Renderable)var30, 256, var4, var25 * Tiles.field1128[var4], var25 * Tiles.field1129[var4], var19, var21);
                  }
               }
            } else {
               if (var8.animationId == -1 && var8.transforms == null) {
                  var22 = var8.getEntity(10, var4, var15, var17, var16, var18);
               } else {
                  var22 = new DynamicObject(var3, 10, var4, var0, var1, var2, var8.animationId, var8.field1801, (Renderable)null);
               }

               if (var22 != null && var6.method4007(var0, var1, var2, var16, var9, var10, (Renderable)var22, var5 == 11 ? 256 : 0, var19, var21) && var8.clipped) {
                  var23 = 15;
                  if (var22 instanceof Model) {
                     var23 = ((Model)var22).method4180() / 4;
                     if (var23 > 30) {
                        var23 = 30;
                     }
                  }

                  for(int var24 = 0; var24 <= var9; ++var24) {
                     for(var25 = 0; var25 <= var10; ++var25) {
                        if (var23 > class372.field4123[var0][var24 + var1][var25 + var2]) {
                           class372.field4123[var0][var24 + var1][var25 + var2] = (byte)var23;
                        }
                     }
                  }
               }

               if (var8.interactType != 0 && var7 != null) {
                  var7.addGameObject(var1, var2, var9, var10, var8.boolean1);
               }
            }
         }
      }

   }

   @ObfuscatedName("kz")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "813346347"
   )
   static final void method85() {
      for(int var0 = 0; var0 < Players.Players_count; ++var0) {
         Player var1 = Client.players[Players.Players_indices[var0]];
         var1.clearIsInFriendsChat();
      }

   }
}
