package meteor.events;

import lombok.Value;
import net.runelite.api.Point;

@Value
public class AutomationMouseMoveEvent {
  public Point point;
}
