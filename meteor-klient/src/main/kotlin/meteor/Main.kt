package meteor

import meteor.Module.CLIENT_MODULE
import meteor.UI.Companion.applet
import net.runelite.api.Client
import net.runelite.api.hooks.Callbacks
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.component.inject
import org.koin.core.context.startKoin

object Main: KoinComponent {
    private val ui: UI by inject()
    private val eventBus: EventBus<Event> by inject()
    lateinit var client: Client

    private lateinit var callbacks: Callbacks

    private fun start() {
        eventBus.subscribe(onEvent())
        eventBus.subscribe(onAppletLoaded()) {
            it == Event.APPLET_LOADED
        }

        AppletConfiguration.init()
        ui.init()
        client = UI.getClient(applet)
        client.callbacks = Hooks()
    }

    @JvmStatic
    fun main(args: Array<String>) {
        startKoin { modules(CLIENT_MODULE) }
        callbacks = get()
        callbacks.post(Event.APPLET_LOADED)
        this.start()
    }

    private fun onAppletLoaded(): (Event) -> Unit {
        return {
            println("Applet Loaded (Filtered)")
        }
    }

    private fun onEvent(): (Event) -> Unit {
        return {
            if (it == Event.APPLET_LOADED)
                println("Applet Loaded (Global)")
        }
    }
}
