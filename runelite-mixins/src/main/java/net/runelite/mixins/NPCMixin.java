package net.runelite.mixins;

import java.awt.Shape;
import java.util.ArrayList;
import java.util.List;
import net.runelite.api.MenuAction;
import net.runelite.api.NPCComposition;
import net.runelite.api.Perspective;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.events.NpcChanged;
import net.runelite.api.events.NpcDespawned;
import net.runelite.api.events.NpcTransformedChanged;
import net.runelite.api.events.NpcTransformedDespawned;
import net.runelite.api.mixins.FieldHook;
import net.runelite.api.mixins.Inject;
import net.runelite.api.mixins.Mixin;
import net.runelite.api.mixins.Shadow;
import net.runelite.rs.api.RSClient;
import net.runelite.rs.api.RSModel;
import net.runelite.rs.api.RSNPC;
import net.runelite.rs.api.RSNPCComposition;

@Mixin(RSNPC.class)
public abstract class NPCMixin implements RSNPC {

  @Shadow("client")
  private static RSClient client;

  @Inject
  private int npcIndex;

  @Inject
  @Override
  public int getIndex() {
    return npcIndex;
  }

  @Inject
  @Override
  public void setIndex(int id) {
    npcIndex = id;
  }

  @Inject
  @Override
  public String getName() {
    RSNPCComposition composition = getComposition();
    if (composition != null && composition.getConfigs() != null) {
      composition = composition.transform$api();
    }
    return composition == null ? null : composition.getName().replace('\u00A0', ' ');
  }

  @FieldHook(value = "definition", before = true)
  @Inject
  public void onDefinitionChanged(RSNPCComposition composition) {
    if (composition == null) {
      if (getComposition() != null)
      if (getComposition().getConfigs() != null)
        client.getCallbacks().post(new NpcTransformedDespawned(this));
      client.getCallbacks().post(new NpcDespawned(this));
    } else if (this.getId() != -1) {
      RSNPCComposition oldComposition = getComposition();
      if (oldComposition == null) {
        return;
      }

      if (oldComposition.getConfigs() != null) {
        NPCComposition transformed = composition.transform$api();
        if (transformed != null) {
          if (transformed.getId() == oldComposition.transform$api().getId()) {
            return;
          }
        } else
        client.getCallbacks().postDeferred(new NpcTransformedChanged(this, composition.transform$api()));
      }

      if (composition.getId() == oldComposition.getId()) {
        return;
      }

      client.getCallbacks().postDeferred(new NpcChanged(this, oldComposition));
    }
  }

  @Inject
  @Override
  public int getId() {
    RSNPCComposition composition = getComposition();
    if (composition != null && composition.getConfigs() != null) {
      composition = composition.transform$api();
    }
    return composition == null ? -1 : composition.getId();
  }

  @Inject
  @Override
  public Shape getConvexHull() {
    RSModel model = getModel$api();
    if (model == null) {
      return null;
    }

    int size = getComposition().getSize();
    LocalPoint tileHeightPoint = new LocalPoint(
        size * Perspective.LOCAL_HALF_TILE_SIZE - Perspective.LOCAL_HALF_TILE_SIZE + getX(),
        size * Perspective.LOCAL_HALF_TILE_SIZE - Perspective.LOCAL_HALF_TILE_SIZE + getY());

    int tileHeight = Perspective.getTileHeight(client, tileHeightPoint, client.getPlane());

    return model.getConvexHull(getX(), getY(), getOrientation(), tileHeight);
  }

  @Inject
  @Override
  public NPCComposition getTransformedComposition()
  {
    RSNPCComposition composition = getComposition();
    if (composition != null && composition.getConfigs() != null)
    {
      composition = composition.transform$api();
    }
    return composition;
  }

  @Inject
  @Override
  public int getDistanceFromLocalPlayer() {
    //Mancrappen :tm:
    int distanceX;
    int distanceY;
    LocalPoint localPlayerPosition = client.getLocalPlayer().getLocalLocation();

    if (getX() > localPlayerPosition.getX())
      distanceX = getX() - localPlayerPosition.getX();
    else
      distanceX = localPlayerPosition.getX() - getX();

    if (getY() > localPlayerPosition.getY())
      distanceY = getY() - localPlayerPosition.getY();
    else
      distanceY = localPlayerPosition.getY() - getY();

    return (distanceX + distanceY) / 2;
  }

  @Inject
  @Override
  public int getCombatLevel()
  {
    RSNPCComposition composition = getComposition();
    if (composition != null && composition.getConfigs() != null)
    {
      composition = composition.transform$api();
    }
    return composition == null ? -1 : composition.getCombatLevel();
  }


  @Override
  @Inject
  public List<String> actions() {
    List<String> actions = new ArrayList<>();
    for (String s : getComposition().getActions())
      if (s != null)
        actions.add(s);
    return actions;
  }

  @Override
  @Inject
  public void interact(String action) {
    String[] actions = getComposition().getActions();

    for (int i = 0; i < actions.length; i++) {
      if (action.equalsIgnoreCase(actions[i])) {
        interact(i);
        return;
      }
    }

    throw new IllegalArgumentException("action \"" + action + "\" not found on NPC " + getId());
  }

  @Override
  @Inject
  public int getActionId(int action) {
    switch (action) {
      case 0:
        return MenuAction.NPC_FIRST_OPTION.getId();
      case 1:
        return MenuAction.NPC_SECOND_OPTION.getId();
      case 2:
        return MenuAction.NPC_THIRD_OPTION.getId();
      case 3:
        return MenuAction.NPC_FOURTH_OPTION.getId();
      case 4:
        return MenuAction.NPC_FIFTH_OPTION.getId();
      default:
        throw new IllegalArgumentException("action = " + action);
    }
  }

  @Override
  @Inject
  public void interact(int action) {
    client.interact(getIndex(), getActionId(action), 0, 0);
  }

  @Override
  public String[] getActions() {
    return client.getNpcDefinition(getId()).getActions();
  }

  @Override
  public void interact(int identifier, int opcode, int param0, int param1) {
    client.interact(identifier, opcode, param0, param1);
  }

  @Inject
  @Override
  public String toString() {
    return getIndex() + ": " + getName() + " (" + getId() + ") at " + getWorldLocation();
  }
}
