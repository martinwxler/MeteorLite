package meteor.plugins.eeelfisher;

import com.google.inject.Inject;
import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.commons.Time;
import meteor.plugins.api.coords.RectangularArea;
import meteor.plugins.api.entities.NPCs;
import meteor.plugins.api.items.Inventory;
import meteor.plugins.api.packets.*;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.NPC;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.MenuOptionClicked;
import net.runelite.api.widgets.WidgetInfo;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

@PluginDescriptor(name = "E Eel Fisher", enabledByDefault = false, description = "fishes infernal or sacred eels for you", disabledOnStartup = true)
public class EEelFisherPlugin extends Plugin {
    @Inject
    EEelFisherConfig config;

    @Inject
    Client client;

    boolean crushing = false;

    int crushingtime = 0;

    private ScheduledExecutorService executor;

    Random rand = new Random();

    RectangularArea fishingMiddle = new RectangularArea(2188, 2191,3067, 3069);

    int tickDelay;

    @Provides
    public EEelFisherConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(EEelFisherConfig.class);
    }

    @Override
    public void startup() {
        crushing = false;
        tickDelay = 0;
        executor = Executors.newSingleThreadScheduledExecutor();
        crushingtime = 0;
    }

    @Subscribe
    public void onGameTick(GameTick e) {
        if (client.getGameState() != GameState.LOGGED_IN) {
            return;
        }
        if(crushing){
            crushingtime++;
            if(crushingtime>84){
                crushing=false;
            }
            return;
        }
        crushingtime =0;
        if (tickDelay > 0) {
            tickDelay--;
            return;
        }
        int delay;
        do {
            double val = rand.nextGaussian() * config.tickDelayDeviation() + config.tickDelayAVG();
            delay = (int) Math.round(val);
        } while (delay <= config.tickDelayMin() || delay >= config.tickDelayMax());
        tickDelay = delay;
        if (client.getLocalPlayer().getInteracting() != null || client.getLocalPlayer().isMoving()) {
            return;
        }
        executor.submit(() -> {
            Time.sleep(millisecondDelay());
            if (Inventory.getFreeSlots() != 0 && !crushing) {
                NPC fishingSpot = NPCs.getNearest(x -> x.getName() != null && x.getName().contains("Fishing spot"));
                if (fishingSpot != null) {
                    MousePackets.queueClickPacket(0, 0);
                    NPCPackets.queueNPCActionPacket(fishingSpot.getIndex(), 0);
                    return;
                }
                try {
                    if (config.location() == FishingType.ZULRAH) {
                        WorldPoint wp = fishingMiddle.getRandomTile();
                        MousePackets.queueClickPacket(0, 0);
                        MovementPackets.sendMovement(wp.getX(), wp.getY(), true);
                    }
                }catch(Exception a){
                    a.printStackTrace();
                }
                return;
            }

            if (!crushing) {
                MousePackets.queueClickPacket(0, 0);
                ItemPackets.useItemOnItem(Inventory.getFirst("Knife", "Hammer"), Inventory.getFirst("Infernal eel", "Sacred eel"));
                crushing = true;
                return;
            }

            if (Inventory.getFirst("Infernal eel", "Sacred eel") == null) {
                crushing = false;
            }
        });
    }
    @Subscribe
    public void onMenuOptionClicked(MenuOptionClicked event){
        crushing=false;
    }
    public int millisecondDelay() {
        int delay;
        do {
            double val = rand.nextGaussian() * config.millisecondDelayDeviation() + config.milisecondDelayAVG();
            delay = (int) Math.round(val);
        } while (delay <= config.millisecondDelayMin() || delay >= config.millisecondDelayMax());
        return delay;
    }

}
