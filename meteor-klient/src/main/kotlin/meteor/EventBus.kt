package meteor

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import java.util.concurrent.Executors
import kotlin.coroutines.CoroutineContext

class EventBus<T>(override val coroutineContext: CoroutineContext
                  = Executors.newSingleThreadExecutor().asCoroutineDispatcher())
    :CoroutineScope {

    private val channel = BroadcastChannel<T>(1)

    fun post(event: T, context: CoroutineContext = coroutineContext) {
        this.launch(context) {
            channel.send(event)
        }
    }

    fun subscribe(subs: (event:T)-> Unit,
                  filter: ((event: T) -> Boolean)? = null){
        this.launch {
            channel.asFlow().collect {item ->
                if(filter?.invoke(item) != false){
                    withContext(Dispatchers.Main){
                        subs.invoke(item)
                    }
                }
            }}
    }

    companion object {
        private val instance: EventBus<Event> = EventBus()

        fun post(event: Event) {
            instance.post(event)
        }

        fun subscribe(unit: (Event) -> Unit) {
            instance.subscribe(unit)
        }

        fun subscribe(unit: (Event) -> Unit, filter: ((event: Event) -> Boolean)? = null) {
            instance.subscribe(unit, filter)
        }
    }
}