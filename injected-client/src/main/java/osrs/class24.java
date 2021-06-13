package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("q")
public class class24 {
   @ObfuscatedName("e")
   @ObfuscatedSignature(
      descriptor = "Lca;"
   )
   @Export("loginScreenRunesAnimation")
   static LoginScreenAnimation loginScreenRunesAnimation;
   @ObfuscatedName("gu")
   @Export("regionLandArchiveIds")
   static int[] regionLandArchiveIds;
   @ObfuscatedName("lp")
   @ObfuscatedGetter(
      intValue = -1944838207
   )
   @Export("menuHeight")
   static int menuHeight;

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(I)[Lhl;",
      garbageValue = "603080453"
   )
   @Export("ServerPacket_values")
   public static ServerPacket[] ServerPacket_values() {
      return new ServerPacket[]{ServerPacket.field2780, ServerPacket.field2751, ServerPacket.field2702, ServerPacket.field2703, ServerPacket.field2704, ServerPacket.field2705, ServerPacket.field2706, ServerPacket.field2798, ServerPacket.field2708, ServerPacket.field2785, ServerPacket.field2729, ServerPacket.field2711, ServerPacket.field2748, ServerPacket.field2713, ServerPacket.field2714, ServerPacket.field2760, ServerPacket.field2716, ServerPacket.field2717, ServerPacket.field2746, ServerPacket.field2719, ServerPacket.field2709, ServerPacket.field2721, ServerPacket.field2715, ServerPacket.field2723, ServerPacket.field2724, ServerPacket.field2725, ServerPacket.field2726, ServerPacket.field2775, ServerPacket.field2756, ServerPacket.field2722, ServerPacket.field2764, ServerPacket.field2731, ServerPacket.field2712, ServerPacket.field2795, ServerPacket.field2734, ServerPacket.field2735, ServerPacket.field2700, ServerPacket.field2737, ServerPacket.field2749, ServerPacket.field2739, ServerPacket.field2740, ServerPacket.field2741, ServerPacket.field2742, ServerPacket.field2743, ServerPacket.field2744, ServerPacket.field2745, ServerPacket.field2774, ServerPacket.field2732, ServerPacket.field2773, ServerPacket.field2738, ServerPacket.field2750, ServerPacket.field2747, ServerPacket.field2752, ServerPacket.field2753, ServerPacket.field2754, ServerPacket.field2755, ServerPacket.field2757, ServerPacket.field2789, ServerPacket.field2710, ServerPacket.field2759, ServerPacket.field2793, ServerPacket.field2761, ServerPacket.field2762, ServerPacket.field2763, ServerPacket.field2787, ServerPacket.field2765, ServerPacket.field2784, ServerPacket.field2767, ServerPacket.field2730, ServerPacket.field2769, ServerPacket.field2770, ServerPacket.field2771, ServerPacket.field2772, ServerPacket.field2768, ServerPacket.field2701, ServerPacket.field2718, ServerPacket.field2776, ServerPacket.field2777, ServerPacket.field2778, ServerPacket.field2779, ServerPacket.field2794, ServerPacket.field2781, ServerPacket.field2782, ServerPacket.field2783, ServerPacket.field2727, ServerPacket.field2728, ServerPacket.field2786, ServerPacket.field2707, ServerPacket.field2788, ServerPacket.field2766, ServerPacket.field2790, ServerPacket.field2791, ServerPacket.field2792, ServerPacket.field2720, ServerPacket.field2736};
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      descriptor = "(Ljava/lang/CharSequence;IZB)I",
      garbageValue = "-105"
   )
   public static int method260(CharSequence var0, int var1, boolean var2) {
      if (var1 >= 2 && var1 <= 36) {
         boolean var3 = false;
         boolean var4 = false;
         int var5 = 0;
         int var6 = var0.length();

         for(int var7 = 0; var7 < var6; ++var7) {
            char var8 = var0.charAt(var7);
            if (var7 == 0) {
               if (var8 == '-') {
                  var3 = true;
                  continue;
               }

               if (var8 == '+') {
                  continue;
               }
            }

            int var9;
            if (var8 >= '0' && var8 <= '9') {
               var9 = var8 - 48;
            } else if (var8 >= 'A' && var8 <= 'Z') {
               var9 = var8 - 55;
            } else {
               if (var8 < 'a' || var8 > 'z') {
                  throw new NumberFormatException();
               }

               var9 = var8 - 87;
            }

            if (var9 >= var1) {
               throw new NumberFormatException();
            }

            if (var3) {
               var9 = -var9;
            }

            int var10 = var9 + var5 * var1;
            if (var10 / var1 != var5) {
               throw new NumberFormatException();
            }

            var5 = var10;
            var4 = true;
         }

         if (!var4) {
            throw new NumberFormatException();
         } else {
            return var5;
         }
      } else {
         throw new IllegalArgumentException("" + var1);
      }
   }

   @ObfuscatedName("s")
   @ObfuscatedSignature(
      descriptor = "(S)[Lop;",
      garbageValue = "-29644"
   )
   static IndexedSprite[] method262() {
      IndexedSprite[] var0 = new IndexedSprite[class396.SpriteBuffer_spriteCount];

      for(int var1 = 0; var1 < class396.SpriteBuffer_spriteCount; ++var1) {
         IndexedSprite var2 = var0[var1] = new IndexedSprite();
         var2.width = class396.SpriteBuffer_spriteWidth;
         var2.height = UserComparator2.SpriteBuffer_spriteHeight;
         var2.xOffset = WorldMapDecoration.SpriteBuffer_xOffsets[var1];
         var2.yOffset = Calendar.SpriteBuffer_yOffsets[var1];
         var2.subWidth = class396.SpriteBuffer_spriteWidths[var1];
         var2.subHeight = class302.SpriteBuffer_spriteHeights[var1];
         var2.palette = MilliClock.SpriteBuffer_spritePalette;
         var2.pixels = class396.SpriteBuffer_pixels[var1];
      }

      MilliClock.method2587();
      return var0;
   }

   @ObfuscatedName("c")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "-355325275"
   )
   public static void method259() {
      ObjectComposition.ObjectDefinition_cached.clear();
      ObjectComposition.ObjectDefinition_cachedModelData.clear();
      ObjectComposition.ObjectDefinition_cachedEntities.clear();
      ObjectComposition.ObjectDefinition_cachedModels.clear();
   }

   @ObfuscatedName("ar")
   @ObfuscatedSignature(
      descriptor = "(ILcf;ZB)I",
      garbageValue = "5"
   )
   static int method261(int var0, Script var1, boolean var2) {
      if (var0 == 5000) {
         Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = Client.publicChatMode;
         return 1;
      } else {
         PacketBufferNode var3;
         if (var0 == 5001) {
            class44.Interpreter_intStackSize -= 3;
            Client.publicChatMode = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
            ScriptEvent.privateChatMode = class124.method2498(Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1]);
            if (ScriptEvent.privateChatMode == null) {
               ScriptEvent.privateChatMode = PrivateChatMode.field3887;
            }

            Client.tradeChatMode = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 2];
            var3 = class21.getPacketBufferNode(ClientPacket.field2611, Client.packetWriter.isaacCipher);
            var3.packetBuffer.writeByte(Client.publicChatMode);
            var3.packetBuffer.writeByte(ScriptEvent.privateChatMode.field3890);
            var3.packetBuffer.writeByte(Client.tradeChatMode);
            Client.packetWriter.addNode(var3);
            return 1;
         } else {
            int var4;
            String var5;
            int var6;
            if (var0 == 5002) {
               var5 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
               class44.Interpreter_intStackSize -= 2;
               var4 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
               var6 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1];
               var3 = class21.getPacketBufferNode(ClientPacket.field2596, Client.packetWriter.isaacCipher);
               var3.packetBuffer.writeByte(Tiles.stringCp1252NullTerminatedByteSize(var5) + 2);
               var3.packetBuffer.writeStringCp1252NullTerminated(var5);
               var3.packetBuffer.writeByte(var4 - 1);
               var3.packetBuffer.writeByte(var6);
               Client.packetWriter.addNode(var3);
               return 1;
            } else {
               int var7;
               Message var8;
               if (var0 == 5003) {
                  class44.Interpreter_intStackSize -= 2;
                  var7 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
                  var4 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1];
                  var8 = ClanChannelMember.Messages_getByChannelAndID(var7, var4);
                  if (var8 != null) {
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var8.count;
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var8.cycle;
                     Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var8.sender != null ? var8.sender : "";
                     Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var8.prefix != null ? var8.prefix : "";
                     Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var8.text != null ? var8.text : "";
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var8.isFromFriend() ? 1 : (var8.isFromIgnored() ? 2 : 0);
                  } else {
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                     Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                     Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                     Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                  }

                  return 1;
               } else {
                  Message var9;
                  if (var0 == 5004) {
                     var7 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                     var9 = class124.Messages_getMessage(var7);
                     if (var9 != null) {
                        Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var9.type;
                        Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var9.cycle;
                        Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var9.sender != null ? var9.sender : "";
                        Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var9.prefix != null ? var9.prefix : "";
                        Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var9.text != null ? var9.text : "";
                        Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var9.isFromFriend() ? 1 : (var9.isFromIgnored() ? 2 : 0);
                     } else {
                        Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
                        Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                        Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                        Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                        Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                        Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                     }

                     return 1;
                  } else if (var0 == 5005) {
                     if (ScriptEvent.privateChatMode == null) {
                        Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
                     } else {
                        Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = ScriptEvent.privateChatMode.field3890;
                     }

                     return 1;
                  } else {
                     PacketBufferNode var10;
                     if (var0 == 5008) {
                        var5 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
                        var4 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                        var10 = WorldMapSectionType.method3596(var4, var5, class378.clientLanguage, -1);
                        Client.packetWriter.addNode(var10);
                        return 1;
                     } else if (var0 == 5009) {
                        Interpreter.Interpreter_stringStackSize -= 2;
                        var5 = Interpreter.Interpreter_stringStack[Interpreter.Interpreter_stringStackSize];
                        String var11 = Interpreter.Interpreter_stringStack[Interpreter.Interpreter_stringStackSize + 1];
                        var10 = class21.getPacketBufferNode(ClientPacket.field2614, Client.packetWriter.isaacCipher);
                        var10.packetBuffer.writeShort(0);
                        int var12 = var10.packetBuffer.offset;
                        var10.packetBuffer.writeStringCp1252NullTerminated(var5);
                        ParamComposition.method2878(var10.packetBuffer, var11);
                        var10.packetBuffer.writeLengthShort(var10.packetBuffer.offset - var12);
                        Client.packetWriter.addNode(var10);
                        return 1;
                     } else if (var0 == 5010) {
                        var5 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
                        class44.Interpreter_intStackSize -= 2;
                        var4 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
                        var6 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1];
                        var3 = WorldMapSectionType.method3596(var4, var5, class378.clientLanguage, var6);
                        Client.packetWriter.addNode(var3);
                        return 1;
                     } else if (var0 != 5015) {
                        if (var0 == 5016) {
                           Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = Client.tradeChatMode;
                           return 1;
                        } else if (var0 == 5017) {
                           var7 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                           Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = class23.Messages_getHistorySize(var7);
                           return 1;
                        } else if (var0 == 5018) {
                           var7 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                           Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = class17.Messages_getLastChatID(var7);
                           return 1;
                        } else if (var0 == 5019) {
                           var7 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                           Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = Timer.method5595(var7);
                           return 1;
                        } else if (var0 == 5020) {
                           var5 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
                           class302.doCheat(var5);
                           return 1;
                        } else if (var0 == 5021) {
                           Client.field863 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize].toLowerCase().trim();
                           return 1;
                        } else if (var0 == 5022) {
                           Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = Client.field863;
                           return 1;
                        } else if (var0 == 5023) {
                           var5 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
                           System.out.println(var5);
                           return 1;
                        } else if (var0 == 5024) {
                           --class44.Interpreter_intStackSize;
                           return 1;
                        } else if (var0 == 5025) {
                           ++class44.Interpreter_intStackSize;
                           return 1;
                        } else if (var0 == 5030) {
                           class44.Interpreter_intStackSize -= 2;
                           var7 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
                           var4 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1];
                           var8 = ClanChannelMember.Messages_getByChannelAndID(var7, var4);
                           if (var8 != null) {
                              Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var8.count;
                              Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var8.cycle;
                              Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var8.sender != null ? var8.sender : "";
                              Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var8.prefix != null ? var8.prefix : "";
                              Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var8.text != null ? var8.text : "";
                              Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var8.isFromFriend() ? 1 : (var8.isFromIgnored() ? 2 : 0);
                              Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                              Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                           } else {
                              Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
                              Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                              Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                              Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                              Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                              Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                              Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                              Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                           }

                           return 1;
                        } else if (var0 == 5031) {
                           var7 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                           var9 = class124.Messages_getMessage(var7);
                           if (var9 != null) {
                              Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var9.type;
                              Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var9.cycle;
                              Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var9.sender != null ? var9.sender : "";
                              Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var9.prefix != null ? var9.prefix : "";
                              Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var9.text != null ? var9.text : "";
                              Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var9.isFromFriend() ? 1 : (var9.isFromIgnored() ? 2 : 0);
                              Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                              Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                           } else {
                              Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
                              Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                              Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                              Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                              Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                              Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                              Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                              Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                           }

                           return 1;
                        } else {
                           return 2;
                        }
                     } else {
                        if (class93.localPlayer != null && class93.localPlayer.username != null) {
                           var5 = class93.localPlayer.username.getName();
                        } else {
                           var5 = "";
                        }

                        Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var5;
                        return 1;
                     }
                  }
               }
            }
         }
      }
   }

   @ObfuscatedName("jl")
   @ObfuscatedSignature(
      descriptor = "(Lio;III)V",
      garbageValue = "-1951037780"
   )
   @Export("alignWidgetPosition")
   static void alignWidgetPosition(Widget var0, int var1, int var2) {
      if (var0.xAlignment == 0) {
         var0.x = var0.rawX;
      } else if (var0.xAlignment == 1) {
         var0.x = var0.rawX + (var1 - var0.width) / 2;
      } else if (var0.xAlignment == 2) {
         var0.x = var1 - var0.width - var0.rawX;
      } else if (var0.xAlignment == 3) {
         var0.x = var0.rawX * var1 >> 14;
      } else if (var0.xAlignment == 4) {
         var0.x = (var1 - var0.width) / 2 + (var0.rawX * var1 >> 14);
      } else {
         var0.x = var1 - var0.width - (var0.rawX * var1 >> 14);
      }

      if (var0.yAlignment == 0) {
         var0.y = var0.rawY;
      } else if (var0.yAlignment == 1) {
         var0.y = (var2 - var0.height) / 2 + var0.rawY;
      } else if (var0.yAlignment == 2) {
         var0.y = var2 - var0.height - var0.rawY;
      } else if (var0.yAlignment == 3) {
         var0.y = var2 * var0.rawY >> 14;
      } else if (var0.yAlignment == 4) {
         var0.y = (var2 - var0.height) / 2 + (var2 * var0.rawY >> 14);
      } else {
         var0.y = var2 - var0.height - (var2 * var0.rawY >> 14);
      }

   }
}
