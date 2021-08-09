package meteor.plugins.zulrah;

import com.google.common.base.Preconditions;
import com.google.inject.Provides;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.GameState;
import net.runelite.api.MenuEntry;
import net.runelite.api.NPC;
import net.runelite.api.Projectile;
import net.runelite.api.Renderable;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.FocusChanged;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.MenuEntryAdded;
import net.runelite.api.events.ProjectileMoved;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.eventbus.events.ConfigChanged;
import meteor.input.KeyListener;
import meteor.input.KeyManager;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.zulrah.overlays.InstanceTimerOverlay;
import meteor.plugins.zulrah.overlays.PhaseOverlay;
import meteor.plugins.zulrah.overlays.PrayerHelperOverlay;
import meteor.plugins.zulrah.overlays.PrayerMarkerOverlay;
import meteor.plugins.zulrah.overlays.SceneOverlay;
import meteor.plugins.zulrah.rotationutils.RotationType;
import meteor.plugins.zulrah.rotationutils.ZulrahData;
import meteor.plugins.zulrah.rotationutils.ZulrahPhase;
import meteor.ui.overlay.OverlayManager;
import meteor.ui.overlay.infobox.Counter;
import meteor.ui.overlay.infobox.InfoBoxManager;
import meteor.util.ImageUtil;
import org.sponge.util.Logger;

@PluginDescriptor(
   name = "Zulrah",
   description = "All-in-One tool to help during the Zulrah fight",
   tags = {"zulrah", "zul", "andra", "snakeling", "zhuri/nicole", "girls rule boys drool"},
   enabledByDefault = false
)
public class ZulrahPlugin extends Plugin implements KeyListener {
   private static final Logger log = Logger.getLogger(ZulrahPlugin.class);
   @Inject
   private Client client;
   @Inject
   private KeyManager keyManager;
   @Inject
   private InfoBoxManager infoBoxManager;
   @Inject
   private OverlayManager overlayManager;
   @Inject
   private InstanceTimerOverlay instanceTimerOverlay;
   @Inject
   private PhaseOverlay phaseOverlay;
   @Inject
   private PrayerHelperOverlay prayerHelperOverlay;
   @Inject
   private PrayerMarkerOverlay prayerMarkerOverlay;
   @Inject
   private SceneOverlay sceneOverlay;
   @Inject
   private ZulrahConfig config;
   private NPC zulrahNpc = null;
   private int stage = 0;
   private int phaseTicks = -1;
   private int attackTicks = -1;
   private int totalTicks = 0;
   private RotationType currentRotation = null;
   private List<RotationType> potentialRotations = new ArrayList();
   private final Map<LocalPoint, Integer> projectilesMap = new HashMap();
   private final Map<GameObject, Integer> toxicCloudsMap = new HashMap();
   private static boolean flipStandLocation = false;
   private static boolean flipPhasePrayer = false;
   private static boolean zulrahReset = false;
   private final Collection<Renderable> snakelings = new ArrayList();
   private boolean holdingSnakelingHotkey = false;
   private Counter zulrahTotalTicksInfoBox;
   public static final BufferedImage[] ZULRAH_IMAGES = new BufferedImage[3];
   private static final BufferedImage CLOCK_ICON = ImageUtil.getResourceStreamFromClass(ZulrahPlugin.class, "clock.png");
   private final BiConsumer<RotationType, RotationType> phaseTicksHandler = (current, potential) -> {
      if (zulrahReset) {
         this.phaseTicks = 38;
      } else {
         ZulrahPhase p = current != null ? this.getCurrentPhase(current) : this.getCurrentPhase(potential);
         Preconditions.checkNotNull(p, "Attempted to set phase ticks but current Zulrah phase was somehow null. Stage: " + this.stage);
         this.phaseTicks = p.getAttributes().getPhaseTicks();
      }

   };

   @Provides
   public ZulrahConfig getConfig(ConfigManager configManager) {
      return (configManager.getConfig(ZulrahConfig.class));
   }

   protected void startUp() {
      this.overlayManager.add(this.instanceTimerOverlay);
      this.overlayManager.add(this.phaseOverlay);
      this.overlayManager.add(this.prayerHelperOverlay);
      this.overlayManager.add(this.prayerMarkerOverlay);
      this.overlayManager.add(this.sceneOverlay);
      this.keyManager.registerKeyListener(this, this.getClass());
   }

   protected void shutDown() {
      this.reset();
      this.overlayManager.remove(this.instanceTimerOverlay);
      this.overlayManager.remove(this.phaseOverlay);
      this.overlayManager.remove(this.prayerHelperOverlay);
      this.overlayManager.remove(this.prayerMarkerOverlay);
      this.overlayManager.remove(this.sceneOverlay);
      this.keyManager.unregisterKeyListener(this);
   }

   private void reset() {
      this.zulrahNpc = null;
      this.stage = 0;
      this.phaseTicks = -1;
      this.attackTicks = -1;
      this.totalTicks = 0;
      this.currentRotation = null;
      this.potentialRotations.clear();
      this.projectilesMap.clear();
      this.toxicCloudsMap.clear();
      flipStandLocation = false;
      flipPhasePrayer = false;
      this.instanceTimerOverlay.resetTimer();
      zulrahReset = false;
      this.clearSnakelingCollection();
      this.holdingSnakelingHotkey = false;
      this.handleTotalTicksInfoBox(true);
      log.debug("Zulrah Reset!");
   }

   public void keyTyped(KeyEvent e) {
   }

   public void keyPressed(KeyEvent e) {
      if (this.config.snakelingSetting() == ZulrahConfig.SnakelingSettings.MES && this.config.snakelingMesHotkey().matches(e)) {
         this.holdingSnakelingHotkey = true;
      }

   }

   public void keyReleased(KeyEvent e) {
      if (this.config.snakelingSetting() == ZulrahConfig.SnakelingSettings.MES && this.config.snakelingMesHotkey().matches(e)) {
         this.holdingSnakelingHotkey = false;
      }

   }

   @Subscribe
   private void onConfigChanged(ConfigChanged event) {
      if (event.getGroup().equalsIgnoreCase("znzulrah")) {
         String var2 = event.getKey();
         byte var3 = -1;
         switch(var2.hashCode()) {
         case -1906254917:
            if (var2.equals("totalTickCounter")) {
               var3 = 1;
            }
            break;
         case -1662720966:
            if (var2.equals("snakelingSetting")) {
               var3 = 0;
            }
         }

         switch(var3) {
         case 0:
            if (this.config.snakelingSetting() != ZulrahConfig.SnakelingSettings.ENTITY) {
               this.clearSnakelingCollection();
            }

            if (this.config.snakelingSetting() != ZulrahConfig.SnakelingSettings.MES) {
               this.holdingSnakelingHotkey = false;
            }
            break;
         case 1:
            if (!this.config.totalTickCounter()) {
               this.handleTotalTicksInfoBox(true);
            }
         }
      }

   }

   private void clearSnakelingCollection() {
      this.snakelings.forEach((npc) -> {
         setHidden(npc, false);
      });
      this.snakelings.clear();
   }

   @Subscribe
   private void onClientTick(ClientTick event) {
      if (this.client.getGameState() == GameState.LOGGED_IN && this.zulrahNpc != null) {
         if (this.config.snakelingSetting() == ZulrahConfig.SnakelingSettings.ENTITY) {
            this.snakelings.addAll((Collection)this.client.getNpcs().stream().filter((npc) -> {
               return npc != null && npc.getName() != null && npc.getName().equalsIgnoreCase("snakeling") && npc.getCombatLevel() == 90;
            }).collect(Collectors.toList()));
            this.snakelings.forEach((npc) -> {
               setHidden(npc, true);
            });
         }

      }
   }

   @Subscribe
   private void onGameTick(GameTick event) {
      if (this.client.getGameState() == GameState.LOGGED_IN && this.zulrahNpc != null) {
         ++this.totalTicks;
         if (this.attackTicks >= 0) {
            --this.attackTicks;
         }

         if (this.phaseTicks >= 0) {
            --this.phaseTicks;
         }

         if (this.projectilesMap.size() > 0) {
            this.projectilesMap.values().removeIf((v) -> v <= 0);
            this.projectilesMap.replaceAll((k, v) -> v - 1);
         }

         if (this.toxicCloudsMap.size() > 0) {
            this.toxicCloudsMap.values().removeIf((v) -> v <= 0);
            this.toxicCloudsMap.replaceAll((k, v) -> v - 1);
         }

         this.handleTotalTicksInfoBox(false);
      }
   }

   @Subscribe
   private void onAnimationChanged(AnimationChanged event) {
      if (event.getActor() instanceof NPC) {
         NPC npc = (NPC)event.getActor();
         if (npc.getName() == null || npc.getName().equalsIgnoreCase("zulrah")) {
            switch(npc.getAnimation()) {
            case 5069:
               this.attackTicks = 4;
               if (this.currentRotation != null && this.getCurrentPhase(this.currentRotation).getZulrahNpc().isJad() && this.zulrahNpc.getInteracting() == this.client.getLocalPlayer()) {
                  flipPhasePrayer = !flipPhasePrayer;
               }
               break;
            case 5071:
               this.zulrahNpc = npc;
               this.instanceTimerOverlay.setTimer();
               this.potentialRotations = RotationType.findPotentialRotations(npc, this.stage);
               this.phaseTicksHandler.accept(this.currentRotation, (RotationType)this.potentialRotations.get(0));
               log.debug("New Zulrah Encounter Started");
               break;
            case 5072:
               if (zulrahReset) {
                  zulrahReset = false;
               }

               if (this.currentRotation != null && this.isLastPhase(this.currentRotation)) {
                  this.stage = -1;
                  this.currentRotation = null;
                  this.potentialRotations.clear();
                  this.snakelings.clear();
                  flipStandLocation = false;
                  flipPhasePrayer = false;
                  zulrahReset = true;
                  log.debug("Resetting Zulrah");
               }
               break;
            case 5073:
               ++this.stage;
               if (this.currentRotation == null) {
                  this.potentialRotations = RotationType.findPotentialRotations(npc, this.stage);
                  this.currentRotation = this.potentialRotations.size() == 1 ? (RotationType)this.potentialRotations.get(0) : null;
               }

               this.phaseTicksHandler.accept(this.currentRotation, (RotationType)this.potentialRotations.get(0));
               break;
            case 5804:
               this.reset();
               break;
            case 5806:
            case 5807:
               this.attackTicks = 8;
               flipStandLocation = !flipStandLocation;
            }

         }
      }
   }

   @Subscribe
   private void onFocusChanged(FocusChanged event) {
      if (!event.isFocused()) {
         this.holdingSnakelingHotkey = false;
      }

   }

   @Subscribe
   private void onMenuEntryAdded(MenuEntryAdded event) {
      if (this.config.snakelingSetting() == ZulrahConfig.SnakelingSettings.MES && this.zulrahNpc != null && !this.zulrahNpc.isDead()) {
         if (!this.holdingSnakelingHotkey && event.getTarget().contains("Snakeling") && event.getOption().equalsIgnoreCase("attack")) {
            NPC npc = this.client.getCachedNPCs()[event.getIdentifier()];
            if (npc == null) {
               return;
            }

            this.client.setMenuEntries((MenuEntry[])Arrays.copyOf(this.client.getMenuEntries(), this.client.getMenuEntries().length - 1));
         }

      }
   }

   @Subscribe
   private void onProjectileMoved(ProjectileMoved event) {
      if (this.zulrahNpc != null) {
         Projectile p = event.getProjectile();
         switch(p.getId()) {
         case 1045:
         case 1047:
            this.projectilesMap.put(event.getPosition(), p.getRemainingCycles() / 30);
         default:
         }
      }
   }

   @Subscribe
   private void onGameObjectSpawned(GameObjectSpawned event) {
      if (this.zulrahNpc != null) {
         GameObject obj = event.getGameObject();
         if (obj.getId() == 11700) {
            this.toxicCloudsMap.put(obj, 30);
         }

      }
   }

   @Subscribe
   private void onGameStateChanged(GameStateChanged event) {
      if (this.zulrahNpc != null) {
         switch(event.getGameState()) {
         case LOADING:
         case CONNECTION_LOST:
         case HOPPING:
            this.reset();
         default:
         }
      }
   }

   @Nullable
   private ZulrahPhase getCurrentPhase(RotationType type) {
      return this.stage >= type.getZulrahPhases().size() ? null : (ZulrahPhase)type.getZulrahPhases().get(this.stage);
   }

   @Nullable
   private ZulrahPhase getNextPhase(RotationType type) {
      return this.isLastPhase(type) ? null : (ZulrahPhase)type.getZulrahPhases().get(this.stage + 1);
   }

   private boolean isLastPhase(RotationType type) {
      return this.stage == type.getZulrahPhases().size() - 1;
   }

   public Set<ZulrahData> getZulrahData() {
      Set<ZulrahData> zulrahDataSet = new LinkedHashSet();
      if (this.currentRotation == null) {
         this.potentialRotations.forEach((type) -> {
            zulrahDataSet.add(new ZulrahData(this.getCurrentPhase(type), this.getNextPhase(type)));
         });
      } else {
         zulrahDataSet.add(new ZulrahData(this.getCurrentPhase(this.currentRotation), this.getNextPhase(this.currentRotation)));
      }

      return (Set)(zulrahDataSet.size() > 0 ? zulrahDataSet : Collections.emptySet());
   }

   private void handleTotalTicksInfoBox(boolean remove) {
      if (remove) {
         this.infoBoxManager.removeInfoBox(this.zulrahTotalTicksInfoBox);
         this.zulrahTotalTicksInfoBox = null;
      } else if (this.config.totalTickCounter()) {
         if (this.zulrahTotalTicksInfoBox == null) {
            this.zulrahTotalTicksInfoBox = new Counter(CLOCK_ICON, this, this.totalTicks);
            this.zulrahTotalTicksInfoBox.setTooltip("Total Ticks Alive");
            this.infoBoxManager.addInfoBox(this.zulrahTotalTicksInfoBox);
         } else {
            this.zulrahTotalTicksInfoBox.setCount(this.totalTicks);
         }
      }

   }

   private static void setHidden(Renderable renderable, boolean hidden) {
      Method setHidden = null;

      try {
         setHidden = renderable.getClass().getMethod("setHidden", Boolean.TYPE);
      } catch (NoSuchMethodException var5) {
         log.debug("Couldn't find method setHidden for class {}", renderable.getClass());
         return;
      }

      try {
         setHidden.invoke(renderable, hidden);
      } catch (InvocationTargetException | IllegalAccessException var4) {
         log.debug("Couldn't call method setHidden for class {}", renderable.getClass());
      }

   }

   public NPC getZulrahNpc() {
      return this.zulrahNpc;
   }

   public int getPhaseTicks() {
      return this.phaseTicks;
   }

   public int getAttackTicks() {
      return this.attackTicks;
   }

   public RotationType getCurrentRotation() {
      return this.currentRotation;
   }

   public Map<LocalPoint, Integer>  getProjectilesMap() {
      return this.projectilesMap;
   }

   public Map<GameObject, Integer> getToxicCloudsMap() {
      return this.toxicCloudsMap;
   }

   public static boolean isFlipStandLocation() {
      return flipStandLocation;
   }

   public static boolean isFlipPhasePrayer() {
      return flipPhasePrayer;
   }

   public static boolean isZulrahReset() {
      return zulrahReset;
   }

   static {
      ZULRAH_IMAGES[0] = ImageUtil.getResourceStreamFromClass(ZulrahPlugin.class, "zulrah_range.png");
      ZULRAH_IMAGES[1] = ImageUtil.getResourceStreamFromClass(ZulrahPlugin.class, "zulrah_melee.png");
      ZULRAH_IMAGES[2] = ImageUtil.getResourceStreamFromClass(ZulrahPlugin.class, "zulrah_magic.png");
   }
}
