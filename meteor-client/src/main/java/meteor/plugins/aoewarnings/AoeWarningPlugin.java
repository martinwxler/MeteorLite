/*
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
 * All rights reserved.
 *
 * Modified by farhan1666
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
package meteor.plugins.aoewarnings;

import com.google.inject.Provides;
import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.inject.Inject;
import lombok.AccessLevel;
import lombok.Getter;
import meteor.config.ConfigManager;
import meteor.eventbus.Subscribe;
import meteor.plugins.Plugin;
import meteor.plugins.PluginDescriptor;
import meteor.plugins.bank.BankConfig;
import meteor.ui.overlay.OverlayManager;
import net.runelite.api.Client;
import net.runelite.api.GameObject;
import net.runelite.api.GameState;
import net.runelite.api.GraphicID;
import net.runelite.api.NullObjectID;
import net.runelite.api.ObjectID;
import net.runelite.api.Projectile;
import net.runelite.api.coords.WorldPoint;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameObjectSpawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.api.events.GameTick;
import net.runelite.api.events.ProjectileMoved;
import net.runelite.api.events.ProjectileSpawned;
import org.apache.commons.lang3.ArrayUtils;

@PluginDescriptor(
    name = "AOE Warnings",
    description = "Shows helpful warnings for area of effect attacks"
)
public class AoeWarningPlugin extends Plugin {

  private static final int VERZIK_REGION = 12611;
  private static final int GROTESQUE_GUARDIANS_REGION = 6727;
  @Getter(AccessLevel.PACKAGE)
  private final Set<CrystalBomb> bombs = new HashSet<>();
  @Getter(AccessLevel.PACKAGE)
  private final Set<ProjectileContainer> projectiles = new HashSet<>();
  @Inject
  public AoeWarningConfig config;
  @Inject
  private AoeWarningOverlay coreOverlay;
  @Inject
  private BombOverlay bombOverlay;
  @Inject
  private OverlayManager overlayManager;
  @Inject
  private ConfigManager configManager;
  @Getter(AccessLevel.PACKAGE)
  private final List<WorldPoint> lightningTrail = new ArrayList<>();
  @Getter(AccessLevel.PACKAGE)
  private final List<GameObject> acidTrail = new ArrayList<>();
  @Getter(AccessLevel.PACKAGE)
  private final List<GameObject> crystalSpike = new ArrayList<>();
  @Getter(AccessLevel.PACKAGE)
  private final List<GameObject> wintertodtSnowFall = new ArrayList<>();

  public AoeWarningPlugin() {
  }

  @Provides
  AoeWarningConfig getProvidedConfig() {
    return configManager.getConfig(AoeWarningConfig.class);
  }

  @Override
  public void startup() {
    overlayManager.add(coreOverlay);
    overlayManager.add(bombOverlay);
    reset();
  }

  @Override
  public void shutdown() {
    overlayManager.remove(coreOverlay);
    overlayManager.remove(bombOverlay);
    reset();
  }

  @Subscribe
  private void onProjectileSpawned(ProjectileSpawned event) {
    final Projectile projectile = event.getProjectile();

    if (AoeProjectileInfo.getById(projectile.getId()) == null) {
      return;
    }

    final int id = projectile.getId();
    final int lifetime = config.delay() + (projectile.getRemainingCycles() * 20);
    int ticksRemaining = projectile.getRemainingCycles() / 30;
    if (!isTickTimersEnabledForProjectileID(id)) {
      ticksRemaining = 0;
    }
    final int tickCycle = client.getTickCount() + ticksRemaining;
    if (isConfigEnabledForProjectileId(id, false)) {
      projectiles.add(new ProjectileContainer(projectile, Instant.now(), lifetime, tickCycle));

    }
  }

  @Subscribe
  private void onProjectileMoved(ProjectileMoved event) {
    if (projectiles.isEmpty()) {
      return;
    }

    final Projectile projectile = event.getProjectile();

    projectiles.forEach(proj ->
    {
      if (proj.getProjectile() == projectile) {
        proj.setTargetPoint(event.getPosition());
      }
    });
  }

  @Subscribe
  private void onGameObjectSpawned(GameObjectSpawned event) {
    final GameObject gameObject = event.getGameObject();

    switch (gameObject.getId()) {
      case ObjectID.CRYSTAL_BOMB:
        bombs.add(new CrystalBomb(gameObject, client.getTickCount()));

        break;
      case ObjectID.ACID_POOL:
        acidTrail.add(gameObject);
        break;
      case ObjectID.SMALL_CRYSTALS:
        crystalSpike.add(gameObject);
        break;
      case NullObjectID.NULL_26690:
        if (config.isWintertodtEnabled()) {
          wintertodtSnowFall.add(gameObject);
        }
        break;
    }
  }

  @Subscribe
  private void onGameObjectDespawned(GameObjectDespawned event) {
    final GameObject gameObject = event.getGameObject();

    switch (gameObject.getId()) {
      case ObjectID.CRYSTAL_BOMB:
        bombs.removeIf(o -> o.getGameObject() == gameObject);
        break;
      case ObjectID.ACID_POOL:
        acidTrail.remove(gameObject);
        break;
      case ObjectID.SMALL_CRYSTALS:
        crystalSpike.remove(gameObject);
        break;
      case NullObjectID.NULL_26690:
        wintertodtSnowFall.remove(gameObject);
        break;
    }
  }

  @Subscribe
  private void onGameStateChanged(GameStateChanged event) {
    if (event.getGameState() == GameState.LOGGED_IN) {
      return;
    }
    reset();
  }

  @Subscribe
  private void onGameTick(GameTick event) {
    lightningTrail.clear();

    if (config.LightningTrail()) {
      client.getGraphicsObjects().forEach(o ->
      {
        if (o.getId() == GraphicID.OLM_LIGHTNING) {
          lightningTrail.add(WorldPoint.fromLocal(client, o.getLocation()));
        }
      });
    }

    bombs.forEach(CrystalBomb::bombClockUpdate);
  }

  private boolean isTickTimersEnabledForProjectileID(int projectileId) {
    AoeProjectileInfo projectileInfo = AoeProjectileInfo.getById(projectileId);

    if (projectileInfo == null) {
      return false;
    }

    switch (projectileInfo) {
      case VASA_RANGED_AOE:
      case VORKATH_POISON_POOL:
      case VORKATH_SPAWN:
      case VORKATH_TICK_FIRE:
      case OLM_BURNING:
      case OLM_FALLING_CRYSTAL_TRAIL:
      case OLM_ACID_TRAIL:
      case OLM_FIRE_LINE:
        return false;
    }

    return true;
  }

  private boolean isConfigEnabledForProjectileId(int projectileId, boolean notify) {
    AoeProjectileInfo projectileInfo = AoeProjectileInfo.getById(projectileId);
    if (projectileInfo == null) {
      return false;
    }

    if (notify && config.aoeNotifyAll()) {
      return true;
    }

    switch (projectileInfo) {
      case LIZARDMAN_SHAMAN_AOE:
        return notify ? config.isShamansNotifyEnabled() : config.isShamansEnabled();
      case CRAZY_ARCHAEOLOGIST_AOE:
        return notify ? config.isArchaeologistNotifyEnabled() : config.isArchaeologistEnabled();
      case ICE_DEMON_RANGED_AOE:
      case ICE_DEMON_ICE_BARRAGE_AOE:
        return notify ? config.isIceDemonNotifyEnabled() : config.isIceDemonEnabled();
      case VASA_AWAKEN_AOE:
      case VASA_RANGED_AOE:
        return notify ? config.isVasaNotifyEnabled() : config.isVasaEnabled();
      case TEKTON_METEOR_AOE:
        return notify ? config.isTektonNotifyEnabled() : config.isTektonEnabled();
      case VORKATH_BOMB:
      case VORKATH_POISON_POOL:
      case VORKATH_SPAWN:
      case VORKATH_TICK_FIRE:
        return notify ? config.isVorkathNotifyEnabled()
            : config.vorkathModes().contains(AoeWarningConfig.VorkathMode.of(projectileInfo));
      case VETION_LIGHTNING:
        return notify ? config.isVetionNotifyEnabled() : config.isVetionEnabled();
      case CHAOS_FANATIC:
        return notify ? config.isChaosFanaticNotifyEnabled() : config.isChaosFanaticEnabled();
      case GALVEK_BOMB:
      case GALVEK_MINE:
        return notify ? config.isGalvekNotifyEnabled() : config.isGalvekEnabled();
      case DAWN_FREEZE:
      case DUSK_CEILING:
        if (regionCheck(GROTESQUE_GUARDIANS_REGION)) {
          return notify ? config.isGargBossNotifyEnabled() : config.isGargBossEnabled();
        }
      case VERZIK_P1_ROCKS:
        if (regionCheck(VERZIK_REGION)) {
          return notify ? config.isVerzikNotifyEnabled() : config.isVerzikEnabled();
        }
      case OLM_FALLING_CRYSTAL:
      case OLM_BURNING:
      case OLM_FALLING_CRYSTAL_TRAIL:
      case OLM_ACID_TRAIL:
      case OLM_FIRE_LINE:
        return notify ? config.isOlmNotifyEnabled() : config.isOlmEnabled();
      case CORPOREAL_BEAST:
      case CORPOREAL_BEAST_DARK_CORE:
        return notify ? config.isCorpNotifyEnabled() : config.isCorpEnabled();
      case XARPUS_POISON_AOE:
        return notify ? config.isXarpusNotifyEnabled() : config.isXarpusEnabled();
      case ADDY_DRAG_POISON:
        return notify ? config.addyDragsNotifyEnabled() : config.addyDrags();
      case DRAKE_BREATH:
        return notify ? config.isDrakeNotifyEnabled() : config.isDrakeEnabled();
      case CERB_FIRE:
        return notify ? config.isCerbFireNotifyEnabled() : config.isCerbFireEnabled();
      case DEMONIC_GORILLA_BOULDER:
        return notify ? config.isDemonicGorillaNotifyEnabled() : config.isDemonicGorillaEnabled();
      case VERZIK_PURPLE_SPAWN:
        return notify ? config.isVerzikNotifyEnabled() : config.isVerzikEnabled();
    }

    return false;
  }

  private void reset() {
    lightningTrail.clear();
    acidTrail.clear();
    crystalSpike.clear();
    wintertodtSnowFall.clear();
    bombs.clear();
    projectiles.clear();
  }

  private boolean regionCheck(int region) {
    return ArrayUtils.contains(client.getMapRegions(), region);
  }
}