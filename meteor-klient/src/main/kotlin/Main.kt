import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import meteor.*
import meteor.ui.ClientPluginToolbar
import meteor.ui.NavigationButton
import meteor.util.ImageUtil
import net.runelite.api.Client
import net.runelite.api.hooks.Callbacks
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.koin.core.context.startKoin
import themes.MeteorliteTheme
import java.awt.BorderLayout
import java.awt.Color.black
import java.awt.Dimension
import java.awt.image.BufferedImage
import javax.swing.JPanel

object Main: KoinComponent {
    lateinit var client: Client
    private lateinit var callbacks: Callbacks
    var toolbar: JPanel? = null

    @JvmStatic
    fun main(args: Array<String>) = application {
        startKoin { modules(Module.CLIENT_MODULE) }
        callbacks = get()
        MeteorliteTheme.install()
        AppletConfiguration.init()
        UI().init()
        Window(
                onCloseRequest = ::exitApplication,
                title = "Meteor Klient",
                state = rememberWindowState(width = 1280.dp, height = 720.dp)
        ) {
            MaterialTheme {
                window.minimumSize = Dimension(1280, 720)
                Column(Modifier.fillMaxSize()) {
                    Row(Modifier.height(45.dp).then(Modifier.fillMaxWidth()))  {
                        SwingPanel(Color.Black, factory = {
                            JPanel().apply {
                                toolbar = this
                                layout = BorderLayout()
                                background = black
                                add(ClientPluginToolbar, BorderLayout.CENTER)
                                val icon: BufferedImage = ImageUtil.loadImageResource(ClientPluginToolbar::class.java, "firemaking.png")

                                val navButton = NavigationButton()
                                navButton.tooltip = ("Loot Tracker")
                                navButton.icon = (icon)
                                navButton.priority = (5)
                                //navButton.panel = (panel)

                                ClientPluginToolbar.addNavigation(navButton)
                            }
                        })
                    }
                    Row(Modifier.fillMaxSize())  {
                        SwingPanel(Color.Black, factory = {
                            JPanel().apply {
                                layout = BorderLayout()
                                add(UI.applet, BorderLayout.CENTER)
                                UI.applet.init()
                                UI.applet.start()
                            }
                        })
                    }
                    Row(Modifier.height(0.dp).then(Modifier.fillMaxWidth()))  {
                        SwingPanel(Color.Black, factory = {
                            JPanel().apply {
                                layout = BorderLayout()
                                background = black
                            }
                        })
                    }
                }
            }
        }
        client = UI.getClient(UI.applet)
        client.callbacks = Hooks()
    }
}
