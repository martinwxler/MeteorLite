package meteor.plugins.void3tteaks;

import meteor.ui.overlay.OverlayLayer;
import meteor.ui.overlay.OverlayPanel;
import meteor.ui.overlay.OverlayPosition;
import meteor.ui.overlay.components.LineComponent;
import meteor.ui.overlay.components.TitleComponent;
import meteor.util.Timer;

import javax.inject.Inject;
import java.awt.*;
import java.text.DecimalFormat;

public class Void3tTeaksInfoOverlay extends OverlayPanel {
  public Timer instanceTimer = new Timer();
  DecimalFormat formatter = new DecimalFormat("#,###");
  Void3tTeaksPlugin plugin;

  @Inject
  public Void3tTeaksInfoOverlay(Void3tTeaksPlugin plugin) {
    setPosition(OverlayPosition.TOP_CENTER);
    setLayer(OverlayLayer.ABOVE_SCENE);
    setResizable(false);
    this.plugin = plugin;
  }

  @Override
  public Dimension render(Graphics2D graphics2D) {
    if (!Void3tTeaksPlugin.enabled) {
      return null;
    }

    panelComponent.getChildren().add(TitleComponent.builder()
        .text("Void 3t Teaks")
        .color(Color.CYAN)
        .build());

    if (instanceTimer != null) {
      panelComponent.getChildren().add(TitleComponent.builder()
          .text(String.format("%02d:%02d:%02d", instanceTimer.getHours(),
              instanceTimer.getMinutes(), instanceTimer.getSeconds()))
          .color(Color.WHITE)
          .build());
    }

    panelComponent.getChildren().add(LineComponent.builder().left("Cut:").leftColor(Color.WHITE)
        .right(formatter.format(plugin.getLogsCut())).rightColor(Color.GREEN).build());
    panelComponent.getChildren().add(LineComponent.builder().left("XP:").leftColor(Color.WHITE)
        .right(formatter.format(plugin.getGainedXP())).rightColor(Color.GREEN).build());
    return super.render(graphics2D);
  }
}
