package net.runelite.mixins;

import net.runelite.api.BufferProvider;
import net.runelite.api.ChatLineBuffer;
import net.runelite.api.ChatMessageType;
import net.runelite.api.CollisionData;
import net.runelite.api.EnumComposition;
import net.runelite.api.Friend;
import net.runelite.api.FriendsChatManager;
import net.runelite.api.GameState;
import net.runelite.api.HashTable;
import net.runelite.api.HintArrowType;
import net.runelite.api.Ignore;
import net.runelite.api.IndexDataBase;
import net.runelite.api.IndexedSprite;
import net.runelite.api.InventoryID;
import net.runelite.api.ItemContainer;
import net.runelite.api.IterableHashTable;
import net.runelite.api.MapElementConfig;
import net.runelite.api.MenuEntry;
import net.runelite.api.MessageNode;
import net.runelite.api.Model;
import net.runelite.api.NameableContainer;
import net.runelite.api.NodeCache;
import net.runelite.api.Player;
import net.runelite.api.Point;
import net.runelite.api.Prayer;
import net.runelite.api.Preferences;
import net.runelite.api.Projectile;
import net.runelite.api.RenderOverview;
import net.runelite.api.SequenceDefinition;
import net.runelite.api.Skill;
import net.runelite.api.TextureProvider;
import net.runelite.api.VarClientInt;
import net.runelite.api.VarClientStr;
import net.runelite.api.VarPlayer;
import net.runelite.api.Varbits;
import net.runelite.api.WidgetNode;
import net.runelite.api.WorldType;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.hooks.Callbacks;
import net.runelite.api.hooks.DrawCallbacks;
import net.runelite.api.mixins.FieldHook;
import net.runelite.api.mixins.Inject;
import net.runelite.api.mixins.Mixin;
import net.runelite.api.mixins.Shadow;
import net.runelite.api.vars.AccountType;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.rs.api.RSClient;
import org.sponge.util.Logger;

import java.awt.*;
import java.math.BigInteger;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Mixin(RSClient.class)
public abstract class ClientMixin implements RSClient{

    @Shadow("client")
    public static RSClient client;

    @Inject
    private static RSClient clientInstance;

    @Inject
    @javax.inject.Inject
    private Callbacks callbacks;

    @Inject
    @Override
    public Callbacks getCallbacks()
    {
        return callbacks;
    }

    @Inject
    private DrawCallbacks drawCallbacks;

    @Inject
    public static Logger logger = new Logger("injected-client");

    @Inject
    @Override
    public DrawCallbacks getDrawCallbacks()
    {
        return drawCallbacks;
    }

    @Inject
    @Override
    public void setDrawCallbacks(DrawCallbacks drawCallbacks)
    {
        this.drawCallbacks = drawCallbacks;
    }

    @Inject
    @FieldHook("gameState")
    public static void onGameStateChanged(int idx)
    {
        clientInstance = client;
        GameStateChanged gameStateChanged = new GameStateChanged();
        gameStateChanged.setGameState(GameState.of(client.api$getRSGameState()));
        client.getCallbacks().post(gameStateChanged);
    }

    @Inject
    @Override
    public Logger getLogger() {
        return logger;
    }

    @Inject
    @Override
    public String getBuildID() {
        return null;
    }

    @Inject
    @Override
    public List<Player> getPlayers() {
        return null;
    }

    @Inject
    @Override
    public List<net.runelite.api.NPC> getNpcs() {
        return null;
    }

    @Inject
    @Override
    public net.runelite.api.NPC[] getCachedNPCs() {
        return new net.runelite.api.NPC[0];
    }

    @Inject
    @Override
    public net.runelite.api.Player[] getCachedPlayers() {
        return new net.runelite.api.Player[0];
    }

    @Inject
    @Override
    public int getBoostedSkillLevel(Skill skill) {
        return 0;
    }

    @Inject
    @Override
    public int getRealSkillLevel(Skill skill) {
        return 0;
    }

    @Inject
    @Override
    public int getTotalLevel() {
        return 0;
    }

    @Inject
    @Override
    public MessageNode addChatMessage(ChatMessageType type, String name, String message, String sender) {
        return null;
    }

    @Inject
    @Override
    public MessageNode addChatMessage(ChatMessageType type, String name, String message, String sender, boolean postEvent) {
        return null;
    }

    @Inject
    @Override
    public GameState getGameState() {
        return null;
    }

    @Inject
    @Override
    public void setGameState(GameState gameState) {

    }

    @Inject
    @Override
    public void setGameState(int gameState) {

    }

    @Inject
    @Override
    public void stopNow() {

    }

    @Inject
    @Override
    public String getUsername() {
        return null;
    }

    @Inject
    @Override
    public void setUsername(String name) {

    }

    @Inject
    @Override
    public void setPassword(String password) {

    }

    @Inject
    @Override
    public void setOtp(String otp) {

    }

    @Inject
    @Override
    public int getCurrentLoginField() {
        return 0;
    }

    @Inject
    @Override
    public int getLoginIndex() {
        return 0;
    }

    @Inject
    @Override
    public AccountType getAccountType() {
        return null;
    }

    @Inject
    @Override
    public java.awt.Canvas getCanvas() {
        return null;
    }

    @Inject
    @Override
    public Thread getClientThread() {
        return null;
    }

    @Inject
    @Override
    public boolean isClientThread() {
        return false;
    }

    @Inject
    @Override
    public void setReplaceCanvasNextFrame(boolean replace) {

    }

    @Inject
    @Override
    public int getFPS() {
        return 0;
    }

    @Inject
    @Override
    public int api$getCameraX() {
        return 0;
    }

    @Inject
    @Override
    public int api$getCameraY() {
        return 0;
    }

    @Inject
    @Override
    public int api$getCameraZ() {
        return 0;
    }

    @Inject
    @Override
    public int getCameraPitch() {
        return 0;
    }

    @Inject
    @Override
    public int getCameraYaw() {
        return 0;
    }

    @Inject
    @Override
    public int getWorld() {
        return 0;
    }

    @Inject
    @Override
    public int getCanvasHeight() {
        return 0;
    }

    @Inject
    @Override
    public int getCanvasWidth() {
        return 0;
    }

    @Inject
    @Override
    public int getViewportHeight() {
        return 0;
    }

    @Inject
    @Override
    public int getViewportWidth() {
        return 0;
    }

    @Inject
    @Override
    public int getViewportXOffset() {
        return 0;
    }

    @Inject
    @Override
    public int getViewportYOffset() {
        return 0;
    }

    @Inject
    @Override
    public int getScale() {
        return 0;
    }

    @Inject
    @Override
    public Point getMouseCanvasPosition() {
        return null;
    }

    @Inject
    @Override
    public int[][][] getTileHeights() {
        return new int[0][][];
    }

    @Inject
    @Override
    public byte[][][] getTileSettings() {
        return new byte[0][][];
    }

    @Inject
    @Override
    public int getPlane() {
        return 0;
    }

    @Inject
    @Override
    public net.runelite.api.Scene getScene() {
        return null;
    }


    @Inject
    @Override
    public net.runelite.api.Player getLocalPlayer() {
        return null;
    }

    @Inject
    @Override
    public int getLocalPlayerIndex() {
        return 0;
    }


    @Inject
    @Override
    public net.runelite.api.ItemComposition getItemComposition(int id) {
        return null;
    }


    @Inject
    @Override
    public net.runelite.api.ItemComposition getItemDefinition(int id) {
        return null;
    }


    @Inject
    @Override
    public net.runelite.api.SpritePixels createItemSprite(int itemId, int quantity, int border, int shadowColor, int stackable, boolean noted, int scale) {
        return null;
    }


    @Inject
    @Override
    public net.runelite.api.SpritePixels[] getSprites(IndexDataBase source, int archiveId, int fileId) {
        return new net.runelite.api.SpritePixels[0];
    }

    @Inject
    @Override
    public IndexDataBase getIndexSprites() {
        return null;
    }

    @Inject
    @Override
    public IndexDataBase getIndexScripts() {
        return null;
    }

    @Inject
    @Override
    public IndexDataBase getIndexConfig() {
        return null;
    }

    @Inject
    @Override
    public int getBaseX() {
        return 0;
    }

    @Inject
    @Override
    public int getBaseY() {
        return 0;
    }

    @Inject
    @Override
    public int getMouseCurrentButton() {
        return 0;
    }


    @Inject
    @Override
    public net.runelite.api.Tile getSelectedSceneTile() {
        return null;
    }

    @Inject
    @Override
    public boolean isDraggingWidget() {
        return false;
    }


    @Inject
    @Override
    public net.runelite.api.widgets.Widget getDraggedWidget() {
        return null;
    }


    @Inject
    @Override
    public net.runelite.api.widgets.Widget getDraggedOnWidget() {
        return null;
    }

    @Inject
    @Override
    public void setDraggedOnWidget(net.runelite.api.widgets.Widget widget) {

    }

    @Inject
    @Override
    public int getTopLevelInterfaceId() {
        return 0;
    }

    @Inject
    @Override
    public net.runelite.api.widgets.Widget[] getWidgetRoots() {
        return new net.runelite.api.widgets.Widget[0];
    }


    @Inject
    @Override
    public net.runelite.api.widgets.Widget getWidget(WidgetInfo widget) {
        return null;
    }


    @Inject
    @Override
    public net.runelite.api.widgets.Widget getWidget(int groupId, int childId) {
        return null;
    }


    @Inject
    @Override
    public net.runelite.api.widgets.Widget getWidget(int packedID) {
        return null;
    }

    @Inject
    @Override
    public int[] getWidgetPositionsX() {
        return new int[0];
    }

    @Inject
    @Override
    public int[] getWidgetPositionsY() {
        return new int[0];
    }

    @Inject
    @Override
    public net.runelite.api.widgets.Widget createWidget() {
        return null;
    }

    @Inject
    @Override
    public int getEnergy() {
        return 0;
    }

    @Inject
    @Override
    public int getWeight() {
        return 0;
    }

    @Inject
    @Override
    public String[] getPlayerOptions() {
        return new String[0];
    }

    @Inject
    @Override
    public boolean[] getPlayerOptionsPriorities() {
        return new boolean[0];
    }

    @Inject
    @Override
    public int[] getPlayerMenuTypes() {
        return new int[0];
    }

    @Inject
    @Override
    public net.runelite.api.World[] getWorldList() {
        return new net.runelite.api.World[0];
    }

    @Inject
    @Override
    public MenuEntry[] getMenuEntries() {
        return new MenuEntry[0];
    }

    @Inject
    @Override
    public int getMenuOptionCount() {
        return 0;
    }

    @Inject
    @Override
    public void setMenuEntries(MenuEntry[] entries) {

    }

    @Inject
    @Override
    public void setMenuOptionCount(int count) {

    }

    @Inject
    @Override
    public boolean isMenuOpen() {
        return false;
    }

    @Inject
    @Override
    public int getMapAngle() {
        return 0;
    }

    @Inject
    @Override
    public boolean isResized() {
        return false;
    }

    @Inject
    @Override
    public int getRevision() {
        return 0;
    }

    @Inject
    @Override
    public int[] getMapRegions() {
        return new int[0];
    }

    @Inject
    @Override
    public int[][][] getInstanceTemplateChunks() {
        return new int[0][][];
    }

    @Inject
    @Override
    public int[][] getXteaKeys() {
        return new int[0][];
    }

    @Inject
    @Override
    public int[] getVarps() {
        return new int[0];
    }

    @Inject
    @Override
    public Map<Integer, Object> getVarcMap() {
        return null;
    }

    @Inject
    @Override
    public int getVar(VarPlayer varPlayer) {
        return 0;
    }

    @Inject
    @Override
    public int getVar(Varbits varbit) {
        return 0;
    }

    @Inject
    @Override
    public int getVar(VarClientInt varClientInt) {
        return 0;
    }

    @Inject
    @Override
    public String getVar(VarClientStr varClientStr) {
        return null;
    }

    @Inject
    @Override
    public int getVarbitValue(int varbitId) {
        return 0;
    }

    @Inject
    @Override
    public int getVarcIntValue(int varcIntId) {
        return 0;
    }

    @Inject
    @Override
    public String getVarcStrValue(int varcStrId) {
        return null;
    }

    @Inject
    @Override
    public void setVar(VarClientStr varClientStr, String value) {

    }

    @Inject
    @Override
    public void setVar(VarClientInt varClientStr, int value) {

    }

    @Inject
    @Override
    public void setVarbit(Varbits varbit, int value) {

    }


    @Inject
    @Override
    public net.runelite.api.VarbitComposition getVarbit(int id) {
        return null;
    }

    @Inject
    @Override
    public int getVarbitValue(int[] varps, int varbitId) {
        return 0;
    }

    @Inject
    @Override
    public int getVarpValue(int[] varps, int varpId) {
        return 0;
    }

    @Inject
    @Override
    public int getVarpValue(int i) {
        return 0;
    }

    @Inject
    @Override
    public void setVarbitValue(int[] varps, int varbit, int value) {

    }

    @Inject
    @Override
    public void queueChangedVarp(int varp) {

    }

    @Inject
    @Override
    public HashTable getWidgetFlags() {
        return null;
    }

    @Inject
    @Override
    public HashTable<WidgetNode> getComponentTable() {
        return null;
    }

    @Inject
    @Override
    public net.runelite.api.GrandExchangeOffer[] getGrandExchangeOffers() {
        return new net.runelite.api.GrandExchangeOffer[0];
    }

    @Inject
    @Override
    public boolean isPrayerActive(Prayer prayer) {
        return false;
    }

    @Inject
    @Override
    public int getSkillExperience(Skill skill) {
        return 0;
    }

    @Inject
    @Override
    public long getOverallExperience() {
        return 0;
    }

    @Inject
    @Override
    public int getGameDrawingMode() {
        return 0;
    }

    @Inject
    @Override
    public void setGameDrawingMode(int gameDrawingMode) {

    }

    @Inject
    @Override
    public void refreshChat() {

    }

    @Inject
    @Override
    public Map<Integer, ChatLineBuffer> getChatLineMap() {
        return null;
    }

    @Inject
    @Override
    public IterableHashTable<MessageNode> getMessages() {
        return null;
    }

    @Inject
    @Override
    public net.runelite.api.ObjectComposition getObjectDefinition(int objectId) {
        return null;
    }

    @Inject
    @Override
    public net.runelite.api.NPCComposition getNpcDefinition(int npcId) {
        return null;
    }

    @Inject
    @Override
    public net.runelite.api.StructComposition getStructComposition(int structID) {
        return null;
    }

    @Inject
    @Override
    public NodeCache getStructCompositionCache() {
        return null;
    }

    @Inject
    @Override
    public MapElementConfig[] getMapElementConfigs() {
        return new MapElementConfig[0];
    }

    @Inject
    @Override
    public IndexedSprite[] getMapScene() {
        return new IndexedSprite[0];
    }

    @Inject
    @Override
    public net.runelite.api.SpritePixels[] getMapDots() {
        return new net.runelite.api.SpritePixels[0];
    }

    @Inject
    @Override
    public int getGameCycle() {
        return 0;
    }

    @Inject
    @Override
    public net.runelite.api.SpritePixels[] getMapIcons() {
        return new net.runelite.api.SpritePixels[0];
    }

    @Inject
    @Override
    public IndexedSprite[] getModIcons() {
        return new IndexedSprite[0];
    }

    @Inject
    @Override
    public void setModIcons(IndexedSprite[] modIcons) {

    }

    @Inject
    @Override
    public IndexedSprite createIndexedSprite() {
        return null;
    }

    @Inject
    @Override
    public net.runelite.api.SpritePixels createSpritePixels(int[] pixels, int width, int height) {
        return null;
    }


    @Inject
    @Override
    public LocalPoint getLocalDestinationLocation() {
        return null;
    }

    @Inject
    @Override
    public List<Projectile> getProjectiles() {
        return null;
    }

    @Inject
    @Override
    public List<net.runelite.api.GraphicsObject> getGraphicsObjects() {
        return null;
    }

    @Inject
    @Override
    public int getMusicVolume() {
        return 0;
    }

    @Inject
    @Override
    public void setMusicVolume(int volume) {

    }

    @Inject
    @Override
    public void playSoundEffect(int id) {

    }

    @Inject
    @Override
    public void playSoundEffect(int id, int x, int y, int range) {

    }

    @Inject
    @Override
    public void playSoundEffect(int id, int x, int y, int range, int delay) {

    }

    @Inject
    @Override
    public void playSoundEffect(int id, int volume) {

    }

    @Inject
    @Override
    public BufferProvider getBufferProvider() {
        return null;
    }

    @Inject
    @Override
    public int getMouseIdleTicks() {
        return 0;
    }

    @Inject
    @Override
    public long getMouseLastPressedMillis() {
        return 0;
    }

    @Inject
    @Override
    public void setMouseLastPressedMillis(long time) {

    }

    @Inject
    @Override
    public long getClientMouseLastPressedMillis() {
        return 0;
    }

    @Inject
    @Override
    public void setClientMouseLastPressedMillis(long time) {

    }

    @Inject
    @Override
    public int getKeyboardIdleTicks() {
        return 0;
    }

    @Inject
    @Override
    public boolean[] getPressedKeys() {
        return new boolean[0];
    }

    @Inject
    @Override
    public void changeMemoryMode(boolean lowMemory) {

    }


    @Inject
    @Override
    public ItemContainer getItemContainer(InventoryID inventory) {
        return null;
    }

    @Inject
    @Override
    public int getIntStackSize() {
        return 0;
    }

    @Inject
    @Override
    public void setIntStackSize(int stackSize) {

    }

    @Inject
    @Override
    public int[] getIntStack() {
        return new int[0];
    }

    @Inject
    @Override
    public int getStringStackSize() {
        return 0;
    }

    @Inject
    @Override
    public void setStringStackSize(int stackSize) {

    }

    @Inject
    @Override
    public String[] getStringStack() {
        return new String[0];
    }

    @Inject
    @Override
    public net.runelite.api.widgets.Widget getScriptActiveWidget() {
        return null;
    }

    @Inject
    @Override
    public net.runelite.api.widgets.Widget getScriptDotWidget() {
        return null;
    }

    @Inject
    @Override
    public boolean isFriended(String name, boolean mustBeLoggedIn) {
        return false;
    }


    @Inject
    @Override
    public FriendsChatManager getFriendsChatManager() {
        return null;
    }

    @Inject
    @Override
    public NameableContainer<Friend> getFriendContainer() {
        return null;
    }

    @Inject
    @Override
    public NameableContainer<Ignore> getIgnoreContainer() {
        return null;
    }

    @Inject
    @Override
    public Preferences getPreferences() {
        return null;
    }

    @Inject
    @Override
    public void setCameraPitchRelaxerEnabled(boolean enabled) {

    }

    @Inject
    @Override
    public void setInvertYaw(boolean invertYaw) {

    }

    @Inject
    @Override
    public void setInvertPitch(boolean invertPitch) {

    }

    @Inject
    @Override
    public RenderOverview getRenderOverview() {
        return null;
    }

    @Inject
    @Override
    public boolean isStretchedEnabled() {
        return false;
    }

    @Inject
    @Override
    public void setStretchedEnabled(boolean state) {

    }

    @Inject
    @Override
    public boolean isStretchedFast() {
        return false;
    }

    @Inject
    @Override
    public void setStretchedFast(boolean state) {

    }

    @Inject
    @Override
    public void setStretchedIntegerScaling(boolean state) {

    }

    @Inject
    @Override
    public void setStretchedKeepAspectRatio(boolean state) {

    }

    @Inject
    @Override
    public void setScalingFactor(int factor) {

    }

    @Inject
    @Override
    public double getScalingFactor() {
        return 0;
    }

    @Inject
    @Override
    public void invalidateStretching(boolean resize) {

    }

    @Inject
    @Override
    public Dimension getStretchedDimensions() {
        return null;
    }

    @Inject
    @Override
    public Dimension getRealDimensions() {
        return null;
    }

    @Inject
    @Override
    public void changeWorld(net.runelite.api.World world) {

    }

    @Inject
    @Override
    public net.runelite.api.World createWorld() {
        return null;
    }

    @Inject
    @Override
    public net.runelite.api.SpritePixels drawInstanceMap(int z) {
        return null;
    }

    @Inject
    @Override
    public void setMinimapReceivesClicks(boolean minimapReceivesClicks) {

    }

    @Inject
    @Override
    public void runScript(Object... args) {

    }

    @Inject
    @Override
    public net.runelite.api.ScriptEvent createScriptEvent(Object... args) {
        return null;
    }

    @Inject
    @Override
    public boolean hasHintArrow() {
        return false;
    }

    @Inject
    @Override
    public HintArrowType getHintArrowType() {
        return null;
    }

    @Inject
    @Override
    public void clearHintArrow() {

    }

    @Inject
    @Override
    public void setHintArrow(WorldPoint point) {

    }

    @Inject
    @Override
    public void setHintArrow(net.runelite.api.Player player) {

    }

    @Inject
    @Override
    public void setHintArrow(net.runelite.api.NPC npc) {

    }

    @Inject
    @Override
    public WorldPoint getHintArrowPoint() {
        return null;
    }

    @Inject
    @Override
    public net.runelite.api.Player getHintArrowPlayer() {
        return null;
    }

    @Inject
    @Override
    public net.runelite.api.NPC getHintArrowNpc() {
        return null;
    }

    @Inject
    @Override
    public boolean isInterpolatePlayerAnimations() {
        return false;
    }

    @Inject
    @Override
    public void setInterpolatePlayerAnimations(boolean interpolate) {

    }

    @Inject
    @Override
    public boolean isInterpolateNpcAnimations() {
        return false;
    }

    @Inject
    @Override
    public void setInterpolateNpcAnimations(boolean interpolate) {

    }

    @Inject
    @Override
    public boolean isInterpolateObjectAnimations() {
        return false;
    }

    @Inject
    @Override
    public void setInterpolateObjectAnimations(boolean interpolate) {

    }

    @Inject
    @Override
    public boolean isInterpolateWidgetAnimations() {
        return false;
    }

    @Inject
    @Override
    public void setInterpolateWidgetAnimations(boolean interpolate) {

    }

    @Inject
    @Override
    public boolean isInInstancedRegion() {
        return false;
    }

    @Inject
    @Override
    public int getItemPressedDuration() {
        return 0;
    }

    @Inject
    @Override
    public void setIsHidingEntities(boolean state) {

    }

    @Inject
    @Override
    public void setOthersHidden(boolean state) {

    }

    @Inject
    @Override
    public void setOthersHidden2D(boolean state) {

    }

    @Inject
    @Override
    public void setFriendsHidden(boolean state) {

    }

    @Inject
    @Override
    public void setFriendsChatMembersHidden(boolean state) {

    }

    @Inject
    @Override
    public void setIgnoresHidden(boolean state) {

    }

    @Inject
    @Override
    public void setLocalPlayerHidden(boolean state) {

    }

    @Inject
    @Override
    public void setLocalPlayerHidden2D(boolean state) {

    }

    @Inject
    @Override
    public void setNPCsHidden(boolean state) {

    }

    @Inject
    @Override
    public void setNPCsHidden2D(boolean state) {

    }

    @Inject
    @Override
    public void setPetsHidden(boolean state) {

    }

    @Inject
    @Override
    public void setAttackersHidden(boolean state) {

    }

    @Inject
    @Override
    public void setHideSpecificPlayers(List<String> names) {

    }

    @Inject
    @Override
    public List<Integer> getHiddenNpcIndices() {
        return null;
    }

    @Inject
    @Override
    public void setHiddenNpcIndices(List<Integer> npcIndices) {

    }

    @Inject
    @Override
    public void addHiddenNpcName(String name) {

    }

    @Inject
    @Override
    public void removeHiddenNpcName(String name) {

    }

    @Inject
    @Override
    public void setProjectilesHidden(boolean state) {

    }

    @Inject
    @Override
    public void setDeadNPCsHidden(boolean state) {

    }

    @Inject
    @Override
    public void setBlacklistDeadNpcs(Set<Integer> blacklist) {

    }


    @Inject
    @Override
    public CollisionData[] getCollisionMaps() {
        return new CollisionData[0];
    }

    @Inject
    @Override
    public int[] getBoostedSkillLevels() {
        return new int[0];
    }

    @Inject
    @Override
    public int[] getRealSkillLevels() {
        return new int[0];
    }

    @Inject
    @Override
    public int[] getSkillExperiences() {
        return new int[0];
    }

    @Inject
    @Override
    public void queueChangedSkill(Skill skill) {

    }

    @Inject
    @Override
    public Map<Integer, net.runelite.api.SpritePixels> getSpriteOverrides() {
        return null;
    }

    @Inject
    @Override
    public Map<Integer, net.runelite.api.SpritePixels> getWidgetSpriteOverrides() {
        return null;
    }

    @Inject
    @Override
    public void setCompass(net.runelite.api.SpritePixels SpritePixels) {

    }

    @Inject
    @Override
    public NodeCache getWidgetSpriteCache() {
        return null;
    }

    @Inject
    @Override
    public int getTickCount() {
        return 0;
    }

    @Inject
    @Override
    public void setTickCount(int tickCount) {

    }

    @Inject
    @Override
    public void setInventoryDragDelay(int delay) {

    }

    @Inject
    @Override
    public boolean isHdMinimapEnabled() {
        return false;
    }

    @Inject
    @Override
    public void setHdMinimapEnabled(boolean enabled) {

    }

    @Inject
    @Override
    public EnumSet<WorldType> getWorldType() {
        return null;
    }

    @Inject
    @Override
    public int getOculusOrbState() {
        return 0;
    }

    @Inject
    @Override
    public void setOculusOrbState(int state) {

    }

    @Inject
    @Override
    public void setOculusOrbNormalSpeed(int speed) {

    }

    @Inject
    @Override
    public int getOculusOrbFocalPointX() {
        return 0;
    }

    @Inject
    @Override
    public int getOculusOrbFocalPointY() {
        return 0;
    }

    @Inject
    @Override
    public void openWorldHopper() {

    }

    @Inject
    @Override
    public void hopToWorld(net.runelite.api.World world) {

    }

    @Inject
    @Override
    public void setSkyboxColor(int skyboxColor) {

    }

    @Inject
    @Override
    public int getSkyboxColor() {
        return 0;
    }

    @Inject
    @Override
    public boolean isGpu() {
        return false;
    }

    @Inject
    @Override
    public void setGpu(boolean gpu) {

    }

    @Inject
    @Override
    public int get3dZoom() {
        return 0;
    }

    @Inject
    @Override
    public int getCenterX() {
        return 0;
    }

    @Inject
    @Override
    public int getCenterY() {
        return 0;
    }

    @Inject
    @Override
    public int getCameraX2() {
        return 0;
    }

    @Inject
    @Override
    public int getCameraY2() {
        return 0;
    }

    @Inject
    @Override
    public int getCameraZ2() {
        return 0;
    }

    @Inject
    @Override
    public TextureProvider getTextureProvider() {
        return null;
    }

    @Inject
    @Override
    public NodeCache getCachedModels2() {
        return null;
    }

    @Inject
    @Override
    public void setRenderArea(boolean[][] renderArea) {

    }

    @Inject
    @Override
    public int getRasterizer3D_clipMidX2() {
        return 0;
    }

    @Inject
    @Override
    public int getRasterizer3D_clipNegativeMidX() {
        return 0;
    }

    @Inject
    @Override
    public int getRasterizer3D_clipNegativeMidY() {
        return 0;
    }

    @Inject
    @Override
    public int getRasterizer3D_clipMidY2() {
        return 0;
    }

    @Inject
    @Override
    public void checkClickbox(Model model, int orientation, int pitchSin, int pitchCos, int yawSin, int yawCos, int x, int y, int z, long hash) {

    }

    @Inject
    @Override
    public net.runelite.api.widgets.Widget getIf1DraggedWidget() {
        return null;
    }

    @Inject
    @Override
    public int getIf1DraggedItemIndex() {
        return 0;
    }

    @Inject
    @Override
    public void setSpellSelected(boolean selected) {

    }

    @Inject
    @Override
    public NodeCache getItemCompositionCache() {
        return null;
    }

    @Inject
    @Override
    public net.runelite.api.SpritePixels[] getCrossSprites() {
        return new net.runelite.api.SpritePixels[0];
    }

    @Inject
    @Override
    public EnumComposition getEnum(int id) {
        return null;
    }

    @Inject
    @Override
    public void draw2010Menu(int alpha) {

    }

    @Inject
    @Override
    public int[] getGraphicsPixels() {
        return new int[0];
    }

    @Inject
    @Override
    public void drawOriginalMenu(int alpha) {

    }

    @Inject
    @Override
    public void resetHealthBarCaches() {

    }

    @Inject
    @Override
    public boolean getRenderSelf() {
        return false;
    }

    @Inject
    @Override
    public void setRenderSelf(boolean enabled) {

    }

    @Inject
    @Override
    public void invokeMenuAction(String option, String target, int identifier, int opcode, int param0, int param1) {

    }

    @Inject
    @Override
    public net.runelite.api.MouseRecorder getMouseRecorder() {
        return null;
    }

    @Inject
    @Override
    public void setPrintMenuActions(boolean b) {

    }

    @Inject
    @Override
    public String getSelectedSpellName() {
        return null;
    }

    @Inject
    @Override
    public void setSelectedSpellName(String name) {

    }

    @Inject
    @Override
    public boolean getSpellSelected() {
        return false;
    }

    @Inject
    @Override
    public String getSelectedSpellActionName() {
        return null;
    }

    @Inject
    @Override
    public int getSelectedSpellFlags() {
        return 0;
    }

    @Inject
    @Override
    public void setHideFriendAttackOptions(boolean yes) {

    }

    @Inject
    @Override
    public void setHideFriendCastOptions(boolean yes) {

    }

    @Inject
    @Override
    public void setHideClanmateAttackOptions(boolean yes) {

    }

    @Inject
    @Override
    public void setHideClanmateCastOptions(boolean yes) {

    }

    @Inject
    @Override
    public void setUnhiddenCasts(Set<String> casts) {

    }

    @Inject
    @Override
    public void addFriend(String name) {

    }

    @Inject
    @Override
    public void removeFriend(String name) {

    }

    @Inject
    @Override
    public void setModulus(BigInteger modulus) {

    }

    @Inject
    @Override
    public int getItemCount() {
        return 0;
    }

    @Inject
    @Override
    public void setAllWidgetsAreOpTargetable(boolean value) {

    }

    @Inject
    @Override
    public void insertMenuItem(String action, String target, int opcode, int identifier, int argument1, int argument2, boolean forceLeftClick) {

    }

    @Inject
    @Override
    public void setSelectedItemID(int id) {

    }

    @Inject
    @Override
    public int getSelectedItemWidget() {
        return 0;
    }

    @Inject
    @Override
    public void setSelectedItemWidget(int widgetID) {

    }

    @Inject
    @Override
    public int getSelectedItemSlot() {
        return 0;
    }

    @Inject
    @Override
    public void setSelectedItemSlot(int idx) {

    }

    @Inject
    @Override
    public int getSelectedSpellWidget() {
        return 0;
    }

    @Inject
    @Override
    public int getSelectedSpellChildIndex() {
        return 0;
    }

    @Inject
    @Override
    public void setSelectedSpellWidget(int widgetID) {

    }

    @Inject
    @Override
    public void setSelectedSpellChildIndex(int index) {

    }

    @Inject
    @Override
    public void scaleSprite(int[] canvas, int[] pixels, int color, int pixelX, int pixelY, int canvasIdx, int canvasOffset, int newWidth, int newHeight, int pixelWidth, int pixelHeight, int oldWidth) {

    }

    @Inject
    @Override
    public MenuEntry getLeftClickMenuEntry() {
        return null;
    }

    @Inject
    @Override
    public void setLeftClickMenuEntry(MenuEntry entry) {

    }

    @Inject
    @Override
    public void setHideDisconnect(boolean dontShow) {

    }

    @Inject
    @Override
    public void setTempMenuEntry(MenuEntry entry) {

    }

    @Inject
    @Override
    public void setShowMouseCross(boolean show) {

    }

    @Inject
    @Override
    public void setMouseIdleTicks(int cycles) {

    }

    @Inject
    @Override
    public void setKeyboardIdleTicks(int cycles) {

    }

    @Inject
    @Override
    public void setGeSearchResultCount(int count) {

    }

    @Inject
    @Override
    public void setGeSearchResultIds(short[] ids) {

    }

    @Inject
    @Override
    public void setGeSearchResultIndex(int index) {

    }

    @Inject
    @Override
    public void setComplianceValue( String key, boolean value) {

    }

    @Inject
    @Override
    public boolean getComplianceValue( String key) {
        return false;
    }

    @Inject
    @Override
    public boolean isMirrored() {
        return false;
    }

    @Inject
    @Override
    public void setMirrored(boolean isMirrored) {

    }

    @Inject
    @Override
    public boolean isComparingAppearance() {
        return false;
    }

    @Inject
    @Override
    public void setComparingAppearance(boolean comparingAppearance) {

    }

    @Inject
    @Override
    public void setLoginScreen(net.runelite.api.SpritePixels pixels) {

    }

    @Inject
    @Override
    public void setShouldRenderLoginScreenFire(boolean val) {

    }

    @Inject
    @Override
    public boolean shouldRenderLoginScreenFire() {
        return false;
    }

    @Inject
    @Override
    public boolean isKeyPressed(int keycode) {
        return false;
    }

    @Inject
    @Override
    public int getFollowerIndex() {
        return 0;
    }

    @Inject
    @Override
    public int isItemSelected() {
        return 0;
    }

    @Inject
    @Override
    public String getSelectedItemName() {
        return null;
    }

    @Inject
    @Override
    public net.runelite.api.widgets.Widget getMessageContinueWidget() {
        return null;
    }

    @Inject
    @Override
    public void setOutdatedScript(String outdatedScript) {

    }

    @Inject
    @Override
    public List<String> getOutdatedScripts() {
        return null;
    }

    @Inject
    @Override
    public net.runelite.api.Frames getFrames(int frameId) {
        return null;
    }

    @Inject
    @Override
    public SequenceDefinition getSequenceDefinition(int id) {
        return null;
    }

    @Inject
    @Override
    public IndexDataBase getSequenceDefinition_skeletonsArchive() {
        return null;
    }

    @Inject
    @Override
    public IndexDataBase getSequenceDefinition_archive() {
        return null;
    }

    @Inject
    @Override
    public IndexDataBase getSequenceDefinition_animationsArchive() {
        return null;
    }

    @Inject
    @Override
    public IndexDataBase getNpcDefinition_archive() {
        return null;
    }

    @Inject
    @Override
    public IndexDataBase getObjectDefinition_modelsArchive() {
        return null;
    }

    @Inject
    @Override
    public IndexDataBase getObjectDefinition_archive() {
        return null;
    }

    @Inject
    @Override
    public IndexDataBase getItemDefinition_archive() {
        return null;
    }

    @Inject
    @Override
    public IndexDataBase getKitDefinition_archive() {
        return null;
    }

    @Inject
    @Override
    public IndexDataBase getKitDefinition_modelsArchive() {
        return null;
    }

    @Inject
    @Override
    public IndexDataBase getSpotAnimationDefinition_archive() {
        return null;
    }

    @Inject
    @Override
    public IndexDataBase getSpotAnimationDefinition_modelArchive() {
        return null;
    }

    @Inject
    @Override
    public net.runelite.api.Buffer createBuffer(byte[] initialBytes) {
        return null;
    }

    @Inject
    @Override
    public long[] getCrossWorldMessageIds() {
        return new long[0];
    }

    @Inject
    @Override
    public int getCrossWorldMessageIdsIndex() {
        return 0;
    }


    @Inject
    @Override
    public net.runelite.api.clan.ClanChannel getClanChannel() {
        return null;
    }


    @Inject
    @Override
    public net.runelite.api.clan.ClanChannel getGuestClanChannel() {
        return null;
    }


    @Inject
    @Override
    public net.runelite.api.clan.ClanSettings getClanSettings() {
        return null;
    }


    @Inject
    @Override
    public net.runelite.api.clan.ClanSettings getGuestClanSettings() {
        return null;
    }
}
