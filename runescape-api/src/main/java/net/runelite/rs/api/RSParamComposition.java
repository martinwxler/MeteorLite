package net.runelite.rs.api;

import net.runelite.mapping.Import;

public interface RSParamComposition {

  @Import("isString")
  boolean isString$api();

  @Import("type")
  char getType();

  @Import("defaultInt")
  int getDefaultInt();

  @Import("defaultStr")
  String getDefaultStr();
}
