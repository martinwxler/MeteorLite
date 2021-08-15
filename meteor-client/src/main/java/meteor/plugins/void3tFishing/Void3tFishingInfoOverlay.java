package meteor.plugins.void3tFishing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.text.DecimalFormat;
import javax.inject.Inject;
import meteor.ui.overlay.OverlayLayer;
import meteor.ui.overlay.OverlayPanel;
import meteor.ui.overlay.OverlayPosition;
import meteor.ui.overlay.components.LineComponent;
import meteor.ui.overlay.components.TitleComponent;
import meteor.util.Timer;

public class Void3tFishingInfoOverlay extends OverlayPanel {
  public Timer instanceTimer = new Timer();
  DecimalFormat formatter = new DecimalFormat("#,###");
  Void3tFishingPlugin plugin;

  @Inject
  public Void3tFishingInfoOverlay(Void3tFishingPlugin plugin) {
    setPosition(OverlayPosition.TOP_CENTER);
    setLayer(OverlayLayer.ABOVE_SCENE);
    setResizable(false);
    this.plugin = plugin;
  }

  @Override
  public Dimension render(Graphics2D graphics2D) {
    if (!Void3tFishingPlugin.enabled) {
      return null;
    }

    panelComponent.getChildren().add(TitleComponent.builder()
        .text("Void 3t Fishing")
        .color(Color.CYAN)
        .build());

    if (instanceTimer != null) {
      panelComponent.getChildren().add(TitleComponent.builder()
          .text(String.format("%02d:%02d:%02d", instanceTimer.getHours(),
              instanceTimer.getMinutes(), instanceTimer.getSeconds()))
          .color(Color.WHITE)
          .build());
    }

    panelComponent.getChildren().add(LineComponent.builder().left("Caught:").leftColor(Color.WHITE)
        .right(formatter.format(plugin.getCaught())).rightColor(Color.GREEN).build());
    panelComponent.getChildren().add(LineComponent.builder().left("XP:").leftColor(Color.WHITE)
        .right(formatter.format(plugin.getGainedXP())).rightColor(Color.GREEN).build());
    return super.render(graphics2D);
  }
}
