package net.runelite.mixins;

import java.awt.Shape;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.Angle;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.mixins.Inject;
import net.runelite.api.mixins.Mixin;
import net.runelite.api.mixins.Shadow;
import net.runelite.rs.api.RSClient;
import net.runelite.rs.api.RSGameObject;
import net.runelite.rs.api.RSModel;
import net.runelite.rs.api.RSRenderable;

@Mixin(RSGameObject.class)
public abstract class GameObjectMixin implements RSGameObject {

  @Shadow("client")
  private static RSClient client;

  @Inject
  private int gameObjectPlane;

  @Inject
  @Override
  public int getPlane() {
    return gameObjectPlane;
  }

  @Inject
  @Override
  public void setPlane(int plane) {
    this.gameObjectPlane = plane;
  }

  @Inject
  @Override
  public Point getSceneMinLocation() {
    return new Point(getStartX(), getStartY());
  }

  @Inject
  @Override
  public Point getSceneMaxLocation() {
    return new Point(getEndX(), getEndY());
  }

  @Inject
  @Override
  public RSModel getModel() {
    RSRenderable renderable = getRenderable();
    if (renderable == null) {
      return null;
    }

    if (renderable instanceof RSModel) {
      return (RSModel) renderable;
    } else {
      return renderable.getModel$api();
    }
  }

  @Inject
  @Override
  public Shape getClickbox() {
    return Perspective.getClickbox(client, getModel(), getRsOrientation(), getLocalLocation());
  }

  @Inject
  @Override
  public Shape getConvexHull() {
    RSModel model = getModel();

    if (model == null) {
      return null;
    }

    int tileHeight = Perspective
        .getTileHeight(client, new LocalPoint(getX(), getY()), client.getPlane());

    return model.getConvexHull(getX(), getY(), getRsOrientation(), tileHeight);
  }

  @Override
  @Inject
  public Angle getOrientation() {
    int orientation = getRsOrientation();
    int rotation = (getFlags() >> 6) & 3;
    return new Angle(rotation * 512 + orientation);
  }

  @Override
  @Inject
  public int sizeX() {
    return getEndX() - getStartX() + 1;
  }

  @Override
  @Inject
  public int sizeY() {
    return getEndY() - getStartY() + 1;
  }
}
