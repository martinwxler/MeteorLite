package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("in")
@Implements("MusicPatch")
public class MusicPatch extends Node {
   @ObfuscatedName("qo")
   @Export("ClanChat_inClanChat")
   static boolean ClanChat_inClanChat;
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = 222358093
   )
   int field2881;
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "[Lau;"
   )
   @Export("rawSounds")
   RawSound[] rawSounds = new RawSound[128];
   @ObfuscatedName("f")
   short[] field2878 = new short[128];
   @ObfuscatedName("y")
   byte[] field2879 = new byte[128];
   @ObfuscatedName("p")
   byte[] field2880 = new byte[128];
   @ObfuscatedName("j")
   @ObfuscatedSignature(
      descriptor = "[Lhk;"
   )
   MusicPatchNode2[] field2876 = new MusicPatchNode2[128];
   @ObfuscatedName("r")
   byte[] field2882 = new byte[128];
   @ObfuscatedName("b")
   int[] field2883 = new int[128];

   MusicPatch(byte[] var1) {
      Buffer var2 = new Buffer(var1);

      int var3;
      for(var3 = 0; var2.array[var3 + var2.offset] != 0; ++var3) {
         ;
      }

      byte[] var4 = new byte[var3];

      int var5;
      for(var5 = 0; var5 < var3; ++var5) {
         var4[var5] = var2.readByte();
      }

      ++var2.offset;
      ++var3;
      var5 = var2.offset;
      var2.offset += var3;

      int var6;
      for(var6 = 0; var2.array[var6 + var2.offset] != 0; ++var6) {
         ;
      }

      byte[] var7 = new byte[var6];

      int var8;
      for(var8 = 0; var8 < var6; ++var8) {
         var7[var8] = var2.readByte();
      }

      ++var2.offset;
      ++var6;
      var8 = var2.offset;
      var2.offset += var6;

      int var9;
      for(var9 = 0; var2.array[var9 + var2.offset] != 0; ++var9) {
         ;
      }

      byte[] var10 = new byte[var9];

      for(int var11 = 0; var11 < var9; ++var11) {
         var10[var11] = var2.readByte();
      }

      ++var2.offset;
      ++var9;
      byte[] var43 = new byte[var9];
      int var12;
      int var13;
      if (var9 > 1) {
         var43[1] = 1;
         int var14 = 1;
         var12 = 2;

         for(var13 = 2; var13 < var9; ++var13) {
            int var15 = var2.readUnsignedByte();
            if (var15 == 0) {
               var14 = var12++;
            } else {
               if (var15 <= var14) {
                  --var15;
               }

               var14 = var15;
            }

            var43[var13] = (byte)var14;
         }
      } else {
         var12 = var9;
      }

      MusicPatchNode2[] var44 = new MusicPatchNode2[var12];

      MusicPatchNode2 var45;
      for(var13 = 0; var13 < var44.length; ++var13) {
         var45 = var44[var13] = new MusicPatchNode2();
         int var16 = var2.readUnsignedByte();
         if (var16 > 0) {
            var45.field2825 = new byte[var16 * 2];
         }

         var16 = var2.readUnsignedByte();
         if (var16 > 0) {
            var45.field2816 = new byte[var16 * 2 + 2];
            var45.field2816[1] = 64;
         }
      }

      var13 = var2.readUnsignedByte();
      byte[] var46 = var13 > 0 ? new byte[var13 * 2] : null;
      var13 = var2.readUnsignedByte();
      byte[] var17 = var13 > 0 ? new byte[var13 * 2] : null;

      int var18;
      for(var18 = 0; var2.array[var18 + var2.offset] != 0; ++var18) {
         ;
      }

      byte[] var19 = new byte[var18];

      int var20;
      for(var20 = 0; var20 < var18; ++var20) {
         var19[var20] = var2.readByte();
      }

      ++var2.offset;
      ++var18;
      var20 = 0;

      int var21;
      for(var21 = 0; var21 < 128; ++var21) {
         var20 += var2.readUnsignedByte();
         this.field2878[var21] = (short)var20;
      }

      var20 = 0;

      short[] var22;
      for(var21 = 0; var21 < 128; ++var21) {
         var20 += var2.readUnsignedByte();
         var22 = this.field2878;
         var22[var21] = (short)(var22[var21] + (var20 << 8));
      }

      var21 = 0;
      int var23 = 0;
      int var24 = 0;

      int var25;
      for(var25 = 0; var25 < 128; ++var25) {
         if (var21 == 0) {
            if (var23 < var19.length) {
               var21 = var19[var23++];
            } else {
               var21 = -1;
            }

            var24 = var2.readVarInt();
         }

         var22 = this.field2878;
         var22[var25] = (short)(var22[var25] + ((var24 - 1 & 2) << 14));
         this.field2883[var25] = var24;
         --var21;
      }

      var21 = 0;
      var23 = 0;
      var25 = 0;

      int var26;
      for(var26 = 0; var26 < 128; ++var26) {
         if (this.field2883[var26] != 0) {
            if (var21 == 0) {
               if (var23 < var4.length) {
                  var21 = var4[var23++];
               } else {
                  var21 = -1;
               }

               var25 = var2.array[var5++] - 1;
            }

            this.field2882[var26] = (byte)var25;
            --var21;
         }
      }

      var21 = 0;
      var23 = 0;
      var26 = 0;

      for(int var27 = 0; var27 < 128; ++var27) {
         if (this.field2883[var27] != 0) {
            if (var21 == 0) {
               if (var23 < var7.length) {
                  var21 = var7[var23++];
               } else {
                  var21 = -1;
               }

               var26 = var2.array[var8++] + 16 << 2;
            }

            this.field2880[var27] = (byte)var26;
            --var21;
         }
      }

      var21 = 0;
      var23 = 0;
      MusicPatchNode2 var47 = null;

      int var28;
      for(var28 = 0; var28 < 128; ++var28) {
         if (this.field2883[var28] != 0) {
            if (var21 == 0) {
               var47 = var44[var43[var23]];
               if (var23 < var10.length) {
                  var21 = var10[var23++];
               } else {
                  var21 = -1;
               }
            }

            this.field2876[var28] = var47;
            --var21;
         }
      }

      var21 = 0;
      var23 = 0;
      var28 = 0;

      int var29;
      for(var29 = 0; var29 < 128; ++var29) {
         if (var21 == 0) {
            if (var23 < var19.length) {
               var21 = var19[var23++];
            } else {
               var21 = -1;
            }

            if (this.field2883[var29] > 0) {
               var28 = var2.readUnsignedByte() + 1;
            }
         }

         this.field2879[var29] = (byte)var28;
         --var21;
      }

      this.field2881 = var2.readUnsignedByte() + 1;

      int var30;
      MusicPatchNode2 var31;
      for(var29 = 0; var29 < var12; ++var29) {
         var31 = var44[var29];
         if (var31.field2825 != null) {
            for(var30 = 1; var30 < var31.field2825.length; var30 += 2) {
               var31.field2825[var30] = var2.readByte();
            }
         }

         if (var31.field2816 != null) {
            for(var30 = 3; var30 < var31.field2816.length - 2; var30 += 2) {
               var31.field2816[var30] = var2.readByte();
            }
         }
      }

      if (var46 != null) {
         for(var29 = 1; var29 < var46.length; var29 += 2) {
            var46[var29] = var2.readByte();
         }
      }

      if (var17 != null) {
         for(var29 = 1; var29 < var17.length; var29 += 2) {
            var17[var29] = var2.readByte();
         }
      }

      for(var29 = 0; var29 < var12; ++var29) {
         var31 = var44[var29];
         if (var31.field2816 != null) {
            var20 = 0;

            for(var30 = 2; var30 < var31.field2816.length; var30 += 2) {
               var20 = 1 + var20 + var2.readUnsignedByte();
               var31.field2816[var30] = (byte)var20;
            }
         }
      }

      for(var29 = 0; var29 < var12; ++var29) {
         var31 = var44[var29];
         if (var31.field2825 != null) {
            var20 = 0;

            for(var30 = 2; var30 < var31.field2825.length; var30 += 2) {
               var20 = 1 + var20 + var2.readUnsignedByte();
               var31.field2825[var30] = (byte)var20;
            }
         }
      }

      byte var32;
      int var33;
      int var34;
      int var35;
      int var36;
      int var37;
      int var38;
      byte var39;
      if (var46 != null) {
         var20 = var2.readUnsignedByte();
         var46[0] = (byte)var20;

         for(var29 = 2; var29 < var46.length; var29 += 2) {
            var20 = 1 + var20 + var2.readUnsignedByte();
            var46[var29] = (byte)var20;
         }

         var39 = var46[0];
         byte var40 = var46[1];

         for(var30 = 0; var30 < var39; ++var30) {
            this.field2879[var30] = (byte)(var40 * this.field2879[var30] + 32 >> 6);
         }

         for(var30 = 2; var30 < var46.length; var30 += 2) {
            var32 = var46[var30];
            byte var41 = var46[var30 + 1];
            var33 = var40 * (var32 - var39) + (var32 - var39) / 2;

            for(var34 = var39; var34 < var32; ++var34) {
               var36 = var32 - var39;
               var37 = var33 >>> 31;
               var35 = (var33 + var37) / var36 - var37;
               this.field2879[var34] = (byte)(var35 * this.field2879[var34] + 32 >> 6);
               var33 += var41 - var40;
            }

            var39 = var32;
            var40 = var41;
         }

         for(var38 = var39; var38 < 128; ++var38) {
            this.field2879[var38] = (byte)(var40 * this.field2879[var38] + 32 >> 6);
         }

         var45 = null;
      }

      if (var17 != null) {
         var20 = var2.readUnsignedByte();
         var17[0] = (byte)var20;

         for(var29 = 2; var29 < var17.length; var29 += 2) {
            var20 = 1 + var20 + var2.readUnsignedByte();
            var17[var29] = (byte)var20;
         }

         var39 = var17[0];
         int var48 = var17[1] << 1;

         for(var30 = 0; var30 < var39; ++var30) {
            var38 = var48 + (this.field2880[var30] & 255);
            if (var38 < 0) {
               var38 = 0;
            }

            if (var38 > 128) {
               var38 = 128;
            }

            this.field2880[var30] = (byte)var38;
         }

         int var49;
         for(var30 = 2; var30 < var17.length; var30 += 2) {
            var32 = var17[var30];
            var49 = var17[var30 + 1] << 1;
            var33 = var48 * (var32 - var39) + (var32 - var39) / 2;

            for(var34 = var39; var34 < var32; ++var34) {
               var36 = var32 - var39;
               var37 = var33 >>> 31;
               var35 = (var33 + var37) / var36 - var37;
               int var42 = var35 + (this.field2880[var34] & 255);
               if (var42 < 0) {
                  var42 = 0;
               }

               if (var42 > 128) {
                  var42 = 128;
               }

               this.field2880[var34] = (byte)var42;
               var33 += var49 - var48;
            }

            var39 = var32;
            var48 = var49;
         }

         for(var38 = var39; var38 < 128; ++var38) {
            var49 = var48 + (this.field2880[var38] & 255);
            if (var49 < 0) {
               var49 = 0;
            }

            if (var49 > 128) {
               var49 = 128;
            }

            this.field2880[var38] = (byte)var49;
         }

         Object var50 = null;
      }

      for(var29 = 0; var29 < var12; ++var29) {
         var44[var29].field2815 = var2.readUnsignedByte();
      }

      for(var29 = 0; var29 < var12; ++var29) {
         var31 = var44[var29];
         if (var31.field2825 != null) {
            var31.field2821 = var2.readUnsignedByte();
         }

         if (var31.field2816 != null) {
            var31.field2819 = var2.readUnsignedByte();
         }

         if (var31.field2815 > 0) {
            var31.field2820 = var2.readUnsignedByte();
         }
      }

      for(var29 = 0; var29 < var12; ++var29) {
         var44[var29].field2817 = var2.readUnsignedByte();
      }

      for(var29 = 0; var29 < var12; ++var29) {
         var31 = var44[var29];
         if (var31.field2817 > 0) {
            var31.field2822 = var2.readUnsignedByte();
         }
      }

      for(var29 = 0; var29 < var12; ++var29) {
         var31 = var44[var29];
         if (var31.field2822 > 0) {
            var31.field2823 = var2.readUnsignedByte();
         }
      }

   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "(Lbu;[B[II)Z",
      garbageValue = "122660467"
   )
   boolean method4588(SoundCache var1, byte[] var2, int[] var3) {
      boolean var4 = true;
      int var5 = 0;
      RawSound var6 = null;

      for(int var7 = 0; var7 < 128; ++var7) {
         if (var2 == null || var2[var7] != 0) {
            int var8 = this.field2883[var7];
            if (var8 != 0) {
               if (var5 != var8) {
                  var5 = var8--;
                  if ((var8 & 1) == 0) {
                     var6 = var1.getSoundEffect(var8 >> 2, var3);
                  } else {
                     var6 = var1.getMusicSample(var8 >> 2, var3);
                  }

                  if (var6 == null) {
                     var4 = false;
                  }
               }

               if (var6 != null) {
                  this.rawSounds[var7] = var6;
                  this.field2883[var7] = 0;
               }
            }
         }
      }

      return var4;
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "1514154807"
   )
   @Export("clear")
   void clear() {
      this.field2883 = null;
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "(Lnd;I)Ljava/lang/String;",
      garbageValue = "502303985"
   )
   public static String method4592(Buffer var0) {
      return ModelData0.method4261(var0, 32767);
   }
}
