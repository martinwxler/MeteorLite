package meteor.plugins.hiscorewise;

import static meteor.plugins.hiscorewise.HiscoreWisePlugin.skillOverviewPanel;
import com.google.common.base.Strings;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import javax.inject.Inject;
import meteor.MeteorLiteClientLauncher;
import meteor.config.ConfigManager;
import meteor.ui.MeteorUI;
import meteor.ui.components.ToolbarButton;
import net.runelite.api.Skill;
import net.runelite.http.api.hiscore.HiscoreEndpoint;
import net.runelite.http.api.wiseoldman.WiseOldManClient;
import net.runelite.http.api.wiseoldman.model.BasicResponse;
import org.sponge.util.Logger;

public class BasicSnapshotController {

  @FXML
  AnchorPane hiscorePanel;

  @Inject
  MeteorUI ui;

  @Inject
  ConfigManager configManager;

  private WiseOldManClient wiseOldManClient;

  public static ArrayList<ToolbarButton> buttons = new ArrayList<>();
  public static AnchorPane staticAP;
  public static JFXTextField searchBox;
  private static final int MAX_USERNAME_LENGTH = 12;
  private boolean loading = false;
  public static HiscoreEndpoint endpoint;
  public static BasicSnapshotController INSTANCE;
  private JFXButton attackButton;
  private JFXButton strengthButton;
  private JFXButton hitpointsButton;
  private JFXButton miningButton;
  private JFXButton agilityButton;
  private JFXButton smithingButton;
  private JFXButton defenceButton;
  private JFXButton rangedButton;
  private JFXButton prayerButton;
  private JFXButton magicButton;
  private JFXButton runecraftButton;
  private JFXButton constructionButton;
  private JFXButton herbloreButton;
  private JFXButton thievingButton;
  private JFXButton craftingButton;
  private JFXButton fletchingButton;
  private JFXButton slayerButton;
  private JFXButton hunterButton;
  private JFXButton fishingButton;
  private JFXButton cookingButton;
  private JFXButton firemakingButton;
  private JFXButton woodcuttingButton;
  private JFXButton farmingButton;
  private JFXButton overallButton;
  public static Skill lastSkillClicked;

  Text displayNameLabel;
  Text typeLabel;
  Text buildLabel;
  Text combatLvlLabel;
  Text ehpLabel;
  Text ehbLabel;
  Text ttmLabel;
  Text tt200mLabel;
  private String lastLookup = "";
  private BasicResponse lastResult;

  static DecimalFormat formatter = new DecimalFormat("#,###");

  @FXML
  protected void initialize() {
    MeteorLiteClientLauncher.injector.injectMembers(this);
    staticAP = hiscorePanel;
    wiseOldManClient = new WiseOldManClient();
    INSTANCE = this;

    Text panelName = new Text("WiseOldMan Lookup");
    panelName.setFill(Paint.valueOf("CYAN"));
    AnchorPane.setTopAnchor(panelName, 10.0);
    AnchorPane.setLeftAnchor(panelName, 130.0);
    hiscorePanel.getChildren().add(panelName);

    searchBox = new JFXTextField();
    searchBox.getStylesheets().add("css/plugins/jfx-textfield.css");
    AnchorPane.setTopAnchor(searchBox, 30.0);
    AnchorPane.setLeftAnchor(searchBox, 5.0);
    AnchorPane.setRightAnchor(searchBox, 50.0);

    String lastSearch = configManager.getConfiguration("wiseTracker", "lastSearch");
    if (lastSearch != null)
      searchBox.setText(lastSearch);

    hiscorePanel.getChildren().add(searchBox);

    JFXButton searchButton = new JFXButton();
    searchButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(searchButton, 30.0);
    AnchorPane.setLeftAnchor(searchButton, 300.0);
    AnchorPane.setRightAnchor(searchButton, 5.0);
    FontAwesomeIconView icon = new FontAwesomeIconView(FontAwesomeIcon.SEARCH);
    icon.setFill(Paint.valueOf("CYAN"));
    searchButton.setGraphic(icon);
    searchButton.pressedProperty().addListener((options, oldValue, pressed) -> {
      if (!pressed) {
        return;
      }

      if (!loading) {
        lookup();
      }
    });
    hiscorePanel.getChildren().add(searchButton);

    setupBasicInfo();
    setupSkillButtons();
  }

  private void setupBasicInfo() {
    displayNameLabel = new Text();
    displayNameLabel.setFill(Paint.valueOf("CYAN"));
    displayNameLabel.setLayoutX(5);
    displayNameLabel.setLayoutY(85);
    hiscorePanel.getChildren().add(displayNameLabel);

    typeLabel = new Text();
    typeLabel.setFill(Paint.valueOf("CYAN"));
    typeLabel.setLayoutX(5);
    typeLabel.setLayoutY(100);
    hiscorePanel.getChildren().add(typeLabel);

    buildLabel = new Text();
    buildLabel.setFill(Paint.valueOf("CYAN"));
    buildLabel.setLayoutX(5);
    buildLabel.setLayoutY(115);
    hiscorePanel.getChildren().add(buildLabel);

    combatLvlLabel = new Text();
    combatLvlLabel.setFill(Paint.valueOf("CYAN"));
    combatLvlLabel.setLayoutX(5);
    combatLvlLabel.setLayoutY(130);
    hiscorePanel.getChildren().add(combatLvlLabel);

    ehpLabel = new Text();
    ehpLabel.setFill(Paint.valueOf("CYAN"));
    ehpLabel.setLayoutX(5);
    ehpLabel.setLayoutY(145);
    hiscorePanel.getChildren().add(ehpLabel);

    ehbLabel = new Text();
    ehbLabel.setFill(Paint.valueOf("CYAN"));
    ehbLabel.setLayoutX(5);
    ehbLabel.setLayoutY(160);
    hiscorePanel.getChildren().add(ehbLabel);

    ttmLabel = new Text();
    ttmLabel.setFill(Paint.valueOf("CYAN"));
    ttmLabel.setLayoutX(5);
    ttmLabel.setLayoutY(175);
    hiscorePanel.getChildren().add(ttmLabel);

    tt200mLabel = new Text();
    tt200mLabel.setFill(Paint.valueOf("CYAN"));
    tt200mLabel.setLayoutX(5);
    tt200mLabel.setLayoutY(190);
    hiscorePanel.getChildren().add(tt200mLabel);
  }

  private void setupSkillButtons() {
    if (attackButton != null)
      return;

    attackButton = new JFXButton();
    attackButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(attackButton, getTopAnchor(1));
    AnchorPane.setLeftAnchor(attackButton, getLeftAnchor(1));
    AnchorPane.setRightAnchor(attackButton, getRightAnchor(1));
    Image img = new Image("skill_icons_small/attack.png");
    ImageView view = new ImageView(img);
    attackButton.setGraphic(view);
    attackButton.setVisible(false);
    attackButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      SkillOverviewController.updateSkill(lastResult, Skill.ATTACK);
      ui.updateRightPanel(skillOverviewPanel);
    });
    hiscorePanel.getChildren().add(attackButton);

    strengthButton = new JFXButton();
    strengthButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(strengthButton, getTopAnchor(2));
    AnchorPane.setLeftAnchor(strengthButton, getLeftAnchor(1));
    AnchorPane.setRightAnchor(strengthButton, getRightAnchor(1));
    Image strengthButtonImg = new Image("skill_icons_small/strength.png");
    ImageView strengthButtonView = new ImageView(strengthButtonImg);
    strengthButton.setGraphic(strengthButtonView);
    strengthButton.setVisible(false);
    strengthButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      SkillOverviewController.updateSkill(lastResult, Skill.STRENGTH);
      ui.updateRightPanel(skillOverviewPanel);
    });
    hiscorePanel.getChildren().add(strengthButton);

    defenceButton = new JFXButton();
    defenceButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(defenceButton, getTopAnchor(3));
    AnchorPane.setLeftAnchor(defenceButton, getLeftAnchor(1));
    AnchorPane.setRightAnchor(defenceButton, getRightAnchor(1));
    Image defenceButtonImg = new Image("skill_icons_small/defence.png");
    ImageView defenceButtonView = new ImageView(defenceButtonImg);
    defenceButton.setGraphic(defenceButtonView);
    defenceButton.setVisible(false);
    defenceButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      SkillOverviewController.updateSkill(lastResult, Skill.DEFENCE);
      ui.updateRightPanel(skillOverviewPanel);
    });
    hiscorePanel.getChildren().add(defenceButton);

    rangedButton = new JFXButton();
    rangedButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(rangedButton, getTopAnchor(4));
    AnchorPane.setLeftAnchor(rangedButton, getLeftAnchor(1));
    AnchorPane.setRightAnchor(rangedButton, getRightAnchor(1));
    Image rangedButtonImg = new Image("skill_icons_small/ranged.png");
    ImageView rangedButtonView = new ImageView(rangedButtonImg);
    rangedButton.setGraphic(rangedButtonView);
    rangedButton.setVisible(false);
    rangedButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      SkillOverviewController.updateSkill(lastResult, Skill.RANGED);
      ui.updateRightPanel(skillOverviewPanel);
    });
    hiscorePanel.getChildren().add(rangedButton);

    prayerButton = new JFXButton();
    prayerButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(prayerButton, getTopAnchor(5));
    AnchorPane.setLeftAnchor(prayerButton, getLeftAnchor(1));
    AnchorPane.setRightAnchor(prayerButton, getRightAnchor(1));
    Image prayerButtonImg = new Image("skill_icons_small/prayer.png");
    ImageView prayerButtonView = new ImageView(prayerButtonImg);
    prayerButton.setGraphic(prayerButtonView);
    prayerButton.setVisible(false);
    prayerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      SkillOverviewController.updateSkill(lastResult, Skill.PRAYER);
      ui.updateRightPanel(skillOverviewPanel);
    });
    hiscorePanel.getChildren().add(prayerButton);

    magicButton = new JFXButton();
    magicButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(magicButton, getTopAnchor(6));
    AnchorPane.setLeftAnchor(magicButton, getLeftAnchor(1));
    AnchorPane.setRightAnchor(magicButton, getRightAnchor(1));
    Image magicButtonImg = new Image("skill_icons_small/magic.png");
    ImageView magicButtonView = new ImageView(magicButtonImg);
    magicButton.setGraphic(magicButtonView);
    magicButton.setVisible(false);
    magicButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      SkillOverviewController.updateSkill(lastResult, Skill.MAGIC);
      ui.updateRightPanel(skillOverviewPanel);
    });
    hiscorePanel.getChildren().add(magicButton);

    runecraftButton = new JFXButton();
    runecraftButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(runecraftButton, getTopAnchor(7));
    AnchorPane.setLeftAnchor(runecraftButton, getLeftAnchor(1));
    AnchorPane.setRightAnchor(runecraftButton, getRightAnchor(1));
    Image runecraftButtonImg = new Image("skill_icons_small/runecraft.png");
    ImageView runecraftButtonView = new ImageView(runecraftButtonImg);
    runecraftButton.setGraphic(runecraftButtonView);
    runecraftButton.setVisible(false);
    runecraftButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      SkillOverviewController.updateSkill(lastResult, Skill.RUNECRAFT);
      ui.updateRightPanel(skillOverviewPanel);
    });
    hiscorePanel.getChildren().add(runecraftButton);

    constructionButton = new JFXButton();
    constructionButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(constructionButton, getTopAnchor(8));
    AnchorPane.setLeftAnchor(constructionButton, getLeftAnchor(1));
    AnchorPane.setRightAnchor(constructionButton, getRightAnchor(1));
    Image constructionButtonImg = new Image("skill_icons_small/construction.png");
    ImageView constructionButtonView = new ImageView(constructionButtonImg);
    constructionButton.setGraphic(constructionButtonView);
    constructionButton.setVisible(false);
    constructionButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      SkillOverviewController.updateSkill(lastResult, Skill.CONSTRUCTION);
      ui.updateRightPanel(skillOverviewPanel);
    });
    hiscorePanel.getChildren().add(constructionButton);

    hitpointsButton = new JFXButton();
    hitpointsButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(hitpointsButton, getTopAnchor(1));
    AnchorPane.setLeftAnchor(hitpointsButton, getLeftAnchor(2));
    AnchorPane.setRightAnchor(hitpointsButton, getRightAnchor(2));
    Image hitpointsButtonImg = new Image("skill_icons_small/hitpoints.png");
    ImageView hitpointsButtonView = new ImageView(hitpointsButtonImg);
    hitpointsButton.setGraphic(hitpointsButtonView);
    hitpointsButton.setVisible(false);
    hitpointsButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      SkillOverviewController.updateSkill(lastResult, Skill.HITPOINTS);
      ui.updateRightPanel(skillOverviewPanel);
    });
    hiscorePanel.getChildren().add(hitpointsButton);

    agilityButton = new JFXButton();
    agilityButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(agilityButton, getTopAnchor(2));
    AnchorPane.setLeftAnchor(agilityButton, getLeftAnchor(2));
    AnchorPane.setRightAnchor(agilityButton, getRightAnchor(2));
    Image agilityButtonImg = new Image("skill_icons_small/agility.png");
    ImageView agilityButtonView = new ImageView(agilityButtonImg);
    agilityButton.setGraphic(agilityButtonView);
    agilityButton.setVisible(false);
    agilityButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      SkillOverviewController.updateSkill(lastResult, Skill.AGILITY);
      ui.updateRightPanel(skillOverviewPanel);
    });
    hiscorePanel.getChildren().add(agilityButton);

    herbloreButton = new JFXButton();
    herbloreButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(herbloreButton, getTopAnchor(3));
    AnchorPane.setLeftAnchor(herbloreButton, getLeftAnchor(2));
    AnchorPane.setRightAnchor(herbloreButton, getRightAnchor(2));
    Image herbloreButtonImg = new Image("skill_icons_small/herblore.png");
    ImageView herbloreButtonView = new ImageView(herbloreButtonImg);
    herbloreButton.setGraphic(herbloreButtonView);
    herbloreButton.setVisible(false);
    herbloreButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      SkillOverviewController.updateSkill(lastResult, Skill.HERBLORE);
      ui.updateRightPanel(skillOverviewPanel);
    });
    hiscorePanel.getChildren().add(herbloreButton);

    thievingButton = new JFXButton();
    thievingButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(thievingButton, getTopAnchor(4));
    AnchorPane.setLeftAnchor(thievingButton, getLeftAnchor(2));
    AnchorPane.setRightAnchor(thievingButton, getRightAnchor(2));
    Image thievingButtonImg = new Image("skill_icons_small/thieving.png");
    ImageView thievingButtonView = new ImageView(thievingButtonImg);
    thievingButton.setGraphic(thievingButtonView);
    thievingButton.setVisible(false);
    thievingButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      SkillOverviewController.updateSkill(lastResult, Skill.THIEVING);
      ui.updateRightPanel(skillOverviewPanel);
    });
    hiscorePanel.getChildren().add(thievingButton);

    craftingButton = new JFXButton();
    craftingButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(craftingButton, getTopAnchor(5));
    AnchorPane.setLeftAnchor(craftingButton, getLeftAnchor(2));
    AnchorPane.setRightAnchor(craftingButton, getRightAnchor(2));
    Image craftingButtonImg = new Image("skill_icons_small/crafting.png");
    ImageView craftingButtonView = new ImageView(craftingButtonImg);
    craftingButton.setGraphic(craftingButtonView);
    craftingButton.setVisible(false);
    craftingButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      SkillOverviewController.updateSkill(lastResult, Skill.CRAFTING);
      ui.updateRightPanel(skillOverviewPanel);
    });
    hiscorePanel.getChildren().add(craftingButton);

    fletchingButton = new JFXButton();
    fletchingButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(fletchingButton, getTopAnchor(6));
    AnchorPane.setLeftAnchor(fletchingButton, getLeftAnchor(2));
    AnchorPane.setRightAnchor(fletchingButton, getRightAnchor(2));
    Image fletchingButtonImg = new Image("skill_icons_small/fletching.png");
    ImageView fletchingButtonView = new ImageView(fletchingButtonImg);
    fletchingButton.setGraphic(fletchingButtonView);
    fletchingButton.setVisible(false);
    fletchingButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      SkillOverviewController.updateSkill(lastResult, Skill.FLETCHING);
      ui.updateRightPanel(skillOverviewPanel);
    });
    hiscorePanel.getChildren().add(fletchingButton);

    slayerButton = new JFXButton();
    slayerButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(slayerButton, getTopAnchor(7));
    AnchorPane.setLeftAnchor(slayerButton, getLeftAnchor(2));
    AnchorPane.setRightAnchor(slayerButton, getRightAnchor(2));
    Image slayerButtonImg = new Image("skill_icons_small/slayer.png");
    ImageView slayerButtonView = new ImageView(slayerButtonImg);
    slayerButton.setGraphic(slayerButtonView);
    slayerButton.setVisible(false);
    slayerButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      SkillOverviewController.updateSkill(lastResult, Skill.SLAYER);
      ui.updateRightPanel(skillOverviewPanel);
    });
    hiscorePanel.getChildren().add(slayerButton);

    hunterButton = new JFXButton();
    hunterButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(hunterButton, getTopAnchor(8));
    AnchorPane.setLeftAnchor(hunterButton, getLeftAnchor(2));
    AnchorPane.setRightAnchor(hunterButton, getRightAnchor(2));
    Image hunterButtonImg = new Image("skill_icons_small/hunter.png");
    ImageView hunterButtonView = new ImageView(hunterButtonImg);
    hunterButton.setGraphic(hunterButtonView);
    hunterButton.setVisible(false);
    hunterButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      SkillOverviewController.updateSkill(lastResult, Skill.HUNTER);
      ui.updateRightPanel(skillOverviewPanel);
    });
    hiscorePanel.getChildren().add(hunterButton);

    miningButton = new JFXButton();
    miningButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(miningButton, getTopAnchor(1));
    AnchorPane.setLeftAnchor(miningButton, getLeftAnchor(3));
    AnchorPane.setRightAnchor(miningButton, getRightAnchor(3));
    Image miningButtonImg = new Image("skill_icons_small/mining.png");
    ImageView miningButtonView = new ImageView(miningButtonImg);
    miningButton.setGraphic(miningButtonView);
    miningButton.setVisible(false);
    miningButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      SkillOverviewController.updateSkill(lastResult, Skill.MINING);
      ui.updateRightPanel(skillOverviewPanel);
    });
    hiscorePanel.getChildren().add(miningButton);

    smithingButton = new JFXButton();
    smithingButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(smithingButton, getTopAnchor(2));
    AnchorPane.setLeftAnchor(smithingButton, getLeftAnchor(3));
    AnchorPane.setRightAnchor(smithingButton, getRightAnchor(3));
    Image smithingButtonImg = new Image("skill_icons_small/smithing.png");
    ImageView smithingButtonView = new ImageView(smithingButtonImg);
    smithingButton.setGraphic(smithingButtonView);
    smithingButton.setVisible(false);
    smithingButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      SkillOverviewController.updateSkill(lastResult, Skill.SMITHING);
      ui.updateRightPanel(skillOverviewPanel);
    });
    hiscorePanel.getChildren().add(smithingButton);

    fishingButton = new JFXButton();
    fishingButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(fishingButton, getTopAnchor(3));
    AnchorPane.setLeftAnchor(fishingButton, getLeftAnchor(3));
    AnchorPane.setRightAnchor(fishingButton, getRightAnchor(3));
    Image fishingButtonImg = new Image("skill_icons_small/fishing.png");
    ImageView fishingButtonView = new ImageView(fishingButtonImg);
    fishingButton.setGraphic(fishingButtonView);
    fishingButton.setVisible(false);
    fishingButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      SkillOverviewController.updateSkill(lastResult, Skill.FISHING);
      ui.updateRightPanel(skillOverviewPanel);
    });
    hiscorePanel.getChildren().add(fishingButton);

    cookingButton = new JFXButton();
    cookingButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(cookingButton, getTopAnchor(4));
    AnchorPane.setLeftAnchor(cookingButton, getLeftAnchor(3));
    AnchorPane.setRightAnchor(cookingButton, getRightAnchor(3));
    Image cookingButtonImg = new Image("skill_icons_small/cooking.png");
    ImageView cookingButtonView = new ImageView(cookingButtonImg);
    cookingButton.setGraphic(cookingButtonView);
    cookingButton.setVisible(false);
    cookingButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      SkillOverviewController.updateSkill(lastResult, Skill.COOKING);
      ui.updateRightPanel(skillOverviewPanel);
    });
    hiscorePanel.getChildren().add(cookingButton);

    firemakingButton = new JFXButton();
    firemakingButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(firemakingButton, getTopAnchor(5));
    AnchorPane.setLeftAnchor(firemakingButton, getLeftAnchor(3));
    AnchorPane.setRightAnchor(firemakingButton, getRightAnchor(3));
    Image firemakingButtonImg = new Image("skill_icons_small/firemaking.png");
    ImageView firemakingButtonView = new ImageView(firemakingButtonImg);
    firemakingButton.setGraphic(firemakingButtonView);
    firemakingButton.setVisible(false);
    firemakingButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      SkillOverviewController.updateSkill(lastResult, Skill.FIREMAKING);
      ui.updateRightPanel(skillOverviewPanel);
    });
    hiscorePanel.getChildren().add(firemakingButton);

    woodcuttingButton = new JFXButton();
    woodcuttingButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(woodcuttingButton, getTopAnchor(6));
    AnchorPane.setLeftAnchor(woodcuttingButton, getLeftAnchor(3));
    AnchorPane.setRightAnchor(woodcuttingButton, getRightAnchor(3));
    Image woodcuttingButtonImg = new Image("skill_icons_small/woodcutting.png");
    ImageView woodcuttingButtonView = new ImageView(woodcuttingButtonImg);
    woodcuttingButton.setGraphic(woodcuttingButtonView);
    woodcuttingButton.setVisible(false);
    woodcuttingButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      SkillOverviewController.updateSkill(lastResult, Skill.WOODCUTTING);
      ui.updateRightPanel(skillOverviewPanel);
    });
    hiscorePanel.getChildren().add(woodcuttingButton);

    farmingButton = new JFXButton();
    farmingButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(farmingButton, getTopAnchor(7));
    AnchorPane.setLeftAnchor(farmingButton, getLeftAnchor(3));
    AnchorPane.setRightAnchor(farmingButton, getRightAnchor(3));
    Image farmingButtonImg = new Image("skill_icons_small/farming.png");
    ImageView farmingButtonView = new ImageView(farmingButtonImg);
    farmingButton.setGraphic(farmingButtonView);
    farmingButton.setVisible(false);
    farmingButton.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
      SkillOverviewController.updateSkill(lastResult, Skill.FARMING);
      ui.updateRightPanel(skillOverviewPanel);
    });
    hiscorePanel.getChildren().add(farmingButton);

    overallButton = new JFXButton();
    overallButton.setStyle("-fx-background-color: #252525; -fx-text-fill: CYAN; -jfx-button-type: RAISED;");
    AnchorPane.setTopAnchor(overallButton, getTopAnchor(8));
    AnchorPane.setLeftAnchor(overallButton, getLeftAnchor(3));
    AnchorPane.setRightAnchor(overallButton, getRightAnchor(3));
    Image overallButtonImg = new Image("skill_icons_small/overall.png");
    ImageView overallButtonView = new ImageView(overallButtonImg);
    overallButton.setGraphic(overallButtonView);
    overallButton.setVisible(false);
    hiscorePanel.getChildren().add(overallButton);
  }

  private double getTopAnchor(int row) {
    return 210.0 + ((row - 1) * 35);
  }

  private double getLeftAnchor(int column) {
    return switch (column) {
      case 3 -> 230;
      case 2 -> 140;
      case 1 -> 30;
      default -> -1;
    };
  }

  private double getRightAnchor(int column) {
    return switch (column) {
      case 3 -> 35;
      case 2 -> 130;
      case 1 -> 225;
      default -> -1;
    };
  }

  private static String sanitize(String lookup)
  {
    return lookup.replace('\u00A0', ' ');
  }

  public void lookup()
  {
    Logger.getLogger(getClass()).error("lookup");
    resetButtons();
    final String lookup = sanitize(searchBox.getText());

    if (lastLookup.equals(lookup)) {
      updateWithResult(lastResult);
      return;
    }
    configManager.setConfiguration("wiseTracker", "lastSearch", lookup);
    lastLookup = lookup;

    if (Strings.isNullOrEmpty(lookup))
    {
      return;
    }

    /* RuneScape usernames can't be longer than 12 characters long */
    if (lookup.length() > MAX_USERNAME_LENGTH)
    {
      loading = false;
      return;
    }
    SkillOverviewController.user = lookup;
    searchBox.setEditable(false);
    loading = true;


    Platform.runLater(() -> {
      BasicResponse result = wiseOldManClient.lookup(lookup);
      if (result == null)
        return;
      lastResult = result;
      //successful player search
      searchBox.setEditable(true);
      loading = false;
      updateWithResult(result);
    });
  }

  public void updateWithResult(BasicResponse result) {
    displayNameLabel.setText("Display Name: " + result.displayName);
    displayNameLabel.setVisible(true);
    typeLabel.setText("Type: " + result.type);
    typeLabel.setVisible(true);
    buildLabel.setText("Build: " + result.build);
    buildLabel.setVisible(true);
    combatLvlLabel.setText("Combat LVL: " + result.combatLvl);
    combatLvlLabel.setVisible(true);
    ehpLabel.setText("Efficient Hours Played: " + formatter.format(result.ehp));
    ehpLabel.setVisible(true);
    ehbLabel.setText("Efficient Hours Bossed: " + formatter.format(result.ehb));
    ehbLabel.setVisible(true);
    ttmLabel.setText("Hours To Max: " + formatter.format(result.ttm));
    ttmLabel.setVisible(true);
    tt200mLabel.setText("Hours To all 200m: " + formatter.format(result.tt200m));
    tt200mLabel.setVisible(true);
    attackButton.setText("  " + result.latestSnapshot.attack.lvl);
    attackButton.setVisible(true);
    strengthButton.setText("  " + result.latestSnapshot.strength.lvl);
    strengthButton.setVisible(true);
    defenceButton.setText("  " + result.latestSnapshot.defence.lvl);
    defenceButton.setVisible(true);
    rangedButton.setText("  " + result.latestSnapshot.ranged.lvl);
    rangedButton.setVisible(true);
    prayerButton.setText("  " + result.latestSnapshot.prayer.lvl);
    prayerButton.setVisible(true);
    magicButton.setText("  " + result.latestSnapshot.magic.lvl);
    magicButton.setVisible(true);
    runecraftButton.setText("  " + result.latestSnapshot.runecrafting.lvl);
    runecraftButton.setVisible(true);
    constructionButton.setText("  " + result.latestSnapshot.construction.lvl);
    constructionButton.setVisible(true);
    hitpointsButton.setText("  " + result.latestSnapshot.hitpoints.lvl);
    hitpointsButton.setVisible(true);
    miningButton.setText("  " + result.latestSnapshot.mining.lvl);
    miningButton.setVisible(true);
    agilityButton.setText("  " + result.latestSnapshot.agility.lvl);
    agilityButton.setVisible(true);
    herbloreButton.setText("  " + result.latestSnapshot.herblore.lvl);
    herbloreButton.setVisible(true);
    thievingButton.setText("  " + result.latestSnapshot.thieving.lvl);
    thievingButton.setVisible(true);
    craftingButton.setText("  " + result.latestSnapshot.crafting.lvl);
    craftingButton.setVisible(true);
    fletchingButton.setText("  " + result.latestSnapshot.fletching.lvl);
    fletchingButton.setVisible(true);
    slayerButton.setText("  " + result.latestSnapshot.slayer.lvl);
    slayerButton.setVisible(true);
    hunterButton.setText("  " + result.latestSnapshot.hunter.lvl);
    hunterButton.setVisible(true);
    smithingButton.setText("  " + result.latestSnapshot.smithing.lvl);
    smithingButton.setVisible(true);
    fishingButton.setText("  " + result.latestSnapshot.fishing.lvl);
    fishingButton.setVisible(true);
    cookingButton.setText("  " + result.latestSnapshot.cooking.lvl);
    cookingButton.setVisible(true);
    firemakingButton.setText("  " + result.latestSnapshot.firemaking.lvl);
    firemakingButton.setVisible(true);
    woodcuttingButton.setText("  " + result.latestSnapshot.woodcutting.lvl);
    woodcuttingButton.setVisible(true);
    farmingButton.setText("  " + result.latestSnapshot.farming.lvl);
    farmingButton.setVisible(true);
    overallButton.setText("  " + result.latestSnapshot.overall.lvl);
    overallButton.setVisible(true);
  }

  private void resetButtons() {
    attackButton.setText("");
    attackButton.setVisible(false);
    strengthButton.setText("");
    strengthButton.setVisible(false);
    defenceButton.setText("");
    defenceButton.setVisible(false);
    rangedButton.setText("");
    rangedButton.setVisible(false);
    prayerButton.setText("");
    prayerButton.setVisible(false);
    magicButton.setText("");
    magicButton.setVisible(false);
    runecraftButton.setText("");
    runecraftButton.setVisible(false);
    constructionButton.setText("");
    constructionButton.setVisible(false);
    hitpointsButton.setText("");
    hitpointsButton.setVisible(false);
    miningButton.setText("");
    miningButton.setVisible(false);
    agilityButton.setText("");
    agilityButton.setVisible(false);
    herbloreButton.setText("");
    herbloreButton.setVisible(false);
    thievingButton.setText("");
    thievingButton.setVisible(false);
    craftingButton.setText("");
    craftingButton.setVisible(false);
    fletchingButton.setText("");
    fletchingButton.setVisible(false);
    slayerButton.setText("");
    slayerButton.setVisible(false);
    hunterButton.setText("");
    hunterButton.setVisible(false);
    smithingButton.setText("");
    smithingButton.setVisible(false);
    fishingButton.setText("");
    fishingButton.setVisible(false);
    cookingButton.setText("");
    cookingButton.setVisible(false);
    firemakingButton.setText("");
    firemakingButton.setVisible(false);
    woodcuttingButton.setText("");
    woodcuttingButton.setVisible(false);
    farmingButton.setText("");
    farmingButton.setVisible(false);
    overallButton.setText("");
    overallButton.setVisible(false);
  }

}
