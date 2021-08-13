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
import net.runelite.rs.api.RSUsername;

@Mixin(RSPlayer.class)
public abstract class PlayerMixin implements RSPlayer {

  @Shadow("client")
  private static RSClient client;

  @Inject
  @Override
  public String getName()
  {
    final RSUsername rsName = getRsName();

    if (rsName == null)
    {
      return null;
    }

    String name = rsName.getName$api();

    if (name == null)
    {
      return null;
    }

    return name.replace('\u00A0', ' ');
  }

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

  @Inject
  private boolean friended;

  @Inject
  public boolean isFriended()
  {
    return isFriend$api() || friended;
  }

  @Inject
  @Override
  public boolean isIdle() {
    return (getIdlePoseAnimation() == getPoseAnimation() && getAnimation() == -1)
        && (getInteracting() == null || getInteracting().isDead());
  }
}
