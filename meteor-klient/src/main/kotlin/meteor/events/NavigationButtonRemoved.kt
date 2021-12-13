package meteor.events

import meteor.eventbus.Event
import meteor.ui.NavigationButton

class NavigationButtonRemoved(button: NavigationButton) : Event {
    var button: NavigationButton

    init {
        this.button = button
    }
}