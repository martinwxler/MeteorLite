/*
 * Copyright (c) 2018 Abex
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
package meteor.plugins.regenmeter;

import meteor.ui.overlay.Overlay;
import meteor.ui.overlay.OverlayLayer;
import meteor.ui.overlay.OverlayPosition;
import net.runelite.api.Client;
import net.runelite.api.VarPlayer;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;

import javax.inject.Inject;
import java.awt.*;
import java.awt.geom.Arc2D;

class RegenMeterOverlay extends Overlay
{
	private static final Color HITPOINTS_COLOR = brighter(0x9B0703);
	private static final Color SPECIAL_COLOR = brighter(0x1E95B0);
	private static final Color OVERLAY_COLOR = new Color(255, 255, 255, 60);
	private static final double DIAMETER = 26D;
	private static final int OFFSET = 27;

	private final Client client;
	private final RegenMeterPlugin plugin;
	private final RegenMeterConfig config;

	private static Color brighter(int color)
	{
		float[] hsv = new float[3];
		Color.RGBtoHSB(color >>> 16, (color >> 8) & 0xFF, color & 0xFF, hsv);
		return Color.getHSBColor(hsv[0], 1.f, 1.f);
	}

	@Inject
	public RegenMeterOverlay(Client client, RegenMeterPlugin plugin, RegenMeterConfig config)
	{
		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
		this.client = client;
		this.plugin = plugin;
		this.config = config;
	}

	@Override
	public Dimension render(Graphics2D g)
	{
		g.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);

		if (config.showHitpoints())
		{
			renderRegen(g, WidgetInfo.MINIMAP_HEALTH_ORB, plugin.getHitpointsPercentage(), HITPOINTS_COLOR);
		}

		if (config.showSpecial())
		{
			if (client.getVar(VarPlayer.SPECIAL_ATTACK_ENABLED) == 1)
			{
				final Widget widget = client.getWidget(WidgetInfo.MINIMAP_SPEC_ORB);

				if (widget != null && !widget.isHidden())
				{
					final Rectangle bounds = widget.getBounds();
					g.setColor(RegenMeterOverlay.OVERLAY_COLOR);
					g.fillOval(
						bounds.x + OFFSET,
						bounds.y + (int) (bounds.height / 2 - (DIAMETER) / 2),
						(int) DIAMETER, (int) DIAMETER);
				}
			}

			renderRegen(g, WidgetInfo.MINIMAP_SPEC_ORB, plugin.getSpecialPercentage(), SPECIAL_COLOR);
		}

		return null;
	}

	private void renderRegen(Graphics2D g, WidgetInfo widgetInfo, double percent, Color color)
	{
		Widget widget = client.getWidget(widgetInfo);
		if (widget == null || widget.isHidden())
		{
			return;
		}
		Rectangle bounds = widget.getBounds();

		Arc2D.Double arc = new Arc2D.Double(bounds.x + OFFSET, bounds.y + (bounds.height / 2 - DIAMETER / 2), DIAMETER, DIAMETER, 90.d, -360.d * percent, Arc2D.OPEN);
		final Stroke STROKE = new BasicStroke(2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER);
		g.setStroke(STROKE);
		g.setColor(color);
		g.draw(arc);
	}
}
