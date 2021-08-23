package net.runelite.mixins;

import java.awt.Shape;
import java.util.Arrays;
import java.util.List;

import net.runelite.api.MenuAction;
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

  @Inject
  @Override
  public int getIndex() {
    for (int i = 0; i < client.getCachedPlayers().length; i++) {
      RSPlayer player = client.getCachedPlayers()[i];
      if (player != null && player.equals(this)) {
        return i;
      }
    }

    return -1;
  }

  @Override
  public int getActionId(int action) {
    switch (action) {
      case 0:
        return MenuAction.PLAYER_FIRST_OPTION.getId();
      case 1:
        return MenuAction.PLAYER_SECOND_OPTION.getId();
      case 2:
        return MenuAction.PLAYER_THIRD_OPTION.getId();
      case 3:
        return MenuAction.PLAYER_FOURTH_OPTION.getId();
      case 4:
        return MenuAction.PLAYER_FIFTH_OPTION.getId();
      case 5:
        return MenuAction.PLAYER_SIXTH_OPTION.getId();
      case 6:
        return MenuAction.PLAYER_SEVENTH_OPTION.getId();
      case 7:
        return MenuAction.PLAYER_EIGTH_OPTION.getId();
      default:
        throw new IllegalArgumentException("action = " + action);
    }
  }

  @Override
  public List<String> actions() {
    return Arrays.asList(getActions());
  }

  @Override
  @Inject
  public void interact(int action) {
    client.interact(getIndex(), getActionId(action), 0, 0);
  }

  @Override
  public void interact(int identifier, int opcode, int param0, int param1) {
    client.interact(identifier, opcode, param0, param1);
  }
}
