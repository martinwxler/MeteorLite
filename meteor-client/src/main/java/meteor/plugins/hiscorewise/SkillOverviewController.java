package meteor.plugins.hiscorewise;

import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import meteor.MeteorLiteClientLauncher;
import net.runelite.api.Skill;
import net.runelite.http.api.wiseoldman.Period;
import net.runelite.http.api.wiseoldman.WiseOldManClient;
import net.runelite.http.api.wiseoldman.model.Snapshot;
import org.sponge.util.Logger;

public class SkillOverviewController {

  public static AnchorPane staticAP;
  public static SkillOverviewController INSTANCE;
  public static String user;

  @FXML
  AnchorPane hiscorePanel;

  private static WiseOldManClient wiseOldManClient;

  private static Skill currentSkill;

  private static Logger log = Logger.getLogger(SkillOverviewController.class);

  @FXML
  protected void initialize() {
    MeteorLiteClientLauncher.injector.injectMembers(this);
    staticAP = hiscorePanel;
    wiseOldManClient = new WiseOldManClient();
    INSTANCE = this;
  }

  public static void updateSkill(Skill skill) {
    INSTANCE.hiscorePanel.getChildren().clear();
    currentSkill = skill;

    final NumberAxis xAxis = new NumberAxis();
    final NumberAxis yAxis = new NumberAxis();
    xAxis.setLabel("Month");
    xAxis.setTickLabelFill(Paint.valueOf("CYAN"));
    yAxis.setTickLabelFill(Paint.valueOf("CYAN"));
    xAxis.setForceZeroInRange(false);
    yAxis.setForceZeroInRange(false);
    final LineChart<Number,Number> lineChart =
        new LineChart<>(xAxis,yAxis);
    lineChart.setTitle(currentSkill.getName() + " - Year");

    XYChart.Series series = new XYChart.Series();
    series.setName(currentSkill.getName());

    ArrayList<Snapshot> snapshots = wiseOldManClient.lookupSnapshots(user, Period.YEAR);
    if (snapshots == null)
      return;
    for (Snapshot s : snapshots) {

      double day = Double.parseDouble(s.createdAt.split("-")[1] + "." + s.createdAt.split("-")[2].substring(0, 2));
      series.getData().add(new XYChart.Data(day, s.getSnapshotFromSkill(currentSkill).xp));
      log.error(day);
    }
    lineChart.setLegendVisible(false);
    lineChart.setCreateSymbols(false);
    lineChart.setMaxWidth(350);
    lineChart.getData().add(series);
    lineChart.getStylesheets().add("css/xychart.css");
    Text displayNameLabel = new Text(skill.getName());
    displayNameLabel.setFill(Paint.valueOf("CYAN"));
    displayNameLabel.setLayoutX(5);
    displayNameLabel.setLayoutY(85);
    INSTANCE.hiscorePanel.getChildren().add(lineChart);
  }
}
