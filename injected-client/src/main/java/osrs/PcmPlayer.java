package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("bd")
@Implements("PcmPlayer")
public class PcmPlayer {
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = 1631160541
   )
   public static int field428;
   @ObfuscatedName("j")
   @Export("PcmPlayer_stereo")
   public static boolean PcmPlayer_stereo;
   @ObfuscatedName("d")
   @ObfuscatedGetter(
      intValue = -1797165253
   )
   public static int field418;
   @ObfuscatedName("c")
   @Export("samples")
   protected int[] samples;
   @ObfuscatedName("e")
   @ObfuscatedSignature(
      descriptor = "Lbm;"
   )
   @Export("stream")
   PcmStream stream;
   @ObfuscatedName("g")
   @ObfuscatedGetter(
      intValue = 881118859
   )
   int field435 = 32;
   @ObfuscatedName("a")
   @ObfuscatedGetter(
      longValue = 2377200656236085237L
   )
   @Export("timeMs")
   long timeMs = ObjectComposition.currentTimeMillis();
   @ObfuscatedName("k")
   @ObfuscatedGetter(
      intValue = 1181152315
   )
   @Export("capacity")
   int capacity;
   @ObfuscatedName("m")
   @ObfuscatedGetter(
      intValue = -908602587
   )
   int field425;
   @ObfuscatedName("x")
   @ObfuscatedGetter(
      intValue = 1178073075
   )
   int field426;
   @ObfuscatedName("z")
   @ObfuscatedGetter(
      longValue = 7200649757382554285L
   )
   long field427 = 0L;
   @ObfuscatedName("w")
   @ObfuscatedGetter(
      intValue = -847045713
   )
   int field436 = 0;
   @ObfuscatedName("t")
   @ObfuscatedGetter(
      intValue = 1580986367
   )
   int field422 = 0;
   @ObfuscatedName("h")
   @ObfuscatedGetter(
      intValue = -70989363
   )
   int field430 = 0;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      longValue = -1818884622277911111L
   )
   long field431 = 0L;
   @ObfuscatedName("i")
   boolean field432 = true;
   @ObfuscatedName("ai")
   @ObfuscatedGetter(
      intValue = -1875393861
   )
   int field433 = 0;
   @ObfuscatedName("ar")
   @ObfuscatedSignature(
      descriptor = "[Lbm;"
   )
   PcmStream[] field434 = new PcmStream[8];
   @ObfuscatedName("ag")
   @ObfuscatedSignature(
      descriptor = "[Lbm;"
   )
   PcmStream[] field439 = new PcmStream[8];

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(B)V",
      garbageValue = "4"
   )
   @Export("init")
   protected void init() throws Exception {
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "(IS)V",
      garbageValue = "-6942"
   )
   @Export("open")
   protected void open(int var1) throws Exception {
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "(B)I",
      garbageValue = "-42"
   )
   @Export("position")
   protected int position() throws Exception {
      return this.capacity;
   }

   @ObfuscatedName("y")
   @Export("write")
   protected void write() throws Exception {
   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      descriptor = "(S)V",
      garbageValue = "4345"
   )
   @Export("close")
   protected void close() {
   }

   @ObfuscatedName("j")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "-2070498959"
   )
   @Export("discard")
   protected void discard() throws Exception {
   }

   @ObfuscatedName("i")
   @ObfuscatedSignature(
      descriptor = "(Lbm;B)V",
      garbageValue = "46"
   )
   @Export("setStream")
   public final synchronized void setStream(PcmStream var1) {
      this.stream = var1;
   }

   @ObfuscatedName("ae")
   @ObfuscatedSignature(
      descriptor = "(B)V",
      garbageValue = "72"
   )
   @Export("run")
   public final synchronized void run() {
      if (this.samples != null) {
         long var1 = ObjectComposition.currentTimeMillis();

         try {
            if (0L != this.field427) {
               if (var1 < this.field427) {
                  return;
               }

               this.open(this.capacity);
               this.field427 = 0L;
               this.field432 = true;
            }

            int var3 = this.position();
            if (this.field430 - var3 > this.field436) {
               this.field436 = this.field430 - var3;
            }

            int var4 = this.field426 + this.field425;
            if (var4 + 256 > 16384) {
               var4 = 16128;
            }

            if (var4 + 256 > this.capacity) {
               this.capacity += 1024;
               if (this.capacity > 16384) {
                  this.capacity = 16384;
               }

               this.close();
               this.open(this.capacity);
               var3 = 0;
               this.field432 = true;
               if (var4 + 256 > this.capacity) {
                  var4 = this.capacity - 256;
                  this.field426 = var4 - this.field425;
               }
            }

            while(var3 < var4) {
               this.fill(this.samples, 256);
               this.write();
               var3 += 256;
            }

            if (var1 > this.field431) {
               if (!this.field432) {
                  if (this.field436 == 0 && this.field422 == 0) {
                     this.close();
                     this.field427 = 2000L + var1;
                     return;
                  }

                  this.field426 = Math.min(this.field422, this.field436);
                  this.field422 = this.field436;
               } else {
                  this.field432 = false;
               }

               this.field436 = 0;
               this.field431 = 2000L + var1;
            }

            this.field430 = var3;
         } catch (Exception var6) {
            this.close();
            this.field427 = var1 + 2000L;
         }

         try {
            if (var1 > this.timeMs + 500000L) {
               var1 = this.timeMs;
            }

            while(var1 > 5000L + this.timeMs) {
               this.skip(256);
               this.timeMs += (long)(256000 / field428);
            }
         } catch (Exception var5) {
            this.timeMs = var1;
         }
      }

   }

   @ObfuscatedName("ap")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "1062568016"
   )
   public final void method733() {
      this.field432 = true;
   }

   @ObfuscatedName("ab")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "955250326"
   )
   @Export("tryDiscard")
   public final synchronized void tryDiscard() {
      this.field432 = true;

      try {
         this.discard();
      } catch (Exception var2) {
         this.close();
         this.field427 = ObjectComposition.currentTimeMillis() + 2000L;
      }

   }

   @ObfuscatedName("al")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "-2106105090"
   )
   @Export("shutdown")
   public final synchronized void shutdown() {
      if (Message.soundSystem != null) {
         boolean var1 = true;

         for(int var2 = 0; var2 < 2; ++var2) {
            if (this == Message.soundSystem.players[var2]) {
               Message.soundSystem.players[var2] = null;
            }

            if (Message.soundSystem.players[var2] != null) {
               var1 = false;
            }
         }

         if (var1) {
            Huffman.soundSystemExecutor.shutdownNow();
            Huffman.soundSystemExecutor = null;
            Message.soundSystem = null;
         }
      }

      this.close();
      this.samples = null;
   }

   @ObfuscatedName("ad")
   @ObfuscatedSignature(
      descriptor = "(II)V",
      garbageValue = "1429485953"
   )
   @Export("skip")
   final void skip(int var1) {
      this.field433 -= var1;
      if (this.field433 < 0) {
         this.field433 = 0;
      }

      if (this.stream != null) {
         this.stream.skip(var1);
      }

   }

   @ObfuscatedName("ai")
   @Export("fill")
   final void fill(int[] var1, int var2) {
      int var3 = var2;
      if (PcmPlayer_stereo) {
         var3 = var2 << 1;
      }

      class306.clearIntArray(var1, 0, var3);
      this.field433 -= var2;
      if (this.stream != null && this.field433 <= 0) {
         this.field433 += field428 >> 4;
         FloorOverlayDefinition.PcmStream_disable(this.stream);
         this.method738(this.stream, this.stream.vmethod987());
         int var4 = 0;
         int var5 = 255;

         int var6;
         PcmStream var7;
         label106:
         for(var6 = 7; var5 != 0; --var6) {
            int var8;
            int var9;
            if (var6 < 0) {
               var8 = var6 & 3;
               var9 = -(var6 >> 2);
            } else {
               var8 = var6;
               var9 = 0;
            }

            for(int var10 = var5 >>> var8 & 286331153; var10 != 0; var10 >>>= 4) {
               if ((var10 & 1) != 0) {
                  var5 &= ~(1 << var8);
                  var7 = null;
                  PcmStream var11 = this.field434[var8];

                  label100:
                  while(true) {
                     while(true) {
                        if (var11 == null) {
                           break label100;
                        }

                        AbstractSound var12 = var11.sound;
                        if (var12 != null && var12.position > var9) {
                           var5 |= 1 << var8;
                           var7 = var11;
                           var11 = var11.after;
                        } else {
                           var11.active = true;
                           int var13 = var11.vmethod4610();
                           var4 += var13;
                           if (var12 != null) {
                              var12.position += var13;
                           }

                           if (var4 >= this.field435) {
                              break label106;
                           }

                           PcmStream var14 = var11.firstSubStream();
                           if (var14 != null) {
                              for(int var15 = var11.field494; var14 != null; var14 = var11.nextSubStream()) {
                                 this.method738(var14, var15 * var14.vmethod987() >> 8);
                              }
                           }

                           PcmStream var18 = var11.after;
                           var11.after = null;
                           if (var7 == null) {
                              this.field434[var8] = var18;
                           } else {
                              var7.after = var18;
                           }

                           if (var18 == null) {
                              this.field439[var8] = var7;
                           }

                           var11 = var18;
                        }
                     }
                  }
               }

               var8 += 4;
               ++var9;
            }
         }

         for(var6 = 0; var6 < 8; ++var6) {
            PcmStream var16 = this.field434[var6];
            PcmStream[] var17 = this.field434;
            this.field439[var6] = null;

            for(var17[var6] = null; var16 != null; var16 = var7) {
               var7 = var16.after;
               var16.after = null;
            }
         }
      }

      if (this.field433 < 0) {
         this.field433 = 0;
      }

      if (this.stream != null) {
         this.stream.fill(var1, 0, var2);
      }

      this.timeMs = ObjectComposition.currentTimeMillis();
   }

   @ObfuscatedName("ag")
   @ObfuscatedSignature(
      descriptor = "(Lbm;II)V",
      garbageValue = "-1859037567"
   )
   final void method738(PcmStream var1, int var2) {
      int var3 = var2 >> 5;
      PcmStream var4 = this.field439[var3];
      if (var4 == null) {
         this.field434[var3] = var1;
      } else {
         var4.after = var1;
      }

      this.field439[var3] = var1;
      var1.field494 = var2;
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "(Lnb;I)V",
      garbageValue = "-1728648073"
   )
   static final void method750(PacketBuffer var0) {
      int var1 = 0;
      var0.importIndex();

      byte[] var2;
      int var3;
      int var4;
      int var5;
      int var6;
      int var7;
      for(var3 = 0; var3 < Players.Players_count; ++var3) {
         var7 = Players.Players_indices[var3];
         if ((Players.field1370[var7] & 1) == 0) {
            if (var1 > 0) {
               --var1;
               var2 = Players.field1370;
               var2[var7] = (byte)(var2[var7] | 2);
            } else {
               var4 = var0.readBits(1);
               if (var4 == 0) {
                  var5 = var0.readBits(2);
                  if (var5 == 0) {
                     var6 = 0;
                  } else if (var5 == 1) {
                     var6 = var0.readBits(5);
                  } else if (var5 == 2) {
                     var6 = var0.readBits(8);
                  } else {
                     var6 = var0.readBits(11);
                  }

                  var1 = var6;
                  var2 = Players.field1370;
                  var2[var7] = (byte)(var2[var7] | 2);
               } else {
                  ItemComposition.readPlayerUpdate(var0, var7);
               }
            }
         }
      }

      var0.exportIndex();
      if (var1 != 0) {
         throw new RuntimeException();
      } else {
         var0.importIndex();

         for(var3 = 0; var3 < Players.Players_count; ++var3) {
            var7 = Players.Players_indices[var3];
            if ((Players.field1370[var7] & 1) != 0) {
               if (var1 > 0) {
                  --var1;
                  var2 = Players.field1370;
                  var2[var7] = (byte)(var2[var7] | 2);
               } else {
                  var4 = var0.readBits(1);
                  if (var4 == 0) {
                     var5 = var0.readBits(2);
                     if (var5 == 0) {
                        var6 = 0;
                     } else if (var5 == 1) {
                        var6 = var0.readBits(5);
                     } else if (var5 == 2) {
                        var6 = var0.readBits(8);
                     } else {
                        var6 = var0.readBits(11);
                     }

                     var1 = var6;
                     var2 = Players.field1370;
                     var2[var7] = (byte)(var2[var7] | 2);
                  } else {
                     ItemComposition.readPlayerUpdate(var0, var7);
                  }
               }
            }
         }

         var0.exportIndex();
         if (var1 != 0) {
            throw new RuntimeException();
         } else {
            var0.importIndex();

            for(var3 = 0; var3 < Players.Players_emptyIdxCount; ++var3) {
               var7 = Players.Players_emptyIndices[var3];
               if ((Players.field1370[var7] & 1) != 0) {
                  if (var1 > 0) {
                     --var1;
                     var2 = Players.field1370;
                     var2[var7] = (byte)(var2[var7] | 2);
                  } else {
                     var4 = var0.readBits(1);
                     if (var4 == 0) {
                        var5 = var0.readBits(2);
                        if (var5 == 0) {
                           var6 = 0;
                        } else if (var5 == 1) {
                           var6 = var0.readBits(5);
                        } else if (var5 == 2) {
                           var6 = var0.readBits(8);
                        } else {
                           var6 = var0.readBits(11);
                        }

                        var1 = var6;
                        var2 = Players.field1370;
                        var2[var7] = (byte)(var2[var7] | 2);
                     } else if (ParamComposition.updateExternalPlayer(var0, var7)) {
                        var2 = Players.field1370;
                        var2[var7] = (byte)(var2[var7] | 2);
                     }
                  }
               }
            }

            var0.exportIndex();
            if (var1 != 0) {
               throw new RuntimeException();
            } else {
               var0.importIndex();

               for(var3 = 0; var3 < Players.Players_emptyIdxCount; ++var3) {
                  var7 = Players.Players_emptyIndices[var3];
                  if ((Players.field1370[var7] & 1) == 0) {
                     if (var1 > 0) {
                        --var1;
                        var2 = Players.field1370;
                        var2[var7] = (byte)(var2[var7] | 2);
                     } else {
                        var4 = var0.readBits(1);
                        if (var4 == 0) {
                           var5 = var0.readBits(2);
                           if (var5 == 0) {
                              var6 = 0;
                           } else if (var5 == 1) {
                              var6 = var0.readBits(5);
                           } else if (var5 == 2) {
                              var6 = var0.readBits(8);
                           } else {
                              var6 = var0.readBits(11);
                           }

                           var1 = var6;
                           var2 = Players.field1370;
                           var2[var7] = (byte)(var2[var7] | 2);
                        } else if (ParamComposition.updateExternalPlayer(var0, var7)) {
                           var2 = Players.field1370;
                           var2[var7] = (byte)(var2[var7] | 2);
                        }
                     }
                  }
               }

               var0.exportIndex();
               if (var1 != 0) {
                  throw new RuntimeException();
               } else {
                  Players.Players_count = 0;
                  Players.Players_emptyIdxCount = 0;

                  for(var3 = 1; var3 < 2048; ++var3) {
                     var2 = Players.field1370;
                     var2[var3] = (byte)(var2[var3] >> 1);
                     Player var8 = Client.players[var3];
                     if (var8 != null) {
                        Players.Players_indices[++Players.Players_count - 1] = var3;
                     } else {
                        Players.Players_emptyIndices[++Players.Players_emptyIdxCount - 1] = var3;
                     }
                  }

               }
            }
         }
      }
   }

   @ObfuscatedName("hi")
   @ObfuscatedSignature(
      descriptor = "(IIIII)V",
      garbageValue = "-572457964"
   )
   static final void method786(int var0, int var1, int var2, int var3) {
      for(int var4 = 0; var4 < Client.rootWidgetCount; ++var4) {
         if (Client.rootWidgetXs[var4] + Client.rootWidgetWidths[var4] > var0 && Client.rootWidgetXs[var4] < var0 + var2 && Client.rootWidgetHeights[var4] + Client.rootWidgetYs[var4] > var1 && Client.rootWidgetYs[var4] < var3 + var1) {
            Client.field780[var4] = true;
         }
      }

   }
}
