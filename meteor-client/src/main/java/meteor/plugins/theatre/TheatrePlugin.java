/*
 * THIS PLUGIN WAS WRITTEN BY A KEYBOARD-WIELDING MONKEY BOI BUT SHUFFLED BY A KANGAROO WITH THUMBS.
 * The plugin and it's refactoring was intended for xKylee's Externals but I'm sure if you're reading this, you're probably planning to yoink..
 * or you're just genuinely curious. If you're trying to yoink, it doesn't surprise me.. just don't claim it as your own. Cheers.
 * Extra contributors: terrabl#0001, nicole#1111
 */

package meteor.plugins.theatre;

import com.google.common.base.Strings;
import com.google.inject.Binder;
import com.google.inject.Provides;
import meteor.eventbus.events.ConfigChanged;
import net.runelite.api.Client;
import net.runelite.api.Varbits;
import net.runelite.api.events.*;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.theatre.Bloat.Bloat;
import meteor.plugins.theatre.Maiden.Maiden;
import meteor.plugins.theatre.Nylocas.Nylocas;
import meteor.plugins.theatre.Sotetseg.Sotetseg;
import meteor.plugins.theatre.Verzik.Verzik;
import meteor.plugins.theatre.Xarpus.Xarpus;
import meteor.util.Text;

import javax.inject.Inject;
import java.util.ArrayList;

@PluginDescriptor(
	name = "Theatre of Blood",
	description = "All-in-one plugin for Theatre of Blood",
	tags = {"ToB", "Theatre", "raids", "bloat", "verzik", "nylo", "xarpus", "sotetseg", "maiden"},
	enabledByDefault = false
)

public class TheatrePlugin extends Plugin
{
	@Inject
	private Client client;
	@Inject
	private Maiden maiden;

	@Inject
	private Bloat bloat;

	@Inject
	private Nylocas nylocas;

	@Inject
	private Sotetseg sotetseg;

	@Inject
	private Xarpus xarpus;

	@Inject
	private Verzik verzik;

	public static final Integer MAIDEN_REGION = 12869;
	public static final Integer BLOAT_REGION = 13125;
	public static final Integer NYLOCAS_REGION = 13122;
	public static final Integer SOTETSEG_REGION_OVERWORLD = 13123;
	public static final Integer SOTETSEG_REGION_UNDERWORLD = 13379;
	public static final Integer XARPUS_REGION = 12612;
	public static final Integer VERZIK_REGION = 12611;

	private Room[] rooms = null;

	private boolean tobActive;
	public static int partySize = 0;
	private final ArrayList<String> playerList = new ArrayList<>();

	@Override
	public void configure(Binder binder)
	{
		binder.bind(TheatreInputListener.class);
	}

	@Provides
	public TheatreConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(TheatreConfig.class);
	}

	@Override
	public void startup()
	{
		if (rooms == null)
		{
			rooms = new Room[]{ maiden, bloat, nylocas, sotetseg, xarpus, verzik};

			for (Room room : rooms)
			{
				room.init();
			}
		}

		for (Room room : rooms)
		{
			room.load();
		}
	}

	@Override
	public void shutdown()
	{
		for (Room room : rooms)
		{
			room.unload();
		}

		partySize = 0;
		playerList.clear();
	}

	@Subscribe
	public void onNpcSpawned(NpcSpawned npcSpawned)
	{
		maiden.onNpcSpawned(npcSpawned);
		bloat.onNpcSpawned(npcSpawned);
		nylocas.onNpcSpawned(npcSpawned);
		sotetseg.onNpcSpawned(npcSpawned);
		xarpus.onNpcSpawned(npcSpawned);
		verzik.onNpcSpawned(npcSpawned);
	}

	@Subscribe
	public void onNpcDespawned(NpcDespawned npcDespawned)
	{
		maiden.onNpcDespawned(npcDespawned);
		bloat.onNpcDespawned(npcDespawned);
		nylocas.onNpcDespawned(npcDespawned);
		sotetseg.onNpcDespawned(npcDespawned);
		xarpus.onNpcDespawned(npcDespawned);
		verzik.onNpcDespawned(npcDespawned);
	}

	@Subscribe
	public void onNpcChanged(NpcChanged npcChanged)
	{
		nylocas.onNpcChanged(npcChanged);
	}

	@Subscribe
	public void onGameTick(GameTick event)
	{
		if (tobActive)
		{
			for (int v = 330; v < 335; ++v)
			{
				String name = Text.standardize(client.getVarcStrValue(v));
				if (!Strings.isNullOrEmpty(name) && !playerList.contains(name))
				{
					playerList.add(name);
					partySize = playerList.size();
				}
			}
		}

		maiden.onGameTick(event);
		bloat.onGameTick(event);
		nylocas.onGameTick(event);
		sotetseg.onGameTick(event);
		xarpus.onGameTick(event);
		verzik.onGameTick(event);
	}

	@Subscribe
	public void onClientTick(ClientTick event)
	{
		nylocas.onClientTick(event);
		xarpus.onClientTick(event);
	}

	@Subscribe
	public void onVarbitChanged(VarbitChanged event)
	{
		tobActive = client.getVar(Varbits.THEATRE_OF_BLOOD) > 1;
		if (!tobActive)
		{
			partySize = 0;
			playerList.clear();
		}

		bloat.onVarbitChanged(event);
		nylocas.onVarbitChanged(event);
		xarpus.onVarbitChanged(event);
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		bloat.onGameStateChanged(gameStateChanged);
		nylocas.onGameStateChanged(gameStateChanged);
		xarpus.onGameStateChanged(gameStateChanged);
	}

	@Subscribe
	public void onMenuEntryAdded(MenuEntryAdded entry)
	{
		nylocas.onMenuEntryAdded(entry);
		verzik.onMenuEntryAdded(entry);
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked option)
	{
		nylocas.onMenuOptionClicked(option);

	}
	@Subscribe
	public void onMenuOpened(MenuOpened menu)
	{
		nylocas.onMenuOpened(menu);
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged change)
	{
		if (!change.getGroup().equals("Theatre"))
		{
			return;
		}

		nylocas.onConfigChanged(change);
		verzik.onConfigChanged(change);
	}

	@Subscribe
	public void onGraphicsObjectCreated(GraphicsObjectCreated graphicsObjectC)
	{
		bloat.onGraphicsObjectCreated(graphicsObjectC);
	}

	@Subscribe
	public void onGroundObjectSpawned(GroundObjectSpawned event)
	{
		sotetseg.onGroundObjectSpawned(event);
		xarpus.onGroundObjectSpawned(event);
	}

	@Subscribe
	private void onGroundObjectDespawned(GroundObjectDespawned event)
	{
		xarpus.onGroundObjectDespawned(event);
	}

	@Subscribe
	public void onAnimationChanged(AnimationChanged animationChanged)
	{
		bloat.onAnimationChanged(animationChanged);
		nylocas.onAnimationChanged(animationChanged);
		sotetseg.onAnimationChanged(animationChanged);
	}

	@Subscribe
	public void onProjectileMoved(ProjectileMoved event)
	{
		verzik.onProjectileMoved(event);
	}
}

