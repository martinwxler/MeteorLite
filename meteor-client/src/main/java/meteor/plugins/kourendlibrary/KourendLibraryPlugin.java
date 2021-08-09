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
package meteor.plugins.kourendlibrary;

import com.google.inject.Provides;
import lombok.AccessLevel;
import lombok.Getter;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.eventbus.events.ConfigChanged;
import meteor.game.ItemManager;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.ui.overlay.OverlayManager;
import meteor.util.ImageUtil;
import net.runelite.api.*;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.*;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@PluginDescriptor(
	name = "Kourend Library",
	description = "Show where the books are found in the Kourend Library",
	tags = {"arceuus", "magic", "runecrafting", "overlay", "panel"}
)
public class KourendLibraryPlugin extends Plugin
{
	private static final Pattern BOOK_EXTRACTOR = Pattern.compile("'<col=0000ff>(.*)</col>'");
	private static final Pattern TAG_MATCHER = Pattern.compile("(<[^>]*>)");
	static final int REGION = 6459;

	static final boolean debug = false;

	@Inject
	private Client client;

	@Inject
	private Library library;

	@Inject
	private OverlayManager overlayManager;

	@Inject
	private KourendLibraryOverlay overlay;

	@Inject
	private KourendLibraryTutorialOverlay tutorialOverlay;

	@Inject
	private KourendLibraryConfig config;

	@Inject
	private ItemManager itemManager;

	private boolean buttonAttached = false;
	private WorldPoint lastBookcaseClick = null;
	private WorldPoint lastBookcaseAnimatedOn = null;
	private EnumSet<Book> playerBooks = null;
	private QuestState depthsOfDespairState = QuestState.FINISHED;

	@Getter(AccessLevel.PACKAGE)
	private final Set<NPC> npcsToMark = new HashSet<>();

	@Provides
	public KourendLibraryConfig getConfig(ConfigManager configManager)
	{
		return configManager.getConfig(KourendLibraryConfig.class);
	}

	@Override
	public void startup()
	{
		Book.fillImages(itemManager);

		overlayManager.add(overlay);
		overlayManager.add(tutorialOverlay);

		updatePlayerBooks();
	}

	@Override
	public void shutdown()
	{
		overlayManager.remove(overlay);
		overlayManager.remove(tutorialOverlay);
		buttonAttached = false;
		lastBookcaseClick = null;
		lastBookcaseAnimatedOn = null;
		playerBooks = null;
		npcsToMark.clear();
	}

	@Subscribe
	public void onConfigChanged(ConfigChanged ev)
	{
		if (!KourendLibraryConfig.GROUP_KEY.equals(ev.getGroup()))
		{
			return;
		}

		if (ev.getKey().equals("hideVarlamoreEnvoy"))
		{
			return;
		}
		else if (ev.getKey().equals("showTargetHintArrow"))
		{
			if (client.getLocalPlayer() == null || client.getLocalPlayer().getWorldLocation().getRegionID() != REGION)
			{
				return;
			}

			updateBookcaseHintArrow();
		}
	}

	@Subscribe
	public void onMenuOptionClicked(MenuOptionClicked menuOpt)
	{
		if (MenuAction.GAME_OBJECT_FIRST_OPTION == menuOpt.getMenuAction() && menuOpt.getMenuTarget().contains("Bookshelf"))
		{
			lastBookcaseClick = WorldPoint.fromScene(client, menuOpt.getParam0(), menuOpt.getParam1(), client.getPlane());
		}
	}

	@Subscribe
	public void onAnimationChanged(AnimationChanged anim)
	{
		if (anim.getActor() == client.getLocalPlayer() && anim.getActor().getAnimation() == AnimationID.LOOKING_INTO)
		{
			lastBookcaseAnimatedOn = lastBookcaseClick;
		}
	}

	@Subscribe
	public void onChatMessage(ChatMessage event)
	{
		if (lastBookcaseAnimatedOn != null && event.getType() == ChatMessageType.GAMEMESSAGE)
		{
			if (event.getMessage().equals("You don't find anything useful here."))
			{
				library.mark(lastBookcaseAnimatedOn, null);
				updateBooksPanel();
				lastBookcaseAnimatedOn = null;
			}
		}
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged event)
	{
		if (event.getGameState() == GameState.LOGIN_SCREEN ||
			event.getGameState() == GameState.HOPPING)
		{
			npcsToMark.clear();
		}
	}

	@Subscribe
	public void onGameTick(GameTick tick)
	{
		depthsOfDespairState = Quest.THE_DEPTHS_OF_DESPAIR.getState(client);

		if (lastBookcaseAnimatedOn != null)
		{
			Widget find = client.getWidget(WidgetInfo.DIALOG_SPRITE_SPRITE);
			if (find != null)
			{
				Book book = Book.byId(find.getItemId());
				if (book != null)
				{
					library.mark(lastBookcaseAnimatedOn, book);
					updateBooksPanel();
					lastBookcaseAnimatedOn = null;
				}
			}
		}

		Widget npcHead = client.getWidget(WidgetInfo.DIALOG_NPC_HEAD_MODEL);
		if (npcHead != null)
		{
			if (isLibraryCustomer(npcHead.getModelId()))
			{
				Widget textw = client.getWidget(WidgetInfo.DIALOG_NPC_TEXT);
				String text = textw.getText();
				Matcher m = BOOK_EXTRACTOR.matcher(text);
				if (m.find())
				{
					String bookName = TAG_MATCHER.matcher(m.group(1).replace("<br>", " ")).replaceAll("");
					Book book = Book.byName(bookName);
					if (book == null)
					{
						return;
					}

					library.setCustomer(npcHead.getModelId(), book);
					updateBooksPanel();
				}
				else if (text.contains("You can have this other book") || text.contains("please accept a token of my thanks.") || text.contains("Thanks, I'll get on with reading it."))
				{
					library.setCustomer(-1, null);
					updateBooksPanel();
				}
			}
		}
	}

	@Subscribe
	public void onItemContainerChanged(ItemContainerChanged itemContainerChangedEvent)
	{
		updatePlayerBooks();
	}

	@Subscribe
	public void onNpcSpawned(NpcSpawned event)
	{
		if (isLibraryCustomer(event.getNpc().getId()))
		{
			npcsToMark.add(event.getNpc());
		}
	}

	@Subscribe
	public void onNpcDespawned(NpcDespawned event)
	{
		npcsToMark.remove(event.getNpc());
	}

	boolean doesPlayerContainBook(Book book)
	{
		return playerBooks.contains(book);
	}

	private void updatePlayerBooks()
	{
		ItemContainer itemContainer = client.getItemContainer(InventoryID.INVENTORY);
		EnumSet<Book> books = EnumSet.noneOf(Book.class);

		if (itemContainer != null)
		{
			for (Item item : itemContainer.getItems())
			{
				Book book = Book.byId(item.getId());

				if (book != null)
				{
					books.add(book);
				}
			}
		}

		playerBooks = books;
	}

	private void updateBooksPanel()
	{
		updateBookcaseHintArrow();
	}

	private void updateBookcaseHintArrow()
	{
		final Book customerBook = library.getCustomerBook();
		final SolvedState state = library.getState();

		// Clear the hint arrow if the player has no book requested of them
		// or if the player is already holding the correct book
		// or if this plugin is configured not to show the target book hint arrow
		if (customerBook == null || doesPlayerContainBook(customerBook) || !config.showTargetHintArrow())
		{
			client.clearHintArrow();
		}
		else if (state == SolvedState.COMPLETE && client.getHintArrowPoint() == null)
		{
			// Show a hint arrow pointing toward the target book if all book locations are known
			// and a hint arrow is not already being displayed
			for (Bookcase bookcase : library.getBookcases())
			{
				final Set<Book> books = bookcase.getPossibleBooks();

				if (!books.isEmpty())
				{
					final Book book = books.iterator().next();

					// Each bookcase in a complete solved state will contain only one book. If that book is the book
					// the customer wants, mark the bookcase which contains it with a hint arrow.
					if (book == customerBook)
					{
						WorldPoint correctLocation = bookcase.getLocation();
						client.setHintArrow(correctLocation);
						break;
					}
				}
			}
		}
	}

	boolean showVarlamoreEnvoy()
	{
		return config.alwaysShowVarlamoreEnvoy() || depthsOfDespairState == QuestState.IN_PROGRESS;
	}

	static boolean isLibraryCustomer(int npcId)
	{
		return npcId == NpcID.VILLIA || npcId == NpcID.PROFESSOR_GRACKLEBONE || npcId == NpcID.SAM_7049;
	}
}