package websocket.test.data.repository

import io.reactivex.Flowable
import websocket.test.data.entity.toSubcribeTickerRequest
import websocket.test.data.remote.Client
import websocket.test.domain.Repository
import websocket.test.domain.entities.SubscribeTicker
import websocket.test.domain.model.TickerData
import websocket.test.domain.model.toTickerData

class RepositoryImpl(private val client: Client) : Repository {
    override fun observeTicker(subscribeTicker: SubscribeTicker): Flowable<TickerData> {
        return client.subscribeTicker(subscribeTicker.toSubcribeTickerRequest())
            .map { response -> response.toTickerData() }
    }
}
