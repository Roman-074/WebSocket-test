package websocket.test.lib

import android.annotation.SuppressLint
import com.tinder.scarlet.WebSocket
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import websocket.test.inapp.SubscribeTickerRequest

interface BaseISocket {
    fun subscribeTicker(subscribeTickerRequest: SubscribeTickerRequest): Flowable<Array<String>>
}

@SuppressLint("CheckResult")
class BaseISocketImpl(private val api: Api) : BaseISocket {

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
            // https://docs.bitfinex.com/reference#ws-public-ticker
            .filter { it.size == 11 } // make sure it's a ticker
    }
}
