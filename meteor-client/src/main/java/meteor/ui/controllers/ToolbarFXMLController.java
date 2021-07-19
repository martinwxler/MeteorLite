package meteor.ui.controllers;
 
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextArea;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import meteor.eventbus.EventBus;
import meteor.eventbus.Subscribe;
import meteor.util.XpTable;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.Skill;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.StatChanged;
import osrs.Launcher;
import static osrs.Launcher.injector;

import javax.inject.Inject;

public class ToolbarFXMLController {

    @Inject
    Client client;

    @Inject
    EventBus eventBus;

    @FXML private JFXButton pluginsButton;
    @FXML private JFXButton optionsButton;
    @FXML private Text title;
    
    @FXML protected void handlePluginsPressed(ActionEvent event) {
        Launcher.togglePluginsPanel();
    }

    @FXML protected void handleOptionsPressed(ActionEvent event) {
    }

    @FXML
    public void initialize() {
        injector.injectMembers(this);
        eventBus.register(this);
    }
}