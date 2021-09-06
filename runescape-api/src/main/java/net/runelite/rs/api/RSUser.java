package net.runelite.rs.api;

import net.runelite.api.ChatEntity;
import net.runelite.mapping.Import;

public interface RSUser extends ChatEntity, Comparable {

  @Import("username")
  RSUsername getRsName();

  @Import("previousUsername")
  RSUsername getRsPrevName();
}
