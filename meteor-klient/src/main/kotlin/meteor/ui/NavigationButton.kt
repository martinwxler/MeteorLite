/*
 * Copyright (c) 2017-2018, Adam <Adam@sigterm.info>
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.con>
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
package meteor.ui

import lombok.Builder
import lombok.Data
import lombok.EqualsAndHashCode
import java.awt.image.BufferedImage
import java.lang.Runnable
import javax.swing.ImageIcon
import javax.swing.JButton

/**
 * UI navigation button.
 */
@Data
@EqualsAndHashCode(of = ["tooltip"])
class NavigationButton {
    /**
     * Icon of button.
     */
    var icon: BufferedImage? = null

    /**
     * If the button is tab or not
     */
    val tab = true

    /**
     * Tooltip to show when hovered.
     */
    var tooltip = ""

    /**
     * Button selection state
     */
    private val selected = false

    /**
     * On click action of the button.
     */
    private val onClick: Runnable? = null

    /**
     * On select action of the button.
     */
    private val onSelect: Runnable? = null

    /**
     * Plugin panel, used when expanding and contracting sidebar.
     */
    //private val panel: PluginPanel? = null

    /**
     * The order in which the button should be displayed in the side bar. (from lower to higher)
     */
    var priority = 0

    /**
     * Map of key-value pairs for setting the popup menu
     */
    private val popup: Map<String, Runnable>? = null

    fun getButton(): JButton {
        val button = JButton("")
        button.icon = ImageIcon(icon)
        return button
    }
}