package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ke")
@Implements("ByteArrayPool")
public class ByteArrayPool {
   @ObfuscatedName("v")
   @ObfuscatedGetter(
      intValue = -1219327977
   )
   @Export("ByteArrayPool_smallCount")
   static int ByteArrayPool_smallCount = 0;
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = -1748405633
   )
   @Export("ByteArrayPool_mediumCount")
   static int ByteArrayPool_mediumCount = 0;
   @ObfuscatedName("f")
   @ObfuscatedGetter(
      intValue = 434807287
   )
   @Export("ByteArrayPool_largeCount")
   static int ByteArrayPool_largeCount = 0;
   @ObfuscatedName("y")
   @Export("ByteArrayPool_small")
   static byte[][] ByteArrayPool_small = new byte[1000][];
   @ObfuscatedName("p")
   @Export("ByteArrayPool_medium")
   static byte[][] ByteArrayPool_medium = new byte[250][];
   @ObfuscatedName("j")
   @Export("ByteArrayPool_large")
   static byte[][] ByteArrayPool_large = new byte[50][];
   @ObfuscatedName("r")
   @Export("ByteArrayPool_alternativeSizes")
   static int[] ByteArrayPool_alternativeSizes;
   @ObfuscatedName("b")
   @Export("ByteArrayPool_altSizeArrayCounts")
   static int[] ByteArrayPool_altSizeArrayCounts;

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "(IZB)[B",
      garbageValue = "100"
   )
   @Export("ByteArrayPool_getArrayBool")
   static synchronized byte[] ByteArrayPool_getArrayBool(int var0, boolean var1) {
      byte[] var2;
      if (var0 != 100) {
         if (var0 < 100) {
            ;
         }
      } else if (ByteArrayPool_smallCount > 0) {
         var2 = ByteArrayPool_small[--ByteArrayPool_smallCount];
         ByteArrayPool_small[ByteArrayPool_smallCount] = null;
         return var2;
      }

      if (var0 != 5000) {
         if (var0 < 5000) {
            ;
         }
      } else if (ByteArrayPool_mediumCount > 0) {
         var2 = ByteArrayPool_medium[--ByteArrayPool_mediumCount];
         ByteArrayPool_medium[ByteArrayPool_mediumCount] = null;
         return var2;
      }

      if (var0 != 30000) {
         if (var0 < 30000) {
            ;
         }
      } else if (ByteArrayPool_largeCount > 0) {
         var2 = ByteArrayPool_large[--ByteArrayPool_largeCount];
         ByteArrayPool_large[ByteArrayPool_largeCount] = null;
         return var2;
      }

      if (class20.ByteArrayPool_arrays != null) {
         for(int var3 = 0; var3 < ByteArrayPool_alternativeSizes.length; ++var3) {
            if (ByteArrayPool_alternativeSizes[var3] != var0) {
               if (var0 < ByteArrayPool_alternativeSizes[var3]) {
                  ;
               }
            } else if (ByteArrayPool_altSizeArrayCounts[var3] > 0) {
               byte[] var4 = class20.ByteArrayPool_arrays[var3][--ByteArrayPool_altSizeArrayCounts[var3]];
               class20.ByteArrayPool_arrays[var3][ByteArrayPool_altSizeArrayCounts[var3]] = null;
               return var4;
            }
         }
      }

      return new byte[var0];
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "(Ljava/lang/Object;ZB)[B",
      garbageValue = "-76"
   )
   public static byte[] method5573(Object var0, boolean var1) {
      if (var0 == null) {
         return null;
      } else if (var0 instanceof byte[]) {
         byte[] var5 = (byte[])((byte[])((byte[])var0));
         if (var1) {
            int var3 = var5.length;
            byte[] var4 = new byte[var3];
            System.arraycopy(var5, 0, var4, 0, var3);
            return var4;
         } else {
            return var5;
         }
      } else if (var0 instanceof AbstractByteArrayCopier) {
         AbstractByteArrayCopier var2 = (AbstractByteArrayCopier)var0;
         return var2.get();
      } else {
         throw new IllegalArgumentException();
      }
   }
}
