package meteor.plugins.cettitutorial;

import meteor.config.*;

@ConfigGroup("cettitutorial")
public interface CettiTutorialConfig extends Config {

    @ConfigItem(
            position = 10,
            name = "Username",
            keyName = "userName",
            description = "Select a game mode."
    )
    default String userName() {return "";}

    @ConfigItem(
            position = 20,
            name = "Game Mode",
            keyName = "gameMode",
            description = "Select a game mode."
    )
    default Methods.GameMode gameMode() {return Methods.GameMode.REGULAR;}

    @ConfigItem(
            position = 25,
            name = "Group Iron",
            keyName = "groupIron",
            description = "Choose to be Group Ironman"
    )
    default boolean groupIron() {return false;}

    @ConfigItem(
            position = 30,
            name = "Required Bank Pin",
            keyName = "bankPin",
            description = "Enter your bank pin. Must be used for ironman modes.",
            hidden = true,
            unhide = "gameMode",
            unhideValue = "Ironman || Hardcore Ironman || Ultimate Ironman"
    )
    default String bankPin() {return "";}

    @ConfigItem(
            position = 40,
            name = "Customize Appearance",
            keyName = "customAppearance",
            description = "Enable customization features."
    )
    default boolean customAppearance() {return false;}

    @ConfigItem(
            position = 50,
            name = "Set Female",
            keyName = "setFemale",
            description = "Start as female.",
            hidden = true,
            unhide = "customAppearance",
            unhideValue = "true"

    )
    default boolean setFemale() {return false;}

    @ConfigItem(
            position = 60,
            name = "Random Appearance",
            keyName = "randomAppearance",
            description = "Set a random appearance.",
            hidden = true,
            unhide = "customAppearance",
            unhideValue = "true"
    )
    default boolean randomAppearance() {return false;}


    @ConfigItem(
            position = 70,
            name = "Run to GE",
            keyName = "runToGE",
            description = "Runs to GE on completion."
    )
    default boolean runToGE() {return false;}

//    @Icon
//    @ConfigItem(
//            name = "Start/Stop",
//            keyName = "startButton",
//            description = "Starts and stops the script."
//    )
//    default Button startButton() {
//        return new Button();
//    }


}
