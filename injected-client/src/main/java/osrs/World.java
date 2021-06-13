package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("bg")
@Implements("World")
public class World {
   @ObfuscatedName("j")
   @ObfuscatedGetter(
      intValue = 1267509105
   )
   @Export("World_count")
   static int World_count = 0;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = -1119546685
   )
   @Export("World_listCount")
   static int World_listCount = 0;
   @ObfuscatedName("b")
   @Export("World_sortOption2")
   static int[] World_sortOption2 = new int[]{1, 1, 1, 1};
   @ObfuscatedName("d")
   @Export("World_sortOption1")
   static int[] World_sortOption1 = new int[]{0, 1, 2, 3};
   @ObfuscatedName("fu")
   @ObfuscatedSignature(
      descriptor = "Lmu;"
   )
   @Export("WorldMapElement_fonts")
   static Fonts WorldMapElement_fonts;
   @ObfuscatedName("jz")
   @ObfuscatedSignature(
      descriptor = "Lgh;"
   )
   @Export("textureProvider")
   static TextureProvider textureProvider;
   @ObfuscatedName("li")
   @ObfuscatedSignature(
      descriptor = "Lio;"
   )
   @Export("mousedOverWidgetIf1")
   static Widget mousedOverWidgetIf1;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = -1428153959
   )
   @Export("id")
   int id;
   @ObfuscatedName("l")
   @ObfuscatedGetter(
      intValue = 264436593
   )
   @Export("properties")
   int properties;
   @ObfuscatedName("o")
   @ObfuscatedGetter(
      intValue = -508707417
   )
   @Export("population")
   int population;
   @ObfuscatedName("c")
   @Export("host")
   String host;
   @ObfuscatedName("e")
   @Export("activity")
   String activity;
   @ObfuscatedName("g")
   @ObfuscatedGetter(
      intValue = -1484367493
   )
   @Export("location")
   int location;
   @ObfuscatedName("a")
   @ObfuscatedGetter(
      intValue = 2005307775
   )
   @Export("index")
   int index;

   @ObfuscatedName("d")
   @ObfuscatedSignature(
      descriptor = "(I)Z",
      garbageValue = "2086816927"
   )
   @Export("isMembersOnly")
   boolean isMembersOnly() {
      return (1 & this.properties) != 0;
   }

   @ObfuscatedName("s")
   @ObfuscatedSignature(
      descriptor = "(I)Z",
      garbageValue = "-1823181912"
   )
   boolean method1638() {
      return (2 & this.properties) != 0;
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      descriptor = "(I)Z",
      garbageValue = "-1133674448"
   )
   @Export("isPvp")
   boolean isPvp() {
      return (4 & this.properties) != 0;
   }

   @ObfuscatedName("l")
   @ObfuscatedSignature(
      descriptor = "(I)Z",
      garbageValue = "-1123465891"
   )
   boolean method1640() {
      return (8 & this.properties) != 0;
   }

   @ObfuscatedName("o")
   @ObfuscatedSignature(
      descriptor = "(I)Z",
      garbageValue = "504803099"
   )
   @Export("isDeadman")
   boolean isDeadman() {
      return (536870912 & this.properties) != 0;
   }

   @ObfuscatedName("c")
   @ObfuscatedSignature(
      descriptor = "(S)Z",
      garbageValue = "255"
   )
   @Export("isBeta")
   boolean isBeta() {
      return (33554432 & this.properties) != 0;
   }

   @ObfuscatedName("e")
   @ObfuscatedSignature(
      descriptor = "(B)Z",
      garbageValue = "12"
   )
   boolean method1643() {
      return (1073741824 & this.properties) != 0;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(ILjava/lang/String;Ljava/lang/String;I)V",
      garbageValue = "-577854303"
   )
   @Export("addGameMessage")
   static void addGameMessage(int var0, String var1, String var2) {
      class5.addChatMessage(var0, var1, var2, (String)null);
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "(Lcz;III)V",
      garbageValue = "704254621"
   )
   @Export("runScript")
   static void runScript(ScriptEvent var0, int var1, int var2) {
      Object[] var3 = var0.args;
      Script var4;
      int var5;
      if (NPC.isWorldMapEvent(var0.type)) {
         class69.worldMapEvent = (WorldMapEvent)var3[0];
         WorldMapElement var6 = class6.WorldMapElement_get(class69.worldMapEvent.mapElement);
         var4 = class43.getWorldMapScript(var0.type, var6.objectId, var6.category);
      } else {
         var5 = (Integer)var3[0];
         var4 = ApproximateRouteStrategy.getScript(var5);
      }

      if (var4 != null) {
         class44.Interpreter_intStackSize = 0;
         Interpreter.Interpreter_stringStackSize = 0;
         var5 = -1;
         int[] var27 = var4.opcodes;
         int[] var7 = var4.intOperands;
         byte var8 = -1;
         Interpreter.Interpreter_frameDepth = 0;
         Interpreter.field973 = false;
         boolean var9 = false;
         int var10 = 0;

         try {
            int var11;
            try {
               class368.Interpreter_intLocals = new int[var4.localIntCount];
               int var12 = 0;
               Interpreter.Interpreter_stringLocals = new String[var4.localStringCount];
               int var28 = 0;

               int var14;
               String var15;
               for(var11 = 1; var11 < var3.length; ++var11) {
                  if (var3[var11] instanceof Integer) {
                     var14 = (Integer)var3[var11];
                     if (var14 == -2147483647) {
                        var14 = var0.mouseX;
                     }

                     if (var14 == -2147483646) {
                        var14 = var0.mouseY;
                     }

                     if (var14 == -2147483645) {
                        var14 = var0.widget != null ? var0.widget.id * 1969535585 * -1722455647 : -1;
                     }

                     if (var14 == -2147483644) {
                        var14 = var0.opIndex;
                     }

                     if (var14 == -2147483643) {
                        var14 = var0.widget != null ? var0.widget.childIndex * 41180389 * 1926103277 : -1;
                     }

                     if (var14 == -2147483642) {
                        var14 = var0.dragTarget != null ? var0.dragTarget.id * 1969535585 * -1722455647 : -1;
                     }

                     if (var14 == -2147483641) {
                        var14 = var0.dragTarget != null ? var0.dragTarget.childIndex * 41180389 * 1926103277 : -1;
                     }

                     if (var14 == -2147483640) {
                        var14 = var0.keyTyped;
                     }

                     if (var14 == -2147483639) {
                        var14 = var0.keyPressed;
                     }

                     class368.Interpreter_intLocals[var12++] = var14;
                  } else if (var3[var11] instanceof String) {
                     var15 = (String)var3[var11];
                     if (var15.equals("event_opbase")) {
                        var15 = var0.targetName;
                     }

                     Interpreter.Interpreter_stringLocals[var28++] = var15;
                  }
               }

               Interpreter.field987 = var0.field1180;

               while(true) {
                  ++var10;
                  if (var10 > var1) {
                     throw new RuntimeException();
                  }

                  ++var5;
                  int var16 = var27[var5];
                  if (var16 >= 100) {
                     boolean var31;
                     if (var4.intOperands[var5] == 1) {
                        var31 = true;
                     } else {
                        var31 = false;
                     }

                     var14 = IgnoreList.method5628(var16, var4, var31);
                     switch(var14) {
                     case 0:
                        return;
                     case 1:
                     default:
                        break;
                     case 2:
                        throw new IllegalStateException();
                     }
                  } else if (var16 == 0) {
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var7[var5];
                  } else if (var16 == 1) {
                     var11 = var7[var5];
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = Varps.Varps_main[var11];
                  } else if (var16 == 2) {
                     var11 = var7[var5];
                     Varps.Varps_main[var11] = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                     ApproximateRouteStrategy.changeGameOptions(var11);
                  } else if (var16 == 3) {
                     Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var4.stringOperands[var5];
                  } else if (var16 == 6) {
                     var5 += var7[var5];
                  } else if (var16 == 7) {
                     class44.Interpreter_intStackSize -= 2;
                     if (Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize] != Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1]) {
                        var5 += var7[var5];
                     }
                  } else if (var16 == 8) {
                     class44.Interpreter_intStackSize -= 2;
                     if (Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize] == Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1]) {
                        var5 += var7[var5];
                     }
                  } else if (var16 == 9) {
                     class44.Interpreter_intStackSize -= 2;
                     if (Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize] < Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1]) {
                        var5 += var7[var5];
                     }
                  } else if (var16 == 10) {
                     class44.Interpreter_intStackSize -= 2;
                     if (Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize] > Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1]) {
                        var5 += var7[var5];
                     }
                  } else if (var16 == 21) {
                     if (Interpreter.Interpreter_frameDepth == 0) {
                        return;
                     }

                     ScriptFrame var30 = Interpreter.Interpreter_frames[--Interpreter.Interpreter_frameDepth];
                     var4 = var30.script;
                     var27 = var4.opcodes;
                     var7 = var4.intOperands;
                     var5 = var30.pc;
                     class368.Interpreter_intLocals = var30.intLocals;
                     Interpreter.Interpreter_stringLocals = var30.stringLocals;
                  } else if (var16 == 25) {
                     var11 = var7[var5];
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = Skeleton.getVarbit(var11);
                  } else if (var16 == 27) {
                     var11 = var7[var5];
                     Login.method1951(var11, Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize]);
                  } else if (var16 == 31) {
                     class44.Interpreter_intStackSize -= 2;
                     if (Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize] <= Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1]) {
                        var5 += var7[var5];
                     }
                  } else if (var16 == 32) {
                     class44.Interpreter_intStackSize -= 2;
                     if (Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize] >= Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1]) {
                        var5 += var7[var5];
                     }
                  } else if (var16 == 33) {
                     Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = class368.Interpreter_intLocals[var7[var5]];
                  } else if (var16 == 34) {
                     class368.Interpreter_intLocals[var7[var5]] = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                  } else if (var16 == 35) {
                     Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = Interpreter.Interpreter_stringLocals[var7[var5]];
                  } else if (var16 == 36) {
                     Interpreter.Interpreter_stringLocals[var7[var5]] = Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize];
                  } else if (var16 == 37) {
                     var11 = var7[var5];
                     Interpreter.Interpreter_stringStackSize -= var11;
                     var15 = BuddyRankComparator.method2484(Interpreter.Interpreter_stringStack, Interpreter.Interpreter_stringStackSize, var11);
                     Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var15;
                  } else if (var16 == 38) {
                     --class44.Interpreter_intStackSize;
                  } else if (var16 == 39) {
                     --Interpreter.Interpreter_stringStackSize;
                  } else {
                     int var17;
                     if (var16 != 40) {
                        if (var16 == 42) {
                           Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = GrandExchangeOfferOwnWorldComparator.varcs.getInt(var7[var5]);
                        } else if (var16 == 43) {
                           GrandExchangeOfferOwnWorldComparator.varcs.setInt(var7[var5], Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize]);
                        } else if (var16 == 44) {
                           var11 = var7[var5] >> 16;
                           var14 = var7[var5] & '\uffff';
                           int var33 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                           if (var33 < 0 || var33 > 5000) {
                              throw new RuntimeException();
                           }

                           Interpreter.Interpreter_arrayLengths[var11] = var33;
                           byte var35 = -1;
                           if (var14 == 105) {
                              var35 = 0;
                           }

                           for(var17 = 0; var17 < var33; ++var17) {
                              Interpreter.Interpreter_arrays[var11][var17] = var35;
                           }
                        } else if (var16 == 45) {
                           var11 = var7[var5];
                           var14 = Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize];
                           if (var14 < 0 || var14 >= Interpreter.Interpreter_arrayLengths[var11]) {
                              throw new RuntimeException();
                           }

                           Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = Interpreter.Interpreter_arrays[var11][var14];
                        } else if (var16 == 46) {
                           var11 = var7[var5];
                           class44.Interpreter_intStackSize -= 2;
                           var14 = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize];
                           if (var14 < 0 || var14 >= Interpreter.Interpreter_arrayLengths[var11]) {
                              throw new RuntimeException();
                           }

                           Interpreter.Interpreter_arrays[var11][var14] = Interpreter.Interpreter_intStack[class44.Interpreter_intStackSize + 1];
                        } else {
                           String var29;
                           if (var16 == 47) {
                              var29 = GrandExchangeOfferOwnWorldComparator.varcs.getStringOld(var7[var5]);
                              if (var29 == null) {
                                 var29 = "null";
                              }

                              Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var29;
                           } else if (var16 == 48) {
                              GrandExchangeOfferOwnWorldComparator.varcs.setStringOld(var7[var5], Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize]);
                           } else if (var16 == 49) {
                              var29 = GrandExchangeOfferOwnWorldComparator.varcs.getString(var7[var5]);
                              Interpreter.Interpreter_stringStack[++Interpreter.Interpreter_stringStackSize - 1] = var29;
                           } else if (var16 == 50) {
                              GrandExchangeOfferOwnWorldComparator.varcs.setString(var7[var5], Interpreter.Interpreter_stringStack[--Interpreter.Interpreter_stringStackSize]);
                           } else if (var16 == 60) {
                              IterableNodeHashTable var32 = var4.switches[var7[var5]];
                              IntegerNode var36 = (IntegerNode)var32.get((long)Interpreter.Interpreter_intStack[--class44.Interpreter_intStackSize]);
                              if (var36 != null) {
                                 var5 += var36.integer;
                              }
                           } else {
                              Integer var34;
                              if (var16 == 74) {
                                 var34 = class26.field220.getTitleGroupValue(var7[var5]);
                                 if (var34 == null) {
                                    Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
                                 } else {
                                    Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var34;
                                 }
                              } else {
                                 if (var16 != 76) {
                                    throw new IllegalStateException();
                                 }

                                 var34 = class34.field254.method6014(var7[var5]);
                                 if (var34 == null) {
                                    Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = -1;
                                 } else {
                                    Interpreter.Interpreter_intStack[++class44.Interpreter_intStackSize - 1] = var34;
                                 }
                              }
                           }
                        }
                     } else {
                        var11 = var7[var5];
                        Script var18 = ApproximateRouteStrategy.getScript(var11);
                        int[] var19 = new int[var18.localIntCount];
                        String[] var20 = new String[var18.localStringCount];

                        for(var17 = 0; var17 < var18.intArgumentCount; ++var17) {
                           var19[var17] = Interpreter.Interpreter_intStack[var17 + (class44.Interpreter_intStackSize - var18.intArgumentCount)];
                        }

                        for(var17 = 0; var17 < var18.stringArgumentCount; ++var17) {
                           var20[var17] = Interpreter.Interpreter_stringStack[var17 + (Interpreter.Interpreter_stringStackSize - var18.stringArgumentCount)];
                        }

                        class44.Interpreter_intStackSize -= var18.intArgumentCount;
                        Interpreter.Interpreter_stringStackSize -= var18.stringArgumentCount;
                        ScriptFrame var21 = new ScriptFrame();
                        var21.script = var4;
                        var21.pc = var5;
                        var21.intLocals = class368.Interpreter_intLocals;
                        var21.stringLocals = Interpreter.Interpreter_stringLocals;
                        Interpreter.Interpreter_frames[++Interpreter.Interpreter_frameDepth - 1] = var21;
                        var4 = var18;
                        var27 = var18.opcodes;
                        var7 = var18.intOperands;
                        var5 = -1;
                        class368.Interpreter_intLocals = var19;
                        Interpreter.Interpreter_stringLocals = var20;
                     }
                  }
               }
            } catch (Exception var25) {
               var9 = true;
               StringBuilder var13 = new StringBuilder(30);
               var13.append("").append(var4.key).append(" ");

               for(var11 = Interpreter.Interpreter_frameDepth - 1; var11 >= 0; --var11) {
                  var13.append("").append(Interpreter.Interpreter_frames[var11].script.key).append(" ");
               }

               var13.append("").append(var8);
               class266.RunException_sendStackTrace(var13.toString(), var25);
            }
         } finally {
            if (Interpreter.field973) {
               Interpreter.field986 = true;
               class4.method47();
               Interpreter.field986 = false;
               Interpreter.field973 = false;
            }

            if (!var9 && var2 > 0 && var10 >= var2) {
               class266.RunException_sendStackTrace("Warning: Script " + var4.field1084 + " finished at op count " + var10 + " of max " + var1, (Throwable)null);
            }

         }
      }
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      descriptor = "(IIIS)I",
      garbageValue = "24074"
   )
   public static int method1685(int var0, int var1, int var2) {
      int var3 = CollisionMap.method3185(var2 - var1 + 1);
      var3 <<= var1;
      return var0 & ~var3;
   }

   @ObfuscatedName("j")
   @ObfuscatedSignature(
      descriptor = "(II)Z",
      garbageValue = "1461307480"
   )
   public static boolean method1663(int var0) {
      return (var0 >> 30 & 1) != 0;
   }
}
