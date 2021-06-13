package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ae")
public class class26 extends class14 {
   @ObfuscatedName("o")
   @ObfuscatedSignature(
      descriptor = "Lbh;"
   )
   @Export("pcmPlayerProvider")
   static PlayerProvider pcmPlayerProvider;
   @ObfuscatedName("k")
   @ObfuscatedSignature(
      descriptor = "Ll;"
   )
   static ClanSettings field220;
   @ObfuscatedName("ac")
   @ObfuscatedSignature(
      descriptor = "Loe;"
   )
   @Export("rasterProvider")
   public static AbstractRasterProvider rasterProvider;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      longValue = 1552499431731106411L
   )
   long field217;
   @ObfuscatedName("n")
   String field218;
   @ObfuscatedName("f")
   @ObfuscatedGetter(
      intValue = -1135955153
   )
   int field219;
   @ObfuscatedSignature(
      descriptor = "Lf;"
   )
   final class2 this$0;

   @ObfuscatedSignature(
      descriptor = "(Lf;)V"
   )
   class26(class2 var1) {
      this.this$0 = var1;
      this.field217 = -1L;
      this.field218 = null;
      this.field219 = 0;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(Lnd;I)V",
      garbageValue = "-1281352827"
   )
   void vmethod276(Buffer var1) {
      if (var1.readUnsignedByte() != 255) {
         --var1.offset;
         this.field217 = var1.readLong();
      }

      this.field218 = var1.readStringCp1252NullTerminatedOrNull();
      this.field219 = var1.readUnsignedShort();
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "(Ll;B)V",
      garbageValue = "29"
   )
   void vmethod281(ClanSettings var1) {
      var1.method103(this.field217, this.field218, this.field219);
   }

   @ObfuscatedName("e")
   @ObfuscatedSignature(
      descriptor = "(III)I",
      garbageValue = "-1494621592"
   )
   static final int method272(int var0, int var1) {
      if (var0 == -2) {
         return 12345678;
      } else if (var0 == -1) {
         if (var1 < 2) {
            var1 = 2;
         } else if (var1 > 126) {
            var1 = 126;
         }

         return var1;
      } else {
         var1 = (var0 & 127) * var1 / 128;
         if (var1 < 2) {
            var1 = 2;
         } else if (var1 > 126) {
            var1 = 126;
         }

         return (var0 & 'ﾀ') + var1;
      }
   }

   @ObfuscatedName("a")
   @ObfuscatedSignature(
      descriptor = "(ILcf;ZI)I",
      garbageValue = "1261558074"
   )
   static int method274(int var0, Script var1, boolean var2) {
      Widget var3;
      if (var0 != 1927 && var0 != 2927) {
         int var7;
         if (var0 == 1928) {
            var3 = var2 ? class13.scriptDotWidget : Interpreter.scriptActiveWidget;
            var7 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
            if (var7 >= 1 && var7 <= 10) {
               class376.widgetDefaultMenuAction(var7, var3.id, var3.childIndex, var3.itemId, "");
               return 1;
            } else {
               throw new RuntimeException();
            }
         } else if (var0 == 2928) {
            class44.Interpreter_intStackSize -= 3;
            int var5 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
            var7 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1];
            int var6 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 2];
            if (var6 >= 1 && var6 <= 10) {
               class376.widgetDefaultMenuAction(var6, var5, var7, Frames.getWidget(var5).itemId, "");
               return 1;
            } else {
               throw new RuntimeException();
            }
         } else {
            return 2;
         }
      } else if (Interpreter.field987 >= 10) {
         throw new RuntimeException();
      } else {
         if (var0 >= 2000) {
            var3 = Frames.getWidget(Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize]);
         } else {
            var3 = var2 ? class13.scriptDotWidget : Interpreter.scriptActiveWidget;
         }

         if (var3.onResize == null) {
            return 0;
         } else {
            ScriptEvent var4 = new ScriptEvent();
            var4.widget = var3;
            var4.args = var3.onResize;
            var4.field1180 = Interpreter.field987 + 1;
            Client.scriptEvents.addFirst(var4);
            return 1;
         }
      }
   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      descriptor = "(ILcf;ZB)I",
      garbageValue = "55"
   )
   static int method273(int var0, Script var1, boolean var2) {
      if (var0 == 3600) {
         if (NetSocket.friendSystem.field941 == 0) {
            Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -2;
         } else if (NetSocket.friendSystem.field941 == 1) {
            Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
         } else {
            Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = NetSocket.friendSystem.friendsList.getSize();
         }

         return 1;
      } else {
         int var3;
         if (var0 == 3601) {
            var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
            if (NetSocket.friendSystem.method1689() && var3 >= 0 && var3 < NetSocket.friendSystem.friendsList.getSize()) {
               Friend var6 = (Friend)NetSocket.friendSystem.friendsList.get(var3);
               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var6.getName();
               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var6.getPreviousName();
            } else {
               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
               Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
            }

            return 1;
         } else if (var0 == 3602) {
            var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
            if (NetSocket.friendSystem.method1689() && var3 >= 0 && var3 < NetSocket.friendSystem.friendsList.getSize()) {
               Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = ((Buddy)NetSocket.friendSystem.friendsList.get(var3)).world;
            } else {
               Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
            }

            return 1;
         } else if (var0 == 3603) {
            var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
            if (NetSocket.friendSystem.method1689() && var3 >= 0 && var3 < NetSocket.friendSystem.friendsList.getSize()) {
               Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = ((Buddy)NetSocket.friendSystem.friendsList.get(var3)).rank;
            } else {
               Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
            }

            return 1;
         } else {
            String var4;
            if (var0 == 3604) {
               var4 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
               int var8 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
               VerticalAlignment.method2796(var4, var8);
               return 1;
            } else if (var0 == 3605) {
               var4 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
               NetSocket.friendSystem.addFriend(var4);
               return 1;
            } else if (var0 == 3606) {
               var4 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
               NetSocket.friendSystem.removeFriend(var4);
               return 1;
            } else if (var0 == 3607) {
               var4 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
               NetSocket.friendSystem.addIgnore(var4);
               return 1;
            } else if (var0 == 3608) {
               var4 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
               NetSocket.friendSystem.removeIgnore(var4);
               return 1;
            } else if (var0 == 3609) {
               var4 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
               var4 = class15.method189(var4);
               Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = NetSocket.friendSystem.isFriended(new Username(var4, WorldMapSection0.loginType), false) ? 1 : 0;
               return 1;
            } else if (var0 == 3611) {
               if (WorldMapRegion.friendsChat != null) {
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = WorldMapRegion.friendsChat.name;
               } else {
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
               }

               return 1;
            } else if (var0 == 3612) {
               if (WorldMapRegion.friendsChat != null) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = WorldMapRegion.friendsChat.getSize();
               } else {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
               }

               return 1;
            } else if (var0 == 3613) {
               var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
               if (WorldMapRegion.friendsChat != null && var3 < WorldMapRegion.friendsChat.getSize()) {
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = WorldMapRegion.friendsChat.get(var3).getUsername().getName();
               } else {
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
               }

               return 1;
            } else if (var0 == 3614) {
               var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
               if (WorldMapRegion.friendsChat != null && var3 < WorldMapRegion.friendsChat.getSize()) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = ((Buddy)WorldMapRegion.friendsChat.get(var3)).getWorld();
               } else {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
               }

               return 1;
            } else if (var0 == 3615) {
               var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
               if (WorldMapRegion.friendsChat != null && var3 < WorldMapRegion.friendsChat.getSize()) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = ((Buddy)WorldMapRegion.friendsChat.get(var3)).rank;
               } else {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
               }

               return 1;
            } else if (var0 == 3616) {
               Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = WorldMapRegion.friendsChat != null ? WorldMapRegion.friendsChat.minKick : 0;
               return 1;
            } else if (var0 == 3617) {
               var4 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
               class105.clanKickUser(var4);
               return 1;
            } else if (var0 == 3618) {
               Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = WorldMapRegion.friendsChat != null ? WorldMapRegion.friendsChat.rank * -759593825 * -78473377 : 0;
               return 1;
            } else if (var0 == 3619) {
               var4 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
               NPCComposition.Clan_joinChat(var4);
               return 1;
            } else if (var0 == 3620) {
               MouseHandler.Clan_leaveChat();
               return 1;
            } else if (var0 == 3621) {
               if (!NetSocket.friendSystem.method1689()) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
               } else {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = NetSocket.friendSystem.ignoreList.getSize();
               }

               return 1;
            } else if (var0 == 3622) {
               var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
               if (NetSocket.friendSystem.method1689() && var3 >= 0 && var3 < NetSocket.friendSystem.ignoreList.getSize()) {
                  Ignored var7 = (Ignored)NetSocket.friendSystem.ignoreList.get(var3);
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var7.getName();
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var7.getPreviousName();
               } else {
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
               }

               return 1;
            } else if (var0 == 3623) {
               var4 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
               var4 = class15.method189(var4);
               Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = NetSocket.friendSystem.isIgnored(new Username(var4, WorldMapSection0.loginType)) ? 1 : 0;
               return 1;
            } else if (var0 == 3624) {
               var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
               if (WorldMapRegion.friendsChat != null && var3 < WorldMapRegion.friendsChat.getSize() && WorldMapRegion.friendsChat.get(var3).getUsername().equals(class93.localPlayer.username)) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 1;
               } else {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
               }

               return 1;
            } else if (var0 == 3625) {
               if (WorldMapRegion.friendsChat != null && WorldMapRegion.friendsChat.owner != null) {
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = WorldMapRegion.friendsChat.owner;
               } else {
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
               }

               return 1;
            } else if (var0 == 3626) {
               var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
               if (WorldMapRegion.friendsChat != null && var3 < WorldMapRegion.friendsChat.getSize() && ((ClanMate)WorldMapRegion.friendsChat.get(var3)).isFriend()) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 1;
               } else {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
               }

               return 1;
            } else if (var0 != 3627) {
               if (var0 == 3628) {
                  NetSocket.friendSystem.friendsList.removeComparator();
                  return 1;
               } else {
                  boolean var5;
                  if (var0 == 3629) {
                     var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                     NetSocket.friendSystem.friendsList.addComparator(new UserComparator1(var5));
                     return 1;
                  } else if (var0 == 3630) {
                     var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                     NetSocket.friendSystem.friendsList.addComparator(new UserComparator2(var5));
                     return 1;
                  } else if (var0 == 3631) {
                     var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                     NetSocket.friendSystem.friendsList.addComparator(new UserComparator3(var5));
                     return 1;
                  } else if (var0 == 3632) {
                     var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                     NetSocket.friendSystem.friendsList.addComparator(new UserComparator4(var5));
                     return 1;
                  } else if (var0 == 3633) {
                     var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                     NetSocket.friendSystem.friendsList.addComparator(new UserComparator5(var5));
                     return 1;
                  } else if (var0 == 3634) {
                     var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                     NetSocket.friendSystem.friendsList.addComparator(new UserComparator6(var5));
                     return 1;
                  } else if (var0 == 3635) {
                     var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                     NetSocket.friendSystem.friendsList.addComparator(new UserComparator7(var5));
                     return 1;
                  } else if (var0 == 3636) {
                     var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                     NetSocket.friendSystem.friendsList.addComparator(new UserComparator8(var5));
                     return 1;
                  } else if (var0 == 3637) {
                     var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                     NetSocket.friendSystem.friendsList.addComparator(new UserComparator9(var5));
                     return 1;
                  } else if (var0 == 3638) {
                     var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                     NetSocket.friendSystem.friendsList.addComparator(new UserComparator10(var5));
                     return 1;
                  } else if (var0 == 3639) {
                     NetSocket.friendSystem.friendsList.sort();
                     return 1;
                  } else if (var0 == 3640) {
                     NetSocket.friendSystem.ignoreList.removeComparator();
                     return 1;
                  } else if (var0 == 3641) {
                     var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                     NetSocket.friendSystem.ignoreList.addComparator(new UserComparator1(var5));
                     return 1;
                  } else if (var0 == 3642) {
                     var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                     NetSocket.friendSystem.ignoreList.addComparator(new UserComparator2(var5));
                     return 1;
                  } else if (var0 == 3643) {
                     NetSocket.friendSystem.ignoreList.sort();
                     return 1;
                  } else if (var0 == 3644) {
                     if (WorldMapRegion.friendsChat != null) {
                        WorldMapRegion.friendsChat.removeComparator();
                     }

                     return 1;
                  } else if (var0 == 3645) {
                     var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                     if (WorldMapRegion.friendsChat != null) {
                        WorldMapRegion.friendsChat.addComparator(new UserComparator1(var5));
                     }

                     return 1;
                  } else if (var0 == 3646) {
                     var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                     if (WorldMapRegion.friendsChat != null) {
                        WorldMapRegion.friendsChat.addComparator(new UserComparator2(var5));
                     }

                     return 1;
                  } else if (var0 == 3647) {
                     var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                     if (WorldMapRegion.friendsChat != null) {
                        WorldMapRegion.friendsChat.addComparator(new UserComparator3(var5));
                     }

                     return 1;
                  } else if (var0 == 3648) {
                     var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                     if (WorldMapRegion.friendsChat != null) {
                        WorldMapRegion.friendsChat.addComparator(new UserComparator4(var5));
                     }

                     return 1;
                  } else if (var0 == 3649) {
                     var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                     if (WorldMapRegion.friendsChat != null) {
                        WorldMapRegion.friendsChat.addComparator(new UserComparator5(var5));
                     }

                     return 1;
                  } else if (var0 == 3650) {
                     var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                     if (WorldMapRegion.friendsChat != null) {
                        WorldMapRegion.friendsChat.addComparator(new UserComparator6(var5));
                     }

                     return 1;
                  } else if (var0 == 3651) {
                     var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                     if (WorldMapRegion.friendsChat != null) {
                        WorldMapRegion.friendsChat.addComparator(new UserComparator7(var5));
                     }

                     return 1;
                  } else if (var0 == 3652) {
                     var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                     if (WorldMapRegion.friendsChat != null) {
                        WorldMapRegion.friendsChat.addComparator(new UserComparator8(var5));
                     }

                     return 1;
                  } else if (var0 == 3653) {
                     var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                     if (WorldMapRegion.friendsChat != null) {
                        WorldMapRegion.friendsChat.addComparator(new UserComparator9(var5));
                     }

                     return 1;
                  } else if (var0 == 3654) {
                     var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                     if (WorldMapRegion.friendsChat != null) {
                        WorldMapRegion.friendsChat.addComparator(new UserComparator10(var5));
                     }

                     return 1;
                  } else if (var0 == 3655) {
                     if (WorldMapRegion.friendsChat != null) {
                        WorldMapRegion.friendsChat.sort();
                     }

                     return 1;
                  } else if (var0 == 3656) {
                     var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                     NetSocket.friendSystem.friendsList.addComparator(new BuddyRankComparator(var5));
                     return 1;
                  } else if (var0 == 3657) {
                     var5 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                     if (WorldMapRegion.friendsChat != null) {
                        WorldMapRegion.friendsChat.addComparator(new BuddyRankComparator(var5));
                     }

                     return 1;
                  } else {
                     return 2;
                  }
               }
            } else {
               var3 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
               if (WorldMapRegion.friendsChat != null && var3 < WorldMapRegion.friendsChat.getSize() && ((ClanMate)WorldMapRegion.friendsChat.get(var3)).isIgnored()) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 1;
               } else {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
               }

               return 1;
            }
         }
      }
   }
}
