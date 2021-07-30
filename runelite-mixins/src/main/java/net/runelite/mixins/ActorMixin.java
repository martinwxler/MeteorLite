package net.runelite.mixins;

import java.awt.Polygon;
import java.awt.image.BufferedImage;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.mixins.Inject;
import net.runelite.api.mixins.Mixin;
import net.runelite.api.mixins.Shadow;
import net.runelite.rs.api.RSActor;
import net.runelite.rs.api.RSClient;
import net.runelite.rs.api.RSHealthBar;
import net.runelite.rs.api.RSHealthBarDefinition;
import net.runelite.rs.api.RSHealthBarUpdate;
import net.runelite.rs.api.RSIterableNodeDeque;
import net.runelite.rs.api.RSNode;

@Mixin(RSActor.class)
public abstract class ActorMixin implements RSActor {

  @Shadow("client")
  private static RSClient client;

  @Inject
  @Override
  public WorldPoint getWorldLocation() {
    return WorldPoint.fromLocal(client,
        this.getPathX()[0] * Perspective.LOCAL_TILE_SIZE + Perspective.LOCAL_TILE_SIZE / 2,
        this.getPathY()[0] * Perspective.LOCAL_TILE_SIZE + Perspective.LOCAL_TILE_SIZE / 2,
        client.getPlane());
  }

  @Inject
  @Override
  public int getHealthRatio() {
    RSIterableNodeDeque healthBars = getHealthBars();
    if (healthBars != null) {
      RSNode current = healthBars.getCurrent();
      RSNode next = current.getNext();
      if (next instanceof RSHealthBar) {
        RSHealthBar wrapper = (RSHealthBar) next;
        RSIterableNodeDeque updates = wrapper.getUpdates();

        RSNode currentUpdate = updates.getCurrent();
        RSNode nextUpdate = currentUpdate.getNext();
        if (nextUpdate instanceof RSHealthBarUpdate) {
          RSHealthBarUpdate update = (RSHealthBarUpdate) nextUpdate;
          return update.getHealthRatio();
        }
      }
    }
    return -1;
  }

  @Inject
  @Override
  public int getHealthScale() {
    RSIterableNodeDeque healthBars = getHealthBars();
    if (healthBars != null) {
      RSNode current = healthBars.getCurrent();
      RSNode next = current.getNext();
      if (next instanceof RSHealthBar) {
        RSHealthBar wrapper = (RSHealthBar) next;
        RSHealthBarDefinition definition = wrapper.getDefinition();
        return definition.getHealthScale();
      }
    }
    return -1;
  }

  @Inject
  @Override
  public Polygon getCanvasTilePoly()
  {
    return Perspective.getCanvasTilePoly(client, getLocalLocation());
  }

  @Inject
  @Override
  public LocalPoint getLocalLocation()
  {
    return new LocalPoint(getX(), getY());
  }

  @Inject
  @Override
  public Point getCanvasImageLocation(BufferedImage image, int zOffset)
  {
    return Perspective.getCanvasImageLocation(client, getLocalLocation(), image, zOffset);
  }

  @Inject
  @Override
  public Point getMinimapLocation()
  {
    return Perspective.localToMinimap(client, getLocalLocation());
  }
}
