package meteor.plugins.highalchemy;

import com.google.inject.Provides;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.api.items.Inventory;
import meteor.plugins.api.magic.Regular;
import meteor.plugins.api.magic.Rune;
import meteor.plugins.api.packets.ItemPackets;
import meteor.plugins.api.packets.MousePackets;
import meteor.plugins.api.packets.SpellPackets;
import meteor.plugins.prayerFlicker.PrayerFlickerConfig;
import meteor.plugins.runepouch.Runes;
import net.runelite.api.ChatMessageType;
import net.runelite.api.GameState;
import net.runelite.api.Item;
import net.runelite.api.Skill;
import net.runelite.api.events.ChatMessage;
import net.runelite.api.events.FakeXpDrop;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.StatChanged;

import javax.inject.Inject;
import java.util.Random;

@PluginDescriptor(
        name = "Auto High Alchemy",
        description = "Cast High Alchemy for you",
        tags = {},
        enabledByDefault = false,
        disabledOnStartup = true
)
public class HighAlchPlugin extends Plugin {
    int timeout = 0;
    Random rand = new Random();
    @Inject
    private HighAlchConfig config;

    @Provides
    public HighAlchConfig getConfig(ConfigManager configManager) {
        return configManager.getConfig(HighAlchConfig.class);
    }
    @Subscribe
    public void onStatChanged(StatChanged event){
        if(event.getSkill()== Skill.MAGIC){
            if(event.getXpChange()!=0){
                timeout=delay();
            }
        }
    }
    @Subscribe
    public void onGameTick(GameTick e){
        if(client.getGameState()!= GameState.LOGGED_IN){
            return;
        }
        if(timeout<=-20){
            timeout=0;
        }
        if(timeout==0){
            Item x = Inventory.getFirst(config.itemID());
            if(x!=null) {
                MousePackets.queueClickPacket(0, 0);
                SpellPackets.spellOnItem(Regular.HIGH_LEVEL_ALCHEMY,x);
            }
        }
        timeout--;
    }
    public int delay() {
        int delay;
        do {
            double val = rand.nextGaussian() * 1 + 3;
            delay = (int) Math.round(val);
        } while (delay <= 1 || delay >= 9);
        logger.debug(delay);
        return delay;
    }
    @Subscribe
    public void onChatMessage(ChatMessage message){
        if(message.getMessage().contains("You do not have enough")){
            client.addChatMessage(ChatMessageType.GAMEMESSAGE, "High Alch Plugin", "missing runes toggling plugin", null);
            this.toggle();
        }
    }
}
