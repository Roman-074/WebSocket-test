package websocket.test.inapp

import com.squareup.moshi.Json

abstract class BaseSubscribeRequest(
    @Json(name = "event")
    open val event: String,
    @Json(name = "channel")
    open val channel: String,
    @Json(name = "pair")
    open val pair: String
)

data class SubscribeTickerRequest(
    override val event: String,
    override val channel: String,
    override val pair: String
) : BaseSubscribeRequest(event, channel, pair) {
    companion object {
        const val SUBSCRIBE_EVENT = "subscribe"
        const val TICKER_CHANNEL = "ticker"
        const val BTCUSD_PAIR = "BTCUSD"
    }
}

fun createTickerRequest() =
    SubscribeTickerRequest(
        event = SubscribeTickerRequest.SUBSCRIBE_EVENT,
        channel = SubscribeTickerRequest.TICKER_CHANNEL,
        pair = SubscribeTickerRequest.BTCUSD_PAIR
    )
