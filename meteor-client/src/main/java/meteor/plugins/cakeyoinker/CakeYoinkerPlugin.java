package meteor.plugins.cakeyoinker;

import com.google.inject.Provides;
import meteor.PluginManager;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.eventbus.events.ConfigChanged;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.illutils.osrs.BankUtils;
import meteor.plugins.illutils.osrs.CalculationUtils;
import meteor.plugins.illutils.IllUtils;
import meteor.plugins.illutils.osrs.InterfaceUtils;
import meteor.plugins.illutils.osrs.InventoryUtils;
import meteor.plugins.illutils.osrs.KeyboardUtils;
import meteor.plugins.illutils.osrs.MenuUtils;
import meteor.plugins.illutils.osrs.MouseUtils;
import meteor.plugins.illutils.osrs.NPCUtils;
import meteor.plugins.illutils.osrs.OSRSUtils;
import meteor.plugins.illutils.osrs.ObjectUtils;
import meteor.plugins.illutils.osrs.PlayerUtils;
import meteor.plugins.illutils.osrs.WalkUtils;
import meteor.plugins.illutils.osrs.wrappers.IllObject;
import meteor.ui.overlay.OverlayManager;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldArea;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.ConfigButtonClicked;
import net.runelite.api.events.GameTick;

import javax.inject.Inject;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static meteor.plugins.cakeyoinker.CakeYoinkerState.*;

@PluginDescriptor(
	name = "CakeYoinker",
	enabledByDefault = false,
	description = "Yoinks cakes and shit",
	tags = {"cakes, shit, yoink, Tsillabak"}
)
public class CakeYoinkerPlugin extends Plugin {
	@Inject
	public Client client;

	@Inject
	public CakeYoinkerConfiguration config;

	@Inject
	public IllUtils utils;

	@Inject
	public MouseUtils mouse;

	@Inject
	public PlayerUtils playerUtils;

	@Inject
	public InventoryUtils inventory;

	@Inject
	public InterfaceUtils interfaceUtils;

	@Inject
	public CalculationUtils calc;

	@Inject
	public MenuUtils menu;

	@Inject
	public ObjectUtils object;

	@Inject
	public BankUtils bank;

	@Inject
	 public NPCUtils npc;

	@Inject
	public KeyboardUtils key;

	@Inject
	public WalkUtils walk;

	@Inject
	public ConfigManager configManager;

	@Inject
	PluginManager pluginManager;

	@Inject
	OverlayManager overlayManager;

	@Inject
	public CakeYoinkerOverlay overlay;

	@Inject
	public OSRSUtils game;


	CakeYoinkerState state;
	GameObject targetObject;
	MenuEntry targetMenu;
	WorldPoint skillLocation;
	Instant botTimer;
	LocalPoint beforeLoc;
	Player player;

	public final WorldPoint CAKEPOINT = new WorldPoint(2669, 3310, 0);
	public final WorldArea BANKAREA = new WorldArea(new WorldPoint(2659, 3288,0),
													new WorldPoint(  2649, 3278,0));
	public static Set<Integer> CAKE = new HashSet<>(Arrays.asList(ItemID.CAKE));

	int timeout = 0;
	long sleepLength;
	boolean startCakeYoinker;

	public CakeYoinkerPlugin() {
	}

	@Provides
	public CakeYoinkerConfiguration getConfig(ConfigManager configManager) {
		return configManager.getConfig(CakeYoinkerConfiguration.class);
	}

	private
	void resetVals() {
		overlayManager.remove(overlay);
		state = null;
		timeout = 0;
		botTimer = null;
		skillLocation = null;
		startCakeYoinker = false;
	}

	@Subscribe
	private
	void onConfigButtonPressed(ConfigButtonClicked configButtonClicked) {
		if (!configButtonClicked.getGroup().equalsIgnoreCase("cakeyoinker")) {
			return;
		}
		if (configButtonClicked.getKey().equals("startButton")) {
			if (!startCakeYoinker) {
				startCakeYoinker = true;
				state = null;
				targetMenu = null;
				botTimer = Instant.now();
				setLocation();
				overlayManager.add(overlay);
			} else {
				resetVals();
			}
		}
	}

	@Override
	public void shutdown() {
		// runs on plugin shutdown
		overlayManager.remove(overlay);
		startCakeYoinker = false;
	}

	@Subscribe
	private
	void onConfigChanged(ConfigChanged event) {
		if (!event.getGroup().equals("CakeYoinker")) {
			return;
		}
		startCakeYoinker = false;
	}

	public
	void setLocation() {
		if (client != null && client.getLocalPlayer() != null && client.getGameState().equals(GameState.LOGGED_IN)) {
			skillLocation = client.getLocalPlayer().getWorldLocation();
			beforeLoc = client.getLocalPlayer().getLocalLocation();
		} else {
			skillLocation = null;
			resetVals();
		}
	}

	private
	long sleepDelay() {
		sleepLength = calc.randomDelay(config.sleepWeightedDistribution(),
										config.sleepMin(),
										config.sleepMax(),
										config.sleepDeviation(),
										config.sleepTarget());
		return sleepLength;
	}

	private
	int tickDelay() {
		int tickLength = (int) calc.randomDelay(config.tickDelayWeightedDistribution(),
												config.tickDelayMin(),
												config.tickDelayMax(),
												config.tickDelayDeviation(),
												config.tickDelayTarget());
		return tickLength;
	}

	private
	void yoinkCake() {
		targetObject = object.findNearestGameObjectWithin(
				new WorldPoint(2668, 3310, 0), 1, 11730);
		if (targetObject != null&&player.getWorldLocation().equals(
				new WorldPoint(2669, 3310, 0))) {
			targetMenu = new MenuEntry("Steal-from",
										"Baker's Stall",
										targetObject.getId(),
										MenuAction.GAME_OBJECT_SECOND_OPTION.getId(),
					targetObject.getSceneMinLocation().getX(),
					targetObject.getSceneMinLocation().getY(), false);
			menu.setEntry(targetMenu);
			mouse.delayMouseClick(targetObject.getConvexHull().getBounds(), sleepDelay());

		} else if (targetObject== null){
			inventory.dropAllExcept(CAKE, true, 1, 1);
		}
	}

	private
	void openBank() {
		IllObject gameObject = game.objects().withId(10355).nearest();
		if (gameObject != null) {
			targetMenu = new MenuEntry("", "",
					gameObject.id(),
					bank.getBankMenuOpcode(gameObject.id()),
					gameObject.localPoint().getSceneX(),
					gameObject.localPoint().getSceneY(), false);
			menu.setEntry(targetMenu);
			gameObject.interact("Bank");
			utils.sendGameMessage("bank clicked");
		}
	}


	public
	CakeYoinkerState getState() {

		if (timeout > 0) {
			playerUtils.handleRun(20, 30);
			return TIMEOUT;
		}
		if (playerUtils.isMoving(beforeLoc)) {
			playerUtils.handleRun(20, 30);
			return MOVING;
		}
		if (inventory.isEmpty() && bank.isOpen()) {
			return WALK_TO_STALL;
		}
		if (!inventory.isFull() && player.getWorldLocation().equals(
								 CAKEPOINT)) {
			return YOINK_CAKES;
		}
		if (inventory.isFull() 	&& !bank.isOpen()
								&& player.getWorldArea()
								!= BANKAREA) {
			return WALK_TO_BANK;
		}
		if (inventory.isFull() 	&& player.getWorldArea()
								== BANKAREA){
			return FIND_BANK;
		}
		if (inventory.isFull() && bank.isOpen()) {
			return DEPOSIT_ITEMS;

	}
		return IDLE;
	}
	@Subscribe
	private
	void onGameTick(GameTick tick) {
		if (!startCakeYoinker) {
			return;
		}
		player = client.getLocalPlayer();
		if (client != null && player != null && skillLocation != null) {
			if (!client.isResized()) {
				utils.sendGameMessage("Client must be set to resizable");
				startCakeYoinker = false;
				return;
			}
			state = getState();
			beforeLoc = player.getLocalLocation();
			switch (state) {
				case TIMEOUT:
					playerUtils.handleRun(30, 20);
					timeout--;
					break;
				case WALK_TO_STALL:
					walk.sceneWalk(new WorldPoint(2668, 3310, 0),0,0);
					timeout = tickDelay();
					break;
				case YOINK_CAKES:
					yoinkCake();
					timeout = tickDelay();
					break;
				case ANIMATING:
					timeout = 1;
					break;
				case MOVING:
					playerUtils.handleRun(30, 20);
					timeout = tickDelay();
					break;
				case WALK_TO_BANK:
					walk.webWalk(new WorldPoint(2655,3286,0),2,false,
					calc.getRandomIntBetweenRange(100,650));
				case FIND_BANK:
					openBank();
					timeout = tickDelay();
					break;
				case DEPOSIT_ITEMS:
					bank.depositAll();
					timeout = tickDelay();
					break;
				case IDLE:
					timeout = 1;
					break;


			}

		}
	}

}



















