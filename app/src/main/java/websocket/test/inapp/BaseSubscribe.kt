package websocket.test.inapp

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

class SubscribeTicker(
    override val event: String,
    override val channel: String,
    override val pair: String
) : BaseSubscribe(event, channel, pair)
