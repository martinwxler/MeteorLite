package net.runelite.api.events;

import lombok.Value;
import net.runelite.api.Actor;

@Value
public class OverheadTextChanged {

  Actor actor;

  String overheadText;
}