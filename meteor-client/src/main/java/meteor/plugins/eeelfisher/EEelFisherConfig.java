package meteor.plugins.eeelfisher;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;
import meteor.config.Range;

@ConfigGroup("EElFisher")
public interface EEelFisherConfig extends Config {
    @ConfigItem(
            keyName = "type",
            name = "Eel Type",
            description = "The type of eel to fish",
            position = -1
    )
    default FishingType location() {
        return FishingType.INFERNAL;
    }
    @Range(textInput = true)
    @ConfigItem(
            keyName = "tickDelayAVG",
            name = "GameTick delay average",
            description = "The average GameTick delay. This decides how long the game waits before making a decision about changing tasks",
            position = 0
    )
    default int tickDelayAVG() {
        return 5;
    }
    @Range(textInput = true)
    @ConfigItem(
            keyName = "tickDelayDeviation",
            name = "GameTick delay deviation",
            description = "Average GameTick deviation",
            position = 1
    )
    default int tickDelayDeviation() {
        return 2;
    }
    @Range(textInput = true)
    @ConfigItem(
            keyName = "tickDelayMax",
            name = "GameTick delay max",
            description = "The maximum GameTick delay",
            position = 2
    )
    default int tickDelayMax() {
        return 10;
    }
    @Range(textInput = true)
    @ConfigItem(
            keyName = "tickDelayMin",
            name = "GameTick delay minimum",
            description = "The minimum GameTick delay",
            position = 3
    )
    default int tickDelayMin() {
        return 3;
    }
    @Range(textInput = true)
    @ConfigItem(
            keyName = "millisecondDelayAVG",
            name = "Millisecond delay average",
            description = " This delay decides how long to wait to make an action once a choice has been made",
            position = 4
    )
    default int milisecondDelayAVG() {
        return 200;
    }
    @Range(textInput = true)
    @ConfigItem(
            keyName = "millisecondDelayDeviation",
            name = "Millisecond delay deviation",
            description = "Average millisecond deviation",
            position = 5
    )
    default int millisecondDelayDeviation() {
        return 150;
    }
    @Range(textInput = true)
    @ConfigItem(
            keyName = "millisecondDelayMax",
            name = "Millisecond delay max",
            description = "The maximum millisecond delay",
            position = 6
    )
    default int millisecondDelayMax() {
        return 400;
    }
    @Range(textInput = true)
    @ConfigItem(
            keyName = "millisecondDelayMin",
            name = "Millisecond delay minimum",
            description = "The minimum millisecond delay",
            position = 7
    )
    default int millisecondDelayMin() {
        return 100;
    }
}
