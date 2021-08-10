package meteor.plugins.illutils.walking;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.concurrent.Future;
import meteor.plugins.illutils.osrs.wrappers.IllInventoryItem;
import meteor.plugins.illutils.osrs.wrappers.IllObject;
import net.runelite.api.Skill;
import meteor.plugins.illutils.osrs.OSRSUtils;
import meteor.plugins.illutils.osrs.wrappers.IllTile;
import meteor.plugins.illutils.api.scene.Area;
import meteor.plugins.illutils.api.scene.ObjectCategory;
import meteor.plugins.illutils.api.scene.Position;
import meteor.plugins.illutils.api.scene.RectangularArea;
import meteor.plugins.illutils.ui.Chatbox;
import meteor.plugins.illutils.util.Util;

import javax.inject.Inject;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.sponge.util.Logger;

public class Walking {
    private static CollisionMap map;
    private static final int MAX_INTERACT_DISTANCE = 20;
    private static final int MIN_TILES_WALKED_IN_STEP = 10;
    private static final int MIN_TILES_WALKED_BEFORE_RECHOOSE = 10; // < MIN_TILES_WALKED_IN_STEP
    private static final int MIN_TILES_LEFT_BEFORE_RECHOOSE = 3; // < MIN_TILES_WALKED_IN_STEP
    private static final Random RANDOM = new Random();
    private static final int MAX_MIN_ENERGY = 50;
    private static final int MIN_ENERGY = 15;
    private static final Area DEATHS_OFFICE = new RectangularArea(3167, 5733, 3184, 5720);
    public static final ExecutorService PATHFINDING_EXECUTOR = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), r -> {
        Thread thread = Executors.defaultThreadFactory().newThread(r);
        thread.setDaemon(true);
        return thread;
    });

    private final OSRSUtils game;
    private final Chatbox chatbox;
    private final Logger log = new Logger("iUtils-Walking");

    private int minEnergy = new Random().nextInt(MAX_MIN_ENERGY - MIN_ENERGY + 1) + MIN_ENERGY;

    static {
        try {
            map = new CollisionMap(Util.ungzip(readAllBytes(Walking.class.getResourceAsStream("/collision-map"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] readAllBytes(InputStream inputStream) throws IOException {
        final int bufLen = 1024;
        byte[] buf = new byte[bufLen];
        int readLen;
        IOException exception = null;

        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            while ((readLen = inputStream.read(buf, 0, bufLen)) != -1)
                outputStream.write(buf, 0, readLen);

            return outputStream.toByteArray();
        } catch (IOException e) {
            exception = e;
            throw e;
        } finally {
            if (exception == null) inputStream.close();
            else try {
                inputStream.close();
            } catch (IOException e) {
                exception.addSuppressed(e);
            }
        }
    }

    @Inject
    public Walking(OSRSUtils game) {
        this.game = game;
        chatbox = new Chatbox(game);
    }

    public void walkTo(Area target) {
        if (target.contains(game.localPlayer().templatePosition())) {
            return;
        }

        if (DEATHS_OFFICE.contains(game.localPlayer().templatePosition())) {
            if (chatbox.chatState() != Chatbox.ChatState.NPC_CHAT) {
                game.npcs().withName("Death").nearest().interact("Talk-to");
                game.waitUntil(() -> chatbox.chatState() == Chatbox.ChatState.NPC_CHAT);
            }

            chatbox.chat(
                    "How do I pay a gravestone fee?",
                    "How long do I have to return to my gravestone?",
                    "How do I know what will happen to my items when I die?",
                    "I think I'm done here."
            );

            game.objects().withName("Portal").nearest().interact("Use");
            game.waitUntil(() -> !DEATHS_OFFICE.contains(game.localPlayer().templatePosition()));
        }

        System.out.println("[Walking] Pathfinding " + game.localPlayer().templatePosition() + " -> " + target);
        HashMap<Position, List<Transport>> transports = new HashMap<>();
        HashMap<Position, List<Position>> transportPositions = new HashMap<>();

        for (Transport transport : TransportLoader.buildTransports(game)) {
            transports.computeIfAbsent(transport.source, k -> new ArrayList<>()).add(transport);
            transportPositions.computeIfAbsent(transport.source, k -> new ArrayList<>()).add(transport.target);
        }

        LinkedHashMap<Position, Teleport> teleports = new LinkedHashMap<>();

        Position playerPosition = game.localPlayer().position();
        for (Teleport teleport : new TeleportLoader(game).buildTeleports()) {
            if (teleport.target.distanceTo(playerPosition) > 50 && (playerPosition.distanceTo(target) > teleport.target.distanceTo(target) + 20)) {
                teleports.putIfAbsent(teleport.target, teleport);
            } else {
                log.info("Teleport not added due to distance reqs: {}", teleport.target);
            }
        }

        ArrayList<Position> starts = new ArrayList<>(teleports.keySet());
        starts.add(game.localPlayer().templatePosition());
        List<Position> path = pathfind(starts, target, transportPositions);

        if (path == null) {
            throw new IllegalStateException("couldn't pathfind " + game.localPlayer().templatePosition() + " -> " + target);
        }

        System.out.println("[Walking] Done pathfinding");

        Position startPosition = path.get(0);
        Teleport teleport = teleports.get(startPosition);

        if (teleport != null) {
            System.out.println("[Walking] Teleporting to path start");
            teleport.handler.run();
            game.waitUntil(() -> game.localPlayer().templatePosition().distanceTo(teleport.target) <= teleport.radius);
        }

        walkAlong(path, transports);
    }

    private List<Position> pathfind(ArrayList<Position> start, Area target, Map<Position, List<Position>> tranports) {
        Future<List<Position>> result = PATHFINDING_EXECUTOR.submit(() -> new Pathfinder(map, tranports, start, target::contains).find());

        while (!result.isDone()) {
            game.tick();
        }

        try {
            return result.get();
        } catch (ExecutionException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void walkAlong(List<Position> path, Map<Position, List<Transport>> transports) {
        Position target = path.get(path.size() - 1);

        int fails = 0;

        while (game.localPlayer().templatePosition().distanceTo(target) > 0) {
            List<Position> remainingPath = remainingPath(path);
            Position start = path.get(0);
            Position current = game.localPlayer().templatePosition();
            Position end = path.get(path.size() - 1);
            int progress = path.size() - remainingPath.size();
            System.out.println("[Walking] " + start + " -> " + current + " -> " + end + ": " + progress + " / " + path.size());

            if (handleBreak(remainingPath, transports)) {
                continue;
            }

            if (!stepAlong(remainingPath)) {
                if (fails++ == 5) {
                    throw new IllegalStateException("stuck in path at " + game.localPlayer().templatePosition());
                }
            } else {
                fails = 0;
            }
        }

        System.out.println("[Walking] Path end reached");
    }

    /**
     * Remaining tiles in a path, including the tile the player is on.
     */
    private List<Position> remainingPath(List<Position> path) {
        Position nearest = path.stream()
                .min(Comparator.comparing(p -> game.localPlayer().templatePosition().distanceTo(p)))
                .orElseThrow(() -> new IllegalArgumentException("empty path"));

        List<Position> remainingPath = path.subList(path.indexOf(nearest), path.size());

        if (remainingPath.isEmpty()) {
            throw new IllegalStateException("too far from path " + game.localPlayer().templatePosition() + " -> " + nearest);
        }

        return remainingPath;
    }

    private boolean handleBreak(List<Position> path, Map<Position, List<Transport>> transports) {
        for (int i = 0; i < MAX_INTERACT_DISTANCE; i++) {
            if (i + 1 >= path.size()) {
                break;
            }

            Position a = path.get(i);
            Position b = path.get(i + 1);
            IllTile tileA = tile(a);
            IllTile tileB = tile(b);

            if (tileA == null) {
                return false;
            }

            List<Transport> transportTargets = transports.get(a);
            Transport transport = transportTargets == null ? null : transportTargets.stream().filter(t -> t.target.equals(b)).findFirst().orElse(null);

            if (transport != null && game.localPlayer().templatePosition().distanceTo(transport.source) <= transport.sourceRadius) {
                handleTransport(transport);
                return true;
            }

            if (hasDiagonalDoor(tileA)) return openDiagonalDoor(a);

            if (tileB == null) {
                return false; // scene edge
            }

            if (hasDoor(tileA) && isWallBlocking(a, b)) return openDoor(a);
            if (hasDoor(tileB) && isWallBlocking(b, a)) return openDoor(b);
        }

        return false;
    }

    private boolean hasDoor(IllTile tile) {
        IllObject wall = tile.object(ObjectCategory.WALL);
        return wall != null && wall.actions().contains("Open");
    }

    private boolean hasDiagonalDoor(IllTile tile) {
        IllObject wall = tile.object(ObjectCategory.REGULAR);
        return wall != null && wall.actions().contains("Open");
    }

    private boolean isWallBlocking(Position a, Position b) {
        switch (tile(a).object(ObjectCategory.WALL).orientation()) {
            case 0:
                return a.west().equals(b) || a.west().north().equals(b) || a.west().south().equals(b);
            case 1:
                return a.north().equals(b) || a.north().west().equals(b) || a.north().east().equals(b);
            case 2:
                return a.east().equals(b) || a.east().north().equals(b) || a.east().south().equals(b);
            case 3:
                return a.south().equals(b) || a.south().west().equals(b) || a.south().east().equals(b);
            default:
                throw new AssertionError();
        }
    }

    private boolean openDoor(Position position) {
        tile(position).object(ObjectCategory.WALL).interact("Open");
        game.waitUntil(this::isStill);
        game.tick();
        return true;
    }


    private boolean openDiagonalDoor(Position position) {
        Objects.requireNonNull(tile(position)).object(ObjectCategory.REGULAR).interact("Open");
        game.tick();
        game.waitUntil(this::isStill);
        return true;
    }

    private void handleTransport(Transport transport) {
        System.out.println("[Walking] Handling transport " + transport.source + " -> " + transport.target);
        transport.handler.accept(game);

        // TODO: if the player isn't on the transport source tile, interacting with the transport may cause the
        //   player to walk to a different source tile for the same transport, which has a different destination
        game.waitUntil(() -> game.localPlayer().templatePosition().distanceTo(transport.target) <= transport.targetRadius, 10);
        game.tick(5);
    }

    private boolean stepAlong(List<Position> path) {
        path = reachablePath(path);
        if (path == null) return false;

        if (path.size() - 1 <= MIN_TILES_WALKED_IN_STEP) {
            return step(path.get(path.size() - 1), Integer.MAX_VALUE);
        }

        int targetDistance = MIN_TILES_WALKED_IN_STEP + RANDOM.nextInt(path.size() - MIN_TILES_WALKED_IN_STEP);
        int rechooseDistance = rechooseDistance(targetDistance);

        return step(path.get(targetDistance), rechooseDistance);
    }

    private int rechooseDistance(int targetDistance) {
        int rechoose = MIN_TILES_WALKED_BEFORE_RECHOOSE + RANDOM.nextInt(targetDistance - MIN_TILES_WALKED_BEFORE_RECHOOSE + 1);
        rechoose = Math.min(rechoose, targetDistance - MIN_TILES_LEFT_BEFORE_RECHOOSE); // don't get too near the end of the path, to avoid stopping
        return rechoose;
    }

    /**
     * Interacts with the target tile to walk to it, and waits for the player to either
     * reach it, or walk {@code tiles} tiles towards it before returning.
     *
     * @return
     */
    private boolean step(Position target, int tiles) {
        if (game.inInstance()) {
            tile(game.instancePositions(target).get(0)).walkTo();
        } else {
            tile(target).walkTo();
        }
        int ticksStill = 0;

        for (int tilesWalked = 0; tilesWalked < tiles; tilesWalked += isRunning() ? 2 : 1) {
            if (game.localPlayer().templatePosition().equals(target)) {
                return false;
            }

            Position oldPosition = game.localPlayer().templatePosition();
            game.tick();

            if (game.localPlayer().templatePosition().equals(oldPosition)) {
                if (++ticksStill == 5) {
                    return false;
                }
            } else {
                ticksStill = 0;
            }

            if (!isRunning() && game.energy() > minEnergy) {
                minEnergy = new Random().nextInt(MAX_MIN_ENERGY - MIN_ENERGY + 1) + MIN_ENERGY;
                System.out.println("[Walking] Enabling run, next minimum run energy: " + minEnergy);
                setRun(true);
            }

            if (game.inventory().withNamePart("Stamina potion").exists() && game.energy() < 70 && (game.varb(25) == 0 || game.energy() <= 4)) {
                IllInventoryItem staminaPotion = game.inventory().withNamePart("Stamina potion").first();
                staminaPotion.interact("Drink");
                game.waitUntil(() -> game.varb(25) == 1 && game.energy() >= 20);
            }

            if (game.varb(25) == 1) {
                setRun(true); // don't waste stamina effect
            }

            if (game.modifiedLevel(Skill.HITPOINTS) < 8 || game.modifiedLevel(Skill.HITPOINTS) < game.baseLevel(Skill.HITPOINTS) - 22) {
                IllInventoryItem food = game.inventory().withAction("Eat").first();

                if (food != null) {
                    food.interact("Eat");
                }
            }
        }

        return true;
    }

    /**
     * Tiles in a remaining path which can be walked to (including the tile the
     * player is currently on).
     */
    private List<Position> reachablePath(List<Position> remainingPath) {
        ArrayList<Position> reachable = new ArrayList<>();

        for (Position position : remainingPath) {
            if (game.tile(position) == null || position.distanceTo(game.localPlayer().templatePosition()) >= MAX_INTERACT_DISTANCE) {
                break;
            }

            reachable.add(position);
        }

        if (reachable.isEmpty() || reachable.size() == 1 && reachable.get(0).equals(game.localPlayer().templatePosition())) {
//            throw new IllegalStateException("no tiles in the path are reachable");
            return null;
        }

        return reachable;
    }

    private IllTile tile(Position position) {
        if (game.inInstance()) {
            List<Position> instancePositions = game.instancePositions(position);

            if (instancePositions.isEmpty()) {
                return null;
            }

            return game.tile(instancePositions.get(0));
        } else {
            return game.tile(position);
        }
    }

    public void setRun(boolean run) {
        if (isRunning() != run) {
            game.widget(160, 23).interact(0);
            game.waitUntil(() -> isRunning() == run);
        }
    }

    public boolean isRunning() {
        return game.varp(173) == 1;
    }

    public boolean reachable(Area target) {
        if (game.localPlayer().templatePosition().equals(target)) {
            return true;
        }

        List<Position> l = new ArrayList<>();
        l.add(game.localPlayer().templatePosition());
        List<Position> path = new Pathfinder(map, Collections.emptyMap(), l, target::contains).find();

        if (path == null) {
            return false;
        }

        for (Position position : path) {
            IllObject wallObject = tile(position).object(ObjectCategory.WALL);

            if (wallObject != null && wallObject.actions().contains("Open")) {
                return false;
            }
        }

        return true;
    }

    public boolean isStill() {
        return game.localPlayer().idlePoseAnimation() == game.localPlayer().poseAnimation();
    }
}
