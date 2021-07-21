package net.runelite.mixins;

import net.runelite.api.*;
import net.runelite.api.Node;
import net.runelite.api.events.*;
import net.runelite.api.hooks.Callbacks;
import net.runelite.api.hooks.DrawCallbacks;
import net.runelite.api.mixins.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;
import net.runelite.api.widgets.WidgetType;
import net.runelite.rs.api.*;
import org.sponge.util.Logger;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;

import static net.runelite.api.MenuAction.UNKNOWN;


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
    private static int tickCount;

    @Inject
    @Override
    public Callbacks getCallbacks()
    {
        return callbacks;
    }

    @Inject
    @Override
    public DrawCallbacks getDrawCallbacks()
    {
        return drawCallbacks;
    }

    @Inject
    private DrawCallbacks drawCallbacks;

    @Inject
    @Override
    public GameState getGameState()
    {
        return GameState.of(getRSGameState$api());
    }

    @Inject
    public static int viewportColor;

    @Inject
    public static Logger logger = new Logger("injected-clients");

    @Inject
    @FieldHook("gameState")
    public static void onGameStateChanged(int idx)
    {
        clientInstance = client;
        GameStateChanged gameStateChanged = new GameStateChanged();
        gameStateChanged.setGameState(GameState.of(client.getRSGameState$api()));
        client.getCallbacks().post(gameStateChanged);
    }

    @Inject
    @Override
    public void setDrawCallbacks(DrawCallbacks drawCallbacks)
    {
        this.drawCallbacks = drawCallbacks;
    }

    @Inject
    @Override
    public Logger getLogger() {
        return logger;
    }

    @Inject
    static int skyboxColor;

    @Inject
    @Override
    public int getSkyboxColor()
    {
        return skyboxColor;
    }

    @Inject
    @Override
    public boolean isGpu()
    {
        return gpu;
    }

    @Inject
    @Override
    public void setGpu(boolean gpu)
    {
        this.gpu = gpu;
    }

    @Inject
    @MethodHook("draw")
    public void draw$api(boolean var1)
    {
        callbacks.clientMainLoop();
    }

    @Inject
    private boolean gpu;

    @Override
    @Inject
    public int getTickCount()
    {
        return tickCount;
    }

    @FieldHook("npcs")
    @Inject
    public static void cachedNPCsChanged(int idx)
    {
        RSNPC[] cachedNPCs = client.getCachedNPCs();
        if (idx < 0 || idx >= cachedNPCs.length)
        {
            return;
        }

        RSNPC npc = cachedNPCs[idx];
        if (npc != null)
        {
            npc.setIndex(idx);

            client.getCallbacks().postDeferred(new NpcSpawned(npc));
        }
    }

    @Inject
    @MethodHook("updateNpcs")
    public static void updateNpcs(boolean var0, RSPacketBuffer var1)
    {
        client.getCallbacks().updateNpcs();
    }

    @Override
    @Inject
    public void setTickCount(int tick)
    {
        tickCount = tick;
    }

    @Inject
    @Override
    public ObjectComposition getObjectDefinition(int objectId)
    {
        assert this.isClientThread() : "getObjectDefinition must be called on client thread";
        return getRSObjectComposition(objectId);
    }

    @Inject
    @Override
    @Nonnull
    public ItemComposition getItemComposition(int id)
    {
        assert this.isClientThread() : "getItemComposition must be called on client thread";
        return getRSItemDefinition(id);
    }

    @Inject
    @Override
    @Nonnull
    public ItemComposition getItemDefinition(int id)
    {
        return getItemComposition(id);
    }

    @Inject
    @Override
    public NPCComposition getNpcDefinition(int id)
    {
        assert this.isClientThread() : "getNpcDefinition must be called on client thread";
        return getRSNpcComposition(id);
    }

    @Inject
    @Override
    public RSTileItem getLastItemDespawn()
    {
        return lastItemDespawn;
    }

    @Inject
    private static RSTileItem lastItemDespawn;

    @Inject
    @Override
    public void setLastItemDespawn(RSTileItem lastItemDespawn)
    {
        ClientMixin.lastItemDespawn = lastItemDespawn;
    }

    @Inject
    @Override
    public List<Projectile> getProjectiles()
    {
        List<Projectile> projectiles = new ArrayList<>();
        RSNodeDeque projectileDeque = this.getProjectilesDeque();
        net.runelite.api.Node head = projectileDeque.getSentinel();

        for (net.runelite.api.Node node = head.getNext(); node != head; node = node.getNext())
        {
            projectiles.add((Projectile) node);
        }

        return projectiles;
    }

    @Inject
    @Override
    public List<GraphicsObject> getGraphicsObjects()
    {
        List<GraphicsObject> graphicsObjects = new ArrayList<>();
        RSNodeDeque graphicsObjectDeque = this.getGraphicsObjectDeque();
        net.runelite.api.Node head = graphicsObjectDeque.getSentinel();

        for (Node node = head.getNext(); node != head; node = node.getNext())
        {
            graphicsObjects.add((GraphicsObject) node);
        }

        return graphicsObjects;
    }

    @Inject
    private static RSPlayer[] oldPlayers = new RSPlayer[2048];

    @FieldHook("players")
    @Inject
    public static void cachedPlayersChanged(int idx)
    {
        RSPlayer[] cachedPlayers = client.getCachedPlayers();
        if (idx < 0 || idx >= cachedPlayers.length)
        {
            return;
        }

        RSPlayer player = cachedPlayers[idx];
        RSPlayer oldPlayer = oldPlayers[idx];
        oldPlayers[idx] = player;

        if (oldPlayer != null)
        {
            client.getCallbacks().post(new PlayerDespawned(oldPlayer));
        }
        if (player != null)
        {
            client.getCallbacks().postDeferred(new PlayerSpawned(player));
        }
    }

    @Inject
    private static boolean hdMinimapEnabled = false;

    @Inject
    @Override
    public boolean isHdMinimapEnabled()
    {
        return hdMinimapEnabled;
    }

    @Inject
    private static ArrayList<WidgetItem> widgetItems = new ArrayList<>();

    @Inject
    private static ArrayList<Widget> hiddenWidgets = new ArrayList<>();

    @MethodHook("drawInterface")
    @Inject
    public static void preRenderWidgetLayer(Widget[] widgets, int parentId, int minX, int minY, int maxX, int maxY, int x, int y, int var8)
    {
        @SuppressWarnings("unchecked") HashTable<WidgetNode> componentTable = client.getComponentTable();

        for (int i = 0; i < widgets.length; i++)
        {
            RSWidget widget = (RSWidget) widgets[i];
            if (widget == null || widget.getRSParentId() != parentId || widget.isSelfHidden())
            {
                continue;
            }

            if (parentId != -1)
            {
                widget.setRenderParentId(parentId);
            }

            final int renderX = x + widget.getRelativeX();
            final int renderY = y + widget.getRelativeY();
            widget.setRenderX(renderX);
            widget.setRenderY(renderY);

            if (widget.getType() == WidgetType.RECTANGLE && renderX == client.getViewportXOffset() && renderY == client.getViewportYOffset()
                    && widget.getWidth() == client.getViewportWidth() && widget.getHeight() == client.getViewportHeight()
                    && widget.getOpacity() > 0 && widget.isFilled() && widget.getFillMode().getOrdinal() == 0 && client.isGpu())
            {
                int tc = widget.getTextColor();
                int alpha = widget.getOpacity() & 0xFF;
                int inverseAlpha = 256 - alpha;
                int vpc = viewportColor;
                int c1 = (inverseAlpha * (tc & 0xFF00FF) >> 8 & 0xFF00FF) + (inverseAlpha * (tc & 0x00FF00) >> 8 & 0x00FF00);
                int c2 = (alpha * (vpc & 0xFF00FF) >> 8 & 0xFF00FF) + (alpha * (vpc & 0x00FF00) >> 8 & 0x00FF00);
                int outAlpha = inverseAlpha + ((vpc >>> 24) * (255 - inverseAlpha) * 0x8081 >>> 23);
                viewportColor = outAlpha << 24 | c1 + c2;
                widget.setHidden(true);
                hiddenWidgets.add(widget);
            }
            else
            {
                WidgetNode childNode = componentTable.get$api(widget.getId());
                if (childNode != null)
                {
                    int widgetId = widget.getId();
                    int groupId = childNode.getId();
                    RSWidget[] children = client.getWidgets()[groupId];

                    for (RSWidget child : children)
                    {
                        if (child.getRSParentId() == -1)
                        {
                            child.setRenderParentId(widgetId);
                        }
                    }
                }
            }
        }
    }

    @Inject
    @MethodHook(value = "drawInterface", end = true)
    public static void postRenderWidgetLayer(Widget[] widgets, int parentId, int minX, int minY, int maxX, int maxY, int x, int y, int var8)
    {
        Callbacks callbacks = client.getCallbacks();
        int oldSize = widgetItems.size();

        for (Widget rlWidget : widgets)
        {
            RSWidget widget = (RSWidget) rlWidget;
            if (widget == null || widget.getRSParentId() != parentId || widget.isSelfHidden())
            {
                continue;
            }

            int type = widget.getType();
            if (type == WidgetType.GRAPHIC && widget.getItemId() != -1)
            {
                final int renderX = x + widget.getRelativeX();
                final int renderY = y + widget.getRelativeY();
                if (renderX >= minX && renderX <= maxX && renderY >= minY && renderY <= maxY)
                {
                    WidgetItem widgetItem = new WidgetItem(widget.getItemId(), widget.getItemQuantity(), -1, widget.getBounds(), widget, null);
                    widgetItems.add(widgetItem);
                }
            }
            else if (type == WidgetType.INVENTORY)
            {
                widgetItems.addAll(widget.getWidgetItems());
            }
        }

        List<WidgetItem> subList = Collections.emptyList();
        if (oldSize < widgetItems.size())
        {
            if (oldSize > 0)
            {
                subList = widgetItems.subList(oldSize, widgetItems.size());
            }
            else
            {
                subList = widgetItems;
            }
        }

        if (parentId == 0xabcdabcd)
        {
            widgetItems.clear();
        }
        else if (parentId != -1)
        {
            Widget widget = client.getWidget(parentId);
            Widget[] children = widget.getChildren();
            if (children == null || children == widgets)
            {
                callbacks.drawLayer(widget, subList);
            }
        }
        else
        {
            int group = -1;
            for (Widget widget : widgets)
            {
                if (widget != null)
                {
                    group = WidgetInfo.TO_GROUP(widget.getId());
                    break;
                }
            }

            if (group == -1)
            {
                return;
            }

            callbacks.drawInterface(group, widgetItems);
            widgetItems.clear();
            for (int i = hiddenWidgets.size() - 1; i >= 0; i--)
            {
                Widget widget = hiddenWidgets.get(i);
                if (WidgetInfo.TO_GROUP(widget.getId()) == group)
                {
                    widget.setHidden(false);
                    hiddenWidgets.remove(i);
                }
            }
        }
    }

    @Inject
    @Override
    public Widget getWidget(int id)
    {
        return getWidget(WidgetInfo.TO_GROUP(id), WidgetInfo.TO_CHILD(id));
    }

    @Inject
    @Override
    public Widget getWidget(int groupId, int childId)
    {
        RSWidget[][] widgets = getWidgets();

        if (widgets == null || widgets.length <= groupId)
        {
            return null;
        }

        RSWidget[] childWidgets = widgets[groupId];
        if (childWidgets == null || childWidgets.length <= childId)
        {
            return null;
        }

        return childWidgets[childId];
    }

    @Inject
    private static boolean interpolateWidgetAnimations;

    @Inject
    @Override
    public boolean isInterpolateWidgetAnimations()
    {
        return interpolateWidgetAnimations;
    }

    @Inject
    private final ArrayList<String> outdatedScripts = new ArrayList<>();

    @Inject
    @Override
    public void setOutdatedScript(String outdatedScript)
    {
        if (!outdatedScripts.contains(outdatedScript))
        {
            outdatedScripts.add(outdatedScript);
        }
    }

    @Inject
    @Override
    public List<String> getOutdatedScripts()
    {
        return this.outdatedScripts;
    }

    @Inject
    @Override
    public int getVar(VarPlayer varPlayer)
    {
        int[] varps = getVarps();
        return varps[varPlayer.getId()];
    }

    @Inject
    @Override
    public ScriptEvent createScriptEvent(Object... args)
    {
        return createRSScriptEvent(args);
    }

    @Inject
    @Override
    public RSScriptEvent createRSScriptEvent(Object... args)
    {
        RSScriptEvent event = createScriptEvent();
        event.setArguments(args);
        return event;
    }

    @Inject
    boolean occluderEnabled = false;

    @Inject
    @Override
    public boolean getOccluderEnabled()
    {
        return occluderEnabled;
    }

    @Inject
    @Override
    public void setOccluderEnabled(boolean enabled)
    {
        occluderEnabled = enabled;
    }

    @Inject
    @Override
    public int getBoostedSkillLevel(Skill skill)
    {
        int[] boostedLevels = getBoostedSkillLevels();
        return boostedLevels[skill.ordinal()];
    }

    @Inject
    @Override
    public int getRealSkillLevel(Skill skill)
    {
        int[] realLevels = getRealSkillLevels();
        return realLevels[skill.ordinal()];
    }

    /**
     * Returns the local player's current experience in the specified
     * {@link Skill}.
     *
     * @param skill the {@link Skill} to retrieve the experience for
     * @return the local player's current experience in the specified
     * {@link Skill}, or -1 if the {@link Skill} isn't valid
     */
    @Inject
    @Override
    public int getSkillExperience(Skill skill)
    {
        int[] experiences = getSkillExperiences();

        if (skill == Skill.OVERALL)
        {
            return (int) getOverallExperience();
        }

        int idx = skill.ordinal();

        // I'm not certain exactly how needed this is, but if the Skill enum is updated in the future
        // to hold something else that's not reported it'll save us from an ArrayIndexOutOfBoundsException.
        if (idx >= experiences.length)
        {
            return -1;
        }

        return experiences[idx];
    }

    @FieldHook("experience")
    @Inject
    public static void experiencedChanged(int idx)
    {
        Skill[] possibleSkills = Skill.values();

        // We subtract one here because 'Overall' isn't considered a skill that's updated.
        if (idx < possibleSkills.length - 1)
        {
            Skill updatedSkill = possibleSkills[idx];
            StatChanged statChanged = new StatChanged(
                    updatedSkill,
                    client.getSkillExperience(updatedSkill),
                    client.getRealSkillLevel(updatedSkill),
                    client.getBoostedSkillLevel(updatedSkill)
            );
            client.getCallbacks().post(statChanged);
        }
    }

    @FieldHook("changedSkills")
    @Inject
    public static void boostedSkillLevelsChanged(int idx)
    {
        if (idx == -1)
        {
            return;
        }

        int changedSkillIdx = idx - 1 & 31;
        int skillIdx = client.getChangedSkillLevels()[changedSkillIdx];
        Skill[] skills = Skill.values();
        if (skillIdx >= 0 && skillIdx < skills.length - 1)
        {
            StatChanged statChanged = new StatChanged(
                    skills[skillIdx],
                    client.getSkillExperiences()[skillIdx],
                    client.getRealSkillLevels()[skillIdx],
                    client.getBoostedSkillLevels()[skillIdx]
            );
            client.getCallbacks().post(statChanged);
        }
    }

    @Inject
    @Override
    public Widget getWidget(WidgetInfo widget)
    {
        int groupId = widget.getGroupId();
        int childId = widget.getChildId();

        return getWidget(groupId, childId);
    }

    @Inject
    @Override
    public Point getMouseCanvasPosition()
    {
        return new Point(getMouseX(), getMouseY());
    }

    @Inject
    @Override
    public MenuEntry[] getMenuEntries()
    {
        int count = getMenuOptionCount();
        String[] menuOptions = getMenuOptions();
        String[] menuTargets = getMenuTargets();
        int[] menuIdentifiers = getMenuIdentifiers();
        int[] menuTypes = getMenuOpcodes();
        int[] params0 = getMenuArguments1();
        int[] params1 = getMenuArguments2();
        boolean[] leftClick = getMenuForceLeftClick();

        MenuEntry[] entries = new MenuEntry[count];
        for (int i = 0; i < count; ++i)
        {
            MenuEntry entry = entries[i] = new MenuEntry();
            entry.setOption(menuOptions[i]);
            entry.setTarget(menuTargets[i]);
            entry.setIdentifier(menuIdentifiers[i]);
            entry.setOpcode(menuTypes[i]);
            entry.setActionParam(params0[i]);
            entry.setActionParam1(params1[i]);
            entry.setForceLeftClick(leftClick[i]);
        }
        return entries;
    }

    @Inject
    private static int oldMenuEntryCount;

    @Inject
    @Override
    public void setMenuEntries(MenuEntry[] entries)
    {
        int count = 0;
        String[] menuOptions = getMenuOptions();
        String[] menuTargets = getMenuTargets();
        int[] menuIdentifiers = getMenuIdentifiers();
        int[] menuTypes = getMenuOpcodes();
        int[] params0 = getMenuArguments1();
        int[] params1 = getMenuArguments2();
        boolean[] leftClick = getMenuForceLeftClick();

        for (MenuEntry entry : entries)
        {
            if (entry == null)
            {
                continue;
            }

            menuOptions[count] = entry.getOption();
            menuTargets[count] = entry.getTarget();
            menuIdentifiers[count] = entry.getIdentifier();
            menuTypes[count] = entry.getOpcode();
            params0[count] = entry.getActionParam();
            params1[count] = entry.getActionParam1();
            leftClick[count] = entry.isForceLeftClick();
            ++count;
        }

        setMenuOptionCount(count);
        oldMenuEntryCount = count;
    }

    @FieldHook("menuOptionsCount")
    @Inject
    public static void onMenuOptionsChanged(int idx)
    {
        int oldCount = oldMenuEntryCount;
        int newCount = client.getMenuOptionCount();

        oldMenuEntryCount = newCount;

        final String[] options = client.getMenuOptions();
        final String[] targets = client.getMenuTargets();
        final int[] identifiers = client.getMenuIdentifiers();
        final int[] opcodes = client.getMenuOpcodes();
        final int[] arguments1 = client.getMenuArguments1();
        final int[] arguments2 = client.getMenuArguments2();
        final boolean[] forceLeftClick = client.getMenuForceLeftClick();

        if (newCount == oldCount + 1)
        {
            MenuEntryAdded event = new MenuEntryAdded(
                    options[oldCount],
                    targets[oldCount],
                    identifiers[oldCount],
                    opcodes[oldCount],
                    arguments1[oldCount],
                    arguments2[oldCount],
                    forceLeftClick[oldCount]
            );

            client.getCallbacks().post(event);

            if (event.isModified() && client.getMenuOptionCount() == newCount)
            {
                options[oldCount] = event.getOption();
                targets[oldCount] = event.getTarget();
                identifiers[oldCount] = event.getIdentifier();
                opcodes[oldCount] = event.getOpcode();
                arguments1[oldCount] = event.getActionParam();
                arguments2[oldCount] = event.getActionParam1();
                forceLeftClick[oldCount] = event.isForceLeftClick();
            }
        }
    }

    @Inject
    @Override
    public EnumSet<WorldType> getWorldType()
    {
        int flags = getFlags();
        return WorldType.fromMask(flags);
    }

    @Inject
    @Override
    public Tile getSelectedSceneTile()
    {
        int tileX = getSelectedSceneTileX();
        int tileY = getSelectedSceneTileY();

        if (tileX == -1 || tileY == -1)
        {
            return null;
        }

        return getScene().getTiles()[getPlane()][tileX][tileY];
    }

    @Copy("menuAction")
    @Replace("menuAction")
    static void copy$menuAction(int param0, int param1, int opcode, int id, String option, String target, int canvasX, int canvasY)
    {
        /*
         * The RuneScape client may deprioritize an action in the menu by incrementing the opcode with 2000,
         * undo it here so we can get the correct opcode
         */
        boolean decremented = false;
        if (opcode >= 2000)
        {
            decremented = true;
            opcode -= 2000;
        }

        final MenuOptionClicked menuOptionClicked = new MenuOptionClicked();
        menuOptionClicked.setActionParam(param0);
        menuOptionClicked.setMenuOption(option);
        menuOptionClicked.setMenuTarget(target);
        menuOptionClicked.setMenuAction(MenuAction.of(opcode));
        menuOptionClicked.setId(id);
        menuOptionClicked.setWidgetId(param1);
        menuOptionClicked.setSelectedItemIndex(client.getSelectedItemSlot());

        client.getCallbacks().post(menuOptionClicked);

        if (menuOptionClicked.isConsumed())
        {
            return;
        }

        if (false)
        {
            /*
            client.getLogger().info(
                    "|MenuAction|: MenuOption={} MenuTarget={} Id={} Opcode={}/{} Param0={} Param1={} CanvasX={} CanvasY={}",
                    menuOptionClicked.getMenuOption(), menuOptionClicked.getMenuTarget(), menuOptionClicked.getId(),
                    menuOptionClicked.getMenuAction(), opcode + (decremented ? 2000 : 0),
                    menuOptionClicked.getActionParam(), menuOptionClicked.getWidgetId(), canvasX, canvasY
            );
            */
        }

        copy$menuAction(menuOptionClicked.getActionParam(), menuOptionClicked.getWidgetId(),
                menuOptionClicked.getMenuAction() == UNKNOWN ? opcode : menuOptionClicked.getMenuAction().getId(),
                menuOptionClicked.getId(), menuOptionClicked.getMenuOption(), menuOptionClicked.getMenuTarget(),
                canvasX, canvasY);
    }

}
