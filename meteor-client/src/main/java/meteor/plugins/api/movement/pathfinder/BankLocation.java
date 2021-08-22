package meteor.plugins.api.movement.pathfinder;

import meteor.plugins.api.coords.RectangularArea;
import net.runelite.api.coords.WorldPoint;

import java.util.Arrays;
import java.util.Comparator;

public enum BankLocation {
    LUMBRIDGE_BANK(new RectangularArea(3207, 3222, 3210, 3215, 2)),
    VARROCK_WEST_BANK(new RectangularArea(3180, 3447, 3190, 3433, 0)),
    VARROCK_EAST_BANK(new RectangularArea(3250, 3424, 3257, 3416, 0)),
    GRAND_EXCHANGE_BANK(new RectangularArea(3160, 3495, 3170, 3484, 0)),
    EDGEVILLE_BANK(new RectangularArea(3091, 3499, 3098, 3488, 0)),
    FALADOR_EAST_BANK(new RectangularArea(3009, 3358, 3018, 3355)),
    FALADOR_WEST_BANK(new RectangularArea(2943, 3373, 2947, 3368)),
    DRAYNOR_BANK(new RectangularArea(3088, 3246, 3097, 3240, 0)),
    DUEL_ARENA_BANK(new RectangularArea(3380, 3273, 3384, 3267, 0)),
    SHANTAY_PASS_BANK(new RectangularArea(3304, 3125, 3312, 3117, 0)),
    AL_KHARID_BANK(new RectangularArea(3265, 3173, 3272, 3161, 0)),
    CATHERBY_BANK(new RectangularArea(2806, 3445, 2812, 3438, 0)),
    SEERS_VILLAGE_BANK(new RectangularArea(2721, 3493, 2730, 3490, 0)),
    ARDOUGNE_NORTH_BANK(new RectangularArea(2612, 3335, 2621, 3330, 0)),
    ARDOUGNE_SOUTH_BANK(new RectangularArea(2649, 3287, 2658, 3280, 0)),
    PORT_KHAZARD_BANK(new RectangularArea(2659, 3164, 2665, 3158)),
    YANILLE_BANK(new RectangularArea(2609, 3097, 2616, 3088, 0)),
    CORSAIR_COVE_BANK(new RectangularArea(2568, 2867, 2572, 2863, 0)),
    CASTLE_WARS_BANK(new RectangularArea(2442, 3084, 2445, 3082, 0)),
    LLETYA_BANK(new RectangularArea(2350, 3166, 2354, 3161, 0)),
    GRAND_TREE_WEST_BANK(new RectangularArea(2438, 3489, 2442, 3487, 1)),
    GRAND_TREE_SOUTH_BANK(new RectangularArea(2448, 3482, 2450, 3478, 1)),
    TREE_GNOME_STRONGHOLD_BANK(new RectangularArea(2443, 3427, 2448, 3422, 1)),
    SHILO_VILLAGE_BANK(new RectangularArea(2851, 2957, 2853, 2951, 0)),
    NEITIZNOT_BANK(new RectangularArea(2334, 3808, 2339, 3805, 0)),
    JATIZSO_BANK(new RectangularArea(2415, 3803, 2418, 3799, 0)),
    BARBARIAN_OUTPOST_BANK(new RectangularArea(2533, 3576, 2537, 3572, 0)),
    ETCETERIA_BANK(new RectangularArea(2618, 3896, 2621, 3893, 0)),
    DARKMEYER_BANK(new RectangularArea(3601, 3370, 3609, 3365, 0)),
    CHARCOAL_BURNERS_BANK(new RectangularArea(1711, 3469, 1723, 3460, 0)),
    HOSIDIUS_BANK(new RectangularArea(1749, 3594, 1745, 3603, 0)),
    PORT_PISCARILIUS_BANK(new RectangularArea(1794, 3793, 1811, 3784)),
    HALLOWED_SEPULCHRE_BANK(new RectangularArea(2383, 5997, 2420, 5963)),
    CANIFIS_BANK(new RectangularArea(3509, 3483, 3516, 3478)),
    BURGH_DE_ROTT_BANK(new RectangularArea(3492, 3213, 3496, 3210)),
    VER_SINHAZA_BANK(new RectangularArea(3649, 3208, 3652, 3209));

    private final RectangularArea area;

    BankLocation(RectangularArea area) {
        this.area = area;
    }

    public RectangularArea getArea() {
        return area;
    }

    public static BankLocation getNearest(WorldPoint worldPoint) {
        return Arrays.stream(values())
                .min(Comparator.comparingInt(x -> x.getArea().getCenter().distanceTo(worldPoint)))
                .orElse(null);
    }
}
