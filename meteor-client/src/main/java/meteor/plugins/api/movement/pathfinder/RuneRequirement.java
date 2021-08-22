package meteor.plugins.api.movement.pathfinder;

import lombok.Value;
import meteor.plugins.api.magic.Rune;

@Value
public class RuneRequirement {
    int quantity;
    Rune rune;
}
