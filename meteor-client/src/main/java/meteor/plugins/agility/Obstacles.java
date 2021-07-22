/*
 * Copyright (c) 2018, SomeoneWithAnInternetConnection
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
package meteor.plugins.agility;

import static net.runelite.api.NullObjectID.NULL_10872;
import static net.runelite.api.NullObjectID.NULL_10873;
import static net.runelite.api.NullObjectID.NULL_12945;
import static net.runelite.api.NullObjectID.NULL_18083;
import static net.runelite.api.NullObjectID.NULL_18116;
import static net.runelite.api.NullObjectID.NULL_18122;
import static net.runelite.api.NullObjectID.NULL_18124;
import static net.runelite.api.NullObjectID.NULL_18129;
import static net.runelite.api.NullObjectID.NULL_18130;
import static net.runelite.api.NullObjectID.NULL_18132;
import static net.runelite.api.NullObjectID.NULL_18133;
import static net.runelite.api.NullObjectID.NULL_18135;
import static net.runelite.api.NullObjectID.NULL_18136;
import static net.runelite.api.NullObjectID.NULL_3550;
import static net.runelite.api.NullObjectID.NULL_36241;
import static net.runelite.api.NullObjectID.NULL_36242;
import static net.runelite.api.NullObjectID.NULL_36243;
import static net.runelite.api.NullObjectID.NULL_36244;
import static net.runelite.api.NullObjectID.NULL_36245;
import static net.runelite.api.NullObjectID.NULL_36246;
import static net.runelite.api.NullObjectID.NULL_39524;
import static net.runelite.api.NullObjectID.NULL_39525;
import static net.runelite.api.NullObjectID.NULL_39526;
import static net.runelite.api.NullObjectID.NULL_39527;
import static net.runelite.api.NullObjectID.NULL_39528;
import static net.runelite.api.NullObjectID.NULL_39533;
import static net.runelite.api.ObjectID.BALANCING_LEDGE;
import static net.runelite.api.ObjectID.BALANCING_LEDGE_23547;
import static net.runelite.api.ObjectID.BALANCING_LEDGE_3561;
import static net.runelite.api.ObjectID.BALANCING_ROPE;
import static net.runelite.api.ObjectID.BALANCING_ROPE_23557;
import static net.runelite.api.ObjectID.BANNER_14937;
import static net.runelite.api.ObjectID.BARRICADE_18054;
import static net.runelite.api.ObjectID.BAR_42213;
import static net.runelite.api.ObjectID.BASALT_ROCK;
import static net.runelite.api.ObjectID.BASALT_ROCK_4552;
import static net.runelite.api.ObjectID.BASALT_ROCK_4553;
import static net.runelite.api.ObjectID.BASALT_ROCK_4554;
import static net.runelite.api.ObjectID.BASALT_ROCK_4555;
import static net.runelite.api.ObjectID.BASALT_ROCK_4556;
import static net.runelite.api.ObjectID.BASALT_ROCK_4557;
import static net.runelite.api.ObjectID.BASALT_ROCK_4558;
import static net.runelite.api.ObjectID.BASKET_14935;
import static net.runelite.api.ObjectID.BEACH;
import static net.runelite.api.ObjectID.BEAM;
import static net.runelite.api.ObjectID.BEAM_42220;
import static net.runelite.api.ObjectID.BOAT_17961;
import static net.runelite.api.ObjectID.BOILER_22635;
import static net.runelite.api.ObjectID.CABLE;
import static net.runelite.api.ObjectID.CABLE_22569;
import static net.runelite.api.ObjectID.CABLE_22572;
import static net.runelite.api.ObjectID.CHIMNEY_36227;
import static net.runelite.api.ObjectID.CLIMBING_ROCKS_10851;
import static net.runelite.api.ObjectID.CLOTHES_LINE;
import static net.runelite.api.ObjectID.CONSOLE;
import static net.runelite.api.ObjectID.CRATE_11632;
import static net.runelite.api.ObjectID.CRUMBLING_WALL_1948;
import static net.runelite.api.ObjectID.DARK_HOLE_36229;
import static net.runelite.api.ObjectID.DARK_HOLE_36238;
import static net.runelite.api.ObjectID.DOORWAY_10855;
import static net.runelite.api.ObjectID.DOOR_18091;
import static net.runelite.api.ObjectID.DRYING_LINE;
import static net.runelite.api.ObjectID.EDGE;
import static net.runelite.api.ObjectID.EDGE_14925;
import static net.runelite.api.ObjectID.EDGE_14931;
import static net.runelite.api.ObjectID.EDGE_42218;
import static net.runelite.api.ObjectID.EDGE_42219;
import static net.runelite.api.ObjectID.FLOORBOARDS;
import static net.runelite.api.ObjectID.FLOORBOARDS_18071;
import static net.runelite.api.ObjectID.FLOORBOARDS_18072;
import static net.runelite.api.ObjectID.FLOORBOARDS_18073;
import static net.runelite.api.ObjectID.FLOORBOARDS_18089;
import static net.runelite.api.ObjectID.FLOORBOARDS_18090;
import static net.runelite.api.ObjectID.FLOORBOARDS_18093;
import static net.runelite.api.ObjectID.FLOORBOARDS_18094;
import static net.runelite.api.ObjectID.FLOORBOARDS_18097;
import static net.runelite.api.ObjectID.FLOORBOARDS_18098;
import static net.runelite.api.ObjectID.FLOORBOARDS_18109;
import static net.runelite.api.ObjectID.FLOORBOARDS_18110;
import static net.runelite.api.ObjectID.FLOORBOARDS_18111;
import static net.runelite.api.ObjectID.FLOORBOARDS_18112;
import static net.runelite.api.ObjectID.FLOORBOARDS_18113;
import static net.runelite.api.ObjectID.FLOORBOARDS_18114;
import static net.runelite.api.ObjectID.FLOORBOARDS_18117;
import static net.runelite.api.ObjectID.FLOORBOARDS_18118;
import static net.runelite.api.ObjectID.GAP_10859;
import static net.runelite.api.ObjectID.GAP_10861;
import static net.runelite.api.ObjectID.GAP_10882;
import static net.runelite.api.ObjectID.GAP_10884;
import static net.runelite.api.ObjectID.GAP_11631;
import static net.runelite.api.ObjectID.GAP_14399;
import static net.runelite.api.ObjectID.GAP_14414;
import static net.runelite.api.ObjectID.GAP_14833;
import static net.runelite.api.ObjectID.GAP_14834;
import static net.runelite.api.ObjectID.GAP_14835;
import static net.runelite.api.ObjectID.GAP_14844;
import static net.runelite.api.ObjectID.GAP_14845;
import static net.runelite.api.ObjectID.GAP_14846;
import static net.runelite.api.ObjectID.GAP_14847;
import static net.runelite.api.ObjectID.GAP_14848;
import static net.runelite.api.ObjectID.GAP_14897;
import static net.runelite.api.ObjectID.GAP_14903;
import static net.runelite.api.ObjectID.GAP_14904;
import static net.runelite.api.ObjectID.GAP_14919;
import static net.runelite.api.ObjectID.GAP_14928;
import static net.runelite.api.ObjectID.GAP_14929;
import static net.runelite.api.ObjectID.GAP_14930;
import static net.runelite.api.ObjectID.GAP_14938;
import static net.runelite.api.ObjectID.GAP_14947;
import static net.runelite.api.ObjectID.GAP_14990;
import static net.runelite.api.ObjectID.GAP_14991;
import static net.runelite.api.ObjectID.GAP_15609;
import static net.runelite.api.ObjectID.GAP_15610;
import static net.runelite.api.ObjectID.GAP_15611;
import static net.runelite.api.ObjectID.GAP_15612;
import static net.runelite.api.ObjectID.GAP_42216;
import static net.runelite.api.ObjectID.GATE_21172;
import static net.runelite.api.ObjectID.GATE_38460;
import static net.runelite.api.ObjectID.HAND_HOLDS_14901;
import static net.runelite.api.ObjectID.HAND_HOLDS_3583;
import static net.runelite.api.ObjectID.HURDLE;
import static net.runelite.api.ObjectID.HURDLE_11639;
import static net.runelite.api.ObjectID.HURDLE_11640;
import static net.runelite.api.ObjectID.ICE;
import static net.runelite.api.ObjectID.ICE_21149;
import static net.runelite.api.ObjectID.ICE_21150;
import static net.runelite.api.ObjectID.ICE_21151;
import static net.runelite.api.ObjectID.ICE_21152;
import static net.runelite.api.ObjectID.ICE_21153;
import static net.runelite.api.ObjectID.ICE_21154;
import static net.runelite.api.ObjectID.ICE_21155;
import static net.runelite.api.ObjectID.ICE_21156;
import static net.runelite.api.ObjectID.ICICLES;
import static net.runelite.api.ObjectID.JUTTING_WALL_22552;
import static net.runelite.api.ObjectID.LADDER_16682;
import static net.runelite.api.ObjectID.LADDER_17999;
import static net.runelite.api.ObjectID.LADDER_18000;
import static net.runelite.api.ObjectID.LADDER_18001;
import static net.runelite.api.ObjectID.LADDER_18002;
import static net.runelite.api.ObjectID.LADDER_22564;
import static net.runelite.api.ObjectID.LADDER_36221;
import static net.runelite.api.ObjectID.LADDER_36231;
import static net.runelite.api.ObjectID.LADDER_36232;
import static net.runelite.api.ObjectID.LADDER_42209;
import static net.runelite.api.ObjectID.LEDGE_10860;
import static net.runelite.api.ObjectID.LEDGE_10886;
import static net.runelite.api.ObjectID.LEDGE_10888;
import static net.runelite.api.ObjectID.LEDGE_14836;
import static net.runelite.api.ObjectID.LEDGE_14920;
import static net.runelite.api.ObjectID.LEDGE_14921;
import static net.runelite.api.ObjectID.LEDGE_14922;
import static net.runelite.api.ObjectID.LEDGE_14923;
import static net.runelite.api.ObjectID.LEDGE_14924;
import static net.runelite.api.ObjectID.LOG_BALANCE;
import static net.runelite.api.ObjectID.LOG_BALANCE_23144;
import static net.runelite.api.ObjectID.LOG_BALANCE_23145;
import static net.runelite.api.ObjectID.LOG_BALANCE_23542;
import static net.runelite.api.ObjectID.LOG_BALANCE_3557;
import static net.runelite.api.ObjectID.LOW_WALL;
import static net.runelite.api.ObjectID.LOW_WALL_10865;
import static net.runelite.api.ObjectID.MARKET_STALL_14936;
import static net.runelite.api.ObjectID.MONKEYBARS;
import static net.runelite.api.ObjectID.MONKEYBARS_15417;
import static net.runelite.api.ObjectID.MONKEYBARS_42211;
import static net.runelite.api.ObjectID.MONKEY_BARS_3564;
import static net.runelite.api.ObjectID.NARROW_WALL;
import static net.runelite.api.ObjectID.OBSTACLE_NET_20211;
import static net.runelite.api.ObjectID.OBSTACLE_NET_23134;
import static net.runelite.api.ObjectID.OBSTACLE_NET_23135;
import static net.runelite.api.ObjectID.OBSTACLE_PIPE_23137;
import static net.runelite.api.ObjectID.OBSTACLE_PIPE_23138;
import static net.runelite.api.ObjectID.OBSTACLE_PIPE_23139;
import static net.runelite.api.ObjectID.PILE_OF_FISH;
import static net.runelite.api.ObjectID.PILLAR_3578;
import static net.runelite.api.ObjectID.PIPE_11657;
import static net.runelite.api.ObjectID.PLANK_10868;
import static net.runelite.api.ObjectID.PLANK_26635;
import static net.runelite.api.ObjectID.PLANK_3570;
import static net.runelite.api.ObjectID.PLANK_3571;
import static net.runelite.api.ObjectID.PLANK_3572;
import static net.runelite.api.ObjectID.PLATFORM_38455;
import static net.runelite.api.ObjectID.PLATFORM_38456;
import static net.runelite.api.ObjectID.PLATFORM_38457;
import static net.runelite.api.ObjectID.PLATFORM_38458;
import static net.runelite.api.ObjectID.PLATFORM_38459;
import static net.runelite.api.ObjectID.PLATFORM_38470;
import static net.runelite.api.ObjectID.PLATFORM_38477;
import static net.runelite.api.ObjectID.POLEVAULT;
import static net.runelite.api.ObjectID.PYLON_22664;
import static net.runelite.api.ObjectID.ROCKS_23640;
import static net.runelite.api.ObjectID.ROCKY_SHORE;
import static net.runelite.api.ObjectID.ROCKY_SURFACE;
import static net.runelite.api.ObjectID.ROCK_17958;
import static net.runelite.api.ObjectID.ROCK_17959;
import static net.runelite.api.ObjectID.ROCK_17960;
import static net.runelite.api.ObjectID.ROOF_EDGE;
import static net.runelite.api.ObjectID.ROOF_TOP_BEAMS;
import static net.runelite.api.ObjectID.ROPESWING_23131;
import static net.runelite.api.ObjectID.ROPESWING_23132;
import static net.runelite.api.ObjectID.ROPE_15487;
import static net.runelite.api.ObjectID.ROPE_BRIDGE_36233;
import static net.runelite.api.ObjectID.ROPE_BRIDGE_36235;
import static net.runelite.api.ObjectID.ROPE_SWING;
import static net.runelite.api.ObjectID.ROUGH_WALL;
import static net.runelite.api.ObjectID.ROUGH_WALL_11633;
import static net.runelite.api.ObjectID.ROUGH_WALL_14412;
import static net.runelite.api.ObjectID.ROUGH_WALL_14898;
import static net.runelite.api.ObjectID.ROUGH_WALL_14940;
import static net.runelite.api.ObjectID.ROUGH_WALL_14946;
import static net.runelite.api.ObjectID.SHELF_18086;
import static net.runelite.api.ObjectID.SHELF_18087;
import static net.runelite.api.ObjectID.SHELF_18095;
import static net.runelite.api.ObjectID.SHELF_18096;
import static net.runelite.api.ObjectID.SHELF_18105;
import static net.runelite.api.ObjectID.SHELF_18106;
import static net.runelite.api.ObjectID.SHELF_18107;
import static net.runelite.api.ObjectID.SHELF_18108;
import static net.runelite.api.ObjectID.SKULL_SLOPE;
import static net.runelite.api.ObjectID.SKULL_SLOPE_15483;
import static net.runelite.api.ObjectID.STAIRS_10857;
import static net.runelite.api.ObjectID.STAIRS_22608;
import static net.runelite.api.ObjectID.STAIRS_22609;
import static net.runelite.api.ObjectID.STAIRS_22650;
import static net.runelite.api.ObjectID.STAIRS_22651;
import static net.runelite.api.ObjectID.STAIRS_38462;
import static net.runelite.api.ObjectID.STAIRS_38463;
import static net.runelite.api.ObjectID.STAIRS_38464;
import static net.runelite.api.ObjectID.STAIRS_38465;
import static net.runelite.api.ObjectID.STAIRS_38466;
import static net.runelite.api.ObjectID.STAIRS_38467;
import static net.runelite.api.ObjectID.STAIRS_38468;
import static net.runelite.api.ObjectID.STAIRS_38469;
import static net.runelite.api.ObjectID.STAIRS_38471;
import static net.runelite.api.ObjectID.STAIRS_38472;
import static net.runelite.api.ObjectID.STAIRS_38473;
import static net.runelite.api.ObjectID.STAIRS_38474;
import static net.runelite.api.ObjectID.STAIRS_38475;
import static net.runelite.api.ObjectID.STAIRS_38476;
import static net.runelite.api.ObjectID.STAIRS_DOWN;
import static net.runelite.api.ObjectID.STEEP_ROOF;
import static net.runelite.api.ObjectID.STEPPING_STONE_11643;
import static net.runelite.api.ObjectID.STEPPING_STONE_15412;
import static net.runelite.api.ObjectID.STEPPING_STONE_21120;
import static net.runelite.api.ObjectID.STEPPING_STONE_21126;
import static net.runelite.api.ObjectID.STEPPING_STONE_21128;
import static net.runelite.api.ObjectID.STEPPING_STONE_21129;
import static net.runelite.api.ObjectID.STEPPING_STONE_21130;
import static net.runelite.api.ObjectID.STEPPING_STONE_21131;
import static net.runelite.api.ObjectID.STEPPING_STONE_21132;
import static net.runelite.api.ObjectID.STEPPING_STONE_21133;
import static net.runelite.api.ObjectID.STEPPING_STONE_23556;
import static net.runelite.api.ObjectID.STILE_7527;
import static net.runelite.api.ObjectID.TALL_TREE_14843;
import static net.runelite.api.ObjectID.TIGHTROPE;
import static net.runelite.api.ObjectID.TIGHTROPE_11406;
import static net.runelite.api.ObjectID.TIGHTROPE_14398;
import static net.runelite.api.ObjectID.TIGHTROPE_14409;
import static net.runelite.api.ObjectID.TIGHTROPE_14899;
import static net.runelite.api.ObjectID.TIGHTROPE_14905;
import static net.runelite.api.ObjectID.TIGHTROPE_14911;
import static net.runelite.api.ObjectID.TIGHTROPE_14932;
import static net.runelite.api.ObjectID.TIGHTROPE_14987;
import static net.runelite.api.ObjectID.TIGHTROPE_14992;
import static net.runelite.api.ObjectID.TIGHTROPE_36225;
import static net.runelite.api.ObjectID.TIGHTROPE_36234;
import static net.runelite.api.ObjectID.TIGHTROPE_36236;
import static net.runelite.api.ObjectID.TIGHTROPE_36237;
import static net.runelite.api.ObjectID.TIGHTROPE_42212;
import static net.runelite.api.ObjectID.TIGHTROPE_42214;
import static net.runelite.api.ObjectID.TIGHTROPE_42215;
import static net.runelite.api.ObjectID.TREE_14939;
import static net.runelite.api.ObjectID.TREE_14944;
import static net.runelite.api.ObjectID.TREE_BRANCH_23559;
import static net.runelite.api.ObjectID.TREE_BRANCH_23560;
import static net.runelite.api.ObjectID.TROPICAL_TREE_14404;
import static net.runelite.api.ObjectID.TROPICAL_TREE_15414;
import static net.runelite.api.ObjectID.TROPICAL_TREE_16062;
import static net.runelite.api.ObjectID.TUNNEL_18085;
import static net.runelite.api.ObjectID.TUNNEL_22557;
import static net.runelite.api.ObjectID.WALL_11630;
import static net.runelite.api.ObjectID.WALL_14832;
import static net.runelite.api.ObjectID.WALL_14927;
import static net.runelite.api.ObjectID.WALL_17980;
import static net.runelite.api.ObjectID.WALL_18078;
import static net.runelite.api.ObjectID.WALL_18088;
import static net.runelite.api.ObjectID.WALL_39172;
import static net.runelite.api.ObjectID.WALL_39173;
import static net.runelite.api.ObjectID.WALL_RUBBLE;
import static net.runelite.api.ObjectID.WALL_RUBBLE_18038;
import static net.runelite.api.ObjectID.WASHING_LINE_18099;
import static net.runelite.api.ObjectID.WASHING_LINE_18100;
import static net.runelite.api.ObjectID.WOODEN_BEAMS;
import static net.runelite.api.ObjectID.ZIPLINE;
import static net.runelite.api.ObjectID.ZIP_LINE;
import static net.runelite.api.ObjectID.ZIP_LINE_11645;
import static net.runelite.api.ObjectID.ZIP_LINE_11646;
import static net.runelite.api.ObjectID.ZIP_LINE_14403;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Multimap;
import java.util.List;
import java.util.Set;

class Obstacles {

  static final Set<Integer> OBSTACLE_IDS = ImmutableSet.of(
      // Gnome
      OBSTACLE_NET_23134, TREE_BRANCH_23559, TREE_BRANCH_23560, OBSTACLE_NET_23135,
      OBSTACLE_PIPE_23138,
      OBSTACLE_PIPE_23139, LOG_BALANCE_23145, BALANCING_ROPE_23557,
      // Brimhaven
      PLANK_3572, PLANK_3571, PLANK_3570, ROPE_SWING, PILLAR_3578, LOW_WALL, LOG_BALANCE,
      LOG_BALANCE_3557,
      BALANCING_LEDGE_3561, BALANCING_LEDGE, MONKEY_BARS_3564, BALANCING_ROPE, HAND_HOLDS_3583,
      // Draynor
      ROUGH_WALL, TIGHTROPE, TIGHTROPE_11406, NARROW_WALL, WALL_11630, GAP_11631, CRATE_11632,
      STILE_7527,
      // Al-Kharid
      ROUGH_WALL_11633, TIGHTROPE_14398, CABLE, ZIP_LINE_14403, TROPICAL_TREE_14404, ROOF_TOP_BEAMS,
      TIGHTROPE_14409, GAP_14399,
      // Pyramid
      STAIRS_10857, LOW_WALL_10865, LEDGE_10860, PLANK_10868, GAP_10882, LEDGE_10886, STAIRS_10857,
      GAP_10884,
      GAP_10859, GAP_10861, LOW_WALL_10865, GAP_10859, LEDGE_10888, PLANK_10868,
      CLIMBING_ROCKS_10851, DOORWAY_10855,
      // Varrock
      ROUGH_WALL_14412, CLOTHES_LINE, GAP_14414, WALL_14832, GAP_14833, GAP_14834, GAP_14835,
      LEDGE_14836, EDGE,
      // Penguin
      STEPPING_STONE_21120, STEPPING_STONE_21126, STEPPING_STONE_21128, STEPPING_STONE_21129,
      STEPPING_STONE_21130, STEPPING_STONE_21131, STEPPING_STONE_21132, STEPPING_STONE_21133,
      ICICLES, ICE, ICE_21149, ICE_21150, ICE_21151, ICE_21152, ICE_21153, ICE_21154, ICE_21155,
      ICE_21156, GATE_21172,
      // Barbarian
      ROPESWING_23131, LOG_BALANCE_23144, OBSTACLE_NET_20211, BALANCING_LEDGE_23547, LADDER_16682,
      CRUMBLING_WALL_1948,
      // Canifis
      TALL_TREE_14843, GAP_14844, GAP_14845, GAP_14848, GAP_14846, POLEVAULT, GAP_14847, GAP_14897,
      // Ape atoll
      STEPPING_STONE_15412, TROPICAL_TREE_15414, MONKEYBARS_15417, SKULL_SLOPE_15483, ROPE_15487,
      TROPICAL_TREE_16062,
      // Falador
      ROUGH_WALL_14898, TIGHTROPE_14899, HAND_HOLDS_14901, GAP_14903, GAP_14904, TIGHTROPE_14905,
      TIGHTROPE_14911, GAP_14919, LEDGE_14920, LEDGE_14921, LEDGE_14922, LEDGE_14923, LEDGE_14924,
      EDGE_14925,
      // Wilderness
      OBSTACLE_PIPE_23137, ROPESWING_23132, STEPPING_STONE_23556, LOG_BALANCE_23542, ROCKS_23640,
      // Seers
      WALL_14927, GAP_14928, TIGHTROPE_14932, GAP_14929, GAP_14930, EDGE_14931,
      // Dorgesh-Kaan
      CABLE_22569, CABLE_22572, LADDER_22564, JUTTING_WALL_22552, TUNNEL_22557, PYLON_22664,
      CONSOLE, BOILER_22635, STAIRS_22650, STAIRS_22651, STAIRS_22609, STAIRS_22608,
      // Pollniveach
      BASKET_14935, MARKET_STALL_14936, BANNER_14937, GAP_14938, TREE_14939, ROUGH_WALL_14940,
      MONKEYBARS, TREE_14944, DRYING_LINE,
      // Rellaka
      ROUGH_WALL_14946, GAP_14947, TIGHTROPE_14987, GAP_14990, GAP_14991, TIGHTROPE_14992,
      PILE_OF_FISH,
      // Ardougne
      WOODEN_BEAMS, GAP_15609, PLANK_26635, GAP_15610, GAP_15611, STEEP_ROOF, GAP_15612,
      // Meiyerditch
      NULL_12945, ROCK_17958, ROCK_17959, ROCK_17960, BOAT_17961, NULL_18122, NULL_18124,
      WALL_RUBBLE,
      WALL_RUBBLE_18038, FLOORBOARDS, FLOORBOARDS_18071, FLOORBOARDS_18072, FLOORBOARDS_18073,
      NULL_18129, NULL_18130,
      WALL_18078, NULL_18132, NULL_18133, NULL_18083, TUNNEL_18085, SHELF_18086, SHELF_18087,
      WALL_18088,
      FLOORBOARDS_18089, FLOORBOARDS_18090, DOOR_18091, FLOORBOARDS_18093, FLOORBOARDS_18094,
      SHELF_18095,
      SHELF_18096, FLOORBOARDS_18097, FLOORBOARDS_18098, WASHING_LINE_18099, WASHING_LINE_18100,
      NULL_18135, NULL_18136, SHELF_18105, SHELF_18106, SHELF_18107, SHELF_18108, FLOORBOARDS_18109,
      FLOORBOARDS_18110, FLOORBOARDS_18112, FLOORBOARDS_18111, FLOORBOARDS_18114, FLOORBOARDS_18113,
      NULL_18116, FLOORBOARDS_18117, FLOORBOARDS_18118, STAIRS_DOWN, WALL_17980, BARRICADE_18054,
      LADDER_17999,
      LADDER_18000, LADDER_18001, LADDER_18002, ROCKY_SURFACE, WALL_39172, WALL_39173,
      // Werewolf
      STEPPING_STONE_11643, HURDLE, HURDLE_11639, HURDLE_11640, PIPE_11657, SKULL_SLOPE, ZIP_LINE,
      ZIP_LINE_11645, ZIP_LINE_11646,
      // Prifddinas
      LADDER_36221, TIGHTROPE_36225, CHIMNEY_36227, ROOF_EDGE, DARK_HOLE_36229, LADDER_36231,
      LADDER_36232,
      ROPE_BRIDGE_36233, TIGHTROPE_36234, ROPE_BRIDGE_36235, TIGHTROPE_36236, TIGHTROPE_36237,
      DARK_HOLE_36238,
      // Rellekka Lighthouse
      BASALT_ROCK, BASALT_ROCK_4553, BASALT_ROCK_4554, BASALT_ROCK_4556, BASALT_ROCK_4558,
      ROCKY_SHORE,
      BASALT_ROCK_4557, BASALT_ROCK_4555, BASALT_ROCK_4552, BEACH,
      // Shayzien
      LADDER_42209, MONKEYBARS_42211, TIGHTROPE_42212,
      // Shayzien basic
      BAR_42213, TIGHTROPE_42214, TIGHTROPE_42215, GAP_42216,
      // Shayzien hard
      BEAM, EDGE_42218, EDGE_42219, BEAM_42220, ZIPLINE
  );

  static final Set<Integer> PORTAL_OBSTACLE_IDS = ImmutableSet.of(
      // Prifddinas portals
      NULL_36241, NULL_36242, NULL_36243, NULL_36244, NULL_36245, NULL_36246
  );

  static final Multimap<Integer, AgilityShortcut> SHORTCUT_OBSTACLE_IDS;

  static final Set<Integer> TRAP_OBSTACLE_IDS = ImmutableSet.of(
      // Agility pyramid
      NULL_3550, NULL_10872, NULL_10873
  );

  static final List<Integer> TRAP_OBSTACLE_REGIONS = ImmutableList.of(12105, 13356);
  static final Set<Integer> SEPULCHRE_OBSTACLE_IDS = ImmutableSet.of(
      // Stairs and Platforms (and one Gate)
      GATE_38460, PLATFORM_38455, PLATFORM_38456, PLATFORM_38457, PLATFORM_38458, PLATFORM_38459,
      PLATFORM_38470, PLATFORM_38477, STAIRS_38462, STAIRS_38463, STAIRS_38464, STAIRS_38465,
      STAIRS_38466, STAIRS_38467, STAIRS_38468, STAIRS_38469, STAIRS_38471, STAIRS_38472,
      STAIRS_38473, STAIRS_38474, STAIRS_38475, STAIRS_38476
  );
  static final Set<Integer> SEPULCHRE_SKILL_OBSTACLE_IDS = ImmutableSet.of(
      // Grapple, Portal, and Bridge skill obstacles
      // They are multilocs, thus we use the NullObjectID
      NULL_39524, NULL_39525, NULL_39526, NULL_39527, NULL_39528, NULL_39533
  );

  static {
    final ImmutableMultimap.Builder<Integer, AgilityShortcut> builder = ImmutableMultimap.builder();
    for (final AgilityShortcut item : AgilityShortcut.values()) {
      for (int obstacle : item.getObstacleIds()) {
        builder.put(obstacle, item);
      }
    }
    SHORTCUT_OBSTACLE_IDS = builder.build();
  }
}
