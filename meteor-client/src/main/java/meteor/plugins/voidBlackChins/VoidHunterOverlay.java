package meteor.plugins.voidBlackChins;

import meteor.ui.overlay.OverlayLayer;
import meteor.ui.overlay.OverlayPanel;
import meteor.ui.overlay.OverlayPosition;
import net.runelite.api.*;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;
import java.util.ArrayList;

@Singleton
public class VoidHunterOverlay extends OverlayPanel {

  private final VoidBlackChins plugin;

  ArrayList<Tile> chinSpotsToRender = new ArrayList<>();

  @Inject
  VoidHunterOverlay(VoidBlackChins plugin) {
    setPosition(OverlayPosition.DYNAMIC);
    setLayer(OverlayLayer.ABOVE_SCENE);
    this.plugin = plugin;
  }

  @Override
  public Dimension render(Graphics2D graphics) {
    if (!VoidBlackChins.enabled) {
      return null;
    }

    chinSpotsToRender.clear();

    return super.render(graphics);
  }
}
