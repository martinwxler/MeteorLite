package osrs;

import java.awt.Component;
import java.net.URL;
import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("bq")
@Implements("SoundSystem")
public class SoundSystem implements Runnable {
   @ObfuscatedName("g")
   @ObfuscatedSignature(
      descriptor = "Lnd;"
   )
   @Export("NetCache_responseArchiveBuffer")
   public static Buffer NetCache_responseArchiveBuffer;
   @ObfuscatedName("bl")
   @ObfuscatedSignature(
      descriptor = "Ljd;"
   )
   static StudioGame field461;
   @ObfuscatedName("gq")
   @ObfuscatedGetter(
      intValue = -27903763
   )
   @Export("baseY")
   static int baseY;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "[Lbd;"
   )
   @Export("players")
   volatile PcmPlayer[] players = new PcmPlayer[2];

   public void run() {
      try {
         for(int var1 = 0; var1 < 2; ++var1) {
            PcmPlayer var2 = this.players[var1];
            if (var2 != null) {
               var2.run();
            }
         }
      } catch (Exception var3) {
         class266.RunException_sendStackTrace((String)null, var3);
      }

   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(II)Leo;",
      garbageValue = "-1234409963"
   )
   public static FloorOverlayDefinition method807(int var0) {
      FloorOverlayDefinition var1 = (FloorOverlayDefinition)FloorOverlayDefinition.FloorOverlayDefinition_cached.get((long)var0);
      if (var1 != null) {
         return var1;
      } else {
         byte[] var2 = class142.FloorOverlayDefinition_archive.takeFile(4, var0);
         var1 = new FloorOverlayDefinition();
         if (var2 != null) {
            var1.decode(new Buffer(var2), var0);
         }

         var1.postDecode();
         FloorOverlayDefinition.FloorOverlayDefinition_cached.put(var1, (long)var0);
         return var1;
      }
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(IIILfc;Lfz;Z[I[II)I",
      garbageValue = "1972841264"
   )
   public static int method808(int var0, int var1, int var2, RouteStrategy var3, CollisionMap var4, boolean var5, int[] var6, int[] var7) {
      int var8;
      int var9;
      for(var9 = 0; var9 < 128; ++var9) {
         for(var8 = 0; var8 < 128; ++var8) {
            class159.directions[var9][var8] = 0;
            class159.distances[var9][var8] = 99999999;
         }
      }

      int var10;
      byte var11;
      byte var12;
      int var13;
      int var14;
      byte var15;
      int var16;
      int[][] var17;
      int var18;
      int var19;
      int var20;
      int var21;
      boolean var22;
      boolean var23;
      int var24;
      int var25;
      int var26;
      if (var2 == 1) {
         var9 = var0;
         var10 = var1;
         var11 = 64;
         var12 = 64;
         var13 = var0 - var11;
         var14 = var1 - var12;
         class159.directions[var11][var12] = 99;
         class159.distances[var11][var12] = 0;
         var15 = 0;
         var16 = 0;
         class159.bufferX[var15] = var0;
         var26 = var15 + 1;
         class159.bufferY[var15] = var1;
         var17 = var4.flags;

         label541: {
            while(var26 != var16) {
               var9 = class159.bufferX[var16];
               var10 = class159.bufferY[var16];
               var16 = var16 + 1 & 4095;
               var24 = var9 - var13;
               var25 = var10 - var14;
               var18 = var9 - var4.xInset;
               var19 = var10 - var4.yInset;
               if (var3.hasArrived(1, var9, var10, var4)) {
                  UserComparator10.field1457 = var9;
                  FaceNormal.field2363 = var10;
                  var23 = true;
                  break label541;
               }

               var20 = class159.distances[var24][var25] + 1;
               if (var24 > 0 && class159.directions[var24 - 1][var25] == 0 && (var17[var18 - 1][var19] & 19136776) == 0) {
                  class159.bufferX[var26] = var9 - 1;
                  class159.bufferY[var26] = var10;
                  var26 = var26 + 1 & 4095;
                  class159.directions[var24 - 1][var25] = 2;
                  class159.distances[var24 - 1][var25] = var20;
               }

               if (var24 < 127 && class159.directions[var24 + 1][var25] == 0 && (var17[var18 + 1][var19] & 19136896) == 0) {
                  class159.bufferX[var26] = var9 + 1;
                  class159.bufferY[var26] = var10;
                  var26 = var26 + 1 & 4095;
                  class159.directions[var24 + 1][var25] = 8;
                  class159.distances[var24 + 1][var25] = var20;
               }

               if (var25 > 0 && class159.directions[var24][var25 - 1] == 0 && (var17[var18][var19 - 1] & 19136770) == 0) {
                  class159.bufferX[var26] = var9;
                  class159.bufferY[var26] = var10 - 1;
                  var26 = var26 + 1 & 4095;
                  class159.directions[var24][var25 - 1] = 1;
                  class159.distances[var24][var25 - 1] = var20;
               }

               if (var25 < 127 && class159.directions[var24][var25 + 1] == 0 && (var17[var18][var19 + 1] & 19136800) == 0) {
                  class159.bufferX[var26] = var9;
                  class159.bufferY[var26] = var10 + 1;
                  var26 = var26 + 1 & 4095;
                  class159.directions[var24][var25 + 1] = 4;
                  class159.distances[var24][var25 + 1] = var20;
               }

               if (var24 > 0 && var25 > 0 && class159.directions[var24 - 1][var25 - 1] == 0 && (var17[var18 - 1][var19 - 1] & 19136782) == 0 && (var17[var18 - 1][var19] & 19136776) == 0 && (var17[var18][var19 - 1] & 19136770) == 0) {
                  class159.bufferX[var26] = var9 - 1;
                  class159.bufferY[var26] = var10 - 1;
                  var26 = var26 + 1 & 4095;
                  class159.directions[var24 - 1][var25 - 1] = 3;
                  class159.distances[var24 - 1][var25 - 1] = var20;
               }

               if (var24 < 127 && var25 > 0 && class159.directions[var24 + 1][var25 - 1] == 0 && (var17[var18 + 1][var19 - 1] & 19136899) == 0 && (var17[var18 + 1][var19] & 19136896) == 0 && (var17[var18][var19 - 1] & 19136770) == 0) {
                  class159.bufferX[var26] = var9 + 1;
                  class159.bufferY[var26] = var10 - 1;
                  var26 = var26 + 1 & 4095;
                  class159.directions[var24 + 1][var25 - 1] = 9;
                  class159.distances[var24 + 1][var25 - 1] = var20;
               }

               if (var24 > 0 && var25 < 127 && class159.directions[var24 - 1][var25 + 1] == 0 && (var17[var18 - 1][var19 + 1] & 19136824) == 0 && (var17[var18 - 1][var19] & 19136776) == 0 && (var17[var18][var19 + 1] & 19136800) == 0) {
                  class159.bufferX[var26] = var9 - 1;
                  class159.bufferY[var26] = var10 + 1;
                  var26 = var26 + 1 & 4095;
                  class159.directions[var24 - 1][var25 + 1] = 6;
                  class159.distances[var24 - 1][var25 + 1] = var20;
               }

               if (var24 < 127 && var25 < 127 && class159.directions[var24 + 1][var25 + 1] == 0 && (var17[var18 + 1][var19 + 1] & 19136992) == 0 && (var17[var18 + 1][var19] & 19136896) == 0 && (var17[var18][var19 + 1] & 19136800) == 0) {
                  class159.bufferX[var26] = var9 + 1;
                  class159.bufferY[var26] = var10 + 1;
                  var26 = var26 + 1 & 4095;
                  class159.directions[var24 + 1][var25 + 1] = 12;
                  class159.distances[var24 + 1][var25 + 1] = var20;
               }
            }

            UserComparator10.field1457 = var9;
            FaceNormal.field2363 = var10;
            var23 = false;
         }

         var22 = var23;
      } else if (var2 == 2) {
         var9 = var0;
         var10 = var1;
         var11 = 64;
         var12 = 64;
         var13 = var0 - var11;
         var14 = var1 - var12;
         class159.directions[var11][var12] = 99;
         class159.distances[var11][var12] = 0;
         var15 = 0;
         var16 = 0;
         class159.bufferX[var15] = var0;
         var26 = var15 + 1;
         class159.bufferY[var15] = var1;
         var17 = var4.flags;

         label530: {
            while(var26 != var16) {
               var9 = class159.bufferX[var16];
               var10 = class159.bufferY[var16];
               var16 = var16 + 1 & 4095;
               var24 = var9 - var13;
               var25 = var10 - var14;
               var18 = var9 - var4.xInset;
               var19 = var10 - var4.yInset;
               if (var3.hasArrived(2, var9, var10, var4)) {
                  UserComparator10.field1457 = var9;
                  FaceNormal.field2363 = var10;
                  var23 = true;
                  break label530;
               }

               var20 = class159.distances[var24][var25] + 1;
               if (var24 > 0 && class159.directions[var24 - 1][var25] == 0 && (var17[var18 - 1][var19] & 19136782) == 0 && (var17[var18 - 1][var19 + 1] & 19136824) == 0) {
                  class159.bufferX[var26] = var9 - 1;
                  class159.bufferY[var26] = var10;
                  var26 = var26 + 1 & 4095;
                  class159.directions[var24 - 1][var25] = 2;
                  class159.distances[var24 - 1][var25] = var20;
               }

               if (var24 < 126 && class159.directions[var24 + 1][var25] == 0 && (var17[var18 + 2][var19] & 19136899) == 0 && (var17[var18 + 2][var19 + 1] & 19136992) == 0) {
                  class159.bufferX[var26] = var9 + 1;
                  class159.bufferY[var26] = var10;
                  var26 = var26 + 1 & 4095;
                  class159.directions[var24 + 1][var25] = 8;
                  class159.distances[var24 + 1][var25] = var20;
               }

               if (var25 > 0 && class159.directions[var24][var25 - 1] == 0 && (var17[var18][var19 - 1] & 19136782) == 0 && (var17[var18 + 1][var19 - 1] & 19136899) == 0) {
                  class159.bufferX[var26] = var9;
                  class159.bufferY[var26] = var10 - 1;
                  var26 = var26 + 1 & 4095;
                  class159.directions[var24][var25 - 1] = 1;
                  class159.distances[var24][var25 - 1] = var20;
               }

               if (var25 < 126 && class159.directions[var24][var25 + 1] == 0 && (var17[var18][var19 + 2] & 19136824) == 0 && (var17[var18 + 1][var19 + 2] & 19136992) == 0) {
                  class159.bufferX[var26] = var9;
                  class159.bufferY[var26] = var10 + 1;
                  var26 = var26 + 1 & 4095;
                  class159.directions[var24][var25 + 1] = 4;
                  class159.distances[var24][var25 + 1] = var20;
               }

               if (var24 > 0 && var25 > 0 && class159.directions[var24 - 1][var25 - 1] == 0 && (var17[var18 - 1][var19] & 19136830) == 0 && (var17[var18 - 1][var19 - 1] & 19136782) == 0 && (var17[var18][var19 - 1] & 19136911) == 0) {
                  class159.bufferX[var26] = var9 - 1;
                  class159.bufferY[var26] = var10 - 1;
                  var26 = var26 + 1 & 4095;
                  class159.directions[var24 - 1][var25 - 1] = 3;
                  class159.distances[var24 - 1][var25 - 1] = var20;
               }

               if (var24 < 126 && var25 > 0 && class159.directions[var24 + 1][var25 - 1] == 0 && (var17[var18 + 1][var19 - 1] & 19136911) == 0 && (var17[var18 + 2][var19 - 1] & 19136899) == 0 && (var17[var18 + 2][var19] & 19136995) == 0) {
                  class159.bufferX[var26] = var9 + 1;
                  class159.bufferY[var26] = var10 - 1;
                  var26 = var26 + 1 & 4095;
                  class159.directions[var24 + 1][var25 - 1] = 9;
                  class159.distances[var24 + 1][var25 - 1] = var20;
               }

               if (var24 > 0 && var25 < 126 && class159.directions[var24 - 1][var25 + 1] == 0 && (var17[var18 - 1][var19 + 1] & 19136830) == 0 && (var17[var18 - 1][var19 + 2] & 19136824) == 0 && (var17[var18][var19 + 2] & 19137016) == 0) {
                  class159.bufferX[var26] = var9 - 1;
                  class159.bufferY[var26] = var10 + 1;
                  var26 = var26 + 1 & 4095;
                  class159.directions[var24 - 1][var25 + 1] = 6;
                  class159.distances[var24 - 1][var25 + 1] = var20;
               }

               if (var24 < 126 && var25 < 126 && class159.directions[var24 + 1][var25 + 1] == 0 && (var17[var18 + 1][var19 + 2] & 19137016) == 0 && (var17[var18 + 2][var19 + 2] & 19136992) == 0 && (var17[var18 + 2][var19 + 1] & 19136995) == 0) {
                  class159.bufferX[var26] = var9 + 1;
                  class159.bufferY[var26] = var10 + 1;
                  var26 = var26 + 1 & 4095;
                  class159.directions[var24 + 1][var25 + 1] = 12;
                  class159.distances[var24 + 1][var25 + 1] = var20;
               }
            }

            UserComparator10.field1457 = var9;
            FaceNormal.field2363 = var10;
            var23 = false;
         }

         var22 = var23;
      } else {
         var9 = var0;
         var10 = var1;
         var11 = 64;
         var12 = 64;
         var13 = var0 - var11;
         var14 = var1 - var12;
         class159.directions[var11][var12] = 99;
         class159.distances[var11][var12] = 0;
         var15 = 0;
         var16 = 0;
         class159.bufferX[var15] = var0;
         var26 = var15 + 1;
         class159.bufferY[var15] = var1;
         var17 = var4.flags;

         label520: {
            label519:
            while(var16 != var26) {
               var9 = class159.bufferX[var16];
               var10 = class159.bufferY[var16];
               var16 = var16 + 1 & 4095;
               var24 = var9 - var13;
               var25 = var10 - var14;
               var18 = var9 - var4.xInset;
               var19 = var10 - var4.yInset;
               if (var3.hasArrived(var2, var9, var10, var4)) {
                  UserComparator10.field1457 = var9;
                  FaceNormal.field2363 = var10;
                  var23 = true;
                  break label520;
               }

               var20 = class159.distances[var24][var25] + 1;
               if (var24 > 0 && class159.directions[var24 - 1][var25] == 0 && (var17[var18 - 1][var19] & 19136782) == 0 && (var17[var18 - 1][var19 + var2 - 1] & 19136824) == 0) {
                  var21 = 1;

                  while(true) {
                     if (var21 >= var2 - 1) {
                        class159.bufferX[var26] = var9 - 1;
                        class159.bufferY[var26] = var10;
                        var26 = var26 + 1 & 4095;
                        class159.directions[var24 - 1][var25] = 2;
                        class159.distances[var24 - 1][var25] = var20;
                        break;
                     }

                     if ((var17[var18 - 1][var21 + var19] & 19136830) != 0) {
                        break;
                     }

                     ++var21;
                  }
               }

               if (var24 < 128 - var2 && class159.directions[var24 + 1][var25] == 0 && (var17[var18 + var2][var19] & 19136899) == 0 && (var17[var18 + var2][var19 + var2 - 1] & 19136992) == 0) {
                  var21 = 1;

                  while(true) {
                     if (var21 >= var2 - 1) {
                        class159.bufferX[var26] = var9 + 1;
                        class159.bufferY[var26] = var10;
                        var26 = var26 + 1 & 4095;
                        class159.directions[var24 + 1][var25] = 8;
                        class159.distances[var24 + 1][var25] = var20;
                        break;
                     }

                     if ((var17[var18 + var2][var21 + var19] & 19136995) != 0) {
                        break;
                     }

                     ++var21;
                  }
               }

               if (var25 > 0 && class159.directions[var24][var25 - 1] == 0 && (var17[var18][var19 - 1] & 19136782) == 0 && (var17[var18 + var2 - 1][var19 - 1] & 19136899) == 0) {
                  var21 = 1;

                  while(true) {
                     if (var21 >= var2 - 1) {
                        class159.bufferX[var26] = var9;
                        class159.bufferY[var26] = var10 - 1;
                        var26 = var26 + 1 & 4095;
                        class159.directions[var24][var25 - 1] = 1;
                        class159.distances[var24][var25 - 1] = var20;
                        break;
                     }

                     if ((var17[var18 + var21][var19 - 1] & 19136911) != 0) {
                        break;
                     }

                     ++var21;
                  }
               }

               if (var25 < 128 - var2 && class159.directions[var24][var25 + 1] == 0 && (var17[var18][var19 + var2] & 19136824) == 0 && (var17[var18 + var2 - 1][var19 + var2] & 19136992) == 0) {
                  var21 = 1;

                  while(true) {
                     if (var21 >= var2 - 1) {
                        class159.bufferX[var26] = var9;
                        class159.bufferY[var26] = var10 + 1;
                        var26 = var26 + 1 & 4095;
                        class159.directions[var24][var25 + 1] = 4;
                        class159.distances[var24][var25 + 1] = var20;
                        break;
                     }

                     if ((var17[var21 + var18][var19 + var2] & 19137016) != 0) {
                        break;
                     }

                     ++var21;
                  }
               }

               if (var24 > 0 && var25 > 0 && class159.directions[var24 - 1][var25 - 1] == 0 && (var17[var18 - 1][var19 - 1] & 19136782) == 0) {
                  var21 = 1;

                  while(true) {
                     if (var21 >= var2) {
                        class159.bufferX[var26] = var9 - 1;
                        class159.bufferY[var26] = var10 - 1;
                        var26 = var26 + 1 & 4095;
                        class159.directions[var24 - 1][var25 - 1] = 3;
                        class159.distances[var24 - 1][var25 - 1] = var20;
                        break;
                     }

                     if ((var17[var18 - 1][var21 + (var19 - 1)] & 19136830) != 0 || (var17[var21 + (var18 - 1)][var19 - 1] & 19136911) != 0) {
                        break;
                     }

                     ++var21;
                  }
               }

               if (var24 < 128 - var2 && var25 > 0 && class159.directions[var24 + 1][var25 - 1] == 0 && (var17[var18 + var2][var19 - 1] & 19136899) == 0) {
                  var21 = 1;

                  while(true) {
                     if (var21 >= var2) {
                        class159.bufferX[var26] = var9 + 1;
                        class159.bufferY[var26] = var10 - 1;
                        var26 = var26 + 1 & 4095;
                        class159.directions[var24 + 1][var25 - 1] = 9;
                        class159.distances[var24 + 1][var25 - 1] = var20;
                        break;
                     }

                     if ((var17[var18 + var2][var21 + (var19 - 1)] & 19136995) != 0 || (var17[var18 + var21][var19 - 1] & 19136911) != 0) {
                        break;
                     }

                     ++var21;
                  }
               }

               if (var24 > 0 && var25 < 128 - var2 && class159.directions[var24 - 1][var25 + 1] == 0 && (var17[var18 - 1][var19 + var2] & 19136824) == 0) {
                  var21 = 1;

                  while(true) {
                     if (var21 >= var2) {
                        class159.bufferX[var26] = var9 - 1;
                        class159.bufferY[var26] = var10 + 1;
                        var26 = var26 + 1 & 4095;
                        class159.directions[var24 - 1][var25 + 1] = 6;
                        class159.distances[var24 - 1][var25 + 1] = var20;
                        break;
                     }

                     if ((var17[var18 - 1][var19 + var21] & 19136830) != 0 || (var17[var21 + (var18 - 1)][var19 + var2] & 19137016) != 0) {
                        break;
                     }

                     ++var21;
                  }
               }

               if (var24 < 128 - var2 && var25 < 128 - var2 && class159.directions[var24 + 1][var25 + 1] == 0 && (var17[var18 + var2][var19 + var2] & 19136992) == 0) {
                  for(var21 = 1; var21 < var2; ++var21) {
                     if ((var17[var21 + var18][var19 + var2] & 19137016) != 0 || (var17[var18 + var2][var21 + var19] & 19136995) != 0) {
                        continue label519;
                     }
                  }

                  class159.bufferX[var26] = var9 + 1;
                  class159.bufferY[var26] = var10 + 1;
                  var26 = var26 + 1 & 4095;
                  class159.directions[var24 + 1][var25 + 1] = 12;
                  class159.distances[var24 + 1][var25 + 1] = var20;
               }
            }

            UserComparator10.field1457 = var9;
            FaceNormal.field2363 = var10;
            var23 = false;
         }

         var22 = var23;
      }

      var8 = var0 - 64;
      var9 = var1 - 64;
      var10 = UserComparator10.field1457;
      var24 = FaceNormal.field2363;
      if (!var22) {
         var25 = Integer.MAX_VALUE;
         var13 = Integer.MAX_VALUE;
         byte var27 = 10;
         var26 = var3.approxDestinationX;
         var16 = var3.approxDestinationY;
         int var28 = var3.approxDestinationSizeX;
         var18 = var3.approxDestinationSizeY;

         for(var19 = var26 - var27; var19 <= var26 + var27; ++var19) {
            for(var20 = var16 - var27; var20 <= var27 + var16; ++var20) {
               var21 = var19 - var8;
               int var29 = var20 - var9;
               if (var21 >= 0 && var29 >= 0 && var21 < 128 && var29 < 128 && class159.distances[var21][var29] < 100) {
                  int var30 = 0;
                  if (var19 < var26) {
                     var30 = var26 - var19;
                  } else if (var19 > var26 + var28 - 1) {
                     var30 = var19 - (var26 + var28 - 1);
                  }

                  int var31 = 0;
                  if (var20 < var16) {
                     var31 = var16 - var20;
                  } else if (var20 > var16 + var18 - 1) {
                     var31 = var20 - (var18 + var16 - 1);
                  }

                  int var32 = var31 * var31 + var30 * var30;
                  if (var32 < var25 || var25 == var32 && class159.distances[var21][var29] < var13) {
                     var25 = var32;
                     var13 = class159.distances[var21][var29];
                     var10 = var19;
                     var24 = var20;
                  }
               }
            }
         }

         if (var25 == Integer.MAX_VALUE) {
            return -1;
         }
      }

      if (var0 == var10 && var24 == var1) {
         return 0;
      } else {
         var12 = 0;
         class159.bufferX[var12] = var10;
         var25 = var12 + 1;
         class159.bufferY[var12] = var24;

         for(var13 = var14 = class159.directions[var10 - var8][var24 - var9]; var0 != var10 || var24 != var1; var13 = class159.directions[var10 - var8][var24 - var9]) {
            if (var13 != var14) {
               var14 = var13;
               class159.bufferX[var25] = var10;
               class159.bufferY[var25++] = var24;
            }

            if ((var13 & 2) != 0) {
               ++var10;
            } else if ((var13 & 8) != 0) {
               --var10;
            }

            if ((var13 & 1) != 0) {
               ++var24;
            } else if ((var13 & 4) != 0) {
               --var24;
            }
         }

         var26 = 0;

         while(var25-- > 0) {
            var6[var26] = class159.bufferX[var25];
            var7[var26++] = class159.bufferY[var25];
            if (var26 >= var6.length) {
               break;
            }
         }

         return var26;
      }
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(I)Z",
      garbageValue = "2145982637"
   )
   @Export("loadWorlds")
   static boolean loadWorlds() {
      try {
         if (MilliClock.World_request == null) {
            MilliClock.World_request = ServerPacket.urlRequester.request(new URL(class6.field58));
         } else if (MilliClock.World_request.isDone()) {
            byte[] var0 = MilliClock.World_request.getResponse();
            Buffer var1 = new Buffer(var0);
            var1.readInt();
            World.World_count = var1.readUnsignedShort();
            Tiles.World_worlds = new World[World.World_count];

            World var2;
            for(int var3 = 0; var3 < World.World_count; var2.index = var3++) {
               var2 = Tiles.World_worlds[var3] = new World();
               var2.id = var1.readUnsignedShort();
               var2.properties = var1.readInt();
               var2.host = var1.readStringCp1252NullTerminated();
               var2.activity = var1.readStringCp1252NullTerminated();
               var2.location = var1.readUnsignedByte();
               var2.population = var1.readShort();
            }

            SoundCache.sortWorlds(Tiles.World_worlds, 0, Tiles.World_worlds.length - 1, World.World_sortOption1, World.World_sortOption2);
            MilliClock.World_request = null;
            return true;
         }
      } catch (Exception var4) {
         var4.printStackTrace();
         MilliClock.World_request = null;
      }

      return false;
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "(Ljava/awt/Component;B)V",
      garbageValue = "-28"
   )
   static void method803(Component var0) {
      var0.removeKeyListener(KeyHandler.KeyHandler_instance);
      var0.removeFocusListener(KeyHandler.KeyHandler_instance);
      KeyHandler.field288 = -1;
   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      descriptor = "(IIIIB)I",
      garbageValue = "-61"
   )
   static final int method806(int var0, int var1, int var2, int var3) {
      int var4 = 65536 - Rasterizer3D.Rasterizer3D_cosine[var2 * 1024 / var3] >> 1;
      return ((65536 - var4) * var0 >> 16) + (var4 * var1 >> 16);
   }

   @ObfuscatedName("hr")
   @ObfuscatedSignature(
      descriptor = "(ZLnb;I)V",
      garbageValue = "-964489928"
   )
   @Export("updateNpcs")
   static final void updateNpcs(boolean var0, PacketBuffer var1) {
      Client.field762 = 0;
      Client.field685 = 0;
      PacketBuffer var2 = Client.packetWriter.packetBuffer;
      var2.importIndex();
      int var3 = var2.readBits(8);
      int var4;
      if (var3 < Client.npcCount) {
         for(var4 = var3; var4 < Client.npcCount; ++var4) {
            Client.field771[++Client.field762 - 1] = Client.npcIndices[var4];
         }
      }

      if (var3 > Client.npcCount) {
         throw new RuntimeException("");
      } else {
         Client.npcCount = 0;

         int var5;
         int var6;
         int var7;
         int var8;
         for(var4 = 0; var4 < var3; ++var4) {
            var5 = Client.npcIndices[var4];
            NPC var9 = Client.npcs[var5];
            var6 = var2.readBits(1);
            if (var6 == 0) {
               Client.npcIndices[++Client.npcCount - 1] = var5;
               var9.npcCycle = Client.cycle;
            } else {
               var7 = var2.readBits(2);
               if (var7 == 0) {
                  Client.npcIndices[++Client.npcCount - 1] = var5;
                  var9.npcCycle = Client.cycle;
                  Client.field686[++Client.field685 - 1] = var5;
               } else {
                  int var10;
                  if (var7 == 1) {
                     Client.npcIndices[++Client.npcCount - 1] = var5;
                     var9.npcCycle = Client.cycle;
                     var8 = var2.readBits(3);
                     var9.method2259(var8, (byte)1);
                     var10 = var2.readBits(1);
                     if (var10 == 1) {
                        Client.field686[++Client.field685 - 1] = var5;
                     }
                  } else if (var7 == 2) {
                     Client.npcIndices[++Client.npcCount - 1] = var5;
                     var9.npcCycle = Client.cycle;
                     var8 = var2.readBits(3);
                     var9.method2259(var8, (byte)2);
                     var10 = var2.readBits(3);
                     var9.method2259(var10, (byte)2);
                     int var11 = var2.readBits(1);
                     if (var11 == 1) {
                        Client.field686[++Client.field685 - 1] = var5;
                     }
                  } else if (var7 == 3) {
                     Client.field771[++Client.field762 - 1] = var5;
                  }
               }
            }
         }

         int var14;
         while(var1.bitsRemaining(Client.packetWriter.serverPacketLength) >= 27) {
            var14 = var1.readBits(15);
            if (var14 == 32767) {
               break;
            }

            boolean var15 = false;
            if (Client.npcs[var14] == null) {
               Client.npcs[var14] = new NPC();
               var15 = true;
            }

            NPC var16 = Client.npcs[var14];
            Client.npcIndices[++Client.npcCount - 1] = var14;
            var16.npcCycle = Client.cycle;
            var16.definition = StructComposition.getNpcDefinition(var1.readBits(14));
            if (var0) {
               var6 = var1.readBits(8);
               if (var6 > 127) {
                  var6 -= 256;
               }
            } else {
               var6 = var1.readBits(5);
               if (var6 > 15) {
                  var6 -= 32;
               }
            }

            int var12;
            if (var0) {
               var12 = var1.readBits(8);
               if (var12 > 127) {
                  var12 -= 256;
               }
            } else {
               var12 = var1.readBits(5);
               if (var12 > 15) {
                  var12 -= 32;
               }
            }

            var7 = Client.defaultRotations[var1.readBits(3)];
            if (var15) {
               var16.orientation = var16.rotation = var7;
            }

            var8 = var1.readBits(1);
            if (var8 == 1) {
               Client.field686[++Client.field685 - 1] = var14;
            }

            boolean var13 = var1.readBits(1) == 1;
            if (var13) {
               var1.readBits(32);
            }

            var5 = var1.readBits(1);
            var16.field1239 = var16.definition.size;
            var16.field1289 = var16.definition.rotation;
            if (var16.field1289 == 0) {
               var16.rotation = 0;
            }

            var16.walkSequence = var16.definition.walkSequence;
            var16.walkBackSequence = var16.definition.walkBackSequence;
            var16.walkLeftSequence = var16.definition.walkLeftSequence;
            var16.walkRightSequence = var16.definition.walkRightSequence;
            var16.idleSequence = var16.definition.idleSequence;
            var16.turnLeftSequence = var16.definition.turnLeftSequence;
            var16.turnRightSequence = var16.definition.turnRightSequence;
            var16.method2260(class93.localPlayer.pathX[0] + var12, class93.localPlayer.pathY[0] + var6, var5 == 1);
         }

         var1.exportIndex();
         ScriptEvent.method2094(var1);

         for(var14 = 0; var14 < Client.field762; ++var14) {
            var3 = Client.field771[var14];
            if (Client.npcs[var3].npcCycle != Client.cycle) {
               Client.npcs[var3].definition = null;
               Client.npcs[var3] = null;
            }
         }

         if (var1.offset != Client.packetWriter.serverPacketLength) {
            throw new RuntimeException(var1.offset + "," + Client.packetWriter.serverPacketLength);
         } else {
            for(var14 = 0; var14 < Client.npcCount; ++var14) {
               if (Client.npcs[Client.npcIndices[var14]] == null) {
                  throw new RuntimeException(var14 + "," + Client.npcCount);
               }
            }

         }
      }
   }
}
