// 
// Decompiled by Procyon v0.5.36
// 

package meteor.plugins.barbassault;

import meteor.config.*;

import java.awt.*;

@ConfigGroup("barbassaultadditions")
public interface BAConfig extends Config
{
    @ConfigSection(
        name = "General",
        keyName = "general",
        description = "",
        position = 1
    )
    String general = "General";
    
    @ConfigItem(
        name = "Callouts MES",
        keyName = "baCalloutsMES",
        description = "Allows you to one-click callout",
        section = general,
        position = 2
    )
    default boolean shouldLeftClickCall() {
        return false;
    }

    @ConfigSection(
        name = "Attacker",
        keyName = "attackerSection",
        description = "",
        position = 3
    )
    String attackerSection = "Attacker";

    @ConfigItem(
        name = "Mark Called Arrows",
        keyName = "baMarkCalledArrows",
        description = "",
        position = 4,
        section = attackerSection
    )
    default boolean shouldMarkArrows() {
        return false;
    }
    
    @ConfigItem(
        name = "Arrow Marker Color",
        keyName = "baArrowMarkerColor"
        , description = "",
        position = 5,
        section = attackerSection
    )
    default Color getArrowMarkerColor() {
        return Color.WHITE;
    }

    @ConfigSection(
        name = "Defender",
        keyName = "defenderSection",
        description = "",
        position = 6
    )
    String defenderSection = "Defender";

    @ConfigItem(
        name = "Mark Called Bait",
        keyName = "baMarkCalledBait",
        description = "Highlights bait called by your teammate",
        position = 7,
        section = defenderSection
    )
    default boolean shouldMarkBait() {
        return false;
    }
    
    @ConfigItem(
        name = "Bait Marker Color",
        keyName = "baBaitMarkerColor",
        description = "Configures the color to highlight the called bait",
        position = 8,
        section = defenderSection
    )
    default Color getBaitMarkerColor() {
        return Color.WHITE;
    }
    
    @ConfigItem(
        name = "Deprioritize Bait",
        keyName = "baDeprioritizeBait",
        description = "Moves 'Take' menu option for all bait below 'Walk Here'",
        position = 9,
        section = defenderSection
    )
    default boolean shouldDeprioBait() {
        return false;
    }

    @ConfigSection(
        name = "Collector",
        keyName = "collectorSection",
        description = "",
        position = 10
    )
    String collectorSection = "Collector";

    @ConfigItem(
        name = "Deprioritize Incorrect Eggs",
        keyName = "baDeprioritizeIncorrectEggs",
        description = "Shifts the 'Take' entry for each uncalled egg under 'Walk Here'",
        position = 11,
        section = collectorSection
    )
    default boolean shouldDeprioIncEggs() {
        return false;
    }
    
    @ConfigItem(
        name = "Mark Collector Eggs",
        keyName = "baMarkCollectorEggs",
        description = "Marks the called eggs for collectors",
        position = 12,
        section = collectorSection
    )
    default boolean shouldMarkCollectorEggs() {
        return false;
    }

    @ConfigSection(
        name = "Healer",
        keyName = "healerSection",
        description = "",
        position = 13
    )
    String healerSection = "Healer";

    @ConfigItem(
        name = "Mark Called Poison",
        keyName = "baMarkCalledPoison",
        description = "Displays an overlay on the called poison",
        position = 14,
        section = healerSection
    )
    default boolean shouldMarkPoison() {
        return false;
    }

    @Alpha
    @ConfigItem(
        name = "Poison Marker Color",
        keyName = "baPoisonMarkerColor",
        description = "Configures the color for the overlay on marked poison",
        position = 15,
        section = healerSection
    )
    default Color getPoisonMarkerColor() {
        return Color.WHITE;
    }

}
