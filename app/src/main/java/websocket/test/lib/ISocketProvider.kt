package websocket.test.lib

import android.annotation.SuppressLint
import com.tinder.scarlet.WebSocket
import io.reactivex.Flowable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

interface ISocketProvider<T> {
    fun <D> observeTicker(data: D): Flowable<T>
}

interface ISocketBaseProvider {
    fun subscribeTicker(request: Any, sizeResponse: Int): Flowable<Array<String>>
}

@SuppressLint("CheckResult")
class ISocketBaseProviderImpl(private val api: SocketApi) : ISocketBaseProvider {

    override fun subscribeTicker(request: Any, sizeResponse: Int): Flowable<Array<String>> {
        api.openWebSocketEvent()
            .filter {
                it is WebSocket.Event.OnConnectionOpened<*>
            }
            .subscribe({
                api.sendRequest(request)
            }, { e ->
                Timber.e(e)
            })

        return api.currentData()
            .subscribeOn(Schedulers.io())
            // https://docs.bitfinex.com/reference#ws-public-ticker (11)
            .filter { it.size == sizeResponse }
    }
}
