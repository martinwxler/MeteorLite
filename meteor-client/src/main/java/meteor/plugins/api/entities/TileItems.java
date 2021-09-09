package meteor.plugins.api.entities;

import meteor.plugins.api.game.Game;
import meteor.plugins.api.game.GameThread;
import meteor.plugins.api.scene.Tiles;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import org.sponge.util.Logger;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TileItems extends Entities<TileItem> {
	private static final TileItems TILE_ITEMS = new TileItems();

	@Override
	protected List<TileItem> all(Predicate<? super TileItem> filter) {
		return Tiles.getTiles().stream()
						.flatMap(tile -> parseTile(tile, filter).stream())
						.collect(Collectors.toList());
	}

	public static List<TileItem> getAll(Predicate<TileItem> filter) {
		return TILE_ITEMS.all(filter);
	}

	public static List<TileItem> getAll(int... ids) {
		return TILE_ITEMS.all(ids);
	}

	public static List<TileItem> getAll(String... names) {
		return TILE_ITEMS.all(names);
	}

	public static TileItem getNearest(Predicate<TileItem> filter) {
		return TILE_ITEMS.nearest(filter);
	}

	public static TileItem getNearest(int... ids) {
		return TILE_ITEMS.nearest(ids);
	}

	public static TileItem getNearest(String... names) {
		return TILE_ITEMS.nearest(names);
	}

	public static List<TileItem> getAt(LocalPoint localPoint, Predicate<TileItem> filter) {
		Tile tile = Game.getClient().getScene().getTiles()[Game.getClient().getPlane()][localPoint.getSceneX()][localPoint.getSceneY()];
		if (tile == null) {
			return Collections.emptyList();
		}

		return parseTile(tile, filter);
	}

	public static List<TileItem> getAt(WorldPoint worldPoint, Predicate<TileItem> filter) {
		LocalPoint localPoint = LocalPoint.fromWorld(Game.getClient(), worldPoint);
		if (localPoint == null) {
			return Collections.emptyList();
		}

		Tile tile = Game.getClient().getScene().getTiles()[Game.getClient().getPlane()][localPoint.getSceneX()][localPoint.getSceneY()];
		if (tile == null) {
			return Collections.emptyList();
		}

		return parseTile(tile, filter);
	}

	public static List<TileItem> getAt(Tile tile, Predicate<TileItem> filter) {
		return parseTile(tile, filter);
	}

	private static List<TileItem> parseTile(Tile tile, Predicate<? super TileItem> pred) {
		List<TileItem> out = new ArrayList<>();
		if (tile.getGroundItems() != null) {
			for (TileItem item : tile.getGroundItems()) {
				if (item == null || item.getId() == -1) {
					continue;
				}

				if (!Game.getClient().isItemDefinitionCached(item.getId())) {
					GameThread.invokeLater(() -> Game.getClient().getItemComposition(item.getId()));
				}

				if (!pred.test(item)) {
					continue;
				}

				out.add(item);
			}
		}

		return out;
	}
}
