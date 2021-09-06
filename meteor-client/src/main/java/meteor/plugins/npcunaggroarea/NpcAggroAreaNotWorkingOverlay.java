/*
 * Copyright (c) 2018, Woox <https://github.com/wooxsolo>
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
package meteor.plugins.npcunaggroarea;

import com.google.inject.Inject;
import meteor.ui.overlay.OverlayPanel;
import meteor.ui.overlay.OverlayPosition;
import meteor.ui.overlay.OverlayPriority;
import meteor.ui.overlay.components.LineComponent;

import java.awt.*;

class NpcAggroAreaNotWorkingOverlay extends OverlayPanel
{
	private final NpcAggroAreaPlugin plugin;

	@Inject
	private NpcAggroAreaNotWorkingOverlay(NpcAggroAreaPlugin plugin)
	{
		this.plugin = plugin;

		panelComponent.getChildren().add(LineComponent.builder()
			.left("Unaggressive NPC timers will start working when you teleport far away or enter a dungeon.")
			.build());

		setPriority(OverlayPriority.LOW);
		setPosition(OverlayPosition.TOP_LEFT);
		setClearChildren(false);
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (!plugin.isActive() || plugin.getSafeCenters()[1] != null)
		{
			return null;
		}

		return super.render(graphics);
	}
}
