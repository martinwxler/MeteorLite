package meteor.plugins.voidBlackChins;

import meteor.ui.overlay.OverlayLayer;
import meteor.ui.overlay.OverlayPanel;
import meteor.ui.overlay.OverlayPosition;
import meteor.ui.overlay.components.LineComponent;
import meteor.ui.overlay.components.TitleComponent;
import meteor.util.Timer;

import javax.inject.Inject;
import java.awt.*;
import java.text.DecimalFormat;

public class VoidHunterInfoOverlay extends OverlayPanel {
  public Timer instanceTimer = new Timer();
  DecimalFormat formatter = new DecimalFormat("#,###");
  VoidBlackChins plugin;

  @Inject
  public VoidHunterInfoOverlay(VoidBlackChins plugin) {
    setPosition(OverlayPosition.TOP_CENTER);
    setLayer(OverlayLayer.ABOVE_SCENE);
    setResizable(false);
    this.plugin = plugin;
  }

  @Override
  public Dimension render(Graphics2D graphics2D) {
    if (!VoidBlackChins.enabled) {
      return null;
    }

    panelComponent.getChildren().add(TitleComponent.builder()
        .text("Void Black Chins")
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
