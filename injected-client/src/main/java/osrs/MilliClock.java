package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ek")
@Implements("MilliClock")
public class MilliClock extends Clock {
   @ObfuscatedName("b")
   @Export("SpriteBuffer_spritePalette")
   public static int[] SpriteBuffer_spritePalette;
   @ObfuscatedName("s")
   @ObfuscatedSignature(
      descriptor = "Ldf;"
   )
   @Export("World_request")
   static UrlRequest World_request;
   @ObfuscatedName("v")
   long[] field1519 = new long[10];
   @ObfuscatedName("n")
   @ObfuscatedGetter(
      intValue = 1403302325
   )
   int field1515 = 256;
   @ObfuscatedName("f")
   @ObfuscatedGetter(
      intValue = 892183401
   )
   int field1517 = 1;
   @ObfuscatedName("y")
   @ObfuscatedGetter(
      longValue = -3130930086544390357L
   )
   long field1516 = ObjectComposition.currentTimeMillis();
   @ObfuscatedName("p")
   @ObfuscatedGetter(
      intValue = 543039723
   )
   int field1518 = 0;
   @ObfuscatedName("j")
   @ObfuscatedGetter(
      intValue = 652562423
   )
   int field1514;

   public MilliClock() {
      for(int var1 = 0; var1 < 10; ++var1) {
         this.field1519[var1] = this.field1516;
      }

   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "-1887937791"
   )
   @Export("mark")
   public void mark() {
      for(int var1 = 0; var1 < 10; ++var1) {
         this.field1519[var1] = 0L;
      }

   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "(III)I",
      garbageValue = "-1841090229"
   )
   @Export("wait")
   public int wait(int var1, int var2) {
      int var3 = this.field1515;
      int var4 = this.field1517;
      this.field1515 = 300;
      this.field1517 = 1;
      this.field1516 = ObjectComposition.currentTimeMillis();
      if (0L == this.field1519[this.field1514]) {
         this.field1515 = var3;
         this.field1517 = var4;
      } else if (this.field1516 > this.field1519[this.field1514]) {
         this.field1515 = (int)((long)(var1 * 2560) / (this.field1516 - this.field1519[this.field1514]));
      }

      if (this.field1515 < 25) {
         this.field1515 = 25;
      }

      if (this.field1515 > 256) {
         this.field1515 = 256;
         this.field1517 = (int)((long)var1 - (this.field1516 - this.field1519[this.field1514]) / 10L);
      }

      if (this.field1517 > var1) {
         this.field1517 = var1;
      }

      this.field1519[this.field1514] = this.field1516;
      this.field1514 = (this.field1514 + 1) % 10;
      if (this.field1517 > 1) {
         for(int var5 = 0; var5 < 10; ++var5) {
            if (0L != this.field1519[var5]) {
               this.field1519[var5] += (long)this.field1517;
            }
         }
      }

      if (this.field1517 < var2) {
         this.field1517 = var2;
      }

      long var13 = (long)this.field1517;
      if (var13 > 0L) {
         if (0L == var13 % 10L) {
            long var7 = var13 - 1L;

            try {
               Thread.sleep(var7);
            } catch (InterruptedException var12) {
               ;
            }

            try {
               Thread.sleep(1L);
            } catch (InterruptedException var11) {
               ;
            }
         } else {
            try {
               Thread.sleep(var13);
            } catch (InterruptedException var10) {
               ;
            }
         }
      }

      int var14;
      for(var14 = 0; this.field1518 < 256; this.field1518 += this.field1515) {
         ++var14;
      }

      this.field1518 &= 255;
      return var14;
   }

   @ObfuscatedName("e")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "179304235"
   )
   public static void method2587() {
      WorldMapDecoration.SpriteBuffer_xOffsets = null;
      Calendar.SpriteBuffer_yOffsets = null;
      class396.SpriteBuffer_spriteWidths = null;
      class302.SpriteBuffer_spriteHeights = null;
      SpriteBuffer_spritePalette = null;
      class396.SpriteBuffer_pixels = (byte[][])null;
   }

   @ObfuscatedName("fs")
   @ObfuscatedSignature(
      descriptor = "(B)V",
      garbageValue = "47"
   )
   @Export("load")
   static void load() {
      int var0;
      if (Client.titleLoadingStage == 0) {
         AbstractSocket.scene = new Scene(4, 104, 104, Tiles.Tiles_heights);

         for(var0 = 0; var0 < 4; ++var0) {
            Client.collisionMaps[var0] = new CollisionMap(104, 104);
         }

         GameEngine.sceneMinimapSprite = new SpritePixels(512, 512);
         Login.Login_loadingText = "Starting game engine...";
         Login.Login_loadingPercent = 5;
         Client.titleLoadingStage = 20;
      } else if (Client.titleLoadingStage == 20) {
         Login.Login_loadingText = "Prepared visibility map";
         Login.Login_loadingPercent = 10;
         Client.titleLoadingStage = 30;
      } else if (Client.titleLoadingStage == 30) {
         NetCache.archive0 = FontName.newArchive(0, false, true, true);
         class27.archive1 = FontName.newArchive(1, false, true, true);
         class8.archive2 = FontName.newArchive(2, true, false, true);
         ClanChannel.archive3 = FontName.newArchive(3, false, true, true);
         class32.archive4 = FontName.newArchive(4, false, true, true);
         class247.archive5 = FontName.newArchive(5, true, true, true);
         UrlRequest.archive6 = FontName.newArchive(6, true, true, true);
         Decimator.archive7 = FontName.newArchive(7, false, true, true);
         GrandExchangeOfferAgeComparator.archive8 = FontName.newArchive(8, false, true, true);
         CollisionMap.archive9 = FontName.newArchive(9, false, true, true);
         SoundCache.archive10 = FontName.newArchive(10, false, true, true);
         WorldMapManager.archive11 = FontName.newArchive(11, false, true, true);
         UserComparator5.archive12 = FontName.newArchive(12, false, true, true);
         Decimator.archive13 = FontName.newArchive(13, true, false, true);
         class125.archive14 = FontName.newArchive(14, false, true, true);
         ClanMate.archive15 = FontName.newArchive(15, false, true, true);
         class8.archive17 = FontName.newArchive(17, true, true, true);
         FontName.archive18 = FontName.newArchive(18, false, true, true);
         class179.archive19 = FontName.newArchive(19, false, true, true);
         class5.archive20 = FontName.newArchive(20, false, true, true);
         Login.Login_loadingText = "Connecting to update server";
         Login.Login_loadingPercent = 20;
         Client.titleLoadingStage = 40;
      } else if (Client.titleLoadingStage != 40) {
         if (Client.titleLoadingStage == 45) {
            boolean var1 = !Client.isLowDetail;
            PcmPlayer.field428 = 22050;
            PcmPlayer.PcmPlayer_stereo = var1;
            PcmPlayer.field418 = 2;
            MidiPcmStream var2 = new MidiPcmStream();
            var2.method4502(9, 128);
            DesktopPlatformInfoProvider.pcmPlayer0 = HorizontalAlignment.method2762(GameEngine.taskHandler, 0, 22050);
            DesktopPlatformInfoProvider.pcmPlayer0.setStream(var2);
            WorldMapID.method3636(ClanMate.archive15, class125.archive14, class32.archive4, var2);
            MouseRecorder.pcmPlayer1 = HorizontalAlignment.method2762(GameEngine.taskHandler, 1, 2048);
            class308.pcmStreamMixer = new PcmStreamMixer();
            MouseRecorder.pcmPlayer1.setStream(class308.pcmStreamMixer);
            TileItem.decimator = new Decimator(22050, PcmPlayer.field428);
            Login.Login_loadingText = "Prepared sound engine";
            Login.Login_loadingPercent = 35;
            Client.titleLoadingStage = 50;
            World.WorldMapElement_fonts = new Fonts(GrandExchangeOfferAgeComparator.archive8, Decimator.archive13);
         } else {
            int var27;
            if (Client.titleLoadingStage == 50) {
               FontName[] var29 = new FontName[]{FontName.FontName_plain12, FontName.FontName_plain11, FontName.FontName_verdana15, FontName.FontName_verdana13, FontName.FontName_verdana11, FontName.FontName_bold12};
               var27 = var29.length;
               Fonts var3 = World.WorldMapElement_fonts;
               FontName[] var4 = new FontName[]{FontName.FontName_plain12, FontName.FontName_plain11, FontName.FontName_verdana15, FontName.FontName_verdana13, FontName.FontName_verdana11, FontName.FontName_bold12};
               Client.fontsMap = var3.createMap(var4);
               if (Client.fontsMap.size() < var27) {
                  Login.Login_loadingText = "Loading fonts - " + Client.fontsMap.size() * 100 / var27 + "%";
                  Login.Login_loadingPercent = 40;
               } else {
                  Actor.fontPlain11 = (Font)Client.fontsMap.get(FontName.FontName_plain11);
                  UserComparator3.fontPlain12 = (Font)Client.fontsMap.get(FontName.FontName_plain12);
                  Widget.fontBold12 = (Font)Client.fontsMap.get(FontName.FontName_bold12);
                  class10.platformInfo = Client.platformInfoProvider.get();
                  Login.Login_loadingText = "Loaded fonts";
                  Login.Login_loadingPercent = 40;
                  Client.titleLoadingStage = 60;
               }
            } else {
               Archive var5;
               int var30;
               int var31;
               Archive var32;
               if (Client.titleLoadingStage == 60) {
                  var32 = SoundCache.archive10;
                  var5 = GrandExchangeOfferAgeComparator.archive8;
                  var30 = 0;
                  if (var32.tryLoadFileByNames("title.jpg", "")) {
                     ++var30;
                  }

                  if (var5.tryLoadFileByNames("logo", "")) {
                     ++var30;
                  }

                  if (var5.tryLoadFileByNames("logo_deadman_mode", "")) {
                     ++var30;
                  }

                  if (var5.tryLoadFileByNames("logo_seasonal_mode", "")) {
                     ++var30;
                  }

                  if (var5.tryLoadFileByNames("titlebox", "")) {
                     ++var30;
                  }

                  if (var5.tryLoadFileByNames("titlebutton", "")) {
                     ++var30;
                  }

                  if (var5.tryLoadFileByNames("runes", "")) {
                     ++var30;
                  }

                  if (var5.tryLoadFileByNames("title_mute", "")) {
                     ++var30;
                  }

                  if (var5.tryLoadFileByNames("options_radio_buttons,0", "")) {
                     ++var30;
                  }

                  if (var5.tryLoadFileByNames("options_radio_buttons,2", "")) {
                     ++var30;
                  }

                  if (var5.tryLoadFileByNames("options_radio_buttons,4", "")) {
                     ++var30;
                  }

                  if (var5.tryLoadFileByNames("options_radio_buttons,6", "")) {
                     ++var30;
                  }

                  var5.tryLoadFileByNames("sl_back", "");
                  var5.tryLoadFileByNames("sl_flags", "");
                  var5.tryLoadFileByNames("sl_arrows", "");
                  var5.tryLoadFileByNames("sl_stars", "");
                  var5.tryLoadFileByNames("sl_button", "");
                  var31 = AbstractSocket.method5901();
                  if (var30 < var31) {
                     Login.Login_loadingText = "Loading title screen - " + var30 * 100 / var31 + "%";
                     Login.Login_loadingPercent = 50;
                  } else {
                     Login.Login_loadingText = "Loaded title screen";
                     Login.Login_loadingPercent = 50;
                     class12.updateGameState(5);
                     Client.titleLoadingStage = 70;
                  }
               } else if (Client.titleLoadingStage == 70) {
                  if (!class8.archive2.isFullyLoaded()) {
                     Login.Login_loadingText = "Loading config - " + class8.archive2.loadPercent() + "%";
                     Login.Login_loadingPercent = 60;
                  } else {
                     Archive var6 = class8.archive2;
                     class142.FloorOverlayDefinition_archive = var6;
                     class27.method277(class8.archive2);
                     ChatChannel.method2012(class8.archive2, Decimator.archive7);
                     var32 = class8.archive2;
                     var5 = Decimator.archive7;
                     boolean var7 = Client.isLowDetail;
                     ObjectComposition.ObjectDefinition_archive = var32;
                     class310.ObjectDefinition_modelsArchive = var5;
                     ObjectComposition.ObjectDefinition_isLowDetail = var7;
                     Archive var8 = class8.archive2;
                     Archive var9 = Decimator.archive7;
                     NPCComposition.NpcDefinition_archive = var8;
                     NPCComposition.NpcDefinition_modelArchive = var9;
                     class13.method180(class8.archive2);
                     Archive var10 = class8.archive2;
                     Archive var11 = Decimator.archive7;
                     boolean var12 = Client.isMembersWorld;
                     Font var13 = Actor.fontPlain11;
                     class341.ItemDefinition_archive = var10;
                     class311.ItemDefinition_modelArchive = var11;
                     ItemContainer.ItemDefinition_inMembersWorld = var12;
                     ItemComposition.ItemDefinition_fileCount = class341.ItemDefinition_archive.getGroupFileCount(10);
                     class288.ItemDefinition_fontPlain11 = var13;
                     Archive var14 = class8.archive2;
                     Archive var15 = NetCache.archive0;
                     Archive var16 = class27.archive1;
                     SequenceDefinition.SequenceDefinition_archive = var14;
                     SequenceDefinition.SequenceDefinition_animationsArchive = var15;
                     SequenceDefinition.SequenceDefinition_skeletonsArchive = var16;
                     class1.method13(class8.archive2, Decimator.archive7);
                     UserComparator10.method2471(class8.archive2);
                     class280.method5070(class8.archive2);
                     Archive var17 = ClanChannel.archive3;
                     Archive var18 = Decimator.archive7;
                     Archive var19 = GrandExchangeOfferAgeComparator.archive8;
                     Archive var20 = Decimator.archive13;
                     HealthBarUpdate.Widget_archive = var17;
                     SpriteMask.Widget_modelsArchive = var18;
                     class245.Widget_spritesArchive = var19;
                     VertexNormal.Widget_fontsArchive = var20;
                     Widget.Widget_interfaceComponents = new Widget[HealthBarUpdate.Widget_archive.getGroupCount()][];
                     Widget.Widget_loadedInterfaces = new boolean[HealthBarUpdate.Widget_archive.getGroupCount()];
                     LoginScreenAnimation.method2210(class8.archive2);
                     GrandExchangeOfferUnitPriceComparator.method5109(class8.archive2);
                     PacketWriter.method2384(class8.archive2);
                     Archive var21 = class8.archive2;
                     ParamComposition.ParamDefinition_archive = var21;
                     GrandExchangeOfferOwnWorldComparator.field634 = new class369(SoundSystem.field461, 54, class378.clientLanguage, class8.archive2);
                     ViewportMouse.HitSplatDefinition_cachedSprites = new class369(SoundSystem.field461, 47, class378.clientLanguage, class8.archive2);
                     GrandExchangeOfferOwnWorldComparator.varcs = new Varcs();
                     Archive var22 = class8.archive2;
                     Archive var23 = GrandExchangeOfferAgeComparator.archive8;
                     Archive var24 = Decimator.archive13;
                     HitSplatDefinition.HitSplatDefinition_archive = var22;
                     HitSplatDefinition.field1734 = var23;
                     HitSplatDefinition.HitSplatDefinition_fontsArchive = var24;
                     Archive var25 = class8.archive2;
                     Archive var26 = GrandExchangeOfferAgeComparator.archive8;
                     HealthBarDefinition.HealthBarDefinition_archive = var25;
                     HealthBarDefinition.HitSplatDefinition_spritesArchive = var26;
                     UserComparator5.method2449(class8.archive2, GrandExchangeOfferAgeComparator.archive8);
                     Login.Login_loadingText = "Loaded config";
                     Login.Login_loadingPercent = 60;
                     Client.titleLoadingStage = 80;
                  }
               } else if (Client.titleLoadingStage == 80) {
                  var0 = 0;
                  if (NPCComposition.compass == null) {
                     NPCComposition.compass = ClientPacket.SpriteBuffer_getSprite(GrandExchangeOfferAgeComparator.archive8, TaskHandler.spriteIds.compass, 0);
                  } else {
                     ++var0;
                  }

                  if (Interpreter.redHintArrowSprite == null) {
                     Interpreter.redHintArrowSprite = ClientPacket.SpriteBuffer_getSprite(GrandExchangeOfferAgeComparator.archive8, TaskHandler.spriteIds.field3897, 0);
                  } else {
                     ++var0;
                  }

                  if (class10.mapSceneSprites == null) {
                     class10.mapSceneSprites = VerticalAlignment.method2799(GrandExchangeOfferAgeComparator.archive8, TaskHandler.spriteIds.mapScenes, 0);
                  } else {
                     ++var0;
                  }

                  if (class35.headIconPkSprites == null) {
                     class35.headIconPkSprites = ItemComposition.method3087(GrandExchangeOfferAgeComparator.archive8, TaskHandler.spriteIds.headIconsPk, 0);
                  } else {
                     ++var0;
                  }

                  if (TextureProvider.headIconPrayerSprites == null) {
                     TextureProvider.headIconPrayerSprites = ItemComposition.method3087(GrandExchangeOfferAgeComparator.archive8, TaskHandler.spriteIds.field3900, 0);
                  } else {
                     ++var0;
                  }

                  if (PacketBufferNode.headIconHintSprites == null) {
                     PacketBufferNode.headIconHintSprites = ItemComposition.method3087(GrandExchangeOfferAgeComparator.archive8, TaskHandler.spriteIds.field3901, 0);
                  } else {
                     ++var0;
                  }

                  if (WorldMapArea.mapMarkerSprites == null) {
                     WorldMapArea.mapMarkerSprites = ItemComposition.method3087(GrandExchangeOfferAgeComparator.archive8, TaskHandler.spriteIds.field3905, 0);
                  } else {
                     ++var0;
                  }

                  if (ArchiveLoader.crossSprites == null) {
                     ArchiveLoader.crossSprites = ItemComposition.method3087(GrandExchangeOfferAgeComparator.archive8, TaskHandler.spriteIds.field3903, 0);
                  } else {
                     ++var0;
                  }

                  if (class170.mapDotSprites == null) {
                     class170.mapDotSprites = ItemComposition.method3087(GrandExchangeOfferAgeComparator.archive8, TaskHandler.spriteIds.field3904, 0);
                  } else {
                     ++var0;
                  }

                  if (Player.scrollBarSprites == null) {
                     Player.scrollBarSprites = VerticalAlignment.method2799(GrandExchangeOfferAgeComparator.archive8, TaskHandler.spriteIds.field3898, 0);
                  } else {
                     ++var0;
                  }

                  if (WorldMapDecoration.modIconSprites == null) {
                     WorldMapDecoration.modIconSprites = VerticalAlignment.method2799(GrandExchangeOfferAgeComparator.archive8, TaskHandler.spriteIds.field3906, 0);
                  } else {
                     ++var0;
                  }

                  if (var0 < 11) {
                     Login.Login_loadingText = "Loading sprites - " + var0 * 100 / 12 + "%";
                     Login.Login_loadingPercent = 70;
                  } else {
                     AbstractFont.AbstractFont_modIconSprites = WorldMapDecoration.modIconSprites;
                     Interpreter.redHintArrowSprite.normalize();
                     var27 = (int)(Math.random() * 21.0D) - 10;
                     int var33 = (int)(Math.random() * 21.0D) - 10;
                     var30 = (int)(Math.random() * 21.0D) - 10;
                     var31 = (int)(Math.random() * 41.0D) - 20;
                     class10.mapSceneSprites[0].shiftColors(var27 + var31, var33 + var31, var31 + var30);
                     Login.Login_loadingText = "Loaded sprites";
                     Login.Login_loadingPercent = 70;
                     Client.titleLoadingStage = 90;
                  }
               } else if (Client.titleLoadingStage == 90) {
                  if (!CollisionMap.archive9.isFullyLoaded()) {
                     Login.Login_loadingText = "Loading textures - 0%";
                     Login.Login_loadingPercent = 90;
                  } else {
                     World.textureProvider = new TextureProvider(CollisionMap.archive9, GrandExchangeOfferAgeComparator.archive8, 20, ObjectComposition.clientPreferences.field1337, Client.isLowDetail ? 64 : 128);
                     Rasterizer3D.Rasterizer3D_setTextureLoader(World.textureProvider);
                     Rasterizer3D.Rasterizer3D_setBrightness(ObjectComposition.clientPreferences.field1337);
                     Client.titleLoadingStage = 100;
                  }
               } else if (Client.titleLoadingStage == 100) {
                  var0 = World.textureProvider.getLoadedPercentage();
                  if (var0 < 100) {
                     Login.Login_loadingText = "Loading textures - " + var0 + "%";
                     Login.Login_loadingPercent = 90;
                  } else {
                     Login.Login_loadingText = "Loaded textures";
                     Login.Login_loadingPercent = 90;
                     Client.titleLoadingStage = 110;
                  }
               } else if (Client.titleLoadingStage == 110) {
                  Skills.mouseRecorder = new MouseRecorder();
                  GameEngine.taskHandler.newThreadTask(Skills.mouseRecorder, 10);
                  Login.Login_loadingText = "Loaded input handler";
                  Login.Login_loadingPercent = 92;
                  Client.titleLoadingStage = 120;
               } else if (Client.titleLoadingStage == 120) {
                  if (!SoundCache.archive10.tryLoadFileByNames("huffman", "")) {
                     Login.Login_loadingText = "Loading wordpack - 0%";
                     Login.Login_loadingPercent = 94;
                  } else {
                     Huffman var34 = new Huffman(SoundCache.archive10.takeFileByNames("huffman", ""));
                     ClanChannelMember.method88(var34);
                     Login.Login_loadingText = "Loaded wordpack";
                     Login.Login_loadingPercent = 94;
                     Client.titleLoadingStage = 130;
                  }
               } else if (Client.titleLoadingStage == 130) {
                  if (!ClanChannel.archive3.isFullyLoaded()) {
                     Login.Login_loadingText = "Loading interfaces - " + ClanChannel.archive3.loadPercent() * 4 / 5 + "%";
                     Login.Login_loadingPercent = 96;
                  } else if (!UserComparator5.archive12.isFullyLoaded()) {
                     Login.Login_loadingText = "Loading interfaces - " + (80 + UserComparator5.archive12.loadPercent() / 6) + "%";
                     Login.Login_loadingPercent = 96;
                  } else if (!Decimator.archive13.isFullyLoaded()) {
                     Login.Login_loadingText = "Loading interfaces - " + (96 + Decimator.archive13.loadPercent() / 50) + "%";
                     Login.Login_loadingPercent = 96;
                  } else {
                     Login.Login_loadingText = "Loaded interfaces";
                     Login.Login_loadingPercent = 98;
                     Client.titleLoadingStage = 140;
                  }
               } else if (Client.titleLoadingStage == 140) {
                  Login.Login_loadingPercent = 100;
                  if (!class179.archive19.tryLoadGroupByName(WorldMapCacheName.field2137.name)) {
                     Login.Login_loadingText = "Loading world map - " + class179.archive19.groupLoadPercentByName(WorldMapCacheName.field2137.name) / 10 + "%";
                  } else {
                     if (class243.worldMap == null) {
                        class243.worldMap = new WorldMap();
                        class243.worldMap.init(class179.archive19, FontName.archive18, class5.archive20, Widget.fontBold12, Client.fontsMap, class10.mapSceneSprites);
                     }

                     Login.Login_loadingText = "Loaded world map";
                     Client.titleLoadingStage = 150;
                  }
               } else if (Client.titleLoadingStage == 150) {
                  class12.updateGameState(10);
               }
            }
         }
      } else {
         byte var28 = 0;
         var0 = var28 + NetCache.archive0.percentage() * 4 / 100;
         var0 += class27.archive1.percentage() * 4 / 100;
         var0 += class8.archive2.percentage() * 2 / 100;
         var0 += ClanChannel.archive3.percentage() * 2 / 100;
         var0 += class32.archive4.percentage() * 6 / 100;
         var0 += class247.archive5.percentage() * 4 / 100;
         var0 += UrlRequest.archive6.percentage() * 2 / 100;
         var0 += Decimator.archive7.percentage() * 56 / 100;
         var0 += GrandExchangeOfferAgeComparator.archive8.percentage() * 2 / 100;
         var0 += CollisionMap.archive9.percentage() * 2 / 100;
         var0 += SoundCache.archive10.percentage() * 2 / 100;
         var0 += WorldMapManager.archive11.percentage() * 2 / 100;
         var0 += UserComparator5.archive12.percentage() * 2 / 100;
         var0 += Decimator.archive13.percentage() * 2 / 100;
         var0 += class125.archive14.percentage() * 2 / 100;
         var0 += ClanMate.archive15.percentage() * 2 / 100;
         var0 += class179.archive19.percentage() / 100;
         var0 += FontName.archive18.percentage() / 100;
         var0 += class5.archive20.percentage() / 100;
         var0 += class8.archive17.method4869() && class8.archive17.isFullyLoaded() ? 1 : 0;
         if (var0 != 100) {
            if (var0 != 0) {
               Login.Login_loadingText = "Checking for updates - " + var0 + "%";
            }

            Login.Login_loadingPercent = 30;
         } else {
            class13.method175(NetCache.archive0, "Animations");
            class13.method175(class27.archive1, "Skeletons");
            class13.method175(class32.archive4, "Sound FX");
            class13.method175(class247.archive5, "Maps");
            class13.method175(UrlRequest.archive6, "Music Tracks");
            class13.method175(Decimator.archive7, "Models");
            class13.method175(GrandExchangeOfferAgeComparator.archive8, "Sprites");
            class13.method175(WorldMapManager.archive11, "Music Jingles");
            class13.method175(class125.archive14, "Music Samples");
            class13.method175(ClanMate.archive15, "Music Patches");
            class13.method175(class179.archive19, "World Map");
            class13.method175(FontName.archive18, "World Map Geography");
            class13.method175(class5.archive20, "World Map Ground");
            TaskHandler.spriteIds = new GraphicsDefaults();
            TaskHandler.spriteIds.decode(class8.archive17);
            Login.Login_loadingText = "Loaded update list";
            Login.Login_loadingPercent = 30;
            Client.titleLoadingStage = 45;
         }
      }

   }

   @ObfuscatedName("is")
   @ObfuscatedSignature(
      descriptor = "(I)I",
      garbageValue = "-1594517863"
   )
   static final int method2588() {
      return Client.menuOptionsCount - 1;
   }
}
