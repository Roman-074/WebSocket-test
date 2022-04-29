package websocket.test.data.remote

import io.reactivex.Flowable
import websocket.test.data.entity.SubscribeTickerRequest

interface Client {
    fun subscribeTicker(subscribeTickerRequest: SubscribeTickerRequest): Flowable<Array<String>>
}
