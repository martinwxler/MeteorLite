package osrs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;
import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ll")
@Implements("ClanMate")
public class ClanMate extends Buddy {
   @ObfuscatedName("ea")
   @ObfuscatedSignature(
      descriptor = "Ljp;"
   )
   @Export("archive15")
   static Archive archive15;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "Llu;"
   )
   @Export("friend")
   TriBool friend;
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "Llu;"
   )
   @Export("ignored")
   TriBool ignored;

   ClanMate() {
      this.friend = TriBool.TriBool_unknown;
      this.ignored = TriBool.TriBool_unknown;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "-1300571849"
   )
   @Export("clearIsFriend")
   void clearIsFriend() {
      this.friend = TriBool.TriBool_unknown;
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "(I)Z",
      garbageValue = "1945487843"
   )
   @Export("isFriend")
   public final boolean isFriend() {
      if (this.friend == TriBool.TriBool_unknown) {
         this.fillIsFriend();
      }

      return this.friend == TriBool.TriBool_true;
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      descriptor = "(S)V",
      garbageValue = "256"
   )
   @Export("fillIsFriend")
   void fillIsFriend() {
      this.friend = NetSocket.friendSystem.friendsList.contains(super.username) ? TriBool.TriBool_true : TriBool.TriBool_false;
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      descriptor = "(B)V",
      garbageValue = "61"
   )
   @Export("clearIsIgnored")
   void clearIsIgnored() {
      this.ignored = TriBool.TriBool_unknown;
   }

   @ObfuscatedName("c")
   @ObfuscatedSignature(
      descriptor = "(I)Z",
      garbageValue = "601254132"
   )
   @Export("isIgnored")
   public final boolean isIgnored() {
      if (this.ignored == TriBool.TriBool_unknown) {
         this.fillIsIgnored();
      }

      return this.ignored == TriBool.TriBool_true;
   }

   @ObfuscatedName("e")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "-440591589"
   )
   @Export("fillIsIgnored")
   void fillIsIgnored() {
      this.ignored = NetSocket.friendSystem.ignoreList.contains(super.username) ? TriBool.TriBool_true : TriBool.TriBool_false;
   }

   @ObfuscatedName("d")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "-1024085126"
   )
   static void method5712() {
      Login.Login_username = Login.Login_username.trim();
      if (Login.Login_username.length() == 0) {
         class260.setLoginResponseString("Please enter your username.", "If you created your account after November", "2010, this will be the creation email address.");
      } else {
         long var0;
         Buffer var6;
         try {
            URL var2 = new URL(KitDefinition.method2705("services", false) + "m=accountappeal/login.ws");
            URLConnection var3 = var2.openConnection();
            var3.setRequestProperty("connection", "close");
            var3.setDoInput(true);
            var3.setDoOutput(true);
            var3.setConnectTimeout(5000);
            OutputStreamWriter var4 = new OutputStreamWriter(var3.getOutputStream());
            var4.write("data1=req");
            var4.flush();
            InputStream var5 = var3.getInputStream();
            var6 = new Buffer(new byte[1000]);

            while(true) {
               int var7 = var5.read(var6.array, var6.offset, 1000 - var6.offset);
               if (var7 == -1) {
                  var6.offset = 0;
                  long var8 = var6.readLong();
                  var0 = var8;
                  break;
               }

               var6.offset += var7;
               if (var6.offset >= 1000) {
                  var0 = 0L;
                  break;
               }
            }
         } catch (Exception var24) {
            var0 = 0L;
         }

         byte var25;
         if (var0 == 0L) {
            var25 = 5;
         } else {
            String var26 = Login.Login_username;
            Random var27 = new Random();
            Buffer var28 = new Buffer(128);
            var6 = new Buffer(128);
            int[] var29 = new int[]{var27.nextInt(), var27.nextInt(), (int)(var0 >> 32), (int)var0};
            var28.writeByte(10);

            int var30;
            for(var30 = 0; var30 < 4; ++var30) {
               var28.writeInt(var27.nextInt());
            }

            var28.writeInt(var29[0]);
            var28.writeInt(var29[1]);
            var28.writeLong(var0);
            var28.writeLong(0L);

            for(var30 = 0; var30 < 4; ++var30) {
               var28.writeInt(var27.nextInt());
            }

            var28.encryptRsa(class80.field1006, class80.field1007);
            var6.writeByte(10);

            for(var30 = 0; var30 < 3; ++var30) {
               var6.writeInt(var27.nextInt());
            }

            var6.writeLong(var27.nextLong());
            var6.writeLongMedium(var27.nextLong());
            if (Client.randomDatData != null) {
               var6.writeBytes(Client.randomDatData, 0, Client.randomDatData.length);
            } else {
               byte[] var9 = new byte[24];

               try {
                  JagexCache.JagexCache_randomDat.seek(0L);
                  JagexCache.JagexCache_randomDat.readFully(var9);

                  int var10;
                  for(var10 = 0; var10 < 24 && var9[var10] == 0; ++var10) {
                     ;
                  }

                  if (var10 >= 24) {
                     throw new IOException();
                  }
               } catch (Exception var23) {
                  for(int var11 = 0; var11 < 24; ++var11) {
                     var9[var11] = -1;
                  }
               }

               var6.writeBytes(var9, 0, var9.length);
            }

            var6.writeLong(var27.nextLong());
            var6.encryptRsa(class80.field1006, class80.field1007);
            var30 = Tiles.stringCp1252NullTerminatedByteSize(var26);
            if (var30 % 8 != 0) {
               var30 += 8 - var30 % 8;
            }

            Buffer var31 = new Buffer(var30);
            var31.writeStringCp1252NullTerminated(var26);
            var31.offset = var30;
            var31.xteaEncryptAll(var29);
            Buffer var32 = new Buffer(var31.offset + var28.offset + var6.offset + 5);
            var32.writeByte(2);
            var32.writeByte(var28.offset);
            var32.writeBytes(var28.array, 0, var28.offset);
            var32.writeByte(var6.offset);
            var32.writeBytes(var6.array, 0, var6.offset);
            var32.writeShort(var31.offset);
            var32.writeBytes(var31.array, 0, var31.offset);
            byte[] var33 = var32.array;
            int var12 = var33.length;
            StringBuilder var13 = new StringBuilder();

            int var14;
            for(int var15 = 0; var15 < var12 + 0; var15 += 3) {
               int var16 = var33[var15] & 255;
               var13.append(class302.field3743[var16 >>> 2]);
               if (var15 < var12 - 1) {
                  var14 = var33[var15 + 1] & 255;
                  var13.append(class302.field3743[(var16 & 3) << 4 | var14 >>> 4]);
                  if (var15 < var12 - 2) {
                     int var17 = var33[var15 + 2] & 255;
                     var13.append(class302.field3743[(var14 & 15) << 2 | var17 >>> 6]).append(class302.field3743[var17 & 63]);
                  } else {
                     var13.append(class302.field3743[(var14 & 15) << 2]).append("=");
                  }
               } else {
                  var13.append(class302.field3743[(var16 & 3) << 4]).append("==");
               }
            }

            String var34 = var13.toString();
            var34 = var34;

            byte var35;
            try {
               URL var36 = new URL(KitDefinition.method2705("services", false) + "m=accountappeal/login.ws");
               URLConnection var18 = var36.openConnection();
               var18.setDoInput(true);
               var18.setDoOutput(true);
               var18.setConnectTimeout(5000);
               OutputStreamWriter var19 = new OutputStreamWriter(var18.getOutputStream());
               var19.write("data2=" + FloorUnderlayDefinition.method2839(var34) + "&dest=" + FloorUnderlayDefinition.method2839("passwordchoice.ws"));
               var19.flush();
               InputStream var20 = var18.getInputStream();
               var32 = new Buffer(new byte[1000]);

               while(true) {
                  var14 = var20.read(var32.array, var32.offset, 1000 - var32.offset);
                  if (var14 == -1) {
                     var19.close();
                     var20.close();
                     String var21 = new String(var32.array);
                     if (var21.startsWith("OFFLINE")) {
                        var35 = 4;
                     } else if (var21.startsWith("WRONG")) {
                        var35 = 7;
                     } else if (var21.startsWith("RELOAD")) {
                        var35 = 3;
                     } else if (var21.startsWith("Not permitted for social network accounts.")) {
                        var35 = 6;
                     } else {
                        var32.xteaDecryptAll(var29);

                        while(var32.offset > 0 && var32.array[var32.offset - 1] == 0) {
                           --var32.offset;
                        }

                        var21 = new String(var32.array, 0, var32.offset);
                        if (Interpreter.method1848(var21)) {
                           Players.openURL(var21, true, false);
                           var35 = 2;
                        } else {
                           var35 = 5;
                        }
                     }
                     break;
                  }

                  var32.offset += var14;
                  if (var32.offset >= 1000) {
                     var35 = 5;
                     break;
                  }
               }
            } catch (Throwable var22) {
               var22.printStackTrace();
               var35 = 5;
            }

            var25 = var35;
         }

         switch(var25) {
         case 2:
            class260.setLoginResponseString(Strings.field3503, Strings.field3504, Strings.field3505);
            Login.loginIndex = 6;
            break;
         case 3:
            class260.setLoginResponseString("", "Error connecting to server.", "");
            break;
         case 4:
            class260.setLoginResponseString("The part of the website you are trying", "to connect to is offline at the moment.", "Please try again later.");
            break;
         case 5:
            class260.setLoginResponseString("Sorry, there was an error trying to", "log you in to this part of the website.", "Please try again later.");
            break;
         case 6:
            class260.setLoginResponseString("", "Error connecting to server.", "");
            break;
         case 7:
            class260.setLoginResponseString("You must enter a valid login to proceed. For accounts", "created after 24th November 2010, please use your", "email address. Otherwise please use your username.");
         }
      }

   }
}
