package meteor.plugins.sockethealing;

import com.google.inject.Provides;
import meteor.callback.ClientThread;
import meteor.config.ConfigManager;
import meteor.eventbus.EventBus;
import meteor.eventbus.Subscribe;
import meteor.eventbus.events.ConfigChanged;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDependency;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.socket.SocketPlugin;
import meteor.plugins.socket.org.json.JSONObject;
import meteor.plugins.socket.packet.SocketBroadcastPacket;
import meteor.plugins.socket.packet.SocketPlayerLeave;
import meteor.plugins.socket.packet.SocketReceivePacket;
import meteor.ui.overlay.Overlay;
import meteor.ui.overlay.OverlayManager;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.Skill;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Map;
import java.util.TreeMap;

@PluginDescriptor(
        name = "Socket - Healing",
        description = "Displays health overlays for socket party members. <br> Created by: A wild animal with a keyboard <br> Modified by: SpoonLite",
        enabledByDefault = false
)
@PluginDependency(SocketPlugin.class)
public class SocketHealingPlugin extends Plugin {
    @Inject
    private Client client;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private SocketHealingOverlay socketHealingOverlay;

    @Inject
    private SocketHealingConfig config;

    @Inject
    private SocketPlugin socketPlugin;

    @Inject
    private ClientThread clientThread;

    @Inject
    private EventBus eventBus;

    private Map<String, SocketHealingPlayer> partyMembers = new TreeMap<>();

    private int lastRefresh;

    public ArrayList<String> playerNames = new ArrayList<String>();

    @Provides
    public SocketHealingConfig getConfig(ConfigManager configManager) {
        return (SocketHealingConfig)configManager.getConfig(SocketHealingConfig.class);
    }

    public void startup() {
        this.overlayManager.add((Overlay)this.socketHealingOverlay);
        this.lastRefresh = 0;
        synchronized (this.partyMembers) {
            this.partyMembers.clear();
        }
    }

    public void shutdown() {
        this.overlayManager.remove((Overlay)this.socketHealingOverlay);
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged e) {
        if(!config.hpPlayerNames().equals("")) {
            this.playerNames.clear();
            byte b;
            int i;
            String[] arrayOfString;
            for (i = (arrayOfString = this.config.hpPlayerNames().split(",")).length, b = 0; b < i; ) {
                String str = arrayOfString[b];
                str = str.trim();
                if (!"".equals(str))
                    this.playerNames.add(str.toLowerCase());
                b++;
            }
        }
    }

    @Subscribe
    private void onGameStateChanged(GameStateChanged event) {
        if (event.getGameState() == GameState.LOGIN_SCREEN || event.getGameState() == GameState.HOPPING)
            synchronized (this.partyMembers) {
                this.partyMembers.clear();
            }
    }

    @Subscribe
    private void onGameTick(GameTick event) {
        if (this.client.getGameState() == GameState.LOGGED_IN) {
            SocketHealingPlayer playerHealth;
            int currentHealth = this.client.getBoostedSkillLevel(Skill.HITPOINTS);
            String name = this.client.getLocalPlayer().getName();
            synchronized (this.partyMembers) {
                playerHealth = this.partyMembers.get(name);
                if (playerHealth == null) {
                    playerHealth = new SocketHealingPlayer(name, currentHealth);
                    this.partyMembers.put(name, playerHealth);
                } else {
                    playerHealth.setHealth(currentHealth);
                }
            }
            this.lastRefresh++;
            if (this.lastRefresh >= Math.max(1, this.config.refreshRate())) {
                JSONObject packet = new JSONObject();
                packet.put("name", name);
                packet.put("player-health", playerHealth.toJSON());
                this.eventBus.post(new SocketBroadcastPacket(packet));
                this.lastRefresh = 0;
            }
        }
    }

    @Subscribe
    public void onSocketReceivePacket(SocketReceivePacket event) {
        try {
            JSONObject payload = event.getPayload();
            String localName = this.client.getLocalPlayer().getName();
            if (payload.has("player-health")) {
                String targetName = payload.getString("name");
                if (targetName.equals(localName))
                    return;
                JSONObject statusJSON = payload.getJSONObject("player-health");
                synchronized (this.partyMembers) {
                    SocketHealingPlayer playerHealth = this.partyMembers.get(targetName);
                    if (playerHealth == null) {
                        playerHealth = SocketHealingPlayer.fromJSON(statusJSON);
                        this.partyMembers.put(targetName, playerHealth);
                    } else {
                        playerHealth.parseJSON(statusJSON);
                    }
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Subscribe
    public void onSocketPlayerLeave(SocketPlayerLeave event) {
        String target = event.getPlayerName();
        synchronized (this.partyMembers) {
            if (this.partyMembers.containsKey(target))
                this.partyMembers.remove(target);
        }
    }

    public Map<String, SocketHealingPlayer> getPartyMembers() {
        return this.partyMembers;
    }
}
