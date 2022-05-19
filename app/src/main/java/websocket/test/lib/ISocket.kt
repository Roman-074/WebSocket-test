package websocket.test.lib

import io.reactivex.Flowable
import websocket.test.inapp.*

interface ISocket {
    fun observeTicker(subscribeTicker: SubscribeTicker): Flowable<TickerData>
}

class ISocketImpl(private val client: BaseISocket) : ISocket {
    override fun observeTicker(subscribeTicker: SubscribeTicker): Flowable<TickerData> {
        return client.subscribeTicker(subscribeTicker.toSubcribeTickerRequest())
            .map { response -> response.toTickerData() }
    }
}
