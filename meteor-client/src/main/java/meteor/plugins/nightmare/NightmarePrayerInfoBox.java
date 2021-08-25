package meteor.plugins.nightmare;

import net.runelite.api.Client;
import meteor.game.SpriteManager;
import meteor.ui.overlay.Overlay;
import meteor.ui.overlay.OverlayLayer;
import meteor.ui.overlay.OverlayPosition;
import meteor.ui.overlay.OverlayPriority;
import meteor.ui.overlay.components.ComponentConstants;
import meteor.ui.overlay.components.ImageComponent;
import meteor.ui.overlay.components.PanelComponent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.awt.*;
import java.awt.image.BufferedImage;

@Singleton
public class NightmarePrayerInfoBox extends Overlay
{
	private static final Color NOT_ACTIVATED_BACKGROUND_COLOR = new Color(150, 0, 0, 150);
	private final Client client;
	private final NightmarePlugin plugin;
	private final NightmareConfig config;
	private final SpriteManager spriteManager;
	private final PanelComponent imagePanelComponent = new PanelComponent();

	@Inject
	private NightmarePrayerInfoBox(final Client client, final NightmarePlugin plugin, final SpriteManager spriteManager, final NightmareConfig config)
	{
		this.client = client;
		this.plugin = plugin;
		this.spriteManager = spriteManager;
		this.config = config;

		setLayer(OverlayLayer.ABOVE_WIDGETS);
		setPriority(OverlayPriority.HIGH);
		setPosition(OverlayPosition.BOTTOM_RIGHT);
	}

	public Dimension render(Graphics2D graphics)
	{
		imagePanelComponent.getChildren().clear();

		if (!plugin.isInFight() || plugin.getNm() == null)
		{
			return null;
		}

		NightmareAttack attack = plugin.getPendingNightmareAttack();

		if (attack == null)
		{
			return null;
		}

		if (!config.prayerHelper().showInfoBox())
		{
			return null;
		}

		BufferedImage prayerImage;
		prayerImage = getPrayerImage(attack);
		imagePanelComponent.setBackgroundColor(client.isPrayerActive(attack.getPrayer()) ? ComponentConstants.STANDARD_BACKGROUND_COLOR : NOT_ACTIVATED_BACKGROUND_COLOR);

		imagePanelComponent.getChildren().add(new ImageComponent(prayerImage));
		return imagePanelComponent.render(graphics);
	}

	private BufferedImage getPrayerImage(NightmareAttack attack)
	{
		return spriteManager.getSprite(attack.getPrayerSpriteId(), 0);
	}
}
