package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ir")
@Implements("MidiFileReader")
public class MidiFileReader {
   @ObfuscatedName("s")
   static final byte[] field2871 = new byte[]{2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 1, 2, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "Lnd;"
   )
   @Export("buffer")
   Buffer buffer = new Buffer((byte[])null);
   @ObfuscatedName("n")
   @Export("division")
   int division;
   @ObfuscatedName("f")
   @Export("trackStarts")
   int[] trackStarts;
   @ObfuscatedName("y")
   @Export("trackPositions")
   int[] trackPositions;
   @ObfuscatedName("p")
   @Export("trackLengths")
   int[] trackLengths;
   @ObfuscatedName("j")
   int[] field2862;
   @ObfuscatedName("r")
   int field2867;
   @ObfuscatedName("d")
   long field2870;

   MidiFileReader(byte[] var1) {
      this.parse(var1);
   }

   MidiFileReader() {
   }

   @ObfuscatedName("v")
   @Export("parse")
   void parse(byte[] var1) {
      this.buffer.array = var1;
      this.buffer.offset = 10;
      int var2 = this.buffer.readUnsignedShort();
      this.division = this.buffer.readUnsignedShort();
      this.field2867 = 500000;
      this.trackStarts = new int[var2];

      Buffer var3;
      int var4;
      int var5;
      for(var4 = 0; var4 < var2; var3.offset += var5) {
         int var6 = this.buffer.readInt();
         var5 = this.buffer.readInt();
         if (var6 == 1297379947) {
            this.trackStarts[var4] = this.buffer.offset;
            ++var4;
         }

         var3 = this.buffer;
      }

      this.field2870 = 0L;
      this.trackPositions = new int[var2];

      for(var4 = 0; var4 < var2; ++var4) {
         this.trackPositions[var4] = this.trackStarts[var4];
      }

      this.trackLengths = new int[var2];
      this.field2862 = new int[var2];
   }

   @ObfuscatedName("n")
   @Export("clear")
   void clear() {
      this.buffer.array = null;
      this.trackStarts = null;
      this.trackPositions = null;
      this.trackLengths = null;
      this.field2862 = null;
   }

   @ObfuscatedName("f")
   @Export("isReady")
   boolean isReady() {
      return this.buffer.array != null;
   }

   @ObfuscatedName("y")
   @Export("trackCount")
   int trackCount() {
      return this.trackPositions.length;
   }

   @ObfuscatedName("p")
   @Export("gotoTrack")
   void gotoTrack(int var1) {
      this.buffer.offset = this.trackPositions[var1];
   }

   @ObfuscatedName("j")
   @Export("markTrackPosition")
   void markTrackPosition(int var1) {
      this.trackPositions[var1] = this.buffer.offset;
   }

   @ObfuscatedName("r")
   @Export("setTrackDone")
   void setTrackDone() {
      this.buffer.offset = -1;
   }

   @ObfuscatedName("b")
   @Export("readTrackLength")
   void readTrackLength(int var1) {
      int var2 = this.buffer.readVarInt();
      int[] var3 = this.trackLengths;
      var3[var1] += var2;
   }

   @ObfuscatedName("d")
   @Export("readMessage")
   int readMessage(int var1) {
      int var2 = this.readMessage0(var1);
      return var2;
   }

   @ObfuscatedName("s")
   @Export("readMessage0")
   int readMessage0(int var1) {
      byte var2 = this.buffer.array[this.buffer.offset];
      int var3;
      if (var2 < 0) {
         var3 = var2 & 255;
         this.field2862[var1] = var3;
         ++this.buffer.offset;
      } else {
         var3 = this.field2862[var1];
      }

      if (var3 != 240 && var3 != 247) {
         return this.method4541(var1, var3);
      } else {
         int var4 = this.buffer.readVarInt();
         if (var3 == 247 && var4 > 0) {
            int var5 = this.buffer.array[this.buffer.offset] & 255;
            if (var5 >= 241 && var5 <= 243 || var5 == 246 || var5 == 248 || var5 >= 250 && var5 <= 252 || var5 == 254) {
               ++this.buffer.offset;
               this.field2862[var1] = var5;
               return this.method4541(var1, var5);
            }
         }

         Buffer var6 = this.buffer;
         var6.offset += var4;
         return 0;
      }
   }

   @ObfuscatedName("u")
   int method4541(int var1, int var2) {
      int var3;
      if (var2 == 255) {
         int var8 = this.buffer.readUnsignedByte();
         var3 = this.buffer.readVarInt();
         Buffer var5;
         if (var8 == 47) {
            var5 = this.buffer;
            var5.offset += var3;
            return 1;
         } else if (var8 == 81) {
            int var6 = this.buffer.readMedium();
            var3 -= 3;
            int var7 = this.trackLengths[var1];
            this.field2870 += (long)var7 * (long)(this.field2867 - var6);
            this.field2867 = var6;
            var5 = this.buffer;
            var5.offset += var3;
            return 2;
         } else {
            var5 = this.buffer;
            var5.offset += var3;
            return 3;
         }
      } else {
         byte var4 = field2871[var2 - 128];
         var3 = var2;
         if (var4 >= 1) {
            var3 = var2 | this.buffer.readUnsignedByte() << 8;
         }

         if (var4 >= 2) {
            var3 |= this.buffer.readUnsignedByte() << 16;
         }

         return var3;
      }
   }

   @ObfuscatedName("l")
   long method4552(int var1) {
      return this.field2870 + (long)var1 * (long)this.field2867;
   }

   @ObfuscatedName("o")
   @Export("getPrioritizedTrack")
   int getPrioritizedTrack() {
      int var1 = this.trackPositions.length;
      int var2 = -1;
      int var3 = Integer.MAX_VALUE;

      for(int var4 = 0; var4 < var1; ++var4) {
         if (this.trackPositions[var4] >= 0 && this.trackLengths[var4] < var3) {
            var2 = var4;
            var3 = this.trackLengths[var4];
         }
      }

      return var2;
   }

   @ObfuscatedName("c")
   @Export("isDone")
   boolean isDone() {
      int var1 = this.trackPositions.length;

      for(int var2 = 0; var2 < var1; ++var2) {
         if (this.trackPositions[var2] >= 0) {
            return false;
         }
      }

      return true;
   }

   @ObfuscatedName("e")
   @Export("reset")
   void reset(long var1) {
      this.field2870 = var1;
      int var3 = this.trackPositions.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         this.trackLengths[var4] = 0;
         this.field2862[var4] = 0;
         this.buffer.offset = this.trackStarts[var4];
         this.readTrackLength(var4);
         this.trackPositions[var4] = this.buffer.offset;
      }

   }
}
