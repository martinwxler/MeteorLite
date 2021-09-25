package meteor.plugins.autobankpin;

import static java.awt.event.KeyEvent.CHAR_UNDEFINED;
import static java.awt.event.KeyEvent.KEY_PRESSED;
import static java.awt.event.KeyEvent.KEY_RELEASED;
import static java.awt.event.KeyEvent.KEY_TYPED;
import static java.awt.event.KeyEvent.VK_UNDEFINED;
import static net.runelite.api.widgets.WidgetInfo.BANK_PIN_INSTRUCTION_TEXT;
import com.google.inject.Provides;
import java.awt.event.KeyEvent;
import javax.inject.Inject;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import net.runelite.api.Client;
import net.runelite.api.events.GameTick;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;


@PluginDescriptor(
    name = "Auto Bank Pin",
    description = "Automatically enters your bank pin",
    enabledByDefault = false
)
public final class AutoBankPinPlugin extends Plugin {

    @Inject
    private Client client;

    @Inject
    private AutoBankPinConfig config;

    @Inject
    private ConfigManager configManager;

    private boolean first;
    private boolean second;
    private boolean third;
    private boolean fourth;

    @Provides
    public AutoBankPinConfig getConfig(final ConfigManager configManager) {
        return configManager.getConfig(AutoBankPinConfig.class);
    }

    public void startUp() {
        configManager.setConfiguration("bank", "bankPinKeyboard", true);
    }

    @Subscribe
    public final void onGameTick(GameTick event) {
        if (client.getWidget(WidgetID.BANK_PIN_GROUP_ID, BANK_PIN_INSTRUCTION_TEXT.getChildId()) == null
            || (client.getWidget(BANK_PIN_INSTRUCTION_TEXT).getText() == "First click the FIRST digit."
                && client.getWidget(BANK_PIN_INSTRUCTION_TEXT).getText() == "Now click the SECOND digit."
                && client.getWidget(BANK_PIN_INSTRUCTION_TEXT).getText() == "Time for the THIRD digit."
                && client.getWidget(BANK_PIN_INSTRUCTION_TEXT).getText() == "Finally, the FOURTH digit."))
        {
            return;
        }

        if (config.bankpin().length() != 4) {
            return;
        }
        String pin = config.bankpin();
        char[] digits = pin.toCharArray();
        int charCode = -1;

        Widget bankpintext = client.getWidget(WidgetID.BANK_PIN_GROUP_ID, BANK_PIN_INSTRUCTION_TEXT.getChildId());
        if (bankpintext == null) {
            return;
        }
        switch (bankpintext.getText()) {
            case "First click the FIRST digit." -> {
                if (first) {
                    return;
                }
                charCode = digits[0];
                first = true;
            }
            case "Now click the SECOND digit." -> {
                if (second) {
                    return;
                }
                charCode = digits[1];
                second = true;
            }
            case "Time for the THIRD digit." -> {
                if (third) {
                    return;
                }
                charCode = digits[2];
                third = true;
            }
            case "Finally, the FOURTH digit." -> {
                if (!first && !fourth) {
                    return;
                }
                charCode = digits[3];
                fourth = true;
            }
        }
        if (charCode != -1) {
            sendKey(charCode);

            if (fourth) {
                first = false;
                second = false;
                third = false;
                fourth = false;
            }
        }
    }
    private void sendKey(int c) {
        KeyEvent kvPressed = new KeyEvent(client.getCanvas(), KEY_PRESSED, System.currentTimeMillis(), 0, c, CHAR_UNDEFINED);
        KeyEvent kvTyped = new KeyEvent(client.getCanvas(), KEY_TYPED, System.currentTimeMillis(), 0, VK_UNDEFINED,
            (char) c);
        KeyEvent kvReleased = new KeyEvent(client.getCanvas(), KEY_RELEASED, System.currentTimeMillis(), 0, c, CHAR_UNDEFINED);

        client.getCanvas().dispatchEvent(kvPressed);
        client.getCanvas().dispatchEvent(kvTyped);
        client.getCanvas().dispatchEvent(kvReleased);
    }
}
