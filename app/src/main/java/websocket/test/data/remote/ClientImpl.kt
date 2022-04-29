package websocket.test.data.remote

import android.annotation.SuppressLint
import com.tinder.scarlet.WebSocket
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import websocket.test.data.entity.SubscribeTickerRequest

@SuppressLint("CheckResult")
class ClientImpl(private val api: Api) : Client {

    private val TICKER_SNAPSHOT_SIZE = 11 // https://docs.bitfinex.com/reference#ws-public-ticker

    override fun subscribeTicker(subscribeTickerRequest: SubscribeTickerRequest): Flowable<Array<String>> {
        api.openWebSocketEvent()
            .filter {
                it is WebSocket.Event.OnConnectionOpened<*>
            }
            .subscribe({
                api.sendTickerRequest(subscribeTickerRequest)
            }, { e ->
                Timber.e(e)
            })

        return api.observeTicker()
            .subscribeOn(Schedulers.io())
            .filter { it.size == TICKER_SNAPSHOT_SIZE } // make sure it's a ticker
    }
}
