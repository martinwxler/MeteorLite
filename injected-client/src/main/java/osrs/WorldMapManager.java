package osrs;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("ff")
@Implements("WorldMapManager")
public final class WorldMapManager {
   @ObfuscatedName("dp")
   @ObfuscatedSignature(
      descriptor = "Ljp;"
   )
   @Export("archive11")
   static Archive archive11;
   @ObfuscatedName("v")
   @Export("loaded")
   boolean loaded = false;
   @ObfuscatedName("n")
   @Export("loadStarted")
   boolean loadStarted = false;
   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "Lgz;"
   )
   @Export("mapAreaData")
   WorldMapAreaData mapAreaData;
   @ObfuscatedName("y")
   @ObfuscatedSignature(
      descriptor = "Loh;"
   )
   @Export("compositeTextureSprite")
   SpritePixels compositeTextureSprite;
   @ObfuscatedName("p")
   @Export("icons")
   HashMap icons;
   @ObfuscatedName("j")
   @ObfuscatedSignature(
      descriptor = "[[Lfq;"
   )
   @Export("regions")
   WorldMapRegion[][] regions;
   @ObfuscatedName("r")
   @Export("scaleHandlers")
   HashMap scaleHandlers = new HashMap();
   @ObfuscatedName("b")
   @ObfuscatedSignature(
      descriptor = "[Lop;"
   )
   @Export("mapSceneSprites")
   IndexedSprite[] mapSceneSprites;
   @ObfuscatedName("d")
   @ObfuscatedSignature(
      descriptor = "Ljv;"
   )
   @Export("geographyArchive")
   final AbstractArchive geographyArchive;
   @ObfuscatedName("s")
   @ObfuscatedSignature(
      descriptor = "Ljv;"
   )
   @Export("groundArchive")
   final AbstractArchive groundArchive;
   @ObfuscatedName("u")
   @Export("fonts")
   final HashMap fonts;
   @ObfuscatedName("l")
   @ObfuscatedGetter(
      intValue = 758122357
   )
   @Export("tileX")
   int tileX;
   @ObfuscatedName("o")
   @ObfuscatedGetter(
      intValue = 197299753
   )
   @Export("tileY")
   int tileY;
   @ObfuscatedName("c")
   @ObfuscatedGetter(
      intValue = 274838579
   )
   @Export("tileWidth")
   int tileWidth;
   @ObfuscatedName("e")
   @ObfuscatedGetter(
      intValue = 1210430205
   )
   @Export("tileHeight")
   int tileHeight;
   @ObfuscatedName("g")
   @ObfuscatedGetter(
      intValue = -978895597
   )
   @Export("pixelsPerTile")
   public int pixelsPerTile = 0;

   @ObfuscatedSignature(
      descriptor = "([Lop;Ljava/util/HashMap;Ljv;Ljv;)V"
   )
   public WorldMapManager(IndexedSprite[] var1, HashMap var2, AbstractArchive var3, AbstractArchive var4) {
      this.mapSceneSprites = var1;
      this.fonts = var2;
      this.geographyArchive = var3;
      this.groundArchive = var4;
   }

   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "(Ljv;Ljava/lang/String;ZB)V",
      garbageValue = "-19"
   )
   @Export("load")
   public void load(AbstractArchive var1, String var2, boolean var3) {
      if (!this.loadStarted) {
         this.loaded = false;
         this.loadStarted = true;
         System.nanoTime();
         int var4 = var1.getGroupId(WorldMapCacheName.field2137.name);
         int var5 = var1.getFileId(var4, var2);
         Buffer var6 = new Buffer(var1.takeFileByNames(WorldMapCacheName.field2137.name, var2));
         Buffer var7 = new Buffer(var1.takeFileByNames(WorldMapCacheName.field2132.name, var2));
         System.nanoTime();
         System.nanoTime();
         this.mapAreaData = new WorldMapAreaData();

         try {
            this.mapAreaData.init(var6, var7, var5, var3);
         } catch (IllegalStateException var16) {
            return;
         }

         this.mapAreaData.getOriginX();
         this.mapAreaData.getOriginPlane();
         this.mapAreaData.getOriginY();
         this.tileX = this.mapAreaData.getRegionLowX() * 64;
         this.tileY = this.mapAreaData.getRegionLowY() * 64;
         this.tileWidth = (this.mapAreaData.getRegionHighX() - this.mapAreaData.getRegionLowX() + 1) * 64;
         this.tileHeight = (this.mapAreaData.getRegionHighY() - this.mapAreaData.getRegionLowY() + 1) * 64;
         int var8 = this.mapAreaData.getRegionHighX() - this.mapAreaData.getRegionLowX() + 1;
         int var9 = this.mapAreaData.getRegionHighY() - this.mapAreaData.getRegionLowY() + 1;
         System.nanoTime();
         System.nanoTime();
         WorldMapRegion.WorldMapRegion_cachedSprites.clear();
         this.regions = new WorldMapRegion[var8][var9];
         Iterator var10 = this.mapAreaData.worldMapData0Set.iterator();

         int var12;
         while(var10.hasNext()) {
            WorldMapData_0 var11 = (WorldMapData_0)var10.next();
            var12 = var11.regionX;
            int var13 = var11.regionY;
            int var14 = var12 - this.mapAreaData.getRegionLowX();
            int var15 = var13 - this.mapAreaData.getRegionLowY();
            this.regions[var14][var15] = new WorldMapRegion(var12, var13, this.mapAreaData.getBackGroundColor(), this.fonts);
            this.regions[var14][var15].initWorldMapData0(var11, this.mapAreaData.iconList);
         }

         for(int var17 = 0; var17 < var8; ++var17) {
            for(var12 = 0; var12 < var9; ++var12) {
               if (this.regions[var17][var12] == null) {
                  this.regions[var17][var12] = new WorldMapRegion(this.mapAreaData.getRegionLowX() + var17, this.mapAreaData.getRegionLowY() + var12, this.mapAreaData.getBackGroundColor(), this.fonts);
                  this.regions[var17][var12].initWorldMapData1(this.mapAreaData.worldMapData1Set, this.mapAreaData.iconList);
               }
            }
         }

         System.nanoTime();
         System.nanoTime();
         if (var1.isValidFileName(WorldMapCacheName.field2134.name, var2)) {
            byte[] var18 = var1.takeFileByNames(WorldMapCacheName.field2134.name, var2);
            this.compositeTextureSprite = ParamComposition.convertJpgToSprite(var18);
         }

         System.nanoTime();
         var1.clearGroups();
         var1.clearFiles();
         this.loaded = true;
      }

   }

   @ObfuscatedName("n")
   @ObfuscatedSignature(
      descriptor = "(S)V",
      garbageValue = "768"
   )
   @Export("clearIcons")
   public final void clearIcons() {
      this.icons = null;
   }

   @ObfuscatedName("f")
   @ObfuscatedSignature(
      descriptor = "(IIIIIIIII)V",
      garbageValue = "181464110"
   )
   @Export("drawTiles")
   public final void drawTiles(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8) {
      int[] var9 = Rasterizer2D.Rasterizer2D_pixels;
      int var10 = Rasterizer2D.Rasterizer2D_width;
      int var11 = Rasterizer2D.Rasterizer2D_height;
      int[] var12 = new int[4];
      Rasterizer2D.Rasterizer2D_getClipArray(var12);
      WorldMapRectangle var13 = this.createWorldMapRectangle(var1, var2, var3, var4);
      float var14 = this.getPixelsPerTile(var7 - var5, var3 - var1);
      int var15 = (int)Math.ceil((double)var14);
      this.pixelsPerTile = var15;
      if (!this.scaleHandlers.containsKey(var15)) {
         WorldMapScaleHandler var16 = new WorldMapScaleHandler(var15);
         var16.init();
         this.scaleHandlers.put(var15, var16);
      }

      int var23 = var13.width + var13.x - 1;
      int var17 = var13.height + var13.y - 1;

      int var18;
      int var19;
      for(var18 = var13.x; var18 <= var23; ++var18) {
         for(var19 = var13.y; var19 <= var17; ++var19) {
            this.regions[var18][var19].drawTile(var15, (WorldMapScaleHandler)this.scaleHandlers.get(var15), this.mapSceneSprites, this.geographyArchive, this.groundArchive);
         }
      }

      Rasterizer2D.Rasterizer2D_replace(var9, var10, var11);
      Rasterizer2D.Rasterizer2D_setClipArray(var12);
      var18 = (int)(var14 * 64.0F);
      var19 = this.tileX + var1;
      int var20 = var2 + this.tileY;

      for(int var21 = var13.x; var21 < var13.width + var13.x; ++var21) {
         for(int var22 = var13.y; var22 < var13.height + var13.y; ++var22) {
            this.regions[var21][var22].method3328(var5 + (this.regions[var21][var22].regionX * 64 - var19) * var18 / 64, var8 - (this.regions[var21][var22].regionY * 64 - var20 + 64) * var18 / 64, var18);
         }
      }

   }

   @ObfuscatedName("y")
   @ObfuscatedSignature(
      descriptor = "(IIIIIIIILjava/util/HashSet;Ljava/util/HashSet;IIZI)V",
      garbageValue = "-1554077956"
   )
   @Export("drawElements")
   public final void drawElements(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, HashSet var9, HashSet var10, int var11, int var12, boolean var13) {
      WorldMapRectangle var14 = this.createWorldMapRectangle(var1, var2, var3, var4);
      float var15 = this.getPixelsPerTile(var7 - var5, var3 - var1);
      int var16 = (int)(var15 * 64.0F);
      int var17 = this.tileX + var1;
      int var18 = var2 + this.tileY;

      int var19;
      int var20;
      for(var19 = var14.x; var19 < var14.x + var14.width; ++var19) {
         for(var20 = var14.y; var20 < var14.height + var14.y; ++var20) {
            if (var13) {
               this.regions[var19][var20].initWorldMapIcon1s();
            }

            this.regions[var19][var20].method3353(var5 + (this.regions[var19][var20].regionX * 64 - var17) * var16 / 64, var8 - (this.regions[var19][var20].regionY * 64 - var18 + 64) * var16 / 64, var16, var9);
         }
      }

      if (var10 != null && var11 > 0) {
         for(var19 = var14.x; var19 < var14.width + var14.x; ++var19) {
            for(var20 = var14.y; var20 < var14.height + var14.y; ++var20) {
               this.regions[var19][var20].flashElements(var10, var11, var12);
            }
         }
      }

   }

   @ObfuscatedName("p")
   @ObfuscatedSignature(
      descriptor = "(IIIILjava/util/HashSet;III)V",
      garbageValue = "-397966592"
   )
   @Export("drawOverview")
   public void drawOverview(int var1, int var2, int var3, int var4, HashSet var5, int var6, int var7) {
      if (this.compositeTextureSprite != null) {
         this.compositeTextureSprite.drawScaledAt(var1, var2, var3, var4);
         if (var6 > 0 && var6 % var7 < var7 / 2) {
            if (this.icons == null) {
               this.buildIcons0();
            }

            Iterator var8 = var5.iterator();

            while(true) {
               List var9;
               do {
                  if (!var8.hasNext()) {
                     return;
                  }

                  int var10 = (Integer)var8.next();
                  var9 = (List)this.icons.get(var10);
               } while(var9 == null);

               Iterator var14 = var9.iterator();

               while(var14.hasNext()) {
                  AbstractWorldMapIcon var11 = (AbstractWorldMapIcon)var14.next();
                  int var12 = var3 * (var11.coord2.x - this.tileX) / this.tileWidth;
                  int var13 = var4 - (var11.coord2.y - this.tileY) * var4 / this.tileHeight;
                  Rasterizer2D.Rasterizer2D_drawCircleAlpha(var12 + var1, var13 + var2, 2, 16776960, 256);
               }
            }
         }
      }

   }

   @ObfuscatedName("j")
   @ObfuscatedSignature(
      descriptor = "(IIIIIIIIIII)Ljava/util/List;",
      garbageValue = "533098241"
   )
   public List method3465(int var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10) {
      LinkedList var11 = new LinkedList();
      if (!this.loaded) {
         return var11;
      } else {
         WorldMapRectangle var12 = this.createWorldMapRectangle(var1, var2, var3, var4);
         float var13 = this.getPixelsPerTile(var7, var3 - var1);
         int var14 = (int)(var13 * 64.0F);
         int var15 = this.tileX + var1;
         int var16 = var2 + this.tileY;

         for(int var17 = var12.x; var17 < var12.x + var12.width; ++var17) {
            for(int var18 = var12.y; var18 < var12.y + var12.height; ++var18) {
               List var19 = this.regions[var17][var18].method3359(var5 + (this.regions[var17][var18].regionX * 64 - var15) * var14 / 64, var8 + var6 - (this.regions[var17][var18].regionY * 64 - var16 + 64) * var14 / 64, var14, var9, var10);
               if (!var19.isEmpty()) {
                  var11.addAll(var19);
               }
            }
         }

         return var11;
      }
   }

   @ObfuscatedName("r")
   @ObfuscatedSignature(
      descriptor = "(IIIIB)Lfu;",
      garbageValue = "-6"
   )
   @Export("createWorldMapRectangle")
   WorldMapRectangle createWorldMapRectangle(int var1, int var2, int var3, int var4) {
      WorldMapRectangle var5 = new WorldMapRectangle(this);
      int var6 = this.tileX + var1;
      int var7 = var2 + this.tileY;
      int var8 = var3 + this.tileX;
      int var9 = var4 + this.tileY;
      int var10 = var6 / 64;
      int var11 = var7 / 64;
      int var12 = var8 / 64;
      int var13 = var9 / 64;
      var5.width = var12 - var10 + 1;
      var5.height = var13 - var11 + 1;
      var5.x = var10 - this.mapAreaData.getRegionLowX();
      var5.y = var11 - this.mapAreaData.getRegionLowY();
      if (var5.x < 0) {
         var5.width += var5.x;
         var5.x = 0;
      }

      if (var5.x > this.regions.length - var5.width) {
         var5.width = this.regions.length - var5.x;
      }

      if (var5.y < 0) {
         var5.height += var5.y;
         var5.y = 0;
      }

      if (var5.y > this.regions[0].length - var5.height) {
         var5.height = this.regions[0].length - var5.y;
      }

      var5.width = Math.min(var5.width, this.regions.length);
      var5.height = Math.min(var5.height, this.regions[0].length);
      return var5;
   }

   @ObfuscatedName("b")
   @ObfuscatedSignature(
      descriptor = "(I)Z",
      garbageValue = "831698872"
   )
   @Export("isLoaded")
   public boolean isLoaded() {
      return this.loaded;
   }

   @ObfuscatedName("d")
   @ObfuscatedSignature(
      descriptor = "(I)Ljava/util/HashMap;",
      garbageValue = "1724645516"
   )
   @Export("buildIcons")
   public HashMap buildIcons() {
      this.buildIcons0();
      return this.icons;
   }

   @ObfuscatedName("s")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "582634997"
   )
   @Export("buildIcons0")
   void buildIcons0() {
      if (this.icons == null) {
         this.icons = new HashMap();
      }

      this.icons.clear();

      for(int var1 = 0; var1 < this.regions.length; ++var1) {
         for(int var2 = 0; var2 < this.regions[var1].length; ++var2) {
            List var3 = this.regions[var1][var2].icons();
            Iterator var4 = var3.iterator();

            while(var4.hasNext()) {
               AbstractWorldMapIcon var5 = (AbstractWorldMapIcon)var4.next();
               if (var5.hasValidElement()) {
                  int var6 = var5.getElement();
                  if (!this.icons.containsKey(var6)) {
                     LinkedList var7 = new LinkedList();
                     var7.add(var5);
                     this.icons.put(var6, var7);
                  } else {
                     List var8 = (List)this.icons.get(var6);
                     var8.add(var5);
                  }
               }
            }
         }
      }

   }

   @ObfuscatedName("u")
   @ObfuscatedSignature(
      descriptor = "(IIB)F",
      garbageValue = "20"
   )
   @Export("getPixelsPerTile")
   float getPixelsPerTile(int var1, int var2) {
      float var3 = (float)var1 / (float)var2;
      if (var3 > 8.0F) {
         return 8.0F;
      } else if (var3 < 1.0F) {
         return 1.0F;
      } else {
         int var4 = Math.round(var3);
         return Math.abs((float)var4 - var3) < 0.05F ? (float)var4 : var3;
      }
   }

   @ObfuscatedName("j")
   @ObfuscatedSignature(
      descriptor = "(Laf;B)V",
      garbageValue = "-51"
   )
   @Export("doCycleTitle")
   static void doCycleTitle(GameEngine var0) {
      if (Login.worldSelectOpen) {
         class20.method230(var0);
      } else {
         if ((MouseHandler.MouseHandler_lastButton == 1 || !Client.mouseCam && MouseHandler.MouseHandler_lastButton == 4) && MouseHandler.MouseHandler_lastPressedX >= Login.xPadding + 765 - 50 && MouseHandler.MouseHandler_lastPressedY >= 453) {
            ObjectComposition.clientPreferences.titleMusicDisabled = !ObjectComposition.clientPreferences.titleMusicDisabled;
            TileItem.savePreferences();
            if (!ObjectComposition.clientPreferences.titleMusicDisabled) {
               Archive var1 = UrlRequest.archive6;
               int var2 = var1.getGroupId("scape main");
               int var3 = var1.getFileId(var2, "");
               LoginScreenAnimation.method2219(var1, var2, var3, 255, false);
            } else {
               class124.midiPcmStream.clear();
               class232.musicPlayerStatus = 1;
               ModelData0.musicTrackArchive = null;
            }
         }

         if (Client.gameState != 5) {
            if (Login.field1050 == -1L) {
               Login.field1050 = ObjectComposition.currentTimeMillis() + 1000L;
            }

            long var26 = ObjectComposition.currentTimeMillis();
            boolean var27;
            if (Client.archiveLoaders != null && Client.archiveLoadersDone < Client.archiveLoaders.size()) {
               label884: {
                  while(Client.archiveLoadersDone < Client.archiveLoaders.size()) {
                     ArchiveLoader var4 = (ArchiveLoader)Client.archiveLoaders.get(Client.archiveLoadersDone);
                     if (!var4.isLoaded()) {
                        var27 = false;
                        break label884;
                     }

                     ++Client.archiveLoadersDone;
                  }

                  var27 = true;
               }
            } else {
               var27 = true;
            }

            if (var27 && -1L == Login.field1022) {
               Login.field1022 = var26;
               if (Login.field1022 > Login.field1050) {
                  Login.field1050 = Login.field1022;
               }
            }

            if (Client.gameState == 10 || Client.gameState == 11) {
               int var28;
               if (Language.Language_EN == class378.clientLanguage) {
                  if (MouseHandler.MouseHandler_lastButton == 1 || !Client.mouseCam && MouseHandler.MouseHandler_lastButton == 4) {
                     var28 = Login.xPadding + 5;
                     short var5 = 463;
                     byte var6 = 100;
                     byte var7 = 35;
                     if (MouseHandler.MouseHandler_lastPressedX >= var28 && MouseHandler.MouseHandler_lastPressedX <= var28 + var6 && MouseHandler.MouseHandler_lastPressedY >= var5 && MouseHandler.MouseHandler_lastPressedY <= var5 + var7) {
                        VarbitComposition.method2859();
                        return;
                     }
                  }

                  if (MilliClock.World_request != null) {
                     VarbitComposition.method2859();
                  }
               }

               var28 = MouseHandler.MouseHandler_lastButton;
               int var29 = MouseHandler.MouseHandler_lastPressedX;
               int var30 = MouseHandler.MouseHandler_lastPressedY;
               if (var28 == 0) {
                  var29 = MouseHandler.MouseHandler_x;
                  var30 = MouseHandler.MouseHandler_y;
               }

               if (!Client.mouseCam && var28 == 4) {
                  var28 = 1;
               }

               int var8;
               short var31;
               if (Login.loginIndex == 0) {
                  boolean var9 = false;

                  while(ClanChannelMember.isKeyDown()) {
                     if (ItemComposition.field1859 == 84) {
                        var9 = true;
                     }
                  }

                  var8 = UserComparator4.loginBoxCenter - 80;
                  var31 = 291;
                  if (var28 == 1 && var29 >= var8 - 75 && var29 <= var8 + 75 && var30 >= var31 - 20 && var30 <= var31 + 20) {
                     Players.openURL(KitDefinition.method2705("secure", true) + "m=account-creation/g=oldscape/create_account_funnel.ws", true, false);
                  }

                  var8 = UserComparator4.loginBoxCenter + 80;
                  if (var28 == 1 && var29 >= var8 - 75 && var29 <= var8 + 75 && var30 >= var31 - 20 && var30 <= var31 + 20 || var9) {
                     if ((Client.worldProperties & 33554432) != 0) {
                        Login.Login_response0 = "";
                        Login.Login_response1 = "This is a <col=00ffff>Beta<col=ffffff> world.";
                        Login.Login_response2 = "Your normal account will not be affected.";
                        Login.Login_response3 = "";
                        Login.loginIndex = 1;
                        if (Client.Login_isUsernameRemembered && Login.Login_username != null && Login.Login_username.length() > 0) {
                           Login.currentLoginField = 1;
                        } else {
                           Login.currentLoginField = 0;
                        }
                     } else if ((Client.worldProperties & 4) != 0) {
                        if ((Client.worldProperties & 1024) != 0) {
                           Login.Login_response1 = "This is a <col=ffff00>High Risk <col=ff0000>PvP<col=ffffff> world.";
                           Login.Login_response2 = "Players can attack each other almost everywhere";
                           Login.Login_response3 = "and the Protect Item prayer won't work.";
                        } else {
                           Login.Login_response1 = "This is a <col=ff0000>PvP<col=ffffff> world.";
                           Login.Login_response2 = "Players can attack each other";
                           Login.Login_response3 = "almost everywhere.";
                        }

                        Login.Login_response0 = "Warning!";
                        Login.loginIndex = 1;
                        if (Client.Login_isUsernameRemembered && Login.Login_username != null && Login.Login_username.length() > 0) {
                           Login.currentLoginField = 1;
                        } else {
                           Login.currentLoginField = 0;
                        }
                     } else if ((Client.worldProperties & 1024) != 0) {
                        Login.Login_response1 = "This is a <col=ffff00>High Risk<col=ffffff> world.";
                        Login.Login_response2 = "The Protect Item prayer will";
                        Login.Login_response3 = "not work on this world.";
                        Login.Login_response0 = "Warning!";
                        Login.loginIndex = 1;
                        if (Client.Login_isUsernameRemembered && Login.Login_username != null && Login.Login_username.length() > 0) {
                           Login.currentLoginField = 1;
                        } else {
                           Login.currentLoginField = 0;
                        }
                     } else {
                        ClanChannelMember.Login_promptCredentials(false);
                     }
                  }
               } else {
                  int var10;
                  short var32;
                  if (Login.loginIndex != 1) {
                     int var11;
                     short var12;
                     boolean var13;
                     int var14;
                     if (Login.loginIndex == 2) {
                        var12 = 201;
                        var10 = var12 + 52;
                        if (var28 == 1 && var30 >= var10 - 12 && var30 < var10 + 2) {
                           Login.currentLoginField = 0;
                        }

                        var10 += 15;
                        if (var28 == 1 && var30 >= var10 - 12 && var30 < var10 + 2) {
                           Login.currentLoginField = 1;
                        }

                        var10 += 15;
                        var12 = 361;
                        if (DynamicObject.field1107 != null) {
                           var8 = DynamicObject.field1107.highX / 2;
                           if (var28 == 1 && var29 >= DynamicObject.field1107.lowX - var8 && var29 <= var8 + DynamicObject.field1107.lowX && var30 >= var12 - 15 && var30 < var12) {
                              switch(Login.field1039) {
                              case 1:
                                 class260.setLoginResponseString("Please enter your username.", "If you created your account after November", "2010, this will be the creation email address.");
                                 Login.loginIndex = 5;
                                 return;
                              case 2:
                                 Players.openURL("https://support.runescape.com/hc/en-gb", true, false);
                              }
                           }
                        }

                        var8 = UserComparator4.loginBoxCenter - 80;
                        var31 = 321;
                        if (var28 == 1 && var29 >= var8 - 75 && var29 <= var8 + 75 && var30 >= var31 - 20 && var30 <= var31 + 20) {
                           Login.Login_username = Login.Login_username.trim();
                           if (Login.Login_username.length() == 0) {
                              class260.setLoginResponseString("", "Please enter your username/email address.", "");
                              return;
                           }

                           if (Login.Login_password.length() == 0) {
                              class260.setLoginResponseString("", "Please enter your password.", "");
                              return;
                           }

                           class260.setLoginResponseString("", "Connecting to server...", "");
                           VarbitComposition.method2849(false);
                           class12.updateGameState(20);
                           return;
                        }

                        var8 = Login.loginBoxX + 180 + 80;
                        if (var28 == 1 && var29 >= var8 - 75 && var29 <= var8 + 75 && var30 >= var31 - 20 && var30 <= var31 + 20) {
                           Login.loginIndex = 0;
                           Login.Login_username = "";
                           Login.Login_password = "";
                           class5.field53 = 0;
                           Varps.otp = "";
                           Login.field1043 = true;
                        }

                        var8 = UserComparator4.loginBoxCenter + -117;
                        var31 = 277;
                        Login.field1041 = var29 >= var8 && var29 < var8 + Skeleton.field2287 && var30 >= var31 && var30 < var31 + WorldMapIcon_1.field1937;
                        if (var28 == 1 && Login.field1041) {
                           Client.Login_isUsernameRemembered = !Client.Login_isUsernameRemembered;
                           if (!Client.Login_isUsernameRemembered && ObjectComposition.clientPreferences.rememberedUsername != null) {
                              ObjectComposition.clientPreferences.rememberedUsername = null;
                              TileItem.savePreferences();
                           }
                        }

                        var8 = UserComparator4.loginBoxCenter + 24;
                        var31 = 277;
                        Login.field1047 = var29 >= var8 && var29 < var8 + Skeleton.field2287 && var30 >= var31 && var30 < var31 + WorldMapIcon_1.field1937;
                        if (var28 == 1 && Login.field1047) {
                           ObjectComposition.clientPreferences.hideUsername = !ObjectComposition.clientPreferences.hideUsername;
                           if (!ObjectComposition.clientPreferences.hideUsername) {
                              Login.Login_username = "";
                              ObjectComposition.clientPreferences.rememberedUsername = null;
                              if (Client.Login_isUsernameRemembered && Login.Login_username != null && Login.Login_username.length() > 0) {
                                 Login.currentLoginField = 1;
                              } else {
                                 Login.currentLoginField = 0;
                              }
                           }

                           TileItem.savePreferences();
                        }

                        while(true) {
                           int var18;
                           char var19;
                           Transferable var36;
                           do {
                              while(true) {
                                 char var16;
                                 label654:
                                 do {
                                    while(true) {
                                       while(ClanChannelMember.isKeyDown()) {
                                          if (ItemComposition.field1859 != 13) {
                                             if (Login.currentLoginField != 0) {
                                                continue label654;
                                             }

                                             var16 = class249.field3116;

                                             for(var11 = 0; var11 < "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"£$%^&*()-_=+[{]};:'@#~,<.>/?\\| ".length() && var16 != "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"£$%^&*()-_=+[{]};:'@#~,<.>/?\\| ".charAt(var11); ++var11) {
                                                ;
                                             }

                                             if (ItemComposition.field1859 == 85 && Login.Login_username.length() > 0) {
                                                Login.Login_username = Login.Login_username.substring(0, Login.Login_username.length() - 1);
                                             }

                                             if (ItemComposition.field1859 == 84 || ItemComposition.field1859 == 80) {
                                                Login.currentLoginField = 1;
                                             }

                                             if (DevicePcmPlayerProvider.method385(class249.field3116) && Login.Login_username.length() < 320) {
                                                Login.Login_username = Login.Login_username + class249.field3116;
                                             }
                                          } else {
                                             Login.loginIndex = 0;
                                             Login.Login_username = "";
                                             Login.Login_password = "";
                                             class5.field53 = 0;
                                             Varps.otp = "";
                                             Login.field1043 = true;
                                          }
                                       }

                                       return;
                                    }
                                 } while(Login.currentLoginField != 1);

                                 if (ItemComposition.field1859 == 85 && Login.Login_password.length() > 0) {
                                    Login.Login_password = Login.Login_password.substring(0, Login.Login_password.length() - 1);
                                 } else if (ItemComposition.field1859 == 84 || ItemComposition.field1859 == 80) {
                                    Login.currentLoginField = 0;
                                    if (ItemComposition.field1859 == 84) {
                                       Login.Login_username = Login.Login_username.trim();
                                       if (Login.Login_username.length() == 0) {
                                          class260.setLoginResponseString("", "Please enter your username/email address.", "");
                                          return;
                                       }

                                       if (Login.Login_password.length() == 0) {
                                          class260.setLoginResponseString("", "Please enter your password.", "");
                                          return;
                                       }

                                       class260.setLoginResponseString("", "Connecting to server...", "");
                                       VarbitComposition.method2849(false);
                                       class12.updateGameState(20);
                                       return;
                                    }
                                 }

                                 if ((KeyHandler.KeyHandler_pressedKeys[82] || KeyHandler.KeyHandler_pressedKeys[87]) && ItemComposition.field1859 == 67) {
                                    Clipboard var34 = Toolkit.getDefaultToolkit().getSystemClipboard();
                                    var36 = var34.getContents(class23.client);
                                    var14 = 20 - Login.Login_password.length();
                                    break;
                                 }

                                 var16 = class249.field3116;
                                 if (var16 >= ' ' && var16 < 127 || var16 > 127 && var16 < 160 || var16 > 160 && var16 <= 255) {
                                    var13 = true;
                                 } else {
                                    label968: {
                                       if (var16 != 0) {
                                          char[] var17 = class301.cp1252AsciiExtension;

                                          for(var18 = 0; var18 < var17.length; ++var18) {
                                             var19 = var17[var18];
                                             if (var16 == var19) {
                                                var13 = true;
                                                break label968;
                                             }
                                          }
                                       }

                                       var13 = false;
                                    }
                                 }

                                 if (var13 && DevicePcmPlayerProvider.method385(class249.field3116) && Login.Login_password.length() < 20) {
                                    Login.Login_password = Login.Login_password + class249.field3116;
                                 }
                              }
                           } while(var14 <= 0);

                           try {
                              String var35 = (String)var36.getTransferData(DataFlavor.stringFlavor);
                              int var37 = Math.min(var14, var35.length());

                              for(var18 = 0; var18 < var37; ++var18) {
                                 var19 = var35.charAt(var18);
                                 boolean var20;
                                 if ((var19 < ' ' || var19 >= 127) && (var19 <= 127 || var19 >= 160) && (var19 <= 160 || var19 > 255)) {
                                    label956: {
                                       if (var19 != 0) {
                                          char[] var21 = class301.cp1252AsciiExtension;

                                          for(int var22 = 0; var22 < var21.length; ++var22) {
                                             char var23 = var21[var22];
                                             if (var23 == var19) {
                                                var20 = true;
                                                break label956;
                                             }
                                          }
                                       }

                                       var20 = false;
                                    }
                                 } else {
                                    var20 = true;
                                 }

                                 if (!var20 || !DevicePcmPlayerProvider.method385(var35.charAt(var18))) {
                                    Login.loginIndex = 3;
                                    return;
                                 }
                              }

                              Login.Login_password = Login.Login_password + var35.substring(0, var37);
                           } catch (UnsupportedFlavorException var24) {
                              ;
                           } catch (IOException var25) {
                              ;
                           }
                        }
                     }

                     if (Login.loginIndex == 3) {
                        var10 = Login.loginBoxX + 180;
                        var32 = 276;
                        if (var28 == 1 && var29 >= var10 - 75 && var29 <= var10 + 75 && var30 >= var32 - 20 && var30 <= var32 + 20) {
                           ClanChannelMember.Login_promptCredentials(false);
                        }

                        var10 = Login.loginBoxX + 180;
                        var32 = 326;
                        if (var28 == 1 && var29 >= var10 - 75 && var29 <= var10 + 75 && var30 >= var32 - 20 && var30 <= var32 + 20) {
                           class260.setLoginResponseString("Please enter your username.", "If you created your account after November", "2010, this will be the creation email address.");
                           Login.loginIndex = 5;
                           return;
                        }
                     } else if (Login.loginIndex == 4) {
                        var10 = Login.loginBoxX + 180 - 80;
                        var32 = 321;
                        if (var28 == 1 && var29 >= var10 - 75 && var29 <= var10 + 75 && var30 >= var32 - 20 && var30 <= var32 + 20) {
                           Varps.otp.trim();
                           if (Varps.otp.length() != 6) {
                              class260.setLoginResponseString("", "Please enter a 6-digit PIN.", "");
                              return;
                           }

                           class5.field53 = Integer.parseInt(Varps.otp);
                           Varps.otp = "";
                           VarbitComposition.method2849(true);
                           class260.setLoginResponseString("", "Connecting to server...", "");
                           class12.updateGameState(20);
                           return;
                        }

                        if (var28 == 1 && var29 >= Login.loginBoxX + 180 - 9 && var29 <= Login.loginBoxX + 180 + 130 && var30 >= 263 && var30 <= 296) {
                           Login.field1043 = !Login.field1043;
                        }

                        if (var28 == 1 && var29 >= Login.loginBoxX + 180 - 34 && var29 <= Login.loginBoxX + 34 + 180 && var30 >= 351 && var30 <= 363) {
                           Players.openURL(KitDefinition.method2705("secure", true) + "m=totp-authenticator/disableTOTPRequest", true, false);
                        }

                        var10 = Login.loginBoxX + 180 + 80;
                        if (var28 == 1 && var29 >= var10 - 75 && var29 <= var10 + 75 && var30 >= var32 - 20 && var30 <= var32 + 20) {
                           Login.loginIndex = 0;
                           Login.Login_username = "";
                           Login.Login_password = "";
                           class5.field53 = 0;
                           Varps.otp = "";
                        }

                        while(ClanChannelMember.isKeyDown()) {
                           boolean var15 = false;

                           for(var14 = 0; var14 < "1234567890".length(); ++var14) {
                              if (class249.field3116 == "1234567890".charAt(var14)) {
                                 var15 = true;
                                 break;
                              }
                           }

                           if (ItemComposition.field1859 == 13) {
                              Login.loginIndex = 0;
                              Login.Login_username = "";
                              Login.Login_password = "";
                              class5.field53 = 0;
                              Varps.otp = "";
                           } else {
                              if (ItemComposition.field1859 == 85 && Varps.otp.length() > 0) {
                                 Varps.otp = Varps.otp.substring(0, Varps.otp.length() - 1);
                              }

                              if (ItemComposition.field1859 == 84) {
                                 Varps.otp.trim();
                                 if (Varps.otp.length() != 6) {
                                    class260.setLoginResponseString("", "Please enter a 6-digit PIN.", "");
                                    return;
                                 }

                                 class5.field53 = Integer.parseInt(Varps.otp);
                                 Varps.otp = "";
                                 VarbitComposition.method2849(true);
                                 class260.setLoginResponseString("", "Connecting to server...", "");
                                 class12.updateGameState(20);
                                 return;
                              }

                              if (var15 && Varps.otp.length() < 6) {
                                 Varps.otp = Varps.otp + class249.field3116;
                              }
                           }
                        }
                     } else if (Login.loginIndex == 5) {
                        var10 = Login.loginBoxX + 180 - 80;
                        var32 = 321;
                        if (var28 == 1 && var29 >= var10 - 75 && var29 <= var10 + 75 && var30 >= var32 - 20 && var30 <= var32 + 20) {
                           ClanMate.method5712();
                           return;
                        }

                        var10 = Login.loginBoxX + 180 + 80;
                        if (var28 == 1 && var29 >= var10 - 75 && var29 <= var10 + 75 && var30 >= var32 - 20 && var30 <= var32 + 20) {
                           ClanChannelMember.Login_promptCredentials(true);
                        }

                        var31 = 361;
                        if (GrandExchangeOfferOwnWorldComparator.field631 != null) {
                           var14 = GrandExchangeOfferOwnWorldComparator.field631.highX / 2;
                           if (var28 == 1 && var29 >= GrandExchangeOfferOwnWorldComparator.field631.lowX - var14 && var29 <= var14 + GrandExchangeOfferOwnWorldComparator.field631.lowX && var30 >= var31 - 15 && var30 < var31) {
                              Players.openURL(KitDefinition.method2705("secure", true) + "m=weblogin/g=oldscape/cant_log_in", true, false);
                           }
                        }

                        while(ClanChannelMember.isKeyDown()) {
                           var13 = false;

                           for(var11 = 0; var11 < "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"£$%^&*()-_=+[{]};:'@#~,<.>/?\\| ".length(); ++var11) {
                              if (class249.field3116 == "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!\"£$%^&*()-_=+[{]};:'@#~,<.>/?\\| ".charAt(var11)) {
                                 var13 = true;
                                 break;
                              }
                           }

                           if (ItemComposition.field1859 == 13) {
                              ClanChannelMember.Login_promptCredentials(true);
                           } else {
                              if (ItemComposition.field1859 == 85 && Login.Login_username.length() > 0) {
                                 Login.Login_username = Login.Login_username.substring(0, Login.Login_username.length() - 1);
                              }

                              if (ItemComposition.field1859 == 84) {
                                 ClanMate.method5712();
                                 return;
                              }

                              if (var13 && Login.Login_username.length() < 320) {
                                 Login.Login_username = Login.Login_username + class249.field3116;
                              }
                           }
                        }
                     } else {
                        if (Login.loginIndex == 6) {
                           while(true) {
                              do {
                                 if (!ClanChannelMember.isKeyDown()) {
                                    var12 = 321;
                                    if (var28 == 1 && var30 >= var12 - 20 && var30 <= var12 + 20) {
                                       ClanChannelMember.Login_promptCredentials(true);
                                    }

                                    return;
                                 }
                              } while(ItemComposition.field1859 != 84 && ItemComposition.field1859 != 13);

                              ClanChannelMember.Login_promptCredentials(true);
                           }
                        }

                        if (Login.loginIndex == 7) {
                           var10 = Login.loginBoxX + 180 - 80;
                           var32 = 321;
                           if (var28 == 1 && var29 >= var10 - 75 && var29 <= var10 + 75 && var30 >= var32 - 20 && var30 <= var32 + 20) {
                              Players.openURL(KitDefinition.method2705("secure", true) + "m=dob/set_dob.ws", true, false);
                              class260.setLoginResponseString("", "Page has opened in a new window.", "(Please check your popup blocker.)");
                              Login.loginIndex = 6;
                              return;
                           }

                           var10 = Login.loginBoxX + 180 + 80;
                           if (var28 == 1 && var29 >= var10 - 75 && var29 <= var10 + 75 && var30 >= var32 - 20 && var30 <= var32 + 20) {
                              ClanChannelMember.Login_promptCredentials(true);
                           }
                        } else if (Login.loginIndex == 8) {
                           var10 = Login.loginBoxX + 180 - 80;
                           var32 = 321;
                           if (var28 == 1 && var29 >= var10 - 75 && var29 <= var10 + 75 && var30 >= var32 - 20 && var30 <= var32 + 20) {
                              Players.openURL("https://www.jagex.com/terms/privacy", true, false);
                              class260.setLoginResponseString("", "Page has opened in a new window.", "(Please check your popup blocker.)");
                              Login.loginIndex = 6;
                              return;
                           }

                           var10 = Login.loginBoxX + 180 + 80;
                           if (var28 == 1 && var29 >= var10 - 75 && var29 <= var10 + 75 && var30 >= var32 - 20 && var30 <= var32 + 20) {
                              ClanChannelMember.Login_promptCredentials(true);
                           }
                        } else if (Login.loginIndex == 12) {
                           String var33 = "";
                           switch(Login.field1032) {
                           case 0:
                              var33 = "https://support.runescape.com/hc/en-gb/articles/115002238729-Account-Bans";
                              break;
                           case 1:
                              var33 = "https://support.runescape.com/hc/en-gb/articles/206103939-My-account-is-locked";
                              break;
                           default:
                              ClanChannelMember.Login_promptCredentials(false);
                           }

                           var8 = Login.loginBoxX + 180;
                           var31 = 276;
                           if (var28 == 1 && var29 >= var8 - 75 && var29 <= var8 + 75 && var30 >= var31 - 20 && var30 <= var31 + 20) {
                              Players.openURL(var33, true, false);
                              class260.setLoginResponseString("", "Page has opened in a new window.", "(Please check your popup blocker.)");
                              Login.loginIndex = 6;
                              return;
                           }

                           var8 = Login.loginBoxX + 180;
                           var31 = 326;
                           if (var28 == 1 && var29 >= var8 - 75 && var29 <= var8 + 75 && var30 >= var31 - 20 && var30 <= var31 + 20) {
                              ClanChannelMember.Login_promptCredentials(false);
                           }
                        } else if (Login.loginIndex == 24) {
                           var10 = Login.loginBoxX + 180;
                           var32 = 301;
                           if (var28 == 1 && var29 >= var10 - 75 && var29 <= var10 + 75 && var30 >= var32 - 20 && var30 <= var32 + 20) {
                              ClanChannelMember.Login_promptCredentials(false);
                           }
                        }
                     }
                  } else {
                     while(ClanChannelMember.isKeyDown()) {
                        if (ItemComposition.field1859 == 84) {
                           ClanChannelMember.Login_promptCredentials(false);
                        } else if (ItemComposition.field1859 == 13) {
                           Login.loginIndex = 0;
                        }
                     }

                     var10 = UserComparator4.loginBoxCenter - 80;
                     var32 = 321;
                     if (var28 == 1 && var29 >= var10 - 75 && var29 <= var10 + 75 && var30 >= var32 - 20 && var30 <= var32 + 20) {
                        ClanChannelMember.Login_promptCredentials(false);
                     }

                     var10 = UserComparator4.loginBoxCenter + 80;
                     if (var28 == 1 && var29 >= var10 - 75 && var29 <= var10 + 75 && var30 >= var32 - 20 && var30 <= var32 + 20) {
                        Login.loginIndex = 0;
                     }
                  }
               }
            }
         }
      }

   }

   @ObfuscatedName("s")
   @ObfuscatedSignature(
      descriptor = "(Ljava/lang/String;B)V",
      garbageValue = "-84"
   )
   static final void method3490(String var0) {
      StringBuilder var1 = (new StringBuilder()).append(var0);
      Object var2 = null;
      String var3 = var1.append(" is already on your friend list").toString();
      World.addGameMessage(30, "", var3);
   }

   @ObfuscatedName("jv")
   @ObfuscatedSignature(
      descriptor = "([Lio;IIIIIIII)V",
      garbageValue = "1695957245"
   )
   @Export("updateInterface")
   static final void updateInterface(Widget[] var0, int var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      for(int var8 = 0; var8 < var0.length; ++var8) {
         Widget var9 = var0[var8];
         if (var9 != null && var9.parentId == var1 && (!var9.isIf3 || var9.type == 0 || var9.hasListener || class21.getWidgetFlags(var9) != 0 || var9 == Client.clickedWidgetParent || var9.contentType == 1338)) {
            if (var9.isIf3) {
               if (HitSplatDefinition.isComponentHidden(var9)) {
                  continue;
               }
            } else if (var9.type == 0 && var9 != World.mousedOverWidgetIf1 && HitSplatDefinition.isComponentHidden(var9)) {
               continue;
            }

            int var10 = var9.x + var6;
            int var11 = var7 + var9.y;
            int var12;
            int var13;
            int var14;
            int var15;
            int var16;
            int var17;
            if (var9.type == 2) {
               var12 = var2;
               var13 = var3;
               var14 = var4;
               var15 = var5;
            } else {
               int var18;
               if (var9.type == 9) {
                  var18 = var10;
                  var16 = var11;
                  var17 = var10 + var9.width;
                  int var19 = var11 + var9.height;
                  if (var17 < var10) {
                     var18 = var17;
                     var17 = var10;
                  }

                  if (var19 < var11) {
                     var16 = var19;
                     var19 = var11;
                  }

                  ++var17;
                  ++var19;
                  var12 = var18 > var2 ? var18 : var2;
                  var13 = var16 > var3 ? var16 : var3;
                  var14 = var17 < var4 ? var17 : var4;
                  var15 = var19 < var5 ? var19 : var5;
               } else {
                  var18 = var10 + var9.width;
                  var16 = var11 + var9.height;
                  var12 = var10 > var2 ? var10 : var2;
                  var13 = var11 > var3 ? var11 : var3;
                  var14 = var18 < var4 ? var18 : var4;
                  var15 = var16 < var5 ? var16 : var5;
               }
            }

            if (var9 == Client.clickedWidget) {
               Client.field822 = true;
               Client.field854 = var10;
               Client.field804 = var11;
            }

            boolean var28 = false;
            if (var9.field3032) {
               switch(Client.field803) {
               case 0:
                  var28 = true;
               case 1:
               default:
                  break;
               case 2:
                  if (Client.field805 == var9.id >>> 16) {
                     var28 = true;
                  }
                  break;
               case 3:
                  if (var9.id == Client.field805) {
                     var28 = true;
                  }
               }
            }

            if (var28 || !var9.isIf3 || var12 < var14 && var13 < var15) {
               if (var9.isIf3) {
                  ScriptEvent var29;
                  if (var9.noClickThrough) {
                     if (MouseHandler.MouseHandler_x >= var12 && MouseHandler.MouseHandler_y >= var13 && MouseHandler.MouseHandler_x < var14 && MouseHandler.MouseHandler_y < var15) {
                        for(var29 = (ScriptEvent)Client.scriptEvents.last(); var29 != null; var29 = (ScriptEvent)Client.scriptEvents.previous()) {
                           if (var29.isMouseInputEvent) {
                              var29.remove();
                              var29.widget.containsMouse = false;
                           }
                        }

                        if (Actor.widgetDragDuration == 0) {
                           Client.clickedWidget = null;
                           Client.clickedWidgetParent = null;
                        }

                        if (!Client.isMenuOpen) {
                           NetSocket.addCancelMenuEntry();
                        }
                     }
                  } else if (var9.noScrollThrough && MouseHandler.MouseHandler_x >= var12 && MouseHandler.MouseHandler_y >= var13 && MouseHandler.MouseHandler_x < var14 && MouseHandler.MouseHandler_y < var15) {
                     for(var29 = (ScriptEvent)Client.scriptEvents.last(); var29 != null; var29 = (ScriptEvent)Client.scriptEvents.previous()) {
                        if (var29.isMouseInputEvent && var29.widget.onScroll == var29.args) {
                           var29.remove();
                        }
                     }
                  }
               }

               var16 = MouseHandler.MouseHandler_x;
               var17 = MouseHandler.MouseHandler_y;
               if (MouseHandler.MouseHandler_lastButton != 0) {
                  var16 = MouseHandler.MouseHandler_lastPressedX;
                  var17 = MouseHandler.MouseHandler_lastPressedY;
               }

               boolean var30 = var16 >= var12 && var17 >= var13 && var16 < var14 && var17 < var15;
               if (var9.contentType == 1337) {
                  if (!Client.isLoading && !Client.isMenuOpen && var30) {
                     class2.addSceneMenuOptions(var16, var17, var12, var13);
                  }
               } else if (var9.contentType == 1338) {
                  class69.checkIfMinimapClicked(var9, var10, var11);
               } else {
                  if (var9.contentType == 1400) {
                     class243.worldMap.onCycle(MouseHandler.MouseHandler_x, MouseHandler.MouseHandler_y, var30, var10, var11, var9.width, var9.height);
                  }

                  if (!Client.isMenuOpen && var30) {
                     if (var9.contentType == 1400) {
                        class243.worldMap.addElementMenuOptions(var10, var11, var9.width, var9.height, var16, var17);
                     } else {
                        HealthBarUpdate.Widget_addToMenu(var9, var16 - var10, var17 - var11);
                     }
                  }

                  boolean var20;
                  int var21;
                  if (var28) {
                     for(int var22 = 0; var22 < var9.field3033.length; ++var22) {
                        var20 = false;
                        boolean var23 = false;
                        if (!var20 && var9.field3033[var22] != null) {
                           for(var21 = 0; var21 < var9.field3033[var22].length; ++var21) {
                              boolean var24 = false;
                              if (var9.field3012 != null) {
                                 var24 = KeyHandler.KeyHandler_pressedKeys[var9.field3033[var22][var21]];
                              }

                              if (NPC.method2258(var9.field3033[var22][var21]) || var24) {
                                 var20 = true;
                                 if (var9.field3012 != null && var9.field3012[var22] > Client.cycle) {
                                    break;
                                 }

                                 byte var25 = var9.field3034[var22][var21];
                                 if (var25 == 0 || ((var25 & 8) == 0 || !KeyHandler.KeyHandler_pressedKeys[86] && !KeyHandler.KeyHandler_pressedKeys[82] && !KeyHandler.KeyHandler_pressedKeys[81]) && ((var25 & 2) == 0 || KeyHandler.KeyHandler_pressedKeys[86]) && ((var25 & 1) == 0 || KeyHandler.KeyHandler_pressedKeys[82]) && ((var25 & 4) == 0 || KeyHandler.KeyHandler_pressedKeys[81])) {
                                    var23 = true;
                                    break;
                                 }
                              }
                           }
                        }

                        if (var23) {
                           if (var22 < 10) {
                              class376.widgetDefaultMenuAction(var22 + 1, var9.id, var9.childIndex, var9.itemId, "");
                           } else if (var22 == 10) {
                              class5.Widget_runOnTargetLeave();
                              LoginScreenAnimation.selectSpell(var9.id, var9.childIndex, AttackOption.Widget_unpackTargetMask(class21.getWidgetFlags(var9)), var9.itemId);
                              Client.selectedSpellActionName = BoundaryObject.Widget_getSpellActionName(var9);
                              if (Client.selectedSpellActionName == null) {
                                 Client.selectedSpellActionName = "null";
                              }

                              Client.selectedSpellName = var9.dataText + class44.colorStartTag(16777215);
                           }

                           var21 = var9.field3035[var22];
                           if (var9.field3012 == null) {
                              var9.field3012 = new int[var9.field3033.length];
                           }

                           if (var9.field3079 == null) {
                              var9.field3079 = new int[var9.field3033.length];
                           }

                           if (var21 != 0) {
                              if (var9.field3012[var22] == 0) {
                                 var9.field3012[var22] = var21 + Client.cycle + var9.field3079[var22];
                              } else {
                                 var9.field3012[var22] = var21 + Client.cycle;
                              }
                           } else {
                              var9.field3012[var22] = Integer.MAX_VALUE;
                           }
                        }

                        if (!var20 && var9.field3012 != null) {
                           var9.field3012[var22] = 0;
                        }
                     }
                  }

                  ScriptEvent var32;
                  if (var9.isIf3) {
                     if (MouseHandler.MouseHandler_x >= var12 && MouseHandler.MouseHandler_y >= var13 && MouseHandler.MouseHandler_x < var14 && MouseHandler.MouseHandler_y < var15) {
                        var30 = true;
                     } else {
                        var30 = false;
                     }

                     boolean var31 = false;
                     if ((MouseHandler.MouseHandler_currentButton == 1 || !Client.mouseCam && MouseHandler.MouseHandler_currentButton == 4) && var30) {
                        var31 = true;
                     }

                     var20 = false;
                     if ((MouseHandler.MouseHandler_lastButton == 1 || !Client.mouseCam && MouseHandler.MouseHandler_lastButton == 4) && MouseHandler.MouseHandler_lastPressedX >= var12 && MouseHandler.MouseHandler_lastPressedY >= var13 && MouseHandler.MouseHandler_lastPressedX < var14 && MouseHandler.MouseHandler_lastPressedY < var15) {
                        var20 = true;
                     }

                     if (var20) {
                        VarcInt.clickWidget(var9, MouseHandler.MouseHandler_lastPressedX - var10, MouseHandler.MouseHandler_lastPressedY - var11);
                     }

                     if (var9.contentType == 1400) {
                        class243.worldMap.method6188(var16, var17, var30 & var31, var30 & var20);
                     }

                     if (Client.clickedWidget != null && var9 != Client.clickedWidget && var30 && class170.method3552(class21.getWidgetFlags(var9))) {
                        Client.draggedOnWidget = var9;
                     }

                     if (var9 == Client.clickedWidgetParent) {
                        Client.field819 = true;
                        Client.field740 = var10;
                        Client.field821 = var11;
                     }

                     if (var9.hasListener) {
                        if (var30 && Client.mouseWheelRotation != 0 && var9.onScroll != null) {
                           var32 = new ScriptEvent();
                           var32.isMouseInputEvent = true;
                           var32.widget = var9;
                           var32.mouseY = Client.mouseWheelRotation;
                           var32.args = var9.onScroll;
                           Client.scriptEvents.addFirst(var32);
                        }

                        if (Client.clickedWidget != null || Script.dragInventoryWidget != null || Client.isMenuOpen) {
                           var20 = false;
                           var31 = false;
                           var30 = false;
                        }

                        if (!var9.isClicked && var20) {
                           var9.isClicked = true;
                           if (var9.onClick != null) {
                              var32 = new ScriptEvent();
                              var32.isMouseInputEvent = true;
                              var32.widget = var9;
                              var32.mouseX = MouseHandler.MouseHandler_lastPressedX - var10;
                              var32.mouseY = MouseHandler.MouseHandler_lastPressedY - var11;
                              var32.args = var9.onClick;
                              Client.scriptEvents.addFirst(var32);
                           }
                        }

                        if (var9.isClicked && var31 && var9.onClickRepeat != null) {
                           var32 = new ScriptEvent();
                           var32.isMouseInputEvent = true;
                           var32.widget = var9;
                           var32.mouseX = MouseHandler.MouseHandler_x - var10;
                           var32.mouseY = MouseHandler.MouseHandler_y - var11;
                           var32.args = var9.onClickRepeat;
                           Client.scriptEvents.addFirst(var32);
                        }

                        if (var9.isClicked && !var31) {
                           var9.isClicked = false;
                           if (var9.onRelease != null) {
                              var32 = new ScriptEvent();
                              var32.isMouseInputEvent = true;
                              var32.widget = var9;
                              var32.mouseX = MouseHandler.MouseHandler_x - var10;
                              var32.mouseY = MouseHandler.MouseHandler_y - var11;
                              var32.args = var9.onRelease;
                              Client.field846.addFirst(var32);
                           }
                        }

                        if (var31 && var9.onHold != null) {
                           var32 = new ScriptEvent();
                           var32.isMouseInputEvent = true;
                           var32.widget = var9;
                           var32.mouseX = MouseHandler.MouseHandler_x - var10;
                           var32.mouseY = MouseHandler.MouseHandler_y - var11;
                           var32.args = var9.onHold;
                           Client.scriptEvents.addFirst(var32);
                        }

                        if (!var9.containsMouse && var30) {
                           var9.containsMouse = true;
                           if (var9.onMouseOver != null) {
                              var32 = new ScriptEvent();
                              var32.isMouseInputEvent = true;
                              var32.widget = var9;
                              var32.mouseX = MouseHandler.MouseHandler_x - var10;
                              var32.mouseY = MouseHandler.MouseHandler_y - var11;
                              var32.args = var9.onMouseOver;
                              Client.scriptEvents.addFirst(var32);
                           }
                        }

                        if (var9.containsMouse && var30 && var9.onMouseRepeat != null) {
                           var32 = new ScriptEvent();
                           var32.isMouseInputEvent = true;
                           var32.widget = var9;
                           var32.mouseX = MouseHandler.MouseHandler_x - var10;
                           var32.mouseY = MouseHandler.MouseHandler_y - var11;
                           var32.args = var9.onMouseRepeat;
                           Client.scriptEvents.addFirst(var32);
                        }

                        if (var9.containsMouse && !var30) {
                           var9.containsMouse = false;
                           if (var9.onMouseLeave != null) {
                              var32 = new ScriptEvent();
                              var32.isMouseInputEvent = true;
                              var32.widget = var9;
                              var32.mouseX = MouseHandler.MouseHandler_x - var10;
                              var32.mouseY = MouseHandler.MouseHandler_y - var11;
                              var32.args = var9.onMouseLeave;
                              Client.field846.addFirst(var32);
                           }
                        }

                        if (var9.onTimer != null) {
                           var32 = new ScriptEvent();
                           var32.widget = var9;
                           var32.args = var9.onTimer;
                           Client.field652.addFirst(var32);
                        }

                        int var26;
                        ScriptEvent var34;
                        int var35;
                        if (var9.onVarTransmit != null && Client.changedVarpCount > var9.field2990) {
                           if (var9.varTransmitTriggers != null && Client.changedVarpCount - var9.field2990 <= 32) {
                              label602:
                              for(var26 = var9.field2990; var26 < Client.changedVarpCount; ++var26) {
                                 var21 = Client.changedVarps[var26 & 31];

                                 for(var35 = 0; var35 < var9.varTransmitTriggers.length; ++var35) {
                                    if (var21 == var9.varTransmitTriggers[var35]) {
                                       var34 = new ScriptEvent();
                                       var34.widget = var9;
                                       var34.args = var9.onVarTransmit;
                                       Client.scriptEvents.addFirst(var34);
                                       break label602;
                                    }
                                 }
                              }
                           } else {
                              var32 = new ScriptEvent();
                              var32.widget = var9;
                              var32.args = var9.onVarTransmit;
                              Client.scriptEvents.addFirst(var32);
                           }

                           var9.field2990 = Client.changedVarpCount;
                        }

                        if (var9.onInvTransmit != null && Client.field830 > var9.field3095) {
                           if (var9.invTransmitTriggers != null && Client.field830 - var9.field3095 <= 32) {
                              label578:
                              for(var26 = var9.field3095; var26 < Client.field830; ++var26) {
                                 var21 = Client.changedItemContainers[var26 & 31];

                                 for(var35 = 0; var35 < var9.invTransmitTriggers.length; ++var35) {
                                    if (var21 == var9.invTransmitTriggers[var35]) {
                                       var34 = new ScriptEvent();
                                       var34.widget = var9;
                                       var34.args = var9.onInvTransmit;
                                       Client.scriptEvents.addFirst(var34);
                                       break label578;
                                    }
                                 }
                              }
                           } else {
                              var32 = new ScriptEvent();
                              var32.widget = var9;
                              var32.args = var9.onInvTransmit;
                              Client.scriptEvents.addFirst(var32);
                           }

                           var9.field3095 = Client.field830;
                        }

                        if (var9.onStatTransmit != null && Client.changedSkillsCount > var9.field3096) {
                           if (var9.statTransmitTriggers != null && Client.changedSkillsCount - var9.field3096 <= 32) {
                              label554:
                              for(var26 = var9.field3096; var26 < Client.changedSkillsCount; ++var26) {
                                 var21 = Client.changedSkills[var26 & 31];

                                 for(var35 = 0; var35 < var9.statTransmitTriggers.length; ++var35) {
                                    if (var21 == var9.statTransmitTriggers[var35]) {
                                       var34 = new ScriptEvent();
                                       var34.widget = var9;
                                       var34.args = var9.onStatTransmit;
                                       Client.scriptEvents.addFirst(var34);
                                       break label554;
                                    }
                                 }
                              }
                           } else {
                              var32 = new ScriptEvent();
                              var32.widget = var9;
                              var32.args = var9.onStatTransmit;
                              Client.scriptEvents.addFirst(var32);
                           }

                           var9.field3096 = Client.changedSkillsCount;
                        }

                        if (Client.chatCycle > var9.field3104 && var9.onChatTransmit != null) {
                           var32 = new ScriptEvent();
                           var32.widget = var9;
                           var32.args = var9.onChatTransmit;
                           Client.scriptEvents.addFirst(var32);
                        }

                        if (Client.field836 > var9.field3104 && var9.onFriendTransmit != null) {
                           var32 = new ScriptEvent();
                           var32.widget = var9;
                           var32.args = var9.onFriendTransmit;
                           Client.scriptEvents.addFirst(var32);
                        }

                        if (Client.field844 > var9.field3104 && var9.onClanTransmit != null) {
                           var32 = new ScriptEvent();
                           var32.widget = var9;
                           var32.args = var9.onClanTransmit;
                           Client.scriptEvents.addFirst(var32);
                        }

                        if (Client.field654 > var9.field3104 && var9.field3043 != null) {
                           var32 = new ScriptEvent();
                           var32.widget = var9;
                           var32.args = var9.field3043;
                           Client.scriptEvents.addFirst(var32);
                        }

                        if (Client.field839 > var9.field3104 && var9.field3071 != null) {
                           var32 = new ScriptEvent();
                           var32.widget = var9;
                           var32.args = var9.field3071;
                           Client.scriptEvents.addFirst(var32);
                        }

                        if (Client.field840 > var9.field3104 && var9.onStockTransmit != null) {
                           var32 = new ScriptEvent();
                           var32.widget = var9;
                           var32.args = var9.onStockTransmit;
                           Client.scriptEvents.addFirst(var32);
                        }

                        if (Client.field748 > var9.field3104 && var9.field3077 != null) {
                           var32 = new ScriptEvent();
                           var32.widget = var9;
                           var32.args = var9.field3077;
                           Client.scriptEvents.addFirst(var32);
                        }

                        if (Client.field659 > var9.field3104 && var9.onMiscTransmit != null) {
                           var32 = new ScriptEvent();
                           var32.widget = var9;
                           var32.args = var9.onMiscTransmit;
                           Client.scriptEvents.addFirst(var32);
                        }

                        var9.field3104 = Client.cycleCntr;
                        if (var9.onKey != null) {
                           for(var26 = 0; var26 < Client.field806; ++var26) {
                              ScriptEvent var27 = new ScriptEvent();
                              var27.widget = var9;
                              var27.keyTyped = Client.field868[var26];
                              var27.keyPressed = Client.field867[var26];
                              var27.args = var9.onKey;
                              Client.scriptEvents.addFirst(var27);
                           }
                        }
                     }
                  }

                  if (!var9.isIf3) {
                     if (Client.clickedWidget != null || Script.dragInventoryWidget != null || Client.isMenuOpen) {
                        continue;
                     }

                     if ((var9.mouseOverRedirect >= 0 || var9.mouseOverColor != 0) && MouseHandler.MouseHandler_x >= var12 && MouseHandler.MouseHandler_y >= var13 && MouseHandler.MouseHandler_x < var14 && MouseHandler.MouseHandler_y < var15) {
                        if (var9.mouseOverRedirect >= 0) {
                           World.mousedOverWidgetIf1 = var0[var9.mouseOverRedirect];
                        } else {
                           World.mousedOverWidgetIf1 = var9;
                        }
                     }

                     if (var9.type == 8 && MouseHandler.MouseHandler_x >= var12 && MouseHandler.MouseHandler_y >= var13 && MouseHandler.MouseHandler_x < var14 && MouseHandler.MouseHandler_y < var15) {
                        ReflectionCheck.field609 = var9;
                     }

                     if (var9.scrollHeight > var9.height) {
                        class313.method5617(var9, var10 + var9.width, var11, var9.height, var9.scrollHeight, MouseHandler.MouseHandler_x, MouseHandler.MouseHandler_y);
                     }
                  }

                  if (var9.type == 0) {
                     updateInterface(var0, var9.id, var12, var13, var14, var15, var10 - var9.scrollX, var11 - var9.scrollY);
                     if (var9.children != null) {
                        updateInterface(var9.children, var9.id, var12, var13, var14, var15, var10 - var9.scrollX, var11 - var9.scrollY);
                     }

                     InterfaceParent var33 = (InterfaceParent)Client.interfaceParents.get((long)var9.id);
                     if (var33 != null) {
                        if (var33.type == 0 && MouseHandler.MouseHandler_x >= var12 && MouseHandler.MouseHandler_y >= var13 && MouseHandler.MouseHandler_x < var14 && MouseHandler.MouseHandler_y < var15 && !Client.isMenuOpen) {
                           for(var32 = (ScriptEvent)Client.scriptEvents.last(); var32 != null; var32 = (ScriptEvent)Client.scriptEvents.previous()) {
                              if (var32.isMouseInputEvent) {
                                 var32.remove();
                                 var32.widget.containsMouse = false;
                              }
                           }

                           if (Actor.widgetDragDuration == 0) {
                              Client.clickedWidget = null;
                              Client.clickedWidgetParent = null;
                           }

                           if (!Client.isMenuOpen) {
                              NetSocket.addCancelMenuEntry();
                           }
                        }

                        class32.updateRootInterface(var33.group, var12, var13, var14, var15, var10, var11);
                     }
                  }
               }
            }
         }
      }

   }
}
