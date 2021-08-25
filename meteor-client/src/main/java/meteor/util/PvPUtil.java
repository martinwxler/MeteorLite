/*
 * Copyright (c) 2019, PKLite
 * Copyright (c) 2020, ThatGamerBlue <thatgamerblue@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package meteor.util;

import meteor.game.ItemManager;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import org.apache.commons.lang3.ArrayUtils;

import java.util.Comparator;
import java.util.Objects;
import java.util.TreeMap;

import static meteor.util.QuantityFormatter.quantityToRSDecimalStack;

public class PvPUtil
{
	/**
	 * Gets the wilderness level based on a world point
	 * @param point the point in the world to get the wilderness level for
	 * @return the int representing the wilderness level
	 */
	public static int getWildernessLevelFrom(WorldPoint point)
	{
		int x = point.getX();
		int y = point.getY();

		int underLevel = ((y - 9920) / 8) + 1;
		int upperLevel = ((y - 3520) / 8) + 1;

		return y > 6400 ? underLevel : upperLevel;
	}

	/**
	 * Determines if another player is attackable based off of wilderness level and combat levels
	 * @param client The client of the local player
	 * @param player the player to determine attackability
	 * @return returns true if the player is attackable, false otherwise
	 */
	public static boolean isAttackable(Client client, Player player)
	{
		int wildernessLevel = 0;
		if (!(client.getVar(Varbits.IN_WILDERNESS) == 1 || WorldType.isPvpWorld(client.getWorldType())))
		{
			return false;
		}
		if (WorldType.isPvpWorld(client.getWorldType()))
		{
			if (client.getVar(Varbits.IN_WILDERNESS) != 1)
			{
				return Math.abs(client.getLocalPlayer().getCombatLevel() - player.getCombatLevel()) <= 15;
			}
			wildernessLevel = 15;
		}
		return Math.abs(client.getLocalPlayer().getCombatLevel() - player.getCombatLevel())
				< (getWildernessLevelFrom(client.getLocalPlayer().getWorldLocation())+ wildernessLevel);
	}

	public static int calculateRisk(Client client, ItemManager itemManager)
	{
		if (client.getItemContainer(InventoryID.EQUIPMENT) == null)
		{
			return 0;
		}
		if (client.getItemContainer(InventoryID.INVENTORY) != null)
		if (client.getItemContainer(InventoryID.INVENTORY).getItems().length == 0)
		{
			return 0;
		}
		Item[] items = ArrayUtils.addAll(Objects.requireNonNull(client.getItemContainer(InventoryID.EQUIPMENT)).getItems(),
				Objects.requireNonNull(client.getItemContainer(InventoryID.INVENTORY)).getItems());
		TreeMap<Integer, Item> priceMap = new TreeMap<>(Comparator.comparingInt(Integer::intValue));
		int wealth = 0;
		for (Item i : items)
		{
			int value = (itemManager.getItemPrice(i.getId()) * i.getQuantity());

			final ItemComposition itemComposition = itemManager.getItemComposition(i.getId());
			if (!itemComposition.isTradeable() && value == 0)
			{
				value = itemComposition.getPrice() * i.getQuantity();
				priceMap.put(value, i);
			}
			else
			{
				value = itemManager.getItemPrice(i.getId()) * i.getQuantity();
				if (i.getId() > 0 && value > 0)
				{
					priceMap.put(value, i);
				}
			}
			wealth += value;
		}
		return Integer.parseInt(quantityToRSDecimalStack(priceMap.keySet().stream().mapToInt(Integer::intValue).sum()));
	}
}
