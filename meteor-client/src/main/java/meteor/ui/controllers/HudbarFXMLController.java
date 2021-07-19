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

import javax.inject.Inject;

import static osrs.Launcher.injector;

public class HudbarFXMLController {

    @Inject
    Client client;

    @Inject
    EventBus eventBus;

    Player p;

    @FXML private JFXButton pluginsButton;
    @FXML private JFXButton optionsButton;
    @FXML private Text title;
    @FXML private JFXProgressBar healthBar;
    @FXML private JFXProgressBar prayerBar;
    @FXML private JFXProgressBar xpBar;
    @FXML private JFXTextArea healthText;
    @FXML private JFXTextArea prayerText;
    @FXML private JFXTextArea xpText;
    
    @FXML protected void handlePluginsPressed(ActionEvent event) {
        Launcher.togglePluginsPanel();
    }

    @FXML protected void handleOptionsPressed(ActionEvent event) {

    }

    @FXML
    public void initialize() {
        injector.injectMembers(this);
        eventBus.register(this);
        healthText.editableProperty().setValue(false);
        prayerText.editableProperty().setValue(false);
        xpText.editableProperty().setValue(false);
    }

    @Subscribe
    public void onGameTick(GameTick event)
    {
        if (p != null)
        {
            healthBar.setProgress((client.getBoostedSkillLevel(Skill.HITPOINTS) / (double) client.getRealSkillLevel(Skill.HITPOINTS)));
            healthText.setText(client.getBoostedSkillLevel(Skill.HITPOINTS) + "");
            prayerBar.setProgress((client.getBoostedSkillLevel(Skill.PRAYER) / (double) client.getRealSkillLevel(Skill.PRAYER)));
            prayerText.setText(client.getBoostedSkillLevel(Skill.PRAYER) + "");
        }
        else
        p = client.getLocalPlayer();
    }

    @Subscribe
    public void onStatChanged(StatChanged event)
    {
        XpTable currentLvl = XpTable.of(event.getLevel());
        int currentSkillXP = client.getSkillExperience(event.getSkill());
        int currentLvlProgress = currentSkillXP - currentLvl.startXP;
        double nextLvlPercentage = (currentLvlProgress / (double) currentLvl.neededXP);
        if (p != null)
        {
            xpBar.setProgress(nextLvlPercentage);
            String no = String.format("%.4f", nextLvlPercentage * 100);
            xpText.setText(no + "%");
        }
        else
            p = client.getLocalPlayer();
    }

}