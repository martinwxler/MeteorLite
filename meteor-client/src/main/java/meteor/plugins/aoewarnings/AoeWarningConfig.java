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

import java.awt.Color;
import java.awt.Font;
import java.util.EnumSet;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;
import meteor.config.ConfigSection;
import meteor.config.Range;
import static meteor.plugins.aoewarnings.AoeProjectileInfo.*;

@ConfigGroup("aoe")
public interface AoeWarningConfig extends Config
{
  @Getter(AccessLevel.PACKAGE)
  @AllArgsConstructor
  enum FontStyle
  {
    BOLD("Bold", Font.BOLD),
    ITALIC("Italic", Font.ITALIC),
    PLAIN("Plain", Font.PLAIN);

    private String name;
    private int font;

    @Override
    public String toString()
    {
      return getName();
    }
  }

  @AllArgsConstructor
  enum VorkathMode
  {
    BOMBS(VORKATH_BOMB),
    POOLS(VORKATH_POISON_POOL),
    SPAWN(VORKATH_SPAWN),
    FIRES(VORKATH_TICK_FIRE); // full auto ratatat

    private final AoeProjectileInfo info;
    static VorkathMode of(AoeProjectileInfo info)
    {
      for (VorkathMode m : values())
      {
        if (m.info == info)
        {
          return m;
        }
      }
      throw new EnumConstantNotPresentException(AoeProjectileInfo.class, info.toString());
    }
  }

  @ConfigSection(
      keyName = "overlayTitle",
      name = "Overlay",
      description = "",
      position = 1
  )
  String overlayTitle = "Overlay";

  @ConfigItem(
      position = 2,
      keyName = "overlayColor",
      name = "Overlay Color",
      description = "Configures the color of the AoE Projectile Warnings overlay",
      section = overlayTitle
  )
  default Color overlayColor()
  {
    return Color.CYAN;
  }

  @ConfigItem(
      keyName = "outline",
      name = "Display Outline",
      description = "Configures whether or not AoE Projectile Warnings have an outline",
      section = overlayTitle,
      position = 3
  )
  default boolean isOutlineEnabled()
  {
    return true;
  }


  @ConfigItem(
      keyName = "delay",
      name = "Fade Delay",
      description = "Configures the amount of time in milliseconds that the warning lingers for after the projectile has touched the ground",
      section = overlayTitle,
      position = 4
  )
  @Range(
      min = 1,
      max = 900
  )
  default int delay()
  {
    return 300;
  }

  @ConfigItem(
      keyName = "fade",
      name = "Fade Warnings",
      description = "Configures whether or not AoE Projectile Warnings fade over time",
      section = overlayTitle,
      position = 5
  )
  default boolean isFadeEnabled()
  {
    return true;
  }

  @ConfigItem(
      keyName = "tickTimers",
      name = "Tick Timers",
      description = "Configures whether or not AoE Projectile Warnings has tick timers overlaid as well.",
      section = overlayTitle,
      position = 6
  )
  default boolean tickTimers()
  {
    return true;
  }

  @ConfigSection(
      keyName = "textTitle",
      position = 7,
      name = "Text",
      description = "",
      hidden = true,
      unhide = "tickTimers"
  )
  String textTitle = "Text";

  @ConfigItem(
      position = 8,
      keyName = "fontStyle",
      name = "Font Style",
      description = "Bold/Italics/Plain",
      section = textTitle,
      hidden = true,
      unhide = "tickTimers"
  )
  default FontStyle fontStyle()
  {
    return FontStyle.BOLD;
  }

  @Range(
      min = 10,
      max = 40
  )
  @ConfigItem(
      position = 9,
      keyName = "textSize",
      name = "Text Size",
      description = "Text Size for Timers.",
      section = textTitle,
      hidden = true,
      unhide = "tickTimers"
  )
  default int textSize()
  {
    return 32;
  }

  @ConfigItem(
      position = 10,
      keyName = "shadows",
      name = "Shadows",
      description = "Adds Shadows to text.",
      section = textTitle,
      hidden = true,
      unhide = "tickTimers"
  )
  default boolean shadows()
  {
    return true;
  }
  @ConfigSection(
      keyName = "lizardmanaoeTitle",
      name = "Lizardman Shamans",
      description = "",
      position = 12
  )
  String lizardmanaoeTitle = "Lizardman Shamans";

  @ConfigItem(
      keyName = "lizardmanaoe",
      name = "Lizardman Shamans",
      description = "Configures whether or not AoE Projectile Warnings for Lizardman Shamans is displayed",
      section = lizardmanaoeTitle,
      position = 13
  )
  default boolean isShamansEnabled()
  {
    return true;
  }

  @ConfigSection(
      keyName = "archaeologistaoeTitle",
      name = "Crazy Archaeologist",
      description = "",
      position = 15
  )
  String archaeologistaoeTitle = "Crazy Archaeologist";

  @ConfigItem(
      keyName = "archaeologistaoe",
      name = "Crazy Archaeologist",
      description = "Configures whether or not AoE Projectile Warnings for Archaeologist is displayed",
      section = archaeologistaoeTitle,
      position = 16
  )
  default boolean isArchaeologistEnabled()
  {
    return true;
  }

  @ConfigSection(
      keyName = "icedemonTitle",
      name = "Ice Demon",
      description = "",
      position = 18
  )
  String icedemonTitle = "Ice Demon";

  @ConfigItem(
      keyName = "icedemon",
      name = "Ice Demon",
      description = "Configures whether or not AoE Projectile Warnings for Ice Demon is displayed",
      section = icedemonTitle,
      position = 19
  )
  default boolean isIceDemonEnabled()
  {
    return true;
  }


  @ConfigSection(
      keyName = "vasaTitle",
      name = "Vasa",
      description = "",
      position = 21
  )
  String vasaTitle = "Vasa";

  @ConfigItem(
      keyName = "vasa",
      name = "Vasa",
      description = "Configures whether or not AoE Projectile Warnings for Vasa is displayed",
      section = vasaTitle,
      position = 22
  )
  default boolean isVasaEnabled()
  {
    return true;
  }


  @ConfigSection(
      keyName = "tektonTitle",
      name = "Tekton",
      description = "",
      position = 24
  )
  String tektonTitle = "Tekton";

  @ConfigItem(
      keyName = "tekton",
      name = "Tekton",
      description = "Configures whether or not AoE Projectile Warnings for Tekton is displayed",
      section = tektonTitle,
      position = 25
  )
  default boolean isTektonEnabled()
  {
    return true;
  }

  @ConfigSection(
      keyName = "vorkathTitle",
      name = "Vorkath",
      description = "",
      position = 27
  )
  String vorkathTitle = "Vorkath";

  @ConfigItem(
      keyName = "vorkath",
      name = "Vorkath",
      description = "Configure what AoE projectiles you should be warned for at Vorkath",
      section = vorkathTitle,
      position = 28,
      enumClass = VorkathMode.class
  )
  default EnumSet<VorkathMode> vorkathModes()
  {
    return EnumSet.allOf(VorkathMode.class);
  }

  @ConfigSection(
      keyName = "galvekTitle",
      name = "Galvek",
      description = "",
      position = 30
  )
  String galvekTitle = "Galvek";

  @ConfigItem(
      keyName = "galvek",
      name = "Galvek",
      description = "Configures whether or not AoE Projectile Warnings for Galvek are displayed",
      section = galvekTitle,
      position = 31
  )
  default boolean isGalvekEnabled()
  {
    return true;
  }


  @ConfigSection(
      keyName = "gargbossTitle",
      name = "Gargoyle Boss",
      description = "",
      position = 33
  )
  String gargbossTitle = "Gargoyle Boss";

  @ConfigItem(
      keyName = "gargboss",
      name = "Gargoyle Boss",
      description = "Configs whether or not AoE Projectile Warnings for Dawn/Dusk are displayed",
      section = gargbossTitle,
      position = 34
  )
  default boolean isGargBossEnabled()
  {
    return true;
  }

  @ConfigSection(
      keyName = "vetionTitle",
      name = "Vet'ion",
      description = "",
      position = 36
  )
  String vetionTitle = "Vet'ion";

  @ConfigItem(
      keyName = "vetion",
      name = "Vet'ion",
      description = "Configures whether or not AoE Projectile Warnings for Vet'ion are displayed",
      section = vetionTitle,
      position = 37
  )
  default boolean isVetionEnabled()
  {
    return true;
  }

  @ConfigSection(
      keyName = "chaosfanaticTitle",
      name = "Chaos Fanatic",
      description = "",
      position = 39
  )
  String chaosfanaticTitle = "Chaos Fanatic";

  @ConfigItem(
      keyName = "chaosfanatic",
      name = "Chaos Fanatic",
      description = "Configures whether or not AoE Projectile Warnings for Chaos Fanatic are displayed",
      section = chaosfanaticTitle,
      position = 40
  )
  default boolean isChaosFanaticEnabled()
  {
    return true;
  }

  @ConfigSection(
      keyName = "olmTitle",
      name = "Olm",
      description = "",
      position = 42
  )
  String olmTitle = "Olm";

  @ConfigItem(
      keyName = "olm",
      name = "Olm",
      description = "Configures whether or not AoE Projectile Warnings for The Great Olm are displayed",
      section = olmTitle,
      position = 43
  )
  default boolean isOlmEnabled()
  {
    return true;
  }

  @ConfigItem(
      keyName = "bombDisplay",
      name = "Olm Bombs",
      description = "Display a timer and colour-coded AoE for Olm's crystal-phase bombs.",
      section = olmTitle,
      position = 46
  )
  default boolean bombDisplay()
  {
    return true;
  }

  @ConfigItem(
      keyName = "lightning",
      name = "Olm Lightning Trails",
      description = "Show Lightning Trails",
      section = olmTitle,
      position = 49
  )
  default boolean LightningTrail()
  {
    return true;
  }

  @ConfigSection(
      keyName = "corpTitle",
      name = "Corporeal Beast",
      description = "",
      position = 51
  )
  String corpTitle = "Corporeal Beast";

  @ConfigItem(
      keyName = "corp",
      name = "Corporeal Beast",
      description = "Configures whether or not AoE Projectile Warnings for the Corporeal Beast are displayed",
      section = corpTitle,
      position = 52
  )
  default boolean isCorpEnabled()
  {
    return true;
  }

  @ConfigSection(
      keyName = "wintertodtTitle",
      name = "Wintertodt",
      description = "",
      position = 54
  )
  String wintertodtTitle = "Wintertodt";

  @ConfigItem(
      keyName = "wintertodt",
      name = "Wintertodt Snow Fall",
      description = "Configures whether or not AOE Projectile Warnings for the Wintertodt snow fall are displayed",
      section = wintertodtTitle,
      position = 55
  )
  default boolean isWintertodtEnabled()
  {
    return true;
  }

  @ConfigSection(
      keyName = "xarpusTitle",
      name = "Xarpus",
      description = "",
      position = 57
  )
  String xarpusTitle = "Xarpus";

  @ConfigItem(
      keyName = "isXarpusEnabled",
      name = "Xarpus",
      description = "Configures whether or not AOE Projectile Warnings for Xarpus are displayed",
      section = xarpusTitle,
      position = 58
  )
  default boolean isXarpusEnabled()
  {
    return true;
  }

  @ConfigSection(
      keyName = "addyDragsTitle",
      name = "Addy Drags",
      description = "",
      position = 60
  )
  String addyDragsTitle = "Addy Drags";

  @ConfigItem(
      keyName = "addyDrags",
      name = "Addy Drags",
      description = "Show Bad Areas",
      section = addyDragsTitle,
      position = 61
  )
  default boolean addyDrags()
  {
    return true;
  }

  @ConfigSection(
      keyName = "drakeTitle",
      name = "Drakes",
      description = "",
      position = 63
  )
  String drakeTitle = "Drakes";

  @ConfigItem(
      keyName = "drake",
      name = "Drakes Breath",
      description = "Configures if Drakes Breath tile markers are displayed",
      section = drakeTitle,
      position = 64
  )
  default boolean isDrakeEnabled()
  {
    return true;
  }

  @ConfigSection(
      keyName = "cerberusTitle",
      name = "Cerberus",
      description = "",
      position = 66
  )
  String cerberusTitle = "Cerberus";

  @ConfigItem(
      keyName = "cerbFire",
      name = "Cerberus Fire",
      description = "Configures if Cerberus fire tile markers are displayed",
      section = cerberusTitle,
      position = 67
  )
  default boolean isCerbFireEnabled()
  {
    return true;
  }

  @ConfigSection(
      keyName = "demonicGorillaTitle",
      name = "Demonic Gorilla",
      description = "",
      position = 69
  )
  String demonicGorillaTitle = "Demonic Gorilla";

  @ConfigItem(
      keyName = "demonicGorilla",
      name = "Demonic Gorilla",
      description = "Configures if Demonic Gorilla boulder tile markers are displayed",
      section = demonicGorillaTitle,
      position = 70
  )
  default boolean isDemonicGorillaEnabled()
  {
    return true;
  }

  @ConfigSection(
      keyName = "verzikTitle",
      name = "Verzik",
      description = "",
      position = 72
  )
  String verzikTitle = "Verzik";

  @ConfigItem(
      keyName = "verzik",
      name = "Verzik",
      description = "Configures if Verzik purple Nylo/falling rock AOE is shown",
      section = verzikTitle,
      position = 73
  )
  default boolean isVerzikEnabled()
  {
    return true;
  }
}