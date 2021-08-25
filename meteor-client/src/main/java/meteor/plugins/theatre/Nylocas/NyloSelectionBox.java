/*
 * THIS PLUGIN WAS WRITTEN BY A KEYBOARD-WIELDING MONKEY BOI BUT SHUFFLED BY A KANGAROO WITH THUMBS.
 * The plugin and it's refactoring was intended for xKylee's Externals but I'm sure if you're reading this, you're probably planning to yoink..
 * or you're just genuinely curious. If you're trying to yoink, it doesn't surprise me.. just don't claim it as your own. Cheers.
 */

package meteor.plugins.theatre.Nylocas;

import lombok.Getter;
import lombok.Setter;
import meteor.ui.overlay.Overlay;
import meteor.ui.overlay.components.InfoBoxComponent;

import java.awt.*;

public class NyloSelectionBox extends Overlay
{
	private final InfoBoxComponent component;

	@Getter
	@Setter
	private boolean isSelected = false;

	@Getter
	@Setter
	private boolean isHovered = false;

	public NyloSelectionBox(InfoBoxComponent component)
	{
		this.component = component;
	}

	@Override
	public Dimension render(Graphics2D graphics)
	{
		if (isSelected)
		{
			component.setColor(Color.GREEN);
			component.setText("On");
		}
		else
		{
			component.setColor(Color.RED);
			component.setText("Off");
		}

		Dimension result = component.render(graphics);

		if (isHovered)
		{
			Color color = graphics.getColor();
			graphics.setColor(new Color(200, 200, 200));
			graphics.drawRect(component.getBounds().x, component.getBounds().y, component.getBounds().width, component.getBounds().height);
			graphics.setColor(color);
		}

		return result;
	}
}
