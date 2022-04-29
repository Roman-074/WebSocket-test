package websocket.test

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber
import timber.log.Timber.DebugTree

class ClientApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@ClientApplication)
            modules(listOf(appModule))
        }
    }
}
