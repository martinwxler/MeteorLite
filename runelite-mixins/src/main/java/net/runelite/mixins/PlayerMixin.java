package net.runelite.mixins;

import java.awt.Shape;
import net.runelite.api.Perspective;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.mixins.Inject;
import net.runelite.api.mixins.Mixin;
import net.runelite.api.mixins.Shadow;
import net.runelite.rs.api.RSClient;
import net.runelite.rs.api.RSModel;
import net.runelite.rs.api.RSPlayer;

@Mixin(RSPlayer.class)
public abstract class PlayerMixin implements RSPlayer {

  @Shadow("client")
  private static RSClient client;

  @Inject
  @Override
  public Shape getConvexHull() {
    RSModel model = getModel$api();
    if (model == null) {
      return null;
    }

    int tileHeight = Perspective
        .getTileHeight(client, new LocalPoint(getX(), getY()), client.getPlane());

    return model.getConvexHull(getX(), getY(), getOrientation(), tileHeight);
  }
}
