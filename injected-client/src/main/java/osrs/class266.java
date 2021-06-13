package osrs;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.URL;
import net.runelite.mapping.Export;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("jq")
public class class266 {
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(Ljava/lang/String;Ljava/lang/Throwable;I)V",
      garbageValue = "2070444539"
   )
   @Export("RunException_sendStackTrace")
   public static void RunException_sendStackTrace(String var0, Throwable var1) {
      if (var1 != null) {
         var1.printStackTrace();
      } else {
         try {
            String var2 = "";
            if (var1 != null) {
               Throwable var3 = var1;
               String var4;
               if (var1 instanceof RunException) {
                  RunException var5 = (RunException)var1;
                  var4 = var5.message + " | ";
                  var3 = var5.throwable;
               } else {
                  var4 = "";
               }

               StringWriter var17 = new StringWriter();
               PrintWriter var6 = new PrintWriter(var17);
               var3.printStackTrace(var6);
               var6.close();
               String var7 = var17.toString();
               BufferedReader var8 = new BufferedReader(new StringReader(var7));
               String var9 = var8.readLine();

               label53:
               while(true) {
                  while(true) {
                     String var10 = var8.readLine();
                     if (var10 == null) {
                        var4 = var4 + "| " + var9;
                        var2 = var4;
                        break label53;
                     }

                     int var11 = var10.indexOf(40);
                     int var12 = var10.indexOf(41, var11 + 1);
                     if (var11 >= 0 && var12 >= 0) {
                        String var13 = var10.substring(var11 + 1, var12);
                        int var14 = var13.indexOf(".java:");
                        if (var14 >= 0) {
                           var13 = var13.substring(0, var14) + var13.substring(var14 + 5);
                           var4 = var4 + var13 + ' ';
                           continue;
                        }

                        var10 = var10.substring(0, var11);
                     }

                     var10 = var10.trim();
                     var10 = var10.substring(var10.lastIndexOf(32) + 1);
                     var10 = var10.substring(var10.lastIndexOf(9) + 1);
                     var4 = var4 + var10 + ' ';
                  }
               }
            }

            if (var0 != null) {
               if (var1 != null) {
                  var2 = var2 + " | ";
               }

               var2 = var2 + var0;
            }

            System.out.println("Error: " + var2);
            var2 = var2.replace(':', '.');
            var2 = var2.replace('@', '_');
            var2 = var2.replace('&', '_');
            var2 = var2.replace('#', '_');
            if (RunException.RunException_applet == null) {
               return;
            }

            URL var16 = new URL(RunException.RunException_applet.getCodeBase(), "clienterror.ws?c=" + RunException.RunException_revision + "&u=" + RunException.localPlayerName + "&v1=" + TaskHandler.javaVendor + "&v2=" + TaskHandler.javaVersion + "&ct=" + RunException.clientType + "&e=" + var2);
            DataInputStream var18 = new DataInputStream(var16.openStream());
            var18.read();
            var18.close();
         } catch (Exception var15) {
            ;
         }
      }

   }

   static {
      int var0 = 0;
      int var1 = 0;
      class261[] var2 = WorldMapData_1.method3525();

      for(int var3 = 0; var3 < var2.length; ++var3) {
         class261 var4 = var2[var3];
         if (var4.field3199 > var0) {
            var0 = var4.field3199;
         }

         if (var4.field3202 > var1) {
            var1 = var4.field3202;
         }
      }

   }
}
