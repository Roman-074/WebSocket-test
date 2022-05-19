package websocket.test.lib

import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable
import websocket.test.inapp.SubscribeTickerRequest

interface Api {

    @Receive
    fun openWebSocketEvent(): Flowable<WebSocket.Event>

    @Send
    fun sendTickerRequest(subscribeTickerRequest: SubscribeTickerRequest)

    @Receive
    fun observeTicker(): Flowable<Array<String>>
}
