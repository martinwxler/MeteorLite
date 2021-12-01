package meteor

fun main(args: Array<String>) {
    EventBus.subscribe(onEvent())
    AppletConfiguration.init()
    UI().init()
}

fun onEvent(): (Event) -> Unit {
    return {
        if (it == Event.APPLET_LOADED)
            println("Applet Loaded")
    }
}