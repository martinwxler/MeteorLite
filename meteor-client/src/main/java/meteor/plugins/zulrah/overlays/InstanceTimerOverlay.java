package meteor.plugins.zulrah.overlays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.time.Duration;
import java.time.Instant;
import javax.inject.Inject;
import meteor.plugins.zulrah.ZulrahConfig;
import meteor.plugins.zulrah.ZulrahPlugin;
import meteor.ui.overlay.OverlayPanel;
import meteor.ui.overlay.OverlayPosition;
import meteor.ui.overlay.OverlayPriority;
import meteor.ui.overlay.components.LineComponent;

public class InstanceTimerOverlay extends OverlayPanel {
   private final ZulrahPlugin plugin;
   private final ZulrahConfig config;
   private Instant instanceTimer;

   @Inject
   private InstanceTimerOverlay(ZulrahPlugin plugin, ZulrahConfig config) {
      this.plugin = plugin;
      this.config = config;
      this.setPriority(OverlayPriority.HIGH);
      this.setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
   }

   public Dimension render(Graphics2D graphics) {
      if (this.config.instanceTimer() && this.instanceTimer != null) {
         Duration elapsed = Duration.between(this.instanceTimer, Instant.now());
         this.panelComponent.getChildren().add(LineComponent.builder().left("Instance Timer:").leftColor(Color.WHITE).right(String.format("%d:%02d", (elapsed.getNano() * 1000 * 1000) % 3600 / 60, (elapsed.getNano() * 1000 * 1000) % 60)).rightColor(Color.GREEN).build());
         return super.render(graphics);
      } else {
         return null;
      }
   }

   public void setTimer() {
      this.instanceTimer = Instant.now();
   }

   public void resetTimer() {
      this.instanceTimer = null;
   }
}
