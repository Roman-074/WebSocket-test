package websocket.test.lib

import io.reactivex.Flowable
import websocket.test.inapp.*

interface Repository {

    fun observeTicker(subscribeTicker: SubscribeTicker): Flowable<TickerData>

    fun invoke(): Flowable<TickerData> {
        val subscribeTicker = SubscribeTicker(
            event = BaseSubscribe.SUBSCRIBE_EVENT,
            channel = BaseSubscribe.TICKER_CHANNEL,
            pair = BaseSubscribe.BTCUSD_PAIR
        )
        return observeTicker(subscribeTicker)
    }
}

class RepositoryImpl(private val client: BaseISocket) : Repository {
    override fun observeTicker(subscribeTicker: SubscribeTicker): Flowable<TickerData> {
        return client.subscribeTicker(subscribeTicker.toSubcribeTickerRequest())
            .map { response -> response.toTickerData() }
    }
}
