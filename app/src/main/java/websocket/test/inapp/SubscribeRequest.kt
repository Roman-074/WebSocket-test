package websocket.test.inapp

import com.squareup.moshi.Json
import websocket.test.lib.SubscribeTicker

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
) : BaseSubscribeRequest(event, channel, pair)

fun SubscribeTicker.toSubcribeTickerRequest() =
    SubscribeTickerRequest(
        event = event,
        channel = channel,
        pair = pair
    )
