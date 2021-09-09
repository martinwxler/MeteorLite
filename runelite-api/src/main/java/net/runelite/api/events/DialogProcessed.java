package net.runelite.api.events;

import lombok.Value;

@Value
public class DialogProcessed {
	int widgetUid;
	int index;
}
