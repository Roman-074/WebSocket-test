package websocket.test.lib

import io.reactivex.Flowable
import websocket.test.inapp.TickerData
import websocket.test.inapp.toSubcribeTickerRequest
import websocket.test.inapp.toTickerData

interface Repository {
    fun observeTicker(subscribeTicker: SubscribeTicker): Flowable<TickerData>
}

class RepositoryImpl(private val client: Client) : Repository {
    override fun observeTicker(subscribeTicker: SubscribeTicker): Flowable<TickerData> {
        return client.subscribeTicker(subscribeTicker.toSubcribeTickerRequest())
            .map { response -> response.toTickerData() }
    }
}
