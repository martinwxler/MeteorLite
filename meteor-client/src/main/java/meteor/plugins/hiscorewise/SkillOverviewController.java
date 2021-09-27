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


    int lineChartOffset = -1;
    ArrayList<Snapshot> fiveMinSnapshots = wiseOldManClient.lookupSnapshots(user, Period.FIVE_MIN);
    ArrayList<Snapshot> daySnapshots = wiseOldManClient.lookupSnapshots(user, Period.DAY);
    ArrayList<Snapshot> weekSnapshots = wiseOldManClient.lookupSnapshots(user, Period.WEEK);
    ArrayList<Snapshot> monthSnapshots = wiseOldManClient.lookupSnapshots(user, Period.MONTH);
    ArrayList<Snapshot> yearSnapshots = wiseOldManClient.lookupSnapshots(user, Period.YEAR);
    if (fiveMinSnapshots != null && fiveMinSnapshots.size() > 0) {
      if (lineChartOffset == -1)
        lineChartOffset = 90;
      else
        lineChartOffset += 400;
      final NumberAxis xAxis = new NumberAxis();
      final NumberAxis yAxis = new NumberAxis();
      xAxis.setLabel("Minute");
      xAxis.setTickLabelFill(Paint.valueOf("CYAN"));
      yAxis.setTickLabelFill(Paint.valueOf("CYAN"));
      xAxis.setForceZeroInRange(false);
      yAxis.setForceZeroInRange(false);
      final LineChart<Number,Number> lineChart =
          new LineChart<>(xAxis,yAxis);
      lineChart.setTitle("5Min");

      XYChart.Series series = new XYChart.Series();
      series.setName(currentSkill.getName());

      for (Snapshot s : fiveMinSnapshots) {
        double day = Double.parseDouble(s.createdAt.split("-")[1] + "." + Integer.parseInt(s.createdAt.split("-")[2].substring(0, 2)) * 3);
        series.getData().add(new XYChart.Data(day, s.getSnapshotFromSkill(currentSkill).xp));
        log.error(day);
      }

      lineChart.setLegendVisible(false);
      lineChart.setCreateSymbols(false);
      lineChart.setMaxWidth(350);
      lineChart.getData().add(series);
      lineChart.setLayoutY(lineChartOffset);
      lineChart.getStylesheets().add("css/xychart.css");
      Text displayNameLabel = new Text(skill.getName());
      displayNameLabel.setFill(Paint.valueOf("CYAN"));
      displayNameLabel.setLayoutX(5);
      displayNameLabel.setLayoutY(85);
      INSTANCE.hiscorePanel.getChildren().add(lineChart);
    }
    if (daySnapshots != null && daySnapshots.size() > 0) {
      if (lineChartOffset == -1)
        lineChartOffset = 90;
      else
        lineChartOffset += 400;
      final NumberAxis xAxis = new NumberAxis();
      final NumberAxis yAxis = new NumberAxis();
      xAxis.setLabel("Minute");
      xAxis.setTickLabelFill(Paint.valueOf("CYAN"));
      yAxis.setTickLabelFill(Paint.valueOf("CYAN"));
      xAxis.setForceZeroInRange(false);
      yAxis.setForceZeroInRange(false);
      final LineChart<Number,Number> lineChart =
          new LineChart<>(xAxis,yAxis);
      lineChart.setTitle("Day");

      XYChart.Series series = new XYChart.Series();
      series.setName(currentSkill.getName());

      for (Snapshot s : daySnapshots) {
        double day = Double.parseDouble(s.createdAt.split("-")[1] + "." + Integer.parseInt(s.createdAt.split("-")[2].substring(0, 2)) * 3);
        series.getData().add(new XYChart.Data(day, s.getSnapshotFromSkill(currentSkill).xp));
        log.error(day);
      }

      lineChart.setLegendVisible(false);
      lineChart.setCreateSymbols(false);
      lineChart.setMaxWidth(350);
      lineChart.getData().add(series);
      lineChart.setLayoutY(lineChartOffset);
      lineChart.getStylesheets().add("css/xychart.css");
      Text displayNameLabel = new Text(skill.getName());
      displayNameLabel.setFill(Paint.valueOf("CYAN"));
      displayNameLabel.setLayoutX(5);
      displayNameLabel.setLayoutY(85);
      INSTANCE.hiscorePanel.getChildren().add(lineChart);
    }
    if (weekSnapshots != null && weekSnapshots.size() > 0) {
      if (lineChartOffset == -1)
        lineChartOffset = 90;
      else
        lineChartOffset += 400;
      final NumberAxis xAxis = new NumberAxis();
      final NumberAxis yAxis = new NumberAxis();
      xAxis.setLabel("Minute");
      xAxis.setTickLabelFill(Paint.valueOf("CYAN"));
      yAxis.setTickLabelFill(Paint.valueOf("CYAN"));
      xAxis.setForceZeroInRange(false);
      yAxis.setForceZeroInRange(false);
      final LineChart<Number,Number> lineChart =
          new LineChart<>(xAxis,yAxis);
      lineChart.setTitle("Week");

      XYChart.Series series = new XYChart.Series();
      series.setName(currentSkill.getName());

      for (Snapshot s : weekSnapshots) {
        double day = Double.parseDouble(s.createdAt.split("-")[1] + "." + Integer.parseInt(s.createdAt.split("-")[2].substring(0, 2)) * 3);
        series.getData().add(new XYChart.Data(day, s.getSnapshotFromSkill(currentSkill).xp));
        log.error(day);
      }

      lineChart.setLegendVisible(false);
      lineChart.setCreateSymbols(false);
      lineChart.setMaxWidth(350);
      lineChart.getData().add(series);
      lineChart.setLayoutY(lineChartOffset);
      lineChart.getStylesheets().add("css/xychart.css");
      Text displayNameLabel = new Text(skill.getName());
      displayNameLabel.setFill(Paint.valueOf("CYAN"));
      displayNameLabel.setLayoutX(5);
      displayNameLabel.setLayoutY(85);
      INSTANCE.hiscorePanel.getChildren().add(lineChart);
    }
    if (monthSnapshots != null && monthSnapshots.size() > 0) {
      if (lineChartOffset == -1)
        lineChartOffset = 90;
      else
        lineChartOffset += 400;
      final NumberAxis xAxis = new NumberAxis();
      final NumberAxis yAxis = new NumberAxis();
      xAxis.setLabel("Minute");
      xAxis.setTickLabelFill(Paint.valueOf("CYAN"));
      yAxis.setTickLabelFill(Paint.valueOf("CYAN"));
      xAxis.setForceZeroInRange(false);
      yAxis.setForceZeroInRange(false);
      final LineChart<Number,Number> lineChart =
          new LineChart<>(xAxis,yAxis);
      lineChart.setTitle("Month");

      XYChart.Series series = new XYChart.Series();
      series.setName(currentSkill.getName());

      for (Snapshot s : monthSnapshots) {
        double day = Double.parseDouble(s.createdAt.split("-")[1] + "." + Integer.parseInt(s.createdAt.split("-")[2].substring(0, 2)) * 3);
        series.getData().add(new XYChart.Data(day, s.getSnapshotFromSkill(currentSkill).xp));
        log.error(day);
      }

      lineChart.setLegendVisible(false);
      lineChart.setCreateSymbols(false);
      lineChart.setMaxWidth(350);
      lineChart.getData().add(series);
      lineChart.setLayoutY(lineChartOffset);
      lineChart.getStylesheets().add("css/xychart.css");
      Text displayNameLabel = new Text(skill.getName());
      displayNameLabel.setFill(Paint.valueOf("CYAN"));
      displayNameLabel.setLayoutX(5);
      displayNameLabel.setLayoutY(85);
      INSTANCE.hiscorePanel.getChildren().add(lineChart);
    }
    if (yearSnapshots != null && yearSnapshots.size() > 0) {
      if (lineChartOffset == -1)
        lineChartOffset = 90;
      else
        lineChartOffset += 400;
      final NumberAxis xAxis = new NumberAxis();
      final NumberAxis yAxis = new NumberAxis();
      xAxis.setLabel("Month");
      xAxis.setTickLabelFill(Paint.valueOf("CYAN"));
      yAxis.setTickLabelFill(Paint.valueOf("CYAN"));
      xAxis.setForceZeroInRange(false);
      yAxis.setForceZeroInRange(false);
      final LineChart<Number,Number> lineChart =
          new LineChart<>(xAxis,yAxis);
      lineChart.setTitle("Year");

      XYChart.Series series = new XYChart.Series();
      series.setName(currentSkill.getName());

      for (Snapshot s : yearSnapshots) {
        double day = Double.parseDouble(s.createdAt.split("-")[1] + "." + Integer.parseInt(s.createdAt.split("-")[2].substring(0, 2)) * 3);
        series.getData().add(new XYChart.Data(day, s.getSnapshotFromSkill(currentSkill).xp));
        log.error(day);
      }

      lineChart.setLegendVisible(false);
      lineChart.setCreateSymbols(false);
      lineChart.setMaxWidth(350);
      lineChart.getData().add(series);
      lineChart.setLayoutY(lineChartOffset);
      lineChart.getStylesheets().add("css/xychart.css");
      Text displayNameLabel = new Text(skill.getName());
      displayNameLabel.setFill(Paint.valueOf("CYAN"));
      displayNameLabel.setLayoutX(5);
      displayNameLabel.setLayoutY(85);
      INSTANCE.hiscorePanel.getChildren().add(lineChart);
    }
  }
}
