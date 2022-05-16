package websocket.test.lib

import io.reactivex.Flowable

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
