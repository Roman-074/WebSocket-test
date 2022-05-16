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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import websocket.test.inapp.common.MainViewModel
import websocket.test.lib.Client
import websocket.test.lib.ClientImpl
import websocket.test.lib.ObserveTickerUseCase
import websocket.test.lib.Repository
import websocket.test.lib.RepositoryImpl

// declare a module
val appModule = module {
    // Define single instance of AndroidLifecycle
    // Resolve constructor dependencies with get(), here we need an Application object
    single { createAndroidLifecycle(application = get()) }
    // Define single instance of OkHttpClient
    single { createOkHttpClient() }
    // Define single instance of Scarlet
    // Resolve constructor dependencies with get(), here we need an OkHttpClient, and a lifecycle
    single { createScarlet(okHttpClient = get(), lifecycle = get()) }
    // Define single instance of type BitfinexDataSource
    // Resolve constructor dependencies with get(), here we need a BitfinexApi
    single<Client> { ClientImpl(api = get()) }
    // Define single instance of type BitfinexService (infered parameter in <>)
    // Resolve constructor dependencies with get(), here we need the BitfinexApi
    single<Repository> {
        RepositoryImpl(
            client = get()
        )
    }

    // Define a factory (create a new instance each time) for ObserveTickerUseCase
    // Resolve constructor dependency with get(), here we need the BitfinexService
    factory { ObserveTickerUseCase(repository = get()) }

    // Define ViewModel and resolve constructor dependencies with get(),
    // here we need ObserveTickerUseCase, and ObserveOrderBookUseCase
    viewModel {
        MainViewModel(
            observeTickerUseCase = get()
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

// A Retrofit inspired WebSocket client for Kotlin, Java, and Android, that supports websockets.
private fun createScarlet(okHttpClient: OkHttpClient, lifecycle: Lifecycle): Api {
    return Scarlet.Builder()
        .webSocketFactory(okHttpClient.newWebSocketFactory(Api.BASE_URI))
        .lifecycle(lifecycle)
        .addMessageAdapterFactory(MoshiMessageAdapter.Factory(jsonMoshi))
        .addStreamAdapterFactory(RxJava2StreamAdapterFactory())
        .build()
        .create()
}

private val jsonMoshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()
