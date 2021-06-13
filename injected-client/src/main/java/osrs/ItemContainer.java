package osrs;

import net.runelite.mapping.Export;
import net.runelite.mapping.Implements;
import net.runelite.mapping.ObfuscatedName;
import net.runelite.mapping.ObfuscatedSignature;

@ObfuscatedName("cj")
@Implements("ItemContainer")
public class ItemContainer extends Node {
   @ObfuscatedName("v")
   @ObfuscatedSignature(
      descriptor = "Lmd;"
   )
   @Export("itemContainers")
   static NodeHashTable itemContainers = new NodeHashTable(32);
   @ObfuscatedName("b")
   @Export("ItemDefinition_inMembersWorld")
   public static boolean ItemDefinition_inMembersWorld;
   @ObfuscatedName("n")
   @Export("ids")
   int[] ids = new int[]{-1};
   @ObfuscatedName("f")
   @Export("quantities")
   int[] quantities = new int[]{0};
}
