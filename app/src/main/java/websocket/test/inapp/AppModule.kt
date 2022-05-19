package websocket.test.inapp

import android.app.Application
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.tinder.scarlet.Lifecycle
import com.tinder.scarlet.Scarlet
import com.tinder.scarlet.lifecycle.android.AndroidLifecycle
import com.tinder.scarlet.messageadapter.moshi.MoshiMessageAdapter
import com.tinder.scarlet.streamadapter.rxjava2.RxJava2StreamAdapterFactory
import com.tinder.scarlet.websocket.okhttp.newWebSocketFactory
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import websocket.test.inapp.common.MainViewModel
import websocket.test.lib.*

val appModule = module {
    single { createAndroidLifecycle(application = get()) }
    single { createOkHttpClient() }
    single { createScarlet(okHttpClient = get()) }
    single<BaseISocket> { BaseISocketImpl(api = get()) }
    single<ISocket> {
        ISocketImpl(
            client = get()
        )
    }

    viewModel {
        MainViewModel(
            socket = get()
        )
    }
}

private fun createOkHttpClient(): OkHttpClient {
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    return OkHttpClient.Builder()
        .addInterceptor(
            httpLoggingInterceptor.apply {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            }
        )
        .build()
}

private fun createAndroidLifecycle(application: Application): Lifecycle {
    return AndroidLifecycle.ofApplicationForeground(application)
}

private fun createScarlet(okHttpClient: OkHttpClient): Api {
    return Scarlet.Builder()
        .webSocketFactory(okHttpClient.newWebSocketFactory("wss://api.bitfinex.com/ws/"))
        .addMessageAdapterFactory(MoshiMessageAdapter.Factory(jsonMoshi))
        .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
        .build()
        .create()
}

private val jsonMoshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private fun createTestClient() {
    val client = OkHttpClient().newBuilder().build()
    val mediaType = "text/plain".toMediaTypeOrNull()
    val body = RequestBody.create(mediaType, "{\n \"firstName\": \"Conso\",\n \"insertToTestClient\": false,\n \"investEmailDomain\": true,\n \"isLegal\": false,\n \"lastName\": \"Barsukov\",\n \"middleName\": \"Medoedovich\"\n}")
    val request = Request.Builder()
        .url("http://host.ru:80/user/register")
        .method("POST", body)
        .addHeader("Content-Type", "text/plain")
        .build()
    val response = client.newCall(request).execute()
}
