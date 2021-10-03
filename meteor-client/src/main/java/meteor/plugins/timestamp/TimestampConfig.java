/*
 * Copyright (c) 2019, Tomas Slusny <slusnucky@gmail.com>
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
package meteor.plugins.timestamp;

import meteor.config.Config;
import meteor.config.ConfigGroup;
import meteor.config.ConfigItem;

import java.awt.Color;

@ConfigGroup("timestamp")
public interface TimestampConfig extends Config
{
	@ConfigItem(
		keyName = "opaqueTimestamp",
		name = "Timestamps (opaque)",
		position = 1,
		description = "Colour of Timestamps from the Timestamps plugin (opaque)"
	)
	default Color opaqueTimestamp() {return Color.BLACK;}

	@ConfigItem(
		keyName = "transparentTimestamp",
		name = "Timestamps (transparent)",
		position = 2,
		description = "Colour of Timestamps from the Timestamps plugin (transparent)"
	)
	default Color transparentTimestamp() {return Color.BLACK;}

	@ConfigItem(
		keyName = "format",
		name = "Timestamp Format",
		position = 3,
		description = "Customize your timestamp format by using the following characters" +
			"'yyyy' : year" +
			"'MM' : month" +
			"'dd' : day" +
			"'HH' : hour in 24 hour format" +
			"'hh' : hour in 12 hour format" +
			"'mm' : minute" +
			"'ss' : second" +
			"'a'  : AM/PM"
	)
	default String timestampFormat()
	{
		return "[HH:mm]";
	}
}
