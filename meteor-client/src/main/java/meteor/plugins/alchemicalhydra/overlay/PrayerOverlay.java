/*
 * BSD 2-Clause License
 *
 * Copyright (c) 2020, dutta64 <https://github.com/dutta64>
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

package meteor.plugins.alchemicalhydra.overlay;

import net.runelite.api.Client;
import net.runelite.api.Prayer;
import meteor.plugins.alchemicalhydra.AlchemicalHydraConfig;
import meteor.plugins.alchemicalhydra.AlchemicalHydraPlugin;
import meteor.plugins.alchemicalhydra.entity.Hydra;
import meteor.ui.overlay.Overlay;
import meteor.ui.overlay.OverlayLayer;
import meteor.ui.overlay.OverlayPosition;
import meteor.ui.overlay.OverlayPriority;

import javax.inject.Inject;
import java.awt.*;

public class PrayerOverlay extends Overlay
{
	private final Client client;
	private final AlchemicalHydraPlugin plugin;
	private final AlchemicalHydraConfig config;

	private Hydra hydra;

	@Inject
	private PrayerOverlay(final Client client, final AlchemicalHydraPlugin plugin, final AlchemicalHydraConfig config)
	{

		this.client = client;
		this.plugin = plugin;
		this.config = config;

		setPosition(OverlayPosition.DYNAMIC);
		setPriority(OverlayPriority.HIGH);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
	}

	@Override
	public Dimension render(final Graphics2D graphics2D)
	{
		hydra = plugin.getHydra();

		if (hydra == null)
		{
			return null;
		}

		renderPrayerWidget(graphics2D);

		return null;
	}

	private void renderPrayerWidget(final Graphics2D graphics2D)
	{
		final Prayer prayer = hydra.getNextAttack().getPrayer();

		OverlayUtil.renderPrayerOverlay(graphics2D, client, prayer, prayer == Prayer.PROTECT_FROM_MAGIC ? Color.CYAN : Color.GREEN);
	}

}
