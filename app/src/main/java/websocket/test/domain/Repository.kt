package websocket.test.domain

import io.reactivex.Flowable
import websocket.test.domain.entities.SubscribeTicker
import websocket.test.domain.model.TickerData

interface Repository {
    fun observeTicker(subscribeTicker: SubscribeTicker): Flowable<TickerData>
}
