package meteor.plugins.zulrah.overlays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import javax.inject.Inject;
import net.runelite.api.Client;
import net.runelite.api.Prayer;
import net.runelite.api.VarClientInt;
import net.runelite.api.widgets.Widget;
import meteor.plugins.zulrah.ZulrahConfig;
import meteor.plugins.zulrah.ZulrahPlugin;
import meteor.plugins.zulrah.util.OverlayUtils;
import meteor.ui.overlay.Overlay;
import meteor.ui.overlay.OverlayLayer;
import meteor.ui.overlay.OverlayPosition;
import meteor.ui.overlay.OverlayPriority;

public class PrayerMarkerOverlay extends Overlay {
   private final Client client;
   private final ZulrahPlugin plugin;
   private final ZulrahConfig config;

   @Inject
   private PrayerMarkerOverlay(Client client, ZulrahPlugin plugin, ZulrahConfig config) {
      this.client = client;
      this.plugin = plugin;
      this.config = config;
      this.setPosition(OverlayPosition.DYNAMIC);
      this.setLayer(OverlayLayer.ABOVE_WIDGETS);
      this.setPriority(OverlayPriority.HIGH);
   }

   public Dimension render(Graphics2D graphics) {
      if (this.config.prayerMarker() && this.plugin.getZulrahNpc() != null && !this.plugin.getZulrahNpc().isDead()) {
         this.plugin.getZulrahData().forEach((data) -> {
            data.getCurrentPhasePrayer().ifPresent((prayer) -> {
               if (this.client.getVar(VarClientInt.INVENTORY_TAB) == 5) {
                  Widget widget = this.client.getWidget(541, this.prayerToChildId(prayer));
                  Color color = !this.client.isPrayerActive(prayer) ? Color.RED : Color.GREEN;
                  OverlayUtils.renderWidgetPolygon(graphics, widget, color, true, false);
               }

            });
         });
         return null;
      } else {
         return null;
      }
   }

   private int prayerToChildId(Prayer prayer) {
      switch(prayer) {
      case PROTECT_FROM_MELEE:
         return 19;
      case PROTECT_FROM_MISSILES:
         return 18;
      case PROTECT_FROM_MAGIC:
         return 17;
      default:
         return -1;
      }
   }
}
