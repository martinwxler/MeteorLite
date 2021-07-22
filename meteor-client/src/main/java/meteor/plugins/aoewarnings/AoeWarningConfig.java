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

import static meteor.plugins.aoewarnings.AoeProjectileInfo.VORKATH_BOMB;
import static meteor.plugins.aoewarnings.AoeProjectileInfo.VORKATH_POISON_POOL;
import static meteor.plugins.aoewarnings.AoeProjectileInfo.VORKATH_SPAWN;
import static meteor.plugins.aoewarnings.AoeProjectileInfo.VORKATH_TICK_FIRE;

import java.awt.Color;
import java.awt.Font;
import java.util.EnumSet;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

public interface AoeWarningConfig {

  String notifyTitle = "Notify";
  String overlayTitle = "Overlay";
  String textTitle = "Text";
  String lizardmanaoeTitle = "Lizardman Shamans";
  String archaeologistaoeTitle = "Crazy Archaeologist";
  String icedemonTitle = "Ice Demon";
  String vasaTitle = "Vasa";
  String tektonTitle = "Tekton";
  String vorkathTitle = "Vorkath";
  String galvekTitle = "Galvek";
  String gargbossTitle = "Gargoyle Boss";
  String vetionTitle = "Vet'ion";
  String chaosfanaticTitle = "Chaos Fanatic";
  String olmTitle = "Olm";
  String corpTitle = "Corporeal Beast";
  String wintertodtTitle = "Wintertodt";
  String xarpusTitle = "Xarpus";
  String addyDragsTitle = "Addy Drags";
  String drakeTitle = "Drakes";
  String cerberusTitle = "Cerberus";
  String demonicGorillaTitle = "Demonic Gorilla";
  String verzikTitle = "Verzik";

  static AoeWarningConfig getInstance() {
    return new AoeWarningConfig() {

    };
  }

  default boolean aoeNotifyAll() {
    return false;
  }

  default Color overlayColor() {
    return new Color(0, 150, 200);
  }

  default boolean isOutlineEnabled() {
    return true;
  }

  default int delay() {
    return 300;
  }

  default boolean isFadeEnabled() {
    return true;
  }

  default boolean tickTimers() {
    return true;
  }

  default FontStyle fontStyle() {
    return FontStyle.BOLD;
  }

  default int textSize() {
    return 32;
  }

  default boolean shadows() {
    return true;
  }

  default boolean isShamansEnabled() {
    return true;
  }

  default boolean isShamansNotifyEnabled() {
    return false;
  }

  default boolean isArchaeologistEnabled() {
    return true;
  }

  default boolean isArchaeologistNotifyEnabled() {
    return false;
  }

  default boolean isIceDemonEnabled() {
    return true;
  }

  default boolean isIceDemonNotifyEnabled() {
    return false;
  }

  default boolean isVasaEnabled() {
    return true;
  }

  default boolean isVasaNotifyEnabled() {
    return false;
  }

  default boolean isTektonEnabled() {
    return true;
  }

  default boolean isTektonNotifyEnabled() {
    return false;
  }

  default EnumSet<VorkathMode> vorkathModes() {
    return EnumSet.allOf(VorkathMode.class);
  }

  default boolean isVorkathNotifyEnabled() {
    return false;
  }

  default boolean isGalvekEnabled() {
    return true;
  }

  default boolean isGalvekNotifyEnabled() {
    return false;
  }

  default boolean isGargBossEnabled() {
    return true;
  }

  default boolean isGargBossNotifyEnabled() {
    return false;
  }

  default boolean isVetionEnabled() {
    return true;
  }

  default boolean isVetionNotifyEnabled() {
    return false;
  }

  default boolean isChaosFanaticEnabled() {
    return true;
  }

  default boolean isChaosFanaticNotifyEnabled() {
    return false;
  }

  default boolean isOlmEnabled() {
    return true;
  }

  default boolean isOlmNotifyEnabled() {
    return false;
  }

  default boolean bombDisplay() {
    return true;
  }

  default boolean bombDisplayNotifyEnabled() {
    return false;
  }

  default boolean LightningTrail() {
    return true;
  }

  default boolean LightningTrailNotifyEnabled() {
    return false;
  }

  default boolean isCorpEnabled() {
    return true;
  }

  default boolean isCorpNotifyEnabled() {
    return false;
  }

  default boolean isWintertodtEnabled() {
    return true;
  }

  default boolean isWintertodtNotifyEnabled() {
    return false;
  }

  default boolean isXarpusEnabled() {
    return true;
  }

  default boolean isXarpusNotifyEnabled() {
    return false;
  }

  default boolean addyDrags() {
    return true;
  }

  default boolean addyDragsNotifyEnabled() {
    return false;
  }

  default boolean isDrakeEnabled() {
    return true;
  }

  default boolean isDrakeNotifyEnabled() {
    return false;
  }

  default boolean isCerbFireEnabled() {
    return true;
  }

  default boolean isCerbFireNotifyEnabled() {
    return false;
  }

  default boolean isDemonicGorillaEnabled() {
    return true;
  }


  default boolean isDemonicGorillaNotifyEnabled() {
    return false;
  }

  default boolean isVerzikEnabled() {
    return true;
  }

  default boolean isVerzikNotifyEnabled() {
    return false;
  }

  @Getter(AccessLevel.PACKAGE)
  @AllArgsConstructor
  enum FontStyle {
    BOLD("Bold", Font.BOLD),
    ITALIC("Italic", Font.ITALIC),
    PLAIN("Plain", Font.PLAIN);

    private String name;
    private int font;

    @Override
    public String toString() {
      return getName();
    }
  }

  @AllArgsConstructor
  enum VorkathMode {
    BOMBS(VORKATH_BOMB),
    POOLS(VORKATH_POISON_POOL),
    SPAWN(VORKATH_SPAWN),
    FIRES(VORKATH_TICK_FIRE); // full auto ratatat

    private final AoeProjectileInfo info;

    static VorkathMode of(AoeProjectileInfo info) {
      for (VorkathMode m : values()) {
        if (m.info == info) {
          return m;
        }
      }
      throw new EnumConstantNotPresentException(AoeProjectileInfo.class, info.toString());
    }
  }
}
