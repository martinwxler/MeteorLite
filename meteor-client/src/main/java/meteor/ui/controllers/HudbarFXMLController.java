package meteor.ui.controllers;

import static meteor.MeteorLite.injector;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXTextArea;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javax.imageio.ImageIO;
import javax.inject.Inject;
import meteor.MeteorLite;
import meteor.eventbus.EventBus;
import meteor.eventbus.Subscribe;
import meteor.plugins.itemstats.Effect;
import meteor.plugins.itemstats.ItemStatChangesService;
import meteor.plugins.itemstats.StatChange;
import meteor.util.XpTable;
import net.runelite.api.Client;
import net.runelite.api.MenuEntry;
import net.runelite.api.Player;
import net.runelite.api.Skill;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.StatChanged;
import net.runelite.api.widgets.WidgetInfo;

public class HudbarFXMLController {

  @Inject
  Client client;

  @Inject
  EventBus eventBus;

  @Inject
  ItemStatChangesService service;

  Player p;
  HashMap<String, Image> skillIconMap = new HashMap<>();

  boolean updatingHud = false;

  @FXML
  private JFXButton pluginsButton;
  @FXML
  private JFXButton optionsButton;
  @FXML
  private Text title;
  @FXML
  private JFXProgressBar healthBar;
  @FXML
  private JFXProgressBar prayerBar;
  @FXML
  private JFXProgressBar xpBar;
  @FXML
  private JFXTextArea healthText;
  @FXML
  private JFXTextArea prayerText;
  @FXML
  private JFXTextArea xpText;
  @FXML
  private JFXTextArea currentLevel;
  @FXML
  private JFXTextArea nextLevel;
  @FXML
  private ImageView currentSkillIcon;

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

  //TODO: Do this better.
  @Subscribe
  public void onClientTick(ClientTick event) {
    if (p != null && !updatingHud) {
      Platform.runLater(() -> {
        updatingHud = true; //prevent stacking runs
        int i = getRestoreValue(Skill.HITPOINTS.getName());
        if (i > 0) {
          double d = ((client.getBoostedSkillLevel(Skill.HITPOINTS) + i) / (double) client
              .getRealSkillLevel(Skill.HITPOINTS));
          if (d <= 1.0) {
            healthBar.lookup(".secondary-bar").lookup(".secondary-bar")
                .setStyle("-fx-background-color: #087f23");
          } else {
            healthBar.lookup(".secondary-bar").lookup(".secondary-bar")
                .setStyle("-fx-background-color: #ffeb3b");
          }
          healthBar.setSecondaryProgress(d);
        } else {
          healthBar.setSecondaryProgress(0.0);
        }

        updatingHud = false;
      });
    }
  }

  @Subscribe
  public void onGameTick(GameTick event) {
    if (p != null) {
      Platform.runLater(() -> {
        healthBar.setProgress((client.getBoostedSkillLevel(Skill.HITPOINTS) / (double) client
            .getRealSkillLevel(Skill.HITPOINTS)));
        healthText.setText(client.getBoostedSkillLevel(Skill.HITPOINTS) + "");
        prayerBar.setProgress((client.getBoostedSkillLevel(Skill.PRAYER) / (double) client
            .getRealSkillLevel(Skill.PRAYER)));
        prayerText.setText(client.getBoostedSkillLevel(Skill.PRAYER) + "");
      });
    } else {
      p = client.getLocalPlayer();
    }
  }

  @Subscribe
  public void onStatChanged(StatChanged event) {
    XpTable currentLvl = XpTable.of(client.getSkillExperience(event.getSkill()));
    if (currentLvl == null) {
      return;
    }
    int currentSkillXP = client.getSkillExperience(event.getSkill());
    int currentLvlProgress = currentSkillXP - currentLvl.startXP;
    double nextLvlPercentage = (currentLvlProgress / (double) currentLvl.neededXP);
    if (p != null) {
      Platform.runLater(() -> {
        if (event.getSkill() != Skill.HITPOINTS) {
          if (currentLvl.getLvl() == 999) {
            currentLevel.setText("MAX");
            nextLevel.setText("MAX");
            xpText.setVisible(false);
          } else {
            currentLevel.setText(event.getLevel() + "");
            nextLevel.setText(event.getLevel() + 1 + "");
            xpText.setVisible(true);
          }
        }

        if (event.getSkill() != Skill.HITPOINTS) {
          xpBar.setProgress(nextLvlPercentage);
          String s = event.getSkill().toString();
          if (skillIconMap.get(s) == null) {
            try {
              skillIconMap.put(s, SwingFXUtils.toFXImage(ImageIO.read(
                  Objects.requireNonNull(ClassLoader.getSystemClassLoader()
                      .getResource("skill_icons/" + s.toLowerCase() + ".png"))),
                  null));
            } catch (IOException e) {
              e.printStackTrace();
            }
          }
          currentSkillIcon.setImage(skillIconMap.get(s));
          String no = String.format("%.4f", nextLvlPercentage * 100);
          int actionsLeft = 0;
          if (event.getXpChange() != 0) {
            actionsLeft =
                (currentLvl.neededXP - (currentSkillXP - currentLvl.startXP)) / event.getXpChange();
            actionsLeft++; // Always +1
            xpText.setText(no + "%  (" + actionsLeft + ")");
          }
        }
      });
    } else {
      p = client.getLocalPlayer();
    }
  }

  private int getRestoreValue(String skill) {
    final MenuEntry[] menu = client.getMenuEntries();
    final int menuSize = menu.length;
    final MenuEntry entry = menuSize > 0 ? menu[menuSize - 1] : null;
    int restoreValue = 0;

    if (entry != null && entry.getParam1() == WidgetInfo.INVENTORY.getId()) {
      final Effect change = service.getItemStatChanges(entry.getIdentifier());

      if (change != null) {
        for (final StatChange c : change.calculate(client).getStatChanges()) {
          final int value = c.getTheoretical();

          if (value != 0 && c.getStat().getName().equals(skill)) {
            restoreValue = value;
          }
        }
      }
    }

    return restoreValue;
  }
}