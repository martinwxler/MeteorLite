package meteor.plugins.autoclicker;

import meteor.config.*;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.VK_C;
import static java.awt.event.KeyEvent.VK_H;

@ConfigGroup("soxsautoclicker")
public interface AutoClickerConfig extends Config
{

    @ConfigSection(
            keyName = "title",
            name = "Soxs' AutoClicker",
            description = "",
            position = 0
    )
    String title = "Soxs' AutoClicker";

    @ConfigItem(
            keyName = "toggle",
            name = "Toggle",
            description = "Toggles the auto-clicker.",
            position = 1,
            section = title
    )
    default Keybind toggle()
    {
        return new Keybind(VK_C, InputEvent.CTRL_DOWN_MASK, false);
    }

    @ConfigSection(
            keyName = "clickerConfig",
            name = "Clicker Config",
            description = "",
            position = 5
    )
    String clickerConfig = "Clicker Config";
    @Range(textInput = true)
    @ConfigItem(
            keyName = "minDelay",
            name = "Minimum Delay (ms)",
            description = "Minimum delay between mouse clicks.",
            position = 6,
            section = clickerConfig
    )
    default int minDelay()
    {
        return 1000;
    }

    @Range(textInput = true)
    @ConfigItem(
            keyName = "maxDelay",
            name = "Maximum Delay (ms)",
            description = "Maximum delay between mouse clicks.",
            position = 7,
            section = clickerConfig
    )
    default int maxDelay()
    {
        return 2000;
    }

    @Range(textInput = true)
    @ConfigItem(
            keyName = "target",
            name = "Delay Target",
            description = "",
            position = 8,
            section = clickerConfig
    )
    default int target()
    {
        return 1500;
    }

    @Range(textInput = true)
    @ConfigItem(
            keyName = "deviation",
            name = "Delay Deviation",
            description = "",
            position = 9,
            section = clickerConfig
    )
    default int deviation()
    {
        return 100;
    }

    @ConfigSection(
            keyName = "afkDelayTitle",
            name = "Random AFK",
            description = "",
            position = 10
    )
    String afkDelayTitle = "Random AFK";

    @Range(
            min = 0,
            max = 99,
            textInput = true
    )

    @ConfigItem(
            keyName = "frequencyAFK",
            name = "AFK Frequency (%)",
            description = "% chance to go AFK.",
            position = 11,
            section = afkDelayTitle
    )
    default int frequencyAFK()
    {
        return 3;
    }

    @Range(textInput = true)
    @ConfigItem(
            keyName = "minDelayAFK",
            name = "Min AFK Delay (ms)",
            description = "Minimum AFK delay.",
            position = 12,
            section = afkDelayTitle
    )
    default int minDelayAFK()
    {
        return 5000;
    }

    @Range(textInput = true)
    @ConfigItem(
            keyName = "maxDelayAFK",
            name = "Max AFK Delay (ms)",
            description = "Maximum AFK delay.",
            position = 13,
            section = afkDelayTitle
    )
    default int maxDelayAFK()
    {
        return 20000;
    }

    @Range(
            min = 5,
            max = 9
    )
    @ConfigItem(
            keyName = "weightSkewAFK",
            name = "AFK Skew (Tightness)",
            description = "The degree to which the AFK random weights cluster around the mode of the distribution; higher values mean tighter clustering.",
            position = 14,
            section = afkDelayTitle
    )
    default int weightSkewAFK()
    {
        return 8;
    }

    @Range(
            min = -10,
            max = 10
    )
    @ConfigItem(
            keyName = "weightBiasAFK",
            name = "AFK Bias (Offset)",
            description = "The tendency of the AFK mode to reach the min, max or midpoint value; positive values bias toward max, negative values toward min.",
            position = 15,
            section = afkDelayTitle
    )
    default int weightBiasAFK()
    {
        return 8;
    }
    @ConfigItem(
            keyName = "weightedDistribution",
            name = "Weighted Distribution",
            description = "Shifts the random distribution towards the lower end at the target, otherwise it will be an even distribution",
            position = 16,
            section = clickerConfig
    )
    default boolean weightedDistribution()
    {
        return false;
    }

    @ConfigItem(
            keyName = "followMouse",
            name = "Follow Mouse",
            description = "Click at the mouse location.",
            position = 17,
            section = clickerConfig
    )
    default boolean followMouse()
    {
        return true;
    }

    @ConfigItem(
            keyName = "disableRealMouse",
            name = "Disable Real Mouse",
            description = "Disable the real mouse after the clicker has started, to prevent interference after setting it up.",
            position = 18,
            section = clickerConfig
    )
    default boolean disableRealMouse()
    {
        return true;
    }

    @ConfigItem(
            keyName = "disableUI",
            name = "Disable UI",
            description = "",
            position = 19,
            section = clickerConfig
    )
    default boolean disableUI()
    {
        return false;
    }

    @ConfigSection(
            keyName = "clickerFilters",
            name = "Clicker Filters",
            description = "",
            position = 20
    )
    String clickerFilters = "Clicker Filters";

    @ConfigItem(
            keyName = "skipOnMoving",
            name = "Skip When Moving",
            description = "",
            position = 21,
            section = clickerFilters
    )
    default boolean skipOnMoving()
    {
        return false;
    }

    @ConfigItem(
            keyName = "skipOnInteraction",
            name = "Skip On Interaction",
            description = "",
            position = 22,
            section = clickerFilters
    )
    default boolean skipOnInteraction()
    {
        return false;
    }

    @ConfigItem(
            keyName = "skipOnAnimating",
            name = "Skip On Animating",
            description = "",
            position = 23,
            section = clickerFilters
    )
    default boolean skipOnAnimating()
    {
        return false;
    }

}
