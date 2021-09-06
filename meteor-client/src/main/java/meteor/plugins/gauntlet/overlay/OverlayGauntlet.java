/*
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

package meteor.plugins.gauntlet.overlay;

import meteor.ui.overlay.outline.OPRSModelOutlineRenderer;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import meteor.plugins.gauntlet.GauntletConfig;
import meteor.plugins.gauntlet.GauntletPlugin;
import meteor.plugins.gauntlet.entity.Demiboss;
import meteor.plugins.gauntlet.entity.Resource;
import meteor.ui.overlay.OverlayLayer;
import meteor.ui.overlay.OverlayPosition;
import meteor.ui.overlay.OverlayPriority;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;

@Singleton
public class OverlayGauntlet extends Overlay
{
	private static final Color TRANSPARENT = new Color(0, 0, 0, 0);

	private final Client client;
	private final GauntletPlugin plugin;
	private final GauntletConfig config;
	public final OPRSModelOutlineRenderer oprsmodelOutlineRenderer;

	private Player player;

	@Inject
	private OverlayGauntlet(final Client client, final GauntletPlugin plugin, final GauntletConfig config, final OPRSModelOutlineRenderer modelOutlineRenderer)
	{
		super(plugin);

		this.client = client;
		this.plugin = plugin;
		this.config = config;
		this.oprsmodelOutlineRenderer = modelOutlineRenderer;

		setPosition(OverlayPosition.DYNAMIC);
		setPriority(OverlayPriority.HIGH);
		determineLayer();
	}

	@Override
	public void determineLayer()
	{
		setLayer(OverlayLayer.UNDER_WIDGETS);
	}

	@Override
	public Dimension render(final Graphics2D graphics2D)
	{
		player = client.getLocalPlayer();

		if (player == null)
		{
			return null;
		}

		renderResources(graphics2D);
		renderUtilities();
		renderDemibosses();
		renderStrongNpcs();
		renderWeakNpcs();

		return null;
	}

	private void renderResources(final Graphics2D graphics2D)
	{
		if ((!config.resourceOverlay() && !config.resourceOutline()) || plugin.getResources().isEmpty())
		{
			return;
		}

		final LocalPoint localPointPlayer = player.getLocalLocation();

		for (final Resource resource : plugin.getResources())
		{
			final GameObject gameObject = resource.getGameObject();

			final LocalPoint localPointGameObject = gameObject.getLocalLocation();

			if (isOutsideRenderDistance(localPointGameObject, localPointPlayer))
			{
				continue;
			}

			if (config.resourceOverlay())
			{
				final Polygon polygon = Perspective.getCanvasTilePoly(client, localPointGameObject);

				if (polygon == null)
				{
					continue;
				}

				drawOutlineAndFill(graphics2D, config.resourceTileOutlineColor(), config.resourceTileFillColor(),
					config.resourceTileOutlineWidth(), polygon);

				OverlayUtil.renderImageLocation(client, graphics2D, localPointGameObject, resource.getIcon(), 0);
			}

			if (config.resourceOutline())
			{
				final Shape shape = gameObject.getConvexHull();

				if (shape == null)
				{
					continue;
				}

				oprsmodelOutlineRenderer.drawOutline(gameObject, config.resourceOutlineWidth(),
					config.resourceOutlineColor(), TRANSPARENT);
			}
		}
	}

	private void renderUtilities()
	{
		if (!config.utilitiesOutline() || plugin.getUtilities().isEmpty())
		{
			return;
		}

		final LocalPoint localPointPlayer = player.getLocalLocation();

		for (final GameObject gameObject : plugin.getUtilities())
		{
			if (isOutsideRenderDistance(gameObject.getLocalLocation(), localPointPlayer))
			{
				continue;
			}

			final Shape shape = gameObject.getConvexHull();

			if (shape == null)
			{
				continue;
			}

			oprsmodelOutlineRenderer.drawOutline(gameObject, config.utilitiesOutlineWidth(),
				config.utilitiesOutlineColor(), TRANSPARENT);
		}
	}

	private void renderDemibosses()
	{
		if (!config.demibossOutline() || plugin.getDemibosses().isEmpty())
		{
			return;
		}

		final LocalPoint localPointPlayer = player.getLocalLocation();

		for (final Demiboss demiboss : plugin.getDemibosses())
		{
			final NPC npc = demiboss.getNpc();

			final LocalPoint localPointNpc = npc.getLocalLocation();

			if (localPointNpc == null || npc.isDead() || isOutsideRenderDistance(localPointNpc, localPointPlayer))
			{
				continue;
			}

			oprsmodelOutlineRenderer.drawOutline(npc, config.demibossOutlineWidth(),
				demiboss.getType().getOutlineColor(), TRANSPARENT);
		}
	}

	private void renderStrongNpcs()
	{
		if (!config.strongNpcOutline() || plugin.getStrongNpcs().isEmpty())
		{
			return;
		}

		final LocalPoint localPointPLayer = player.getLocalLocation();

		for (final NPC npc : plugin.getStrongNpcs())
		{
			final LocalPoint localPointNpc = npc.getLocalLocation();

			if (localPointNpc == null || npc.isDead() || isOutsideRenderDistance(localPointNpc, localPointPLayer))
			{
				continue;
			}

			oprsmodelOutlineRenderer.drawOutline(npc, config.strongNpcOutlineWidth(), config.strongNpcOutlineColor(),
				TRANSPARENT);
		}
	}

	private void renderWeakNpcs()
	{
		if (!config.weakNpcOutline() || plugin.getWeakNpcs().isEmpty())
		{
			return;
		}

		final LocalPoint localPointPlayer = player.getLocalLocation();

		for (final NPC npc : plugin.getWeakNpcs())
		{
			final LocalPoint localPointNpc = npc.getLocalLocation();

			if (localPointNpc == null || npc.isDead() || isOutsideRenderDistance(localPointNpc, localPointPlayer))
			{
				continue;
			}

			oprsmodelOutlineRenderer.drawOutline(npc, config.weakNpcOutlineWidth(), config.weakNpcOutlineColor(),
				TRANSPARENT);
		}
	}

	private boolean isOutsideRenderDistance(final LocalPoint localPoint, final LocalPoint playerLocation)
	{
		final int maxDistance = config.resourceRenderDistance().getDistance();

		if (maxDistance == 0)
		{
			return false;
		}

		return localPoint.distanceTo(playerLocation) >= maxDistance;
	}
}
