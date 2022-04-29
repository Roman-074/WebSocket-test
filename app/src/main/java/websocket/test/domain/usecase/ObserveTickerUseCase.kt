package websocket.test.domain.usecase

import io.reactivex.Flowable
import websocket.test.domain.Repository
import websocket.test.domain.entities.BaseSubscribe
import websocket.test.domain.entities.SubscribeTicker
import websocket.test.domain.model.TickerData

class ObserveTickerUseCase(private val repository: Repository) {

    operator fun invoke(): Flowable<TickerData> {
        val subscribeTicker = SubscribeTicker(
            event = BaseSubscribe.SUBSCRIBE_EVENT,
            channel = BaseSubscribe.TICKER_CHANNEL,
            pair = BaseSubscribe.BTCUSD_PAIR
        )
        return repository.observeTicker(subscribeTicker)
    }
}
