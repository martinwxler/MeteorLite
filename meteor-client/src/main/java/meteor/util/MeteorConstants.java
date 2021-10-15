package meteor.util;

import net.runelite.api.Constants;

import java.awt.*;

public class MeteorConstants {

	public static final int SIDEBAR_WIDTH = 32;
	public static final int PANEL_WIDTH = 350;
	public static final int RIGHT_PANEL_WIDTH = PANEL_WIDTH + SIDEBAR_WIDTH;
	public static final int CLIENT_WIDTH = Constants.GAME_FIXED_WIDTH + (Constants.GAME_FIXED_WIDTH - 749);
	public static final int CLIENT_HEIGHT = Constants.GAME_FIXED_HEIGHT + (Constants.GAME_FIXED_HEIGHT - 464);
	public static final Dimension CLIENT_SIZE = new Dimension(CLIENT_WIDTH + SIDEBAR_WIDTH, CLIENT_HEIGHT);

}
