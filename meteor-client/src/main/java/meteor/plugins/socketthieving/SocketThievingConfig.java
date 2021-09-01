package meteor.plugins.socketthieving;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;
import meteor.config.Range;

@ConfigGroup("thieving")
public interface SocketThievingConfig extends Config {

    @Range(textInput = true)
    @ConfigItem(
            keyName = "grubRate",
            name = "Average grubs",
            description = "Average grubs per chest (225 = 2.25) (based on team size and thieving levels)",
            position = 1
    )
    default int grubRate() {
        return 225;
    }

    @Range(textInput = true)
    @ConfigItem(
            keyName = "gumdropFactor",
            name = "Gumdrop Highlight Factor",
            description = "Highlight bat chests in pretty colors idk why this is a text field",
            position = 2
    )
    default int gumdropFactor() {
        return 0;
    }

    @ConfigItem(
            keyName = "highlightBats",
            name = "Highlight Potential Bats",
            description = "Highlight bat chests",
            position = 3
    )
    default boolean highlightBatChests() {
        return true;
    }

    @ConfigItem(
            keyName = "thievingRateMsg",
            name = "Chest Success Rate Message",
            description = "Puts a game message of your success rate in the completed thieving room",
            position = 4
    )
    default boolean thievingRateMsg() { return true; }

    @ConfigItem(
            keyName = "display4Prep",
            name = "Display Overlay for CM Prepper",
            description = "Displays the overlay when you are in Prep or Thieving. Have to be in Socket to accurately display the information.",
            position = 5
    )
    default boolean display4Prep() {
        return true;
    }

    @ConfigItem(
            keyName = "covidMsg",
            name = "Bats Message",
            description = "Quarantine you disgusting fuck",
            position = 6
    )
    default boolean covidMsg() {
        return true;
    }

    @ConfigItem(
            keyName = "dumpMsg",
            name = "Dump Message",
            description = "Puts a message in game chat of when to dump based off your current raids party size",
            position = 7
    )
    default boolean dumpMsg() {
        return true;
    }

    @ConfigItem(
            keyName = "displayMinGrubs",
            name = "Display Minimum Grubs Needed",
            description = "Puts how many grubs you need minimum per team size next to the total amount of grubs",
            position = 8
    )
    default boolean displayMinGrubs() {
        return true;
    }
}
