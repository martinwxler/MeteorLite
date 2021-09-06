package net.runelite.rs.api;

import net.runelite.api.ChatEntity;
import net.runelite.api.ChatEntityContainer;
import net.runelite.mapping.Import;

public interface RSUserList<T extends ChatEntity> extends ChatEntityContainer<T> {

  @Import("array")
  ChatEntity[] getNameables();

  @Import("getSize")
  int getCount();

  @Import("getByUsername")
  T findByName(RSUsername name);

  /**
   * Method called by the container when an element is added
   *
   * @param name
   * @param prevName
   */
  void rl$add(RSUsername name, RSUsername prevName);

  /**
   * Method called by the container when an element is removed
   *
   * @param nameable
   */
  void rl$remove(RSUser nameable);
}
