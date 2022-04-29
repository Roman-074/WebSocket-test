package websocket.test.data.remote

import com.tinder.scarlet.WebSocket
import com.tinder.scarlet.ws.Receive
import com.tinder.scarlet.ws.Send
import io.reactivex.Flowable
import websocket.test.data.entity.JsonResponse
import websocket.test.data.entity.SubscribeTickerRequest

interface Api {

    @Receive
    fun openWebSocketEvent(): Flowable<WebSocket.Event>

    @Receive
    fun receiveResponse(): Flowable<JsonResponse>

    @Send
    fun sendTickerRequest(subscribeTickerRequest: SubscribeTickerRequest)

    @Receive
    fun observeTicker(): Flowable<Array<String>>

    companion object {
        const val BASE_URI = "wss://api.bitfinex.com/ws/"
    }
}
