package net.runelite.api.events;

import lombok.Data;
import lombok.Value;

@Data
@Value
public class ConfigButtonClicked {
  private String group, key;
}
