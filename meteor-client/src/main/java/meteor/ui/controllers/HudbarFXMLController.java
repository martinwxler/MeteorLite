package meteor.ui.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextArea;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import meteor.MeteorLite;
import meteor.eventbus.EventBus;
import meteor.eventbus.Subscribe;
import meteor.util.XpTable;
import net.runelite.api.Client;
import net.runelite.api.Player;
import net.runelite.api.Skill;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.StatChanged;

import javax.imageio.ImageIO;
import javax.inject.Inject;

import java.io.IOException;
import java.util.HashMap;

import static meteor.MeteorLite.injector;

public class HudbarFXMLController {

    @Inject
    Client client;

    @Inject
    EventBus eventBus;

    Player p;
    HashMap<String, Image> skillIconMap = new HashMap<>();

    @FXML private JFXButton pluginsButton;
    @FXML private JFXButton optionsButton;
    @FXML private Text title;
    @FXML private JFXProgressBar healthBar;
    @FXML private JFXProgressBar prayerBar;
    @FXML private JFXProgressBar xpBar;
    @FXML private JFXTextArea healthText;
    @FXML private JFXTextArea prayerText;
    @FXML private JFXTextArea xpText;
    @FXML private JFXTextArea currentLevel;
    @FXML private JFXTextArea nextLevel;
    @FXML private ImageView currentSkillIcon;
    
    @FXML protected void handlePluginsPressed(ActionEvent event) {
        MeteorLite.togglePluginsPanel();
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
        currentLevel.editableProperty().setValue(false);
        nextLevel.editableProperty().setValue(false);
    }

    @Subscribe
    public void onGameTick(GameTick event)
    {
        if (p != null)
        {
            Platform.runLater(() -> {
                healthBar.setProgress((client.getBoostedSkillLevel(Skill.HITPOINTS) / (double) client.getRealSkillLevel(Skill.HITPOINTS)));
                healthText.setText(client.getBoostedSkillLevel(Skill.HITPOINTS) + "");
                prayerBar.setProgress((client.getBoostedSkillLevel(Skill.PRAYER) / (double) client.getRealSkillLevel(Skill.PRAYER)));
                prayerText.setText(client.getBoostedSkillLevel(Skill.PRAYER) + "");
            });
        }
        else
        p = client.getLocalPlayer();
    }

    @Subscribe
    public void onStatChanged(StatChanged event)
    {
        XpTable currentLvl = XpTable.of(client.getSkillExperience(event.getSkill()));
        if (currentLvl == null)
            return;
        int currentSkillXP = client.getSkillExperience(event.getSkill());
        int currentLvlProgress = currentSkillXP - currentLvl.startXP;
        double nextLvlPercentage = (currentLvlProgress / (double) currentLvl.neededXP);
        if (p != null)
        {
            Platform.runLater(() -> {
                if (currentLvl.getLvl() == 999)
                {
                    currentLevel.setText("MAX");
                    nextLevel.setText("MAX");
                    xpText.setVisible(false);
                }
                else
                {
                    currentLevel.setText(event.getLevel() + "");
                    nextLevel.setText(event.getLevel() + 1 + "");
                    xpText.setVisible(true);
                }

                if (event.getSkill() != Skill.HITPOINTS)
                {
                    xpBar.setProgress(nextLvlPercentage);
                    String s = event.getSkill().toString();
                    if (skillIconMap.get(s) == null)
                    {
                        try {
                            skillIconMap.put(s, SwingFXUtils.toFXImage(ImageIO.read(ClassLoader.getSystemClassLoader().getResource("skill_icons/" + s + ".png")), null));
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    currentSkillIcon.setImage(skillIconMap.get(s));
                    String no = String.format("%.4f", nextLvlPercentage * 100);
                    xpText.setText(no + "%");
                }
            });
        }
        else
            p = client.getLocalPlayer();
    }

}