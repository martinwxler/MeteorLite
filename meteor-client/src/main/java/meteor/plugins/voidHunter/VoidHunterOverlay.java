package meteor.plugins.voidHunter;

import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import meteor.ui.overlay.Overlay;

public class VoidHunterOverlay extends Overlay {

  VoidHunterPlugin plugin;

  @Inject
  VoidHunterOverlay(VoidHunterPlugin plugin) {
    this.plugin = plugin;
  }

  @Override
  public Dimension render(Graphics2D graphics) {
    return null;
  }
}
