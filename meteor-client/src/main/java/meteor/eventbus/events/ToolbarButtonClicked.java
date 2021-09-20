package meteor.eventbus.events;

import lombok.Data;
import lombok.Value;

@Data
@Value
public class ToolbarButtonClicked {
  private String name;
}
