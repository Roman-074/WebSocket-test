package websocket.test.domain.entities

abstract class BaseSubscribe(
    open val event: String,
    open val channel: String,
    open val pair: String
) {
    companion object {
        const val SUBSCRIBE_EVENT = "subscribe"
        const val TICKER_CHANNEL = "ticker"
        const val BTCUSD_PAIR = "BTCUSD"
    }
}
