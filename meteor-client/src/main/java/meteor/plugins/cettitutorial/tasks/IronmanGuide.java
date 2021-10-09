package meteor.plugins.cettitutorial.tasks;

import meteor.PluginTask;
import meteor.plugins.api.commons.Time;
import meteor.plugins.api.entities.NPCs;
import meteor.plugins.api.entities.Players;
import meteor.plugins.api.entities.TileObjects;
import meteor.plugins.api.game.Game;
import meteor.plugins.api.game.GameThread;
import meteor.plugins.api.input.Keyboard;
import meteor.plugins.api.movement.Movement;
import meteor.plugins.api.widgets.Dialog;
import meteor.plugins.cettitutorial.CettiTutorialConfig;
import meteor.plugins.cettitutorial.CettiTutorialPlugin;
import meteor.plugins.cettitutorial.Methods;
import net.runelite.api.NPC;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.widgets.Widget;
import osrs.PlayerComposition;

import static osrs.Client.logger;

public class IronmanGuide implements PluginTask {

    CettiTutorialConfig config;
    CettiTutorialPlugin plugin;
    public IronmanGuide(CettiTutorialConfig config, CettiTutorialPlugin plugin) {
        super();
        this.config = config;
        this.plugin = plugin;
    }

    @Override
    public boolean validate() {
        return !config.gameMode().toString().equals("Regular") && !CettiTutorialPlugin.ironTypeSet;
    }

    private void talkToGuide() {
        NPC guide = NPCs.getNearest("Iron Man tutor");
        if (Dialog.canContinue()) {
            Dialog.continueSpace();
        } else if (Dialog.isViewingOptions()) {
            logger.info("Viewing options!");
            Dialog.chooseOption("Tell me about", "I'd like to change my");
        }
        else {
            if (guide == null) {
                Movement.walk(new WorldPoint(3130,3086,0));
            } else {
                if (!Players.getLocal().isMoving()) {
                    guide.interact(0);
                }
            }
        }
    }

    private static boolean modeIsSet = false;
    private void setIronmanType() {
//        Widget soloMode = Game.getClient().getWidget(215,4).getChild(0);
//        Widget groupMode = Game.getClient().getWidget(215,4).getChild(9);

        if (Game.getClient().getWidget(215,14).getChild(0).getActions() == null) {
            if (config.groupIron()) {
                Game.getClient().getWidget(215,14).getChild(9).interact("View");
            }
            if (config.gameMode().equals(Methods.GameMode.IRONMAN)) {
                Game.getClient().getWidget(215, 9).interact(0);
                modeIsSet=true;
            } else if (config.gameMode().equals(Methods.GameMode.HARDCORE_IRONMAN)) {
                Game.getClient().getWidget(215, 10).interact(0);
                modeIsSet=true;
            } else if (config.gameMode().equals(Methods.GameMode.ULTIMATE_IRONMAN)) {
                Game.getClient().getWidget(215, 11).interact(0);
                modeIsSet=true;
            }
        } else {
            if (!config.groupIron()) {
                Game.getClient().getWidget(215,14).getChild(0).interact("View");
            }
            if (config.gameMode().equals(Methods.GameMode.IRONMAN)) {
                Game.getClient().getWidget(215, 13).interact(0);
                modeIsSet=true;
            } else if (config.gameMode().equals(Methods.GameMode.HARDCORE_IRONMAN)) {
                Game.getClient().getWidget(215, 31).interact(0);
                modeIsSet=true;
            }
        }

    }


    private void setBankPin() {

        if (Game.getClient().getWidget(213, 0) != null) {
            if (Game.getClient().getWidget(213, 7).getText().contains("enter that number again")) {
                Keyboard.type(config.bankPin());
                CettiTutorialPlugin.ironTypeSet = true;
            } else if (Game.getClient().getWidget(213, 7).getText().contains("Please choose a new")) {
                Keyboard.type(config.bankPin());
            }
        } else if (Dialog.canContinue()) {
            Dialog.continueSpace();
        } else if (Dialog.isViewingOptions()) {
            logger.info("Setting pin.");
            Dialog.chooseOption("Okay, let me set a PIN.");
        } else {
            modeIsSet = false;
        }
    }

    private void checkIronStatus() {
        if (GameThread.invokeLater(() -> Game.getClient().getAccountType().isIronman())) {
            logger.info("Ironman type is set.");
            CettiTutorialPlugin.ironTypeSet = true;
        }
    }

    @Override
    public int execute() {
        logger.info("Setting up Ironman");
        checkIronStatus();
        if (modeIsSet) {
            logger.info("Mode is set");
            setBankPin();
            return 1200;
        } else if (!CettiTutorialPlugin.ironTypeSet){
            if (Game.getClient().getWidget(215, 6) == null) {
                logger.info("Talking to guide");
                talkToGuide();
            } else {
                logger.info("Choosing Ironman type");
                setIronmanType();
            }
        }

        return 1200;
    }
}
