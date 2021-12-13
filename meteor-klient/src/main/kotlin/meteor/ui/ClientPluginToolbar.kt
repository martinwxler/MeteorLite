/*
 * Copyright (c) 2017-2018, Adam <Adam@sigterm.info>
 * Copyright (c) 2018, Tomas Slusny <slusnucky@gmail.com>
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

import Main.toolbar
import com.google.common.collect.ComparisonChain
import meteor.eventbus.EventBus
import meteor.events.NavigationButtonAdded
import meteor.events.NavigationButtonRemoved
import javax.swing.JToolBar
import meteor.ui.NavigationButton
import java.util.TreeMap
import meteor.ui.ClientPluginToolbar
import java.awt.Component
import java.awt.Dimension
import javax.swing.Box
import javax.swing.JButton

/**
 * Client plugin toolbar.
 */
object ClientPluginToolbar : JToolBar(HORIZONTAL) {
    private val buttons: MutableSet<NavigationButton> = HashSet()
    private const val TOOLBAR_WIDTH = 36
    private const val TOOLBAR_HEIGHT = 503

    /**
     * Add navigation.
     *
     * @param button the button
     */
    fun addNavigation(button: NavigationButton) {
        if (buttons.contains(button)) {
            return
        }
        if (buttons.add(button)) {
            addComponent(button, button.getButton())
            EventBus.post(NavigationButtonAdded(button))
        }
    }

    /**
     * Remove navigation.
     *
     * @param button the button
     */
    fun removeNavigation(button: NavigationButton) {
        if (buttons.remove(button)) {
            EventBus.post(NavigationButtonRemoved(button))
        }
    }

    private val componentMap: MutableMap<NavigationButton, Component?> = TreeMap { a: NavigationButton, b: NavigationButton ->
        ComparisonChain
                .start()
                .compareTrueFirst(a.tab, b.tab)
                .compare(a.priority, b.priority)
                .compare(a.tooltip, b.tooltip)
                .result()
    }

    fun addComponent(button: NavigationButton, c: Component?) {
        if (componentMap.put(button, c) == null) {
            update()
        }
    }

    fun removeComponent(button: NavigationButton) {
        if (componentMap.remove(button) != null) {
            update()
        }
    }

    private fun update() {
        removeAll()
        var isDelimited = false
        for ((key, value) in componentMap) {
            if (!key.tab && !isDelimited) {
                isDelimited = true
                add(Box.createVerticalGlue())
                addSeparator()
            }
            add(value)
        }
        repaint()
    }

    /**
     * Instantiates a new Client plugin toolbar.
     */
    init {
        isFloatable = false
        size = Dimension(TOOLBAR_WIDTH, TOOLBAR_HEIGHT)
        minimumSize = Dimension(TOOLBAR_WIDTH, TOOLBAR_HEIGHT)
        preferredSize = Dimension(TOOLBAR_WIDTH, TOOLBAR_HEIGHT)
        maximumSize = Dimension(TOOLBAR_WIDTH, Int.MAX_VALUE)
    }
}