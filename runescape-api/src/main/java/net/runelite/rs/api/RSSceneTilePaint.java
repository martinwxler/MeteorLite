package net.runelite.rs.api;

import net.runelite.api.SceneTilePaint;
import net.runelite.mapping.Import;

public interface RSSceneTilePaint extends SceneTilePaint {

  @Import("rgb")
  @Override
  int getRBG();

  @Import("rgb")
  void setRBG(int val);

  @Import("swColor")
  @Override
  int getSwColor();

  @Import("swColor")
  void setSwColor(int val);

  @Import("seColor")
  @Override
  int getSeColor();

  @Import("seColor")
  void setSeColor(int val);

  @Import("nwColor")
  @Override
  int getNwColor();

  @Import("nwColor")
  void setNwColor(int val);

  @Import("neColor")
  @Override
  int getNeColor();

  @Import("neColor")
  void setNeColor(int val);

  @Import("isFlat")
  boolean getIsFlat();

  @Import("isFlat")
  void setIsFlat(boolean val);

  @Import("texture")
  @Override
  int getTexture();

  @Import("texture")
  void setTexture(int val);
}
