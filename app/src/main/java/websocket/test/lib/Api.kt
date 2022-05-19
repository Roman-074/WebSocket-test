package websocket.test.lib

import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable

interface Api {

    @Receive
    fun openWebSocketEvent(): Flowable<WebSocket.Event>

    @Receive
    fun observeTicker(): Flowable<Array<String>>

    @Send
    fun sendTickerRequest(request: Any)
}
