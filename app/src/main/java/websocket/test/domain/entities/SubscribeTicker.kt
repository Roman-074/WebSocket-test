package websocket.test.domain.entities

class SubscribeTicker(
    override val event: String,
    override val channel: String,
    override val pair: String
) : BaseSubscribe(event, channel, pair)
