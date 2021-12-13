package meteor.events

import meteor.eventbus.Event
import meteor.plugins.Plugin
import meteor.ui.NavigationButton

class PluginChanged(plugin: Plugin, enabled: Boolean) : Event {
    var plugin: Plugin
    var enabled: Boolean
    init {
        this.plugin = plugin
        this.enabled = enabled
    }
}