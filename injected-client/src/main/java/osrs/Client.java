package osrs;

import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import net.runelite.api.BufferProvider;
import net.runelite.api.ChatMessageType;
import net.runelite.api.CollisionData;
import net.runelite.api.FriendsChatManager;
import net.runelite.api.GameState;
import net.runelite.api.HashTable;
import net.runelite.api.HintArrowType;
import net.runelite.api.IndexDataBase;
import net.runelite.api.InventoryID;
import net.runelite.api.IterableHashTable;
import net.runelite.api.MapElementConfig;
import net.runelite.api.MenuEntry;
import net.runelite.api.MessageNode;
import net.runelite.api.NameableContainer;
import net.runelite.api.NodeCache;
import net.runelite.api.Point;
import net.runelite.api.Prayer;
import net.runelite.api.Preferences;
import net.runelite.api.RenderOverview;
import net.runelite.api.Skill;
import net.runelite.api.VarClientInt;
import net.runelite.api.VarClientStr;
import net.runelite.api.VarPlayer;
import net.runelite.api.Varbits;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.hooks.DrawCallbacks;
import net.runelite.api.vars.AccountType;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedGetter;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;
import net.runelite.rs.api.RSClient;
import org.slf4j.Logger;

@Implements("Client")
@ObfuscatedName("client")
public final class Client extends GameEngine implements Usernamed, RSClient {
   @ObfuscatedName("qh")
   @ObfuscatedSignature(
      descriptor = "[Ll;"
   )
   @Export("currentClanSettings")
   static ClanSettings[] currentClanSettings;
   @ObfuscatedName("ok")
   @ObfuscatedGetter(
      intValue = 257317803
   )
   @Export("rootWidgetCount")
   static int rootWidgetCount;
   @ObfuscatedName("ol")
   @ObfuscatedGetter(
      intValue = -1275777925
   )
   static int field849;
   @ObfuscatedName("pq")
   @ObfuscatedGetter(
      longValue = 6326177537901632971L
   )
   static long field858;
   @ObfuscatedName("ov")
   static boolean[] field719;
   @ObfuscatedName("pc")
   static boolean[] field860;
   @ObfuscatedName("or")
   static boolean[] field780;
   @ObfuscatedName("pb")
   @Export("isResizable")
   static boolean isResizable;
   @ObfuscatedName("tr")
   @ObfuscatedGetter(
      intValue = -106098945
   )
   public static int field717;
   @ObfuscatedName("qr")
   static boolean field881;
   @ObfuscatedName("pv")
   @Export("rootWidgetWidths")
   static int[] rootWidgetWidths;
   @ObfuscatedName("oq")
   @ObfuscatedSignature(
      descriptor = "Lmd;"
   )
   @Export("widgetFlags")
   static NodeHashTable widgetFlags;
   @ObfuscatedName("pz")
   @Export("rootWidgetXs")
   static int[] rootWidgetXs;
   @ObfuscatedName("pm")
   @ObfuscatedGetter(
      intValue = 568772133
   )
   @Export("gameDrawingMode")
   static int gameDrawingMode;
   @ObfuscatedName("pl")
   @Export("rootWidgetYs")
   static int[] rootWidgetYs;
   @ObfuscatedName("pa")
   @Export("rootWidgetHeights")
   static int[] rootWidgetHeights;
   @ObfuscatedName("tu")
   static int[] field689;
   @ObfuscatedName("tt")
   static int[] field851;
   @ObfuscatedName("ty")
   @ObfuscatedSignature(
      descriptor = "Lbb;"
   )
   static final ApproximateRouteStrategy field785;
   @ObfuscatedName("ot")
   @ObfuscatedSignature(
      descriptor = "Lkx;"
   )
   @Export("scriptEvents")
   static NodeDeque scriptEvents;
   @ObfuscatedName("qy")
   @ObfuscatedGetter(
      intValue = -1714532039
   )
   @Export("destinationX")
   static int destinationX;
   @ObfuscatedName("sv")
   @ObfuscatedSignature(
      descriptor = "[Lju;"
   )
   @Export("grandExchangeOffers")
   static GrandExchangeOffer[] grandExchangeOffers;
   @ObfuscatedName("qw")
   @ObfuscatedGetter(
      intValue = 1810222737
   )
   @Export("destinationY")
   static int destinationY;
   @ObfuscatedName("ow")
   @ObfuscatedGetter(
      intValue = 1005623717
   )
   @Export("mouseWheelRotation")
   static int mouseWheelRotation;
   @ObfuscatedName("rg")
   @Export("isCameraLocked")
   static boolean isCameraLocked;
   @ObfuscatedName("rj")
   @ObfuscatedGetter(
      intValue = -1084759211
   )
   @Export("soundEffectCount")
   static int soundEffectCount;
   @ObfuscatedName("rb")
   @Export("soundEffectIds")
   static int[] soundEffectIds;
   @ObfuscatedName("sd")
   @ObfuscatedGetter(
      intValue = 909671199
   )
   static int field908;
   @ObfuscatedName("rt")
   @Export("queuedSoundEffectLoops")
   static int[] queuedSoundEffectLoops;
   @ObfuscatedName("sj")
   @ObfuscatedSignature(
      descriptor = "Lic;"
   )
   @Export("playerAppearance")
   static PlayerComposition playerAppearance;
   @ObfuscatedName("rq")
   @Export("queuedSoundEffectDelays")
   static int[] queuedSoundEffectDelays;
   @ObfuscatedName("sh")
   @ObfuscatedGetter(
      intValue = 15595769
   )
   static int field752;
   @ObfuscatedName("rc")
   @ObfuscatedSignature(
      descriptor = "[Laa;"
   )
   @Export("soundEffects")
   static SoundEffect[] soundEffects;
   @ObfuscatedName("rx")
   @Export("soundLocations")
   static int[] soundLocations;
   @ObfuscatedName("to")
   @Export("archiveLoaders")
   static ArrayList archiveLoaders;
   @ObfuscatedName("te")
   @ObfuscatedGetter(
      intValue = -1953954605
   )
   @Export("archiveLoadersDone")
   static int archiveLoadersDone;
   @ObfuscatedName("qj")
   @ObfuscatedGetter(
      intValue = -771809921
   )
   @Export("minimapState")
   static int minimapState;
   @ObfuscatedName("qq")
   @ObfuscatedGetter(
      intValue = 565415243
   )
   static int field872;
   @ObfuscatedName("sn")
   @ObfuscatedSignature(
      descriptor = "Lmh;"
   )
   @Export("platformInfoProvider")
   static PlatformInfoProvider platformInfoProvider;
   @ObfuscatedName("sg")
   static short field894;
   @ObfuscatedName("sp")
   static short field895;
   @ObfuscatedName("sa")
   static short field900;
   @ObfuscatedName("su")
   static short field901;
   @ObfuscatedName("qs")
   @ObfuscatedGetter(
      intValue = -1674931291
   )
   @Export("mapIconCount")
   static int mapIconCount;
   @ObfuscatedName("qe")
   @ObfuscatedGetter(
      intValue = 2076100495
   )
   @Export("currentTrackGroupId")
   static int currentTrackGroupId;
   @ObfuscatedName("sm")
   static short field828;
   @ObfuscatedName("ss")
   @ObfuscatedGetter(
      intValue = 755828989
   )
   @Export("viewportZoom")
   static int viewportZoom;
   @ObfuscatedName("sk")
   static short field909;
   @ObfuscatedName("sc")
   @ObfuscatedGetter(
      intValue = -592780465
   )
   @Export("viewportWidth")
   static int viewportWidth;
   @ObfuscatedName("qu")
   @Export("mapIconXs")
   static int[] mapIconXs;
   @ObfuscatedName("qp")
   @Export("mapIconYs")
   static int[] mapIconYs;
   @ObfuscatedName("qz")
   @ObfuscatedSignature(
      descriptor = "[Loh;"
   )
   @Export("mapIcons")
   static SpritePixels[] mapIcons;
   @ObfuscatedName("sy")
   @ObfuscatedGetter(
      intValue = 1806233111
   )
   @Export("viewportHeight")
   static int viewportHeight;
   @ObfuscatedName("st")
   @ObfuscatedGetter(
      intValue = -2020111655
   )
   @Export("viewportOffsetX")
   static int viewportOffsetX;
   @ObfuscatedName("sw")
   @ObfuscatedGetter(
      intValue = 407581195
   )
   @Export("viewportOffsetY")
   static int viewportOffsetY;
   @ObfuscatedName("pe")
   @Export("crossWorldMessageIds")
   static long[] crossWorldMessageIds;
   @ObfuscatedName("qb")
   @ObfuscatedGetter(
      longValue = 1950859972192234267L
   )
   static long field809;
   @ObfuscatedName("pw")
   @ObfuscatedGetter(
      intValue = 2087031963
   )
   static int field806;
   @ObfuscatedName("pn")
   @ObfuscatedGetter(
      intValue = -2103302667
   )
   @Export("crossWorldMessageIdsIndex")
   static int crossWorldMessageIdsIndex;
   @ObfuscatedName("tx")
   @ObfuscatedGetter(
      intValue = 112640073
   )
   static int field916;
   @ObfuscatedName("rs")
   static boolean[] field693;
   @ObfuscatedName("ph")
   static int[] field868;
   @ObfuscatedName("rd")
   static int[] field891;
   @ObfuscatedName("qv")
   @ObfuscatedSignature(
      descriptor = "[Ly;"
   )
   @Export("currentClanChannels")
   static ClanChannel[] currentClanChannels;
   @ObfuscatedName("pi")
   static int[] field867;
   @ObfuscatedName("oj")
   @ObfuscatedSignature(
      descriptor = "Lkx;"
   )
   static NodeDeque field652;
   @ObfuscatedName("om")
   @ObfuscatedSignature(
      descriptor = "Lkx;"
   )
   static NodeDeque field846;
   @ObfuscatedName("rv")
   static int[] field890;
   @ObfuscatedName("si")
   @Export("zoomHeight")
   static short zoomHeight;
   @ObfuscatedName("so")
   @Export("zoomWidth")
   static short zoomWidth;
   @ObfuscatedName("ps")
   @ObfuscatedGetter(
      intValue = -639645947
   )
   @Export("publicChatMode")
   static int publicChatMode;
   @ObfuscatedName("sb")
   static int[] field893;
   @ObfuscatedName("rh")
   static int[] field892;
   @ObfuscatedName("pg")
   @ObfuscatedGetter(
      intValue = -1863932723
   )
   @Export("tradeChatMode")
   static int tradeChatMode;
   @ObfuscatedName("py")
   static int[] field877;
   @ObfuscatedName("pp")
   static String field863;
   @ObfuscatedName("sz")
   @ObfuscatedSignature(
      descriptor = "Lbk;"
   )
   @Export("GrandExchangeEvents_worldComparator")
   static GrandExchangeOfferOwnWorldComparator GrandExchangeEvents_worldComparator;
   @ObfuscatedName("w")
   @ObfuscatedSignature(
      descriptor = "[Lfz;"
   )
   @Export("collisionMaps")
   static CollisionMap[] collisionMaps;
   @ObfuscatedName("an")
   static boolean field889 = true;
   @ObfuscatedName("bd")
   @ObfuscatedGetter(
      intValue = 1396273817
   )
   @Export("worldId")
   public static int worldId = 1;
   @ObfuscatedName("bt")
   @ObfuscatedGetter(
      intValue = 1088533541
   )
   @Export("worldProperties")
   static int worldProperties = 0;
   @ObfuscatedName("bu")
   @ObfuscatedGetter(
      intValue = 60643507
   )
   @Export("gameBuild")
   static int gameBuild = 0;
   @ObfuscatedName("bm")
   @Export("isMembersWorld")
   public static boolean isMembersWorld = false;
   @ObfuscatedName("bz")
   @Export("isLowDetail")
   static boolean isLowDetail = false;
   @ObfuscatedName("ba")
   @ObfuscatedGetter(
      intValue = -1731105959
   )
   @Export("clientType")
   static int clientType = -1;
   @ObfuscatedName("be")
   @ObfuscatedGetter(
      intValue = 1523027191
   )
   static int field643 = -1;
   @ObfuscatedName("bj")
   @Export("onMobile")
   static boolean onMobile = false;
   @ObfuscatedName("bx")
   @ObfuscatedGetter(
      intValue = -888167097
   )
   @Export("gameState")
   static int gameState = 0;
   @ObfuscatedName("cs")
   @Export("isLoading")
   static boolean isLoading = true;
   @ObfuscatedName("cg")
   @ObfuscatedGetter(
      intValue = -1702273423
   )
   @Export("cycle")
   static int cycle = 0;
   @ObfuscatedName("co")
   @ObfuscatedGetter(
      longValue = 8707347461681450731L
   )
   @Export("mouseLastLastPressedTimeMillis")
   static long mouseLastLastPressedTimeMillis = -1L;
   @ObfuscatedName("cj")
   @ObfuscatedGetter(
      intValue = -1975403091
   )
   static int field651 = -1;
   @ObfuscatedName("cc")
   @ObfuscatedGetter(
      intValue = 424553417
   )
   static int field812 = -1;
   @ObfuscatedName("cu")
   @ObfuscatedGetter(
      longValue = 8694062253931131003L
   )
   static long field653 = -1L;
   @ObfuscatedName("cz")
   @Export("hadFocus")
   static boolean hadFocus = true;
   @ObfuscatedName("cb")
   @Export("displayFps")
   static boolean displayFps = false;
   @ObfuscatedName("ce")
   @ObfuscatedGetter(
      intValue = -414435005
   )
   @Export("rebootTimer")
   static int rebootTimer = 0;
   @ObfuscatedName("ch")
   @ObfuscatedGetter(
      intValue = -1764619255
   )
   @Export("hintArrowType")
   static int hintArrowType = 0;
   @ObfuscatedName("cy")
   @ObfuscatedGetter(
      intValue = 1564576307
   )
   @Export("hintArrowNpcIndex")
   static int hintArrowNpcIndex = 0;
   @ObfuscatedName("cx")
   @ObfuscatedGetter(
      intValue = 1851530429
   )
   @Export("hintArrowPlayerIndex")
   static int hintArrowPlayerIndex = 0;
   @ObfuscatedName("ca")
   @ObfuscatedGetter(
      intValue = -659326571
   )
   @Export("hintArrowX")
   static int hintArrowX = 0;
   @ObfuscatedName("cl")
   @ObfuscatedGetter(
      intValue = 122660467
   )
   @Export("hintArrowY")
   static int hintArrowY = 0;
   @ObfuscatedName("cw")
   @ObfuscatedGetter(
      intValue = 1213777129
   )
   @Export("hintArrowHeight")
   static int hintArrowHeight = 0;
   @ObfuscatedName("db")
   @ObfuscatedGetter(
      intValue = -213439381
   )
   @Export("hintArrowSubX")
   static int hintArrowSubX = 0;
   @ObfuscatedName("dz")
   @ObfuscatedGetter(
      intValue = 2031424669
   )
   @Export("hintArrowSubY")
   static int hintArrowSubY = 0;
   @ObfuscatedName("dj")
   @Export("mouseCam")
   static boolean mouseCam;
   @ObfuscatedName("dr")
   @ObfuscatedSignature(
      descriptor = "Ldj;"
   )
   @Export("playerAttackOption")
   static AttackOption playerAttackOption;
   @ObfuscatedName("di")
   @ObfuscatedSignature(
      descriptor = "Ldj;"
   )
   @Export("npcAttackOption")
   static AttackOption npcAttackOption;
   @ObfuscatedName("dk")
   @ObfuscatedGetter(
      intValue = -1127540073
   )
   @Export("titleLoadingStage")
   static int titleLoadingStage;
   @ObfuscatedName("df")
   @ObfuscatedGetter(
      intValue = 534657201
   )
   @Export("js5ConnectState")
   static int js5ConnectState;
   @ObfuscatedName("dv")
   @ObfuscatedGetter(
      intValue = -587760519
   )
   static int field670;
   @ObfuscatedName("ep")
   @ObfuscatedGetter(
      intValue = -515874317
   )
   @Export("js5Errors")
   static int js5Errors;
   @ObfuscatedName("ew")
   @ObfuscatedGetter(
      intValue = -1024706269
   )
   @Export("loginState")
   static int loginState;
   @ObfuscatedName("ej")
   @ObfuscatedGetter(
      intValue = 1134111881
   )
   static int field673;
   @ObfuscatedName("el")
   @ObfuscatedGetter(
      intValue = -2135696499
   )
   static int field845;
   @ObfuscatedName("et")
   @ObfuscatedGetter(
      intValue = -1200580919
   )
   static int field675;
   @ObfuscatedName("eb")
   @ObfuscatedSignature(
      descriptor = "Ldq;"
   )
   static class125 field676;
   @ObfuscatedName("ei")
   @Export("Login_isUsernameRemembered")
   static boolean Login_isUsernameRemembered;
   @ObfuscatedName("ev")
   @ObfuscatedSignature(
      descriptor = "Lcr;"
   )
   @Export("secureRandomFuture")
   static SecureRandomFuture secureRandomFuture;
   @ObfuscatedName("fz")
   @Export("randomDatData")
   static byte[] randomDatData;
   @ObfuscatedName("fs")
   @ObfuscatedSignature(
      descriptor = "[Ldb;"
   )
   @Export("npcs")
   static NPC[] npcs;
   @ObfuscatedName("fp")
   @ObfuscatedGetter(
      intValue = -1300723479
   )
   @Export("npcCount")
   static int npcCount;
   @ObfuscatedName("fe")
   @Export("npcIndices")
   static int[] npcIndices;
   @ObfuscatedName("fb")
   @ObfuscatedGetter(
      intValue = 1045404765
   )
   static int field685;
   @ObfuscatedName("fo")
   static int[] field686;
   @ObfuscatedName("fw")
   @ObfuscatedSignature(
      descriptor = "Lds;"
   )
   @Export("packetWriter")
   public static final PacketWriter packetWriter;
   @ObfuscatedName("fj")
   @ObfuscatedSignature(
      descriptor = "Lls;"
   )
   static AbstractSocket field688;
   @ObfuscatedName("fg")
   @ObfuscatedGetter(
      intValue = -688107431
   )
   @Export("logoutTimer")
   static int logoutTimer;
   @ObfuscatedName("fi")
   @Export("hadNetworkError")
   static boolean hadNetworkError;
   @ObfuscatedName("fy")
   @Export("useBufferedSocket")
   static boolean useBufferedSocket;
   @ObfuscatedName("fx")
   @ObfuscatedSignature(
      descriptor = "Lkv;"
   )
   @Export("timer")
   static Timer timer;
   @ObfuscatedName("fn")
   @Export("fontsMap")
   static HashMap fontsMap;
   @ObfuscatedName("gz")
   @ObfuscatedGetter(
      intValue = -1548218417
   )
   static int field694;
   @ObfuscatedName("gb")
   @ObfuscatedGetter(
      intValue = 834526125
   )
   static int field695;
   @ObfuscatedName("gs")
   @ObfuscatedGetter(
      intValue = 1282334079
   )
   static int field696;
   @ObfuscatedName("gv")
   @ObfuscatedGetter(
      intValue = 1781171757
   )
   static int field697;
   @ObfuscatedName("gm")
   @ObfuscatedGetter(
      intValue = -189422017
   )
   static int field698;
   @ObfuscatedName("gl")
   @Export("isInInstance")
   static boolean isInInstance;
   @ObfuscatedName("gk")
   @Export("instanceChunkTemplates")
   static int[][][] instanceChunkTemplates;
   @ObfuscatedName("gd")
   static final int[] field702;
   @ObfuscatedName("gp")
   @ObfuscatedGetter(
      intValue = 613265553
   )
   static int field913;
   @ObfuscatedName("ht")
   @ObfuscatedGetter(
      intValue = -119839221
   )
   static int field704;
   @ObfuscatedName("hm")
   @ObfuscatedGetter(
      intValue = 1576725955
   )
   static int field705;
   @ObfuscatedName("hh")
   @ObfuscatedGetter(
      intValue = 1820360451
   )
   static int field706;
   @ObfuscatedName("hn")
   @ObfuscatedGetter(
      intValue = -944863209
   )
   static int field707;
   @ObfuscatedName("hc")
   static boolean field708;
   @ObfuscatedName("hz")
   @ObfuscatedGetter(
      intValue = -2035208823
   )
   @Export("alternativeScrollbarWidth")
   static int alternativeScrollbarWidth;
   @ObfuscatedName("hv")
   @ObfuscatedGetter(
      intValue = -2031705619
   )
   @Export("camAngleX")
   static int camAngleX;
   @ObfuscatedName("hu")
   @ObfuscatedGetter(
      intValue = -574530013
   )
   @Export("camAngleY")
   static int camAngleY;
   @ObfuscatedName("hi")
   @ObfuscatedGetter(
      intValue = -308632201
   )
   @Export("camAngleDY")
   static int camAngleDY;
   @ObfuscatedName("hk")
   @ObfuscatedGetter(
      intValue = -1928381453
   )
   @Export("camAngleDX")
   static int camAngleDX;
   @ObfuscatedName("hx")
   @ObfuscatedGetter(
      intValue = -1017968913
   )
   @Export("mouseCamClickedX")
   static int mouseCamClickedX;
   @ObfuscatedName("hp")
   @ObfuscatedGetter(
      intValue = 1156171963
   )
   @Export("mouseCamClickedY")
   static int mouseCamClickedY;
   @ObfuscatedName("ir")
   @ObfuscatedGetter(
      intValue = -400479567
   )
   @Export("oculusOrbState")
   static int oculusOrbState;
   @ObfuscatedName("in")
   @ObfuscatedGetter(
      intValue = 334319879
   )
   @Export("camFollowHeight")
   static int camFollowHeight;
   @ObfuscatedName("it")
   @Export("selectedItemName")
   static String selectedItemName;
   @ObfuscatedName("ik")
   @ObfuscatedGetter(
      intValue = -859124845
   )
   static int field718;
   @ObfuscatedName("ii")
   @ObfuscatedGetter(
      intValue = -1624140925
   )
   static int field732;
   @ObfuscatedName("iu")
   @ObfuscatedGetter(
      intValue = 286253175
   )
   static int field720;
   @ObfuscatedName("ig")
   @ObfuscatedGetter(
      intValue = -503425261
   )
   @Export("oculusOrbNormalSpeed")
   static int oculusOrbNormalSpeed;
   @ObfuscatedName("io")
   @ObfuscatedGetter(
      intValue = 918404705
   )
   @Export("oculusOrbSlowedSpeed")
   static int oculusOrbSlowedSpeed;
   @ObfuscatedName("is")
   @ObfuscatedGetter(
      intValue = -315190483
   )
   static int field723;
   @ObfuscatedName("id")
   static boolean field724;
   @ObfuscatedName("il")
   @ObfuscatedGetter(
      intValue = -1396591603
   )
   static int field725;
   @ObfuscatedName("im")
   static boolean field726;
   @ObfuscatedName("ip")
   @ObfuscatedGetter(
      intValue = 627480141
   )
   static int field727;
   @ObfuscatedName("if")
   @ObfuscatedGetter(
      intValue = 964514413
   )
   @Export("overheadTextCount")
   static int overheadTextCount;
   @ObfuscatedName("ia")
   @ObfuscatedGetter(
      intValue = 1347852179
   )
   @Export("overheadTextLimit")
   static int overheadTextLimit;
   @ObfuscatedName("ij")
   @Export("overheadTextXs")
   static int[] overheadTextXs;
   @ObfuscatedName("iw")
   @Export("overheadTextYs")
   static int[] overheadTextYs;
   @ObfuscatedName("iv")
   @Export("overheadTextAscents")
   static int[] overheadTextAscents;
   @ObfuscatedName("iy")
   @Export("overheadTextXOffsets")
   static int[] overheadTextXOffsets;
   @ObfuscatedName("ib")
   @Export("overheadTextColors")
   static int[] overheadTextColors;
   @ObfuscatedName("ji")
   @Export("overheadTextEffects")
   static int[] overheadTextEffects;
   @ObfuscatedName("jw")
   @Export("overheadTextCyclesRemaining")
   static int[] overheadTextCyclesRemaining;
   @ObfuscatedName("jk")
   @Export("overheadText")
   static String[] overheadText;
   @ObfuscatedName("jd")
   @Export("tileLastDrawnActor")
   static int[][] tileLastDrawnActor;
   @ObfuscatedName("jl")
   @ObfuscatedGetter(
      intValue = -213028925
   )
   @Export("viewportDrawCount")
   static int viewportDrawCount;
   @ObfuscatedName("jh")
   @ObfuscatedGetter(
      intValue = 1057202329
   )
   @Export("viewportTempX")
   static int viewportTempX;
   @ObfuscatedName("jq")
   @ObfuscatedGetter(
      intValue = -1775322899
   )
   @Export("viewportTempY")
   static int viewportTempY;
   @ObfuscatedName("jo")
   @ObfuscatedGetter(
      intValue = 2140784851
   )
   @Export("mouseCrossX")
   static int mouseCrossX;
   @ObfuscatedName("jn")
   @ObfuscatedGetter(
      intValue = -1518689867
   )
   @Export("mouseCrossY")
   static int mouseCrossY;
   @ObfuscatedName("ja")
   @ObfuscatedGetter(
      intValue = -1932536521
   )
   @Export("mouseCrossState")
   static int mouseCrossState;
   @ObfuscatedName("jy")
   @ObfuscatedGetter(
      intValue = 993621687
   )
   @Export("mouseCrossColor")
   static int mouseCrossColor;
   @ObfuscatedName("jg")
   @Export("showMouseCross")
   static boolean showMouseCross;
   @ObfuscatedName("jf")
   @ObfuscatedGetter(
      intValue = -1890193251
   )
   static int field747;
   @ObfuscatedName("jv")
   @ObfuscatedGetter(
      intValue = -657236193
   )
   static int field841;
   @ObfuscatedName("jr")
   @ObfuscatedGetter(
      intValue = -65004085
   )
   @Export("dragItemSlotSource")
   static int dragItemSlotSource;
   @ObfuscatedName("jc")
   @ObfuscatedGetter(
      intValue = 823942939
   )
   @Export("draggedWidgetX")
   static int draggedWidgetX;
   @ObfuscatedName("jt")
   @ObfuscatedGetter(
      intValue = -732224895
   )
   @Export("draggedWidgetY")
   static int draggedWidgetY;
   @ObfuscatedName("jb")
   @ObfuscatedGetter(
      intValue = -1148388107
   )
   @Export("dragItemSlotDestination")
   static int dragItemSlotDestination;
   @ObfuscatedName("jj")
   static boolean field848;
   @ObfuscatedName("js")
   @ObfuscatedGetter(
      intValue = -599181589
   )
   @Export("itemDragDuration")
   static int itemDragDuration;
   @ObfuscatedName("ju")
   @ObfuscatedGetter(
      intValue = -267997189
   )
   static int field755;
   @ObfuscatedName("je")
   @Export("showLoadingMessages")
   static boolean showLoadingMessages;
   @ObfuscatedName("kj")
   @ObfuscatedSignature(
      descriptor = "[Lce;"
   )
   @Export("players")
   static Player[] players;
   @ObfuscatedName("kk")
   @ObfuscatedGetter(
      intValue = 929092845
   )
   @Export("localPlayerIndex")
   static int localPlayerIndex;
   @ObfuscatedName("kl")
   @ObfuscatedGetter(
      intValue = -1721569989
   )
   static int field759;
   @ObfuscatedName("ka")
   @Export("renderSelf")
   static boolean renderSelf;
   @ObfuscatedName("kg")
   @ObfuscatedGetter(
      intValue = -1493328845
   )
   @Export("drawPlayerNames")
   static int drawPlayerNames;
   @ObfuscatedName("ko")
   @ObfuscatedGetter(
      intValue = 1717996379
   )
   static int field762;
   @ObfuscatedName("kt")
   static int[] field771;
   @ObfuscatedName("kz")
   @Export("playerMenuOpcodes")
   static final int[] playerMenuOpcodes;
   @ObfuscatedName("kd")
   @Export("playerMenuActions")
   static String[] playerMenuActions;
   @ObfuscatedName("kp")
   @Export("playerOptionsPriorities")
   static boolean[] playerOptionsPriorities;
   @ObfuscatedName("kw")
   @Export("defaultRotations")
   static int[] defaultRotations;
   @ObfuscatedName("ku")
   @ObfuscatedGetter(
      intValue = 845336023
   )
   @Export("combatTargetPlayerIndex")
   static int combatTargetPlayerIndex;
   @ObfuscatedName("kb")
   @ObfuscatedSignature(
      descriptor = "[[[Lkx;"
   )
   @Export("groundItems")
   static NodeDeque[][][] groundItems;
   @ObfuscatedName("ke")
   @ObfuscatedSignature(
      descriptor = "Lkx;"
   )
   @Export("pendingSpawns")
   static NodeDeque pendingSpawns;
   @ObfuscatedName("kf")
   @ObfuscatedSignature(
      descriptor = "Lkx;"
   )
   @Export("projectiles")
   static NodeDeque projectiles;
   @ObfuscatedName("kv")
   @ObfuscatedSignature(
      descriptor = "Lkx;"
   )
   @Export("graphicsObjects")
   static NodeDeque graphicsObjects;
   @ObfuscatedName("kn")
   @Export("currentLevels")
   static int[] currentLevels;
   @ObfuscatedName("ks")
   @Export("levels")
   static int[] levels;
   @ObfuscatedName("lr")
   @Export("experience")
   static int[] experience;
   @ObfuscatedName("lk")
   @ObfuscatedGetter(
      intValue = 161491329
   )
   @Export("leftClickOpensMenu")
   static int leftClickOpensMenu;
   @ObfuscatedName("lo")
   @Export("isMenuOpen")
   static boolean isMenuOpen;
   @ObfuscatedName("ll")
   @ObfuscatedGetter(
      intValue = 673512273
   )
   @Export("menuOptionsCount")
   static int menuOptionsCount;
   @ObfuscatedName("lx")
   @Export("menuArguments1")
   static int[] menuArguments1;
   @ObfuscatedName("ln")
   @Export("menuArguments2")
   static int[] menuArguments2;
   @ObfuscatedName("lj")
   @Export("menuOpcodes")
   static int[] menuOpcodes;
   @ObfuscatedName("la")
   @Export("menuIdentifiers")
   static int[] menuIdentifiers;
   @ObfuscatedName("lv")
   @Export("menuActions")
   static String[] menuActions;
   @ObfuscatedName("lu")
   @Export("menuTargets")
   static String[] menuTargets;
   @ObfuscatedName("ly")
   @Export("menuShiftClick")
   static boolean[] menuShiftClick;
   @ObfuscatedName("ls")
   @Export("followerOpsLowPriority")
   static boolean followerOpsLowPriority;
   @ObfuscatedName("lw")
   @Export("shiftClickDrop")
   static boolean shiftClickDrop;
   @ObfuscatedName("le")
   @Export("tapToDrop")
   static boolean tapToDrop;
   @ObfuscatedName("lh")
   @Export("showMouseOverText")
   static boolean showMouseOverText;
   @ObfuscatedName("lm")
   @ObfuscatedGetter(
      intValue = 1449324429
   )
   @Export("viewportX")
   static int viewportX;
   @ObfuscatedName("lf")
   @ObfuscatedGetter(
      intValue = -406218029
   )
   @Export("viewportY")
   static int viewportY;
   @ObfuscatedName("mi")
   @ObfuscatedGetter(
      intValue = -1946501383
   )
   static int field792;
   @ObfuscatedName("ms")
   @ObfuscatedGetter(
      intValue = 625904081
   )
   static int field833;
   @ObfuscatedName("me")
   @ObfuscatedGetter(
      intValue = -438265211
   )
   @Export("isItemSelected")
   static int isItemSelected;
   @ObfuscatedName("ml")
   @Export("isSpellSelected")
   static boolean isSpellSelected;
   @ObfuscatedName("mp")
   @ObfuscatedGetter(
      intValue = 1683168531
   )
   @Export("selectedSpellChildIndex")
   static int selectedSpellChildIndex;
   @ObfuscatedName("mz")
   @ObfuscatedGetter(
      intValue = 1424616685
   )
   static int field788;
   @ObfuscatedName("mk")
   @Export("selectedSpellActionName")
   static String selectedSpellActionName;
   @ObfuscatedName("mn")
   @Export("selectedSpellName")
   static String selectedSpellName;
   @ObfuscatedName("mx")
   @ObfuscatedGetter(
      intValue = 1338880923
   )
   @Export("rootInterface")
   static int rootInterface;
   @ObfuscatedName("mo")
   @ObfuscatedSignature(
      descriptor = "Lmd;"
   )
   @Export("interfaceParents")
   static NodeHashTable interfaceParents;
   @ObfuscatedName("ma")
   @ObfuscatedGetter(
      intValue = -907665653
   )
   static int field803;
   @ObfuscatedName("mc")
   @ObfuscatedGetter(
      intValue = 1274187443
   )
   static int field805;
   @ObfuscatedName("mu")
   @ObfuscatedGetter(
      intValue = -234914901
   )
   @Export("chatEffects")
   static int chatEffects;
   @ObfuscatedName("mv")
   @ObfuscatedGetter(
      intValue = -1287342199
   )
   static int field912;
   @ObfuscatedName("mj")
   @ObfuscatedSignature(
      descriptor = "Lio;"
   )
   @Export("meslayerContinueWidget")
   static Widget meslayerContinueWidget;
   @ObfuscatedName("mr")
   @ObfuscatedGetter(
      intValue = 1008679671
   )
   @Export("runEnergy")
   static int runEnergy;
   @ObfuscatedName("md")
   @ObfuscatedGetter(
      intValue = -612986977
   )
   @Export("weight")
   static int weight;
   @ObfuscatedName("mg")
   @ObfuscatedGetter(
      intValue = -816484905
   )
   @Export("staffModLevel")
   static int staffModLevel;
   @ObfuscatedName("mt")
   @ObfuscatedGetter(
      intValue = -1092871253
   )
   @Export("followerIndex")
   static int followerIndex;
   @ObfuscatedName("nc")
   @Export("playerMod")
   static boolean playerMod;
   @ObfuscatedName("nk")
   @ObfuscatedSignature(
      descriptor = "Lio;"
   )
   @Export("viewportWidget")
   static Widget viewportWidget;
   @ObfuscatedName("nm")
   @ObfuscatedSignature(
      descriptor = "Lio;"
   )
   @Export("clickedWidget")
   static Widget clickedWidget;
   @ObfuscatedName("nf")
   @ObfuscatedSignature(
      descriptor = "Lio;"
   )
   @Export("clickedWidgetParent")
   static Widget clickedWidgetParent;
   @ObfuscatedName("ni")
   @ObfuscatedGetter(
      intValue = -653830947
   )
   @Export("widgetClickX")
   static int widgetClickX;
   @ObfuscatedName("nu")
   @ObfuscatedGetter(
      intValue = 1055501491
   )
   @Export("widgetClickY")
   static int widgetClickY;
   @ObfuscatedName("nv")
   @ObfuscatedSignature(
      descriptor = "Lio;"
   )
   @Export("draggedOnWidget")
   static Widget draggedOnWidget;
   @ObfuscatedName("no")
   static boolean field819;
   @ObfuscatedName("nx")
   @ObfuscatedGetter(
      intValue = 1941546241
   )
   static int field740;
   @ObfuscatedName("nw")
   @ObfuscatedGetter(
      intValue = -356014141
   )
   static int field821;
   @ObfuscatedName("np")
   static boolean field822;
   @ObfuscatedName("nq")
   @ObfuscatedGetter(
      intValue = -227792449
   )
   static int field854;
   @ObfuscatedName("nl")
   @ObfuscatedGetter(
      intValue = 662563071
   )
   static int field804;
   @ObfuscatedName("nz")
   @Export("isDraggingWidget")
   static boolean isDraggingWidget;
   @ObfuscatedName("nd")
   @ObfuscatedGetter(
      intValue = 1675804533
   )
   @Export("cycleCntr")
   static int cycleCntr;
   @ObfuscatedName("nn")
   @Export("changedVarps")
   static int[] changedVarps;
   @ObfuscatedName("na")
   @ObfuscatedGetter(
      intValue = 1149971569
   )
   @Export("changedVarpCount")
   static int changedVarpCount;
   @ObfuscatedName("ns")
   @Export("changedItemContainers")
   static int[] changedItemContainers;
   @ObfuscatedName("nj")
   @ObfuscatedGetter(
      intValue = 102304557
   )
   static int field830;
   @ObfuscatedName("ne")
   @Export("changedSkills")
   static int[] changedSkills;
   @ObfuscatedName("oc")
   @ObfuscatedGetter(
      intValue = 28091927
   )
   @Export("changedSkillsCount")
   static int changedSkillsCount;
   @ObfuscatedName("oi")
   static int[] field911;
   @ObfuscatedName("oe")
   @ObfuscatedGetter(
      intValue = 1843609775
   )
   static int field852;
   @ObfuscatedName("on")
   @ObfuscatedGetter(
      intValue = 1908476521
   )
   @Export("chatCycle")
   static int chatCycle;
   @ObfuscatedName("op")
   @ObfuscatedGetter(
      intValue = 56143531
   )
   static int field836;
   @ObfuscatedName("oh")
   @ObfuscatedGetter(
      intValue = 1400025619
   )
   static int field844;
   @ObfuscatedName("ox")
   @ObfuscatedGetter(
      intValue = -1827987357
   )
   static int field654;
   @ObfuscatedName("of")
   @ObfuscatedGetter(
      intValue = -31959039
   )
   static int field839;
   @ObfuscatedName("oy")
   @ObfuscatedGetter(
      intValue = 77736245
   )
   static int field840;
   @ObfuscatedName("og")
   @ObfuscatedGetter(
      intValue = -194606347
   )
   static int field748;
   @ObfuscatedName("ou")
   @ObfuscatedGetter(
      intValue = 1047300925
   )
   static int field659;
   @ObfuscatedName("eo")
   @ObfuscatedSignature(
      descriptor = "Lnd;"
   )
   Buffer field679;
   @ObfuscatedName("fl")
   @ObfuscatedSignature(
      descriptor = "Las;"
   )
   class35 field680;
   public static RSClient clientInstance;

   @ObfuscatedName("o")
   @ObfuscatedSignature(
      descriptor = "(B)V",
      garbageValue = "50"
   )
   @Export("resizeGame")
   protected final void resizeGame() {
      field858 = ObjectComposition.currentTimeMillis() + 500L;
      this.resizeJS();
      if (rootInterface != -1) {
         this.resizeRoot(true);
      }

   }

   @ObfuscatedName("ab")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "-232069568"
   )
   @Export("setUp")
   protected final void setUp() {
      SecureRandomFuture.method1981(new int[]{20, 260, 10000}, new int[]{1000, 100, 500});
      WorldMapLabelSize.worldPort = gameBuild == 0 ? 'ꩊ' : worldId + '鱀';
      GrandExchangeOfferTotalQuantityComparator.js5Port = gameBuild == 0 ? 443 : worldId + '썐';
      DesktopPlatformInfoProvider.currentPort = WorldMapLabelSize.worldPort;
      class29.field233 = class245.field2944;
      class15.field137 = class245.field2940;
      UserComparator5.field1442 = class245.field2941;
      VarbitComposition.field1708 = class245.field2942;
      ServerPacket.urlRequester = new UrlRequester();
      this.setUpKeyboard();
      this.setUpMouse();
      MidiPcmStream.mouseWheel = this.mouseWheel();
      class43.masterDisk = new ArchiveDisk(255, JagexCache.JagexCache_dat2File, JagexCache.JagexCache_idx255File, 500000);
      AccessFile var1 = null;
      ClientPreferences var2 = new ClientPreferences();

      try {
         var1 = class82.getPreferencesFile("", SoundSystem.field461.name, false);
         byte[] var3 = new byte[(int)var1.length()];

         int var4;
         for(int var5 = 0; var5 < var3.length; var5 += var4) {
            var4 = var1.read(var3, var5, var3.length - var5);
            if (var4 == -1) {
               throw new IOException();
            }
         }

         var2 = new ClientPreferences(new Buffer(var3));
      } catch (Exception var7) {
         ;
      }

      try {
         if (var1 != null) {
            var1.close();
         }
      } catch (Exception var6) {
         ;
      }

      ObjectComposition.clientPreferences = var2;
      this.setUpClipboard();
      String var8 = PacketWriter.null_string;
      class44.applet = this;
      if (var8 != null) {
         class44.field318 = var8;
      }

      if (gameBuild != 0) {
         displayFps = true;
      }

      CollisionMap.setWindowedMode(ObjectComposition.clientPreferences.windowMode);
      NetSocket.friendSystem = new FriendSystem(WorldMapSection0.loginType);
   }

   @ObfuscatedName("al")
   @ObfuscatedSignature(
      descriptor = "(B)V",
      garbageValue = "68"
   )
   @Export("doCycle")
   protected final void doCycle() {
      ++cycle;
      this.doCycleJs5();

      while(true) {
         NodeDeque var2 = ArchiveDiskActionHandler.ArchiveDiskActionHandler_requestQueue;
         ArchiveDiskAction var1;
         synchronized(ArchiveDiskActionHandler.ArchiveDiskActionHandler_requestQueue) {
            var1 = (ArchiveDiskAction)ArchiveDiskActionHandler.ArchiveDiskActionHandler_responseQueue.removeLast();
         }

         if (var1 == null) {
            Interpreter.method1870();
            HealthBarUpdate.playPcmPlayers();
            KeyHandler var7 = KeyHandler.KeyHandler_instance;
            synchronized(KeyHandler.KeyHandler_instance) {
               ++KeyHandler.KeyHandler_idleCycles;
               KeyHandler.field293 = KeyHandler.field295;
               KeyHandler.field292 = 0;
               int var3;
               if (KeyHandler.field288 >= 0) {
                  while(KeyHandler.field296 != KeyHandler.field288) {
                     var3 = KeyHandler.field287[KeyHandler.field296];
                     KeyHandler.field296 = KeyHandler.field296 + 1 & 127;
                     if (var3 < 0) {
                        KeyHandler.KeyHandler_pressedKeys[~var3] = false;
                     } else {
                        if (!KeyHandler.KeyHandler_pressedKeys[var3] && KeyHandler.field292 < KeyHandler.field274.length - 1) {
                           KeyHandler.field274[++KeyHandler.field292 - 1] = var3;
                        }

                        KeyHandler.KeyHandler_pressedKeys[var3] = true;
                     }
                  }
               } else {
                  for(var3 = 0; var3 < 112; ++var3) {
                     KeyHandler.KeyHandler_pressedKeys[var3] = false;
                  }

                  KeyHandler.field288 = KeyHandler.field296;
               }

               if (KeyHandler.field292 > 0) {
                  KeyHandler.KeyHandler_idleCycles = 0;
               }

               KeyHandler.field295 = KeyHandler.field294;
            }

            Player.method2166();
            int var8;
            if (MidiPcmStream.mouseWheel != null) {
               var8 = MidiPcmStream.mouseWheel.useRotation();
               mouseWheelRotation = var8;
            }

            if (gameState == 0) {
               MilliClock.load();
               GameEngine.clock.mark();

               for(var8 = 0; var8 < 32; ++var8) {
                  GameEngine.graphicsTickTimes[var8] = 0L;
               }

               for(var8 = 0; var8 < 32; ++var8) {
                  GameEngine.clientTickTimes[var8] = 0L;
               }

               class260.gameCyclesToDo = 0;
            } else if (gameState == 5) {
               WorldMapManager.doCycleTitle(this);
               MilliClock.load();
               GameEngine.clock.mark();

               for(var8 = 0; var8 < 32; ++var8) {
                  GameEngine.graphicsTickTimes[var8] = 0L;
               }

               for(var8 = 0; var8 < 32; ++var8) {
                  GameEngine.clientTickTimes[var8] = 0L;
               }

               class260.gameCyclesToDo = 0;
            } else if (gameState != 10 && gameState != 11) {
               if (gameState == 20) {
                  WorldMapManager.doCycleTitle(this);
                  this.doCycleLoggedOut();
               } else if (gameState == 25) {
                  WorldMapAreaData.method3708();
               }
            } else {
               WorldMapManager.doCycleTitle(this);
            }

            if (gameState == 30) {
               this.doCycleLoggedIn();
            } else if (gameState == 40 || gameState == 45) {
               this.doCycleLoggedOut();
            }

            return;
         }

         var1.archive.load(var1.archiveDisk, (int)var1.key, var1.data, false);
      }
   }

   @ObfuscatedName("ad")
   @ObfuscatedSignature(
      descriptor = "(ZS)V",
      garbageValue = "9311"
   )
   @Export("draw")
   protected final void draw(boolean var1) {
      boolean var2;
      label139: {
         try {
            if (class232.musicPlayerStatus == 2) {
               if (class2.musicTrack == null) {
                  class2.musicTrack = MusicTrack.readTrack(ModelData0.musicTrackArchive, class32.musicTrackGroupId, class18.musicTrackFileId);
                  if (class2.musicTrack == null) {
                     var2 = false;
                     break label139;
                  }
               }

               if (Messages.soundCache == null) {
                  Messages.soundCache = new SoundCache(WorldMapElement.soundEffectsArchive, class232.musicSamplesArchive);
               }

               if (class124.midiPcmStream.loadMusicTrack(class2.musicTrack, class232.musicPatchesArchive, Messages.soundCache, 22050)) {
                  class124.midiPcmStream.clearAll();
                  class124.midiPcmStream.setPcmStreamVolume(class232.musicTrackVolume);
                  class124.midiPcmStream.setMusicTrack(class2.musicTrack, class232.musicTrackBoolean);
                  class232.musicPlayerStatus = 0;
                  class2.musicTrack = null;
                  Messages.soundCache = null;
                  ModelData0.musicTrackArchive = null;
                  var2 = true;
                  break label139;
               }
            }
         } catch (Exception var4) {
            var4.printStackTrace();
            class124.midiPcmStream.clear();
            class232.musicPlayerStatus = 0;
            class2.musicTrack = null;
            Messages.soundCache = null;
            ModelData0.musicTrackArchive = null;
         }

         var2 = false;
      }

      if (var2 && field881 && DesktopPlatformInfoProvider.pcmPlayer0 != null) {
         DesktopPlatformInfoProvider.pcmPlayer0.tryDiscard();
      }

      if ((gameState == 10 || gameState == 20 || gameState == 30) && field858 != 0L && ObjectComposition.currentTimeMillis() > field858) {
         CollisionMap.setWindowedMode(SpotAnimationDefinition.getWindowedMode());
      }

      int var3;
      if (var1) {
         for(var3 = 0; var3 < 100; ++var3) {
            field719[var3] = true;
         }
      }

      if (gameState == 0) {
         this.drawInitial(Login.Login_loadingPercent, Login.Login_loadingText, var1);
      } else if (gameState == 5) {
         FriendSystem.drawTitle(Widget.fontBold12, Actor.fontPlain11, UserComparator3.fontPlain12);
      } else if (gameState != 10 && gameState != 11) {
         if (gameState == 20) {
            FriendSystem.drawTitle(Widget.fontBold12, Actor.fontPlain11, UserComparator3.fontPlain12);
         } else if (gameState == 25) {
            if (field698 == 1) {
               if (field694 > field695) {
                  field695 = field694;
               }

               var3 = (field695 * 50 - field694 * 50) / field695;
               DirectByteArrayCopier.drawLoadingMessage("Loading - please wait.<br> (" + var3 + "%)", false);
            } else if (field698 == 2) {
               if (field696 > field697) {
                  field697 = field696;
               }

               var3 = (field697 * 50 - field696 * 50) / field697 + 50;
               DirectByteArrayCopier.drawLoadingMessage("Loading - please wait.<br> (" + var3 + "%)", false);
            } else {
               DirectByteArrayCopier.drawLoadingMessage("Loading - please wait.", false);
            }
         } else if (gameState == 30) {
            this.drawLoggedIn();
         } else if (gameState == 40) {
            DirectByteArrayCopier.drawLoadingMessage("Connection lost<br>Please wait - attempting to reestablish", false);
         } else if (gameState == 45) {
            DirectByteArrayCopier.drawLoadingMessage("Please wait...", false);
         }
      } else {
         FriendSystem.drawTitle(Widget.fontBold12, Actor.fontPlain11, UserComparator3.fontPlain12);
      }

      if (gameState == 30 && gameDrawingMode == 0 && !var1 && !isResizable) {
         for(var3 = 0; var3 < rootWidgetCount; ++var3) {
            if (field780[var3]) {
               class26.rasterProvider.draw(rootWidgetXs[var3], rootWidgetYs[var3], rootWidgetWidths[var3], rootWidgetHeights[var3]);
               field780[var3] = false;
            }
         }
      } else if (gameState > 0) {
         class26.rasterProvider.drawFull(0, 0);

         for(var3 = 0; var3 < rootWidgetCount; ++var3) {
            field780[var3] = false;
         }
      }

   }

   @ObfuscatedName("ai")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "561275520"
   )
   @Export("kill0")
   protected final void kill0() {
      if (GrandExchangeOfferOwnWorldComparator.varcs.hasUnwrittenChanges()) {
         GrandExchangeOfferOwnWorldComparator.varcs.write();
      }

      if (Skills.mouseRecorder != null) {
         Skills.mouseRecorder.isRunning = false;
      }

      Skills.mouseRecorder = null;
      packetWriter.close();
      if (KeyHandler.KeyHandler_instance != null) {
         KeyHandler var1 = KeyHandler.KeyHandler_instance;
         synchronized(KeyHandler.KeyHandler_instance) {
            KeyHandler.KeyHandler_instance = null;
         }
      }

      if (MouseHandler.MouseHandler_instance != null) {
         MouseHandler var6 = MouseHandler.MouseHandler_instance;
         synchronized(MouseHandler.MouseHandler_instance) {
            MouseHandler.MouseHandler_instance = null;
         }
      }

      MidiPcmStream.mouseWheel = null;
      if (DesktopPlatformInfoProvider.pcmPlayer0 != null) {
         DesktopPlatformInfoProvider.pcmPlayer0.shutdown();
      }

      if (MouseRecorder.pcmPlayer1 != null) {
         MouseRecorder.pcmPlayer1.shutdown();
      }

      VarcInt.method2673();
      Widget.method4759();
      if (ServerPacket.urlRequester != null) {
         ServerPacket.urlRequester.close();
         ServerPacket.urlRequester = null;
      }

      FriendSystem.method1746();
   }

   @ObfuscatedName("av")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "-992375266"
   )
   protected final void vmethod1471() {
   }

   public final void init() {
      try {
         if (this.checkHost()) {
            int var1;
            for(int var2 = 0; var2 <= 20; ++var2) {
               String var3 = this.getParameter(Integer.toString(var2));
               if (var3 != null) {
                  switch(var2) {
                  case 1:
                     useBufferedSocket = Integer.parseInt(var3) != 0;
                  case 2:
                  case 11:
                  case 13:
                  case 16:
                  default:
                     break;
                  case 3:
                     if (var3.equalsIgnoreCase("true")) {
                        isMembersWorld = true;
                     } else {
                        isMembersWorld = false;
                     }
                     break;
                  case 4:
                     if (clientType == -1) {
                        clientType = Integer.parseInt(var3);
                     }
                     break;
                  case 5:
                     worldProperties = Integer.parseInt(var3);
                     break;
                  case 6:
                     class378.clientLanguage = Language.method5151(Integer.parseInt(var3));
                     break;
                  case 7:
                     var1 = Integer.parseInt(var3);
                     GameBuild[] var4 = new GameBuild[]{GameBuild.WIP, GameBuild.LIVE, GameBuild.RC, GameBuild.BUILDLIVE};
                     GameBuild[] var5 = var4;
                     int var6 = 0;

                     GameBuild var7;
                     while(true) {
                        if (var6 >= var5.length) {
                           var7 = null;
                           break;
                        }

                        GameBuild var21 = var5[var6];
                        if (var1 == var21.buildId) {
                           var7 = var21;
                           break;
                        }

                        ++var6;
                     }

                     FaceNormal.field2365 = var7;
                     break;
                  case 8:
                     if (var3.equalsIgnoreCase("true")) {
                        ;
                     }
                     break;
                  case 9:
                     class363.field4079 = var3;
                     break;
                  case 10:
                     StudioGame[] var8 = new StudioGame[]{StudioGame.runescape, StudioGame.stellardawn, StudioGame.game5, StudioGame.oldscape, StudioGame.game3, StudioGame.game4};
                     SoundSystem.field461 = (StudioGame)Messages.findEnumerated(var8, Integer.parseInt(var3));
                     if (StudioGame.oldscape == SoundSystem.field461) {
                        WorldMapSection0.loginType = LoginType.oldscape;
                     } else {
                        WorldMapSection0.loginType = LoginType.field4091;
                     }
                     break;
                  case 12:
                     worldId = Integer.parseInt(var3);
                     break;
                  case 14:
                     Script.field1094 = Integer.parseInt(var3);
                     break;
                  case 15:
                     gameBuild = Integer.parseInt(var3);
                     break;
                  case 17:
                     class6.field58 = var3;
                  }
               }
            }

            Scene.Scene_isLowDetail = false;
            isLowDetail = false;
            class80.worldHost = this.getCodeBase().getHost();
            String var14 = FaceNormal.field2365.name;
            byte var15 = 0;

            try {
               SoundCache.idxCount = 21;
               UserComparator10.cacheGamebuild = var15;

               try {
                  class35.operatingSystemName = System.getProperty("os.name");
               } catch (Exception var11) {
                  class35.operatingSystemName = "Unknown";
               }

               UserComparator5.formattedOperatingSystemName = class35.operatingSystemName.toLowerCase();

               try {
                  class22.userHomeDirectory = System.getProperty("user.home");
                  if (class22.userHomeDirectory != null) {
                     class22.userHomeDirectory = class22.userHomeDirectory + "/";
                  }
               } catch (Exception var10) {
                  ;
               }

               try {
                  if (UserComparator5.formattedOperatingSystemName.startsWith("win")) {
                     if (class22.userHomeDirectory == null) {
                        class22.userHomeDirectory = System.getenv("USERPROFILE");
                     }
                  } else if (class22.userHomeDirectory == null) {
                     class22.userHomeDirectory = System.getenv("HOME");
                  }

                  if (class22.userHomeDirectory != null) {
                     class22.userHomeDirectory = class22.userHomeDirectory + "/";
                  }
               } catch (Exception var9) {
                  ;
               }

               if (class22.userHomeDirectory == null) {
                  class22.userHomeDirectory = "~/";
               }

               InterfaceParent.cacheParentPaths = new String[]{"c:/rscache/", "/rscache/", "c:/windows/", "c:/winnt/", "c:/", class22.userHomeDirectory, "/tmp/", ""};
               PlayerType.cacheSubPaths = new String[]{".jagex_cache_" + UserComparator10.cacheGamebuild, ".file_store_" + UserComparator10.cacheGamebuild};

               label133:
               for(int var16 = 0; var16 < 4; ++var16) {
                  class12.cacheDir = Canvas.method393("oldschool", var14, var16);
                  if (!class12.cacheDir.exists()) {
                     class12.cacheDir.mkdirs();
                  }

                  File[] var18 = class12.cacheDir.listFiles();
                  if (var18 == null) {
                     break;
                  }

                  File[] var19 = var18;
                  int var20 = 0;

                  while(true) {
                     if (var20 >= var19.length) {
                        break label133;
                     }

                     File var22 = var19[var20];
                     if (!FaceNormal.isWriteable(var22, false)) {
                        break;
                     }

                     ++var20;
                  }
               }

               File var17 = class12.cacheDir;
               FileSystem.FileSystem_cacheDir = var17;
               if (!FileSystem.FileSystem_cacheDir.exists()) {
                  throw new RuntimeException("");
               }

               FileSystem.FileSystem_hasPermissions = true;
               SoundCache.method830();
               JagexCache.JagexCache_dat2File = new BufferedFile(new AccessFile(ScriptFrame.getFile("main_file_cache.dat2"), "rw", 1048576000L), 5200, 0);
               JagexCache.JagexCache_idx255File = new BufferedFile(new AccessFile(ScriptFrame.getFile("main_file_cache.idx255"), "rw", 1048576L), 6000, 0);
               class93.JagexCache_idxFiles = new BufferedFile[SoundCache.idxCount];

               for(var1 = 0; var1 < SoundCache.idxCount; ++var1) {
                  class93.JagexCache_idxFiles[var1] = new BufferedFile(new AccessFile(ScriptFrame.getFile("main_file_cache.idx" + var1), "rw", 1048576L), 6000, 0);
               }
            } catch (Exception var12) {
               class266.RunException_sendStackTrace((String)null, var12);
            }

            class23.client = this;
            RunException.clientType = clientType;
            if (field643 == -1) {
               field643 = 0;
            }

            this.startThread(765, 503, 196);
         }

      } catch (RuntimeException var13) {
         throw WorldMapDecoration.newRunException(var13, "client.init()");
      }
   }

   @ObfuscatedName("ei")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "1243838219"
   )
   @Export("doCycleJs5")
   void doCycleJs5() {
      if (gameState != 1000) {
         long var1 = ObjectComposition.currentTimeMillis();
         int var3 = (int)(var1 - class18.field159);
         class18.field159 = var1;
         if (var3 > 200) {
            var3 = 200;
         }

         NetCache.NetCache_loadTime += var3;
         boolean var4;
         if (NetCache.NetCache_pendingResponsesCount == 0 && NetCache.NetCache_pendingPriorityResponsesCount == 0 && NetCache.NetCache_pendingWritesCount == 0 && NetCache.NetCache_pendingPriorityWritesCount == 0) {
            var4 = true;
         } else if (NetCache.NetCache_socket == null) {
            var4 = false;
         } else {
            try {
               label205: {
                  if (NetCache.NetCache_loadTime > 30000) {
                     throw new IOException();
                  }

                  NetFileRequest var5;
                  Buffer var6;
                  while(NetCache.NetCache_pendingPriorityResponsesCount < 200 && NetCache.NetCache_pendingPriorityWritesCount > 0) {
                     var5 = (NetFileRequest)NetCache.NetCache_pendingPriorityWrites.first();
                     var6 = new Buffer(4);
                     var6.writeByte(1);
                     var6.writeMedium((int)var5.key);
                     NetCache.NetCache_socket.write(var6.array, 0, 4);
                     NetCache.NetCache_pendingPriorityResponses.put(var5, var5.key);
                     --NetCache.NetCache_pendingPriorityWritesCount;
                     ++NetCache.NetCache_pendingPriorityResponsesCount;
                  }

                  while(NetCache.NetCache_pendingResponsesCount < 200 && NetCache.NetCache_pendingWritesCount > 0) {
                     var5 = (NetFileRequest)NetCache.NetCache_pendingWritesQueue.removeLast();
                     var6 = new Buffer(4);
                     var6.writeByte(0);
                     var6.writeMedium((int)var5.key);
                     NetCache.NetCache_socket.write(var6.array, 0, 4);
                     var5.removeDual();
                     NetCache.NetCache_pendingResponses.put(var5, var5.key);
                     --NetCache.NetCache_pendingWritesCount;
                     ++NetCache.NetCache_pendingResponsesCount;
                  }

                  for(int var7 = 0; var7 < 100; ++var7) {
                     int var8 = NetCache.NetCache_socket.available();
                     if (var8 < 0) {
                        throw new IOException();
                     }

                     if (var8 == 0) {
                        break;
                     }

                     NetCache.NetCache_loadTime = 0;
                     byte var9 = 0;
                     if (BuddyRankComparator.NetCache_currentResponse == null) {
                        var9 = 8;
                     } else if (NetCache.field3612 == 0) {
                        var9 = 1;
                     }

                     int var10;
                     int var11;
                     int var12;
                     int var13;
                     byte[] var14;
                     int var15;
                     Buffer var16;
                     if (var9 > 0) {
                        var10 = var9 - NetCache.NetCache_responseHeaderBuffer.offset;
                        if (var10 > var8) {
                           var10 = var8;
                        }

                        NetCache.NetCache_socket.read(NetCache.NetCache_responseHeaderBuffer.array, NetCache.NetCache_responseHeaderBuffer.offset, var10);
                        if (NetCache.field3607 != 0) {
                           for(var11 = 0; var11 < var10; ++var11) {
                              var14 = NetCache.NetCache_responseHeaderBuffer.array;
                              var15 = var11 + NetCache.NetCache_responseHeaderBuffer.offset;
                              var14[var15] ^= NetCache.field3607;
                           }
                        }

                        var16 = NetCache.NetCache_responseHeaderBuffer;
                        var16.offset += var10;
                        if (NetCache.NetCache_responseHeaderBuffer.offset < var9) {
                           break;
                        }

                        if (BuddyRankComparator.NetCache_currentResponse == null) {
                           NetCache.NetCache_responseHeaderBuffer.offset = 0;
                           var11 = NetCache.NetCache_responseHeaderBuffer.readUnsignedByte();
                           var12 = NetCache.NetCache_responseHeaderBuffer.readUnsignedShort();
                           int var17 = NetCache.NetCache_responseHeaderBuffer.readUnsignedByte();
                           var13 = NetCache.NetCache_responseHeaderBuffer.readInt();
                           long var18 = (long)(var12 + (var11 << 16));
                           NetFileRequest var20 = (NetFileRequest)NetCache.NetCache_pendingPriorityResponses.get(var18);
                           class8.field75 = true;
                           if (var20 == null) {
                              var20 = (NetFileRequest)NetCache.NetCache_pendingResponses.get(var18);
                              class8.field75 = false;
                           }

                           if (var20 == null) {
                              throw new IOException();
                           }

                           int var21 = var17 == 0 ? 5 : 9;
                           BuddyRankComparator.NetCache_currentResponse = var20;
                           SoundSystem.NetCache_responseArchiveBuffer = new Buffer(var21 + var13 + BuddyRankComparator.NetCache_currentResponse.padding);
                           SoundSystem.NetCache_responseArchiveBuffer.writeByte(var17);
                           SoundSystem.NetCache_responseArchiveBuffer.writeInt(var13);
                           NetCache.field3612 = 8;
                           NetCache.NetCache_responseHeaderBuffer.offset = 0;
                        } else if (NetCache.field3612 == 0) {
                           if (NetCache.NetCache_responseHeaderBuffer.array[0] == -1) {
                              NetCache.field3612 = 1;
                              NetCache.NetCache_responseHeaderBuffer.offset = 0;
                           } else {
                              BuddyRankComparator.NetCache_currentResponse = null;
                           }
                        }
                     } else {
                        var10 = SoundSystem.NetCache_responseArchiveBuffer.array.length - BuddyRankComparator.NetCache_currentResponse.padding;
                        var11 = 512 - NetCache.field3612;
                        if (var11 > var10 - SoundSystem.NetCache_responseArchiveBuffer.offset) {
                           var11 = var10 - SoundSystem.NetCache_responseArchiveBuffer.offset;
                        }

                        if (var11 > var8) {
                           var11 = var8;
                        }

                        NetCache.NetCache_socket.read(SoundSystem.NetCache_responseArchiveBuffer.array, SoundSystem.NetCache_responseArchiveBuffer.offset, var11);
                        if (NetCache.field3607 != 0) {
                           for(var12 = 0; var12 < var11; ++var12) {
                              var14 = SoundSystem.NetCache_responseArchiveBuffer.array;
                              var15 = var12 + SoundSystem.NetCache_responseArchiveBuffer.offset;
                              var14[var15] ^= NetCache.field3607;
                           }
                        }

                        var16 = SoundSystem.NetCache_responseArchiveBuffer;
                        var16.offset += var11;
                        NetCache.field3612 += var11;
                        if (var10 == SoundSystem.NetCache_responseArchiveBuffer.offset) {
                           if (BuddyRankComparator.NetCache_currentResponse.key == 16711935L) {
                              class125.NetCache_reference = SoundSystem.NetCache_responseArchiveBuffer;

                              for(var12 = 0; var12 < 256; ++var12) {
                                 Archive var25 = NetCache.NetCache_archives[var12];
                                 if (var25 != null) {
                                    class125.NetCache_reference.offset = var12 * 8 + 5;
                                    var13 = class125.NetCache_reference.readInt();
                                    int var26 = class125.NetCache_reference.readInt();
                                    var25.loadIndex(var13, var26);
                                 }
                              }
                           } else {
                              NetCache.NetCache_crc.reset();
                              NetCache.NetCache_crc.update(SoundSystem.NetCache_responseArchiveBuffer.array, 0, var10);
                              var12 = (int)NetCache.NetCache_crc.getValue();
                              if (var12 != BuddyRankComparator.NetCache_currentResponse.crc) {
                                 try {
                                    NetCache.NetCache_socket.close();
                                 } catch (Exception var23) {
                                    ;
                                 }

                                 ++NetCache.NetCache_crcMismatches;
                                 NetCache.NetCache_socket = null;
                                 NetCache.field3607 = (byte)((int)(Math.random() * 255.0D + 1.0D));
                                 var4 = false;
                                 break label205;
                              }

                              NetCache.NetCache_crcMismatches = 0;
                              NetCache.NetCache_ioExceptions = 0;
                              BuddyRankComparator.NetCache_currentResponse.archive.write((int)(BuddyRankComparator.NetCache_currentResponse.key & 65535L), SoundSystem.NetCache_responseArchiveBuffer.array, (BuddyRankComparator.NetCache_currentResponse.key & 16711680L) == 16711680L, class8.field75);
                           }

                           BuddyRankComparator.NetCache_currentResponse.remove();
                           if (class8.field75) {
                              --NetCache.NetCache_pendingPriorityResponsesCount;
                           } else {
                              --NetCache.NetCache_pendingResponsesCount;
                           }

                           NetCache.field3612 = 0;
                           BuddyRankComparator.NetCache_currentResponse = null;
                           SoundSystem.NetCache_responseArchiveBuffer = null;
                        } else {
                           if (NetCache.field3612 != 512) {
                              break;
                           }

                           NetCache.field3612 = 0;
                        }
                     }
                  }

                  var4 = true;
               }
            } catch (IOException var24) {
               try {
                  NetCache.NetCache_socket.close();
               } catch (Exception var22) {
                  ;
               }

               ++NetCache.NetCache_ioExceptions;
               NetCache.NetCache_socket = null;
               var4 = false;
            }
         }

         if (!var4) {
            this.doCycleJs5Connect();
         }
      }

   }

   @ObfuscatedName("ev")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "1090327888"
   )
   @Export("doCycleJs5Connect")
   void doCycleJs5Connect() {
      if (NetCache.NetCache_crcMismatches >= 4) {
         this.error("js5crc");
         gameState = 1000;
         onGameStateChanged(-1);
      } else {
         if (NetCache.NetCache_ioExceptions >= 4) {
            if (gameState <= 5) {
               this.error("js5io");
               gameState = 1000;
               onGameStateChanged(-1);
               return;
            }

            field670 = 3000;
            NetCache.NetCache_ioExceptions = 3;
         }

         if (--field670 + 1 <= 0) {
            try {
               if (js5ConnectState == 0) {
                  class159.js5SocketTask = GameEngine.taskHandler.newSocketTask(class80.worldHost, DesktopPlatformInfoProvider.currentPort);
                  ++js5ConnectState;
               }

               if (js5ConnectState == 1) {
                  if (class159.js5SocketTask.status == 2) {
                     this.js5Error(-1);
                     return;
                  }

                  if (class159.js5SocketTask.status == 1) {
                     ++js5ConnectState;
                  }
               }

               if (js5ConnectState == 2) {
                  if (useBufferedSocket) {
                     Socket var1 = (Socket)class159.js5SocketTask.result;
                     BufferedNetSocket var2 = new BufferedNetSocket(var1, 40000, 5000);
                     WorldMapID.js5Socket = var2;
                  } else {
                     WorldMapID.js5Socket = new NetSocket((Socket)class159.js5SocketTask.result, GameEngine.taskHandler, 5000);
                  }

                  Buffer var10 = new Buffer(5);
                  var10.writeByte(15);
                  var10.writeInt(196);
                  WorldMapID.js5Socket.write(var10.array, 0, 5);
                  ++js5ConnectState;
                  ArchiveDiskAction.field3556 = ObjectComposition.currentTimeMillis();
               }

               if (js5ConnectState == 3) {
                  if (WorldMapID.js5Socket.available() > 0 || !useBufferedSocket && gameState <= 5) {
                     int var11 = WorldMapID.js5Socket.readUnsignedByte();
                     if (var11 != 0) {
                        this.js5Error(var11);
                        return;
                     }

                     ++js5ConnectState;
                  } else if (ObjectComposition.currentTimeMillis() - ArchiveDiskAction.field3556 > 30000L) {
                     this.js5Error(-2);
                     return;
                  }
               }

               if (js5ConnectState == 4) {
                  AbstractSocket var12 = WorldMapID.js5Socket;
                  boolean var13 = gameState > 20;
                  if (NetCache.NetCache_socket != null) {
                     try {
                        NetCache.NetCache_socket.close();
                     } catch (Exception var8) {
                        ;
                     }

                     NetCache.NetCache_socket = null;
                  }

                  NetCache.NetCache_socket = var12;
                  StudioGame.method4847(var13);
                  NetCache.NetCache_responseHeaderBuffer.offset = 0;
                  BuddyRankComparator.NetCache_currentResponse = null;
                  SoundSystem.NetCache_responseArchiveBuffer = null;
                  NetCache.field3612 = 0;

                  while(true) {
                     NetFileRequest var3 = (NetFileRequest)NetCache.NetCache_pendingPriorityResponses.first();
                     if (var3 == null) {
                        while(true) {
                           var3 = (NetFileRequest)NetCache.NetCache_pendingResponses.first();
                           if (var3 == null) {
                              if (NetCache.field3607 != 0) {
                                 try {
                                    Buffer var4 = new Buffer(4);
                                    var4.writeByte(4);
                                    var4.writeByte(NetCache.field3607);
                                    var4.writeShort(0);
                                    NetCache.NetCache_socket.write(var4.array, 0, 4);
                                 } catch (IOException var7) {
                                    try {
                                       NetCache.NetCache_socket.close();
                                    } catch (Exception var6) {
                                       ;
                                    }

                                    ++NetCache.NetCache_ioExceptions;
                                    NetCache.NetCache_socket = null;
                                 }
                              }

                              NetCache.NetCache_loadTime = 0;
                              class18.field159 = ObjectComposition.currentTimeMillis();
                              class159.js5SocketTask = null;
                              WorldMapID.js5Socket = null;
                              js5ConnectState = 0;
                              js5Errors = 0;
                              return;
                           }

                           NetCache.NetCache_pendingWritesQueue.addLast(var3);
                           NetCache.NetCache_pendingWrites.put(var3, var3.key);
                           ++NetCache.NetCache_pendingWritesCount;
                           --NetCache.NetCache_pendingResponsesCount;
                        }
                     }

                     NetCache.NetCache_pendingPriorityWrites.put(var3, var3.key);
                     ++NetCache.NetCache_pendingPriorityWritesCount;
                     --NetCache.NetCache_pendingPriorityResponsesCount;
                  }
               }
            } catch (IOException var9) {
               this.js5Error(-3);
            }
         }
      }

   }

   @ObfuscatedName("eq")
   @ObfuscatedSignature(
      descriptor = "(II)V",
      garbageValue = "1995142761"
   )
   @Export("js5Error")
   void js5Error(int var1) {
      class159.js5SocketTask = null;
      WorldMapID.js5Socket = null;
      js5ConnectState = 0;
      if (WorldMapLabelSize.worldPort == DesktopPlatformInfoProvider.currentPort) {
         DesktopPlatformInfoProvider.currentPort = GrandExchangeOfferTotalQuantityComparator.js5Port;
      } else {
         DesktopPlatformInfoProvider.currentPort = WorldMapLabelSize.worldPort;
      }

      ++js5Errors;
      if (js5Errors < 2 || var1 != 7 && var1 != 9) {
         if (js5Errors >= 2 && var1 == 6) {
            this.error("js5connect_outofdate");
            gameState = 1000;
            onGameStateChanged(-1);
         } else if (js5Errors >= 4) {
            if (gameState <= 5) {
               this.error("js5connect");
               gameState = 1000;
               onGameStateChanged(-1);
            } else {
               field670 = 3000;
            }
         }
      } else if (gameState <= 5) {
         this.error("js5connect_full");
         gameState = 1000;
         onGameStateChanged(-1);
      } else {
         field670 = 3000;
      }

   }

   @ObfuscatedName("fe")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "-607645058"
   )
   @Export("doCycleLoggedOut")
   final void doCycleLoggedOut() {
      Object var1 = packetWriter.getSocket();
      PacketBuffer var2 = packetWriter.packetBuffer;

      try {
         if (loginState == 0) {
            if (ClientPreferences.secureRandom == null && (secureRandomFuture.isDone() || field673 > 250)) {
               ClientPreferences.secureRandom = secureRandomFuture.get();
               secureRandomFuture.shutdown();
               secureRandomFuture = null;
            }

            if (ClientPreferences.secureRandom != null) {
               if (var1 != null) {
                  ((AbstractSocket)var1).close();
                  var1 = null;
               }

               UserComparator9.socketTask = null;
               hadNetworkError = false;
               field673 = 0;
               loginState = 1;
            }
         }

         if (loginState == 1) {
            if (UserComparator9.socketTask == null) {
               UserComparator9.socketTask = GameEngine.taskHandler.newSocketTask(class80.worldHost, DesktopPlatformInfoProvider.currentPort);
            }

            if (UserComparator9.socketTask.status == 2) {
               throw new IOException();
            }

            if (UserComparator9.socketTask.status == 1) {
               if (useBufferedSocket) {
                  Socket var3 = (Socket)UserComparator9.socketTask.result;
                  BufferedNetSocket var4 = new BufferedNetSocket(var3, 40000, 5000);
                  var1 = var4;
               } else {
                  var1 = new NetSocket((Socket)UserComparator9.socketTask.result, GameEngine.taskHandler, 5000);
               }

               packetWriter.setSocket((AbstractSocket)var1);
               UserComparator9.socketTask = null;
               loginState = 2;
            }
         }

         PacketBufferNode var22;
         if (loginState == 2) {
            packetWriter.clearBuffer();
            var22 = class22.method247();
            var22.packetBuffer.writeByte(LoginPacket.field2806.id);
            packetWriter.addNode(var22);
            packetWriter.flush();
            var2.offset = 0;
            loginState = 3;
         }

         int var5;
         boolean var23;
         if (loginState == 3) {
            if (DesktopPlatformInfoProvider.pcmPlayer0 != null) {
               DesktopPlatformInfoProvider.pcmPlayer0.method733();
            }

            if (MouseRecorder.pcmPlayer1 != null) {
               MouseRecorder.pcmPlayer1.method733();
            }

            var23 = true;
            if (useBufferedSocket && !((AbstractSocket)var1).isAvailable(1)) {
               var23 = false;
            }

            if (var23) {
               var5 = ((AbstractSocket)var1).readUnsignedByte();
               if (DesktopPlatformInfoProvider.pcmPlayer0 != null) {
                  DesktopPlatformInfoProvider.pcmPlayer0.method733();
               }

               if (MouseRecorder.pcmPlayer1 != null) {
                  MouseRecorder.pcmPlayer1.method733();
               }

               if (var5 != 0) {
                  MouseHandler.getLoginError(var5);
                  return;
               }

               var2.offset = 0;
               loginState = 4;
            }
         }

         int var6;
         if (loginState == 4) {
            if (var2.offset < 8) {
               var6 = ((AbstractSocket)var1).available();
               if (var6 > 8 - var2.offset) {
                  var6 = 8 - var2.offset;
               }

               if (var6 > 0) {
                  ((AbstractSocket)var1).read(var2.array, var2.offset, var6);
                  var2.offset += var6;
               }
            }

            if (var2.offset == 8) {
               var2.offset = 0;
               GrandExchangeOfferAgeComparator.field3634 = var2.readLong();
               loginState = 5;
            }
         }

         int var7;
         int var8;
         int var9;
         int var12;
         if (loginState == 5) {
            packetWriter.packetBuffer.offset = 0;
            packetWriter.clearBuffer();
            PacketBuffer var10 = new PacketBuffer(500);
            int[] var11 = new int[]{ClientPreferences.secureRandom.nextInt(), ClientPreferences.secureRandom.nextInt(), ClientPreferences.secureRandom.nextInt(), ClientPreferences.secureRandom.nextInt()};
            var10.offset = 0;
            var10.writeByte(1);
            var10.writeInt(var11[0]);
            var10.writeInt(var11[1]);
            var10.writeInt(var11[2]);
            var10.writeInt(var11[3]);
            var10.writeLong(GrandExchangeOfferAgeComparator.field3634);
            if (gameState == 40) {
               var10.writeInt(HealthBarDefinition.field1603[0]);
               var10.writeInt(HealthBarDefinition.field1603[1]);
               var10.writeInt(HealthBarDefinition.field1603[2]);
               var10.writeInt(HealthBarDefinition.field1603[3]);
            } else {
               var10.writeByte(field676.rsOrdinal());
               switch(field676.field1472) {
               case 0:
                  var10.offset += 4;
                  break;
               case 1:
               case 3:
                  var10.writeMedium(class5.field53);
                  ++var10.offset;
                  break;
               case 2:
                  LinkedHashMap var13 = ObjectComposition.clientPreferences.parameters;
                  String var14 = Login.Login_username;
                  var8 = var14.length();
                  int var15 = 0;

                  for(var12 = 0; var12 < var8; ++var12) {
                     var15 = (var15 << 5) - var15 + var14.charAt(var12);
                  }

                  var10.writeInt((Integer)var13.get(var15));
               }

               var10.writeByte(class386.field4210.rsOrdinal());
               var10.writeStringCp1252NullTerminated(Login.Login_password);
            }

            var10.encryptRsa(class82.field1018, class82.field1017);
            HealthBarDefinition.field1603 = var11;
            PacketBufferNode var30 = class22.method247();
            var30.packetBuffer.offset = 0;
            if (gameState == 40) {
               var30.packetBuffer.writeByte(LoginPacket.field2799.id);
            } else {
               var30.packetBuffer.writeByte(LoginPacket.field2802.id);
            }

            var30.packetBuffer.writeShort(0);
            var9 = var30.packetBuffer.offset;
            var30.packetBuffer.writeInt(196);
            var30.packetBuffer.writeInt(1);
            var30.packetBuffer.writeByte(clientType);
            var30.packetBuffer.writeByte(field643);
            var30.packetBuffer.writeBytes(var10.array, 0, var10.offset);
            var7 = var30.packetBuffer.offset;
            var30.packetBuffer.writeStringCp1252NullTerminated(Login.Login_username);
            var30.packetBuffer.writeByte((isResizable ? 1 : 0) << 1 | (isLowDetail ? 1 : 0));
            var30.packetBuffer.writeShort(class32.canvasWidth);
            var30.packetBuffer.writeShort(ReflectionCheck.canvasHeight);
            PacketBuffer var34 = var30.packetBuffer;
            if (randomDatData != null) {
               var34.writeBytes(randomDatData, 0, randomDatData.length);
            } else {
               byte[] var37 = new byte[24];

               try {
                  JagexCache.JagexCache_randomDat.seek(0L);
                  JagexCache.JagexCache_randomDat.readFully(var37);

                  for(var12 = 0; var12 < 24 && var37[var12] == 0; ++var12) {
                     ;
                  }

                  if (var12 >= 24) {
                     throw new IOException();
                  }
               } catch (Exception var20) {
                  for(int var17 = 0; var17 < 24; ++var17) {
                     var37[var17] = -1;
                  }
               }

               var34.writeBytes(var37, 0, var37.length);
            }

            var30.packetBuffer.writeStringCp1252NullTerminated(class363.field4079);
            var30.packetBuffer.writeInt(Script.field1094);
            Buffer var39 = new Buffer(class10.platformInfo.size());
            class10.platformInfo.write(var39);
            var30.packetBuffer.writeBytes(var39.array, 0, var39.array.length);
            var30.packetBuffer.writeByte(clientType);
            var30.packetBuffer.writeInt(0);
            var30.packetBuffer.writeInt(UrlRequest.archive6.hash);
            var30.packetBuffer.method6612(0);
            var30.packetBuffer.method6612(class8.archive17.hash);
            var30.packetBuffer.writeIntME(class27.archive1.hash);
            var30.packetBuffer.method6612(ClanChannel.archive3.hash);
            var30.packetBuffer.writeIntME(class5.archive20.hash);
            var30.packetBuffer.writeInt(class179.archive19.hash);
            var30.packetBuffer.method6611(NetCache.archive0.hash);
            var30.packetBuffer.writeInt(ClanMate.archive15.hash);
            var30.packetBuffer.writeInt(SoundCache.archive10.hash);
            var30.packetBuffer.writeInt(class8.archive2.hash);
            var30.packetBuffer.method6611(WorldMapManager.archive11.hash);
            var30.packetBuffer.writeIntME(class32.archive4.hash);
            var30.packetBuffer.writeInt(Decimator.archive13.hash);
            var30.packetBuffer.writeIntME(class125.archive14.hash);
            var30.packetBuffer.method6612(CollisionMap.archive9.hash);
            var30.packetBuffer.method6611(UserComparator5.archive12.hash);
            var30.packetBuffer.writeIntME(FontName.archive18.hash);
            var30.packetBuffer.method6611(class247.archive5.hash);
            var30.packetBuffer.writeInt(Decimator.archive7.hash);
            var30.packetBuffer.writeInt(GrandExchangeOfferAgeComparator.archive8.hash);
            var30.packetBuffer.xteaEncrypt(var11, var7, var30.packetBuffer.offset);
            var30.packetBuffer.writeLengthShort(var30.packetBuffer.offset - var9);
            packetWriter.addNode(var30);
            packetWriter.flush();
            packetWriter.isaacCipher = new IsaacCipher(var11);
            int[] var16 = new int[4];

            for(var12 = 0; var12 < 4; ++var12) {
               var16[var12] = var11[var12] + 50;
            }

            var2.newIsaacCipher(var16);
            loginState = 6;
         }

         if (loginState == 6 && ((AbstractSocket)var1).available() > 0) {
            var6 = ((AbstractSocket)var1).readUnsignedByte();
            if (var6 == 21 && gameState == 20) {
               loginState = 12;
            } else if (var6 == 2) {
               loginState = 14;
            } else if (var6 == 15 && gameState == 40) {
               packetWriter.serverPacketLength = -1;
               loginState = 19;
            } else if (var6 == 64) {
               loginState = 10;
            } else if (var6 == 23 && field845 < 1) {
               ++field845;
               loginState = 0;
            } else if (var6 == 29) {
               loginState = 17;
            } else {
               if (var6 != 69) {
                  MouseHandler.getLoginError(var6);
                  return;
               }

               loginState = 7;
            }
         }

         if (loginState == 7 && ((AbstractSocket)var1).available() >= 2) {
            ((AbstractSocket)var1).read(var2.array, 0, 2);
            var2.offset = 0;
            class27.field231 = var2.readUnsignedShort();
            loginState = 8;
         }

         if (loginState == 8 && ((AbstractSocket)var1).available() >= class27.field231) {
            var2.offset = 0;
            ((AbstractSocket)var1).read(var2.array, var2.offset, class27.field231);
            class34[] var24 = new class34[]{class34.field256};
            class34 var26 = var24[var2.readUnsignedByte()];

            try {
               class31 var32 = class374.method6477(var26);
               this.field680 = new class35(var2, var32);
               loginState = 9;
            } catch (Exception var19) {
               MouseHandler.getLoginError(22);
               return;
            }
         }

         if (loginState == 9 && this.field680.method342()) {
            this.field679 = this.field680.method331();
            this.field680.method332();
            this.field680 = null;
            if (this.field679 == null) {
               MouseHandler.getLoginError(22);
               return;
            }

            packetWriter.clearBuffer();
            var22 = class22.method247();
            var22.packetBuffer.writeByte(LoginPacket.field2803.id);
            var22.packetBuffer.writeShort(this.field679.offset);
            var22.packetBuffer.method6768(this.field679);
            packetWriter.addNode(var22);
            packetWriter.flush();
            this.field679 = null;
            loginState = 6;
         }

         if (loginState == 10 && ((AbstractSocket)var1).available() > 0) {
            ChatChannel.field1110 = ((AbstractSocket)var1).readUnsignedByte();
            loginState = 11;
         }

         if (loginState == 11 && ((AbstractSocket)var1).available() >= ChatChannel.field1110) {
            ((AbstractSocket)var1).read(var2.array, 0, ChatChannel.field1110);
            var2.offset = 0;
            loginState = 6;
         }

         if (loginState == 12 && ((AbstractSocket)var1).available() > 0) {
            field675 = (((AbstractSocket)var1).readUnsignedByte() + 3) * 60;
            loginState = 13;
         }

         if (loginState == 13) {
            field673 = 0;
            class260.setLoginResponseString("You have only just left another world.", "Your profile will be transferred in:", field675 / 60 + " seconds.");
            if (--field675 <= 0) {
               loginState = 0;
            }
         } else {
            if (loginState == 14 && ((AbstractSocket)var1).available() >= 1) {
               class20.field184 = ((AbstractSocket)var1).readUnsignedByte();
               loginState = 15;
            }

            boolean var25;
            String var27;
            if (loginState == 15 && ((AbstractSocket)var1).available() >= class20.field184) {
               var23 = ((AbstractSocket)var1).readUnsignedByte() == 1;
               ((AbstractSocket)var1).read(var2.array, 0, 4);
               var2.offset = 0;
               var25 = false;
               if (var23) {
                  var5 = var2.readByteIsaac() << 24;
                  var5 |= var2.readByteIsaac() << 16;
                  var5 |= var2.readByteIsaac() << 8;
                  var5 |= var2.readByteIsaac();
                  var27 = Login.Login_username;
                  var7 = var27.length();
                  var12 = 0;
                  var8 = 0;

                  while(true) {
                     if (var8 >= var7) {
                        if (ObjectComposition.clientPreferences.parameters.size() >= 10 && !ObjectComposition.clientPreferences.parameters.containsKey(var12)) {
                           Iterator var31 = ObjectComposition.clientPreferences.parameters.entrySet().iterator();
                           var31.next();
                           var31.remove();
                        }

                        ObjectComposition.clientPreferences.parameters.put(var12, var5);
                        break;
                     }

                     var12 = (var12 << 5) - var12 + var27.charAt(var8);
                     ++var8;
                  }
               }

               if (Login_isUsernameRemembered) {
                  ObjectComposition.clientPreferences.rememberedUsername = Login.Login_username;
               } else {
                  ObjectComposition.clientPreferences.rememberedUsername = null;
               }

               TileItem.savePreferences();
               staffModLevel = ((AbstractSocket)var1).readUnsignedByte();
               playerMod = ((AbstractSocket)var1).readUnsignedByte() == 1;
               localPlayerIndex = ((AbstractSocket)var1).readUnsignedByte();
               localPlayerIndex <<= 8;
               localPlayerIndex += ((AbstractSocket)var1).readUnsignedByte();
               field759 = ((AbstractSocket)var1).readUnsignedByte();
               ((AbstractSocket)var1).read(var2.array, 0, 1);
               var2.offset = 0;
               ServerPacket[] var28 = class24.ServerPacket_values();
               var9 = var2.readSmartByteShortIsaac();
               if (var9 < 0 || var9 >= var28.length) {
                  throw new IOException(var9 + " " + var2.offset);
               }

               packetWriter.serverPacket = var28[var9];
               packetWriter.serverPacketLength = packetWriter.serverPacket.length;
               ((AbstractSocket)var1).read(var2.array, 0, 2);
               var2.offset = 0;
               packetWriter.serverPacketLength = var2.readUnsignedShort();

               try {
                  class42.method421(class23.client, "zap");
               } catch (Throwable var18) {
                  ;
               }

               loginState = 16;
            }

            if (loginState != 16) {
               if (loginState == 17 && ((AbstractSocket)var1).available() >= 2) {
                  var2.offset = 0;
                  ((AbstractSocket)var1).read(var2.array, 0, 2);
                  var2.offset = 0;
                  WorldMapData_1.field2008 = var2.readUnsignedShort();
                  loginState = 18;
               }

               if (loginState == 18 && ((AbstractSocket)var1).available() >= WorldMapData_1.field2008) {
                  var2.offset = 0;
                  ((AbstractSocket)var1).read(var2.array, 0, WorldMapData_1.field2008);
                  var2.offset = 0;
                  var27 = var2.readStringCp1252NullTerminated();
                  String var35 = var2.readStringCp1252NullTerminated();
                  String var33 = var2.readStringCp1252NullTerminated();
                  class260.setLoginResponseString(var27, var35, var33);
                  class12.updateGameState(10);
               }

               if (loginState == 19) {
                  if (packetWriter.serverPacketLength == -1) {
                     if (((AbstractSocket)var1).available() < 2) {
                        return;
                     }

                     ((AbstractSocket)var1).read(var2.array, 0, 2);
                     var2.offset = 0;
                     packetWriter.serverPacketLength = var2.readUnsignedShort();
                  }

                  if (((AbstractSocket)var1).available() >= packetWriter.serverPacketLength) {
                     ((AbstractSocket)var1).read(var2.array, 0, packetWriter.serverPacketLength);
                     var2.offset = 0;
                     var6 = packetWriter.serverPacketLength;
                     timer.method5594();
                     WorldMapSectionType.method3594();
                     Coord.updatePlayer(var2);
                     if (var6 != var2.offset) {
                        throw new RuntimeException();
                     }
                  }
               } else {
                  ++field673;
                  if (field673 > 2000) {
                     if (field845 < 1) {
                        if (DesktopPlatformInfoProvider.currentPort == WorldMapLabelSize.worldPort) {
                           DesktopPlatformInfoProvider.currentPort = GrandExchangeOfferTotalQuantityComparator.js5Port;
                        } else {
                           DesktopPlatformInfoProvider.currentPort = WorldMapLabelSize.worldPort;
                        }

                        ++field845;
                        loginState = 0;
                     } else {
                        MouseHandler.getLoginError(-3);
                     }
                  }
               }
            } else if (((AbstractSocket)var1).available() >= packetWriter.serverPacketLength) {
               var2.offset = 0;
               ((AbstractSocket)var1).read(var2.array, 0, packetWriter.serverPacketLength);
               timer.method5592();
               mouseLastLastPressedTimeMillis = -1L;
               Skills.mouseRecorder.index = 0;
               WorldMapSection1.hasFocus = true;
               hadFocus = true;
               field809 = -1L;
               SoundCache.method811();
               packetWriter.clearBuffer();
               packetWriter.packetBuffer.offset = 0;
               packetWriter.serverPacket = null;
               packetWriter.field1411 = null;
               packetWriter.field1412 = null;
               packetWriter.field1400 = null;
               packetWriter.serverPacketLength = 0;
               packetWriter.field1409 = 0;
               rebootTimer = 0;
               logoutTimer = 0;
               hintArrowType = 0;
               class1.method11();
               ArchiveDiskActionHandler.method4867(0);
               class43.method437();
               isItemSelected = 0;
               isSpellSelected = false;
               soundEffectCount = 0;
               camAngleY = 0;
               oculusOrbState = 0;
               UserComparator3.field1444 = null;
               minimapState = 0;
               field872 = -1;
               destinationX = 0;
               destinationY = 0;
               playerAttackOption = AttackOption.AttackOption_hidden;
               npcAttackOption = AttackOption.AttackOption_hidden;
               npcCount = 0;
               Player.method2110();

               for(var6 = 0; var6 < 2048; ++var6) {
                  players[var6] = null;
               }

               for(var6 = 0; var6 < 32768; ++var6) {
                  npcs[var6] = null;
               }

               combatTargetPlayerIndex = -1;
               projectiles.clear();
               graphicsObjects.clear();

               int var29;
               for(var6 = 0; var6 < 4; ++var6) {
                  for(var5 = 0; var5 < 104; ++var5) {
                     for(var29 = 0; var29 < 104; ++var29) {
                        groundItems[var6][var5][var29] = null;
                     }
                  }
               }

               pendingSpawns = new NodeDeque();
               NetSocket.friendSystem.clear();

               for(var6 = 0; var6 < VarpDefinition.VarpDefinition_fileCount; ++var6) {
                  VarpDefinition var36 = ClanSettings.VarpDefinition_get(var6);
                  if (var36 != null) {
                     Varps.Varps_temp[var6] = 0;
                     Varps.Varps_main[var6] = 0;
                  }
               }

               GrandExchangeOfferOwnWorldComparator.varcs.clearTransient();
               followerIndex = -1;
               if (rootInterface != -1) {
                  var6 = rootInterface;
                  if (var6 != -1 && Widget.Widget_loadedInterfaces[var6]) {
                     HealthBarUpdate.Widget_archive.clearFilesGroup(var6);
                     if (Widget.Widget_interfaceComponents[var6] != null) {
                        var25 = true;

                        for(var29 = 0; var29 < Widget.Widget_interfaceComponents[var6].length; ++var29) {
                           if (Widget.Widget_interfaceComponents[var6][var29] != null) {
                              if (Widget.Widget_interfaceComponents[var6][var29].type != 2) {
                                 Widget.Widget_interfaceComponents[var6][var29] = null;
                              } else {
                                 var25 = false;
                              }
                           }
                        }

                        if (var25) {
                           Widget.Widget_interfaceComponents[var6] = null;
                        }

                        Widget.Widget_loadedInterfaces[var6] = false;
                     }
                  }
               }

               for(InterfaceParent var38 = (InterfaceParent)interfaceParents.first(); var38 != null; var38 = (InterfaceParent)interfaceParents.next()) {
                  class43.closeInterface(var38, true);
               }

               rootInterface = -1;
               interfaceParents = new NodeHashTable(8);
               meslayerContinueWidget = null;
               class1.method11();
               playerAppearance.update((int[])null, new int[]{0, 0, 0, 0, 0}, false, -1);

               for(var6 = 0; var6 < 8; ++var6) {
                  playerMenuActions[var6] = null;
                  playerOptionsPriorities[var6] = false;
               }

               Skeleton.method3921();
               isLoading = true;

               for(var6 = 0; var6 < 100; ++var6) {
                  field719[var6] = true;
               }

               var22 = class21.getPacketBufferNode(ClientPacket.field2662, packetWriter.isaacCipher);
               var22.packetBuffer.writeByte(SpotAnimationDefinition.getWindowedMode());
               var22.packetBuffer.writeShort(class32.canvasWidth);
               var22.packetBuffer.writeShort(ReflectionCheck.canvasHeight);
               packetWriter.addNode(var22);
               WorldMapRegion.friendsChat = null;
               NPCComposition.guestClanSettings = null;
               Arrays.fill(currentClanSettings, (Object)null);
               ApproximateRouteStrategy.guestClanChannel = null;
               Arrays.fill(currentClanChannels, (Object)null);

               for(var6 = 0; var6 < 8; ++var6) {
                  grandExchangeOffers[var6] = new GrandExchangeOffer();
               }

               BuddyRankComparator.grandExchangeEvents = null;
               Coord.updatePlayer(var2);
               GrandExchangeOfferOwnWorldComparator.field632 = -1;
               class339.loadRegions(false, var2);
               packetWriter.serverPacket = null;
            }
         }
      } catch (IOException var21) {
         if (field845 < 1) {
            if (DesktopPlatformInfoProvider.currentPort == WorldMapLabelSize.worldPort) {
               DesktopPlatformInfoProvider.currentPort = GrandExchangeOfferTotalQuantityComparator.js5Port;
            } else {
               DesktopPlatformInfoProvider.currentPort = WorldMapLabelSize.worldPort;
            }

            ++field845;
            loginState = 0;
         } else {
            MouseHandler.getLoginError(-2);
         }
      }

   }

   @ObfuscatedName("fg")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "-1496762169"
   )
   @Export("doCycleLoggedIn")
   final void doCycleLoggedIn() {
      if (rebootTimer > 1) {
         --rebootTimer;
      }

      if (logoutTimer > 0) {
         --logoutTimer;
      }

      if (hadNetworkError) {
         hadNetworkError = false;
         NetFileRequest.method4912();
      } else {
         if (!isMenuOpen) {
            NetSocket.addCancelMenuEntry();
         }

         int var1;
         for(var1 = 0; var1 < 100 && this.method1192(packetWriter); ++var1) {
            ;
         }

         if (gameState == 30) {
            int var2;
            PacketBufferNode var3;
            while(class20.method231()) {
               var3 = class21.getPacketBufferNode(ClientPacket.field2584, packetWriter.isaacCipher);
               var3.packetBuffer.writeByte(0);
               var2 = var3.packetBuffer.offset;
               UserComparator3.performReflectionCheck(var3.packetBuffer);
               var3.packetBuffer.writeLengthByte(var3.packetBuffer.offset - var2);
               packetWriter.addNode(var3);
            }

            if (timer.field3769) {
               var3 = class21.getPacketBufferNode(ClientPacket.field2645, packetWriter.isaacCipher);
               var3.packetBuffer.writeByte(0);
               var2 = var3.packetBuffer.offset;
               timer.write(var3.packetBuffer);
               var3.packetBuffer.writeLengthByte(var3.packetBuffer.offset - var2);
               packetWriter.addNode(var3);
               timer.method5593();
            }

            Object var15 = Skills.mouseRecorder.lock;
            int var4;
            int var5;
            int var6;
            int var7;
            int var8;
            int var9;
            int var10;
            int var11;
            int var12;
            int var13;
            PacketBuffer var14;
            synchronized(Skills.mouseRecorder.lock) {
               if (!field889) {
                  Skills.mouseRecorder.index = 0;
               } else if (MouseHandler.MouseHandler_lastButton != 0 || Skills.mouseRecorder.index >= 40) {
                  PacketBufferNode var16 = null;
                  var4 = 0;
                  var5 = 0;
                  var6 = 0;
                  var7 = 0;

                  for(var8 = 0; var8 < Skills.mouseRecorder.index && (var16 == null || var16.packetBuffer.offset - var4 < 246); ++var8) {
                     var5 = var8;
                     var9 = Skills.mouseRecorder.ys[var8];
                     if (var9 < -1) {
                        var9 = -1;
                     } else if (var9 > 65534) {
                        var9 = 65534;
                     }

                     var10 = Skills.mouseRecorder.xs[var8];
                     if (var10 < -1) {
                        var10 = -1;
                     } else if (var10 > 65534) {
                        var10 = 65534;
                     }

                     if (var10 != field651 || var9 != field812) {
                        if (var16 == null) {
                           var16 = class21.getPacketBufferNode(ClientPacket.field2628, packetWriter.isaacCipher);
                           var16.packetBuffer.writeByte(0);
                           var4 = var16.packetBuffer.offset;
                           var14 = var16.packetBuffer;
                           var14.offset += 2;
                           var6 = 0;
                           var7 = 0;
                        }

                        if (-1L != field653) {
                           var11 = var10 - field651;
                           var12 = var9 - field812;
                           var13 = (int)((Skills.mouseRecorder.millis[var8] - field653) / 20L);
                           var6 = (int)((long)var6 + (Skills.mouseRecorder.millis[var8] - field653) % 20L);
                        } else {
                           var11 = var10;
                           var12 = var9;
                           var13 = Integer.MAX_VALUE;
                        }

                        field651 = var10;
                        field812 = var9;
                        if (var13 < 8 && var11 >= -32 && var11 <= 31 && var12 >= -32 && var12 <= 31) {
                           var11 += 32;
                           var12 += 32;
                           var16.packetBuffer.writeShort((var13 << 12) + var12 + (var11 << 6));
                        } else if (var13 < 32 && var11 >= -128 && var11 <= 127 && var12 >= -128 && var12 <= 127) {
                           var11 += 128;
                           var12 += 128;
                           var16.packetBuffer.writeByte(var13 + 128);
                           var16.packetBuffer.writeShort(var12 + (var11 << 8));
                        } else if (var13 < 32) {
                           var16.packetBuffer.writeByte(var13 + 192);
                           if (var10 != -1 && var9 != -1) {
                              var16.packetBuffer.writeInt(var10 | var9 << 16);
                           } else {
                              var16.packetBuffer.writeInt(Integer.MIN_VALUE);
                           }
                        } else {
                           var16.packetBuffer.writeShort((var13 & 8191) + '\ue000');
                           if (var10 != -1 && var9 != -1) {
                              var16.packetBuffer.writeInt(var10 | var9 << 16);
                           } else {
                              var16.packetBuffer.writeInt(Integer.MIN_VALUE);
                           }
                        }

                        ++var7;
                        field653 = Skills.mouseRecorder.millis[var8];
                     }
                  }

                  if (var16 != null) {
                     var16.packetBuffer.writeLengthByte(var16.packetBuffer.offset - var4);
                     var8 = var16.packetBuffer.offset;
                     var16.packetBuffer.offset = var4;
                     var16.packetBuffer.writeByte(var6 / var7);
                     var16.packetBuffer.writeByte(var6 % var7);
                     var16.packetBuffer.offset = var8;
                     packetWriter.addNode(var16);
                  }

                  if (var5 >= Skills.mouseRecorder.index) {
                     Skills.mouseRecorder.index = 0;
                  } else {
                     MouseRecorder var17 = Skills.mouseRecorder;
                     var17.index -= var5;
                     System.arraycopy(Skills.mouseRecorder.xs, var5, Skills.mouseRecorder.xs, 0, Skills.mouseRecorder.index);
                     System.arraycopy(Skills.mouseRecorder.ys, var5, Skills.mouseRecorder.ys, 0, Skills.mouseRecorder.index);
                     System.arraycopy(Skills.mouseRecorder.millis, var5, Skills.mouseRecorder.millis, 0, Skills.mouseRecorder.index);
                  }
               }
            }

            PacketBufferNode var28;
            long var30;
            if (MouseHandler.MouseHandler_lastButton == 1 || !mouseCam && MouseHandler.MouseHandler_lastButton == 4 || MouseHandler.MouseHandler_lastButton == 2) {
               var30 = MouseHandler.MouseHandler_lastPressedTimeMillis - mouseLastLastPressedTimeMillis;
               if (var30 > 32767L) {
                  var30 = 32767L;
               }

               mouseLastLastPressedTimeMillis = MouseHandler.MouseHandler_lastPressedTimeMillis;
               var4 = MouseHandler.MouseHandler_lastPressedY;
               if (var4 < 0) {
                  var4 = 0;
               } else if (var4 > ReflectionCheck.canvasHeight) {
                  var4 = ReflectionCheck.canvasHeight;
               }

               var5 = MouseHandler.MouseHandler_lastPressedX;
               if (var5 < 0) {
                  var5 = 0;
               } else if (var5 > class32.canvasWidth) {
                  var5 = class32.canvasWidth;
               }

               var6 = (int)var30;
               var28 = class21.getPacketBufferNode(ClientPacket.field2663, packetWriter.isaacCipher);
               var28.packetBuffer.writeShort((MouseHandler.MouseHandler_lastButton == 2 ? 1 : 0) + (var6 << 1));
               var28.packetBuffer.writeShort(var5);
               var28.packetBuffer.writeShort(var4);
               packetWriter.addNode(var28);
            }

            if (KeyHandler.field292 > 0) {
               var3 = class21.getPacketBufferNode(ClientPacket.field2573, packetWriter.isaacCipher);
               var3.packetBuffer.writeShort(0);
               var2 = var3.packetBuffer.offset;
               var30 = ObjectComposition.currentTimeMillis();

               for(var6 = 0; var6 < KeyHandler.field292; ++var6) {
                  long var18 = var30 - field809;
                  if (var18 > 16777215L) {
                     var18 = 16777215L;
                  }

                  field809 = var30;
                  var3.packetBuffer.method6594(KeyHandler.field274[var6]);
                  var3.packetBuffer.method6758((int)var18);
               }

               var3.packetBuffer.writeLengthShort(var3.packetBuffer.offset - var2);
               packetWriter.addNode(var3);
            }

            if (field725 > 0) {
               --field725;
            }

            if (KeyHandler.KeyHandler_pressedKeys[96] || KeyHandler.KeyHandler_pressedKeys[97] || KeyHandler.KeyHandler_pressedKeys[98] || KeyHandler.KeyHandler_pressedKeys[99]) {
               field726 = true;
            }

            if (field726 && field725 <= 0) {
               field725 = 20;
               field726 = false;
               var3 = class21.getPacketBufferNode(ClientPacket.field2630, packetWriter.isaacCipher);
               var3.packetBuffer.method6619(camAngleX);
               var3.packetBuffer.writeShort(camAngleY);
               packetWriter.addNode(var3);
            }

            if (WorldMapSection1.hasFocus && !hadFocus) {
               hadFocus = true;
               var3 = class21.getPacketBufferNode(ClientPacket.field2580, packetWriter.isaacCipher);
               var3.packetBuffer.writeByte(1);
               packetWriter.addNode(var3);
            }

            if (!WorldMapSection1.hasFocus && hadFocus) {
               hadFocus = false;
               var3 = class21.getPacketBufferNode(ClientPacket.field2580, packetWriter.isaacCipher);
               var3.packetBuffer.writeByte(0);
               packetWriter.addNode(var3);
            }

            if (class243.worldMap != null) {
               class243.worldMap.method6100();
            }

            if (MusicPatch.ClanChat_inClanChat) {
               if (WorldMapRegion.friendsChat != null) {
                  WorldMapRegion.friendsChat.sort();
               }

               ClanChannelMember.method85();
               MusicPatch.ClanChat_inClanChat = false;
            }

            VarbitComposition.method2846();
            if (class22.Client_plane != field872) {
               field872 = class22.Client_plane;
               GraphicsObject.createMinimapSprite(class22.Client_plane);
            }

            if (gameState == 30) {
               for(PendingSpawn var34 = (PendingSpawn)pendingSpawns.last(); var34 != null; var34 = (PendingSpawn)pendingSpawns.previous()) {
                  if (var34.hitpoints > 0) {
                     --var34.hitpoints;
                  }

                  ObjectComposition var29;
                  boolean var33;
                  if (var34.hitpoints == 0) {
                     if (var34.objectId >= 0) {
                        var4 = var34.objectId;
                        var5 = var34.field1229;
                        var29 = class23.getObjectDefinition(var4);
                        if (var5 == 11) {
                           var5 = 10;
                        }

                        if (var5 >= 5 && var5 <= 8) {
                           var5 = 4;
                        }

                        var33 = var29.method2956(var5);
                        if (!var33) {
                           continue;
                        }
                     }

                     class247.addPendingSpawnToScene(var34.plane, var34.type, var34.x, var34.y, var34.objectId, var34.field1225, var34.field1229);
                     var34.remove();
                  } else {
                     if (var34.delay > 0) {
                        --var34.delay;
                     }

                     if (var34.delay == 0 && var34.x >= 1 && var34.y >= 1 && var34.x <= 102 && var34.y <= 102) {
                        if (var34.id >= 0) {
                           var4 = var34.id;
                           var5 = var34.field1232;
                           var29 = class23.getObjectDefinition(var4);
                           if (var5 == 11) {
                              var5 = 10;
                           }

                           if (var5 >= 5 && var5 <= 8) {
                              var5 = 4;
                           }

                           var33 = var29.method2956(var5);
                           if (!var33) {
                              continue;
                           }
                        }

                        class247.addPendingSpawnToScene(var34.plane, var34.type, var34.x, var34.y, var34.id, var34.orientation, var34.field1232);
                        var34.delay = -1;
                        if (var34.objectId == var34.id && var34.objectId == -1) {
                           var34.remove();
                        } else if (var34.id == var34.objectId && var34.orientation == var34.field1225 && var34.field1229 == var34.field1232) {
                           var34.remove();
                        }
                     }
                  }
               }

               for(var1 = 0; var1 < soundEffectCount; ++var1) {
                  --queuedSoundEffectDelays[var1];
                  if (queuedSoundEffectDelays[var1] >= -10) {
                     SoundEffect var31 = soundEffects[var1];
                     if (var31 == null) {
                        var14 = null;
                        var31 = SoundEffect.readSoundEffect(class32.archive4, soundEffectIds[var1], 0);
                        if (var31 == null) {
                           continue;
                        }

                        int[] var36 = queuedSoundEffectDelays;
                        var36[var1] += var31.calculateDelay();
                        soundEffects[var1] = var31;
                     }

                     if (queuedSoundEffectDelays[var1] < 0) {
                        if (soundLocations[var1] != 0) {
                           var5 = (soundLocations[var1] & 255) * 128;
                           var6 = soundLocations[var1] >> 16 & 255;
                           var7 = var6 * 128 + 64 - class93.localPlayer.x;
                           if (var7 < 0) {
                              var7 = -var7;
                           }

                           var8 = soundLocations[var1] >> 8 & 255;
                           var9 = var8 * 128 + 64 - class93.localPlayer.y;
                           if (var9 < 0) {
                              var9 = -var9;
                           }

                           var10 = var7 + var9 - 128;
                           if (var10 > var5) {
                              queuedSoundEffectDelays[var1] = -100;
                              continue;
                           }

                           if (var10 < 0) {
                              var10 = 0;
                           }

                           var4 = (var5 - var10) * ObjectComposition.clientPreferences.areaSoundEffectsVolume / var5;
                        } else {
                           var4 = ObjectComposition.clientPreferences.soundEffectsVolume;
                        }

                        if (var4 > 0) {
                           RawSound var38 = var31.toRawSound().resample(TileItem.decimator);
                           RawPcmStream var19 = RawPcmStream.createRawPcmStream(var38, 100, var4);
                           var19.setNumLoops(queuedSoundEffectLoops[var1] - 1);
                           class308.pcmStreamMixer.addSubStream(var19);
                        }

                        queuedSoundEffectDelays[var1] = -100;
                     }
                  } else {
                     --soundEffectCount;

                     for(var2 = var1; var2 < soundEffectCount; ++var2) {
                        soundEffectIds[var2] = soundEffectIds[var2 + 1];
                        soundEffects[var2] = soundEffects[var2 + 1];
                        queuedSoundEffectLoops[var2] = queuedSoundEffectLoops[var2 + 1];
                        queuedSoundEffectDelays[var2] = queuedSoundEffectDelays[var2 + 1];
                        soundLocations[var2] = soundLocations[var2 + 1];
                     }

                     --var1;
                  }
               }

               if (field881) {
                  boolean var32;
                  if (class232.musicPlayerStatus != 0) {
                     var32 = true;
                  } else {
                     var32 = class124.midiPcmStream.isReady();
                  }

                  if (!var32) {
                     if (ObjectComposition.clientPreferences.musicVolume != 0 && currentTrackGroupId != -1) {
                        LoginScreenAnimation.method2219(UrlRequest.archive6, currentTrackGroupId, 0, ObjectComposition.clientPreferences.musicVolume, false);
                     }

                     field881 = false;
                  }
               }

               ++packetWriter.field1409;
               if (packetWriter.field1409 <= 750) {
                  var1 = Players.Players_count;
                  int[] var37 = Players.Players_indices;

                  for(var4 = 0; var4 < var1; ++var4) {
                     Player var39 = players[var37[var4]];
                     if (var39 != null) {
                        class7.updateActorSequence(var39, 1);
                     }
                  }

                  for(var1 = 0; var1 < npcCount; ++var1) {
                     var2 = npcIndices[var1];
                     NPC var40 = npcs[var2];
                     if (var40 != null) {
                        class7.updateActorSequence(var40, var40.definition.size);
                     }
                  }

                  ClanSettings.method165();
                  ++field913;
                  if (mouseCrossColor != 0) {
                     mouseCrossState += 20;
                     if (mouseCrossState >= 400) {
                        mouseCrossColor = 0;
                     }
                  }

                  if (GrandExchangeEvents.field3626 != null) {
                     ++field747;
                     if (field747 >= 15) {
                        WorldMapCacheName.invalidateWidget(GrandExchangeEvents.field3626);
                        GrandExchangeEvents.field3626 = null;
                     }
                  }

                  Widget var42 = World.mousedOverWidgetIf1;
                  Widget var35 = ReflectionCheck.field609;
                  World.mousedOverWidgetIf1 = null;
                  ReflectionCheck.field609 = null;
                  draggedOnWidget = null;
                  field822 = false;
                  field819 = false;
                  field806 = 0;

                  while(ClanChannelMember.isKeyDown() && field806 < 128) {
                     if (staffModLevel >= 2 && KeyHandler.KeyHandler_pressedKeys[82] && ItemComposition.field1859 == 66) {
                        String var20 = "";

                        Message var21;
                        for(Iterator var22 = Messages.Messages_hashTable.iterator(); var22.hasNext(); var20 = var20 + var21.sender + ':' + var21.text + '\n') {
                           var21 = (Message)var22.next();
                        }

                        class23.client.clipboardSetString(var20);
                     } else if (oculusOrbState != 1 || class249.field3116 <= 0) {
                        field868[field806] = ItemComposition.field1859;
                        field867[field806] = class249.field3116;
                        ++field806;
                     }
                  }

                  if (FontName.method6297() && KeyHandler.KeyHandler_pressedKeys[82] && KeyHandler.KeyHandler_pressedKeys[81] && mouseWheelRotation != 0) {
                     var4 = class93.localPlayer.plane - mouseWheelRotation;
                     if (var4 < 0) {
                        var4 = 0;
                     } else if (var4 > 3) {
                        var4 = 3;
                     }

                     if (var4 != class93.localPlayer.plane) {
                        class4.method50(class93.localPlayer.pathX[0] + VertexNormal.baseX, class93.localPlayer.pathY[0] + SoundSystem.baseY, var4, false);
                     }

                     mouseWheelRotation = 0;
                  }

                  if (rootInterface != -1) {
                     class32.updateRootInterface(rootInterface, 0, 0, class32.canvasWidth, ReflectionCheck.canvasHeight, 0, 0);
                  }

                  ++cycleCntr;

                  while(true) {
                     Widget var41;
                     ScriptEvent var43;
                     Widget var44;
                     do {
                        var43 = (ScriptEvent)field652.removeLast();
                        if (var43 == null) {
                           while(true) {
                              do {
                                 var43 = (ScriptEvent)field846.removeLast();
                                 if (var43 == null) {
                                    while(true) {
                                       do {
                                          var43 = (ScriptEvent)scriptEvents.removeLast();
                                          if (var43 == null) {
                                             this.menu();
                                             if (class243.worldMap != null) {
                                                class243.worldMap.method6110(class22.Client_plane, (class93.localPlayer.x >> 7) + VertexNormal.baseX, (class93.localPlayer.y >> 7) + SoundSystem.baseY, false);
                                                class243.worldMap.loadCache();
                                             }

                                             if (clickedWidget != null) {
                                                this.method1198();
                                             }

                                             PacketBufferNode var23;
                                             if (Script.dragInventoryWidget != null) {
                                                WorldMapCacheName.invalidateWidget(Script.dragInventoryWidget);
                                                ++itemDragDuration;
                                                if (MouseHandler.MouseHandler_currentButton == 0) {
                                                   if (field848) {
                                                      if (Script.dragInventoryWidget == class18.hoveredItemContainer && dragItemSlotSource != dragItemSlotDestination) {
                                                         Widget var45 = Script.dragInventoryWidget;
                                                         byte var25 = 0;
                                                         if (field912 == 1 && var45.contentType == 206) {
                                                            var25 = 1;
                                                         }

                                                         if (var45.itemIds[dragItemSlotDestination] <= 0) {
                                                            var25 = 0;
                                                         }

                                                         if (Canvas.method394(class21.getWidgetFlags(var45))) {
                                                            var6 = dragItemSlotSource;
                                                            var7 = dragItemSlotDestination;
                                                            var45.itemIds[var7] = var45.itemIds[var6];
                                                            var45.itemQuantities[var7] = var45.itemQuantities[var6];
                                                            var45.itemIds[var6] = -1;
                                                            var45.itemQuantities[var6] = 0;
                                                         } else if (var25 == 1) {
                                                            var6 = dragItemSlotSource;
                                                            var7 = dragItemSlotDestination;

                                                            while(var6 != var7) {
                                                               if (var6 > var7) {
                                                                  var45.swapItems(var6 - 1, var6);
                                                                  --var6;
                                                               } else if (var6 < var7) {
                                                                  var45.swapItems(var6 + 1, var6);
                                                                  ++var6;
                                                               }
                                                            }
                                                         } else {
                                                            var45.swapItems(dragItemSlotDestination, dragItemSlotSource);
                                                         }

                                                         var23 = class21.getPacketBufferNode(ClientPacket.field2658, packetWriter.isaacCipher);
                                                         var23.packetBuffer.method6612(Script.dragInventoryWidget.id);
                                                         var23.packetBuffer.method6594(var25);
                                                         var23.packetBuffer.method6602(dragItemSlotSource);
                                                         var23.packetBuffer.writeShort(dragItemSlotDestination);
                                                         packetWriter.addNode(var23);
                                                      }
                                                   } else if (this.shouldLeftClickOpenMenu()) {
                                                      this.openMenu(draggedWidgetX, draggedWidgetY);
                                                   } else if (menuOptionsCount > 0) {
                                                      var4 = draggedWidgetX;
                                                      var5 = draggedWidgetY;
                                                      MenuAction var24 = ReflectionCheck.tempMenuAction;
                                                      Script.menuAction(var24.param0, var24.param1, var24.opcode, var24.identifier, var24.action, var24.action, var4, var5);
                                                      ReflectionCheck.tempMenuAction = null;
                                                   }

                                                   field747 = 10;
                                                   MouseHandler.MouseHandler_lastButton = 0;
                                                   Script.dragInventoryWidget = null;
                                                } else if (itemDragDuration >= 5 && (MouseHandler.MouseHandler_x > draggedWidgetX + 5 || MouseHandler.MouseHandler_x < draggedWidgetX - 5 || MouseHandler.MouseHandler_y > draggedWidgetY + 5 || MouseHandler.MouseHandler_y < draggedWidgetY - 5)) {
                                                   field848 = true;
                                                }
                                             }

                                             if (Scene.shouldSendWalk()) {
                                                var4 = Scene.Scene_selectedX;
                                                var5 = Scene.Scene_selectedY;
                                                var23 = class21.getPacketBufferNode(ClientPacket.field2642, packetWriter.isaacCipher);
                                                var23.packetBuffer.writeByte(5);
                                                var23.packetBuffer.method6584(KeyHandler.KeyHandler_pressedKeys[82] ? (KeyHandler.KeyHandler_pressedKeys[81] ? 2 : 1) : 0);
                                                var23.packetBuffer.writeShort(var5 + SoundSystem.baseY);
                                                var23.packetBuffer.writeShort(var4 + VertexNormal.baseX);
                                                packetWriter.addNode(var23);
                                                Scene.method4047();
                                                mouseCrossX = MouseHandler.MouseHandler_lastPressedX;
                                                mouseCrossY = MouseHandler.MouseHandler_lastPressedY;
                                                mouseCrossColor = 1;
                                                mouseCrossState = 0;
                                                destinationX = var4;
                                                destinationY = var5;
                                             }

                                             if (var42 != World.mousedOverWidgetIf1) {
                                                if (var42 != null) {
                                                   WorldMapCacheName.invalidateWidget(var42);
                                                }

                                                if (World.mousedOverWidgetIf1 != null) {
                                                   WorldMapCacheName.invalidateWidget(World.mousedOverWidgetIf1);
                                                }
                                             }

                                             if (var35 != ReflectionCheck.field609 && field833 == field792) {
                                                if (var35 != null) {
                                                   WorldMapCacheName.invalidateWidget(var35);
                                                }

                                                if (ReflectionCheck.field609 != null) {
                                                   WorldMapCacheName.invalidateWidget(ReflectionCheck.field609);
                                                }
                                             }

                                             if (ReflectionCheck.field609 != null) {
                                                if (field792 < field833) {
                                                   ++field792;
                                                   if (field833 == field792) {
                                                      WorldMapCacheName.invalidateWidget(ReflectionCheck.field609);
                                                   }
                                                }
                                             } else if (field792 > 0) {
                                                --field792;
                                             }

                                             FloorOverlayDefinition.method3104();
                                             if (isCameraLocked) {
                                                var4 = VarpDefinition.field1544 * 128 + 64;
                                                var5 = Ignored.field3847 * 128 + 64;
                                                var6 = class105.getTileHeight(var4, var5, class22.Client_plane) - AbstractByteArrayCopier.field3119;
                                                if (MouseHandler.cameraX < var4) {
                                                   MouseHandler.cameraX = (var4 - MouseHandler.cameraX) * class18.field156 / 1000 + MouseHandler.cameraX + class35.field264;
                                                   if (MouseHandler.cameraX > var4) {
                                                      MouseHandler.cameraX = var4;
                                                   }
                                                }

                                                if (MouseHandler.cameraX > var4) {
                                                   MouseHandler.cameraX -= class18.field156 * (MouseHandler.cameraX - var4) / 1000 + class35.field264;
                                                   if (MouseHandler.cameraX < var4) {
                                                      MouseHandler.cameraX = var4;
                                                   }
                                                }

                                                if (SecureRandomCallable.cameraY < var6) {
                                                   SecureRandomCallable.cameraY = (var6 - SecureRandomCallable.cameraY) * class18.field156 / 1000 + SecureRandomCallable.cameraY + class35.field264;
                                                   if (SecureRandomCallable.cameraY > var6) {
                                                      SecureRandomCallable.cameraY = var6;
                                                   }
                                                }

                                                if (SecureRandomCallable.cameraY > var6) {
                                                   SecureRandomCallable.cameraY -= class18.field156 * (SecureRandomCallable.cameraY - var6) / 1000 + class35.field264;
                                                   if (SecureRandomCallable.cameraY < var6) {
                                                      SecureRandomCallable.cameraY = var6;
                                                   }
                                                }

                                                if (class105.cameraZ < var5) {
                                                   class105.cameraZ = (var5 - class105.cameraZ) * class18.field156 / 1000 + class105.cameraZ + class35.field264;
                                                   if (class105.cameraZ > var5) {
                                                      class105.cameraZ = var5;
                                                   }
                                                }

                                                if (class105.cameraZ > var5) {
                                                   class105.cameraZ -= class18.field156 * (class105.cameraZ - var5) / 1000 + class35.field264;
                                                   if (class105.cameraZ < var5) {
                                                      class105.cameraZ = var5;
                                                   }
                                                }

                                                var4 = ArchiveDisk.field3886 * 128 + 64;
                                                var5 = class4.field47 * 128 + 64;
                                                var6 = class105.getTileHeight(var4, var5, class22.Client_plane) - class18.field153;
                                                var7 = var4 - MouseHandler.cameraX;
                                                var8 = var6 - SecureRandomCallable.cameraY;
                                                var9 = var5 - class105.cameraZ;
                                                var10 = (int)Math.sqrt((double)(var7 * var7 + var9 * var9));
                                                var11 = (int)(Math.atan2((double)var8, (double)var10) * 325.949D) & 2047;
                                                var12 = (int)(Math.atan2((double)var7, (double)var9) * -325.949D) & 2047;
                                                if (var11 < 128) {
                                                   var11 = 128;
                                                }

                                                if (var11 > 383) {
                                                   var11 = 383;
                                                }

                                                if (SpotAnimationDefinition.cameraPitch < var11) {
                                                   SpotAnimationDefinition.cameraPitch = (var11 - SpotAnimationDefinition.cameraPitch) * SoundCache.field468 / 1000 + SpotAnimationDefinition.cameraPitch + FriendsList.field3812;
                                                   if (SpotAnimationDefinition.cameraPitch > var11) {
                                                      SpotAnimationDefinition.cameraPitch = var11;
                                                   }
                                                }

                                                if (SpotAnimationDefinition.cameraPitch > var11) {
                                                   SpotAnimationDefinition.cameraPitch -= SoundCache.field468 * (SpotAnimationDefinition.cameraPitch - var11) / 1000 + FriendsList.field3812;
                                                   if (SpotAnimationDefinition.cameraPitch < var11) {
                                                      SpotAnimationDefinition.cameraPitch = var11;
                                                   }
                                                }

                                                var13 = var12 - class376.cameraYaw;
                                                if (var13 > 1024) {
                                                   var13 -= 2048;
                                                }

                                                if (var13 < -1024) {
                                                   var13 += 2048;
                                                }

                                                if (var13 > 0) {
                                                   class376.cameraYaw = var13 * SoundCache.field468 / 1000 + class376.cameraYaw + FriendsList.field3812;
                                                   class376.cameraYaw &= 2047;
                                                }

                                                if (var13 < 0) {
                                                   class376.cameraYaw -= -var13 * SoundCache.field468 / 1000 + FriendsList.field3812;
                                                   class376.cameraYaw &= 2047;
                                                }

                                                int var46 = var12 - class376.cameraYaw;
                                                if (var46 > 1024) {
                                                   var46 -= 2048;
                                                }

                                                if (var46 < -1024) {
                                                   var46 += 2048;
                                                }

                                                if (var46 < 0 && var13 > 0 || var46 > 0 && var13 < 0) {
                                                   class376.cameraYaw = var12;
                                                }
                                             }

                                             for(var4 = 0; var4 < 5; ++var4) {
                                                ++field893[var4];
                                             }

                                             GrandExchangeOfferOwnWorldComparator.varcs.tryWrite();
                                             var4 = ++MouseHandler.MouseHandler_idleCycles - 1;
                                             var6 = WorldMapLabelSize.method3206();
                                             if (var4 > 15000 && var6 > 15000) {
                                                logoutTimer = 250;
                                                ArchiveDiskActionHandler.method4867(14500);
                                                var28 = class21.getPacketBufferNode(ClientPacket.field2644, packetWriter.isaacCipher);
                                                packetWriter.addNode(var28);
                                             }

                                             NetSocket.friendSystem.processFriendUpdates();
                                             ++packetWriter.pendingWrites;
                                             if (packetWriter.pendingWrites > 50) {
                                                var28 = class21.getPacketBufferNode(ClientPacket.field2590, packetWriter.isaacCipher);
                                                packetWriter.addNode(var28);
                                             }

                                             try {
                                                packetWriter.flush();
                                             } catch (IOException var26) {
                                                NetFileRequest.method4912();
                                             }

                                             return;
                                          }

                                          var44 = var43.widget;
                                          if (var44.childIndex < 0) {
                                             break;
                                          }

                                          var41 = Frames.getWidget(var44.parentId);
                                       } while(var41 == null || var41.children == null || var44.childIndex >= var41.children.length || var44 != var41.children[var44.childIndex]);

                                       PacketWriter.runScriptEvent(var43);
                                    }
                                 }

                                 var44 = var43.widget;
                                 if (var44.childIndex < 0) {
                                    break;
                                 }

                                 var41 = Frames.getWidget(var44.parentId);
                              } while(var41 == null || var41.children == null || var44.childIndex >= var41.children.length || var44 != var41.children[var44.childIndex]);

                              PacketWriter.runScriptEvent(var43);
                           }
                        }

                        var44 = var43.widget;
                        if (var44.childIndex < 0) {
                           break;
                        }

                        var41 = Frames.getWidget(var44.parentId);
                     } while(var41 == null || var41.children == null || var44.childIndex >= var41.children.length || var44 != var41.children[var44.childIndex]);

                     PacketWriter.runScriptEvent(var43);
                  }
               }

               NetFileRequest.method4912();
            }
         }
      }

   }

   @ObfuscatedName("gv")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "65280"
   )
   @Export("resizeJS")
   void resizeJS() {
      int var1 = class32.canvasWidth;
      int var2 = ReflectionCheck.canvasHeight;
      if (super.contentWidth < var1) {
         var1 = super.contentWidth;
      }

      if (super.contentHeight < var2) {
         var2 = super.contentHeight;
      }

      if (ObjectComposition.clientPreferences != null) {
         try {
            class42.method422(class23.client, "resize", new Object[]{SpotAnimationDefinition.getWindowedMode()});
         } catch (Throwable var4) {
            ;
         }
      }

   }

   @ObfuscatedName("gm")
   @ObfuscatedSignature(
      descriptor = "(I)V",
      garbageValue = "-1520673658"
   )
   @Export("drawLoggedIn")
   final void drawLoggedIn() {
      int var1;
      if (rootInterface != -1) {
         var1 = rootInterface;
         if (Clock.loadInterface(var1)) {
            Canvas.drawModelComponents(Widget.Widget_interfaceComponents[var1], -1);
         }
      }

      for(var1 = 0; var1 < rootWidgetCount; ++var1) {
         if (field719[var1]) {
            field780[var1] = true;
         }

         field860[var1] = field719[var1];
         field719[var1] = false;
      }

      field849 = cycle;
      viewportX = -1;
      viewportY = -1;
      class18.hoveredItemContainer = null;
      if (rootInterface != -1) {
         rootWidgetCount = 0;
         class225.drawWidgets(rootInterface, 0, 0, class32.canvasWidth, ReflectionCheck.canvasHeight, 0, 0, -1);
      }

      Rasterizer2D.Rasterizer2D_resetClip();
      if (showMouseCross) {
         if (mouseCrossColor == 1) {
            ArchiveLoader.crossSprites[mouseCrossState / 100].drawTransBgAt(mouseCrossX - 8, mouseCrossY - 8);
         }

         if (mouseCrossColor == 2) {
            ArchiveLoader.crossSprites[mouseCrossState / 100 + 4].drawTransBgAt(mouseCrossX - 8, mouseCrossY - 8);
         }
      }

      if (!isMenuOpen) {
         if (viewportX != -1) {
            FileSystem.method2547(viewportX, viewportY);
         }
      } else {
         var1 = class14.menuX;
         int var2 = class243.menuY;
         int var3 = class29.menuWidth;
         int var4 = class24.menuHeight;
         int var5 = 6116423;
         Rasterizer2D.Rasterizer2D_fillRectangle(var1, var2, var3, var4, var5);
         Rasterizer2D.Rasterizer2D_fillRectangle(var1 + 1, var2 + 1, var3 - 2, 16, 0);
         Rasterizer2D.Rasterizer2D_drawRectangle(var1 + 1, var2 + 18, var3 - 2, var4 - 19, 0);
         Widget.fontBold12.draw("Choose Option", var1 + 3, var2 + 14, var5, -1);
         int var6 = MouseHandler.MouseHandler_x;
         int var7 = MouseHandler.MouseHandler_y;

         for(int var8 = 0; var8 < menuOptionsCount; ++var8) {
            int var9 = var2 + (menuOptionsCount - 1 - var8) * 15 + 31;
            int var10 = 16777215;
            if (var6 > var1 && var6 < var1 + var3 && var7 > var9 - 13 && var7 < var9 + 3) {
               var10 = 16776960;
            }

            Widget.fontBold12.draw(MouseRecorder.method2098(var8), var1 + 3, var9, var10, 0);
         }

         PcmPlayer.method786(class14.menuX, class243.menuY, class29.menuWidth, class24.menuHeight);
      }

      if (gameDrawingMode == 3) {
         for(var1 = 0; var1 < rootWidgetCount; ++var1) {
            if (field860[var1]) {
               Rasterizer2D.Rasterizer2D_fillRectangleAlpha(rootWidgetXs[var1], rootWidgetYs[var1], rootWidgetWidths[var1], rootWidgetHeights[var1], 16711935, 128);
            } else if (field780[var1]) {
               Rasterizer2D.Rasterizer2D_fillRectangleAlpha(rootWidgetXs[var1], rootWidgetYs[var1], rootWidgetWidths[var1], rootWidgetHeights[var1], 16711680, 128);
            }
         }
      }

      class169.method3549(class22.Client_plane, class93.localPlayer.x, class93.localPlayer.y, field913);
      field913 = 0;
   }

   @ObfuscatedName("hh")
   @ObfuscatedSignature(
      descriptor = "(Lds;I)Z",
      garbageValue = "-2018950508"
   )
   final boolean method1192(PacketWriter var1) {
      AbstractSocket var2 = var1.getSocket();
      PacketBuffer var3 = var1.packetBuffer;
      if (var2 == null) {
         return false;
      } else {
         int var4;
         String var5;
         try {
            int var6;
            if (var1.serverPacket == null) {
               if (var1.field1408) {
                  if (!var2.isAvailable(1)) {
                     return false;
                  }

                  var2.read(var1.packetBuffer.array, 0, 1);
                  var1.field1409 = 0;
                  var1.field1408 = false;
               }

               var3.offset = 0;
               if (var3.method6529()) {
                  if (!var2.isAvailable(1)) {
                     return false;
                  }

                  var2.read(var1.packetBuffer.array, 1, 1);
                  var1.field1409 = 0;
               }

               var1.field1408 = true;
               ServerPacket[] var7 = class24.ServerPacket_values();
               var6 = var3.readSmartByteShortIsaac();
               if (var6 < 0 || var6 >= var7.length) {
                  throw new IOException(var6 + " " + var3.offset);
               }

               var1.serverPacket = var7[var6];
               var1.serverPacketLength = var1.serverPacket.length;
            }

            if (var1.serverPacketLength == -1) {
               if (!var2.isAvailable(1)) {
                  return false;
               }

               var1.getSocket().read(var3.array, 0, 1);
               var1.serverPacketLength = var3.array[0] & 255;
            }

            if (var1.serverPacketLength == -2) {
               if (!var2.isAvailable(2)) {
                  return false;
               }

               var1.getSocket().read(var3.array, 0, 2);
               var3.offset = 0;
               var1.serverPacketLength = var3.readUnsignedShort();
            }

            if (!var2.isAvailable(var1.serverPacketLength)) {
               return false;
            }

            var3.offset = 0;
            var2.read(var3.array, 0, var1.serverPacketLength);
            var1.field1409 = 0;
            timer.method5602();
            var1.field1400 = var1.field1412;
            var1.field1412 = var1.field1411;
            var1.field1411 = var1.serverPacket;
            int var8;
            int var9;
            int var10;
            int var45;
            if (ServerPacket.field2732 == var1.serverPacket) {
               var10 = var3.readUnsignedShort();
               var6 = var3.readInt();
               var4 = var10 >> 10 & 31;
               var45 = var10 >> 5 & 31;
               var8 = var10 & 31;
               var9 = (var45 << 11) + (var4 << 19) + (var8 << 3);
               Widget var46 = Frames.getWidget(var6);
               if (var9 != var46.color) {
                  var46.color = var9;
                  WorldMapCacheName.invalidateWidget(var46);
               }

               var1.serverPacket = null;
               return true;
            }

            boolean var11;
            if (ServerPacket.field2734 == var1.serverPacket) {
               var11 = var3.readBoolean();
               if (var11) {
                  if (UserComparator3.field1444 == null) {
                     UserComparator3.field1444 = new class285();
                  }
               } else {
                  UserComparator3.field1444 = null;
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2757 == var1.serverPacket) {
               class69.field596 = var3.readUnsignedByte();
               Occluder.field2387 = var3.readUnsignedByte();

               while(var3.offset < var1.serverPacketLength) {
                  var10 = var3.readUnsignedByte();
                  class225 var47 = PlayerComposition.method4705()[var10];
                  Tile.method3843(var47);
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2750 == var1.serverPacket) {
               Tile.method3843(class225.field2687);
               var1.serverPacket = null;
               return true;
            }

            String var12;
            if (ServerPacket.field2781 == var1.serverPacket) {
               var12 = var3.readStringCp1252NullTerminated();
               Object[] var50 = new Object[var12.length() + 1];

               for(var4 = var12.length() - 1; var4 >= 0; --var4) {
                  if (var12.charAt(var4) == 's') {
                     var50[var4 + 1] = var3.readStringCp1252NullTerminated();
                  } else {
                     var50[var4 + 1] = new Integer(var3.readInt());
                  }
               }

               var50[0] = new Integer(var3.readInt());
               ScriptEvent var48 = new ScriptEvent();
               var48.args = var50;
               PacketWriter.runScriptEvent(var48);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2759 == var1.serverPacket) {
               hintArrowType = var3.readUnsignedByte();
               if (hintArrowType == 1) {
                  hintArrowNpcIndex = var3.readUnsignedShort();
               }

               if (hintArrowType >= 2 && hintArrowType <= 6) {
                  if (hintArrowType == 2) {
                     hintArrowSubX = 64;
                     hintArrowSubY = 64;
                  }

                  if (hintArrowType == 3) {
                     hintArrowSubX = 0;
                     hintArrowSubY = 64;
                  }

                  if (hintArrowType == 4) {
                     hintArrowSubX = 128;
                     hintArrowSubY = 64;
                  }

                  if (hintArrowType == 5) {
                     hintArrowSubX = 64;
                     hintArrowSubY = 0;
                  }

                  if (hintArrowType == 6) {
                     hintArrowSubX = 64;
                     hintArrowSubY = 128;
                  }

                  hintArrowType = 2;
                  hintArrowX = var3.readUnsignedShort();
                  hintArrowY = var3.readUnsignedShort();
                  hintArrowHeight = var3.readUnsignedByte();
               }

               if (hintArrowType == 10) {
                  hintArrowPlayerIndex = var3.readUnsignedShort();
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2711 == var1.serverPacket) {
               Tile.method3843(class225.field2683);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2730 == var1.serverPacket) {
               class34.field254 = new class339(ViewportMouse.HitSplatDefinition_cachedSprites);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2777 == var1.serverPacket) {
               var10 = var3.readUnsignedByte();
               class15.forceDisconnect(var10);
               var1.serverPacket = null;
               return false;
            }

            if (ServerPacket.field2752 == var1.serverPacket) {
               var10 = var3.readUnsignedByte();
               if (var3.readUnsignedByte() == 0) {
                  grandExchangeOffers[var10] = new GrandExchangeOffer();
                  var3.offset += 18;
               } else {
                  --var3.offset;
                  grandExchangeOffers[var10] = new GrandExchangeOffer(var3, false);
               }

               field840 = cycleCntr;
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2741 == var1.serverPacket) {
               ScriptEvent.privateChatMode = class124.method2498(var3.readUnsignedByte());
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2746 == var1.serverPacket) {
               Tile.method3843(class225.field2681);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2704 == var1.serverPacket) {
               var10 = var3.readUnsignedShort();
               var6 = var3.readUnsignedByte();
               var4 = var3.readUnsignedShort();
               BufferedSource.queueSoundEffect(var10, var6, var4);
               var1.serverPacket = null;
               return true;
            }

            Widget var13;
            if (ServerPacket.field2717 == var1.serverPacket) {
               var10 = var3.readInt();
               var6 = var3.readUnsignedShort();
               var13 = Frames.getWidget(var10);
               if (var13.modelType != 2 || var6 != var13.modelId) {
                  var13.modelType = 2;
                  var13.modelId = var6;
                  WorldMapCacheName.invalidateWidget(var13);
               }

               var1.serverPacket = null;
               return true;
            }

            int var14;
            if (ServerPacket.field2788 == var1.serverPacket) {
               isCameraLocked = true;
               ArchiveDisk.field3886 = var3.readUnsignedByte();
               class4.field47 = var3.readUnsignedByte();
               class18.field153 = var3.readUnsignedShort();
               FriendsList.field3812 = var3.readUnsignedByte();
               SoundCache.field468 = var3.readUnsignedByte();
               if (SoundCache.field468 >= 100) {
                  var10 = ArchiveDisk.field3886 * 128 + 64;
                  var6 = class4.field47 * 128 + 64;
                  var4 = class105.getTileHeight(var10, var6, class22.Client_plane) - class18.field153;
                  var45 = var10 - MouseHandler.cameraX;
                  var8 = var4 - SecureRandomCallable.cameraY;
                  var9 = var6 - class105.cameraZ;
                  var14 = (int)Math.sqrt((double)(var45 * var45 + var9 * var9));
                  SpotAnimationDefinition.cameraPitch = (int)(Math.atan2((double)var8, (double)var14) * 325.949D) & 2047;
                  class376.cameraYaw = (int)(Math.atan2((double)var45, (double)var9) * -325.949D) & 2047;
                  if (SpotAnimationDefinition.cameraPitch < 128) {
                     SpotAnimationDefinition.cameraPitch = 128;
                  }

                  if (SpotAnimationDefinition.cameraPitch > 383) {
                     SpotAnimationDefinition.cameraPitch = 383;
                  }
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2776 == var1.serverPacket) {
               var10 = var3.method6613();
               var6 = var3.method6726();
               var13 = Frames.getWidget(var10);
               if (var6 != var13.sequenceId || var6 == -1) {
                  var13.sequenceId = var6;
                  var13.modelFrame = 0;
                  var13.modelFrameCycle = 0;
                  WorldMapCacheName.invalidateWidget(var13);
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2721 == var1.serverPacket) {
               Tile.method3843(class225.field2689);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2709 == var1.serverPacket) {
               Tile.method3843(class225.field2686);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2714 == var1.serverPacket) {
               var10 = var3.readUnsignedByte();
               ClanSettings.method163(var10);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2738 == var1.serverPacket) {
               if (class34.field254 == null) {
                  class34.field254 = new class339(ViewportMouse.HitSplatDefinition_cachedSprites);
               }

               class390 var49 = ViewportMouse.HitSplatDefinition_cachedSprites.method6021(var3);
               class34.field254.field3921.vmethod6364(var49.field4219, var49.field4218);
               field911[++field852 - 1 & 31] = var49.field4219;
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2735 == var1.serverPacket) {
               NetSocket.friendSystem.readUpdate(var3, var1.serverPacketLength);
               field836 = cycleCntr;
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2701 == var1.serverPacket) {
               isCameraLocked = true;
               VarpDefinition.field1544 = var3.readUnsignedByte();
               Ignored.field3847 = var3.readUnsignedByte();
               AbstractByteArrayCopier.field3119 = var3.readUnsignedShort();
               class35.field264 = var3.readUnsignedByte();
               class18.field156 = var3.readUnsignedByte();
               if (class18.field156 >= 100) {
                  MouseHandler.cameraX = VarpDefinition.field1544 * 128 + 64;
                  class105.cameraZ = Ignored.field3847 * 128 + 64;
                  SecureRandomCallable.cameraY = class105.getTileHeight(MouseHandler.cameraX, class105.cameraZ, class22.Client_plane) - AbstractByteArrayCopier.field3119;
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2767 == var1.serverPacket) {
               SoundSystem.updateNpcs(true, var3);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2772 == var1.serverPacket) {
               destinationX = var3.readUnsignedByte();
               if (destinationX == 255) {
                  destinationX = 0;
               }

               destinationY = var3.readUnsignedByte();
               if (destinationY == 255) {
                  destinationY = 0;
               }

               var1.serverPacket = null;
               return true;
            }

            InterfaceParent var15;
            Widget var16;
            InterfaceParent var51;
            if (ServerPacket.field2743 == var1.serverPacket) {
               var10 = var3.method6614();
               var6 = var3.method6614();
               var51 = (InterfaceParent)interfaceParents.get((long)var6);
               var15 = (InterfaceParent)interfaceParents.get((long)var10);
               if (var15 != null) {
                  class43.closeInterface(var15, var51 == null || var15.group != var51.group);
               }

               if (var51 != null) {
                  var51.remove();
                  interfaceParents.put(var51, (long)var10);
               }

               var16 = Frames.getWidget(var6);
               if (var16 != null) {
                  WorldMapCacheName.invalidateWidget(var16);
               }

               var16 = Frames.getWidget(var10);
               if (var16 != null) {
                  WorldMapCacheName.invalidateWidget(var16);
                  class313.revalidateWidgetScroll(Widget.Widget_interfaceComponents[var16.id >>> 16], var16, true);
               }

               if (rootInterface != -1) {
                  Login.runIntfCloseListeners(rootInterface, 1);
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2729 == var1.serverPacket) {
               FriendLoginUpdate.method5690(var3.readStringCp1252NullTerminated());
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2736 == var1.serverPacket) {
               var10 = var3.readInt();
               var51 = (InterfaceParent)interfaceParents.get((long)var10);
               if (var51 != null) {
                  class43.closeInterface(var51, true);
               }

               if (meslayerContinueWidget != null) {
                  WorldMapCacheName.invalidateWidget(meslayerContinueWidget);
                  meslayerContinueWidget = null;
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2722 == var1.serverPacket) {
               if (rootInterface != -1) {
                  Login.runIntfCloseListeners(rootInterface, 0);
               }

               var1.serverPacket = null;
               return true;
            }

            long var17;
            if (ServerPacket.field2775 == var1.serverPacket) {
               var10 = var3.offset + var1.serverPacketLength;
               var6 = var3.readUnsignedShort();
               var4 = var3.readUnsignedShort();
               if (var6 != rootInterface) {
                  rootInterface = var6;
                  this.resizeRoot(false);
                  class21.Widget_resetModelFrames(rootInterface);
                  FloorDecoration.runWidgetOnLoadListener(rootInterface);

                  for(var45 = 0; var45 < 100; ++var45) {
                     field719[var45] = true;
                  }
               }

               InterfaceParent var52;
               for(; var4-- > 0; var52.field1165 = true) {
                  var45 = var3.readInt();
                  var8 = var3.readUnsignedShort();
                  var9 = var3.readUnsignedByte();
                  var52 = (InterfaceParent)interfaceParents.get((long)var45);
                  if (var52 != null && var8 != var52.group) {
                     class43.closeInterface(var52, true);
                     var52 = null;
                  }

                  if (var52 == null) {
                     var52 = class17.method208(var45, var8, var9);
                  }
               }

               for(var15 = (InterfaceParent)interfaceParents.first(); var15 != null; var15 = (InterfaceParent)interfaceParents.next()) {
                  if (var15.field1165) {
                     var15.field1165 = false;
                  } else {
                     class43.closeInterface(var15, true);
                  }
               }

               widgetFlags = new NodeHashTable(512);

               while(var3.offset < var10) {
                  var45 = var3.readInt();
                  var8 = var3.readUnsignedShort();
                  var9 = var3.readUnsignedShort();
                  var14 = var3.readInt();

                  for(int var53 = var8; var53 <= var9; ++var53) {
                     var17 = ((long)var45 << 32) + (long)var53;
                     widgetFlags.put(new IntegerNode(var14), var17);
                  }
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2705 == var1.serverPacket) {
               var11 = var3.method6549() == 1;
               var6 = var3.method6598();
               var13 = Frames.getWidget(var6);
               if (var11 != var13.isHidden) {
                  var13.isHidden = var11;
                  WorldMapCacheName.invalidateWidget(var13);
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2751 == var1.serverPacket) {
               class34.field254 = null;
               var1.serverPacket = null;
               return true;
            }

            int var19;
            long var20;
            long var22;
            long var24;
            String var26;
            if (ServerPacket.field2785 == var1.serverPacket) {
               var12 = var3.readStringCp1252NullTerminated();
               var20 = var3.readLong();
               var22 = (long)var3.readUnsignedShort();
               var24 = (long)var3.readMedium();
               PlayerType var55 = (PlayerType)Messages.findEnumerated(class372.PlayerType_values(), var3.readUnsignedByte());
               var17 = var24 + (var22 << 32);
               boolean var58 = false;

               for(var19 = 0; var19 < 100; ++var19) {
                  if (var17 == crossWorldMessageIds[var19]) {
                     var58 = true;
                     break;
                  }
               }

               if (var55.isUser && NetSocket.friendSystem.isIgnored(new Username(var12, WorldMapSection0.loginType))) {
                  var58 = true;
               }

               if (!var58 && field755 == 0) {
                  crossWorldMessageIds[crossWorldMessageIdsIndex] = var17;
                  crossWorldMessageIdsIndex = (crossWorldMessageIdsIndex + 1) % 100;
                  var26 = AbstractFont.escapeBrackets(class43.method433(MusicPatch.method4592(var3)));
                  if (var55.modIcon != -1) {
                     class5.addChatMessage(9, SecureRandomFuture.method1982(var55.modIcon) + var12, var26, class258.base37DecodeLong(var20));
                  } else {
                     class5.addChatMessage(9, var12, var26, class258.base37DecodeLong(var20));
                  }
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2770 == var1.serverPacket) {
               Tile.method3843(class225.field2685);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2779 == var1.serverPacket) {
               var12 = var3.readStringCp1252NullTerminated();
               var6 = var3.method6614();
               var13 = Frames.getWidget(var6);
               if (!var12.equals(var13.text)) {
                  var13.text = var12;
                  WorldMapCacheName.invalidateWidget(var13);
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2787 == var1.serverPacket) {
               var10 = var3.method6603();
               var6 = var3.method6598();
               var13 = Frames.getWidget(var6);
               if (var13.modelType != 1 || var10 != var13.modelId) {
                  var13.modelType = 1;
                  var13.modelId = var10;
                  WorldMapCacheName.invalidateWidget(var13);
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2706 == var1.serverPacket) {
               var10 = var3.readInt();
               if (var10 != field723) {
                  field723 = var10;
                  Script.method1999();
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2778 == var1.serverPacket) {
               class7.logOut();
               var1.serverPacket = null;
               return false;
            }

            if (ServerPacket.field2737 == var1.serverPacket) {
               World var54 = new World();
               var54.host = var3.readStringCp1252NullTerminated();
               var54.id = var3.readUnsignedShort();
               var6 = var3.readInt();
               var54.properties = var6;
               class12.updateGameState(45);
               var2.close();
               var2 = null;
               class0.changeWorld(var54);
               var1.serverPacket = null;
               return false;
            }

            if (ServerPacket.field2728 == var1.serverPacket) {
               rebootTimer = var3.readUnsignedShort() * 30;
               field659 = cycleCntr;
               var1.serverPacket = null;
               return true;
            }

            String var27;
            if (ServerPacket.field2765 == var1.serverPacket) {
               byte[] var56 = new byte[var1.serverPacketLength];
               var3.method6521(var56, 0, var56.length);
               Buffer var60 = new Buffer(var56);
               var27 = var60.readStringCp1252NullTerminated();
               Players.openURL(var27, true, false);
               var1.serverPacket = null;
               return true;
            }

            byte var28;
            if (ServerPacket.field2739 == var1.serverPacket) {
               class124.method2496();
               var28 = var3.readByte();
               if (var1.serverPacketLength == 1) {
                  if (var28 >= 0) {
                     currentClanSettings[var28] = null;
                  } else {
                     NPCComposition.guestClanSettings = null;
                  }

                  var1.serverPacket = null;
                  return true;
               }

               if (var28 >= 0) {
                  currentClanSettings[var28] = new ClanSettings(var3);
               } else {
                  NPCComposition.guestClanSettings = new ClanSettings(var3);
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2724 == var1.serverPacket) {
               var10 = var3.method6598();
               var6 = var3.method6603();
               var13 = Frames.getWidget(var10);
               if (var13 != null && var13.type == 0) {
                  if (var6 > var13.scrollHeight - var13.height) {
                     var6 = var13.scrollHeight - var13.height;
                  }

                  if (var6 < 0) {
                     var6 = 0;
                  }

                  if (var6 != var13.scrollY) {
                     var13.scrollY = var6;
                     WorldMapCacheName.invalidateWidget(var13);
                  }
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2790 == var1.serverPacket) {
               for(var10 = 0; var10 < Varps.Varps_main.length; ++var10) {
                  if (Varps.Varps_main[var10] != Varps.Varps_temp[var10]) {
                     Varps.Varps_main[var10] = Varps.Varps_temp[var10];
                     ApproximateRouteStrategy.changeGameOptions(var10);
                     changedVarps[++changedVarpCount - 1 & 31] = var10;
                  }
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2710 == var1.serverPacket) {
               PlayerComposition.readReflectionCheck(var3, var1.serverPacketLength);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2792 == var1.serverPacket) {
               var10 = var3.readInt();
               var6 = var3.readUnsignedShort();
               if (var10 < -70000) {
                  var6 += 32768;
               }

               if (var10 >= 0) {
                  var13 = Frames.getWidget(var10);
               } else {
                  var13 = null;
               }

               for(; var3.offset < var1.serverPacketLength; class4.itemContainerSetItem(var6, var45, var8 - 1, var9)) {
                  var45 = var3.readUShortSmart();
                  var8 = var3.readUnsignedShort();
                  var9 = 0;
                  if (var8 != 0) {
                     var9 = var3.readUnsignedByte();
                     if (var9 == 255) {
                        var9 = var3.readInt();
                     }
                  }

                  if (var13 != null && var45 >= 0 && var45 < var13.itemIds.length) {
                     var13.itemIds[var45] = var8;
                     var13.itemQuantities[var45] = var9;
                  }
               }

               if (var13 != null) {
                  WorldMapCacheName.invalidateWidget(var13);
               }

               class170.method3554();
               changedItemContainers[++field830 - 1 & 31] = var6 & 32767;
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2780 == var1.serverPacket) {
               class339.loadRegions(false, var1.packetBuffer);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2726 == var1.serverPacket) {
               FriendSystem.updatePlayers(var3, var1.serverPacketLength);
               ObjectSound.method1761();
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2769 == var1.serverPacket) {
               Tile.method3843(class225.field2680);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2760 == var1.serverPacket) {
               class170.method3554();
               runEnergy = var3.readUnsignedByte();
               field659 = cycleCntr;
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2718 == var1.serverPacket) {
               var10 = var3.readUnsignedShort();
               if (var10 == 65535) {
                  var10 = -1;
               }

               var6 = var3.method6613();
               var4 = var3.method6605();
               if (var4 == 65535) {
                  var4 = -1;
               }

               var45 = var3.method6598();

               for(var8 = var10; var8 <= var4; ++var8) {
                  var24 = ((long)var45 << 32) + (long)var8;
                  Node var59 = widgetFlags.get(var24);
                  if (var59 != null) {
                     var59.remove();
                  }

                  widgetFlags.put(new IntegerNode(var6), var24);
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2764 == var1.serverPacket) {
               var10 = var3.readInt();
               var6 = var3.readInt();
               var4 = class43.getGcDuration();
               PacketBufferNode var57 = class21.getPacketBufferNode(ClientPacket.field2634, packetWriter.isaacCipher);
               var57.packetBuffer.method6611(var10);
               var57.packetBuffer.writeInt(var6);
               var57.packetBuffer.method6581(GameEngine.fps);
               var57.packetBuffer.method6584(var4);
               packetWriter.addNode(var57);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2786 == var1.serverPacket) {
               class339.loadRegions(true, var1.packetBuffer);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2712 == var1.serverPacket) {
               Tile.method3843(class225.field2682);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2727 == var1.serverPacket) {
               var11 = var3.readUnsignedByte() == 1;
               if (var11) {
                  Widget.field3099 = ObjectComposition.currentTimeMillis() - var3.readLong();
                  BuddyRankComparator.grandExchangeEvents = new GrandExchangeEvents(var3, true);
               } else {
                  BuddyRankComparator.grandExchangeEvents = null;
               }

               field748 = cycleCntr;
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2789 == var1.serverPacket) {
               isCameraLocked = false;

               for(var10 = 0; var10 < 5; ++var10) {
                  field693[var10] = false;
               }

               var1.serverPacket = null;
               return true;
            }

            Widget var29;
            if (ServerPacket.field2753 == var1.serverPacket) {
               var10 = var3.method6598();
               var6 = var3.method6614();
               var4 = var3.method6604();
               if (var4 == 65535) {
                  var4 = -1;
               }

               var29 = Frames.getWidget(var6);
               ItemComposition var61;
               if (!var29.isIf3) {
                  if (var4 == -1) {
                     var29.modelType = 0;
                     var1.serverPacket = null;
                     return true;
                  }

                  var61 = class260.ItemDefinition_get(var4);
                  var29.modelType = 4;
                  var29.modelId = var4;
                  var29.modelAngleX = var61.xan2d;
                  var29.modelAngleY = var61.yan2d;
                  var29.modelZoom = var61.zoom2d * 100 / var10;
                  WorldMapCacheName.invalidateWidget(var29);
               } else {
                  var29.itemId = var4;
                  var29.itemQuantity = var10;
                  var61 = class260.ItemDefinition_get(var4);
                  var29.modelAngleX = var61.xan2d;
                  var29.modelAngleY = var61.yan2d;
                  var29.modelAngleZ = var61.zan2d;
                  var29.modelOffsetX = var61.offsetX2d;
                  var29.modelOffsetY = var61.offsetY2d;
                  var29.modelZoom = var61.zoom2d;
                  if (var61.isStackable == 1) {
                     var29.itemQuantityMode = 1;
                  } else {
                     var29.itemQuantityMode = 2;
                  }

                  if (var29.field3013 > 0) {
                     var29.modelZoom = var29.modelZoom * 32 / var29.field3013;
                  } else if (var29.rawWidth > 0) {
                     var29.modelZoom = var29.modelZoom * 32 / var29.rawWidth;
                  }

                  WorldMapCacheName.invalidateWidget(var29);
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2794 == var1.serverPacket) {
               minimapState = var3.readUnsignedByte();
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2745 == var1.serverPacket) {
               var12 = var3.readStringCp1252NullTerminated();
               var5 = AbstractFont.escapeBrackets(class43.method433(MusicPatch.method4592(var3)));
               World.addGameMessage(6, var12, var5);
               var1.serverPacket = null;
               return true;
            }

            String var30;
            int var31;
            if (ServerPacket.field2719 == var1.serverPacket) {
               var28 = var3.readByte();
               var20 = (long)var3.readUnsignedShort();
               var22 = (long)var3.readMedium();
               var24 = var22 + (var20 << 32);
               boolean var62 = false;
               ClanChannel var72 = var28 >= 0 ? currentClanChannels[var28] : ApproximateRouteStrategy.guestClanChannel;
               if (var72 == null) {
                  var62 = true;
               } else {
                  for(var31 = 0; var31 < 100; ++var31) {
                     if (var24 == crossWorldMessageIds[var31]) {
                        var62 = true;
                        break;
                     }
                  }
               }

               if (!var62) {
                  crossWorldMessageIds[crossWorldMessageIdsIndex] = var24;
                  crossWorldMessageIdsIndex = (crossWorldMessageIdsIndex + 1) % 100;
                  var30 = MusicPatch.method4592(var3);
                  int var75 = var28 >= 0 ? 43 : 46;
                  class5.addChatMessage(var75, "", var30, var72.name);
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2708 == var1.serverPacket) {
               Occluder.field2387 = var3.method6595();
               class69.field596 = var3.method6549();
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2762 == var1.serverPacket) {
               var10 = var3.method6603();
               var6 = var3.method6605();
               var4 = var3.method6604();
               var45 = var3.method6598();
               var16 = Frames.getWidget(var45);
               if (var10 != var16.modelAngleX || var6 != var16.modelAngleY || var4 != var16.modelZoom) {
                  var16.modelAngleX = var10;
                  var16.modelAngleY = var6;
                  var16.modelZoom = var4;
                  WorldMapCacheName.invalidateWidget(var16);
               }

               var1.serverPacket = null;
               return true;
            }

            Widget var32;
            if (ServerPacket.field2761 == var1.serverPacket) {
               var10 = var3.method6614();
               var32 = Frames.getWidget(var10);
               var32.modelType = 3;
               var32.modelId = class93.localPlayer.appearance.getChatHeadId();
               WorldMapCacheName.invalidateWidget(var32);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2763 == var1.serverPacket) {
               var10 = var3.readUShortSmart();
               boolean var71 = var3.readUnsignedByte() == 1;
               var27 = "";
               boolean var74 = false;
               if (var71) {
                  var27 = var3.readStringCp1252NullTerminated();
                  if (NetSocket.friendSystem.isIgnored(new Username(var27, WorldMapSection0.loginType))) {
                     var74 = true;
                  }
               }

               String var70 = var3.readStringCp1252NullTerminated();
               if (!var74) {
                  World.addGameMessage(var10, var27, var70);
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2793 == var1.serverPacket) {
               class170.method3554();
               weight = var3.readShort();
               field659 = cycleCntr;
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2791 == var1.serverPacket) {
               publicChatMode = var3.readUnsignedByte();
               tradeChatMode = var3.method6549();
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2784 == var1.serverPacket) {
               var3.offset += 28;
               if (var3.checkCrc()) {
                  class245.method4714(var3, var3.offset - 28);
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2740 == var1.serverPacket) {
               var10 = var3.readUnsignedShort();
               if (var10 == 65535) {
                  var10 = -1;
               }

               class27.playSong(var10);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2725 == var1.serverPacket) {
               var10 = var3.method6604();
               if (var10 == 65535) {
                  var10 = -1;
               }

               var6 = var3.method6610();
               GrandExchangeOfferNameComparator.method5059(var10, var6);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2754 == var1.serverPacket) {
               var12 = var3.readStringCp1252NullTerminated();
               var6 = var3.method6549();
               var4 = var3.method6671();
               if (var6 >= 1 && var6 <= 8) {
                  if (var12.equalsIgnoreCase("null")) {
                     var12 = null;
                  }

                  playerMenuActions[var6 - 1] = var12;
                  playerOptionsPriorities[var6 - 1] = var4 == 0;
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2748 == var1.serverPacket) {
               var10 = var3.readUnsignedByte();
               var6 = var3.readUnsignedByte();
               var4 = var3.readUnsignedByte();
               var45 = var3.readUnsignedByte();
               field693[var10] = true;
               field890[var10] = var6;
               field891[var10] = var4;
               field892[var10] = var45;
               field893[var10] = 0;
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2783 == var1.serverPacket) {
               class69.field596 = var3.readUnsignedByte();
               Occluder.field2387 = var3.readUnsignedByte();

               for(var10 = Occluder.field2387; var10 < Occluder.field2387 + 8; ++var10) {
                  for(var6 = class69.field596; var6 < class69.field596 + 8; ++var6) {
                     if (groundItems[class22.Client_plane][var10][var6] != null) {
                        groundItems[class22.Client_plane][var10][var6] = null;
                        ClanSettings.updateItemPile(var10, var6);
                     }
                  }
               }

               for(PendingSpawn var68 = (PendingSpawn)pendingSpawns.last(); var68 != null; var68 = (PendingSpawn)pendingSpawns.previous()) {
                  if (var68.x >= Occluder.field2387 && var68.x < Occluder.field2387 + 8 && var68.y >= class69.field596 && var68.y < class69.field596 + 8 && var68.plane == class22.Client_plane) {
                     var68.hitpoints = 0;
                  }
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2723 == var1.serverPacket) {
               for(var10 = 0; var10 < players.length; ++var10) {
                  if (players[var10] != null) {
                     players[var10].sequence = -1;
                  }
               }

               for(var10 = 0; var10 < npcs.length; ++var10) {
                  if (npcs[var10] != null) {
                     npcs[var10].sequence = -1;
                  }
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2715 == var1.serverPacket) {
               var10 = var3.method6613();
               var32 = Frames.getWidget(var10);

               for(var4 = 0; var4 < var32.itemIds.length; ++var4) {
                  var32.itemIds[var4] = -1;
                  var32.itemIds[var4] = 0;
               }

               WorldMapCacheName.invalidateWidget(var32);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2774 == var1.serverPacket) {
               class170.method3554();
               var10 = var3.method6595();
               var6 = var3.method6549();
               var4 = var3.method6614();
               experience[var6] = var4;
               currentLevels[var6] = var10;
               levels[var6] = 1;

               for(var45 = 0; var45 < 98; ++var45) {
                  if (var4 >= Skills.Skills_experienceTable[var45]) {
                     levels[var6] = var45 + 2;
                  }
               }

               changedSkills[++changedSkillsCount - 1 & 31] = var6;
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2782 == var1.serverPacket) {
               NetSocket.friendSystem.method1698();
               field836 = cycleCntr;
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2747 == var1.serverPacket) {
               var10 = var3.method6614();
               var6 = var3.method6605();
               var4 = var3.method6605();
               var29 = Frames.getWidget(var10);
               var29.field3014 = var4 + (var6 << 16);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2731 == var1.serverPacket) {
               var10 = var3.method6614();
               var6 = var3.readUnsignedShort();
               Varps.Varps_temp[var6] = var10;
               if (Varps.Varps_main[var6] != var10) {
                  Varps.Varps_main[var6] = var10;
               }

               ApproximateRouteStrategy.changeGameOptions(var6);
               changedVarps[++changedVarpCount - 1 & 31] = var6;
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2771 == var1.serverPacket) {
               var10 = var3.readInt();
               var6 = var3.readUnsignedShort();
               if (var10 < -70000) {
                  var6 += 32768;
               }

               if (var10 >= 0) {
                  var13 = Frames.getWidget(var10);
               } else {
                  var13 = null;
               }

               if (var13 != null) {
                  for(var45 = 0; var45 < var13.itemIds.length; ++var45) {
                     var13.itemIds[var45] = 0;
                     var13.itemQuantities[var45] = 0;
                  }
               }

               GrandExchangeOfferOwnWorldComparator.clearItemContainer(var6);
               var45 = var3.readUnsignedShort();

               for(var8 = 0; var8 < var45; ++var8) {
                  var9 = var3.method6604();
                  var14 = var3.method6595();
                  if (var14 == 255) {
                     var14 = var3.method6613();
                  }

                  if (var13 != null && var8 < var13.itemIds.length) {
                     var13.itemIds[var8] = var9;
                     var13.itemQuantities[var8] = var14;
                  }

                  class4.itemContainerSetItem(var6, var8, var9 - 1, var14);
               }

               if (var13 != null) {
                  WorldMapCacheName.invalidateWidget(var13);
               }

               class170.method3554();
               changedItemContainers[++field830 - 1 & 31] = var6 & 32767;
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2756 == var1.serverPacket) {
               var10 = var3.method6604();
               Login.method1941(var10);
               changedItemContainers[++field830 - 1 & 31] = var10 & 32767;
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2720 == var1.serverPacket) {
               if (WorldMapRegion.friendsChat != null) {
                  WorldMapRegion.friendsChat.method5659(var3);
               }

               CollisionMap.method3155();
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2755 == var1.serverPacket) {
               for(var10 = 0; var10 < VarpDefinition.VarpDefinition_fileCount; ++var10) {
                  VarpDefinition var67 = ClanSettings.VarpDefinition_get(var10);
                  if (var67 != null) {
                     Varps.Varps_temp[var10] = 0;
                     Varps.Varps_main[var10] = 0;
                  }
               }

               class170.method3554();
               changedVarpCount += 32;
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2707 == var1.serverPacket) {
               SoundSystem.updateNpcs(false, var3);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2702 == var1.serverPacket) {
               var10 = var3.readUnsignedShort();
               byte var66 = var3.readByte();
               Varps.Varps_temp[var10] = var66;
               if (Varps.Varps_main[var10] != var66) {
                  Varps.Varps_main[var10] = var66;
               }

               ApproximateRouteStrategy.changeGameOptions(var10);
               changedVarps[++changedVarpCount - 1 & 31] = var10;
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2768 == var1.serverPacket) {
               NetSocket.friendSystem.ignoreList.read(var3, var1.serverPacketLength);
               MouseRecorder.FriendSystem_invalidateIgnoreds();
               field836 = cycleCntr;
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2744 == var1.serverPacket) {
               Tile.method3843(class225.field2684);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2713 == var1.serverPacket) {
               var12 = var3.readStringCp1252NullTerminated();
               var20 = (long)var3.readUnsignedShort();
               var22 = (long)var3.readMedium();
               PlayerType var65 = (PlayerType)Messages.findEnumerated(class372.PlayerType_values(), var3.readUnsignedByte());
               long var73 = var22 + (var20 << 32);
               boolean var36 = false;

               for(var31 = 0; var31 < 100; ++var31) {
                  if (crossWorldMessageIds[var31] == var73) {
                     var36 = true;
                     break;
                  }
               }

               if (NetSocket.friendSystem.isIgnored(new Username(var12, WorldMapSection0.loginType))) {
                  var36 = true;
               }

               if (!var36 && field755 == 0) {
                  crossWorldMessageIds[crossWorldMessageIdsIndex] = var73;
                  crossWorldMessageIdsIndex = (crossWorldMessageIdsIndex + 1) % 100;
                  var30 = AbstractFont.escapeBrackets(class43.method433(MusicPatch.method4592(var3)));
                  byte var76;
                  if (var65.isPrivileged) {
                     var76 = 7;
                  } else {
                     var76 = 3;
                  }

                  if (var65.modIcon != -1) {
                     World.addGameMessage(var76, SecureRandomFuture.method1982(var65.modIcon) + var12, var30);
                  } else {
                     World.addGameMessage(var76, var12, var30);
                  }
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2742 == var1.serverPacket) {
               var28 = var3.readByte();
               var5 = var3.readStringCp1252NullTerminated();
               long var64 = (long)var3.readUnsignedShort();
               long var35 = (long)var3.readMedium();
               PlayerType var37 = (PlayerType)Messages.findEnumerated(class372.PlayerType_values(), var3.readUnsignedByte());
               long var38 = (var64 << 32) + var35;
               boolean var40 = false;
               ClanChannel var41 = null;
               var41 = var28 >= 0 ? currentClanChannels[var28] : ApproximateRouteStrategy.guestClanChannel;
               if (var41 == null) {
                  var40 = true;
               } else {
                  label1640: {
                     for(var19 = 0; var19 < 100; ++var19) {
                        if (crossWorldMessageIds[var19] == var38) {
                           var40 = true;
                           break label1640;
                        }
                     }

                     if (var37.isUser && NetSocket.friendSystem.isIgnored(new Username(var5, WorldMapSection0.loginType))) {
                        var40 = true;
                     }
                  }
               }

               if (!var40) {
                  crossWorldMessageIds[crossWorldMessageIdsIndex] = var38;
                  crossWorldMessageIdsIndex = (crossWorldMessageIdsIndex + 1) % 100;
                  var26 = AbstractFont.escapeBrackets(MusicPatch.method4592(var3));
                  int var42 = var28 >= 0 ? 41 : 44;
                  if (var37.modIcon != -1) {
                     class5.addChatMessage(var42, SecureRandomFuture.method1982(var37.modIcon) + var5, var26, var41.name);
                  } else {
                     class5.addChatMessage(var42, var5, var26, var41.name);
                  }
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2716 == var1.serverPacket) {
               field839 = cycleCntr;
               var28 = var3.readByte();
               class19 var63 = new class19(var3);
               ClanChannel var69;
               if (var28 >= 0) {
                  var69 = currentClanChannels[var28];
               } else {
                  var69 = ApproximateRouteStrategy.guestClanChannel;
               }

               var63.method218(var69);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2798 == var1.serverPacket) {
               class124.method2496();
               var28 = var3.readByte();
               class2 var33 = new class2(var3);
               ClanSettings var34;
               if (var28 >= 0) {
                  var34 = currentClanSettings[var28];
               } else {
                  var34 = NPCComposition.guestClanSettings;
               }

               var33.method16(var34);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2703 == var1.serverPacket) {
               var10 = var3.method6661();
               var6 = var3.method6598();
               var4 = var3.method6621();
               var29 = Frames.getWidget(var6);
               if (var4 != var29.rawX || var10 != var29.rawY || var29.xAlignment != 0 || var29.yAlignment != 0) {
                  var29.rawX = var4;
                  var29.rawY = var10;
                  var29.xAlignment = 0;
                  var29.yAlignment = 0;
                  WorldMapCacheName.invalidateWidget(var29);
                  this.alignWidget(var29);
                  if (var29.type == 0) {
                     class313.revalidateWidgetScroll(Widget.Widget_interfaceComponents[var6 >> 16], var29, false);
                  }
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2773 == var1.serverPacket) {
               var10 = var3.readUnsignedByte();
               var6 = var3.method6605();
               var4 = var3.method6613();
               var15 = (InterfaceParent)interfaceParents.get((long)var4);
               if (var15 != null) {
                  class43.closeInterface(var15, var6 != var15.group);
               }

               class17.method208(var4, var6, var10);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2700 == var1.serverPacket) {
               var10 = var3.method6603();
               rootInterface = var10;
               this.resizeRoot(false);
               class21.Widget_resetModelFrames(var10);
               FloorDecoration.runWidgetOnLoadListener(rootInterface);

               for(var6 = 0; var6 < 100; ++var6) {
                  field719[var6] = true;
               }

               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2749 == var1.serverPacket) {
               if (var1.serverPacketLength == 0) {
                  WorldMapRegion.friendsChat = null;
               } else {
                  if (WorldMapRegion.friendsChat == null) {
                     WorldMapRegion.friendsChat = new FriendsChat(WorldMapSection0.loginType, class23.client);
                  }

                  WorldMapRegion.friendsChat.readUpdate(var3);
               }

               CollisionMap.method3155();
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2795 == var1.serverPacket) {
               Tile.method3843(class225.field2688);
               var1.serverPacket = null;
               return true;
            }

            if (ServerPacket.field2766 == var1.serverPacket) {
               field839 = cycleCntr;
               var28 = var3.readByte();
               if (var1.serverPacketLength == 1) {
                  if (var28 >= 0) {
                     currentClanChannels[var28] = null;
                  } else {
                     ApproximateRouteStrategy.guestClanChannel = null;
                  }

                  var1.serverPacket = null;
                  return true;
               }

               if (var28 >= 0) {
                  currentClanChannels[var28] = new ClanChannel(var3);
               } else {
                  ApproximateRouteStrategy.guestClanChannel = new ClanChannel(var3);
               }

               var1.serverPacket = null;
               return true;
            }

            class266.RunException_sendStackTrace("" + (var1.serverPacket != null ? var1.serverPacket.id * -1581409905 * 1593533807 : -1) + "," + (var1.field1412 != null ? var1.field1412.id * -1581409905 * 1593533807 : -1) + "," + (var1.field1400 != null ? var1.field1400.id * -1581409905 * 1593533807 : -1) + "," + var1.serverPacketLength, (Throwable)null);
            class7.logOut();
         } catch (IOException var43) {
            NetFileRequest.method4912();
         } catch (Exception var44) {
            var5 = "" + (var1.serverPacket != null ? var1.serverPacket.id * -1581409905 * 1593533807 : -1) + "," + (var1.field1412 != null ? var1.field1412.id * -1581409905 * 1593533807 : -1) + "," + (var1.field1400 != null ? var1.field1400.id * -1581409905 * 1593533807 : -1) + "," + var1.serverPacketLength + "," + (class93.localPlayer.pathX[0] + VertexNormal.baseX) + "," + (class93.localPlayer.pathY[0] + SoundSystem.baseY) + ",";

            for(var4 = 0; var4 < var1.serverPacketLength && var4 < 50; ++var4) {
               var5 = var5 + var3.array[var4] + ",";
            }

            class266.RunException_sendStackTrace(var5, var44);
            class7.logOut();
         }

         return true;
      }
   }

   @ObfuscatedName("hk")
   @ObfuscatedSignature(
      descriptor = "(B)V",
      garbageValue = "0"
   )
   @Export("menu")
   final void menu() {
      class377.method6509();
      if (Script.dragInventoryWidget == null && clickedWidget == null) {
         int var1;
         int var2;
         int var3;
         int var4;
         int var5;
         label225: {
            int var6 = MouseHandler.MouseHandler_lastButton;
            int var7;
            int var8;
            if (isMenuOpen) {
               int var14;
               int var15;
               if (var6 != 1 && (mouseCam || var6 != 4)) {
                  var1 = MouseHandler.MouseHandler_x;
                  var7 = MouseHandler.MouseHandler_y;
                  if (var1 < class14.menuX - 10 || var1 > class14.menuX + class29.menuWidth + 10 || var7 < class243.menuY - 10 || var7 > class243.menuY + class24.menuHeight + 10) {
                     isMenuOpen = false;
                     var2 = class14.menuX;
                     var3 = class243.menuY;
                     var14 = class29.menuWidth;
                     var15 = class24.menuHeight;

                     for(var8 = 0; var8 < rootWidgetCount; ++var8) {
                        if (rootWidgetWidths[var8] + rootWidgetXs[var8] > var2 && rootWidgetXs[var8] < var14 + var2 && rootWidgetHeights[var8] + rootWidgetYs[var8] > var3 && rootWidgetYs[var8] < var3 + var15) {
                           field719[var8] = true;
                        }
                     }
                  }
               }

               if (var6 == 1 || !mouseCam && var6 == 4) {
                  var1 = class14.menuX;
                  var7 = class243.menuY;
                  var2 = class29.menuWidth;
                  var3 = MouseHandler.MouseHandler_lastPressedX;
                  var14 = MouseHandler.MouseHandler_lastPressedY;
                  var15 = -1;

                  for(var8 = 0; var8 < menuOptionsCount; ++var8) {
                     var4 = var7 + (menuOptionsCount - 1 - var8) * 15 + 31;
                     if (var3 > var1 && var3 < var1 + var2 && var14 > var4 - 13 && var14 < var4 + 3) {
                        var15 = var8;
                     }
                  }

                  if (var15 != -1) {
                     Interpreter.method1888(var15);
                  }

                  isMenuOpen = false;
                  var8 = class14.menuX;
                  var4 = class243.menuY;
                  var5 = class29.menuWidth;
                  int var16 = class24.menuHeight;

                  for(int var12 = 0; var12 < rootWidgetCount; ++var12) {
                     if (rootWidgetXs[var12] + rootWidgetWidths[var12] > var8 && rootWidgetXs[var12] < var5 + var8 && rootWidgetYs[var12] + rootWidgetHeights[var12] > var4 && rootWidgetYs[var12] < var4 + var16) {
                        field719[var12] = true;
                     }
                  }
               }
            } else {
               var1 = MilliClock.method2588();
               if ((var6 == 1 || !mouseCam && var6 == 4) && var1 >= 0) {
                  var7 = menuOpcodes[var1];
                  if (var7 == 39 || var7 == 40 || var7 == 41 || var7 == 42 || var7 == 43 || var7 == 33 || var7 == 34 || var7 == 35 || var7 == 36 || var7 == 37 || var7 == 38 || var7 == 1005) {
                     var2 = menuArguments1[var1];
                     var3 = menuArguments2[var1];
                     Widget var9 = Frames.getWidget(var3);
                     var8 = class21.getWidgetFlags(var9);
                     boolean var10 = (var8 >> 28 & 1) != 0;
                     if (var10) {
                        break label225;
                     }

                     Object var11 = null;
                     if (Canvas.method394(class21.getWidgetFlags(var9))) {
                        break label225;
                     }
                  }
               }

               if ((var6 == 1 || !mouseCam && var6 == 4) && this.shouldLeftClickOpenMenu()) {
                  var6 = 2;
               }

               if ((var6 == 1 || !mouseCam && var6 == 4) && menuOptionsCount > 0) {
                  Interpreter.method1888(var1);
               }

               if (var6 == 2 && menuOptionsCount > 0) {
                  this.openMenu(MouseHandler.MouseHandler_lastPressedX, MouseHandler.MouseHandler_lastPressedY);
               }
            }

            return;
         }

         if (Script.dragInventoryWidget != null && !field848 && menuOptionsCount > 0 && !this.shouldLeftClickOpenMenu()) {
            var4 = draggedWidgetX;
            var5 = draggedWidgetY;
            MenuAction var13 = ReflectionCheck.tempMenuAction;
            Script.menuAction(var13.param0, var13.param1, var13.opcode, var13.identifier, var13.action, var13.action, var4, var5);
            ReflectionCheck.tempMenuAction = null;
         }

         field848 = false;
         itemDragDuration = 0;
         if (Script.dragInventoryWidget != null) {
            WorldMapCacheName.invalidateWidget(Script.dragInventoryWidget);
         }

         Script.dragInventoryWidget = Frames.getWidget(var3);
         dragItemSlotSource = var2;
         draggedWidgetX = MouseHandler.MouseHandler_lastPressedX;
         draggedWidgetY = MouseHandler.MouseHandler_lastPressedY;
         if (var1 >= 0) {
            ReflectionCheck.tempMenuAction = new MenuAction();
            ReflectionCheck.tempMenuAction.param0 = menuArguments1[var1];
            ReflectionCheck.tempMenuAction.param1 = menuArguments2[var1];
            ReflectionCheck.tempMenuAction.opcode = menuOpcodes[var1];
            ReflectionCheck.tempMenuAction.identifier = menuIdentifiers[var1];
            ReflectionCheck.tempMenuAction.action = menuActions[var1];
         }

         WorldMapCacheName.invalidateWidget(Script.dragInventoryWidget);
      }

   }

   @ObfuscatedName("hx")
   @ObfuscatedSignature(
      descriptor = "(I)Z",
      garbageValue = "-1407075512"
   )
   @Export("shouldLeftClickOpenMenu")
   final boolean shouldLeftClickOpenMenu() {
      int var1 = MilliClock.method2588();
      return (leftClickOpensMenu == 1 && menuOptionsCount > 2 || class5.method51(var1)) && !menuShiftClick[var1];
   }

   @ObfuscatedName("hp")
   @ObfuscatedSignature(
      descriptor = "(III)V",
      garbageValue = "1521465353"
   )
   @Export("openMenu")
   final void openMenu(int var1, int var2) {
      ReflectionCheck.method1126(var1, var2);
      AbstractSocket.scene.menuOpen(class22.Client_plane, var1, var2, false);
      isMenuOpen = true;
   }

   @ObfuscatedName("iy")
   @ObfuscatedSignature(
      descriptor = "(ZI)V",
      garbageValue = "2038823416"
   )
   @Export("resizeRoot")
   final void resizeRoot(boolean var1) {
      PlayerType.method4857(rootInterface, class32.canvasWidth, ReflectionCheck.canvasHeight, var1);
   }

   @ObfuscatedName("ib")
   @ObfuscatedSignature(
      descriptor = "(Lio;I)V",
      garbageValue = "2012935641"
   )
   @Export("alignWidget")
   void alignWidget(Widget var1) {
      Widget var2 = var1.parentId == -1 ? null : Frames.getWidget(var1.parentId);
      int var3;
      int var4;
      if (var2 == null) {
         var3 = class32.canvasWidth;
         var4 = ReflectionCheck.canvasHeight;
      } else {
         var3 = var2.width;
         var4 = var2.height;
      }

      Clock.alignWidgetSize(var1, var3, var4, false);
      class24.alignWidgetPosition(var1, var3, var4);
   }

   @ObfuscatedName("jt")
   @ObfuscatedSignature(
      descriptor = "(B)V",
      garbageValue = "-72"
   )
   final void method1198() {
      WorldMapCacheName.invalidateWidget(clickedWidget);
      ++Actor.widgetDragDuration;
      if (field822 && field819) {
         int var1 = MouseHandler.MouseHandler_x;
         int var2 = MouseHandler.MouseHandler_y;
         var1 -= widgetClickX;
         var2 -= widgetClickY;
         if (var1 < field740) {
            var1 = field740;
         }

         if (var1 + clickedWidget.width > field740 + clickedWidgetParent.width) {
            var1 = field740 + clickedWidgetParent.width - clickedWidget.width;
         }

         if (var2 < field821) {
            var2 = field821;
         }

         if (var2 + clickedWidget.height > field821 + clickedWidgetParent.height) {
            var2 = field821 + clickedWidgetParent.height - clickedWidget.height;
         }

         int var3 = var1 - field854;
         int var4 = var2 - field804;
         int var5 = clickedWidget.dragZoneSize;
         if (Actor.widgetDragDuration > clickedWidget.dragThreshold && (var3 > var5 || var3 < -var5 || var4 > var5 || var4 < -var5)) {
            isDraggingWidget = true;
         }

         int var6 = var1 - field740 + clickedWidgetParent.scrollX;
         int var7 = var2 - field821 + clickedWidgetParent.scrollY;
         ScriptEvent var8;
         if (clickedWidget.onDrag != null && isDraggingWidget) {
            var8 = new ScriptEvent();
            var8.widget = clickedWidget;
            var8.mouseX = var6;
            var8.mouseY = var7;
            var8.args = clickedWidget.onDrag;
            PacketWriter.runScriptEvent(var8);
         }

         if (MouseHandler.MouseHandler_currentButton == 0) {
            if (isDraggingWidget) {
               if (clickedWidget.onDragComplete != null) {
                  var8 = new ScriptEvent();
                  var8.widget = clickedWidget;
                  var8.mouseX = var6;
                  var8.mouseY = var7;
                  var8.dragTarget = draggedOnWidget;
                  var8.args = clickedWidget.onDragComplete;
                  PacketWriter.runScriptEvent(var8);
               }

               if (draggedOnWidget != null && StructComposition.method2892(clickedWidget) != null) {
                  PacketBufferNode var9 = class21.getPacketBufferNode(ClientPacket.field2586, packetWriter.isaacCipher);
                  var9.packetBuffer.method6611(draggedOnWidget.id);
                  var9.packetBuffer.method6602(clickedWidget.itemId);
                  var9.packetBuffer.writeShort(draggedOnWidget.childIndex);
                  var9.packetBuffer.writeInt(clickedWidget.id);
                  var9.packetBuffer.writeShort(draggedOnWidget.itemId);
                  var9.packetBuffer.method6602(clickedWidget.childIndex);
                  packetWriter.addNode(var9);
               }
            } else if (this.shouldLeftClickOpenMenu()) {
               this.openMenu(field854 + widgetClickX, widgetClickY + field804);
            } else if (menuOptionsCount > 0) {
               int var12 = field854 + widgetClickX;
               int var10 = widgetClickY + field804;
               MenuAction var11 = ReflectionCheck.tempMenuAction;
               Script.menuAction(var11.param0, var11.param1, var11.opcode, var11.identifier, var11.action, var11.action, var12, var10);
               ReflectionCheck.tempMenuAction = null;
            }

            clickedWidget = null;
         }
      } else if (Actor.widgetDragDuration > 1) {
         clickedWidget = null;
      }

   }

   @ObfuscatedName("ll")
   @ObfuscatedSignature(
      descriptor = "(I)Lly;",
      garbageValue = "-1180229355"
   )
   @Export("username")
   public Username username() {
      return class93.localPlayer != null ? class93.localPlayer.username : null;
   }

   @ObfuscatedName("gn")
   @ObfuscatedSignature(
      descriptor = "(I)Z",
      garbageValue = "2028963456"
   )
   static boolean method1514() {
      return (drawPlayerNames & 4) != 0;
   }

   static {
      playerAttackOption = AttackOption.AttackOption_hidden;
      npcAttackOption = AttackOption.AttackOption_hidden;
      titleLoadingStage = 0;
      js5ConnectState = 0;
      field670 = 0;
      js5Errors = 0;
      loginState = 0;
      field673 = 0;
      field845 = 0;
      field675 = 0;
      field676 = class125.field1468;
      Login_isUsernameRemembered = false;
      secureRandomFuture = new SecureRandomFuture();
      randomDatData = null;
      npcs = new NPC['耀'];
      npcCount = 0;
      npcIndices = new int['耀'];
      field685 = 0;
      field686 = new int[250];
      packetWriter = new PacketWriter();
      logoutTimer = 0;
      hadNetworkError = false;
      useBufferedSocket = true;
      timer = new Timer();
      fontsMap = new HashMap();
      field694 = 0;
      field695 = 1;
      field696 = 0;
      field697 = 1;
      field698 = 0;
      collisionMaps = new CollisionMap[4];
      isInInstance = false;
      instanceChunkTemplates = new int[4][13][13];
      field702 = new int[]{0, 0, 0, 0, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3};
      field913 = 0;
      field704 = 2301979;
      field705 = 5063219;
      field706 = 3353893;
      field707 = 7759444;
      field708 = false;
      alternativeScrollbarWidth = 0;
      camAngleX = 128;
      camAngleY = 0;
      camAngleDY = 0;
      camAngleDX = 0;
      mouseCamClickedX = 0;
      mouseCamClickedY = 0;
      oculusOrbState = 0;
      camFollowHeight = 50;
      field718 = 0;
      field732 = 0;
      field720 = 0;
      oculusOrbNormalSpeed = 12;
      oculusOrbSlowedSpeed = 6;
      field723 = 0;
      field724 = false;
      field725 = 0;
      field726 = false;
      field727 = 0;
      overheadTextCount = 0;
      overheadTextLimit = 50;
      overheadTextXs = new int[overheadTextLimit];
      overheadTextYs = new int[overheadTextLimit];
      overheadTextAscents = new int[overheadTextLimit];
      overheadTextXOffsets = new int[overheadTextLimit];
      overheadTextColors = new int[overheadTextLimit];
      overheadTextEffects = new int[overheadTextLimit];
      overheadTextCyclesRemaining = new int[overheadTextLimit];
      overheadText = new String[overheadTextLimit];
      tileLastDrawnActor = new int[104][104];
      viewportDrawCount = 0;
      viewportTempX = -1;
      viewportTempY = -1;
      mouseCrossX = 0;
      mouseCrossY = 0;
      mouseCrossState = 0;
      mouseCrossColor = 0;
      showMouseCross = true;
      field747 = 0;
      field841 = 0;
      dragItemSlotSource = 0;
      draggedWidgetX = 0;
      draggedWidgetY = 0;
      dragItemSlotDestination = 0;
      field848 = false;
      itemDragDuration = 0;
      field755 = 0;
      showLoadingMessages = true;
      players = new Player[2048];
      localPlayerIndex = -1;
      field759 = 0;
      renderSelf = true;
      drawPlayerNames = 0;
      field762 = 0;
      field771 = new int[1000];
      playerMenuOpcodes = new int[]{44, 45, 46, 47, 48, 49, 50, 51};
      playerMenuActions = new String[8];
      playerOptionsPriorities = new boolean[8];
      defaultRotations = new int[]{768, 1024, 1280, 512, 1536, 256, 0, 1792};
      combatTargetPlayerIndex = -1;
      groundItems = new NodeDeque[4][104][104];
      pendingSpawns = new NodeDeque();
      projectiles = new NodeDeque();
      graphicsObjects = new NodeDeque();
      currentLevels = new int[25];
      levels = new int[25];
      experience = new int[25];
      leftClickOpensMenu = 0;
      isMenuOpen = false;
      menuOptionsCount = 0;
      menuArguments1 = new int[500];
      menuArguments2 = new int[500];
      menuOpcodes = new int[500];
      menuIdentifiers = new int[500];
      menuActions = new String[500];
      menuTargets = new String[500];
      menuShiftClick = new boolean[500];
      followerOpsLowPriority = false;
      shiftClickDrop = false;
      tapToDrop = false;
      showMouseOverText = true;
      viewportX = -1;
      viewportY = -1;
      field792 = 0;
      field833 = 50;
      isItemSelected = 0;
      selectedItemName = null;
      isSpellSelected = false;
      selectedSpellChildIndex = -1;
      field788 = -1;
      selectedSpellActionName = null;
      selectedSpellName = null;
      rootInterface = -1;
      interfaceParents = new NodeHashTable(8);
      field803 = 0;
      field805 = -1;
      chatEffects = 0;
      field912 = 0;
      meslayerContinueWidget = null;
      runEnergy = 0;
      weight = 0;
      staffModLevel = 0;
      followerIndex = -1;
      playerMod = false;
      viewportWidget = null;
      clickedWidget = null;
      clickedWidgetParent = null;
      widgetClickX = 0;
      widgetClickY = 0;
      draggedOnWidget = null;
      field819 = false;
      field740 = -1;
      field821 = -1;
      field822 = false;
      field854 = -1;
      field804 = -1;
      isDraggingWidget = false;
      cycleCntr = 1;
      changedVarps = new int[32];
      changedVarpCount = 0;
      changedItemContainers = new int[32];
      field830 = 0;
      changedSkills = new int[32];
      changedSkillsCount = 0;
      field911 = new int[32];
      field852 = 0;
      chatCycle = 0;
      field836 = 0;
      field844 = 0;
      field654 = 0;
      field839 = 0;
      field840 = 0;
      field748 = 0;
      field659 = 0;
      mouseWheelRotation = 0;
      scriptEvents = new NodeDeque();
      field652 = new NodeDeque();
      field846 = new NodeDeque();
      widgetFlags = new NodeHashTable(512);
      rootWidgetCount = 0;
      field849 = -2;
      field719 = new boolean[100];
      field780 = new boolean[100];
      field860 = new boolean[100];
      rootWidgetXs = new int[100];
      rootWidgetYs = new int[100];
      rootWidgetWidths = new int[100];
      rootWidgetHeights = new int[100];
      gameDrawingMode = 0;
      field858 = 0L;
      isResizable = true;
      field877 = new int[]{16776960, 16711680, 65280, 65535, 16711935, 16777215};
      publicChatMode = 0;
      tradeChatMode = 0;
      field863 = "";
      crossWorldMessageIds = new long[100];
      crossWorldMessageIdsIndex = 0;
      field806 = 0;
      field867 = new int[128];
      field868 = new int[128];
      field809 = -1L;
      currentClanSettings = new ClanSettings[1];
      currentClanChannels = new ClanChannel[1];
      field872 = -1;
      mapIconCount = 0;
      mapIconXs = new int[1000];
      mapIconYs = new int[1000];
      mapIcons = new SpritePixels[1000];
      destinationX = 0;
      destinationY = 0;
      minimapState = 0;
      currentTrackGroupId = -1;
      field881 = false;
      soundEffectCount = 0;
      soundEffectIds = new int[50];
      queuedSoundEffectLoops = new int[50];
      queuedSoundEffectDelays = new int[50];
      soundLocations = new int[50];
      soundEffects = new SoundEffect[50];
      isCameraLocked = false;
      field693 = new boolean[5];
      field890 = new int[5];
      field891 = new int[5];
      field892 = new int[5];
      field893 = new int[5];
      field894 = 256;
      field895 = 205;
      zoomHeight = 256;
      zoomWidth = 320;
      field909 = 1;
      field828 = 32767;
      field900 = 1;
      field901 = 32767;
      viewportOffsetX = 0;
      viewportOffsetY = 0;
      viewportWidth = 0;
      viewportHeight = 0;
      viewportZoom = 0;
      playerAppearance = new PlayerComposition();
      field908 = -1;
      field752 = -1;
      platformInfoProvider = new DesktopPlatformInfoProvider();
      grandExchangeOffers = new GrandExchangeOffer[8];
      GrandExchangeEvents_worldComparator = new GrandExchangeOfferOwnWorldComparator();
      field717 = -1;
      archiveLoaders = new ArrayList(10);
      archiveLoadersDone = 0;
      field916 = 0;
      field785 = new ApproximateRouteStrategy();
      field689 = new int[50];
      field851 = new int[50];
   }

   public static void onGameStateChanged(int var0) {
      System.out.println("GameState: " + class23.client.api$getRSGameState());
      throw new RuntimeException("Check out this fuckin stacktrace");
   }

   public DrawCallbacks getDrawCallbacks() {
      return null;
   }

   public void setDrawCallbacks(DrawCallbacks var1) {
   }

   public Logger getLogger() {
      return null;
   }

   public String getBuildID() {
      return null;
   }

   public List getPlayers() {
      return null;
   }

   public List getNpcs() {
      return null;
   }

   public net.runelite.api.NPC[] getCachedNPCs() {
      return new net.runelite.api.NPC[0];
   }

   public net.runelite.api.Player[] getCachedPlayers() {
      return new net.runelite.api.Player[0];
   }

   public int getBoostedSkillLevel(Skill var1) {
      return 0;
   }

   public int getRealSkillLevel(Skill var1) {
      return 0;
   }

   public int getTotalLevel() {
      return 0;
   }

   public MessageNode addChatMessage(ChatMessageType var1, String var2, String var3, String var4) {
      return null;
   }

   public MessageNode addChatMessage(ChatMessageType var1, String var2, String var3, String var4, boolean var5) {
      return null;
   }

   public GameState getGameState() {
      return null;
   }

   public void setGameState(GameState var1) {
   }

   public void setGameState(int var1) {
   }

   public void stopNow() {
   }

   public String getUsername() {
      return null;
   }

   public void setUsername(String var1) {
   }

   public void setPassword(String var1) {
   }

   public void setOtp(String var1) {
   }

   public int getCurrentLoginField() {
      return 0;
   }

   public int getLoginIndex() {
      return 0;
   }

   public AccountType getAccountType() {
      return null;
   }

   public java.awt.Canvas getCanvas() {
      return null;
   }

   public Thread getClientThread() {
      return null;
   }

   public boolean isClientThread() {
      return false;
   }

   public void setReplaceCanvasNextFrame(boolean var1) {
   }

   public int getFPS() {
      return 0;
   }

   public int api$getCameraX() {
      return 0;
   }

   public int api$getCameraY() {
      return 0;
   }

   public int api$getCameraZ() {
      return 0;
   }

   public int getCameraPitch() {
      return 0;
   }

   public int getCameraYaw() {
      return 0;
   }

   public int getWorld() {
      return 0;
   }

   public int getCanvasHeight() {
      return 0;
   }

   public int getCanvasWidth() {
      return 0;
   }

   public int getViewportHeight() {
      return 0;
   }

   public int getViewportWidth() {
      return 0;
   }

   public int getViewportXOffset() {
      return 0;
   }

   public int getViewportYOffset() {
      return 0;
   }

   public int getScale() {
      return 0;
   }

   public Point getMouseCanvasPosition() {
      return null;
   }

   public int[][][] getTileHeights() {
      return new int[0][][];
   }

   public byte[][][] getTileSettings() {
      return new byte[0][][];
   }

   public int getPlane() {
      return 0;
   }

   public net.runelite.api.Scene getScene() {
      return null;
   }

   public net.runelite.api.Player getLocalPlayer() {
      return null;
   }

   public int getLocalPlayerIndex() {
      return 0;
   }

   public net.runelite.api.ItemComposition getItemComposition(int var1) {
      return null;
   }

   public net.runelite.api.ItemComposition getItemDefinition(int var1) {
      return null;
   }

   public net.runelite.api.SpritePixels createItemSprite(int var1, int var2, int var3, int var4, int var5, boolean var6, int var7) {
      return null;
   }

   public net.runelite.api.SpritePixels[] getSprites(IndexDataBase var1, int var2, int var3) {
      return new net.runelite.api.SpritePixels[0];
   }

   public IndexDataBase getIndexSprites() {
      return null;
   }

   public IndexDataBase getIndexScripts() {
      return null;
   }

   public IndexDataBase getIndexConfig() {
      return null;
   }

   public int getBaseX() {
      return 0;
   }

   public int getBaseY() {
      return 0;
   }

   public int getMouseCurrentButton() {
      return 0;
   }

   public net.runelite.api.Tile getSelectedSceneTile() {
      return null;
   }

   public boolean isDraggingWidget() {
      return false;
   }

   public net.runelite.api.widgets.Widget getDraggedWidget() {
      return null;
   }

   public net.runelite.api.widgets.Widget getDraggedOnWidget() {
      return null;
   }

   public void setDraggedOnWidget(net.runelite.api.widgets.Widget var1) {
   }

   public int getTopLevelInterfaceId() {
      return 0;
   }

   public net.runelite.api.widgets.Widget[] getWidgetRoots() {
      return new net.runelite.api.widgets.Widget[0];
   }

   public net.runelite.api.widgets.Widget getWidget(WidgetInfo var1) {
      return null;
   }

   public net.runelite.api.widgets.Widget getWidget(int var1, int var2) {
      return null;
   }

   public net.runelite.api.widgets.Widget getWidget(int var1) {
      return null;
   }

   public int[] getWidgetPositionsX() {
      return new int[0];
   }

   public int[] getWidgetPositionsY() {
      return new int[0];
   }

   public net.runelite.api.widgets.Widget createWidget() {
      return null;
   }

   public int getEnergy() {
      return 0;
   }

   public int getWeight() {
      return 0;
   }

   public String[] getPlayerOptions() {
      return new String[0];
   }

   public boolean[] getPlayerOptionsPriorities() {
      return new boolean[0];
   }

   public int[] getPlayerMenuTypes() {
      return new int[0];
   }

   public net.runelite.api.World[] getWorldList() {
      return new net.runelite.api.World[0];
   }

   public MenuEntry[] getMenuEntries() {
      return new MenuEntry[0];
   }

   public int getMenuOptionCount() {
      return 0;
   }

   public void setMenuEntries(MenuEntry[] var1) {
   }

   public void setMenuOptionCount(int var1) {
   }

   public boolean isMenuOpen() {
      return false;
   }

   public int getMapAngle() {
      return 0;
   }

   public boolean isResized() {
      return false;
   }

   public int getRevision() {
      return 0;
   }

   public int[] getMapRegions() {
      return new int[0];
   }

   public int[][][] getInstanceTemplateChunks() {
      return new int[0][][];
   }

   public int[][] getXteaKeys() {
      return new int[0][];
   }

   public int[] getVarps() {
      return new int[0];
   }

   public Map getVarcMap() {
      return null;
   }

   public int getVar(VarPlayer var1) {
      return 0;
   }

   public int getVar(Varbits var1) {
      return 0;
   }

   public int getVar(VarClientInt var1) {
      return 0;
   }

   public String getVar(VarClientStr var1) {
      return null;
   }

   public int getVarbitValue(int var1) {
      return 0;
   }

   public int getVarcIntValue(int var1) {
      return 0;
   }

   public String getVarcStrValue(int var1) {
      return null;
   }

   public void setVar(VarClientStr var1, String var2) {
   }

   public void setVar(VarClientInt var1, int var2) {
   }

   public void setVarbit(Varbits var1, int var2) {
   }

   public net.runelite.api.VarbitComposition getVarbit(int var1) {
      return null;
   }

   public int getVarbitValue(int[] var1, int var2) {
      return 0;
   }

   public int getVarpValue(int[] var1, int var2) {
      return 0;
   }

   public int getVarpValue(int var1) {
      return 0;
   }

   public void setVarbitValue(int[] var1, int var2, int var3) {
   }

   public void queueChangedVarp(int var1) {
   }

   public HashTable getWidgetFlags() {
      return null;
   }

   public HashTable getComponentTable() {
      return null;
   }

   public net.runelite.api.GrandExchangeOffer[] getGrandExchangeOffers() {
      return new net.runelite.api.GrandExchangeOffer[0];
   }

   public boolean isPrayerActive(Prayer var1) {
      return false;
   }

   public int getSkillExperience(Skill var1) {
      return 0;
   }

   public long getOverallExperience() {
      return 0L;
   }

   public int getGameDrawingMode() {
      return 0;
   }

   public void setGameDrawingMode(int var1) {
   }

   public void refreshChat() {
   }

   public Map getChatLineMap() {
      return null;
   }

   public IterableHashTable getMessages() {
      return null;
   }

   public net.runelite.api.ObjectComposition getObjectDefinition(int var1) {
      return null;
   }

   public net.runelite.api.NPCComposition getNpcDefinition(int var1) {
      return null;
   }

   public net.runelite.api.StructComposition getStructComposition(int var1) {
      return null;
   }

   public NodeCache getStructCompositionCache() {
      return null;
   }

   public MapElementConfig[] getMapElementConfigs() {
      return new MapElementConfig[0];
   }

   public net.runelite.api.IndexedSprite[] getMapScene() {
      return new net.runelite.api.IndexedSprite[0];
   }

   public net.runelite.api.SpritePixels[] getMapDots() {
      return new net.runelite.api.SpritePixels[0];
   }

   public int getGameCycle() {
      return 0;
   }

   public net.runelite.api.SpritePixels[] getMapIcons() {
      return new net.runelite.api.SpritePixels[0];
   }

   public net.runelite.api.IndexedSprite[] getModIcons() {
      return new net.runelite.api.IndexedSprite[0];
   }

   public void setModIcons(net.runelite.api.IndexedSprite[] var1) {
   }

   public net.runelite.api.IndexedSprite createIndexedSprite() {
      return null;
   }

   public net.runelite.api.SpritePixels createSpritePixels(int[] var1, int var2, int var3) {
      return null;
   }

   public LocalPoint getLocalDestinationLocation() {
      return null;
   }

   public List getProjectiles() {
      return null;
   }

   public List getGraphicsObjects() {
      return null;
   }

   public int getMusicVolume() {
      return 0;
   }

   public void setMusicVolume(int var1) {
   }

   public void playSoundEffect(int var1) {
   }

   public void playSoundEffect(int var1, int var2, int var3, int var4) {
   }

   public void playSoundEffect(int var1, int var2, int var3, int var4, int var5) {
   }

   public void playSoundEffect(int var1, int var2) {
   }

   public BufferProvider getBufferProvider() {
      return null;
   }

   public int getMouseIdleTicks() {
      return 0;
   }

   public long getMouseLastPressedMillis() {
      return 0L;
   }

   public void setMouseLastPressedMillis(long var1) {
   }

   public long getClientMouseLastPressedMillis() {
      return 0L;
   }

   public void setClientMouseLastPressedMillis(long var1) {
   }

   public int getKeyboardIdleTicks() {
      return 0;
   }

   public boolean[] getPressedKeys() {
      return new boolean[0];
   }

   public void changeMemoryMode(boolean var1) {
   }

   public net.runelite.api.ItemContainer getItemContainer(InventoryID var1) {
      return null;
   }

   public int getIntStackSize() {
      return 0;
   }

   public void setIntStackSize(int var1) {
   }

   public int[] getIntStack() {
      return new int[0];
   }

   public int getStringStackSize() {
      return 0;
   }

   public void setStringStackSize(int var1) {
   }

   public String[] getStringStack() {
      return new String[0];
   }

   public net.runelite.api.widgets.Widget getScriptActiveWidget() {
      return null;
   }

   public net.runelite.api.widgets.Widget getScriptDotWidget() {
      return null;
   }

   public boolean isFriended(String var1, boolean var2) {
      return false;
   }

   public FriendsChatManager getFriendsChatManager() {
      return null;
   }

   public NameableContainer getFriendContainer() {
      return null;
   }

   public NameableContainer getIgnoreContainer() {
      return null;
   }

   public Preferences getPreferences() {
      return null;
   }

   public void setCameraPitchRelaxerEnabled(boolean var1) {
   }

   public void setInvertYaw(boolean var1) {
   }

   public void setInvertPitch(boolean var1) {
   }

   public RenderOverview getRenderOverview() {
      return null;
   }

   public boolean isStretchedEnabled() {
      return false;
   }

   public void setStretchedEnabled(boolean var1) {
   }

   public boolean isStretchedFast() {
      return false;
   }

   public void setStretchedFast(boolean var1) {
   }

   public void setStretchedIntegerScaling(boolean var1) {
   }

   public void setStretchedKeepAspectRatio(boolean var1) {
   }

   public void setScalingFactor(int var1) {
   }

   public double getScalingFactor() {
      return 0.0D;
   }

   public void invalidateStretching(boolean var1) {
   }

   public Dimension getStretchedDimensions() {
      return null;
   }

   public Dimension getRealDimensions() {
      return null;
   }

   public void changeWorld(net.runelite.api.World var1) {
   }

   public net.runelite.api.World createWorld() {
      return null;
   }

   public net.runelite.api.SpritePixels drawInstanceMap(int var1) {
      return null;
   }

   public void setMinimapReceivesClicks(boolean var1) {
   }

   public void runScript(Object... var1) {
   }

   public net.runelite.api.ScriptEvent createScriptEvent(Object... var1) {
      return null;
   }

   public boolean hasHintArrow() {
      return false;
   }

   public HintArrowType getHintArrowType() {
      return null;
   }

   public void clearHintArrow() {
   }

   public void setHintArrow(WorldPoint var1) {
   }

   public void setHintArrow(net.runelite.api.Player var1) {
   }

   public void setHintArrow(net.runelite.api.NPC var1) {
   }

   public WorldPoint getHintArrowPoint() {
      return null;
   }

   public net.runelite.api.Player getHintArrowPlayer() {
      return null;
   }

   public net.runelite.api.NPC getHintArrowNpc() {
      return null;
   }

   public boolean isInterpolatePlayerAnimations() {
      return false;
   }

   public void setInterpolatePlayerAnimations(boolean var1) {
   }

   public boolean isInterpolateNpcAnimations() {
      return false;
   }

   public void setInterpolateNpcAnimations(boolean var1) {
   }

   public boolean isInterpolateObjectAnimations() {
      return false;
   }

   public void setInterpolateObjectAnimations(boolean var1) {
   }

   public boolean isInterpolateWidgetAnimations() {
      return false;
   }

   public void setInterpolateWidgetAnimations(boolean var1) {
   }

   public boolean isInInstancedRegion() {
      return false;
   }

   public int getItemPressedDuration() {
      return 0;
   }

   public void setIsHidingEntities(boolean var1) {
   }

   public void setOthersHidden(boolean var1) {
   }

   public void setOthersHidden2D(boolean var1) {
   }

   public void setFriendsHidden(boolean var1) {
   }

   public void setFriendsChatMembersHidden(boolean var1) {
   }

   public void setIgnoresHidden(boolean var1) {
   }

   public void setLocalPlayerHidden(boolean var1) {
   }

   public void setLocalPlayerHidden2D(boolean var1) {
   }

   public void setNPCsHidden(boolean var1) {
   }

   public void setNPCsHidden2D(boolean var1) {
   }

   public void setPetsHidden(boolean var1) {
   }

   public void setAttackersHidden(boolean var1) {
   }

   public void setHideSpecificPlayers(List var1) {
   }

   public List getHiddenNpcIndices() {
      return null;
   }

   public void setHiddenNpcIndices(List var1) {
   }

   public void addHiddenNpcName(String var1) {
   }

   public void removeHiddenNpcName(String var1) {
   }

   public void setProjectilesHidden(boolean var1) {
   }

   public void setDeadNPCsHidden(boolean var1) {
   }

   public void setBlacklistDeadNpcs(Set var1) {
   }

   public CollisionData[] getCollisionMaps() {
      return new CollisionData[0];
   }

   public int[] getBoostedSkillLevels() {
      return new int[0];
   }

   public int[] getRealSkillLevels() {
      return new int[0];
   }

   public int[] getSkillExperiences() {
      return new int[0];
   }

   public void queueChangedSkill(Skill var1) {
   }

   public Map getSpriteOverrides() {
      return null;
   }

   public Map getWidgetSpriteOverrides() {
      return null;
   }

   public void setCompass(net.runelite.api.SpritePixels var1) {
   }

   public NodeCache getWidgetSpriteCache() {
      return null;
   }

   public int getTickCount() {
      return 0;
   }

   public void setTickCount(int var1) {
   }

   public void setInventoryDragDelay(int var1) {
   }

   public boolean isHdMinimapEnabled() {
      return false;
   }

   public void setHdMinimapEnabled(boolean var1) {
   }

   public EnumSet getWorldType() {
      return null;
   }

   public int getOculusOrbState() {
      return 0;
   }

   public void setOculusOrbState(int var1) {
   }

   public void setOculusOrbNormalSpeed(int var1) {
   }

   public int getOculusOrbFocalPointX() {
      return 0;
   }

   public int getOculusOrbFocalPointY() {
      return 0;
   }

   public void openWorldHopper() {
   }

   public void hopToWorld(net.runelite.api.World var1) {
   }

   public void setSkyboxColor(int var1) {
   }

   public int getSkyboxColor() {
      return 0;
   }

   public boolean isGpu() {
      return false;
   }

   public void setGpu(boolean var1) {
   }

   public int get3dZoom() {
      return 0;
   }

   public int getCenterX() {
      return 0;
   }

   public int getCenterY() {
      return 0;
   }

   public int getCameraX2() {
      return 0;
   }

   public int getCameraY2() {
      return 0;
   }

   public int getCameraZ2() {
      return 0;
   }

   public net.runelite.api.TextureProvider getTextureProvider() {
      return null;
   }

   public NodeCache getCachedModels2() {
      return null;
   }

   public void setRenderArea(boolean[][] var1) {
   }

   public int getRasterizer3D_clipMidX2() {
      return 0;
   }

   public int getRasterizer3D_clipNegativeMidX() {
      return 0;
   }

   public int getRasterizer3D_clipNegativeMidY() {
      return 0;
   }

   public int getRasterizer3D_clipMidY2() {
      return 0;
   }

   public void checkClickbox(net.runelite.api.Model var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, long var10) {
   }

   public net.runelite.api.widgets.Widget getIf1DraggedWidget() {
      return null;
   }

   public int getIf1DraggedItemIndex() {
      return 0;
   }

   public void setSpellSelected(boolean var1) {
   }

   public NodeCache getItemCompositionCache() {
      return null;
   }

   public net.runelite.api.SpritePixels[] getCrossSprites() {
      return new net.runelite.api.SpritePixels[0];
   }

   public net.runelite.api.EnumComposition getEnum(int var1) {
      return null;
   }

   public void draw2010Menu(int var1) {
   }

   public int[] getGraphicsPixels() {
      return new int[0];
   }

   public void drawOriginalMenu(int var1) {
   }

   public void resetHealthBarCaches() {
   }

   public boolean getRenderSelf() {
      return false;
   }

   public void setRenderSelf(boolean var1) {
   }

   public void invokeMenuAction(String var1, String var2, int var3, int var4, int var5, int var6) {
   }

   public net.runelite.api.MouseRecorder getMouseRecorder() {
      return null;
   }

   public void setPrintMenuActions(boolean var1) {
   }

   public String getSelectedSpellName() {
      return null;
   }

   public void setSelectedSpellName(String var1) {
   }

   public boolean getSpellSelected() {
      return false;
   }

   public String getSelectedSpellActionName() {
      return null;
   }

   public int getSelectedSpellFlags() {
      return 0;
   }

   public void setHideFriendAttackOptions(boolean var1) {
   }

   public void setHideFriendCastOptions(boolean var1) {
   }

   public void setHideClanmateAttackOptions(boolean var1) {
   }

   public void setHideClanmateCastOptions(boolean var1) {
   }

   public void setUnhiddenCasts(Set var1) {
   }

   public void addFriend(String var1) {
   }

   public void removeFriend(String var1) {
   }

   public void setModulus(BigInteger var1) {
   }

   public int getItemCount() {
      return 0;
   }

   public void setAllWidgetsAreOpTargetable(boolean var1) {
   }

   public void insertMenuItem(String var1, String var2, int var3, int var4, int var5, int var6, boolean var7) {
   }

   public void setSelectedItemID(int var1) {
   }

   public int getSelectedItemWidget() {
      return 0;
   }

   public void setSelectedItemWidget(int var1) {
   }

   public int getSelectedItemSlot() {
      return 0;
   }

   public void setSelectedItemSlot(int var1) {
   }

   public int getSelectedSpellWidget() {
      return 0;
   }

   public int getSelectedSpellChildIndex() {
      return 0;
   }

   public void setSelectedSpellWidget(int var1) {
   }

   public void setSelectedSpellChildIndex(int var1) {
   }

   public void scaleSprite(int[] var1, int[] var2, int var3, int var4, int var5, int var6, int var7, int var8, int var9, int var10, int var11, int var12) {
   }

   public MenuEntry getLeftClickMenuEntry() {
      return null;
   }

   public void setLeftClickMenuEntry(MenuEntry var1) {
   }

   public void setHideDisconnect(boolean var1) {
   }

   public void setTempMenuEntry(MenuEntry var1) {
   }

   public void setShowMouseCross(boolean var1) {
   }

   public void setMouseIdleTicks(int var1) {
   }

   public void setKeyboardIdleTicks(int var1) {
   }

   public void setGeSearchResultCount(int var1) {
   }

   public void setGeSearchResultIds(short[] var1) {
   }

   public void setGeSearchResultIndex(int var1) {
   }

   public void setComplianceValue(String var1, boolean var2) {
   }

   public boolean getComplianceValue(String var1) {
      return false;
   }

   public boolean isMirrored() {
      return false;
   }

   public void setMirrored(boolean var1) {
   }

   public boolean isComparingAppearance() {
      return false;
   }

   public void setComparingAppearance(boolean var1) {
   }

   public void setLoginScreen(net.runelite.api.SpritePixels var1) {
   }

   public void setShouldRenderLoginScreenFire(boolean var1) {
   }

   public boolean shouldRenderLoginScreenFire() {
      return false;
   }

   public boolean isKeyPressed(int var1) {
      return false;
   }

   public int getFollowerIndex() {
      return 0;
   }

   public int isItemSelected() {
      return 0;
   }

   public String getSelectedItemName() {
      return null;
   }

   public net.runelite.api.widgets.Widget getMessageContinueWidget() {
      return null;
   }

   public void setOutdatedScript(String var1) {
   }

   public List getOutdatedScripts() {
      return null;
   }

   public net.runelite.api.Frames getFrames(int var1) {
      return null;
   }

   public net.runelite.api.SequenceDefinition getSequenceDefinition(int var1) {
      return null;
   }

   public IndexDataBase getSequenceDefinition_skeletonsArchive() {
      return null;
   }

   public IndexDataBase getSequenceDefinition_archive() {
      return null;
   }

   public IndexDataBase getSequenceDefinition_animationsArchive() {
      return null;
   }

   public IndexDataBase getNpcDefinition_archive() {
      return null;
   }

   public IndexDataBase getObjectDefinition_modelsArchive() {
      return null;
   }

   public IndexDataBase getObjectDefinition_archive() {
      return null;
   }

   public IndexDataBase getItemDefinition_archive() {
      return null;
   }

   public IndexDataBase getKitDefinition_archive() {
      return null;
   }

   public IndexDataBase getKitDefinition_modelsArchive() {
      return null;
   }

   public IndexDataBase getSpotAnimationDefinition_archive() {
      return null;
   }

   public IndexDataBase getSpotAnimationDefinition_modelArchive() {
      return null;
   }

   public net.runelite.api.Buffer createBuffer(byte[] var1) {
      return null;
   }

   public long[] getCrossWorldMessageIds() {
      return new long[0];
   }

   public int getCrossWorldMessageIdsIndex() {
      return 0;
   }

   public net.runelite.api.clan.ClanChannel getClanChannel() {
      return null;
   }

   public net.runelite.api.clan.ClanChannel getGuestClanChannel() {
      return null;
   }

   public net.runelite.api.clan.ClanSettings getClanSettings() {
      return null;
   }

   public net.runelite.api.clan.ClanSettings getGuestClanSettings() {
      return null;
   }

   public int api$getRSGameState() {
      return gameState;
   }
}
