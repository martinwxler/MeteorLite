package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("lt")
@Implements("FriendsChat")
public class FriendsChat extends UserList {
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "Lnc;"
   )
   @Export("loginType")
   final LoginType loginType;
   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "Llc;"
   )
   @Export("localUser")
   final Usernamed localUser;
   @ObfuscatedName("y")
   @Export("name")
   public String name = null;
   @ObfuscatedName("p")
   @Export("owner")
   public String owner = null;
   @ObfuscatedName("j")
   @Export("minKick")
   public byte minKick;
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = -78473377
   )
   @Export("rank")
   public int rank;
   @ObfuscatedName("b")
   @ObfuscatedGetter(
      intValue = -1952190137
   )
   int field3817 = 1;

   @ObfuscatedSignature(
      descriptor = "(Lnc;Llc;)V"
   )
   public FriendsChat(LoginType var1, Usernamed var2) {
      super(100);
      this.loginType = var1;
      this.localUser = var2;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(B)Llx;",
      garbageValue = "126"
   )
   @Export("newInstance")
   User newInstance() {
      return new ClanMate();
   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "(II)[Llx;",
      garbageValue = "1924184817"
   )
   @Export("newTypedArray")
   User[] newTypedArray(int var1) {
      return new ClanMate[var1];
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "(Ljava/lang/String;B)V",
      garbageValue = "-11"
   )
   @Export("readName")
   final void readName(String var1) {
      this.name = WorldMapSection0.method3589(var1);
   }

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      descriptor = "(Ljava/lang/String;I)V",
      garbageValue = "-412609184"
   )
   @Export("setOwner")
   final void setOwner(String var1) {
      this.owner = WorldMapSection0.method3589(var1);
   }

   @ObfuscatedName("d")
   @ObfuscatedSignature(
      descriptor = "(Lnd;I)V",
      garbageValue = "-1281712604"
   )
   @Export("readUpdate")
   public final void readUpdate(Buffer var1) {
      this.setOwner(var1.readStringCp1252NullTerminated());
      long var2 = var1.readLong();
      long var4 = var2;
      String var6;
      int var7;
      if (var2 > 0L && var2 < 6582952005840035281L) {
         if (0L == var2 % 37L) {
            var6 = null;
         } else {
            var7 = 0;

            for(long var8 = var2; var8 != 0L; var8 /= 37L) {
               ++var7;
            }

            StringBuilder var11 = new StringBuilder(var7);

            while(0L != var4) {
               long var9 = var4;
               var4 /= 37L;
               var11.append(class305.base37Table[(int)(var9 - var4 * 37L)]);
            }

            var6 = var11.reverse().toString();
         }
      } else {
         var6 = null;
      }

      this.readName(var6);
      this.minKick = var1.readByte();
      var7 = var1.readUnsignedByte();
      if (var7 != 255) {
         this.clear();

         for(int var12 = 0; var12 < var7; ++var12) {
            ClanMate var13 = (ClanMate)this.addLastNoPreviousUsername(new Username(var1.readStringCp1252NullTerminated(), this.loginType));
            int var10 = var1.readUnsignedShort();
            var13.set(var10, ++this.field3817 - 1);
            var13.rank = var1.readByte();
            var1.readStringCp1252NullTerminated();
            this.isLocalPlayer(var13);
         }
      }

   }

   @ObfuscatedName("s")
   @ObfuscatedSignature(
      descriptor = "(Lnd;I)V",
      garbageValue = "817545734"
   )
   public final void method5659(Buffer var1) {
      Username var2 = new Username(var1.readStringCp1252NullTerminated(), this.loginType);
      int var3 = var1.readUnsignedShort();
      byte var4 = var1.readByte();
      boolean var5 = false;
      if (var4 == -128) {
         var5 = true;
      }

      ClanMate var6;
      if (var5) {
         if (this.getSize() == 0) {
            return;
         }

         var6 = (ClanMate)this.getByCurrentUsername(var2);
         if (var6 != null && var6.getWorld() == var3) {
            this.remove(var6);
         }
      } else {
         var1.readStringCp1252NullTerminated();
         var6 = (ClanMate)this.getByCurrentUsername(var2);
         if (var6 == null) {
            if (this.getSize() > super.capacity) {
               return;
            }

            var6 = (ClanMate)this.addLastNoPreviousUsername(var2);
         }

         var6.set(var3, ++this.field3817 - 1);
         var6.rank = var4;
         this.isLocalPlayer(var6);
      }

   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "308669829"
   )
   @Export("clearFriends")
   public final void clearFriends() {
      for(int var1 = 0; var1 < this.getSize(); ++var1) {
         ((ClanMate)this.get(var1)).clearIsFriend();
      }

   }

   @ObfuscatedName("l")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "1091935656"
   )
   @Export("invalidateIgnoreds")
   public final void invalidateIgnoreds() {
      for(int var1 = 0; var1 < this.getSize(); ++var1) {
         ((ClanMate)this.get(var1)).clearIsIgnored();
      }

   }

   @ObfuscatedName("o")
   @ObfuscatedSignature(
      descriptor = "(Lll;I)V",
      garbageValue = "-263854446"
   )
   @Export("isLocalPlayer")
   final void isLocalPlayer(ClanMate var1) {
      if (var1.getUsername().equals(this.localUser.username())) {
         this.rank = var1.rank;
      }

   }
}
