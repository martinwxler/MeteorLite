package osrs;

import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import javax.imageio.ImageIO;
import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("eh")
@Implements("ParamComposition")
public class ParamComposition extends DualNode {
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "Ljv;"
   )
   @Export("ParamDefinition_archive")
   public static AbstractArchive ParamDefinition_archive;
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "Lhz;"
   )
   @Export("ParamDefinition_cached")
   static EvictingDualNodeHashTable ParamDefinition_cached = new EvictingDualNodeHashTable(64);
   @ObfuscatedName("o")
   @Export("Tiles_hueMultiplier")
   static int[] Tiles_hueMultiplier;
   @ObfuscatedName("f")
   @Export("type")
   char type;
   @ObfuscatedName("y")
   @ObfuscatedGetter(
      intValue = 1035114241
   )
   @Export("defaultInt")
   public int defaultInt;
   @ObfuscatedName("p")
   @Export("defaultStr")
   public String defaultStr;
   @ObfuscatedName("j")
   @Export("autoDisable")
   boolean autoDisable = true;

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "-2035039136"
   )
   @Export("postDecode")
   void postDecode() {
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "(Lnd;S)V",
      garbageValue = "-22209"
   )
   @Export("decode")
   void decode(Buffer var1) {
      while(true) {
         int var2 = var1.readUnsignedByte();
         if (var2 == 0) {
            return;
         }

         this.decodeNext(var1, var2);
      }
   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      descriptor = "(Lnd;IB)V",
      garbageValue = "36"
   )
   @Export("decodeNext")
   void decodeNext(Buffer var1, int var2) {
      if (var2 == 1) {
         byte var3 = var1.readByte();
         int var4 = var3 & 255;
         if (var4 == 0) {
            throw new IllegalArgumentException("" + Integer.toString(var4, 16));
         }

         char var5;
         if (var4 >= 128 && var4 < 160) {
            var5 = class301.cp1252AsciiExtension[var4 - 128];
            if (var5 == 0) {
               var5 = '?';
            }

            var4 = var5;
         }

         var5 = (char)var4;
         this.type = var5;
      } else if (var2 == 2) {
         this.defaultInt = var1.readInt();
      } else if (var2 == 4) {
         this.autoDisable = false;
      } else if (var2 == 5) {
         this.defaultStr = var1.readStringCp1252NullTerminated();
      }

   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      descriptor = "(S)Z",
      garbageValue = "25333"
   )
   @Export("isString")
   public boolean isString() {
      return this.type == 's';
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "([BB)Loh;",
      garbageValue = "116"
   )
   @Export("convertJpgToSprite")
   public static final SpritePixels convertJpgToSprite(byte[] var0) {
      BufferedImage var1 = null;

      try {
         var1 = ImageIO.read(new ByteArrayInputStream(var0));
         int var2 = var1.getWidth();
         int var3 = var1.getHeight();
         int[] var4 = new int[var2 * var3];
         PixelGrabber var5 = new PixelGrabber(var1, 0, 0, var2, var3, var4, 0, var2);
         var5.grabPixels();
         return new SpritePixels(var4, var2, var3);
      } catch (IOException var6) {
         ;
      } catch (InterruptedException var7) {
         ;
      }

      return new SpritePixels(0, 0);
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "(Lnd;Ljava/lang/String;I)I",
      garbageValue = "-44618968"
   )
   public static int method2878(Buffer var0, String var1) {
      int var2 = var0.offset;
      byte[] var3 = DynamicObject.method2004(var1);
      var0.writeSmartByteShort(var3.length);
      var0.offset += class249.huffman.compress(var3, 0, var3.length, var0.array, var0.offset);
      return var0.offset - var2;
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      descriptor = "(Lnb;II)Z",
      garbageValue = "422196307"
   )
   @Export("updateExternalPlayer")
   static boolean updateExternalPlayer(PacketBuffer var0, int var1) {
      int var2 = var0.readBits(2);
      int var3;
      int var4;
      int var5;
      int var6;
      int var7;
      int var8;
      if (var2 == 0) {
         if (var0.readBits(1) != 0) {
            updateExternalPlayer(var0, var1);
         }

         var3 = var0.readBits(13);
         var4 = var0.readBits(13);
         boolean var11 = var0.readBits(1) == 1;
         if (var11) {
            Players.Players_pendingUpdateIndices[++Players.Players_pendingUpdateCount - 1] = var1;
         }

         if (Client.players[var1] != null) {
            throw new RuntimeException();
         } else {
            Player var12 = Client.players[var1] = new Player();
            var12.index = var1;
            if (Players.field1374[var1] != null) {
               var12.read(Players.field1374[var1]);
            }

            var12.orientation = Players.Players_orientations[var1];
            var12.targetIndex = Players.Players_targetIndices[var1];
            var5 = Players.Players_regions[var1];
            var6 = var5 >> 28;
            var7 = var5 >> 14 & 255;
            var8 = var5 & 255;
            var12.pathTraversed[0] = Players.field1373[var1];
            var12.plane = (byte)var6;
            var12.resetPath((var7 << 13) + var3 - VertexNormal.baseX, (var8 << 13) + var4 - SoundSystem.baseY);
            var12.field1218 = false;
            return true;
         }
      } else if (var2 == 1) {
         var3 = var0.readBits(2);
         var4 = Players.Players_regions[var1];
         Players.Players_regions[var1] = (var4 & 268435455) + (((var4 >> 28) + var3 & 3) << 28);
         return false;
      } else {
         int var9;
         int var10;
         if (var2 == 2) {
            var3 = var0.readBits(5);
            var4 = var3 >> 3;
            var9 = var3 & 7;
            var10 = Players.Players_regions[var1];
            var5 = (var10 >> 28) + var4 & 3;
            var6 = var10 >> 14 & 255;
            var7 = var10 & 255;
            if (var9 == 0) {
               --var6;
               --var7;
            }

            if (var9 == 1) {
               --var7;
            }

            if (var9 == 2) {
               ++var6;
               --var7;
            }

            if (var9 == 3) {
               --var6;
            }

            if (var9 == 4) {
               ++var6;
            }

            if (var9 == 5) {
               --var6;
               ++var7;
            }

            if (var9 == 6) {
               ++var7;
            }

            if (var9 == 7) {
               ++var6;
               ++var7;
            }

            Players.Players_regions[var1] = (var6 << 14) + var7 + (var5 << 28);
            return false;
         } else {
            var3 = var0.readBits(18);
            var4 = var3 >> 16;
            var9 = var3 >> 8 & 255;
            var10 = var3 & 255;
            var5 = Players.Players_regions[var1];
            var6 = (var5 >> 28) + var4 & 3;
            var7 = var9 + (var5 >> 14) & 255;
            var8 = var10 + var5 & 255;
            Players.Players_regions[var1] = (var7 << 14) + var8 + (var6 << 28);
            return false;
         }
      }
   }
}
