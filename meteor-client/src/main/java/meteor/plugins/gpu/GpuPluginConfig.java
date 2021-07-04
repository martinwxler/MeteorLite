/*
 * Copyright (c) 2018, Adam <Adam@sigterm.info>
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
package meteor.plugins.gpu;

import meteor.plugins.gpu.config.AntiAliasingMode;
import meteor.plugins.gpu.config.ColorBlindMode;
import meteor.plugins.gpu.config.UIScalingMode;

import static meteor.plugins.gpu.GpuPlugin.MAX_DISTANCE;
import static meteor.plugins.gpu.GpuPlugin.MAX_FOG_DEPTH;

public class GpuPluginConfig
{
	public static int drawDistance = MAX_DISTANCE;
	public static int drawDistanceMax = MAX_DISTANCE;
	public static boolean smoothBanding = false;

	public static AntiAliasingMode antiAliasingMode = AntiAliasingMode.DISABLED;

	public static UIScalingMode uiScalingMode = UIScalingMode.LINEAR;

	public static int fogDepth = 0;
	public static int fogDepthMax = MAX_FOG_DEPTH;

	public static boolean useComputeShaders = true;

	public static int anisotropicFilteringLevelMin = 0;
	public static int anisotropicFilteringLevel = anisotropicFilteringLevelMin;
	public static int anisotropicFilteringLevelMax = 16;

	public static ColorBlindMode colorBlindMode = ColorBlindMode.NONE;

	public static boolean brightTextures = false;
}
