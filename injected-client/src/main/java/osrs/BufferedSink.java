package osrs;

import java.io.IOException;
import java.io.OutputStream;
import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("le")
@Implements("BufferedSink")
public class BufferedSink implements Runnable {
   @ObfuscatedName("v")
   @Export("thread")
   Thread thread;
   @ObfuscatedName("n")
   @Export("outputStream")
   OutputStream outputStream;
   @ObfuscatedName("f")
   @ObfuscatedGetter(
      intValue = 255745785
   )
   @Export("capacity")
   int capacity;
   @ObfuscatedName("y")
   @Export("buffer")
   byte[] buffer;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = -1232062539
   )
   @Export("position")
   int position = 0;
   @ObfuscatedName("j")
   @ObfuscatedGetter(
      intValue = 956656333
   )
   @Export("limit")
   int limit = 0;
   @ObfuscatedName("r")
   @Export("exception")
   IOException exception;
   @ObfuscatedName("b")
   @Export("closed")
   boolean closed;

   BufferedSink(OutputStream var1, int var2) {
      this.outputStream = var1;
      this.capacity = var2 + 1;
      this.buffer = new byte[this.capacity];
      this.thread = new Thread(this);
      this.thread.setDaemon(true);
      this.thread.start();
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(B)Z",
      garbageValue = "1"
   )
   @Export("isClosed")
   boolean isClosed() {
      if (this.closed) {
         try {
            this.outputStream.close();
            if (this.exception == null) {
               this.exception = new IOException("");
            }
         } catch (IOException var2) {
            if (this.exception == null) {
               this.exception = new IOException(var2);
            }
         }

         return true;
      } else {
         return false;
      }
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "([BIII)V",
      garbageValue = "1271901026"
   )
   @Export("write")
   void write(byte[] var1, int var2, int var3) throws IOException {
      if (var3 >= 0 && var2 >= 0 && var3 + var2 <= var1.length) {
         synchronized(this) {
            if (this.exception != null) {
               throw new IOException(this.exception.toString());
            } else {
               int var5;
               if (this.position <= this.limit) {
                  var5 = this.capacity - this.limit + this.position - 1;
               } else {
                  var5 = this.position - this.limit - 1;
               }

               if (var5 < var3) {
                  throw new IOException("");
               } else {
                  if (var3 + this.limit <= this.capacity) {
                     System.arraycopy(var1, var2, this.buffer, this.limit, var3);
                  } else {
                     int var6 = this.capacity - this.limit;
                     System.arraycopy(var1, var2, this.buffer, this.limit, var6);
                     System.arraycopy(var1, var6 + var2, this.buffer, 0, var3 - var6);
                  }

                  this.limit = (var3 + this.limit) % this.capacity;
                  this.notifyAll();
               }
            }
         }
      } else {
         throw new IOException();
      }
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "(B)V",
      garbageValue = "21"
   )
   @Export("close")
   void close() {
      synchronized(this) {
         this.closed = true;
         this.notifyAll();
      }

      try {
         this.thread.join();
      } catch (InterruptedException var3) {
         ;
      }

   }

   public void run() {
      while(true) {
         synchronized(this){}

         while(true) {
            boolean var13 = false;

            int var1;
            try {
               var13 = true;
               if (this.exception != null) {
                  return;
               }

               if (this.position <= this.limit) {
                  var1 = this.limit - this.position;
               } else {
                  var1 = this.capacity - this.position + this.limit;
               }

               if (var1 <= 0) {
                  try {
                     this.outputStream.flush();
                  } catch (IOException var17) {
                     this.exception = var17;
                     return;
                  }

                  if (this.isClosed()) {
                     return;
                  }

                  try {
wait();
} catch (Exception var18) {
                     ;
                  }
                  continue;
               }

               var13 = false;
            } finally {
               if (var13) {
                  ;
               }
            }

            try {
               if (var1 + this.position <= this.capacity) {
                  this.outputStream.write(this.buffer, this.position, var1);
               } else {
                  int var2 = this.capacity - this.position;
                  this.outputStream.write(this.buffer, this.position, var2);
                  this.outputStream.write(this.buffer, 0, var1 - var2);
               }
            } catch (IOException var16) {
               IOException var3 = var16;
               synchronized(this) {
                  this.exception = var3;
                  return;
               }
            }

            synchronized(this) {
               this.position = (var1 + this.position) % this.capacity;
            }

            if (!this.isClosed()) {
               break;
            }

            return;
         }
      }
   }
}
