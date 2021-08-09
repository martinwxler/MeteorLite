// Decompiled with: CFR 0.151
// Class Version: 11
package meteor.plugins.zulrah.overlays;

import com.google.common.collect.LinkedHashMultimap;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.IntUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.inject.Inject;
import meteor.plugins.zulrah.ZulrahConfig;
import meteor.plugins.zulrah.ZulrahPlugin;
import meteor.plugins.zulrah.constants.ZulrahType;
import meteor.plugins.zulrah.rotationutils.ZulrahData;
import meteor.plugins.zulrah.rotationutils.ZulrahPhase;
import meteor.ui.overlay.OverlayPanel;
import meteor.ui.overlay.OverlayPosition;
import meteor.ui.overlay.OverlayPriority;
import meteor.ui.overlay.components.ComponentOrientation;
import meteor.ui.overlay.components.InfoBoxComponent;
import meteor.ui.overlay.components.LayoutableRenderableEntity;
import meteor.ui.overlay.components.TextComponent;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;

public class PhaseOverlay
    extends OverlayPanel {
   private final ZulrahPlugin plugin;
   private final ZulrahConfig config;
   private static final int DEFAULT_DIMENSION = 55;
   private static final int GAP = 1;

   @Inject
   private PhaseOverlay(ZulrahPlugin plugin, ZulrahConfig config) {
      this.plugin = plugin;
      this.config = config;
      this.setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
      this.setPriority(OverlayPriority.HIGH);
      this.setResizable(false);
      this.panelComponent.setPreferredSize(new Dimension(56, 56));
      this.panelComponent.setBorder(new Rectangle());
      this.panelComponent.setGap(new Point(1, 1));
      this.panelComponent.setOrientation(ComponentOrientation.HORIZONTAL);
   }

   @Override
   public Dimension render(Graphics2D graphics) {
      if (this.config.phaseDisplayType() == ZulrahConfig.DisplayType.OFF || this.config.phaseDisplayType() == ZulrahConfig.DisplayType.TILE || this.plugin.getZulrahNpc() == null || this.plugin.getZulrahNpc().isDead()) {
         return null;
      }
      LinkedHashMultimap<ZulrahType, MutablePair<String, Boolean>> zulrahPhases = LinkedHashMultimap.create();
      this.plugin.getZulrahData().forEach(data -> {
         switch (this.config.phaseDisplayMode()) {
            case CURRENT: {
               data.getCurrentPhase().ifPresent(phase -> zulrahPhases.put(phase.getZulrahNpc().getType(), new MutablePair<String, Boolean>("Current", false)));
               break;
            }
            case NEXT: {
               data.getNextPhase().ifPresent(phase -> zulrahPhases.put(phase.getZulrahNpc().getType(), new MutablePair<String, Boolean>(this.getNextString(), true)));
               break;
            }
            case BOTH: {
               data.getCurrentPhase().ifPresent(phase -> zulrahPhases.put(phase.getZulrahNpc().getType(), new MutablePair<String, Boolean>("Current", false)));
               data.getNextPhase().ifPresent(phase -> zulrahPhases.put(phase.getZulrahNpc().getType(), new MutablePair<String, Boolean>(this.getNextString(), true)));
               break;
            }
            default: {
               throw new IllegalStateException("[PhaseOverlay] Invalid 'phaseDisplayMode' config state");
            }
         }
      });
      if (zulrahPhases.size() <= 0) {
         return null;
      }

      List<InfoBoxComponent> components = zulrahPhases.entries().stream().map(this::infoBoxComponent).collect(Collectors.toList());
      IntStream.iterate(components.size() - 1, i -> i - 1).forEach(i -> this.panelComponent.getChildren().add(components.get(i)));
      if (this.config.phaseRotationName()) {
         this.displayRotationName(graphics);
      }
      return super.render(graphics);
   }

   private String getNextString() {
      return this.plugin.getCurrentRotation() != null ? "Next" : "P. Next";
   }

   private InfoBoxComponent infoBoxComponent(Map.Entry<ZulrahType, MutablePair<String, Boolean>> entry) {
      InfoBoxComponent infoBox = new InfoBoxComponent();
      infoBox.setText(entry.getValue().getLeft());
      infoBox.setOutline(this.config.textOutline());
      infoBox.setColor(entry.getValue().getRight() == false ? Color.GREEN : Color.RED);
      infoBox.setImage(entry.getKey().getImage());
      infoBox.setBackgroundColor(entry.getKey().getColorWithAlpha(50));
      return infoBox;
   }

   private void displayRotationName(Graphics2D graphics) {
      Rectangle bounds = this.panelComponent.getBounds();
      String text = this.plugin.getCurrentRotation() != null ? this.plugin.getCurrentRotation().getRotationName() : "Unidentified";
      TextComponent textComponent = new TextComponent();
      textComponent.setPosition(new Point(bounds.x + (bounds.width - graphics.getFontMetrics().stringWidth(text)) / 2, bounds.y + 1));
      textComponent.setText(text);
      textComponent.setColor(this.plugin.getCurrentRotation() != null ? Color.GREEN : Color.YELLOW);
      textComponent.setOutline(this.config.textOutline());
      textComponent.render(graphics);
   }
}
