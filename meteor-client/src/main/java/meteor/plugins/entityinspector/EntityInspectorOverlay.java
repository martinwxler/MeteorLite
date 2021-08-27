package meteor.plugins.entityinspector;

import meteor.plugins.api.entities.*;
import meteor.plugins.api.game.GameThread;
import meteor.plugins.api.movement.Movement;
import meteor.plugins.api.scene.Tiles;
import meteor.plugins.api.widgets.Widgets;
import meteor.ui.FontManager;
import meteor.ui.overlay.*;
import net.runelite.api.Point;
import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.widgets.Widget;
import net.runelite.api.widgets.WidgetInfo;
import net.runelite.api.widgets.WidgetItem;

import javax.inject.Inject;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;
import java.util.List;

public class EntityInspectorOverlay extends Overlay {
	private static final Font FONT = FontManager.getRunescapeFont().deriveFont(Font.BOLD, 16);
	private static final Color RED = new Color(221, 44, 0);
	private static final Color GREEN = new Color(0, 200, 83);
	private static final Color TURQOISE = new Color(0, 200, 157);
	private static final Color ORANGE = new Color(255, 109, 0);
	private static final Color YELLOW = new Color(255, 214, 0);
	private static final Color CYAN = new Color(0, 184, 212);
	private static final Color BLUE = new Color(41, 98, 255);
	private static final Color DEEP_PURPLE = new Color(98, 0, 234);
	private static final Color PURPLE = new Color(170, 0, 255);
	private static final Color GRAY = new Color(158, 158, 158);

	private final Client client;
	private final EntityInspectorConfig config;

	@Inject
	private EntityInspectorOverlay(Client client, EntityInspectorConfig config) {
		this.client = client;
		this.config = config;

		setPosition(OverlayPosition.DYNAMIC);
		setLayer(OverlayLayer.ABOVE_WIDGETS);
		setPriority(OverlayPriority.HIGHEST);
	}

	@Override
	public Dimension render(Graphics2D g) {
		g.setFont(FONT);

		if (config.gameObjects() || config.groundObjects() || config.wallObjects() || config.decorObjects()) {
			renderTileObjects(g);
		}

		if (config.tileItems()) {
			renderTileItems(g);
		}

		if (config.npcs()) {
			renderNpcs(g);
		}

		if (config.players()) {
			renderPlayers(g);
		}

		if (config.projectiles()) {
			renderProjectiles(g);
		}

		if (config.graphicsObjects()) {
			renderGraphicsObjects(g);
		}

		if (config.inventory()) {
			renderInventory(g);
		}

		Tile hoveredTile = Tiles.getHoveredTile();

		if (config.tileLocation()) {
			renderTileTooltip(g, hoveredTile);
		}

		if (config.path()) {
			Movement.drawPath(g, hoveredTile.getWorldLocation());
		}

		if (config.collisions()) {
			Movement.drawCollisions(g);
		}

		return null;
	}

	public void renderPlayers(Graphics2D graphics) {
		List<Player> players = Players.getAll();
		Player local = Players.getLocal();
		Point point = client.getMouseCanvasPosition();

		for (Player p : players) {
			if (!p.equals(local) && p.getConvexHull() != null && p.getConvexHull().contains(point.getX(), point.getY())) {
				String text = "Name: " + p.getName() + "\n" +
								"Actions: " + Arrays.toString(client.getPlayerOptions()) + "\n" +
								"Anim: " + p.getAnimation() + "\n" +
								"Graphic: " + p.getGraphic() + "\n" +
								"Loc: " + p.getWorldLocation() + "\n" +
								"Index: " + p.getPlayerId();
				graphics.setColor(BLUE);
				graphics.draw(p.getConvexHull());

				OverlayUtil.renderActorParagraph(graphics, p, text, BLUE);
			}
		}

		if (local != null && local.getConvexHull() != null && local.getConvexHull().contains(point.getX(), point.getY())) {
			String text = "Name: " + local.getName() + "\n" +
							"Actions: " + Arrays.toString(client.getPlayerOptions()) + "\n" +
							"Anim: " + local.getAnimation() + "\n" +
							"Graphic: " + local.getGraphic() + "\n" +
							"Loc: " + local.getWorldLocation() + "\n" +
							"Index: " + local.getPlayerId();

			graphics.setColor(CYAN);

			OverlayUtil.renderActorParagraph(graphics, local, text, CYAN);
			renderPlayerWireframe(graphics, local, CYAN);
		}
	}

	public void renderNpcs(Graphics2D graphics) {
		List<NPC> npcs = NPCs.getAll(x -> true);
		Point point = client.getMouseCanvasPosition();

		for (NPC npc : npcs) {
			Color color = npc.getCombatLevel() > 1 ? YELLOW : ORANGE;
			graphics.setColor(color);

			if (npc.getConvexHull() != null && npc.getConvexHull().contains(point.getX(), point.getY())) {
				graphics.draw(npc.getConvexHull());

				String text = "Name: " + npc.getName() + "\n" +
								"ID: " + npc.getId() + "\n" +
								"Actions: " + Arrays.toString(npc.getActions()) + "\n" +
								"Moving: " + npc.isMoving() + "\n" +
								"Anim: " + npc.getAnimation() + "\n" +
								"Graphic: " + npc.getGraphic() + "\n" +
								"Loc: " + npc.getWorldLocation() + "\n" +
								"Index: " + npc.getIndex();

				OverlayUtil.renderActorParagraph(graphics, npc, text, color);
			}
		}
	}

	public void renderTileObjects(Graphics2D graphics) {
		List<TileObject> tileObjects = TileObjects.getAll(x -> true);
		for (TileObject tileObject : tileObjects) {
			if (tileObject instanceof GameObject gameObject && config.gameObjects()) {
				renderGameObjects(graphics, gameObject);
				continue;
			}

			if (tileObject instanceof WallObject wallObject && config.wallObjects()) {
				renderWallObject(graphics, wallObject);
				continue;
			}

			if (tileObject instanceof GroundObject groundObject && config.groundObjects()) {
				renderGroundObject(graphics, groundObject);
				continue;
			}

			if (tileObject instanceof DecorativeObject decorativeObject && config.decorObjects()) {
				renderDecorObject(graphics, decorativeObject);
			}
		}
	}

	public void renderTileTooltip(Graphics2D graphics, Tile tile) {
		Polygon poly = Perspective.getCanvasTilePoly(client, tile.getLocalLocation());
		if (poly != null && poly.contains(client.getMouseCanvasPosition().getX(), client.getMouseCanvasPosition().getY())) {
			OverlayUtil.renderPolygon(graphics, poly, GREEN);
			OverlayUtil.renderTextLocation(graphics, client.getMouseCanvasPosition(),
							"World Location: " + tile.getWorldLocation().getX() + ", " + tile.getWorldLocation().getY() + ", " + client.getPlane(), Color.GREEN);
		}
	}

	public void renderTileItems(Graphics2D graphics) {
		List<TileItem> tileItems = TileItems.getAll(x -> true);
		Point point = client.getMouseCanvasPosition();

		for (TileItem tileItem : tileItems) {
			ItemLayer tileItemPile = tileItem.getTile().getItemLayer();
			if (tileItemPile != null
							&& tileItemPile.getCanvasTilePoly() != null
							&& tileItemPile.getCanvasTilePoly().contains(point.getX(), point.getY())) {
				Node current = tileItemPile.getBottom();
				while (current instanceof TileItem item) {
					String sb = "ID: " + item.getId() + "\n" +
									"Qty: " + item.getQuantity() + "\n" +
									"Loc: " + item.getTile().getWorldLocation() + "\n" +
									"Actions: " + Arrays.toString(item.getActions());
					OverlayUtil.renderTileOverlayParagraph(graphics, tileItemPile, sb, RED);
					current = current.getNext();
				}
			}
		}
	}

	public void renderGameObjects(Graphics2D graphics, GameObject gameObject) {
		Point point = client.getMouseCanvasPosition();

		if (gameObject != null && gameObject.getConvexHull() != null
						&& gameObject.getConvexHull().contains(point.getX(), point.getY())) {
			Renderable entity = gameObject.getRenderable();
			Color color = entity instanceof DynamicObject ? TURQOISE : GREEN;

			StringBuilder sb = new StringBuilder();
			sb.append("ID: ").append(gameObject.getId()).append("\n");
			if (entity instanceof DynamicObject) {
				sb.append("Anim: ").append(((DynamicObject) entity).getAnimationID()).append("\n");
			}

			sb.append("Loc: ").append(gameObject.getWorldLocation()).append("\n");
			sb.append("Actions: ").append(Arrays.toString(client.getObjectDefinition(gameObject.getId()).getActions()));

			graphics.setColor(color);
			graphics.draw(gameObject.getConvexHull());

			OverlayUtil.renderTileOverlayParagraph(graphics, gameObject, sb.toString(), color);
		}
	}

	public void renderGroundObject(Graphics2D graphics, GroundObject groundObject) {
		Point point = client.getMouseCanvasPosition();

		if (groundObject != null && groundObject.getConvexHull() != null
						&& groundObject.getConvexHull().contains(point.getX(), point.getY())) {
			String sb = "ID: " + groundObject.getId() + "\n" +
							"Loc: " + groundObject.getWorldLocation() + "\n" +
							"Actions: " + Arrays.toString(client.getObjectDefinition(groundObject.getId()).getActions());
			OverlayUtil.renderTileOverlayParagraph(graphics, groundObject, sb, PURPLE);
		}
	}

	public void renderWallObject(Graphics2D graphics, WallObject wallObject) {
		Point point = client.getMouseCanvasPosition();

		if (wallObject != null && wallObject.getConvexHull() != null
						&& wallObject.getConvexHull().contains(point.getX(), point.getY())) {
			String sb = "ID: " + wallObject.getId() + "\n" +
							"Loc: " + wallObject.getWorldLocation() + "\n" +
							"Actions: " + Arrays.toString(client.getObjectDefinition(wallObject.getId()).getActions());
			OverlayUtil.renderTileOverlayParagraph(graphics, wallObject, sb, GRAY);
		}
	}

	public void renderDecorObject(Graphics2D graphics, DecorativeObject decorObject) {
		if (decorObject != null) {
			OverlayUtil.renderTileOverlay(graphics, decorObject, "ID: " + decorObject.getId(), DEEP_PURPLE);

			Shape p = decorObject.getConvexHull();
			if (p != null) {
				graphics.draw(p);
			}

			p = decorObject.getConvexHull2();
			if (p != null) {
				graphics.draw(p);
			}
		}
	}

	public void renderInventory(Graphics2D graphics) {
		Widget inventoryWidget = Widgets.get(WidgetInfo.INVENTORY);
		if (inventoryWidget == null || GameThread.invokeLater(inventoryWidget::isHidden)) {
			return;
		}

		for (WidgetItem item : inventoryWidget.getWidgetItems()) {
			Rectangle slotBounds = item.getCanvasBounds();

			String idText = "" + item.getId();
			FontMetrics fm = graphics.getFontMetrics();
			Rectangle2D textBounds = fm.getStringBounds(idText, graphics);

			int textX = (int) (slotBounds.getX() + (slotBounds.getWidth() / 2) - (textBounds.getWidth() / 2));
			int textY = (int) (slotBounds.getY() + (slotBounds.getHeight() / 2) + (textBounds.getHeight() / 2));

			graphics.setColor(new Color(255, 255, 255, 65));
			graphics.fill(slotBounds);

			graphics.setColor(Color.BLACK);
			graphics.drawString(idText, textX + 1, textY + 1);
			graphics.setColor(YELLOW);
			graphics.drawString(idText, textX, textY);
		}
	}

	public void renderProjectiles(Graphics2D graphics) {
		List<Projectile> projectiles = Projectiles.getAll();

		for (Projectile projectile : projectiles) {
			int originX = projectile.getX1();
			int originY = projectile.getY1();

			LocalPoint tilePoint = new LocalPoint(originX, originY);
			Polygon poly = Perspective.getCanvasTilePoly(client, tilePoint);

			if (poly != null) {
				OverlayUtil.renderPolygon(graphics, poly, Color.RED);
			}

			int projectileId = projectile.getId();
			Actor projectileInteracting = projectile.getInteracting();

			String infoString = "";

			if (projectileInteracting == null) {
				infoString += "AoE";
			} else {
				infoString += "Targeted (T: " + projectileInteracting.getName() + ")";
			}

			infoString += " (ID: " + projectileId + ")";

			if (projectileInteracting != null) {
				OverlayUtil.renderActorOverlay(graphics, projectile.getInteracting(), infoString, Color.RED);
			} else {
				LocalPoint projectilePoint = new LocalPoint((int) projectile.getX(), (int) projectile.getY());
				Point textLocation = Perspective.getCanvasTextLocation(client, graphics, projectilePoint, infoString, 0);

				if (textLocation != null) {
					OverlayUtil.renderTextLocation(graphics, textLocation, infoString, Color.RED);
				}
			}
		}
	}

	public void renderGraphicsObjects(Graphics2D graphics) {
		List<GraphicsObject> graphicsObjects = client.getGraphicsObjects();

		for (GraphicsObject graphicsObject : graphicsObjects) {
			LocalPoint lp = graphicsObject.getLocation();
			Polygon poly = Perspective.getCanvasTilePoly(client, lp);

			if (poly != null) {
				OverlayUtil.renderPolygon(graphics, poly, Color.MAGENTA);
			}

			String infoString = "(ID: " + graphicsObject.getId() + ")";
			Point textLocation = Perspective.getCanvasTextLocation(
							client, graphics, lp, infoString, 0);
			if (textLocation != null) {
				OverlayUtil.renderTextLocation(graphics, textLocation, infoString, Color.WHITE);
			}
		}
	}

	public void renderPlayerWireframe(Graphics2D graphics, Player player, Color color) {
		Polygon[] polys = player.getPolygons();

		if (polys == null) {
			return;
		}

		graphics.setColor(color);

		for (Polygon p : polys) {
			graphics.drawPolygon(p);
		}
	}
}
