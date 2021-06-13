package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ef")
@Implements("NPCComposition")
public class NPCComposition extends DualNode {
   @ObfuscatedName("qn")
   @ObfuscatedSignature(
      descriptor = "Ll;"
   )
   @Export("guestClanSettings")
   static ClanSettings guestClanSettings;
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "Ljv;"
   )
   @Export("NpcDefinition_archive")
   public static AbstractArchive NpcDefinition_archive;
   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "Ljv;"
   )
   @Export("NpcDefinition_modelArchive")
   public static AbstractArchive NpcDefinition_modelArchive;
   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "Lhz;"
   )
   @Export("NpcDefinition_cached")
   public static EvictingDualNodeHashTable NpcDefinition_cached = new EvictingDualNodeHashTable(64);
   @ObfuscatedName("y")
   @ObfuscatedSignature(
      descriptor = "Lhz;"
   )
   @Export("NpcDefinition_cachedModels")
   public static EvictingDualNodeHashTable NpcDefinition_cachedModels = new EvictingDualNodeHashTable(50);
   @ObfuscatedName("gr")
   @ObfuscatedSignature(
      descriptor = "Loh;"
   )
   @Export("compass")
   static SpritePixels compass;
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = -2060838679
   )
   @Export("id")
   public int id;
   @ObfuscatedName("j")
   @Export("name")
   public String name = "null";
   @ObfuscatedName("r")
   @ObfuscatedGetter(
      intValue = 1748856001
   )
   @Export("size")
   public int size = 1;
   @ObfuscatedName("b")
   @Export("models")
   int[] models;
   @ObfuscatedName("d")
   int[] field1638;
   @ObfuscatedName("s")
   @ObfuscatedGetter(
      intValue = 1310735685
   )
   @Export("idleSequence")
   public int idleSequence = -1;
   @ObfuscatedName("u")
   @ObfuscatedGetter(
      intValue = 1743545491
   )
   @Export("turnLeftSequence")
   public int turnLeftSequence = -1;
   @ObfuscatedName("l")
   @ObfuscatedGetter(
      intValue = -1143137827
   )
   @Export("turnRightSequence")
   public int turnRightSequence = -1;
   @ObfuscatedName("o")
   @ObfuscatedGetter(
      intValue = -1881319333
   )
   @Export("walkSequence")
   public int walkSequence = -1;
   @ObfuscatedName("c")
   @ObfuscatedGetter(
      intValue = -2006031471
   )
   @Export("walkBackSequence")
   public int walkBackSequence = -1;
   @ObfuscatedName("e")
   @ObfuscatedGetter(
      intValue = -1692683489
   )
   @Export("walkLeftSequence")
   public int walkLeftSequence = -1;
   @ObfuscatedName("g")
   @ObfuscatedGetter(
      intValue = -1089328917
   )
   @Export("walkRightSequence")
   public int walkRightSequence = -1;
   @ObfuscatedName("a")
   @Export("recolorFrom")
   short[] recolorFrom;
   @ObfuscatedName("k")
   @Export("recolorTo")
   short[] recolorTo;
   @ObfuscatedName("m")
   @Export("retextureFrom")
   short[] retextureFrom;
   @ObfuscatedName("x")
   @Export("retextureTo")
   short[] retextureTo;
   @ObfuscatedName("z")
   @Export("actions")
   public String[] actions = new String[5];
   @ObfuscatedName("w")
   @Export("drawMapDot")
   public boolean drawMapDot = true;
   @ObfuscatedName("t")
   @ObfuscatedGetter(
      intValue = -427478981
   )
   @Export("combatLevel")
   public int combatLevel = -1;
   @ObfuscatedName("h")
   @ObfuscatedGetter(
      intValue = 1078672503
   )
   @Export("widthScale")
   int widthScale = 128;
   @ObfuscatedName("q")
   @ObfuscatedGetter(
      intValue = -1467757175
   )
   @Export("heightScale")
   int heightScale = 128;
   @ObfuscatedName("i")
   @Export("isVisible")
   public boolean isVisible = false;
   @ObfuscatedName("ae")
   @ObfuscatedGetter(
      intValue = 2079488611
   )
   @Export("ambient")
   int ambient = 0;
   @ObfuscatedName("ap")
   @ObfuscatedGetter(
      intValue = 1867659125
   )
   @Export("contrast")
   int contrast = 0;
   @ObfuscatedName("ab")
   @ObfuscatedGetter(
      intValue = 1099409813
   )
   @Export("headIconPrayer")
   public int headIconPrayer = -1;
   @ObfuscatedName("al")
   @ObfuscatedGetter(
      intValue = -729878735
   )
   @Export("rotation")
   public int rotation = 32;
   @ObfuscatedName("ad")
   @Export("transforms")
   public int[] transforms;
   @ObfuscatedName("ai")
   @ObfuscatedGetter(
      intValue = -17335253
   )
   @Export("transformVarbit")
   int transformVarbit = -1;
   @ObfuscatedName("ar")
   @ObfuscatedGetter(
      intValue = -1483491067
   )
   @Export("transformVarp")
   int transformVarp = -1;
   @ObfuscatedName("ag")
   @Export("isInteractable")
   public boolean isInteractable = true;
   @ObfuscatedName("ax")
   @Export("isClickable")
   public boolean isClickable = true;
   @ObfuscatedName("as")
   @Export("isFollower")
   public boolean isFollower = false;
   @ObfuscatedName("aj")
   @ObfuscatedSignature(
      descriptor = "Lmr;"
   )
   @Export("params")
   IterableNodeHashTable params;

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "-260686681"
   )
   @Export("postDecode")
   void postDecode() {
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "(Lnd;I)V",
      garbageValue = "1170104499"
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
      garbageValue = "-16"
   )
   @Export("decodeNext")
   void decodeNext(Buffer var1, int var2) {
      int var3;
      int var4;
      if (var2 == 1) {
         var3 = var1.readUnsignedByte();
         this.models = new int[var3];

         for(var4 = 0; var4 < var3; ++var4) {
            this.models[var4] = var1.readUnsignedShort();
         }
      } else if (var2 == 2) {
         this.name = var1.readStringCp1252NullTerminated();
      } else if (var2 == 12) {
         this.size = var1.readUnsignedByte();
      } else if (var2 == 13) {
         this.idleSequence = var1.readUnsignedShort();
      } else if (var2 == 14) {
         this.walkSequence = var1.readUnsignedShort();
      } else if (var2 == 15) {
         this.turnLeftSequence = var1.readUnsignedShort();
      } else if (var2 == 16) {
         this.turnRightSequence = var1.readUnsignedShort();
      } else if (var2 == 17) {
         this.walkSequence = var1.readUnsignedShort();
         this.walkBackSequence = var1.readUnsignedShort();
         this.walkLeftSequence = var1.readUnsignedShort();
         this.walkRightSequence = var1.readUnsignedShort();
      } else if (var2 == 18) {
         var1.readUnsignedShort();
      } else if (var2 >= 30 && var2 < 35) {
         this.actions[var2 - 30] = var1.readStringCp1252NullTerminated();
         if (this.actions[var2 - 30].equalsIgnoreCase("Hidden")) {
            this.actions[var2 - 30] = null;
         }
      } else if (var2 == 40) {
         var3 = var1.readUnsignedByte();
         this.recolorFrom = new short[var3];
         this.recolorTo = new short[var3];

         for(var4 = 0; var4 < var3; ++var4) {
            this.recolorFrom[var4] = (short)var1.readUnsignedShort();
            this.recolorTo[var4] = (short)var1.readUnsignedShort();
         }
      } else if (var2 == 41) {
         var3 = var1.readUnsignedByte();
         this.retextureFrom = new short[var3];
         this.retextureTo = new short[var3];

         for(var4 = 0; var4 < var3; ++var4) {
            this.retextureFrom[var4] = (short)var1.readUnsignedShort();
            this.retextureTo[var4] = (short)var1.readUnsignedShort();
         }
      } else if (var2 == 60) {
         var3 = var1.readUnsignedByte();
         this.field1638 = new int[var3];

         for(var4 = 0; var4 < var3; ++var4) {
            this.field1638[var4] = var1.readUnsignedShort();
         }
      } else if (var2 == 93) {
         this.drawMapDot = false;
      } else if (var2 == 95) {
         this.combatLevel = var1.readUnsignedShort();
      } else if (var2 == 97) {
         this.widthScale = var1.readUnsignedShort();
      } else if (var2 == 98) {
         this.heightScale = var1.readUnsignedShort();
      } else if (var2 == 99) {
         this.isVisible = true;
      } else if (var2 == 100) {
         this.ambient = var1.readByte();
      } else if (var2 == 101) {
         this.contrast = var1.readByte() * 5;
      } else if (var2 == 102) {
         this.headIconPrayer = var1.readUnsignedShort();
      } else if (var2 == 103) {
         this.rotation = var1.readUnsignedShort();
      } else if (var2 != 106 && var2 != 118) {
         if (var2 == 107) {
            this.isInteractable = false;
         } else if (var2 == 109) {
            this.isClickable = false;
         } else if (var2 == 111) {
            this.isFollower = true;
         } else if (var2 == 249) {
            this.params = ModeWhere.readStringIntParameters(var1, this.params);
         }
      } else {
         this.transformVarbit = var1.readUnsignedShort();
         if (this.transformVarbit == 65535) {
            this.transformVarbit = -1;
         }

         this.transformVarp = var1.readUnsignedShort();
         if (this.transformVarp == 65535) {
            this.transformVarp = -1;
         }

         var3 = -1;
         if (var2 == 118) {
            var3 = var1.readUnsignedShort();
            if (var3 == 65535) {
               var3 = -1;
            }
         }

         var4 = var1.readUnsignedByte();
         this.transforms = new int[var4 + 2];

         for(int var5 = 0; var5 <= var4; ++var5) {
            this.transforms[var5] = var1.readUnsignedShort();
            if (this.transforms[var5] == 65535) {
               this.transforms[var5] = -1;
            }
         }

         this.transforms[var4 + 1] = var3;
      }

   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      descriptor = "(Lfl;ILfl;II)Lgr;",
      garbageValue = "-26460885"
   )
   @Export("getModel")
   public final Model getModel(SequenceDefinition var1, int var2, SequenceDefinition var3, int var4) {
      if (this.transforms != null) {
         NPCComposition var11 = this.transform();
         return var11 == null ? null : var11.getModel(var1, var2, var3, var4);
      } else {
         Model var5 = (Model)NpcDefinition_cachedModels.get((long)this.id);
         if (var5 == null) {
            boolean var6 = false;

            for(int var7 = 0; var7 < this.models.length; ++var7) {
               if (!NpcDefinition_modelArchive.tryLoadFile(this.models[var7], 0)) {
                  var6 = true;
               }
            }

            if (var6) {
               return null;
            }

            ModelData[] var12 = new ModelData[this.models.length];

            int var8;
            for(var8 = 0; var8 < this.models.length; ++var8) {
               var12[var8] = ModelData.ModelData_get(NpcDefinition_modelArchive, this.models[var8], 0);
            }

            ModelData var9;
            if (var12.length == 1) {
               var9 = var12[0];
            } else {
               var9 = new ModelData(var12, var12.length);
            }

            if (this.recolorFrom != null) {
               for(var8 = 0; var8 < this.recolorFrom.length; ++var8) {
                  var9.recolor(this.recolorFrom[var8], this.recolorTo[var8]);
               }
            }

            if (this.retextureFrom != null) {
               for(var8 = 0; var8 < this.retextureFrom.length; ++var8) {
                  var9.retexture(this.retextureFrom[var8], this.retextureTo[var8]);
               }
            }

            var5 = var9.toModel(this.ambient + 64, this.contrast + 850, -30, -50, -30);
            NpcDefinition_cachedModels.put(var5, (long)this.id);
         }

         Model var10;
         if (var1 != null && var3 != null) {
            var10 = var1.applyTransformations(var5, var2, var3, var4);
         } else if (var1 != null) {
            var10 = var1.transformActorModel(var5, var2);
         } else if (var3 != null) {
            var10 = var3.transformActorModel(var5, var4);
         } else {
            var10 = var5.toSharedSequenceModel(true);
         }

         if (this.widthScale != 128 || this.heightScale != 128) {
            var10.scale(this.widthScale, this.heightScale, this.widthScale);
         }

         return var10;
      }
   }

   @ObfuscatedName("j")
   @ObfuscatedSignature(
      descriptor = "(S)Lgm;",
      garbageValue = "7106"
   )
   @Export("getModelData")
   public final ModelData getModelData() {
      if (this.transforms != null) {
         NPCComposition var5 = this.transform();
         return var5 == null ? null : var5.getModelData();
      } else if (this.field1638 == null) {
         return null;
      } else {
         boolean var1 = false;

         for(int var2 = 0; var2 < this.field1638.length; ++var2) {
            if (!NpcDefinition_modelArchive.tryLoadFile(this.field1638[var2], 0)) {
               var1 = true;
            }
         }

         if (var1) {
            return null;
         } else {
            ModelData[] var6 = new ModelData[this.field1638.length];

            for(int var3 = 0; var3 < this.field1638.length; ++var3) {
               var6[var3] = ModelData.ModelData_get(NpcDefinition_modelArchive, this.field1638[var3], 0);
            }

            ModelData var7;
            if (var6.length == 1) {
               var7 = var6[0];
            } else {
               var7 = new ModelData(var6, var6.length);
            }

            int var4;
            if (this.recolorFrom != null) {
               for(var4 = 0; var4 < this.recolorFrom.length; ++var4) {
                  var7.recolor(this.recolorFrom[var4], this.recolorTo[var4]);
               }
            }

            if (this.retextureFrom != null) {
               for(var4 = 0; var4 < this.retextureFrom.length; ++var4) {
                  var7.retexture(this.retextureFrom[var4], this.retextureTo[var4]);
               }
            }

            return var7;
         }
      }
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      descriptor = "(B)Lef;",
      garbageValue = "5"
   )
   @Export("transform")
   public final NPCComposition transform() {
      int var1 = -1;
      if (this.transformVarbit != -1) {
         var1 = Skeleton.getVarbit(this.transformVarbit);
      } else if (this.transformVarp != -1) {
         var1 = Varps.Varps_main[this.transformVarp];
      }

      int var2;
      if (var1 >= 0 && var1 < this.transforms.length - 1) {
         var2 = this.transforms[var1];
      } else {
         var2 = this.transforms[this.transforms.length - 1];
      }

      return var2 != -1 ? StructComposition.getNpcDefinition(var2) : null;
   }

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      descriptor = "(I)Z",
      garbageValue = "-1279569027"
   )
   @Export("transformIsVisible")
   public boolean transformIsVisible() {
      if (this.transforms == null) {
         return true;
      } else {
         int var1 = -1;
         if (this.transformVarbit != -1) {
            var1 = Skeleton.getVarbit(this.transformVarbit);
         } else if (this.transformVarp != -1) {
            var1 = Varps.Varps_main[this.transformVarp];
         }

         if (var1 >= 0 && var1 < this.transforms.length) {
            return this.transforms[var1] != -1;
         } else {
            return this.transforms[this.transforms.length - 1] != -1;
         }
      }
   }

   @ObfuscatedName("d")
   @ObfuscatedSignature(
      descriptor = "(III)I",
      garbageValue = "506916452"
   )
   @Export("getIntParam")
   public int getIntParam(int var1, int var2) {
      return SecureRandomFuture.method1977(this.params, var1, var2);
   }

   @ObfuscatedName("s")
   @ObfuscatedSignature(
      descriptor = "(ILjava/lang/String;I)Ljava/lang/String;",
      garbageValue = "-614305753"
   )
   @Export("getStringParam")
   public String getStringParam(int var1, String var2) {
      return FriendsList.method5650(this.params, var1, var2);
   }

   @ObfuscatedName("kb")
   @ObfuscatedSignature(
      descriptor = "(Ljava/lang/String;S)V",
      garbageValue = "-1922"
   )
   @Export("Clan_joinChat")
   static final void Clan_joinChat(String var0) {
      if (!var0.equals("")) {
         PacketBufferNode var1 = class21.getPacketBufferNode(ClientPacket.field2574, Client.packetWriter.isaacCipher);
         var1.packetBuffer.writeByte(Tiles.stringCp1252NullTerminatedByteSize(var0));
         var1.packetBuffer.writeStringCp1252NullTerminated(var0);
         Client.packetWriter.addNode(var1);
      }

   }

   @ObfuscatedName("lu")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "-1165253281"
   )
   static void method2791() {
      if (Client.field724 && class93.localPlayer != null) {
         int var0 = class93.localPlayer.pathX[0];
         int var1 = class93.localPlayer.pathY[0];
         if (var0 < 0 || var1 < 0 || var0 >= 104 || var1 >= 104) {
            return;
         }

         RouteStrategy.oculusOrbFocalPointX = class93.localPlayer.x;
         int var2 = class105.getTileHeight(class93.localPlayer.x, class93.localPlayer.y, class22.Client_plane) - Client.camFollowHeight;
         if (var2 < class17.field148) {
            class17.field148 = var2;
         }

         ModelData0.oculusOrbFocalPointY = class93.localPlayer.y;
         Client.field724 = false;
      }

   }
}
