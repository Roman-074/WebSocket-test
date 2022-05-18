package websocket.test.inapp

import io.reactivex.Flowable
import websocket.test.lib.ISocketBaseProvider
import websocket.test.lib.ISocketProvider

class ISocketProviderImpl(
    private val iSocketBaseProvider: ISocketBaseProvider
) : ISocketProvider<TickerData> {

    override fun <SubscribeTickerRequest> observeTicker(data: SubscribeTickerRequest): Flowable<TickerData> {
        return iSocketBaseProvider.subscribeTicker(
            createTickerRequest(),
            11,
        )
            .map { response -> response.toTickerData() }
    }
}
