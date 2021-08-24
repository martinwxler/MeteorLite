package meteor.plugins.api.widgets;

import meteor.plugins.api.commons.Time;
import meteor.plugins.api.game.GameThread;
import meteor.plugins.api.input.Keyboard;
import net.runelite.api.Client;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetID;
import net.runelite.api.widgets.WidgetInfo;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dialog {
    @Inject
    private static Client client;

    // Tutorial island continue dialogs
    public static void continueTutorial() {
        GameThread.invoke(() -> client.runScript(299, 1, 1, 1));
    }

    public static boolean isOpen() {
        Widget widget = Widgets.get(162, 557);
        return widget != null && GameThread.invokeLater(() -> !widget.isHidden());
    }

    public static boolean canContinue() {
        return GameThread.invokeLater(() -> canContinueNPC() || canContinuePlayer() || canContinueDeath()
                || canWeirdContinue() || canWeirderContinue() || canContinueTutIsland() || canContinueTutIsland2()
                || canContinueTutIsland3());
    }

    public static boolean canLevelUpContinue() {
        Widget widget = Widgets.get(WidgetInfo.LEVEL_UP_LEVEL);
        return widget != null && GameThread.invokeLater(() -> !widget.isHidden());
    }

    public static boolean canWeirdContinue() {
        Widget widget = Widgets.get(WidgetInfo.DIALOG2_SPRITE_CONTINUE);
        return widget != null && GameThread.invokeLater(() -> !widget.isHidden());
    }

    public static boolean canWeirderContinue() {
        Widget widget = Widgets.get(193, 3);
        return widget != null && GameThread.invokeLater(() -> !widget.isHidden());
    }

    public static boolean canContinueNPC() {
        Widget widget = Widgets.get(WidgetID.DIALOG_NPC_GROUP_ID, 4);
        return widget != null && GameThread.invokeLater(() -> !widget.isHidden());
    }

    public static boolean canContinuePlayer() {
        Widget widget = Widgets.get(WidgetID.DIALOG_PLAYER_GROUP_ID, 3);
        return widget != null && GameThread.invokeLater(() -> !widget.isHidden());
    }

    public static boolean canContinueDeath() {
        Widget widget = Widgets.get(663, 0);
        return widget != null
                && GameThread.invokeLater(() -> !widget.isHidden())
                && widget.getChild(2) != null
                && GameThread.invokeLater(() -> !widget.getChild(2).isHidden());
    }

    public static boolean canContinueTutIsland() {
        Widget widget = Widgets.get(229, 2);
        return widget != null && GameThread.invokeLater(() -> !widget.isHidden());
    }

    public static boolean canContinueTutIsland2() {
        Widget widget = Widgets.get(WidgetInfo.DIALOG_SPRITE);
        return widget != null
                && GameThread.invokeLater(() -> !widget.isHidden())
                && widget.getChild(2) != null
                && GameThread.invokeLater(() -> !widget.getChild(2).isHidden());
    }

    public static boolean canContinueTutIsland3() {
        Widget widget = Widgets.get(WidgetInfo.CHATBOX_FULL_INPUT);
        return widget != null
                && GameThread.invokeLater(() -> !widget.isHidden())
                && widget.getText().toLowerCase().contains("continue");
    }

    public static boolean isEnterInputOpen() {
        Widget widget = Widgets.get(WidgetInfo.CHATBOX_FULL_INPUT);
        // TODO: make sure GE search isn't open
        return widget != null && GameThread.invokeLater(() -> !widget.isHidden());
    }

    public static void enterInput(String input) {
        Time.sleepUntil(Dialog::isEnterInputOpen, 2000);
        Keyboard.type(input);
    }

    public static void enterInput(int input) {
        enterInput(String.valueOf(input));
    }

    public static boolean isViewingOptions() {
        return !getOptions().isEmpty();
    }

    public static void continueSpace() {
        if (Dialog.isOpen()) {
            Keyboard.sendSpace();
        }
    }

    public static boolean chooseOption(int index) {
        if (isViewingOptions()) {
            Keyboard.type(index);
            return true;
        }

        return false;
    }

    public static boolean chooseOption(String... options) {
        if (isViewingOptions()) {
            for (int i = 0; i < getOptions().size(); i++) {
                Widget widget = getOptions().get(i);
                for (String option : options) {
                    if (widget.getText().contains(option)) {
                        return chooseOption(i + 1);
                    }
                }
            }
        }

        return false;
    }

    public static List<Widget> getOptions() {
        Widget widget = Widgets.get(WidgetID.DIALOG_OPTION_GROUP_ID, 1);
        if (widget == null || GameThread.invokeLater(widget::isHidden)) {
            return Collections.emptyList();
        }

        List<Widget> out = new ArrayList<>();
        Widget[] children = widget.getChildren();
        if (children == null) {
            return out;
        }

        // Skip first child, it's not a dialog option
        for (int i = 1; i < children.length; i++) {
            if (children[i].getText().isBlank()) {
                continue;
            }

            out.add(children[i]);
        }

        return out;
    }
}
