package meteor.plugins.api.entities;

import meteor.plugins.api.game.Game;
import meteor.plugins.api.game.GameThread;
import meteor.plugins.api.scene.Tiles;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;

import javax.inject.Inject;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TileObjects extends Entities<TileObject> {
	private static final TileObjects TILE_OBJECTS = new TileObjects();

	@Override
	protected List<TileObject> all(Predicate<? super TileObject> filter) {
		return getTileObjects(Tiles.getTiles()).stream()
						.filter(filter)
						.collect(Collectors.toList());
	}

	public static List<TileObject> getAll(Predicate<TileObject> filter) {
		return TILE_OBJECTS.all(filter);
	}

	public static List<TileObject> getAll(int... ids) {
		return TILE_OBJECTS.all(ids);
	}

	public static List<TileObject> getAll(String... names) {
		return TILE_OBJECTS.all(names);
	}

	public static TileObject getNearest(Predicate<TileObject> filter) {
		return TILE_OBJECTS.nearest(filter);
	}

	public static TileObject getNearest(int... ids) {
		return TILE_OBJECTS.nearest(ids);
	}

	public static TileObject getNearest(String... names) {
		return TILE_OBJECTS.nearest(names);
	}

	public static List<TileObject> getAt(LocalPoint localPoint, Predicate<TileObject> filter) {
		Tile tile = Game.getClient().getScene().getTiles()[Game.getClient().getPlane()][localPoint.getSceneX()][localPoint.getSceneY()];
		if (tile == null) {
			return Collections.emptyList();
		}

		return parseTile(tile, filter);
	}

	public static List<TileObject> getAt(WorldPoint worldPoint, Predicate<TileObject> filter) {
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

	public static List<TileObject> getAt(Tile tile, Predicate<TileObject> filter) {
		return parseTile(tile, filter);
	}

	private static List<TileObject> parseTile(Tile tile, Predicate<TileObject> pred) {
		Predicate<TileObject> filter = x -> {
			if (!x.isDefinitionCached()) {
				return GameThread.invokeLater(() -> {
					x.getCachedDefinition(); // cache it
					return pred.test(x);
				});
			}

			return pred.test(x);
		};

		return getTileObjects(tile).stream()
						.filter(filter)
						.collect(Collectors.toList());
	}

	private static List<TileObject> getTileObjects(List<Tile> tiles) {
		List<TileObject> out = new ArrayList<>();
		for (Tile tile : tiles) {
			out.addAll(getTileObjects(tile));
		}

		List<TileObject> notCached = out.stream()
						.filter(x -> !x.isDefinitionCached())
						.collect(Collectors.toList());
		if (!notCached.isEmpty()) {
			GameThread.invokeLater(() -> {
				for (TileObject tileObject : notCached) {
					tileObject.getDefinition();
				}

				return true;
			});
		}

		return out;
	}

	private static List<TileObject> getTileObjects(Tile tile) {
		List<TileObject> out = new ArrayList<>();
		DecorativeObject dec = tile.getDecorativeObject();
		if (dec != null && dec.getId() != -1) {
			out.add(dec);
		}

		WallObject wall = tile.getWallObject();
		if (wall != null && wall.getId() != -1) {
			out.add(wall);
		}

		GroundObject grnd = tile.getGroundObject();
		if (grnd != null && grnd.getId() != -1) {
			out.add(grnd);
		}

		GameObject[] gameObjects = tile.getGameObjects();
		if (gameObjects != null) {
			for (GameObject gameObject : gameObjects) {
				if (gameObject == null
								|| !Game.getClient().isTileObjectValid(tile, gameObject)
								|| gameObject.getId() == -1) {
					continue;
				}

				out.add(gameObject);
			}
		}

		return out;
	}
}
