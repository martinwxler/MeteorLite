package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("bn")
public class class69 {
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "Lki;"
   )
   @Export("reflectionChecks")
   static IterableNodeDeque reflectionChecks = new IterableNodeDeque();
   @ObfuscatedName("t")
   @ObfuscatedSignature(
      descriptor = "Lgs;"
   )
   @Export("worldMapEvent")
   static WorldMapEvent worldMapEvent;
   @ObfuscatedName("bf")
   @ObfuscatedSignature(
      descriptor = "Lop;"
   )
   static IndexedSprite field597;
   @ObfuscatedName("gh")
   @ObfuscatedGetter(
      intValue = -1125497563
   )
   static int field596;

   @ObfuscatedName("s")
   @ObfuscatedSignature(
      descriptor = "(II)Lgd;",
      garbageValue = "-910296846"
   )
   @Export("getFrames")
   static Frames getFrames(int var0) {
      Frames var1 = (Frames)SequenceDefinition.SequenceDefinition_cachedFrames.get((long)var0);
      if (var1 != null) {
         return var1;
      } else {
         AbstractArchive var2 = SequenceDefinition.SequenceDefinition_animationsArchive;
         AbstractArchive var3 = SequenceDefinition.SequenceDefinition_skeletonsArchive;
         boolean var4 = true;
         int[] var5 = var2.getGroupFileIds(var0);

         for(int var6 = 0; var6 < var5.length; ++var6) {
            byte[] var7 = var2.getFile(var0, var5[var6]);
            if (var7 == null) {
               var4 = false;
            } else {
               int var8 = (var7[0] & 255) << 8 | var7[1] & 255;
               byte[] var9 = var3.getFile(var8, 0);
               if (var9 == null) {
                  var4 = false;
               }
            }
         }

         Frames var11;
         if (!var4) {
            var11 = null;
         } else {
            try {
               var11 = new Frames(var2, var3, var0, false);
            } catch (Exception var10) {
               var11 = null;
            }
         }

         if (var11 != null) {
            SequenceDefinition.SequenceDefinition_cachedFrames.put(var11, (long)var0);
         }

         return var11;
      }
   }

   @ObfuscatedName("fd")
   @ObfuscatedSignature(
      descriptor = "(Lio;III)V",
      garbageValue = "1981003125"
   )
   @Export("checkIfMinimapClicked")
   static final void checkIfMinimapClicked(Widget var0, int var1, int var2) {
      if ((Client.minimapState == 0 || Client.minimapState == 3) && !Client.isMenuOpen && (MouseHandler.MouseHandler_lastButton == 1 || !Client.mouseCam && MouseHandler.MouseHandler_lastButton == 4)) {
         SpriteMask var3 = var0.getSpriteMask(true);
         if (var3 == null) {
            return;
         }

         int var4 = MouseHandler.MouseHandler_lastPressedX - var1;
         int var5 = MouseHandler.MouseHandler_lastPressedY - var2;
         if (var3.contains(var4, var5)) {
            var4 -= var3.width / 2;
            var5 -= var3.height / 2;
            int var6 = Client.camAngleY & 2047;
            int var7 = Rasterizer3D.Rasterizer3D_sine[var6];
            int var8 = Rasterizer3D.Rasterizer3D_cosine[var6];
            int var9 = var8 * var4 + var5 * var7 >> 11;
            int var10 = var5 * var8 - var7 * var4 >> 11;
            int var11 = var9 + class93.localPlayer.x >> 7;
            int var12 = class93.localPlayer.y - var10 >> 7;
            PacketBufferNode var13 = class21.getPacketBufferNode(ClientPacket.field2589, Client.packetWriter.isaacCipher);
            var13.packetBuffer.writeByte(18);
            var13.packetBuffer.method6584(KeyHandler.KeyHandler_pressedKeys[82] ? (KeyHandler.KeyHandler_pressedKeys[81] ? 2 : 1) : 0);
            var13.packetBuffer.writeShort(var12 + SoundSystem.baseY);
            var13.packetBuffer.writeShort(var11 + VertexNormal.baseX);
            var13.packetBuffer.writeByte(var4);
            var13.packetBuffer.writeByte(var5);
            var13.packetBuffer.writeShort(Client.camAngleY);
            var13.packetBuffer.writeByte(57);
            var13.packetBuffer.writeByte(0);
            var13.packetBuffer.writeByte(0);
            var13.packetBuffer.writeByte(89);
            var13.packetBuffer.writeShort(class93.localPlayer.x);
            var13.packetBuffer.writeShort(class93.localPlayer.y);
            var13.packetBuffer.writeByte(63);
            Client.packetWriter.addNode(var13);
            Client.destinationX = var11;
            Client.destinationY = var12;
         }
      }

   }
}
