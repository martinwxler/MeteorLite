/*
 * Copyright (c) 2018, Jasper Ketelaar <Jasper0781@gmail.com>
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
package meteor.plugins.mta;

import com.google.inject.Provides;
import lombok.AccessLevel;
import lombok.Getter;
import meteor.config.ConfigManager;
import meteor.eventbus.EventBus;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.mta.alchemy.AlchemyRoom;
import meteor.plugins.mta.enchantment.EnchantmentRoom;
import meteor.plugins.mta.graveyard.GraveyardRoom;
import meteor.plugins.mta.telekinetic.TelekineticRoom;
import meteor.ui.overlay.OverlayManager;

import javax.inject.Inject;

@PluginDescriptor(
	name = "Mage Training Arena",
	description = "Show helpful information for the Mage Training Arena minigame",
	tags = {"mta", "magic", "minigame", "overlay"}
)
public class MTAPlugin extends Plugin
{
	@Inject
	private OverlayManager overlayManager;

	@Inject
	private AlchemyRoom alchemyRoom;

	@Inject
	private GraveyardRoom graveyardRoom;

	@Inject
	private TelekineticRoom telekineticRoom;

	@Inject
	private EnchantmentRoom enchantmentRoom;

	@Inject
	private EventBus eventBus;

	@Inject
	private MTASceneOverlay sceneOverlay;

	@Inject
	private MTAInventoryOverlay inventoryOverlay;

	@Getter(AccessLevel.PROTECTED)
	private MTARoom[] rooms;

	@Provides
	public MTAConfig getConfig(ConfigManager manager)
	{
		return manager.getConfig(MTAConfig.class);
	}

	@Override
	public void startup()
	{
		overlayManager.add(sceneOverlay);
		overlayManager.add(inventoryOverlay);

		this.rooms = new MTARoom[]{alchemyRoom, graveyardRoom, telekineticRoom, enchantmentRoom};

		for (MTARoom room : rooms)
		{
			eventBus.register(room);
		}
	}

	@Override
	public void shutdown()
	{
		overlayManager.remove(sceneOverlay);
		overlayManager.remove(inventoryOverlay);

		for (MTARoom room : rooms)
		{
			eventBus.unregister(room);
		}

		telekineticRoom.resetRoom();
	}

}
