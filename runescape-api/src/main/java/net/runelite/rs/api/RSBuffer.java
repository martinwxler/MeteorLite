package net.runelite.rs.api;

import net.runelite.api.Buffer;
import net.runelite.mapping.Import;

public interface RSBuffer extends Buffer, RSNode {

  @Import("array")
  byte[] getPayload();

  @Import("offset")
  int getOffset();

  @Import("writeByte")
  @Override
  void writeByte$api(int var1);

  @Import("writeByte01")
  @Override
  void writeByte01$api(int var1);

  @Import("writeShort")
  @Override
  void writeShort$api(int var1);

  @Import("writeShort01")
  @Override
  void writeShort01$api(int var1);

  @Import("writeInt0123")
  @Override
  void writeInt0123$api(int var1);

  @Import("writeShortA")
  @Override
  void writeShortA$api(int var1);

  @Import("writeInt2")
  @Override
  void writeInt2$api(int var1);

  @Import("writeIntME")
  @Override
  void writeIntME$api(int var1);

  @Import("writeShort01A")
  @Override
  void writeShort01A$api(int var1);

  @Import("writeMedium")
  @Override
  void writeMedium$api(int var1);

  @Import("writeInt")
  @Override
  void writeInt$api(int var1);

  @Import("writeLong")
  @Override
  void writeLong$api(long var1);

  @Import("writeStringCp1252NullTerminated")
  @Override
  void writeStringCp1252NullTerminated$api(String string);
}
