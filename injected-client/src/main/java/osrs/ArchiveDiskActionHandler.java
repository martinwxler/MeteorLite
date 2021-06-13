package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("jg")
@Implements("ArchiveDiskActionHandler")
public class ArchiveDiskActionHandler implements Runnable {
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "Lkx;"
   )
   @Export("ArchiveDiskActionHandler_requestQueue")
   public static NodeDeque ArchiveDiskActionHandler_requestQueue = new NodeDeque();
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "Lkx;"
   )
   @Export("ArchiveDiskActionHandler_responseQueue")
   public static NodeDeque ArchiveDiskActionHandler_responseQueue = new NodeDeque();
   @ObfuscatedName("f")
   @ObfuscatedGetter(
      intValue = 2106150539
   )
   static int field3562 = 0;
   @ObfuscatedName("y")
   @Export("ArchiveDiskActionHandler_lock")
   static Object ArchiveDiskActionHandler_lock = new Object();
   @ObfuscatedName("p")
   @Export("ArchiveDiskActionHandler_thread")
   static Thread ArchiveDiskActionHandler_thread;

   public void run() {
      try {
         while(true) {
            NodeDeque var2 = ArchiveDiskActionHandler_requestQueue;
            ArchiveDiskAction var1;
            synchronized(ArchiveDiskActionHandler_requestQueue) {
               var1 = (ArchiveDiskAction)ArchiveDiskActionHandler_requestQueue.last();
            }

            if (var1 != null) {
               if (var1.type == 0) {
                  var1.archiveDisk.write((int)var1.key, var1.data, var1.data.length);
                  var2 = ArchiveDiskActionHandler_requestQueue;
                  synchronized(ArchiveDiskActionHandler_requestQueue) {
                     var1.remove();
                  }
               } else if (var1.type == 1) {
                  var1.data = var1.archiveDisk.read((int)var1.key);
                  var2 = ArchiveDiskActionHandler_requestQueue;
                  synchronized(ArchiveDiskActionHandler_requestQueue) {
                     ArchiveDiskActionHandler_responseQueue.addFirst(var1);
                  }
               }

               Object var17 = ArchiveDiskActionHandler_lock;
               synchronized(ArchiveDiskActionHandler_lock) {
                  if (field3562 <= 1) {
                     field3562 = 0;
                     ArchiveDiskActionHandler_lock.notifyAll();
                     return;
                  }

                  field3562 = 600;
               }
            } else {
               long var16 = 99L;

               try {
                  Thread.sleep(var16);
               } catch (InterruptedException var13) {
                  ;
               }

               try {
                  Thread.sleep(1L);
               } catch (InterruptedException var12) {
                  ;
               }

               Object var4 = ArchiveDiskActionHandler_lock;
               synchronized(ArchiveDiskActionHandler_lock) {
                  if (field3562 <= 1) {
                     field3562 = 0;
                     ArchiveDiskActionHandler_lock.notifyAll();
                     return;
                  }

                  --field3562;
               }
            }
         }
      } catch (Exception var15) {
         class266.RunException_sendStackTrace((String)null, var15);
      }
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "(IB)V",
      garbageValue = "13"
   )
   public static void method4867(int var0) {
      MouseHandler.MouseHandler_idleCycles = var0;
   }
}
