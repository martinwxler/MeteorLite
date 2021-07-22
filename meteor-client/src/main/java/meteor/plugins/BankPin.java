package meteor.plugins;

import javax.inject.Inject;
import meteor.Plugin;
import meteor.callback.ClientThread;
import meteor.eventbus.Subscribe;
import net.runelite.api.ScriptID;
import net.runelite.api.VarClientStr;
import net.runelite.api.events.ScriptCallbackEvent;
import net.runelite.api.widgets.JavaScriptCallback;
import net.runelite.api.widgets.Widget;
import org.sponge.util.Logger;

public class BankPin extends Plugin {

  @Inject
  private ClientThread clientThread;

  @Inject
  private Logger log;

  @Subscribe
  public void onScriptCallbackEvent(ScriptCallbackEvent event) {
    int[] intStack = client.getIntStack();
    String[] stringStack = client.getStringStack();
    int intStackSize = client.getIntStackSize();
    int stringStackSize = client.getStringStackSize();

    switch (event.getEventName()) {
      case "bankpinButtonSetup": {
        final int compId = intStack[intStackSize - 2];
        final int buttonId = intStack[intStackSize - 1];
        Widget button = client.getWidget(compId);
        Widget buttonRect = button.getChild(0);

        final Object[] onOpListener = buttonRect.getOnOpListener();
        buttonRect.setOnKeyListener((JavaScriptCallback) e ->
        {
          int typedChar = e.getTypedKeyChar() - '0';
          if (typedChar != buttonId) {
            return;
          }

          log.debug("Bank pin keypress");

          final String chatboxTypedText = client.getVar(VarClientStr.CHATBOX_TYPED_TEXT);
          final String inputText = client.getVar(VarClientStr.INPUT_TEXT);
          clientThread.invokeLater(() ->
          {
            // reset chatbox input to avoid pin going to chatbox..
            client.setVar(VarClientStr.CHATBOX_TYPED_TEXT, chatboxTypedText);
            client.runScript(ScriptID.CHAT_PROMPT_INIT);
            client.setVar(VarClientStr.INPUT_TEXT, inputText);
            client.runScript(ScriptID.CHAT_TEXT_INPUT_REBUILD, "");

            client.runScript(onOpListener);
          });
        });
        break;
      }
    }
  }
}
