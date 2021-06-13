package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("dg")
@Implements("UserComparator9")
public class UserComparator9 extends AbstractUserComparator {
   @ObfuscatedName("ff")
   @ObfuscatedSignature(
      descriptor = "Leu;"
   )
   @Export("socketTask")
   static Task socketTask;
   @ObfuscatedName("v")
   @Export("reversed")
   final boolean reversed;

   public UserComparator9(boolean var1) {
      this.reversed = var1;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(Llv;Llv;B)I",
      garbageValue = "-69"
   )
   @Export("compareBuddy")
   int compareBuddy(Buddy var1, Buddy var2) {
      if (Client.worldId == var1.world && var2.world == Client.worldId) {
         return this.reversed ? var1.getUsername().compareToTyped(var2.getUsername()) : var2.getUsername().compareToTyped(var1.getUsername());
      } else {
         return this.compareUser(var1, var2);
      }
   }

   public int compare(Object var1, Object var2) {
      return this.compareBuddy((Buddy)var1, (Buddy)var2);
   }

   @ObfuscatedName("w")
   @ObfuscatedSignature(
      descriptor = "(ILcf;ZI)I",
      garbageValue = "910297623"
   )
   static int method2466(int var0, Script var1, boolean var2) {
      String var3;
      if (var0 == 3100) {
         var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
         World.addGameMessage(0, "", var3);
         return 1;
      } else if (var0 == 3101) {
         class44.Interpreter_intStackSize -= 2;
         class32.performPlayerAnimation(class93.localPlayer, Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize], Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1]);
         return 1;
      } else if (var0 == 3103) {
         if (!Interpreter.field986) {
            Interpreter.field973 = true;
         }

         return 1;
      } else {
         int var4;
         PacketBufferNode var5;
         if (var0 == 3104) {
            var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
            var4 = 0;
            if (class20.isNumber(var3)) {
               var4 = class82.method1908(var3);
            }

            var5 = class21.getPacketBufferNode(ClientPacket.field2582, Client.packetWriter.isaacCipher);
            var5.packetBuffer.writeInt(var4);
            Client.packetWriter.addNode(var5);
            return 1;
         } else if (var0 == 3105) {
            var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
            var5 = class21.getPacketBufferNode(ClientPacket.field2595, Client.packetWriter.isaacCipher);
            var5.packetBuffer.writeByte(var3.length() + 1);
            var5.packetBuffer.writeStringCp1252NullTerminated(var3);
            Client.packetWriter.addNode(var5);
            return 1;
         } else if (var0 == 3106) {
            var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
            var5 = class21.getPacketBufferNode(ClientPacket.field2653, Client.packetWriter.isaacCipher);
            var5.packetBuffer.writeByte(var3.length() + 1);
            var5.packetBuffer.writeStringCp1252NullTerminated(var3);
            Client.packetWriter.addNode(var5);
            return 1;
         } else {
            String var6;
            int var7;
            if (var0 == 3107) {
               var7 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
               var6 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
               Actor.method2192(var7, var6);
               return 1;
            } else if (var0 == 3108) {
               class44.Interpreter_intStackSize -= 3;
               var7 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
               var4 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1];
               int var14 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 2];
               Widget var12 = Frames.getWidget(var14);
               VarcInt.clickWidget(var12, var7, var4);
               return 1;
            } else if (var0 == 3109) {
               class44.Interpreter_intStackSize -= 2;
               var7 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
               var4 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1];
               Widget var13 = var2 ? class13.scriptDotWidget : Interpreter.scriptActiveWidget;
               VarcInt.clickWidget(var13, var7, var4);
               return 1;
            } else if (var0 == 3110) {
               Client.mouseCam = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
               return 1;
            } else if (var0 == 3111) {
               Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = ObjectComposition.clientPreferences.roofsHidden ? 1 : 0;
               return 1;
            } else if (var0 == 3112) {
               ObjectComposition.clientPreferences.roofsHidden = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
               TileItem.savePreferences();
               return 1;
            } else {
               boolean var8;
               if (var0 == 3113) {
                  var3 = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
                  var8 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                  Players.openURL(var3, var8, false);
                  return 1;
               } else if (var0 == 3115) {
                  var7 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                  var5 = class21.getPacketBufferNode(ClientPacket.field2647, Client.packetWriter.isaacCipher);
                  var5.packetBuffer.writeShort(var7);
                  Client.packetWriter.addNode(var5);
                  return 1;
               } else if (var0 == 3116) {
                  var7 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                  Interpreter.Interpreter_stringStackSize -= 2;
                  var6 = Interpreter.Interpreter_stringStack[Interpreter.Interpreter_stringStackSize];
                  String var10 = Interpreter.Interpreter_stringStack[Interpreter.Interpreter_stringStackSize + 1];
                  if (var6.length() > 500) {
                     return 1;
                  } else if (var10.length() > 500) {
                     return 1;
                  } else {
                     PacketBufferNode var11 = class21.getPacketBufferNode(ClientPacket.field2588, Client.packetWriter.isaacCipher);
                     var11.packetBuffer.writeShort(1 + Tiles.stringCp1252NullTerminatedByteSize(var6) + Tiles.stringCp1252NullTerminatedByteSize(var10));
                     var11.packetBuffer.writeStringCp1252NullTerminated(var6);
                     var11.packetBuffer.writeStringCp1252NullTerminated(var10);
                     var11.packetBuffer.writeByte(var7);
                     Client.packetWriter.addNode(var11);
                     return 1;
                  }
               } else if (var0 == 3117) {
                  Client.shiftClickDrop = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                  return 1;
               } else if (var0 == 3118) {
                  Client.showMouseOverText = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                  return 1;
               } else if (var0 == 3119) {
                  Client.renderSelf = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                  return 1;
               } else if (var0 == 3120) {
                  if (Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1) {
                     Client.drawPlayerNames |= 1;
                  } else {
                     Client.drawPlayerNames &= -2;
                  }

                  return 1;
               } else if (var0 == 3121) {
                  if (Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1) {
                     Client.drawPlayerNames |= 2;
                  } else {
                     Client.drawPlayerNames &= -3;
                  }

                  return 1;
               } else if (var0 == 3122) {
                  if (Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1) {
                     Client.drawPlayerNames |= 4;
                  } else {
                     Client.drawPlayerNames &= -5;
                  }

                  return 1;
               } else if (var0 == 3123) {
                  if (Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1) {
                     Client.drawPlayerNames |= 8;
                  } else {
                     Client.drawPlayerNames &= -9;
                  }

                  return 1;
               } else if (var0 == 3124) {
                  Client.drawPlayerNames = 0;
                  return 1;
               } else if (var0 == 3125) {
                  Client.showMouseCross = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                  return 1;
               } else if (var0 == 3126) {
                  Client.showLoadingMessages = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                  return 1;
               } else if (var0 == 3127) {
                  UserComparator4.setTapToDrop(Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1);
                  return 1;
               } else if (var0 == 3128) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = class8.getTapToDrop() ? 1 : 0;
                  return 1;
               } else if (var0 == 3129) {
                  class44.Interpreter_intStackSize -= 2;
                  Client.oculusOrbNormalSpeed = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
                  Client.oculusOrbSlowedSpeed = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1];
                  return 1;
               } else if (var0 == 3130) {
                  class44.Interpreter_intStackSize -= 2;
                  return 1;
               } else if (var0 == 3131) {
                  --class44.Interpreter_intStackSize;
                  return 1;
               } else if (var0 == 3132) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = class32.canvasWidth;
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = ReflectionCheck.canvasHeight;
                  return 1;
               } else if (var0 == 3133) {
                  --class44.Interpreter_intStackSize;
                  return 1;
               } else if (var0 == 3134) {
                  return 1;
               } else if (var0 == 3135) {
                  class44.Interpreter_intStackSize -= 2;
                  return 1;
               } else if (var0 == 3136) {
                  Client.field803 = 3;
                  Client.field805 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                  return 1;
               } else if (var0 == 3137) {
                  Client.field803 = 2;
                  Client.field805 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                  return 1;
               } else if (var0 == 3138) {
                  Client.field803 = 0;
                  return 1;
               } else if (var0 == 3139) {
                  Client.field803 = 1;
                  return 1;
               } else if (var0 == 3140) {
                  Client.field803 = 3;
                  Client.field805 = var2 ? class13.scriptDotWidget.id * 1969535585 * -1722455647 : Interpreter.scriptActiveWidget.id * 1969535585 * -1722455647;
                  return 1;
               } else if (var0 == 3141) {
                  var8 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                  ObjectComposition.clientPreferences.hideUsername = var8;
                  TileItem.savePreferences();
                  return 1;
               } else if (var0 == 3142) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = ObjectComposition.clientPreferences.hideUsername ? 1 : 0;
                  return 1;
               } else if (var0 == 3143) {
                  var8 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                  Client.Login_isUsernameRemembered = var8;
                  if (!var8) {
                     ObjectComposition.clientPreferences.rememberedUsername = "";
                     TileItem.savePreferences();
                  }

                  return 1;
               } else if (var0 == 3144) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = Client.Login_isUsernameRemembered ? 1 : 0;
                  return 1;
               } else if (var0 == 3145) {
                  return 1;
               } else if (var0 == 3146) {
                  var8 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize] == 1;
                  if (var8 == ObjectComposition.clientPreferences.titleMusicDisabled) {
                     ObjectComposition.clientPreferences.titleMusicDisabled = !var8;
                     TileItem.savePreferences();
                  }

                  return 1;
               } else if (var0 == 3147) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = ObjectComposition.clientPreferences.titleMusicDisabled ? 0 : 1;
                  return 1;
               } else if (var0 == 3148) {
                  return 1;
               } else if (var0 == 3149) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                  return 1;
               } else if (var0 == 3150) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                  return 1;
               } else if (var0 == 3151) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                  return 1;
               } else if (var0 == 3152) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                  return 1;
               } else if (var0 == 3153) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = Login.Login_loadingPercent;
                  return 1;
               } else if (var0 == 3154) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = class43.method440();
                  return 1;
               } else if (var0 == 3155) {
                  --Interpreter.Interpreter_stringStackSize;
                  return 1;
               } else if (var0 == 3156) {
                  return 1;
               } else if (var0 == 3157) {
                  class44.Interpreter_intStackSize -= 2;
                  return 1;
               } else if (var0 == 3158) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                  return 1;
               } else if (var0 == 3159) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                  return 1;
               } else if (var0 == 3160) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                  return 1;
               } else if (var0 == 3161) {
                  --class44.Interpreter_intStackSize;
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                  return 1;
               } else if (var0 == 3162) {
                  --class44.Interpreter_intStackSize;
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                  return 1;
               } else if (var0 == 3163) {
                  --Interpreter.Interpreter_stringStackSize;
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                  return 1;
               } else if (var0 == 3164) {
                  --class44.Interpreter_intStackSize;
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                  return 1;
               } else if (var0 == 3165) {
                  --class44.Interpreter_intStackSize;
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                  return 1;
               } else if (var0 == 3166) {
                  class44.Interpreter_intStackSize -= 2;
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                  return 1;
               } else if (var0 == 3167) {
                  class44.Interpreter_intStackSize -= 2;
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                  return 1;
               } else if (var0 == 3168) {
                  class44.Interpreter_intStackSize -= 2;
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                  Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = "";
                  return 1;
               } else if (var0 == 3169) {
                  return 1;
               } else if (var0 == 3170) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                  return 1;
               } else if (var0 == 3171) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                  return 1;
               } else if (var0 == 3172) {
                  --class44.Interpreter_intStackSize;
                  return 1;
               } else if (var0 == 3173) {
                  --class44.Interpreter_intStackSize;
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                  return 1;
               } else if (var0 == 3174) {
                  --class44.Interpreter_intStackSize;
                  return 1;
               } else if (var0 == 3175) {
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 0;
                  return 1;
               } else if (var0 == 3176) {
                  return 1;
               } else if (var0 == 3177) {
                  return 1;
               } else if (var0 == 3178) {
                  --Interpreter.Interpreter_stringStackSize;
                  return 1;
               } else if (var0 == 3179) {
                  return 1;
               } else if (var0 == 3180) {
                  --Interpreter.Interpreter_stringStackSize;
                  return 1;
               } else if (var0 == 3181) {
                  var7 = 100 - Math.min(Math.max(Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize], 0), 100);
                  InterfaceParent.method2082((double)(0.5F + (float)var7 / 200.0F));
                  return 1;
               } else if (var0 == 3182) {
                  float var9 = 200.0F * ((float)ObjectComposition.clientPreferences.field1337 - 0.5F);
                  Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = 100 - Math.round(var9);
                  return 1;
               } else if (var0 != 3183 && var0 != 3184) {
                  return 2;
               } else {
                  --class44.Interpreter_intStackSize;
                  return 1;
               }
            }
         }
      }
   }

   @ObfuscatedName("am")
   @ObfuscatedSignature(
      descriptor = "(II)I",
      garbageValue = "70996791"
   )
   static int method2467(int var0) {
      return (int)((Math.log((double)var0) / Interpreter.field989 - 7.0D) * 256.0D);
   }
}
