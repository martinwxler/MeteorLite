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

import static meteor.plugins.aoewarnings.AoeProjectileInfo.*;

public interface AoeWarningConfig
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

	String notifyTitle = "Notify";

	default boolean aoeNotifyAll()
	{
		return false;
	}

	String overlayTitle = "Overlay";

	default Color overlayColor()
	{
		return new Color(0, 150, 200);
	}
	default boolean isOutlineEnabled()
	{
		return true;
	}
	default int delay()
	{
		return 300;
	}
	default boolean isFadeEnabled()
	{
		return true;
	}
	default boolean tickTimers()
	{
		return true;
	}
	String textTitle = "Text";
	default FontStyle fontStyle()
	{
		return FontStyle.BOLD;
	}
	default int textSize()
	{
		return 32;
	}
	default boolean shadows()
	{
		return true;
	}
	String lizardmanaoeTitle = "Lizardman Shamans";

	default boolean isShamansEnabled()
	{
		return true;
	}

	default boolean isShamansNotifyEnabled()
	{
		return false;
	}

	String archaeologistaoeTitle = "Crazy Archaeologist";

	default boolean isArchaeologistEnabled()
	{
		return true;
	}

	default boolean isArchaeologistNotifyEnabled()
	{
		return false;
	}

	String icedemonTitle = "Ice Demon";

	default boolean isIceDemonEnabled()
	{
		return true;
	}

	default boolean isIceDemonNotifyEnabled()
	{
		return false;
	}

	String vasaTitle = "Vasa";

	default boolean isVasaEnabled()
	{
		return true;
	}

	default boolean isVasaNotifyEnabled()
	{
		return false;
	}

	String tektonTitle = "Tekton";

	default boolean isTektonEnabled()
	{
		return true;
	}

	default boolean isTektonNotifyEnabled()
	{
		return false;
	}

	String vorkathTitle = "Vorkath";

	default EnumSet<VorkathMode> vorkathModes()
	{
		return EnumSet.allOf(VorkathMode.class);
	}

	default boolean isVorkathNotifyEnabled()
	{
		return false;
	}

	String galvekTitle = "Galvek";

	default boolean isGalvekEnabled()
	{
		return true;
	}

	default boolean isGalvekNotifyEnabled()
	{
		return false;
	}

	String gargbossTitle = "Gargoyle Boss";

	default boolean isGargBossEnabled()
	{
		return true;
	}

	default boolean isGargBossNotifyEnabled()
	{
		return false;
	}

	String vetionTitle = "Vet'ion";

	default boolean isVetionEnabled()
	{
		return true;
	}

	default boolean isVetionNotifyEnabled()
	{
		return false;
	}

	String chaosfanaticTitle = "Chaos Fanatic";

	default boolean isChaosFanaticEnabled()
	{
		return true;
	}

	default boolean isChaosFanaticNotifyEnabled()
	{
		return false;
	}

	String olmTitle = "Olm";

	default boolean isOlmEnabled()
	{
		return true;
	}

	default boolean isOlmNotifyEnabled()
	{
		return false;
	}

	default boolean bombDisplay()
	{
		return true;
	}

	default boolean bombDisplayNotifyEnabled()
	{
		return false;
	}

	default boolean LightningTrail()
	{
		return true;
	}

	default boolean LightningTrailNotifyEnabled()
	{
		return false;
	}

	String corpTitle = "Corporeal Beast";

	default boolean isCorpEnabled()
	{
		return true;
	}

	default boolean isCorpNotifyEnabled()
	{
		return false;
	}

	String wintertodtTitle = "Wintertodt";

	default boolean isWintertodtEnabled()
	{
		return true;
	}

	default boolean isWintertodtNotifyEnabled()
	{
		return false;
	}

	String xarpusTitle = "Xarpus";

	default boolean isXarpusEnabled()
	{
		return true;
	}

	default boolean isXarpusNotifyEnabled()
	{
		return false;
	}

	String addyDragsTitle = "Addy Drags";

	default boolean addyDrags()
	{
		return true;
	}

	default boolean addyDragsNotifyEnabled()
	{
		return false;
	}

	String drakeTitle = "Drakes";

	default boolean isDrakeEnabled()
	{
		return true;
	}

	default boolean isDrakeNotifyEnabled()
	{
		return false;
	}

	String cerberusTitle = "Cerberus";

	default boolean isCerbFireEnabled()
	{
		return true;
	}

	default boolean isCerbFireNotifyEnabled()
	{
		return false;
	}

	String demonicGorillaTitle = "Demonic Gorilla";

	default boolean isDemonicGorillaEnabled()
	{
		return true;
	}


	default boolean isDemonicGorillaNotifyEnabled()
	{
		return false;
	}

	String verzikTitle = "Verzik";


	default boolean isVerzikEnabled()
	{
		return true;
	}
	default boolean isVerzikNotifyEnabled()
	{
		return false;
	}

	static AoeWarningConfig getInstance()
	{
		return new AoeWarningConfig() {

		};
	}
}
