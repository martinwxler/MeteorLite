package meteor.plugins

import com.google.inject.Binder
import com.google.inject.Module
import meteor.ConfigManager
import meteor.config.Config
import meteor.eventbus.EventBus
import meteor.events.PluginChanged
import org.sponge.util.Logger

class Plugin : Module {
    var logger = Logger("")

    var enabled = false

    private val config: Config? = null

    private val external = false

    fun startup() {}
    fun shutdown() {}
    fun updateConfig() {}
    fun resetConfiguration() {}
    override fun configure(binder: Binder) {}
    val descriptor: PluginDescriptor
        get() = javaClass.getAnnotation(PluginDescriptor::class.java)
    val name: String
        get() = descriptor.name

    fun getConfig(configManager: ConfigManager?): Config? {
        return config
    }

    fun unload() {
        shutdown()
    }

    @JvmOverloads
    fun toggle(on: Boolean = !enabled) {
        if (!on) {
            shutdown()
            enabled = false
        } else {
            startup()
            enabled = true
        }
        updateConfig()
        EventBus.post(PluginChanged(this, enabled))
    }

    val isToggleable: Boolean
        get() = !descriptor.cantDisable

    init {
        logger.name = descriptor.name
    }
}