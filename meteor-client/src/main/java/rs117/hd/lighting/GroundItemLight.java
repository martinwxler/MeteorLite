package rs117.hd.lighting;

import lombok.Getter;
import rs117.hd.lighting.LightManager.Alignment;
import rs117.hd.lighting.LightManager.LightType;

@Getter
public class GroundItemLight {

  private final int[] id;
  private final int height;
  private final Alignment alignment;
  private final int size;
  private final float strength;
  private final int rgb;
  private final LightType lightType;
  private final float range;

  public GroundItemLight(int height, Alignment alignment, int size, float strength, int rgb, LightType lightType, float range, int... ids)
  {
    this.height = height;
    this.alignment = alignment;
    this.size = size;
    this.strength = strength;
    this.rgb = rgb;
    this.lightType = lightType;
    this.range = range;
    this.id = ids;
  }
}
