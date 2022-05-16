package websocket.test.inapp.common

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree
import websocket.test.BuildConfig
import websocket.test.inapp.appModule

class ClientApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        startKoin {
            modules(listOf(appModule))
            androidLogger()
            androidContext(this@ClientApplication)
        }
    }
}
